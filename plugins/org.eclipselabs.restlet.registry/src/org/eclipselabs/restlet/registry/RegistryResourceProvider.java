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
import org.eclipselabs.restlet.impl.DynamicFinder;
import org.eclipselabs.restlet.impl.ResourceProvider;
import org.osgi.framework.Bundle;
import org.osgi.service.log.LogService;

public class RegistryResourceProvider extends ResourceProvider
{
	public RegistryResourceProvider(Bundle bundle, IConfigurationElement config, LogService logService)
	{
		super(config.getAttribute("application_alias"), getPaths(config), new DynamicFinder(bundle, config.getAttribute("class"), logService));
	}

	private static String[] getPaths(IConfigurationElement config)
	{

		IConfigurationElement[] resourcePaths = config.getChildren("resource_path");
		String[] paths = new String[resourcePaths.length];

		for (int i = 0; i < resourcePaths.length; i++)
			paths[i] = resourcePaths[i].getAttribute("path");

		return paths;
	}
}
