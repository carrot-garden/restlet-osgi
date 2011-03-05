/*******************************************************************************
 * Copyright (c) 2010 Bryan Hunt.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Bryan Hunt - initial API and implementation
 *******************************************************************************/

package org.eclipselabs.restlet.registry.junit.support;

import org.restlet.Application;
import org.restlet.Context;
import org.restlet.Restlet;
import org.restlet.data.Parameter;
import org.restlet.routing.Router;

/**
 * @author bhunt
 * 
 */
public class TestApplication extends Application
{
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.restlet.Application#createInboundRoot()
	 */
	@Override
	public Restlet createInboundRoot()
	{
		return new Router();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.restlet.Application#setContext(org.restlet.Context)
	 */
	@Override
	public void setContext(Context context)
	{
		// TODO Auto-generated method stub
		super.setContext(context);
		System.out.println("Context parameters");

		for (Parameter parameter : context.getParameters())
			System.out.println(parameter.getName() + " : " + parameter.getValue());
	}
}
