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

package org.eclipselabs.restlet.providers;

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
public interface IFilterProvider extends IRestletProvider
{
	/**
	 * 
	 * @return the filter to add to the routing chain.
	 */
	Filter getFilter();
}
