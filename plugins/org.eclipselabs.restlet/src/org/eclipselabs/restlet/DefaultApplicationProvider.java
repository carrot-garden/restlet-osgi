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
public class DefaultApplicationProvider implements IApplicationProvider
{
	public DefaultApplicationProvider()
	{
		this.application = createApplication();
	}

	public DefaultApplicationProvider(String alias)
	{
		this();
		this.alias = alias;
	}

	@Override
	public String getAlias()
	{
		return alias;
	}

	@Override
	public Application getApplication()
	{
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

	private Application application;
	private String alias;
	private HttpContext context;
	private Dictionary<String, Object> initParms;
}
