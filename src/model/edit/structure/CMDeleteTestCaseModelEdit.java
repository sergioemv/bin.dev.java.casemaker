package model.edit.structure;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import model.Structure;
import model.TestCase;
import model.edit.CMModelUndoableEdit;

/**
 * @author smoreno
 *  Class that registers the deletion of a test case from a structure, no view objects are
 *  referenced in this class
 */
public class CMDeleteTestCaseModelEdit extends AbstractUndoableEdit implements CMModelUndoableEdit{
	private TestCase m_TestCase;
	private Structure m_Structure;

	public CMDeleteTestCaseModelEdit(Structure p_Structure, TestCase p_TestCase) {
		this.m_TestCase = p_TestCase;
		this.m_Structure = p_Structure;

	}

	public void redo() throws CannotRedoException {
		super.redo();
		//save the current index
		m_Structure.removeTestCase(m_TestCase);

	}
	public void undo() throws CannotUndoException {
		super.undo();

		m_Structure.addTestCase(m_TestCase);
	}
	public boolean canRedo() {
		return m_Structure.getLnkTestCases().contains(m_TestCase);
	}
	public boolean canUndo() {

		return !m_Structure.getLnkTestCases().contains(m_TestCase);
	}

	public Object getModifiedObject() {
		// TODO Auto-generated method stub
		return m_Structure;
	}
}
