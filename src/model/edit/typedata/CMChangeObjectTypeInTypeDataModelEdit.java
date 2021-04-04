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
public class CMChangeObjectTypeInTypeDataModelEdit extends AbstractUndoableEdit {
	private ITypeData m_typeData;
	/*private String m_newObjectType;
	private String m_oldObjectType;*///ccastedo 27.09.06
	private int m_newObjectType;
	private int m_oldObjectType;

	public CMChangeObjectTypeInTypeDataModelEdit(ITypeData p_typeData, int p_Value/* ccastedo 27.09.06 String p_Value*/){
		this.m_typeData = p_typeData;
		/*this.m_oldObjectType = p_typeData.getToolVendorOT();
		this.m_newObjectType = p_Value;*///ccastdeo 27.09.06
		this.m_oldObjectType = p_typeData.getStateOT();
		this.m_newObjectType = p_Value;
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#canRedo()
	 */
	@Override
	public boolean canRedo() {
		if((this.m_typeData !=null))
			return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#canUndo()
	 */
	@Override
	public boolean canUndo() {
		if((this.m_typeData !=null))
			return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#redo()
	 */
	@Override
	public void redo() throws CannotRedoException {
		super.redo();
		//ccastedo 27.09.06 this.m_typeData.setToolVendorOT(this.m_newObjectType);
		this.m_typeData.setStateOT(this.m_newObjectType);
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#undo()
	 */
	@Override
	public void undo() throws CannotUndoException {
		super.undo();
		//ccastedo 27.09.06  this.m_typeData.setToolVendorOT(this.m_oldObjectType);
		this.m_typeData.setStateOT(this.m_oldObjectType);
	}



}
