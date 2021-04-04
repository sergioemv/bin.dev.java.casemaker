/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved. 
 */
package model.edit;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import model.util.CMDelegate;
import model.util.CMNameBean;

/**
 * @author smoreno
 *
 */
public class CMChangeNameModelEdit extends AbstractUndoableEdit implements CMModelUndoableEdit{
private CMNameBean element;
private String name;
private String oldName;
private CMDelegate delegate;

/* (non-Javadoc)
 * @see javax.swing.undo.AbstractUndoableEdit#canRedo()
 */


	/**
	 * @param p_element
	 * @param p_name
	 */
	public CMChangeNameModelEdit(CMNameBean p_element, String p_name) {
		element = p_element;
		oldName = element.getName(); 
		name = p_name;
	}
  /**
	 * @param p_p_bean
	 * @param p_newName
	 * @param p_delegate
	 */
	public CMChangeNameModelEdit(CMNameBean p_bean, String p_newName, CMDelegate p_delegate) {
		element = p_bean;
		oldName = element.getName(); 
		name = p_newName;
		delegate = p_delegate;
	}
/* (non-Javadoc)
 * @see javax.swing.undo.AbstractUndoableEdit#undo()
 */
@Override
public void undo() throws CannotUndoException {
	
	super.undo();
	element.setName(oldName);
	if (delegate!=null)
		delegate.execute();
}	
/* (non-Javadoc)
 * @see javax.swing.undo.AbstractUndoableEdit#redo()
 */
@Override
public void redo() throws CannotRedoException {
	super.redo();
	element.setName(name);
	if (delegate!=null)
		delegate.execute();
}
public Object getModifiedObject() {
	
	return element;
}
}
