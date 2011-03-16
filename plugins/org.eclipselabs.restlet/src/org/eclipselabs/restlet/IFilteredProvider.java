/*******************************************************************************
 * Copyright (c) 2011.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     - initial API and implementation
 *******************************************************************************/

package org.eclipselabs.restlet;

import org.restlet.Restlet;

/**
 * @author bhunt
 * 
 */
public interface IFilteredProvider extends IApplicationComponent
{
	boolean addFilterProvider(IFilterProvider filterProvider);

	Restlet getInboundRoot();

	boolean removeFilterProvider(IFilterProvider filterProvider);
}
