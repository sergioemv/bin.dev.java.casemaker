/**
 * Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.view.actions.typedata;

import java.awt.Event;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;

import model.ICMValue;
import model.ITypeData;
import model.StructureTestData;
import model.TypeDataLocal;
import model.edit.CMModelEditFactory;
import model.util.CMDelegate;

import bi.view.actions.CMAction;
import bi.view.actions.CMEnabledAction;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.mainframeviews.CMFrameView;
import bi.view.testdataviews.CMGridTDStructure;
import bi.view.testdataviews.CMPanelTestDataView;
import bi.view.undomanagementviews.CMCompoundEdit;
import bi.view.undomanagementviews.undoviews.CMComponentAwareEdit;
import bi.view.undomanagementviews.undoviews.CMUndoMediator;

/**
 * @author svonborries
 *
 */
@SuppressWarnings("serial")
public class CMAssignGlobalValueAction extends AbstractAction implements Action,CMEnabledAction {

	private CMFrameView m_frame;
	private CMGridTDStructure m_gridTDStructure;
	private CMPanelTestDataView m_panelTestDataView;

	public CMAssignGlobalValueAction(){
		super(CMMessages.getString("TESTDATA_ASSIGN_GLOBAL_VALUE"));
		putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("TESTDATA_ASSIGN_GLOBAL_VALUE"));
	    putValue(Action.SMALL_ICON, CMAction.TESTDATA_ASSIGN_GLOBAL_VALUE.getIcon());
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("ASSIGN_GLOBAL_VALUE_MNEMONIC").charAt(0));
	    putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, Event.CTRL_MASK));
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		this.m_frame = CMApplication.frame;
		this.m_gridTDStructure = m_frame.getGridTDStructure();
		this.m_panelTestDataView = m_frame.getPanelTestDataView();
		CMCompoundEdit ce = new CMCompoundEdit();
		ITypeData localTypeData = m_panelTestDataView.getM_CMGridTDStructure().getSelectedTypeData();
		StructureTestData stdLocal = localTypeData.getStructureTestData();
		int indexLocal = stdLocal.getGlobalIndex();
		for(Object structure: m_gridTDStructure.getTDStructure().getM_StructureTestData()){
			if(((StructureTestData)structure).getGlobalIndex() == indexLocal){
				for(Object typeDataGlobal: ((StructureTestData)structure).getTypeData()){
					if(((ITypeData)typeDataGlobal).getField().equalsIgnoreCase(((TypeDataLocal)localTypeData).getM_ReferenceTypeData().getField())){
						
						ce.addDelegateEdit(new CMDelegate(){

		        			public void execute() {
		        	        	m_panelTestDataView.update();
		        			}

		        		});
		        		ce.addEdit(new CMComponentAwareEdit());
						
						ICMValue globalValue = (ICMValue) ((ITypeData)typeDataGlobal).getValue();
						ce.addEdit(CMModelEditFactory.INSTANCE.createChangeValueInTypeDataModelEdit(localTypeData, globalValue));
						localTypeData.setValue(globalValue);
						
						 ce.addDelegateEdit(new CMDelegate(){

			        			public void execute() {
			        				m_panelTestDataView.update();
			        			}

			        		});
						 
						 m_panelTestDataView.update();
						 
						 if(ce.hasEdits())
					        	CMUndoMediator.getInstance().doEdit(ce);
					}
				}
			}
		}
		

//		int table = CMIndexTDStructureUpdate.getInstance().getnumOfTable();
//        int row = CMIndexTDStructureUpdate.getInstance().getnumOfRow();
//        int col = CMIndexTDStructureUpdate.getInstance().getnumOfCol();
//        int sizeGrid = m_gridTDStructure.getSizeGridTDStructure();
//        col= col-(sizeGrid-12);
//        String value = m_gridTDStructure.getGlobalValue(table, row, col);
//        int testdatatable = CMIndexTDStructureUpdate.getInstance().getIndex();
//		ITypeData old=(ITypeData)m_panelTestDataView.getTypeData(testdatatable,row,col).clone();
//        m_panelTestDataView.setGlovalValue(value, testdatatable, row, col);
//
//        if (col == 10) {
//            value=value.trim();
//            if(!value.equals("")){
//            	String value1 = m_gridTDStructure.getGlobalValue(table, row, col - 1);
//           		m_panelTestDataView.setGlovalValue(value1, testdatatable, row, col - 1);
//            	String value2 = m_gridTDStructure.getGlobalValue(table, row, col + 1);
//            	m_panelTestDataView.setGlovalValue(value2, testdatatable, row, col + 1);
//            	String value3 = m_gridTDStructure.getGlobalValue(table, row, col - 5);
//            	m_panelTestDataView.setGlovalValue(value3, testdatatable, row, col - 5);
//                String value4 = m_gridTDStructure.getGlobalValue(table, row, 12);
//            	m_panelTestDataView.setGlovalValue(value4, testdatatable, row, 12);
//            	TestDataFormat value5=m_gridTDStructure.getGlobalFormatter(table, row, col - 1);
//            	m_panelTestDataView.setGlovalFormatter(value5,testdatatable,row, col - 1);
//            }
//        }
//        if (col == 11) {
//            String value3 = m_gridTDStructure.getGlobalValue(table, row, col - 6);
//            m_panelTestDataView.setGlovalValue(value3, testdatatable, row, col - 6);
//        }
//        m_panelTestDataView.update();
//		ITypeData newTD=(ITypeData)m_panelTestDataView.getTypeData(testdatatable,row,col).clone();
//        int indexTestData=CMIndexTDStructureUpdate.getInstance().getindexTestData();
//        int indexStructure=CMIndexTDStructureUpdate.getInstance().getIndex();
//        m_frame.getM_CMUndoMediator().assignGlobalValueinTestDataEdit(m_panelTestDataView,indexTestData,
//        		indexStructure,old,newTD,testdatatable,row,col,m_frame.getm_TabbedPaneView(),
//        		CMMessages.getString("TESTDATA_TESTDATA"));

	}

	public boolean calculateEnabled() {
		if(!CMApplication.frame.isIsPanelTestDataSelected())
			return false;
		else
			return true;
	}
	
	

}
