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

package org.eclipse.birt.report.designer.internal.ui.dialogs.resource;

import java.io.File;
import java.net.URL;

import org.eclipse.birt.report.designer.internal.ui.resourcelocator.ResourceEntry;
import org.eclipse.birt.report.designer.ui.ReportPlugin;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

/**
 * 
 * Tree viewer label provider adapter for resource browser.
 */

public class ResourceFileLabelProvider extends LabelProvider {

	private File rootFile;

	private static final Image IMG_FOLDER = PlatformUI.getWorkbench().getSharedImages()
			.getImage(ISharedImages.IMG_OBJ_FOLDER);

	private static final Image IMG_FILE = PlatformUI.getWorkbench().getSharedImages()
			.getImage(ISharedImages.IMG_OBJ_FILE);

	public ResourceFileLabelProvider() {
		this.rootFile = new File(getRootFilePath());
	}

	private String getRootFilePath() {
		return ReportPlugin.getDefault().getResourcePreference();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.LabelProvider#getImage(java.lang.Object)
	 */
	public Image getImage(Object element) {
		if (element instanceof File) {
			if (((File) element).isDirectory()) {
				return IMG_FOLDER;
			} else {
				return IMG_FILE;
			}
		}
		if (element instanceof ResourceEntry) {
			return ((ResourceEntry) element).getImage();
		}
		return super.getImage(element);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.LabelProvider#getText(java.lang.Object)
	 */
	public String getText(Object element) {
		if (element instanceof File) {
			if (element.equals(rootFile)) {
				String path = ((File) element).getPath();
				if (path == null || path.length() == 0) {
					return ""; //$NON-NLS-1$
				}
				String retString = path.substring(path.lastIndexOf(File.separator) + 1);
				if (retString == null || retString.length() == 0) {
					retString = path;
				}
				return retString;
			} else {
				return ((File) element).getName();
			}
		}
		if (element instanceof ResourceEntry) {
			String text = ((ResourceEntry) element).getDisplayName();

			if (text == null || text.trim().length() <= 0) {
				text = ((ResourceEntry) element).getName();
			}
			return text;
		}
		return super.getText(element);
	}

	/**
	 * 
	 * @return the absolute path of resource folder
	 */
	public String getToolTip(Object element) {
		if (element instanceof File) {
			if (element.equals(rootFile)) {
				return ((File) element).getAbsolutePath();
			} else {
				return ((File) element).getName();
			}
		}
		if (element instanceof ResourceEntry) {
			URL url = ((ResourceEntry) element).getURL();
			if (url != null) {
				if (url.getPath().indexOf("/") == 0) //$NON-NLS-1$
				{
					return url.getPath().substring(1);
				} else {
					return url.getPath();
				}
			} else {
				return ""; //$NON-NLS-1$
			}
		}
		return super.getText(element);
	}

}
