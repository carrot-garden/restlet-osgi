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

package org.eclipselabs.restlet.components;

import java.util.Dictionary;

import org.eclipselabs.restlet.providers.IApplicationProvider;
import org.eclipselabs.restlet.providers.IRouterProvider;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.http.HttpContext;
import org.restlet.Application;

/**
 * @author bhunt
 * 
 */
public class ApplicationProvider implements IApplicationProvider
{
	public void bindRouterProvider(IRouterProvider routerProvider)
	{
		this.routerProvider = routerProvider;
		getApplication().setInboundRoot(routerProvider.getInboundRoot());
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
			application = createApplication();

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

	protected Application createApplication()
	{
		return new Application();
	}

	private String alias;
	private Application application;
	private IRouterProvider routerProvider;
}
