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

import org.eclipselabs.restlet.IResourceProvider;
import org.eclipselabs.restlet.IRouterProvider;
import org.restlet.Restlet;
import org.restlet.routing.Router;

/**
 * @author bhunt
 * 
 */
public class RouterProvider extends FilteredProvider implements IRouterProvider
{
	public RouterProvider()
	{
		this.router = createRouter();
	}

	public RouterProvider(String applicationAlias)
	{
		super(applicationAlias);
		this.router = createRouter();
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
		return false;
	}

	protected Router createRouter()
	{
		return new Router();
	}

	@Override
	protected Restlet getFilteredRestlet()
	{
		return router;
	}

	Router router;
}
