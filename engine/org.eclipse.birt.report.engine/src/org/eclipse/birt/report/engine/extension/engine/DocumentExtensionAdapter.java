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

public class DocumentExtensionAdapter implements IDocumentExtension {

	public void close() {
	}

	public IContentProcessor getContentProcessor() throws EngineException {
		return null;
	}

	public IContentProcessor getPageProcessor() throws EngineException {
		return null;
	}
}
