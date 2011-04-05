/*******************************************************************************
 * Copyright (c) 2011.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     - initial API and implementation
 *******************************************************************************/

package org.eclipselabs.restlet.components;

import org.eclipselabs.restlet.providers.IFilterProvider;
import org.restlet.Restlet;
import org.restlet.routing.Filter;

/**
 * @author bhunt
 * 
 */
public abstract class FilterProvider extends RestletProvider implements IFilterProvider
{
	@Override
	public Filter getFilter()
	{
		if (filter == null)
			filter = createFilter();

		return filter;
	}

	@Override
	public Restlet getInboundRoot()
	{
		Restlet inboundRoot = super.getInboundRoot();
		return inboundRoot != null ? inboundRoot : getFilter();
	}

	protected abstract Filter createFilter();

	private Filter filter;
}
