/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.controller.testcase.generator;

import java.util.List;

import javax.swing.undo.UndoableEdit;

import bi.view.lang.CMMessages;
import bi.view.undomanagementviews.CMCompoundEdit;

import model.Dependency;
import model.Structure;

/**
 * @author smoreno
 * Generates Positive test cases from a structure
 */
public class CMWorkFlowAllTestCasesGenerator extends CMAbstractTestCaseGenerator {


	/**
	 * @param p_structure
	 * @param p_Dependencies
	 */
	public CMWorkFlowAllTestCasesGenerator(Structure p_structure, List<Dependency> p_Dependencies) {
		super(p_structure, p_Dependencies);
		// TODO Auto-generated constructor stub
	}

	/**
	 *
	 */
	public CMWorkFlowAllTestCasesGenerator() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see bi.controller.testcase.generator.CMTestCaseGenerator#generate()
	 */
	public UndoableEdit generate() throws CMTestCaseGenerationException {
		if (getStructure() == null || getDependencies() == null) return null;
		CMCompoundEdit ce = new CMCompoundEdit();
			ce.addEdit(CMTestCaseGeneratorFactory.INSTANCE.createWorkflowPositiveTestCasesGenerator(structure,dependencies).generate());
			ce.addEdit(CMTestCaseGeneratorFactory.INSTANCE.createWorkflowNegativeTestCasesGenerator(structure,dependencies).generate());
			ce.addEdit(CMTestCaseGeneratorFactory.INSTANCE.createWorkflowFaultyTestCasesGenerator(structure,dependencies).generate());
		return ce;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return CMMessages.getString("LABEL_WORKFLOW_ALL_KIND_OF_TEST_CASES"); //$NON-NLS-1$
	}

}
