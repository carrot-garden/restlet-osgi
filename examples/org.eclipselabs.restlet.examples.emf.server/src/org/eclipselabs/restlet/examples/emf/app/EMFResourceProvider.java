/*******************************************************************************
 * Copyright (c) 2010.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     - initial API and implementation
 *******************************************************************************/

package org.eclipselabs.restlet.examples.emf.app;

import org.eclipselabs.restlet.IResourceProvider;
import org.restlet.resource.Finder;

/**
 * @author bhunt
 * 
 */
public class EMFResourceProvider implements IResourceProvider
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
		return new String[] { "/resource/{collection}/", "/resource/{collection}/{resource}" };
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

	private Finder finder = new Finder(null, EMFResource.class);;
}
