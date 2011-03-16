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

package org.eclipselabs.restlet;

import java.util.Dictionary;

import org.osgi.service.http.HttpContext;
import org.restlet.Application;

/**
 * This is an OSGi service interface for registering Restlet applications with a server servlet.
 * Users are expected to register an instance as an OSGi service. You may use the
 * DefaultApplicationProvider directly, extend it, or provide your own implementation of
 * IApplicationProvider. A server servlet will be created and registered with the web container at
 * the specified alias. The application will then be registered with the servlet.
 * 
 * @author bhunt
 */
public interface IApplicationProvider extends IApplicationComponent
{
	/**
	 * 
	 * @return the application to be register at the specified alias.
	 */
	Application getApplication();

	/**
	 * The context is passed to @See org.osgi.service.http.HttpService#registerServlet(String alias,
	 * Servlet servlet, Dictionary initparams, HttpContext context) when the servlet is registered.
	 * 
	 * @return the context to use with the server servlet.
	 */
	HttpContext getContext();

	/**
	 * The parameters are passed to @See org.osgi.service.http.HttpService#registerServlet(String
	 * alias, Servlet servlet, Dictionary initparams, HttpContext context) when the servlet is
	 * registered.
	 * 
	 * @return the initialization parameters to use with the server servlet.
	 */
	Dictionary<String, Object> getInitParms();
}
