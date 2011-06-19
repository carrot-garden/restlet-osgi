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

package org.eclipselabs.restlet.wadl.di.eclipse;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipselabs.restlet.di.eclipse.InjectedResource;
import org.restlet.ext.wadl.WadlServerResource;
import org.restlet.resource.ResourceException;

/**
 * @author bhunt
 * 
 */
@SuppressWarnings("restriction")
public class InjectedWadlServerResource extends WadlServerResource implements InjectedResource
{
	/**
	 * @param eclipseContext the eclipseContext to set
	 */
	@Override
	public void setEclipseContext(IEclipseContext eclipseContext)
	{
		this.eclipseContext = eclipseContext;
	}

	@Override
	protected void doRelease() throws ResourceException
	{
		super.doRelease();

		if (eclipseContext != null)
			eclipseContext.dispose();
	}

	private IEclipseContext eclipseContext;
}
