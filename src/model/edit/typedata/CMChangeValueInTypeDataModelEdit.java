/**
 *
 */
package model.edit.typedata;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import model.ICMValue;
import model.ITypeData;

/**
 * @author svonborries
 *
 */
@SuppressWarnings("serial")
public class CMChangeValueInTypeDataModelEdit extends AbstractUndoableEdit {

	private ITypeData m_typeData;
	private ICMValue m_newValue;
	private ICMValue m_oldValue;

	public CMChangeValueInTypeDataModelEdit(ITypeData p_typeData, ICMValue p_Value){
		this.m_typeData = p_typeData;
		this.m_oldValue = p_typeData.getValue();
		this.m_newValue = p_Value;
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#canRedo()
	 */
	@Override
	public boolean canRedo() {
		if((this.m_typeData!=null))
			return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#canUndo()
	 */
	@Override
	public boolean canUndo() {
		if((this.m_typeData!=null))
			return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#redo()
	 */
	@Override
	public void redo() throws CannotRedoException {
		super.redo();
		this.m_typeData.setValue(this.m_newValue);
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#undo()
	 */
	@Override
	public void undo() throws CannotUndoException {
		super.undo();
		this.m_typeData.setValue(this.m_oldValue);
	}



}
