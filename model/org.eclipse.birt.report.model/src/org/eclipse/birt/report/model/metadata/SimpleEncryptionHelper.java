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

package org.eclipse.birt.report.model.metadata;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;
import org.eclipse.birt.report.model.api.extension.IEncryptionHelper;

/**
 * Represents the default encryption helper Model provides. This class uses
 * Base64 encoder to protected value.
 */

public class SimpleEncryptionHelper implements IEncryptionHelper {

	static final private String CHARSET = "8859_1"; //$NON-NLS-1$

	/**
	 * Id of this simple encryption helper.
	 */
	static final public String ENCRYPTION_ID = "base64"; //$NON-NLS-1$

	private static final SimpleEncryptionHelper instance = new SimpleEncryptionHelper();

	/**
	 * Returns the singleton instance of <code>SimpleEncryptionHelper</code>
	 * 
	 * @return the singleton instance of <code>SimpleEncryptionHelper</code>
	 */
	public static SimpleEncryptionHelper getInstance() {
		return instance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.birt.report.model.api.extension.IEncryptionHelper#encrypt
	 * (java.lang.String)
	 */

	public String encrypt(String string) {
		if (string == null)
			return null;
		try {
			Base64 codec = new Base64();
			byte[] data = codec.encode(string.getBytes(CHARSET));
			if (data == null)
				return null;
			return new String(data, CHARSET).trim();
		} catch (UnsupportedEncodingException e) {
			assert false;
		}

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.birt.report.model.api.extension.IEncryptionHelper#decrypt
	 * (java.lang.String)
	 */

	public String decrypt(String string) {
		if (string == null)
			return null;
		try {
			Base64 codec = new Base64();
			byte[] data = codec.decode(string.getBytes(CHARSET));
			if (data == null)
				return null;

			return new String(data, CHARSET);
		} catch (UnsupportedEncodingException e) {
			assert false;
		}

		return null;
	}

}
