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
import org.eclipselabs.restlet.IResourceProvider;
import org.eclipselabs.restlet.IRouterProvider;
import org.eclipselabs.restlet.impl.FilterProvider;
import org.restlet.routing.Filter;

/**
 * @author bhunt
 * 
 */
public class TestFilterProvider extends FilterProvider
{
	public TestFilterProvider(int weight)
	{
		super("/", weight);
	}

	@Override
	protected Filter createFilter()
	{
		return new TestFilter();
	}

	@Override
	public boolean isFilterFor(IFilterProvider filterProvider)
	{
		return getWeight() < filterProvider.getWeight();
	}

	@Override
	public boolean isFilterFor(IResourceProvider resourceProvider)
	{
		return false;
	}

	@Override
	public boolean isFilterFor(IRouterProvider routerProvider)
	{
		return true;
	}
}
