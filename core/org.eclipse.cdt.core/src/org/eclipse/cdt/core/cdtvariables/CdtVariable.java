/*******************************************************************************
 * Copyright (c) 2005, 2011, 2023 Intel Corporation and others.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Intel Corporation - Initial API and implementation
 * IBM Corporation
 * Serge Beauchamp (Freescale Semiconductor) - Bug 421276 - The CDT Managed Builder should support long command lines
 *******************************************************************************/
package org.eclipse.cdt.core.cdtvariables;

import java.util.Arrays;

import org.eclipse.cdt.internal.core.SafeStringInterner;
import org.eclipse.cdt.utils.cdtvariables.CdtVariableResolver;

/**
 * This is the trivial implementation of the IBuildMacro used internaly by the MBS
 *
 * @since 3.0
 */
public class CdtVariable implements ICdtVariable {
	protected String fName;
	protected int fType;
	protected String fStringValue;
	protected String fStringListValue[];

	protected CdtVariable() {

	}

	public CdtVariable(String name, int type, String value) {
		fName = SafeStringInterner.safeIntern(name);
		fType = type;
		fStringValue = SafeStringInterner.safeIntern(value);
	}

	public CdtVariable(String name, int type, String value[]) {
		fName = SafeStringInterner.safeIntern(name);
		fType = type;
		fStringListValue = value;
	}

	public CdtVariable(ICdtVariable var) {
		fName = var.getName();
		fType = var.getValueType();
		try {
			if (CdtVariableResolver.isStringListVariable(fType))
				fStringListValue = var.getStringListValue();
			else
				fStringValue = var.getStringValue();
		} catch (CdtVariableException e) {
		}
	}

	@Override
	public String getName() {
		return fName;
	}

	@Override
	public int getValueType() {
		return fType;
	}

	@Override
	public String getStringValue() throws CdtVariableException {
		if (CdtVariableResolver.isStringListVariable(fType))
			throw new CdtVariableException(ICdtVariableStatus.TYPE_MACRO_NOT_STRING, fName, null, fName);

		return fStringValue;
	}

	@Override
	public String[] getStringListValue() throws CdtVariableException {
		if (!CdtVariableResolver.isStringListVariable(fType))
			throw new CdtVariableException(ICdtVariableStatus.TYPE_MACRO_NOT_STRINGLIST, fName, null, fName);

		return fStringListValue;
	}

	@Override
	public String toString() {
		try {
			if (CdtVariableResolver.isStringListVariable(fType))
				return getName() + "=" + Arrays.toString(getStringListValue()); //$NON-NLS-1$
			else
				return getName() + "=" + getStringValue(); //$NON-NLS-1$
		} catch (CdtVariableException e) {
		}
		return ""; //$NON-NLS-1$
	}

}
