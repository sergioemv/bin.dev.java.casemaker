/**
 * Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.view.actions.typedata;

import java.awt.event.ActionEvent;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import model.ITypeData;
import model.StructureTestData;
import model.TDStructure;
import model.TestData;
import model.edit.CMModelEditFactory;
import model.util.CMDelegate;
import bi.controller.TDStructureManager;
import bi.view.actions.CMAction;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.mainframeviews.CMFrameView;
import bi.view.testdataviews.CMGridTDStructure;
import bi.view.testdataviews.CMIndexTDStructureUpdate;
import bi.view.undomanagementviews.CMCompoundEdit;
import bi.view.undomanagementviews.undoviews.CMUndoMediator;

/**
 * @author svonborries
 *
 */
@SuppressWarnings("serial")
public class CMDeleteElementAction extends AbstractAction implements Action {

	private CMFrameView m_frame;
	private CMGridTDStructure m_gridTDStructure;
	private TDStructure testData;
	private int rowSelected;

	public CMDeleteElementAction(){
		super(CMMessages.getString("TESTDATA_CONTEXMENU_DELETE_FIELD"));
		putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("BUTTON_TOOLTIP_DELETE_FIELDS"));
	    putValue(Action.SMALL_ICON, CMAction.TESTDATA_ADD_VARIABLE.getIcon());
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("DELETE_ELEMENT_MNEMONIC").charAt(0));
	    putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_DELETE,0));
	}
	public void actionPerformed(ActionEvent e) {

		m_frame = CMApplication.frame;
		m_gridTDStructure = m_frame.getGridTDStructure();
		testData = m_gridTDStructure.getTDStructure();
		rowSelected = m_gridTDStructure.getRowSelected();

		deleteFieldTDStructure();
	//cc	m_frame.getPanelTDStructureView().update();
    //cc	m_frame.getPanelTestDataView().update();

	}

    public void deleteFieldTDStructure() {
    	CMCompoundEdit ce = new CMCompoundEdit();
    
    	ce.addUndoDelegateEdit(new CMDelegate(){
			public void execute() {				
	            m_gridTDStructure.update(CMIndexTDStructureUpdate.getInstance().getIndex());	      	            
			}
		});
    	
        int confirmation = JOptionPane.YES_OPTION;
        confirmation = JOptionPane.showConfirmDialog(m_frame, CMMessages.getString("TESTDATA_MESSAGE_DELETE_FIELDS"),
            CMMessages.getString("TESTDATA_TITLE_MENSSAGE_DELETE_STRUCTURE"), JOptionPane.YES_NO_OPTION);
        if (confirmation == JOptionPane.YES_OPTION) {
            ITypeData newTypeData = null;
            int index = CMIndexTDStructureUpdate.getInstance().getIndex();
            if (testData.getM_StructureTestData().size() > 0 && index < testData.getM_StructureTestData().size()) {
                int globalindex = ((StructureTestData)this.testData.getM_StructureTestData().elementAt(index)).getGlobalIndex();
                StructureTestData structureTD = (StructureTestData)this.testData.getM_StructureTestData().elementAt(index);
                if (rowSelected > structureTD.getTypeData().size())
                    rowSelected = structureTD.getTypeData().size();
                if (rowSelected >= 0 && structureTD.getTypeData().size() != 0) {
                    newTypeData = (ITypeData)structureTD.getTypeData().elementAt(rowSelected);
                    Vector observers=newTypeData.getObservers();
                    for (Iterator iter = observers.iterator(); iter.hasNext();) {
						ITypeData typ = (ITypeData) iter.next();
						ce.addEdit(TDStructureManager.deleteTypeDataReference(typ,true));
					}
                    ce.addEdit(CMModelEditFactory.INSTANCE.createRemoveElementAtInStructureTestDataModelEdit((ITypeData) structureTD.getTypeData().elementAt(rowSelected),structureTD,rowSelected));
                    structureTD.getTypeData().removeElementAt(rowSelected);
                }
                for (int i = 0; i < this.testData.getTestDataCombination().getM_TestDatas().size(); i++) {
                    TestData td = (TestData)this.testData.getTestDataCombination().getM_TestDatas().elementAt(i);
                    for (int j = 0; j < td.getM_TDStructure().getM_StructureTestData().size(); j++) {
                        int globalindex2 = ((StructureTestData)td.getM_TDStructure().getM_StructureTestData().elementAt(j)).getGlobalIndex();
                        if (globalindex == globalindex2) {
                            structureTD = (StructureTestData)td.getM_TDStructure().getM_StructureTestData().elementAt(j);
                            if (rowSelected >= 0 && structureTD.getTypeData().size() != 0){
                            	ITypeData aux=(ITypeData)structureTD.getTypeData().elementAt(rowSelected);
                                Vector observers=aux.getObservers();
                                for (Iterator iter = observers.iterator(); iter.hasNext();) {
            						ITypeData typ = (ITypeData) iter.next();
            						ce.addEdit(TDStructureManager.deleteTypeDataReference(typ,false));
            					}
                                ce.addEdit(CMModelEditFactory.INSTANCE.createRemoveElementAtInStructureTestDataModelEdit((ITypeData) structureTD.getTypeData().elementAt(rowSelected),structureTD,rowSelected));
                                structureTD.getTypeData().removeElementAt(rowSelected);
                            }
                        }
                    }
                }
                m_gridTDStructure.update(index);
                
                ce.addRedoDelegateEdit(new CMDelegate(){
        			public void execute() {				
        	            m_gridTDStructure.update(CMIndexTDStructureUpdate.getInstance().getIndex());        	         
        			}
        		});
        		
        		if(ce.hasEdits())
        			CMUndoMediator.getInstance().doEdit(ce);
                
               /* m_frame.getM_CMUndoMediator().deleteTDStructureFields(m_gridTDStructure, rowSelected, m_frame.getm_TabbedPaneView(),
                    CMMessages.getString("TESTDATA_TDSTRUCTURE"),ce);
               *///ccastedo comments this 26.02.07
            }
        }
    }

}
