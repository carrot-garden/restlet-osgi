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

package org.eclipselabs.restlet.di.junit.support;

import javax.inject.Inject;

import org.eclipselabs.restlet.di.InjectedServerResource;
import org.osgi.service.log.LogService;
import org.restlet.representation.EmptyRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;

/**
 * @author bhunt
 * 
 */
public class TestResource extends InjectedServerResource
{
	@Put
	public Representation logData(String data)
	{
		if (logService != null)
			logService.log(LogService.LOG_INFO, data);

		return new EmptyRepresentation();
	}

	@Inject
	LogService logService;
}
