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

import org.restlet.routing.Router;

/**
 * This is an OSGi service interface for registering Restlet filters with an application. Users are
 * expected to implement this interface and register an instance as an OSGi service.
 * 
 * It is recommended to use or extend {@link org.eclipselabs.restlet.impl.RouterProvider}
 * 
 * @author bhunt
 * 
 */
public interface IRouterProvider extends IFilteredProvider
{
	/**
	 * @return the router to add to the routing chain.
	 */
	Router getRouter();

	/**
	 * Users can return an application specific object to help in determining whether or not this
	 * router should be before or after another router.
	 * 
	 * @return application specific data
	 */
	Object getRouterInfo();

	/**
	 * Determines whether or not this router handles the routing for the target resource.
	 * 
	 * @param resourceProvider the target resource.
	 * @return true if this router handles the routing for the target resource and false otherwise.
	 */
	boolean isRouterFor(IResourceProvider resourceProvider);

	/**
	 * Determines whether or not this router precedes the target router.
	 * 
	 * @param routerProvider the target router.
	 * @return true if this router precedes the target router and false if this router is after the
	 *         target filter
	 */
	boolean isRouterFor(IRouterProvider routerProvider);
}
