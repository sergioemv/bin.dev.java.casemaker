/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.controller.testcase.generator;

import java.util.ArrayList;
import java.util.List;



import model.Dependency;
import model.Structure;

/**
 * Factory for all test case generators
 * @author smoreno
 *
 */
public interface CMTestCaseGeneratorFactory {


	CMTestCaseGeneratorFactory INSTANCE = new CMTestCaseGeneratorFactory(){

		public List<CMTestCaseGenerator> createVisibleGenerators() {
			ArrayList<CMTestCaseGenerator> list = new ArrayList<CMTestCaseGenerator>();
			list.add(createAllTestCasesGenerator());
			list.add(createPositiveTestCasesGenerator());
			list.add(createNegativeTestCasesGenerator());
			list.add(createFaultyTestCasesGenerator());
			list.add(createNegativeEClassGenerator());
			list.add(createNegativeCombinationGenerator());
			list.add(createFaultyEclassGenerator());
			list.add(createFaultyCombinationGenerator());
			return list;
		}

		public CMTestCaseGenerator createPositiveTestCasesGenerator() {

			return new CMPositiveTestCasesGenerator();
		}

		public CMTestCaseGenerator createNegativeTestCasesGenerator() {
			return new CMNegativeTestCasesGenerator();
		}

		public CMTestCaseGenerator createFaultyTestCasesGenerator() {
			return new CMFaultyTestCasesGenerator();
		}

		public CMTestCaseGenerator createNegativeEClassGenerator() {
			return new CMNegativeEClassTestCasesGenerator();

		}

		public CMTestCaseGenerator createNegativeCombinationGenerator() {
			return new CMNegativeCombinationTestCasesGenerator();
		}

		public CMTestCaseGenerator createFaultyEclassGenerator() {
			return new CMFaultyEClassTestCasesGenerator();
		}

		public CMTestCaseGenerator createFaultyCombinationGenerator() {
			return new CMFaultyCombinationTestCasesGenerator();
		}


		public CMTestCaseGenerator createAllTestCasesGenerator() {
			return new CMAllTestCasesGenerator();
		}

		public CMTestCaseGenerator createOneWiseGenerator(Structure p_structure, List<Dependency> p_deps) {

			return new CMPositiveEquivalenceClassOneWiseGenerator(p_structure,p_deps);
		}

		public CMTestCaseGenerator createPositiveTestCasesGenerator(Structure p_structure, List<Dependency> p_dependencies) {

			return new CMPositiveTestCasesGenerator(p_structure,p_dependencies);
		}

		public CMTestCaseGenerator createNegativeTestCasesGenerator(Structure p_structure, List<Dependency> p_dependencies) {
			return new CMNegativeTestCasesGenerator(p_structure,p_dependencies);
		}

		public CMTestCaseGenerator createFaultyTestCasesGenerator(Structure p_structure, List<Dependency> p_dependencies) {
			return new CMFaultyTestCasesGenerator(p_structure,p_dependencies);
		}

		public List<CMTestCaseGenerator> createFlowGenerators() {
			ArrayList<CMTestCaseGenerator> list = new ArrayList<CMTestCaseGenerator>();
			list.add(createWorkflowAllTestCaseGenerator());
			list.add(createWorkflowPositiveTestCaseGenerator());
			list.add(createWorkflowNegativeTestCaseGenerator());
			list.add(createWorkflowFaultyTestCaseGenerator());

			return list;
		}

		public CMTestCaseGenerator createWorkflowPositiveTestCaseGenerator() {

			return new CMWorkFlowPositiveTestCaseGenerator();
		}

		public CMTestCaseGenerator createWorkflowAllTestCaseGenerator() {
			return new CMWorkFlowAllTestCasesGenerator();
		}

		public CMTestCaseGenerator createWorkflowFaultyTestCaseGenerator() {
			return new CMWorkFlowFaultyTestCaseGenerator();
		}

		public CMTestCaseGenerator createWorkflowNegativeTestCaseGenerator() {
			return new CMWorkFlowNegativeTestCaseGenerator();
		}

		public CMTestCaseGenerator createWorkflowPositiveTestCasesGenerator(Structure p_structure, List<Dependency> p_dependencies) {

			return new CMWorkFlowPositiveTestCaseGenerator(p_structure,p_dependencies);
		}

		public CMTestCaseGenerator createWorkflowNegativeTestCasesGenerator(Structure p_structure, List<Dependency> p_dependencies) {
			
			return new CMWorkFlowNegativeTestCaseGenerator(p_structure,p_dependencies);
		}

		public CMTestCaseGenerator createWorkflowFaultyTestCasesGenerator(Structure p_structure, List<Dependency> p_dependencies) {
			return new CMWorkFlowFaultyTestCaseGenerator(p_structure,p_dependencies);
		}


	};
	/**
	 * @return
	 */
	public List<CMTestCaseGenerator> createVisibleGenerators();
	/**
	 * @return
	 */
	public CMTestCaseGenerator createWorkflowAllTestCaseGenerator();
	/**
	 * @return
	 */
	public CMTestCaseGenerator createWorkflowFaultyTestCaseGenerator();
	/**
	 * @return
	 */
	public CMTestCaseGenerator createWorkflowNegativeTestCaseGenerator();
	/**
	 * @param p_structure
	 * @param p_dependencies
	 * @return
	 */
	public CMTestCaseGenerator createWorkflowPositiveTestCaseGenerator();
	/**
	 * @return
	 */
	public CMTestCaseGenerator createAllTestCasesGenerator();
	/**
	 * @return
	 */
	public CMTestCaseGenerator createFaultyCombinationGenerator();
	/**
	 * @return
	 */
	public CMTestCaseGenerator createFaultyEclassGenerator();
	/**
	 * @return
	 */
	public CMTestCaseGenerator createNegativeCombinationGenerator();
	/**
	 * @return
	 */
	public CMTestCaseGenerator createNegativeEClassGenerator();
	/**
	 * @return
	 */
	public CMTestCaseGenerator createFaultyTestCasesGenerator();
	/**
	 * @return
	 */
	public CMTestCaseGenerator createNegativeTestCasesGenerator();
	/**
	 * @return
	 */
	public CMTestCaseGenerator createPositiveTestCasesGenerator();

	public CMTestCaseGenerator createOneWiseGenerator(Structure p_structure, List<Dependency> p_deps);
	/**
	 * @param p_structure
	 * @param p_dependencies
	 * @return
	 */
	public CMTestCaseGenerator createPositiveTestCasesGenerator(Structure p_structure, List<Dependency> p_dependencies);
	/**
	 * @param p_structure
	 * @param p_dependencies
	 * @return
	 */
	public CMTestCaseGenerator createNegativeTestCasesGenerator(Structure p_structure, List<Dependency> p_dependencies);
	/**
	 * @param p_structure
	 * @param p_dependencies
	 * @return
	 */
	public CMTestCaseGenerator createFaultyTestCasesGenerator(Structure p_structure, List<Dependency> p_dependencies);
	/**
	 * Creates the flow generators for the flow test object
	 * @return
	 */
	public List<CMTestCaseGenerator> createFlowGenerators();
	/**
	 * @param p_structure
	 * @param p_dependencies
	 * @return
	 */
	public CMTestCaseGenerator createWorkflowPositiveTestCasesGenerator(Structure p_structure, List<Dependency> p_dependencies);
	/**
	 * @param p_structure
	 * @param p_dependencies
	 * @return
	 */
	public CMTestCaseGenerator createWorkflowNegativeTestCasesGenerator(Structure p_structure, List<Dependency> p_dependencies);
	/**
	 * @param p_structure
	 * @param p_dependencies
	 * @return
	 */
	public CMTestCaseGenerator createWorkflowFaultyTestCasesGenerator(Structure p_structure, List<Dependency> p_dependencies);
}


