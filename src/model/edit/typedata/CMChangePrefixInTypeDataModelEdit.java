package model.edit.typedata;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import model.ITypeData;

/**
 * @author svonborries
 * @since 21/02/2006
 */
@SuppressWarnings("serial")
public class CMChangePrefixInTypeDataModelEdit extends AbstractUndoableEdit {

	private String m_oldPrefix;
	private String m_newPrefix;
	private ITypeData m_TypeData;

	public CMChangePrefixInTypeDataModelEdit (ITypeData p_TypeData,String p_Value){
		this.m_TypeData = p_TypeData;
		this.m_oldPrefix = p_TypeData.getPrefix();
		this.m_newPrefix = p_Value;
	}

	@Override
	public boolean canRedo() {
		if((this.m_TypeData != null))
			return true;
		return false;
	}

	@Override
	public boolean canUndo() {
		if((this.m_TypeData != null))
			return true;
		return false;
	}

	@Override
	public void redo() throws CannotRedoException {
		super.redo();
		this.m_TypeData.setPrefix(this.m_newPrefix);
	}

	@Override
	public void undo() throws CannotUndoException {
		super.undo();
		this.m_TypeData.setPrefix(this.m_oldPrefix);
	}


}
