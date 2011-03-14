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

package org.eclipselabs.restlet;

import java.util.LinkedList;

import org.restlet.Application;
import org.restlet.routing.Filter;
import org.restlet.routing.Router;

/**
 * @author bhunt
 * 
 */
public class DefaultApplicationBuilder implements IApplicationBuilder
{
	public DefaultApplicationBuilder()
	{
		filterProviders = new LinkedList<IFilterProvider>();
		routerProviders = new LinkedList<IRouterProvider>();
		resourceProviders = new LinkedList<IResourceProvider>();

		IRouterProvider rootRouterProvider = createRootRouterProvider();

		if (rootRouterProvider != null)
			addRouterProvider(rootRouterProvider);
	}

	public DefaultApplicationBuilder(String applicationAlias)
	{
		this();
		this.applicationAlias = applicationAlias;
	}

	@Override
	public void addFilterProvider(IFilterProvider filterProvider)
	{
		if (applicationProvider != null)
			applyApplicationFilter(applicationProvider.getApplication(), filterProvider);
		else
			filterProviders.add(filterProvider);
	}

	@Override
	public void addResourceProvider(IResourceProvider resourceProvider)
	{
		resourceProviders.add(resourceProvider);

		for (IRouterProvider routerProvider : routerProviders)
		{
			if (routerProvider.isRouterFor(resourceProvider))
			{
				Router router = routerProvider.getRouter();
				routeResource(resourceProvider, router);

				break;
			}
		}
	}

	@Override
	public void addRouterProvider(IRouterProvider routerProvider)
	{
		routerProviders.add(routerProvider);

		Router router = routerProvider.getRouter();

		for (IResourceProvider resourceProvider : resourceProviders)
		{
			if (routerProvider.isRouterFor(resourceProvider))
				routeResource(resourceProvider, router);
		}
	}

	@Override
	public Application buildApplication(IApplicationProvider applicationProvider)
	{
		this.applicationProvider = applicationProvider;
		attachRouters(applicationProvider.getApplication());

		return applicationProvider.getApplication();
	}

	@Override
	public String getApplicationAlias()
	{
		return applicationAlias;
	}

	@Override
	public void removeFilterProvider(IFilterProvider filterProvider)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void removeResourceProvider(IResourceProvider resourceProvider)
	{
		resourceProviders.remove(resourceProvider);

		for (IRouterProvider routerProvider : routerProviders)
		{
			if (routerProvider.isRouterFor(resourceProvider))
			{
				routerProvider.getRouter().detach(resourceProvider.getFinder());
				break;
			}
		}
	}

	@Override
	public void removeRouterProvider(IRouterProvider routerProvider)
	{
		// TODO Auto-generated method stub

	}

	protected Application createApplication()
	{
		return new Application();
	}

	protected IRouterProvider createRootRouterProvider()
	{
		return new DefaultRouterProvider();
	}

	protected void routeResource(IResourceProvider resourceProvider, Router router)
	{
		resourceProviders.remove(resourceProvider);

		for (String path : resourceProvider.getPaths())
			router.attach(path, resourceProvider.getFinder());
	}

	private void applyApplicationFilter(Application application, IFilterProvider filterProvider)
	{
		if (!filterProviders.isEmpty())
		{
			for (int i = filterProviders.size() - 1; i >= 0; i--)
			{
				IFilterProvider previousFilterProvider = filterProviders.get(i);

				if (!filterProvider.isFilterFor(previousFilterProvider))
				{
					Filter previousFilter = previousFilterProvider.getFilter();
					filterProvider.getFilter().setNext(previousFilter.getNext());
					previousFilter.setNext(filterProvider.getFilter());
					filterProviders.add(i + 1, filterProvider);
					return;
				}
			}
		}

		filterProviders.addFirst(filterProvider);
		Filter filter = filterProvider.getFilter();
		filter.setNext(application.getInboundRoot());
		application.setInboundRoot(filter);
	}

	private void attachRouters(Application application)
	{
		Router root = null;
		Router previousRouter = null;

		for (IRouterProvider routerProvider : routerProviders)
		{
			Router router = routerProvider.getRouter();

			if (root == null)
				root = router;

			if (previousRouter != null)
				previousRouter.attachDefault(router);

			previousRouter = router;
		}

		if (application.getInboundRoot() == null)
		{
			application.setInboundRoot(root);
		}
		else
		{
			Filter filter = (Filter) application.getInboundRoot();

			while (filter.getNext() != null)
				filter = (Filter) filter.getNext();

			filter.setNext(root);
		}
	}

	private String applicationAlias;

	private IApplicationProvider applicationProvider;
	private LinkedList<IFilterProvider> filterProviders;

	private LinkedList<IRouterProvider> routerProviders;
	private LinkedList<IResourceProvider> resourceProviders;
}
