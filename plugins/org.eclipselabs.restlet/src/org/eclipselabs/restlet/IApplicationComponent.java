/*******************************************************************************
 * Copyright (c) 2011 Bryan Hunt.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Bryan Hunt - initial API and implementation
 *******************************************************************************/

package org.eclipselabs.restlet;

import java.util.Dictionary;

import org.osgi.service.http.HttpContext;

/**
 * @author bhunt
 * 
 */
public interface IApplicationComponent
{
	/**
	 * This is a common interface for several of the providers. Users are not expected to implement
	 * this interface, but instead implement one of the provider interfaces.
	 * 
	 * In the case of an application, the alias is passed to
	 * {@link org.osgi.service.http.HttpService#registerServlet(String alias, Servlet servlet, Dictionary initparams, HttpContext context)}
	 * when the servlet is registered. Any resources that are to be
	 * registered with the application must use this alias. The alias must begin with '/' and must not
	 * end with '/' with one exception when the alias is '/' itself.
	 * 
	 * In the case of a filter, router, or resource, the alias is used to identify the application to
	 * which the component belongs.
	 * 
	 * @return the application alias.
	 * 
	 */
	String getApplicationAlias();
}
