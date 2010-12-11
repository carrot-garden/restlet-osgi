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

package org.eclipselabs.restlet.servlet.junit.support;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

/**
 * @author bhunt
 * 
 */
public class TestResource extends ServerResource
{
	@Get
	public String getRepresentation()
	{
		return "JUnit";
	}
}
