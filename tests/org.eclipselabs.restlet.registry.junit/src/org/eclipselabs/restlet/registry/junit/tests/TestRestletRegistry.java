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

package org.eclipselabs.restlet.registry.junit.tests;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.BeforeClass;
import org.junit.Test;
import org.restlet.resource.ClientResource;

/**
 * @author bhunt
 * 
 */
public class TestRestletRegistry
{
	@BeforeClass
	public static void globalSetUp()
	{
		baseURI = "http://localhost:" + System.getProperty("org.osgi.service.http.port", "80") + "/registry";
	}

	@Test
	public void testApplication()
	{
		ClientResource client = createResource("/junit/");
		String result = client.get(String.class);
		assertThat(result, is(notNullValue()));
		assertThat(result, is("JUnit"));
	}

	@Test
	public void getIsWhatWasPut()
	{
		ClientResource client = createResource("/store");
		String value = "store me";
		client.put(value);
		String result = client.get(String.class);
		assertThat(result, is(value));

	}

	@Test
	public void getIsWhatWasSetAsURIParam()
	{
		ClientResource client = createResource("/param/test");
		client.put(null);
		String result = client.get(String.class);
		assertThat(result, is("test"));

	}

	private ClientResource createResource(String path)
	{
		return new ClientResource(baseURI + path);
	}

	private static String baseURI;
}
