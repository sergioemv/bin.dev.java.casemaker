/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package model.edit;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import model.util.CMUserOrderBean;

/**
 * @author smoreno
 *
 */
public class CMChangeUserOrderModelEdit extends AbstractUndoableEdit implements
		CMModelUndoableEdit {

	private CMUserOrderBean bean;
	private int order;
	private int oldOrder;

	/**
	 * @param p_element
	 * @param p_order
	 */
	public CMChangeUserOrderModelEdit(CMUserOrderBean p_element, int p_order) {
		this.bean = p_element;
		this.order = p_order;
		this.oldOrder = this.bean.getUserOrder();
	}
/* (non-Javadoc)
 * @see javax.swing.undo.AbstractUndoableEdit#undo()
 */
@Override
public void undo() throws CannotUndoException {
	super.undo();
	this.bean.setUserOrder(this.oldOrder);
}
/* (non-Javadoc)
 * @see javax.swing.undo.AbstractUndoableEdit#redo()
 */
@Override
public void redo() throws CannotRedoException {
	super.redo();
	this.bean.setUserOrder(this.order);
}
public Object getModifiedObject() {
	return bean;
}

}
