/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package model.edit;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import model.util.CMIdBean;

/**
 * @author smoreno
 *
 */
public class CMChangeIdModelEdit extends AbstractUndoableEdit implements
		CMModelUndoableEdit {
	private int oldId;
	private int id;
	private CMIdBean model;
/**
	 * @param p_idBean
	 * @param p_i
	 */
	public CMChangeIdModelEdit(CMIdBean p_idBean, int p_i) {
		model = p_idBean;
		id = p_i;
		oldId = model.getId();
	}
	@Override
	public void undo() throws CannotUndoException {

		super.undo();
		model.setId(oldId);
	}
	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#redo()
	 */
	@Override
	public void redo() throws CannotRedoException {
		super.redo();
		model.setId(id);
	}
	public Object getModifiedObject() {
		return model;
	}


}
