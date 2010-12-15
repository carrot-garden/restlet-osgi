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
import org.restlet.routing.Router;

/**
 * This is an OSGi service interface for registering Restlet applications with a server servlet.
 * Users are expected to implement this interface and register an instance as an OSGi service. A
 * server servlet will be created and registered with the web container at the specified alias. The
 * application will then be registered with the servlet. Any resources registered with the
 * application alias will be automatically attached to the router.
 * 
 * @author bhunt
 */
public interface IApplicationProvider
{
	/**
	 * The alias is passed to @See org.osgi.service.http.HttpService#registerServlet(String
	 * alias, Servlet servlet, Dictionary initparams, HttpContext context) when the servlet is
	 * registered. Any resources that are to be registered with the application must use this alias.
	 * The alias must begin with '/' and must not end with '/' with one exception when the alias is
	 * '/' itself.
	 * 
	 * @return the application alias used to register the server servlet with the web container.
	 * 
	 */
	String getAlias();

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

	/**
	 * All resources registered with the application alias will be attached to this router. It is
	 * up to the implementation of this interface to attach the router to the application for example,
	 * the inboundRoot.
	 * 
	 * @return the application router.
	 */
	Router getRouter();
}
