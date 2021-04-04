/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package model.edit;

import java.util.Date;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import model.util.CMTimeStampBean;

/**
 * @author smoreno
 *
 */
public class CMChangeTimeStampModelEdit extends AbstractUndoableEdit implements
		CMModelUndoableEdit {

	private CMTimeStampBean bean;
	private Date date;
	private Date oldDate;

	/**
	 * @param p_p_date
	 * @param p_p_bean
	 *
	 */
	public CMChangeTimeStampModelEdit(CMTimeStampBean p_bean, Date p_date) {
		super();
		this.bean = p_bean;
		this.date = p_date;
		this.oldDate = p_bean.getTimeStamp();
	}
/* (non-Javadoc)
 * @see javax.swing.undo.AbstractUndoableEdit#undo()
 */
@Override
public void undo() throws CannotUndoException {
	super.undo();
	this.bean.setTimeStamp(oldDate);
}
/* (non-Javadoc)
 * @see javax.swing.undo.AbstractUndoableEdit#redo()
 */
@Override
public void redo() throws CannotRedoException {
	super.redo();
	this.bean.setTimeStamp(date);
}
public Object getModifiedObject() {

	return bean;
}

}
