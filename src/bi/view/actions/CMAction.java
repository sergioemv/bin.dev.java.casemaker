/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.view.actions;

import java.awt.event.ActionEvent;


import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;


import org.apache.log4j.Logger;


import bi.view.actions.businessrules.CMBusinessRulesCheckAction;
import bi.view.actions.businessrules.CMBusinessRulesCheckAndGenerateAction;
import bi.view.actions.businessrules.CMBusinessRulesClearAllAction;
import bi.view.actions.businessrules.CMBusinessRulesGenerateAction;
import bi.view.actions.businessrules.CMBusinessRulesImportAction;
import bi.view.actions.businessrules.CMBusinessRulesSaveAsAction;
import bi.view.actions.combination.CMCombinationAssingToTestCaseAction;
import bi.view.actions.combination.CMCombinationCreateAction;
import bi.view.actions.combination.CMCombinationCreateAssingEffectsAction;
import bi.view.actions.combination.CMCombinationDeleteAction;
import bi.view.actions.combination.CMCombinationDeleteAllAction;
import bi.view.actions.combination.CMCombinationEditAction;
import bi.view.actions.combination.CMCombinationEditEffectsAction;
import bi.view.actions.combination.CMCombinationMergeAction;
import bi.view.actions.combination.CMCombinationUnMergeAction;
import bi.view.actions.dependency.CMDependencyCreateAction;
import bi.view.actions.dependency.CMDependencyDeleteAction;
import bi.view.actions.dependency.CMDependencyEditAction;
import bi.view.actions.dependency.CMDependencyGenerateCombAction;
import bi.view.actions.dependency.CMDependencyGenerateCombAllPairsAction;
import bi.view.actions.dependency.CMDependencyGenerateCombPermutationAction;
import bi.view.actions.edit.CMCopyAction;
import bi.view.actions.edit.CMCutAction;
import bi.view.actions.edit.CMFindReplaceAction;
import bi.view.actions.edit.CMPasteAction;
import bi.view.actions.edit.CMRedoAction;
import bi.view.actions.edit.CMSelectAllAction;
import bi.view.actions.edit.CMUndoAction;
import bi.view.actions.effect.CMEffectCreateAction;
import bi.view.actions.effect.CMEffectDeleteAction;
import bi.view.actions.effect.CMEffectDeleteNotUsedAction;
import bi.view.actions.effect.CMEffectEditAction;
import bi.view.actions.element.CMElementCreateAction;
import bi.view.actions.element.CMElementDeleteAction;
import bi.view.actions.element.CMElementInsertAction;
import bi.view.actions.element.CMElementReorderAction;
import bi.view.actions.equivalenceclass.CMEquivalenceClassCreateAction;
import bi.view.actions.equivalenceclass.CMEquivalenceClassCreateAssignEffectAction;
import bi.view.actions.equivalenceclass.CMEquivalenceClassDeleteAction;
import bi.view.actions.equivalenceclass.CMEquivalenceClassEditAction;
import bi.view.actions.equivalenceclass.CMEquivalenceClassEditAssignedEffectsAction;
import bi.view.actions.error.CMErrorAssignTestCasesAction;
import bi.view.actions.error.CMErrorCreateAction;
import bi.view.actions.error.CMErrorDeleteAction;
import bi.view.actions.error.CMErrorEditAction;
import bi.view.actions.file.CMAddOpenAction;
import bi.view.actions.file.CMCSVImportAction;
import bi.view.actions.file.CMCaseMakerSettingsAction;
import bi.view.actions.file.CMCreateAction;
import bi.view.actions.file.CMDeleteAction;
import bi.view.actions.file.CMExitAction;
import bi.view.actions.file.CMHideShowTreeAction;
import bi.view.actions.file.CMImportProjectAction;
import bi.view.actions.file.CMSaveAction;
import bi.view.actions.file.rename.CMRenameProjectAction;
import bi.view.actions.file.rename.CMRenameTestObjectAction;
import bi.view.actions.file.rename.CMRenameWorkspaceAction;
import bi.view.actions.file.reorder.CMReorderTestObjectsAction;
import bi.view.actions.file.reorder.CMReorderWorkspacesAction;
import bi.view.actions.help.CMAboutAction;
import bi.view.actions.help.CMHelpAction;
import bi.view.actions.help.CMOnTheWebAction;
import bi.view.actions.report.CMDependencyExcelReportAction;
import bi.view.actions.report.CMErrorReportListAction;
import bi.view.actions.report.CMTestCaseReportCompuwareAction;
import bi.view.actions.report.CMTestCaseReportList1Action;
import bi.view.actions.report.CMTestCaseReportList2Action;
import bi.view.actions.report.CMTestCaseReportQADirectorAction;
import bi.view.actions.resultcomparison.CMCreateResultComparisonAction;
import bi.view.actions.resultcomparison.CMEditResultComparisonAction;
import bi.view.actions.resultcomparison.CMResultComparisonReportAction;
import bi.view.actions.stdcombination.CMStdCombinationAssignEquivalenceClassAction;
import bi.view.actions.stdcombination.CMStdCombinationAssignTestCasesAction;
import bi.view.actions.stdcombination.CMStdCombinationCancelAssignEquivalenceClassAction;
import bi.view.actions.stdcombination.CMStdCombinationCreateAction;
import bi.view.actions.stdcombination.CMStdCombinationDeleteAction;
import bi.view.actions.stdcombination.CMStdCombinationEditAction;
import bi.view.actions.tdstructure.CMAssignGlobalStructureAction;
import bi.view.actions.tdstructure.CMCancelAssignGlobalStructureAction;
import bi.view.actions.tdstructure.CMCreateTDStructureAction;
import bi.view.actions.tdstructure.CMDeleteTDStructureAction;
import bi.view.actions.tdstructure.CMEditTDStructureAction;
import bi.view.actions.tdstructure.CMGenerateResultStructureAction;
import bi.view.actions.tdstructure.CMGenerateStructureAction;
import bi.view.actions.tdstructure.CMGenerateTestDataCombinationsFromAllTestCasesAction;
import bi.view.actions.tdstructure.CMGenerateTestDataCombinationsFromFaultyTestCasesAction;
import bi.view.actions.tdstructure.CMGenerateTestDataCombinationsFromNegativeTestCasesAction;
import bi.view.actions.tdstructure.CMGenerateTestDataCombinationsFromPositiveTestCasesAction;
import bi.view.actions.tdstructure.CMGenerateTestDataCombinationsFromSpecificTestCasesAction;
import bi.view.actions.testcase.CMTestCaseAssignCombinationAction;
import bi.view.actions.testcase.CMTestCaseAssignEquivalenceClassAction;
import bi.view.actions.testcase.CMTestCaseAssignErrorsAction;
import bi.view.actions.testcase.CMTestCaseAssignStdCombinationAction;
import bi.view.actions.testcase.CMTestCaseCancelAssignEquivalenceClassAction;
import bi.view.actions.testcase.CMTestCaseCreateAction;
import bi.view.actions.testcase.CMTestCaseDeleteAction;
import bi.view.actions.testcase.CMTestCaseDeleteAllAction;
import bi.view.actions.testcase.CMTestCaseEditAction;
import bi.view.actions.testcase.CMTestCaseGenerateAction;
import bi.view.actions.testdata.CMAssignTestCaseToTestDataAction;
import bi.view.actions.testdata.CMCancelTestCaseToTestDataAction;
import bi.view.actions.testdata.CMCreateTestDataAction;
import bi.view.actions.testdata.CMDeleteTestDataAction;
import bi.view.actions.testdata.CMEditTestDataAction;
import bi.view.actions.testdata.CMImportTestDataAction;
import bi.view.actions.testdataset.CMDeleteTestDataSetAction;
import bi.view.actions.testdataset.CMEditTestDataSetAction;
import bi.view.actions.testdataset.CMGenerateTestDataSetReportAction;
import bi.view.actions.testdataset.CMNewTestDataSetAction;
import bi.view.actions.testdataset.reports.CMAddReportAction;
import bi.view.actions.testdataset.reports.CMDeleteReportAction;
import bi.view.actions.testdataset.reports.CMEditReportAction;
import bi.view.actions.testdataset.reports.CMOpenReportAction;
import bi.view.actions.testdataset.reports.CMOpenReportFolderAction;
import bi.view.actions.testdataset.reports.CMUpdateReportAction;
import bi.view.actions.testobject.CMTestObjectOrderedFlowAction;
import bi.view.actions.typedata.CMAddFormulaAction;
import bi.view.actions.typedata.CMAddLinkElementAction;
import bi.view.actions.typedata.CMAddVariableAction;
import bi.view.actions.typedata.CMAssignFormattoTypeDataAction;
import bi.view.actions.typedata.CMAssignGlobalValueAction;
import bi.view.actions.typedata.CMAssignGlobalValueReferenceAction;
import bi.view.actions.typedata.CMCancelGlobalValueReferenceAction;
import bi.view.actions.typedata.CMChangeNameColumnAddedAction;
import bi.view.actions.typedata.CMDeleteColumnAddedAction;
import bi.view.actions.typedata.CMDeleteElementAction;
import bi.view.actions.typedata.CMDeleteFormulaAction;
import bi.view.actions.typedata.CMDeleteLinkElementAction;
import bi.view.actions.typedata.CMDeleteVariableAction;
import bi.view.actions.typedata.CMEditFormulaAction;
import bi.view.actions.typedata.CMInsertNewColumnAction;
import bi.view.actions.typedata.CMInsertNewElementAction;
import bi.view.actions.variable.CMCreateVariableAction;
import bi.view.actions.variable.CMEditVariableAction;

import bi.view.icons.CMIcon;



/**
 * @author smoreno
 * this class ennumerates all the actions that can be called in CM
 */
public enum CMAction {
	CREATE,
	ADDOPEN,
	SAVE,
	DELETE,
	IMPORT_PROJECT,
	CASEMAKER_SETTINGS,
	HIDE_SHOW_TREE,
	RENAME_WORKSPACE,
	RENAME_PROJECT,
	RENAME_TESTOBJECT,
	REORDER_WORKSPACES,
	REORDER_TESTOBJECTS,
	EXIT,
	UNDO,
	REDO,
	CUT,
	COPY,
	PASTE,
	TESTOBJECT_ORDERED_FLOWS,
	ELEMENT_CREATE,
	ELEMENT_DELETE,
	ELEMENT_INSERT,
	ELEMENT_REORDER,
	EQUIVALENCECLASS_CREATE,
	EQUIVALENCECLASS_EDIT,
	EQUIVALENCECLASS_DELETE,
	EQUIVALENCECLASS_CREATE_ASSIGN_EFFECT,
	EQUIVALENCECLASS_EDIT_ASSIGNED_EFFECTS,
	EFFECT_CREATE,
	EFFECT_EDIT,
	EFFECT_DELETE,
	EFFECT_DELETE_NOT_USED,
	DEPENDENCY_CREATE,
	DEPENDENCY_EDIT,
	DEPENDENCY_DELETE,
	DEPENDENCY_REPORT_EXCEL,
	DEPENDENCY_GENERATE_COMB,
	DEPENDENCY_GENERATE_COMB_ALLPAIRS,
	DEPENDENCY_GENERATE_COMB_PERMUTATION,
	COMBINATION_CREATE,
	COMBINATION_EDIT,
	COMBINATION_DELETE,
	COMBINATION_MERGE,
	COMBINATION_UNMERGE,
	COMBINATION_ASSIGN_TO_TESTCASE,
	COMBINATION_CREATE_ASSIGN_EFFECTS,
	COMBINATION_EDIT_EFFECTS,
	COMBINATION_DELETE_ALL,
	TESTCASE_CREATE,
	TESTCASE_EDIT,
	TESTCASE_DELETE,
	TESTCASE_ASSIGN_COMBINATION,
	TESTCASE_ASSIGN_EQUIVALENCECLASS,
	TESTCASE_ASSIGN_STDCOMBINATION,
	STDCOMBINATION_ASSIGN_TEST_CASES,
	TESTCASE_ASSIGN_ERRORS,
	TESTCASE_CANCEL_ASSIGN_EQUIVALENCECLASS,
	TESTCASE_GENERATE,
	TESTCASE_REPORT_LIST1,
	TESTCASE_REPORT_QADIRECTOR,
	TESTCASE_REPORT_CSV,
	TESTCASE_REPORT_LIST2,
	TESTCASE_REPORT_COMPUWARE,
	TESTCASE_DELETE_ALL,
	STDCOMBINATION_CREATE,
	STDCOMBINATION_EDIT,
	STDCOMBINATION_DELETE,
	STDCOMBINATION_ASSIGN_EQUIVALENCECLASS,
	STDCOMBINATION_CANCEL_ASSIGN_EQUIVALENCECLASS,
	ERROR_CREATE,
	ERROR_EDIT,
	ERROR_DELETE,
	ERROR_ASSIGN_TESTCASES,
	ERROR_REPORT_LIST,
	IMPORT_CSV,
	TESTDATA_CANCEL_GLOBAL_REFERENCE,//svonborries_06032006
	TESTDATA_ASSIGN_GLOBAL_REFERENCE,//svonborries_08032006
	TESTDATA_ADD_FORMULA,
	TESTDATA_EDIT_FORMULA,//svonborries_11042006
	TESTDATA_DELETE_FORMULA,
	TESTDATA_ADD_VARIABLE,
	TESTDATA_DELETE_VARIABLE,
	TESTDATA_ADD_LINK_ELEMENT,
	TESTDATA_DELETE_LINK_ELEMENT,
	TESTDATA_INSERT_ELEMENT,
	TESTDATA_DELETE_ELEMENT,//svonborries_14032006
	TESTDATA_INSERT_NEW_COLUMN,//svonborries_06042006
	TESTDATA_DELETE_COLUMN_ADDED,//svonborries_07042006
	TESTDATA_CHANGE_NAME_COLUMN_ADDED,
	TESTDATA_ASSIGN_GLOBAL_VALUE,//svonborries_07042006
	TESTDATA_CREATE_TDSTRUCTURE,//svonborries_07042006
	TESTDATA_EDIT_TDSTRUCTURE,//svonborries_07042006
	TESTDATA_DELETE_TDSTRUCTURE,//svonborries_07042006
	TESTDATA_GENERATE_STRUCTURE,//svonborries_10042006
	TESTDATA_GENERATE_TESTDATA_FROM_ALL_TESTCASES,//svonborries_10042006
	TESTDATA_GENERATE_TESTDATA_FROM_ALL_POSITIVE,//svonborries_10042006
	TESTDATA_GENERATE_TESTDATA_FROM_ALL_NEGATIVE,//svonborries_10042006
	TESTDATA_GENERATE_TESTDATA_FROM_ALL_FAULTY,//svonborries_10042006
	TESTDATA_GENERATE_TESTDATA_FROM_ALL_SPECIFIC,//svonborries_10042006
	TESTDATA_GENERATE_RESULT_STRUCTURE,//svonborries_11052006
	TESTDATA_CREATE_TESTDATA,//svonborries_11042006
	TESTDATA_EDIT_TESTDATA,//svonborries_11042006
	TESTDATA_DELETE_TESTDATA,//svonborries_11042006
	TESTDATA_ASSIGN_TESTCASE_TO_TESTDATA,//svonborries_11042006
	TESTDATA_CANCEL_TESTCASE_TO_TESTDATA,//svonborries_11042006
	TESTDATA_IMPORT_TESTDATA,//svonborries_11042006
	TESTDATA_ASSIGN_GLOBAL_STRUCTURE,//svonborries_11042006
	TESTDATA_CANCEL_GLOBAL_STRUCTURE,//svonborries_12042006
	TESTDATASET_NEW_TESTDATASET,//svonborries_12042006
	TESTDATASET_EDIT_TESTDATASET,//svonborries_12042006
	TESTDATASET_DELETE_TESTDATASET,//svonborries12042006
	TESTDATASET_GENERATE_TESTDATASET_REPORT,//svonborries_12042006
	TESTDATASETREPORT_OPEN_REPORT,//svonborries_12042006
	TESTDATASETREPORT_UPDATE,//svonborries_12042006
	TESTDATASETREPORT_ADD_REPORT,//svonborries_12042006
	TESTDATASETREPORT_EDIT_REPORT,//svonborries_12042006
	TESTDATASETREPORT_DELETE_REPORT,//svonborries_12042006
	TESTDATASETREPORT_OPEN_REPORT_FOLDER,//ccastedo 30.08.06
	TESTDATA_VARIABLE_CREATE,//svonborries_13042006
	TESTDATA_VARIABLE_EDIT,//svonborries_13042006
	TESTDATA_VARIABLE_DELETE,//svonborries_13042006
	HELP,
	ON_THE_WEB,
	ABOUT,
//HCanedo_30032006_begin
	SELECTALL,
    FINDREPLACE,
    BUSINESSRULES_CHECK,
    BUSINESSRULES_GENERATE,
    BUSINESSRULES_CHECK_AND_GENERATE,
    BI_BUSINESS_RULES_IMPORT,
    BI_BUSINESS_RULES_SAVE_AS,
    BI_BUSINESS_RULES_CLEAR_ALL,
    TESTDATA_ASSIGN_FORMAT,
//HCanedo_30032006_end
	RESULT_COMPARISON_CREATE,
	RESULT_COMPARISON_EDIT,
	RESULT_COMPARISON_REPORT;

	private class CMDummyAction extends AbstractAction implements Action {

		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			Logger.getLogger(this.getClass()).info("Dummy action called!");
		}

	}

	private Icon icon;
	private Action action;
	/**
	*@autor smoreno
	 * @return the icon 16x16 for the action
	 */
	public Icon getIcon()
	{
		if (icon==null)
			createIcon();
		return icon;
	}

	/**
	*@autor smoreno
	 * @return the asociated action
	 */
	public Action getAction()
	{
			if (this.action==null)
				createAction();
			return this.action;
	}
	private void createAction()
	{
		try {
		switch (this) {
		case CREATE:
			this.action = new CMCreateAction();
			break;
		case ADDOPEN:
			this.action = new CMAddOpenAction();
			break;
		case SAVE:
			this.action = new CMSaveAction();
			break;
		case DELETE:
			this.action = new CMDeleteAction();
			break;
		case IMPORT_PROJECT:
			this.action = new CMImportProjectAction();
			break;
		case CASEMAKER_SETTINGS:
			this.action = new CMCaseMakerSettingsAction();
			break;
		case HIDE_SHOW_TREE:
			this.action = new CMHideShowTreeAction();
			break;
		case REORDER_TESTOBJECTS:
			this.action = new CMReorderTestObjectsAction();
			break;
		case REORDER_WORKSPACES:
				this.action = new CMReorderWorkspacesAction();
				break;
		case RENAME_WORKSPACE:
			this.action = new CMRenameWorkspaceAction();
			break;
		case RENAME_PROJECT:
			this.action = new CMRenameProjectAction();
			break;
		case RENAME_TESTOBJECT:
			this.action = new CMRenameTestObjectAction();
			break;
		case EXIT:
			this.action = new CMExitAction();
			break;
		case UNDO:
			this.action = new CMUndoAction();
			break;
		case REDO:
			this.action = new CMRedoAction();
			break;
		case CUT:
			this.action = new CMCutAction();
			break;
		case COPY:
			this.action = new CMCopyAction();
			break;
		case PASTE:
			this.action = new CMPasteAction();
			break;

		case TESTOBJECT_ORDERED_FLOWS:
			this.action = new CMTestObjectOrderedFlowAction();
			break;
		case ELEMENT_CREATE:
			this.action = new CMElementCreateAction();
			break;
		case ELEMENT_DELETE:
		this.action = new CMElementDeleteAction();
		break;
		case ELEMENT_INSERT:
			this.action = new CMElementInsertAction();
			break;
		case ELEMENT_REORDER:
			this.action = new CMElementReorderAction();
			break;
		case IMPORT_CSV:
			this.action = new CMCSVImportAction();
			break;
		case EQUIVALENCECLASS_CREATE:
			this.action = new CMEquivalenceClassCreateAction();
			break;
		case EQUIVALENCECLASS_EDIT:
			this.action = new CMEquivalenceClassEditAction();
			break;
		case EQUIVALENCECLASS_DELETE:
			this.action = new CMEquivalenceClassDeleteAction();
			break;
		case EQUIVALENCECLASS_CREATE_ASSIGN_EFFECT:
			this.action = new CMEquivalenceClassCreateAssignEffectAction();
			break;
		case EQUIVALENCECLASS_EDIT_ASSIGNED_EFFECTS :
			this.action = new CMEquivalenceClassEditAssignedEffectsAction();
			break;
		case EFFECT_CREATE :
			this.action = new CMEffectCreateAction();
			break;
		case EFFECT_EDIT :
			this.action = new CMEffectEditAction();
			break;
		case EFFECT_DELETE:
			this.action = new CMEffectDeleteAction();
			break;
		case EFFECT_DELETE_NOT_USED:
			this.action = new CMEffectDeleteNotUsedAction();
			break;
		case DEPENDENCY_CREATE:
			this.action = new CMDependencyCreateAction();
			break;
		case DEPENDENCY_EDIT:
			this.action = new CMDependencyEditAction();
			break;
		case DEPENDENCY_DELETE:
			this.action = new CMDependencyDeleteAction();
			break;
		case DEPENDENCY_GENERATE_COMB_ALLPAIRS:
			this.action = new CMDependencyGenerateCombAllPairsAction();
			break;
		case DEPENDENCY_GENERATE_COMB_PERMUTATION:
			this.action = new CMDependencyGenerateCombPermutationAction();
			break;
		case DEPENDENCY_GENERATE_COMB:
			this.action = new CMDependencyGenerateCombAction();
			break;

		case DEPENDENCY_REPORT_EXCEL:
			this.action = new CMDependencyExcelReportAction();
			break;
		case COMBINATION_CREATE:
			this.action = new CMCombinationCreateAction();
			break;
		case COMBINATION_EDIT:
			this.action = new CMCombinationEditAction();
			break;
		case COMBINATION_DELETE:
			this.action = new CMCombinationDeleteAction();
			break;
		case COMBINATION_MERGE:
			this.action = new CMCombinationMergeAction();
			break;
		case COMBINATION_UNMERGE:
			this.action = new CMCombinationUnMergeAction();
			break;
		case COMBINATION_ASSIGN_TO_TESTCASE:
			this.action = new CMCombinationAssingToTestCaseAction();
			break;
		case COMBINATION_CREATE_ASSIGN_EFFECTS:
			this.action = new CMCombinationCreateAssingEffectsAction();
			break;
		case COMBINATION_EDIT_EFFECTS:
			this.action = new CMCombinationEditEffectsAction();
			break;
		case COMBINATION_DELETE_ALL:
			this.action = new CMCombinationDeleteAllAction();
			break;
		case TESTCASE_CREATE:
			this.action = new CMTestCaseCreateAction();
			break;
		case TESTCASE_EDIT:
			this.action = new CMTestCaseEditAction();
			break;
		case TESTCASE_DELETE:
			this.action = new CMTestCaseDeleteAction();
			break;
		case TESTCASE_ASSIGN_COMBINATION:
			this.action = new CMTestCaseAssignCombinationAction();
			break;
		case TESTCASE_ASSIGN_EQUIVALENCECLASS:
			this.action = new CMTestCaseAssignEquivalenceClassAction();
			break;
		case TESTCASE_ASSIGN_STDCOMBINATION:
			this.action = new CMTestCaseAssignStdCombinationAction();
			//this.action = new CMStdCombinationAssignTestCasesAction();
			break;
		case TESTCASE_ASSIGN_ERRORS:
			this.action = new CMTestCaseAssignErrorsAction();
			break;
		case TESTCASE_CANCEL_ASSIGN_EQUIVALENCECLASS:
			this.action = new CMTestCaseCancelAssignEquivalenceClassAction();
			break;
		case TESTCASE_GENERATE:
			this.action = new CMTestCaseGenerateAction();
			break;
		case TESTCASE_REPORT_LIST1:
			this.action = new CMTestCaseReportList1Action();
			break;
		case TESTCASE_REPORT_LIST2:
			this.action = new CMTestCaseReportList2Action();
			break;
		case TESTCASE_REPORT_COMPUWARE:
			this.action = new CMTestCaseReportCompuwareAction();
			break;
		case TESTCASE_REPORT_QADIRECTOR:
			this.action = new CMTestCaseReportQADirectorAction();
			break;
		case TESTCASE_REPORT_CSV:
			this.action = new CMTestCaseReportQADirectorAction();
			break;
		case TESTCASE_DELETE_ALL:
			this.action = new CMTestCaseDeleteAllAction();
			break;
		case STDCOMBINATION_CREATE:
			this.action = new CMStdCombinationCreateAction();
			break;
		case STDCOMBINATION_EDIT:
			this.action = new CMStdCombinationEditAction();
			break;
		case STDCOMBINATION_DELETE:
			this.action = new CMStdCombinationDeleteAction();
			break;
		case STDCOMBINATION_ASSIGN_EQUIVALENCECLASS:
			this.action = new CMStdCombinationAssignEquivalenceClassAction();
			break;
		case STDCOMBINATION_ASSIGN_TEST_CASES:
			this.action = new CMStdCombinationAssignTestCasesAction();
			break;
		case STDCOMBINATION_CANCEL_ASSIGN_EQUIVALENCECLASS:
			this.action = new CMStdCombinationCancelAssignEquivalenceClassAction();
			break;
		case ERROR_CREATE:
			this.action = new CMErrorCreateAction();
			break;
		case ERROR_EDIT:
			this.action = new CMErrorEditAction();
			break;
		case ERROR_DELETE:
			this.action = new CMErrorDeleteAction();
			break;
		case ERROR_ASSIGN_TESTCASES:
			this.action = new CMErrorAssignTestCasesAction();
			break;
		case ERROR_REPORT_LIST:
			this.action = new CMErrorReportListAction();
			break;
		case HELP:
			this.action = new CMHelpAction();
			break;
		//svonborries_06032006_begin
		case TESTDATA_CANCEL_GLOBAL_REFERENCE:
			this.action = new CMCancelGlobalValueReferenceAction();
			break;
		//svonborries_06032006_end
//			svonborries_08032006_begin
		case TESTDATA_ASSIGN_GLOBAL_REFERENCE:
			this.action = new CMAssignGlobalValueReferenceAction();
			break;
//			svonborries_08032006_end
//			svonborries_14032006_begin
		case TESTDATA_ADD_FORMULA:
			this.action = new CMAddFormulaAction();
			break;
		case TESTDATA_DELETE_FORMULA:
			this.action = new CMDeleteFormulaAction();
			break;
		case TESTDATA_ADD_VARIABLE:
			this.action = new CMAddVariableAction();
			break;
		case TESTDATA_DELETE_VARIABLE:
			this.action = new CMDeleteVariableAction();
			break;
		case TESTDATA_ADD_LINK_ELEMENT:
			this.action = new CMAddLinkElementAction();
			break;
		case TESTDATA_DELETE_LINK_ELEMENT:
			this.action = new CMDeleteLinkElementAction();
			break;
		case TESTDATA_INSERT_ELEMENT:
			this.action = new CMInsertNewElementAction();
			break;
		case TESTDATA_DELETE_ELEMENT:
			this.action = new CMDeleteElementAction();
			break;
//			svonborries_14032006_begin
			//svonborries_06042006_begin
		case TESTDATA_INSERT_NEW_COLUMN:
			this.action = new CMInsertNewColumnAction();
			break;
			//svonborries_06042006_end
			//svonborries_07042006_begin
		case TESTDATA_DELETE_COLUMN_ADDED:
			this.action = new CMDeleteColumnAddedAction();
			break;
		case TESTDATA_CHANGE_NAME_COLUMN_ADDED:
			this.action = new CMChangeNameColumnAddedAction();
			break;
		case TESTDATA_ASSIGN_GLOBAL_VALUE:
			this.action = new CMAssignGlobalValueAction();
			break;
		case TESTDATA_CREATE_TDSTRUCTURE:
			this.action = new CMCreateTDStructureAction();
			break;
		case TESTDATA_EDIT_TDSTRUCTURE:
			this.action = new CMEditTDStructureAction();
			break;
		case TESTDATA_DELETE_TDSTRUCTURE:
			this.action = new CMDeleteTDStructureAction();
			break;
			//svonborries_07042006_end
			//svonborries_10042006_begin
		case TESTDATA_GENERATE_STRUCTURE:
			this.action = new CMGenerateStructureAction();
			break;
		case TESTDATA_GENERATE_TESTDATA_FROM_ALL_TESTCASES:
			this.action = new CMGenerateTestDataCombinationsFromAllTestCasesAction();
			break;
		case TESTDATA_GENERATE_TESTDATA_FROM_ALL_POSITIVE:
			this.action = new CMGenerateTestDataCombinationsFromPositiveTestCasesAction();
			break;
		case TESTDATA_GENERATE_TESTDATA_FROM_ALL_NEGATIVE:
			this.action = new CMGenerateTestDataCombinationsFromNegativeTestCasesAction();
			break;
		case TESTDATA_GENERATE_TESTDATA_FROM_ALL_FAULTY:
			this.action = new CMGenerateTestDataCombinationsFromFaultyTestCasesAction();
			break;
		case TESTDATA_GENERATE_TESTDATA_FROM_ALL_SPECIFIC:
			this.action = new CMGenerateTestDataCombinationsFromSpecificTestCasesAction();
			break;
			//svonborries_10042006_end
			//svonborries_11042006_begin
		case TESTDATA_CREATE_TESTDATA:
			this.action = new CMCreateTestDataAction();
			break;
		case TESTDATA_EDIT_TESTDATA:
			this.action = new CMEditTestDataAction();
			break;
		case TESTDATA_DELETE_TESTDATA:
			this.action = new CMDeleteTestDataAction();
			break;
		case TESTDATA_ASSIGN_TESTCASE_TO_TESTDATA:
			this.action = new CMAssignTestCaseToTestDataAction();
			break;
		case TESTDATA_CANCEL_TESTCASE_TO_TESTDATA:
			this.action = new CMCancelTestCaseToTestDataAction();
			break;
		case TESTDATA_IMPORT_TESTDATA:
			this.action = new CMImportTestDataAction();
			break;
		case TESTDATA_EDIT_FORMULA:
			this.action = new CMEditFormulaAction();
			break;
		case TESTDATA_ASSIGN_GLOBAL_STRUCTURE:
			this.action = new CMAssignGlobalStructureAction();
			break;
			//svonborries_11042006_end
			//svonborries_12042006_begin
		case TESTDATA_CANCEL_GLOBAL_STRUCTURE:
			this.action = new CMCancelAssignGlobalStructureAction();
			break;
		case TESTDATASET_NEW_TESTDATASET:
			this.action = new CMNewTestDataSetAction();
			break;
		case TESTDATASET_EDIT_TESTDATASET:
			this.action = new CMEditTestDataSetAction();
			break;
		case TESTDATASET_DELETE_TESTDATASET:
			this.action = new CMDeleteTestDataSetAction();
			break;
		case TESTDATASET_GENERATE_TESTDATASET_REPORT:
			this.action = new CMGenerateTestDataSetReportAction();
			break;
		case TESTDATASETREPORT_OPEN_REPORT:
			this.action = new CMOpenReportAction();
			break;
		case TESTDATASETREPORT_UPDATE:
			this.action = new CMUpdateReportAction();
			break;
		case TESTDATASETREPORT_ADD_REPORT:
			this.action = new CMAddReportAction();
			break;
		case TESTDATASETREPORT_EDIT_REPORT:
			this.action = new CMEditReportAction();
			break;
		case TESTDATASETREPORT_DELETE_REPORT:
			this.action = new CMDeleteReportAction();
			break;
			//svonborries_12042006_end
		//ccastedo begins 30.08.06
		case TESTDATASETREPORT_OPEN_REPORT_FOLDER:
			this.action = new CMOpenReportFolderAction();
			break;
		//ccastedo ends 30.08.06
		case TESTDATA_VARIABLE_CREATE:
			this.action = new CMCreateVariableAction();
			break;
		case TESTDATA_VARIABLE_EDIT:
			this.action = new CMEditVariableAction();
			break;
		case TESTDATA_VARIABLE_DELETE:
			this.action = new bi.view.actions.variable.CMDeleteVariableAction();
			break;
		case ON_THE_WEB:
			this.action = new CMOnTheWebAction();
			break;
		case ABOUT:
			this.action = new CMAboutAction();
			break;
//HCanedo_29032006_begin
		case SELECTALL:
			this.action= new CMSelectAllAction();
			break;
		case FINDREPLACE:
			this.action= new CMFindReplaceAction();
			break;
		case BUSINESSRULES_CHECK:
			this.action= new CMBusinessRulesCheckAction();
			break;
		case BUSINESSRULES_GENERATE:
			this.action= new CMBusinessRulesGenerateAction();
			break;
		case BUSINESSRULES_CHECK_AND_GENERATE:
			this.action= new CMBusinessRulesCheckAndGenerateAction();
			break;
		case BI_BUSINESS_RULES_IMPORT:
			this.action= new CMBusinessRulesImportAction();
			break;
		case BI_BUSINESS_RULES_CLEAR_ALL:
			this.action= new CMBusinessRulesClearAllAction();
			break;
		case BI_BUSINESS_RULES_SAVE_AS:
			this.action= new CMBusinessRulesSaveAsAction();
			break;
//HCanedo_29032006_end
//svonborries_11052006_begin
		case TESTDATA_GENERATE_RESULT_STRUCTURE:
			this.action = new CMGenerateResultStructureAction();
			break;
//svonborries_11052006_end
		case TESTDATA_ASSIGN_FORMAT:
			this.action = new CMAssignFormattoTypeDataAction();
			break;
		case RESULT_COMPARISON_CREATE:
			this.action = new CMCreateResultComparisonAction();
			break;
		case RESULT_COMPARISON_EDIT:
			this.action = new CMEditResultComparisonAction();
			break;
		case RESULT_COMPARISON_REPORT:
			this.action = new CMResultComparisonReportAction();
			break;
		default:
			this.action = new CMDummyAction();
			break;
		}

		} catch (Exception e) {
			Logger.getLogger(this.getClass()).error("The action could not be created "+e.getMessage());
			e.printStackTrace();
			//this is only a dummy action
			this.action = new CMDummyAction();
		}
	}

	private void createIcon()
	{
    //look in the CMIcon enum for the corresponding action icon (it must have the same name)
   try {
	if (CMIcon.valueOf(this.name())!=null) {
		icon = CMIcon.valueOf(this.name()).getImageIcon();
		return ;
	}
   } catch (IllegalArgumentException e) {
	   //the icon was not found so just continue the operation
   }
	switch (this) {
	//just special cases (repeated icons for different actions)
	case EQUIVALENCECLASS_CREATE_ASSIGN_EFFECT:
		 icon =CMIcon.EFFECT_CREATE.getImageIcon();
		break;
	case TESTDATASET_GENERATE_TESTDATASET_REPORT :
		icon = CMIcon.TESTCASE_REPORT_LIST1.getImageIcon();
		break;
	case ERROR_REPORT_LIST:
		icon = CMIcon.TESTCASE_REPORT_LIST1.getImageIcon();
		break;
	case DEPENDENCY_REPORT_EXCEL:
		icon = CMIcon.TESTCASE_REPORT_LIST2.getImageIcon();
		break;
	case TESTCASE_ASSIGN_COMBINATION:
		icon = CMIcon.COMBINATION_ASSIGN_TO_TESTCASE.getImageIcon();
		break;
	case STDCOMBINATION_ASSIGN_TEST_CASES:
		 icon = TESTCASE_ASSIGN_STDCOMBINATION.getIcon();
		break;
	case ERROR_ASSIGN_TESTCASES:
		 icon = TESTCASE_ASSIGN_ERRORS.getIcon();
		break;
	default:
		break;
	}

	}

	public boolean isEnabled() {
		if (action !=null)
			return this.action.isEnabled();
		else
			return false;
	}

	public void setEnabled(boolean p_b) {
		if (action != null)
			this.action.setEnabled(p_b);
	}
}
