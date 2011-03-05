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

package org.eclipselabs.restlet.junit.bundle;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.service.http.HttpService;
import org.osgi.service.log.LogReaderService;
import org.osgi.service.log.LogService;
import org.osgi.util.tracker.ServiceTracker;

public class Activator implements BundleActivator
{
	public static Activator getInstance()
	{
		return instance;
	}

	public BundleContext getContext()
	{
		return context;
	}

	public HttpService getHttpService() throws InterruptedException
	{
		return (HttpService) httpServiceTracker.waitForService(1000);
	}

	public LogService getLogService() throws InterruptedException
	{
		return (LogService) logServiceTracker.waitForService(1000);
	}

	public LogReaderService getLogReaderService() throws InterruptedException
	{
		return (LogReaderService) logReaderServiceTracker.waitForService(1000);
	}

	@Override
	public void start(BundleContext context) throws Exception
	{
		this.context = context;
		httpServiceTracker = new ServiceTracker(context, HttpService.class.getName(), null);
		httpServiceTracker.open();
		logServiceTracker = new ServiceTracker(context, LogService.class.getName(), null);
		logServiceTracker.open();
		logReaderServiceTracker = new ServiceTracker(context, LogReaderService.class.getName(), null);
		logReaderServiceTracker.open();
		instance = this;
	}

	@Override
	public void stop(BundleContext context) throws Exception
	{
		instance = null;

		if (httpServiceTracker != null)
			httpServiceTracker.close();

		if (logServiceTracker != null)
			logServiceTracker.close();

		if (logReaderServiceTracker != null)
			logReaderServiceTracker.close();
	}

	private static Activator instance;
	private BundleContext context;
	private ServiceTracker httpServiceTracker;
	private ServiceTracker logServiceTracker;
	private ServiceTracker logReaderServiceTracker;
}
