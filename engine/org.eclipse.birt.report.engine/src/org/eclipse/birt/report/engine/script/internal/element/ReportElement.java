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

package org.eclipse.birt.report.engine.script.internal.element;

import org.eclipse.birt.report.engine.api.script.ScriptException;
import org.eclipse.birt.report.engine.api.script.element.IReportElement;
import org.eclipse.birt.report.model.api.ReportElementHandle;
import org.eclipse.birt.report.model.api.activity.SemanticException;

public class ReportElement extends DesignElement implements IReportElement {
	public ReportElement(ReportElementHandle handle) {
		super(handle);
	}

	public ReportElement(org.eclipse.birt.report.model.api.simpleapi.IReportElement reportElementImpl) {
		super(reportElementImpl);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.birt.report.engine.api.script.element.IReportItem#setCustomXml(
	 * java.lang.String)
	 */

	public void setCustomXml(String customXml) throws ScriptException {
		try {
			((org.eclipse.birt.report.model.api.simpleapi.IReportElement) designElementImpl).setCustomXml(customXml);
		} catch (SemanticException e) {
			throw new ScriptException(e.getLocalizedMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.birt.report.engine.api.script.element.IReportItem#getName()
	 */

	public String getName() {
		return ((org.eclipse.birt.report.model.api.simpleapi.IReportElement) designElementImpl).getName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.birt.report.engine.api.script.element.IReportItem#setName(java.
	 * lang.String)
	 */

	public void setName(String name) throws ScriptException {
		try {
			((org.eclipse.birt.report.model.api.simpleapi.IReportElement) designElementImpl).setName(name);
		} catch (SemanticException e) {
			throw new ScriptException(e.getLocalizedMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.birt.report.engine.api.script.element.IReportItem#getCustomXml()
	 */

	public String getCustomXml() {
		return ((org.eclipse.birt.report.model.api.simpleapi.IReportElement) designElementImpl).getCustomXml();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.birt.report.engine.api.script.element.IReportItem#setComments(
	 * java.lang.String)
	 */

	public void setComments(String theComments) throws ScriptException {
		try {
			((org.eclipse.birt.report.model.api.simpleapi.IReportElement) designElementImpl).setComments(theComments);
		} catch (SemanticException e) {
			throw new ScriptException(e.getLocalizedMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.birt.report.engine.api.script.element.IReportItem#getComments()
	 */

	public String getComments() {
		return ((org.eclipse.birt.report.model.api.simpleapi.IReportElement) designElementImpl).getComments();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.birt.report.engine.api.script.element.IReportItem#
	 * setDisplayNameKey(java.lang.String)
	 */

	public void setDisplayNameKey(String displayNameKey) throws ScriptException {
		try {
			((org.eclipse.birt.report.model.api.simpleapi.IReportElement) designElementImpl)
					.setDisplayNameKey(displayNameKey);
		} catch (SemanticException e) {
			throw new ScriptException(e.getLocalizedMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.birt.report.engine.api.script.element.IReportItem#
	 * getDisplayNameKey()
	 */

	public String getDisplayNameKey() {
		return ((org.eclipse.birt.report.model.api.simpleapi.IReportElement) designElementImpl).getDisplayNameKey();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.birt.report.engine.api.script.element.IReportItem#setDisplayName(
	 * java.lang.String)
	 */

	public void setDisplayName(String displayName) throws ScriptException {
		try {
			((org.eclipse.birt.report.model.api.simpleapi.IReportElement) designElementImpl)
					.setDisplayName(displayName);
		} catch (SemanticException e) {
			throw new ScriptException(e.getLocalizedMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.birt.report.engine.api.script.element.IReportItem#getDisplayName(
	 * )
	 */

	public String getDisplayName() {
		return ((org.eclipse.birt.report.model.api.simpleapi.IReportElement) designElementImpl).getDisplayName();
	}
}
