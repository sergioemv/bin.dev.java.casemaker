    /* Developed by BUSINESS SOFTWARE INNOVATIONS.  .. Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved..*/

package bi.view.dependencycombinationviews;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Event;
import java.awt.SystemColor;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JViewport;

import model.CMField;
import model.Combination;
import model.Dependency;
import model.Element;
import model.EquivalenceClass;
import model.State;
import model.Structure;
import model.util.CMModelEvent;
import model.util.CMModelListener;
import model.util.CMStateBean;
import bi.controller.CombinationManager;
import bi.controller.DependencyManager;
import bi.controller.TestCaseManager;
import bi.view.actions.CMAction;
import bi.view.cells.CMCellCombination;
import bi.view.cells.CMCellDependencyName;
import bi.view.cells.CMCellEffectsInCombination;
import bi.view.cells.CMCellElementDescription;
import bi.view.cells.CMCellElementDescriptionEmpty;
import bi.view.cells.CMCellEmpty;
import bi.view.cells.CMCellEquivalenceClassInCombination;
import bi.view.cells.CMCellEquivalenceClassName;
import bi.view.cells.CMCellEquivalenceClassState;
import bi.view.cells.headers.CMCellHeaderCombination;
import bi.view.cells.headers.CMCellHeaderDependencyName;
import bi.view.cells.headers.CMCellHeaderEffectsInCombination;
import bi.view.cells.headers.CMCellHeaderElementDescription;
import bi.view.cells.headers.CMCellHeaderElementDescriptionEmpty;
import bi.view.cells.headers.CMCellHeaderElementName;
import bi.view.cells.headers.CMCellHeaderEquivalenceClassName;
import bi.view.cells.headers.CMCellHeaderEquivalenceClassState;
import bi.view.cells.headers.CMCellHeaderEquivalenceClassValue;
import bi.view.cells.renderers.CMCombinationCellRenderer;
import bi.view.cells.renderers.CMImageEquivalenceClassinCombinationRenderer;
import bi.view.grids.CMBaseJSmartGrid;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.mainframeviews.CMFrameView;
import bi.view.undomanagementviews.CMCompoundEdit;
import bi.view.undomanagementviews.undoviews.CMUndoMediator;

import com.eliad.model.AbstractDirectSpanModel;
import com.eliad.model.ExtentCell;
import com.eliad.model.GenericGridModel;
import com.eliad.model.GridSelectionEvent;
import com.eliad.model.defaults.DefaultStyleModel;
import com.eliad.swing.GridEditingEvent;
import com.eliad.swing.GridEvent;
import com.eliad.util.RulerConstants;

@SuppressWarnings("serial")
public class CMCombinationViews extends CMBaseJSmartGrid implements CMModelListener {

  private CMSpanModel  spanModel = null;
  private DefaultStyleModel styleModel = null;
  private CMGridModel  cmGridModel = null;
  private CombinationManager combinationManager = null;
  private DependencyManager  dependencyManager = null;
  private TestCaseManager    testCaseManager = null;
  private Dependency selectedDependency = null;
  private CMFrameView cmFrame = null;
  private Combination selectedCombination = null;
  private EquivalenceClass selectedEquivalenceClass = null;
  private Structure selectedStructure = null;
  private CMPanelDependencies cmPanelDependencies = null;
  private CMCombinationCellRenderer cmColorRenderer = new CMCombinationCellRenderer();
  private CMImageEquivalenceClassinCombinationRenderer cmImageRenderer = new CMImageEquivalenceClassinCombinationRenderer();
  private int selectedRow = 0;
  private int selectedColumn = 0;
    /**
     * @link aggregationByValue
     * @clientCardinality 1
     * @supplierCardinality 0..*
     * @directed
     */
    //private CMCombinationView lnkCMCombinationView;

  private FocusListener focusListener = new FocusAdapter(){
		public void focusLost(FocusEvent e) {
			//getM_CMStdCombinationStructureView().getM_CMElementAndStdCombinationViews().getLnkCMStdCombinationViews().repaint();
			repaint();
	   }
		public void focusGained(FocusEvent e) {
			cmPanelDependencies.getM_CMDependencyElementView().repaint();
		}
  };
    public CMCombinationViews(CMPanelDependencies cmPanelDependencies, CMFrameView cmFrame /*, CMPopupMenuCombinations p_cmPopupMenuCombinations*/) {
    		super();
			combinationManager = cmFrame.getCmApplication().getSessionManager().getCombinationManager();
			dependencyManager  = cmFrame.getCmApplication().getSessionManager().getDependencyManager();
            testCaseManager = cmFrame.getCmApplication().getSessionManager().getTestCaseManager();
            this.cmFrame = cmFrame;
            cmFrame.getKeyEventIntercepter().setM_CMCombinationViews(this);//fcastro_13092004
			this.cmPanelDependencies = cmPanelDependencies;
	 initGUI();
      this.setGridNavigationPolicy(new CMCombinationsGridNavPolicy(this));
    }

    public void initGUI() {
    	    this.addFocusListener(focusListener);
			cmGridModel = new CMGridModel(0,0);
			this.setModel(cmGridModel);
			spanModel = new CMSpanModel(cmGridModel);
			this.setSpanModel(spanModel);
			styleModel = new DefaultStyleModel();
			styleModel.setRenderer(CMCellHeaderCombination.class,cmColorRenderer);
			styleModel.setRenderer(CMCellEmpty.class,cmColorRenderer);
			styleModel.setRenderer(CMCellHeaderEffectsInCombination.class,cmColorRenderer);
			styleModel.setRenderer(CMCellEffectsInCombination.class,cmColorRenderer);

			styleModel.setEditor(CMCellEquivalenceClassInCombination.class, CMCellEquivalenceClassInCombination.defaultEditor);
			styleModel.setRenderer(CMCellEquivalenceClassInCombination.class, cmImageRenderer);

			this.setStyleModel(styleModel);

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
    this.setFocusable(true);
			this.addGridListener(new com.eliad.swing.GridAdapter() {
				public void gridMouseMoved(GridEvent e) {
					eventGridMouseMoved(e);
   				}
				public void gridMouseClicked(GridEvent e) {
					eventGridMouseClicked(e);
                    updateCommandos();
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



    public void rangeChanged(GridSelectionEvent e){
        super.rangeChanged(e);
        updateCommandos();
    }

	public void changeSelection(int row,int column,boolean toogle,boolean extend){
		super.changeSelection(row,column,toogle,extend);

		cmPanelDependencies.updateScrolls();
		cmPanelDependencies.getM_CMCombinationViews().repaint();
		this.repaint();


    }

    public boolean isCombinationHeaderCell(int row,int column){
        Object obj = cmGridModel.getCellObjectAt(row,column);
        if(obj!=null && obj instanceof CMCellHeaderCombination){
            return true;
        }
        else{
            return false;
        }
    }



    public JViewport getViewport1() {
      return getViewport();    // getViewport is not public in JSmartGrid
    }

 public void setM_Structure(Structure p_Structure){
   selectedStructure = p_Structure;

   this.deleteAllCombinationViews();
   updateCommandos();
 }
 public Structure getM_Structure(){
   return selectedStructure;
 }
 //fcastro_13092004_begin
 public boolean isDeletePossible(){
    int row =this.getSelectionModel().getLeadRow();
    int column = this.getSelectionModel().getLeadColumn();
    Object obj = this.cmGridModel.getCellObjectAt(row,column);
    if(obj!=null){
    if(obj instanceof CMCellHeaderCombination){
        Combination combination = ((CMCellHeaderCombination)obj).getCombination();
        if(combination.getOriginType().equals(Combination.Origin.MANUAL)){
            return true;
        }
        return false;
    }
    }
    return false;
 }
 //fcastro_13092004_end

 public void setWidthOfLastCombinationView(){
    int numOfColumns = this.getColumnCount();
    int index = numOfColumns - 1;
    this.setWidthOfCombinationViewAt(index);
 }

 public void setWidthOfCombinationViewAt(int p_index){
   this.setColumnWidth(p_index,51);
 }

  void eventGridMouseMoved(GridEvent e) {

  }

public void anchorChanged(GridSelectionEvent e){
  int selectedRow = e.getNewAnchorRow();
  int selectedColumn = e.getNewAnchorColumn();
  selectedCombination = getSelectedCombination(selectedRow,selectedColumn);
  selectedEquivalenceClass = getSelectedEquivalenceClass(selectedRow,selectedColumn);
  super.anchorChanged(e);
}

public EquivalenceClass getSelectedEquivalenceClass(int row, int column) {
    if( row >= 0 && column >= 0) {
			Object obj = this.cmGridModel.getCellObjectAt(row,column);
			if( obj != null) {
        EquivalenceClass equivalenceClass = null;
        if( obj instanceof CMCellEquivalenceClassInCombination) {
            equivalenceClass = ((CMCellEquivalenceClassInCombination)obj).getEquivalenceClass();
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

  public Combination getSelectedCombination(int row, int column) {
    if( row >= 0 && column >= 0) {
			Object obj = this.cmGridModel.getCellObjectAt(row,column);
			if( obj != null) {
        Combination combination = null;
				if( obj instanceof CMCellHeaderCombination  || obj instanceof CMCellEquivalenceClassInCombination ||
          obj instanceof CMCellHeaderEffectsInCombination || obj instanceof CMCellEffectsInCombination) {
					combination = ((CMCellCombination)obj).getCombination();
					return combination;
				}
        else if( obj instanceof CMCellEmpty) {
          combination = getSelectedCombination(0,column);
          return combination;
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

  void eventGridMouseClicked(GridEvent e) {
    MouseEvent mouseEvent = (MouseEvent) e.getSourceEvent();
    int row = e.getRow();
    int column = e.getColumn();
    setSelectedRow(row);
    setSelectedColumn(column);
		Object obj = cmGridModel.getCellObjectAt(row,column);




    if( mouseEvent.getClickCount() == 2) {
    	//put the grid in a disabled state and show the hourglass
    	this.setEnabled(false);
    	CMApplication.frame.setCursor(new Cursor(Cursor.WAIT_CURSOR));
	  if( obj != null) {
        //fcastro_02092004_begin
        if(obj instanceof CMCellHeaderCombination){
			CMAction.COMBINATION_EDIT.getAction().actionPerformed(null);
        }
        //fcastro_02092004_end
        if( obj instanceof CMCellEquivalenceClassInCombination) {
            selectedCombination = ((CMCellCombination)obj).getCombination();
            selectedEquivalenceClass = ((CMCellEquivalenceClassInCombination)obj).getEquivalenceClass();
            if( selectedCombination.getOriginType() == null) {
              selectedCombination.setOriginType(Combination.Origin.PERMUTATION);
            }
            if( selectedCombination.getOriginType().equals(Combination.Origin.MANUAL)) {
                if( selectedCombination.contains(selectedEquivalenceClass) ){
                   cancelEquivalenceClassAssignment(selectedEquivalenceClass, selectedCombination);
                   this.changeSelection(getSelectedRow(),getSelectedColumn(),false,false);//CC
                }
                else {
                  if( !combinationManager.isThereAnEquivalenceClassInTheCombinationThatBelongsToTheSameElement(selectedCombination, selectedEquivalenceClass) ) {
                    assignEquivalenceClassToCombination(selectedEquivalenceClass, selectedCombination);
            //        this.changeSelection(row,column,false,false);//CC
                    if( combinationManager.isCombinationComplete(selectedCombination, selectedDependency) ) {
                      if( combinationManager.isCombinationInDependency(selectedCombination, selectedDependency) ) {
				          JOptionPane.showMessageDialog(this.cmFrame,CMMessages.getString("INFO_COMBINATION_ALREADY_EXISTS")); //$NON-NLS-1$
                          cancelEquivalenceClassAssignment(selectedEquivalenceClass, selectedCombination);
                          this.changeSelection(getSelectedRow(),getSelectedColumn(),false,false);//CC
                      }
                    }
                  }
                }
            }
            else {
				if( selectedCombination.contains(selectedEquivalenceClass)) {
					CMAction.COMBINATION_UNMERGE.getAction().actionPerformed(null);
			//  changeSelection(row,column,false,false); fcastro_13092004
				}
				else {
				 CMAction.COMBINATION_MERGE.getAction().actionPerformed(null);
				}
            }
        }
        else if( obj instanceof CMCellEffectsInCombination) {
            selectedCombination = ((CMCellCombination)obj).getCombination();
            CMAction.COMBINATION_EDIT_EFFECTS.getAction().actionPerformed(null);
        }
      }
    }
    else if(e.getSourceEvent().getModifiers() == Event.META_MASK) {
        if( row >= 0 && column >= 0) {
					//this.clearSelection();
			this.changeSelection(row,column,false,false);
          obj = cmGridModel.getCellObjectAt(row,column);
          if( obj instanceof CMCellHeaderCombination  || obj instanceof CMCellHeaderEffectsInCombination ||
              obj instanceof CMCellEffectsInCombination) {
							mouseEvent = (MouseEvent) e.getSourceEvent();
				CMAction.COMBINATION_MERGE.setEnabled(false);
				CMAction.COMBINATION_UNMERGE.setEnabled(false);

               cmFrame.getJPopupMenuCombinations().show(this, mouseEvent.getX(), mouseEvent.getY());
							selectedCombination = ((CMCellCombination)obj).getCombination();
							this.revalidate();
          }
          if( obj instanceof CMCellEquivalenceClassInCombination) {
						mouseEvent = (MouseEvent) e.getSourceEvent();
				CMAction.COMBINATION_MERGE.setEnabled(false);
				CMAction.COMBINATION_UNMERGE.setEnabled(false);
               cmFrame.getJPopupMenuCombinations().show(this, mouseEvent.getX(), mouseEvent.getY());
            //  this.changeSelection(row,column,false,false);//CC
            selectedCombination = ((CMCellCombination)obj).getCombination();
            selectedEquivalenceClass = ((CMCellEquivalenceClassInCombination)obj).getEquivalenceClass();
          }
        }

    }
  //  this.repaint();
    this.setEnabled(true);
	CMApplication.frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
  }


  void eventEditingCanceled(GridEditingEvent e) {
  }

  void eventEditingStopped(GridEditingEvent e) {
      int row = e.getRow();
      int column = e.getColumn();
      setSelectedRow(row);
      setSelectedColumn(column);
  }
  void eventEditingStarted(GridEditingEvent e) {
      int row = e.getRow();
      int column = e.getColumn();
      setSelectedRow(row);
      setSelectedColumn(column);
  }

   public void setDependencyDefinitions(Dependency dependency) {
     this.selectedDependency = dependency;
     if (selectedDependency!=null)
    	 removeListenerFromAll(selectedDependency);

	 this.selectedDependency = dependency;
	 if (selectedDependency!=null)
		 addModelListenerToAll(selectedDependency);
     this.deleteAllCombinationViews();
     this.addAllCombinationViews(selectedDependency);
     clearSelection();//fcastro_19102004
     updateCommandos();
   }
   private void addModelListenerToAll(Dependency selectedDependency2) {
		  selectedDependency2.addModelListener(this);
			for (Element element : selectedDependency2.getElements()) {
				element.addModelListener(this);
				for (EquivalenceClass equivalenceClass : element.getEquivalenceClasses())
					equivalenceClass.addModelListener(this);
			}
	}
	private void removeListenerFromAll(Dependency selectedDependency2) {
		selectedDependency2.removeModelListener(this);
		for (Element element : selectedDependency2.getElements()) {
			element.removeModelListener(this);
			for (EquivalenceClass equivalenceClass : element.getEquivalenceClasses())
				equivalenceClass.removeModelListener(this);
			}
	}

  void deleteCombinationView(long index,int count) {
	  cmGridModel.removeColumns((int) index,count);
  }

  public void deleteAllCombinationViews() {
    int numOfColumns = cmGridModel.getColumnCount();
    int numOfCombinationViews = numOfColumns;
    deleteCombinationView(0,numOfCombinationViews);
  }


  private int derivePossibilitiesIndex(String[] p_Possibilities, String p_Selection){
  	String option = null;
    for( int i = 0; i < p_Possibilities.length; i++){
      option = p_Possibilities[i];
      if( option.equals(p_Selection)){
        return i;
      }
    }
    return 0;
  }




  public void showMessage(int p_Index) {
    if( p_Index == 1) {
	  JOptionPane.showMessageDialog(this.cmFrame,CMMessages.getString("INFO_GENERATION_OF_COMBINATIONS_NOT_POSIBLE_1"));
    }
    else if( p_Index == 2) {
	  JOptionPane.showMessageDialog(this.cmFrame,CMMessages.getString("INFO_GENERATION_OF_COMBINATIONS_NOT_POSIBLE_2"));
    }
    else if( p_Index == 3) {
      JOptionPane.showMessageDialog(this.cmFrame,CMMessages.getString("INFO_GENERATION_OF_COMBINATIONS_NOT_POSIBLE_3"));
    }
  }

  void addCombinationView(Vector combinationView) {
    cmGridModel.addColumn(combinationView);
  }

  void addCombinationView(Vector combinationView, int index) {
    cmGridModel.insertColumn(index, combinationView);
  }

  void setCombinationViewAt(Vector combinationView, int index) {
      //grueda_26072004_begin
      int nextElementIndex = index+1;
    if( index >= 0 && nextElementIndex >= 0) {
      cmGridModel.insertColumn(index, combinationView);
      if( cmGridModel.getColumnCount() > nextElementIndex) {
          this.setColumnWidth(index, getColumnWidth(nextElementIndex));
          cmGridModel.fireGridColumnsChanged(index,1);
          cmGridModel.removeColumns(nextElementIndex,1);
      }
      else {
          this.setColumnWidth(index, getColumnWidth(index));
          cmGridModel.fireGridColumnsChanged(index,1);
          //cmGridModel.removeColumns(index,1);
      }
    }
    //grueda_26072004_end
  }

  private int getNumberOfRowsInElementDependencyViews() {
	  //number of rows =
	  if (selectedDependency== null)
		  return 0;
	  int count=0;
	  //2 for each element +
	  count+=selectedDependency.getLnkElements().size()*2;
	  //	one more for each positive equivalence class
	  for (Element element : selectedDependency.getElements())
		  count+=element.getEquivalenceClassesbyState(State.POSITIVE.intValue()).size();

	  //1 more if the element have any positive eqclass
	  for (Element element : selectedDependency.getElements())
		  if (element.getEquivalenceClassesbyState(State.POSITIVE.intValue()).size()>0)
			  count++;
	  //2 more for the cause effects
	  count+=1;
	  return count;
  }

  public void addAllCombinationViews(Dependency dependency) {
    if( dependency == null) return;
    int numOfCombinations = dependency.getLnkCombinations().size();
    cmGridModel.setRowCount(getNumberOfRowsInElementDependencyViews());
    for( int i = 0; i < numOfCombinations; i++) {
      Combination combination = (Combination) dependency.getLnkCombinations().elementAt(i);
      Vector combinationView = createCombinationView(dependency,combination);
      addCombinationView(combinationView);
      this.setWidthOfLastCombinationView();
    }
  }

  void addCombinationViewsAt(Vector views, int index) {
    int numOfViews = views.size();
    for( int i = 0; i < numOfViews; i++) {
      Vector view = (Vector) views.elementAt(i);
      this.addCombinationView(view, index);
      this.setWidthOfCombinationViewAt(index);
      index++;
    }
  }



  Vector createCombinationView(Dependency dependency, Combination combination) {
    Vector combinationView = new Vector(0);
    int numOfElements = dependency.getLnkElements().size();
    for( int i = 0; i < numOfElements; i++) {
    	Element element = (Element) dependency.getLnkElements().elementAt(i);
    		if (!combinationView.contains(combination))
    		{
				combinationView.addElement(new CMCellHeaderCombination(combination) );
				combinationView.addElement(new CMCellEmpty());
				if (element.getEquivalenceClassesbyState(State.POSITIVE.intValue()).size()>0)
					combinationView.addElement(new CMCellEmpty());
    		}
				int numOfEquivalenceClasses = element.getEquivalenceClasses().size();
				for( int j = 0; j < numOfEquivalenceClasses; j++) {
					EquivalenceClass equivalenceClass = (EquivalenceClass) element.getEquivalenceClasses().get(j);
					if( equivalenceClass.getState() == CMStateBean.STATE_POSITIVE /*&& !combinationView.contains(combination)*/) {
						combinationView.addElement(new CMCellEquivalenceClassInCombination(equivalenceClass,combination));
					}
				}
    }
   // combinationView.addElement(new CMCellHeaderEffectsInCombination(combination));
    combinationView.addElement(new CMCellEffectsInCombination(combination));
    return combinationView;
  }

  public int getCombinationViewIndex(Combination p_Combination) {
    int numOfViews = this.getColumnCount();
    for( int i = 0; i < numOfViews; i++) {
        Object obj = cmGridModel.getCellObjectAt(0,i);
		Combination combinationCell = ((CMCellHeaderCombination)obj).getCombination();
        if( p_Combination == combinationCell) {
          return i;
        }
    }
    return -1;
  }


  
      
  void assignEquivalenceClassToCombination(EquivalenceClass p_EquivalenceClass, Combination p_Combination) {
	  CMCompoundEdit ce = new CMCompoundEdit();
	  ce.addEdit(combinationManager.addEquivalenceClassToCombination(p_EquivalenceClass, p_Combination));
		      int row = this.getSelectionModel().getLeadRow();
			  int column = this.getSelectionModel().getLeadColumn();
			  this.setValueAt(new CMCellEquivalenceClassInCombination(p_EquivalenceClass, p_Combination), row, column);
              this.cmGridModel.fireGridColumnsChanged(column,1);
      if (ce.hasEdits())
    	  CMUndoMediator.getInstance().doEdit(ce);

  }

  public void cancelEquivalenceClassAssignment(EquivalenceClass p_EquivalenceClass, Combination p_Combination){
               combinationManager.deleteEquivalenceFromCombination(p_EquivalenceClass, p_Combination);
				int row = this.getSelectionModel().getLeadRow();
				int column = this.getSelectionModel().getLeadColumn();
				this.setValueAt(new CMCellEquivalenceClassInCombination(p_EquivalenceClass, p_Combination), row, column);
                cmGridModel.fireGridColumnsChanged(column,1);
  }


  public void updateCommandos() {
    int selectedRow = getSelectionModel().getLeadRow();
    int selectedColumn = getSelectionModel().getLeadColumn();
    selectedCombination = this.getSelectedCombination(selectedRow, selectedColumn);

    if( this.selectedDependency != null) {
    	CMAction.DEPENDENCY_CREATE.setEnabled(true);
    	CMAction.DEPENDENCY_EDIT.setEnabled(true);


      CMAction.DEPENDENCY_GENERATE_COMB.setEnabled(true);
      CMAction.DEPENDENCY_GENERATE_COMB_ALLPAIRS.setEnabled(true);
      CMAction.DEPENDENCY_GENERATE_COMB_PERMUTATION.setEnabled(true);
			  Combination combination = null;
			  if( selectedDependency.getLnkCombinations().size() > 0) {
				combination = (Combination) selectedDependency.getLnkCombinations().elementAt(0);
				if( combination.getOriginType() != null) {
                  if( !(combination.getOriginType() == Combination.Origin.PERMUTATION)) {
                	  CMAction.COMBINATION_CREATE.setEnabled(true);
                  }
                  else{
                	  CMAction.COMBINATION_CREATE.setEnabled(false);
                  }
				}
              }
              else{
            	  CMAction.COMBINATION_CREATE.setEnabled(true);
              }

    }
    else {
    	CMAction.DEPENDENCY_CREATE.setEnabled(true);
    	CMAction.DEPENDENCY_EDIT.setEnabled(false);
    	CMAction.DEPENDENCY_DELETE.setEnabled(false);


     CMAction.DEPENDENCY_GENERATE_COMB_PERMUTATION.setEnabled(false);
      CMAction.DEPENDENCY_GENERATE_COMB.setEnabled(true);
      CMAction.DEPENDENCY_GENERATE_COMB_ALLPAIRS.setEnabled(false);
      CMAction.COMBINATION_CREATE.setEnabled(false);
    }
    if( this.selectedCombination != null) {
      if( this.selectedCombination.getOriginType() == null) {
        selectedCombination.setOriginType(Combination.Origin.PERMUTATION);
      }
      if( this.selectedCombination.getOriginType().equals(Combination.Origin.MANUAL) ){
        //editCombination.true
    	  CMAction.COMBINATION_EDIT.setEnabled(true);
        //deleteCombination.treu
    	  CMAction.COMBINATION_DELETE.setEnabled(true);

        //assignsSelectedCombiantionToTestCases.true
        CMAction.COMBINATION_ASSIGN_TO_TESTCASE.setEnabled(true);
        //assigenCauseEffectsToCombination.true
        CMAction.COMBINATION_CREATE_ASSIGN_EFFECTS.setEnabled(true);
        CMAction.COMBINATION_EDIT_EFFECTS.setEnabled(true);
        //createCombination.true
        CMAction.COMBINATION_CREATE.setEnabled(true);
        CMAction.DEPENDENCY_GENERATE_COMB_PERMUTATION.setEnabled(true);;
        CMAction.DEPENDENCY_GENERATE_COMB.setEnabled(true);
        CMAction.DEPENDENCY_GENERATE_COMB_ALLPAIRS.setEnabled(true);
      }
      else
      if( this.selectedCombination.getOriginType() == Combination.Origin.PERMUTATION){
         //editCombination.true
    	  CMAction.COMBINATION_EDIT.setEnabled(true);
         //mergeCombination.true
    	  CMAction.COMBINATION_MERGE.setEnabled(true);

         //separateCombination.true
    	  CMAction.COMBINATION_UNMERGE.setEnabled(true);

        //assignsSelectedCombiantionToTestCases.true
         CMAction.COMBINATION_ASSIGN_TO_TESTCASE.setEnabled(true);
        //assigenCauseEffectsToCombination.true
         CMAction.COMBINATION_CREATE_ASSIGN_EFFECTS.setEnabled(true);
         CMAction.COMBINATION_EDIT_EFFECTS.setEnabled(true);
        //createCombination.true
        CMAction.COMBINATION_DELETE_ALL.setEnabled(true);
        CMAction.DEPENDENCY_GENERATE_COMB.setEnabled(true);
        CMAction.DEPENDENCY_GENERATE_COMB_ALLPAIRS.setEnabled(true);
        CMAction.DEPENDENCY_GENERATE_COMB_PERMUTATION.setEnabled(true);
      }
      else
      if( this.selectedCombination.getOriginType() == Combination.Origin.ALLPAIRS) {
        //editCombination.true
    	  CMAction.COMBINATION_EDIT.setEnabled(true);

    	  CMAction.COMBINATION_MERGE.setEnabled(false);
        CMAction.COMBINATION_DELETE.setEnabled(false);

         //separateCombination.true
        CMAction.COMBINATION_UNMERGE.setEnabled(false);

        //assignsSelectedCombiantionToTestCases.true
        CMAction.COMBINATION_ASSIGN_TO_TESTCASE.setEnabled(true);
        //assigenCauseEffectsToCombination.true
        CMAction.COMBINATION_CREATE_ASSIGN_EFFECTS.setEnabled(true);
        CMAction.COMBINATION_EDIT_EFFECTS.setEnabled(true);
        //createCombination.true
        CMAction.COMBINATION_CREATE.setEnabled(true);
        CMAction.COMBINATION_DELETE_ALL.setEnabled(true);

        CMAction.DEPENDENCY_GENERATE_COMB_PERMUTATION.setEnabled(true);
        CMAction.DEPENDENCY_GENERATE_COMB.setEnabled(true);
        CMAction.DEPENDENCY_GENERATE_COMB_ALLPAIRS.setEnabled(true);
      }
      else {

    	  CMAction.COMBINATION_CREATE.setEnabled(false);

    	  CMAction.COMBINATION_EDIT.setEnabled(false);

        //assignsSelectedCombiantionToTestCases.false
    	  CMAction.COMBINATION_ASSIGN_TO_TESTCASE.setEnabled(false);
        //assigenCauseEffectsToCombination.false
    	  CMAction.COMBINATION_CREATE_ASSIGN_EFFECTS.setEnabled(false);
    	  CMAction.COMBINATION_EDIT_EFFECTS.setEnabled(false);
        //createCombination.false
        CMAction.COMBINATION_CREATE.setEnabled(false);
        CMAction.COMBINATION_DELETE_ALL.setEnabled(false);

        CMAction.DEPENDENCY_GENERATE_COMB_PERMUTATION.setEnabled(false);
        CMAction.DEPENDENCY_GENERATE_COMB.setEnabled(false);
        CMAction.DEPENDENCY_GENERATE_COMB_ALLPAIRS.setEnabled(true);
      }
    }
    else {


    		CMAction.COMBINATION_EDIT.setEnabled(false);
			//deleteCombination.treu
    		CMAction.COMBINATION_DELETE.setEnabled(false);
			 //mergeCombination.false
    		CMAction.COMBINATION_MERGE.setEnabled(false);
			 //separateCombination.false
    		CMAction.COMBINATION_UNMERGE.setEnabled(false);

			//assignsSelectedCombiantionToTestCases.false
    		CMAction.COMBINATION_ASSIGN_TO_TESTCASE.setEnabled(false);
			//assigenCauseEffectsToCombination.false
    		CMAction.COMBINATION_CREATE_ASSIGN_EFFECTS.setEnabled(false);
    		CMAction.COMBINATION_EDIT_EFFECTS.setEnabled(false);
			//createCombination.false
		}
  }
 //////////////////////// GridModel ///////////////////////////////////////////
  public class CMGridModel extends GenericGridModel {
	private boolean modified = false;
    public CMGridModel(int numRows, int numColumns) {
      super(numRows, numColumns);
    }



    public boolean isCellEditable(int row, int column) {
      Object obj = super.getValueAt(row,column);
      if(obj == null) {
        return false;
      }
      if( obj instanceof CMCellHeaderElementName || obj instanceof CMCellHeaderElementDescription ||
        obj instanceof CMCellHeaderEquivalenceClassName || obj instanceof CMCellHeaderEquivalenceClassState ||
        obj instanceof CMCellHeaderEquivalenceClassValue || obj instanceof CMCellHeaderDependencyName ||
         obj instanceof CMCellEquivalenceClassName || obj instanceof CMCellDependencyName ||
        obj instanceof CMCellEmpty || obj instanceof CMCellHeaderCombination ||
        obj instanceof CMCellHeaderEffectsInCombination || obj instanceof CMCellEffectsInCombination ||
        obj instanceof CMCellEquivalenceClassInCombination || obj instanceof CMCellEquivalenceClassState) {
        return false;
      }
      else {
    	 return true;
      }
    }
    public Object getCellObjectAt(int row, int column) {
      if(this.getRowCount() > row && this.getColumnCount() > column) {
        return super.getValueAt(row,column);
      }
      else {
        return null;
      }
    }

	public boolean isModified() {
		return this.modified;
	}

	public void setModified(boolean p_modified) {
		this.modified = p_modified;
	}
  }

  //////////////////////// SpanModel ///////////////////////////////////////////
  public class CMSpanModel extends AbstractDirectSpanModel {
    CMCombinationViews.CMGridModel model = null;
    public CMSpanModel(CMCombinationViews.CMGridModel m) {
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

/**
 * @return Returns the combinationManager.
 */
public CombinationManager getCombinationManager() {
    return combinationManager;
}
/**
 * @param combinationManager The combinationManager to set.
 */
public void setCombinationManager(CombinationManager combinationManager) {
    this.combinationManager = combinationManager;
}

public CMGridModel getCmGridModel() {
	return cmGridModel;
}

public void setCmGridModel(CMGridModel cmGridModel) {
	this.cmGridModel = cmGridModel;
}

public Dependency getSelectedDependency() {
	return selectedDependency;
}

public Combination getSelectedCombination() {
	if (selectedCombination==null)
		selectedCombination = getSelectedCombination(getSelectionModel().getLeadRow(),getSelectionModel().getLeadColumn());
	return selectedCombination;
}

public void setSelectedCombination(Combination selectedCombination) {
	this.selectedCombination = selectedCombination;
}

public EquivalenceClass getSelectedEquivalenceClass() {
	if (selectedEquivalenceClass==null)
		selectedEquivalenceClass = getSelectedEquivalenceClass(getSelectionModel().getLeadRow(),getSelectionModel().getLeadColumn());
	return selectedEquivalenceClass;
}

public void setSelectedEquivalenceClass(
		EquivalenceClass selectedEquivalenceClass) {
	this.selectedEquivalenceClass = selectedEquivalenceClass;
}

public void setSelectedDependency(Dependency selectedDependency) {
	this.selectedDependency = selectedDependency;
}

public CMPanelDependencies getCmPanelDependencies() {
	return cmPanelDependencies;
}

public void handleCMModelChange(CMModelEvent evt) {
	if (evt.getChangedField()==CMField.STATE) {
		EquivalenceClass equivalenceClass = (EquivalenceClass) evt.getSource();
if (selectedDependency!=null){
		  this.deleteAllCombinationViews();
		     this.addAllCombinationViews(selectedDependency);
}
		     clearSelection();
		     updateCommandos();

	}
	if (evt.getSource() instanceof Element && evt.getChangedField() == CMField.EQUIVALENCE_CLASSES)
	{
		if (selectedDependency !=null){
			removeListenerFromAll(selectedDependency);
		  	addModelListenerToAll(selectedDependency);
		  	this.deleteAllCombinationViews();
		     this.addAllCombinationViews(selectedDependency);
		}
		     clearSelection();
		     updateCommandos();
	}
	if (evt.getSource() instanceof Dependency && evt.getChangedField() == CMField.COMBINATIONS)
	{
		  this.deleteAllCombinationViews();
		     this.addAllCombinationViews(selectedDependency);
		     clearSelection();
		     updateCommandos();
	}
}

public int getSelectedRow() {
	return selectedRow;
}

public void setSelectedRow(int selectedRow) {
	this.selectedRow = selectedRow;
}

public int getSelectedColumn() {
	return selectedColumn;
}

public void setSelectedColumn(int selectedColumn) {
	this.selectedColumn = selectedColumn;
}

@Override
protected HashMap<Class, Component> getCellClasses() {
	// TODO Auto-generated method stub
	return null;
}
}
