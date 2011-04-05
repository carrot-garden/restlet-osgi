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

import org.eclipselabs.restlet.components.ResourceProvider;
import org.restlet.resource.Finder;

/**
 * @author bhunt
 * 
 */
public class TestResourceProvider extends ResourceProvider
{
	@Override
	public String[] getPaths()
	{
		return new String[] { "/junit/" };
	}

	@Override
	protected Finder createFinder()
	{
		return new Finder(null, TestResource.class);
	}
}
