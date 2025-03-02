/*************************************************************************************
 * Copyright (c) 2004-2008 Actuate Corporation and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Actuate Corporation - Initial implementation.
 ************************************************************************************/

package org.eclipse.birt.report.taglib.component;

import java.io.Serializable;

/**
 * Specifies the report parameter value.
 * <p>
 * There are the following parameter attributes:
 * <ol>
 * <li>value</li>
 * <li>displayText</li>
 * </ol>
 */
public class ParamValueField implements Serializable {
	private static final long serialVersionUID = -7929285151061323600L;

	private Object value;
	private String displayText;

	public ParamValueField() {
		super();
		value = null;
		displayText = null;
	}

	/**
	 * @return the value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * @return the displayText
	 */
	public String getDisplayText() {
		return displayText;
	}

	/**
	 * @param displayText the displayText to set
	 */
	public void setDisplayText(String displayText) {
		this.displayText = displayText;
	}

}
