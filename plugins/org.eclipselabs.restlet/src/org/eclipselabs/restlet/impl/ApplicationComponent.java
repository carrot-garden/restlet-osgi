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

package org.eclipselabs.restlet.impl;

import org.eclipselabs.restlet.IApplicationComponent;

/**
 * @author bhunt
 * 
 */
public class ApplicationComponent implements IApplicationComponent
{
	public ApplicationComponent()
	{}

	public ApplicationComponent(String applicationAlias)
	{
		this.applicationAlias = applicationAlias;
	}

	@Override
	public String getApplicationAlias()
	{
		return applicationAlias;
	}

	protected void setApplicationAlias(String applicationAlias)
	{
		this.applicationAlias = applicationAlias;
	}

	private String applicationAlias;
}
