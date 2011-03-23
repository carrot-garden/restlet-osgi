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

import org.eclipselabs.restlet.IFilterProvider;
import org.eclipselabs.restlet.IRouterProvider;
import org.restlet.routing.Filter;

/**
 * @author bhunt
 * 
 */
public abstract class FilterProvider extends ApplicationComponent implements IFilterProvider
{
	public FilterProvider(String applicationAlias, int weight)
	{
		super(applicationAlias);
		this.weight = weight;
	}

	@Override
	public Filter getFilter()
	{
		if (filter == null)
			filter = createFilter();

		return filter;
	}

	@Override
	public int getWeight()
	{
		return weight;
	}

	protected abstract Filter createFilter();

	private int weight;
	private Filter filter;
}
