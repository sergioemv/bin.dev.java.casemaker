/**
 * Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.view.actions.typedata;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;
import model.ITypeData;
import model.StructureTestData;
import model.TDStructure;
import model.TestData;
import model.TypeDataGlobal;
import model.TypeDataLocal;
import model.edit.CMModelEditFactory;
import model.util.CMDelegate;
import bi.controller.TDStructureManager;
import bi.view.actions.CMAction;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.mainframeviews.CMFrameView;
import bi.view.testdataviews.CMDialogInsertFields;
import bi.view.testdataviews.CMGridTDStructure;
import bi.view.testdataviews.CMIndexTDStructureUpdate;
import bi.view.undomanagementviews.CMCompoundEdit;
import bi.view.undomanagementviews.undoviews.CMComponentAwareEdit;
import bi.view.undomanagementviews.undoviews.CMUndoMediator;

/**
 * @author svonborries
 *
 */
@SuppressWarnings("serial")
public class CMInsertNewElementAction extends AbstractAction implements Action {

	private CMFrameView m_frame;
	private CMGridTDStructure m_gridTDStructure;
	private TDStructure testData;
	private int rowSelected;
	private CMCompoundEdit m_ce;
	private int index;

	public CMInsertNewElementAction(){
		super(CMMessages.getString("TESTDATA_CONTEXMENU_INSERT_FIELD"));
		putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("TESTDATA_CONTEXMENU_INSERT_FIELD"));
	    putValue(Action.SMALL_ICON, CMAction.TESTDATA_INSERT_ELEMENT.getIcon());
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("INSERT_NEW_ELEMENT_MNEMONIC").charAt(0));
	    putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_INSERT,0));
	}
	public void actionPerformed(ActionEvent e) {

		m_frame = CMApplication.frame;
		m_gridTDStructure = m_frame.getGridTDStructure();
		testData = m_gridTDStructure.getTDStructure();
		rowSelected = m_gridTDStructure.getRowSelected();

        insertFieldTDStructure();
        m_frame.getPanelTDStructureView().update();
        m_frame.getPanelTestDataView().update();

	}

    @SuppressWarnings("unchecked")
	public void insertFieldTDStructure() {
    	m_ce = new CMCompoundEdit();
    	
    	index = CMIndexTDStructureUpdate.getInstance().getIndex();
        m_ce.addDelegateEdit(new CMDelegate(){

			public void execute() {
				m_gridTDStructure.update(index);
			}

		});
		m_ce.addEdit(new CMComponentAwareEdit());
    	
    	
        
        if (testData.getM_StructureTestData().size() > 0 && index < testData.getM_StructureTestData().size()) {
            ITypeData newTypeData = new TypeDataGlobal();
            int idName = 1;
            int idField = 1;
            String typName = CMMessages.getString("TESTDATA_DEFAULT_NAME") + " " + Integer.toString(idName) + ">";
            String typField = CMMessages.getString("TESTDATA_DEFAULT_FIELD") + " " + Integer.toString(idField) + ">";
            int indexTyp = ((StructureTestData)testData.getM_StructureTestData().elementAt(index)).getTypeData().size();
            boolean uniqueName = false;
            boolean uniqueField = false;
            int count = 0;
            while (count < indexTyp) {
                ITypeData compareTyp = (ITypeData)((StructureTestData)testData.getM_StructureTestData().elementAt(index)).getTypeData().elementAt(count);
                if (compareTyp.getName().equalsIgnoreCase(typName)) {
                    idName++;
                    typName = CMMessages.getString("TESTDATA_DEFAULT_NAME") + " " + Integer.toString(idName) + ">";
                    count = 0;
                    uniqueName = false;
                }
                else {
                    uniqueName = true;
                }
                if (compareTyp.getField().equalsIgnoreCase(typField)) {
                    idField++;
                    typField = CMMessages.getString("TESTDATA_DEFAULT_FIELD") + " " + Integer.toString(idField) + ">";
                    count = 0;
                    uniqueField = false;
                }
                else {
                    uniqueField = true;
                }
                if (uniqueName && uniqueField) {
                    count++;
                }
            }
            m_ce.addEdit(CMModelEditFactory.INSTANCE.createChangeNameInTypeDataModelEdit(newTypeData, typName));
            newTypeData.setName(typName);
            m_ce.addEdit(CMModelEditFactory.INSTANCE.createChangeFieldInTypeDataModelEdit(newTypeData, typField));
            newTypeData.setField(typField);
            m_ce.addEdit(CMModelEditFactory.INSTANCE.createChangeGlobalReferenceInTypeDataModelEdit(newTypeData, "G"));
            newTypeData.setGlobal("G");
            Vector columns1 = new Vector(5);
            int indexStructure = CMIndexTDStructureUpdate.getInstance().getIndex();
            int sizeNC1 = m_gridTDStructure.sizeNewColumnsHeader(indexStructure);
            if (sizeNC1 > 0){
            	   	for (int k=0;k<sizeNC1;k++){
            	   		columns1.addElement("");
                    }
            }
            m_ce.addEdit(CMModelEditFactory.INSTANCE.createChangeNewColumnsInTypeDataModelEdit(newTypeData, columns1));
            newTypeData.setNewColumns(columns1);
            CMDialogInsertFields cmd = new CMDialogInsertFields(newTypeData, m_frame, m_gridTDStructure.getM_Technology());
            cmd.setVisible(true);
            if (cmd.isOkSelected) {
                int globalindex = ((StructureTestData)this.testData.getM_StructureTestData().elementAt(index)).getGlobalIndex();
                StructureTestData structureTD = (StructureTestData)this.testData.getM_StructureTestData().elementAt(index);
                if (rowSelected > structureTD.getTypeData().size())
                    rowSelected = structureTD.getTypeData().size();
                if (rowSelected == structureTD.getTypeData().size()){
                	m_ce.addEdit(CMModelEditFactory.INSTANCE.createAddTypeDataToStructureTestDataModelEdit(structureTD, newTypeData));
                    structureTD.getTypeData().addElement(newTypeData);
                    newTypeData.setStructureTestData(structureTD);
                }
                else{
                	m_ce.addEdit(CMModelEditFactory.INSTANCE.createInsertElementAtInStructureTestDataModelEdit(newTypeData, structureTD, rowSelected));
                    structureTD.getTypeData().insertElementAt(newTypeData, rowSelected);
                    m_ce.addEdit(CMModelEditFactory.INSTANCE.createChangeStructureTestDataInTypeDataModelEdit(newTypeData, structureTD));
                    newTypeData.setStructureTestData(structureTD);
                }
                for (int i = 0; i < this.testData.getTestDataCombination().getM_TestDatas().size(); i++) {
                    TestData td = (TestData)this.testData.getTestDataCombination().getM_TestDatas().elementAt(i);
                    for (int j = 0; j < td.getM_TDStructure().getM_StructureTestData().size(); j++) {
                        int globalindex2 = ((StructureTestData)td.getM_TDStructure().getM_StructureTestData().elementAt(j)).getGlobalIndex();
                        if (globalindex == globalindex2) {
                            structureTD = (StructureTestData)td.getM_TDStructure().getM_StructureTestData().elementAt(j);
                            ITypeData newTypeData2 = new TypeDataLocal();
                            m_ce.addEdit(CMModelEditFactory.INSTANCE.createChangeReferenceTypeDataInTypeDataModelEdit(newTypeData2, newTypeData));
                            ((TypeDataLocal)newTypeData2).setM_ReferenceTypeData(newTypeData);

                            m_ce.addEdit(CMModelEditFactory.INSTANCE.createChangeGlobalReferenceInTypeDataModelEdit(newTypeData2, newTypeData.getGlobal()));
                            newTypeData2.setGlobal(newTypeData.getGlobal());

                           /* m_ce.addEdit(CMModelEditFactory.INSTANCE.createChangeFormulaInTypeDataModelEdit(newTypeData2, newTypeData.getStringFormula()));
                            newTypeData2.setFormula(newTypeData.getStringFormula());*/

                            /*m_ce.addEdit(CMModelEditFactory.INSTANCE.createChangeisFormulaInTypeDataModelEdit(newTypeData2, newTypeData.isFormula()));
                            newTypeData2.setisFormula(newTypeData.isFormula());*/

                            m_ce.addEdit(CMModelEditFactory.INSTANCE.createChangeValueInTypeDataModelEdit(newTypeData2, newTypeData.getValue()));
                            newTypeData2.setValue(newTypeData.getValue());
                            //newTypeData2.setStringValue(newTypeData.getStringValue());

                            m_ce.addEdit(CMModelEditFactory.INSTANCE.createChangeStructureTestDataInTypeDataModelEdit(newTypeData2, structureTD));
                            newTypeData2.setStructureTestData(structureTD);
                            if (rowSelected == structureTD.getTypeData().size()){

                            	m_ce.addEdit(CMModelEditFactory.INSTANCE.createAddTypeDataToStructureTDModelEdit(structureTD,newTypeData2));
                                structureTD.getTypeData().addElement(newTypeData2);}
                            else{

                            	m_ce.addEdit(CMModelEditFactory.INSTANCE.createInsertElementAtInStructureTestDataModelEdit(newTypeData2, structureTD, rowSelected));
                                structureTD.getTypeData().insertElementAt(newTypeData2, rowSelected);}

                        }
                    }
                }
                HashMap subjects= newTypeData.getM_Subjects();
                Vector keys= new Vector( subjects.keySet());
                for (Iterator iter = keys.iterator(); iter.hasNext();) {
					String key = (String) iter.next();
					ITypeData subject= (ITypeData) subjects.get(key);
					m_ce.addEdit(TDStructureManager.getTypeDataReferenceForGlobalStructure(m_frame, newTypeData, subject, key, false));

				}
                
                
                m_ce.addDelegateEdit(new CMDelegate(){

        			public void execute() {
        				m_gridTDStructure.update(index);
        			}

        		});
    			
    			if(m_ce.hasEdits())
    	        	CMUndoMediator.getInstance().doEdit(m_ce);
                
                
                m_gridTDStructure.update(index);
                /*m_frame.getM_CMUndoMediator().insertTDStructureFields(m_gridTDStructure, rowSelected, index, newTypeData, m_frame.getm_TabbedPaneView(),
                        CMMessages.getString("TESTDATA_TDSTRUCTURE"));*/
            }
        }
    }

}
