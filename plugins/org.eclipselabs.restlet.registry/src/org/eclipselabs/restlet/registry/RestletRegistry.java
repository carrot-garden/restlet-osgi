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

package org.eclipselabs.restlet.registry;

import java.util.HashMap;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IRegistryEventListener;
import org.eclipselabs.restlet.IApplicationProvider;
import org.eclipselabs.restlet.IResourceProvider;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.log.LogService;
import org.osgi.service.packageadmin.PackageAdmin;

/**
 * @author bhunt
 * 
 */
public class RestletRegistry implements IRegistryEventListener
{
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.runtime.IRegistryEventListener#added(org.eclipse.core.runtime.IExtension[])
	 */
	@Override
	public void added(IExtension[] extensions)
	{
		for (IExtension extension : extensions)
			addElements(extension.getConfigurationElements());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.runtime.IRegistryEventListener#removed(org.eclipse.core.runtime.IExtension[])
	 */
	@Override
	public void removed(IExtension[] extensions)
	{
		for (IExtension extension : extensions)
			removeElements(extension.getConfigurationElements());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.runtime.IRegistryEventListener#added(org.eclipse.core.runtime.IExtensionPoint
	 * [])
	 */
	@Override
	public void added(IExtensionPoint[] extensionPoints)
	{}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.runtime.IRegistryEventListener#removed(org.eclipse.core.runtime.IExtensionPoint
	 * [])
	 */
	@Override
	public void removed(IExtensionPoint[] extensionPoints)
	{}

	protected void activate(ComponentContext context)
	{
		extensionRegistry.addListener(this, "org.eclipselabs.restlet.registry.restlet");
		IConfigurationElement[] configs = extensionRegistry.getConfigurationElementsFor("org.eclipselabs.restlet.registry.restlet");
		bundleContext = context.getBundleContext();
		addElements(configs);
	}

	protected void bindExtensionRegistry(IExtensionRegistry extensionRegistry)
	{
		this.extensionRegistry = extensionRegistry;
	}

	protected void bindLogService(LogService logService)
	{
		this.logService = logService;
	}

	protected void bindPackageAdmin(PackageAdmin packageAdmin)
	{
		this.packageAdmin = packageAdmin;
	}

	protected void unbindExtensionRegistry(IExtensionRegistry extensionRegistry)
	{
		if (this.extensionRegistry == extensionRegistry)
			this.extensionRegistry = null;
	}

	protected void unbindLogService(LogService logService)
	{
		if (this.logService == logService)
			this.logService = null;
	}

	protected void unbindPackageAdmin(PackageAdmin packageAdmin)
	{
		if (this.packageAdmin == packageAdmin)
			this.packageAdmin = null;
	}

	/**
	 * @param configs
	 */
	private void addElements(IConfigurationElement[] configs)
	{
		for (IConfigurationElement config : configs)
		{
			if (config.getName().equals("application"))
			{
				RegistryApplicationProvider registryApplicationProvider = new RegistryApplicationProvider(config, logService);
				ServiceRegistration serviceRegistration = bundleContext.registerService(IApplicationProvider.class.getName(), registryApplicationProvider, null);
				applicationProviders.put(config.getAttribute("alias"), serviceRegistration);
			}
			else if (config.getName().equals("resource"))
			{
				RegistryResourceProvider registryResourceProvider = new RegistryResourceProvider(getBundle(config.getContributor().getName()), config, logService);
				ServiceRegistration serviceRegistration = bundleContext.registerService(IResourceProvider.class.getName(), registryResourceProvider, null);
				resourceProviders.put(config.getAttribute("application_alias") + config.getAttribute("path"), serviceRegistration);
			}
		}
	}

	private Bundle getBundle(String symbolicName)
	{
		PackageAdmin admin = packageAdmin;

		// The code below was copied from org.eclipse.core.internal.runtime.InternalPlatform

		if (admin == null)
			return null;

		Bundle[] bundles = packageAdmin.getBundles(symbolicName, null);

		if (bundles == null)
			return null;

		// Return the first bundle that is not installed or uninstalled

		for (int i = 0; i < bundles.length; i++)
		{
			if ((bundles[i].getState() & (Bundle.INSTALLED | Bundle.UNINSTALLED)) == 0)
				return bundles[i];
		}

		return null;
	}

	private void removeElements(IConfigurationElement[] configs)
	{
		for (IConfigurationElement config : configs)
		{
			if (config.getName().equals("application"))
				applicationProviders.remove(config.getAttribute("alias")).unregister();
			else if (config.getName().equals("resource"))
				resourceProviders.remove(config.getAttribute("application_alias") + config.getAttribute("path")).unregister();
		}
	}

	private IExtensionRegistry extensionRegistry;
	private LogService logService;
	private PackageAdmin packageAdmin;
	private HashMap<String, ServiceRegistration> applicationProviders = new HashMap<String, ServiceRegistration>();
	private HashMap<String, ServiceRegistration> resourceProviders = new HashMap<String, ServiceRegistration>();
	private BundleContext bundleContext;
}
