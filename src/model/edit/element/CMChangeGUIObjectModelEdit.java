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

/**
 * @author smoreno
 *
 */
public class CMChangeGUIObjectModelEdit extends AbstractUndoableEdit implements
		CMModelUndoableEdit {

	private Element element;
	private int newGUIObject, oldGUIObject;


	/**
	 * @param p_bean
	 * @param p_element
	 */
	public CMChangeGUIObjectModelEdit( Element p_element, int GUIObject) {

		element  = p_element;
		this.newGUIObject = GUIObject;
		this.oldGUIObject = element.getGUIObject();
	}
/* (non-Javadoc)
 * @see javax.swing.undo.AbstractUndoableEdit#redo()
 */
@Override
public void redo() throws CannotRedoException {
	super.redo();
	element.setGUIObject(newGUIObject);

}
/* (non-Javadoc)
 * @see javax.swing.undo.AbstractUndoableEdit#undo()
 */
@Override
public void undo() throws CannotUndoException {
	// TODO Auto-generated method stub
	super.undo();
	element.setGUIObject(oldGUIObject);
}
public Object getModifiedObject() {
	return  element;
}
}
