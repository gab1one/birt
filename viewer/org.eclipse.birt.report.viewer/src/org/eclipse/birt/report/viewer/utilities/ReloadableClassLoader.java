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

package org.eclipse.birt.report.viewer.utilities;

import java.net.URL;

import org.eclipse.birt.core.framework.URLClassLoader;

/**
 * This class can reload the class by specifying URL.
 */
public class ReloadableClassLoader extends ClassLoader {

	URL[] urls;
	URLClassLoader loader;
	ClassLoader parent;

	/**
	 * Constructor
	 * 
	 * @param urls
	 * @param parent
	 */
	public ReloadableClassLoader(URL[] urls, ClassLoader parent) {
		super(parent);
		this.urls = urls;
		this.parent = parent;
		this.loader = new URLClassLoader(urls, parent);
	}

	/**
	 * re-create URLClassLoader to reload class by URLs.
	 */
	public void reload() {
		if (this.loader != null) {
			this.loader.close();
		}
		this.loader = new URLClassLoader(this.urls, this.parent);
	}

	/**
	 * @see java.lang.ClassLoader#loadClass(java.lang.String)
	 */
	public Class loadClass(String name) throws ClassNotFoundException {
		return this.loader.loadClass(name);
	}

	/**
	 * @see java.lang.ClassLoader#loadClass(java.lang.String, boolean)
	 */
	protected synchronized Class loadClass(String name, boolean resolve) throws ClassNotFoundException {
		Class clz = loadClass(name);
		if (resolve)
			resolveClass(clz);

		return clz;
	}

	public void setParent(ClassLoader parent) {
		this.parent = parent;
	}

	/**
	 * @return the urls
	 */
	public URL[] getUrls() {
		return urls;
	}

	/**
	 * @param urls the urls to set
	 */
	public void setUrls(URL[] urls) {
		this.urls = urls;
	}

	/**
	 * @return the loader
	 */
	public URLClassLoader getLoader() {
		return loader;
	}
}
