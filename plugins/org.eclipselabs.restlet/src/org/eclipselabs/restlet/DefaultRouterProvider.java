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
	public String getApplicationAlias()
	{
		return applicationAlias;
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

	protected Router createRouter()
	{
		return new Router();
	}

	private String applicationAlias;
	private Router router;
}
