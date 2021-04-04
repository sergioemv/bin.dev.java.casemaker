/**
 * Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.view.actions.typedata;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;

import model.CMDefaultValue;
import model.ITypeData;
import model.edit.CMModelEditFactory;
import model.util.CMDelegate;

import bi.controller.testdata.CMTypeDataUtils;
import bi.view.actions.CMAction;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.mainframeviews.CMFrameView;
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
public class CMDeleteLinkElementAction extends AbstractAction implements Action {

	private CMFrameView m_frame;
	//private CMCompoundEdit m_ce;
	private CMGridTDStructure m_gridTDStructure;

	public CMDeleteLinkElementAction(){
		super(CMMessages.getString("TESTDATA_DELETE_LINKELEMENT"));
		putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("TESTDATA_DELETE_LINKELEMENT"));
	    putValue(Action.SMALL_ICON, CMAction.TESTDATA_DELETE_LINK_ELEMENT.getIcon());
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("TESTDATA_DELETE_LINKELEMENT_MNEMONIC").charAt(0));
	    //putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V,Event.CTRL_MASK));
	}

	public void actionPerformed(ActionEvent e) {

		CMCompoundEdit ce = new CMCompoundEdit();
		this.m_frame = CMApplication.frame;
		//m_ce = new CMCompoundEdit();
		m_gridTDStructure = m_frame.getGridTDStructure();
		if(m_frame.isIsPanelTestDataSelected())
			m_gridTDStructure = m_frame.getPanelTestDataView().getM_CMGridTDStructure();
		
		ce.addUndoDelegateEdit(new CMDelegate(){
			public void execute() {
        		m_gridTDStructure.update(CMIndexTDStructureUpdate.getInstance().getIndex());
			}
		});
		ce.addEdit(new CMComponentAwareEdit());
		
		int confirmation = JOptionPane.YES_OPTION;
        confirmation = JOptionPane.showConfirmDialog(this.m_frame, CMMessages.getString("TESTDATA_MESSAGE_DELETE_REFERENCE"),
            CMMessages.getString("TESTDATA_TITLE_MENSSAGE_DELETE_STRUCTURE"), JOptionPane.YES_NO_OPTION);
        if (confirmation == JOptionPane.YES_OPTION) {
        	/*m_ce.addEdit(TDStructureManager.deleteTypeDataReference());
        	m_gridTDStructure.setFormulaDelete(true);
        	m_gridTDStructure.setundoredoFormulasMediator(m_gridTDStructure.getRowSelected(),m_gridTDStructure.getColumnSelected(),m_ce);
        	m_gridTDStructure.setFormulaDelete(false);
        	m_frame.getM_CMUndoMediator().deleteLinkElementEdit(m_gridTDStructure,m_ce,m_frame.getm_TabbedPaneView(),
        			CMMessages.getString("TESTDATA_DELETE_LINKELEMENT"));*/
        	ITypeData typedataSelected = m_gridTDStructure.getSelectedTypeData();
        	CMDefaultValue defaultValue = null;
        	try {
				defaultValue = new CMDefaultValue(typedataSelected.getValue().getValue());
			} catch (Exception e1) {
				defaultValue = new CMDefaultValue("");
			}
			ce.addEdit(CMModelEditFactory.INSTANCE.createChangeValueInTypeDataModelEdit(typedataSelected, defaultValue));
        	typedataSelected.setValue(defaultValue);
        	
        	if(!m_frame.isIsPanelTestDataSelected())
        		ce.addEdit(CMTypeDataUtils.updateTypeDataValueForLocalTypeDatas(typedataSelected));
        	
        	ce.addUndoDelegateEdit(new CMDelegate(){
    			public void execute() {
            		m_gridTDStructure.update(CMIndexTDStructureUpdate.getInstance().getIndex());
    			}
    		});
        	
        	//cc m_gridTDStructure.update(CMIndexTDStructureUpdate.getInstance().getIndex());
        	m_gridTDStructure.update(CMIndexTDStructureUpdate.getInstance().getIndex());
        	
        	if(ce.hasEdits())
            	CMUndoMediator.getInstance().doEdit(ce);
        }

	}

}
