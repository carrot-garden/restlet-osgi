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

package org.eclipselabs.restlet.servlet;

import java.util.LinkedList;

import org.eclipselabs.restlet.IApplicationBuilder;
import org.eclipselabs.restlet.IApplicationProvider;
import org.eclipselabs.restlet.IFilterProvider;
import org.eclipselabs.restlet.IResourceProvider;
import org.eclipselabs.restlet.IRouterProvider;
import org.restlet.Application;

/**
 * This class is a staging area for holding providers until the application bulider is registered.
 * Once the application builder is registered, the staging area deferrs to the application builder.
 * 
 * @author bhunt
 * 
 */
public class ApplicationStagingArea
{
	public ApplicationStagingArea()
	{
		pendingFilterProviders = new LinkedList<IFilterProvider>();
		pendingRouterProviders = new LinkedList<IRouterProvider>();
		pendingResourceProviders = new LinkedList<IResourceProvider>();
	}

	public synchronized void addFilterProvider(IFilterProvider filterProvider)
	{
		if (applicationBuilder != null)
			applicationBuilder.addFilterProvider(filterProvider);
		else
			pendingFilterProviders.add(filterProvider);
	}

	public synchronized void addResourceProvider(IResourceProvider resourceProvider)
	{
		if (applicationBuilder != null)
			applicationBuilder.addResourceProvider(resourceProvider);
		else
			pendingResourceProviders.add(resourceProvider);
	}

	public synchronized void addRouterProvider(IRouterProvider routerProvider)
	{
		if (applicationBuilder != null)
			applicationBuilder.addRouterProvider(routerProvider);
		else
			pendingRouterProviders.add(routerProvider);
	}

	public synchronized Application getApplication()
	{
		if (application == null)
			application = applicationBuilder.buildApplication(applicationProvider);

		return application;
	}

	public synchronized IApplicationProvider getApplicationProvider()
	{
		return applicationProvider;
	}

	public synchronized boolean isReady()
	{
		return applicationBuilder != null && applicationProvider != null;
	}

	public synchronized void removeFilterProvider(IFilterProvider filterProvider)
	{
		if (applicationBuilder != null)
			applicationBuilder.removeFilterProvider(filterProvider);
		else
			pendingFilterProviders.remove(filterProvider);
	}

	public synchronized void removeResourceProvider(IResourceProvider resourceProvider)
	{
		if (applicationBuilder != null)
			applicationBuilder.removeResourceProvider(resourceProvider);
		else
			pendingResourceProviders.remove(resourceProvider);
	}

	public synchronized void removeRouterProvider(IRouterProvider routerProvider)
	{
		if (applicationBuilder != null)
			applicationBuilder.removeRouterProvider(routerProvider);
		else
			pendingRouterProviders.remove(routerProvider);
	}

	public synchronized void setApplicationBuilder(IApplicationBuilder applicationBuilder)
	{
		this.applicationBuilder = applicationBuilder;

		for (IFilterProvider filterProvider : pendingFilterProviders)
			applicationBuilder.addFilterProvider(filterProvider);

		for (IRouterProvider routerProvider : pendingRouterProviders)
			applicationBuilder.addRouterProvider(routerProvider);

		for (IResourceProvider resourceProvider : pendingResourceProviders)
			applicationBuilder.addResourceProvider(resourceProvider);

		pendingFilterProviders.clear();
		pendingRouterProviders.clear();
		pendingResourceProviders.clear();
	}

	public synchronized void setApplicationProvider(IApplicationProvider applicationProvider)
	{
		this.applicationProvider = applicationProvider;
	}

	private Application application;

	private IApplicationBuilder applicationBuilder;
	private IApplicationProvider applicationProvider;
	private LinkedList<IFilterProvider> pendingFilterProviders;
	private LinkedList<IRouterProvider> pendingRouterProviders;
	private LinkedList<IResourceProvider> pendingResourceProviders;
}
