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

package org.eclipse.birt.chart.examples.builder;

import org.eclipse.birt.chart.model.component.Axis;
import org.eclipse.birt.chart.ui.swt.interfaces.ITaskChangeListener;
import org.eclipse.birt.chart.ui.swt.wizard.TaskSelectData;
import org.eclipse.birt.chart.ui.swt.wizard.format.SubtaskSheetImpl;
import org.eclipse.birt.core.ui.frameworks.taskwizard.interfaces.ITask;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.swt.widgets.Composite;

/**
 * Subtask to wrap TaskSelectData
 */

public class SubtaskSelectData extends SubtaskSheetImpl implements ITaskChangeListener {

	final private ITask task;

	public SubtaskSelectData() {
		task = new TaskSelectData();
	}

	@Override
	public void createControl(Composite parent) {
		task.setContext(getContext());
		task.setUIProvider(getWizard());
		task.createControl(parent);
		cmpContent = (Composite) task.getControl();
	}

	@Override
	public boolean isPreviewable() {
		// Has internal preview canvas
		return true;
	}

	public void changeTask(Notification notification) {
		// Preview by delegating notification from TaskFormatChart to
		// TaskSelectData
		((ITaskChangeListener) task).changeTask(notification);

		if (notification.getNotifier() instanceof Axis) {
			// Update tree if series or axes are updated
			getParentTask().updateTree();
		}
	}
}
