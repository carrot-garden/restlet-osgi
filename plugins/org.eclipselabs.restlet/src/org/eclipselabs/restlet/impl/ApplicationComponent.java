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

import java.util.Dictionary;

import org.eclipselabs.restlet.IApplicationComponent;
import org.osgi.service.component.ComponentContext;

/**
 * @author bhunt
 * 
 */
public abstract class ApplicationComponent implements IApplicationComponent
{
	public ApplicationComponent(String applicationAlias)
	{
		this.applicationAlias = applicationAlias;
	}

	@Override
	public String getApplicationAlias()
	{
		if (applicationAlias == null || applicationAlias.length() == 0)
			throw new IllegalStateException("The application.alias property was not set when the provider was registered.");

		return applicationAlias;
	}

	protected void activate(ComponentContext context)
	{
		if (applicationAlias == null)
		{
			@SuppressWarnings("unchecked")
			Dictionary<String, Object> properties = context.getProperties();
			applicationAlias = (String) properties.get("application.alias");
		}
	}

	private String applicationAlias;
}
