/*******************************************************************************
 * Copyright (c) 2004 Actuate Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Actuate Corporation  - initial API and implementation
 *******************************************************************************/

package org.eclipse.birt.chart.tests.engine.computation;

import junit.framework.TestCase;

import org.eclipse.birt.chart.computation.Rectangle;
import org.eclipse.birt.chart.computation.Point;

public class RectangleTest extends TestCase {

	Rectangle r;

	protected void setUp() throws Exception {
		super.setUp();
		r = new Rectangle();
		r.setRect(1.0, 1.0, 4.0, 4.0);
	}

	protected void tearDown() throws Exception {
		r = null;
		super.tearDown();
	}

	public void testGet() {
		assertEquals(r.getHeight(), 4.0, 0);
		assertEquals(r.getWidth(), 4.0, 0);
		assertEquals(r.getX(), 1.0, 0);
		assertEquals(r.getY(), 1.0, 0);
		assertEquals(r.getMinX(), 1.0, 0);
		assertEquals(r.getMinY(), 1.0, 0);
		assertEquals(r.getMaxX(), 5.0, 0);
		assertEquals(r.getMaxY(), 5.0, 0);
	}

	public void testOutcode() {
		assertEquals(r.outcode(0.0, 1.0), 1);
		assertEquals(r.outcode(1.0, 0.0), 2);
		assertEquals(r.outcode(0.0, 0.0), 3);
		assertEquals(r.outcode(6.0, 5.0), 4);
		assertEquals(r.outcode(6.0, 0.0), 6);
		assertEquals(r.outcode(5.0, 6.0), 8);
		assertEquals(r.outcode(0.0, 6.0), 9);
	}

	public void testIsEmpty() {
		assertEquals(r.isEmpty(), false);
	}

	public void testContain() {
		assertEquals(r.contains(new Point(0.0, 0.0)), false);
		assertEquals(r.contains(new Point(2.0, 2.0)), true);
	}

}
