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

import org.eclipselabs.restlet.components.FilterProvider;
import org.restlet.Context;
import org.restlet.routing.Filter;

/**
 * @author bhunt
 * 
 */
public class TestFilterProvider extends FilterProvider
{
	public TestFilterProvider(int returnValue)
	{
		this.returnValue = returnValue;
	}

	@Override
	protected Filter createFilter(Context context)
	{
		TestFilter testFilter = new TestFilter(context);
		testFilter.setReturnValue(returnValue);
		return testFilter;
	}

	private int returnValue;
}
