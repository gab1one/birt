/*******************************************************************************
 * Copyright (c) 2004, 2007 Actuate Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Actuate Corporation  - initial API and implementation
 *******************************************************************************/

package org.eclipse.birt.report.engine.content;

/**
 * Provides the interface for the Label Content.
 */
public interface ILabelContent extends ITextContent {
	/**
	 * Set the text string of label content.
	 * 
	 * @param labelText the text string of the label content.
	 */
	void setLabelText(String labelText);

	/**
	 * Get the text string of the label content.
	 * 
	 * @return the text string of the label content.
	 */
	String getLabelText();

	/**
	 * Set the label key string of the label content.
	 * 
	 * @param labelKey the label key string of the label content.
	 */
	void setLabelKey(String labelKey);

	/**
	 * Get the label key string of the label content.
	 * 
	 * @return the label key string of the label content.
	 */
	String getLabelKey();

	/**
	 * Set the help text string of the label content.
	 */
	void setHelpText(String helpText);

	/**
	 * Get the help text string of the label content.
	 * 
	 * @return the help text string of the label content.
	 */
	String getHelpText();

	/**
	 * Get the help key string of the label content.
	 * 
	 * @return the help key string of the label content.
	 */
	String getHelpKey();
}
