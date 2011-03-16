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
import org.restlet.Restlet;
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
		filterProviders.add(filterProvider);
		boolean firstRouter = true;

		for (IRouterProvider routerProvider : routerProviders)
		{
			if (filterProvider.isFilterFor(routerProvider))
			{
				boolean inboundRootChanged = routerProvider.addFilterProvider(filterProvider);

				if (firstRouter && inboundRootChanged)
					appllicationProvider.getApplication().setInboundRoot(routerProvider.getInboundRoot());
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

		for (IFilterProvider filterProvider : filterProviders)
		{
			if (filterProvider.isFilterFor(routerProvider))
				routerProvider.addFilterProvider(filterProvider);
		}

		for (IResourceProvider resourceProvider : resourceProviders)
		{
			if (routerProvider.isRouterFor(resourceProvider))
				routeResource(resourceProvider, router);
		}
	}

	@Override
	public Application buildApplication(IApplicationProvider applicationProvider)
	{
		this.appllicationProvider = applicationProvider;
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

	private void attachRouters(Application application)
	{
		Restlet applicationRoot = null;
		Router previousRouter = null;

		for (IRouterProvider routerProvider : routerProviders)
		{
			Router router = routerProvider.getRouter();

			if (applicationRoot == null)
				applicationRoot = routerProvider.getInboundRoot();

			if (previousRouter != null)
				previousRouter.attachDefault(routerProvider.getInboundRoot());

			previousRouter = router;
		}

		application.setInboundRoot(applicationRoot);
	}

	private String applicationAlias;
	private IApplicationProvider appllicationProvider;
	private LinkedList<IFilterProvider> filterProviders;
	private LinkedList<IRouterProvider> routerProviders;
	private LinkedList<IResourceProvider> resourceProviders;
}
