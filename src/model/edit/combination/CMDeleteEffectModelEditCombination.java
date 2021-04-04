package model.edit.combination;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import model.Effect;
import model.edit.CMModelUndoableEdit;
import model.util.CMEffectsBean;

public class CMDeleteEffectModelEditCombination extends AbstractUndoableEdit
		implements CMModelUndoableEdit {

	private CMEffectsBean effectBean;
	private Effect m_Effect;

	public CMDeleteEffectModelEditCombination(CMEffectsBean combination, Effect effect) {
		effectBean = combination;
		m_Effect = effect;

	}
public boolean canRedo() {
	return (effectBean.getEffects().contains(m_Effect));
}
public boolean canUndo() {
	return (!effectBean.getEffects().contains(m_Effect));
}
public void undo() throws CannotUndoException {
	super.undo();

		effectBean.addEffect(m_Effect);
}
public void redo() throws CannotRedoException {
	super.redo();
	effectBean.removeEffect(m_Effect);
}
public Object getModifiedObject() {
	return effectBean;
}
}
