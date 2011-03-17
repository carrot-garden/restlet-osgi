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

import org.osgi.service.http.HttpContext;
import org.restlet.Application;

/**
 * @author bhunt
 * 
 */
public class DefaultApplicationProvider extends ApplicationComponent implements IApplicationProvider
{
	public DefaultApplicationProvider()
	{}

	public DefaultApplicationProvider(String applicationAlias, HttpContext context, Dictionary<String, Object> initParms)
	{
		super(applicationAlias);
		this.context = context;
		this.initParms = initParms;
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
		return context;
	}

	@Override
	public Dictionary<String, Object> getInitParms()
	{
		return initParms;
	}

	protected Application createApplication()
	{
		return new Application();
	}

	protected void setContext(HttpContext context)
	{
		this.context = context;
	}

	protected void setInitParms(Dictionary<String, Object> initParms)
	{
		this.initParms = initParms;
	}

	private Application application;
	private HttpContext context;
	private Dictionary<String, Object> initParms;
}
