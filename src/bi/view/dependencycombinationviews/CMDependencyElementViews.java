/* Developed by BUSINESS SOFTWARE INNOVATIONS.  .. Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved..*/

package bi.view.dependencycombinationviews;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Event;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JViewport;

import model.CMField;
import model.Combination;
import model.Dependency;
import model.Element;
import model.EquivalenceClass;
import model.Structure;
import model.util.CMModelEvent;
import model.util.CMModelListener;
import model.util.CMStateBean;
import bi.controller.CombinationManager;
import bi.controller.DependencyManager;
import bi.view.actions.CMAction;
import bi.view.cells.CMCellColorRendererGray;
import bi.view.cells.CMCellCombination;
import bi.view.cells.CMCellDependency;
import bi.view.cells.CMCellDependencyName;
import bi.view.cells.CMCellEffectsInCombination;
import bi.view.cells.CMCellElement;
import bi.view.cells.CMCellElementDescription;
import bi.view.cells.CMCellElementDescriptionEmpty;
import bi.view.cells.CMCellElementName;
import bi.view.cells.CMCellEmpty;
import bi.view.cells.CMCellEquivalenceClassDescription;
import bi.view.cells.CMCellEquivalenceClassInCombination;
import bi.view.cells.CMCellEquivalenceClassName;
import bi.view.cells.CMCellEquivalenceClassState;
import bi.view.cells.CMCellEquivalenceClassValue;
import bi.view.cells.CMCellGroupDataElement;
import bi.view.cells.CMCellGroupDataEquivalenceClass;
import bi.view.cells.CMCellGroupHeaderElement;
import bi.view.cells.CMCellGroupHeaderEquivalenceClass;
import bi.view.cells.CMCellSelectAllEquivalenceClassesOfElement;
import bi.view.cells.CMCellSelectEquivalenceClass;
import bi.view.cells.headers.CMCellHeaderCombination;
import bi.view.cells.headers.CMCellHeaderDefault;
import bi.view.cells.headers.CMCellHeaderDependencyName;
import bi.view.cells.headers.CMCellHeaderEffectsInCombination;
import bi.view.cells.headers.CMCellHeaderElementDescription;
import bi.view.cells.headers.CMCellHeaderElementDescriptionEmpty;
import bi.view.cells.headers.CMCellHeaderElementName;
import bi.view.cells.headers.CMCellHeaderEquivalenceClassDescription;
import bi.view.cells.headers.CMCellHeaderEquivalenceClassName;
import bi.view.cells.headers.CMCellHeaderEquivalenceClassState;
import bi.view.cells.headers.CMCellHeaderEquivalenceClassValue;
import bi.view.cells.renderers.CMCellDefaultHeaderRenderer;
import bi.view.cells.renderers.CMCheckBoxRenderer;
import bi.view.grids.CMBaseJSmartGrid;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMFrameView;
import bi.view.utils.CMBaseJComboBox;

import com.eliad.model.AbstractDirectSpanModel;
import com.eliad.model.ExtentCell;
import com.eliad.model.GenericGridModel;
import com.eliad.model.GridContext;
import com.eliad.model.defaults.DefaultGridCellRenderer;
import com.eliad.model.defaults.DefaultStyleModel;
import com.eliad.swing.GridEditingEvent;
import com.eliad.swing.GridEditingListener;
import com.eliad.swing.GridEvent;
import com.eliad.util.RulerConstants;


public class CMDependencyElementViews extends CMBaseJSmartGrid implements CMModelListener {
  CMSpanModel  spanModel = null;
  CMStyleModel styleModel = null;
  CMGridModel  cmGridModel = null;
  Object editingObject = null;
  CombinationManager combinationManager = null;
  DependencyManager  dependencyManager = null;
  Dependency selectedDependency = null;
  CMFrameView cmFrame = null;
  Combination selectedCombination = null;
  EquivalenceClass selectedEquivalenceClass = null;
  Structure selectedStructure = null;
  CMPanelDependencies cmPanelDependencies = null;
    private int m_Width;

private CMCellColorRendererGray cmColorRendererGray = new CMCellColorRendererGray();
private CMCheckBoxRenderer cmCheckBoxRenderer = new CMCheckBoxRenderer();
private FocusListener focusListener = new FocusAdapter(){
	public void focusLost(FocusEvent e) {
		//getCmPanelDependencies().getM_CMCombinationViews().repaint();
   }
	public void focusGained(FocusEvent e) {
		getCmPanelDependencies().getM_CMCombinationViews().repaint();
	}
};
private GridEditingListener gridEditingListener = new GridEditingListener()
 {
public void editingStarted(GridEditingEvent e) {
eventEditingStarted(e);
}
public void editingStopped(GridEditingEvent e) {
eventEditingStopped(e);
}
public void editingCanceled(GridEditingEvent e) {
eventEditingCanceled(e);
}
 };
    /**
     * @link aggregationByValue
     * @clientCardinality 1
     * @supplierCardinality 0..*
     * @directed
     */
    //private CMDependencyElementView lnkCMDependencyElementView;

    public CMDependencyElementViews(CMPanelDependencies cmPanelDependencies, CMFrameView cmFrame /*, CMPopupMenuCombinations p_cmPopupMenuCombinations*/) {
    super();
    combinationManager = cmFrame.getCmApplication().getSessionManager().getCombinationManager();
    dependencyManager  = cmFrame.getCmApplication().getSessionManager().getDependencyManager();
 		this.cmFrame = cmFrame;
    this.cmPanelDependencies = cmPanelDependencies;
    cmFrame.getKeyEventIntercepter().setM_CMDependencyElementViews(this);//fcastro_20092004
    initGUI();
    this.setGridNavigationPolicy(new CMDependencyElementGridNavPolicy(this));
    this.addFocusListener(focusListener );
    initializeCellEditors();
    initializeCellRenderers();
    }
protected HashMap getCellClasses() {
	HashMap map = new HashMap();
	map.put(CMCellElementName.class,new JTextField());
	map.put(CMCellElementDescription.class,new JTextField());
	//map.put(CMCellElementGuiObject.class,new CMBaseJComboBox(this));
	map.put(CMCellEquivalenceClassValue.class,new JTextField());
	map.put(CMCellEquivalenceClassState.class,new CMBaseJComboBox(this));
	map.put(CMCellEquivalenceClassDescription.class,new JTextField());
	map.put(CMCellSelectAllEquivalenceClassesOfElement.class, new JCheckBox());
    map.put(CMCellSelectEquivalenceClass.class,new JCheckBox());
	//map.put()
	return map;
}
    public JViewport getViewport1() {
      return getViewport();    // getViewport is not public in JSmartGrid
    }

    public void setM_Structure(Structure p_Structure) {
      selectedStructure = p_Structure;
     deleteAllDependencyElementViews();

    }

    public Structure getM_Structure() {
      return selectedStructure;
    }

    public void initGUI() {
   // cmGridModel = new CMGridModel(0,5); //OLD fcastro_1309204
   cmGridModel = new CMGridModel(0,6);//fcastro_13092004   I) change 6 to 7
    this.setModel(cmGridModel);
    spanModel = new CMSpanModel(cmGridModel);
    this.setSpanModel(spanModel);
    styleModel = new CMStyleModel(cmGridModel);


    styleModel.setRenderer(CMCellElement.class,CMCellElement.defaultGridHeaderRenderer);

   styleModel.setRenderer(CMCellDependency.class,CMCellDependency.defaultGridHeaderRenderer);
   styleModel.setRenderer(CMCellEquivalenceClassDescription.class,CMCellEquivalenceClassDescription.defaultRenderer);
   styleModel.setRenderer(CMCellSelectEquivalenceClass.class,cmCheckBoxRenderer);//CMCellSelectEquivalenceClass.defaultRenderer);
    styleModel.setEditor(CMCellSelectEquivalenceClass.class,CMCellSelectEquivalenceClass.defaultEditor);
    styleModel.setRenderer(CMCellDependencyName.class,cmColorRendererGray);
    styleModel.setRenderer(CMCellEffectsInCombination.class,cmColorRendererGray);

    this.setStyleModel(styleModel);

///////////////////////////////////////////////////////////////////////////////
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
    this.setColumnWidths();
///////////////////////////////////////////////////////////////////////////////
    //this.setAlignmentY((float) 0.5);
    //this.setAlignmentX((float) 0.5);
    this.addGridListener(new com.eliad.swing.GridAdapter() {
      public void gridMouseMoved(GridEvent e) {
        eventGridMouseMoved(e);
      }
      public void gridMouseClicked(GridEvent e) {
        eventGridMouseClicked(e);
      }
     });
    this.addGridEditingListener(gridEditingListener);

    addComponentListener(new ComponentAdapter(){public void componentResized(ComponentEvent e){thisComponentResized(e);}});
    addPropertyChangeListener(new PropertyChangeListener(){public void propertyChange(PropertyChangeEvent e){thisPropertyChange(e);}});
    }


public void changeSelection(int arg0, int arg1, boolean arg2, boolean arg3) {
	super.changeSelection(arg0, arg1, arg2, arg3);
	cmPanelDependencies.updateScrolls();
	this.repaint();
	cmPanelDependencies.getM_CMCombinationViews().repaint();

}
//    public void changeSelection1(int row,int column, boolean toogle, boolean extent){
//    	super.changeSelection(row,column,false,false);
//
//
//		if(row>=0 && row < this.getRowCount() && column >=0 && column < this.getColumnCount()){
//			Object obj = cmGridModel.getCellObjectAt(row,column);
//            if(obj!=null){
//            if(obj instanceof  CMCellEquivalenceClassName || obj instanceof CMCellEquivalenceClassDescription
//                || obj instanceof CMCellEquivalenceClassState || obj instanceof CMCellEquivalenceClassValue
//      			|| obj instanceof CMCellSelectEquivalenceClass){
//
//
//                this.cmFrame.getM_CMKeyEventIntercepter().setColumnSelectedInCMDependencyElementViews(column);
//               // this.setSelectionUnit(com.eliad.swing.JSmartGrid.UNIT_CELL);
//	            this.setSelectionPolicy(com.eliad.swing.JSmartGrid.POLICY_RANGE);
//
//	            this.setSelectionCellBorder(BorderFactory.createEmptyBorder());

  				//this.setSelectionBackgroundColor(SystemColor.inactiveCaptionText);

				///////////////////////////////////////////////
                //change Selection Mode in CMCombinationViews
//                int horizontalScrollValue = cmPanelDependencies.getRightHorizontalScrollValue();//fcastro_19102004
//                this.cmPanelDependencies.getM_CMCombinationViews().clearSelection();
//                this.cmFrame.getM_CMKeyEventIntercepter().setCombinationHeaderRowSelected(-1);
//                this.cmPanelDependencies.getM_CMCombinationViews().setSelectionMode(com.eliad.swing.JSmartGrid.SINGLE_ROW_SELECTION);
//                this.cmPanelDependencies.getM_CMCombinationViews().setSelectionBackgroundColor(SystemColor.inactiveCaptionText);
//                this.cmPanelDependencies.getM_CMCombinationViews().setSelectionCellBorder(BorderFactory.createEmptyBorder());
//                this.cmPanelDependencies.getM_CMCombinationViews().setFocusHighlightBorder(BorderFactory.createEmptyBorder());
//                this.cmPanelDependencies.getM_CMCombinationViews().setSelectionDefaultMode(true);
//                this.cmPanelDependencies.getM_CMCombinationViews().changeSelection(row,0,false,false);
//                cmPanelDependencies.setRightHorizontalScroll(horizontalScrollValue);//fcastro_19102004
//                this.repaint();
//                ////////////////////////////////////////////////
//                return;
//            }
//            else if((this.getSelectionPolicy() == com.eliad.swing.JSmartGrid.POLICY_RANGE) || (obj instanceof CMCellSelectAllEquivalenceClassesOfElement)){
            //    this.cmFrame.getM_CMKeyEventIntercepter().setColumnSelectedInCMDependencyElementViews(-1);
             //   this.setSelectionMode(com.eliad.swing.JSmartGrid.SINGLE_CELL_SELECTION);
            //    this.setSelectionCellBorder(BorderFactory.createLineBorder(Color.orange,2));
   			//	this.setSelectionBackgroundColor(SystemColor.activeCaptionText);
   		//		this.setFocusBackgroundColor(SystemColor.white);
              //  super.changeSelection(row,column,false,false);
              //  this.styleModel.setRenderer(String.class, this.styleModel.getDefaultCellRenderer());
                ///////////////////////////////////////////////
                //change Selection Mode in CMCombinationViews
            //    this.cmPanelDependencies.getM_CMCombinationViews().clearSelection();
            //    this.cmFrame.getM_CMKeyEventIntercepter().setCombinationHeaderRowSelected(-1);
            //    this.cmPanelDependencies.getM_CMCombinationViews().setSelectionMode(com.eliad.swing.JSmartGrid.SINGLE_CELL_SELECTION);
            //     this.cmPanelDependencies.getM_CMCombinationViews().setSelectionBackgroundColor(SystemColor.activeCaptionText);
            //    this.cmPanelDependencies.getM_CMCombinationViews().setSelectionCellBorder(BorderFactory.createLineBorder(Color.orange,2));
            //    this.cmPanelDependencies.getM_CMCombinationViews().setFocusHighlightBorder(BorderFactory.createLineBorder(Color.orange,2));
                ////////////////////////////////////////////////
            //    cmPanelDependencies.updateScrolls();//fcastro_19102004
           //     this.repaint();
   //             return;
     //       }
       //     }
          //  super.changeSelection(row,column,toogle,extent);
           // cmPanelDependencies.updateScrolls();//fcastro_19102004


      //  }
	//	this.repaint();
   // }


    public boolean isEquivalenceClassCell(int row,int column){
        Object obj = cmGridModel.getCellObjectAt(row,column);
        if(obj != null && (obj instanceof  CMCellEquivalenceClassName || obj instanceof CMCellEquivalenceClassDescription
                			|| obj instanceof CMCellEquivalenceClassState || obj instanceof CMCellEquivalenceClassValue
      						|| obj instanceof CMCellSelectEquivalenceClass)){
            return true;
        }
        return false;
    }


    //fcastro_20092004_end

 public void setColumnWidths(){

    int columnWidth1 = 15*6;
    int columnWidth2 = 18*6;
    int columnWidth3 = 1*6;
    int columnWidth4 = 15*6;
    int columnWidth5 = 13*6;
    int columnWidth6 = 13*6;//fcastro_13092004
    this.setM_Width(columnWidth1+columnWidth2+columnWidth3+columnWidth4+columnWidth5+columnWidth6);//fcastro_13092004
    this.setColumnWidth(0,columnWidth1);
    this.setColumnWidth(1,columnWidth2);
    this.setColumnWidth(2,columnWidth3);
    this.setColumnWidth(3,columnWidth4);
    this.setColumnWidth(4,columnWidth5);
    this.setColumnWidth(5,columnWidth6);//fcastro_13092004
 }


  void eventGridMouseMoved(GridEvent e) {
    int row = e.getRow();
    int column = e.getColumn();
    Object obj = cmGridModel.getCellObjectAt(row,column);
    if( obj != null) {
        if( obj instanceof CMCellElementName/* || obj instanceof CMCellElementGuiObject */|| obj instanceof CMCellElementDescription ||
            obj instanceof CMCellEquivalenceClassValue) {
            this.setCursor(new Cursor(Cursor.TEXT_CURSOR));
        }
				else {
					this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}
    }
  }

  void eventGridMouseClicked(GridEvent e) {
    int row = e.getRow();
    int column = e.getColumn();
    if(e.getSourceEvent().getModifiers() == Event.META_MASK) {
        if( row >= 0 && column >= 0) {
					this.clearSelection();
					this.changeSelection(row,column,false,false);
          Object obj = cmGridModel.getCellObjectAt(row,column);
          if( obj instanceof CMCellHeaderCombination  || obj instanceof CMCellHeaderEffectsInCombination ||
              obj instanceof CMCellEffectsInCombination) {
							MouseEvent mouseEvent = (MouseEvent) e.getSourceEvent();
				CMAction.COMBINATION_MERGE.setEnabled(false);
				 CMAction.COMBINATION_UNMERGE.setEnabled(true);
                     cmFrame.getJPopupMenuCombinations().show(this, mouseEvent.getX(), mouseEvent.getY());
							selectedCombination = ((CMCellCombination)obj).getCombination();
							this.revalidate();
          }
          if( obj instanceof CMCellEquivalenceClassInCombination) {
						MouseEvent mouseEvent = (MouseEvent) e.getSourceEvent();
			   CMAction.COMBINATION_MERGE.setEnabled(true);
			   CMAction.COMBINATION_UNMERGE.setEnabled(true);
               cmFrame.getJPopupMenuCombinations().show(this, mouseEvent.getX(), mouseEvent.getY());
            selectedCombination = ((CMCellCombination)obj).getCombination();
            selectedEquivalenceClass = ((CMCellEquivalenceClassInCombination)obj).getEquivalenceClass();
          }
          if( obj instanceof CMCellHeaderDependencyName){
			  MouseEvent mouseEvent = (MouseEvent) e.getSourceEvent();
			  CMAction.DEPENDENCY_CREATE.setEnabled(false);
              cmFrame.getPopupMenuDependency().show(this, mouseEvent.getX(), mouseEvent.getY());

          }
        }
    }
  }


  void eventEditingCanceled(GridEditingEvent e) {
  }
  public void selectAllEquivalenceClassesFromElement(Element p_Element, Dependency p_Dependency){
	  //smoreno ,int dependencyIndex/*integration_fcastro_17082004*/, int p_Row,int  p_Column){

    selectAllEquivalenceClasses(p_Element, p_Dependency);//,dependencyIndex/*integration_fcastro_17082004*/, p_Row, p_Column);
    //super.setValueAt(new CMCellSelectAllEquivalenceClassesOfElement(new Boolean(true),p_Element,p_Dependency),p_Row, p_Column);
  }

  public void deSelectAllEquivalenceClassesFromElement(Element p_Element, Dependency p_Dependency)
  {
	  //smoreno int dependencyIndex/*integration_fcastro_17082004*/, int p_Row, int p_Column){
    deSelectAllEquivalenceClasses(p_Element, p_Dependency);
    		//smoreno comments//,dependencyIndex/*integration_fcastro_17082004*/, p_Row, p_Column);
    //super.setValueAt(new CMCellSelectAllEquivalenceClassesOfElement(new Boolean(false), p_Element, p_Dependency), p_Row, p_Column);
  }

  public Object deleteCharacterSpecialSemiColon(Object p_Value)
  {
		String value= (String)p_Value;
        int index= value.indexOf(";");
        if(index >=0)
        {
            JOptionPane.showMessageDialog(cmFrame,CMMessages.getString("TESTCASE_DELETE_CONTROL_CHAR_MENSSAGE"),CMMessages.getString("TESTDATA_IMPORT_TITLE_INFORMATION"),JOptionPane.INFORMATION_MESSAGE);
            value=value.replaceAll(";","");
        }
        return value;
  }

  //integration_fcastro_17082004_begin
  void eventEditingStopped(GridEditingEvent e) {
      int row = e.getRow();
      int column = e.getColumn();
      Object value = null;
      Dependency dependency = null;

      /**smoreno
       * This code must be refactored to the editor of each cell!!!!!!!!!!!!!!!!!!
       * a lot of coupling here...
      */
//      if( editingObject instanceof CMCellSelectAllEquivalenceClassesOfElement) {
//        Element element = ((CMCellElement)editingObject).getElement();
//	    dependency = ((CMCellSelectAllEquivalenceClassesOfElement)editingObject).getDependency();
//        value = ((CMCellSelectAllEquivalenceClassesOfElement)editingObject).getSelected();//super.getValueAt(row,column);//cmGridModel.getValueAt(row,column);
//        if(((Boolean)value).booleanValue() == true ) {
         // cmFrame.getM_CMUndoMediator().addAllEquivalenceClassesToDependency(this,this.cmPanelDependencies.getCMGridDependencies().getSelectionModel().getLeadRow()/*integration_fcastro_17082004*/,row,column,element,dependency, cmFrame.getm_TabbedPaneView(), CMMessages.getString("LABEL_DEPENDENCIES_COMBINATIONS")); //$NON-NLS-1$
          //OLD:
          //this.selectAllEquivalenceClasses(element,dependency, row,column);
          //this.setValueAt(new CMCellSelectAllEquivalenceClassesOfElement(new Boolean(true),element,dependency),row,column);
          //	  cmFrame.getM_CMUndoMediator().deleteAllEquivalenceClassesFromDependency(element,dependency); //$NON-NLS-1$
//        }
//        else{
         // cmFrame.getM_CMUndoMediator().deleteAllEquivalenceClassesFromDependency(this,this.cmPanelDependencies.getCMGridDependencies().getSelectionModel().getLeadRow()/*integration_fcastro_17082004*/,row,column,element,dependency, cmFrame.getm_TabbedPaneView(), CMMessages.getString("LABEL_DEPENDENCIES_COMBINATIONS")); //$NON-NLS-1$
          //this.deSelectAllEquivalenceClasses(element,dependency, row,column);
		  //this.setValueAt(new CMCellSelectAllEquivalenceClassesOfElement(new Boolean(false),element,dependency),row,column);
         // cmFrame.getM_CMUndoMediator().addAllEquivalenceClassesToDependency(element,dependency);//
          //, cmFrame.getm_TabbedPaneView(), CMMessages.getString("LABEL_DEPENDENCIES_COMBINATIONS")); //$NON-NLS-1$
 //       }
//      }
//      else if( editingObject instanceof CMCellSelectEquivalenceClass) {
//        EquivalenceClass equivalenceClass = ((CMCellEquivalenceClass)editingObject).getEquivalenceClass();
//        dependency = ((CMCellSelectEquivalenceClass)editingObject).getDependency();
//        value = ((CMCellSelectEquivalenceClass)editingObject).getSelected();//super.getValueAt(row,column); //cmGridModel.getValueAt(row,column);
//        if(((Boolean)value).booleanValue() == true ) {
		//  cmFrame.getM_CMUndoMediator().addEquivalenceClassToDependency(this,this.cmPanelDependencies.getCMGridDependencies().getSelectionModel().getLeadRow()/*integration_fcastro_17082004*/,row,column,equivalenceClass,dependency, cmFrame.getm_TabbedPaneView(), CMMessages.getString("LABEL_DEPENDENCIES_COMBINATIONS")); //$NON-NLS-1$//fcastro_0107200_added second parameter
          //OLD: this.selectEquivalenceClass(equivalenceClass, dependency, row,column);
		  //SMORENO TRASHED: cmFrame.getM_CMUndoMediator().deleteEquivalenceClassFromDependency(this,this.cmPanelDependencies.getCMGridDependencies().getSelectionModel().getLeadRow()/*integration_fcastro_17082004*/,row,column, equivalenceClass,dependency, cmFrame.getm_TabbedPaneView() ); //$NON-NLS-1$//fcastro_0107200_added second parameter
          //smoreno refactor to the editor cell
        	//cmFrame.getM_CMUndoMediator().deleteEquivalenceClassFromDependency( equivalenceClass,dependency ); //$NON-NLS-1$//fcastro_0107200_added second parameter
  //      }
 //       else {
        	// smoreno refactor to the editor cell
          //OLD: this.deSelectEquivalenceClass(equivalenceClass, dependency, row,column);
        //  cmFrame.getM_CMUndoMediator().deleteEquivalenceClassFromDependency(this,this.cmPanelDependencies.getCMGridDependencies().getSelectionModel().getLeadRow()/*integration_fcastro_17082004*/,row,column, equivalenceClass,dependency, cmFrame.getm_TabbedPaneView(), CMMessages.getString("LABEL_DEPENDENCIES_COMBINATIONS")); //$NON-NLS-1$//fcastro_0107200_added second parameter
        //	cmFrame.getM_CMUndoMediator().addEquivalenceClassToDependency(equivalenceClass,dependency); //$NON-NLS-1$//fcastro_0107200_added second parameter
//        }

  //    }
  //    else*/

//      if( editingObject instanceof CMCellElementName) {
//						Element element = ((CMCellElement)editingObject).getElement();
//            value = super.getValueAt(row,column);//cmGridModel.getValueAt(row,column);
//						element.setName((String)value);
//						cmGridModel.setValueAt(new CMCellElementName(this,element),row,column);
//						this.cmPanelDependencies.getCMGridDependencies().updateDescription(element);
//		  }
      //My add
      /*else if( editingObject instanceof CMCellElementGuiObject) {
			Element element = ((CMCellElement)editingObject).getElement();
			value = cmGridModel.getValueAt(row,column);
			element.setGuiObject((String)value);
			cmGridModel.setValueAt(new CMCellElementGuiObject(element),row,column);
			this.cmPanelDependencies.getCMGridDependencies().updateDescription(element);
      }*/

      //Ends my add
//	  else if( editingObject instanceof CMCellElementDescription) {
//					Element element = ((CMCellElement)editingObject).getElement();
//					value = super.getValueAt(row,column);//cmGridModel.getValueAt(row,column);
//					element.setDescription((String) value);
//					cmGridModel.setValueAt(new CMCellElementDescription(this,element),row,column);
//		  }
//      if( editingObject instanceof CMCellEquivalenceClassValue) {
//					EquivalenceClass equivalenceClass = ((CMCellEquivalenceClass)editingObject).getEquivalenceClass();
//					value = super.getValueAt(row,column);//cmGridModel.getValueAt(row,column);
//                    value= deleteCharacterSpecialSemiColon(value);
//					equivalenceClass.setValue((String)value);
//					cmGridModel.setValueAt(new CMCellEquivalenceClassValue(this,equivalenceClass),row,column);
//		  }
//      else
//    	  if( editingObject instanceof CMCellEquivalenceClassState) {
//        Object obj = cmGridModel.getCellObjectAt(row,column-1);
//        EquivalenceClass equivalenceClass = ((CMCellEquivalenceClass)obj).getEquivalenceClass();
//        CMCellEquivalenceClassState selectedItem = (CMCellEquivalenceClassState) cmGridModel.getCellObjectAt(row,column);
//        equivalenceClass.setState(selectedItem.intValue());
//      }
      //fcastro_13092004_begin
//      else if(editingObject instanceof CMCellEquivalenceClassDescription){
//	    EquivalenceClass equivalenceClass = ((CMCellEquivalenceClass)editingObject).getEquivalenceClass();
//        value = super.getValueAt(row,column);//cmGridModel.getValueAt(row,column);
//		equivalenceClass.setDescription((String) value);
//		cmGridModel.setValueAt(new CMCellEquivalenceClassDescription(this,equivalenceClass),row,column);
//
//      }
      //fcastro_13092004_begin
     // else {
        int x = 0; // Do nothing...
      //}
  }
  //integration_fcastro_17082004_end

  void eventEditingStarted(GridEditingEvent e) {
      int row = e.getRow();
      int column = e.getColumn();
      editingObject = cmGridModel.getCellObjectAt(row,column);

  }

//TODO move to the dependency manager
  public void selectAllEquivalenceClasses(Element element, Dependency dependency){
  //smoreno comments,int dependencyIndex/*integration_fcastro_17082004*/, int row, int column) {
    int numEquivalenceClasses = element.getEquivalenceClasses().size();
    //int index = row + 1;
    this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
    for( int i = 0; i < numEquivalenceClasses; i++) {
        EquivalenceClass equivalenceClass = element.getEquivalenceClasses().get(i);
        if( equivalenceClass.getState() == CMStateBean.STATE_POSITIVE) {
   	    	this.selectEquivalenceClass(equivalenceClass,dependency);
   	    	//smoreno comment parameters
   	    	//,dependencyIndex/*integration_fcastro_17082004*/,index,column);
       //   index++;
        }
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }
  }
//TODO move to the dependency manager
  public void deSelectAllEquivalenceClasses(Element element,Dependency dependency){
		 // smoreno comments //,int dependencyIndex/*integration_fcastro_17082004*/, int row, int column) {
    int numEquivalenceClasses = element.getEquivalenceClasses().size();
    //int index = row + 1;

    for( int i = 0; i < numEquivalenceClasses ; i++) {
        EquivalenceClass equivalenceClass = (EquivalenceClass) element.getEquivalenceClasses().get(i);
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));

        if( equivalenceClass.getState() == CMStateBean.STATE_POSITIVE) {
					this.deSelectEquivalenceClass(equivalenceClass,dependency);
					//smoreno deletin parameters
					//,dependencyIndex/*integration_fcastro_17082004*/,index,column);
		//			index++;
        }
    }

    this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
  }
//smoreno
//TODO This method should be on the dependency manager
    public void selectEquivalenceClass(EquivalenceClass equivalenceClass, Dependency dependency){

    if( !dependency.getLnkEquivalenceClasses().contains(equivalenceClass) ) {
				dependency.getLnkEquivalenceClasses().addElement(equivalenceClass);
    }
//smoreno comments : the cell should return
//     this.cmPanelDependencies.getCMGridDependencies().clearSelection();
//           this.cmPanelDependencies.getCMGridDependencies().changeSelection(dependencyIndex,0,false,false);
      //     changeSelection(row,1,false,false);//fcastro_20092004
    //       changeSelection(row,column,false,false);
//Ccastedo comments this 28-10-05
  }
//smoreno
//TODO this method should be on the dependency manager
    public void deSelectEquivalenceClass(EquivalenceClass equivalenceClass, Dependency dependency)
    {
    //int dependencyIndex, int row, int column){//smoreno refactored
    if( dependency.getLnkEquivalenceClasses().contains(equivalenceClass)) {
				dependency.getLnkEquivalenceClasses().removeElement(equivalenceClass);
		}
//     this.cmPanelDependencies.getCMGridDependencies().clearSelection();
//           this.cmPanelDependencies.getCMGridDependencies().changeSelection(dependencyIndex,0,false,false);
         //  changeSelection(row,1,false,false);//fcastro_20092004
//           changeSelection(row,column,false,false);
    //smoreno refactored // the cell editor shoul do this
//Ccastedo comments this 28-10-05
  }






  int getElementViewIndex(Element element) {
     int numOfRows = cmGridModel.getRowCount();
     Object obj = null;
     Element otherElement = null;
     int column = 0;
     for( int i = 0; i < numOfRows; i++) {
		 		obj = this.cmGridModel.getCellObjectAt(i,column);
        if( obj instanceof CMCellElementName) {
					otherElement = ((CMCellElementName)obj).getElement();
          if( otherElement == element) {
            return i;
          }
        }
     }
     return 0;

  }

  int getEquivalenceClassViewIndex(EquivalenceClass equivalenceClass) {
     int numOfRows = cmGridModel.getRowCount();
     Object obj = null;
     EquivalenceClass otherEquivalenceClass = null;
     int column = 1;
     for( int i = 0; i < numOfRows; i++) {
		 		obj = this.cmGridModel.getCellObjectAt(i,column);
        if( obj instanceof CMCellEquivalenceClassName) {
					otherEquivalenceClass = ((CMCellEquivalenceClassName)obj).getEquivalenceClass();
          if( otherEquivalenceClass == equivalenceClass) {
            return i;
          }
        }
     }
     return 0;
  }

  int getNumberOfPositiveEquivalenceClasses(Element element) {
    int numOfEquivalenceClasses = element.getEquivalenceClasses().size();
    EquivalenceClass otherEquivalenceClass = null;
    int n = -1;
    for( int i = 0; i < numOfEquivalenceClasses; i++) {
			otherEquivalenceClass = (EquivalenceClass) element.getEquivalenceClasses().get(i);
      if( otherEquivalenceClass.getState() == CMStateBean.STATE_POSITIVE) {
        n++;
      }
    }
    return n;
  }



  EquivalenceClass getPredecessorPositiveEquivalenceClass(EquivalenceClass equivalenceClass) {
    Element element = equivalenceClass.getLnkElement();
    int numOfEquivalenceClasses = element.getEquivalenceClasses().size();
    int before = element.getEquivalenceClasses().indexOf(equivalenceClass) - 1;
		EquivalenceClass otherEquivalenceClass = null;


    for( int i = before; i >= 0; i--) {
      otherEquivalenceClass = (EquivalenceClass) element.getEquivalenceClasses().get(i);
      if( otherEquivalenceClass.getState() == CMStateBean.STATE_POSITIVE) {
        return otherEquivalenceClass;
      }
    }
    return null;
  }



   public void setDependencyDefinitions(Dependency dependency) {
     if (selectedDependency!=null)
    	 removeListenerFromAll(selectedDependency);

	 this.selectedDependency = dependency;
	 if (selectedDependency!=null)
		 addModelListenerToAll(selectedDependency);
     deleteAllDependencyElementViews();
     createDependencyView(dependency);
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
void deleteAllDependencyElementViews(){
     int rowCount = cmGridModel.getRowCount();
     if( rowCount > 0) {
       cmGridModel.removeRows(0, rowCount);
     }
  }

	 void createDependencyView(Dependency dependency) {
       if( dependency == null) { return; }
	   int numElements = dependency.getLnkElements().size();
	   for( int i = 0; i < numElements; i++) {
				 Element element = (Element) dependency.getLnkElements().elementAt(i);
						 if(i == 0) {
							 createDependencyDefinitionsHeader(dependency,element);
						 }
						 else {
							 createElementView(element);
						 }
				 int numEquivalenceClasses = element.getEquivalenceClasses().size();
				 for(int j = 0; j < numEquivalenceClasses; j++) {
					 EquivalenceClass equivalenceClass = (EquivalenceClass) element.getEquivalenceClasses().get(j);
           // ONLY THE POSITIVE EQUIVALENCE CLASSES SHOULD BE CONSIDERED
           if(equivalenceClass.getState() == CMStateBean.STATE_POSITIVE) {
					 		createEquivalenceClassView(element,equivalenceClass,dependency);
           }
           //////////////////////////////////////////////////////////////////
				 }
				 if( i == (numElements - 1)) {
					 addFinalRows(1);
				 }
	   }
	 }

void addFinalRows(int count) {
    for(int i = 0; i < count; i++) {
		Vector row = new Vector(6);

	    row.addElement(new CMCellHeaderDefault(this,CMMessages.getString("LABEL_CAUSE_EFFECTS")+" >>   "));
	    ((DefaultStyleModel) getStyleModel()).setRenderer(CMCellHeaderDefault.class, new CMCellDefaultHeaderRenderer() {
	    	  @Override
	    	public Component getComponent(Object value, boolean isSelected, boolean hasFocus, boolean isEditable, int row, int column, GridContext context) {
	    		// TODO Auto-generated method stub
	    		Component c = super.getComponent(value, isSelected, hasFocus, isEditable, row, column,
	    				context);
	    		setHorizontalAlignment(JLabel.RIGHT);
	    		setBackground(SystemColor.control);
	    		setForeground(SystemColor.BLACK);
	    		setFont(new Font(null,Font.ITALIC,12));
	    		setBorder(BorderFactory.createEtchedBorder(1));
	    		return c;
	    	}
	      });
//	    row.addElement(new CMCellEmpty());
//		row.addElement(new CMCellEmpty());
//		row.addElement(new CMCellEmpty());
//		row.addElement(new CMCellEmpty());
//		row.addElement(new CMCellEmpty());
		cmGridModel.addRow(row);
    }
}
 void createDependencyDefinitionsHeader(Dependency dependency, Element element) {
    CMCellGroupHeaderElement cmCellGroupHeaderElement = new CMCellGroupHeaderElement(6); //I change 6 to 7
    cmCellGroupHeaderElement.addElement(new CMCellHeaderElementName(this,element) );

    cmCellGroupHeaderElement.addElement(new CMCellHeaderElementDescription(this,element) );
    cmCellGroupHeaderElement.addElement(new CMCellHeaderElementDescriptionEmpty());
    cmCellGroupHeaderElement.addElement(new CMCellHeaderElementDescriptionEmpty());
    cmCellGroupHeaderElement.addElement(new CMCellHeaderElementDescriptionEmpty());//fcastro_13092004
    cmCellGroupHeaderElement.addElement(new CMCellHeaderDependencyName(dependency));
//  My add
  //  cmCellGroupHeaderElement.addElement(new CMCellHeaderElementGuiObject(element) );

    CMCellGroupDataElement cmCellGroupDataElement = new CMCellGroupDataElement(6); //I change 6 to 7
    cmCellGroupDataElement.addElement( new CMCellElementName(this,element));

    cmCellGroupDataElement.addElement( new CMCellElementDescription(this,element));
    cmCellGroupDataElement.addElement(new CMCellElementDescriptionEmpty());
    cmCellGroupDataElement.addElement(new CMCellElementDescriptionEmpty());
    cmCellGroupDataElement.addElement(new CMCellElementDescriptionEmpty());//fcastro_13092004
    cmCellGroupDataElement.addElement( new CMCellDependencyName(dependency));
//  My add
  //  cmCellGroupDataElement.addElement( new CMCellElementGuiObject(element));

    cmGridModel.addRow(cmCellGroupHeaderElement);
    cmGridModel.addRow(cmCellGroupDataElement);
 }

 void createElementView(Element element) {
    CMCellGroupHeaderElement cmCellGroupHeaderElement = new CMCellGroupHeaderElement(6);//I change 6 to 7
    cmCellGroupHeaderElement.addElement(new CMCellHeaderElementName(this,element) );

    cmCellGroupHeaderElement.addElement(new CMCellHeaderElementDescription(this,element) );
    cmCellGroupHeaderElement.addElement(new CMCellHeaderElementDescriptionEmpty());
    cmCellGroupHeaderElement.addElement(new CMCellHeaderElementDescriptionEmpty());
    cmCellGroupHeaderElement.addElement(new CMCellHeaderElementDescriptionEmpty());//fcastro_13092004
    cmCellGroupHeaderElement.addElement(new CMCellEmpty());
//  My add
  //  cmCellGroupHeaderElement.addElement(new CMCellHeaderElementGuiObject(element) );



    CMCellGroupDataElement cmCellGroupDataElement = new CMCellGroupDataElement(6); //I change 6 to 7
    cmCellGroupDataElement.addElement( new CMCellElementName(this,element));

    cmCellGroupDataElement.addElement( new CMCellElementDescription(this,element));
    cmCellGroupDataElement.addElement( new CMCellElementDescriptionEmpty());
    cmCellGroupDataElement.addElement( new CMCellElementDescriptionEmpty());
    cmCellGroupDataElement.addElement( new CMCellElementDescriptionEmpty());//fcastro_13092004
    cmCellGroupDataElement.addElement( new CMCellEmpty());
//  My add
 //   cmCellGroupDataElement.addElement( new CMCellElementGuiObject(element));

    cmGridModel.addRow(cmCellGroupHeaderElement);
    cmGridModel.addRow(cmCellGroupDataElement);
 }

 Boolean areAllEquivalenceClassesDependent(Element element, Dependency dependency) {
  int numOfEquivalenceClasses = element.getEquivalenceClasses().size();
  for( int i = 0; i < numOfEquivalenceClasses; i++) {
    EquivalenceClass equivalenceClass = (EquivalenceClass)element.getEquivalenceClasses().get(i);
    if( equivalenceClass.getState() == CMStateBean.STATE_POSITIVE) {
        Vector equivalenceClasses = dependency.getLnkEquivalenceClasses();
        if( equivalenceClasses != null){
    	  if( !equivalenceClasses.contains(equivalenceClass)) {
      	    return new Boolean(false);
    	  }
        }
    }
  }
  return new Boolean(true);
 }

 Boolean isEquivalenceClassDependent(EquivalenceClass equivalenceClass, Dependency dependency) {
     List list = dependency.getLnkEquivalenceClasses();
 	if( (list != null) && list.contains(equivalenceClass) ) {
    return new Boolean(true);
  }
  else {
    return new Boolean(false);
  }
 }

 public boolean isFirstPositiveEquivalenceClass(EquivalenceClass equivalenceClass,Element element) {
   int numOfEquivalenceClasses = element.getEquivalenceClasses().size();
   EquivalenceClass otherEquivalenceClass = null;
   for( int i = 0; i < numOfEquivalenceClasses; i++) {
     otherEquivalenceClass =  element.getEquivalenceClasses().get(i);
     if( otherEquivalenceClass.getState() == CMStateBean.STATE_POSITIVE) {
       if( otherEquivalenceClass == equivalenceClass) {
         return true;
       }
       else {
         return false;
       }
     }
   }
   return false;
 }

 Vector createEquivalenceClassHeader(Element element, EquivalenceClass equivalenceClass, Dependency dependency) {
			Vector cmCellGroupHeaderEquivalenceClass = new Vector(6);
			cmCellGroupHeaderEquivalenceClass.addElement(new CMCellEmpty() );
			cmCellGroupHeaderEquivalenceClass.addElement(new CMCellHeaderEquivalenceClassName(this,equivalenceClass) );
			cmCellGroupHeaderEquivalenceClass.addElement(new CMCellHeaderEquivalenceClassState(this,equivalenceClass) );
			cmCellGroupHeaderEquivalenceClass.addElement(new CMCellHeaderEquivalenceClassValue(this,equivalenceClass) );
            cmCellGroupHeaderEquivalenceClass.addElement(new CMCellHeaderEquivalenceClassDescription(this,equivalenceClass));//fcastro_13092004
 			cmCellGroupHeaderEquivalenceClass.addElement(new CMCellSelectAllEquivalenceClassesOfElement(element,dependency,this));
			return cmCellGroupHeaderEquivalenceClass;
 }

 Vector createEquivalenceClassBody(Element element, EquivalenceClass equivalenceClass, Dependency dependency) {
		Vector cmCellGroupDataEquivalenceClass = new Vector(6);
		cmCellGroupDataEquivalenceClass.addElement(new CMCellEmpty() );
		cmCellGroupDataEquivalenceClass.addElement(new CMCellEquivalenceClassName(this,equivalenceClass) );
		cmCellGroupDataEquivalenceClass.addElement(new CMCellEquivalenceClassState(this,equivalenceClass) );
		cmCellGroupDataEquivalenceClass.addElement(new CMCellEquivalenceClassValue(this,equivalenceClass) );
        cmCellGroupDataEquivalenceClass.addElement(new CMCellEquivalenceClassDescription(this,equivalenceClass));//fcastro_13092004
    	//Boolean equivalenceClassIsDependent = isEquivalenceClassDependent(equivalenceClass, dependency);
		cmCellGroupDataEquivalenceClass.addElement(new CMCellSelectEquivalenceClass(this,equivalenceClass,dependency));
    return cmCellGroupDataEquivalenceClass;
 }

 void createEquivalenceClassView(Element element, EquivalenceClass equivalenceClass, Dependency dependency) {
    if( isFirstPositiveEquivalenceClass(equivalenceClass,element)) {
    //if( equivalenceClass.getLnkElement().getLnkEquivalenceClasses().indexOf(equivalenceClass) == 0 ) {
			CMCellGroupHeaderEquivalenceClass cmCellGroupHeaderEquivalenceClass = new CMCellGroupHeaderEquivalenceClass(6);
			cmCellGroupHeaderEquivalenceClass.addElement(new CMCellEmpty() );
			cmCellGroupHeaderEquivalenceClass.addElement(new CMCellHeaderEquivalenceClassName(this,equivalenceClass) );
			cmCellGroupHeaderEquivalenceClass.addElement(new CMCellHeaderEquivalenceClassState(this,equivalenceClass) );
			cmCellGroupHeaderEquivalenceClass.addElement(new CMCellHeaderEquivalenceClassValue(this,equivalenceClass) );
            cmCellGroupHeaderEquivalenceClass.addElement(new CMCellHeaderEquivalenceClassDescription(this,equivalenceClass));//fcastro_13092004
  		cmCellGroupHeaderEquivalenceClass.addElement(new CMCellSelectAllEquivalenceClassesOfElement(element,dependency,this));
			cmGridModel.addRow(cmCellGroupHeaderEquivalenceClass);
    }
		CMCellGroupDataEquivalenceClass cmCellGroupDataEquivalenceClass = new CMCellGroupDataEquivalenceClass(6);
		cmCellGroupDataEquivalenceClass.addElement(new CMCellEmpty() );
		cmCellGroupDataEquivalenceClass.addElement(new CMCellEquivalenceClassName(this,equivalenceClass) );
		cmCellGroupDataEquivalenceClass.addElement(new CMCellEquivalenceClassState(this,equivalenceClass) );
		cmCellGroupDataEquivalenceClass.addElement(new CMCellEquivalenceClassValue(this,equivalenceClass) );
        cmCellGroupDataEquivalenceClass.addElement(new CMCellEquivalenceClassDescription(this,equivalenceClass));//fcastro_13092004
     //  Boolean equivalenceClassIsDependent = isEquivalenceClassDependent(equivalenceClass, dependency);
		cmCellGroupDataEquivalenceClass.addElement(new CMCellSelectEquivalenceClass(this,equivalenceClass,dependency));
		cmGridModel.addRow(cmCellGroupDataEquivalenceClass);
 }

  public void thisComponentResized(ComponentEvent e) {
    this.setColumnWidth(2,1*6);
  }

  public void thisPropertyChange(PropertyChangeEvent e) {
    this.setColumnWidth(2,1*6);
  }

  public int getM_Width(){
          return m_Width;
      }

  public void setM_Width(int m_Width){
          this.m_Width = m_Width;
      }

  //////////////////////// GridModel ///////////////////////////////////////////
  public class CMGridModel extends GenericGridModel {
    public CMGridModel(int numRows, int numColumns) {
      super(numRows, numColumns);
    }


    public boolean isCellEditable(int row, int column) {
      Object obj = super.getValueAt(row,column);
      if(obj == null) {
        return false;
      }

      if( obj instanceof CMCellHeaderElementName/* || obj instanceof CMCellHeaderElementGuiObject */|| obj instanceof CMCellHeaderElementDescription ||
        obj instanceof CMCellHeaderEquivalenceClassName || obj instanceof CMCellHeaderEquivalenceClassState ||
        obj instanceof CMCellHeaderEquivalenceClassValue || obj instanceof CMCellHeaderDependencyName ||
        obj instanceof CMCellHeaderEquivalenceClassDescription ||//fcastro_13092004
         obj instanceof CMCellEquivalenceClassName || obj instanceof CMCellDependencyName ||
        obj instanceof CMCellEmpty || obj instanceof CMCellHeaderCombination ||
        obj instanceof CMCellHeaderEffectsInCombination || obj instanceof CMCellEffectsInCombination ||
        obj instanceof CMCellEquivalenceClassInCombination
        || obj instanceof CMCellEquivalenceClassState) {
        return false;
      }
      else {
        return true;
      }
    }
    public Object getCellObjectAt(int row, int column) {
      if(this.getRowCount() > row && this.getColumnCount() > column) {
        if( row >= 0 && column >= 0) {
          return super.getValueAt(row,column);
        }
        else {
          return null;
        }
      }
      else {
        return null;
      }
    }
    public void setValueAt(Object obj,int row, int column) {
      if( this.getRowCount() > row && this.getColumnCount() > column) {
        if( row >= 0 && column >= 0) {
          super.setValueAt(obj,row,column);
        }
      }
    }
    public Object getValueAt1(int row, int column) {
      Object obj = null;
      if( this.getRowCount() > row && this.getColumnCount() > column) {
        if( row >= 0 && column >= 0) {
        obj = super.getValueAt(row,column);
        }
      }
      if( obj == null) { return null; }
      if( obj instanceof CMCellEmpty ) { return null; }

      else if( obj instanceof CMCellHeaderElementName) { return obj;}     else if( obj instanceof CMCellSelectEquivalenceClass) {
        return ((CMCellSelectEquivalenceClass)obj).getSelected();
      }
      else if( obj instanceof CMCellSelectAllEquivalenceClassesOfElement) {
        return ((CMCellSelectAllEquivalenceClassesOfElement)obj).getSelected();
      }

      else {
        return obj;
      }
    }
  }

  //////////////////////// SpanModel ///////////////////////////////////////////
  public class CMSpanModel extends AbstractDirectSpanModel {
    CMDependencyElementViews.CMGridModel model = null;
    public CMSpanModel(CMDependencyElementViews.CMGridModel m) {
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
								//return 3;//OLD fcastro_13092004
                                return 4;//fcastro_13092004
							}
							public Object getIdentifier() {
								return null;
							}
						};
        }

        if (obj instanceof CMCellHeaderDefault)
        	return new ExtentCell() {
			public int getRow() {
				return row;
			}
			public int getRowCount() {
				return 1;
			}
			public int getColumn() {
				return 0;
			}
			public int getColumnCount() {
                return 6;
			}
			public Object getIdentifier() {
				return null;
			}
		};
		return null;
    }
    public boolean isEmpty() {
        return false;
    }
  }

  //////////////////////////////////////////////////////////////////////////////

  class CMStyleModel extends DefaultStyleModel {
	  CMCellRenderer defaultCellRenderer;
    public CMStyleModel(CMDependencyElementViews.CMGridModel model) {
      this.defaultCellRenderer =  	new CMCellRenderer(model);
      this.setRenderer(String.class, defaultCellRenderer);

    /*  this.setRenderer(CMCellHeaderElementName.class,defaultCellRenderer);
      this.setRenderer(CMCellHeaderElementDescription.class,defaultCellRenderer);*/

    }
  /////////////////////////////////////////////////////////////////////////////

    public class CMCellRenderer extends DefaultGridCellRenderer {

      CMDependencyElementViews.CMGridModel model = null;
      public CMCellRenderer(CMDependencyElementViews.CMGridModel model) {
        this.model = model;
      }

      public Component getComponent(Object value, boolean isSelected, boolean hasFocus, boolean isEditable, int row, int column, GridContext context) {
            Component c = super.getComponent(value, isSelected, hasFocus, isEditable, row, column, context);

            Object obj = model.getCellObjectAt(row,column);

            if ( obj instanceof CMCellHeaderElementName /*|| obj instanceof CMCellHeaderElementGuiObject*/ || obj instanceof CMCellHeaderElementDescription) {
               c.setBackground(new Color(36,38,116));
               c.setForeground(new Color(252,254,252));
							 this.setHorizontalAlignment(JLabel.LEFT);
							 c.setFont(new Font("Dialog",Font.PLAIN,12));
               if( !isSelected) {
									 this.setBorder(BorderFactory.createRaisedBevelBorder());
               }

            }
            else if( obj instanceof CMCellHeaderEquivalenceClassName || obj instanceof CMCellHeaderEquivalenceClassState ||
                 obj instanceof CMCellHeaderEquivalenceClassValue || obj instanceof CMCellHeaderDependencyName ||
                 obj instanceof CMCellHeaderCombination || obj instanceof CMCellHeaderEffectsInCombination ||
                 obj instanceof CMCellHeaderEquivalenceClassDescription ||//fcastro_13092004
                 obj instanceof CMCellSelectAllEquivalenceClassesOfElement) {
               c.setBackground(new Color(156,154,252));
               c.setForeground(new Color(252,254,252));
							 c.setFont(new Font("Dialog",Font.PLAIN,12));
               if( !isSelected) {
									 this.setBorder(BorderFactory.createRaisedBevelBorder());
               }

            }
            else if( obj instanceof CMCellEquivalenceClassName || obj instanceof CMCellDependencyName ||
                     obj instanceof CMCellEffectsInCombination) {
              c.setBackground(new Color(235, 235, 228));
            }
            else if( obj instanceof CMCellEquivalenceClassInCombination) {
              c.setFont(new Font("Serif",Font.BOLD,12));
            }
//HCanedo_22112004_Begin
			if(obj instanceof CMCellEquivalenceClassName)
            {
                c.setForeground(new Color(0,0,0));
		  		c.setFont(new Font("Dialog",Font.PLAIN,12));


            }

            else if(obj instanceof CMCellElementName)
            {
                c.setForeground(new Color(0,0,0));
		   		 c.setFont(new Font("Dialog",Font.PLAIN,12));

            }
//HCanedo_22112004_End

            return c;
          }

    }
	public CMCellRenderer getDefaultCellRenderer() {
		return defaultCellRenderer;
	}
	public void setDefaultCellRenderer(CMCellRenderer defaultCellRenderer) {
		this.defaultCellRenderer = defaultCellRenderer;
	}
  }

public CMGridModel getCmGridModel() {
	return cmGridModel;
}

public void setCmGridModel(CMGridModel cmGridModel) {
	this.cmGridModel = cmGridModel;
}

public CMFrameView getCmFrame() {
	return cmFrame;
}

public CMPanelDependencies getCmPanelDependencies() {
	return cmPanelDependencies;
}
public void handleCMModelChange(CMModelEvent evt) {
	// TODO Auto-generated method stub
	if (evt.getChangedField() == CMField.ELEMENTS){
		if (selectedDependency!=null){
		deleteAllDependencyElementViews();
	     createDependencyView(selectedDependency);
		}
	}

	if (evt.getChangedField()==CMField.STATE) {
		EquivalenceClass equivalenceClass = (EquivalenceClass) evt.getSource();
		if (selectedDependency!=null){
			deleteAllDependencyElementViews();
			createDependencyView(selectedDependency);
		}
	}
	if (evt.getSource() instanceof Element && evt.getChangedField() == CMField.EQUIVALENCE_CLASSES)
	{
		if (selectedDependency!=null){
		removeListenerFromAll(selectedDependency);
		addModelListenerToAll(selectedDependency);
		deleteAllDependencyElementViews();
		createDependencyView(selectedDependency);
		}
	}
}

  //////////////////////////////////////////////////////////////////////////////

}
