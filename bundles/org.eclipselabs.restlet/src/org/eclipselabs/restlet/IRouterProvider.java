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

package org.eclipselabs.restlet;

/**
 * This is an OSGi service interface for registering Restlet filters with an application. Users are
 * expected to implement this interface and register an instance as an OSGi service.
 * 
 * It is recommended to use or extend {@link org.eclipselabs.restlet.builders.RouterBuilder}
 * 
 * @author bhunt
 * 
 */
public interface IRouterProvider extends IRestletProvider
{}
