package bi.view.icons;

import java.net.URL;

import javax.swing.ImageIcon;

/**
 * @author smoreno
 *  Factory and ennumeration for all icons all main icons should be here
 */
public enum CMIcon {
	//General icons
	LOADING("loader.gif"),
	UP("up.gif"),
	ARROW_DOWN("arrowdown.gif"),
	ARROW_UP("arrowup.gif"),
	CASEMAKER("cmicon.gif"),
	CASEMAKER_RIGHT("casemaker.gif"),
	ABOUT("about.gif"),
	CASEMAKER_LOGO("cmlogo.jpg"),
	CASEMAKER_LOGO_SMALL("cmlogoSmall.jpg"),
	LOCK("lock.gif"),
	UNLOCK("unlock.gif"),
	UNLOCKLOCAL("unlocklocal.gif"),
	ORDER_FIRST("order_first.gif"),
	ORDER_LAST("order_last.gif"),
	ORDER_NEXT("order_next.gif"),
	ORDER_PREV("order_prev.gif"),
	SUMMARY("Summary.gif"),
	USER("User.gif"),
	WARNING("warning.gif"),
	WARNING2("Warning2.gif"),
	//General toolbar icons
	CREATE("create.gif"),
	ADDOPEN("open.gif"),
	SAVE("save.gif"),
	DELETE("delete.gif"),
	UNDO("undo.gif"),
	REDO("redo.gif"),
	COPY("copy.gif"),
	CUT("cut.gif"),
	PASTE("paste.gif"),
	RENAME_WORKSPACE("rename.gif"),
	HELP("help.gif"),
	//Workspace icons
	WORKSPACE("workspace.gif"),
	WORKSPACES("workspaces.gif"),
	//Project icons
	PROJECT("project.gif"),
	//Test Object Icons
	TESTOBJECT("object.gif"),
	TESTOBJECT_CHECKOUT_ANOTHER("checked_out_by_another.gif"),
	TESTOBJECT_CHECKOUT_SAME("checked_out_by_the_same_user.gif"),
	TESTOBJECT_CHECKOUT_ANOTHERLOCAL("checked_out_local_by_another.gif"),
	TESTOBJECT_CHECKOUT_SAMELOCAL("checked_out_local_by_the_same_user.gif"),

	//Element Icons
	ELEMENT("element.gif"),
	ELEMENT_CREATE("create_element.gif"),
	ELEMENT_INSERT("insert_element.gif"),
	ELEMENT_DELETE("delete_element.gif"),
	ELEMENT_REORDER("reorder_element.gif"),
	IMPORT_CSV("csv_import.gif"),

	//Equivalence Class icons
	EQUIVALENCECLASS_CREATE("create_elementclass.gif"),
	EQUIVALENCECLASS_EDIT("edit_elementclass.gif"),
	EQUIVALENCECLASS_DELETE("delete_elementclass.gif"),
	EQUIVALENCECLASS_CREATE_ASSIGN_EFFECT("asign_elementclass_cause_efect.gif"),
	EQUIVALENCECLASS_EDIT_ASSIGNED_EFFECTS("edit_elementclass_cause_efect.gif"),
	EQUIVALENCECLASS_IN_COMBINATION ("bola.gif"),
	EQUIVALENCECLASS_IN_MANUAL_COMBINATION("comb_manual.gif"),
	EQUIVALENCECLASS_IN_ALLPAIRS_COMBINATION("comb_allpairs.gif"),
	EQUIVALENCECLASS_IN_TESTCASE("hacken.gif"),
	EQUIVALENCECLASS_FAULTY_IN_TESTCASE("red_hook.gif"),
	EQUIVALENCECLASS("elementclass.gif"),
	//Effect icons
	EFFECT("cause_efect.gif"),
	EFFECT_CREATE("create_cause_efect.gif"),
	EFFECT_EDIT("edit_cause_efect.gif"),
	EFFECT_DELETE("delete_cause_efect.gif"),
	EFFECT_DELETE_NOT_USED("cause_effects_not_use_delete.gif"),
	EFFECT_REQUIREMENT("cause_effectreq.gif"),
	EFFECT_RESULT("cause_effectres.gif"),
	//Dependency icons
	DEPENDENCY_CREATE("create_dependencies.gif"),
	DEPENDENCY_EDIT("edit_dependencies.gif"),
	DEPENDENCY_DELETE("delete_dependencies.gif"),
	DEPENDENCY_GENERATE_COMB_ALLPAIRS("paarwise.gif"),
	DEPENDENCY_GENERATE_COMB_PERMUTATION("generate_combinations.gif"),

	//Combination icons
	COMBINATION_CREATE("add_combination.gif"),
	COMBINATION_EDIT("edit_combination.gif"),
	COMBINATION_DELETE("delete_combination.gif"),
	COMBINATION_MERGE("merge_combination.gif"),
	COMBINATION_UNMERGE("separate_combination.gif"),
	COMBINATION_ASSIGN_TO_TESTCASE("asign_combi_to_testcases.gif"),
	COMBINATION_CREATE_ASSIGN_EFFECTS("create_asign_cause_efect_to_combi.gif"),
	COMBINATION_EDIT_EFFECTS("edit_cause_efect_to_combi.gif"),
	//Test Case icons
	TESTCASE("testcases.gif"),
	TESTCASE_CREATE("create_testcases.gif"),
	TESTCASE_DELETE("delete_testcase.gif"),
	TESTCASE_EDIT("edit_testcase.gif"),
	TESTCASE_REPORT_QADIRECTOR("qadirector.gif"),
	TESTCASE_REPORT_COMPUWARE("compuware.gif"),
	TESTCASE_REPORT_LIST2("export_testcases_excel.gif"),
	TESTCASE_REPORT_LIST1("generate_testlist.gif"),
	TESTCASE_REPORT_CSV("csv.gif"),
	TESTCASE_ASSIGN_EQUIVALENCECLASS("asign_elementclass_to_testcase.gif"),
	TESTCASE_ASSIGN_STDCOMBINATION("asign_standard_to_testcase.gif"),
	TESTCASE_CANCEL_ASSIGN_EQUIVALENCECLASS("cancel_aisgn_elementclass_to_testcase.gif"),
	TESTCASE_ASSIGN_ERRORS("eas.gif"),
	TESTCASE_GENERATE("generate_testcases_from_combi.gif"),

	//Std combinations icons
	STDCOMBINATION("standard.gif"),
	STDCOMBINATION_CREATE("create_standard.gif"),
	STDCOMBINATION_DELETE("delete_standard.gif"),
	STDCOMBINATION_EDIT("edit_standard.gif"),
	STDCOMBINATION_ASSIGN_EQUIVALENCECLASS("asign_elementclass_to_standard.gif"),
	STDCOMBINATION_CANCEL_ASSIGN_EQUIVALENCECLASS("cancel_asign_elementclass_to_standard.gif"),
	STDCOMBINATION_ASSIGN_TEST_CASES("cancel_asign_standard_to_testcase.gif"),
	//Error images
	ERROR_CREATE("ecr.gif"),
	ERROR_DELETE("ede.gif"),
	ERROR_EDIT("eed.gif"),
	ERROR_MANAGMENT("ermgmt.gif"),
	//Result Comparison
	RESULT("results.gif"),
	RESULT_REPORT("create_result_report.gif"),
	RESULT_CREATE("result_create.gif"),
	RESULT_EDIT("result_edit.gif"),
	//Business Rules images
	BUSINESS_RULE("business_rule.gif"),
	BUSINESS_RULES_WIZLOGO("import_wiz.gif"),
	BUSINESS_RULES_TEXT_IMPORT("br_import.gif"),
	BI_BUSINESS_RULES_IMPORT("import_wiz16.gif"),
	BR_JRULES_IMPORT("jrules.gif"),
	BR_VISIO_IMPORT("visio.gif"),
	FINDREPLACE("findReplace.gif") ,
	BI_BUSINESS_RULES_CLEAR_ALL("clear_all.gif"),
	BI_BUSINESS_RULES_SAVE_AS("save_as.gif"),
	//Test data icons
	TESTDATA("testdata_1.gif"),
	TESTDATA_IMPORT_TESTDATA("import.gif"),
	TESTDATA_CREATE_TESTDATA("create_testdata.gif"),
	TESTDATA_DELETE_TESTDATA("delete_testdata.gif"),
	TESTDATA_EDIT_TESTDATA("edit_testdata.gif"),
	TESTDATA_INSERT_ELEMENT("insert_element.gif"),
	TESTDATA_DELETE_ELEMENT("delete_fields.gif"),
	TESTDATA_ASSIGN_GLOBAL_VALUE("assign_global_value.gif"),
	//generate test data
	TESTDATA_GENERATE_TESTDATA_FROM_ALL_TESTCASES("create_struc_from_elements_all.gif"),
	TESTDATA_GENERATE_TESTDATA_FROM_ALL_FAULTY("create_struc_from_elements_faulty.gif"),
	TESTDATA_GENERATE_TESTDATA_FROM_ALL_NEGATIVE("create_struc_from_elements_negative.gif"),
	TESTDATA_GENERATE_TESTDATA_FROM_ALL_POSITIVE("create_struc_from_elements_positive.gif"),
	TESTDATA_GENERATE_TESTDATA_FROM_ALL_SPECIFIC("create_struc_from_elements_specific.gif"),
	//columns
	TESTDATA_INSERT_NEW_COLUMN("insert_column.gif"),
	TESTDATA_DELETE_COLUMN_ADDED("delete_column.gif"),
	TESTDATA_CHANGE_NAME_COLUMN_ADDED("change_name_column.gif"),
	//structures
	TESTDATA_STRUCTURE("testdata.gif"),
	TESTDATA_CREATE_TDSTRUCTURE("create_struc.gif"),
	TESTDATA_DELETE_TDSTRUCTURE("delete_struc.gif"),
	TESTDATA_EDIT_TDSTRUCTURE("struc_properties.gif"),
	TESTDATA_GENERATE_STRUCTURE("create_struc_from_elements.gif"),
	TESTDATA_GENERATE_RESULT_STRUCTURE("structure_result.gif"),
	TESTDATA_ASSIGN_GLOBAL_STRUCTURE("assign_struc_to_testdata.gif"),
	TESTDATA_CANCEL_GLOBAL_STRUCTURE("cancel_assignement_struc_to_testdata.gif"),
	//test case asignment
	TESTDATA_ASSIGN_TESTCASE_TO_TESTDATA("assign_testcase_to_testdata.gif"),
	TESTDATA_CANCEL_TESTCASE_TO_TESTDATA("cancel_assignement_testcase_to_testdata.gif"),
	//test data set
	TESTDATASET("testdataset.gif"),
	TESTDATASET_NEW_TESTDATASET("create_testdataset.gif"),
	TESTDATASET_DELETE_TESTDATASET("delete_testdataset.gif"),
	TESTDATASET_EDIT_TESTDATASET("edit_testdataset.gif"),
	TESTDATA_CANCEL_TESTDATA_TO_TESTDATASET("cancel_assignment_td_to_tdset.gif"),
	TESTDATA_ASSIGN_TESTDATA_TO_TESTDATASET("assign_testdata_to_testdataset.gif"),
	//test data set reports
	TESTDATASETREPORT("testdataset_reports.gif"),
	TESTDATASETREPORT_ADD_REPORT("testdataset_reports_add.gif"),
	TESTDATASETREPORT_DELETE_REPORT("testdataset_reports_delete.gif"),
	TESTDATASETREPORT_OPEN_REPORT("testdataset_reports_open.gif"),
	TESTDATASETREPORT_OPEN_REPORT_FOLDER("testdataset_reports_openfolder.gif"),
	TESTDATASETREPORT_EDIT_REPORT("testdataset_reports_rename.gif"),
	TESTDATASETREPORT_UPDATE("testdataset_reports_update.gif"),
	//links
	TESTDATA_DELETE_LINK_ELEMENT("deleteLinkElement.gif"),
	TESTDATA_ADD_LINK_ELEMENT("linkElement.png"),
	//variables
	TESTDATA_VARIABLE("variable.gif"),
	TESTDATA_VARIABLE_CREATE("add_variable.gif"),
	TESTDATA_VARIABLE_DELETE("delete_variable.gif"),
	TESTDATA_VARIABLE_EDIT("edit_variable.gif"),
	TESTDATA_VARIABLE_ASSIGN_FORMAT("assign_format_variable.gif"),
	TESTDATA_ADD_VARIABLE("assign_formula.gif"),
	TESTDATA_DELETE_VARIABLE("cancel_assignement_formula.gif"),
	//references
	TESTDATA_CANCEL_GLOBAL_REFERENCE("assign_cancel_global_Reference.gif"),
	TESTDATA_ASSIGN_GLOBAL_REFERENCE("assign_global_Reference.gif"),
	//formulas
	TESTDATA_ADD_FORMULA("add_formula.gif"),
	TESTDATA_EDIT_FORMULA("edit_formula.gif"),
	TESTDATA_DELETE_FORMULA("delete_formula.gif"),
	//languages
	LANG_USA("lang_usa.gif"),
	LANG_GERMANY("lang_germany.gif"),
	LANG_SPAIN("lang_spain.gif"),
	LANG_USER("flag.gif"),
	EDIT_LANG("edit.gif"),
	WIZARD_ICON("topwiz.gif"),
	//dual list
	ARROW_RIGHT("arrow_rigth.gif"),
	ARROW_ALL_RIGHT("arrow_all_rigth.gif"),
	ARROW_LEFT("arrow_left.gif"),
	ARROW_ALL_LEFT("arrow_all_left.gif"),
	//ok cancel panel
	OK("tick.gif"),
	CANCEL("delete2.gif"),
	// CRUD preferences buttons
	INSERT_PREF("insertdb.gif"),
	EDIT_PREF("editdb.gif"),
	DELETE_PREF("deletedb.gif"),
	SETDEFAULT_PREF("setdefault.gif"),
	//preference inons
	EXTERNAL_APP("external_app.gif"),
	GENERAL_PREFERENCES("preferences.gif"),
	LANGUAGE_PREFERENCES("earth.gif"),
	REPORT_PREFERENCES("report.gif"),
	TOOL_VENDOR_PREFERENCES("tools.gif")
	;
	private String filename;
	private ImageIcon icon;
	CMIcon(String filename){
		this.filename = filename;
	}
	public ImageIcon getImageIcon() {
		if (icon==null){
			icon = new ImageIcon(this.getClass().getResource(filename));
		}
		return icon;
	}
	public String getFilename() {
		return filename;
	}
	public URL getURL() {
		return this.getClass().getResource(filename);
	}
}
