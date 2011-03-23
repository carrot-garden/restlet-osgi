
package org.eclipselabs.restlet.internal.di.bundle;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator
{

	private static BundleContext context;

	public static BundleContext getContext()
	{
		return context;
	}

	public void start(BundleContext bundleContext) throws Exception
	{
		Activator.context = bundleContext;
	}

	public void stop(BundleContext bundleContext) throws Exception
	{
		Activator.context = null;
	}

}
