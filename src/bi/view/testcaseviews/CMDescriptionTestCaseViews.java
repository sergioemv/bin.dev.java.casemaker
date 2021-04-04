/* Developed by BUSINESS SOFTWARE INNOVATIONS.  .. Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved..*/

package bi.view.testcaseviews;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Toolkit;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JViewport;

import model.CMField;
import model.Structure;
import model.TestCase;
import model.util.CMModelEvent;
import model.util.CMModelListener;
import bi.controller.TestCaseManager;
import bi.view.actions.CMAction;
import bi.view.cells.CMTestCaseDescriptionCellView;
import bi.view.cells.CMTestCaseDescriptionView;
import bi.view.cells.CMTestCaseNameView;
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

public class CMDescriptionTestCaseViews extends CMBaseJSmartGrid  implements CMModelListener{
    private TestCaseManager m_TestCaseManager = null;
		private CMGridModel m_CMGridModel;
		private CMStyleModel m_CMStyleModel;
    private CMFrameView m_CMFrame = null;
    private Structure m_Structure;

    private FocusListener focusListener = new FocusAdapter(){
    	public void focusGained(FocusEvent e) {
    		getM_CMTestCaseStructureView().getM_CMElementAndTestCaseViews().getLnkCMTestCaseViews().repaint();
    	}

    };

    /**
     * @directed
     */
    private CMTestCaseStructureView m_CMTestCaseStructureView;
    private boolean selectionMessageReceived = false;

    public CMDescriptionTestCaseViews(CMFrameView p_CMFrame, CMTestCaseStructureView p_CMTestCaseStructureView) {
      super();
      m_CMFrame = p_CMFrame;
      p_CMFrame.getKeyEventIntercepter().setM_CMDescriptionTestCaseViews(this);
      m_CMTestCaseStructureView = p_CMTestCaseStructureView;
      initGUI();
      this.addFocusListener(focusListener);
    }

public void initGUI() {
			  m_CMGridModel = new CMGridModel(0,2);
				m_CMStyleModel = new CMStyleModel();
        setModels();
        setUIProperties();
				addEventListeners();
}

//fcastro_13092004_begin
public boolean isDeletePossible(){
    int row =this.getSelectionModel().getLeadRow();
    int column = this.getSelectionModel().getLeadColumn();
    Object obj = this.m_CMGridModel.getCellObjectAt(row,column);
    if(obj!=null){
    if(obj instanceof CMTestCaseNameView || obj instanceof CMTestCaseDescriptionView){

            return true;

    }
    }
    return false;
 }
//fcastro_13092004_end
    public JViewport getViewport1() {
      return getViewport();    // getViewport is not public in JSmartGrid
    }


public void setM_Structure(Structure p_Structure) {
    m_Structure = p_Structure;
    m_Structure.addModelListener(this);
    deleteAllTestCaseViews();
    addCMTestCaseViews(m_Structure.getLnkTestCases());
    selectCMTestCaseView(0);
}

public void deleteAllDescriptionTestCaseViews() {
    int numOfViews = this.getRowCount();
    this.m_CMGridModel.removeRows(0,numOfViews);
}

public Structure getM_Structure() {
    return m_Structure;
}

public void setModels() {
				this.setStyleModel(m_CMStyleModel);
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
                        //m_CMTestCaseStructureView.getM_CMElementAndTestCaseViews().getLnkCMTestCaseViews().selectionChangedInDescriptionView(e.getRow());//fcastro_25082004
						eventGridMouseClicked(e);
					}
				});

}
//fcastro_25082004_begin
public void selectionChangedInGridView(int gridColumnSelected){
	if(gridColumnSelected>=0&&gridColumnSelected<this.m_Structure.getLnkTestCases().size()){
		if(gridColumnSelected!= this.getSelectionModel().getLeadRow()){
    		this.changeSelection(gridColumnSelected,0,false,false);
        }
    }
}

public void fireEventTestCaseInserted(int index,TestCase testCase){
	CMTestCaseDescriptionView v = createCMTestCaseDescriptionView(testCase);
  	m_CMGridModel.insertRow(index,v);
    selectCMTestCaseView(index);
}

//fcastro_25082004_end

//fcastro_02092004_begin
public void rangeChanged(GridSelectionEvent e){
    super.rangeChanged(e);
    if(!e.isAdjusting()){
		if(isSelectionMessageReceived()){
            	this.setSelectionMessageReceived(false);
        }
        else{
            this.getM_CMTestCaseStructureView().getM_CMElementAndTestCaseViews().getLnkCMTestCaseViews().setSelectionMessagedReceived(true);
            this.getM_CMTestCaseStructureView().getM_CMElementAndTestCaseViews().getLnkCMTestCaseViews().selectionChangedInDescriptionView(this.getFirstSelectedRow());
        }
    }
}
//fcastro_02092004_end

  void eventGridMouseClicked(GridEvent e) {
    MouseEvent mouseEvent = (MouseEvent) e.getSourceEvent();
    int row = e.getRow();
    int column = e.getColumn();

//  hmendez_11112005_begin
	//if(mouseEvent.getClickCount() == 2)
    if((mouseEvent.getClickCount() == 2)&&(mouseEvent.getButton()==1)) {
//hmendez_11112005_end
        this.getM_CMTestCaseStructureView().getM_CMElementAndTestCaseViews().getLnkCMTestCaseViews().setSelectionMessagedReceived(true);
            this.getM_CMTestCaseStructureView().getM_CMElementAndTestCaseViews().getLnkCMTestCaseViews().selectionChangedInDescriptionView(this.getFirstSelectedRow());


      this.edit();
    }
    else {
				if(e.getSourceEvent().getModifiers() == Event.META_MASK) {
						if( row >= 0 && column >= 0) {
							this.changeSelection(row,column,false,false);
						}
            //m_CMTestCaseStructureView.getM_CMElementAndTestCaseViews().getLnkCMTestCaseViews().getCmPopupMenuTestCases().show(this, mouseEvent.getX(), mouseEvent.getY());
	}
                else{
                    this.getM_CMTestCaseStructureView().getM_CMElementAndTestCaseViews().getLnkCMTestCaseViews().setSelectionMessagedReceived(true);
            this.getM_CMTestCaseStructureView().getM_CMElementAndTestCaseViews().getLnkCMTestCaseViews().selectionChangedInDescriptionView(this.getFirstSelectedRow());

                }
    }
  }

public void selectCMTestCaseView(int p_index){
			if( p_index >= 0 && this.getRowCount() > 0) {
					this.changeSelection(p_index,0, false, false);
			}
}


public CMTestCaseDescriptionView createCMTestCaseDescriptionView(TestCase p_TestCase) {
      CMTestCaseDescriptionView testCaseDescriptionView = new CMTestCaseDescriptionView(p_TestCase);
      CMTestCaseNameView nameView = new CMTestCaseNameView(this,p_TestCase);
      nameView.setM_CMTestCaseDescriptionView(testCaseDescriptionView);
      CMTestCaseDescriptionCellView descriptionCellView = new CMTestCaseDescriptionCellView();
      descriptionCellView.setM_CMTestCaseDescriptionView(testCaseDescriptionView);
			testCaseDescriptionView.setM_CMTestCaseNameView(nameView);
      testCaseDescriptionView.setM_CMTestCaseDescriptionCellView(descriptionCellView);
      testCaseDescriptionView.addElement(nameView);
      testCaseDescriptionView.addElement(descriptionCellView);
      return testCaseDescriptionView;
}



public void addCMTestCaseView(CMTestCaseDescriptionView p_CMTestCaseView) {
  this.m_CMGridModel.addRow(p_CMTestCaseView);
  int newSelectionIndex = this.getRowCount()-1;
	selectCMTestCaseView(newSelectionIndex);
}



public void addCMTestCaseViews(List<TestCase> name) {

  int numOfEffects = name.size();
  for( int i = 0; i < numOfEffects; i++) {
    TestCase testCase = (TestCase) name.get(i);
    addCMTestCaseView(createCMTestCaseDescriptionView(testCase));
  }
}


  public void updateAll(List<TestCase> name) {
    deleteAllTestCaseViews();
    addCMTestCaseViews(name);
  }

  void deleteAllTestCaseViews() {
    int numOfRows = m_CMGridModel.getRowCount();
    int numOfTestCaseViews = numOfRows;
    deleteTestCaseView(0,numOfTestCaseViews);
  }

  void deleteTestCaseView(int index,int count) {
    m_CMGridModel.removeRows(index,count);
  }


public int getTheNextCMTestCaseView(int p_index) {
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

public void fireEventTestCaseAdded(TestCase p_Effect) {
  CMTestCaseDescriptionView v = createCMTestCaseDescriptionView(p_Effect);
  this.addCMTestCaseView(v);
}

public int getSelectedCMTestCaseView() {
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

public CMTestCaseDescriptionView getSelectedCMTestCaseView(int p_index){
        Object obj = m_CMGridModel.getCellObjectAt(p_index,0);
        CMTestCaseNameView nameView;

        if( obj != null) {
            if( obj instanceof CMTestCaseNameView) {
              nameView = (CMTestCaseNameView) obj;
              return nameView.getM_CMTestCaseDescriptionView();
            }
            else {
              return null;
            }
        }
        else {
          return null;
        }
}


//integration_fcastro_17082004_begin

  public void updateChangedRow(int row){
    this.m_CMGridModel.fireGridRowsChanged(row,1);
  }
//integration_fcastro_17082004_end
public void edit() {
     CMAction.TESTCASE_EDIT.getAction().actionPerformed(null);
}


public void delete() {
  int index = getSelectedCMTestCaseView();
  if( index >= 0) {
     	int confirmation = JOptionPane.showConfirmDialog(m_CMFrame,CMMessages.getString("QUESTION_DELETE_CAUSE_EFFECT"),CMMessages.getString("LABEL_DELETE_CAUSE_EFFECT"),JOptionPane.YES_NO_OPTION); //$NON-NLS-1$ //$NON-NLS-2$
      if( confirmation == JOptionPane.YES_OPTION) {
				CMTestCaseDescriptionView selectedView = getSelectedCMTestCaseView(index);
      }
  }
}

  public CMTestCaseStructureView getM_CMTestCaseStructureView(){
          return m_CMTestCaseStructureView;
      }

  public void setM_CMTestCaseStructureView(CMTestCaseStructureView m_CMTestCaseStructureView){
          this.m_CMTestCaseStructureView = m_CMTestCaseStructureView;
      }

  public boolean isSelectionMessageReceived(){
        return selectionMessageReceived;
    }

  public void setSelectionMessageReceived(boolean selectionMessageReceived){
          this.selectionMessageReceived= selectionMessageReceived;
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

    public Object getValueAt(int row, int column) {
      Object obj = null;
      if( row >= 0 && column >= 0) {
        obj = super.getValueAt(row,column);
      }
      else {
        return null;
      }
      if( obj == null) { return null; }
//      if( obj instanceof CMTestCaseNameView) {
//        CMTestCaseNameView nameView = (CMTestCaseNameView) obj;
//        if( nameView != null) {
//                //grueda06082004_begin
//                StringBuffer sBuffer = new StringBuffer();
//                sBuffer.append(nameView.getM_CMTestCaseDescriptionView().getM_TestCase().getName());
//                sBuffer.append(nameView.getM_CMTestCaseDescriptionView().getM_TestCase().getStateName());
//				return sBuffer.toString();
//                //grueda06082004_end
//        }
//        else {
//            return super.getValueAt(row, column);
//        }
//      }
//      else
    	  if( obj instanceof CMTestCaseDescriptionCellView) {
					CMTestCaseDescriptionCellView descriptionView = (CMTestCaseDescriptionCellView) obj;
					return descriptionView.getM_CMTestCaseDescriptionView().getM_TestCase().getDescription();
			}
      else {
    	  return super.getValueAt(row, column);
      }
    }
  }

  /////////////////////////////Style Model /////////////////////////////////////

  public class CMStyleModel extends DefaultStyleModel {
    public CMStyleModel() {
      this.setRenderer(String.class, new CMCellRendererDependencies());
      this.setRenderer(CMTestCaseNameView.class, new CMCellRendererDependencies());
    }

    public class CMCellRendererDependencies extends DefaultGridCellRenderer {
      public CMCellRendererDependencies() {
      }

      public Component getComponent(Object value, boolean isSelected, boolean hasFocus, boolean isEditable, int row, int column, GridContext context) {
            Component c = super.getComponent(value, isSelected, hasFocus, isEditable, row, column, context);
              this.setHorizontalAlignment(JLabel.LEFT);
              if( !isSelected) {
                this.setBackground(new Color(235,235,228));
              }
              return c;
          }
    }
  }
public void changeSelection(int arg0, int arg1, boolean arg2, boolean arg3) {
	// TODO Auto-generated method stub
	super.changeSelection(arg0, arg1, arg2, arg3);
}

public TestCase getSelectedTestCase() {
//	Logger.getLogger(this.getClass()).debug(this.getCellAt(this.getSelectedCMTestCaseView(),0));
	if (this.getValueAt(this.getSelectedCMTestCaseView(),0) instanceof CMTestCaseNameView){
		CMTestCaseNameView nameView = (CMTestCaseNameView) this.getCellObjectAt(this.getSelectedCMTestCaseView(),0);
        if( nameView != null) {
				return nameView.getM_CMTestCaseDescriptionView().getM_TestCase();
                //grueda06082004_end
        }
	}

	return null;
}

@Override
protected HashMap<Class, Component> getCellClasses() {
	// TODO Auto-generated method stub
	return null;
}

public void handleCMModelChange(CMModelEvent evt) {
	if (evt.getSource() == m_Structure && evt.getChangedField() == CMField.TESTCASES ){
		deleteAllTestCaseViews();
	    addCMTestCaseViews(m_Structure.getLnkTestCases());
	}

}


}
