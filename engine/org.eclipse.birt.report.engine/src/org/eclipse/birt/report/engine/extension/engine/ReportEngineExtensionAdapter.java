/*******************************************************************************
 * Copyright (c) 2008 Actuate Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Actuate Corporation  - initial API and implementation
 *******************************************************************************/

package org.eclipse.birt.report.engine.extension.engine;

import org.eclipse.birt.report.engine.api.EngineException;
import org.eclipse.birt.report.engine.api.IEngineTask;
import org.eclipse.birt.report.engine.api.IReportDocument;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportRunnable;

abstract public class ReportEngineExtensionAdapter implements IReportEngineExtension {

	protected IReportEngine engine;

	public ReportEngineExtensionAdapter(IReportEngine engine) {
		this.engine = engine;
	}

	public IReportEngine getReportEngine() {
		return engine;
	}

	public void close() {
	}

	public boolean needExtension(IReportRunnable runnable) {
		return false;
	}

	public IReportDocumentExtension createDocumentExtension(IReportDocument document) throws EngineException {
		return null;
	}

	public IDataExtension createDataExtension(IRunContext context) throws EngineException {
		return null;
	}

	public IDocumentExtension createDocumentExtension(IRunContext context) throws EngineException {
		return null;
	}

	public IGenerateExtension createGenerateExtension(IRunContext context) throws EngineException {
		return null;
	}

	public IRenderExtension createRenderExtension(IRenderContext context) throws EngineException {
		return null;
	}

	public IEngineTask createEngineTask(String taskName) throws EngineException {
		return null;
	}
}
