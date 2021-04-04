/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package model.edit;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import model.util.CMOriginTypeBean;
import model.util.CMOriginTypeBean.Origin;

/**
 * @author smoreno
 *
 */
public class CMChangeOriginTypeModelEdit extends AbstractUndoableEdit implements
		CMModelUndoableEdit {

	private CMOriginTypeBean originTypeBean;
	private Origin origin;
	private Origin oldOrigin;

	/**
	 * @param p_bean
	 * @param p_origin
	 */
	public CMChangeOriginTypeModelEdit(CMOriginTypeBean p_bean, Origin p_origin) {

		this.originTypeBean = p_bean;
		this.origin = p_origin;
		this.oldOrigin = originTypeBean.getOriginType();
	}
/* (non-Javadoc)
 * @see javax.swing.undo.AbstractUndoableEdit#undo()
 */
@Override
public void undo() throws CannotUndoException {
	super.undo();
	this.originTypeBean.setOriginType(oldOrigin);
}
/* (non-Javadoc)
 * @see javax.swing.undo.AbstractUndoableEdit#redo()
 */
@Override
public void redo() throws CannotRedoException {
	super.redo();
	this.originTypeBean.setOriginType(origin);
}
public Object getModifiedObject() {

	return originTypeBean;
}

}
