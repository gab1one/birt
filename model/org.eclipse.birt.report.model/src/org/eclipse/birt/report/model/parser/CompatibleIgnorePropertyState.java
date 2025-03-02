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

package org.eclipse.birt.report.model.parser;

import org.eclipse.birt.report.model.core.DesignElement;

/**
 * This state is for handling the compatibility problem for not used property.
 * If the property is removed from element, when parser reads the old design
 * file, this state will be return to ignore the removed property.
 */

public class CompatibleIgnorePropertyState extends CompatiblePropertyState {

	/**
	 * Constructs a <code>CompatibleIgnorePropertyState</code> to parse an removed
	 * property.
	 * 
	 * @param theHandler the parser handle
	 * @param element    the element that holds the obsolete property
	 * 
	 */

	public CompatibleIgnorePropertyState(ModuleParserHandler theHandler, DesignElement element) {
		super(theHandler, element);

	}

	public void end() {
	}
}
