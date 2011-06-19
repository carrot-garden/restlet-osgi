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

package org.eclipselabs.restlet.internal.mongo;

import org.eclipselabs.mongo.IMongoDB;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author bhunt
 * 
 */
public class Activator implements BundleActivator
{
	public static Activator getInstance()
	{
		return instance;
	}

	public IMongoDB getMongoDB()
	{
		return (IMongoDB) mongoServiceTracker.getService();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(BundleContext context) throws Exception
	{
		mongoServiceTracker = new ServiceTracker(context, IMongoDB.class.getName(), null);
		mongoServiceTracker.open();
		instance = this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(BundleContext context) throws Exception
	{
		instance = null;

		if (mongoServiceTracker != null)
			mongoServiceTracker.close();
	}

	private static Activator instance;
	private ServiceTracker mongoServiceTracker;
}
