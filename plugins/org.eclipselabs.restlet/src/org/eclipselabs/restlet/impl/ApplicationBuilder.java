/*******************************************************************************
 * Copyright (c) 2011.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     - initial API and implementation
 *******************************************************************************/

package org.eclipselabs.restlet.impl;

import java.util.LinkedList;

import org.eclipselabs.restlet.IApplicationBuilder;
import org.eclipselabs.restlet.IApplicationProvider;
import org.eclipselabs.restlet.IFilterProvider;
import org.eclipselabs.restlet.IResourceProvider;
import org.eclipselabs.restlet.IRouterProvider;
import org.restlet.Application;
import org.restlet.routing.Router;

/**
 * @author bhunt
 * 
 */
public class ApplicationBuilder extends ApplicationComponent implements IApplicationBuilder
{
	public ApplicationBuilder()
	{
		this(null);
	}

	public ApplicationBuilder(String applicationAlias)
	{
		super(applicationAlias);

		filterProviders = new LinkedList<IFilterProvider>();
		routerProviders = new LinkedList<IRouterProvider>();
		resourceProviders = new LinkedList<IResourceProvider>();

		IRouterProvider rootRouterProvider = createRootRouterProvider();

		if (rootRouterProvider != null)
			addRouterProvider(rootRouterProvider);
	}

	@Override
	public void addFilterProvider(IFilterProvider filterProvider)
	{
		filterProviders.add(filterProvider);
		boolean firstRouter = true;

		for (IRouterProvider routerProvider : routerProviders)
		{
			if (filterProvider.isFilterFor(routerProvider))
			{
				boolean inboundRootChanged = routerProvider.addFilterProvider(filterProvider);

				if (firstRouter && inboundRootChanged)
					applicationProvider.getApplication().setInboundRoot(routerProvider.getInboundRoot());

				return;
			}
		}

		for (IResourceProvider resourceProvider : resourceProviders)
		{
			if (filterProvider.isFilterFor(resourceProvider))
			{
				if (resourceProvider.addFilterProvider(filterProvider))
					rerouteResource(resourceProvider);

				break;
			}
		}
	}

	@Override
	public void addResourceProvider(IResourceProvider resourceProvider)
	{
		resourceProviders.add(resourceProvider);

		for (IRouterProvider routerProvider : routerProviders)
		{
			if (routerProvider.isRouterFor(resourceProvider))
			{
				routerProvider.addResourceProvider(resourceProvider);
				break;
			}
		}

		for (IFilterProvider filterProvider : filterProviders)
		{
			if (filterProvider.isFilterFor(resourceProvider))
			{
				if (resourceProvider.addFilterProvider(filterProvider))
					rerouteResource(resourceProvider);

				break;
			}
		}
	}

	@Override
	public void addRouterProvider(IRouterProvider routerProvider)
	{
		if (attachRouter(routerProvider))
		{
			if (applicationProvider != null)
				applicationProvider.getApplication().setInboundRoot(routerProvider.getInboundRoot());
		}

		Router router = routerProvider.getRouter();

		for (IFilterProvider filterProvider : filterProviders)
		{
			if (filterProvider.isFilterFor(routerProvider))
			{
				routerProvider.addFilterProvider(filterProvider);
				break;
			}
		}

		for (IResourceProvider resourceProvider : resourceProviders)
		{
			if (routerProvider.isRouterFor(resourceProvider))
			{
				routeResource(resourceProvider, router);
				break;
			}
		}
	}

	@Override
	public Application buildApplication(IApplicationProvider applicationProvider)
	{
		this.applicationProvider = applicationProvider;

		if (!routerProviders.isEmpty())
			applicationProvider.getApplication().setInboundRoot(routerProviders.getFirst().getInboundRoot());

		String messages = checkRouting();

		if (messages.length() > 0)
			System.out.println(messages);

		return applicationProvider.getApplication();
	}

	public String checkRouting()
	{
		StringBuilder messages = new StringBuilder();

		for (IResourceProvider resourceProvider : resourceProviders)
		{
			boolean routed = false;

			for (IRouterProvider routerProvider : routerProviders)
			{
				if (routerProvider.getResourceProviders().contains(resourceProvider))
				{
					routed = true;
					break;
				}
			}

			if (!routed)
			{
				messages.append("Resource not routed: ");

				for (String path : resourceProvider.getPaths())
				{
					messages.append(path);
					messages.append(' ');
				}

				messages.append("\n");
			}
		}

		for (IFilterProvider filterProvider : filterProviders)
		{
			boolean routed = false;

			for (IRouterProvider routerProvider : routerProviders)
			{
				if (routerProvider.getFilterProviders().contains(filterProvider))
				{
					routed = true;
					break;
				}

				if (!routed)
				{
					for (IResourceProvider resourceProvider : routerProvider.getResourceProviders())
					{
						if (resourceProvider.getFilterProviders().contains(filterProvider))
						{
							routed = true;
							break;
						}
					}
				}
			}

			if (!routed)
			{
				messages.append("Filter not routed: ");
				messages.append(filterProvider.getFilter().getClass().getName());
				messages.append("\n");
			}
		}

		return messages.toString();
	}

	@Override
	public void removeFilterProvider(IFilterProvider filterProvider)
	{
		for (IRouterProvider routerProvider : routerProviders)
		{
			if (filterProvider.isFilterFor(routerProvider))
			{
				routerProvider.removeFilterProvider(filterProvider);
				return;
			}
		}

		for (IResourceProvider resourceProvider : resourceProviders)
		{
			if (filterProvider.isFilterFor(resourceProvider))
			{
				if (resourceProvider.removeFilterProvider(filterProvider))
					rerouteResource(resourceProvider);

				return;
			}
		}
	}

	@Override
	public void removeResourceProvider(IResourceProvider resourceProvider)
	{
		resourceProviders.remove(resourceProvider);

		for (IRouterProvider routerProvider : routerProviders)
		{
			if (routerProvider.isRouterFor(resourceProvider))
			{
				routerProvider.removeResourceProvider(resourceProvider);
				break;
			}
		}
	}

	@Override
	public void removeRouterProvider(IRouterProvider routerProvider)
	{
		int index = routerProviders.indexOf(routerProvider);

		if (index == 0)
		{
			if (applicationProvider != null)
			{
				Router nextRouter = null;

				if (routerProviders.size() > 1)
					nextRouter = routerProviders.get(1).getRouter();

				applicationProvider.getApplication().setInboundRoot(nextRouter);
			}
		}
		else
		{
			Router previousRouter = routerProviders.get(index - 1).getRouter();
			previousRouter.detach(routerProvider.getRouter());

			if (routerProviders.size() > index + 1)
				previousRouter.attachDefault(routerProviders.get(index + 1).getInboundRoot());
		}

		routerProviders.remove(index);
	}

	protected Application createApplication()
	{
		return new Application();
	}

	protected IRouterProvider createRootRouterProvider()
	{
		return new RouterProvider();
	}

	protected void routeResource(IResourceProvider resourceProvider, Router router)
	{
		for (String path : resourceProvider.getPaths())
			router.attach(path, resourceProvider.getFinder());
	}

	private void rerouteResource(IResourceProvider resourceProvider)
	{
		for (IRouterProvider routerProvider : routerProviders)
		{
			if (routerProvider.getResourceProviders().contains(resourceProvider))
			{
				routeResource(resourceProvider, routerProvider.getRouter());
				break;
			}
		}
	}

	private boolean attachRouter(IRouterProvider routerProvider)
	{
		for (int i = 0; i < routerProviders.size(); i++)
		{
			IRouterProvider targetFilterProvider = routerProviders.get(i);

			if (routerProvider.isRouterFor(targetFilterProvider))
			{
				routerProviders.add(i, routerProvider);
				routerProvider.getRouter().attachDefault(targetFilterProvider.getInboundRoot());

				if (i > 0)
				{
					IRouterProvider previousRouterProvider = routerProviders.get(i - 1);
					previousRouterProvider.getRouter().attachDefault(routerProvider.getRouter());
				}

				return i == 0;
			}
		}

		routerProviders.add(routerProvider);
		return true;
	}

	private IApplicationProvider applicationProvider;
	private LinkedList<IFilterProvider> filterProviders;
	private LinkedList<IRouterProvider> routerProviders;
	private LinkedList<IResourceProvider> resourceProviders;
}
