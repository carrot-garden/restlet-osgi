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
import org.restlet.Restlet;
import org.restlet.resource.Finder;

/**
 * @author bhunt
 * 
 */
public abstract class ResourceProvider extends FilteredProvider implements IResourceProvider
{
	public ResourceProvider(String applicationAlias, String[] paths, String router)
	{
		super(applicationAlias);
		this.paths = paths;
		this.router = router;
	}

	@Override
	public Finder getFinder()
	{
		if (finder == null)
			finder = createFinder();

		return finder;
	}

	@Override
	public String[] getPaths()
	{
		return paths;
	}

	@Override
	public String getRouter()
	{
		return router;
	}

	protected abstract Finder createFinder();

	@Override
	protected Restlet getFilteredRestlet()
	{
		return getFinder();
	}

	private String router;
	private String[] paths;
	private Finder finder;
}
