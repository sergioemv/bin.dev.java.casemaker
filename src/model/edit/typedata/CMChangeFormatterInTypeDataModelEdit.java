/**
 *
 */
package model.edit.typedata;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import model.ITypeData;
import model.TestDataFormat;

/**
 * @author svonborries
 *
 */
@SuppressWarnings("serial")
public class CMChangeFormatterInTypeDataModelEdit extends AbstractUndoableEdit {

	private ITypeData m_typeData;
	private TestDataFormat m_oldFormatter;
	private TestDataFormat m_newFormatter;

	public CMChangeFormatterInTypeDataModelEdit (ITypeData p_typeData, TestDataFormat p_Formatter){
		this.m_typeData = p_typeData;
		this.m_oldFormatter = p_typeData.getFormatter();
		this.m_newFormatter = p_Formatter;
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#canRedo()
	 */
	@Override
	public boolean canRedo() {
		if((this.m_typeData!= null))
			return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#canUndo()
	 */
	@Override
	public boolean canUndo() {
		if((this.m_typeData!= null))
			return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#redo()
	 */
	@Override
	public void redo() throws CannotRedoException {
		super.redo();
		this.m_typeData.setFormatter(this.m_newFormatter);
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#undo()
	 */
	@Override
	public void undo() throws CannotUndoException {
		super.undo();
		this.m_typeData.setFormatter(this.m_oldFormatter);
	}

}
