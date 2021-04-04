package model.edit.structuretestdata;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import model.ITypeData;
import model.StructureTestData;

@SuppressWarnings("serial")
public class CMAddTypeDataToStructureTDModelEdit extends AbstractUndoableEdit {

	private StructureTestData m_structure;
	private ITypeData m_typeData;

	public CMAddTypeDataToStructureTDModelEdit(StructureTestData structureTD, ITypeData newTypeData2) {
		m_structure = structureTD;
		m_typeData = newTypeData2;
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#canRedo()
	 */
	@Override
	public boolean canRedo() {
		/*if((m_structure!= null) && (m_typeData!=null))
			return true;
		return false;*/
		return true;
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#canUndo()
	 */
	@Override
	public boolean canUndo() {
		/*if((m_structure!= null) && (m_typeData!=null))
			return true;
		return false;*/
		return true;
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#redo()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void redo() throws CannotRedoException {
		super.redo();
		m_structure.getTypeData().addElement(m_typeData);
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#undo()
	 */
	@Override
	public void undo() throws CannotUndoException {
		super.undo();
		m_structure.getTypeData().removeElement(m_typeData);
	}
	
	
}
