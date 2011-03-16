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

import java.util.HashMap;

import org.eclipselabs.restlet.IApplicationBuilder;
import org.eclipselabs.restlet.IApplicationProvider;
import org.eclipselabs.restlet.IFilterProvider;
import org.eclipselabs.restlet.IResourceProvider;
import org.eclipselabs.restlet.IRouterProvider;
import org.osgi.service.http.HttpService;
import org.osgi.service.log.LogService;

/**
 * @author bhunt
 * 
 */
public class RestletServletService
{
	public void bindApplicationBuilder(IApplicationBuilder applicationBuilder)
	{
		ApplicationStagingArea applicationStagingArea = getApplicationStagingArea(applicationBuilder.getApplicationAlias());
		applicationStagingArea.setApplicationBuilder(applicationBuilder);

		if (applicationStagingArea.isReady() && httpService != null)
			registerServlet(applicationStagingArea);
	}

	public void bindApplicationProvider(IApplicationProvider applicationProvider)
	{
		ApplicationStagingArea applicationStagingArea = getApplicationStagingArea(applicationProvider.getApplicationAlias());

		if (applicationStagingArea.getApplicationProvider() == null)
		{
			applicationStagingArea.setApplicationProvider(applicationProvider);

			if (applicationStagingArea.isReady() && httpService != null)
				registerServlet(applicationStagingArea);
		}
		else
		{
			if (logService != null)
				logService.log(LogService.LOG_ERROR, "Application servlet at alias: '" + applicationProvider.getApplicationAlias() + "' is already registered");
		}
	}

	public void bindFilterProvider(IFilterProvider filterProvider)
	{
		getApplicationStagingArea(filterProvider.getApplicationAlias()).addFilterProvider(filterProvider);
	}

	public void bindHttpService(HttpService httpService)
	{
		this.httpService = httpService;

		for (ApplicationStagingArea applicationStagingArea : applicationStagingAreas.values())
		{
			if (applicationStagingArea.isReady())
				registerServlet(applicationStagingArea);
		}
	}

	public void bindLogService(LogService logService)
	{
		this.logService = logService;
	}

	public void bindResourceProvider(IResourceProvider resourceProvider)
	{
		getApplicationStagingArea(resourceProvider.getApplicationAlias()).addResourceProvider(resourceProvider);
	}

	public void bindRouterProvider(IRouterProvider routerProvider)
	{
		getApplicationStagingArea(routerProvider.getApplicationAlias()).addRouterProvider(routerProvider);
	}

	public void unbindApplicationBuilder(IApplicationBuilder applicationBuilder)
	{
		getApplicationStagingArea(applicationBuilder.getApplicationAlias()).setApplicationBuilder(null);
	}

	public void unbindApplicationProvider(IApplicationProvider applicationProvider)
	{
		if (httpService != null)
		{
			try
			{
				httpService.unregister(applicationProvider.getApplicationAlias());
			}
			catch (Throwable t)
			{}
		}

		getApplicationStagingArea(applicationProvider.getApplicationAlias()).setApplicationProvider(null);
	}

	public void unbindFilterProvider(IFilterProvider filterProvider)
	{
		getApplicationStagingArea(filterProvider.getApplicationAlias()).removeFilterProvider(filterProvider);
	}

	public void unbindHttpService(HttpService httpService)
	{
		if (this.httpService == httpService)
		{
			for (String alias : applicationStagingAreas.keySet())
			{
				try
				{
					httpService.unregister(alias);
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

	public void unbindResourceProvider(IResourceProvider resourceProvider)
	{
		getApplicationStagingArea(resourceProvider.getApplicationAlias()).removeResourceProvider(resourceProvider);
	}

	public void unbindRouterProvider(IRouterProvider routerProvider)
	{
		getApplicationStagingArea(routerProvider.getApplicationAlias()).removeRouterProvider(routerProvider);
	}

	private synchronized ApplicationStagingArea getApplicationStagingArea(String applicationAlias)
	{
		ApplicationStagingArea applicationStagingArea = applicationStagingAreas.get(applicationAlias);

		if (applicationStagingArea == null)
		{
			applicationStagingArea = new ApplicationStagingArea();
			applicationStagingAreas.put(applicationAlias, applicationStagingArea);
		}

		return applicationStagingArea;
	}

	private void registerServlet(ApplicationStagingArea applicationStagingArea)
	{
		ApplicationServlet servlet = new ApplicationServlet();
		servlet.setApplication(applicationStagingArea.getApplication());
		IApplicationProvider applicationProvider = applicationStagingArea.getApplicationProvider();

		try
		{
			httpService.registerServlet(applicationProvider.getApplicationAlias(), servlet, applicationProvider.getInitParms(), applicationProvider.getContext());
		}
		catch (Exception e)
		{
			if (logService != null)
				logService.log(LogService.LOG_ERROR, "Failed to register the application servlet at alias: '" + applicationProvider.getApplicationAlias() + "'", e);
		}
	}

	private HttpService httpService;
	private LogService logService;
	private HashMap<String, ApplicationStagingArea> applicationStagingAreas = new HashMap<String, ApplicationStagingArea>();
}
