/*******************************************************************************
 * Copyright (c) 2010.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     - initial API and implementation
 *******************************************************************************/

package org.eclipselabs.restlet.examples.resource;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

/**
 * @author bhunt
 * 
 */
public class ExampleResource extends ServerResource
{
	@Get
	@Override
	public String toString()
	{
		return "Example";
	}
}
