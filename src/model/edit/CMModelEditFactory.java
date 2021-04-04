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

public interface CMModelEditFactory {

	public static final CMModelEditFactory INSTANCE = new CMModelEditFactoryImpl();

	UndoableEdit createDeleteTestCaseModelEdit(Structure structure, TestCase testCase);

	UndoableEdit createRemoveCombinationModelEdit(CMCombinationsBean dependency, Combination combination);

	UndoableEdit createAddEffectModelEdit(CMEffectsBean model, Effect effect);

	UndoableEdit createAddDependencyModelEdit(CMDependencyBean model, Dependency dependency);

	UndoableEdit createChangeCombinationParentModelEdit(Combination combination, Combination parent);

	UndoableEdit createChangeStateModelEdit(CMStateBean combination, State newState);

	UndoableEdit createDeleteTestCaseModelEdit(CMError error, TestCase testCase);

	UndoableEdit createDeleteEffectModelEdit(CMEffectsBean combination, Effect effect);

	/**
	*@autor smoreno
	 * @param p_structure
	 * @param p_element
	 * @return
	 */
//ccastedo 31.10.06	UndoableEdit createAddElementModelEdit(Structure p_structure, Element p_element);

	/**
	*@autor smoreno
	 * @return
	 */
	UndoableEdit createChangeNameModelEdit(CMNameBean p_element, String name);

	/**
	*@autor smoreno
	 * @param p_element
	 * @param p_string
	 * @return
	 */
	UndoableEdit createChangeDescriptionModelEdit(CMDescriptionBean p_model, String p_description);

	/**
	*@autor smoreno
	 * @param p_equivalenceClass
	 * @param p_i
	 * @return
	 */
	UndoableEdit createChangeRiskLevelModelEdit(CMRiskLevelBean p_model, int p_level);

	/**
	*@autor smoreno
	 * @param p_equivalenceClass
	 * @param p_stringValue
	 * @return
	 */
	UndoableEdit createEquivalenceClassChangeValueModelEdit(EquivalenceClass p_equivalenceClass, String p_stringValue);

	/**
	*@autor smoreno
	 * @param p_element
	 * @param p_equivalenceClass
	 * @return
	 */
	UndoableEdit createAddEquivalenceClassModelEdit(CMEquivalenceClassesBean p_bean, EquivalenceClass p_equivalenceClass);

	/**
	*@autor smoreno
	 * @param p_currentSession
	 * @param p_workspace
	 * @return
	 */
	UndoableEdit createAddWorkspaceModelEdit(Session2 p_currentSession, Workspace2 p_workspace);

	/**
	*@autor smoreno
	 * @param p_workspace
	 * @param p_i
	 * @return
	 */
	UndoableEdit createChangeIdModelEdit(CMIdBean p_idBean, int p_i);

	/**
	*@autor smoreno
	 * @param p_selectedWorkspace2
	 * @return
	 */
	UndoableEdit createDeleteWorkspaceModelEdit(Workspace2 p_selectedWorkspace2);


	//svonborries_23022006_begin
	UndoableEdit createChangeFieldInTypeDataModelEdit (ITypeData p_typeData, String p_Value);

	UndoableEdit createChangeFormatInTypeDataModelEdit(ITypeData p_typeData, String p_Value);

	UndoableEdit createChangeFormulaInTypeDataModelEdit (ITypeData p_typeData, String p_Value);

	UndoableEdit createChangeKeyInTypeDataModelEdit(ITypeData p_typeData, String p_Value);

	UndoableEdit createChangeLengthInTypeDataModelEdit(ITypeData p_typeData, String p_Value);

	UndoableEdit createChangeNameInTypeDataModelEdit(ITypeData p_typeData, String p_Value);

	UndoableEdit createChangeNewColumnInTypeDataModelEdit(ITypeData p_typeData, String p_Value, int p_Column);

	UndoableEdit createChangeObjectTypeInTypeDataModelEdit (ITypeData p_typeData, int p_Value/*ccastedo 27.09.06  String p_Value*/);

	UndoableEdit createChangePrefixInTypeDataModelEdit (ITypeData p_typeData, String p_Value);

	UndoableEdit createChangeSuffixInTypeDataModelEdit(ITypeData p_typeData, String p_Value);

	UndoableEdit createChangeTypeInTypeDataModelEdit(ITypeData p_typeData, String p_Value);

	UndoableEdit createChangeValueInTypeDataModelEdit(ITypeData p_typeData, ICMValue p_Value);
	//svonborries_23022006_end

	UndoableEdit createChangeGlobalReferenceInTypeDataModelEdit (ITypeData p_typeData, String p_Value);//svonborries_02032006

	UndoableEdit createChangeFormatterInTypeDataModelEdit(ITypeData p_typeData, TestDataFormat p_Formatter);//svonborries_08032006

	UndoableEdit createChangeToolVendorOTInTypeDataModelEdit(ITypeData p_typeData, int stateOT/* ccastedo 27.09.06 String p_ToolVendor*/);//svonborries_09032006

	UndoableEdit createChangeNewColumnsInTypeDataModelEdit(ITypeData p_typeData, Vector p_newColumns);//svonborries_09032006

	UndoableEdit createChangeisFormulaInTypeDataModelEdit(ITypeData p_typeData, boolean p_value);//svonborries_10032006

	UndoableEdit createChangeisLinkElementInTypeDataModelEdit(ITypeData p_typeData, boolean p_value);//svonborries_15032006

	UndoableEdit createDeleteObserverInTypeDataModelEdit(ITypeData p_observerTypeData, ITypeData p_observableTypeData, String p_key);//svonborries_15032006

	UndoableEdit createAddObserverInTypeDataModelEdit(ITypeData p_observerTypeData, ITypeData p_observableTypeData, String p_key);//svonborries_15032006

	UndoableEdit createAddElementInStructureTestDataModelEdit(ITypeData p_typeData, StructureTestData p_structureTestData, int p_indexTyp);//svonborries_15032006

	UndoableEdit createInsertElementAtInStructureTestDataModelEdit(ITypeData p_typeData, StructureTestData p_structureTestData, int p_row);//svonborries_15032006

	UndoableEdit createChangeStructureTestDataInTypeDataModelEdit(ITypeData p_typeData, StructureTestData p_structureTestData);//svonborries_15032006

	UndoableEdit createChangeReferenceTypeDataInTypeDataModelEdit(ITypeData p_typedatalocal, ITypeData p_typedataglobal);//svonborries_15032006

	UndoableEdit createRemoveElementInStructureTestDataModelEdit(ITypeData p_typeData, StructureTestData p_structureTestData);//svonborries_15032006

	UndoableEdit createRemoveElementAtInStructureTestDataModelEdit(ITypeData p_typeData, StructureTestData p_structureTestData, int p_row);//svonborries_15032006

	UndoableEdit createAddObserverVariableInTypeDataModelEdit(Variable p_variable, ITypeData p_typeData);

	/**
	*@autor smoreno
	 * @param p_session
	 * @param p_workspace
	 * @param p_index
	 * @return
	 */
	UndoableEdit createAddWorkspaceModelEdit(Session2 p_session, Workspace2 p_workspace, int p_index);

	/**
	*@autor smoreno
	 * @param p_session
	 * @param p_workspace
	 * @param p_index
	 * @return
	 */
	UndoableEdit createMoveWorkspaceModelEdit(Session2 p_session, Workspace2 p_workspace, int p_index);

	/**
	*@autor smoreno
	 * @param p_newProject
	 * @param p_access_state_checked_out
	 */
	UndoableEdit createChangeAccessStateModelEdit(CMAccessStateBean p_stateBean, String p_access_state_checked_out);

	/**
	*@autor smoreno
	 * @param p_newProject
	 * @param p_user
	 * @return
	 */
	UndoableEdit createChangeUserModelEdit(CMUserBean p_userBean, String p_user);


	/**
	*@autor smoreno
	 * @param p_newProjectReference
	 * @param p_string
	 * @return
	 */
	UndoableEdit createChangeTimeStampModelEdit(CMTimeStampBean p_bean, Date p_date);

	/**
	*@autor smoreno
	 * @param p_newProject
	 * @param p_newProjectReference
	 * @return
	 */
	UndoableEdit createBindProjectAndReferenceModelEdit(Project2 p_newProject, ProjectReference p_newProjectReference);

	/**
	*@autor smoreno
	 * @param p_workspace2
	 * @param p_projectReference
	 * @return
	 */
	UndoableEdit createAddProjectReferenceModelEdit(Workspace2 p_workspace2, ProjectReference p_projectReference);

	/**
	*@autor smoreno
	 * @param p_selectedProjectReference
	 * @return
	 */
	UndoableEdit createDeleteProjectReferenceModelEdit(ProjectReference p_selectedProjectReference);

	/**
	*@autor smoreno
	 * @param p_workspace
	 * @param p_newName
	 * @param p_delegate
	 * @return
	 */
	UndoableEdit createChangeNameModelEdit(CMNameBean p_workspace, String p_newName, CMDelegate p_delegate);


	/**
	*@autor smoreno
	 * @param p_projectReference
	 * @param p_newName
	 * @param p_newPath
	 * @param p_project
	 * @param p_delegate
	 * @return
	 */
	UndoableEdit createChangeFilePathModelEdit(CMFilePathBean p_projectReference, String p_newName, String p_newPath, CMNameBean p_project, CMDelegate p_delegate);

	/**
	 * @param p_structure
	 * @param p_req
	 * @return
	 */
	UndoableEdit createAddRequirementModelEdit(CMRequirementBean p_parent, Requirement p_req);

	/**
	 * @param p_effect
	 * @param p_req
	 * @return
	 */
	UndoableEdit createRemoveRequirementModelEdit(CMRequirementBean p_effect, Requirement p_req);

	/**
	 * @param p_effect
	 * @param p_expRes
	 * @return
	 */
	UndoableEdit createAddExpectedResult(Effect p_effect, ExpectedResult p_expRes);

	/**
	 * @param p_effect
	 * @param p_exp
	 * @return
	 */
	UndoableEdit createRemoveExpectedResultModelEdit(Effect p_effect, ExpectedResult p_exp);

	/**
	 * @param createStructure
	 * @param typetdstructure_result
	 * @return
	 */
	UndoableEdit createChangeTypeInStructureTestData(StructureTestData createStructure, String typetdstructure_result);

	/**
	 * @param structureTestData
	 * @param name
	 * @return
	 */
	UndoableEdit createChangeNameInStructureTestData(StructureTestData structureTestData, String name);

	/**
	 * @param createStructure
	 * @param i
	 * @return
	 */
	UndoableEdit createChangeGlobalIndexInStructureTestData(StructureTestData createStructure, int i);

	/**
	 * @param createStructure
	 * @param newTypeData
	 * @return
	 */
	UndoableEdit createAddTypeDataToStructureTestDataModelEdit(StructureTestData createStructure, ITypeData newTypeData);

	/**
	 * @param structure
	 * @param createStructure
	 * @return
	 */
	UndoableEdit createAddStructureTestDataToTDStructureModelEdit(TDStructure structure, StructureTestData createStructure);

	/**
	 * @param newTDStructure
	 * @param testDataCombination
	 * @return
	 */
	UndoableEdit createAddTestdataCOmbinationToTDStructureModelEdit(TDStructure newTDStructure, TestDataCombinations testDataCombination);

	/**
	 * @param newTestData
	 * @param testDataName
	 * @return
	 */
	UndoableEdit createChangeNameTestDataModelEdit(TestData newTestData, String testDataName);

	/**
	 * @param newTestData
	 * @param testdataDescrip
	 * @return
	 */
	UndoableEdit createChangeDescriptionTestDataModelEdit(TestData newTestData, String testdataDescrip);

	/**
	 * @param newTestData
	 * @param testCaseInTestData
	 * @return
	 */
	UndoableEdit createAddTestCaseInTestDataModelEdit(TestData newTestData, String testCaseInTestData);

	/**
	 * @param newTestData
	 * @param riskLevel
	 * @return
	 */
	UndoableEdit createChangeRiskLevelInTestDataModelEdit(TestData newTestData, int riskLevel);

	/**
	 * @param structureTestData
	 * @param descrip
	 * @return
	 */
	UndoableEdit createChangeDescriptionInStructureTestData(StructureTestData structureTestData, String descrip);

	/**
	 * @param structure
	 * @param newTestData
	 * @return
	 */
	UndoableEdit createAddTestDataToTDStructureModelEdit(TDStructure structure, TestData newTestData);

	/**
	 * @param set
	 * @param i
	 * @return
	 */
	UndoableEdit createAddElementToidSet(IdSet set, int i);

	/**
	 * @param structure
	 * @param id
	 * @return
	 */
	UndoableEdit createChangeIdInTDStructureModelEdit(TDStructure structure, int id);

	/**
	 * @param data
	 * @param id
	 * @return
	 */
	UndoableEdit createChangeIDStructureTestDataModelEdit(StructureTestData data, int id);

	/**
	 * @param createStructure
	 * @param createStructure2
	 * @return
	 */
	UndoableEdit createChangeStrucTestDataToResultStructTestData(ResultStructureTestData createStructure, StructureTestData createStructure2);

	/**
	 * @param observers
	 * @param s
	 * @return
	 */
	UndoableEdit createDeleteIObserverFromDelegateObservable(DelegateObservable observers, ITypeData s);

	/**
	 * @param formatter
	 * @param value
	 * @return
	 */
	UndoableEdit createChangeValueInTestDataFormatModelEdit(TestDataFormat formatter, String value);

	/**
	 * @param set
	 * @param i
	 * @return
	 */
	UndoableEdit createDeleteElementToidSet(IdSet set, int i);

	/**
	 * @param structure
	 * @param i
	 * @return
	 */
	UndoableEdit createChangeIdTestDataSetInTDStructureModelEdit(TDStructure structure, int i);

	/**
	 * @param tdStructure
	 * @param std
	 * @return
	 */
	UndoableEdit createChangeStructureTestDataInTDStructure(TDStructure tdStructure, Vector std);

	/**
	 * @param tdStructure
	 * @param testdataset
	 * @return
	 */
	UndoableEdit createAddTestDataSetToTDStructureModelEdit(TDStructure tdStructure, Vector testdataset);

	/**
	 * @param testData
	 * @param newColumnsHeader
	 * @return
	 */
	UndoableEdit createChangeNewColumnsHeaderInStructureTestDataModelEdit(StructureTestData testData, Vector newColumnsHeader);

	/**
	 * @param testData2
	 * @param typeData2
	 * @return
	 */
	UndoableEdit createAddTypeDataToStructureTestDataOnlyModelEdit(StructureTestData testData2, ITypeData typeData2);

	/**
	 * @param testData2
	 * @param numofname
	 * @return
	 */
	UndoableEdit createRemoveElementAtInStructureTestDataWithTwoParamModelEdit(StructureTestData testData2, int numofname);

	/**
	 * @param data
	 * @param id
	 * @return
	 */
	UndoableEdit createChangeIdInTestDataModelEdit(TestData data, int id);

	/**
	 * @param testData
	 * @param testCaseIntestData
	 * @return
	 */
	UndoableEdit createChangeTestCaseInTDStructureModelEdit(TDStructure testData, Vector testCaseIntestData);

	/**
	 * @param structure
	 * @param i
	 * @return
	 */
	UndoableEdit createChangeIdTestDataInTDStructureModelEdit(StructureTestData structure, int i);

	/**
	 * @param subjects
	 * @param newtypData
	 * @return
	 */
	UndoableEdit createAddObserverInTypeDataWithiTwoParamModelEdit(ITypeData subjects, TypeDataLocal newtypData);

	/**
	 * @param structure
	 * @param index
	 * @return
	 */
	UndoableEdit createRemoveStructureTestDataFromTDStructure(TDStructure structure, int index);

	/**
	 * @param testDataCombination
	 * @param i
	 * @return
	 */
	UndoableEdit createRemoveTestDataFromTestDataCombination(TestDataCombinations testDataCombination, int i);

	/**
	 * @param resultComparation
	 * @param name
	 * @return
	 */
	UndoableEdit createDeleteTestDataSetFromResultComparison(ResultComparation resultComparation, String name);

	/**
	 * @param resultComparation
	 * @param name
	 * @return
	 */
	UndoableEdit createDeleteTestDataSetTargetFromResultComparison(ResultComparation resultComparation, String name);

	/**
	 * @param structure
	 * @param k
	 * @return
	 */
	UndoableEdit createRemoveElementTestDataSetAtFromTestDataSet(TDStructure structure, int k);

	/**
	 * @param td
	 * @param i
	 * @return
	 */
	UndoableEdit createChangeContTestDataSetInTestData(TestData td, int i);

	/**
	 * @param td
	 * @param b
	 * @return
	 */
	UndoableEdit createChangeIsSetTestDataModelEdit(TestData td, boolean b);

	/**
	 * @param testDataCombinations
	 * @param testDatas
	 * @return
	 */
	UndoableEdit createAddTestDatasToTestDataCombination(TestDataCombinations testDataCombinations, Vector testDatas);

	/**
	 * @param testDataSet
	 * @param text
	 * @return
	 */
	UndoableEdit createChangeDescriptionInTestDataSet(TestDataSet testDataSet, String text);

	/**
	 * @param structure
	 * @param testDataSet
	 * @return
	 */
	UndoableEdit createAddTestDataSetSingleToTDStructure(TDStructure structure, TestDataSet testDataSet);

	/**
	 * @param structure
	 * @param testDataSet
	 * @return
	 */
	UndoableEdit createDeleteTestDataSetFromTDStructure(TDStructure structure, TestDataSet testDataSet);
	/**
	 * @param p_testCase
	 * @param p_combination
	 * @return
	 */
	UndoableEdit createAddCombinationModelEdit(CMCombinationsBean p_bean, Combination p_combination);

	/**
	 * @param p_testCase
	 * @param p_deps
	 * @return
	 */
	UndoableEdit createChangeTestCaseDependenciesModelEdit(TestCase p_testCase, Vector p_deps);

	/**
	 * @param p_testCase
	 * @param p_group
	 * @return
	 */
	UndoableEdit createChangeTestCaseGroupModelEdit(TestCase p_testCase, TestCaseGroup p_group);

	/**
	 * @param p_testCase
	 * @param p_test_case_origin_type_automatic
	 * @return
	 */
	UndoableEdit createChangeOriginTypeModelEdit(CMOriginTypeBean p_bean, Origin origin);

	/**
	 * @param p_element
	 * @param p_i
	 * @return
	 */
	UndoableEdit createChangeUserOrderModelEdit(CMUserOrderBean p_element, int p_order);

	/**
	 * @param p_dependency
	 * @param p_element
	 * @return
	 */
	UndoableEdit createRemoveElementModelEdit(CMElementsBean p_Bean, Element p_element);

	/**
	 * @param p_testCase
	 * @param p_dependency
	 * @return
	 */
	UndoableEdit createRemoveDependencyModelEdit(CMDependencyBean p_bean, Dependency p_dependency);

	/**
	 * @param p_dependency
	 * @param p_equivalenceClass
	 * @return
	 */
	UndoableEdit createRemoveEquivalenceClassModelEdit(CMEquivalenceClassesBean p_bean, EquivalenceClass p_equivalenceClass);

	UndoableEdit createAddTypeDataToStructureTDModelEdit(StructureTestData structureTD, ITypeData newTypeData2);

	UndoableEdit createChangeIdsIdSet(IdSet p_set, Vector p_vector);

	UndoableEdit createChangeValueModelEdit(CMValueBean bean, String text);

	UndoableEdit createChangeDescriptionEditableModelEdit(CMDescriptionEditableBean bean, String text);

	UndoableEdit createAddElementModelEdit(CMElementsBean p_Bean, Element p_element);

	UndoableEdit createChangeTypeModelEdit(CMTypeBean typedata, Type newType);

	UndoableEdit createChangeGUIObjectModelEdit(Element element, int newGUIObject);

	UndoableEdit createAddTestCaseModelEdit(TestCasesBean bean, TestCase t);

	UndoableEdit createRemoveErrorModelEdit(CMErrorsBean parent, CMError error);

	UndoableEdit createAddErrorModelEdit(CMErrorsBean parent, CMError error);


}
