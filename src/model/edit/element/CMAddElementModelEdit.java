/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package model.edit.element;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import model.Element;
import model.edit.CMModelUndoableEdit;
import model.util.CMElementsBean;

/**
 * @author smoreno
 *
 */
public class CMAddElementModelEdit extends AbstractUndoableEdit implements
		CMModelUndoableEdit {

	private CMElementsBean bean;
	private Element element;
	private int oldPosition;

	/**
	 * @param p_bean
	 * @param p_element
	 */
	public CMAddElementModelEdit(CMElementsBean p_bean, Element p_element) {
		// TODO Auto-generated constructor stub
		bean = p_bean;
		element  = p_element;
		oldPosition = p_bean.getElements().indexOf(p_element);
	}
/* (non-Javadoc)
 * @see javax.swing.undo.AbstractUndoableEdit#redo()
 */
@Override
public void redo() throws CannotRedoException {
	super.redo();
	bean.addElement(element, oldPosition);
	
}
/* (non-Javadoc)
 * @see javax.swing.undo.AbstractUndoableEdit#undo()
 */
@Override
public void undo() throws CannotUndoException {
	// TODO Auto-generated method stub
	super.undo();
	bean.removeElement(element);
}
public Object getModifiedObject() {
	return bean;
}
}
