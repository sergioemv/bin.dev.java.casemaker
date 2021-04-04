/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package model.edit.effect;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import model.Effect;
import model.edit.CMModelUndoableEdit;
import model.util.CMEffectsBean;

/**
 * @author smoreno
 *
 */
public class CMAddEffectNotifiedModelEdit extends AbstractUndoableEdit
implements CMModelUndoableEdit {
	private Effect m_Effect;
	private CMEffectsBean m_model;
	public CMAddEffectNotifiedModelEdit(CMEffectsBean p_model,Effect p_effect) {
		this.m_Effect = p_effect;
		this.m_model = p_model;
	}
	public void undo() throws CannotUndoException {
		super.undo();
		m_model.removeEffect(m_Effect);
	}
	public void redo() throws CannotRedoException {
		super.redo();
		this.m_model.addEffect(m_Effect);
	}
	public boolean canRedo() {
		return (!m_model.getEffects().contains(m_Effect));
	}
	public Object getModifiedObject() {
		return m_model;
	}

}
