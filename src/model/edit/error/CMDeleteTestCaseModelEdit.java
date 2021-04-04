package model.edit.error;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import model.CMError;
import model.TestCase;
import model.edit.CMModelUndoableEdit;

public class CMDeleteTestCaseModelEdit extends AbstractUndoableEdit
		implements CMModelUndoableEdit {
	private CMError m_error;
	private TestCase m_TestCase;
	private int index = -1; 
	public CMDeleteTestCaseModelEdit(CMError error, TestCase testCase) {
		this.m_error = error;
		this.m_TestCase = testCase;
		if (m_error.getM_TestCases().contains(m_TestCase))
			index  = m_error.getM_TestCases().indexOf(m_TestCase);
	}
	public boolean canUndo() {
		
		return (!m_error.getM_TestCases().contains(m_TestCase));
	}
	public boolean canRedo() {
		return (m_error.getM_TestCases().contains(m_TestCase));
	}
	public void undo() throws CannotUndoException {
		super.undo();
		if (index>=0)
			m_error.getM_TestCases().insertElementAt(m_TestCase, index);
		else
			m_error.getM_TestCases().add(m_TestCase);
	}
	public void redo() throws CannotRedoException {
		
		super.redo();
		index  = m_error.getM_TestCases().indexOf(m_TestCase);
		m_error.getM_TestCases().remove(m_TestCase);
	}
	public Object getModifiedObject() {
		// TODO Auto-generated method stub
		return m_error;
	}
}
