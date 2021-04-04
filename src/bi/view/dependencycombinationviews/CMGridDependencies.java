/*******************************************************************************
Developed by BUSINESS SOFTWARE INNOVATIONS. .
Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
*******************************************************************************/

package bi.view.dependencycombinationviews;

import java.awt.Color;
import java.awt.Component;
import java.awt.Event;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JViewport;

import model.CMField;
import model.Dependency;
import model.Structure;
import model.util.CMModelEvent;
import model.util.CMModelListener;
import bi.controller.DependencyManager;
import bi.view.actions.CMAction;
import bi.view.cells.CMCellDependency;
import bi.view.cells.CMCellDependencyDescription;
import bi.view.cells.CMCellDependencyName;
import bi.view.grids.CMBaseJSmartGrid;
import bi.view.mainframeviews.CMApplication;
import bi.view.mainframeviews.CMFrameView;

import com.eliad.model.GenericGridModel;
import com.eliad.model.GridSelectionEvent;
import com.eliad.swing.GridEvent;
import com.eliad.util.RulerConstants;


/**
 * Title:        CaseMaker
 * Description:  Test tool for the automatic generation of test cases.
 * Copyright:    Copyright (c) 2003
 * Company:      BUSINESS SOFTWARE INNOVATIONS
 * @author Gary Rueda Sandoval
 * @version 1.0
 */

public class CMGridDependencies extends CMBaseJSmartGrid implements CMModelListener{
  //CMPopupMenuDependencies cmPopupMenuDependencies = null;
  CMGridModel cmGridModel = null;
  CMPanelDependencies cmPanelDependencies = null;
  CMCombinationViews m_CMCombinationViews = null;
  CMDependencyElementViews   m_CMDependencyView   = null;
  DependencyManager m_DependencyManager = null;
  Structure m_Structure = null;
  int m_RowCount;
  int m_ColumnCount;
  CMFrameView m_Frame;

  public CMGridDependencies(CMPanelDependencies cmPanelDependencies, CMFrameView cmFrame,int rowCount, int columnCount,
    												CMDependencyElementViews p_CMDependencyView, CMCombinationViews p_CMCombinationViews /*, CMPopupMenuDependencies  p_cmPopupMenuDependencies*/) {
	super();
    this.cmPanelDependencies = cmPanelDependencies;
	m_CMDependencyView = p_CMDependencyView;
    m_CMCombinationViews = p_CMCombinationViews;
    //cmPopupMenuDependencies = p_cmPopupMenuDependencies;
    m_RowCount = rowCount;
    m_ColumnCount = columnCount;
    m_Frame = cmFrame;
    cmFrame.getKeyEventIntercepter().setM_CMGridDependencies(this);//fcastro_13092004
    initGUI();
  }

  public void initGUI() {
    cmGridModel = new CMGridModel(m_RowCount,m_ColumnCount);
    this.setModel(cmGridModel);

    this.setOpaque(false);
		this.setColumnResizable(true);
    this.setAutoResizeMode(RulerConstants.HORIZONTAL);
    this.setSelectionCellBorder(BorderFactory.createLineBorder(Color.orange,2));
		this.setFocusHighlightBorder(BorderFactory.createLineBorder(Color.orange,2));
    this.setSelectionBackgroundColor(Color.orange);
    this.setSelectionForegroundColor(Color.black);
    this.setSelectionUnit(com.eliad.swing.JSmartGrid.UNIT_ROW);
    this.setSelectionPolicy(com.eliad.swing.JSmartGrid.POLICY_SINGLE);
    this.setGridColor(new Color(127,157,185));  // Read Only Grid Color
///////////////////////////////////////////////////////////////////////////////
    this.addGridListener(new com.eliad.swing.GridAdapter() {
      public void gridMouseClicked(GridEvent e) {
        eventGridMouseClicked(e);
      }
    });
    m_DependencyManager = m_Frame.getCmApplication().getSessionManager().getDependencyManager();
    this.initializeCellRenderers();
      }

    public JViewport getViewport1() {
      return getViewport();    // getViewport is not public in JSmartGrid
    }
@Override
protected HashMap<Class,Component> getCellClasses() {
	HashMap<Class,Component>  map = new HashMap<Class,Component> ();
	map.put(CMCellDependencyName.class,null);
	map.put(CMCellDependencyDescription.class,null);
	return map;

}
  public void setM_Structure(Structure p_Structure) {
    m_Structure = p_Structure;
    m_Structure.addModelListener(this);
    initialize(m_Structure);
  }

  public Structure getM_Structure() {
    return m_Structure;
  }

  public void deleteAllDependencyViews() {
    int numOfDependencyViews = this.getRowCount();
    cmGridModel.removeRows(0,numOfDependencyViews);
  }

  void initialize(Structure structure) {
    deleteAllDependencyViews();
    addDependencyViews(structure);
    int numOfDependencyViews = this.getRowCount();
    if( numOfDependencyViews > 0) {
    		this.changeSelection(0,0, false, false);
    }
  }

  void addDependencyViews(Structure structure) {
    int numOfDependencies = structure.getLnkDependencies().size();
    for(int i = 0; i < numOfDependencies; i++) {
      Dependency dependency = (Dependency) structure.getLnkDependencies().elementAt(i);
      addDependencyView(dependency);
    }
  }
  void addDependencyView(Dependency dependency) {
    Vector dependencyView = createDependencyView(dependency);
    cmGridModel.addRow(dependencyView);
  }

   
    //TODO refactor to structure manager
    public void addDependencyAt(Dependency p_Dependency, int p_Index) {
        Vector dependencyView = createDependencyView(p_Dependency);
        cmGridModel.insertRow(p_Index, dependencyView);
        m_Structure.getLnkDependencies().insertElementAt(p_Dependency, p_Index);
        //smoreno parent reference
        p_Dependency.setLnkStructure(m_Structure);
        this.changeSelection(p_Index, 0, false, false);
    }


  Vector createDependencyView(Dependency dependency) {
      Vector dependencyView = new Vector(2);
      dependencyView.add(new CMCellDependencyName(dependency));
      dependencyView.add(new CMCellDependencyDescription(dependency));
      return dependencyView;
  }
//fcastro_02092004_begin
  void eventGridMouseClicked(GridEvent e) {
    int row = e.getRow();
    int column = e.getColumn();
    CMAction.DEPENDENCY_GENERATE_COMB.setEnabled(true);
    CMAction.DEPENDENCY_CREATE.setEnabled(true);
	if(((MouseEvent)e.getSourceEvent()).getClickCount() == 2){
        if(m_Structure.getLnkDependencies()!=null){
			if(row>=0 && row<m_Structure.getLnkDependencies().size()){
				CMAction.DEPENDENCY_EDIT.getAction().actionPerformed(null);
    		}
        }
    }
    if(e.getSourceEvent().getModifiers() == Event.META_MASK) {
        if( row >= 0 && column >= 0) {
        	this.changeSelection(row,column,false,false);
        }
        MouseEvent mouseEvent = (MouseEvent) e.getSourceEvent();

        m_Frame.getPopupMenuDependency().show(this, mouseEvent.getX(), mouseEvent.getY());

    }
  }
  //fcastro_02092004_end


  public void rangeChanged(GridSelectionEvent event) {
    int selectedDependencyIndex = 0;
    if( !event.isAdjusting() ) {
      int[] selectedRows = this.getSelectedRows();
      int numSelectedRows = this.getSelectedRowCount();
      if( numSelectedRows > 0) {
        selectedDependencyIndex = selectedRows[0];
        Object obj = cmGridModel.getCellObjectAt(selectedDependencyIndex,0);
        if( obj != null) {
          CMApplication.frame.setWaitCursor(true);
          Dependency selectedDependency = ((CMCellDependency)obj).getDependency();
          m_CMDependencyView.setDependencyDefinitions(selectedDependency);
          m_CMCombinationViews.setDependencyDefinitions(selectedDependency);
          CMApplication.frame.setWaitCursor(false);
        }
      }
      else {
          m_CMDependencyView.setDependencyDefinitions(null);
          m_CMCombinationViews.setDependencyDefinitions(null);
      }
    }
    super.rangeChanged(event);
  }


  public void updateDependencies() {
    int selectedIndex = this.getSelectionModel().getLeadRow();
    this.clearSelection();
    this.changeSelection(selectedIndex, 0, false, false);
  }

public void changeSelection(Dependency dependency) {
	// TODO Auto-generated method stub
	//super.changeSelection(arg0, arg1, arg2, arg3);
	for (int i=0; i<this.getModel().getRowCount();i++){
		if (getCellObjectAt(i, 0) instanceof CMCellDependencyName){
			if (((CMCellDependencyName)getCellObjectAt(i, 0)).getDependency() == dependency)
				selectDependencyView(i);
		}
	}
}
public Dependency getSelectedDependency() {
    int selectedColumn = this.getSelectionModel().getLeadColumn();
		int selectedRow = this.getSelectionModel().getLeadRow();
    if( selectedRow >= 0 && selectedColumn >= 0) {
			Object obj = cmGridModel.getCellObjectAt(selectedRow, selectedColumn);
      if( obj != null) {
        if( obj instanceof CMCellDependencyName || obj instanceof CMCellDependencyDescription) {
 					Dependency d = ((CMCellDependency)obj).getDependency();
          return d;
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

//fcastro_13092004_begin
public boolean isDeletePossible(){
    int row =this.getSelectionModel().getLeadRow();
    int column = this.getSelectionModel().getLeadColumn();
    Object obj = this.cmGridModel.getCellObjectAt(row,column);
    if(obj!=null){
    if(obj instanceof CMCellDependencyName || obj instanceof CMCellDependencyDescription){
        return true;
    }
    }
    return false;
}
//fcastro_13092004_end



public void selectDependencyView(int p_index){
			if( p_index >= 0 && this.getRowCount() > 0) {
					this.changeSelection(p_index,0, false, false);
			}
}

public void deleteDependencyView(int index) {
  cmGridModel.removeRows(index,1);
}

   public void updateAllViewsDependencies()
   {
    	initialize(m_Structure);
   }

   public void undoSortedDependencies(int p_Index, Vector oldLnkDependecies)
   {
   	m_Frame.setWaitCursor(true);
    	m_Structure.getLnkDependencies().removeAllElements();
        for(int i=0; i< oldLnkDependecies.size();i++)
        {
            m_Structure.getLnkDependencies().addElement((Dependency) oldLnkDependecies.elementAt(i));
        }
        updateAllViewsDependencies();
        this.clearSelection();
        changeSelection(p_Index, 0, false, false);
		m_Frame.setWaitCursor(false);
   }
   public void redoSortedDependencies()
   {
    m_Frame.setWaitCursor(true);
    	Structure actualStructure = getM_Structure();
            int sizeDependencies = actualStructure.getLnkDependencies().size();
            int maxIdDependency = cmPanelDependencies.getMaxIDDependency(actualStructure);
            if (maxIdDependency > 0) {
                Object orderDependencies[] = new Object[maxIdDependency];
                for (int i = 0; i < sizeDependencies; i++) {
                    Dependency actualDependency = (Dependency)actualStructure.getLnkDependencies().elementAt(i);
                    int idDependecy = actualDependency.getId() - 1;
                    orderDependencies[idDependecy] = actualDependency;
                }
                actualStructure.getLnkDependencies().removeAllElements();
                for (int j = 0; j < orderDependencies.length; j++) {
                    if(orderDependencies[j]!= null)
                      actualStructure.getLnkDependencies().addElement((Dependency) orderDependencies[j]);
                }
					updateAllViewsDependencies();
            }
            m_Frame.setWaitCursor(false);
   }
//HCanedo_30112004_End


  //////////////////////// Grid Model /////////////////////////////////////////
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

  }



public CMGridModel getCmGridModel() {
	return cmGridModel;
}

public void setCmGridModel(CMGridModel cmGridModel) {
	this.cmGridModel = cmGridModel;
}

public CMDependencyElementViews getCMDependencyView() {
	return m_CMDependencyView;
}

public CMCombinationViews getCMCombinationViews() {
	return m_CMCombinationViews;
}

public void handleCMModelChange(CMModelEvent evt) {
	if (evt.getChangedField()==CMField.DEPENDENCIES)
			initialize(getM_Structure());

}
}