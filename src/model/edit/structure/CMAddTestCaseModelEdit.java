package model.edit.structure;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import model.Structure;
import model.TestCase;
import model.util.TestCasesBean;

/**
 * @author smoreno
 *  Class that registers the creation of a test case in a structure, no view objects are
 *  referenced in this class
 */
public class CMAddTestCaseModelEdit extends AbstractUndoableEdit {
	private TestCase m_TestCase;
	private TestCasesBean m_Structure;
	public CMAddTestCaseModelEdit(TestCasesBean bean, TestCase p_TestCase) {
		this.m_TestCase = p_TestCase;
		this.m_Structure = bean;

	}

	public void redo() throws CannotRedoException {
		super.redo();

		//	insert the test case in the same position

			m_Structure.addTestCase(m_TestCase);


	}
	public void undo() throws CannotUndoException {
		super.undo();


		m_Structure.removeTestCase(m_TestCase);
	}
	public boolean canRedo() {
		return !m_Structure.getTestCases().contains(m_TestCase);
	}

	@Override
	public boolean canUndo() {
		// TODO Auto-generated method stub
		return m_Structure.getTestCases().contains(m_TestCase);
	}
}
