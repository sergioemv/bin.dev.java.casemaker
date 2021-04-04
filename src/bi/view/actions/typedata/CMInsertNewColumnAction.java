/**
 * Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.view.actions.typedata;


import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;

import model.BusinessRules;
import model.ITypeData;
import model.StructureTestData;
import model.TDStructure;
import model.TestData;

import bi.controller.TDStructureManager;
import bi.view.actions.CMAction;
import bi.view.actions.CMEnabledAction;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.mainframeviews.CMFrameView;
import bi.view.testdataviews.CMDialogNewColumn;
import bi.view.testdataviews.CMGridTDStructure;
import bi.view.testdataviews.CMIndexTDStructureUpdate;

/**
 * @author svonborries
 *
 */
@SuppressWarnings("serial")
public class CMInsertNewColumnAction extends AbstractAction implements Action,CMEnabledAction {

	private CMFrameView m_frame;
	private CMGridTDStructure m_gridTDStructure;
	private TDStructure testData;

	public CMInsertNewColumnAction(){
		super(CMMessages.getString("TESTDATA_STRUCTURE_ROW_INSERT_COLUMN"));
		putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("TESTDATA_STRUCTURE_ROW_INSERT_COLUMN"));
	    putValue(Action.SMALL_ICON, CMAction.TESTDATA_INSERT_NEW_COLUMN.getIcon());
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("INSERT_A_NEW_COLUMN_MNEMONIC").charAt(0));
	    //putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, Event.CTRL_MASK));
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		this.m_frame = CMApplication.frame;
		this.m_gridTDStructure = m_frame.getGridTDStructure();
		this.testData = m_gridTDStructure.getTDStructure();

		insertColumn();
	}

    public void insertColumn(){
            int index = CMIndexTDStructureUpdate.getInstance().getIndex();
            if (this.m_gridTDStructure.sizeNewColumnsHeader(index) > 0 ){
            	 if (this.m_gridTDStructure.sizeNewColumnsHeader(index) < 5){
            		 insertNewColumn(false,"",0,index);
            	 }
            	 else{
            		 JOptionPane.showMessageDialog(this.m_gridTDStructure,CMMessages.getString("LABEL_NEWCOLUMN_NO_ENTRIES"),CMMessages.getString("TITLE_DEFAULT_APPLICATION_DELETE"),JOptionPane.ERROR_MESSAGE);
            	 }
             }
            else{
            	insertNewColumn(false,"",0,index);

            }
    }

    @SuppressWarnings("unchecked")
	public void insertNewColumn(boolean swRedo, String columnName, int p_index, int indexSTD){

	int index = 0;
   	CMDialogNewColumn cmd = new CMDialogNewColumn(this.m_frame);
   	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
   	Dimension dlgSize = cmd.getPreferredSize();
       cmd.setLocation((screenSize.width - dlgSize.width) / 2, (screenSize.height - dlgSize.height) / 2);
       cmd.setModal(true);
       cmd.requestFocus();
       cmd.setVisible(true);
       Vector newcol = new Vector();
       boolean jButtonOkClicked = cmd.jButtonOKClicked();
       if (jButtonOkClicked) {
    	   StructureTestData tdS = (StructureTestData)testData.getM_StructureTestData().elementAt(indexSTD);
    	   this.m_gridTDStructure.setSwInsertColumn(true);
    	   String newColumnName = cmd.getNewColumnName();
    	   
    	   if (newColumnName.equals("")){
    		   newColumnName = TDStructureManager.INSTANCE.generateNewColumnName(tdS);
    	   }
    	   else{
    		   if (existName(tdS.getNewColumnsHeader(),newColumnName)){
       			    String message = CMMessages.getString("SAME_NAME_COLUMNS");
	            	JOptionPane.showMessageDialog(CMApplication.frame, message,CMMessages.getString("TESTDATA_STRUCTURE_CHANGE_NAME_COLUMN_ADDED"), JOptionPane.WARNING_MESSAGE);
	            	return;
    		   }
       		
    	   }
    	   this.m_gridTDStructure.setM_NameNewColumn(newColumnName);

      	int globalindex = ((StructureTestData)this.testData.getM_StructureTestData().elementAt(indexSTD)).getGlobalIndex();

  	    
  	    int sizeTD = this.testData.getTestDataCombination().getM_TestDatas().size();
       for (int i = 0; i < sizeTD; i++) {
       	Vector newcolumnsView = new Vector(5);
       	newcol.addAll(newcolumnsView);
            TestData td = (TestData)this.testData.getTestDataCombination().getM_TestDatas().elementAt(i);
            int sizetd = td.getM_TDStructure().getM_StructureTestData().size();
            for (int j = 0; j < sizetd; j++) {
           	 int globalindex2 = ((StructureTestData)td.getM_TDStructure().getM_StructureTestData().elementAt(j)).getGlobalIndex();
                if (globalindex == globalindex2) {
               	    tdS = (StructureTestData)td.getM_TDStructure().getM_StructureTestData().elementAt(j);

               		Vector headers = new Vector(5);

                   	 headers = tdS.getNewColumnsHeader();
                   	 if (headers == null){
                   		 Vector newHeaders = new Vector(5);
                   		 newHeaders.addElement(this.m_gridTDStructure.getM_NameNewColumn());
                   		 tdS.setNewColumnsHeader(newHeaders);
                   	 }
                   	 else{
                   		 headers.addElement(this.m_gridTDStructure.getM_NameNewColumn());
                   		 tdS.setNewColumnsHeader(headers);
                   	 }
                   	 	ITypeData typedata = null;//new TypeData();

                     	Vector typeDatas = tdS.getTypeData();

                  	    int sizeTypeDatas = typeDatas.size();

                         for(int k=0; k < sizeTypeDatas;k++){
                            typedata = (ITypeData)typeDatas.elementAt(k);
                            Vector columns = new Vector(5);
                            if (typedata.getNewColumns()!= null)
                           	 	columns.addAll(typedata.getNewColumns());
                            if (columns == null){
                           	 	Vector newcolumns = new Vector(5);
                                newcolumns.addElement("");
                                typedata.setNewColumns(newcolumns);
                                index = newcolumns.size() + 4;
                                newcolumnsView.addElement("");
                             }
                             else{
                                 columns.addElement("");
                                 typedata.setNewColumns(columns);
                                 index = columns.size() + 4;
                                 newcolumnsView.addElement("");
                             }
                        }
                }

            }
       }

     tdS = (StructureTestData)testData.getM_StructureTestData().elementAt(indexSTD);
     Vector headers = new Vector(5);

	 headers = tdS.getNewColumnsHeader();
	 if (headers == null){
		 Vector newHeaders = new Vector(5);
		 newHeaders.addElement(this.m_gridTDStructure.getM_NameNewColumn());
		 tdS.setNewColumnsHeader(newHeaders);
	 }
	 else{
		 if(!existName(tdS.getNewColumnsHeader(), m_gridTDStructure.getM_NameNewColumn())){
			 headers.addElement(this.m_gridTDStructure.getM_NameNewColumn());
			 tdS.setNewColumnsHeader(headers);
		 }
		 
	 }

     ITypeData typedata = null;//new TypeData();

     Vector typeDatas = tdS.getTypeData();

  	  int sizeTypeDatas = typeDatas.size();

        for(int k=0; k < sizeTypeDatas;k++){
            typedata = (ITypeData)typeDatas.elementAt(k);
            Vector columns = new Vector(5);
            if (typedata.getNewColumns()!=null)
           	 columns.addAll(typedata.getNewColumns());
            if ( columns == null ){
           	 Vector newcolumns = new Vector(5);
                newcolumns.addElement("");
                typedata.setNewColumns(newcolumns);
                index = newcolumns.size() + 4;
                newcol.addElement("");
             }
             else{
                 columns.addElement("");
                 typedata.setNewColumns(columns);
                 index = columns.size() + 4;
                 newcol.addElement("");
             }
        }
       this.m_gridTDStructure.getCmGridModel().insertColumn(5,newcol);
       this.m_gridTDStructure.update(indexSTD);

       this.m_gridTDStructure.changeSelection(1, index, false, false);

       this.m_frame.stateDeleteColumnOptionsinStructure(true);
       this.m_frame.stateChangeNameColumnOptionsinStructure(true);
       this.m_gridTDStructure.setSwInsertColumn(false);

       this.m_frame.getM_CMUndoMediator().insertTDStructureNewColumn(m_gridTDStructure, index, indexSTD, this.m_gridTDStructure.getM_NameNewColumn(), this.m_frame.getContentTabbedPane(),
            CMMessages.getString("TESTDATA_TDSTRUCTURE"));

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
    
	public boolean calculateEnabled() {
		if(CMApplication.frame.getTreeWorkspaceView() == null)
			return false;
		if(CMApplication.frame.getTreeWorkspaceView().getCurrentTestObject() == null)
			return false;
		if(CMApplication.frame.getTreeWorkspaceView().getCurrentTestObject().getAccessState().equals
						(BusinessRules.ACCESS_STATE_CHECKED_IN))
			return false;
		if(!(CMApplication.frame.getTreeWorkspaceView().getCurrentTestObject().getUser().equals
				(CMApplication.frame.getTreeWorkspaceView().getM_Session2().getM_User())))
			return false;
		m_gridTDStructure = CMApplication.frame.getGridTDStructure();
		if(m_gridTDStructure.getTDStructure() == null){
			return false;
		}
		if (m_gridTDStructure.getTDStructure().getM_StructureTestData().size()<=CMIndexTDStructureUpdate.getInstance().getIndex()){
			return false;
		}
		StructureTestData std = (StructureTestData)
        m_gridTDStructure.getTDStructure().getM_StructureTestData().elementAt
        (CMIndexTDStructureUpdate.getInstance().getIndex());

		if((this.enabled == true)&&(std.getTypeData().size() == 0)){
			return false;
		}
		if((this.enabled == false)&&(std.getTypeData().size() > 0)){
			return true;
		}
		return this.enabled;
	}
}