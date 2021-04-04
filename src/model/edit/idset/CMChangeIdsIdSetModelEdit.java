/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package model.edit.idset;

import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import model.edit.CMModelUndoableEdit;
import model.util.IdSet;

/**
 * @author smoreno
 *
 */
public class CMChangeIdsIdSetModelEdit extends AbstractUndoableEdit implements
		CMModelUndoableEdit {
private Set<Integer> ids;
private Set<Integer> oldids;
private IdSet idSet;
/**
	 * @param p_set
	 * @param p_vector
	 */
	public CMChangeIdsIdSetModelEdit(IdSet p_set, Vector<Integer> p_newIds) {
		if (p_newIds == null) p_newIds = new Vector<Integer>();
	   this.ids = new TreeSet<Integer>(p_newIds);
	   this.oldids = p_set.getIds();
	   this.idSet = p_set;
	}
/* (non-Javadoc)
 * @see javax.swing.undo.AbstractUndoableEdit#undo()
 */
@Override
public void undo() throws CannotUndoException {
	super.undo();
	this.idSet.deleteIds();
	for (Integer id : oldids)
		this.idSet.registerId(id);

}
/* (non-Javadoc)
 * @see javax.swing.undo.AbstractUndoableEdit#redo()
 */
@Override
public void redo() throws CannotRedoException {
	super.redo();
	this.idSet.deleteIds();
	for (Integer id : ids)
		this.idSet.registerId(id);
}
public Object getModifiedObject() {
	return idSet;
}
}
