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

package org.eclipselabs.restlet.examples.app;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

/**
 * @author bhunt
 * 
 */
public class HelloResource extends ServerResource
{
	@Get("txt")
	public String sayHello()
	{
		return "Hello";
	}

	@Get("html")
	public String getDocument()
	{
		StringBuilder html = new StringBuilder();
		html.append("<html>\n");
		html.append("  <body>\n");
		html.append("    <h2>Hello</h2>\n");
		html.append("   </body>\n");
		html.append("</html>\n");
		return html.toString();
	}
}
