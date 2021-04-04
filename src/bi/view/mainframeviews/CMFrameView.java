/*******************************************************************************
Developed by BUSINESS SOFTWARE INNOVATIONS. .
Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
*******************************************************************************/

package bi.view.mainframeviews;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Insets;
import java.awt.KeyboardFocusManager;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EventObject;
import java.util.List;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JViewport;
import javax.swing.KeyStroke;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import model.BusinessRules;
import model.Project2;
import model.ProjectReference;
import model.Session2;
import model.Structure;
import model.TDStructure;
import model.TestObject;
import model.TestObjectReference;

import org.apache.log4j.Logger;

import bi.controller.ToolVendorManager;
import bi.controller.WorkspaceManager;
import bi.controller.utils.CMClipBoard;
import bi.view.actions.CMAction;
import bi.view.actions.edit.CMFindReplaceAction;
import bi.view.actions.edit.CMSelectAllAction;
import bi.view.businessrulesviews.CMBusinessRulesPanelView;
import bi.view.businessrulesviews.CMToolBarBusinessRules;
import bi.view.businessrulesviews.editor.CMCustomJTextPane;
import bi.view.causeeffectviews.CMCauseEffectStructureView;
import bi.view.dependencycombinationviews.CMCombinationViews;
import bi.view.dependencycombinationviews.CMGridDependencies;
import bi.view.dependencycombinationviews.CMPanelDependencies;
import bi.view.elementviews.CMGridElements;
import bi.view.errorviews.CMErrorGridView;
import bi.view.errorviews.CMErrorScrollView;
import bi.view.errorviews.CMErrorsPanelView;
import bi.view.icons.CMIcon;
import bi.view.lang.CMMessages;
import bi.view.projectviews.CMPanelProjectView;
import bi.view.report.CMReportRunner;
import bi.view.resultscomparationviews.CMDialogCreateEditResultComparation;
import bi.view.resultscomparationviews.CMResultsComparationPanelView;
import bi.view.stdcombinationviews.CMStdCombinationStructureView;
import bi.view.stdcombinationviews.CMStdCombinationViews;
import bi.view.testcaseviews.CMTestCaseStructureView;
import bi.view.testcaseviews.CMTestCaseViews;
import bi.view.testdataviews.CMCutCopyPasteStructures;
import bi.view.testdataviews.CMGridTDStructure;
import bi.view.testdataviews.CMIndexTDStructureUpdate;
import bi.view.testdataviews.CMPanelTDStructureView;
import bi.view.testdataviews.CMPanelTestDataSetView;
import bi.view.testdataviews.CMPanelTestDataView;
import bi.view.testdataviews.CMScrollPaneOutput;
import bi.view.testdataviews.CMVariables;
import bi.view.testobjectviews.CMCutCopyTestObject;
import bi.view.testobjectviews.CMPanelTestObjectView;
import bi.view.treeviews.CMTreeWorkspaceView;
import bi.view.treeviews.nodes.CMTestObjectNode;
import bi.view.undomanagementviews.undoviews.CMUndoMediator;
import bi.view.undomanagementviews.undoviews.CMUndoMediator.FocusInfo;
import bi.view.utils.CMBaseJMenuItem;
import bi.view.utils.CMToolBarButton;
import bi.view.workspaceviews.CMPanelWorkspaceView;

import com.jidesoft.action.CommandBar;
import com.jidesoft.action.CommandBarFactory;
import com.jidesoft.action.DefaultDockableBarDockableHolder;
import com.jidesoft.action.DefaultDockableBarHolder;
import com.jidesoft.action.DockableBar;
import com.jidesoft.action.DockableBarContext;
import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.plaf.office2003.Office2003Painter;
import com.jidesoft.status.LabelStatusBarItem;
import com.jidesoft.swing.JideMenu;
import com.jidesoft.utils.PortingUtils;
import com.jidesoft.utils.SystemInfo;


@SuppressWarnings("serial")
public class CMFrameView extends DefaultDockableBarDockableHolder
{

	private static final String PROFILE_NAME = "bi-CaseMaker";
	private CMApplication cmApplication;
    private CMStatusBar statusBar;
    private JSplitPane jSplitPaneTreeTabs;
    private CMJTabbedPaneTestCases contentTabbedPane;
    private CMPanelProjectView panelProjectView;
    private CMPanelTestObjectView panelTestObjectsView;
    private CMBusinessRulesPanelView businessRulesPanelView;
    private CMResultsComparationPanelView resultsComparationPanelView;
    private CMErrorsPanelView errorsPanelView;
    private JPanel panelElementsView ;
    private JPanel panelCauseEffectsView;
    private   CMPanelDependencies panelDependencyCombiView;
    private JPanel panelTestCasesView;
    private JPanel panelStdCombinationsView;
    private JScrollPane jScrollPaneElements;

    private CMGridElements elementsGrid;

    public CMCauseEffectStructureView causeEffectStructureView;
    private CMErrorScrollView errorScrollView;
    private CMTestCaseStructureView jSPlitPaneTestCaseStructureView;
    private CMStdCombinationStructureView    jSplitPaneStdCombinationStructureView;
    private JScrollPane scrollPaneWorkspaceView;
    private CMTreeWorkspaceView treeWorkspaceView;
    private CMPanelWorkspaceView panelWorkspaceView ;
    private  JPopupMenu jPopupMenuElements ;
    private JPopupMenu jPopupMenuCauseEffects;
    private JPopupMenu jPopupMenuDependency;
    private  JPopupMenu jPopupMenuCombinations;
    private  JPopupMenu jPopupMenuTestCases ;
    private JPopupMenu jPopupMenuStdCombinations;


	private JPopupMenu jPopupMenuErrors;
    private CMPanelTDStructureView panelTDStructureView;
    private CMVariables panelVariablesView;
    private CMGridTDStructure gridTDStructure;

    private CMPanelTestDataView panelTestDataView;
    private CMPanelTestDataSetView panelTestDataSetView;

    private CMScrollPaneOutput panelTestDataSetReportsView;

    private JPopupMenu jPopupMenuTDStructureFormula;
    private boolean isPanelTestDataSelected;
    private boolean isPanelResultComparationSelected;
    private JPopupMenu jPopupMenuAssignGlobalValue;
    private JPopupMenu jPopupMenuAssignFormattoLocalTypeData;
    private JPopupMenu jPopupMenuAssignGlobalTDStructure;
    private JPopupMenu jPopupMenuEditTDStructure;

    private  JPopupMenu jPopupMenuFile ;


    private JPopupMenu jPopupMenuTestDataSet;
    private JPopupMenu jPopupMenuTestData;
    private JPopupMenu jPopupMenuFields;

	private JPopupMenu jPopupMenuFormatStructure;

	private JPopupMenu jPopupMenuTestDataSetReports;
    public JPopupMenu jPopupMenuResultComparison= new JPopupMenu();
    private CMKeyEventIntercepter keyEventIntercepter;//fcastro_13092004
    private JPanel gridElementPanel ;
    private JViewport gridElementViewport;



   public CMFrameView(CMApplication p_CMApplication)
    {
        this.cmApplication = p_CMApplication;
      
    	getDockableBarManager().setUsePref(false);
    	getDockableBarManager().setLayoutDirectory(new File("").getAbsolutePath());
    	getDockableBarManager().setProfileKey(PROFILE_NAME);
    	initGUI();
        //pack();
       getDockableBarManager().loadLayoutData();
       //getDockableBarManager().resetToDefault();
       System.gc();
    }

    public CMUndoMediator getM_CMUndoMediator() {
      return CMUndoMediator.getInstance();
    }


    public CMCauseEffectStructureView getCauseEffectStructureView() {
    	if (causeEffectStructureView == null){
    		causeEffectStructureView = new CMCauseEffectStructureView(this);
    	}
      return causeEffectStructureView;
    }

    public CMErrorScrollView getCMErrorScrollView() {
      return getErrorScrollView();
    }

    private void initGUI()
    {
		setVisible(false);
       setIconImage(CMIcon.CASEMAKER_LOGO.getImageIcon().getImage());
       
       for (CommandBar commandBar:CMMenuPerspective.DEFAULT.getCommandBars())
    	   getDockableBarManager().addDockableBar(commandBar);
       getDockableBarManager().getMainContainer().setLayout(new BorderLayout());
       getDockableBarManager().getMainContainer().add((Component) getJSplitPaneTreeTabs(), BorderLayout.CENTER);
       //getDockableBarManager().setShowContextMenu(false);
        setTitle(" "+model.BusinessRules.APPLICATIONNAME+" "+model.BusinessRules.APPLICATIONVERSION); //$NON-NLS-1$
        getContentPane().add(getStatusBar(), BorderLayout.AFTER_LAST_LINE); //$NON-NLS-1$
        setIconImage(CMIcon.CASEMAKER.getImageIcon().getImage());


        addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent evt)
            {

                exitForm(evt);
            }

        });
        setStateOneElements();
        setStateOneDependencies();

			jPopupMenuResultComparison.add(new CMBaseJMenuItem(CMAction.RESULT_COMPARISON_CREATE.getAction()));
			jPopupMenuResultComparison.add(new CMBaseJMenuItem(CMAction.RESULT_COMPARISON_EDIT.getAction()));
            jPopupMenuResultComparison.add(new CMBaseJMenuItem(CMAction.RESULT_COMPARISON_REPORT.getAction()));


        panelTDStructureView = new CMPanelTDStructureView(this);
      	gridTDStructure =  new CMGridTDStructure(this);
      	panelVariablesView= new CMVariables(this);
      	panelTestDataView= new CMPanelTestDataView(this);

        panelTestDataSetReportsView= new CMScrollPaneOutput(this);

      	panelTestDataSetView = new CMPanelTestDataSetView(this);
        this.panelTDStructureView.ScrollPaneTDStructureView.setViewportView(gridTDStructure);
        this.panelTDStructureView.ScrollPaneTDStructureView.setBorder(null);

        CMAction.TESTDATA_ASSIGN_GLOBAL_REFERENCE.setEnabled(isIsPanelTestDataSelected());
        CMAction.TESTDATA_CANCEL_GLOBAL_REFERENCE.setEnabled(isIsPanelTestDataSelected());
		 this.addMouseListener(new MouseAdapter(){public void mouseClicked(MouseEvent e){eventMouseClicked(e);}});
         KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(getKeyEventIntercepter());
    stateDeleteColumnOptionsinStructure(false);
		showBusinessRulesEditMenus(false);

   }


public void eventMouseClicked(EventObject e){

	/////////////element editing////////
	if (getElementsGrid()!=null)
	if(getElementsGrid().isEditing()){
		getElementsGrid().stopCellEditing();
    }
//hcanedo_21_09_2004_begin
	if(gridTDStructure!=null && gridTDStructure.isEditing()){
		gridTDStructure.stopCellEditing();
    }
    if(panelTestDataView!=null && panelTestDataView.getM_CMTDStructure()!=null && panelTestDataView.getM_CMTDStructure().isEditing()){
        panelTestDataView.getM_CMTDStructure().stopCellEditing();
    }
//hcanedo_21_09_2004_end

    //////////test object editing///////////
    if(e!=null){
   		/////if it comes from the tree, it has been taken care of////
        if(!(e.getSource().getClass().toString().equals(this.getTreeWorkspaceView().getClass().toString()))){
            if(getPanelTestObjectView().getTestObject()!=null && getPanelTestObjectView().isShowing()){//fcastro_19082004
            	getPanelTestObjectView().newNameEntered(e);
            }//fcastro_19082004

        }
    }
    else{
 		if(getPanelTestObjectView().getTestObject()!=null && getPanelTestObjectView().isShowing()){//fcastro_19082004
 			getPanelTestObjectView().newNameEntered(null);
        }//fcastro_19082004
    }
    ///////////////////////////////////////

}
//integration_fcastro_17082004_end

    public void exitForm(WindowEvent evt)
    {
		if(cmApplication.getSessionManager().getM_Save_UnsaveVariable()>0)
        {
      		int confirmation = JOptionPane.showConfirmDialog(this,CMMessages.getString("QUESTION_EXIT_CASEMAKER"),CMMessages.getString("LABEL_EXIT_CASEMAKER"),JOptionPane.YES_NO_CANCEL_OPTION); //$NON-NLS-1$ //$NON-NLS-2$
      		if( confirmation == JOptionPane.YES_OPTION) {
      			getTreeWorkspaceView().save2();
        		if (CMReportRunner.currentReportProcess!=null)
        			CMReportRunner.currentReportProcess.destroy();
        		getDockableBarManager().saveLayoutData();
        		System.exit(0);
      		}
      		else if( confirmation == JOptionPane.NO_OPTION) {
      			if (CMReportRunner.currentReportProcess!=null)
        			CMReportRunner.currentReportProcess.destroy();

      			getDockableBarManager().saveLayoutData();
        		System.exit(0);
      		}
      		else if( confirmation == JOptionPane.CANCEL_OPTION)
      		{
        		setVisible(true);
      		}
        }
        else{
            int confirmation = JOptionPane.showConfirmDialog(this,CMMessages.getString("QUESTION_EXIT_CASEMAKER_YES_NO"),CMMessages.getString("LABEL_EXIT_CASEMAKER"),JOptionPane.YES_NO_OPTION); //$NON-NLS-1$ //$NON-NLS-2$
      		if( confirmation == JOptionPane.YES_OPTION) {
      			if (CMReportRunner.currentReportProcess!=null)
        			CMReportRunner.currentReportProcess.destroy();
      			getDockableBarManager().saveLayoutData();
        		 System.exit(0);
      		}
      		else{
				setVisible(true);
      		}
        }
    }

    public void updateMenuPerspective(ChangeEvent e) {
     int index = getContentTabbedPane().getSelectedIndex();
     CMMenuPerspective perspective = CMMenuPerspective.DEFAULT;
     eventMouseClicked(null);
	 setIsPanelTestDataSelected(false);
     setIsPanelResultComparationSelected(false);
     CMUndoMediator.getInstance().getUndoManager().setLimit(50);
     showBusinessRulesEditMenus(false);
     getStatusBar().setVisibleBusinessRulesStatusBar(false);
     if (index > -1) {
        this.setWaitCursor(true);
           Component selectedComponent = getContentTabbedPane().getComponentAt(index);
            if( selectedComponent == this.businessRulesPanelView) {
                  showBusinessRulesEditMenus(true);
                  perspective = CMMenuPerspective.BUSINESS_RULES;
                  getStatusBar().setVisibleBusinessRulesStatusBar(true);
                  CMUndoMediator.getInstance().getUndoManager().setLimit(1000);
           }
           else if( selectedComponent == this.panelTDStructureView) {
            	CMIndexTDStructureUpdate.getInstance().initIndex();
                panelTDStructureView.update();
                  updateGridTDStructure();
                  perspective = CMMenuPerspective.TDSTRUCTURE;
           }
            else if( selectedComponent == this.panelTestDataView) {
               CMIndexTDStructureUpdate.getInstance().initIndex();
               setIsPanelTestDataSelected(true);
               perspective = CMMenuPerspective.TEST_DATA;
            }
           else if( selectedComponent == this.panelTestDataSetView) {
               CMIndexTDStructureUpdate.getInstance().initIndex();
                  panelTestDataSetView.update();
                  perspective = CMMenuPerspective.TEST_DATASET;

           }
        else if( selectedComponent == this.panelTestDataSetReportsView) {
              CMIndexTDStructureUpdate.getInstance().initIndex();
           	  panelTestDataSetReportsView.update();
           	  perspective = CMMenuPerspective.TEST_DATASET_REPORTS;
         }
		    else if( selectedComponent == this.panelVariablesView) {
	           	perspective = CMMenuPerspective.VARIABLES;
           }
           else if( selectedComponent == this.resultsComparationPanelView) {
            	CMIndexTDStructureUpdate.getInstance().initIndex();
				setIsPanelResultComparationSelected(true);
				perspective = CMMenuPerspective.RESULT_COMPARISON;
          }
           else if( selectedComponent == this.errorsPanelView){
           	perspective = CMMenuPerspective.ERROR;
           }
           else if( selectedComponent == this.panelElementsView) {
        	   perspective = CMMenuPerspective.ELEMENT_EQUIVALENCECLASS;
           }
           else if( selectedComponent == this.panelCauseEffectsView) {
           	perspective = CMMenuPerspective.EFFECTS;
            statesMenuPaste(CMClipBoard.getInstance().getEffect()!=null);
                  try{
                  	int cantEffect=getCauseEffectStructureView().getM_Structure().getEffects().size();
                  	statesMenusCutCopy(cantEffect>0);
                  }
                  catch(Exception ex){
                  	statesMenusCutCopy(false);
                  }
           }
           else if( selectedComponent == this.panelDependencyCombiView) {
        	   perspective = CMMenuPerspective.DEPENDENCY_COMBINATION;
           }
           else if( selectedComponent == this.panelTestCasesView) {
        	   perspective = CMMenuPerspective.TEST_CASES;
           }
           else if( selectedComponent == this.panelStdCombinationsView) {
        	   perspective = CMMenuPerspective.STD_COMBINATION;
           }
            //menu
            DockableBar mainMenu = getDockableBarManager().getDockableBar("MainMenu");

            String[] standartMenus = new String[] {"FILE","EDIT","HELP"};

            for (int i =mainMenu.getMenuCount()-1; i >=0 ;i-- ){
            	JMenu menu = mainMenu.getMenu(i);
            	if (!Arrays.asList(standartMenus).contains(menu.getName()))
            			mainMenu.remove(menu);
            }
            for (JMenu menu : perspective.getMenus())
            	mainMenu.add(menu, mainMenu.getMenuCount()-1);
            //toolbars
           	for (String name : (List<String>)getDockableBarManager().getAllDockableBarNames()){
           		Logger.getLogger(" actual :"+name);
           		if (!CMMenuPerspective.DEFAULT.getCommandBarNames().contains(name)){
           			getDockableBarManager().hideDockableBar(name);
           			getDockableBarManager().removeDockableBar(name);
           		}
           			
           	}
           	for (CommandBar commandBar : perspective.getCommandBars()){
           		Logger.getLogger(this.getClass()).debug(commandBar.getTitle());
           		if (getDockableBarManager().getDockableBar(commandBar.getTitle())==null)
           			getDockableBarManager().addDockableBar(commandBar);
           		else
           			getDockableBarManager().showDockableBar(commandBar.getTitle());
           	}
           	
           	
            	this.validate();
                  this.repaint();

                  	this.setWaitCursor(false);//fcastro_20092004

      }

    }



  public void addWorkspaceTabs(CMPanelWorkspaceView p_PanelWorkspace){
	  getContentTabbedPane().setVisible(true);
        getJSplitPaneTreeTabs().setDividerLocation(300);

        getContentTabbedPane().removeAll();
        //panelWorkspaceView.setM_Workspace(p_Workspace);
        panelWorkspaceView = p_PanelWorkspace;
        getContentTabbedPane().addTab(CMMessages.getString("LABEL_WORKSPACE"), null,p_PanelWorkspace, null); //$NON-NLS-1$
        getContentTabbedPane().setToolTipTextAt(0, CMMessages.getString("TAB_TOOLTIP_WORKSPACE_MANAGEMENT")); //$NON-NLS-1$
        getContentTabbedPane().setIconAt(0, CMIcon.WORKSPACE.getImageIcon());
  }

  public void addEmptyTabs() {
	  getContentTabbedPane().removeAll();
	  getContentTabbedPane().setVisible(false);
  }

  public void addProjectTab(CMPanelProjectView panelProject){
	  panelProjectView = panelProject;
	  getContentTabbedPane().setVisible(true);
      getJSplitPaneTreeTabs().setDividerLocation(300);
      getContentTabbedPane().removeAll();
      getContentTabbedPane().addTab(CMMessages.getString("LABEL_PROJECT"),null, getPanelProjectView(), null); //$NON-NLS-1$
      getContentTabbedPane().setToolTipTextAt(0, CMMessages.getString("TAB_TOOLTIP_PROJECT_MANAGEMENT")); //$NON-NLS-1$
      getContentTabbedPane().setIconAt(0, CMIcon.PROJECT.getImageIcon());
	if(CMCutCopyTestObject.getInstance().existTestObjectToPaste())
      statesMenuPaste(true);
  else
      statesMenuPaste(false);
}

  public void initializeTheUndoManager() {
        //Reset the UndoManager
        CMUndoMediator.getInstance().getUndoManager().discardAllEdits();
        CMUndoMediator.getInstance().setM_NumberOfRedoableEditObjects(0);
        CMUndoMediator.getInstance().setM_NumberOfUndoableEditObjects(0);
        CMAction.UNDO.setEnabled(false);
        CMAction.REDO.setEnabled(false);
  }

  public void addTestObjectTabs(CMPanelTestObjectView panelTestObject,TestObject p_TestObject, TestObjectReference p_TestObjectReference, Session2 p_Session, ProjectReference p_ProjectReference, TestObjectReference p_LocalTestObjectReference){
	    panelTestObjectsView = panelTestObject;
        if( p_TestObject == null) {
          return;
        }
        getContentTabbedPane().setVisible(true);
        getJSplitPaneTreeTabs().setDividerLocation(300);
        getContentTabbedPane().removeAll();
        initializeTheUndoManager();
        Project2 project=getTreeWorkspaceView().getCurrentProject();
        //GARY_06062004_BEGIN_30
        getPanelTestObjectView().setDefaultSeparator(p_TestObject.isDefaultSeparator());//Ccastedo 21-04-06
        String ToolVendor=p_TestObject.getToolVendor();
        if (ToolVendor ==null)
        		return;

    	ToolVendorManager toolvendormanager = ToolVendorManager.INSTANCE;

    	boolean toolVendorSelected   = toolvendormanager.existToolVendor(ToolVendor);

    	if (!toolVendorSelected){
    		JOptionPane.showMessageDialog(this,CMMessages.getString("LABEL_TOOLVENDOR_FILE_NO_EXIST"),CMMessages.getString("TITLE_DEFAULT_APPLICATION_DELETE"),JOptionPane.ERROR_MESSAGE);
    		p_TestObject.setToolVendor("compuware");
    		p_TestObject.setToolVendorTechnology("DotNET");
    	}
    	getPanelTestObjectView().setTestObject(project,p_TestObject, p_TestObjectReference, p_Session, p_ProjectReference, p_LocalTestObjectReference);
    	getPanelTestObjectView().setToolVendor(p_TestObject);

    		getContentTabbedPane().addTab(CMMessages.getString("TAB_LABEL_TEST_OBJECT"), null, getPanelTestObjectView(), null ); //$NON-NLS-1$
    		getContentTabbedPane().setToolTipTextAt(0, CMMessages.getString("TOOLTIP_TEST_OBJECT_MANAGEMENT")); //$NON-NLS-1$
    		getContentTabbedPane().setIconAt(0, CMIcon.TESTOBJECT.getImageIcon());

            setAccessRights(p_TestObjectReference, p_Session, getPanelTestObjectView());
    		statesMenusCutCopy(true);
  }
/**
* Freddy's code
*
*/
  public void addTestObjectBusinessRulesTabs(TestObject p_TestObject, TestObjectReference p_TestObjectReference, Session2 p_Session, ProjectReference p_ProjectReference){
  	boolean fileError;
		getContentTabbedPane().setVisible(true);
        getJSplitPaneTreeTabs().setDividerLocation(300);

        getContentTabbedPane().removeAll();
      fileError=getBusinessRulesPanelView().setM_TestObject(p_TestObject, p_TestObjectReference, p_Session,this, p_ProjectReference);
      getPanelTestObjectView().setTestObject(p_TestObject);
           if(fileError){
               	JOptionPane.showMessageDialog(this,CMMessages.getString("LABEL_BRFILE_NOT_FOUND"),CMMessages.getString("TITLE_BRFILE_NOT_FOUND"),
                JOptionPane.ERROR_MESSAGE);
            }
           getContentTabbedPane().addTab(CMMessages.getString("TAB_LABEL_BUSINESS_RULES"), null, getBusinessRulesPanelView() , null ); //$NON-NLS-1$
           getContentTabbedPane().setToolTipTextAt(0, CMMessages.getString("TOOLTIP_BUSINESS_RULES_MANAGEMENT")); //$NON-NLS-1$
           getContentTabbedPane().setIconAt(0, CMIcon.BUSINESS_RULE.getImageIcon());
        setAccessRights(p_TestObjectReference, p_Session, getPanelTestObjectView());
        //CMAction.DELETE.setEnabled(false);
        initializeTheUndoManager();
        getBusinessRulesPanelView().enablesViews();
  }
  /**
   * ends Freddy's code
   *
   */
  public CMJTabbedPaneTestCases getTabbedPaneTestCasesElementsViews(){
	  return getContentTabbedPane();
  }
  public JTabbedPane getTabbedPane(){
	  return getContentTabbedPane();
  }
  public void addTestObjectTestCasesTabs(TestObject p_TestObject, TestObjectReference p_TestObjectReference, Session2 p_Session){
	  getContentTabbedPane().setVisible(true);
        getJSplitPaneTreeTabs().setDividerLocation(300);

        getContentTabbedPane().removeAll();
        initializeTheUndoManager();
        Structure s = p_TestObject.getStructure();

        getPanelTestObjectView().setTestObject(p_TestObject);

        getElementsGrid().setStructure(s);

        getCauseEffectStructureView().setM_Structure(s);
        getPanelDependencyCombiView().setM_Structure(s);
        getJSPlitPaneTestCaseStructureView().setM_Structure(s);
        getJSplitPaneStdCombinationStructureView().setM_Structure(s);

        getContentTabbedPane().addTab(CMMessages.getString("TAB_LABEL_ELEMENTS"), null,getPanelElementsView(), null ); //$NON-NLS-1$
        getContentTabbedPane().addTab(CMMessages.getString("TAB_LABEL_CAUSE_EFFECTS"), null,  getPanelCauseEffectsView(), null); //$NON-NLS-1$
        getContentTabbedPane().addTab(CMMessages.getString("TAB_LABEL_DEPENDENCIES_COMBINATIONS"), null, getPanelDependencyCombiView(), null); //$NON-NLS-1$
        getContentTabbedPane().addTab(CMMessages.getString("TAB_LABEL_TEST_CASES"), null, getPanelTestCasesView(), null); //$NON-NLS-1$
        getContentTabbedPane().addTab(CMMessages.getString("TAB_LABEL_STD_COMBINATIONS"), null, getPanelStdCombinationsView(), null ); //$NON-NLS-1$


        getContentTabbedPane().setToolTipTextAt(0, CMMessages.getString("TOOLTIP_ELEMENTS_MANAGEMENT")); //$NON-NLS-1$
        getContentTabbedPane().setToolTipTextAt(1, CMMessages.getString("TOOLTIP_CAUSE_EFFECTS_MANAGEMENT")); //$NON-NLS-1$
        getContentTabbedPane().setToolTipTextAt(2, CMMessages.getString("TOOLTIP_DEPENDENCIES_COMBINATIONS_MANAGEMENT")); //$NON-NLS-1$
        getContentTabbedPane().setToolTipTextAt(3, CMMessages.getString("TOOLTIP_TEST_CASES_MANAGEMENT")); //$NON-NLS-1$
        getContentTabbedPane().setToolTipTextAt(4, CMMessages.getString("TOOLTIP_STDCOMBINATIONS_MANAGEMENT")); //$NON-NLS-1$

        getContentTabbedPane().setIconAt(0, CMIcon.ELEMENT.getImageIcon());
        getContentTabbedPane().setIconAt(1, CMIcon.EFFECT.getImageIcon());
        getContentTabbedPane().setIconAt(2, CMIcon.EQUIVALENCECLASS_IN_COMBINATION.getImageIcon());
        getContentTabbedPane().setIconAt(3, CMIcon.TESTCASE.getImageIcon());
        getContentTabbedPane().setIconAt(4, CMIcon.STDCOMBINATION.getImageIcon());

        setAccessRights(p_TestObjectReference, p_Session, getPanelTestObjectView());
    	//CMAction.DELETE.setEnabled(false);
        //hmendez_06102005_begin
        this.getTreeWorkspaceView().requestFocus();
        //hmendez_06102005_end
  }

  public void addTestObjectTestDataTabs(TestObject p_TestObject, TestObjectReference p_TestObjectReference, Session2 p_Session, TDStructure p_TDStructure){
	  getContentTabbedPane().setVisible(true);
        getJSplitPaneTreeTabs().setDividerLocation(300);

        getContentTabbedPane().removeAll();
        initializeTheUndoManager();
        addTDStructureTabs(p_TDStructure, p_TestObjectReference);
        getPanelTestObjectView().setTestObject(p_TestObject);
         getContentTabbedPane().setToolTipTextAt(0, CMMessages.getString("TOOLTIP_TEST_DATA_MANAGEMENT")); //$NON-NLS-1$
         getContentTabbedPane().setIconAt(0, CMIcon.TESTDATA_STRUCTURE.getImageIcon());
        setAccessRights(p_TestObjectReference, p_Session, getPanelTestObjectView());
        setStatesMenusTestData(false);//modificaciones harold

        this.getTreeWorkspaceView().requestFocus();


  }

  public void addTestObjectResultsComparationTabs(TestObject p_TestObject, TestObjectReference p_TestObjectReference, Session2 p_Session, TDStructure p_TDStructure){
	  getContentTabbedPane().setVisible(true);
        getJSplitPaneTreeTabs().setDividerLocation(300);
        getContentTabbedPane().removeAll();
        initializeTheUndoManager();
        getPanelTestObjectView().setTestObject(p_TestObject);
        getPanelResultComparation().setM_TestObject(p_TestObject, p_TestObjectReference, p_Session);
        getPanelResultComparation().setM_TDStructure(p_TDStructure);// nuevo harold Canedo ResultComparation
        getPanelResultComparation().update();
        getContentTabbedPane().addTab(CMMessages.getString("TAB_LABEL_RESULTS_COMPARATION"), null, getPanelResultComparation(), null ); //$NON-NLS-1$
        getContentTabbedPane().setToolTipTextAt(0, CMMessages.getString("TOOLTIP_RESULTS_COMPARATION_MANAGEMENT")); //$NON-NLS-1$
        getContentTabbedPane().setIconAt(0, CMIcon.RESULT.getImageIcon());
        setAccessRights(p_TestObjectReference, p_Session, getPanelTestObjectView());
    	//CMAction.DELETE.setEnabled(false);
  }

  @SuppressWarnings("unchecked")
public void addTestObjectTestManagementTabs(TestObject p_TestObject, TestObjectReference p_TestObjectReference, Session2 p_Session){
	  getContentTabbedPane().setVisible(true);
        getJSplitPaneTreeTabs().setDividerLocation(300);
        getContentTabbedPane().removeAll();
        initializeTheUndoManager();
        Structure s = p_TestObject.getStructure();
        getErrorScrollView().setM_Structure(s);
        getPanelTestObjectView().setTestObject(p_TestObject);
        getErrorsPanelView().setM_TestObject(p_TestObject, p_TestObjectReference, p_Session);
        getContentTabbedPane().addTab(CMMessages.getString("TAB_LABEL_ERRORS"), null, getErrorsPanelView(), null ); //$NON-NLS-1$
        getContentTabbedPane().setToolTipTextAt(0, CMMessages.getString("TOOLTIP_ERROR_MANAGEMENT")); //$NON-NLS-1$
        getContentTabbedPane().setIconAt(0, CMIcon.ERROR_MANAGMENT.getImageIcon());
        //GRUEDA_21042004_BEGIN_2
        setAccessRights(p_TestObjectReference, p_Session, getPanelTestObjectView());
		//GRUEDA_21042004_END_2: 1 line of code deleted and this one added.
    	//CMAction.DELETE.setEnabled(false);
  }



  public void setAccessRights(TestObjectReference p_TestObjectReference, Session2 p_Session, CMPanelTestObjectView p_TestObjectView) {
        //grueda06112004_begin
        TestObject actualTestObject= p_TestObjectView.getTestObject();
        if( actualTestObject !=null &&(/*p_TestObjectReference*/actualTestObject.getAccessState().equals(BusinessRules.ACCESS_STATE_CHECKED_OUT)
            || /*p_TestObjectReference*/actualTestObject.getAccessState().equals(BusinessRules.ACCESS_STATE_CHECKED_OUT_LOCAL) )){
          if( !p_Session.getM_User().equalsIgnoreCase(/*p_TestObjectReference*/actualTestObject.getUser()) ) {
            enableViews(false, p_TestObjectView, p_TestObjectReference, p_Session);
			setStateTestObjectReadOnly();
          }
          else {
            enableViews(true, p_TestObjectView, p_TestObjectReference, p_Session);
            setStateOneTestObjects();
            p_TestObjectView.setViewsEnabled(true, p_TestObjectReference, p_Session);
          }
        }
        else {
          enableViews(false, p_TestObjectView, p_TestObjectReference, p_Session);
		  setStateTestObjectReadOnly();

		  p_TestObjectView.setAccessViewsEnabled(true);
        }
        //grueda06112004_end
  }

  public void enableViews(boolean p_Enable, CMPanelTestObjectView p_testObjectView, TestObjectReference p_TestObjectReference, Session2 p_Session){
	  getContentTabbedPane().setEnabled(p_Enable);
       p_testObjectView.setViewsEnabled(p_Enable, p_TestObjectReference, p_Session);
       getElementsGrid().setEnabled(p_Enable);
       gridTDStructure.setEnabled(p_Enable);//harold modificacion
        statesAllMenusTDStructure(p_Enable);//haroldModificaciones
        getPanelTDStructureView().setAccessRights(p_Enable);
		stateAllMenusResultComparison(p_Enable);

		getErrorsPanelView().setEnabled(p_Enable);
		getErrorScrollView().setEnabled(p_Enable);
		getErrorScrollView().getM_CMErrorGridView().setEnabled(p_Enable);//fcastro_20092004
       allowCommandosUse(p_Enable);

  }

  public CMApplication getCmApplication(){
          return cmApplication;
      }


public void setStateWorkspaceRootSelected() {
	CMAction.PASTE.setEnabled(false);
   // CMAction.CREATE.setEnabled(true);
    CMAction.ADDOPEN.setEnabled(false);
	 CMAction.IMPORT_PROJECT.setEnabled(false);
 	//CMAction.DELETE.setEnabled(false);
 	CMAction.REORDER_TESTOBJECTS.setEnabled(false);
}

public void setStateWorkspaceSelected() {
    //CMAction.CREATE.setEnabled(true);
    CMAction.ADDOPEN.setEnabled(true);
    CMAction.IMPORT_PROJECT.setEnabled(true);
    CMAction.PASTE.setEnabled(false);
	//CMAction.DELETE.setEnabled(true);
	CMAction.REORDER_TESTOBJECTS.setEnabled(false);

}

public void setStateProjectSelected() {
    //grueda07112004_begin

	CMAction.IMPORT_PROJECT.setEnabled(false);
     CMAction.PASTE.setEnabled(false);

    Project2 currentProject = this.getTreeWorkspaceView().getCurrentProject();
    ProjectReference currentReference=this.getTreeWorkspaceView().getCurrentProjectReference();
    Session2 session=this.getTreeWorkspaceView().getM_Session2();
	if((currentProject!=null) && (currentProject.getAccessState().equals(BusinessRules.ACCESS_STATE_CHECKED_OUT)) && (currentProject.getUser().equalsIgnoreCase(System.getProperty("user.name")))){
		CMAction.REORDER_TESTOBJECTS.setEnabled(true);
    }
    else{
    	CMAction.REORDER_TESTOBJECTS.setEnabled(false);
    }
    if( !this.getCmApplication().getSessionManager().getM_WorkspaceManager().areCheckedOutLocalAndCheckedOutTestObjectsUnderCurrentProject(currentProject, currentReference, session) && currentProject.getAccessState().equals(BusinessRules.ACCESS_STATE_CHECKED_IN) ) {

    	CMAction.ADDOPEN.setEnabled(true);
    	//CMAction.DELETE.setEnabled(true);
    	CMAction.SAVE.setEnabled(true);
    }
    else{
        ProjectReference currentProjectReference = getTreeWorkspaceView().getCurrentProjectReference();
        if( getCmApplication().getSessionManager().getM_WorkspaceManager().exists(currentProjectReference.getFilePath())) {
        	//CMAction.CREATE.setEnabled(true);
        	CMAction.ADDOPEN.setEnabled(true);
        }
        else{
        	//CMAction.CREATE.setEnabled(false);
        	CMAction.ADDOPEN.setEnabled(false);
        }
    //	CMAction.DELETE.setEnabled(false);
    	CMAction.SAVE.setEnabled(false);
    }
	//grueda07112004_end
}


public void setStateTestObjectSelected() {
    this.setStateOneElements();
    this.setStateOneDependencies();
    CMAction.PASTE.setEnabled(false);
    //CMAction.CREATE.setEnabled(false);
    CMAction.ADDOPEN.setEnabled(false);
    CMAction.IMPORT_PROJECT.setEnabled(false);

    Project2 currentProject = this.getTreeWorkspaceView().getCurrentProject();

    if( currentProject!=null && currentProject.getAccessState().equals(BusinessRules.ACCESS_STATE_CHECKED_OUT) && currentProject.getUser().equalsIgnoreCase(System.getProperty("user.name"))){//!this.getCmApplication().getSessionManager().getM_WorkspaceManager().areCheckedOutLocalAndCheckedInTestObjectsUnderCurrentProject(currentProject, currentReference,session) ) {
       CMAction.REORDER_TESTOBJECTS.setEnabled(true);
    }
    else{
    	CMAction.REORDER_TESTOBJECTS.setEnabled(false);
    }
    //grueda24112004_end
}

public void setStateTestObjectBusinessRulesSelected(){

}

public void setStateTestObjectTestCasesSelected() {
    this.setStateOneElements();
    this.setStateOneDependencies();
    CMAction.PASTE.setEnabled(false);
    //CMAction.CREATE.setEnabled(false);
    CMAction.ADDOPEN.setEnabled(false);
    CMAction.IMPORT_PROJECT.setEnabled(false);

}

public void setStateTestObjectTestDataSelected() {
    statesMenusTDStructureCreate(true);
        statesMenusTestDataCreate(true);
        statesMenusTestDataSetCreate(true);
        statesMenusVariablesCreate(true);

}



public void setStateTestObjectTestManagementSelected() {
    this.setStateOneElements();
    this.setStateOneDependencies();
    CMAction.PASTE.setEnabled(false);
    CMAction.ADDOPEN.setEnabled(false);
    CMAction.IMPORT_PROJECT.setEnabled(false);


}

public void setStateTestObjectTestManagementErrorDeleted(){
    if( getCMErrorScrollView().getM_CMErrorGridView().getFirstSelectedRow() >= 0){
      setErrorCommandos(true);
    }
    else{
      setErrorCommandos(false);
    }
}

public void setErrorCommandos(boolean p_State) {
      CMAction.ERROR_EDIT.setEnabled(p_State);
      CMAction.ERROR_DELETE.setEnabled(p_State);
      CMAction.ERROR_ASSIGN_TESTCASES.setEnabled(p_State);
}


public void setStateTestObjectTestManagementErrorAdded(){
    if( getCMErrorScrollView().getM_CMErrorGridView().getFirstSelectedRow() >= 0){
      setErrorCommandos(true);
    }
    else{
      setErrorCommandos(false);
    }
}
    public void allowCommandosUse(boolean p_Enable) {
      CMAction.ERROR_CREATE.setEnabled(p_Enable);
      CMAction.ERROR_EDIT.setEnabled(p_Enable);
      CMAction.ERROR_REPORT_LIST.setEnabled(p_Enable);
      CMAction.ERROR_DELETE.setEnabled(p_Enable);
      CMAction.ERROR_ASSIGN_TESTCASES.setEnabled(p_Enable);
      if( p_Enable && getCMErrorScrollView().getM_CMErrorGridView().getFirstSelectedRow() >= 0){
		  setErrorCommandos(true);
		}
	  else{
		  setErrorCommandos(false);
	  }
    }



public void setStateOneTestObjects() {

       TestObject currentTestObject= this.getTreeWorkspaceView().getCurrentTestObject();
    Project2 currentProject = this.getTreeWorkspaceView().getCurrentProject();
    if( currentTestObject == null || currentProject == null) {
      return;
    }
    CMAction.SAVE.setEnabled(true);
}

  public void setStateTestObjectLocalCheckedOut() {

        CMAction.SAVE.setEnabled(true);
  }
//grueda30122004_end

public void setStateTestObjectReadOnly() {
    setStateElementContentSelected();
    //grueda07112004_begin
       TestObject currentTestObject= this.getTreeWorkspaceView().getCurrentTestObject();
    Project2 currentProject = this.getTreeWorkspaceView().getCurrentProject();
    Logger.getLogger(this.getClass()).debug("Test Object "+currentTestObject+"Project "+currentProject);
    CMAction.SAVE.setEnabled(false);

}

 public void setStateElementHasBeenCopied(){
	 CMAction.PASTE.setEnabled(true);
	 }

 public void setStateElementHasBeenCut(){
	 CMAction.PASTE.setEnabled(true);
 }
 public void setStateNoElementCopiedNorCut(){
	 CMAction.PASTE.setEnabled(false);
 }

 public void setStateOneElements() {

    CMAction.ELEMENT_CREATE.setEnabled(true);
    CMAction.ELEMENT_DELETE.setEnabled(false);
    CMAction.ELEMENT_INSERT.setEnabled(false);
    CMAction.EQUIVALENCECLASS_CREATE.setEnabled(false);
    CMAction.EQUIVALENCECLASS_EDIT.setEnabled(false);
    CMAction.EQUIVALENCECLASS_DELETE.setEnabled(false);
    CMAction.EQUIVALENCECLASS_CREATE_ASSIGN_EFFECT.setEnabled(false);
    CMAction.EQUIVALENCECLASS_EDIT_ASSIGNED_EFFECTS.setEnabled(false);
    CMAction.COPY.setEnabled(false);
    CMAction.CUT.setEnabled(false);
    CMAction.PASTE.setEnabled(false);
    if( CMUndoMediator.getInstance().getM_NumberOfUndoableEditObjects() > 0) {
    	CMAction.UNDO.setEnabled(true);
    }
    else{
    	CMAction.UNDO.setEnabled(false);
    }
    if( CMUndoMediator.getInstance().getM_NumberOfRedoableEditObjects() > 0) {
      CMAction.REDO.setEnabled(true);
    }
    else {
    	CMAction.REDO.setEnabled(false);
    }
  }

 public void setStateElementHeaderSelected() {

    CMAction.ELEMENT_CREATE.setEnabled(true);
    CMAction.ELEMENT_DELETE.setEnabled(true);
    CMAction.ELEMENT_INSERT.setEnabled(true);
    CMAction.EQUIVALENCECLASS_CREATE.setEnabled(true);
    CMAction.EQUIVALENCECLASS_EDIT.setEnabled(false);
    CMAction.EQUIVALENCECLASS_DELETE.setEnabled(false);
    CMAction.EQUIVALENCECLASS_CREATE_ASSIGN_EFFECT.setEnabled(false);
    CMAction.EQUIVALENCECLASS_EDIT_ASSIGNED_EFFECTS.setEnabled(false);
    CMAction.COPY.setEnabled(true);
    CMAction.CUT.setEnabled(true);

    if(  CMClipBoard.getInstance().getElement()!=null || CMClipBoard.getInstance().getEquivalenceClass()!=null) {
    	CMAction.PASTE.setEnabled(true);
    	}
    else {
    	CMAction.PASTE.setEnabled(false);
    }

  }

 public void setStateToolbarElements(boolean p_State) {

	    CMAction.ELEMENT_CREATE.setEnabled(p_State);
	    CMAction.ELEMENT_DELETE.setEnabled(p_State);
	    CMAction.ELEMENT_INSERT.setEnabled(p_State);
	    CMAction.EQUIVALENCECLASS_CREATE.setEnabled(p_State);
	    CMAction.EQUIVALENCECLASS_EDIT.setEnabled(p_State);
	    CMAction.EQUIVALENCECLASS_DELETE.setEnabled(p_State);
	    CMAction.EQUIVALENCECLASS_CREATE_ASSIGN_EFFECT.setEnabled(p_State);
	    CMAction.EQUIVALENCECLASS_EDIT_ASSIGNED_EFFECTS.setEnabled(p_State);


 }

 public void setStateElementContentSelected() {
	 CMAction.ELEMENT_CREATE.setEnabled(true);
     CMAction.ELEMENT_DELETE.setEnabled(true);
	 CMAction.ELEMENT_INSERT.setEnabled(false);
	 CMAction.EQUIVALENCECLASS_CREATE.setEnabled(true);
	 CMAction.EQUIVALENCECLASS_EDIT.setEnabled(false);
	 CMAction.EQUIVALENCECLASS_DELETE.setEnabled(false);
	 CMAction.EQUIVALENCECLASS_CREATE_ASSIGN_EFFECT.setEnabled(false);
	 CMAction.EQUIVALENCECLASS_EDIT_ASSIGNED_EFFECTS.setEnabled(false);
	 CMAction.CUT.setEnabled(false);
	 CMAction.COPY.setEnabled(false);
	 CMAction.PASTE.setEnabled(false);
 }

 public void setStateEquivalenceClassHeaderSelected() {

    CMAction.ELEMENT_CREATE.setEnabled(false);
    CMAction.ELEMENT_DELETE.setEnabled(false);
    CMAction.ELEMENT_INSERT.setEnabled(false);
    CMAction.EQUIVALENCECLASS_CREATE.setEnabled(true);
    CMAction.EQUIVALENCECLASS_EDIT.setEnabled(false);
    CMAction.EQUIVALENCECLASS_DELETE.setEnabled(false);
    CMAction.EQUIVALENCECLASS_CREATE_ASSIGN_EFFECT.setEnabled(false);
    CMAction.EQUIVALENCECLASS_EDIT_ASSIGNED_EFFECTS.setEnabled(false);
    CMAction.CUT.setEnabled(false);
    CMAction.COPY.setEnabled(false);
    if(CMClipBoard.getInstance().getEquivalenceClass()!=null)
    {
    	 CMAction.PASTE.setEnabled(true);
   	}
    else
    {
    	CMAction.PASTE.setEnabled(false);
   }

 }

 public void setStateEquivalenceClassContentSelected() {

    CMAction.ELEMENT_CREATE.setEnabled(false);
    CMAction.ELEMENT_DELETE.setEnabled(false);
    CMAction.ELEMENT_INSERT.setEnabled(false);
    CMAction.EQUIVALENCECLASS_CREATE.setEnabled(true);
    CMAction.EQUIVALENCECLASS_EDIT.setEnabled(true);
	 CMAction.EQUIVALENCECLASS_DELETE.setEnabled(true);
	 CMAction.EQUIVALENCECLASS_CREATE_ASSIGN_EFFECT.setEnabled(true);
	 CMAction.EQUIVALENCECLASS_EDIT_ASSIGNED_EFFECTS.setEnabled(false);
	 CMAction.CUT.setEnabled(true);
	 CMAction.COPY.setEnabled(true);
	 CMAction.PASTE.setEnabled(false);
	 }

  public void setStateOneDependencies() {

	  CMAction.COMBINATION_MERGE.setEnabled(true);
	  CMAction.COMBINATION_UNMERGE.setEnabled(true);
	  CMAction.CUT.setEnabled(false);
	  CMAction.COPY.setEnabled(false);
	  CMAction.PASTE.setEnabled(false);
  }

  public void setStateEquivalenceClassNotInCombinationSelected() {

	  CMAction.COMBINATION_MERGE.setEnabled(true);
	  CMAction.COMBINATION_UNMERGE.setEnabled(false);
	  CMAction.CUT.setEnabled(false);
	  CMAction.COPY.setEnabled(false);
	  CMAction.PASTE.setEnabled(true);
  }

  public void setStateEquivalenceClassInCombinationSelected() {

    CMAction.COMBINATION_MERGE.setEnabled(false);
    CMAction.COMBINATION_UNMERGE.setEnabled(true);
    CMAction.CUT.setEnabled(false);
    CMAction.COPY.setEnabled(false);
    CMAction.PASTE.setEnabled(true);
  }
  public void eventMenuSaveFile(ActionEvent e) {
	  getTreeWorkspaceView().save2();
  }
//hmendez_21122005_begin
  public boolean runDefaultBrowser(String p_File)
  {
	  boolean l_success;
      String operatingSystem = System.getProperty("os.name"); //$NON-NLS-1$
      operatingSystem = operatingSystem.toLowerCase();
      int index = operatingSystem.indexOf("windows"); //$NON-NLS-1$
      if (index >= 0) {
	    String l_appPath = "rundll32 url.dll,FileProtocolHandler ";
	    try {
	 		if (CMReportRunner.currentReportProcess!=null)
	 			CMReportRunner.currentReportProcess.destroy();
	 		CMReportRunner.currentReportProcess = Runtime.getRuntime().exec(l_appPath+p_File);
			l_success = true;
	    }
	    catch(IOException exception) {
	        exception.printStackTrace();
	        l_success = false;
	    }
      }
      else
      {
  	    l_success = false;
      }
      return l_success;
  }

  public void jButtonTestObjectGenerateTestDataActionPerformed(ActionEvent e) {
    eventMouseClicked(null);////integration_fcastro_17082004
  }

  public void jButtonTestObjectTestDataPropertiesActionPerformed(ActionEvent e) {
    eventMouseClicked(null);//integration_fcastro_17082004
    }

  public void jButtonTestObjectEditTestDataActionPerformed(ActionEvent e) {
    eventMouseClicked(null);//integration_fcastro_17082004
    }

  public CMTreeWorkspaceView getTreeWorkspaceView() {
	  if (this.treeWorkspaceView== null)
	  {
		  treeWorkspaceView = new CMTreeWorkspaceView(this);
		  treeWorkspaceView.addTreeSelectionListener((TreeSelectionListener) CMAction.CREATE.getAction());
		  treeWorkspaceView.addTreeSelectionListener((TreeSelectionListener) CMAction.ADDOPEN.getAction());
	  }
    return this.treeWorkspaceView;
  }

  public  CMPanelProjectView getPanelProjectView(){
	  if (panelProjectView == null){
		  panelProjectView = new CMPanelProjectView();
	  }

    return this.panelProjectView;
  }

  public CMPanelWorkspaceView getPanelWorkspaceView(){
	  if (panelWorkspaceView == null)
		  panelWorkspaceView = new CMPanelWorkspaceView();
    return this.panelWorkspaceView;
  }

  public CMPanelTestObjectView getPanelTestObjectView() {

	  if (panelTestObjectsView == null)
		  panelTestObjectsView = new CMPanelTestObjectView();
    return this.panelTestObjectsView;
  }


  public void viewFile(String p_Viewer, String p_FileName){
    StringBuffer filePath = new StringBuffer();
    filePath.append(this.cmApplication.getSessionManager().getM_InstallationDirectory()); //$NON-NLS-1$
    filePath.append(BusinessRules.URL_SEPARATOR); //$NON-NLS-1$
    filePath.append(p_FileName);

    File app = new File (filePath.toString());
	if((app!=null && app.canRead())){
	  File l_ie = new File (p_Viewer);
		if(l_ie!=null && l_ie.canRead()){
		  StringBuffer completePath = new StringBuffer();
		  completePath.append(p_Viewer);
		  completePath.append(" "); //$NON-NLS-1$
		  completePath.append(filePath.toString());
          try {
	         @SuppressWarnings("unused")
			Process theProcess = Runtime.getRuntime().exec(completePath.toString());
          }
          catch(IOException exception) {
             exception.printStackTrace();
          }
		}
		else
		{
			if(!runDefaultBrowser(filePath.toString()))
			{
	    		JOptionPane.showMessageDialog(null,app.getName()+" "+CMMessages.getString("LABEL_APPLICATION_NOT_FOUND"),CMMessages.getString("TITLE_APPLICATION_NOT_FOUND"),JOptionPane.ERROR_MESSAGE);
			}
		}
	}
    //hmendez_22122005_end
	else
	{
	  JOptionPane.showMessageDialog(null,app.getName()+" "+CMMessages.getString("TITLE_BRFILE_NOT_FOUND"),CMMessages.getString("TITLE_APPLICATION_NOT_FOUND"),JOptionPane.ERROR_MESSAGE);
	}
  }

  public CMJTabbedPaneTestCases getContentTabbedPane(){
	  if (contentTabbedPane == null){
			contentTabbedPane = new CMJTabbedPaneTestCases(this);
			 contentTabbedPane.setBounds(new Rectangle(70, 1, 325, 295));
		     contentTabbedPane.setBorder(null);
		     contentTabbedPane.setBackground(new java.awt.Color(212,208,200));
		     contentTabbedPane.addChangeListener(new ChangeListener(){public void stateChanged(ChangeEvent e){
		    	   eventMouseClicked(null);
		    	   updateMenuPerspective(e);
		    	 }});
		     contentTabbedPane.addMouseListener(new MouseAdapter(){public void mouseClicked(MouseEvent e){ eventMouseClicked(e);}});

		}
    return contentTabbedPane;
  }

  public JPopupMenu getJPopupMenuErrors(){
	  if (this.jPopupMenuErrors==null)
	  {
		  this.jPopupMenuErrors = new JPopupMenu();
		    jPopupMenuErrors.add(new CMBaseJMenuItem(CMAction.ERROR_CREATE.getAction()));
	        jPopupMenuErrors.add(new CMBaseJMenuItem(CMAction.ERROR_EDIT.getAction()));
	        jPopupMenuErrors.add(new CMBaseJMenuItem(CMAction.ERROR_DELETE.getAction()));
	        jPopupMenuErrors.addSeparator();
	        jPopupMenuErrors.add(new CMBaseJMenuItem(CMAction.ERROR_ASSIGN_TESTCASES.getAction()));
	        jPopupMenuErrors.addSeparator();
	        jPopupMenuErrors.add(new CMBaseJMenuItem(CMAction.ERROR_REPORT_LIST.getAction()));
	  }
          return jPopupMenuErrors;
      }
    public CMPanelTestDataView getPanelTestDataView() {
        return panelTestDataView;
    }

    public void setPanelTestDataView(CMPanelTestDataView panelTestDataView) {
        this.panelTestDataView = panelTestDataView;
    }

    public CMPanelTestDataSetView getPanelTestDataSetView() {
        return panelTestDataSetView;
    }

    public void setPanelTestDataSetView(CMPanelTestDataSetView panelTestDataSetView) {
        this.panelTestDataSetView = panelTestDataSetView;
    }
	 //hcanedo 12_07_04 begin
      public CMScrollPaneOutput getPanelTestDataSetReportsView() {
        return panelTestDataSetReportsView;
    }

    public void setPanelTestDataSetView(CMScrollPaneOutput panelTestDataSetReportsView) {
        this.panelTestDataSetReportsView = panelTestDataSetReportsView;
    }
    //hcanedo 12_07_04 end

    public void addTDStructureTabs(TDStructure p_TDStructure, TestObjectReference p_TestObjectReference) {
        p_TDStructure.setM_TestObjectReference(p_TestObjectReference);
        panelTDStructureView.setTDStructure(p_TDStructure);
        gridTDStructure.setTDStructure(p_TDStructure);
        //svonborries_05012006_begin
        //panelVariablesView.setM_Project(this.getTreeWorkspaceView().getCurrentProject());//p_TestObjectReference.getM_Project());
        //DefaultMutableTreeNode l_Node =  this.getTreeWorkspaceView().getCurrentProjectNode();
        //panelVariablesView.setM_TDStructure(this.getTreeWorkspaceView().getCurrentTestData(l_Node));
        panelVariablesView.setM_TDStructure(p_TDStructure);
        //svonborries_05122006_end
        panelVariablesView.charge();
        CMIndexTDStructureUpdate.getInstance().initIndex();
        gridTDStructure.update(CMIndexTDStructureUpdate.getInstance().getIndex());
        panelTDStructureView.update();

        panelTestDataView.setM_TestDataCombinations(p_TDStructure.getTestDataCombination());
        panelTestDataView.update();
        panelTestDataSetView.setTDStructure(p_TDStructure);
        panelTestDataSetView.update();
        getContentTabbedPane().addTab(CMMessages.getString("TESTDATA_TDSTRUCTURE"), null, panelTDStructureView, null); //borrar //$NON-NLS-1$
        getContentTabbedPane().addTab(CMMessages.getString("TESTDATA_TESTDATA"), null, panelTestDataView, null); //$NON-NLS-1$
        getContentTabbedPane().addTab(CMMessages.getString("TESTDATA_TESTDATASET"), null, panelTestDataSetView, null); //borrar //$NON-NLS-1$
        getContentTabbedPane().addTab(CMMessages.getString("TESTDATA_TESTDATASET_REPORTS"), null, panelTestDataSetReportsView, null); //borrar //$NON-NLS-1$

        getContentTabbedPane().addTab(CMMessages.getString("TESTDATA_VARIABLES"), null, panelVariablesView, null); //$NON-NLS-1$
        getContentTabbedPane().setIconAt(1, CMIcon.TESTDATA.getImageIcon());
        getContentTabbedPane().setIconAt(2, CMIcon.TESTDATASET.getImageIcon());
        getContentTabbedPane().setIconAt(3, CMIcon.TESTDATASETREPORT.getImageIcon());
        getContentTabbedPane().setIconAt(4, CMIcon.TESTDATA_VARIABLE.getImageIcon());

        gridTDStructure.requestFocus();

    }

    public CMPanelTDStructureView getPanelTDStructureView() {
        return this.panelTDStructureView;
    }



    public void updateGridTDStructure() {
    	gridTDStructure.update(CMIndexTDStructureUpdate.getInstance().getIndex());
    }


    public void menuItemTestObjectDeleteTDStructureActionPerformed(ActionEvent e) {
    	getTreeWorkspaceView().deleteTDStructure();
    }

    public boolean isIsPanelTestDataSelected() { return isPanelTestDataSelected; }

    public void setIsPanelTestDataSelected(boolean isPanelTestDataSelected) {
        this.isPanelTestDataSelected = isPanelTestDataSelected;
        statesMenusCutCopy(isPanelTestDataSelected);//HCanedo 25.06.04
        if(!isPanelTestDataSelected)
        {
            CMCutCopyPasteStructures.getInstance().setStructureCutOrCopy(null);
        }
    }
	public boolean isIsPanelResultComparationSelected() { return isPanelResultComparationSelected; }

    public void setIsPanelResultComparationSelected(boolean isPanelResultComparationSelected) {
        this.isPanelResultComparationSelected = isPanelResultComparationSelected;
    }
    public void changeJItemMenuAssignGlobalValue() {
    	CMAction.TESTDATA_ASSIGN_GLOBAL_REFERENCE.setEnabled(isIsPanelTestDataSelected());
    	CMAction.TESTDATA_CANCEL_GLOBAL_REFERENCE.setEnabled(isIsPanelTestDataSelected());
    }



//////////////////////////////Estados de Menus y Botones///////////////////////////////////////

	public void statesMenusTDStructureCreate(boolean state)
    {
		CMAction.TESTDATA_GENERATE_TESTDATA_FROM_ALL_TESTCASES.setEnabled(state);
		CMAction.TESTDATA_GENERATE_TESTDATA_FROM_ALL_POSITIVE.setEnabled(state);
		CMAction.TESTDATA_GENERATE_TESTDATA_FROM_ALL_NEGATIVE.setEnabled(state);
		CMAction.TESTDATA_GENERATE_TESTDATA_FROM_ALL_SPECIFIC.setEnabled(state);
		CMAction.TESTDATA_GENERATE_STRUCTURE.setEnabled(state);
		CMAction.TESTDATA_CREATE_TDSTRUCTURE.setEnabled(state);
        CMAction.TESTDATA_GENERATE_TESTDATA_FROM_ALL_FAULTY.setEnabled(state);
        CMAction.TESTDATA_GENERATE_RESULT_STRUCTURE.setEnabled(state);//svonborries_11052006
    }
   	public void statesMenusTDStructureEditDelete(boolean state)
    {
        if(CMIndexTDStructureUpdate.getInstance().getState())
        {
        	CMAction.TESTDATA_EDIT_TDSTRUCTURE.setEnabled(state);
        	CMAction.TESTDATA_DELETE_TDSTRUCTURE.setEnabled(state);

        }
    }
    public void statesMenusFields(boolean state)
    {
        if(CMIndexTDStructureUpdate.getInstance().getState())
        {
        	CMAction.TESTDATA_INSERT_ELEMENT.setEnabled(state);
        	CMAction.TESTDATA_DELETE_ELEMENT.setEnabled(state);

        }
    }
   	public void statesMenusFieldsHeader(boolean state)
    {
        if(CMIndexTDStructureUpdate.getInstance().getState())
        {
        	CMAction.TESTDATA_INSERT_ELEMENT.setEnabled(state);
        	CMAction.TESTDATA_DELETE_ELEMENT.setEnabled(false);

            stateGlobalValueReferenceinStructure(false);
        }
    }
    public void stateGlobalValueReferenceinStructure(boolean state)
    {
		CMAction.TESTDATA_CANCEL_GLOBAL_REFERENCE.setEnabled(state);
        CMAction.TESTDATA_ASSIGN_GLOBAL_REFERENCE.setEnabled(state);
    }
    public void stateAssignCancelGlobalValueReferenceinStructure(boolean state)
    {
    	//svonborries_03052006_begin
    	if(CMIndexTDStructureUpdate.getInstance().getState()){
    		CMAction.TESTDATA_CANCEL_GLOBAL_REFERENCE.setEnabled(state);
    		CMAction.TESTDATA_ASSIGN_GLOBAL_REFERENCE.setEnabled(!state);
    	}
    	//svonborries_03052006_end
    }
	//hCanedo 15.07.04_begin
	public void statesMenusEditFormulas(boolean state)
    {
        if(CMIndexTDStructureUpdate.getInstance().getState())
        {
        	CMAction.TESTDATA_EDIT_FORMULA.setEnabled(state);
        }
    }
	//svonborries_11112005_begin
	public void statesMenusDeleteLinkElement(boolean state){
		if(CMIndexTDStructureUpdate.getInstance().getState())
        {
			CMAction.TESTDATA_DELETE_LINK_ELEMENT.setEnabled(state);//svonborries_11112005
        }
	}
	//svonborries_11112005_end
    	public void statesMenusDeleteFormulas(boolean state)
    {
        if(CMIndexTDStructureUpdate.getInstance().getState())
        {
        	CMAction.TESTDATA_DELETE_FORMULA.setEnabled(state);
        }
    }
    public void statesMenusVariables(boolean state)
    {
        if(CMIndexTDStructureUpdate.getInstance().getState())
        {
        	CMAction.TESTDATA_ADD_VARIABLE.setEnabled(state);
        }
    }
    public void statesMenusDeleteVariable(boolean state)
    {
        if(CMIndexTDStructureUpdate.getInstance().getState())
        {
        	CMAction.TESTDATA_DELETE_VARIABLE.setEnabled(state);
        }
    }
    //svonborries_11112005_begin
    public void statesMenusLinkElement(boolean state){
    	if(CMIndexTDStructureUpdate.getInstance().getState())
        {
    		CMAction.TESTDATA_ADD_LINK_ELEMENT.setEnabled(state);
        }
    }
    //svonborries_11112005_end

    public void statesMenusFormulas(boolean state)
    {
        if(CMIndexTDStructureUpdate.getInstance().getState())
        {
        	CMAction.TESTDATA_ADD_FORMULA.setEnabled(state);
        	CMAction.TESTDATA_ASSIGN_GLOBAL_REFERENCE.setEnabled(state);//svonborries_23112005
        }
    }
//hCanedo 15.07.04_end

    public void statesMenusTestDataCreate(boolean state)
    {
    	CMAction.TESTDATA_CREATE_TESTDATA.setEnabled(state);
    }
    public void statesMenusTestDataEditDeleteAssign(boolean state)
    {
    	CMAction.TESTDATA_EDIT_TESTDATA.setEnabled(state);
    	CMAction.TESTDATA_DELETE_TESTDATA.setEnabled( state);
    	CMAction.TESTDATA_ASSIGN_TESTCASE_TO_TESTDATA.setEnabled(state);
    	CMAction.TESTDATA_CANCEL_TESTCASE_TO_TESTDATA.setEnabled(state);
        CMAction.TESTDATA_EDIT_TDSTRUCTURE.setEnabled(state);
        CMAction.TESTDATA_ASSIGN_GLOBAL_STRUCTURE.setEnabled(state);
        CMAction.TESTDATA_CANCEL_GLOBAL_STRUCTURE.setEnabled(state);
    }
    public void stateMenusAssignTdStructure(boolean state)
    {

        CMAction.TESTDATA_ASSIGN_GLOBAL_STRUCTURE.setEnabled(state);
        CMAction.TESTDATA_CANCEL_GLOBAL_STRUCTURE.setEnabled(state);
    	CMAction.TESTDATA_EDIT_TDSTRUCTURE.setEnabled(state);
    }
    public void statesMenusTestDataAssingGlobalValue(boolean state)
    {

		CMAction.TESTDATA_ASSIGN_GLOBAL_REFERENCE.setEnabled(state);
		CMAction.TESTDATA_CANCEL_GLOBAL_REFERENCE.setEnabled(state);
    }
    public void statesMenusYTestDataAssignglobalReference(boolean state)
    {
        CMAction.TESTDATA_ASSIGN_GLOBAL_REFERENCE.setEnabled(state);//svonborries_23112005
        CMAction.TESTDATA_CANCEL_GLOBAL_REFERENCE.setEnabled(!state);
    }
    public void  statesMenusTDAssingGlobalInFieldGlobal(boolean state)
    {
		CMAction.TESTDATA_ASSIGN_GLOBAL_REFERENCE.setEnabled(state);
		CMAction.TESTDATA_CANCEL_GLOBAL_REFERENCE.setEnabled(!state);
    }
    public void statesMenusTestDataSetCreate(boolean state)
    {
    	CMAction.TESTDATASET_NEW_TESTDATASET.setEnabled(state);
    }
    public void statesMenusTestDataSetEditDeleteReport(boolean state)
    {
    	CMAction.TESTDATASET_EDIT_TESTDATASET.setEnabled(state);
    	CMAction.TESTDATASET_DELETE_TESTDATASET.setEnabled(state);
    	CMAction.TESTDATASET_GENERATE_TESTDATASET_REPORT.setEnabled(state);
    }
    public void statesMenusVariablesCreate(boolean state)
    {
    	if(this.getTreeWorkspaceView().getCurrentTestObject()!=null)
    	if(this.getTreeWorkspaceView().getCurrentTestObject().getAccessState().equals(BusinessRules.ACCESS_STATE_CHECKED_IN))
    		state=false;//svonborries_09012006
    	CMAction.TESTDATA_VARIABLE_CREATE.setEnabled(state);
    }

    public void statesMenusVariablesEditDelete(boolean state)
    {

    	if(this.getGridTDStructure().getTDStructure().getM_TestObject().getAccessState().equals(BusinessRules.ACCESS_STATE_CHECKED_IN))
    		state=false; //Ccastedo 06-04-06

    	CMAction.TESTDATA_VARIABLE_EDIT.setEnabled(state);
    	CMAction.TESTDATA_VARIABLE_DELETE.setEnabled(state);

    }

	public void statesAllMenusTDStructure(boolean p_Enable)
    {
        statesMenusFields(p_Enable);
        stateGlobalValueReferenceinStructure(p_Enable);
        statesMenusFieldsHeader(p_Enable);
        statesMenusFormulas(p_Enable);
        //hcanedo 15.07.04_begin
		statesMenusEditFormulas(p_Enable);
        statesMenusDeleteFormulas(p_Enable);
        statesMenusVariables(p_Enable);
        statesMenusDeleteVariable(p_Enable);
        //hcanedo 15.07.04_fin
        statesMenusTDStructureCreate(p_Enable);
        statesMenusTDStructureEditDelete(p_Enable);
        statesMenusTestDataAssingGlobalValue(p_Enable);
       // statesMenusYTestDataAssignglobalReference(p_Enable);
        statesMenusTestDataCreate(p_Enable);
        statesMenusTestDataEditDeleteAssign(p_Enable);
        statesMenusTestDataSetCreate(p_Enable);
        statesMenusTestDataSetEditDeleteReport(p_Enable);
        statesMenusVariablesCreate(p_Enable);
//      svonborries_14112005_begin
        statesMenusLinkElement(p_Enable);
        statesMenusDeleteLinkElement(p_Enable);
//      svonborries_14112005_end
       // statesMenusVariablesEditDelete(p_Enable);
        CMIndexTDStructureUpdate.getInstance().setState(p_Enable);
    }
    public void setStatesMenusTestData(boolean state)
    {
       		statesMenusTDStructureEditDelete(state);
            statesMenusFields(state);
             stateGlobalValueReferenceinStructure(state);
        	statesMenusFieldsHeader(state);
            statesMenusTestDataSetEditDeleteReport(state);
            statesMenusTestDataEditDeleteAssign(state);
            statesMenusFormulas(state);
             //hcanedo 15.07.04_begin
			statesMenusEditFormulas(state);
            statesMenusDeleteFormulas(state);
        	statesMenusVariables(state);
        	statesMenusDeleteVariable(state);
        	//hcanedo 15.07.04_fin
            statesMenusTestDataAssingGlobalValue(state);
           // statesMenusYTestDataAssignglobalReference(state);
           // statesMenusVariablesEditDelete(state);
            statesMenusTestDataSetEditDeleteReport(state);
            stateMenusAssignTdStructure(state);
//          svonborries_14112005_begin
            statesMenusLinkElement(state);
            statesMenusDeleteLinkElement(state);
//          svonborries_14112005_end
    }
    public void statesMenusCutCopy(boolean state)
    {
    	CMAction.CUT.setEnabled(state);
    	CMAction.COPY.setEnabled(state);
    }
    public void statesMenuPaste(boolean state)
    {
    	CMAction.PASTE.setEnabled(state);
    }



    public CMGridTDStructure getGridTDStructure()
    {
        return gridTDStructure;
    }

 public CMResultsComparationPanelView getPanelResultComparation()
 {
	 if (resultsComparationPanelView==null)
	 resultsComparationPanelView = new CMResultsComparationPanelView(this);
    return resultsComparationPanelView;
 }


	public void statesMenusTestDataSetReports(boolean state)
    {
		CMAction.TESTDATASETREPORT_OPEN_REPORT.setEnabled(state);
        CMAction.TESTDATASETREPORT_DELETE_REPORT.setEnabled(state);
      CMAction.TESTDATASETREPORT_EDIT_REPORT.setEnabled(state);
        CMAction.TESTDATASETREPORT_OPEN_REPORT_FOLDER.setEnabled(state);
    }
 /**
  * Freddy's code
  */



   public void setMenuBusinessRulesEnabled(boolean value){

       CMAction.BUSINESSRULES_CHECK.setEnabled(value);
       CMAction.BUSINESSRULES_CHECK_AND_GENERATE.setEnabled(value);
       CMAction.BUSINESSRULES_GENERATE.setEnabled(value);
    }
    public void setMenuItemGenerateTestCases(boolean value){
        CMAction.BUSINESSRULES_GENERATE.setEnabled(value);
    }
	public void saveBRules(){
        this.getBusinessRulesPanelView() .saveFile();
    }

    public CMKeyEventIntercepter getKeyEventIntercepter(){
    	if(keyEventIntercepter == null){
    		keyEventIntercepter = new CMKeyEventIntercepter(this);
    		keyEventIntercepter.setM_CMGridElements(this.getElementsGrid());
    	    keyEventIntercepter.setM_CMUndoMediator(CMUndoMediator.getInstance());
    	    keyEventIntercepter.setM_CMPanelTestDataView(this.panelTestDataView);
    	}
        return keyEventIntercepter;
    }
    //fcastro_13092004_end
    //grueda13092004_begin
    //grueda24112004_begin
    //public String getCurrentAbsoluteBRulesFilePath() {
    public String findAbsoluteBRReportsPath(){
        WorkspaceManager workspaceManager = cmApplication.getSessionManager().getM_WorkspaceManager();
        ProjectReference projectReference = getTreeWorkspaceView().getCurrentProjectReference();
        TestObjectReference testObjectReference = this.getTreeWorkspaceView().getCurrentTestObjectReference();
        TestObject testObject=this.getTreeWorkspaceView().getCurrentTestObject();
        //grueda24112004_begin
		if( workspaceManager.isTestObjectCheckedOutLocalByTheSameUser(testObject, getTreeWorkspaceView().getM_Session2()) ) {
		  projectReference = projectReference.getM_LocalProjectReference();
		}
		//grueda24112004_end

        String absoluteFilePath = workspaceManager.buildAbsoluteBRulesFilePath(projectReference, testObjectReference);
        return absoluteFilePath;
    }
    //grueda24112004_end
    //grueda13092004_end
    //fcastro_20092004_begin

    private boolean waitCursorOn = false;

    public void setWaitCursor(boolean value){
        //this.setGlassPane(newGlassPane);
        if(value==true){

			this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            this.waitCursorOn = true;


        }
        else{
			this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            this.waitCursorOn = false;
            this.getGlassPane().setVisible(false);

        }
    }
    public boolean isWaitCursorOn(){
        return waitCursorOn;
    }
    //fcastro_20092004_end
//hcanedo_18_10_2004_begin
	public void stateAllMenusResultComparison(boolean p_Enable)
    {
    	CMAction.RESULT_COMPARISON_EDIT.getAction().setEnabled(p_Enable);
    	CMAction.RESULT_COMPARISON_CREATE.getAction().setEnabled(p_Enable);
    	CMAction.RESULT_COMPARISON_REPORT.getAction().setEnabled(p_Enable);
    }
//hcanedo_18_10_2004_end
public CMPanelDependencies getPanelDependencyCombiView() {
	if (panelDependencyCombiView == null)
		panelDependencyCombiView = new CMPanelDependencies(this);
		return panelDependencyCombiView;
	}

	public void setStatePasteTestObject(Component p_Component)
    {
		if( p_Component == getPanelProjectView()) {
            statesMenuPaste(true);
        }

    }
//grueda30122004_end

    public void stateDeleteColumnOptionsinStructure(boolean state){
    	CMAction.TESTDATA_DELETE_COLUMN_ADDED.setEnabled(state);
    }

    public void stateChangeNameColumnOptionsinStructure(boolean state){
    	CMAction.TESTDATA_CHANGE_NAME_COLUMN_ADDED.setEnabled(state);
    }

    public void stateInsertColumnOptionsinStructure(boolean state){
    	CMAction.TESTDATA_INSERT_NEW_COLUMN.setEnabled(state);
    }

	public CMGridElements getElementsGrid() {
		if (elementsGrid == null){
			elementsGrid = new CMGridElements(this);
		}

		return elementsGrid;
	}

	public CMTestCaseStructureView getJSPlitPaneTestCaseStructureView() {
		if (jSPlitPaneTestCaseStructureView == null){
			jSPlitPaneTestCaseStructureView = new CMTestCaseStructureView(this);
		}
		return jSPlitPaneTestCaseStructureView;
	}

	public CMStdCombinationStructureView getJSplitPaneStdCombinationStructureView() {
		if (jSplitPaneStdCombinationStructureView == null){
			jSplitPaneStdCombinationStructureView  = new CMStdCombinationStructureView(this);

		}
		return jSplitPaneStdCombinationStructureView;
	}

	/**
	 * @return Returns the panelVariablesView.
	 */
	public CMVariables getPanelVariablesView() {
		return panelVariablesView;
	}
//svonborries_13102005_begin
	public CMBusinessRulesPanelView getBusinessRulesPanelView() {
		if (businessRulesPanelView==null)
		businessRulesPanelView = new CMBusinessRulesPanelView(this);
		return businessRulesPanelView;
	}
	public CMErrorScrollView getErrorScrollView() {
		if (errorScrollView == null){
			errorScrollView = new CMErrorScrollView(this);
		}
		return errorScrollView;
	}

	//Ccastedo begins 13-10-05
	public void setStateEffectsContentSelected() {
		 CMAction.ELEMENT_DELETE.setEnabled(false);
		 CMAction.ELEMENT_CREATE.setEnabled(false);
		 CMAction.ELEMENT_INSERT.setEnabled(false);
		 CMAction.EQUIVALENCECLASS_CREATE.setEnabled(false);
		 CMAction.EQUIVALENCECLASS_EDIT.setEnabled(false);
		 CMAction.EQUIVALENCECLASS_DELETE.setEnabled(false);
		 CMAction.EQUIVALENCECLASS_CREATE_ASSIGN_EFFECT.setEnabled(true);
		CMAction.CUT.setEnabled(true);
		CMAction.COPY.setEnabled(true);
		CMAction.PASTE.setEnabled(false);

	  Object nodeInfo = ((DefaultMutableTreeNode)getTreeWorkspaceView().getSelectedNode().getParent()).getUserObject();
	  if (nodeInfo instanceof CMTestObjectNode)
	  {
          CMTestObjectNode temp = (CMTestObjectNode) nodeInfo;
          if (temp.getTestObject().getStructure().getEffects().size()>0)
          {
        	  CMAction.EQUIVALENCECLASS_EDIT_ASSIGNED_EFFECTS.setEnabled(true);


          }
          else
          {

        	  CMAction.EQUIVALENCECLASS_EDIT_ASSIGNED_EFFECTS.setEnabled(false);
          }
	  }



	}

	public void setStateCellEmptyContentSelected(){
		CMAction.ELEMENT_CREATE.setEnabled(false);
		CMAction.ELEMENT_INSERT.setEnabled(false);
		CMAction.ELEMENT_DELETE.setEnabled(false);
		CMAction.EQUIVALENCECLASS_CREATE.setEnabled(false);
		CMAction.EQUIVALENCECLASS_EDIT.setEnabled(false);
		CMAction.EQUIVALENCECLASS_DELETE.setEnabled(false);
		CMAction.EQUIVALENCECLASS_CREATE_ASSIGN_EFFECT.setEnabled(false);
		CMAction.EQUIVALENCECLASS_EDIT_ASSIGNED_EFFECTS.setEnabled(false);
		CMAction.CUT.setEnabled(false);
		CMAction.COPY.setEnabled(false);
		CMAction.PASTE.setEnabled(false);

	}

	//Ccastedo ends 13-10-05
	/**
	 * @author smoreno
	 * This method searchs trough the children components of the frame to obtain the current focus information
	 * @return
	 */
	public  FocusInfo getCurrentFocusInfo() {
		String tabname = getContentTabbedPane().getTitleAt(getContentTabbedPane().getSelectedIndex());

		return new CMUndoMediator.FocusInfo((JComponent)this.getFocusOwner(),this.getContentTabbedPane(),tabname);
	}
	/**
	 * @author hcanedo
	 */
	public LabelStatusBarItem getStatusLabel() {
		return this.getStatusBar().getLabelStatus();
	}

	public CMGridDependencies getCMGridDependencies() {
		return this.getPanelDependencyCombiView().getCMGridDependencies();
	}

	public CMCombinationViews getCMGridCombinationViews() {
		return this.getPanelDependencyCombiView().getM_CMCombinationViews();
	}

	public JPopupMenu getJPopupMenuCombinations() {
		if (this.jPopupMenuCombinations==null)
		{
			this.jPopupMenuCombinations = new JPopupMenu();
			jPopupMenuCombinations.add(new CMBaseJMenuItem(CMAction.COMBINATION_CREATE.getAction()));
			jPopupMenuCombinations.add(new CMBaseJMenuItem(CMAction.COMBINATION_EDIT.getAction()));
			jPopupMenuCombinations.add(new CMBaseJMenuItem(CMAction.COMBINATION_DELETE.getAction()));
			jPopupMenuCombinations.addSeparator();
			jPopupMenuCombinations.add(new CMBaseJMenuItem(CMAction.COMBINATION_MERGE.getAction()));
			jPopupMenuCombinations.add(new CMBaseJMenuItem(CMAction.COMBINATION_UNMERGE.getAction()));
			jPopupMenuCombinations.addSeparator();
			jPopupMenuCombinations.add(new CMBaseJMenuItem(CMAction.COMBINATION_ASSIGN_TO_TESTCASE.getAction()));
			//jPopupMenuCombinations.add(new CMBaseJMenuItem(CMAction.COMBINATION_ASSIGN_EFFECTS.getAction()));
			jPopupMenuCombinations.add(new CMBaseJMenuItem(CMAction.COMBINATION_CREATE_ASSIGN_EFFECTS.getAction()));
			jPopupMenuCombinations.add(new CMBaseJMenuItem(CMAction.COMBINATION_EDIT_EFFECTS.getAction()));
			jPopupMenuCombinations.addSeparator();
			jPopupMenuCombinations.add(new CMBaseJMenuItem(CMAction.COMBINATION_DELETE_ALL.getAction()));
		}
		return this.jPopupMenuCombinations;
	}

	public JPopupMenu getJPopupMenuTestCases() {
		if (this.jPopupMenuTestCases==null)
		{
			this.jPopupMenuTestCases = new JPopupMenu();
		    jPopupMenuTestCases.add(new CMBaseJMenuItem(CMAction.TESTCASE_CREATE.getAction()));
			jPopupMenuTestCases.add(new CMBaseJMenuItem(CMAction.TESTCASE_EDIT.getAction()));
			jPopupMenuTestCases.add(new CMBaseJMenuItem(CMAction.TESTCASE_DELETE.getAction()));
			jPopupMenuTestCases.addSeparator();
			jPopupMenuTestCases.add(new CMBaseJMenuItem(CMAction.TESTCASE_ASSIGN_EQUIVALENCECLASS.getAction()));
			jPopupMenuTestCases.add(new CMBaseJMenuItem(CMAction.TESTCASE_ASSIGN_COMBINATION.getAction()));
			jPopupMenuTestCases.add(new CMBaseJMenuItem(CMAction.TESTCASE_ASSIGN_STDCOMBINATION.getAction()));
	        jPopupMenuTestCases.add(new CMBaseJMenuItem(CMAction.TESTCASE_ASSIGN_ERRORS.getAction()));
			jPopupMenuTestCases.addSeparator();
			jPopupMenuTestCases.add(new CMBaseJMenuItem(CMAction.TESTCASE_CANCEL_ASSIGN_EQUIVALENCECLASS.getAction()));
			jPopupMenuTestCases.addSeparator();
			jPopupMenuTestCases.add(new CMBaseJMenuItem(CMAction.TESTCASE_GENERATE.getAction()));
			jPopupMenuTestCases.addSeparator();
			jPopupMenuTestCases.add(new CMBaseJMenuItem(CMAction.TESTCASE_REPORT_LIST1.getAction()));
		//	jPopupMenuTestCases.add(new CMBaseJMenuItem(CMAction.TESTCASE_REPORT_LIST2.getAction()));
			//jPopupMenuTestCases.add(new CMBaseJMenuItem(CMAction.TESTCASE_REPORT_CSV.getAction()));
			//jPopupMenuTestCases.add(new CMBaseJMenuItem(CMAction.TESTCASE_REPORT_COMPUWARE.getAction()));
			//jPopupMenuTestCases.add(new CMBaseJMenuItem(CMAction.TESTCASE_REPORT_QADIRECTOR.getAction()));
			jPopupMenuTestCases.addSeparator();
			jPopupMenuTestCases.add(new CMBaseJMenuItem(CMAction.TESTCASE_DELETE_ALL.getAction()));

		}
		return this.jPopupMenuTestCases;
	}

	public JPopupMenu getPopupMenuDependency() {
		if (this.jPopupMenuDependency==null)
		{
			this.jPopupMenuDependency = new JPopupMenu();
			jPopupMenuDependency.add(new CMBaseJMenuItem(CMAction.DEPENDENCY_CREATE.getAction()));
			jPopupMenuDependency.add(new CMBaseJMenuItem(CMAction.DEPENDENCY_EDIT.getAction()));
			jPopupMenuDependency.add(new CMBaseJMenuItem(CMAction.DEPENDENCY_DELETE.getAction()));
			jPopupMenuDependency.addSeparator();
			JideMenu jMenuDependencyGenerateCombinations = new JideMenu(CMAction.DEPENDENCY_GENERATE_COMB.getAction());
			jMenuDependencyGenerateCombinations.add(new CMBaseJMenuItem(CMAction.DEPENDENCY_GENERATE_COMB_PERMUTATION.getAction()));
			jMenuDependencyGenerateCombinations.add(new CMBaseJMenuItem(CMAction.DEPENDENCY_GENERATE_COMB_ALLPAIRS.getAction()));
			jPopupMenuDependency.add(jMenuDependencyGenerateCombinations );
			jPopupMenuDependency.addSeparator();
			jPopupMenuDependency.add(new CMBaseJMenuItem(CMAction.DEPENDENCY_REPORT_EXCEL.getAction()));
		}
		return this.jPopupMenuDependency;
	}

	public JPopupMenu getJPopupMenuCauseEffects() {
		if (jPopupMenuCauseEffects==null)
		{
			jPopupMenuCauseEffects = new JPopupMenu();

		jPopupMenuCauseEffects.add(new CMBaseJMenuItem(CMAction.EFFECT_CREATE.getAction()));
		jPopupMenuCauseEffects.add(new CMBaseJMenuItem(CMAction.EFFECT_EDIT.getAction()));
		jPopupMenuCauseEffects.add(new CMBaseJMenuItem(CMAction.EFFECT_DELETE.getAction()));

		jPopupMenuCauseEffects.addSeparator();

        jPopupMenuCauseEffects.add(new CMBaseJMenuItem(CMAction.CUT.getAction()));
        jPopupMenuCauseEffects.add(new CMBaseJMenuItem(CMAction.COPY.getAction()));
        jPopupMenuCauseEffects.add(new CMBaseJMenuItem(CMAction.PASTE.getAction()));
        jPopupMenuCauseEffects.addSeparator();
		jPopupMenuCauseEffects.add(new CMBaseJMenuItem(CMAction.EFFECT_DELETE_NOT_USED.getAction()));
		jPopupMenuCauseEffects.addSeparator();
		jPopupMenuCauseEffects.add(new CMBaseJMenuItem(CMAction.IMPORT_CSV.getAction()));
		}
		return this.jPopupMenuCauseEffects;
	}


	public JPopupMenu getPopupMenuElements() {
		if (jPopupMenuElements== null)
		{
			jPopupMenuElements = new JPopupMenu();

		jPopupMenuElements.add(new CMBaseJMenuItem(CMAction.ELEMENT_CREATE.getAction(),true));
		jPopupMenuElements.add(new CMBaseJMenuItem(CMAction.ELEMENT_INSERT.getAction(),true));
		jPopupMenuElements.add(new CMBaseJMenuItem(CMAction.ELEMENT_DELETE.getAction(),true));


		jPopupMenuElements.add(new CMBaseJMenuItem(CMAction.EQUIVALENCECLASS_CREATE.getAction(),true));
		jPopupMenuElements.add(new CMBaseJMenuItem(CMAction.EQUIVALENCECLASS_EDIT.getAction(),true));
		jPopupMenuElements.add(new CMBaseJMenuItem(CMAction.EQUIVALENCECLASS_DELETE.getAction(),true));

		jPopupMenuElements.add(new CMBaseJMenuItem(CMAction.EQUIVALENCECLASS_CREATE_ASSIGN_EFFECT.getAction(),true));
		jPopupMenuElements.add(new CMBaseJMenuItem(CMAction.EQUIVALENCECLASS_EDIT_ASSIGNED_EFFECTS.getAction(),true));


		jPopupMenuElements.add(new CMBaseJMenuItem(CMAction.CUT.getAction()));
		jPopupMenuElements.add(new CMBaseJMenuItem(CMAction.COPY.getAction()));
	    jPopupMenuElements.add(new CMBaseJMenuItem(CMAction.PASTE.getAction()));

	    jPopupMenuElements.addSeparator();
	    jPopupMenuElements.add(new CMBaseJMenuItem(CMAction.ELEMENT_REORDER.getAction(),true));
	    jPopupMenuElements.add(new CMBaseJMenuItem(CMAction.IMPORT_CSV.getAction()));
		}
		return this.jPopupMenuElements;
	}

	public CMTestCaseViews getCMTestCaseViews() {
		return this.getJSPlitPaneTestCaseStructureView().getM_CMElementAndTestCaseViews().getLnkCMTestCaseViews();
	}

		public CMStdCombinationViews getCMStdCombinationViews() {
		return this.getJSplitPaneStdCombinationStructureView().getM_CMElementAndStdCombinationViews().getLnkCMStdCombinationViews();
	}

	public JPopupMenu getJPopupMenuStdCombinations() {
		if (this.jPopupMenuStdCombinations==null)
		{
			this.jPopupMenuStdCombinations = new JPopupMenu();
			jPopupMenuStdCombinations.add(new CMBaseJMenuItem(CMAction.STDCOMBINATION_CREATE.getAction()));
	        jPopupMenuStdCombinations.add(new CMBaseJMenuItem(CMAction.STDCOMBINATION_EDIT.getAction()));
	        jPopupMenuStdCombinations.add(new CMBaseJMenuItem(CMAction.STDCOMBINATION_DELETE.getAction()));
	        jPopupMenuStdCombinations.addSeparator();
	        jPopupMenuStdCombinations.add(new CMBaseJMenuItem(CMAction.STDCOMBINATION_ASSIGN_EQUIVALENCECLASS.getAction()));
	        jPopupMenuStdCombinations.add(new CMBaseJMenuItem(CMAction.STDCOMBINATION_ASSIGN_TEST_CASES.getAction()));
	        jPopupMenuStdCombinations.addSeparator();
	        jPopupMenuStdCombinations.add(new CMBaseJMenuItem(CMAction.STDCOMBINATION_CANCEL_ASSIGN_EQUIVALENCECLASS.getAction()));


		}
		return this.jPopupMenuStdCombinations;
	}

	public Component getSelectedComponent() {
		return getContentTabbedPane().getSelectedComponent();
	}

	public JPanel getPanelElementsView() {
		if (panelElementsView == null){
			panelElementsView = new JPanel();
		   panelElementsView.setLayout(new BorderLayout());
	        panelElementsView.setBorder(javax.swing.BorderFactory.createEmptyBorder());
	        panelElementsView.add(getJScrollPaneElements() , "Center"); //$NON-NLS-1$
		}
		return this.panelElementsView;
	}

	public JPanel getPanelCauseEffectsView() {

		if (panelCauseEffectsView == null){
			panelCauseEffectsView = new JPanel();
	        panelCauseEffectsView.setBorder(null);
	        panelCauseEffectsView.setLayout(new BorderLayout());
	        panelCauseEffectsView.add(getCauseEffectStructureView(), "Center"); //$NON-NLS-1$
		}
		return this.panelCauseEffectsView;
	}



	public JSplitPane getJSplitPaneTreeTabs() {
		if (jSplitPaneTreeTabs== null){
			jSplitPaneTreeTabs = new JSplitPane();
			 jSplitPaneTreeTabs.setOneTouchExpandable(true);
		     jSplitPaneTreeTabs.setDividerSize(9);
		     jSplitPaneTreeTabs.setContinuousLayout(false);
		     jSplitPaneTreeTabs.setDividerLocation(300);
		     jSplitPaneTreeTabs.setLeftComponent(getScrollPaneWorkspaceView());
		     jSplitPaneTreeTabs.setRightComponent(getContentTabbedPane());
		     jSplitPaneTreeTabs.addMouseListener(new MouseAdapter(){public void mouseClicked(MouseEvent e){eventMouseClicked(e);}});
		}

		return this.jSplitPaneTreeTabs;
	}

	public JPopupMenu getJPopupMenuFile() {
		if (this.jPopupMenuFile==null){
			this.jPopupMenuFile = new JPopupMenu();
			jPopupMenuFile.add(new CMBaseJMenuItem(CMAction.CREATE.getAction()));
			jPopupMenuFile.add(new CMBaseJMenuItem(CMAction.ADDOPEN.getAction()));
			jPopupMenuFile.addSeparator();
	        jPopupMenuFile.add(new CMBaseJMenuItem(CMAction.SAVE.getAction()));
	        jPopupMenuFile.addSeparator();
	        jPopupMenuFile.add(new CMBaseJMenuItem(CMAction.DELETE.getAction()));
	        jPopupMenuFile.addSeparator();
	        jPopupMenuFile.add(new CMBaseJMenuItem(CMAction.IMPORT_PROJECT.getAction()));
	        jPopupMenuFile.addSeparator();
	        jPopupMenuFile.add(new CMBaseJMenuItem(CMAction.CUT.getAction()));
	        jPopupMenuFile.add(new CMBaseJMenuItem(CMAction.COPY.getAction()));
	        jPopupMenuFile.add(new CMBaseJMenuItem(CMAction.PASTE.getAction()));
	        jPopupMenuFile.addSeparator();
			jPopupMenuFile.add(new CMBaseJMenuItem(CMAction.HIDE_SHOW_TREE.getAction()));
			jPopupMenuFile.addSeparator();
	        jPopupMenuFile.add(new CMBaseJMenuItem(CMAction.REORDER_TESTOBJECTS.getAction(),true));
	        jPopupMenuFile.add(new CMBaseJMenuItem(CMAction.REORDER_WORKSPACES.getAction(),true));
	        jPopupMenuFile.add(new CMBaseJMenuItem(CMAction.RENAME_WORKSPACE.getAction(),true));
	        jPopupMenuFile.add(new CMBaseJMenuItem(CMAction.RENAME_PROJECT.getAction(),true));

		}
		return this.jPopupMenuFile;
	}

	/**
	*@autor smoreno
	 * @return
	 */
	public CMErrorGridView getCMErrorGridView() {

		return this.getCMErrorScrollView().getM_CMErrorGridView();
	}



	public JPopupMenu getJPopupMenuTDStructureFormula(){
		if(jPopupMenuTDStructureFormula == null){
			jPopupMenuTDStructureFormula = new JPopupMenu();
	        jPopupMenuTDStructureFormula.add(new CMBaseJMenuItem(CMAction.TESTDATA_ADD_FORMULA.getAction()));
	        jPopupMenuTDStructureFormula.add(new CMBaseJMenuItem(CMAction.TESTDATA_EDIT_FORMULA.getAction()));
	        jPopupMenuTDStructureFormula.add(new CMBaseJMenuItem(CMAction.TESTDATA_DELETE_FORMULA.getAction()));
	        jPopupMenuTDStructureFormula.addSeparator();//svonborries_14112005
	        jPopupMenuTDStructureFormula.add(new CMBaseJMenuItem(CMAction.TESTDATA_ADD_VARIABLE.getAction()));
	        jPopupMenuTDStructureFormula.add(new CMBaseJMenuItem(CMAction.TESTDATA_DELETE_VARIABLE.getAction()));
	        jPopupMenuTDStructureFormula.addSeparator();//svonborries_14112005
	        jPopupMenuTDStructureFormula.add(new CMBaseJMenuItem(CMAction.TESTDATA_ADD_LINK_ELEMENT.getAction()));//svonborries_11112005
	        jPopupMenuTDStructureFormula.add(new CMBaseJMenuItem(CMAction.TESTDATA_DELETE_LINK_ELEMENT.getAction()));//svonborries_11112005
	        jPopupMenuTDStructureFormula.addSeparator();//svonborries_14112005
	        jPopupMenuTDStructureFormula.add(new CMBaseJMenuItem(CMAction.TESTDATA_ASSIGN_GLOBAL_VALUE.getAction(),true));
	        jPopupMenuTDStructureFormula.add(new CMBaseJMenuItem(CMAction.TESTDATA_ASSIGN_GLOBAL_REFERENCE.getAction(),true));
			jPopupMenuTDStructureFormula.add(new CMBaseJMenuItem(CMAction.TESTDATA_CANCEL_GLOBAL_REFERENCE.getAction(),true));
		}
		return jPopupMenuTDStructureFormula;
	}

    public JPopupMenu jPopupMenuFields(){
    	if(jPopupMenuFields == null){
    		jPopupMenuFields = new JPopupMenu();
    		jPopupMenuFields.add(new CMBaseJMenuItem(CMAction.TESTDATA_INSERT_ELEMENT.getAction()));
            jPopupMenuFields.add(new CMBaseJMenuItem(CMAction.TESTDATA_DELETE_ELEMENT.getAction()));
            jPopupMenuFields.addSeparator();
            jPopupMenuFields.add(new CMBaseJMenuItem(CMAction.TESTDATA_ASSIGN_GLOBAL_REFERENCE.getAction(),true));
            jPopupMenuFields.add(new CMBaseJMenuItem(CMAction.TESTDATA_CANCEL_GLOBAL_REFERENCE.getAction(),true));
            jPopupMenuFields.addSeparator();
            jPopupMenuFields.add(new CMBaseJMenuItem(CMAction.TESTDATA_ASSIGN_FORMAT.getAction()),true);
            jPopupMenuFields.addSeparator();
            jPopupMenuFields.add(new CMBaseJMenuItem(CMAction.TESTDATA_INSERT_NEW_COLUMN.getAction()));
            jPopupMenuFields.add(new CMBaseJMenuItem(CMAction.TESTDATA_DELETE_COLUMN_ADDED.getAction()));
            jPopupMenuFields.add(new CMBaseJMenuItem(CMAction.TESTDATA_CHANGE_NAME_COLUMN_ADDED.getAction()));
    	}
    	return jPopupMenuFields;
    }

    public JPopupMenu getJPopupMenuAssignGlobalValue(){
    	if(jPopupMenuAssignGlobalValue == null){
    		jPopupMenuAssignGlobalValue = new JPopupMenu();
            jPopupMenuAssignGlobalValue.add(new CMBaseJMenuItem(CMAction.TESTDATA_ASSIGN_GLOBAL_VALUE.getAction(),true));
    		jPopupMenuAssignGlobalValue.add(new CMBaseJMenuItem(CMAction.TESTDATA_ASSIGN_GLOBAL_REFERENCE.getAction(),true));
            jPopupMenuAssignGlobalValue.add(new CMBaseJMenuItem(CMAction.TESTDATA_CANCEL_GLOBAL_REFERENCE.getAction(),true));
            //jPopupMenuAssignGlobalValue.add(jMenuItemFormatStructure2);
    	}
    	return jPopupMenuAssignGlobalValue;
    }

    public JPopupMenu getJPopupMenuAssignGlobalTDStructure(){
    	if(jPopupMenuAssignGlobalTDStructure == null){
    		jPopupMenuAssignGlobalTDStructure = new JPopupMenu();
            jPopupMenuAssignGlobalTDStructure.setLabel("jPopupMenu1");
            jPopupMenuAssignGlobalTDStructure.add(new CMBaseJMenuItem(CMAction.TESTDATA_ASSIGN_GLOBAL_STRUCTURE.getAction()));
            jPopupMenuAssignGlobalTDStructure.add(new CMBaseJMenuItem(CMAction.TESTDATA_CANCEL_GLOBAL_STRUCTURE.getAction()));
            jPopupMenuAssignGlobalTDStructure.addSeparator();
            jPopupMenuAssignGlobalTDStructure.add(new CMBaseJMenuItem(CMAction.TESTDATA_EDIT_TDSTRUCTURE.getAction()));
    	}
    	return jPopupMenuAssignGlobalTDStructure;
    }

    public JPopupMenu getJPopupMenuEditTDStructure(){
    	if(jPopupMenuEditTDStructure == null){
    		jPopupMenuEditTDStructure = new JPopupMenu();
            jPopupMenuEditTDStructure.setLabel("jPopupMenu1");
            jPopupMenuEditTDStructure.add(new CMBaseJMenuItem(CMAction.TESTDATA_CREATE_TDSTRUCTURE.getAction()));
            jPopupMenuEditTDStructure.add(new CMBaseJMenuItem(CMAction.TESTDATA_EDIT_TDSTRUCTURE.getAction()));
            jPopupMenuEditTDStructure.add(new CMBaseJMenuItem(CMAction.TESTDATA_DELETE_TDSTRUCTURE.getAction()));
            jPopupMenuEditTDStructure.addSeparator();
            jPopupMenuEditTDStructure.add(new CMBaseJMenuItem(CMAction.TESTDATA_GENERATE_STRUCTURE.getAction()));
            jPopupMenuEditTDStructure.addSeparator();
            jPopupMenuEditTDStructure.add(new CMBaseJMenuItem(CMAction.TESTDATA_GENERATE_TESTDATA_FROM_ALL_TESTCASES.getAction()));
            jPopupMenuEditTDStructure.add(new CMBaseJMenuItem(CMAction.TESTDATA_GENERATE_TESTDATA_FROM_ALL_POSITIVE.getAction()));
            jPopupMenuEditTDStructure.add(new CMBaseJMenuItem(CMAction.TESTDATA_GENERATE_TESTDATA_FROM_ALL_NEGATIVE.getAction()));
    		jPopupMenuEditTDStructure.add(new CMBaseJMenuItem(CMAction.TESTDATA_GENERATE_TESTDATA_FROM_ALL_FAULTY.getAction()));
            jPopupMenuEditTDStructure.add(new CMBaseJMenuItem(CMAction.TESTDATA_GENERATE_TESTDATA_FROM_ALL_SPECIFIC.getAction()));
            jPopupMenuEditTDStructure.add(new CMBaseJMenuItem(CMAction.TESTDATA_GENERATE_RESULT_STRUCTURE.getAction()));//svonborries_11052006
    	}
    	return jPopupMenuEditTDStructure;
    }

    public JPopupMenu getJPopupMenuTestDataSet(){
    	if(jPopupMenuTestDataSet == null){
    		jPopupMenuTestDataSet = new JPopupMenu();
            jPopupMenuTestDataSet.add(new CMBaseJMenuItem(CMAction.TESTDATASET_NEW_TESTDATASET.getAction()));
            jPopupMenuTestDataSet.add(new CMBaseJMenuItem(CMAction.TESTDATASET_EDIT_TESTDATASET.getAction()));
            jPopupMenuTestDataSet.add(new CMBaseJMenuItem(CMAction.TESTDATASET_DELETE_TESTDATASET.getAction()));
            jPopupMenuTestDataSet.addSeparator();
    		jPopupMenuTestDataSet.add(new CMBaseJMenuItem(CMAction.TESTDATASET_GENERATE_TESTDATASET_REPORT.getAction()));
    	}
    	return jPopupMenuTestDataSet;
    }

    public JPopupMenu getJPopupMenuTestData(){
    	if(jPopupMenuTestData == null){
    		jPopupMenuTestData = new JPopupMenu();
      		jPopupMenuTestData.add(new CMBaseJMenuItem(CMAction.TESTDATA_CREATE_TESTDATA.getAction()));
    		jPopupMenuTestData.add(new CMBaseJMenuItem(CMAction.TESTDATA_EDIT_TESTDATA.getAction()));
            jPopupMenuTestData.add(new CMBaseJMenuItem(CMAction.TESTDATA_DELETE_TESTDATA.getAction()));
            jPopupMenuTestData.addSeparator();
            jPopupMenuTestData.add(new CMBaseJMenuItem(CMAction.TESTDATA_ASSIGN_TESTCASE_TO_TESTDATA.getAction()));
            jPopupMenuTestData.add(new CMBaseJMenuItem(CMAction.TESTDATA_CANCEL_TESTCASE_TO_TESTDATA.getAction()));
            jPopupMenuTestData.addSeparator();
            jPopupMenuTestData.add(new CMBaseJMenuItem(CMAction.TESTDATA_IMPORT_TESTDATA.getAction()));
    	}
    	return jPopupMenuTestData;
    }

    public JPopupMenu getJPopupMenuFormatStructure(){
    	if(jPopupMenuFormatStructure == null){
    		jPopupMenuFormatStructure = new JPopupMenu();
   		 	//jPopupMenuFormatStructure.add(jPopupMenuItemFormatStructure);
    	}
    	return jPopupMenuFormatStructure;
    }

    public JPopupMenu getJPopupMenuTestDataSetReports(){
    	if(jPopupMenuTestDataSetReports == null){
    		jPopupMenuTestDataSetReports = new JPopupMenu();
			jPopupMenuTestDataSetReports.add(new CMBaseJMenuItem(CMAction.TESTDATASETREPORT_OPEN_REPORT.getAction()));
			//ccastedo begins 30.08.06
    		jPopupMenuTestDataSetReports.add(new CMBaseJMenuItem(CMAction.TESTDATASETREPORT_OPEN_REPORT_FOLDER.getAction()));
    		jPopupMenuTestDataSetReports.addSeparator();
    		//ccastedo ends 30.08.06

            jPopupMenuTestDataSetReports.add(new CMBaseJMenuItem(CMAction.TESTDATASETREPORT_UPDATE.getAction()));
            jPopupMenuTestDataSetReports.addSeparator();
    		jPopupMenuTestDataSetReports.add(new CMBaseJMenuItem(CMAction.TESTDATASETREPORT_ADD_REPORT.getAction()));
    		jPopupMenuTestDataSetReports.add(new CMBaseJMenuItem(CMAction.TESTDATASETREPORT_EDIT_REPORT.getAction()));
    		jPopupMenuTestDataSetReports.add(new CMBaseJMenuItem(CMAction.TESTDATASETREPORT_DELETE_REPORT.getAction()));
    	}
    	return jPopupMenuTestDataSetReports;
    }





	public void showBusinessRulesEditMenus(boolean p_value){

		Object menus[]=getDockableBarManager().getDockableBar("MainMenu").getMenu(1).getPopupMenu().getComponents();
		for (int i = 0; i < menus.length; i++) {
			if (menus[i] instanceof CMBaseJMenuItem) {
				CMBaseJMenuItem menuItem = (CMBaseJMenuItem) menus[i];
				Action action= menuItem.getAction();
				if (action instanceof CMFindReplaceAction || action instanceof CMSelectAllAction) {
					menuItem.setVisible(p_value);
					if(menus[i-1]instanceof JSeparator){
						JSeparator separator=(JSeparator)menus[i-1];
						separator.setVisible(p_value);
					}
				}
			}
		}
		if (getDockableBarManager().getDockableBar("Standart")==null) return;
		menus=getDockableBarManager().getDockableBar("Standart").getComponents();
		for (int i = 0; i < menus.length; i++) {
			if (menus[i] instanceof CMToolBarButton) {
				CMToolBarButton menuItem = (CMToolBarButton) menus[i];
				Action action= menuItem.getAction();
				if (action instanceof CMFindReplaceAction || action instanceof CMSelectAllAction) {
					menuItem.setVisible(p_value);
					CMCustomJTextPane.disableKeyBindings("alt pressed NUMPAD4",menuItem);
					if(menus[i-1]instanceof JSeparator){
						JSeparator separator=(JSeparator)menus[i-1];
						separator.setVisible(p_value);
					}
				}
			}
		}
	}




	/**
	 * @author hcanedo
	 */
	public CMStatusBar getStatusBar() {
		if (statusBar == null)
		{
			statusBar = new CMStatusBar();
			 statusBar.setBorder(BorderFactory.createBevelBorder(1));

		}
		return statusBar;
	}
//svonborries_18072007_begin
	public JPopupMenu getJPopupMenuAssignFormattoLocalTypeData() {
		if(jPopupMenuAssignFormattoLocalTypeData == null){
			jPopupMenuAssignFormattoLocalTypeData = new JPopupMenu();
			jPopupMenuAssignFormattoLocalTypeData.add(new CMBaseJMenuItem(CMAction.TESTDATA_ASSIGN_FORMAT.getAction(),true));
		}
		return jPopupMenuAssignFormattoLocalTypeData;
	}
	//svonborries_18072007_end



	private JScrollPane getScrollPaneWorkspaceView() {
		if (scrollPaneWorkspaceView == null){
			scrollPaneWorkspaceView = new JScrollPane(getTreeWorkspaceView());
		}

		return scrollPaneWorkspaceView;
	}

	public CMErrorsPanelView getErrorsPanelView() {
		if (errorsPanelView==null){
			errorsPanelView = new CMErrorsPanelView();
		    errorsPanelView.setBorder(null);
	        errorsPanelView.setLayout(new BorderLayout());
	        errorsPanelView.add(getErrorScrollView(), "Center"); //$NON-NLS-1$
		}
		return errorsPanelView;
	}

	private JScrollPane getJScrollPaneElements() {
		if (jScrollPaneElements==null)
		{
			jScrollPaneElements = new JScrollPane();
			jScrollPaneElements.setViewport( getGridElementViewport());
	        jScrollPaneElements.getVerticalScrollBar().setUnitIncrement(22);
	        jScrollPaneElements.getViewport().setScrollMode(JViewport.BACKINGSTORE_SCROLL_MODE);
	        jScrollPaneElements.setBorder(null);
	        jScrollPaneElements.setVerticalScrollBarPolicy(javax.swing.JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		}
		return jScrollPaneElements;
	}

	private JViewport getGridElementViewport() {
		if (gridElementViewport==null)
		{gridElementViewport = new JViewport();
		  gridElementViewport.setView(getGridElementPanel());
		}
		return gridElementViewport;
	}

	private JPanel getPanelTestCasesView() {
		if (panelTestCasesView == null){
			panelTestCasesView = new JPanel();
			  panelTestCasesView.setBorder(null);
		      panelTestCasesView.setLayout(new BorderLayout());
		      panelTestCasesView.add(getJSPlitPaneTestCaseStructureView(), "Center"); //$NON-NLS-1$
		}
		return panelTestCasesView;
	}

	private JPanel getGridElementPanel() {
		if (gridElementPanel == null){
			gridElementPanel = new JPanel();
			gridElementPanel.setLayout(new BorderLayout());
	        gridElementPanel.add(getElementsGrid(),BorderLayout.CENTER);
	        JLabel blankLabel = new JLabel(" ");
	        blankLabel.setFont(new Font("Arial",Font.PLAIN,100));
	        gridElementPanel.add(blankLabel,BorderLayout.SOUTH);
		}
		return gridElementPanel;
	}

	private JPanel getPanelStdCombinationsView() {
		if (panelStdCombinationsView == null)
		{
			panelStdCombinationsView = new JPanel();
			 panelStdCombinationsView.setBorder(null);
		     panelStdCombinationsView.setLayout(new BorderLayout());
		     panelStdCombinationsView.add(getJSplitPaneStdCombinationStructureView(), "Center"); //$NON-NLS-1$

		}
		return panelStdCombinationsView;
	}


}