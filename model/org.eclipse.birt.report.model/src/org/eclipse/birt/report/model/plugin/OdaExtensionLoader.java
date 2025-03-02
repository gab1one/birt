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

package org.eclipse.birt.report.model.plugin;

import java.util.Iterator;
import java.util.Properties;

import org.eclipse.birt.report.model.api.elements.ReportDesignConstants;
import org.eclipse.birt.report.model.metadata.ExtensionElementDefn;
import org.eclipse.birt.report.model.metadata.MetaDataDictionary;
import org.eclipse.birt.report.model.metadata.MetaDataException;
import org.eclipse.birt.report.model.metadata.ODAExtensionElementDefn;
import org.eclipse.datatools.connectivity.oda.util.manifest.DataSetType;
import org.eclipse.datatools.connectivity.oda.util.manifest.ExtensionManifest;
import org.eclipse.datatools.connectivity.oda.util.manifest.ManifestExplorer;
import org.eclipse.datatools.connectivity.oda.util.manifest.Property;

/**
 * The loader for ODA meta information.
 */

public class OdaExtensionLoader {

	public static void load() {
		loadDataSources();
	}

	private static void loadDataSources() {
		ExtensionManifest[] dataSources = ManifestExplorer.getInstance().getExtensionManifests();
		if (dataSources != null) {
			for (int i = 0; i < dataSources.length; i++) {
				ExtensionManifest manifest = dataSources[i];
				ExtensionElementDefn cachedExtDefn = new ODAExtensionElementDefn(
						MetaDataDictionary.getInstance().getElement(ReportDesignConstants.ODA_DATA_SOURCE));

				try {
					Property[] properties = null;
					Properties visibilities = null;

					properties = manifest.getProperties();
					visibilities = manifest.getPropertiesVisibility();

					if (properties != null) {
						for (int j = 0; j < properties.length; j++) {
							ODAPropertyDefn propDefn = new ODAPropertyDefn(properties[j]);

							cachedExtDefn.addProperty(propDefn);
						}

						if (visibilities != null) {
							for (Iterator<Object> iter = visibilities.keySet().iterator(); iter.hasNext();) {
								String key = (String) iter.next();
								cachedExtDefn.addPropertyVisibility(key, visibilities.getProperty(key));
							}
						}
					}

					MetaDataDictionary.getInstance().cacheOdaExtension(manifest.getExtensionID(), cachedExtDefn);

					// load the data sets
					loadDataSets(manifest);

				} catch (MetaDataException e) {
					// do nothing
					assert false;
				}
			}
		}

	}

	private static void loadDataSets(ExtensionManifest dataSource) {
		assert dataSource != null;
		DataSetType[] dataSets = dataSource.getDataSetTypes();
		if (dataSets != null) {
			for (int i = 0; i < dataSets.length; i++) {
				DataSetType type = dataSets[i];
				ExtensionElementDefn cachedExtDefn = new ODAExtensionElementDefn(
						MetaDataDictionary.getInstance().getElement(ReportDesignConstants.ODA_DATA_SET));
				try {
					Property[] properties = null;
					Properties visibilities = null;

					properties = type.getProperties();
					visibilities = type.getPropertiesVisibility();

					if (properties != null) {
						for (int j = 0; j < properties.length; j++) {
							ODAPropertyDefn propDefn = new ODAPropertyDefn(properties[j]);

							cachedExtDefn.addProperty(propDefn);
						}

						if (visibilities != null) {
							for (Iterator<Object> iter = visibilities.keySet().iterator(); iter.hasNext();) {
								String key = (String) iter.next();
								cachedExtDefn.addPropertyVisibility(key, visibilities.getProperty(key));
							}
						}
					}

					MetaDataDictionary.getInstance().cacheOdaExtension(type.getID(), cachedExtDefn);

				} catch (MetaDataException e) {
					// do nothing
					assert false;
				}
			}
		}
	}
}
