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
import static org.junit.Assert.assertThat;

import java.util.ArrayList;

import org.eclipselabs.restlet.IApplicationProvider;
import org.eclipselabs.restlet.IResourceProvider;
import org.eclipselabs.restlet.servlet.RestletServletService;
import org.eclipselabs.restlet.servlet.junit.support.Activator;
import org.eclipselabs.restlet.servlet.junit.support.TestApplicationProvider;
import org.eclipselabs.restlet.servlet.junit.support.TestResourceMultiPathProvider;
import org.eclipselabs.restlet.servlet.junit.support.TestResourceProvider;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.osgi.framework.BundleContext;
import org.osgi.service.http.HttpService;
import org.osgi.service.log.LogEntry;
import org.osgi.service.log.LogListener;
import org.osgi.service.log.LogReaderService;
import org.osgi.service.log.LogService;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;

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
		context = Activator.getInstance().getContext();

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
	}

	@After
	public void tearDown()
	{
		try
		{
			httpService.unregister("/");
		}
		catch (Throwable t)
		{}
	}

	@Test
	public void testHttpApplicationResource()
	{
		restletServletService.bindHttpService(httpService);
		restletServletService.bindApplicationProvider(new TestApplicationProvider());
		restletServletService.bindResourceProvider(new TestResourceProvider());

		ClientResource client = createResource("/junit/");
		String result = client.get(String.class);
		assertThat(result, is(notNullValue()));
		assertThat(result, is("JUnit"));
	}

	@Test
	public void testHttpResourceApplication()
	{
		restletServletService.bindHttpService(httpService);
		restletServletService.bindResourceProvider(new TestResourceProvider());
		restletServletService.bindApplicationProvider(new TestApplicationProvider());

		ClientResource client = createResource("/junit/");
		String result = client.get(String.class);
		assertThat(result, is(notNullValue()));
		assertThat(result, is("JUnit"));
	}

	@Test
	public void testApplicationHttpResource()
	{
		restletServletService.bindApplicationProvider(new TestApplicationProvider());
		restletServletService.bindHttpService(httpService);
		restletServletService.bindResourceProvider(new TestResourceProvider());

		ClientResource client = createResource("/junit/");
		String result = client.get(String.class);
		assertThat(result, is(notNullValue()));
		assertThat(result, is("JUnit"));
	}

	@Test
	public void testApplicationResourceHttp()
	{
		restletServletService.bindApplicationProvider(new TestApplicationProvider());
		restletServletService.bindResourceProvider(new TestResourceProvider());
		restletServletService.bindHttpService(httpService);

		ClientResource client = createResource("/junit/");
		String result = client.get(String.class);
		assertThat(result, is(notNullValue()));
		assertThat(result, is("JUnit"));
	}

	@Test
	public void testResourceHttpApplication()
	{
		restletServletService.bindResourceProvider(new TestResourceProvider());
		restletServletService.bindHttpService(httpService);
		restletServletService.bindApplicationProvider(new TestApplicationProvider());

		ClientResource client = createResource("/junit/");
		String result = client.get(String.class);
		assertThat(result, is(notNullValue()));
		assertThat(result, is("JUnit"));
	}

	@Test
	public void testResourceApplicationHttp()
	{
		restletServletService.bindResourceProvider(new TestResourceProvider());
		restletServletService.bindApplicationProvider(new TestApplicationProvider());
		restletServletService.bindHttpService(httpService);

		ClientResource client = createResource("/junit/");
		String result = client.get(String.class);
		assertThat(result, is(notNullValue()));
		assertThat(result, is("JUnit"));
	}

	@Test(expected = ResourceException.class)
	public void testUnbindResource()
	{
		TestResourceProvider resourceProvider = new TestResourceProvider();

		restletServletService.bindHttpService(httpService);
		restletServletService.bindApplicationProvider(new TestApplicationProvider());
		restletServletService.bindResourceProvider(resourceProvider);

		restletServletService.unbindResourceProvider(resourceProvider);

		ClientResource client = createResource("/junit/");
		client.get(String.class);
	}

	@Test(expected = ResourceException.class)
	public void testUnbindApplication()
	{
		TestApplicationProvider applicationProvider = new TestApplicationProvider();

		restletServletService.bindHttpService(httpService);
		restletServletService.bindApplicationProvider(applicationProvider);
		restletServletService.bindResourceProvider(new TestResourceProvider());

		restletServletService.unbindApplicationProvider(applicationProvider);

		ClientResource client = createResource("/junit/");
		client.get(String.class);
	}

	@Test(expected = ResourceException.class)
	public void testUnbindHttpService()
	{
		restletServletService.bindHttpService(httpService);
		restletServletService.bindApplicationProvider(new TestApplicationProvider());
		restletServletService.bindResourceProvider(new TestResourceProvider());

		restletServletService.unbindHttpService(httpService);

		ClientResource client = createResource("/junit/");
		client.get(String.class);
	}

	@Test
	public void testUnbindRebindHttpService()
	{
		restletServletService.bindHttpService(httpService);
		restletServletService.bindApplicationProvider(new TestApplicationProvider());
		restletServletService.bindResourceProvider(new TestResourceProvider());

		restletServletService.unbindHttpService(httpService);
		restletServletService.bindHttpService(httpService);

		ClientResource client = createResource("/junit/");
		String result = client.get(String.class);
		assertThat(result, is(notNullValue()));
		assertThat(result, is("JUnit"));
	}

	@Test(expected = ResourceException.class)
	public void testBindUnbindResource()
	{
		TestResourceProvider resourceProvider = new TestResourceProvider();

		restletServletService.bindHttpService(httpService);
		restletServletService.bindResourceProvider(resourceProvider);

		restletServletService.unbindResourceProvider(resourceProvider);
		restletServletService.bindApplicationProvider(new TestApplicationProvider());

		ClientResource client = createResource("/junit/");
		client.get(String.class);
	}

	@Test
	public void testDuplicateApplication()
	{
		TestLogListener logListener = new TestLogListener();
		logReaderService.addLogListener(logListener);

		restletServletService.bindHttpService(httpService);
		restletServletService.bindLogService(logService);
		restletServletService.bindApplicationProvider(new TestApplicationProvider());
		restletServletService.bindResourceProvider(new TestResourceProvider());
		restletServletService.bindApplicationProvider(new TestApplicationProvider());

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
		restletServletService.bindHttpService(httpService);
		restletServletService.bindApplicationProvider(new TestApplicationProvider());
		restletServletService.bindResourceProvider(new TestResourceMultiPathProvider());

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
	public void testServiceRegistration()
	{
		context.registerService(IApplicationProvider.class.getName(), new TestApplicationProvider(), null);
		context.registerService(IResourceProvider.class.getName(), new TestResourceProvider(), null);

		ClientResource client = createResource("/junit/");
		String result = client.get(String.class);
		assertThat(result, is(notNullValue()));
		assertThat(result, is("JUnit"));
	}

	private ClientResource createResource(String path)
	{
		return new ClientResource(baseURI + path);
	}

	private static String baseURI;
	private static BundleContext context;
	private static HttpService httpService;
	private static LogService logService;
	private static LogReaderService logReaderService;
	private RestletServletService restletServletService;

	private static class TestLogListener implements LogListener
	{

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.osgi.service.log.LogListener#logged(org.osgi.service.log.LogEntry)
		 */
		@Override
		public void logged(LogEntry entry)
		{
			entries.add(entry);
		}

		public ArrayList<LogEntry> entries = new ArrayList<LogEntry>();
	}
}
