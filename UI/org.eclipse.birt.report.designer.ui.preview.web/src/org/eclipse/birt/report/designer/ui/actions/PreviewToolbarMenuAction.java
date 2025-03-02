/*******************************************************************************
 * Copyright (c) 2004 Actuate Corporation and others. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Actuate Corporation - Initial implementation.
 ******************************************************************************/

package org.eclipse.birt.report.designer.ui.actions;

import org.eclipse.birt.report.designer.internal.ui.util.Policy;
import org.eclipse.birt.report.designer.internal.ui.util.UIUtil;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowPulldownDelegate;

/**
 * PreviewToolbarMenuAction
 */
public class PreviewToolbarMenuAction extends PreviewSupport implements IWorkbenchWindowPulldownDelegate {

	/**
	 * The constructor.
	 */
	public PreviewToolbarMenuAction() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IWorkbenchWindowPulldownDelegate#getMenu(org.eclipse.swt
	 * .widgets.Control)
	 */
	public Menu getMenu(Control parent) {
		return getPreviewMenu(parent, true);
	}

	/**
	 * @see org.eclipse.ui.IWorkbenchWindowActionDelegate#init(org.eclipse.ui.
	 *      IWorkbenchWindow)
	 */
	public void init(IWorkbenchWindow window) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.birt.report.designer.ui.actions.PreviewAction#dispose()
	 */
	public void dispose() {
	}

	public void run(IAction action) {
		if (Policy.TRACING_ACTIONS) {
			System.out.println("Preview action >> Run ..."); //$NON-NLS-1$
		}
		preview(TYPE_HTML, true);
	}

	/**
	 * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action
	 *      .IAction, org.eclipse.jface.viewers.ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		action.setEnabled(isEnable());
	}

	private boolean isEnable() {
		IEditorPart editor = UIUtil.getActiveEditor(true);
		if (editor != null) {
			IContentType[] contentTypes = Platform.getContentTypeManager()
					.findContentTypesFor(editor.getEditorInput().getName());
			for (IContentType type : contentTypes) {
				if (type.getId().equals("org.eclipse.birt.report.designer.ui.editors.reportdesign") //$NON-NLS-1$
						|| type.getId().equals("org.eclipse.birt.report.designer.ui.editors.reporttemplate")) //$NON-NLS-1$
					return true;
			}
		}
		return false;
	}
}