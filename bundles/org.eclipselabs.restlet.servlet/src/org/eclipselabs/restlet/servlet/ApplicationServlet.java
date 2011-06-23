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

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.eclipselabs.restlet.IApplicationProvider;
import org.restlet.Application;
import org.restlet.Context;
import org.restlet.ext.servlet.ServerServlet;

/**
 * @author bhunt
 * 
 */
public class ApplicationServlet extends ServerServlet
{
	public static final String SERVLETCONFIG_ATTRIBUTE = "javax.servlet.ServletConfig";

	public ApplicationServlet(IApplicationProvider applicationProvider)
	{
		this.applicationProvider = applicationProvider;
	}

	@Override
	protected Application createApplication(Context context)
	{
		Context childContext = context.createChildContext();
		childContext.getAttributes().put(SERVLETCONFIG_ATTRIBUTE, servletConfig);
		return applicationProvider.createApplication(childContext);
	}

	@Override
	public void init(ServletConfig config) throws ServletException
	{
		servletConfig = config;
		super.init(config);
	}

	private static final long serialVersionUID = 5252087180467260130L;
	private transient IApplicationProvider applicationProvider;
	private ServletConfig servletConfig;
}
