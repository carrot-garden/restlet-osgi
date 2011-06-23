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

import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipselabs.restlet.ResourceProvider;
import org.eclipselabs.restlet.examples.emf.model.Data;
import org.eclipselabs.restlet.examples.emf.model.ModelFactory;
import org.restlet.Context;
import org.restlet.resource.Finder;

/**
 * @author bhunt
 * 
 */
public class EMFResourceProvider extends ResourceProvider
{
	@Override
	protected Finder createFinder(Context context)
	{
		return new Finder(context, EMFResource.class);
	}

	static
	{
		// Hack to get something in the database

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
}
