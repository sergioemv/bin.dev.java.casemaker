/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.controller.testcase.generator;

import java.util.List;

import javax.swing.undo.UndoableEdit;

import model.Dependency;
import model.Structure;

/**
 * @author smoreno
 *
 */
public interface CMTestCaseGenerator {

	public UndoableEdit generate() throws CMTestCaseGenerationException;
	public void setDependencies(List<Dependency> dependencies);
	public void setStructure(Structure p_structure);
}
