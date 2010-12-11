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

package org.eclipselabs.restlet.servlet.junit.support;

import java.util.Dictionary;

import org.eclipselabs.restlet.IApplicationProvider;
import org.osgi.service.http.HttpContext;
import org.restlet.Application;
import org.restlet.routing.Router;

/**
 * @author bhunt
 * 
 */
public class TestApplicationProvider implements IApplicationProvider
{
	@Override
	public String getAlias()
	{
		return "/";
	}

	@Override
	public Application getApplication()
	{
		if (application == null)
		{
			application = new TestApplication();
			application.setInboundRoot(router);
		}

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

	@Override
	public Router getRouter()
	{
		return router;
	}

	private Router router = new Router();
	private Application application;
}
