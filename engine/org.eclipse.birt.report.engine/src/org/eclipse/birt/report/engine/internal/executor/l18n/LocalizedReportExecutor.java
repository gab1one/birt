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

package org.eclipse.birt.report.engine.internal.executor.l18n;

import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.report.engine.content.IReportContent;
import org.eclipse.birt.report.engine.executor.ExecutionContext;
import org.eclipse.birt.report.engine.executor.IReportExecutor;
import org.eclipse.birt.report.engine.extension.IReportItemExecutor;
import org.eclipse.birt.report.engine.internal.executor.wrap.WrappedReportExecutor;
import org.eclipse.birt.report.engine.presentation.LocalizedContentVisitor;
import org.eclipse.birt.report.engine.util.FastPool;

public class LocalizedReportExecutor extends WrappedReportExecutor {

	IReportExecutor executor;
	LocalizedContentVisitor l18nVisitor;
	FastPool freeExecutors;

	public LocalizedReportExecutor(ExecutionContext context, IReportExecutor executor) {
		super(executor);
		this.l18nVisitor = new LocalizedContentVisitor(context);
		this.freeExecutors = new FastPool();
		this.executor = executor;
	}

	protected IReportItemExecutor createWrappedExecutor(IReportItemExecutor executor) {
		LocalizedReportItemExecutor l18nExecutor = null;
		if (!freeExecutors.isEmpty()) {
			l18nExecutor = (LocalizedReportItemExecutor) freeExecutors.remove();
			l18nExecutor.setExecutor(executor);
		} else {
			l18nExecutor = new LocalizedReportItemExecutor(this, executor);
		}
		return l18nExecutor;
	}

	protected void closeWrappedExecutor(IReportItemExecutor executor) {
		freeExecutors.add(executor);
	}

	public IReportContent execute() throws BirtException {
		IReportContent report = super.execute();
		if (report != null) {
			report = l18nVisitor.localizeReport(report);
		}
		return report;
	}
}
