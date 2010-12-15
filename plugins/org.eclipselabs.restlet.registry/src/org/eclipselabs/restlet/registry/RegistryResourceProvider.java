/*******************************************************************************
 * Copyright (c) 2010 Bryan Hunt & Wolfgang Werner.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Bryan Hunt & Wolfgang Werner - initial API and implementation
 *******************************************************************************/

package org.eclipselabs.restlet.registry;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipselabs.restlet.DynamicFinder;
import org.eclipselabs.restlet.IResourceProvider;
import org.osgi.framework.Bundle;
import org.osgi.service.log.LogService;
import org.restlet.resource.Finder;

public class RegistryResourceProvider implements IResourceProvider
{
	public RegistryResourceProvider(Bundle bundle, IConfigurationElement config, LogService logService)
	{
		this.applicationAlias = config.getAttribute("application_alias");
		this.finder = new DynamicFinder(bundle, config.getAttribute("class"), logService);

		IConfigurationElement[] resourcePaths = config.getChildren("resource_path");
		paths = new String[resourcePaths.length];

		for (int i = 0; i < resourcePaths.length; i++)
			paths[i] = resourcePaths[i].getAttribute("path");
	}

	@Override
	public String getApplicationAlias()
	{
		return applicationAlias;
	}

	@Override
	public Finder getFinder()
	{
		return finder;
	}

	@Override
	public String[] getPaths()
	{
		return paths;
	}

	private String applicationAlias;
	private String[] paths;
	private Finder finder;
}
