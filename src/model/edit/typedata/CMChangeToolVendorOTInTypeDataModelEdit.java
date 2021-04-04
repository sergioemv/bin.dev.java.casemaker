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
public class CMChangeToolVendorOTInTypeDataModelEdit extends
		AbstractUndoableEdit {

	private ITypeData m_typeData;
	/*private String m_newToolVendor;
	private String m_oldToolVendor;*/
	private int m_newStateOT;
	private int m_oldStateOT;

	public CMChangeToolVendorOTInTypeDataModelEdit(ITypeData p_typeData, int p_stateToolVendorOT/*String p_toolVendor*/){
		this.m_typeData = p_typeData;
		//ccastedo 27.09.06 this.m_oldToolVendor = p_typeData.getToolVendorOT();
		//ccastedo 27.09.06 this.m_newToolVendor = p_toolVendor;
		this.m_oldStateOT = p_typeData.getStateOT();
		this.m_newStateOT = p_stateToolVendorOT;
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#canRedo()
	 */
	@Override
	public boolean canRedo() {
		if ((m_typeData!=null))
			return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#canUndo()
	 */
	@Override
	public boolean canUndo() {
		if ((m_typeData!=null))
			return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#redo()
	 */
	@Override
	public void redo() throws CannotRedoException {
		super.redo();
		//ccastedo 27.09.06 this.m_typeData.setToolVendorOT(this.m_newToolVendor);
		this.m_typeData.setStateOT(this.m_newStateOT);
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#undo()
	 */
	@Override
	public void undo() throws CannotUndoException {
		super.undo();
		//		ccastedo 27.09.06 this.m_typeData.setToolVendorOT(this.m_oldToolVendor);
		this.m_typeData.setStateOT(this.m_oldStateOT);
	}


}
