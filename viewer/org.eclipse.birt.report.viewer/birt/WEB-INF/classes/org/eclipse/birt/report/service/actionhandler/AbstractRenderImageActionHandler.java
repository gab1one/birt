/*************************************************************************************
 * Copyright (c) 2004 Actuate Corporation and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Actuate Corporation - Initial implementation.
 ************************************************************************************/

package org.eclipse.birt.report.service.actionhandler;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.birt.report.context.IContext;
import org.eclipse.birt.report.service.api.InputOptions;
import org.eclipse.birt.report.soapengine.api.GetUpdatedObjectsResponse;
import org.eclipse.birt.report.soapengine.api.Operation;
import org.eclipse.birt.report.utility.ParameterAccessor;

abstract public class AbstractRenderImageActionHandler extends AbstractBaseActionHandler {

	public AbstractRenderImageActionHandler(IContext context, Operation operation, GetUpdatedObjectsResponse response) {
		super(context, operation, response);
	}

	public void __execute() throws Exception {
		HttpServletRequest request = context.getRequest();
		HttpServletResponse response = context.getResponse();

		String docName = null;// TODO: Do we need document name?
		String imageId = request.getParameter(ParameterAccessor.PARAM_IMAGEID);

		response.setContentType(__getContentTypeByID(imageId));
		ServletOutputStream out = response.getOutputStream();

		InputOptions options = new InputOptions();
		options.setOption(InputOptions.OPT_REQUEST, request);

		getReportService().getImage(docName, imageId, out, options);
	}

	private String __getContentTypeByID(String imageId) {
		if (imageId.endsWith(".svg")) //$NON-NLS-1$
		{
			return "image/svg+xml"; //$NON-NLS-1$
		} else if (imageId.endsWith(".ico")) //$NON-NLS-1$
		{
			return "image/x-icon"; //$NON-NLS-1$
		}
		return "image"; //$NON-NLS-1$
	}
}
