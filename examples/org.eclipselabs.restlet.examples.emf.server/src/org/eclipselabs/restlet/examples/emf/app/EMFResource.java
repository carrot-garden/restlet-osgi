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

package org.eclipselabs.restlet.examples.emf.app;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIHandler;
import org.eclipse.emf.utilities.restlet.RestletEmfServerResource;
import org.eclipse.emf.utilities.restlet.impl.DefaultResourceNameCreator;
import org.eclipse.emf.utilities.restlet.impl.DefaultRestURIConverter;
import org.eclipselabs.mongo.emf.MongoDBURIHandlerImpl;

/**
 * @author bhunt
 * 
 */
public class EMFResource extends RestletEmfServerResource
{
	public EMFResource()
	{
		setResourceNameCreator(new DefaultResourceNameCreator());
		DefaultRestURIConverter restUriConverter = new DefaultRestURIConverter();
		restUriConverter.setServerSideResourcePattern("mongo://localhost/test/emf/{resourcename}");
		restUriConverter.setClientSideResourcePattern("/resource/emf/{resourcename}");
		setRestUriConverter(restUriConverter);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.emf.utilities.restlet.RestletEmfServerResource#configureResourceSet(org.eclipse
	 * .emf.utilities.restlet.ResourceSet)
	 */
	@Override
	protected void configureResourceSet(ResourceSet resourceSet)
	{
		EList<URIHandler> uriHandlers = resourceSet.getURIConverter().getURIHandlers();
		uriHandlers.add(0, new MongoDBURIHandlerImpl());
	}
}
