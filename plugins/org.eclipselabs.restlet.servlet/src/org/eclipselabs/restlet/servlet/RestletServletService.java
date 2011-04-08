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

package org.eclipselabs.restlet.servlet;

import java.util.HashSet;

import org.eclipselabs.restlet.providers.IApplicationProvider;
import org.osgi.service.http.HttpService;
import org.osgi.service.log.LogService;

/**
 * @author bhunt
 * 
 */
public class RestletServletService
{
	public void bindApplicationProvider(IApplicationProvider applicationProvider)
	{
		applicationProviders.add(applicationProvider);

		if (httpService != null)
			registerServlet(applicationProvider);
	}

	public void bindHttpService(HttpService httpService)
	{
		this.httpService = httpService;

		for (IApplicationProvider applicationProvider : applicationProviders)
			registerServlet(applicationProvider);
	}

	public void bindLogService(LogService logService)
	{
		this.logService = logService;
	}

	public void unbindApplicationProvider(IApplicationProvider applicationProvider)
	{
		applicationProviders.remove(applicationProvider);

		if (httpService != null)
		{
			try
			{
				httpService.unregister(applicationProvider.getAlias());
			}
			catch (Throwable t)
			{}
		}
	}

	public void unbindHttpService(HttpService httpService)
	{
		if (this.httpService == httpService)
		{
			for (IApplicationProvider applicationProvider : applicationProviders)
			{
				try
				{
					httpService.unregister(applicationProvider.getAlias());
				}
				catch (IllegalArgumentException e)
				{}
			}

			httpService = null;
		}
	}

	public void unbindLogService(LogService logService)
	{
		if (this.logService == logService)
			this.logService = null;
	}

	private void registerServlet(IApplicationProvider applicationProvider)
	{
		ApplicationServlet servlet = new ApplicationServlet(applicationProvider);

		try
		{
			httpService.registerServlet(applicationProvider.getAlias(), servlet, applicationProvider.getInitParms(), applicationProvider.getContext());
		}
		catch (Exception e)
		{
			if (logService != null)
				logService.log(LogService.LOG_ERROR, "Failed to register the application servlet at alias: '" + applicationProvider.getAlias() + "'", e);
		}
	}

	private HttpService httpService;
	private LogService logService;
	private HashSet<IApplicationProvider> applicationProviders = new HashSet<IApplicationProvider>();
}
