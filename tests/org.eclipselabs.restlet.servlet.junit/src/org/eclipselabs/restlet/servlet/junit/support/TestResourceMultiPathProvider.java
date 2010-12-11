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

package org.eclipselabs.restlet.servlet.junit.support;

import org.eclipselabs.restlet.IResourceProvider;
import org.restlet.resource.Finder;

/**
 * @author bhunt
 * 
 */
public class TestResourceMultiPathProvider implements IResourceProvider
{
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipselabs.restlet.IResourceProvider#getApplicationAlias()
	 */
	@Override
	public String getApplicationAlias()
	{
		return "/";
	}

	@Override
	public Finder getFinder()
	{
		return finder;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipselabs.restlet.IResourceProvider#getPath()
	 */
	@Override
	public String[] getPaths()
	{
		return new String[] { "/junit/", "/junit/{$id}" };
	}

	private Finder finder = new Finder(null, TestResource.class);
}
