/*******************************************************************************
 * Copyright (c) 2011 Bryan Hunt.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Bryan Hunt - initial API and implementation
 *******************************************************************************/

package org.eclipselabs.restlet.junit;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.eclipselabs.restlet.IApplicationBuilder;
import org.eclipselabs.restlet.IApplicationProvider;
import org.eclipselabs.restlet.IFilterProvider;
import org.eclipselabs.restlet.IResourceProvider;
import org.eclipselabs.restlet.IRouterProvider;
import org.eclipselabs.restlet.junit.bundle.Activator;
import org.eclipselabs.restlet.servlet.RestletServletService;
import org.osgi.service.http.HttpService;
import org.osgi.service.log.LogReaderService;
import org.osgi.service.log.LogService;

/**
 * @author bhunt
 * 
 */
public class RestletTestHarness
{
	public void addApplication(IApplicationProvider applicationProvider)
	{
		restletServletService.bindApplicationProvider(applicationProvider);
	}

	public void addApplicationBuilder(IApplicationBuilder applicationBuilder)
	{
		restletServletService.bindApplicationBuilder(applicationBuilder);
	}

	public void addFilter(IFilterProvider filterProvider)
	{
		restletServletService.bindFilterProvider(filterProvider);
	}

	public void addResource(IResourceProvider resourceProvider)
	{
		restletServletService.bindResourceProvider(resourceProvider);
	}

	public void addRouter(IRouterProvider routerProvider)
	{
		restletServletService.bindRouterProvider(routerProvider);
	}

	public LogReaderService getLogReaderService()
	{
		return logReaderService;
	}

	public void removeApplication(IApplicationProvider applicationProvider)
	{
		restletServletService.unbindApplicationProvider(applicationProvider);
	}

	public void removeApplicationBuilder(IApplicationBuilder applicationBuilder)
	{
		restletServletService.unbindApplicationBuilder(applicationBuilder);
	}

	public void removeFilter(IFilterProvider filterProvider)
	{
		restletServletService.unbindFilterProvider(filterProvider);
	}

	public void removeResource(IResourceProvider resourceProvider)
	{
		restletServletService.unbindResourceProvider(resourceProvider);
	}

	public void removeRouter(IRouterProvider routerProvider)
	{
		restletServletService.unbindRouterProvider(routerProvider);
	}

	public void setUp() throws InterruptedException
	{
		httpService = Activator.getInstance().getHttpService();
		assertThat(httpService, is(notNullValue()));

		logService = Activator.getInstance().getLogService();
		assertThat(logService, is(notNullValue()));

		logReaderService = Activator.getInstance().getLogReaderService();
		assertThat(logReaderService, is(notNullValue()));

		restletServletService = new RestletServletService();
		restletServletService.bindHttpService(httpService);
		restletServletService.bindLogService(logService);
	}

	public void tearDown(String applicationAlias)
	{
		try
		{
			httpService.unregister(applicationAlias);
		}
		catch (Throwable t)
		{}
	}

	private HttpService httpService;
	private static LogService logService;
	private static LogReaderService logReaderService;
	private RestletServletService restletServletService;
}
