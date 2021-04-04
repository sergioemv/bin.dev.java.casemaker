/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package model.edit.requirement;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import model.Requirement;
import model.edit.CMModelUndoableEdit;
import model.util.CMRequirementBean;

/**
 * @author smoreno
 *
 */
public class CMAddRequirementModelEdit extends AbstractUndoableEdit implements
		CMModelUndoableEdit {

	private CMRequirementBean reqBean;
	private Requirement req;
	public CMAddRequirementModelEdit(CMRequirementBean p_reqBean, Requirement p_req) {
		super();
		// TODO Auto-generated constructor stub
		this.reqBean = p_reqBean;
		this.req = p_req;
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#canRedo()
	 */
	@Override
	public boolean canRedo() {
		// TODO Auto-generated method stub
		return !reqBean.getRequirements().contains(req);
	}
	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#undo()
	 */
	@Override
	public void undo() throws CannotUndoException {
		super.undo();
		reqBean.removeRequirement(req);
	}
	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#redo()
	 */
	@Override
	public void redo() throws CannotRedoException {
		// TODO Auto-generated method stub
		super.redo();
		reqBean.addRequirement(req);
	}

	public Object getModifiedObject() {
		return reqBean;
	}
}
