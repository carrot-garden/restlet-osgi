/*******************************************************************************
 * Copyright (c) 2011 Bryan Hunt.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Bryan Hunt - initial API and implementation
 *******************************************************************************/

package org.eclipselabs.restlet.providers;

import org.restlet.Restlet;

/**
 * This is a common interface for several of the providers. Users are not expected to implement
 * this interface, but instead implement one of the provider interfaces.
 * 
 * @author bhunt
 */
public interface IRestletProvider
{
	Restlet getInboundRoot();
}
