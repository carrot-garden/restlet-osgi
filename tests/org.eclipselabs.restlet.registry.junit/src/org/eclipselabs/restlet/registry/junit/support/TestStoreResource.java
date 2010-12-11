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

import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;


/**
 * @author wwerner
 *
 */
public class TestStoreResource extends ServerResource {

	@Get
	public String getData() {
		return (String) TestStore.getInstance().get("TestStoreResource");
	}

	@Put
	public void setData(String data) {
		TestStore.getInstance().set("TestStoreResource",data);
	}
}
