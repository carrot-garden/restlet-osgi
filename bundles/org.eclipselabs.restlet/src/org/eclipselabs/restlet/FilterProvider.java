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

package org.eclipselabs.restlet;

import org.restlet.Context;
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
		return filter;
	}

	@Override
	public Restlet getInboundRoot(Context context)
	{
		if (filter == null)
			filter = createFilter(context);

		Restlet inboundRoot = super.getInboundRoot(context);
		return inboundRoot != null ? inboundRoot : filter;
	}

	protected abstract Filter createFilter(Context context);

	@Override
	protected Restlet getFilteredRestlet()
	{
		return filter;
	}

	private Filter filter;
}
