/*******************************************************************************
 * Copyright (c) 2017 Actuate Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Actuate Corporation  - initial API and implementation
 *******************************************************************************/

package org.eclipse.birt.core.script;

import org.eclipse.birt.core.fs.LocalFileTest;
import org.eclipse.birt.core.script.bre.BirtCompTest;
import org.eclipse.birt.core.script.bre.BirtDateTimeTest;
import org.eclipse.birt.core.script.bre.BirtDurationTest;
import org.eclipse.birt.core.script.bre.BirtMathTest;
import org.eclipse.birt.core.script.bre.BirtStrTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Test cases in script package.
 */

public class AllScriptTests {

	/**
	 * @return the test
	 */

	public static Test suite() {
		TestSuite test = new TestSuite();

		test.addTestSuite(CoreJavaScriptWrapperTest.class);
		test.addTestSuite(NativeDateTimeSpanTest.class);
		test.addTestSuite(NativeFinanceTest.class);
		test.addTestSuite(NativeJavaMapTest.class);
		test.addTestSuite(NativeNamedListTest.class);
		test.addTestSuite(ScriptableParametersTest.class);
		test.addTestSuite(ScriptContextTest.class);
		test.addTestSuite(BirtCompTest.class);
		test.addTestSuite(BirtDateTimeTest.class);
		test.addTestSuite(BirtDurationTest.class);
		test.addTestSuite(BirtMathTest.class);
		test.addTestSuite(BirtStrTest.class);
		test.addTestSuite(LocalFileTest.class);
		// add all test classes here

		return test;
	}
}
