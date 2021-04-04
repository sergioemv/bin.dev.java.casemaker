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
public class CMChangeisFormulaInTypeDataModelEdit extends AbstractUndoableEdit {

	//private boolean m_newValue;
	//private boolean m_oldValue;
	private ITypeData m_typeData;

	public CMChangeisFormulaInTypeDataModelEdit(ITypeData p_typeData, boolean p_value){

		this.m_typeData = p_typeData;
		//if(this.m_typeData.isFormula())
			//this.m_oldValue = true;
		//else this.m_oldValue = false;
		//this.m_newValue = p_value;
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#canRedo()
	 */
	@Override
	public boolean canRedo() {
		if(m_typeData!=null)
			return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#canUndo()
	 */
	@Override
	public boolean canUndo() {
		if(m_typeData!=null)
			return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#redo()
	 */
	@Override
	public void redo() throws CannotRedoException {
		super.redo();
		//this.m_typeData.setisFormula(this.m_newValue);
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#undo()
	 */
	@Override
	public void undo() throws CannotUndoException {
		super.undo();
		//this.m_typeData.setisFormula(this.m_oldValue);
	}





}
