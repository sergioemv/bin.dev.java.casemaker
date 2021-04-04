/**
 *
 */
package model.edit.typedata;

import java.util.Vector;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import model.ITypeData;

/**
 * @author svonborries
 *
 */
@SuppressWarnings("serial")
public class CMChangeNewColumnInTypeDataModelEdit extends AbstractUndoableEdit {

	private ITypeData m_typeData;
	private String m_newValue;
	private String m_oldValue;
	private int m_Column;

	public CMChangeNewColumnInTypeDataModelEdit(ITypeData p_typeData, String p_Value, int p_Column){
		this.m_typeData = p_typeData;
		this.m_Column = p_Column;
		int i= p_Column-5;
        Vector columns = new Vector();
        columns = (Vector) p_typeData.getNewColumns().clone();
        m_oldValue = (String) columns.get(i);
        this.m_newValue = p_Value;
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#canRedo()
	 */
	@Override
	public boolean canRedo() {
		return true;
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#canUndo()
	 */
	@Override
	public boolean canUndo() {
		return true;
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#redo()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void redo() throws CannotRedoException {
		super.redo();
		super.undo();
    	int i= m_Column-5;
        Vector columns = new Vector();
        columns = (Vector) m_typeData.getNewColumns().clone();
        columns.setElementAt((String)m_newValue,i);

        m_typeData.setNewColumns(columns);
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#undo()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void undo() throws CannotUndoException {
		super.undo();
    	int i= m_Column-5;
        Vector columns = new Vector();
        columns = (Vector) m_typeData.getNewColumns().clone();
        columns.setElementAt((String)m_oldValue,i);

        m_typeData.setNewColumns(columns);
	}





}
