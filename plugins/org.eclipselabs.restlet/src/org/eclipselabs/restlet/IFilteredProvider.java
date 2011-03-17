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

import org.restlet.Restlet;

/**
 * This is a common interface for several of the providers. Users are not expected to implement
 * this interface, but instead implement one of the provider interfaces.
 * 
 * @author bhunt
 */
public interface IFilteredProvider extends IApplicationComponent
{
	/**
	 * This function should be called by the application builder to attach a filter to a router or
	 * resource. If the filter is added as the first filter in the chain, the implementation must
	 * return true so the builder knows to hook the new inbound root to the last restlet in the
	 * preceding chain should one exist.
	 * 
	 * @param filterProvider the filter provider to attach
	 * @return true if the filter was added as the first filter in the chain and false otherwise.
	 */
	boolean addFilterProvider(IFilterProvider filterProvider);

	/**
	 * Returns the Restlet that is the first one in the chain which could be a filter, router, or
	 * resource.
	 * 
	 * @return the inbound root of the restlet chain.
	 */
	Restlet getInboundRoot();

	/**
	 * This function should be called by the application builder to detach a filter from a router or
	 * resource. If the filter was first in the chain, the implementation must return true so the
	 * builder knows to hook the new inbound root to the last restlet in the preceding chain should
	 * one exist.
	 * 
	 * @param filterProvider the filter provider to detach.
	 * @return true if the filter was removed as the first filter in the chain and false otherwise.
	 */
	boolean removeFilterProvider(IFilterProvider filterProvider);
}
