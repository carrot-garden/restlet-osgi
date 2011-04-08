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
import org.eclipselabs.restlet.providers.IRestletProvider;
import org.restlet.Context;
import org.restlet.Restlet;

/**
 * @author bhunt
 * 
 */
public abstract class RestletProvider implements IRestletProvider
{
	public void bindFilterProvider(IFilterProvider filterProvider)
	{
		this.filterProvider = filterProvider;
	}

	@Override
	public Restlet getInboundRoot(Context context)
	{
		Restlet inboundRoot = null;

		if (filterProvider != null)
		{
			inboundRoot = filterProvider.getInboundRoot(context);
			filterProvider.getFilter().setNext(getFilteredRestlet());
		}

		return inboundRoot;
	}

	public void unbindFilterProvider(IFilterProvider filterProvider)
	{
		if (this.filterProvider == filterProvider)
			this.filterProvider = null;
	}

	protected abstract Restlet getFilteredRestlet();

	private IFilterProvider filterProvider;
}
