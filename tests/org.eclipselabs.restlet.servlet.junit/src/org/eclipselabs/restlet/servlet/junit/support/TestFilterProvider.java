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

package org.eclipselabs.restlet.servlet.junit.support;

import org.eclipselabs.restlet.IFilterProvider;
import org.eclipselabs.restlet.IRouterProvider;
import org.restlet.routing.Filter;

/**
 * @author bhunt
 * 
 */
public class TestFilterProvider implements IFilterProvider
{
	public TestFilterProvider()
	{
		this.weight = 0;
	}

	public TestFilterProvider(int weight)
	{
		this.weight = weight;
	}

	@Override
	public String getApplicationAlias()
	{
		return "/";
	}

	@Override
	public Filter getFilter()
	{
		return filter;
	}

	@Override
	public int getWeight()
	{
		return weight;
	}

	@Override
	public boolean isFilterFor(IFilterProvider filterProvider)
	{
		return filterProvider.getWeight() > weight;
	}

	@Override
	public boolean isFilterFor(IRouterProvider routerProvider)
	{
		return true;
	}

	private int weight;
	private Filter filter = new TestFilter();
}
