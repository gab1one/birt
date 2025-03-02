/*
 *************************************************************************
 * Copyright (c) 2011 Actuate Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Actuate Corporation  - initial API and implementation
 *  
 *************************************************************************
 */
package org.eclipse.birt.data.engine.api.timefunction;

import java.util.Date;

public class ReferenceDate implements IReferenceDate {
	private Date referenceDate;

	public ReferenceDate(Date referenceDate) {
		this.referenceDate = referenceDate;
	}

	public Date getDate() {
		return this.referenceDate;
	}

}
