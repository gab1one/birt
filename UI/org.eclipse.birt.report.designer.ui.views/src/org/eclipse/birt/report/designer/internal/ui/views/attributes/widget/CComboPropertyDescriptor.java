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

package org.eclipse.birt.report.designer.internal.ui.views.attributes.widget;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.birt.report.designer.internal.ui.swt.custom.CCombo;
import org.eclipse.birt.report.designer.internal.ui.swt.custom.FormWidgetFactory;
import org.eclipse.birt.report.designer.internal.ui.views.attributes.page.WidgetUtil;
import org.eclipse.birt.report.designer.internal.ui.views.attributes.provider.DataSetDescriptorProvider;
import org.eclipse.birt.report.model.api.activity.SemanticException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * ComboPropertyDescriptor manages Combo choice control.
 */
public class CComboPropertyDescriptor extends PropertyDescriptor {

	protected CCombo combo;

	public CComboPropertyDescriptor(boolean formStyle) {
		setFormStyle(formStyle);
	}

	public void setInput(Object handle) {
		this.input = handle;
		getDescriptorProvider().setInput(input);
	}

	private int style = SWT.BORDER | SWT.READ_ONLY;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.birt.report.designer.internal.ui.views.attributes.widget.
	 * PropertyDescriptor#getControl()
	 */
	public Control getControl() {
		return combo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.birt.report.designer.ui.extensions.IPropertyDescriptor#
	 * createControl(org.eclipse.swt.widgets.Composite)
	 */
	public Control createControl(Composite parent) {
		if (isFormStyle()) {
			combo = FormWidgetFactory.getInstance().createBirtCCombo(parent);
		} else {
			combo = new CCombo(parent, style);
		}
		combo.addControlListener(new ControlListener() {

			public void controlMoved(ControlEvent e) {
				combo.clearSelection();
			}

			public void controlResized(ControlEvent e) {
				combo.clearSelection();
			}
		});

		if (comboSelectList.isEmpty()) {
			SelectionListener listener = new SelectionAdapter() {

				public void widgetSelected(SelectionEvent e) {
					handleComboSelectEvent();
				}

				public void widgetDefaultSelected(SelectionEvent e) {
					handleComboSelectEvent();
				}
			};
			addComboSelectionListener(listener);
		}
		return combo;
	}

	/**
	 * Processes the save action.
	 */
	protected void handleComboSelectEvent() {
		try {
			save(combo.getText());
		} catch (SemanticException e) {
			combo.setText(oldValue);
			WidgetUtil.processError(combo.getShell(), e);
		}
	}

	public void save(Object value) throws SemanticException {
		descriptorProvider.save(value);
	}

	public String getStringValue() {
		return combo.getText();
	}

	public void setStringValue(String value) {
		combo.setText(value);
	}

	public void setHidden(boolean isHidden) {
		WidgetUtil.setExcludeGridData(combo, isHidden);
	}

	public void setVisible(boolean isVisible) {
		combo.setVisible(isVisible);
	}

	protected String oldValue;

	public void load() {
		if (getDescriptorProvider() instanceof DataSetDescriptorProvider) {
			DataSetDescriptorProvider provider = (DataSetDescriptorProvider) getDescriptorProvider();
			if (!provider.isEnable()) {
				combo.setEnabled(false);
				combo.deselectAll();
				return;
			}
			combo.setEnabled(true);

			String selectedDataSetName = combo.getText();
			String[] oldList = combo.getItems();
			String[] newList = provider.getItems();
			if (!Arrays.asList(oldList).equals(Arrays.asList(newList))) {
				combo.setItems(newList);
				combo.setText(selectedDataSetName);
			}
			String dataSetName = provider.load().toString();
			if (!dataSetName.equals(selectedDataSetName)) {
				combo.deselectAll();
				combo.setText(dataSetName);
			}

			oldValue = combo.getText();

		}
	}

	public void addStyle(int style) {
		this.style |= style;
	}

	protected List comboSelectList = new ArrayList();

	public void addComboSelectionListener(SelectionListener listener) {
		if (!comboSelectList.contains(listener)) {
			if (!comboSelectList.isEmpty())
				removeComboSelectionListener((SelectionListener) comboSelectList.get(0));
			comboSelectList.add(listener);
			if (combo != null)
				combo.addSelectionListener(listener);
		}
	}

	public void removeComboSelectionListener(SelectionListener listener) {
		if (comboSelectList.contains(listener)) {
			comboSelectList.remove(listener);
			if (combo != null)
				combo.removeSelectionListener(listener);
		}
	}
}