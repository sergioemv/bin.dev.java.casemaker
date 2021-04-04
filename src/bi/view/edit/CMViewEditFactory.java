/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.view.edit;

import javax.swing.tree.TreePath;
import javax.swing.undo.UndoableEdit;

import model.Effect;
import model.EquivalenceClass;
import model.TestCase;
import model.TestDataCombinations;
import bi.view.testdataviews.CMGridTDStructure;
import bi.view.testdataviews.CMIndexTDStructureUpdate;
import bi.view.testdataviews.CMPanelTestDataView;

/**
 * @author smoreno
 *
 */
public interface CMViewEditFactory {

	public static final CMViewEditFactory INSTANCE = new CMViewEditFactoryImpl();

	/**
	*@autor smoreno
	 * @param p_workspaceNode
	 * @return
	 */
	UndoableEdit createTreeSelectNodeEdit(TreePath p_Path);

	/**
	 * @param p_effect
	 * @return
	 */
	UndoableEdit createSelectEffectGridViewEdit(Effect p_effect);
	
	
	UndoableEdit createSelectTestCaseGridViewEdit(TestCase p_TestCase);

	/**
	 * @param p_equivalenceClass
	 * @return
	 */
	UndoableEdit createSelecteEquivalenceClassEffectGrid(EquivalenceClass p_equivalenceClass);

	/**
	 * @param testDataView
	 * @param testDataCombination
	 * @return
	 */
	UndoableEdit createAddTDCombinationToPanelTDViewViewEdit(CMPanelTestDataView testDataView, TestDataCombinations testDataCombination);

	/**
	 * @param instance2
	 * @param index
	 * @return
	 */
	UndoableEdit createChangeIndexInCMIndexTDStructureUpdate(CMIndexTDStructureUpdate instance2, int index);

	/**
	 * @param instance2
	 * @param i
	 * @return
	 */
	UndoableEdit createChangeIndexTestDataInCMIndexTDStructureUpdate(CMIndexTDStructureUpdate instance2, int i);

	/**
	 * @param structure
	 * @param i
	 * @return
	 */
	UndoableEdit createChangeNumOfColumnsDinamicInGridTDStructure(CMGridTDStructure structure, int i);

	/**
	 * @param instance2
	 * @param i
	 * @return
	 */
	UndoableEdit createChangeIndexTestDataSetInCMIndexTDStructureUpdate(CMIndexTDStructureUpdate instance2, int i);

	/**
	 * @param cmGridModel
	 * @param cellGlobal
	 * @param row
	 * @param column
	 * @return
	 */
	//UndoableEdit createChangeCellGlobalObjectInTDstructureViewEdit(CMGridModel cmGridModel, CMCellTDStructureGlobal cellGlobal, int row, int column);

}
