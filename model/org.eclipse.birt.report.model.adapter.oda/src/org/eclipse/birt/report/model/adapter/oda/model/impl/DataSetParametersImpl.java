/**
 * <copyright>
 * </copyright>
 *
 * $Id: DataSetParametersImpl.java,v 1.1.2.1 2010/11/29 06:23:52 rlu Exp $
 */
package org.eclipse.birt.report.model.adapter.oda.model.impl;

import java.util.Collection;

import org.eclipse.birt.report.model.adapter.oda.model.DataSetParameter;
import org.eclipse.birt.report.model.adapter.oda.model.DataSetParameters;
import org.eclipse.birt.report.model.adapter.oda.model.ModelPackage;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Data
 * Set Parameters</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.birt.report.model.adapter.oda.model.impl.DataSetParametersImpl#getParameter
 * <em>Parameter</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DataSetParametersImpl extends EObjectImpl implements DataSetParameters {
	/**
	 * The cached value of the '{@link #getParameters() <em>Parameter</em>}'
	 * containment reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getParameters()
	 * @generated
	 * @ordered
	 */
	protected EList<DataSetParameter> parameter;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected DataSetParametersImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelPackage.Literals.DATA_SET_PARAMETERS;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<DataSetParameter> getParameters() {
		if (parameter == null) {
			parameter = new EObjectContainmentEList<DataSetParameter>(DataSetParameter.class, this,
					ModelPackage.DATA_SET_PARAMETERS__PARAMETER);
		}
		return parameter;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case ModelPackage.DATA_SET_PARAMETERS__PARAMETER:
			return ((InternalEList<?>) getParameters()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case ModelPackage.DATA_SET_PARAMETERS__PARAMETER:
			return getParameters();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case ModelPackage.DATA_SET_PARAMETERS__PARAMETER:
			getParameters().clear();
			getParameters().addAll((Collection<? extends DataSetParameter>) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case ModelPackage.DATA_SET_PARAMETERS__PARAMETER:
			getParameters().clear();
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case ModelPackage.DATA_SET_PARAMETERS__PARAMETER:
			return parameter != null && !parameter.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} // DataSetParametersImpl
