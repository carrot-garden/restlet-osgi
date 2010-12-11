/*******************************************************************************
 * Copyright (c) 2010 Bryan Hunt.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Bryan Hunt - initial API and implementation
 *******************************************************************************/

package org.eclipselabs.restlet.servlet;

import java.util.ArrayList;
import java.util.HashMap;

import org.eclipselabs.restlet.IApplicationProvider;
import org.eclipselabs.restlet.IResourceProvider;
import org.osgi.service.http.HttpService;
import org.osgi.service.log.LogService;

/**
 * @author bhunt
 * 
 */
public class RestletServletService
{
	public synchronized void bindApplicationProvider(IApplicationProvider applicationProvider)
	{
		if (!applicationProviders.containsKey(applicationProvider.getAlias()))
		{
			applicationProviders.put(applicationProvider.getAlias(), applicationProvider);
			ArrayList<IResourceProvider> resourceProviders = pendingResources.get(applicationProvider.getAlias());

			if (resourceProviders != null)
			{
				for (IResourceProvider resourceProvider : resourceProviders)
				{
					for (String path : resourceProvider.getPaths())
						applicationProvider.getRouter().attach(path, resourceProvider.getFinder());
				}

				resourceProviders.clear();
			}

			if (httpService != null)
				registerServlet(applicationProvider);
		}
		else
		{
			if (logService != null)
				logService.log(LogService.LOG_ERROR, "Application servlet at alias: '" + applicationProvider.getAlias() + "' is already registered");
		}
	}

	public synchronized void bindHttpService(HttpService httpService)
	{
		this.httpService = httpService;

		for (IApplicationProvider provider : applicationProviders.values())
			registerServlet(provider);
	}

	public synchronized void bindLogService(LogService logService)
	{
		this.logService = logService;
	}

	public synchronized void bindResourceProvider(IResourceProvider resourceProvider)
	{
		IApplicationProvider applicationProvider = applicationProviders.get(resourceProvider.getApplicationAlias());

		if (applicationProvider != null)
		{
			for (String path : resourceProvider.getPaths())
				applicationProvider.getRouter().attach(path, resourceProvider.getFinder());
		}
		else
		{
			ArrayList<IResourceProvider> resources = pendingResources.get(resourceProvider.getApplicationAlias());

			if (resources == null)
			{
				resources = new ArrayList<IResourceProvider>();
				pendingResources.put(resourceProvider.getApplicationAlias(), resources);
			}

			resources.add(resourceProvider);
		}
	}

	public synchronized void unbindApplicationProvider(IApplicationProvider applicationProvider)
	{
		if (httpService != null)
		{
			try
			{
				httpService.unregister(applicationProvider.getAlias());
			}
			catch (Throwable t)
			{}
		}

		applicationProviders.remove(applicationProvider.getAlias());
	}

	public synchronized void unbindHttpService(HttpService httpService)
	{
		if (this.httpService == httpService)
		{
			for (String alias : applicationProviders.keySet())
				httpService.unregister(alias);

			httpService = null;
		}
	}

	public synchronized void unbindLogService(LogService logService)
	{
		if (this.logService == logService)
			this.logService = null;
	}

	public synchronized void unbindResourceProvider(IResourceProvider resourceProvider)
	{
		IApplicationProvider applicationProvider = applicationProviders.get(resourceProvider.getApplicationAlias());

		if (applicationProvider != null)
		{
			applicationProvider.getRouter().detach(resourceProvider.getFinder());
		}
		else
		{
			ArrayList<IResourceProvider> resources = pendingResources.get(resourceProvider.getApplicationAlias());

			if (resources != null)
				resources.remove(resourceProvider);
		}
	}

	private void registerServlet(IApplicationProvider applicationProvider)
	{
		ApplicationServlet servlet = new ApplicationServlet();
		servlet.setApplication(applicationProvider.getApplication());

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
	private HashMap<String, IApplicationProvider> applicationProviders = new HashMap<String, IApplicationProvider>();
	private HashMap<String, ArrayList<IResourceProvider>> pendingResources = new HashMap<String, ArrayList<IResourceProvider>>();
}
