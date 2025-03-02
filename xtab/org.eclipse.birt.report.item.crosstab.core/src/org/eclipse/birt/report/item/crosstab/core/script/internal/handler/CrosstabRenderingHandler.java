/*******************************************************************************
 * Copyright (c) 2007 Actuate Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Actuate Corporation  - initial API and implementation
 *******************************************************************************/

package org.eclipse.birt.report.item.crosstab.core.script.internal.handler;

import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.report.engine.api.script.IReportContext;
import org.eclipse.birt.report.engine.content.ICellContent;
import org.eclipse.birt.report.engine.content.ITableContent;
import org.eclipse.birt.report.engine.script.internal.instance.RunningState;
import org.eclipse.birt.report.item.crosstab.core.ICrosstabReportItemConstants;
import org.eclipse.birt.report.item.crosstab.core.de.CrosstabCellHandle;
import org.eclipse.birt.report.item.crosstab.core.de.CrosstabReportItemHandle;
import org.eclipse.birt.report.item.crosstab.core.script.ICrosstabCellInstance;
import org.eclipse.birt.report.item.crosstab.core.script.ICrosstabInstance;
import org.eclipse.birt.report.item.crosstab.core.script.internal.CrosstabCellInstanceImpl;
import org.eclipse.birt.report.item.crosstab.core.script.internal.CrosstabInstanceImpl;
import org.eclipse.birt.report.model.api.ExtendedItemHandle;

/**
 * CrosstabRenderingHandler
 */
public class CrosstabRenderingHandler extends BaseCrosstabEventHandler {

	private CrosstabScriptHandler handler;

	public CrosstabRenderingHandler(ExtendedItemHandle modelHandle, ClassLoader contextLoader) throws BirtException {
		String javaClass = modelHandle.getEventHandlerClass();
		String script = modelHandle.getOnRender();

		if ((javaClass == null || javaClass.trim().length() == 0) && (script == null || script.trim().length() == 0)) {
			return;
		}

		handler = createScriptHandler(modelHandle, ICrosstabReportItemConstants.ON_RENDER_METHOD, script,
				contextLoader);
	}

	public void handleCrosstab(CrosstabReportItemHandle crosstab, ITableContent content, IReportContext context,
			RunningState runningState) throws BirtException {
		if (handler == null || crosstab == null) {
			return;
		}

		ICrosstabInstance crosstabInst = new CrosstabInstanceImpl(content, crosstab.getModelHandle(), runningState);

		handler.callFunction(CrosstabScriptHandler.ON_RENDER_CROSSTAB, crosstabInst, context);
	}

	public void handleCell(CrosstabCellHandle cell, ICellContent content, IReportContext context) throws BirtException {
		if (handler == null || cell == null) {
			return;
		}

		ICrosstabCellInstance cellInst = new CrosstabCellInstanceImpl(content,
				(ExtendedItemHandle) cell.getModelHandle(), context);

		handler.callFunction(CrosstabScriptHandler.ON_RENDER_CELL, cellInst, context);
	}
}
