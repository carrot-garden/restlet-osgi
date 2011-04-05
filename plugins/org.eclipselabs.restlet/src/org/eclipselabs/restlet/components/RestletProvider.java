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
import org.restlet.Restlet;

/**
 * @author bhunt
 * 
 */
public class RestletProvider implements IRestletProvider
{
	public void bindfilterProvider(IFilterProvider filterProvider)
	{
		this.filterProvider = filterProvider;
	}

	@Override
	public Restlet getInboundRoot()
	{
		return filterProvider != null ? filterProvider.getInboundRoot() : null;
	}

	public void unbindFilterProvider(IFilterProvider filterProvider)
	{
		if (this.filterProvider == filterProvider)
			this.filterProvider = null;
	}

	private IFilterProvider filterProvider;
}
