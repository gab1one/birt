/*******************************************************************************
 * Copyright (c) 2005 Actuate Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Actuate Corporation  - initial API and implementation
 *******************************************************************************/

package org.eclipse.birt.report.engine.api.script.element;

import org.eclipse.birt.report.engine.api.script.ScriptException;
import org.eclipse.birt.report.model.api.core.IStructure;

/**
 * Represents the design of an HighRule in the scripting environment
 * 
 */

public interface IHideRule {

	/**
	 * Returns format
	 * 
	 * @return format
	 */
	public String getFormat();

	/**
	 * Sets format
	 * 
	 * @param format
	 * @throws ScriptException
	 */
	public void setFormat(String format) throws ScriptException;

	/**
	 * Returns value expression
	 * 
	 * @return value expression
	 */
	public String getValueExpr();

	/**
	 * Sets value expression.
	 * 
	 * @param valueExpr
	 * @throws ScriptException
	 */
	public void setValueExpr(String valueExpr) throws ScriptException;

	/**
	 * Returns structure.
	 * 
	 * @return structure
	 */

	public IStructure getStructure();
}
