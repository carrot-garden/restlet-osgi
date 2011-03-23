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

package org.eclipselabs.restlet.di;

import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import org.eclipse.e4.core.contexts.IEclipseContext;

/**
 * @author bhunt
 * 
 */
@SuppressWarnings("restriction")
public class InjectedServerResource extends ServerResource
{
	/**
	 * @param eclipseContext the eclipseContext to set
	 */
	public void setEclipseContext(IEclipseContext eclipseContext)
	{
		this.eclipseContext = eclipseContext;
	}

	@Override
	protected void doRelease() throws ResourceException
	{
		super.doRelease();
		eclipseContext.dispose();
	}

	private IEclipseContext eclipseContext;
}
