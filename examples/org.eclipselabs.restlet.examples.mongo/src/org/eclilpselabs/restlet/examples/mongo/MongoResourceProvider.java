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

package org.eclilpselabs.restlet.examples.mongo;

import org.eclipselabs.restlet.ResourceProvider;
import org.eclipselabs.restlet.mongo.MongoResource;
import org.restlet.Context;
import org.restlet.resource.Finder;

/**
 * @author bhunt
 * 
 */
public class MongoResourceProvider extends ResourceProvider
{
	@Override
	protected Finder createFinder(Context context)
	{
		return new Finder(context, MongoResource.class);
	}
}
