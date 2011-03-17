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

package org.eclipselabs.restlet;

import org.restlet.routing.Filter;

/**
 * This is an OSGi service interface for registering Restlet filters with a router or a resource.
 * Users are expected to implement this interface and register an instance as an OSGi service.
 * Filters are registered with a router provider. If a router provider is not found for the
 * filter, the filter will be cached until the router provider is registered. If your filters are
 * not being registered, check there is not a typo in the alias in both the filter provider and
 * application provider.
 * 
 * @author bhunt
 */
public interface IFilterProvider extends IApplicationComponent
{
	/**
	 * 
	 * @return the filter to add to the routing chain.
	 */
	Filter getFilter();

	/**
	 * The weight determines the relative ordering of filters attached to the same path. Lower values
	 * will be ordered first in the filter chain while higher values will be ordered later in the
	 * chain. If two filters have the same path and same weight, they will be ordered randomly
	 * relative to each other.
	 * 
	 * This function is very experimental.
	 * 
	 * @return the ordering weight of the filter.
	 */
	int getWeight();

	/**
	 * Determines whether or not this filter precedes the target filter.
	 * 
	 * @param filterProvider the target filter.
	 * @return true if this filter precedes the target filter and false if this filter is after the
	 *         target filter
	 */
	boolean isFilterFor(IFilterProvider filterProvider);

	/**
	 * Determines whether or not this filter is a filter for the target router.
	 * 
	 * @param routerProvider the target router.
	 * @return true if this filter is a filter for the target router and false otherwise
	 */
	boolean isFilterFor(IRouterProvider routerProvider);
}
