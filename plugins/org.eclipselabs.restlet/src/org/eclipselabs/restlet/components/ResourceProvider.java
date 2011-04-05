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

import java.util.Dictionary;

import org.eclipselabs.restlet.providers.IResourceProvider;
import org.osgi.service.component.ComponentContext;
import org.restlet.Restlet;
import org.restlet.resource.Finder;

/**
 * @author bhunt
 * 
 */
public abstract class ResourceProvider extends RestletProvider implements IResourceProvider
{
	@Override
	public Finder getFinder()
	{
		if (finder == null)
			finder = createFinder();

		return finder;
	}

	@Override
	public Restlet getInboundRoot()
	{
		Restlet inboundRoot = super.getInboundRoot();
		return inboundRoot != null ? inboundRoot : getFinder();
	}

	@Override
	public String[] getPaths()
	{
		return paths;
	}

	protected void activate(ComponentContext context)
	{
		@SuppressWarnings("unchecked")
		Dictionary<String, Object> properties = context.getProperties();
		paths = (String[]) properties.get("paths");
	}

	protected abstract Finder createFinder();

	private Finder finder;
	private String[] paths;
}
