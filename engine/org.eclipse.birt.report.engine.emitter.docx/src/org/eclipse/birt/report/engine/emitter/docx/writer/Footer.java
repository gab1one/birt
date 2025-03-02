/*******************************************************************************
 * Copyright (c) 2013 Actuate Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Actuate Corporation  - initial API and implementation
 *******************************************************************************/

package org.eclipse.birt.report.engine.emitter.docx.writer;

import java.io.IOException;

import org.eclipse.birt.report.engine.ooxml.IPart;

public class Footer extends BasicComponent {

	Document document;
	int footerHeight;
	int footerWidth;

	Footer(IPart part, Document document, int footerHeight, int footerWidth) throws IOException {
		super(part);
		this.document = document;
		this.footerHeight = footerHeight;
		this.footerWidth = footerWidth;
	}

	void start() {
		writer.startWriter();
		writer.openTag("w:ftr");
		writeXmlns();
		startHeaderFooterContainer(footerHeight, footerWidth);
	}

	void end() {
		endHeaderFooterContainer();
		writer.closeTag("w:ftr");
		writer.endWriter();
		writer.close();
	}

	protected int getImageID() {
		return document.getImageID();
	}

	protected int getMhtTextId() {
		return document.getMhtTextId();
	}
}
