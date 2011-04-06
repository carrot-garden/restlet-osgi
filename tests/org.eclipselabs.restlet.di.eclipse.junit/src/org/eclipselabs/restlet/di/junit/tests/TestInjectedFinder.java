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

package org.eclipselabs.restlet.di.junit.tests;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;

import org.eclipselabs.restlet.components.ApplicationProvider;
import org.eclipselabs.restlet.components.ResourceProvider;
import org.eclipselabs.restlet.components.RouterProvider;
import org.eclipselabs.restlet.developer.junit.RestletTestHarness;
import org.eclipselabs.restlet.di.junit.support.TestApplicationProvider;
import org.eclipselabs.restlet.di.junit.support.TestResourceProvider;
import org.junit.BeforeClass;
import org.junit.Test;
import org.osgi.service.log.LogEntry;
import org.osgi.service.log.LogListener;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;

/**
 * @author bhunt
 * 
 */
public class TestInjectedFinder
{
	@BeforeClass
	public static void globalSetUp() throws InterruptedException
	{
		baseURI = "http://localhost:" + System.getProperty("org.osgi.service.http.port", "80");
		testHarness = new RestletTestHarness();
		testHarness.setUp();
	}

	@Test
	public void testFinder()
	{
		ApplicationProvider applicationProvider = new TestApplicationProvider();
		RouterProvider routerProvider = new RouterProvider();
		ResourceProvider resourceProvider = new TestResourceProvider();
		applicationProvider.bindRouterProvider(routerProvider);
		routerProvider.bindResourceProvider(resourceProvider);

		testHarness.addApplication(applicationProvider);

		TestLogListener logListener = new TestLogListener();
		testHarness.getLogReaderService().addLogListener(logListener);

		ClientResource client = createResource("/junit/");
		Representation result = client.put("Hello");
		assertThat(result, is(notNullValue()));
		System.out.println(result);

		assertThat(logListener.entries.size(), is(not(0)));
		assertThat(logListener.entries.get(logListener.entries.size() - 1).getMessage(), is("Hello"));
	}

	private ClientResource createResource(String path)
	{
		return new ClientResource(baseURI + path);
	}

	private static class TestLogListener implements LogListener
	{
		@Override
		public void logged(LogEntry entry)
		{
			entries.add(entry);
		}

		public ArrayList<LogEntry> entries = new ArrayList<LogEntry>();
	}

	private static String baseURI;
	private static RestletTestHarness testHarness;
}
