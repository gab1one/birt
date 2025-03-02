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

package org.eclipse.birt.report.designer.internal.ui.palette;

import org.eclipse.birt.report.designer.internal.ui.editors.schematic.tools.AbstractToolHandleExtends;
import org.eclipse.birt.report.designer.internal.ui.editors.schematic.tools.ReportCreationTool;
import org.eclipse.gef.Tool;
import org.eclipse.gef.palette.CombinedTemplateCreationEntry;
import org.eclipse.gef.requests.CreationFactory;
import org.eclipse.jface.resource.ImageDescriptor;

/**
 * This class is extended from CombinedTemplateCreationEntry. Provides the
 * feature to add control before and after mouse event is handled.
 * 
 * @see org.eclipse.birt.report.designer.internal.ui.editors.schematic.tools.AbstractToolHandleExtends
 * 
 * 
 */
public class ReportCombinedTemplateCreationEntry extends CombinedTemplateCreationEntry {

	private AbstractToolHandleExtends preHandle;

	/**
	 * Constructor.
	 * 
	 * @param label
	 * @param shortDesc
	 * @param template
	 * @param factory
	 * @param iconSmall
	 * @param iconLarge
	 */
	public ReportCombinedTemplateCreationEntry(String label, String shortDesc, Object template, CreationFactory factory,
			ImageDescriptor iconSmall, ImageDescriptor iconLarge, AbstractToolHandleExtends preHandle) {
		super(label, shortDesc, template, factory, iconSmall, iconLarge);
		this.preHandle = preHandle;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.palette.ToolEntry#createTool()
	 */
	public Tool createTool() {
		return new ReportCreationTool(this.factory, preHandle);
	}
}