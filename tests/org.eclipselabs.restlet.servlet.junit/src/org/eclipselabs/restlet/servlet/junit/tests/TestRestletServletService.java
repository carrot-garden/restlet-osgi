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

package org.eclipselabs.restlet.servlet.junit.tests;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.eclipselabs.restlet.components.ApplicationProvider;
import org.eclipselabs.restlet.components.FilterProvider;
import org.eclipselabs.restlet.components.ResourceProvider;
import org.eclipselabs.restlet.components.RouterProvider;
import org.eclipselabs.restlet.servlet.RestletServletService;
import org.eclipselabs.restlet.servlet.junit.support.Activator;
import org.eclipselabs.restlet.servlet.junit.support.TestApplicationProvider;
import org.eclipselabs.restlet.servlet.junit.support.TestFilter;
import org.eclipselabs.restlet.servlet.junit.support.TestFilterProvider;
import org.eclipselabs.restlet.servlet.junit.support.TestResourceMultiPathProvider;
import org.eclipselabs.restlet.servlet.junit.support.TestResourceProvider;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.osgi.service.http.HttpService;
import org.osgi.service.log.LogEntry;
import org.osgi.service.log.LogListener;
import org.osgi.service.log.LogReaderService;
import org.osgi.service.log.LogService;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;
import org.restlet.routing.Filter;

/**
 * @author bhunt
 * 
 */
public class TestRestletServletService
{
	@BeforeClass
	public static void globalSetup() throws InterruptedException
	{
		baseURI = "http://localhost:" + System.getProperty("org.osgi.service.http.port", "80");

		httpService = Activator.getInstance().getHttpService();
		assertThat(httpService, is(notNullValue()));

		logService = Activator.getInstance().getLogService();
		assertThat(logService, is(notNullValue()));

		logReaderService = Activator.getInstance().getLogReaderService();
		assertThat(logReaderService, is(notNullValue()));
	}

	@Before
	public void setUp()
	{
		restletServletService = new RestletServletService();
		unregisterApplication = true;
	}

	@After
	public void tearDown()
	{
		try
		{
			if (unregisterApplication)
				httpService.unregister("/");
		}
		catch (Throwable t)
		{}
	}

	@Test
	public void testHttpServiceApplication()
	{
		ApplicationProvider applicationProvider = new TestApplicationProvider();
		RouterProvider routerProvider = new RouterProvider();
		ResourceProvider resourceProvider = new TestResourceProvider();
		applicationProvider.bindRouterProvider(routerProvider);
		routerProvider.bindResourceProvider(resourceProvider);

		restletServletService.bindHttpService(httpService);
		restletServletService.bindApplicationProvider(applicationProvider);

		ClientResource client = createResource("/junit/");
		String result = client.get(String.class);
		assertThat(result, is(notNullValue()));
		assertThat(result, is("JUnit"));
	}

	@Test
	public void testApplicationHttpService()
	{
		ApplicationProvider applicationProvider = new TestApplicationProvider();
		RouterProvider routerProvider = new RouterProvider();
		ResourceProvider resourceProvider = new TestResourceProvider();
		applicationProvider.bindRouterProvider(routerProvider);
		routerProvider.bindResourceProvider(resourceProvider);

		restletServletService.bindApplicationProvider(applicationProvider);
		restletServletService.bindHttpService(httpService);

		ClientResource client = createResource("/junit/");
		String result = client.get(String.class);
		assertThat(result, is(notNullValue()));
		assertThat(result, is("JUnit"));
	}

	@Test(expected = ResourceException.class)
	public void testUnbindApplication()
	{
		ApplicationProvider applicationProvider = new TestApplicationProvider();
		RouterProvider routerProvider = new RouterProvider();
		ResourceProvider resourceProvider = new TestResourceProvider();
		applicationProvider.bindRouterProvider(routerProvider);
		routerProvider.bindResourceProvider(resourceProvider);

		restletServletService.bindHttpService(httpService);
		restletServletService.bindApplicationProvider(applicationProvider);

		restletServletService.unbindApplicationProvider(applicationProvider);

		ClientResource client = createResource("/junit/");
		client.get(String.class);
	}

	@Test(expected = ResourceException.class)
	public void testUnbindHttpService()
	{
		ApplicationProvider applicationProvider = new TestApplicationProvider();
		RouterProvider routerProvider = new RouterProvider();
		ResourceProvider resourceProvider = new TestResourceProvider();
		applicationProvider.bindRouterProvider(routerProvider);
		routerProvider.bindResourceProvider(resourceProvider);

		restletServletService.bindHttpService(httpService);
		restletServletService.bindApplicationProvider(applicationProvider);

		restletServletService.unbindHttpService(httpService);

		ClientResource client = createResource("/junit/");
		client.get(String.class);
	}

	@Test
	public void testUnbindRebindHttpService()
	{
		ApplicationProvider applicationProvider = new TestApplicationProvider();
		RouterProvider routerProvider = new RouterProvider();
		ResourceProvider resourceProvider = new TestResourceProvider();
		applicationProvider.bindRouterProvider(routerProvider);
		routerProvider.bindResourceProvider(resourceProvider);

		restletServletService.bindHttpService(httpService);
		restletServletService.bindApplicationProvider(applicationProvider);

		restletServletService.unbindHttpService(httpService);
		restletServletService.bindHttpService(httpService);

		ClientResource client = createResource("/junit/");
		String result = client.get(String.class);
		assertThat(result, is(notNullValue()));
		assertThat(result, is("JUnit"));
	}

	@Test
	public void testDuplicateApplication()
	{
		TestLogListener logListener = new TestLogListener();
		logReaderService.addLogListener(logListener);

		ApplicationProvider applicationProvider = new TestApplicationProvider();
		RouterProvider routerProvider = new RouterProvider();
		ResourceProvider resourceProvider = new TestResourceProvider();
		applicationProvider.bindRouterProvider(routerProvider);
		routerProvider.bindResourceProvider(resourceProvider);

		restletServletService.bindLogService(logService);
		restletServletService.bindHttpService(httpService);
		restletServletService.bindApplicationProvider(applicationProvider);
		restletServletService.bindApplicationProvider(applicationProvider);

		ClientResource client = createResource("/junit/");
		String result = client.get(String.class);
		assertThat(result, is(notNullValue()));
		assertThat(result, is("JUnit"));

		assertThat(logListener.entries.size(), is(1));
		assertThat(logListener.entries.get(0).getLevel(), is(LogService.LOG_ERROR));
	}

	@Test
	public void testResourceWithMultiplePaths()
	{
		ApplicationProvider applicationProvider = new TestApplicationProvider();
		RouterProvider routerProvider = new RouterProvider();
		ResourceProvider resourceProvider = new TestResourceMultiPathProvider();
		applicationProvider.bindRouterProvider(routerProvider);
		routerProvider.bindResourceProvider(resourceProvider);

		restletServletService.bindHttpService(httpService);
		restletServletService.bindApplicationProvider(applicationProvider);

		ClientResource client = createResource("/junit/");
		String result = client.get(String.class);
		assertThat(result, is(notNullValue()));
		assertThat(result, is("JUnit"));
		System.out.println(result);

		client = createResource("/junit/1");
		result = client.get(String.class);
		assertThat(result, is(notNullValue()));
		assertThat(result, is("JUnit"));
	}

	@Test
	public void testFilterRouter()
	{
		ApplicationProvider applicationProvider = new TestApplicationProvider();
		RouterProvider routerProvider = new RouterProvider();
		ResourceProvider resourceProvider = new TestResourceProvider();
		FilterProvider filterProvider = new TestFilterProvider();

		routerProvider.bindResourceProvider(resourceProvider);
		routerProvider.bindfilterProvider(filterProvider);
		applicationProvider.bindRouterProvider(routerProvider);

		restletServletService.bindHttpService(httpService);
		restletServletService.bindApplicationProvider(applicationProvider);

		TestFilter filter = (TestFilter) filterProvider.getFilter();
		filter.setReturnValue(Filter.STOP);

		ClientResource client = createResource("/junit/");
		String result = client.get(String.class);
		assertThat(result, is(nullValue()));
		assertTrue(filter.isBeforeHandleCalled());
	}

	@Test
	public void testFilterResource()
	{
		ApplicationProvider applicationProvider = new TestApplicationProvider();
		RouterProvider routerProvider = new RouterProvider();
		ResourceProvider resourceProvider = new TestResourceProvider();
		FilterProvider filterProvider = new TestFilterProvider();

		applicationProvider.bindRouterProvider(routerProvider);
		resourceProvider.bindfilterProvider(filterProvider);
		routerProvider.bindResourceProvider(resourceProvider);

		restletServletService.bindHttpService(httpService);
		restletServletService.bindApplicationProvider(applicationProvider);

		TestFilter filter = (TestFilter) filterProvider.getFilter();
		filter.setReturnValue(Filter.STOP);

		ClientResource client = createResource("/junit/");
		String result = client.get(String.class);
		assertThat(result, is(nullValue()));
		assertTrue(filter.isBeforeHandleCalled());
	}

	private ClientResource createResource(String path)
	{
		return new ClientResource(baseURI + path);
	}

	private static String baseURI;
	private static HttpService httpService;
	private static LogService logService;
	private static LogReaderService logReaderService;
	private RestletServletService restletServletService;
	private boolean unregisterApplication;

	private static class TestLogListener implements LogListener
	{
		@Override
		public void logged(LogEntry entry)
		{
			entries.add(entry);
		}

		public ArrayList<LogEntry> entries = new ArrayList<LogEntry>();
	}
}
