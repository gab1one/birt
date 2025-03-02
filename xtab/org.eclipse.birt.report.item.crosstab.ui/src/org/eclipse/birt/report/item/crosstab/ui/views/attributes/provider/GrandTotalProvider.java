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

package org.eclipse.birt.report.item.crosstab.ui.views.attributes.provider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

import org.eclipse.birt.report.designer.internal.ui.views.attributes.provider.AbstractFormHandleProvider;
import org.eclipse.birt.report.designer.ui.util.ExceptionUtil;
import org.eclipse.birt.report.designer.ui.views.attributes.providers.ChoiceSetFactory;
import org.eclipse.birt.report.designer.util.DEUtil;
import org.eclipse.birt.report.item.crosstab.core.ICrosstabReportItemConstants;
import org.eclipse.birt.report.item.crosstab.core.de.ComputedMeasureViewHandle;
import org.eclipse.birt.report.item.crosstab.core.de.CrosstabReportItemHandle;
import org.eclipse.birt.report.item.crosstab.core.de.CrosstabViewHandle;
import org.eclipse.birt.report.item.crosstab.core.de.MeasureViewHandle;
import org.eclipse.birt.report.item.crosstab.core.util.CrosstabUtil;
import org.eclipse.birt.report.item.crosstab.ui.i18n.Messages;
import org.eclipse.birt.report.item.crosstab.ui.views.dialogs.CrosstabGrandTotalDialog;
import org.eclipse.birt.report.model.api.ExtendedItemHandle;
import org.eclipse.birt.report.model.api.activity.NotificationEvent;
import org.eclipse.birt.report.model.api.activity.SemanticException;
import org.eclipse.birt.report.model.api.command.ContentEvent;
import org.eclipse.birt.report.model.api.command.PropertyEvent;
import org.eclipse.birt.report.model.api.elements.ReportDesignConstants;
import org.eclipse.birt.report.model.api.extension.ExtendedElementException;
import org.eclipse.birt.report.model.api.metadata.IChoice;
import org.eclipse.birt.report.model.elements.interfaces.IMeasureModel;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Table;

/**
 * 
 */

public class GrandTotalProvider extends AbstractFormHandleProvider {

	private CellEditor[] editors;
	private String[] columnNames = new String[] { Messages.getString("GrandTotalProvider.Column.DataField") // $NON-NLS-2$
	};

	protected CrosstabReportItemHandle crosstabReportItemHandle;
	private int[] columnWidths = new int[] { 200 };

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.birt.report.designer.internal.ui.views.attributes.provider
	 * .IFormProvider#canModify(java.lang.Object, java.lang.String)
	 */
	public boolean canModify(Object element, String property) {
		return false;
	}

	private int axis;

	public void setAxis(int axis) {
		this.axis = axis;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.birt.report.designer.internal.ui.views.attributes.provider
	 * .IFormProvider#doAddItem(int)
	 */
	public boolean doAddItem(int pos) throws Exception {
		// TODO Auto-generated method stub

		CrosstabReportItemHandle reportHandle = null;
		try {
			reportHandle = (CrosstabReportItemHandle) ((ExtendedItemHandle) (((List) input)).get(0)).getReportItem();
		} catch (ExtendedElementException e) {
			// TODO Auto-generated catch block
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		CrosstabGrandTotalDialog grandTotalDialog = new CrosstabGrandTotalDialog(reportHandle, axis);
		if (grandTotalDialog.open() == Dialog.CANCEL) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.birt.report.designer.internal.ui.views.attributes.provider
	 * .IFormProvider#doDeleteItem(int)
	 */
	public boolean doDeleteItem(int pos) throws Exception {
		// TODO Auto-generated method stub
		Object obj[] = getGrandTotalInfo(crosstabReportItemHandle);
		GrandTotalInfo info = (GrandTotalInfo) obj[pos];
		MeasureViewHandle measure = crosstabReportItemHandle.getMeasure(info.getMeasureName());
		ExtendedItemHandle itemHandle = (ExtendedItemHandle) crosstabReportItemHandle.getModelHandle();
		List measureList = itemHandle.getPropertyHandle(ICrosstabReportItemConstants.MEASURES_PROP).getContents();

		ExtendedItemHandle extend = (ExtendedItemHandle) DEUtil.getInputFirstElement(this.input);
		CrosstabReportItemHandle crossTab = null;
		try {
			crossTab = (CrosstabReportItemHandle) extend.getReportItem();
		} catch (ExtendedElementException e) {
			ExceptionUtil.handle(e);
			return false;
		}
		if (crossTab == null)
			return false;

		for (int i = 0; i < measureList.size(); i++) {
			ExtendedItemHandle extMeasure = (ExtendedItemHandle) measureList.get(i);
			if (extMeasure.getReportItem() == measure) {
				if (CrosstabUtil.isAggregationAffectAllMeasures(crossTab, axis))
					crosstabReportItemHandle.removeGrandTotal(axis);
				else
					crosstabReportItemHandle.removeGrandTotal(axis, i);
				return true;
			}
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.birt.report.designer.internal.ui.views.attributes.provider
	 * .IFormProvider#doEditItem(int)
	 */
	public boolean doEditItem(int pos) {
		// TODO Auto-generated method stub
		CrosstabGrandTotalDialog grandTotalDialog = new CrosstabGrandTotalDialog(crosstabReportItemHandle, axis);
		Object obj[] = getGrandTotalInfo(crosstabReportItemHandle);
		GrandTotalInfo info = (GrandTotalInfo) obj[pos];
		grandTotalDialog.setInput(info);
		if (grandTotalDialog.open() == Dialog.CANCEL) {
			return false;
		}

		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.birt.report.designer.internal.ui.views.attributes.provider
	 * .IFormProvider#doMoveItem(int, int)
	 */
	public boolean doMoveItem(int oldPos, int newPos) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.birt.report.designer.internal.ui.views.attributes.provider
	 * .IFormProvider#getColumnNames()
	 */
	public String[] getColumnNames() {
		// TODO Auto-generated method stub
		return columnNames;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.birt.report.designer.internal.ui.views.attributes.provider
	 * .IFormProvider#getColumnText(java.lang.Object, int)
	 */
	public String getColumnText(Object element, int columnIndex) {
		// TODO Auto-generated method stub
		GrandTotalInfo info = (GrandTotalInfo) element;
		switch (columnIndex) {
		case 0:
			return info.getMeasureName() == null ? "" : info.getMeasureName();
		case 1:
			return getFunctionDisplayName(info.getFunction());
		default:
			break;
		}
		return ""; //$NON-NLS-1$
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.birt.report.designer.internal.ui.views.attributes.provider
	 * .IFormProvider#getColumnWidths()
	 */
	public int[] getColumnWidths() {
		// TODO Auto-generated method stub
		return columnWidths;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.birt.report.designer.internal.ui.views.attributes.provider
	 * .IFormProvider#getEditors(org.eclipse.swt.widgets.Table)
	 */
	public CellEditor[] getEditors(Table table) {
		// TODO Auto-generated method stub
		if (editors == null) {
			editors = new CellEditor[columnNames.length];
			editors[0] = new TextCellEditor();
		}
		return editors;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.birt.report.designer.internal.ui.views.attributes.provider
	 * .IFormProvider#getElements(java.lang.Object)
	 */
	public Object[] getElements(Object inputElement) {
		// TODO Auto-generated method stub
		input = inputElement;
		Object obj = null;
		if (inputElement instanceof List) {
			obj = ((List) inputElement).get(0);
		} else {
			obj = inputElement;
		}

		try {
			crosstabReportItemHandle = (CrosstabReportItemHandle) (((ExtendedItemHandle) obj).getReportItem());
		} catch (ExtendedElementException e) {
			ExceptionUtil.handle(e);
		}

		return getGrandTotalInfo(crosstabReportItemHandle);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.birt.report.designer.internal.ui.views.attributes.provider
	 * .IFormProvider#getImagePath(java.lang.Object, int)
	 */
	public Image getImage(Object element, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.birt.report.designer.internal.ui.views.attributes.provider
	 * .IFormProvider#getValue(java.lang.Object, java.lang.String)
	 */
	public Object getValue(Object element, String property) {
		// TODO Auto-generated method stub
		int index = Arrays.asList(columnNames).indexOf(property);
		String columnText = getColumnText(element, index);
		return columnText;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.birt.report.designer.internal.ui.views.attributes.provider
	 * .IFormProvider#modify(java.lang.Object, java.lang.String, java.lang.Object)
	 */
	public boolean modify(Object data, String property, Object value) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.birt.report.designer.internal.ui.views.attributes.provider
	 * .IFormProvider
	 * #needRefreshed(org.eclipse.birt.report.model.api.activity.NotificationEvent )
	 */
	public boolean needRefreshed(NotificationEvent event) {
		if (event instanceof ContentEvent || event instanceof PropertyEvent) {
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.birt.report.designer.internal.ui.views.attributes.provider
	 * .IDescriptorProvider#getDisplayName()
	 */
	public String getDisplayName() {
		// TODO Auto-generated method stub
		return Messages.getString("CrosstabPageGenerator.List.GrandTotals"); //$NON-NLS-1$
	}

	public String[] getFunctionDisplayNames() {
		IChoice[] choices = getFunctions();
		if (choices == null)
			return new String[0];

		String[] displayNames = new String[choices.length];
		for (int i = 0; i < choices.length; i++) {
			displayNames[i] = choices[i].getDisplayName();
		}
		return displayNames;

	}

	public static String[] getFunctionNames() {
		IChoice[] choices = getFunctions();
		if (choices == null)
			return new String[0];

		String[] displayNames = new String[choices.length];
		for (int i = 0; i < choices.length; i++) {
			displayNames[i] = choices[i].getName();
		}
		return displayNames;
	}

	// public String getFunctionDisplayName( String name )
	// {
	// return ChoiceSetFactory.getDisplayNameFromChoiceSet( name,
	// DEUtil.getMetaDataDictionary( )
	// .getChoiceSet( DesignChoiceConstants.CHOICE_MEASURE_FUNCTION ) );
	// }
	//
	// private IChoice[] getFunctions( )
	// {
	// return DEUtil.getMetaDataDictionary( )
	// .getChoiceSet( DesignChoiceConstants.CHOICE_MEASURE_FUNCTION )
	// .getChoices( );
	// }
	public String getFunctionDisplayName(String name)

	{
		return ChoiceSetFactory.getDisplayNameFromChoiceSet(name,
				DEUtil.getMetaDataDictionary().getElement(ReportDesignConstants.MEASURE_ELEMENT)
						.getProperty(IMeasureModel.FUNCTION_PROP).getAllowedChoices());

	}

	private static IChoice[] getFunctions()

	{
		return DEUtil.getMetaDataDictionary().getElement(ReportDesignConstants.MEASURE_ELEMENT)
				.getProperty(IMeasureModel.FUNCTION_PROP).getAllowedChoices().getChoices();

	}

	private Object[] getGrandTotalInfo(CrosstabReportItemHandle reportHandle) {

		List retValue = new ArrayList();
		List measures = reportHandle.getAggregationMeasures(axis);
		for (int i = 0; i < measures.size(); i++) {
			MeasureViewHandle measureViewHandle = (MeasureViewHandle) measures.get(i);
			if (measureViewHandle instanceof ComputedMeasureViewHandle) {
				continue;
			}
			GrandTotalInfo info = new GrandTotalInfo(reportHandle, axis, measureViewHandle);
			retValue.add(info);
		}

		return retValue.toArray(new Object[retValue.size()]);

	}

	public boolean isAddEnable(Object selectedObject) {
		return isAddEnable();
	}

	public boolean isAddEnable() {
		ExtendedItemHandle extend = (ExtendedItemHandle) DEUtil.getInputFirstElement(this.input);
		CrosstabReportItemHandle crossTab = null;
		try {
			crossTab = (CrosstabReportItemHandle) extend.getReportItem();
		} catch (ExtendedElementException e) {
			ExceptionUtil.handle(e);
			return false;
		}
		if (crossTab == null)
			return false;
		ExtendedItemHandle extendedItem = (ExtendedItemHandle) crossTab.getModelHandle();
		int measureCount = extendedItem.getPropertyHandle(ICrosstabReportItemConstants.MEASURES_PROP).getContentCount()
				- crossTab.getComputedMeasures().size();
		if (measureCount == 0 || getGrandTotalInfo(crossTab).length >= measureCount)
			return false;

		// fix bug 202113
		int dimCount = getDimensionCount(crosstabReportItemHandle);
		if (dimCount == 0) {
			return false;
		}

		return true;
	}

	private int getDimensionCount(CrosstabReportItemHandle crosstab) {
		CrosstabViewHandle crosstabView = crosstab.getCrosstabView(axis);
		if (crosstabView == null) {
			return 0;
		} else {
			return crosstabView.getDimensionCount();
		}
	}

	public static class GrandTotalInfo {

		private CrosstabReportItemHandle reportHandle;
		private MeasureViewHandle measureViewHandle;
		private int axis;

		public GrandTotalInfo(CrosstabReportItemHandle reportHandle, int axis, MeasureViewHandle measureViewHandle) {
			this.reportHandle = reportHandle;
			this.axis = axis;
			this.measureViewHandle = measureViewHandle;
		}

		public String getMeasureName() {
			return measureViewHandle.getCubeMeasureName();
		}

		public void setMeasureName(String name) {
		}

		public String getFunction() {
			String function = reportHandle.getAggregationFunction(axis, measureViewHandle);
			return function == null ? getFunctionNames()[0] : function;
		}

		public void setFunction(String function) throws SemanticException {
			reportHandle.setAggregationFunction(axis, measureViewHandle, function);
		}
	}
}
