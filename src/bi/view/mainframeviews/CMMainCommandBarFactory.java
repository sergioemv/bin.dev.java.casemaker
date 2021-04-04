package bi.view.mainframeviews;


import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JMenu;

import bi.view.actions.CMAction;
import bi.view.businessrulesviews.CMToolBarBusinessRules;
import bi.view.lang.CMMessages;
import bi.view.utils.CMBaseJMenuItem;
import bi.view.utils.CMToolBarButton;

import com.jidesoft.action.CommandBar;
import com.jidesoft.action.CommandBarFactory;
import com.jidesoft.action.DockableBarContext;
import com.jidesoft.swing.JideMenu;

/**
 */

public class CMMainCommandBarFactory extends CommandBarFactory {



	 static JMenu createMenuBusinessRules(){

			JideMenu menuBusinessRules = new JideMenu();
			menuBusinessRules.setText(CMMessages.getString("MENU_BUSINESS_RULES")); //$NON-NLS-1$
			menuBusinessRules.setMnemonic(CMMessages.getString("MENU_BUSINESS_RULES_MNEMONIC").charAt(0));
			menuBusinessRules.add(new CMBaseJMenuItem(CMAction.BI_BUSINESS_RULES_IMPORT.getAction()));//getMenuBusinessRulesImport());
			menuBusinessRules.add(new CMBaseJMenuItem(CMAction.BUSINESSRULES_CHECK.getAction()));
			menuBusinessRules.add(new CMBaseJMenuItem(CMAction.BUSINESSRULES_CHECK_AND_GENERATE.getAction()));


		return menuBusinessRules;
	}
	static CommandBar createMainMenuBar(Container container) {
		CommandBar mainMenuBar;
		mainMenuBar = new CommandBar("MainMenu");
		mainMenuBar.setInitSide(DockableBarContext.DOCK_SIDE_NORTH);
		mainMenuBar.setInitIndex(0);
		mainMenuBar.setInitSubindex(0);
		mainMenuBar.setPaintBackground(true);
		mainMenuBar.setStretch(true);
		mainMenuBar.setFloatable(true);
		mainMenuBar.setMenuBar(true);
		mainMenuBar.add(createMenuFile(CMApplication.frame));
		mainMenuBar.add(createMenuEdit());
		mainMenuBar.add(createMenuHelp());
		return mainMenuBar;
	}
    private static JideMenu createMenuFile(Container container) {
    	JideMenu menuFile = new JideMenu();
    	menuFile.setName("FILE");
	    menuFile.setText(CMMessages.getString("MENU_PROJECT")); //$NON-NLS-1$
        menuFile.setMnemonic(CMMessages.getString("MENU_PROJECT_MNEMONIC").charAt(0));


	     menuFile.add(new CMBaseJMenuItem(CMAction.CREATE.getAction()));
        menuFile.add(new CMBaseJMenuItem(CMAction.ADDOPEN.getAction()));
        menuFile.addSeparator();
        menuFile.add(new CMBaseJMenuItem(CMAction.SAVE.getAction()));
        menuFile.addSeparator();
        menuFile.add(new CMBaseJMenuItem(CMAction.DELETE.getAction()));
        menuFile.addSeparator();
        menuFile.add(new CMBaseJMenuItem(CMAction.IMPORT_PROJECT.getAction()));
        menuFile.add(new CMBaseJMenuItem(CMAction.CASEMAKER_SETTINGS.getAction()));
     //   menuFile.add(CommandBarFactory.createLookAndFeelMenu(container));

        menuFile.add(new CMBaseJMenuItem(CMAction.HIDE_SHOW_TREE.getAction()));
        menuFile.addSeparator();
        menuFile.add(new CMBaseJMenuItem(CMAction.EXIT.getAction()));

		return menuFile;
	}
    private static JideMenu createMenuEdit() {

			JideMenu menuEdit = new JideMenu();
			  menuEdit.setName("EDIT");
			   menuEdit.setText(CMMessages.getString("MENU_EDIT")); //$NON-NLS-1$
		        menuEdit.setMnemonic(CMMessages.getString("MENU_EDIT_MNEMONIC").charAt(0));
		        menuEdit.add(new CMBaseJMenuItem(CMAction.UNDO.getAction()));
		        menuEdit.add(new CMBaseJMenuItem(CMAction.REDO.getAction()));
		        menuEdit.addSeparator();
		        menuEdit.add(new CMBaseJMenuItem(CMAction.CUT.getAction()));
		        menuEdit.add(new CMBaseJMenuItem(CMAction.COPY.getAction()));
		        menuEdit.add(new CMBaseJMenuItem(CMAction.PASTE.getAction()));
		        //HCanedo_11042006_begin
		        menuEdit.addSeparator();
		        menuEdit.add(new CMBaseJMenuItem(CMAction.SELECTALL.getAction()));
		        menuEdit.addSeparator();
		       menuEdit.add(new CMBaseJMenuItem(CMAction.FINDREPLACE.getAction()));
		        //HCanedo_11042006_end


		return menuEdit;
	}

    private static JideMenu createMenuHelp() {
			JideMenu menuHelp = new JideMenu();
			menuHelp.setName("HELP");
			menuHelp.setText(CMMessages.getString("MENU_HELP")); //$NON-NLS-1$
			menuHelp.setMnemonic(CMMessages.getString("MENU_HELP_MNEMONIC").charAt(0));
			menuHelp.add(new CMBaseJMenuItem(CMAction.HELP.getAction()));
			menuHelp.add(new CMBaseJMenuItem(CMAction.ON_THE_WEB.getAction()));
			menuHelp.addSeparator();
			menuHelp.add(new CMBaseJMenuItem(CMAction.ABOUT.getAction()));
		return menuHelp;
	}
    static CommandBar createToolBarHelp() {

				CommandBar toolBarHelp = new CommandBar("Help");
				toolBarHelp.setInitSide(DockableBarContext.DOCK_SIDE_NORTH);
				toolBarHelp.setInitMode(DockableBarContext.STATE_HORI_DOCKED);
				toolBarHelp.setInitIndex(1);
				toolBarHelp.setInitSubindex(4);
		        toolBarHelp.addSeparator(new Dimension(30,0));
		        toolBarHelp.add(new CMToolBarButton(CMAction.HELP.getAction()));

		return toolBarHelp;
	}
    static  CommandBar createToolBarStandart() {
			CommandBar toolBarProject = new CommandBar("Standart");
			toolBarProject.setInitSide(DockableBarContext.DOCK_SIDE_NORTH);
			toolBarProject.setInitMode(DockableBarContext.STATE_HORI_DOCKED);
			toolBarProject.setInitIndex(1);
			toolBarProject.setInitSubindex(0);

			toolBarProject.add(new CMToolBarButton(CMAction.CREATE.getAction()));
		    toolBarProject.add(new CMToolBarButton(CMAction.ADDOPEN.getAction()));
		    toolBarProject.add(new CMToolBarButton(CMAction.SAVE.getAction()));
		    toolBarProject.add(new CMToolBarButton(CMAction.DELETE.getAction()));
		    toolBarProject.addSeparator(new Dimension(8,20));
		    toolBarProject.add(new CMToolBarButton(CMAction.UNDO.getAction()));
		    toolBarProject.add(new CMToolBarButton(CMAction.REDO.getAction()));
		    toolBarProject.addSeparator(new Dimension(8,20));
		    toolBarProject.add(new CMToolBarButton(CMAction.CUT.getAction()));
		    toolBarProject.add(new CMToolBarButton(CMAction.COPY.getAction()));
		    toolBarProject.add(new CMToolBarButton(CMAction.PASTE.getAction()));
		    toolBarProject.addSeparator(new Dimension(8,20));
		    toolBarProject.add(new CMToolBarButton(CMAction.FINDREPLACE.getAction()));
		  //  toolBarProject.addMouseListener(new MouseAdapter(){public void mouseClicked(MouseEvent e){eventMouseClicked(e);}});

		return toolBarProject;
	}
	static CommandBar createToolBarBusinessRules() {

			CMToolBarBusinessRules toolBarBusinessRules = new CMToolBarBusinessRules("Business Rules");

		return toolBarBusinessRules;
	}
	static CommandBar createToolBarStructure(){
	    	CommandBar  toolbar = new CommandBar("Structure");
	    	toolbar.setInitSide(DockableBarContext.DOCK_SIDE_NORTH);
			toolbar.setInitMode(DockableBarContext.STATE_HORI_DOCKED);
			toolbar.setInitIndex(1);
			toolbar.setInitSubindex(2);
			toolbar.add(new CMToolBarButton(CMAction.TESTDATA_GENERATE_TESTDATA_FROM_ALL_TESTCASES.getAction()));
			toolbar.add(new CMToolBarButton(CMAction.TESTDATA_GENERATE_TESTDATA_FROM_ALL_POSITIVE.getAction()));
			toolbar.add( new CMToolBarButton(CMAction.TESTDATA_GENERATE_TESTDATA_FROM_ALL_NEGATIVE.getAction()));
			toolbar.add(new CMToolBarButton(CMAction.TESTDATA_GENERATE_TESTDATA_FROM_ALL_FAULTY.getAction()));
			toolbar.add(new CMToolBarButton(CMAction.TESTDATA_GENERATE_TESTDATA_FROM_ALL_SPECIFIC.getAction()));
			toolbar.add(new CMToolBarButton(CMAction.TESTDATA_GENERATE_STRUCTURE.getAction()));
			toolbar.add( new CMToolBarButton(CMAction.TESTDATA_GENERATE_RESULT_STRUCTURE.getAction()));//svonborries_11052006
			toolbar.addSeparator(new Dimension(8,20));
			toolbar.add(new CMToolBarButton(CMAction.TESTDATA_CREATE_TDSTRUCTURE.getAction()));
			toolbar.add(new CMToolBarButton(CMAction.TESTDATA_EDIT_TDSTRUCTURE.getAction()));
			toolbar.add(new CMToolBarButton(CMAction.TESTDATA_DELETE_TDSTRUCTURE.getAction()));
	    	return toolbar;
	    }
	  static CommandBar createToolBarFields(){
	    	CommandBar  toolbar = new CommandBar("Fields");
	    	toolbar.setInitSide(DockableBarContext.DOCK_SIDE_NORTH);
			toolbar.setInitMode(DockableBarContext.STATE_HORI_DOCKED);
			toolbar.setInitIndex(1);
			toolbar.setInitSubindex(2);
			toolbar.add(new CMToolBarButton(CMAction.TESTDATA_INSERT_ELEMENT.getAction()));
			toolbar.add(new CMToolBarButton(CMAction.TESTDATA_DELETE_ELEMENT.getAction()));
			toolbar.addSeparator(new Dimension(8,20));
			toolbar.add(new CMToolBarButton(CMAction.TESTDATA_INSERT_NEW_COLUMN.getAction()));
			toolbar.add(new CMToolBarButton(CMAction.TESTDATA_DELETE_COLUMN_ADDED.getAction()));
			toolbar.add( new CMToolBarButton(CMAction.TESTDATA_CHANGE_NAME_COLUMN_ADDED.getAction()));//ccastedo 24.01.07
			toolbar.addSeparator(new Dimension(8,20));
			toolbar.add( new CMToolBarButton(CMAction.TESTDATA_ASSIGN_GLOBAL_REFERENCE.getAction()));
			toolbar.add( new CMToolBarButton(CMAction.TESTDATA_CANCEL_GLOBAL_REFERENCE.getAction()));
	    	return toolbar;
	    }
	  static CommandBar createToolBarFormulasAndVariables(){
	    	CommandBar  toolbar = new CommandBar("Formulas and Variables");
	    	toolbar.setInitSide(DockableBarContext.DOCK_SIDE_NORTH);
			toolbar.setInitMode(DockableBarContext.STATE_HORI_DOCKED);
			toolbar.setInitIndex(1);
			toolbar.setInitSubindex(2);
			toolbar.add(new CMToolBarButton(CMAction.TESTDATA_ADD_FORMULA.getAction()));
			toolbar.add(new CMToolBarButton(CMAction.TESTDATA_EDIT_FORMULA.getAction()));
			toolbar.add(new CMToolBarButton(CMAction.TESTDATA_DELETE_FORMULA.getAction()));
			toolbar.addSeparator(new Dimension(8,20));
			toolbar.add(new CMToolBarButton(CMAction.TESTDATA_ADD_VARIABLE.getAction()));
			toolbar.add(new CMToolBarButton(CMAction.TESTDATA_DELETE_VARIABLE.getAction()));
			toolbar.addSeparator(new Dimension(8,20));
			toolbar.add(new CMToolBarButton(CMAction.TESTDATA_ADD_LINK_ELEMENT.getAction()));
			toolbar.add(new CMToolBarButton(CMAction.TESTDATA_DELETE_LINK_ELEMENT.getAction()));
			return toolbar;
	    }
	 static JideMenu createMenuStructure(){

			JideMenu menuStructure = new JideMenu();
	        menuStructure.setText(CMMessages.getString("TESTDATA_TDSTRUCTURE"));
	        menuStructure.setMnemonic(CMMessages.getString("STRUCTURE_MNEMONIC").charAt(0));
	        menuStructure.add(new CMBaseJMenuItem(CMAction.TESTDATA_GENERATE_TESTDATA_FROM_ALL_TESTCASES.getAction()));
	        menuStructure.add(new CMBaseJMenuItem(CMAction.TESTDATA_GENERATE_TESTDATA_FROM_ALL_POSITIVE.getAction()));
	        menuStructure.add(new CMBaseJMenuItem(CMAction.TESTDATA_GENERATE_TESTDATA_FROM_ALL_NEGATIVE.getAction()));
			menuStructure.add(new CMBaseJMenuItem(CMAction.TESTDATA_GENERATE_TESTDATA_FROM_ALL_FAULTY.getAction()));
	        menuStructure.add(new CMBaseJMenuItem(CMAction.TESTDATA_GENERATE_TESTDATA_FROM_ALL_SPECIFIC.getAction()));
	        menuStructure.add(new CMBaseJMenuItem(CMAction.TESTDATA_GENERATE_RESULT_STRUCTURE.getAction()));//svonborries_11052006
	        menuStructure.addSeparator();
	        menuStructure.add(new CMBaseJMenuItem(CMAction.TESTDATA_GENERATE_STRUCTURE.getAction()));
	        menuStructure.addSeparator();
	        menuStructure.add(new CMBaseJMenuItem(CMAction.TESTDATA_CREATE_TDSTRUCTURE.getAction()));
			menuStructure.add(new CMBaseJMenuItem(CMAction.TESTDATA_EDIT_TDSTRUCTURE.getAction()));
	        menuStructure.add(new CMBaseJMenuItem(CMAction.TESTDATA_DELETE_TDSTRUCTURE.getAction()));
			menuStructure.addSeparator();
			menuStructure.add(createSubMenuStructureRow());
			//menuStructure.addMouseListener(new MouseAdapter(){public void mouseClicked(MouseEvent e){eventMouseClicked(e);}});
		return menuStructure;
	}
	private static JideMenu createSubMenuStructureRow(){
			JideMenu subMenuStructureRow = new JideMenu();
	        subMenuStructureRow.setText(CMMessages.getString("TESTDATA_STRUCTURE_ROW"));
	        subMenuStructureRow.setMnemonic(CMMessages.getString("ROWS_AND_COLUMNS_MNEMONIC").charAt(0));
	        subMenuStructureRow.add(new CMBaseJMenuItem(CMAction.TESTDATA_INSERT_ELEMENT.getAction()));
	        subMenuStructureRow.add(new CMBaseJMenuItem(CMAction.TESTDATA_DELETE_ELEMENT.getAction()));
	        subMenuStructureRow.addSeparator();
			subMenuStructureRow.add(new CMBaseJMenuItem(CMAction.TESTDATA_ASSIGN_GLOBAL_REFERENCE.getAction(),true));
	        subMenuStructureRow.add(new CMBaseJMenuItem(CMAction.TESTDATA_CANCEL_GLOBAL_REFERENCE.getAction(),true));
	        subMenuStructureRow.addSeparator();
	        subMenuStructureRow.add(new CMBaseJMenuItem(CMAction.TESTDATA_INSERT_NEW_COLUMN.getAction()));
	        subMenuStructureRow.add(new CMBaseJMenuItem(CMAction.TESTDATA_DELETE_COLUMN_ADDED.getAction()));
	        subMenuStructureRow.add(new CMBaseJMenuItem(CMAction.TESTDATA_CHANGE_NAME_COLUMN_ADDED.getAction()));

		return subMenuStructureRow;
	}
	public static JMenu createMenuFormulas(){
			JideMenu jMenuFormulas = new JideMenu();
			jMenuFormulas.setText(CMMessages.getString("TESTDATA_MENU_FORMULA"));
		    jMenuFormulas.setMnemonic(CMMessages.getString("FORMULA_MNEMONIC").charAt(0));
			jMenuFormulas.add(new CMBaseJMenuItem(CMAction.TESTDATA_ADD_FORMULA.getAction()));
	        jMenuFormulas.add(new CMBaseJMenuItem(CMAction.TESTDATA_EDIT_FORMULA.getAction()));
			jMenuFormulas.add(new CMBaseJMenuItem(CMAction.TESTDATA_DELETE_FORMULA.getAction()));
			jMenuFormulas.addSeparator();//svonborries_14112005
	        jMenuFormulas.add(new CMBaseJMenuItem(CMAction.TESTDATA_ADD_VARIABLE.getAction()));
	        jMenuFormulas.add(new CMBaseJMenuItem(CMAction.TESTDATA_DELETE_VARIABLE.getAction()));
	        jMenuFormulas.addSeparator();//svonborries_14112005
	        jMenuFormulas.add(new CMBaseJMenuItem(CMAction.TESTDATA_ADD_LINK_ELEMENT.getAction()));//svonborries_11112005
	        jMenuFormulas.add(new CMBaseJMenuItem(CMAction.TESTDATA_DELETE_LINK_ELEMENT.getAction()));//svonborries_11112005
		return jMenuFormulas;
	}

	static JideMenu  createMenuTestDataCombination(){
			JideMenu menuTestDataCombination = new JideMenu();
			menuTestDataCombination.setText(CMMessages.getString("TESTDATA_MENU_TITLE"));
			menuTestDataCombination.setMnemonic(CMMessages.getString("TEST_DATA_MNEMONIC").charAt(0));
	        menuTestDataCombination.add(new CMBaseJMenuItem(CMAction.TESTDATA_CREATE_TESTDATA.getAction()));
	        menuTestDataCombination.add(new CMBaseJMenuItem(CMAction.TESTDATA_EDIT_TESTDATA.getAction()));
	        menuTestDataCombination.add(new CMBaseJMenuItem(CMAction.TESTDATA_DELETE_TESTDATA.getAction()));
	        menuTestDataCombination.addSeparator();
	        menuTestDataCombination.add(new CMBaseJMenuItem(CMAction.TESTDATA_ASSIGN_TESTCASE_TO_TESTDATA.getAction()));
	        menuTestDataCombination.add(new CMBaseJMenuItem(CMAction.TESTDATA_CANCEL_TESTCASE_TO_TESTDATA.getAction()));
	        menuTestDataCombination.addSeparator();
	        menuTestDataCombination.add(new CMBaseJMenuItem(CMAction.TESTDATA_IMPORT_TESTDATA.getAction()));
	        menuTestDataCombination.addSeparator();
			menuTestDataCombination.add(createSubMenuTestDataStructure());

		return menuTestDataCombination;
	}

	static  JideMenu createMenuTestDataSet(){
			JideMenu menuTestDataSet = new JideMenu();
			menuTestDataSet.setText(CMMessages.getString("TESTDATA_TESTDATASET"));
			menuTestDataSet.setMnemonic(CMMessages.getString("TEST_DATA_SET_MNEMONIC").charAt(0));
	        menuTestDataSet.add(new CMBaseJMenuItem(CMAction.TESTDATASET_NEW_TESTDATASET.getAction()));
	        menuTestDataSet.add(new CMBaseJMenuItem(CMAction.TESTDATASET_EDIT_TESTDATASET.getAction()));
	        menuTestDataSet.add(new CMBaseJMenuItem(CMAction.TESTDATASET_DELETE_TESTDATASET.getAction()));
	        menuTestDataSet.addSeparator();
	        menuTestDataSet.add(new CMBaseJMenuItem(CMAction.TESTDATASET_GENERATE_TESTDATASET_REPORT.getAction()));
		return menuTestDataSet;
	}
	private static JideMenu createSubMenuTestDataStructure(){
			JideMenu subMenuTestDataStructure = new JideMenu();
			subMenuTestDataStructure.setText(CMMessages.getString("TESTDATA_TDSTRUCTURE"));
		    subMenuTestDataStructure.setMnemonic(CMMessages.getString("STRUCTURE_MNEMONIC").charAt(0));
			subMenuTestDataStructure.add(new CMBaseJMenuItem(CMAction.TESTDATA_ASSIGN_GLOBAL_STRUCTURE.getAction()));
	        subMenuTestDataStructure.add(new CMBaseJMenuItem(CMAction.TESTDATA_CANCEL_GLOBAL_STRUCTURE.getAction()));
	        subMenuTestDataStructure.addSeparator();
			subMenuTestDataStructure.add(new CMBaseJMenuItem(CMAction.TESTDATA_EDIT_TDSTRUCTURE.getAction()));
			subMenuTestDataStructure.addSeparator();
	        subMenuTestDataStructure.add(new CMBaseJMenuItem(CMAction.TESTDATA_ASSIGN_GLOBAL_VALUE.getAction(),true));
	        subMenuTestDataStructure.add(new CMBaseJMenuItem(CMAction.TESTDATA_ASSIGN_GLOBAL_REFERENCE.getAction(),true));
	        subMenuTestDataStructure.add(new CMBaseJMenuItem(CMAction.TESTDATA_CANCEL_GLOBAL_REFERENCE.getAction(),true));

		return subMenuTestDataStructure;
	}
	 static CommandBar createToolBarTestData() {
		 CommandBar  toolbar = new CommandBar("Test Data");
	    	toolbar.setInitSide(DockableBarContext.DOCK_SIDE_NORTH);
			toolbar.setInitMode(DockableBarContext.STATE_HORI_DOCKED);
			toolbar.setInitIndex(1);
			toolbar.setInitSubindex(2);
			toolbar.add(new CMToolBarButton(CMAction.TESTDATA_CREATE_TESTDATA.getAction()));
			toolbar.add(new CMToolBarButton(CMAction.TESTDATA_EDIT_TESTDATA.getAction()));
			toolbar.add(new CMToolBarButton(CMAction.TESTDATA_DELETE_TESTDATA.getAction()));
			toolbar.addSeparator(new Dimension(8,20));
			toolbar.add(new CMToolBarButton(CMAction.TESTDATA_ASSIGN_TESTCASE_TO_TESTDATA.getAction()));
			toolbar.add(new CMToolBarButton(CMAction.TESTDATA_CANCEL_TESTCASE_TO_TESTDATA.getAction()));
			toolbar.addSeparator(new Dimension(8,20));
			toolbar.add(new CMToolBarButton(CMAction.TESTDATA_ASSIGN_GLOBAL_VALUE.getAction()));
			toolbar.add(new CMToolBarButton(CMAction.TESTDATA_ASSIGN_GLOBAL_REFERENCE.getAction()));
			toolbar.add(new CMToolBarButton(CMAction.TESTDATA_CANCEL_GLOBAL_REFERENCE.getAction()));
			toolbar.addSeparator(new Dimension(8,20));
			toolbar.add(new CMToolBarButton(CMAction.TESTDATA_EDIT_TDSTRUCTURE.getAction()));
			toolbar.addSeparator(new Dimension(8,20));
			toolbar.add(new CMToolBarButton(CMAction.TESTDATA_ASSIGN_GLOBAL_STRUCTURE.getAction()));
			toolbar.add(new CMToolBarButton(CMAction.TESTDATA_CANCEL_GLOBAL_STRUCTURE.getAction()));
			toolbar.addSeparator(new Dimension(8,20));
			toolbar.add(new CMToolBarButton(CMAction.TESTDATA_IMPORT_TESTDATA.getAction()));
			return toolbar;
	}
	 static CommandBar createToolBarTestDataSet() {
		 CommandBar  toolbar = new CommandBar("Test Data Set");
	    	toolbar.setInitSide(DockableBarContext.DOCK_SIDE_NORTH);
			toolbar.setInitMode(DockableBarContext.STATE_HORI_DOCKED);
			toolbar.setInitIndex(1);
			toolbar.setInitSubindex(2);
			toolbar.add(new CMToolBarButton(CMAction.TESTDATASET_NEW_TESTDATASET.getAction()));
			toolbar.add(new CMToolBarButton(CMAction.TESTDATASET_EDIT_TESTDATASET.getAction()));
			toolbar.add(new CMToolBarButton(CMAction.TESTDATASET_DELETE_TESTDATASET.getAction()));
			toolbar.addSeparator(new Dimension(8,20));
			toolbar.add(new CMToolBarButton(CMAction.TESTDATASET_GENERATE_TESTDATASET_REPORT.getAction()));
			return toolbar;
	}

	 static JideMenu createMenuTestDataSetReports(){
				JideMenu jMenuTestDataSetReports = new JideMenu();
				jMenuTestDataSetReports.setText(CMMessages.getString("TESTDATASET_REPORTS_MENU"));
				jMenuTestDataSetReports.setMnemonic(CMMessages.getString("TEST_DATA_SET_REPORTS_MNEMONIC").charAt(0));
				jMenuTestDataSetReports.add(new CMBaseJMenuItem(CMAction.TESTDATASETREPORT_OPEN_REPORT.getAction()));
				jMenuTestDataSetReports.add(new CMBaseJMenuItem(CMAction.TESTDATASETREPORT_OPEN_REPORT_FOLDER.getAction()));
				jMenuTestDataSetReports.addSeparator();

				jMenuTestDataSetReports.add(new CMBaseJMenuItem(CMAction.TESTDATASETREPORT_UPDATE.getAction()));
				jMenuTestDataSetReports.addSeparator();
				jMenuTestDataSetReports.add(new CMBaseJMenuItem(CMAction.TESTDATASETREPORT_ADD_REPORT.getAction()));
				jMenuTestDataSetReports.add(new CMBaseJMenuItem(CMAction.TESTDATASETREPORT_EDIT_REPORT.getAction()));
				jMenuTestDataSetReports.add(new CMBaseJMenuItem(CMAction.TESTDATASETREPORT_DELETE_REPORT.getAction()));

			return jMenuTestDataSetReports;
		}

	 static CommandBar createToolBarTestDataSetReports() {
		 CommandBar  toolbar = new CommandBar("Test Data Set Reports");
	    	toolbar.setInitSide(DockableBarContext.DOCK_SIDE_NORTH);
			toolbar.setInitMode(DockableBarContext.STATE_HORI_DOCKED);
			toolbar.setInitIndex(1);
			toolbar.setInitSubindex(2);
			toolbar.add(new CMToolBarButton(CMAction.TESTDATASETREPORT_OPEN_REPORT.getAction()));
			toolbar.add(new CMToolBarButton(CMAction.TESTDATASETREPORT_OPEN_REPORT_FOLDER.getAction()));//ccastedo 31.08.06
			toolbar.addSeparator(new Dimension(8,20));
			toolbar.add(new CMToolBarButton(CMAction.TESTDATASETREPORT_UPDATE.getAction()));
			toolbar.addSeparator(new Dimension(8,20));
			toolbar.add(new CMToolBarButton(CMAction.TESTDATASETREPORT_ADD_REPORT.getAction()));
			toolbar.add(new CMToolBarButton(CMAction.TESTDATASETREPORT_EDIT_REPORT.getAction()));
			toolbar.add( new CMToolBarButton(CMAction.TESTDATASETREPORT_DELETE_REPORT.getAction()));
			return toolbar;
	}
		static JideMenu createMenuVariables(){
				JideMenu menuVariables = new JideMenu();
		        menuVariables.setText(CMMessages.getString("TESTDATA_VARIABLES"));
		        menuVariables.setMnemonic(CMMessages.getString("VARIABLES_MNEMONIC").charAt(0));
		        menuVariables.add(new CMBaseJMenuItem(CMAction.TESTDATA_VARIABLE_CREATE.getAction()));
		        menuVariables.add(new CMBaseJMenuItem(CMAction.TESTDATA_VARIABLE_EDIT.getAction()));
		        menuVariables.add(new CMBaseJMenuItem(CMAction.TESTDATA_VARIABLE_DELETE.getAction()));
			return menuVariables;
		}
		static CommandBar createToolBarVariables() {
			 CommandBar  toolbar = new CommandBar("Variables");
		    	toolbar.setInitSide(DockableBarContext.DOCK_SIDE_NORTH);
				toolbar.setInitMode(DockableBarContext.STATE_HORI_DOCKED);
				toolbar.setInitIndex(1);
				toolbar.setInitSubindex(2);
				toolbar.add(new CMToolBarButton(CMAction.TESTDATA_VARIABLE_CREATE.getAction()));
				toolbar.add(new CMToolBarButton(CMAction.TESTDATA_VARIABLE_EDIT.getAction()));
				toolbar.add(new CMToolBarButton(CMAction.TESTDATA_VARIABLE_DELETE.getAction()));

				return toolbar;
		}
		static  JideMenu createMenuResultComparison(){
			JideMenu menuResultsComparation = new JideMenu();

	        menuResultsComparation.setActionCommand("Results Comparison"); //$NON-NLS-1$
	        menuResultsComparation.setText(CMMessages.getString("MENU_RESULT_COMPARATION")); //$NON-NLS-1$
	        menuResultsComparation.setMnemonic(CMMessages.getString("MENU_RESULT_COMPARATION_MNEMONIC").charAt(0));
	        menuResultsComparation.add(new CMBaseJMenuItem(CMAction.RESULT_COMPARISON_CREATE.getAction()));
			menuResultsComparation.add(new CMBaseJMenuItem(CMAction.RESULT_COMPARISON_EDIT.getAction()));
			menuResultsComparation.add(new CMBaseJMenuItem(CMAction.RESULT_COMPARISON_REPORT.getAction()));
			return menuResultsComparation;
	}
		static CommandBar createToolBarResultComparison() {
			 CommandBar  toolbar = new CommandBar("Result Comparison");
		    	toolbar.setInitSide(DockableBarContext.DOCK_SIDE_NORTH);
				toolbar.setInitMode(DockableBarContext.STATE_HORI_DOCKED);
				toolbar.setInitIndex(1);
				toolbar.setInitSubindex(2);
				toolbar.add(new CMToolBarButton(CMAction.RESULT_COMPARISON_CREATE.getAction()));
				toolbar.add(new CMToolBarButton(CMAction.RESULT_COMPARISON_EDIT.getAction()));
				toolbar.add(new CMToolBarButton(CMAction.RESULT_COMPARISON_REPORT.getAction()));

				return toolbar;
		}
		static JideMenu createMenuError() {
				JideMenu menuError = new JideMenu();
		        menuError.setText(CMMessages.getString("MENU_ERROR")); //$NON-NLS-1$
		        menuError.setMnemonic(CMMessages.getString("MENU_ERROR_MNEMONIC").charAt(0));
		         menuError.add(new CMBaseJMenuItem(CMAction.ERROR_CREATE.getAction()));
		        menuError.add(new CMBaseJMenuItem(CMAction.ERROR_EDIT.getAction()));
		        menuError.add(new CMBaseJMenuItem(CMAction.ERROR_DELETE.getAction()));
		        menuError.addSeparator();
		        menuError.add(new CMBaseJMenuItem(CMAction.ERROR_ASSIGN_TESTCASES.getAction()));
		        menuError.addSeparator();
		        menuError.add(new CMBaseJMenuItem(CMAction.ERROR_REPORT_LIST.getAction()));
			return menuError;
		}

		static CommandBar createToolBarErrors() {

				CommandBar toolBarErrors = new CommandBar("Errors");
				toolBarErrors.setInitSide(DockableBarContext.DOCK_SIDE_NORTH);
				toolBarErrors.setInitMode(DockableBarContext.STATE_HORI_DOCKED);
				toolBarErrors.setInitIndex(1);
				toolBarErrors.setInitSubindex(2);

			        toolBarErrors.add(new CMToolBarButton(CMAction.ERROR_CREATE.getAction()));
			        toolBarErrors.add(new CMToolBarButton(CMAction.ERROR_EDIT.getAction()));
			        toolBarErrors.add(new CMToolBarButton(CMAction.ERROR_DELETE.getAction()));
			        toolBarErrors.addSeparator(new Dimension(8,20));
			        toolBarErrors.add(new CMToolBarButton(CMAction.ERROR_ASSIGN_TESTCASES.getAction()));
			        toolBarErrors.add(new CMToolBarButton(CMAction.ERROR_REPORT_LIST.getAction()));
			return toolBarErrors;
		}

		static JMenu createMenuElement() {

				JideMenu menuElement = new JideMenu();
			        menuElement.setText(CMMessages.getString("MENU_ELEMENT")); //$NON-NLS-1$
			        menuElement.setMnemonic(CMMessages.getString("MENU_ELEMENT_MNEMONIC").charAt(0));
			        menuElement.add(new CMBaseJMenuItem(CMAction.ELEMENT_CREATE.getAction()));
			        menuElement.add(new CMBaseJMenuItem(CMAction.ELEMENT_INSERT.getAction()));
			        menuElement.add(new CMBaseJMenuItem(CMAction.ELEMENT_DELETE.getAction()));
			        menuElement.addSeparator();
			        menuElement.add(new CMBaseJMenuItem(CMAction.ELEMENT_REORDER.getAction()));
			        menuElement.add(new CMBaseJMenuItem(CMAction.IMPORT_CSV.getAction()));

			return menuElement;
		}


		static JideMenu createMenuEquivalenceClass() {

			JideMenu menuEquivalenceClass = new JideMenu();
	        menuEquivalenceClass.setText(CMMessages.getString("MENU_EQUIVALENCE_CLASS")); //$NON-NLS-1$
	        menuEquivalenceClass.setMnemonic(CMMessages.getString("MENU_EQUIVALENCE_CLASS_MNEMONIC").charAt(0));
	        menuEquivalenceClass.add(new CMBaseJMenuItem(CMAction.EQUIVALENCECLASS_CREATE.getAction()));
	        menuEquivalenceClass.add(new CMBaseJMenuItem(CMAction.EQUIVALENCECLASS_EDIT.getAction()));
	        menuEquivalenceClass.add(new CMBaseJMenuItem(CMAction.EQUIVALENCECLASS_DELETE.getAction()));
	        menuEquivalenceClass.addSeparator();
	        menuEquivalenceClass.add(new CMBaseJMenuItem(CMAction.EQUIVALENCECLASS_CREATE_ASSIGN_EFFECT.getAction()));
	        menuEquivalenceClass.add(new CMBaseJMenuItem(CMAction.EQUIVALENCECLASS_EDIT_ASSIGNED_EFFECTS.getAction()));

			return menuEquivalenceClass;
		}

		static CommandBar createToolBarElements() {

		     CommandBar toolBarElements = new CommandBar("Elements and Equivalence Classes");
		     toolBarElements.setInitSide(DockableBarContext.DOCK_SIDE_NORTH);
		     toolBarElements.setInitMode(DockableBarContext.STATE_HORI_DOCKED);
		     toolBarElements.setInitIndex(1);
		     toolBarElements.setInitSubindex(2);

		        toolBarElements.add(new CMToolBarButton(CMAction.ELEMENT_CREATE.getAction()));
		        toolBarElements.add(new CMToolBarButton(CMAction.ELEMENT_INSERT.getAction()));
		        toolBarElements.add(new CMToolBarButton(CMAction.ELEMENT_DELETE.getAction()));
		        toolBarElements.add(new CMToolBarButton(CMAction.ELEMENT_REORDER.getAction()));
		        toolBarElements.addSeparator(new Dimension(8,20));
		        toolBarElements.add(new CMToolBarButton(CMAction.EQUIVALENCECLASS_CREATE.getAction()));
		        toolBarElements.add(new CMToolBarButton(CMAction.EQUIVALENCECLASS_EDIT.getAction()));
		        toolBarElements.add(new CMToolBarButton(CMAction.EQUIVALENCECLASS_DELETE.getAction()));
		        toolBarElements.addSeparator(new Dimension(8,20));
		        toolBarElements.add(new CMToolBarButton(CMAction.EQUIVALENCECLASS_CREATE_ASSIGN_EFFECT.getAction()));
		        toolBarElements.add(new CMToolBarButton(CMAction.EQUIVALENCECLASS_EDIT_ASSIGNED_EFFECTS.getAction()));
		        toolBarElements.addSeparator(new Dimension(8,20));
		        toolBarElements.add(new CMToolBarButton(CMAction.IMPORT_CSV.getAction()));

			return toolBarElements;
		}

		static JideMenu createMenuEffects() {

				JideMenu menuCauseEffect1 = new JideMenu();
		        menuCauseEffect1.setText(CMMessages.getString("MENU_CAUSE_EFFECT")); //$NON-NLS-1$
		        menuCauseEffect1.setMnemonic(CMMessages.getString("MENU_CAUSE_EFFECT_MNEMONIC").charAt(0));

		        menuCauseEffect1.add(new CMBaseJMenuItem(CMAction.EFFECT_CREATE.getAction()));
		        menuCauseEffect1.add(new CMBaseJMenuItem(CMAction.EFFECT_EDIT.getAction()));
		        menuCauseEffect1.add(new CMBaseJMenuItem(CMAction.EFFECT_DELETE.getAction()));
		        menuCauseEffect1.addSeparator();
				menuCauseEffect1.add(new CMBaseJMenuItem(CMAction.EFFECT_DELETE_NOT_USED.getAction()));
				menuCauseEffect1.addSeparator();
				menuCauseEffect1.add(new CMBaseJMenuItem(CMAction.IMPORT_CSV.getAction()));

			return menuCauseEffect1;
		}

		static CommandBar createToolBarCauseEffects() {

				CommandBar toolBarCauseEffects = new CommandBar("Effects");
				toolBarCauseEffects.setInitSide(DockableBarContext.DOCK_SIDE_NORTH);
				toolBarCauseEffects.setInitMode(DockableBarContext.STATE_HORI_DOCKED);
				toolBarCauseEffects.setInitIndex(1);
				toolBarCauseEffects.setInitSubindex(2);
		        toolBarCauseEffects.add(new CMToolBarButton(CMAction.EFFECT_CREATE.getAction()));
		        toolBarCauseEffects.add( new CMToolBarButton(CMAction.EFFECT_EDIT.getAction()));
		        toolBarCauseEffects.add(new CMToolBarButton(CMAction.EFFECT_DELETE.getAction()));
		        toolBarCauseEffects.addSeparator(new Dimension(8,20));
				toolBarCauseEffects.add( new CMToolBarButton(CMAction.EFFECT_DELETE_NOT_USED.getAction()));
				toolBarCauseEffects.addSeparator(new Dimension(8,20));
				toolBarCauseEffects.add(new CMToolBarButton(CMAction.IMPORT_CSV.getAction()));

			return toolBarCauseEffects;
		}
		static JMenu createMenuDependency() {

			JideMenu menuDependency = new JideMenu();
	        JideMenu menuItemDependencyGenCombinations = new JideMenu(CMAction.DEPENDENCY_GENERATE_COMB.getAction());
	        menuDependency.setText(CMMessages.getString("MENU_DEPENDENCY")); //$NON-NLS-1$
	        menuDependency.setMnemonic(CMMessages.getString("MENU_DEPENDENCY_MNEMONIC").charAt(0));
	  	    menuDependency.add(new CMBaseJMenuItem(CMAction.DEPENDENCY_CREATE.getAction()));
	        menuDependency.add(new CMBaseJMenuItem(CMAction.DEPENDENCY_EDIT.getAction()));
	        menuDependency.add(new CMBaseJMenuItem(CMAction.DEPENDENCY_DELETE.getAction()));
	        menuDependency.addSeparator();
	        menuDependency.add(menuItemDependencyGenCombinations);

	        menuItemDependencyGenCombinations.add(new CMBaseJMenuItem(CMAction.DEPENDENCY_GENERATE_COMB_PERMUTATION.getAction()));
	        menuItemDependencyGenCombinations.add(new CMBaseJMenuItem(CMAction.DEPENDENCY_GENERATE_COMB_ALLPAIRS.getAction()));
	    	menuDependency.addSeparator();
	        menuDependency.add(new CMBaseJMenuItem(CMAction.DEPENDENCY_REPORT_EXCEL.getAction()));

		return menuDependency;
	}

		/**
		*@autor smoreno
		 * @return
		 */
		static JMenu createMenuCombination() {

				JideMenu menu = new JideMenu();
				menu .setText(CMMessages.getString("MENU_COMBINATION")); //$NON-NLS-1$
				menu.setMnemonic(CMMessages.getString("MENU_COMBINATION_MNEMONIC").charAt(0));

				menu.add(new CMBaseJMenuItem(CMAction.COMBINATION_CREATE.getAction()));
				menu.add(new CMBaseJMenuItem(CMAction.COMBINATION_EDIT.getAction()));
				menu.add(new CMBaseJMenuItem(CMAction.COMBINATION_DELETE.getAction()));
				menu.addSeparator();
				menu.add(new CMBaseJMenuItem(CMAction.COMBINATION_MERGE.getAction()));
				menu.add(new CMBaseJMenuItem(CMAction.COMBINATION_UNMERGE.getAction()));
				menu.addSeparator();
				menu.add(new CMBaseJMenuItem(CMAction.COMBINATION_ASSIGN_TO_TESTCASE.getAction()));
				//menu.add(new CMBaseJMenuItem(CMAction.COMBINATION_ASSIGN_EFFECTS.getAction()));
				menu.add(new CMBaseJMenuItem(CMAction.COMBINATION_CREATE_ASSIGN_EFFECTS.getAction()));
				menu.add(new CMBaseJMenuItem(CMAction.COMBINATION_EDIT_EFFECTS.getAction()));
				menu.addSeparator();
				menu.add(new CMBaseJMenuItem(CMAction.COMBINATION_DELETE_ALL.getAction()));
		    return menu;
			}
		static CommandBar createToolBarDependenciesCombinations() {

				CommandBar toolBarDependenciesCombinations = new CommandBar("Dependencies/Combinations");
				toolBarDependenciesCombinations.setInitSide(DockableBarContext.DOCK_SIDE_NORTH);
				toolBarDependenciesCombinations.setInitMode(DockableBarContext.STATE_HORI_DOCKED);
				toolBarDependenciesCombinations.setInitIndex(1);
				toolBarDependenciesCombinations.setInitSubindex(2);
			        toolBarDependenciesCombinations.add(new CMToolBarButton(CMAction.DEPENDENCY_CREATE.getAction()));
			        toolBarDependenciesCombinations.add(new CMToolBarButton(CMAction.DEPENDENCY_EDIT.getAction()));
			        toolBarDependenciesCombinations.add(new CMToolBarButton(CMAction.DEPENDENCY_DELETE.getAction()));
			        toolBarDependenciesCombinations.addSeparator(new Dimension(8,20));
			        toolBarDependenciesCombinations.add(new CMToolBarButton(CMAction.DEPENDENCY_GENERATE_COMB_PERMUTATION.getAction()));
			        toolBarDependenciesCombinations.add(new CMToolBarButton(CMAction.DEPENDENCY_GENERATE_COMB_ALLPAIRS.getAction()));
			        toolBarDependenciesCombinations.addSeparator(new Dimension(8,20));
			        toolBarDependenciesCombinations.add(new CMToolBarButton(CMAction.COMBINATION_MERGE.getAction()));
			        toolBarDependenciesCombinations.add(new CMToolBarButton(CMAction.COMBINATION_UNMERGE.getAction()));
			        toolBarDependenciesCombinations.addSeparator(new Dimension(8,20));
			        toolBarDependenciesCombinations.add(new CMToolBarButton(CMAction.COMBINATION_CREATE.getAction()));
			        toolBarDependenciesCombinations.add(new CMToolBarButton(CMAction.COMBINATION_EDIT.getAction()));
			        toolBarDependenciesCombinations.add(new CMToolBarButton(CMAction.COMBINATION_DELETE.getAction()));
			        toolBarDependenciesCombinations.addSeparator(new Dimension(8,20));
			        toolBarDependenciesCombinations.add(new CMToolBarButton(CMAction.COMBINATION_ASSIGN_TO_TESTCASE.getAction()));
			        //toolBarDependenciesCombinations.add(new CMToolBarButton(CMAction.COMBINATION_ASSIGN_EFFECTS.getAction()));
			        toolBarDependenciesCombinations.add(new CMToolBarButton(CMAction.COMBINATION_CREATE_ASSIGN_EFFECTS.getAction()));
			        toolBarDependenciesCombinations.add(new CMToolBarButton(CMAction.COMBINATION_EDIT_EFFECTS.getAction()));
			        toolBarDependenciesCombinations.addSeparator(new Dimension(8,20));
			        toolBarDependenciesCombinations.add(new CMToolBarButton(CMAction.DEPENDENCY_REPORT_EXCEL.getAction()));
			return toolBarDependenciesCombinations;
		}

		static JideMenu createMenuTestCase() {


				   JideMenu menuTestCase = new JideMenu();
			        menuTestCase.setText(CMMessages.getString("MENU_TEST_CASE")); //$NON-NLS-1$
			        menuTestCase.setMnemonic(CMMessages.getString("MENU_TEST_CASE_MNEMONIC").charAt(0));
			        menuTestCase.add(new CMBaseJMenuItem(CMAction.TESTCASE_CREATE.getAction()));
			        menuTestCase.add(new CMBaseJMenuItem(CMAction.TESTCASE_EDIT.getAction()));
			        menuTestCase.add(new CMBaseJMenuItem(CMAction.TESTCASE_DELETE.getAction()));
			        menuTestCase.addSeparator();
			        menuTestCase.add(new CMBaseJMenuItem(CMAction.TESTCASE_ASSIGN_EQUIVALENCECLASS.getAction()));
			        menuTestCase.add(new CMBaseJMenuItem(CMAction.TESTCASE_ASSIGN_COMBINATION.getAction()));
			        menuTestCase.add(new CMBaseJMenuItem(CMAction.TESTCASE_ASSIGN_STDCOMBINATION.getAction()));
			        menuTestCase.add(new CMBaseJMenuItem(CMAction.TESTCASE_ASSIGN_ERRORS.getAction()));
			        menuTestCase.addSeparator();
			        menuTestCase.add(new CMBaseJMenuItem(CMAction.TESTCASE_CANCEL_ASSIGN_EQUIVALENCECLASS.getAction()));
			        menuTestCase.addSeparator();
			        menuTestCase.add(new CMBaseJMenuItem(CMAction.TESTCASE_GENERATE.getAction()));
			        menuTestCase.addSeparator();
			        menuTestCase.add(new CMBaseJMenuItem(CMAction.TESTCASE_REPORT_LIST1.getAction()));
			       // menuTestCase.add(new CMBaseJMenuItem(CMAction.TESTCASE_REPORT_LIST2.getAction()));
			       // menuTestCase.add(new CMBaseJMenuItem(CMAction.TESTCASE_REPORT_CSV.getAction()));
			       // menuTestCase.add(new CMBaseJMenuItem(CMAction.TESTCASE_REPORT_COMPUWARE.getAction()));
			    //    menuTestCase.add(new CMBaseJMenuItem(CMAction.TESTCASE_REPORT_QADIRECTOR.getAction()));
			        menuTestCase.addSeparator();
			        menuTestCase.add(new CMBaseJMenuItem(CMAction.TESTCASE_DELETE_ALL.getAction()));



			return menuTestCase;
		}

		static CommandBar createToolBarTestCases() {
				CommandBar toolBarTestCases = new CommandBar("Test Cases");
				toolBarTestCases.setInitSide(DockableBarContext.DOCK_SIDE_NORTH);
				toolBarTestCases.setInitMode(DockableBarContext.STATE_HORI_DOCKED);
				toolBarTestCases.setInitIndex(1);
				toolBarTestCases.setInitSubindex(2);

	             toolBarTestCases.add(new CMToolBarButton(CMAction.TESTCASE_CREATE.getAction()));
	             toolBarTestCases.add(new CMToolBarButton(CMAction.TESTCASE_EDIT.getAction()));
		         toolBarTestCases.add(new CMToolBarButton(CMAction.TESTCASE_DELETE.getAction()));
		         toolBarTestCases.addSeparator(new Dimension(8,20));
		         toolBarTestCases.add(new CMToolBarButton(CMAction.TESTCASE_ASSIGN_EQUIVALENCECLASS.getAction()));
		         toolBarTestCases.add(new CMToolBarButton(CMAction.TESTCASE_ASSIGN_COMBINATION.getAction()));
		         toolBarTestCases.add(new CMToolBarButton(CMAction.TESTCASE_ASSIGN_STDCOMBINATION.getAction()));
		         toolBarTestCases.add(new CMToolBarButton(CMAction.TESTCASE_ASSIGN_ERRORS.getAction()));
		         toolBarTestCases.addSeparator(new Dimension(8,20));
		         toolBarTestCases.add(new CMToolBarButton(CMAction.TESTCASE_CANCEL_ASSIGN_EQUIVALENCECLASS.getAction()));
		         toolBarTestCases.addSeparator(new Dimension(8,20));
		         toolBarTestCases.add(new CMToolBarButton(CMAction.TESTCASE_GENERATE.getAction()));
		         toolBarTestCases.addSeparator(new Dimension(8,20));
		         toolBarTestCases.add(new CMToolBarButton(CMAction.TESTCASE_REPORT_LIST1.getAction()));

			return toolBarTestCases;
		}

		static JideMenu createMenuStdCombination() {

				JideMenu menuStdCombination = new JideMenu();

		        menuStdCombination.setText(CMMessages.getString("MENU_STD_COMBINATION")); //$NON-NLS-1$
		        menuStdCombination.setMnemonic(CMMessages.getString("MENU_STD_COMBINATION_MNEMONIC").charAt(0));
		        menuStdCombination.add(new CMBaseJMenuItem(CMAction.STDCOMBINATION_CREATE.getAction()));
		        menuStdCombination.add(new CMBaseJMenuItem(CMAction.STDCOMBINATION_EDIT.getAction()));
		        menuStdCombination.add(new CMBaseJMenuItem(CMAction.STDCOMBINATION_DELETE.getAction()));
		        menuStdCombination.addSeparator();
		        menuStdCombination.add(new CMBaseJMenuItem(CMAction.STDCOMBINATION_ASSIGN_EQUIVALENCECLASS.getAction()));
		        menuStdCombination.add(new CMBaseJMenuItem(CMAction.STDCOMBINATION_ASSIGN_TEST_CASES.getAction()));
		        menuStdCombination.addSeparator();
		        menuStdCombination.add(new CMBaseJMenuItem(CMAction.STDCOMBINATION_CANCEL_ASSIGN_EQUIVALENCECLASS.getAction()));
				return menuStdCombination;
		}

		static CommandBar createToolBarStdCombinations() {

				CommandBar toolBarStdCombinations = new CommandBar("Std Combinations");
				toolBarStdCombinations.setInitSide(DockableBarContext.DOCK_SIDE_NORTH);
				toolBarStdCombinations.setInitMode(DockableBarContext.STATE_HORI_DOCKED);
				toolBarStdCombinations.setInitIndex(1);
				toolBarStdCombinations.setInitSubindex(2);
		        toolBarStdCombinations.add(new CMToolBarButton(CMAction.STDCOMBINATION_CREATE.getAction()));
		        toolBarStdCombinations.add(new CMToolBarButton(CMAction.STDCOMBINATION_EDIT.getAction()));
		        toolBarStdCombinations.add(new CMToolBarButton(CMAction.STDCOMBINATION_DELETE.getAction()));
		        toolBarStdCombinations.addSeparator(new Dimension(8,20));
		        toolBarStdCombinations.add(new CMToolBarButton(CMAction.STDCOMBINATION_ASSIGN_EQUIVALENCECLASS.getAction()));
		        toolBarStdCombinations.add(new CMToolBarButton(CMAction.STDCOMBINATION_ASSIGN_TEST_CASES.getAction()));
		        toolBarStdCombinations.addSeparator(new Dimension(8,20));
		        toolBarStdCombinations.add(new CMToolBarButton(CMAction.STDCOMBINATION_CANCEL_ASSIGN_EQUIVALENCECLASS.getAction()));

			return toolBarStdCombinations;
		}


}

