/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package model.edit;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import model.util.CMUserBean;

/**
 * @author smoreno
 *
 */
public class CMChangeUserModelEdit extends AbstractUndoableEdit implements
		CMModelUndoableEdit {

	private CMUserBean userBean;
	private String user;
	private String oldUser;

	/**
	 * @param p_user
	 * @param p_bean
	 *
	 */
	public CMChangeUserModelEdit(CMUserBean p_bean, String p_user) {
		super();
		// TODO Auto-generated constructor stub
		this.userBean = p_bean;
		this.user = p_user;
		this.oldUser = p_bean.getUser();
		}
 /* (non-Javadoc)
 * @see javax.swing.undo.AbstractUndoableEdit#undo()
 */
@Override
public void undo() throws CannotUndoException {
	// TODO Auto-generated method stub
	super.undo();
	userBean.setUser(oldUser);
}
/* (non-Javadoc)
 * @see javax.swing.undo.AbstractUndoableEdit#redo()
 */
@Override
public void redo() throws CannotRedoException {
	// TODO Auto-generated method stub
	super.redo();
	userBean.setUser(user);
}
public Object getModifiedObject() {
	return userBean;
}
}
