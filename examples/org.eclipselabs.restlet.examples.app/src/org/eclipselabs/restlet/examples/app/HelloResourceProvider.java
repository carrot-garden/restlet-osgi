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

package org.eclipselabs.restlet.examples.app;

import org.eclipselabs.restlet.IResourceProvider;
import org.restlet.resource.Finder;

/**
 * @author bhunt
 * 
 */
public class HelloResourceProvider implements IResourceProvider
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipselabs.restlet.IResourceProvider#getPath()
	 */
	@Override
	public String[] getPaths()
	{
		return new String[] { "/hello" };
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipselabs.restlet.IResourceProvider#getFinder()
	 */
	@Override
	public Finder getFinder()
	{
		return finder;
	}

	private Finder finder = new Finder(null, HelloResource.class);;
}
