/*******************************************************************************
 * Copyright (c) 2010 Bryan Hunt & Wolfgang Werner.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Bryan Hunt & Wolfgang Werner - initial API and implementation
 *******************************************************************************/

package org.eclipselabs.restlet;

/**
 * This is an OSGi service interface for registering Restlet resources with an application.
 * Users are expected to implement this interface and register an instance as an OSGi service.
 * Resources are registered with an application according to the application alias. If
 * an application is not found that corresponds to the specified alias, the resource will be
 * cached until the application is registered. If your resources are not being registered, check
 * there is not a typo in the alias in both the resource provider and application provider.
 * 
 * It is recommended that you use or extend {@link ResourceBuilder}
 * 
 * @author bhunt
 */
public interface IResourceProvider extends IRestletProvider
{
	/**
	 * 
	 * @return the paths to the resource relative to the application alias. The paths must start with
	 *         '/'.
	 */
	String[] getPaths();
}
