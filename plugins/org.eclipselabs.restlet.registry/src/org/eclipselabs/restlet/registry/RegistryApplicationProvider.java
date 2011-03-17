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

import java.util.Dictionary;
import java.util.Hashtable;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipselabs.restlet.impl.ApplicationProvider;
import org.osgi.service.log.LogService;
import org.restlet.Application;

/**
 * @author bhunt
 * 
 */
public class RegistryApplicationProvider extends ApplicationProvider
{
	public RegistryApplicationProvider(IConfigurationElement config, LogService logService)
	{
		super(config.getAttribute("application_alias"), null, getInitParms(config));
		this.config = config;
		this.logService = logService;
	}

	@Override
	protected Application createApplication()
	{
		try
		{
			return (Application) config.createExecutableExtension("class");
		}
		catch (CoreException e)
		{
			if (logService != null)
				logService.log(LogService.LOG_ERROR, "Failed to create application class: '" + config.getAttribute("class"), e);

			return null;
		}
	}

	private static Dictionary<String, Object> getInitParms(IConfigurationElement config)
	{
		Hashtable<String, Object> initParms = new Hashtable<String, Object>();

		for (IConfigurationElement configElement : config.getChildren("init_parm"))
			initParms.put(configElement.getAttribute("key"), configElement.getAttribute("value"));

		return initParms;
	}

	private IConfigurationElement config;
	private LogService logService;
}
