/* Developed by BUSINESS SOFTWARE INNOVATIONS.  .. Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved..*/

package bi.view.stdcombinationviews;
import java.awt.Color;
import java.awt.Component;
import java.awt.Event;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JViewport;

import model.CMField;
import model.StdCombination;
import model.Structure;
import model.util.CMModelEvent;
import model.util.CMModelListener;
import bi.controller.CombinationManager;
import bi.view.actions.CMAction;
import bi.view.cells.CMCellDependencyDescription;
import bi.view.cells.CMCellDependencyName;
import bi.view.cells.CMStdCombinationDescriptionCellView;
import bi.view.cells.CMStdCombinationDescriptionView;
import bi.view.cells.CMStdCombinationNameView;
import bi.view.grids.CMBaseJSmartGrid;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMFrameView;

import com.eliad.model.GenericGridModel;
import com.eliad.model.GridContext;
import com.eliad.model.GridSelectionEvent;
import com.eliad.model.defaults.DefaultGridCellRenderer;
import com.eliad.model.defaults.DefaultStyleModel;
import com.eliad.swing.GridEvent;
import com.eliad.util.RulerConstants;

public class CMDescriptionStdCombinationViews extends CMBaseJSmartGrid implements CMModelListener {
 		private CMGridModel m_CMGridModel;
		private CMFrameView m_CMFrame = null;
		private Structure m_Structure;
        private boolean selectionMessageReceived =  false;

        private FocusListener focusListener = new FocusAdapter(){
        	public void focusLost(FocusEvent e) {
        		getM_CMStdCombinationStructureView().getM_CMElementAndStdCombinationViews().getLnkCMStdCombinationViews().repaint();
           }
        };
    /**
     * @directed
     */
    private CMStdCombinationStructureView m_CMStdCombinationStructureView;

    public CMDescriptionStdCombinationViews(CMFrameView p_CMFrame, CMStdCombinationStructureView p_CMStdCombinationStructureView) {
      super();
      m_CMFrame = p_CMFrame;
      p_CMFrame.getKeyEventIntercepter().setM_CMDescriptionStdCombinationViews(this);//fcastro_13092004
      m_CMStdCombinationStructureView = p_CMStdCombinationStructureView;
      initGUI();
      this.addFocusListener(focusListener);
      initializeCellRenderers();
    }

public void initGUI() {
		m_CMGridModel = new CMGridModel(0,2);
        setModels();
        setUIProperties();
		addEventListeners();
}
    public JViewport getViewport1() {
      return getViewport();    // getViewport is not public in JSmartGrid
    }
    //fcastro_13092004_begin
  public boolean isDeletePossible(){
        int row = this.getSelectionModel().getLeadRow();
        int column = this.getSelectionModel().getLeadColumn();
        Object obj = this.m_CMGridModel.getCellObjectAt(row,column);
        if(obj!=null){
        if(obj instanceof CMStdCombinationNameView || obj instanceof CMStdCombinationDescriptionCellView){
            return true;
        }
        }
        return false;
    }
  //fcastro_13092004_end
public void setM_Structure(Structure p_Structure) {
    m_Structure = p_Structure;
    if (m_Structure!=null)
    	m_Structure.addModelListener(this);
    deleteAllViews();
    addCMStdCombinationViews(m_Structure.getCombinations());
    selectCMStdCombinationView(0);
}

public Structure getM_Structure() {
    return m_Structure;
}

public void setModels() {
				
				this.setModel(m_CMGridModel);
}
public void setUIProperties() {
				this.setOpaque(false);
				this.setColumnResizable(true);

        this.setAutoResizeMode(RulerConstants.HORIZONTAL);
        this.setGridColor(new Color(127,157,185));  // Read Only Grid Color
    		this.setSelectionCellBorder(BorderFactory.createLineBorder(Color.orange,2));
				this.setFocusHighlightBorder(BorderFactory.createLineBorder(Color.orange,2));
    		this.setSelectionBackgroundColor(Color.orange);
    		this.setSelectionForegroundColor(Color.black);
				this.setSelectionUnit(com.eliad.swing.JSmartGrid.UNIT_ROW);
				this.setSelectionPolicy(com.eliad.swing.JSmartGrid.POLICY_SINGLE);
        ////////////////////////////////////////////////////////
}
public void addEventListeners() {
				this.addGridListener(new com.eliad.swing.GridAdapter() {
					public void gridMouseClicked(GridEvent e) {

						eventGridMouseClicked(e);
					}
				});

}
//fcastro_25082004_begin
public void selectionChangedInGridView(int gridColumnSelected){

    if(gridColumnSelected>=0 && gridColumnSelected<this.m_Structure.getCombinations().size()){
        if(gridColumnSelected != this.getSelectionModel().getLeadRow()){
		this.changeSelection(gridColumnSelected,0,false,false);
        }
    }
}
//fcastro_25082004_end
  void eventGridMouseClicked(GridEvent e) {
    MouseEvent mouseEvent = (MouseEvent) e.getSourceEvent();
    int row = e.getRow();
    int column = e.getColumn();

//  hmendez_11112005_begin
	//if(mouseEvent.getClickCount() == 2)
    if((mouseEvent.getClickCount() == 2)&&(mouseEvent.getButton()==1)) {
//hmendez_11112005_end

     CMAction.STDCOMBINATION_EDIT.getAction().actionPerformed(null);
	this.getM_CMStdCombinationStructureView().getM_CMElementAndStdCombinationViews().getLnkCMStdCombinationViews().setSelectionMessageReceived(true);//fcastro_02092004
      this.getM_CMStdCombinationStructureView().getM_CMElementAndStdCombinationViews().getLnkCMStdCombinationViews().selectionChangedInDescriptionView(row);
    }
    else {
				if(e.getSourceEvent().getModifiers() == Event.META_MASK) {
						if( row >= 0 && column >= 0) {
							this.changeSelection(row,column,false,false);
						}
            //m_CMStdCombinationStructureView.getM_CMElementAndStdCombinationViews().getLnkCMStdCombinationViews().getCmPopupMenuStdCombinations().show(this, mouseEvent.getX(), mouseEvent.getY());
				}
                else{
                    this.getM_CMStdCombinationStructureView().getM_CMElementAndStdCombinationViews().getLnkCMStdCombinationViews().setSelectionMessageReceived(true);//fcastro_02092004
                    this.getM_CMStdCombinationStructureView().getM_CMElementAndStdCombinationViews().getLnkCMStdCombinationViews().selectionChangedInDescriptionView(row);
                }
    }
  }

public void selectCMStdCombinationView(int p_index){
			if( p_index >= 0 && this.getRowCount() > 0) {
					this.changeSelection(p_index,0, false, false);
			}
}


public CMStdCombinationDescriptionView createCMStdCombinationDescriptionView(StdCombination p_StdCombination) {
      CMStdCombinationDescriptionView stdCombinationDescriptionView = new CMStdCombinationDescriptionView(p_StdCombination);
      CMStdCombinationNameView nameView = new CMStdCombinationNameView(this,p_StdCombination);
      CMStdCombinationDescriptionCellView descriptionCellView = new CMStdCombinationDescriptionCellView(this,p_StdCombination);
      stdCombinationDescriptionView.setM_CMStdCombinationNameView(nameView);
      stdCombinationDescriptionView.setM_CMStdCombinationDescriptionCellView(descriptionCellView);
      stdCombinationDescriptionView.addElement(nameView);
      stdCombinationDescriptionView.addElement(descriptionCellView);
      return stdCombinationDescriptionView;
}

public Vector createCMStdCombinationViews(Vector p_effects){
  int numOfStdCombinations = p_effects.size();
  Vector v = new Vector(numOfStdCombinations);
  StdCombination stdCombination = null;
  for( int i = 0; i < numOfStdCombinations; i++) {
    stdCombination = (StdCombination) p_effects.elementAt(i);
		v.addElement(createCMStdCombinationDescriptionView(stdCombination));
  }
  return v;
}

public void addCMStdCombinationView(CMStdCombinationDescriptionView p_CMStdCombinationView) {
  this.m_CMGridModel.addRow(p_CMStdCombinationView);
  int newSelectionIndex = this.getRowCount()-1;
	selectCMStdCombinationView(newSelectionIndex);
}



public void addCMStdCombinationViews(List stdCombinations) {
  int numOfEffects = stdCombinations.size();
  StdCombination stdCombination = null;
  for( int i = 0; i < numOfEffects; i++) {
    stdCombination = (StdCombination) stdCombinations.get(i);
    addCMStdCombinationView(createCMStdCombinationDescriptionView(stdCombination));
  }
}

  public void updateAll(List p_StdCombinations) {
    deleteAllViews();
    addCMStdCombinationViews(p_StdCombinations);
  }

  void deleteAllViews() {
    int numOfRows = m_CMGridModel.getRowCount();
    int numOfStdCombinationViews = numOfRows;
    deleteStdCombinationView(0,numOfStdCombinationViews);
  }

  void deleteStdCombinationView(int index,int count) {
    m_CMGridModel.removeRows(index,count);
  }


public int getTheNextCMStdCombinationView(int p_index) {
  int next = 0;
  if( p_index > 0) {
    next = p_index - 1;
    return next;
  }
  else if (p_index == 0 && m_CMGridModel.getRowCount() > 0) {
    next = 0;
    return next;
  }
  else {
    return -1;
  }
}

public void fireEventStdCombinationAdded(StdCombination p_Effect) {
  CMStdCombinationDescriptionView v = createCMStdCombinationDescriptionView(p_Effect);
  this.addCMStdCombinationView(v);
}

//fcastro_24082004_begin
public void fireEventStdCombinationInserted(StdCombination p_StdCombination,int index){
    CMStdCombinationDescriptionView v = createCMStdCombinationDescriptionView(p_StdCombination);
   	this.insertStdCombinationViewAt(v,index);
}
public void insertStdCombinationViewAt(Vector v,int index){
    ((GenericGridModel)this.m_CMGridModel).insertRow(index,v);
  	selectCMStdCombinationView(index);

}


public int getSelectedCMStdCombinationView() {
      int selectedIndex = 0;
      int[] selectedRows = this.getSelectedRows();
      int numSelectedRows = selectedRows.length;
      if( numSelectedRows > 0) {
        return selectedRows[0];
      }
      else {
        return -1;
      }
}


  public void updateChangedRow(int row){
    this.m_CMGridModel.fireGridRowsChanged(row,1);
  }


  public CMStdCombinationStructureView getM_CMStdCombinationStructureView(){
          return m_CMStdCombinationStructureView;
      }

  public void setM_CMStdCombinationStructureView(CMStdCombinationStructureView m_CMStdCombinationStructureView){
          this.m_CMStdCombinationStructureView = m_CMStdCombinationStructureView;
      }

  public void rangeChanged(GridSelectionEvent e){
  
    super.rangeChanged(e);
    if(!e.isAdjusting()){
		if(isSelectionMessageReceived()){
            	this.setSelectionMessageReceived(false);
        }
        else{
        	this.getM_CMStdCombinationStructureView().getM_CMElementAndStdCombinationViews().getLnkCMStdCombinationViews().setSelectionMessageReceived(true);
			this.m_CMStdCombinationStructureView.getM_CMElementAndStdCombinationViews().getLnkCMStdCombinationViews().selectionChangedInDescriptionView(this.getFirstSelectedRow());
        }
    }

  }

  public boolean isSelectionMessageReceived(){
        return selectionMessageReceived;
    }

  public void setSelectionMessageReceived(boolean selectionMessageReceived){
          this.selectionMessageReceived = selectionMessageReceived;
      }

  //////////////////////// Grid Model /////////////////////////////////////////
  public class CMGridModel extends GenericGridModel {
    public CMGridModel(int numRows, int numColumns) {
      super(numRows, numColumns);
    }
    public boolean isCellEditable(int row, int column) {
      return false;
    }

    public Object getCellObjectAt(int row, int column) {
      if( row >= 0 && column >= 0) {
        return super.getValueAt(row,column);
      }
      else {
        return null;
      }
    }
  }

  
@Override
protected HashMap<Class, Component> getCellClasses() {
	HashMap<Class,Component>  map = new HashMap<Class,Component> ();
	map.put(CMStdCombinationDescriptionCellView.class,null);
	map.put(CMStdCombinationNameView.class,null);
	return map;
}

public void handleCMModelChange(CMModelEvent evt) {
	
	if (evt.getSource() instanceof Structure)
		if (evt.getChangedField() == CMField.STDCOMBINATIONS){
			deleteAllViews();
		    addCMStdCombinationViews(m_Structure.getCombinations());
		}
			
}

}
