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

package org.eclipselabs.restlet.components;

import org.eclipselabs.restlet.providers.IResourceProvider;
import org.eclipselabs.restlet.providers.IRouterProvider;
import org.restlet.Restlet;
import org.restlet.routing.Router;

/**
 * @author bhunt
 * 
 */
public class RouterProvider extends RestletProvider implements IRouterProvider
{
	public void bindResourceProvider(IResourceProvider resourceProvider)
	{
		for (String path : resourceProvider.getPaths())
			getRouter().attach(path, resourceProvider.getInboundRoot());
	}

	@Override
	public Restlet getInboundRoot()
	{
		Restlet inboundRoot = super.getInboundRoot();
		return inboundRoot != null ? inboundRoot : getRouter();
	}

	@Override
	public Router getRouter()
	{
		if (router == null)
			router = createRouter();

		return router;
	}

	public void unbindResourceProvider(IResourceProvider resourceProvider)
	{
		getRouter().detach(resourceProvider.getInboundRoot());
	}

	protected Router createRouter()
	{
		return new Router();
	}

	private Router router;
}
