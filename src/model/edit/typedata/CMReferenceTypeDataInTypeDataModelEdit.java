/**
 * Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package model.edit.typedata;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import model.ITypeData;
import model.TypeDataLocal;

/**
 * @author svonborries
 *
 */
@SuppressWarnings("serial")
public class CMReferenceTypeDataInTypeDataModelEdit extends
		AbstractUndoableEdit {

	private ITypeData m_referencedTypeData;
	private ITypeData m_referenceTypeData;

	public CMReferenceTypeDataInTypeDataModelEdit(ITypeData p_typedatalocal, ITypeData p_typedataglobal){

		this.m_referencedTypeData = p_typedatalocal;
		this.m_referenceTypeData = p_typedataglobal;

	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#canRedo()
	 */
	@Override
	public boolean canRedo() {
		if((m_referencedTypeData!=null)&&(m_referenceTypeData!=null))
			return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#canUndo()
	 */
	@Override
	public boolean canUndo() {
		if((m_referencedTypeData!=null)&&(m_referenceTypeData!=null))
			return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#redo()
	 */
	@Override
	public void redo() throws CannotRedoException {
		super.redo();
		((TypeDataLocal)this.m_referencedTypeData).setM_ReferenceTypeData(m_referenceTypeData);
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#undo()
	 */
	@Override
	public void undo() throws CannotUndoException {
		super.undo();
		((TypeDataLocal)this.m_referencedTypeData).setM_ReferenceTypeData(null);
	}



}
