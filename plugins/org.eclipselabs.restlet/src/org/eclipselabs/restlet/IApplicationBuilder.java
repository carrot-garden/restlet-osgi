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

package org.eclipselabs.restlet;

import org.restlet.Application;

/**
 * @author bhunt
 * 
 */
public interface IApplicationBuilder
{
	void addFilterProvider(IFilterProvider filterProvider);

	void addResourceProvider(IResourceProvider resourceProvider);

	void addRouterProvider(IRouterProvider routerProvider);

	Application buildApplication(IApplicationProvider applicationProvider);

	String getApplicationAlias();

	void removeFilterProvider(IFilterProvider filterProvider);

	void removeResourceProvider(IResourceProvider resourceProvider);

	void removeRouterProvider(IRouterProvider routerProvider);
}
