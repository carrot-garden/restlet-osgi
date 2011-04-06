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

package org.eclipselabs.restlet.servlet;

import org.restlet.Application;
import org.restlet.Context;
import org.restlet.ext.servlet.ServerServlet;

/**
 * @author bhunt
 * 
 */
public class ApplicationServlet extends ServerServlet
{
	public void setApplication(Application application)
	{
		this.application = application;
	}

	@Override
	protected Application createApplication(Context context)
	{
		application.setContext(context.createChildContext());
		return application;
	}

	private static final long serialVersionUID = 5252087180467260130L;
	private transient Application application;
}
