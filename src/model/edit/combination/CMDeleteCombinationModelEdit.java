package model.edit.combination;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import model.Combination;
import model.edit.CMModelUndoableEdit;
import model.util.CMCombinationsBean;

public class CMDeleteCombinationModelEdit extends
		AbstractUndoableEdit implements CMModelUndoableEdit {
	private CMCombinationsBean m_Dependency;
	private Combination m_Combination;

	public CMDeleteCombinationModelEdit(CMCombinationsBean p_Dependency,Combination p_Combination) {
		this.m_Dependency = p_Dependency;
		this.m_Combination = p_Combination;

	}
	public void undo() throws CannotUndoException {
		super.undo();
		m_Dependency.addCombination(m_Combination);
	}
	public void redo() throws CannotRedoException {
		super.redo();
		m_Dependency.removeCombination(m_Combination);


	}
	public Object getModifiedObject() {
		return m_Dependency;
	}

}
