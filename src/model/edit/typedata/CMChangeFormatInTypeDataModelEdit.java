/**
 *
 */
package model.edit.typedata;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import model.ITypeData;

/**
 * @author svonborries
 *
 */
@SuppressWarnings("serial")
public class CMChangeFormatInTypeDataModelEdit extends AbstractUndoableEdit {

	private ITypeData m_typeData;
	private String m_newFormat;
	private String m_oldFormat;

	public CMChangeFormatInTypeDataModelEdit(ITypeData p_typeData, String p_Value){
		this.m_typeData = p_typeData;
		this.m_oldFormat = p_typeData.getFormat();
		this.m_newFormat = p_Value;
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#canRedo()
	 */
	@Override
	public boolean canRedo() {
		if((this.m_typeData != null))
			return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#canUndo()
	 */
	@Override
	public boolean canUndo() {
		if((this.m_typeData != null))
			return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#redo()
	 */
	@Override
	public void redo() throws CannotRedoException {
		super.redo();
		this.m_typeData.setFormat(this.m_newFormat);
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#undo()
	 */
	@Override
	public void undo() throws CannotUndoException {
		super.undo();
		this.m_typeData.setFormat(this.m_oldFormat);
	}


}
