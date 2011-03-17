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

import org.eclipselabs.restlet.IFilterProvider;
import org.eclipselabs.restlet.IFilteredProvider;
import org.restlet.Restlet;

/**
 * @author bhunt
 * 
 */
public abstract class FilteredProvider extends ApplicationComponent implements IFilteredProvider
{
	public FilteredProvider()
	{}

	public FilteredProvider(String applicationAlias)
	{
		super(applicationAlias);
	}

	@Override
	public boolean addFilterProvider(IFilterProvider filterProvider)
	{
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
		filterProvider.getFilter().setNext(getFilteredRestlet());

		if (filterProviders.size() > 1)
			filterProviders.get(filterProviders.size() - 2).getFilter().setNext(filterProvider.getFilter());

		return filterProviders.size() == 1;
	}

	@Override
	public Restlet getInboundRoot()
	{
		if (!filterProviders.isEmpty())
			return filterProviders.getFirst().getFilter();

		return getFilteredRestlet();
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

	protected abstract Restlet getFilteredRestlet();

	private LinkedList<IFilterProvider> filterProviders = new LinkedList<IFilterProvider>();
}
