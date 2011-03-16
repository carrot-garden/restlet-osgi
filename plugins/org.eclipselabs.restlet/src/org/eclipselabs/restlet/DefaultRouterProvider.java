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

import org.restlet.Restlet;
import org.restlet.routing.Router;

/**
 * @author bhunt
 * 
 */
public class DefaultRouterProvider implements IRouterProvider
{
	public DefaultRouterProvider()
	{
		this.router = createRouter();
	}

	public DefaultRouterProvider(String applicationAlias)
	{
		this.applicationAlias = applicationAlias;
	}

	@Override
	public boolean addFilterProvider(IFilterProvider filterProvider)
	{
		// FIXME hack to get things working - order needs to be determined by calling isFilterFor()
		// TODO look at pulling the code from the DefaultApplicationBuilder

		for (int i = 0; i < filterProviders.size(); i++)
		{
			IFilterProvider targetFilterProvider = filterProviders.get(i);

			if (filterProvider.isFilterFor(targetFilterProvider))
			{
				filterProviders.add(i, filterProvider);
				filterProvider.getFilter().setNext(targetFilterProvider.getFilter());
				return i == 0;
			}
		}

		// The filter is the last one in the chain before the router

		filterProviders.add(filterProvider);
		filterProvider.getFilter().setNext(router);

		if (filterProviders.size() > 1)
			filterProviders.get(filterProviders.size() - 2).getFilter().setNext(filterProvider.getFilter());

		return filterProviders.size() == 1;
	}

	@Override
	public String getApplicationAlias()
	{
		return applicationAlias;
	}

	@Override
	public Restlet getInboundRoot()
	{
		if (!filterProviders.isEmpty())
			return filterProviders.getFirst().getFilter();

		return router;
	}

	@Override
	public Router getRouter()
	{
		return router;
	}

	@Override
	public Object getRouterInfo()
	{
		return null;
	}

	@Override
	public boolean isRouterFor(IResourceProvider resourceProvider)
	{
		return true;
	}

	@Override
	public boolean isRouterFor(IRouterProvider routerProvider)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeFilterProvider(IFilterProvider filterProvider)
	{
		int index = filterProviders.indexOf(filterProvider);
		filterProviders.remove(index);

		if (index > 0)
		{
			IFilterProvider previousFilterProvider = filterProviders.get(index - 1);
			previousFilterProvider.getFilter().setNext(filterProvider.getFilter().getNext());
			return false;
		}

		return true;
	}

	protected Router createRouter()
	{
		return new Router();
	}

	private String applicationAlias;
	private Router router;
	private LinkedList<IFilterProvider> filterProviders = new LinkedList<IFilterProvider>();
}
