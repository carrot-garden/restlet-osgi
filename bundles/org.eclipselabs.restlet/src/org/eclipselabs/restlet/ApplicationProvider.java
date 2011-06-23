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

package org.eclipselabs.restlet;

import java.util.Dictionary;

import org.osgi.service.component.ComponentContext;
import org.osgi.service.http.HttpContext;
import org.restlet.Application;
import org.restlet.Context;

/**
 * @author bhunt
 * 
 */
public class ApplicationProvider implements IApplicationProvider
{
	public void bindRouterProvider(IRouterProvider routerProvider)
	{
		this.routerProvider = routerProvider;

		if (application != null)
			application.setInboundRoot(routerProvider.getInboundRoot(application.getContext()));
	}

	@Override
	public Application createApplication(Context context)
	{
		application = doCreateApplication(context);

		if (routerProvider != null)
			application.setInboundRoot(routerProvider.getInboundRoot(context));

		return application;
	}

	@Override
	public String getAlias()
	{
		return alias;
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

	public void unbindRouterProvider(IRouterProvider routerProvider)
	{
		if (this.routerProvider == routerProvider)
			this.routerProvider = null;
	}

	protected void activate(ComponentContext context)
	{
		@SuppressWarnings("unchecked")
		Dictionary<String, Object> properties = context.getProperties();
		alias = (String) properties.get("alias");
	}

	protected Application doCreateApplication(Context context)
	{
		return new Application(context);
	}

	private String alias;
	private Application application;
	private IRouterProvider routerProvider;
}
