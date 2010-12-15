/*******************************************************************************
 * Copyright (c) 2010 Wolfgang Werner.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Wolfgang Werner - initial API and implementation
 *******************************************************************************/

package org.eclipselabs.restlet.registry.junit.support;

import java.util.HashMap;
import java.util.Map;

public class TestStore
{
	private static TestStore instance = new TestStore();
	private Map<String, Object> data = new HashMap<String, Object>();

	public static TestStore getInstance()
	{
		return instance;
	}

	public void set(String key, Object value)
	{
		data.put(key, value);
	}

	public Object get(String key)
	{
		return data.get(key);
	}
}
