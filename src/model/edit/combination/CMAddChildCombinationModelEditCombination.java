package model.edit.combination;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoableEdit;

import model.Combination;

public class CMAddChildCombinationModelEditCombination extends
		AbstractUndoableEdit implements UndoableEdit {
	private int index = -1;
	private Combination m_CombinationChild;
	private Combination m_Combination;

	public CMAddChildCombinationModelEditCombination(Combination p_Combination,Combination p_CombinationChild) {
		this.m_CombinationChild = p_CombinationChild;
		this.m_Combination = p_Combination;
		if (p_Combination.getCombinations().contains(p_CombinationChild))
			this.index = p_Combination.getCombinations().indexOf(p_CombinationChild);
	}
	public boolean canUndo() {

		return (m_Combination.getCombinations().contains(m_CombinationChild));
	}
	public boolean canRedo() {

		return (!m_Combination.getCombinations().contains(m_CombinationChild));
	}
	public void undo() throws CannotUndoException {

		super.undo();
		this.index = m_Combination.getCombinations().indexOf(m_CombinationChild);
		m_Combination.removeCombination(m_CombinationChild);
	}
	public void redo() throws CannotRedoException {
		super.redo();
		if (index >=0 && (index <  	m_Combination.getCombinations().size()))
			m_Combination.getCombinations().set(index,m_CombinationChild);
		else
			m_Combination.addCombination(m_CombinationChild);

	}
}
