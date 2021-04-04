/* Developed by BUSINESS SOFTWARE INNOVATIONS.  .. Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved..*/

package bi.view.testcaseviews;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Event;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JViewport;
import javax.swing.tree.DefaultMutableTreeNode;

import model.BusinessRules;
import model.CMError;
import model.CMField;
import model.Combination;
import model.Dependency;
import model.Effect;
import model.Element;
import model.EquivalenceClass;
import model.Project2;
import model.ProjectReference;
import model.Session2;
import model.StdCombination;
import model.Structure;
import model.TestCase;
import model.TestCaseGroup;
import model.TestObject;
import model.TestObjectReference;
import model.Workspace2;
import model.util.CMModelEvent;
import model.util.CMModelListener;
import model.util.CMStateBean;
import model.util.CMUserOrderBean;
import bi.controller.CombinationManager;
import bi.controller.ProjectManager;
import bi.controller.SessionManager;
import bi.controller.TestCaseManager;
import bi.view.actions.CMAction;
import bi.view.actions.CMEnabledAction;
import bi.view.cells.CMBaseCell;
import bi.view.cells.CMCellCombination;
import bi.view.cells.CMCellDependency;
import bi.view.cells.CMCellDependencyName;
import bi.view.cells.CMCellEffectsInCombination;
import bi.view.cells.CMCellElement;
import bi.view.cells.CMCellElementDescription;
import bi.view.cells.CMCellElementDescriptionEmpty;
import bi.view.cells.CMCellElementName;
import bi.view.cells.CMCellEmpty;
import bi.view.cells.CMCellEquivalenceClass;
import bi.view.cells.CMCellEquivalenceClassInCombination;
import bi.view.cells.CMCellEquivalenceClassInTestCase;
import bi.view.cells.CMCellEquivalenceClassName;
import bi.view.cells.CMCellEquivalenceClassState;
import bi.view.cells.CMCellEquivalenceClassValue;
import bi.view.cells.CMCellHeaderTestCase;
import bi.view.cells.CMCellSelectAllEquivalenceClassesOfElement;
import bi.view.cells.CMCellSelectEquivalenceClass;
import bi.view.cells.headers.CMCellHeaderCombination;
import bi.view.cells.headers.CMCellHeaderDependencyName;
import bi.view.cells.headers.CMCellHeaderEffectsInCombination;
import bi.view.cells.headers.CMCellHeaderElementDescription;
import bi.view.cells.headers.CMCellHeaderElementDescriptionEmpty;
import bi.view.cells.headers.CMCellHeaderElementName;
import bi.view.cells.headers.CMCellHeaderEquivalenceClassName;
import bi.view.cells.headers.CMCellHeaderEquivalenceClassState;
import bi.view.cells.headers.CMCellHeaderEquivalenceClassValue;
import bi.view.cells.renderers.CMImageEquivalenceClassinTestCaseRenderer;
import bi.view.cells.renderers.CMTestCaseCellRenderer;
import bi.view.elementviews.CMElementViews;
import bi.view.grids.CMBaseJSmartGrid;
import bi.view.icons.CMIcon;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.mainframeviews.CMFrameView;
import bi.view.treeviews.nodes.CMWorkspaceNode;

import com.eliad.model.AbstractDirectSpanModel;
import com.eliad.model.AbstractStyleModel;
import com.eliad.model.ExtentCell;
import com.eliad.model.GenericGridModel;
import com.eliad.model.GridContext;
import com.eliad.model.GridSelectionEvent;
import com.eliad.model.StyleModel;
import com.eliad.model.defaults.DefaultGridCellRenderer;
import com.eliad.model.defaults.DefaultStyleModel;
import com.eliad.swing.GridEditingEvent;
import com.eliad.swing.GridEvent;
import com.eliad.util.RulerConstants;

public class CMTestCaseViews extends CMBaseJSmartGrid implements CMModelListener {
   private CMSpanModel  spanModel = null;
  private CMFrameView cmFrame = null;

  private  Structure m_Structure = null;

  private CMGridModel cmGridModel = null;
  private  TestCaseManager m_TestCaseManager = null;
  private CombinationManager m_CombinationManager = null;
  private Object editingObject = null;
    /**
     * @clientCardinality 1
     * @supplierCardinality 1
     * @directed
     * @label uses*/
    private CMElementViews lnkCMElementViews;
    private CMElementAndTestCaseViews m_CMElementAndTestCaseViews;
    private boolean selectionMessagedReceived =false;//fcastro_02092004
	private boolean defaultSelectionMode = false;//fcastro_20092004

	private CMTestCaseCellRenderer m_ColorRenderer = new CMTestCaseCellRenderer();
	private CMImageEquivalenceClassinTestCaseRenderer cmImageRenderer = new CMImageEquivalenceClassinTestCaseRenderer();
	
	private FocusListener focusListener = new FocusAdapter(){
		public void focusLost(FocusEvent e) {
			//repaint();
			lnkCMElementViews.repaint();
	   }
		public void focusGained(FocusEvent e) {
		//	m_CMElementAndTestCaseViews.getLnkCMElementViews().repaint();
		}
  };


    public CMTestCaseViews(CMFrameView frame, CMElementViews p_CMElementViews, CMElementAndTestCaseViews p_CMElementAndTestCaseViews) {
    	super();
	  this.lnkCMElementViews = p_CMElementViews;
      this.m_CMElementAndTestCaseViews = p_CMElementAndTestCaseViews;
      cmFrame = frame;
      frame.getKeyEventIntercepter().setM_CMTestCaseViews(this);//fcastro_13092004
      this.addFocusListener(focusListener);
	  initGUI();
	  this.setGridNavigationPolicy(new CMTestCaseGridNavPolicy(this));
    }

 public void initGUI(){

    m_TestCaseManager = TestCaseManager.INSTANCE;
    m_CombinationManager = CombinationManager.INSTANCE;

    cmGridModel = new CMGridModel(0,0);
    this.setModel(cmGridModel);
    spanModel = new CMSpanModel(cmGridModel);
    this.setSpanModel(spanModel);
    DefaultStyleModel styleModel = (DefaultStyleModel) this.getStyleModel();
	styleModel.setRenderer(CMCellHeaderTestCase.class,m_ColorRenderer);
	styleModel.setRenderer(CMCellEmpty.class,m_ColorRenderer);
	styleModel.setRenderer(CMCellEquivalenceClassInTestCase.class, cmImageRenderer);



////////////////// Style ///////////////////////////////////////////////////////
    this.setOpaque(false);
	this.setColumnResizable(true);
    this.setAutoResizeMode(RulerConstants.HORIZONTAL);
    this.setSelectionCellBorder(BorderFactory.createLineBorder(Color.orange,2));
	this.setFocusHighlightBorder(BorderFactory.createLineBorder(Color.orange,2));
    this.setSelectionBackgroundColor(SystemColor.activeCaptionText);
    this.setSelectionForegroundColor(Color.black);
    this.setSelectionUnit(com.eliad.swing.JSmartGrid.UNIT_CELL);
    this.setSelectionPolicy(com.eliad.swing.JSmartGrid.POLICY_SINGLE);
    this.setGridColor(new Color(196,194,196));

///////////////////////////////////////////////////////////////////////////////
    this.setAlignmentY((float) 0.5);
    this.setAlignmentX((float) 0.5);
    this.addFocusListener(new FocusAdapter(){
    	public void focusGained(FocusEvent e) {
    
    	}

    });
    this.addGridListener(new com.eliad.swing.GridAdapter() {
      public void gridMouseMoved(GridEvent e) {
      
      }
      public void gridMouseClicked(GridEvent e) {

        eventGridMouseClicked(e);
      }



      public void gridMouseReleased(GridEvent e){
        eventGridMouseReleased(e);
      }
    });

    this.addGridEditingListener(new com.eliad.swing.GridEditingListener() {
      public void editingStarted(GridEditingEvent e) {
        eventEditingStarted(e);
      }
      public void editingStopped(GridEditingEvent e) {
        eventEditingStopped(e);
      }
      public void editingCanceled(GridEditingEvent e) {
        eventEditingCanceled(e);
      }
    });

 }
//fcastro_20092004_begin
    public void setDefaultSelectionMode(boolean value){
        this.defaultSelectionMode =value;
    }


    public void changeSelection(int row, int column, boolean toogle, boolean extent) {
    	super.changeSelection(row,column,toogle,extent);

		if(defaultSelectionMode){
            defaultSelectionMode = false;
        }
        else{
        if(row< this.getRowCount() && row >=0 && column < this.getColumnCount() && column >=0){

    		this.setSelectionCellBorder(BorderFactory.createEmptyBorder());
            this.setFocusHighlightBorder(BorderFactory.createLineBorder(Color.orange,2));
        }
        }
		this.repaint();
    }
    public boolean isTestCaseHeaderView(int row, int column){
        Object obj = cmGridModel.getCellObjectAt(row,column);
        if(obj !=null && (obj instanceof CMCellHeaderTestCase)){
            return true;
        }
        return false;

    }
    //fcastro_20092004_end


    public JViewport getViewport1() {
      return getViewport();    // getViewport is not public in JSmartGrid
    }

    //fcastro_13092004_begin
    public boolean isDeletePossible(){
    int row =this.getSelectionModel().getLeadRow();
    int column = this.getSelectionModel().getLeadColumn();
    Object obj = this.cmGridModel.getCellObjectAt(row,column);
    if(obj!=null){
    if(obj instanceof CMCellHeaderTestCase){

            return true;

    }
    }
    return false;
 }
    //fcastro_13092004_end

    public void selectTestCaseView(int p_Index) {
		if( p_Index < this.getColumnCount()) {
		  changeSelection(0, p_Index, false, false);
			//m_CMElementAndTestCaseViews.getM_CMTestCaseStructureView().getM_CMDescriptionTestCaseViews().selectionChangedInGridView(p_Index);//fcastro_25082004
            //updateCommandos(null);//fcastro_26082004

		}
    }


    //fcastro_25082004_begin

    public void selectionChangedInDescriptionView(int selectedRow){
        if(this.m_Structure!=null){
            if(selectedRow>=0&&selectedRow<this.m_Structure.getTestCases().size()){
                if(selectedRow!=this.getSelectionModel().getLeadColumn()){
        			this.changeSelection(0,selectedRow,false,false);
                }

        	}
        }
    }

    public void updateCommandos(GridEvent e){
        int row = -1;
        int column = -1;
        if(m_Structure.getTestCases().size()>0){
        	CMAction.TESTCASE_ASSIGN_COMBINATION.setEnabled(true);
        	CMAction.TESTCASE_ASSIGN_EQUIVALENCECLASS.setEnabled(true);
        	CMAction.TESTCASE_ASSIGN_ERRORS.setEnabled(true);
        	CMAction.TESTCASE_ASSIGN_STDCOMBINATION.setEnabled(true);
        	CMAction.TESTCASE_CANCEL_ASSIGN_EQUIVALENCECLASS.setEnabled(true);
        	CMAction.TESTCASE_DELETE.setEnabled(true);
        	CMAction.TESTCASE_DELETE_ALL.setEnabled(true);
            CMAction.TESTCASE_REPORT_COMPUWARE.setEnabled(true);
            CMAction.TESTCASE_EDIT.setEnabled(true);
            CMAction.TESTCASE_REPORT_CSV.setEnabled(true);
            CMAction.TESTCASE_REPORT_LIST2.setEnabled(true);
            CMAction.TESTCASE_GENERATE.setEnabled(true);
            CMAction.TESTCASE_REPORT_LIST1.setEnabled(true);

            if(e!=null){
            row = e.getRow();
            column = e.getColumn();


        }
        else{
			row = getSelectionModel().getLeadRow();
            column = getSelectionModel().getLeadColumn();
        }
        if(row>=0&&column>=0){
            Object obj = cmGridModel.getCellObjectAt(row,column);
            if(obj!=null){

				if(obj instanceof CMCellHeaderTestCase){
					CMAction.TESTCASE_ASSIGN_COMBINATION.setEnabled(false);
		        	CMAction.TESTCASE_ASSIGN_EQUIVALENCECLASS.setEnabled(false);
		        	CMAction.TESTCASE_CANCEL_ASSIGN_EQUIVALENCECLASS.setEnabled(false);


                }
                else if(obj instanceof CMCellEmpty){
                	CMAction.TESTCASE_ASSIGN_COMBINATION.setEnabled(false);
		        	CMAction.TESTCASE_ASSIGN_EQUIVALENCECLASS.setEnabled(false);
		        	CMAction.TESTCASE_CANCEL_ASSIGN_EQUIVALENCECLASS.setEnabled(false);
		        	CMAction.TESTCASE_ASSIGN_ERRORS.setEnabled(false);
		        	CMAction.TESTCASE_ASSIGN_STDCOMBINATION.setEnabled(false);
		        	CMAction.TESTCASE_DELETE.setEnabled(false);
		        	CMAction.TESTCASE_EDIT.setEnabled(false);

                }


            	this.getM_CMElementAndTestCaseViews().getM_CMTestCaseStructureView().getM_CMDescriptionTestCaseViews().setSelectionMessageReceived(true);
				this.getM_CMElementAndTestCaseViews().getM_CMTestCaseStructureView().getM_CMDescriptionTestCaseViews().selectionChangedInGridView(column);


        }
        }
        }
        else{
        	CMAction.TESTCASE_ASSIGN_COMBINATION.setEnabled(false);

        	CMAction.TESTCASE_ASSIGN_EQUIVALENCECLASS.setEnabled(false);

        	CMAction.TESTCASE_ASSIGN_ERRORS.setEnabled(false);

            CMAction.TESTCASE_ASSIGN_STDCOMBINATION.setEnabled(false);
            CMAction.TESTCASE_CANCEL_ASSIGN_EQUIVALENCECLASS.setEnabled(false);
            CMAction.TESTCASE_DELETE.setEnabled(false);
            CMAction.TESTCASE_DELETE_ALL.setEnabled(false);
            CMAction.TESTCASE_REPORT_COMPUWARE.setEnabled(true);

        	CMAction.TESTCASE_EDIT.setEnabled(false);

            CMAction.TESTCASE_REPORT_CSV.setEnabled(false);
            CMAction.TESTCASE_REPORT_LIST2.setEnabled(false);

        	CMAction.TESTCASE_GENERATE.setEnabled(true);

            CMAction.TESTCASE_REPORT_LIST1.setEnabled(false);

        }


    }
    //fcastro_25082004_end
    //fcastro_02092004_begin
    public void rangeChanged(GridSelectionEvent e){
        super.rangeChanged(e);
        if(!e.isAdjusting()){
            int selectedRow = this.getFirstSelectedRow();
            int selectedColumn = this.getFirstSelectedColumn();
            if(this.isSelectionMessagedReceived()){
                this.setSelectionMessagedReceived(false);
            }
            else{
				this.getM_CMElementAndTestCaseViews().getM_CMTestCaseStructureView().getM_CMDescriptionTestCaseViews().setSelectionMessageReceived(true);
				this.getM_CMElementAndTestCaseViews().getM_CMTestCaseStructureView().getM_CMDescriptionTestCaseViews().selectionChangedInGridView(selectedColumn);
            }
            if(selectedRow>=0&&selectedColumn>=0){
            Object obj = cmGridModel.getCellObjectAt(selectedRow,selectedColumn);
            if(obj!=null){

				if(obj instanceof CMCellHeaderTestCase){
					CMAction.TESTCASE_ASSIGN_COMBINATION.setEnabled(false);
		        	CMAction.TESTCASE_ASSIGN_EQUIVALENCECLASS.setEnabled(false);
		        	CMAction.TESTCASE_ASSIGN_ERRORS.setEnabled(true);
		        	CMAction.TESTCASE_ASSIGN_STDCOMBINATION.setEnabled(true);
		        	CMAction.TESTCASE_CANCEL_ASSIGN_EQUIVALENCECLASS.setEnabled(false);
		        	CMAction.TESTCASE_DELETE.setEnabled(true);
		        	CMAction.TESTCASE_DELETE_ALL.setEnabled(true);
		        	CMAction.TESTCASE_REPORT_COMPUWARE.setEnabled(true);
		        	CMAction.TESTCASE_EDIT.setEnabled(true);
		        	CMAction.TESTCASE_REPORT_CSV.setEnabled(true);
		        	CMAction.TESTCASE_REPORT_LIST2.setEnabled(true);
		        	CMAction.TESTCASE_GENERATE.setEnabled(true);
		        	CMAction.TESTCASE_REPORT_LIST1.setEnabled(true);
                }
                else if(obj instanceof CMCellEmpty){
                	CMAction.TESTCASE_ASSIGN_COMBINATION.setEnabled(false);
                	CMAction.TESTCASE_ASSIGN_EQUIVALENCECLASS.setEnabled(false);
                	CMAction.TESTCASE_ASSIGN_ERRORS.setEnabled(false);
                	CMAction.TESTCASE_ASSIGN_STDCOMBINATION.setEnabled(false);
                	CMAction.TESTCASE_CANCEL_ASSIGN_EQUIVALENCECLASS.setEnabled(false);
                	CMAction.TESTCASE_DELETE.setEnabled(false);
                	CMAction.TESTCASE_DELETE_ALL.setEnabled(false);
                	CMAction.TESTCASE_REPORT_COMPUWARE.setEnabled(true);
                	CMAction.TESTCASE_EDIT.setEnabled(false);
                	CMAction.TESTCASE_REPORT_CSV.setEnabled(true);
                	CMAction.TESTCASE_REPORT_LIST2.setEnabled(true);
                	CMAction.TESTCASE_GENERATE.setEnabled(true);
                	CMAction.TESTCASE_REPORT_LIST1.setEnabled(true);

                }
                else if(obj instanceof CMCellEquivalenceClassInTestCase ){
                	CMAction.TESTCASE_ASSIGN_COMBINATION.setEnabled(true);
                	CMAction.TESTCASE_ASSIGN_EQUIVALENCECLASS.setEnabled(true);
                	CMAction.TESTCASE_ASSIGN_ERRORS.setEnabled(true);
                	CMAction.TESTCASE_ASSIGN_STDCOMBINATION.setEnabled(true);
                	CMAction.TESTCASE_CANCEL_ASSIGN_EQUIVALENCECLASS.setEnabled(true);
                	CMAction.TESTCASE_DELETE.setEnabled(true);
                	CMAction.TESTCASE_DELETE_ALL.setEnabled(true);
                	CMAction.TESTCASE_REPORT_COMPUWARE.setEnabled(true);
                	CMAction.TESTCASE_EDIT.setEnabled(true);
                	CMAction.TESTCASE_REPORT_CSV.setEnabled(true);
                	CMAction.TESTCASE_REPORT_LIST2.setEnabled(true);
                	CMAction.TESTCASE_GENERATE.setEnabled(true);
                	CMAction.TESTCASE_REPORT_LIST1.setEnabled(true);
                }

        	}
        }
        }

    }
    //fcastro_02092004_end

	//fcastro_02092004_begin
	public void eventGridMouseReleased(GridEvent e){
        updateCommandos(e);
        repaint();
      //  changeSelection(e.getRow(),e.getColumn(),false,false);
    }
    

 public Workspace2 getSelectedWorkspace() {
   DefaultMutableTreeNode node = cmFrame.getTreeWorkspaceView().getSelectedNode();
   DefaultMutableTreeNode parentNode;
   DefaultMutableTreeNode parentOfParentNode;
   DefaultMutableTreeNode targetNode;
   if( node != null) {
     parentNode = (DefaultMutableTreeNode) node.getParent();
     if( parentNode != null) {
       parentOfParentNode = (DefaultMutableTreeNode) parentNode.getParent();
       if( parentOfParentNode != null) {
	     targetNode = (DefaultMutableTreeNode) parentOfParentNode.getParent();
		 if( targetNode != null) {
		   Object nodeInfo = targetNode.getUserObject();
		   if( nodeInfo != null) {
			 if( nodeInfo instanceof CMWorkspaceNode) {
			   CMWorkspaceNode temp = (CMWorkspaceNode) nodeInfo;
			   return temp.getM_Workspace();
			 }
		   }
		 }
       }
     }
   }
   return null;
 }

 public void setM_Structure(Structure p_Structure) {
    if (m_Structure != null)
		  removeModelListener(m_Structure);
    m_Structure = p_Structure;
	addModelListener(m_Structure);
    deleteAllTestCaseViews();
    addTestCaseViews(m_Structure);
    selectTestCaseView(0);
 }
 private void addModelListener(Structure structure2) {
	  structure2.addModelListener(this);
		for (Element element : structure2.getElements())
			element.addModelListener(this);

}


private void removeModelListener(Structure structure2) {
	// TODO Auto-generated method stub
	structure2.removeModelListener(this);
	for (Element element : structure2.getElements())
		element.removeModelListener(this);
}
 public Structure getM_Structure() {
    return m_Structure;
 }

 public void setWidthOfLastTestCaseView(){
    int numOfColumns = this.getColumnCount();
    int index = numOfColumns - 1;
    this.setWidthOfTestCaseViewAt(index);
 }

 public void setWidthOfTestCaseViewAt(int p_index){
   this.setColumnWidth(p_index,11*6);
 }



  void deleteTestCaseView(int index,int count) {
    cmGridModel.removeColumns(index,count);
    lnkCMElementViews.update();
  }



  public void deleteAllTestCaseViews() {
    int numOfColumns = cmGridModel.getColumnCount();
    int numOfTestCaseViews = numOfColumns;
    deleteTestCaseView(0,numOfTestCaseViews);
    lnkCMElementViews.update();
  }


	void insertTestCaseView(int i, Vector testCaseView) {
    cmGridModel.insertColumn(i,testCaseView);
    if( i >= 0) {
      changeSelection(0,i, false, false);

    }
    //updateCommandos(null);//fcastro_26082004

  }

  void addTestCaseView(Vector testCaseView) {
    cmGridModel.addColumn(testCaseView);
    int lastColumn = this.getColumnCount() - 1;
    if( lastColumn >= 0) {
      changeSelection(0, lastColumn, false, false);
    }
    //updateCommandos(null);//fcastro_26082004
  }

  int getNumberOfRowsInElementTestCaseViews() {
    return this.getLnkCMElementViews().getRowCount();
  }

  public void addTestCaseViews(Structure p_Structure) {
    int numOfTestCaseViews = p_Structure.getLnkTestCases().size();
    cmGridModel.setRowCount(getNumberOfRowsInElementTestCaseViews());

    for( int i = 0; i < numOfTestCaseViews; i++) {
      TestCase testCase = (TestCase) p_Structure.getLnkTestCases().elementAt(i);
      Vector testCaseView = createTestCaseView(testCase, p_Structure.getElements(CMUserOrderBean.COMPARATOR));
      addTestCaseView(testCaseView);
      setWidthOfLastTestCaseView();
    }
  }


  public void update() {
    deleteAllTestCaseViews();
    addTestCaseViews(m_Structure);
    updateCommandos(null);//fcastro_26082004
  }


	public boolean isElementInDependency(Element element, Dependency dependency) {
		if( dependency.getLnkElements().contains(element) ) {
      return true;
    }
    else {
      return false;
    }
  }

  public boolean isElementInAnyDependency(Element element, Vector dependencies) {
    int numOfDependencies = dependencies.size();
    for( int i = 0; i < numOfDependencies; i++) {
			Dependency dependency = (Dependency) dependencies.elementAt(i);
      if( isElementInDependency(element,dependency) ) {
        return true;
      }
    }
    return false;
  }


  Vector createTestCaseView(TestCase testCase, List<Element> testCaseElements) {
    Vector testCaseView = new Vector(0);
    int numOfTestCaseElements = testCaseElements.size();

    for( int i = 0; i < numOfTestCaseElements; i++) {
    	Element element = (Element) testCaseElements.get(i);
    	if (element.getEquivalenceClasses().size() == 0)
    		continue;
	  testCaseView.addElement(new CMCellHeaderTestCase(testCase) );
	  testCaseView.addElement(new CMCellEmpty());
	  testCaseView.addElement(new CMCellEmpty());

	  int numOfEquivalenceClasses = element.getEquivalenceClasses().size();
	  for( int j = 0; j < numOfEquivalenceClasses; j++) {
	    EquivalenceClass equivalenceClass = (EquivalenceClass) element.getEquivalenceClasses().get(j);
       
            testCaseView.addElement(new CMCellEquivalenceClassInTestCase(equivalenceClass,testCase));
       
	  }
    }
    return testCaseView;
  }

  public void save() {
    //structureManager.saveStructure(m_Structure);
  }

  public TestCase getSelectedTestCase() {
    int selectedColumn = this.getSelectionModel().getLeadColumn();
		int selectedRow = this.getSelectionModel().getLeadRow();
    if( selectedRow >= 0 && selectedColumn >= 0) {
			Object obj = cmGridModel.getCellObjectAt(selectedRow, selectedColumn);
      if( obj != null) {
        if( obj instanceof CMCellHeaderTestCase  ||
            obj instanceof CMCellEquivalenceClassInTestCase) {
 					TestCase s = (TestCase) ((CMBaseCell)obj).getModel();
          return s;
        }
        else{
          return null;
        }
      }
      else {
        return null;
			}
    }
    else {
      return null;
    }
  }

  public int getIndexOfNewTestCaseView(Structure p_Structure, TestCase p_TestCase) {
    TestCaseGroup positive = (TestCaseGroup) p_Structure.getM_TestCaseGroups().get(BusinessRules.TEST_CASE_GROUP_POSITION_OF_POSITIVES);
    TestCaseGroup negative = (TestCaseGroup) p_Structure.getM_TestCaseGroups().get(BusinessRules.TEST_CASE_GROUP_POSITION_OF_NEGATIVES);
    TestCaseGroup faulty = (TestCaseGroup) p_Structure.getM_TestCaseGroups().get(BusinessRules.TEST_CASE_GROUP_POSITION_OF_FAULTIES);
    TestCaseGroup irrelevant = (TestCaseGroup) p_Structure.getM_TestCaseGroups().get(BusinessRules.TEST_CASE_GROUP_POSITION_OF_IRRELEVANTS);
    int positionInViews = 0;
    int index = 0;
    if( p_TestCase.getTestCaseGroup() == positive) {
	  positionInViews = getNumberOfTestCaseViews(positive);
    }
    else if( p_TestCase.getTestCaseGroup() == negative){
      positionInViews = getNumberOfTestCaseViews(positive) + getNumberOfTestCaseViews(negative);
    }
    else if( p_TestCase.getTestCaseGroup() == faulty){
      positionInViews = getNumberOfTestCaseViews(positive) + getNumberOfTestCaseViews(negative) +
                            getNumberOfTestCaseViews(faulty);
    }
    else if( p_TestCase.getTestCaseGroup() == irrelevant) {
      positionInViews = getNumberOfTestCaseViews(positive) + getNumberOfTestCaseViews(negative) +
                            getNumberOfTestCaseViews(faulty) + getNumberOfTestCaseViews(irrelevant);
    }
    Vector testCaseView = createTestCaseView(p_TestCase,p_Structure.getLnkElements());

    if( isThereAViewAt(positionInViews)) {
          index = positionInViews;
    }
    else {
		  index =  getColumnCount();
    }
    return index;
  }



  public void changeTestCaseRiskLevel(int p_Index, int p_RiskLevel, TestCase p_TestCase){
	  p_TestCase.setRiskLevel(p_RiskLevel);
  }



public void changeTestCaseDescriptionEditable(int p_Index, String p_NewDescription, TestCase p_TestCase) {
        p_TestCase.setDescriptionEditable(p_NewDescription);
        //fcastro_26082004_begin
        int indexInStructureVector = m_Structure.getLnkTestCases().indexOf(p_TestCase);
        this.cmGridModel.fireGridColumnsChanged(/*p_Index*/indexInStructureVector, 1);
        this.m_CMElementAndTestCaseViews.getM_CMTestCaseStructureView().getM_CMDescriptionTestCaseViews().updateChangedRow(/*p_Index*/ indexInStructureVector);//integration_fcastro_17082004
        //fcastro_26082004_end
    }


  public EquivalenceClass getSelectedEquivalenceClass() {
    int row = this.getSelectionModel().getLeadRow();
    int column = this.getSelectionModel().getLeadColumn();
		EquivalenceClass equivalenceClass = null;
    if( row >= 0 && column >= 0) {
			Object obj = this.cmGridModel.getCellObjectAt(row,column);
			if( obj != null) {
				if( obj instanceof CMCellEquivalenceClassInTestCase)  {
					equivalenceClass = ((CMCellEquivalenceClassInTestCase)obj).getEquivalenceClass();
					return equivalenceClass;
				}
    
				else {
					return null;
				}
			}
			else{
				return null;
			}
		}
    else {
      return null;
    }
  }

  public boolean isTestCaseHeaderSelected(){
    int selectedColumn = this.getSelectionModel().getLeadColumn();
	int selectedRow = this.getSelectionModel().getLeadRow();
    if( selectedRow >= 0 && selectedColumn >= 0) {
	  Object obj = cmGridModel.getCellObjectAt(selectedRow, selectedColumn);
      if( obj != null) {
        if( obj instanceof CMCellHeaderTestCase) {
          return true;
        }
        else{
          return false;
        }
      }
      else {
	    return false;
      }
    }
    else {
        return false;
    }
  }




  public TestCase getTestCaseFromTestCaseViewHeader(int p_Column){
    Object obj = cmGridModel.getCellObjectAt(0, p_Column);
    if( obj != null) {
      if( obj instanceof CMCellHeaderTestCase){
        return (TestCase) ((CMCellHeaderTestCase)obj).getModel();
      }
      else{
		return null;
      }
    }
    else {
      return null;
    }
  }


  public int getNumberOfTestCaseViews(TestCaseGroup p_Group) {
    int numOfTestCaseViews = this.getColumnCount();
    TestCase testCase;
    int counter = 0;
		for( int i = 0; i < numOfTestCaseViews; i++) {
		  testCase = (TestCase) this.getTestCaseFromTestCaseViewHeader(i);
          if( testCase != null) {
			  if( testCase.getTestCaseGroup() == p_Group){
				counter++;
			  }
          }
		}
	return counter;
  }


  public boolean isThereAViewAt(int p_Index) {
    if ( (getColumnCount() > p_Index) && (p_Index >= 0)){
      return true;
    }
    else{
      return false;
    }
  }


  void eventEditingCanceled(GridEditingEvent e) {
  }

  void eventEditingStopped(GridEditingEvent e) {
      int row = e.getRow();
      int column = e.getColumn();
      Object value = null;

      if( editingObject instanceof CMCellElementName) {
			Element element = ((CMCellElement)editingObject).getElement();
            value = cmGridModel.getValueAt(row,column);
			element.setName((String)value);
			cmGridModel.setValueAt(new CMCellElementName(this,element),row,column);
		  }

      //My add
      /*else if( editingObject instanceof CMCellElementGuiObject) {
			Element element = ((CMCellElement)editingObject).getElement();
            value = cmGridModel.getValueAt(row,column);
			element.setGuiObject((String)value);
			cmGridModel.setValueAt(new CMCellElementGuiObject(element),row,column);
		  }
	  //my adds end */
      else if( editingObject instanceof CMCellElementDescription) {
			Element element = ((CMCellElement)editingObject).getElement();
            value = cmGridModel.getValueAt(row,column);
			element.setDescription((String) value);
			cmGridModel.setValueAt(new CMCellElementDescription(this,element),row,column);
		  }


      else if( editingObject instanceof CMCellEquivalenceClassValue) {
			EquivalenceClass equivalenceClass = ((CMCellEquivalenceClass)editingObject).getEquivalenceClass();
            value = cmGridModel.getValueAt(row,column);
			equivalenceClass.setValue((String)value);
			cmGridModel.setValueAt(new CMCellEquivalenceClassValue(this,equivalenceClass),row,column);
		  }
      else if( editingObject instanceof CMCellEquivalenceClassState) {
        Object obj = cmGridModel.getCellObjectAt(row,column-1);
        EquivalenceClass equivalenceClass = ((CMCellEquivalenceClass)obj).getEquivalenceClass();
        CMCellEquivalenceClassState selectedItem = (CMCellEquivalenceClassState) cmGridModel.getCellObjectAt(row,column);
        equivalenceClass.setState(selectedItem.intValue());
      }
  }
  void eventEditingStarted(GridEditingEvent e) {
      int row = e.getRow();
      int column = e.getColumn();
      editingObject = cmGridModel.getCellObjectAt(row,column);
  }


 

  void eventGridMouseClicked(GridEvent e) {
    MouseEvent mouseEvent = (MouseEvent) e.getSourceEvent();
    int row = e.getRow();
    int column = e.getColumn();
this.changeSelection(row,column,false,false);
    Object obj = null;
		obj = cmGridModel.getCellObjectAt(row,column);
	if( mouseEvent.getClickCount() == 2) {
		 CMApplication.frame.setCursor(new Cursor(Cursor.WAIT_CURSOR));
		if( obj == null) return;
        
        if( obj instanceof CMCellHeaderTestCase){    
            CMAction.TESTCASE_EDIT.getAction().actionPerformed(null);
            return;
        }

        if( obj instanceof CMCellEquivalenceClassInTestCase) {
                      if (((CMEnabledAction)CMAction.TESTCASE_CANCEL_ASSIGN_EQUIVALENCECLASS.getAction()).calculateEnabled()){
                    	  	CMAction.TESTCASE_CANCEL_ASSIGN_EQUIVALENCECLASS.getAction().actionPerformed(null);
                    	  	return;
                      }
                      if (((CMEnabledAction)CMAction.TESTCASE_ASSIGN_EQUIVALENCECLASS.getAction()).calculateEnabled()){
                  	  	CMAction.TESTCASE_ASSIGN_EQUIVALENCECLASS.getAction().actionPerformed(null);
                  	  	return;
                    }
					
        }
      
      
    }

   else if(e.getSourceEvent().getModifiers() == Event.META_MASK) {
	   mouseEvent = (MouseEvent) e.getSourceEvent();
       this.cmFrame.getJPopupMenuTestCases().show(this, mouseEvent.getX(), mouseEvent.getY());
    }
	this.getM_CMElementAndTestCaseViews().getM_CMTestCaseStructureView().getM_CMDescriptionTestCaseViews().getStyleModel().updateUI();
    this.repaint();
    CMApplication.frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
  }


  

  public CMElementViews getLnkCMElementViews(){
    return lnkCMElementViews;
  }

  public void setLnkCMElementViews(CMElementViews lnkCMElementViews){
          this.lnkCMElementViews = lnkCMElementViews;
      }

  public CMElementAndTestCaseViews getM_CMElementAndTestCaseViews(){
          return m_CMElementAndTestCaseViews;
      }

  public void setM_CMElementAndTestCaseViews(CMElementAndTestCaseViews m_CMElementAndTestCaseViews){
          this.m_CMElementAndTestCaseViews = m_CMElementAndTestCaseViews;
      }

  public boolean isSelectionMessagedReceived(){
        return selectionMessagedReceived;
    }

  public void setSelectionMessagedReceived(boolean selectionMessagedReceived){
          this.selectionMessagedReceived = selectionMessagedReceived;
      }
  //grueda14092004_begin
  String findAbsoluteReportsPath(){
    ProjectReference projectReference = cmFrame.getTreeWorkspaceView().getCurrentProjectReference();
    TestObjectReference testObjectReference = cmFrame.getTreeWorkspaceView().getCurrentTestObjectReference();
    TestObject testObject =cmFrame.getTreeWorkspaceView().getCurrentTestObject();
    //grueda06112004_begin
	if( cmFrame.getCmApplication().getSessionManager().getM_WorkspaceManager().isTestObjectCheckedOutLocalByTheSameUser(testObject, SessionManager.getCurrentSession()) ) {
	  projectReference = projectReference.getM_LocalProjectReference();
	}
    //grueda06112004_end
    String absoluteReportsPath = cmFrame.getCmApplication().getSessionManager().getM_WorkspaceManager().buildAbsoluteReportsPath(projectReference, testObjectReference);
    return absoluteReportsPath;
  }
  //grueda14092004_end
  //grueda1111092004_begin
  public CMGridModel getCMGridModel() {
    return this.cmGridModel;
  }
  //grueda1111092004_end

 //////////////////////// GridModel ///////////////////////////////////////////
  public class CMGridModel extends GenericGridModel {
    public CMGridModel(int numRows, int numColumns) {
      super(numRows, numColumns);
    }
    public boolean isCellEditable(int row, int column) {
      return false;
    }
    public Object getCellObjectAt(int row, int column) {
      if(this.getRowCount() > row && this.getColumnCount() > column) {
        return super.getValueAt(row,column);
      }
      else {
        return null;
      }
    }
    public void setValueAt(Object obj,int row, int column) {
        if ((row > -1) && (column > -1) && (getRowCount() > row) && (getColumnCount() > column)) {
            super.setValueAt(obj,row,column);
        }
    }

    
      public Vector getCombinations(TestCase testCase, EquivalenceClass equivalenceClass) {
        Vector combinations = testCase.getLnkCombinations();
        Vector showableCombinations = new Vector(0);
        int numOfCombinations = combinations.size();
        for( int i = 0; i < numOfCombinations; i++) {
          Combination combination = (Combination) combinations.elementAt(i);
          if( !(combination instanceof StdCombination) ) {
            if( combination.contains(equivalenceClass)) {
              showableCombinations.addElement(combination);
            }
          }
        }
        return showableCombinations;
  }

  public Vector getStdCombinations(TestCase testCase, EquivalenceClass equivalenceClass) {
        Vector combinations = testCase.getLnkCombinations();
        Vector showableCombinations = new Vector(0);
        int numOfCombinations = combinations.size();
        for( int i = 0; i < numOfCombinations; i++) {
          Combination combination = (Combination) combinations.elementAt(i);

          if( combination instanceof StdCombination) {
            if( combination.contains(equivalenceClass)) {
              showableCombinations.addElement(combination);
            }
          }
        }
        return showableCombinations;
  }

  public boolean AreEquivalenceClassesInTestCaseWithinTheSameElement(TestCase testCase, EquivalenceClass equivalenceClass) {
    Vector equivalenceClasses = testCase.getLnkEquivalenceClasses();
    int numOfEquivalenceClasses = testCase.getLnkEquivalenceClasses().size();
    Element element = equivalenceClass.getLnkElement();
    EquivalenceClass ec;
    for( int i = 0; i < numOfEquivalenceClasses; i++) {
      ec = (EquivalenceClass) testCase.getLnkEquivalenceClasses().elementAt(i);
      if( ec.getLnkElement() == element) {
        return true;
      }
    }
    return false;
  }

  public boolean AreCombinationsInTestCaseWithinTheSameElement(TestCase testCase, EquivalenceClass equivalenceClass) {
    Element element = equivalenceClass.getLnkElement();
    int numOfCombinations = testCase.getLnkCombinations().size();
    Combination combination;
	for( int i = 0; i < numOfCombinations; i++) {
      combination = (Combination) testCase.getLnkCombinations().elementAt(i);
      if( ! (combination instanceof StdCombination)) {
        if( isCombinationInElement(combination, element)) {
          return true;
        }
      }
    }
    return false;
  }

  public boolean isCombinationInElement(Combination p_Combination, Element p_Element) {
    int numOfEquivalenceClasses = p_Combination.getEquivalenceClassesRecursiv().size();
	EquivalenceClass equivalenceClass = null;

    for( int i = 0; i < numOfEquivalenceClasses; i++) {
      equivalenceClass = (EquivalenceClass) p_Combination.getEquivalenceClassesRecursiv().get(i);
      if( equivalenceClass.getLnkElement() == p_Element) {
        return true;
      }
    }
    return false;
  }

  }

  //////////////////////// SpanModel ///////////////////////////////////////////
  public class CMSpanModel extends AbstractDirectSpanModel {
    CMTestCaseViews.CMGridModel model = null;
    public CMSpanModel(CMTestCaseViews.CMGridModel m) {
      model = m;
    }
    public ExtentCell getSpanOver(final int row, final int column) {
        Object obj = model.getCellObjectAt(row,column);
        if( obj == null) {
          return null;
        }
        if ( obj instanceof CMCellHeaderElementDescription || obj instanceof CMCellElementDescription ||
             obj instanceof CMCellHeaderElementDescriptionEmpty || obj instanceof CMCellElementDescriptionEmpty) {
						return new ExtentCell() {
							public int getRow() {
								return row;
							}
							public int getRowCount() {
								return 1;
							}
							public int getColumn() {
								return 1;
							}
							public int getColumnCount() {
								return 3;
							}
							public Object getIdentifier() {
								return null;
							}
						};
        }
        else {
          return null;
        }
    }
    public boolean isEmpty() {
        return false;
    }
  }
  


public void handleCMModelChange(CMModelEvent evt) {
	if (evt.getSource() instanceof Structure)
	{
		if (evt.getChangedField()== CMField.ELEMENTS) {
			this.removeModelListener(getM_Structure());
			this.addModelListener(getM_Structure());
			update();
		}
		if (evt.getChangedField() == CMField.TESTCASES)
			update();
	}
	if (evt.getSource() instanceof Element)
	{
		if (evt.getChangedField() == CMField.EQUIVALENCE_CLASSES)
		{
			update();
		}
	}

}

@Override
protected HashMap<Class, Component> getCellClasses() {
	// TODO Auto-generated method stub
	return null;
}

}
