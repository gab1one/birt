/*
 *************************************************************************
 * Copyright (c) 2004, 2008 Actuate Corporation.
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

package org.eclipse.birt.data.aggregation.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;

import org.eclipse.birt.core.data.DataType;
import org.eclipse.birt.core.data.DataTypeUtil;
import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.data.aggregation.api.IBuildInAggregation;
import org.eclipse.birt.data.aggregation.i18n.Messages;
import org.eclipse.birt.data.engine.api.aggregation.Accumulator;
import org.eclipse.birt.data.engine.api.aggregation.IParameterDefn;
import org.eclipse.birt.data.engine.core.DataException;

/**
 * Implements the built-in Total.concatenate aggregation
 */
public class TotalConcatenate extends AggrFunction {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.birt.data.engine.aggregation.Aggregation#getName()
	 */
	public String getName() {
		return IBuildInAggregation.TOTAL_CONCATENATE_FUNC;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.birt.data.engine.aggregation.Aggregation#getType()
	 */
	public int getType() {
		return SUMMARY_AGGR;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.birt.data.engine.api.aggregation.IAggregation#getDateType()
	 */
	public int getDataType() {
		return DataType.STRING_TYPE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.birt.data.engine.api.aggregation.IAggrFunction#getParameterDefn
	 * ()
	 */
	public IParameterDefn[] getParameterDefn() {
		IParameterDefn paramDefn[] = new IParameterDefn[] {
				new ParameterDefn(Constants.EXPRESSION_NAME, Constants.EXPRESSION_DISPLAY_NAME, false, true,
						SupportedDataTypes.ANY, Messages.getString("TotalConcatenate.paramDescription.expression")),
				new ParameterDefn(Constants.SEPARATOR_NAME, Constants.SEPARATOR_DISPLAY_NAME, true, false,
						SupportedDataTypes.CALCULATABLE,
						Messages.getString("TotalConcatenate.paramDescription.separator")),
				new ParameterDefn(Constants.MAXLENGTH_NAME, Constants.MAXLENGTH__DISPLAY_NAME, true, false,
						SupportedDataTypes.CALCULATABLE,
						Messages.getString("TotalConcatenate.paramDescription.maxLength")),
				new ParameterDefn(Constants.SHOWALLVALUES_NAME, Constants.SHOWALLVALUES_DISPLAY_NAME, true, false,
						SupportedDataTypes.CALCULATABLE,
						Messages.getString("TotalConcatenate.paramDescription.showAllValues")) };
		return paramDefn;
	}

	public Accumulator newAccumulator() {
		return new MyAccumulator();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.birt.data.engine.api.aggregation.IAggrFunction#getDescription ()
	 */
	public String getDescription() {
		return Messages.getString("TotalConcatenate.description"); //$NON-NLS-1$
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.birt.data.engine.api.aggregation.IAggrFunction#getDisplayName ()
	 */
	public String getDisplayName() {
		return Messages.getString("TotalConcatenate.displayName"); //$NON-NLS-1$
	}

	private class MyAccumulator extends SummaryAccumulator {

		private Collection<String> values;

		private String separator;

		private int maxLength;

		private boolean isInitialized;

		final private static int DEFAULT_MAX_LENGTH = 1024;

		public void start() {
			super.start();
			values = null;
			separator = "";
			isInitialized = false;
			maxLength = DEFAULT_MAX_LENGTH;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.birt.data.engine.aggregation.Accumulator#onRow(java.lang
		 * .Object[])
		 */
		public void onRow(Object[] args) throws DataException {
			assert (args != null && args.length >= 1);

			try {
				if (!isInitialized) {
					isInitialized = true;
					setSeparator(args.length >= 2 ? args[1] : null);
					setMaxLength(args.length >= 3 ? args[2] : DEFAULT_MAX_LENGTH);
					setShowAllValues(args.length == 4 ? args[3] : false);
				}
				if (args[0] != null) {
					values.add(DataTypeUtil.toString(args[0]));
				}
			} catch (BirtException e) {
				throw DataException.wrap(e);
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @seeorg.eclipse.birt.data.engine.aggregation.SummaryAccumulator#
		 * getSummaryValue()
		 */
		public Object getSummaryValue() {
			if (values == null) {
				return null;
			}
			StringBuffer buffer = new StringBuffer();
			Iterator<String> valueIterator = values.iterator();
			while (valueIterator.hasNext()) {
				String currentValue = valueIterator.next();
				// reach the max length of the concatenated string
				if (buffer.length() > this.maxLength - currentValue.length()) {
					break;
				}
				if (currentValue.trim().length() > 0) {
					buffer.append(currentValue).append(separator);
				}
			}
			// delete the last separator character
			if (buffer.length() > 0) {
				return buffer.toString().substring(0, buffer.length() - separator.length());
			}
			return buffer.toString();
		}

		/**
		 * Set the separator of the concatenated string
		 * 
		 * @param source
		 * @throws BirtException
		 */
		private void setSeparator(Object source) throws BirtException {
			String value = DataTypeUtil.toString(source);
			if (value != null) {
				// should not trim the separator string
				separator = value;
			}
		}

		/**
		 * Set the max length of the concatenated string by calculating the string
		 * character number
		 * 
		 * @param source
		 * @throws DataException
		 */
		private void setMaxLength(Object source) throws DataException {
			try {
				if (source == null || DataTypeUtil.toString(source).trim().length() == 0) {
					maxLength = DEFAULT_MAX_LENGTH;
				} else {
					int value = DataTypeUtil.toInteger(source);
					if (value == 0)
						maxLength = DEFAULT_MAX_LENGTH;
					if (value < 0) {
						throw new DataException(Messages.getString("aggregation.InvalidParameterValue"),
								new Object[] { getParameterDefn()[2].getDisplayName(), getDisplayName(), value });
					}
					maxLength = value;
				}
			} catch (BirtException e) {
				throw DataException.wrap(e);
			}
		}

		/**
		 * Decide whether should show all values whatever some strings are the same
		 * 
		 * @param source
		 * @throws BirtException
		 */
		private void setShowAllValues(Object source) throws BirtException {
			boolean showAllValues;
			if (source == null || DataTypeUtil.toString(source).trim().length() == 0) {
				showAllValues = false;
			} else if (source instanceof String) {
				try {
					showAllValues = DataTypeUtil.toBoolean(source);
				} catch (Exception ex) {
					throw new DataException(Messages.getString("aggregation.InvalidParameterValue"), new Object[] {
							getParameterDefn()[3].getDisplayName().replaceAll("&", ""), getDisplayName(), source });
				}
			} else if (!(source instanceof Boolean)) {
				throw new DataException(Messages.getString("aggregation.InvalidParameterType") + getName());
			} else {
				showAllValues = DataTypeUtil.toBoolean(source);
			}
			if (showAllValues)// add each row's value to an ArrayList
			{
				values = new ArrayList<String>();
			} else
			// add each row's value to a LinkedHashSet
			{
				values = new LinkedHashSet<String>();
			}

		}
	}

}
