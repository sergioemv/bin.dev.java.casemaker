/**
 * Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package model.edit.testdata;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import model.TestData;

/**
 * @author svonborries
 *
 */
@SuppressWarnings("serial")
public class CMTestDataEditRiskLevelModelEdit extends AbstractUndoableEdit {

	private TestData m_testdata;
	private int m_newRisk;
	private int m_oldRisk;
	
	public CMTestDataEditRiskLevelModelEdit(TestData p_testdata, int m_riskLevel){
		this.m_testdata = p_testdata;
		this.m_newRisk = m_riskLevel;
		this.m_oldRisk = m_testdata.getM_RiskLevel();
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#canRedo()
	 */
	@Override
	public boolean canRedo() {
		if(m_testdata!=null)
			return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#canUndo()
	 */
	@Override
	public boolean canUndo() {
		if(m_testdata!=null)
			return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#redo()
	 */
	@Override
	public void redo() throws CannotRedoException {
		super.redo();
		this.m_testdata.setM_RiskLevel(this.m_newRisk);
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#undo()
	 */
	@Override
	public void undo() throws CannotUndoException {
		super.undo();
		this.m_testdata.setM_RiskLevel(this.m_oldRisk);
	}

}
