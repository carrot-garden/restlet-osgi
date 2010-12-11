package org.eclipselabs.restlet.registry.junit.support;

import java.util.HashMap;
import java.util.Map;

public class TestStore {
	private static TestStore instance = new TestStore();
	private Map<String, Object> data = new HashMap<String, Object>();

	public static TestStore getInstance() {
		return instance;
	}

	public void set(String key, Object value) {
		data.put(key, value);
	}

	public Object get(String key) {
		return data.get(key);
	}
}
