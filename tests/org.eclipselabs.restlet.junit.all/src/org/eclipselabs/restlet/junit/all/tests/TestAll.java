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

package org.eclipselabs.restlet.junit.all.tests;

import org.eclipselabs.restlet.di.junit.tests.TestInjectedFinder;
import org.eclipselabs.restlet.servlet.junit.tests.TestRestletServletService;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * @author bhunt
 * 
 */

@RunWith(Suite.class)
@SuiteClasses({ TestRestletServletService.class, TestInjectedFinder.class })
public class TestAll
{}
