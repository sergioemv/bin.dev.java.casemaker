package model.edit;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import model.Type;
import model.util.CMTypeBean;
/**
 * @author ccastedo
 *
 */
@SuppressWarnings("serial")
public class CMChangeTypeModelEdit extends AbstractUndoableEdit
		implements CMModelUndoableEdit {
	private Type m_TypeOld;
	private Type m_Type;
	private CMTypeBean m_model;

	public CMChangeTypeModelEdit(CMTypeBean p_model, Type p_newType) {
		this.m_Type = p_newType;
		this.m_model = p_model;
		this.m_TypeOld = Type.values()[p_model.getTypeIndex()];
	}
	public void undo() throws CannotUndoException {

		super.undo();
		m_model.setType(m_TypeOld);
	}
	public void redo() throws CannotRedoException {
		super.redo();
		this.m_TypeOld = Type.values()[ m_model.getTypeIndex()];
		this.m_model.setType(m_Type);
	}
	public Object getModifiedObject() {
		return m_model;
	}


}
