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
 * This class parses an abstract scalar parameter.
 * 
 */
public abstract class AbstractScalarParameterState extends ParameterState {

	/**
	 * Constructs the AbstractScalarParameter state with the design parser handler,
	 * the container element and the container slot of the parameter.
	 * 
	 * @param handler      the design file parser handler
	 * @param theContainer the container of this parameter
	 * @param slot         the slot ID of the slot where the parameter is stored.
	 */

	public AbstractScalarParameterState(ModuleParserHandler handler, DesignElement theContainer, int slot) {
		super(handler, theContainer, slot);
	}

}
