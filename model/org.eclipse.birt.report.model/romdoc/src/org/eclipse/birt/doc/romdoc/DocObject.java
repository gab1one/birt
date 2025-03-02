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

package org.eclipse.birt.doc.romdoc;

import org.eclipse.birt.report.model.api.metadata.IStructureDefn;
import org.eclipse.birt.report.model.metadata.PropertyType;

public abstract class DocObject {
	protected String description;
	protected String summary;
	protected String seeAlso;

	public abstract String getName();

	public String makeElementLink(String elementName, String dir) {
		StringBuffer link = new StringBuffer();
		link.append("<a href=\""); //$NON-NLS-1$
		if (dir == null)
			link.append("elements/"); //$NON-NLS-1$
		else if (!dir.equals("elements")) //$NON-NLS-1$
			link.append("../elements/"); //$NON-NLS-1$
		link.append(elementName);
		link.append(".html\">"); //$NON-NLS-1$
		link.append(elementName);
		link.append("</a>"); //$NON-NLS-1$
		return link.toString();
	}

	public String makeStructureLink(IStructureDefn struct, String dir) {
		StringBuffer link = new StringBuffer();
		link.append("<a href=\""); //$NON-NLS-1$
		if (dir == null)
			link.append("structs/"); //$NON-NLS-1$
		else if (!dir.equals("structs")) //$NON-NLS-1$
			link.append("../structs/"); //$NON-NLS-1$
		link.append(struct.getName());
		link.append(".html\">"); //$NON-NLS-1$
		link.append(struct.getName());
		link.append("</a>"); //$NON-NLS-1$
		return link.toString();
	}

	public String makeTypeLink(PropertyType type, String dir) {
		StringBuffer link = new StringBuffer();
		link.append("<a href=\""); //$NON-NLS-1$
		if (dir != null)
			link.append("../"); //$NON-NLS-1$
		link.append("types.html#"); //$NON-NLS-1$
		link.append(type.getName());
		link.append("\">"); //$NON-NLS-1$
		link.append(type.getName());
		link.append("</a>"); //$NON-NLS-1$
		return link.toString();
	}

	public String yesNo(boolean flag) {
		return flag ? "Yes" : "No"; //$NON-NLS-1$//$NON-NLS-2$
	}

	public String getDescription() {
		return description;
	}

	public String getSummary() {
		return summary;
	}

	public String getSeeAlso() {
		return seeAlso;
	}

	public void setDescription(String string) {
		description = string;
	}

	public void setSeeAlso(String string) {
		seeAlso = string;
	}

	public void setSummary(String string) {
		summary = string;
	}
}
