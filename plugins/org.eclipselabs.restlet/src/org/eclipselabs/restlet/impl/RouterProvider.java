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

import java.util.HashSet;
import java.util.Set;

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
		this(null, new String[] { "" });
	}

	public RouterProvider(String applicationAlias, String[] path)
	{
		super(applicationAlias);
		this.path = path;
		resourceProviders = new HashSet<IResourceProvider>();
	}

	@Override
	public void addResourceProvider(IResourceProvider resourceProvider)
	{
		resourceProviders.add(resourceProvider);

		for (String path : resourceProvider.getPaths())
			router.attach(path, resourceProvider.getInboundRoot());
	}

	@Override
	public String[] getPath()
	{
		return path;
	}

	@Override
	public Set<IResourceProvider> getResourceProviders()
	{
		return resourceProviders; // TODO do we need to worry about thread safety
	}

	@Override
	public Router getRouter()
	{
		if (router == null)
			router = createRouter();

		return router;
	}

	@Override
	public boolean isRouterFor(IResourceProvider resourceProvider)
	{
		return path[path.length - 1].equals(resourceProvider.getRouter());
	}

	@Override
	public boolean isRouterFor(IRouterProvider routerProvider)
	{
		if (path.length == routerProvider.getPath().length)
			throw new IllegalStateException("Router provider has conflicting path at " + path);

		return path.length < routerProvider.getPath().length;
	}

	@Override
	public void removeResourceProvider(IResourceProvider resourceProvider)
	{
		if (resourceProviders.remove(resourceProvider))
			router.detach(resourceProvider.getInboundRoot());
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

	private Router router;
	private String[] path;
	private HashSet<IResourceProvider> resourceProviders;
}
