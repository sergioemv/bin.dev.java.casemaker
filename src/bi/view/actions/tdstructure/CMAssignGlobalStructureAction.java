/**
 * Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.view.actions.tdstructure;

import java.awt.Event;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;
import javax.swing.undo.UndoableEdit;

import model.ITypeData;
import model.StructureTestData;
import model.TDStructure;
import model.TestData;
import model.TypeDataLocal;
import model.edit.CMModelEditFactory;
import model.util.CMDelegate;

import bi.controller.TDStructureManager;
import bi.view.actions.CMAction;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.mainframeviews.CMFrameView;
import bi.view.testdataviews.CMDialogAssignGlobalStructure;
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
public class CMAssignGlobalStructureAction extends AbstractAction implements
		Action {

	private CMFrameView m_frame;
	private CMGridTDStructure m_gridTDStructure;
	private TDStructure m_TDStructure;

	public CMAssignGlobalStructureAction(){
		super(CMMessages.getString("TESTDATA_ASSIGN_GLOBAL_TDSTRUCTURE"));
		putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("BUTTON_TOOLTIP_ASSIGN_GLOBAL_STRUCTURE"));
	    putValue(Action.SMALL_ICON, CMAction.TESTDATA_ASSIGN_GLOBAL_STRUCTURE.getIcon());
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("ASSIGN_GLOBAL_STRUCTURE_MNEMONIC").charAt(0));
	    putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_G, Event.CTRL_MASK));
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@SuppressWarnings("unchecked")
	public void actionPerformed(ActionEvent e) {

		m_frame = CMApplication.frame;
		m_gridTDStructure = m_frame.getGridTDStructure();
		m_TDStructure = m_gridTDStructure.getTDStructure();
		CMCompoundEdit ce = new CMCompoundEdit();
		
		ce.addDelegateEdit(new CMDelegate(){

			public void execute() {
				m_frame.getPanelTestDataView().update();
			}

		});
		ce.addEdit(new CMComponentAwareEdit());

		if (m_frame.getPanelTestDataView().getM_TestDataCombinations().getM_TestDatas().size() != 0){
			CMDialogAssignGlobalStructure cmd = new CMDialogAssignGlobalStructure(this.m_frame, m_TDStructure);
			cmd.setVisible(true);

			
			TestData testdata= (TestData)(m_TDStructure.getTestDataCombination().getM_TestDatas().elementAt
					(CMIndexTDStructureUpdate.getInstance().getindexTestData()));
	       	StructureTestData std= new StructureTestData();
	       	if(CMDialogAssignGlobalStructure.indexOfGlobalStructure != -1){
	        StructureTestData aux=(StructureTestData)m_TDStructure.getM_StructureTestData().elementAt
	        (CMDialogAssignGlobalStructure.indexOfGlobalStructure);
	        ce.addEdit(CMModelEditFactory.INSTANCE.createChangeDescriptionInStructureTestData(std,aux.getDescription()));
	            std.setDescription(aux.getDescription());
	            ce.addEdit(CMModelEditFactory.INSTANCE.createChangeNameInStructureTestData(std,aux.getName()));
	            std.setName(aux.getName());
	            ce.addEdit(CMModelEditFactory.INSTANCE.createChangeTypeInStructureTestData(std,aux.getType()));
	            std.setType(aux.getType());
	            ce.addEdit(CMModelEditFactory.INSTANCE.createChangeGlobalIndexInStructureTestData(std,aux.getGlobalIndex()));
	            std.setGlobalIndex(aux.getGlobalIndex());
	            ce.addEdit(CMModelEditFactory.INSTANCE.createChangeNewColumnsHeaderInStructureTestDataModelEdit(std, aux.getNewColumnsHeader()));
	            std.setNewColumnsHeader(aux.getNewColumnsHeader());
	            TDStructureManager tdmanager=m_frame.getCmApplication().getSessionManager().getTDStructureManager();
	            ce.addEdit(tdmanager.setTestDataIdentifierToStructure(std,testdata));
	            for(int j=0; j<aux.getTypeData().size();j++)
	            {
	                ITypeData typeData = (ITypeData)aux.getTypeData().elementAt(j);
	                TypeDataLocal newtypData= new TypeDataLocal();

	                newtypData.setM_Subjects((HashMap) typeData.getM_Subjects().clone());
	                for (Iterator iter = newtypData.getM_Subjects().values().iterator(); iter.hasNext();) {
						ITypeData subjects = (ITypeData) iter.next();
						ce.addEdit(CMModelEditFactory.INSTANCE.createAddObserverInTypeDataWithiTwoParamModelEdit(subjects,newtypData));
						subjects.addObserver(newtypData);
					}
	                /*ce.addEdit(CMModelEditFactory.INSTANCE.createChangeFormulaInTypeDataModelEdit(newtypData,typeData.getStringFormula()));
	                newtypData.setFormula(typeData.getStringFormula());*/
	                ce.addEdit(CMModelEditFactory.INSTANCE.createChangeValueInTypeDataModelEdit(newtypData,typeData.getValue()));
	                newtypData.setValue(typeData.getValue());
	                /*ce.addEdit(CMModelEditFactory.INSTANCE.createChangeisFormulaInTypeDataModelEdit(newtypData,typeData.isFormula()));
	                newtypData.setisFormula(typeData.isFormula());*/
	                ce.addEdit(CMModelEditFactory.INSTANCE.createChangeGlobalReferenceInTypeDataModelEdit(newtypData,"G"));
	                newtypData.setGlobal("G");
	                ce.addEdit(CMModelEditFactory.INSTANCE.createChangeGlobalReferenceInTypeDataModelEdit(typeData,"G"));
	                typeData.setGlobal("G");
	                ce.addEdit(CMModelEditFactory.INSTANCE.createAddTypeDataToStructureTestDataModelEdit(std,newtypData));
	                newtypData.setStructureTestData(std);
	                std.getTypeData().addElement(newtypData);
	            }

	            ce.addEdit(setTypeDataReferences(std,aux));
	            ce.addEdit(CMModelEditFactory.INSTANCE.createAddStructureTestDataToTDStructureModelEdit(testdata.getM_TDStructure(),std));
	            testdata.getM_TDStructure().getM_StructureTestData().addElement(std);
	            /*m_frame.getM_CMUndoMediator().assignStructureEdit(m_frame.getPanelTestDataView(),
	            		CMIndexTDStructureUpdate.getInstance().getindexTestData(),std,m_frame.getm_TabbedPaneView(),
	            		CMMessages.getString("TESTDATA_TESTDATA"));*/
	            
	            ce.addDelegateEdit(new CMDelegate(){

	    			public void execute() {
	    				m_frame.getPanelTestDataView().update();
	    			}

	    		});
	            
	            
	            m_frame.getPanelTestDataView().update();
	            
				if(ce.hasEdits())
		        	CMUndoMediator.getInstance().doEdit(ce);
		}
		}
	}

    private UndoableEdit setTypeDataReferences(StructureTestData stdPopulated, StructureTestData createStructure) {
    	CMCompoundEdit ce = new CMCompoundEdit();
		for(int i=0; i < stdPopulated.getTypeData().size();i++){
			ITypeData TypeDataRefered= (ITypeData) createStructure.getTypeData().elementAt(i);
			ITypeData toRefer=(ITypeData) stdPopulated.getTypeData().elementAt(i);
			ce.addEdit(CMModelEditFactory.INSTANCE.createChangeReferenceTypeDataInTypeDataModelEdit(toRefer,TypeDataRefered));
			((TypeDataLocal)toRefer).setM_ReferenceTypeData(TypeDataRefered);
		}
		return ce;
	}

}
