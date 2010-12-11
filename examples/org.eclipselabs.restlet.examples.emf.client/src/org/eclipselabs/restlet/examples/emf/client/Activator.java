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

package org.eclipselabs.restlet.examples.emf.client;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIHandler;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.utilities.restlet.RestletURIHandler;
import org.eclipse.emf.utilities.restlet.impl.ExplicitCreateSaveStrategyDetectorImpl;
import org.eclipselabs.restlet.examples.emf.model.Child;
import org.eclipselabs.restlet.examples.emf.model.Data;
import org.eclipselabs.restlet.examples.emf.model.ModelFactory;
import org.eclipselabs.restlet.examples.emf.model.Parent;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * @author bhunt
 * 
 */
public class Activator implements BundleActivator
{
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(BundleContext context) throws Exception
	{
		URI parentURI = URI.createURI("http://localhost:8080/resource/emf/");

		{
			ResourceSet resourceSet = new ResourceSetImpl();
			URIHandler handler = createURIHandlerWithExplicitStrategy("http://localhost:8080/resource/emf/{resource}", "http://localhost:8080/resource/emf/");
			EList<URIHandler> handlers = resourceSet.getURIConverter().getURIHandlers();
			handlers.add(handlers.size() - 2, handler);

			Resource resource = resourceSet.createResource(URI.createURI("http://localhost:8080/resource/emf/"));
			Data data = ModelFactory.eINSTANCE.createData();
			data.setName("Created");
			resource.getContents().add(data);

			Resource childResource1 = resourceSet.createResource(URI.createURI("http://localhost:8080/resource/emf/"));
			Child child1 = ModelFactory.eINSTANCE.createChild();
			child1.setName("Child 1");
			Data data1 = ModelFactory.eINSTANCE.createData();
			data1.setName("Data 1");
			child1.setData(data1);
			childResource1.getContents().add(child1);

			Resource childResource2 = resourceSet.createResource(URI.createURI("http://localhost:8080/resource/emf/"));
			Child child2 = ModelFactory.eINSTANCE.createChild();
			child2.setName("Child 2");
			Data data2 = ModelFactory.eINSTANCE.createData();
			data2.setName("Data 2");
			child2.setData(data2);
			childResource2.getContents().add(child2);

			Resource parentResource = resourceSet.createResource(parentURI);
			Parent parent = ModelFactory.eINSTANCE.createParent();
			parent.setName("Parent");
			parent.getChildren().add(child1);
			parent.getChildren().add(child2);
			parentResource.getContents().add(parent);

			resource.save(null);
			childResource1.save(null);
			childResource2.save(null);
			parentResource.save(null);
			parentURI = parentResource.getURI();
		}

		{
			ResourceSet resourceSet = new ResourceSetImpl();
			URIHandler handler = createURIHandlerWithExplicitStrategy("http://localhost:8080/resource/emf/{resource}", "http://localhost:8080/resource/emf/");
			EList<URIHandler> handlers = resourceSet.getURIConverter().getURIHandlers();
			handlers.add(handlers.size() - 2, handler);

			Resource parentResource = resourceSet.getResource(parentURI, true);
			Parent parent = (Parent) parentResource.getContents().get(0);
			System.out.println("Parent: " + parent.getName());

			for (Child child : parent.getChildren())
			{
				System.out.println("Child: " + child.getName());
				System.out.println("Child data: " + child.getData().getName());
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(BundleContext context) throws Exception
	{}

	protected URIHandler createURIHandlerWithExplicitStrategy(String putPattern, String postPattern)
	{
		RestletURIHandler uriHandler = new RestletURIHandler();
		ExplicitCreateSaveStrategyDetectorImpl detector = new ExplicitCreateSaveStrategyDetectorImpl();
		detector.setPostUriPattern(postPattern);
		detector.setPutUriPattern(putPattern);
		detector.init();
		uriHandler.setCreateSaveStrategyDetector(detector);
		return uriHandler;
	}

}
