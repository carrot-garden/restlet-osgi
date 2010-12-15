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

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipselabs.restlet.IApplicationProvider;
import org.osgi.service.http.HttpContext;
import org.osgi.service.log.LogService;
import org.restlet.Application;
import org.restlet.routing.Router;

/**
 * @author bhunt
 * 
 */
public class RegistryApplicationProvider implements IApplicationProvider
{
	public RegistryApplicationProvider(IConfigurationElement config, LogService logService)
	{
		this.alias = config.getAttribute("alias");
		this.config = config;
		this.logService = logService;
	}

	@Override
	public String getAlias()
	{
		return alias;
	}

	@Override
	public Application getApplication()
	{
		if (application == null)
		{
			try
			{
				application = (Application) config.createExecutableExtension("class");
				application.setInboundRoot(router);
			}
			catch (CoreException e)
			{
				if (logService != null)
					logService.log(LogService.LOG_ERROR, "Failed to create application class: '" + config.getAttribute("class"), e);
			}
		}

		return application;
	}

	@Override
	public HttpContext getContext()
	{
		return null;
	}

	@Override
	public Dictionary<String, Object> getInitParms()
	{
		return null;
	}

	@Override
	public Router getRouter()
	{
		return router;
	}

	private String alias;
	private IConfigurationElement config;
	private Router router = new Router();
	private Application application;
	private LogService logService;
}
