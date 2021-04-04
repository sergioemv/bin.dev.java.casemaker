package model.edit;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import model.State;
import model.util.CMStateBean;

public class CMChangeStateModelEdit extends AbstractUndoableEdit
		implements CMModelUndoableEdit {
	private State m_StateOld;
	private State m_State;
	private CMStateBean m_model;

	public CMChangeStateModelEdit(CMStateBean p_model,State p_newState) {
		this.m_State = p_newState;
		this.m_model = p_model;
		this.m_StateOld = State.values()[p_model.getState()];
	}
	public void undo() throws CannotUndoException {

		super.undo();
		m_model.setState(m_StateOld);
	}
	public void redo() throws CannotRedoException {
		super.redo();
		this.m_StateOld = State.values()[ m_model.getState()];
		this.m_model.setState(m_State);
	}
	public Object getModifiedObject() {
		return m_model;
	}


}
