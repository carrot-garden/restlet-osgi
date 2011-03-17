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

import org.restlet.Application;

/**
 * This is the interface for building applications from its parts. These functions are called by the
 * staging area when filters, resources, and routers are bound and unbound.
 * 
 * It is recommended that you use or extend {@link org.eclipselabs.restlet.impl.ApplicationBuilder}
 * 
 * @author bhunt
 * 
 */
public interface IApplicationBuilder extends IApplicationComponent
{
	/**
	 * Adds a filter to the application
	 * 
	 * @param filterProvider the filter to add
	 */
	void addFilterProvider(IFilterProvider filterProvider);

	/**
	 * Adds a resource to the application
	 * 
	 * @param resourceProvider the resource to add
	 */
	void addResourceProvider(IResourceProvider resourceProvider);

	/**
	 * Adds a router to the application
	 * 
	 * @param routerProvider the router to add
	 */
	void addRouterProvider(IRouterProvider routerProvider);

	/**
	 * Builds the application by connecting the filters, routers, and resources in a chain.
	 * 
	 * @param applicationProvider the application factory
	 * @return the constructed application
	 */
	Application buildApplication(IApplicationProvider applicationProvider);

	/**
	 * Removes a filter from the application
	 * 
	 * @param filterProvider the filter to remove
	 */
	void removeFilterProvider(IFilterProvider filterProvider);

	/**
	 * Removes a resource from the application
	 * 
	 * @param resourceProvider the resource to remove
	 */
	void removeResourceProvider(IResourceProvider resourceProvider);

	/**
	 * Removes a router from the application
	 * 
	 * @param routerProvider the router to remove
	 */
	void removeRouterProvider(IRouterProvider routerProvider);
}
