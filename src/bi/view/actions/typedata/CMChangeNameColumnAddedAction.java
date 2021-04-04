/**
 * Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.view.actions.typedata;

import java.awt.event.ActionEvent;
import java.util.Vector;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.undo.UndoableEdit;
import model.StructureTestData;
import model.TDStructure;
import model.TestData;
import model.edit.CMModelEditFactory;
import model.util.CMDelegate;
import bi.view.actions.CMAction;
import bi.view.cells.CMCellHeaderTDStructureNewColumn;
import bi.view.cells.CMCellTDStructureNewColumn;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.mainframeviews.CMFrameView;
import bi.view.testdataviews.CMGridTDStructure;
import bi.view.testdataviews.CMIndexTDStructureUpdate;
import bi.view.undomanagementviews.CMCompoundEdit;
import bi.view.undomanagementviews.undoviews.CMComponentAwareEdit;
import bi.view.undomanagementviews.undoviews.CMUndoMediator;

/**
 * @author ccastedo
 *
 */
@SuppressWarnings("serial")
public class CMChangeNameColumnAddedAction extends AbstractAction implements Action {

	private CMFrameView m_frame;
	private CMGridTDStructure m_gridTDStructure;

	public CMChangeNameColumnAddedAction(){
		super(CMMessages.getString("TESTDATA_STRUCTURE_CHANGE_NAME_COLUMN_ADDED"));
		putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("TESTDATA_STRUCTURE_CHANGE_NAME_COLUMN_ADDED"));
	    putValue(Action.SMALL_ICON, CMAction.TESTDATA_CHANGE_NAME_COLUMN_ADDED.getIcon());
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("CHANGE_NAME_COLUMN_ADDED_MNEMONIC").charAt(0));
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		this.m_frame = CMApplication.frame;
		this.m_gridTDStructure = m_frame.getGridTDStructure();
		CMCompoundEdit m_ce = new CMCompoundEdit();
		m_ce.addEdit(new CMComponentAwareEdit());
		m_ce.addUndoDelegateEdit(new CMDelegate(){

			public void execute() {				
	            m_gridTDStructure.update(CMIndexTDStructureUpdate.getInstance().getIndex());
	            m_gridTDStructure.setTDStructure(m_frame.getTreeWorkspaceView().getSelectedTDStructure());      
	            
			}

		});
		
		changeNameNewColumnAddedTDStructure(m_ce);
		m_ce.addRedoDelegateEdit(new CMDelegate(){

			public void execute() {				
	            m_gridTDStructure.update(CMIndexTDStructureUpdate.getInstance().getIndex());
	            m_gridTDStructure.setTDStructure(m_frame.getTreeWorkspaceView().getSelectedTDStructure());
			}

		});
		
		if(m_ce.hasEdits())
        	CMUndoMediator.getInstance().doEdit(m_ce);

	}

    public void changeNameNewColumnAddedTDStructure(CMCompoundEdit ce){
    	int columnSelected = 0;
    	int column = this.m_gridTDStructure.getColumnSelected();
    	int row = this.m_gridTDStructure.getRowSelected();
    	Object obj = null;
    	if (row!=-1 && column != -1){
    		 obj = this.m_gridTDStructure.getCellObjectAt(row, column);
    		 columnSelected = column;
    	}
    	else{
    		columnSelected = this.m_gridTDStructure.getM_GridHeader().getSelectionModel().getLeadColumn();
    		if (columnSelected != -1)
    			obj = this.m_gridTDStructure.getM_CMHeaderGridTDStructure().getGroupHeaderTDStructure().elementAt(columnSelected);    		
    	}
    	if (obj != null){
    		
        	final int index = CMIndexTDStructureUpdate.getInstance().getIndex();        	
        	if (obj instanceof CMCellHeaderTDStructureNewColumn || obj instanceof CMCellTDStructureNewColumn){
        		JTextField textfieldOldName = new JTextField();
        		String oldName = ((StructureTestData)this.m_gridTDStructure.getTDStructure().getM_StructureTestData().elementAt(index)).getNewColumnsHeader().elementAt(columnSelected-5).toString();
            	textfieldOldName.setText(oldName);
            	textfieldOldName.setEnabled(false);
            
            	String newName = (String) JOptionPane.showInputDialog(m_frame, textfieldOldName, CMMessages.getString("TESTDATA_STRUCTURE_CHANGE_NAME_COLUMN_ADDED"), JOptionPane.PLAIN_MESSAGE, null, null, null);
            	if (newName != null){
            		if (existName(((StructureTestData)this.m_gridTDStructure.getTDStructure().getM_StructureTestData().elementAt(index)).getNewColumnsHeader(),newName)){
            			String message = CMMessages.getString("SAME_NAME_COLUMNS");
    	            	JOptionPane.showMessageDialog(CMApplication.frame, message,CMMessages.getString("TESTDATA_STRUCTURE_CHANGE_NAME_COLUMN_ADDED"), JOptionPane.WARNING_MESSAGE);
    	            	return;
            		}
            		
            		if (!newName.equalsIgnoreCase("")){                 	
            			ce.addEdit(changeNameNewColumnTDStructure(columnSelected,index,newName));
            			if (obj instanceof CMCellHeaderTDStructureNewColumn)
            				this.m_gridTDStructure.getM_GridHeader().changeSelection(this.m_gridTDStructure.getSelectedHeaderRow(), this.m_gridTDStructure.getSelectedHeaderColumn(), false, false);
            		}
            	} 
             }
         }    	
    }
    	
    	
   private boolean existName(Vector newColumn,String newName){
	   boolean exist = false;
	   for (int i=0;i<newColumn.size();i++){
		   if (newColumn.elementAt(i).equals(newName)){
			   exist = true;
			   break;
		   }
	   }
	   return exist;
   }
    
    @SuppressWarnings("unchecked")
	public UndoableEdit changeNameNewColumnTDStructure(int p_index,int indexStructure, String newName) {
    	CMCompoundEdit ce = new CMCompoundEdit();
    	int index = 0;
    	TDStructure testData = this.m_gridTDStructure.getTDStructure();
    	int sizeTD = testData.getM_StructureTestData().size();
    	
        if ( sizeTD >= 0 ) {       	 
         	
        	 StructureTestData tdS = (StructureTestData)testData.getM_StructureTestData().elementAt(indexStructure);
         	    
         	 index = p_index - 5;
         	
         	 if (index >= 0){

         		int globalindex = ((StructureTestData)testData.getM_StructureTestData().elementAt(indexStructure)).getGlobalIndex();
          	  
           	    int sizeTDs = testData.getTestDataCombination().getM_TestDatas().size();
                for (int i = 0; i < sizeTDs; i++) {
                     TestData td = (TestData)testData.getTestDataCombination().getM_TestDatas().elementAt(i);
                     int sizetd = td.getM_TDStructure().getM_StructureTestData().size();
                     for (int j = 0; j < sizetd; j++) {
                    	 int globalindex2 = ((StructureTestData)td.getM_TDStructure().getM_StructureTestData().elementAt(j)).getGlobalIndex();
                         if (globalindex == globalindex2) {
                        	     tdS = (StructureTestData)td.getM_TDStructure().getM_StructureTestData().elementAt(j);
                        	     Vector oldheaders = new Vector(5);                        	   
                        	     oldheaders.addAll(tdS.getNewColumnsHeader());
                           	     oldheaders.setElementAt(newName, index);
                           	     ce.addEdit(CMModelEditFactory.INSTANCE.createChangeNewColumnsHeaderInStructureTestDataModelEdit(tdS, oldheaders));
                           	     tdS.setNewColumnsHeader(oldheaders);                          	  
                         }
                     }
                  }
            
            	  tdS = (StructureTestData)testData.getM_StructureTestData().elementAt(indexStructure);
            	  Vector oldheaders = new Vector(5);     
            	  oldheaders.addAll(tdS.getNewColumnsHeader());
            	  oldheaders.setElementAt(newName, index);
                  ce.addEdit(CMModelEditFactory.INSTANCE.createChangeNewColumnsHeaderInStructureTestDataModelEdit(tdS, oldheaders));
           	      tdS.setNewColumnsHeader(oldheaders);
                  this.m_gridTDStructure.update(indexStructure);
               
                }
         	}
        return ce;
        }
    

  

}
