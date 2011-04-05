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

import org.osgi.framework.Bundle;
import org.osgi.service.log.LogService;
import org.restlet.resource.Finder;

/**
 * This class allows Restlet to lazily load resources in an OSGi environment. This class may be used
 * as the finder in a @see IResourceProvider.
 * 
 * @author bhunt
 */
public class DynamicFinder extends Finder
{
	/**
	 * @param bundle the bundle containing the resource - must not be null
	 * @param className the class name of the resource - must not be null
	 */
	public DynamicFinder(Bundle bundle, String className)
	{
		this(bundle, className, null);
	}

	/**
	 * @param bundle the bundle containg the resource - must not be null
	 * @param className the class name of the resource - must not be null
	 * @param logService the OSGi log service for logging errors - may be null
	 */
	public DynamicFinder(Bundle bundle, String className, LogService logService)
	{
		if (bundle == null)
			throw new IllegalArgumentException("bundle must not be null");

		if (className == null)
			throw new IllegalArgumentException("className must not be null");

		this.bundle = bundle;
		this.className = className;
		this.logService = logService;
	}

	@Override
	public Class<?> getTargetClass()
	{
		if (targetClass == null)
		{
			try
			{
				targetClass = bundle.loadClass(className);
			}
			catch (ClassNotFoundException e)
			{
				if (logService != null)
					logService.log(LogService.LOG_ERROR, "Failed to load class: '" + className + "'", e);
			}
		}

		return targetClass;
	}

	private Bundle bundle;
	private String className;
	private Class<?> targetClass;
	private LogService logService;
}
