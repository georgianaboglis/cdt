/*******************************************************************************
 * Copyright (c) 2007 Intel Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Intel Corporation - Initial API and implementation
 *******************************************************************************/
package org.eclipse.cdt.managedbuilder.tcmodification;

import org.eclipse.core.runtime.CoreException;


public interface IApplicableModification extends IModification {
	/**
	 * applies the settings made to the given modification to the underlying
	 * resource info 
	 * @throws CoreException
	 */
	void apply() throws CoreException;
}
