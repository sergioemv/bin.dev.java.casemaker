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
import bi.view.edit.cmgridtdstructure.CMChangeNumOfDinamycColumnsInGridTDStructure;
import bi.view.indextdstructureupdate.CMUpdateIndexInIndexTDStructureUpdateViewEdit;
import bi.view.indextdstructureupdate.CMUpdateIndexTestDataInInIndexTDStructureUpdateViewEdit;
import bi.view.indextdstructureupdate.CMUpdateIndexTestDataSetInCMIndexTDStructureUpdateVIewEdit;
import bi.view.paneltestdataview.CMAddTDCombinationToPanelTestDataViewEdit;
import bi.view.testdataviews.CMGridTDStructure;
import bi.view.testdataviews.CMIndexTDStructureUpdate;
import bi.view.testdataviews.CMPanelTestDataView;

/**
 * @author smoreno
 *
 */
class CMViewEditFactoryImpl implements CMViewEditFactory {

	/**
	 * 
	 */
	public CMViewEditFactoryImpl() {
		super();
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see bi.view.edit.CMViewEditFactory#createTreeSelectNodeEdit(javax.swing.tree.DefaultMutableTreeNode)
	 */
	public UndoableEdit createTreeSelectNodeEdit(TreePath p_path) {
		return new CMSelectNodeViewEdit(p_path);
	}

	/* (non-Javadoc)
	 * @see bi.view.edit.CMViewEditFactory#createSelectEffectGridViewEdit(model.Effect)
	 */
	public UndoableEdit createSelectEffectGridViewEdit(Effect p_effect) {

		return new CMSelectEffectGridView(p_effect);
	}

	/* (non-Javadoc)
	 * @see bi.view.edit.CMViewEditFactory#createSelecteEquivalenceClassEffectGrid(model.EquivalenceClass)
	 */
	public UndoableEdit createSelecteEquivalenceClassEffectGrid(EquivalenceClass p_equivalenceClass) {
		return new CMSelectEquivalenceClassEffectsGridView(p_equivalenceClass);
	}

	/* (non-Javadoc)
	 * @see bi.view.edit.CMViewEditFactory#createAddTDCombinationToPanelTDViewViewEdit(bi.view.testdataviews.CMPanelTestDataView, model.TestDataCombinations)
	 */
	public UndoableEdit createAddTDCombinationToPanelTDViewViewEdit(CMPanelTestDataView testDataView, TestDataCombinations testDataCombination) {

		return new CMAddTDCombinationToPanelTestDataViewEdit(testDataView,testDataCombination);
	}

	/* (non-Javadoc)
	 * @see bi.view.edit.CMViewEditFactory#createChangeIndexInCMIndexTDStructureUpdate(bi.view.testdataviews.CMIndexTDStructureUpdate, int)
	 */
	public UndoableEdit createChangeIndexInCMIndexTDStructureUpdate(CMIndexTDStructureUpdate instance2, int index) {
		
		return new CMUpdateIndexInIndexTDStructureUpdateViewEdit(instance2,index);
	}

	/* (non-Javadoc)
	 * @see bi.view.edit.CMViewEditFactory#createChangeIndexTestDataInCMIndexTDStructureUpdate(bi.view.testdataviews.CMIndexTDStructureUpdate, int)
	 */
	public UndoableEdit createChangeIndexTestDataInCMIndexTDStructureUpdate(CMIndexTDStructureUpdate instance2, int i) {
		
		return new CMUpdateIndexTestDataInInIndexTDStructureUpdateViewEdit(instance2,i);
	}

	/* (non-Javadoc)
	 * @see bi.view.edit.CMViewEditFactory#createChangeNumOfColumnsDinamicInGridTDStructure(bi.view.testdataviews.CMGridTDStructure, int)
	 */
	public UndoableEdit createChangeNumOfColumnsDinamicInGridTDStructure(CMGridTDStructure structure, int i) {
		
		return new CMChangeNumOfDinamycColumnsInGridTDStructure(structure,i);
	}

	/* (non-Javadoc)
	 * @see bi.view.edit.CMViewEditFactory#createChangeIndexTestDataSetInCMIndexTDStructureUpdate(bi.view.testdataviews.CMIndexTDStructureUpdate, int)
	 */
	public UndoableEdit createChangeIndexTestDataSetInCMIndexTDStructureUpdate(CMIndexTDStructureUpdate instance2, int i) {
		
		return new CMUpdateIndexTestDataSetInCMIndexTDStructureUpdateVIewEdit(instance2,i);
	}

	public UndoableEdit createSelectTestCaseGridViewEdit(TestCase testCase) {
		
		return new CMSelectTestCaseGridView(testCase);
	}

	/* (non-Javadoc)
	 * @see bi.view.edit.CMViewEditFactory#createChangeCellGlobalObjectInTDstructureViewEdit(bi.view.testdataviews.CMGridTDStructure.CMGridModel, bi.view.cells.CMCellTDStructureGlobal, int, int)
	 */
	/*public UndoableEdit createChangeCellGlobalObjectInTDstructureViewEdit(CMGridModel cmGridModel, CMCellTDStructureGlobal cellGlobal, int row, int column) {
		
		return new CMChangeCellGlobalInGridTDStructureViewEdit(cmGridModel,cellGlobal,row,column);
	}*/

}
