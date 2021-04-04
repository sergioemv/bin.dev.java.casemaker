package model.edit.combination;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoableEdit;

import model.Combination;

public class CMCombinationChangeCombinationParent extends AbstractUndoableEdit
		implements UndoableEdit {
	private Combination m_CombinationParentOld;
	private Combination m_CombinationParent;
	private Combination m_CombinationChild;
	
	public CMCombinationChangeCombinationParent(Combination p_Combination,Combination p_CombinationParent) {
		this.m_CombinationParent = p_CombinationParent;
		this.m_CombinationChild = p_Combination;
		this.m_CombinationParentOld = p_Combination.getLnkCombinationParent();
	}
	
	public void undo() throws CannotUndoException {
		
		super.undo();
	
		m_CombinationChild.setLnkCombinationParent(m_CombinationParentOld);
	}
	public void redo() throws CannotRedoException {
		super.redo();
		this.m_CombinationParentOld = m_CombinationChild.getLnkCombinationParent();
		this.m_CombinationChild.setLnkCombinationParent(m_CombinationParent);
	}


}
