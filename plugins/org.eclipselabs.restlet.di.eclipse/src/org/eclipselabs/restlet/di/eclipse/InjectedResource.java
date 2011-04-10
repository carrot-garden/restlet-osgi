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

package org.eclipselabs.restlet.di.eclipse;

import org.eclipse.e4.core.contexts.IEclipseContext;

/**
 * @author bhunt
 * 
 */
@SuppressWarnings("restriction")
public interface InjectedResource
{
	void setEclipseContext(IEclipseContext eclipseContext);
}
