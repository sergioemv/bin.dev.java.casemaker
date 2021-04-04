package model.edit;

import java.util.Date;
import java.util.Vector;

import javax.swing.undo.UndoableEdit;

import model.CMError;
import model.Combination;
import model.DelegateObservable;
import model.Dependency;
import model.Effect;
import model.Element;
import model.EquivalenceClass;
import model.ExpectedResult;
import model.ICMValue;
import model.ITypeData;
import model.Project2;
import model.ProjectReference;
import model.Requirement;
import model.ResultComparation;
import model.ResultStructureTestData;
import model.Session2;
import model.State;
import model.Structure;
import model.StructureTestData;
import model.TDStructure;
import model.TestCase;
import model.TestCaseGroup;
import model.TestData;
import model.TestDataCombinations;
import model.TestDataFormat;
import model.TestDataSet;
import model.Type;
import model.TypeDataLocal;
import model.Variable;
import model.Workspace2;
import model.edit.combination.CMAddChildCombinationModelEditCombination;
import model.edit.combination.CMAddCombinationModelEdit;
import model.edit.combination.CMCombinationChangeCombinationParent;
import model.edit.combination.CMDeleteCombinationModelEdit;
import model.edit.combination.CMDeleteEffectModelEditCombination;
import model.edit.delegateobservable.CMDelegateObservableDeleteObserverModelEdit;
import model.edit.dependency.CMAddDependencyModelEdit;
import model.edit.dependency.CMRemoveDependencyModelEdit;
import model.edit.effect.CMAddEffectNotifiedModelEdit;
import model.edit.effect.CMAddExpectedResult;
import model.edit.effect.CMRemoveExpectedResultModelEdit;
import model.edit.element.CMAddElementModelEdit;
import model.edit.element.CMChangeGUIObjectModelEdit;
import model.edit.element.CMRemoveElementModelEdit;
import model.edit.equivalenceclass.CMAddEquivalenceClassModelEdit;
import model.edit.equivalenceclass.CMEquivalenceClassChangeValueModelEdit;
import model.edit.equivalenceclass.CMRemoveEquivalenceClassModelEdit;
import model.edit.idset.CMAddElementStructureToIdSetModelEdit;
import model.edit.idset.CMChangeIdsIdSetModelEdit;
import model.edit.idset.CMDeleteElementStructureToIdSetModelEdit;
import model.edit.project.CMBindProjectAndReferenceModelEdit;
import model.edit.requirement.CMAddRequirementModelEdit;
import model.edit.requirement.CMRemoveRequirementModelEdit;
import model.edit.resultcomparison.CMDeleteTestDataSetFromResultComparisionModelEdit;
import model.edit.resultcomparison.CMDeleteTestDataSetTargetFromResultComparisonModelEdit;
import model.edit.resultstructuretestdata.CMChangeStructureTestDataToResultStructureTDModelEdit;
import model.edit.session.CMSessionAddWorkspace;
import model.edit.session.CMSessionDeleteWorkspaceModelEdit;
import model.edit.session.CMSessionMoveWorkspace;
import model.edit.structure.CMAddTestCaseModelEdit;
import model.edit.structure.CMDeleteTestCaseModelEdit;
import model.edit.structuretestdata.CMAddElementInStructureTestData;
import model.edit.structuretestdata.CMAddTypeDataToStructureTDModelEdit;
import model.edit.structuretestdata.CMChangeColumnsHeaderInStructureTestDataModelEdit;
import model.edit.structuretestdata.CMChangeIdInStructureTestDataModelEdit;
import model.edit.structuretestdata.CMChangeIdTestDataInStructureTestDataModelEdit;
import model.edit.structuretestdata.CMInsertElementAtInStructureTestData;
import model.edit.structuretestdata.CMRemoveElementAtInStructureTestData;
import model.edit.structuretestdata.CMRemoveElementAtInStructureWithTwoParamTestData;
import model.edit.structuretestdata.CMRemoveElementInStructureTestData;
import model.edit.structuretestdata.CMStructureEditDescriptionModelEdit;
import model.edit.structuretestdata.CMStructureEditGlobalIndexModelEdit;
import model.edit.structuretestdata.CMStructureEditNameModelEdit;
import model.edit.structuretestdata.CMStructureEditTypeModelEdit;
import model.edit.structuretestdata.CMTypeDataToStructureTestDataModelEdit;
import model.edit.structuretestdata.CMTypeDataToStructureTestDataOnlyModelEdit;
import model.edit.tdstructure.CMAddTestDataSetToTDStructureModelEdit;
import model.edit.tdstructure.CMAddTestDataToTDStructureModelEdit;
import model.edit.tdstructure.CMAssignStructureTestDataToTDStructureModelEdit;
import model.edit.tdstructure.CMAssignTestDataCombinationToTDStructureModelEdit;
import model.edit.tdstructure.CMChangeIdTestDataSetInTDStructureModelEdit;
import model.edit.tdstructure.CMChangeIdsInTDStructureModelEdit;
import model.edit.tdstructure.CMChangeStructureTestDataInTDStructureModelEdit;
import model.edit.tdstructure.CMChangeTestCasesInTDStructureModelEdit;
import model.edit.tdstructure.CMChangeTestDataSetToTDStructureModelEdit;
import model.edit.tdstructure.CMRemoveSTDFromTDStructureModelEdit;
import model.edit.tdstructure.CMRemoveTestDataSetElementAtFromTDStructureModelEdit;
import model.edit.tdstructure.CMRemoveTestDataSetFromTDStructureModelEdit;
import model.edit.testcase.CMChangeTestCaseDependenciesModelEdit;
import model.edit.testcase.CMChangeTestCaseGroupModelEdit;
import model.edit.testdata.CMChangeCOntTestDataSetInTestDataModelEdit;
import model.edit.testdata.CMChangeIsSetInTestDataModelEdit;
import model.edit.testdata.CMRemoveTestDataFromTestDataCombination;
import model.edit.testdata.CMTestDataEditDescriptionModelEdit;
import model.edit.testdata.CMTestDataEditIDModelEdit;
import model.edit.testdata.CMTestDataEditNameModelEdit;
import model.edit.testdata.CMTestDataEditRiskLevelModelEdit;
import model.edit.testdata.CMTestDataEditTestCaseInTestDataModelEdit;
import model.edit.testdatacombination.CMAddTestDatasToTestDataCombinationModelEdit;
import model.edit.testdataformat.CMChangeValueInTestDataFormatModelEdit;
import model.edit.testdataset.CMChangeDescInTestDataSetModelEdit;
import model.edit.typedata.CMAddObserverInTypeDataModelEdit;
import model.edit.typedata.CMAddObserverVariableInTypeDataModelEdit;
import model.edit.typedata.CMAddObserverWithTwoParamInTypeDataModelEdit;
import model.edit.typedata.CMChangeFieldInTypeDataModelEdit;
import model.edit.typedata.CMChangeFormatInTypeDataModelEdit;
import model.edit.typedata.CMChangeFormatterInTypeDataModelEdit;
import model.edit.typedata.CMChangeFormulaInTypeDataModelEdit;
import model.edit.typedata.CMChangeGlobalReferenceInTypeDataModelEdit;
import model.edit.typedata.CMChangeKeyInTypeDataModelEdit;
import model.edit.typedata.CMChangeLengthInTypeDataModelEdit;
import model.edit.typedata.CMChangeNameInTypeDataModelEdit;
import model.edit.typedata.CMChangeNewColumnInTypeDataModelEdit;
import model.edit.typedata.CMChangeNewColumnsInTypeDataModelEdit;
import model.edit.typedata.CMChangeObjectTypeInTypeDataModelEdit;
import model.edit.typedata.CMChangePrefixInTypeDataModelEdit;
import model.edit.typedata.CMChangeStructureTestDataInTypeDataModelEdit;
import model.edit.typedata.CMChangeSuffixInTypeDataModelEdit;
import model.edit.typedata.CMChangeToolVendorOTInTypeDataModelEdit;
import model.edit.typedata.CMChangeTypeInTypeDataModelEdit;
import model.edit.typedata.CMChangeValueInTypeDataModelEdit;
import model.edit.typedata.CMChangeisFormulaInTypeDataModelEdit;
import model.edit.typedata.CMChangeisLinkElementInTypeDataModelEdit;
import model.edit.typedata.CMDeleteObserverInTypeDataModelEdit;
import model.edit.typedata.CMReferenceTypeDataInTypeDataModelEdit;
import model.edit.workspace.CMAddProjectReferenceModelEdit;
import model.edit.workspace.CMDeleteProjectReferenceModelEdit;
import model.util.CMAccessStateBean;
import model.util.CMCombinationsBean;
import model.util.CMDelegate;
import model.util.CMDependencyBean;
import model.util.CMDescriptionBean;
import model.util.CMDescriptionEditableBean;
import model.util.CMEffectsBean;
import model.util.CMElementsBean;
import model.util.CMEquivalenceClassesBean;
import model.util.CMErrorsBean;
import model.util.CMFilePathBean;
import model.util.CMIdBean;
import model.util.CMNameBean;
import model.util.CMOriginTypeBean;
import model.util.CMRequirementBean;
import model.util.CMRiskLevelBean;
import model.util.CMStateBean;
import model.util.CMTimeStampBean;
import model.util.CMTypeBean;
import model.util.CMUserBean;
import model.util.CMUserOrderBean;
import model.util.CMValueBean;
import model.util.IdSet;
import model.util.TestCasesBean;
import model.util.CMOriginTypeBean.Origin;

class CMModelEditFactoryImpl implements CMModelEditFactory{

	public UndoableEdit createDeleteTestCaseModelEdit(Structure structure, TestCase testCase) {
		return new CMDeleteTestCaseModelEdit(structure,testCase);
	}

	public UndoableEdit createRemoveCombinationModelEdit(CMCombinationsBean dependency, Combination combination) {
		return new CMDeleteCombinationModelEdit(dependency,combination);
	}


	public UndoableEdit createAddEffectModelEdit(CMEffectsBean p_model, Effect p_Effect) {
		return new CMAddEffectNotifiedModelEdit(p_model,p_Effect);
	}

	public UndoableEdit createAddCombinationModelEdit(Combination combinationParent, Combination combinationChild) {
		return new CMAddChildCombinationModelEditCombination(combinationParent,combinationChild);
	}

	
	public UndoableEdit createChangeCombinationParentModelEdit(Combination combination, Combination parent) {

		return new CMCombinationChangeCombinationParent(combination,parent);
	}



	public UndoableEdit createChangeStateModelEdit(CMStateBean combination, State newState) {

		return new CMChangeStateModelEdit(combination,newState);
	}
	public UndoableEdit createDeleteTestCaseModelEdit(CMError error, TestCase testCase) {

		return new model.edit.error.CMDeleteTestCaseModelEdit(error,testCase);
	}

	public UndoableEdit createDeleteEffectModelEdit(CMEffectsBean combination, Effect effect) {

		return new CMDeleteEffectModelEditCombination(combination,effect);
	}

	public UndoableEdit createChangeNameModelEdit(CMNameBean p_element, String p_name) {

		return new CMChangeNameModelEdit(p_element,p_name);
	}

	/* (non-Javadoc)
	 * @see model.edit.CMModelEditFactory#createChangeDescriptionModelEdit(model.CMDescriptionBean, java.lang.String)
	 */
	public UndoableEdit createChangeDescriptionModelEdit(CMDescriptionBean p_model, String p_description) {

		return new CMChangeDescriptionModelEdit(p_model,p_description);
	}

	/* (non-Javadoc)
	 * @see model.edit.CMModelEditFactory#createChangeRiskLevelModelEdit(model.CMRiskLevelBean, int)
	 */
	public UndoableEdit createChangeRiskLevelModelEdit(CMRiskLevelBean p_model, int p_level) {
		return new CMChangeRiskLevelModelEdit(p_model,p_level);
	}

	/* (non-Javadoc)
	 * @see model.edit.CMModelEditFactory#createEquivalenceClassChangeValueModelEdit(model.EquivalenceClass, java.lang.String)
	 */
	public UndoableEdit createEquivalenceClassChangeValueModelEdit(EquivalenceClass p_equivalenceClass, String p_stringValue) {
		return new CMEquivalenceClassChangeValueModelEdit(p_equivalenceClass,p_stringValue);
	}

	/* (non-Javadoc)
	 * @see model.edit.CMModelEditFactory#createAddEquivalenceClassModelEdit(model.Element, model.EquivalenceClass)
	 */
	public UndoableEdit createAddEquivalenceClassModelEdit(CMEquivalenceClassesBean p_bean, EquivalenceClass p_equivalenceClass) {
		return new CMAddEquivalenceClassModelEdit(p_bean, p_equivalenceClass);
	}
//svonborries_23022006_begin
	public UndoableEdit createChangeFieldInTypeDataModelEdit(ITypeData p_typeData, String p_Value) {

		return new CMChangeFieldInTypeDataModelEdit(p_typeData,p_Value);
	}

	public UndoableEdit createChangeFormatInTypeDataModelEdit(ITypeData p_typeData, String p_Value) {

		return new CMChangeFormatInTypeDataModelEdit(p_typeData,p_Value);
	}

	public UndoableEdit createChangeFormulaInTypeDataModelEdit(ITypeData p_typeData, String p_Value) {

		return new CMChangeFormulaInTypeDataModelEdit(p_typeData,p_Value);
	}

	public UndoableEdit createChangeKeyInTypeDataModelEdit(ITypeData p_typeData, String p_Value) {

		return new CMChangeKeyInTypeDataModelEdit(p_typeData,p_Value);
	}

	public UndoableEdit createChangeLengthInTypeDataModelEdit(ITypeData p_typeData, String p_Value) {

		return new CMChangeLengthInTypeDataModelEdit(p_typeData,p_Value);
	}

	public UndoableEdit createChangeNameInTypeDataModelEdit(ITypeData p_typeData, String p_Value) {

		return new CMChangeNameInTypeDataModelEdit(p_typeData,p_Value);
	}

	public UndoableEdit createChangeNewColumnInTypeDataModelEdit(ITypeData p_typeData, String p_Value, int p_Column) {

		return new CMChangeNewColumnInTypeDataModelEdit(p_typeData,p_Value,p_Column);
	}

	public UndoableEdit createChangeObjectTypeInTypeDataModelEdit(ITypeData p_typeData, int p_Value/*ccastedo 27.09.06 String p_Value*/) {

		return new CMChangeObjectTypeInTypeDataModelEdit(p_typeData,p_Value);
	}

	public UndoableEdit createChangePrefixInTypeDataModelEdit(ITypeData p_typeData, String p_Value) {

		return new CMChangePrefixInTypeDataModelEdit(p_typeData,p_Value);
	}

	public UndoableEdit createChangeSuffixInTypeDataModelEdit(ITypeData p_typeData, String p_Value) {

		return new CMChangeSuffixInTypeDataModelEdit(p_typeData,p_Value);
	}

	public UndoableEdit createChangeTypeInTypeDataModelEdit(ITypeData p_typeData, String p_Value) {

		return new CMChangeTypeInTypeDataModelEdit(p_typeData,p_Value);
	}

	public UndoableEdit createChangeValueInTypeDataModelEdit(ITypeData p_typeData, ICMValue p_Value) {

		return new CMChangeValueInTypeDataModelEdit(p_typeData,p_Value);
	}
//svonborries_23022006_end
//	svonborries_02032006_begin
	public UndoableEdit createChangeGlobalReferenceInTypeDataModelEdit(ITypeData p_typeData, String p_Value) {

		return new CMChangeGlobalReferenceInTypeDataModelEdit(p_typeData,p_Value);
	}
//	svonborries_02032006_end
//	svonborries_08032006_begin
	public UndoableEdit createChangeFormatterInTypeDataModelEdit(ITypeData p_typeData, TestDataFormat p_Formatter) {

		return new CMChangeFormatterInTypeDataModelEdit(p_typeData,p_Formatter);
	}
//	svonborries_08032006_end
//	svonborries_09032006_begin
	public UndoableEdit createChangeToolVendorOTInTypeDataModelEdit(ITypeData p_typeData, int stateOT/*ccastedo 27.09.06 String p_ToolVendor*/) {

		return new CMChangeToolVendorOTInTypeDataModelEdit(p_typeData,stateOT/*p_ToolVendor*/);
	}

	public UndoableEdit createChangeNewColumnsInTypeDataModelEdit(ITypeData p_typeData, Vector p_newColumns) {

		return new CMChangeNewColumnsInTypeDataModelEdit(p_typeData,p_newColumns);
	}
//	svonborries_09032006_end
//	svonborries_10032006_begin
	public UndoableEdit createChangeisFormulaInTypeDataModelEdit(ITypeData p_typeData, boolean p_value) {

		return new CMChangeisFormulaInTypeDataModelEdit(p_typeData,p_value);
	}
//	svonborries_10032006_end
//	svonborries_15032006_begin
	public UndoableEdit createChangeisLinkElementInTypeDataModelEdit(ITypeData p_typeData, boolean p_value) {

		return new CMChangeisLinkElementInTypeDataModelEdit(p_typeData,p_value);
	}

	public UndoableEdit createDeleteObserverInTypeDataModelEdit(ITypeData p_observerTypeData, ITypeData p_observableTypeData, String p_key) {

		return new CMDeleteObserverInTypeDataModelEdit(p_observerTypeData,p_observableTypeData, p_key);
	}

	public UndoableEdit createAddObserverInTypeDataModelEdit(ITypeData p_observerTypeData, ITypeData p_observableTypeData, String p_key) {

		return new CMAddObserverInTypeDataModelEdit(p_observerTypeData,p_observableTypeData,p_key);
	}

	public UndoableEdit createAddElementInStructureTestDataModelEdit(ITypeData p_typeData, StructureTestData p_structureTestData, int p_indexTyp) {

		return new CMAddElementInStructureTestData(p_typeData,p_structureTestData,p_indexTyp);
	}

	public UndoableEdit createInsertElementAtInStructureTestDataModelEdit(ITypeData p_typeData, StructureTestData p_structureTestData, int p_row) {

		return new CMInsertElementAtInStructureTestData(p_typeData,p_structureTestData,p_row);
	}

	public UndoableEdit createChangeStructureTestDataInTypeDataModelEdit(ITypeData p_typeData, StructureTestData p_structureTestData) {

		return new CMChangeStructureTestDataInTypeDataModelEdit(p_typeData,p_structureTestData);
	}

	public UndoableEdit createChangeReferenceTypeDataInTypeDataModelEdit(ITypeData p_typedatalocal, ITypeData p_typedataglobal) {

		return new CMReferenceTypeDataInTypeDataModelEdit(p_typedatalocal, p_typedataglobal);
	}

	public UndoableEdit createRemoveElementInStructureTestDataModelEdit(ITypeData p_typeData, StructureTestData p_structureTestData) {

		return new CMRemoveElementInStructureTestData(p_typeData,p_structureTestData);
	}

	public UndoableEdit createRemoveElementAtInStructureTestDataModelEdit(ITypeData p_typeData, StructureTestData p_structureTestData, int p_row) {

		return new CMRemoveElementAtInStructureTestData(p_typeData,p_structureTestData,p_row);
	}

	public UndoableEdit createAddObserverVariableInTypeDataModelEdit(Variable p_variable, ITypeData p_typeData) {

		return new CMAddObserverVariableInTypeDataModelEdit(p_variable,p_typeData);
	}


//	svonborries_15032006_end
	/* (non-Javadoc)
	 * @see model.edit.CMModelEditFactory#createAddWorkspaceModelEdit(model.Session2, model.Workspace2)
	 */
	public UndoableEdit createAddWorkspaceModelEdit(Session2 p_currentSession, Workspace2 p_workspace) {
		return new CMSessionAddWorkspace(p_currentSession, p_workspace);
	}

	/* (non-Javadoc)
	 * @see model.edit.CMModelEditFactory#createChangeIdModelEdit(model.util.CMIdBean, int)
	 */
	public UndoableEdit createChangeIdModelEdit(CMIdBean p_idBean, int p_i) {
		return new CMChangeIdModelEdit(p_idBean, p_i);
	}

	/* (non-Javadoc)
	 * @see model.edit.CMModelEditFactory#createDeleteWorkspaceModelEdit(model.Workspace2)
	 */
	public UndoableEdit createDeleteWorkspaceModelEdit(Workspace2 p_selectedWorkspace2) {
		return new CMSessionDeleteWorkspaceModelEdit(p_selectedWorkspace2);
	}

	/* (non-Javadoc)
	 * @see model.edit.CMModelEditFactory#createAddWorkspaceModelEdit(model.Session2, model.Workspace2, int)
	 */
	public UndoableEdit createAddWorkspaceModelEdit(Session2 p_session, Workspace2 p_workspace, int p_index) {
		return new CMSessionAddWorkspace(p_session, p_workspace, p_index);
	}

	/* (non-Javadoc)
	 * @see model.edit.CMModelEditFactory#createMoveWorkspaceModelEdit(model.Session2, model.Workspace2, int)
	 */
	public UndoableEdit createMoveWorkspaceModelEdit(Session2 p_session, Workspace2 p_workspace, int p_index) {
		return new CMSessionMoveWorkspace(p_session, p_workspace, p_index);
	}

	/* (non-Javadoc)
	 * @see model.edit.CMModelEditFactory#createChangeAccessStateModelEdit(model.util.CMAccessStateBean, java.lang.String)
	 */
	public UndoableEdit createChangeAccessStateModelEdit(CMAccessStateBean p_stateBean, String p_access_state) {
		return new CMChangeAccessStateModelEdit(p_stateBean, p_access_state);
	}

	/* (non-Javadoc)
	 * @see model.edit.CMModelEditFactory#createChangeUserModelEdit(model.Project2, java.lang.String)
	 */
	public UndoableEdit createChangeUserModelEdit(CMUserBean p_userBean, String p_user) {
		return new CMChangeUserModelEdit(p_userBean, p_user);
	}

	/* (non-Javadoc)
	 * @see model.edit.CMModelEditFactory#createChangeTimeStampModelEdit(model.util.CMTimeStampBean, java.lang.String)
	 */
	public UndoableEdit createChangeTimeStampModelEdit(CMTimeStampBean p_bean, Date p_date) {
		return new CMChangeTimeStampModelEdit(p_bean, p_date);
	}

	/* (non-Javadoc)
	 * @see model.edit.CMModelEditFactory#createBindProjectAndReferenceModelEdit(model.Project2, model.ProjectReference)
	 */
	public UndoableEdit createBindProjectAndReferenceModelEdit(Project2 p_newProject, ProjectReference p_newProjectReference) {
		return new CMBindProjectAndReferenceModelEdit(p_newProject,p_newProjectReference);
	}

	/* (non-Javadoc)
	 * @see model.edit.CMModelEditFactory#createAddProjectReferenceModelEdit(model.Workspace2, model.ProjectReference)
	 */
	public UndoableEdit createAddProjectReferenceModelEdit(Workspace2 p_workspace2, ProjectReference p_projectReference) {
		return new CMAddProjectReferenceModelEdit(p_workspace2,p_projectReference);
	}

	/* (non-Javadoc)
	 * @see model.edit.CMModelEditFactory#createDeleteProjectReferenceModelEdit(model.ProjectReference)
	 */
	public UndoableEdit createDeleteProjectReferenceModelEdit(ProjectReference p_selectedProjectReference) {
		return new CMDeleteProjectReferenceModelEdit(p_selectedProjectReference);
	}

	/* (non-Javadoc)
	 * @see model.edit.CMModelEditFactory#createChangeNameModelEdit(model.Workspace2, java.lang.String, model.util.CMDelegate)
	 */
	public UndoableEdit createChangeNameModelEdit(CMNameBean p_bean, String p_newName, CMDelegate p_delegate) {
		return new CMChangeNameModelEdit(p_bean,p_newName,p_delegate);
	}

	/* (non-Javadoc)
	 * @see model.edit.CMModelEditFactory#createChangeFilePathModelEdit(model.ProjectReference, java.lang.String, java.lang.String, model.Project2, model.util.CMDelegate)
	 */
	public UndoableEdit createChangeFilePathModelEdit(CMFilePathBean p_projectReference, String p_newName, String p_newPath, CMNameBean p_nameBean, CMDelegate p_delegate) {
		return new CMChangeFilePathModelEdit(p_projectReference,  p_newName,  p_newPath, p_nameBean,p_delegate);
	}

	/* (non-Javadoc)
	 * @see model.edit.CMModelEditFactory#createAddRequirementModelEdit(model.util.CMRequirementBean, model.Requirement)
	 */
	public UndoableEdit createAddRequirementModelEdit(CMRequirementBean p_parent, Requirement p_req) {
		return new CMAddRequirementModelEdit(p_parent,p_req);
	}

	/* (non-Javadoc)
	 * @see model.edit.CMModelEditFactory#createRemoveRequirementModelEdit(model.util.CMRequirementBean, model.Requirement)
	 */
	public UndoableEdit createRemoveRequirementModelEdit(CMRequirementBean p_effect, Requirement p_req) {
		return new CMRemoveRequirementModelEdit(p_effect, p_req);
	}

	/* (non-Javadoc)
	 * @see model.edit.CMModelEditFactory#createAddExpectedResult(model.Effect, model.ExpectedResults)
	 */
	public UndoableEdit createAddExpectedResult(Effect p_effect, ExpectedResult p_expRes) {
		return new CMAddExpectedResult(p_effect,p_expRes);
	}

	/* (non-Javadoc)
	 * @see model.edit.CMModelEditFactory#createRemoveExpectedResultModelEdit(model.Effect, model.ExpectedResult)
	 */
	public UndoableEdit createRemoveExpectedResultModelEdit(Effect p_effect, ExpectedResult p_exp) {
		return new CMRemoveExpectedResultModelEdit(p_effect,p_exp);
	}

	/* (non-Javadoc)
	 * @see model.edit.CMModelEditFactory#createChangeTypeInStructureTestData(model.StructureTestData, java.lang.String)
	 */
	public UndoableEdit createChangeTypeInStructureTestData(StructureTestData createStructure, String typetdstructure_result) {

		return new CMStructureEditTypeModelEdit(createStructure,typetdstructure_result);
	}

	/* (non-Javadoc)
	 * @see model.edit.CMModelEditFactory#createChangeNameInStructureTestData(model.StructureTestData, java.lang.String)
	 */
	public UndoableEdit createChangeNameInStructureTestData(StructureTestData structureTestData, String name) {

		return new CMStructureEditNameModelEdit(structureTestData,name);
	}

	/* (non-Javadoc)
	 * @see model.edit.CMModelEditFactory#createChangeGlobalIndexInStructureTestData(model.StructureTestData, int)
	 */
	public UndoableEdit createChangeGlobalIndexInStructureTestData(StructureTestData createStructure, int i) {

		return new CMStructureEditGlobalIndexModelEdit(createStructure,i);
	}

	/* (non-Javadoc)
	 * @see model.edit.CMModelEditFactory#createAddTypeDataToStructureTestDataModelEdit(model.StructureTestData, model.ITypeData)
	 */
	public UndoableEdit createAddTypeDataToStructureTestDataModelEdit(StructureTestData createStructure, ITypeData newTypeData) {

		return new CMTypeDataToStructureTestDataModelEdit(createStructure,newTypeData);
	}

	/* (non-Javadoc)
	 * @see model.edit.CMModelEditFactory#createAddStructureTestDataToTDStructureModelEdit(model.TDStructure, model.StructureTestData)
	 */
	public UndoableEdit createAddStructureTestDataToTDStructureModelEdit(TDStructure structure, StructureTestData createStructure) {

		return new CMAssignStructureTestDataToTDStructureModelEdit(structure,createStructure);
	}

	/* (non-Javadoc)
	 * @see model.edit.CMModelEditFactory#createAddTestdataCOmbinationToTDStructureModelEdit(model.TDStructure, model.TestDataCombinations)
	 */
	public UndoableEdit createAddTestdataCOmbinationToTDStructureModelEdit(TDStructure newTDStructure, TestDataCombinations testDataCombination) {

		return new CMAssignTestDataCombinationToTDStructureModelEdit(newTDStructure,testDataCombination);
	}

	/* (non-Javadoc)
	 * @see model.edit.CMModelEditFactory#createChangeNameTestDataModelEdit(model.TestData, java.lang.String)
	 */
	public UndoableEdit createChangeNameTestDataModelEdit(TestData newTestData, String testDataName) {

		return new CMTestDataEditNameModelEdit(newTestData,testDataName);
	}

	/* (non-Javadoc)
	 * @see model.edit.CMModelEditFactory#createChangeDescriptionTestDataModelEdit(model.TestData, java.lang.String)
	 */
	public UndoableEdit createChangeDescriptionTestDataModelEdit(TestData newTestData, String testdataDescrip) {

		return new CMTestDataEditDescriptionModelEdit(newTestData,testdataDescrip);
	}

	/* (non-Javadoc)
	 * @see model.edit.CMModelEditFactory#createAddTestCaseInTestDataModelEdit(model.TestData, java.lang.String)
	 */
	public UndoableEdit createAddTestCaseInTestDataModelEdit(TestData newTestData, String testCaseInTestData) {

		return new CMTestDataEditTestCaseInTestDataModelEdit(newTestData,testCaseInTestData);
	}

	/* (non-Javadoc)
	 * @see model.edit.CMModelEditFactory#createChangeRiskLevelInTestDataModelEdit(model.TestData, int)
	 */
	public UndoableEdit createChangeRiskLevelInTestDataModelEdit(TestData newTestData, int riskLevel) {

		return new CMTestDataEditRiskLevelModelEdit(newTestData,riskLevel);
	}

	/* (non-Javadoc)
	 * @see model.edit.CMModelEditFactory#createChangeDescriptionInStructureTestData(model.StructureTestData, java.lang.String)
	 */
	public UndoableEdit createChangeDescriptionInStructureTestData(StructureTestData structureTestData, String descrip) {

		return new CMStructureEditDescriptionModelEdit(structureTestData,descrip);
	}

	/* (non-Javadoc)
	 * @see model.edit.CMModelEditFactory#createAddTestDataToTDStructureModelEdit(model.TDStructure, model.TestData)
	 */
	public UndoableEdit createAddTestDataToTDStructureModelEdit(TDStructure structure, TestData newTestData) {

		return new CMAddTestDataToTDStructureModelEdit(structure,newTestData);
	}

	/* (non-Javadoc)
	 * @see model.edit.CMModelEditFactory#createAddElementToidSet(java.util.Vector, int)
	 */
	public UndoableEdit createAddElementToidSet(IdSet ids, int i) {

		return new CMAddElementStructureToIdSetModelEdit(ids,i);
	}

	/* (non-Javadoc)
	 * @see model.edit.CMModelEditFactory#createChangeIdInTDStructureModelEdit(model.TDStructure, int)
	 */
	public UndoableEdit createChangeIdInTDStructureModelEdit(TDStructure structure, int id) {

		return new CMChangeIdsInTDStructureModelEdit(structure,id);
	}

	/* (non-Javadoc)
	 * @see model.edit.CMModelEditFactory#createChangeIDStructureTestDataModelEdit(model.StructureTestData, int)
	 */
	public UndoableEdit createChangeIDStructureTestDataModelEdit(StructureTestData data, int id) {

		return new CMChangeIdInStructureTestDataModelEdit(data,id);
	}

	/* (non-Javadoc)
	 * @see model.edit.CMModelEditFactory#createChangeStrucTestDataToResultStructTestData(model.ResultStructureTestData, model.StructureTestData)
	 */
	public UndoableEdit createChangeStrucTestDataToResultStructTestData(ResultStructureTestData createStructure, StructureTestData createStructure2) {

		return new CMChangeStructureTestDataToResultStructureTDModelEdit(createStructure,createStructure2);
	}

	/* (non-Javadoc)
	 * @see model.edit.CMModelEditFactory#createDeleteIObserverFromDelegateObservable(model.DelegateObservable, model.ITypeData)
	 */
	public UndoableEdit createDeleteIObserverFromDelegateObservable(DelegateObservable observers, ITypeData s) {

		return new CMDelegateObservableDeleteObserverModelEdit(observers,s);
	}

	/* (non-Javadoc)
	 * @see model.edit.CMModelEditFactory#createChangeValueInTestDataFormatModelEdit(model.TestDataFormat, java.lang.String)
	 */
	public UndoableEdit createChangeValueInTestDataFormatModelEdit(TestDataFormat formatter, String value) {

		return new CMChangeValueInTestDataFormatModelEdit(formatter,value);
	}

	/* (non-Javadoc)
	 * @see model.edit.CMModelEditFactory#createDeleteElementToidSet(model.IdSet, int)
	 */
	public UndoableEdit createDeleteElementToidSet(IdSet set, int i) {

		return new CMDeleteElementStructureToIdSetModelEdit(set,i);
	}

	/* (non-Javadoc)
	 * @see model.edit.CMModelEditFactory#createChangeIdTestDataSetInTDStructureModelEdit(model.TDStructure, int)
	 */
	public UndoableEdit createChangeIdTestDataSetInTDStructureModelEdit(TDStructure structure, int i) {

		return new CMChangeIdTestDataSetInTDStructureModelEdit(structure,i);
	}

	/* (non-Javadoc)
	 * @see model.edit.CMModelEditFactory#createChangeStructureTestDataInTDStructure(model.TDStructure, java.util.Vector)
	 */
	public UndoableEdit createChangeStructureTestDataInTDStructure(TDStructure tdStructure, Vector std) {

		return new CMChangeStructureTestDataInTDStructureModelEdit(tdStructure,std);
	}

	/* (non-Javadoc)
	 * @see model.edit.CMModelEditFactory#createAddTestDataSetToTDStructureModelEdit(model.TDStructure, java.util.Vector)
	 */
	public UndoableEdit createAddTestDataSetToTDStructureModelEdit(TDStructure tdStructure, Vector testdataset) {

		return new CMChangeTestDataSetToTDStructureModelEdit(tdStructure,testdataset);
	}

	/* (non-Javadoc)
	 * @see model.edit.CMModelEditFactory#createChangeNewColumnsHeaderInStructureTestDataModelEdit(model.StructureTestData, java.util.Vector)
	 */
	public UndoableEdit createChangeNewColumnsHeaderInStructureTestDataModelEdit(StructureTestData testData, Vector newColumnsHeader) {

		return new CMChangeColumnsHeaderInStructureTestDataModelEdit(testData,newColumnsHeader);
	}

	/* (non-Javadoc)
	 * @see model.edit.CMModelEditFactory#createAddTypeDataToStructureTestDataOnlyModelEdit(model.StructureTestData, model.ITypeData)
	 */
	public UndoableEdit createAddTypeDataToStructureTestDataOnlyModelEdit(StructureTestData testData2, ITypeData typeData2) {

		return new CMTypeDataToStructureTestDataOnlyModelEdit(testData2,typeData2);
	}

	/* (non-Javadoc)
	 * @see model.edit.CMModelEditFactory#createRemoveElementAtInStructureTestDataWithTwoParamModelEdit(model.StructureTestData, int)
	 */
	public UndoableEdit createRemoveElementAtInStructureTestDataWithTwoParamModelEdit(StructureTestData testData2, int numofname) {

		return new CMRemoveElementAtInStructureWithTwoParamTestData(testData2,numofname);
	}

	/* (non-Javadoc)
	 * @see model.edit.CMModelEditFactory#createChangeIdInTestDataModelEdit(model.TestData, int)
	 */
	public UndoableEdit createChangeIdInTestDataModelEdit(TestData data, int id) {

		return new CMTestDataEditIDModelEdit(data,id);
	}

	/* (non-Javadoc)
	 * @see model.edit.CMModelEditFactory#createChangeTestCaseInTDStructureModelEdit(model.TDStructure, java.util.Vector)
	 */
	public UndoableEdit createChangeTestCaseInTDStructureModelEdit(TDStructure testData, Vector testCaseIntestData) {

		return new CMChangeTestCasesInTDStructureModelEdit(testData, testCaseIntestData);
	}

	/* (non-Javadoc)
	 * @see model.edit.CMModelEditFactory#createChangeIdTestDataInTDStructureModelEdit(model.StructureTestData, int)
	 */
	public UndoableEdit createChangeIdTestDataInTDStructureModelEdit(StructureTestData structure, int i) {

		return new CMChangeIdTestDataInStructureTestDataModelEdit(structure,i);
	}

	/* (non-Javadoc)
	 * @see model.edit.CMModelEditFactory#createAddObserverInTypeDataWithiTwoParamModelEdit(model.ITypeData, model.TypeDataLocal)
	 */
	public UndoableEdit createAddObserverInTypeDataWithiTwoParamModelEdit(ITypeData subjects, TypeDataLocal newtypData) {

		return new CMAddObserverWithTwoParamInTypeDataModelEdit(subjects,newtypData);
	}

	/* (non-Javadoc)
	 * @see model.edit.CMModelEditFactory#createRemoveStructureTestDataFromTDStructure(model.TDStructure, int)
	 */
	public UndoableEdit createRemoveStructureTestDataFromTDStructure(TDStructure structure, int index) {

		return new CMRemoveSTDFromTDStructureModelEdit(structure,index);
	}

	/* (non-Javadoc)
	 * @see model.edit.CMModelEditFactory#createRemoveTestDataFromTestDataCombination(model.TestDataCombinations, int)
	 */
	public UndoableEdit createRemoveTestDataFromTestDataCombination(TestDataCombinations testDataCombination, int i) {

		return new CMRemoveTestDataFromTestDataCombination(testDataCombination,i);
	}

	/* (non-Javadoc)
	 * @see model.edit.CMModelEditFactory#createDeleteTestDataSetFromResultComparison(model.ResultComparation, java.lang.String)
	 */
	public UndoableEdit createDeleteTestDataSetFromResultComparison(ResultComparation resultComparation, String name) {

		return new CMDeleteTestDataSetFromResultComparisionModelEdit(resultComparation,name);
	}

	/* (non-Javadoc)
	 * @see model.edit.CMModelEditFactory#createDeleteTestDataSetTargetFromResultComparison(model.ResultComparation, java.lang.String)
	 */
	public UndoableEdit createDeleteTestDataSetTargetFromResultComparison(ResultComparation resultComparation, String name) {

		return new CMDeleteTestDataSetTargetFromResultComparisonModelEdit(resultComparation,name);
	}

	/* (non-Javadoc)
	 * @see model.edit.CMModelEditFactory#createRemoveElementTestDataSetAtFromTestDataSet(model.TDStructure, int)
	 */
	public UndoableEdit createRemoveElementTestDataSetAtFromTestDataSet(TDStructure structure, int k) {

		return new CMRemoveTestDataSetElementAtFromTDStructureModelEdit(structure,k);
	}

	/* (non-Javadoc)
	 * @see model.edit.CMModelEditFactory#createChangeContTestDataSetInTestData(model.TestData, int)
	 */
	public UndoableEdit createChangeContTestDataSetInTestData(TestData td, int i) {

		return new CMChangeCOntTestDataSetInTestDataModelEdit(td,i);
	}

	/* (non-Javadoc)
	 * @see model.edit.CMModelEditFactory#createChangeIsSetTestDataModelEdit(model.TestData, boolean)
	 */
	public UndoableEdit createChangeIsSetTestDataModelEdit(TestData td, boolean b) {

		return new CMChangeIsSetInTestDataModelEdit(td,b);
	}

	/* (non-Javadoc)
	 * @see model.edit.CMModelEditFactory#createAddTestDatasToTestDataCombination(model.TestDataCombinations, java.util.Vector)
	 */
	public UndoableEdit createAddTestDatasToTestDataCombination(TestDataCombinations testDataCombinations, Vector testDatas) {

		return new CMAddTestDatasToTestDataCombinationModelEdit(testDataCombinations,testDatas);
	}

	/* (non-Javadoc)
	 * @see model.edit.CMModelEditFactory#createChangeDescriptionInTestDataSet(model.TestDataSet, java.lang.String)
	 */
	public UndoableEdit createChangeDescriptionInTestDataSet(TestDataSet testDataSet, String text) {

		return new CMChangeDescInTestDataSetModelEdit(testDataSet,text);
	}

	/* (non-Javadoc)
	 * @see model.edit.CMModelEditFactory#createAddTestDataSetSingleToTDStructure(model.TDStructure, model.TestDataSet)
	 */
	public UndoableEdit createAddTestDataSetSingleToTDStructure(TDStructure structure, TestDataSet testDataSet) {

		return new CMAddTestDataSetToTDStructureModelEdit(structure,testDataSet);
	}

	/* (non-Javadoc)
	 * @see model.edit.CMModelEditFactory#createDeleteTestDataSetFromTDStructure(model.TDStructure, model.TestDataSet)
	 */
	public UndoableEdit createDeleteTestDataSetFromTDStructure(TDStructure structure, TestDataSet testDataSet) {

		return new CMRemoveTestDataSetFromTDStructureModelEdit(structure,testDataSet);
	}


	/* (non-Javadoc)
	 * @see model.edit.CMModelEditFactory#createAddCombinationModelEdit(model.util.CMCombinationsBean, model.Combination)
	 */
	public UndoableEdit createAddCombinationModelEdit(CMCombinationsBean p_bean, Combination p_combination) {
		return new CMAddCombinationModelEdit(p_bean,p_combination);
	}

	/* (non-Javadoc)
	 * @see model.edit.CMModelEditFactory#createChangeTestCaseDependenciesModelEdit(model.TestCase, java.util.Vector)
	 */
	public UndoableEdit createChangeTestCaseDependenciesModelEdit(TestCase p_testCase, Vector p_deps) {
		return new CMChangeTestCaseDependenciesModelEdit(p_testCase,p_deps);
	}

	/* (non-Javadoc)
	 * @see model.edit.CMModelEditFactory#createChangeTestCaseGroupModelEdit(model.TestCase, model.TestCaseGroup)
	 */
	public UndoableEdit createChangeTestCaseGroupModelEdit(TestCase p_testCase, TestCaseGroup p_group) {
		return new CMChangeTestCaseGroupModelEdit(p_testCase,p_group);
	}

	/* (non-Javadoc)
	 * @see model.edit.CMModelEditFactory#createChangeOriginTypeModelEdit(model.util.CMOriginTypeBean, java.lang.String)
	 */
	public UndoableEdit createChangeOriginTypeModelEdit(CMOriginTypeBean p_bean, Origin p_origin) {
		return new CMChangeOriginTypeModelEdit(p_bean,p_origin);
	}

	/* (non-Javadoc)
	 * @see model.edit.CMModelEditFactory#createChangeUserOrderModelEdit(model.util.CMUserOrderBean, int)
	 */
	public UndoableEdit createChangeUserOrderModelEdit(CMUserOrderBean p_element, int p_order) {
		return new CMChangeUserOrderModelEdit(p_element,p_order);
	}

	/* (non-Javadoc)
	 * @see model.edit.CMModelEditFactory#createRemoveElementModelEdit(model.util.CMElementsBean, model.Element)
	 */
	public UndoableEdit createRemoveElementModelEdit(CMElementsBean p_Bean, Element p_element) {

		return new CMRemoveElementModelEdit(p_Bean,p_element);
	}

	/* (non-Javadoc)
	 * @see model.edit.CMModelEditFactory#createRemoveDependencyModelEdit(model.util.CMDependencyBean, model.Dependency)
	 */
	public UndoableEdit createRemoveDependencyModelEdit(CMDependencyBean p_bean, Dependency p_dependency) {
		return new CMRemoveDependencyModelEdit(p_bean, p_dependency);
	}

	/* (non-Javadoc)
	 * @see model.edit.CMModelEditFactory#createRemoveEquivalenceClassModelEdit(model.util.CMEquivalenceClassesBean, model.EquivalenceClass)
	 */
	public UndoableEdit createRemoveEquivalenceClassModelEdit(CMEquivalenceClassesBean p_bean, EquivalenceClass p_equivalenceClass) {
		return new CMRemoveEquivalenceClassModelEdit(p_bean,p_equivalenceClass);
	}

	public UndoableEdit createAddTypeDataToStructureTDModelEdit(StructureTestData structureTD, ITypeData newTypeData2) {

		return new CMAddTypeDataToStructureTDModelEdit(structureTD,newTypeData2);
	}

	/* (non-Javadoc)
	 * @see model.edit.CMModelEditFactory#createChangeIdsIdSet(model.IdSet, java.lang.Object)
	 */
	public UndoableEdit createChangeIdsIdSet(IdSet p_set, Vector p_vector) {
		return new CMChangeIdsIdSetModelEdit(p_set,p_vector);
	}

	public UndoableEdit createChangeValueModelEdit(CMValueBean bean, String text) {
		return new CMChangeValueModelEdit(bean,text);
	}

	public UndoableEdit createAddElementModelEdit(CMElementsBean p_Bean, Element p_element) {

		return new CMAddElementModelEdit(p_Bean,p_element);
	}
	public UndoableEdit createChangeDescriptionEditableModelEdit(CMDescriptionEditableBean bean, String text) {
		return new CMChangeDescriptionEditableModelEdit(bean,text);
	}

	public UndoableEdit createChangeTypeModelEdit(CMTypeBean typedata, Type newType){
		return new CMChangeTypeModelEdit(typedata,newType);
	}

	public UndoableEdit createAddDependencyModelEdit(CMDependencyBean model, Dependency dependency) {

		return new CMAddDependencyModelEdit(model,dependency);
	}

	public UndoableEdit createChangeGUIObjectModelEdit(Element element, int newGUIObject) {

		return new CMChangeGUIObjectModelEdit(element,newGUIObject);
	}

	public UndoableEdit createAddTestCaseModelEdit(TestCasesBean bean, TestCase t) {
		
		return new CMAddTestCaseModelEdit(bean,t);
	}

	public UndoableEdit createAddErrorModelEdit(CMErrorsBean parent,
			CMError error) {

		return new CMAddErrorModelEdit(parent,error);
	}

	public UndoableEdit createRemoveErrorModelEdit(CMErrorsBean parent,
			CMError error) {

		return new CMRemoveErrorModelEdit(parent,error);
	}
}
