/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved. 
 */
package model.edit;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import model.util.CMAccessStateBean;

/**
 * @author smoreno
 *
 */
public class CMChangeAccessStateModelEdit extends AbstractUndoableEdit
		implements CMModelUndoableEdit {

	private String accessState;
	private String oldAccessState;
	private CMAccessStateBean stateBean;
	/**
	 * @param p_p_access_state 
	 * @param p_bean 
	 * 
	 */
	public CMChangeAccessStateModelEdit(CMAccessStateBean p_bean, String p_access_state) {
		super();
		accessState = p_access_state;
		stateBean = p_bean;
		oldAccessState = stateBean.getAccessState();
	}
/* (non-Javadoc)
 * @see javax.swing.undo.AbstractUndoableEdit#undo()
 */
@Override
public void undo() throws CannotUndoException {
	// TODO Auto-generated method stub
	super.undo();
	stateBean.setAccessState(oldAccessState);
}
/* (non-Javadoc)
 * @see javax.swing.undo.AbstractUndoableEdit#redo()
 */
@Override
public void redo() throws CannotRedoException {
	// TODO Auto-generated method stub
	super.redo();
	stateBean.setAccessState(accessState);
}
public Object getModifiedObject() {
	// TODO Auto-generated method stub
	return stateBean;
}
}
