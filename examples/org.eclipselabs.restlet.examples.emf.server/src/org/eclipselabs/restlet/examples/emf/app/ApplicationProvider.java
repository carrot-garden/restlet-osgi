/*******************************************************************************
 * Copyright (c) 2010.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     - initial API and implementation
 *******************************************************************************/

package org.eclipselabs.restlet.examples.emf.app;

import java.io.IOException;
import java.util.Dictionary;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipselabs.restlet.IApplicationProvider;
import org.eclipselabs.restlet.examples.emf.model.Data;
import org.eclipselabs.restlet.examples.emf.model.ModelFactory;
import org.osgi.service.http.HttpContext;
import org.restlet.Application;
import org.restlet.routing.Router;

/**
 * @author bhunt
 * 
 */
public class ApplicationProvider implements IApplicationProvider
{
	public ApplicationProvider()
	{
		ResourceSet resourceSet = new ResourceSetImpl();
		Resource dataResource = resourceSet.createResource(URI.createFileURI("/tmp/data.xml"));
		Data data = ModelFactory.eINSTANCE.createData();
		data.setName("test");
		dataResource.getContents().add(data);

		try
		{
			dataResource.save(null);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipselabs.restlet.IApplicationProvider#getAlias()
	 */
	@Override
	public String getAlias()
	{
		return "/";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipselabs.restlet.IApplicationProvider#getInitParms()
	 */
	@Override
	public Dictionary<String, Object> getInitParms()
	{
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipselabs.restlet.IApplicationProvider#getApplication()
	 */
	@Override
	public Application getApplication()
	{
		application.setInboundRoot(router);
		return application;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipselabs.restlet.IApplicationProvider#getContext()
	 */
	@Override
	public HttpContext getContext()
	{
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipselabs.restlet.IApplicationProvider#getRouter()
	 */
	@Override
	public Router getRouter()
	{
		return router;
	}

	private Application application = new Application();
	private Router router = new Router();
}
