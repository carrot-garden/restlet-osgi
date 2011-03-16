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

import org.restlet.routing.Filter;

/**
 * This is an OSGi service interface for registering Restlet filters with an application or route
 * to a resource. Users are expected to implement this interface and register an instance as an OSGi
 * service. Filters are registered with an application according to the application alias and a path
 * to a resource. If an application is not found that corresponds to the specified alias, or a
 * resource is not found at the specified path, the filter will be cached until the application /
 * resource are registered. If your filters are not being registered, check there is not a typo in
 * the alias in both the filter provider and application provider.
 * 
 * @author bhunt
 */
public interface IFilterProvider
{
	/**
	 * The application alias is the identifier of the application to which this resource belongs.
	 * 
	 * @return the application alias to attach this resource to.
	 */
	String getApplicationAlias();

	/**
	 * 
	 * @return the finder for locating the resource. It is recommended to use @see DynamicFinder if
	 *         you need to support lazy loading.
	 */
	Filter getFilter();

	/**
	 * 
	 * @return the path to the resource relative to the application alias. The path must start with
	 *         '/'. If the path is the empty string, the filter will be attached to the application.
	 */
	String getPath();

	/**
	 * The weight determines the relative ordering of filters attached to the same path. Lower values
	 * will be ordered first in the filter chain while higher values will be ordered later in the
	 * chain.
	 * If two filters have the same path and same weight, they will be ordered randomly relative to
	 * each
	 * other.
	 * 
	 * @return the ordering weight of the filter.
	 */
	int getWeight();

	boolean isFilterFor(IFilterProvider filterProvider);

	boolean isFilterFor(IRouterProvider routerProvider);
}
