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

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.routing.Filter;

/**
 * @author bhunt
 * 
 */
public class TestFilter extends Filter
{
	public boolean isBeforeHandleCalled()
	{
		return beforeHandleCalled;
	}

	public void setReturnValue(int returnValue)
	{
		this.returnValue = returnValue;
	}

	@Override
	protected int beforeHandle(Request request, Response response)
	{
		beforeHandleCalled = true;
		return returnValue;
	}

	int returnValue;
	boolean beforeHandleCalled = false;
}
