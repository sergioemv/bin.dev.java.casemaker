/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved. 
 */
package model.edit;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import model.util.CMRiskLevelBean;

/**
 * @author smoreno
 *
 */
public class CMChangeRiskLevelModelEdit extends AbstractUndoableEdit implements
		CMModelUndoableEdit {
private CMRiskLevelBean model;
private int level;
private int oldLevel;

/**
	 * @param p_model
	 * @param p_level
	 */
	public CMChangeRiskLevelModelEdit(CMRiskLevelBean p_model, int p_level) {
		
		model = p_model;
		oldLevel = model.getRiskLevel();
		level = p_level;
	}

/* (non-Javadoc)
 * @see javax.swing.undo.AbstractUndoableEdit#undo()
 */
@Override
public void undo() throws CannotUndoException {
	// TODO Auto-generated method stub
	super.undo();
	model.setRiskLevel(oldLevel);
}
/* (non-Javadoc)
 * @see javax.swing.undo.AbstractUndoableEdit#redo()
 */
@Override
public void redo() throws CannotRedoException {
	// TODO Auto-generated method stub
	super.redo();
	model.setRiskLevel(level);
}

public Object getModifiedObject() {
	
	return model;
}
}
