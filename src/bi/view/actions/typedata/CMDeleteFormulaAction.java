/**
 *
 */
package bi.view.actions.typedata;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import model.BusinessRules;
import model.CMDefaultValue;
import model.ITypeData;
import model.TestDataFormat;
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
public class CMDeleteFormulaAction extends AbstractAction implements Action {

	private CMFrameView m_frame = null;
	private CMGridTDStructure m_gridTDStructure = null;

	public CMDeleteFormulaAction(){

		super(CMMessages.getString("TESTDATA_DELETE_FORMULA"));
		putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("TESTDATA_DELETE_FORMULA"));
	    putValue(Action.SMALL_ICON, CMAction.TESTDATA_DELETE_FORMULA.getIcon());
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("DELETE_FORMULA_MNEMONIC").charAt(0));
	    putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_DELETE,0));

	}

	public void actionPerformed(ActionEvent e) {
		CMCompoundEdit ce = new CMCompoundEdit();
		this.m_frame = CMApplication.frame;
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
        confirmation = JOptionPane.showConfirmDialog(m_frame, CMMessages.getString("TESTDATA_MESSAGE_DELETE_FORMULA"),
            CMMessages.getString("TESTDATA_TITLE_MENSSAGE_DELETE_STRUCTURE"), JOptionPane.YES_NO_OPTION);
        if (confirmation == JOptionPane.YES_OPTION) {
        	TestDataFormat testDataFormat = new TestDataFormat();
        	testDataFormat.setOriginalFormatter(BusinessRules.FORMULAS_FORMAT_STRING);
        	testDataFormat.setRealFormat(BusinessRules.FORMULAS_FORMAT_STRING);
        	testDataFormat.setVisualFormatter(BusinessRules.FORMULAS_FORMAT_STRING);
        	if (m_frame.isIsPanelTestDataSelected()){
        		
        		ITypeData selectedTypeData = m_gridTDStructure.getSelectedTypeData();
        		CMDefaultValue defaultValue = new CMDefaultValue("");
        		ce.addEdit(CMModelEditFactory.INSTANCE.createChangeValueInTypeDataModelEdit(selectedTypeData, defaultValue));
        		selectedTypeData.setValue(defaultValue);
        		ce.addEdit(CMModelEditFactory.INSTANCE.createChangeFormatterInTypeDataModelEdit(selectedTypeData, testDataFormat));
        		selectedTypeData.setFormatter(testDataFormat);
        		ce.addEdit(CMModelEditFactory.INSTANCE.createChangeFormatInTypeDataModelEdit(selectedTypeData, testDataFormat.getRealFormat()));
        		selectedTypeData.setFormat(testDataFormat.getRealFormat());
        	}
        	else{
        		
        		ITypeData selectedTypeData = m_gridTDStructure.getSelectedTypeData();
        		CMDefaultValue defaultValue = new CMDefaultValue("");
        		ce.addEdit(CMModelEditFactory.INSTANCE.createChangeValueInTypeDataModelEdit(selectedTypeData, defaultValue));
        		selectedTypeData.setValue(defaultValue);
        		ce.addEdit(CMModelEditFactory.INSTANCE.createChangeFormatterInTypeDataModelEdit(selectedTypeData, testDataFormat));
        		selectedTypeData.setFormatter(testDataFormat);
        		ce.addEdit(CMModelEditFactory.INSTANCE.createChangeFormatInTypeDataModelEdit(selectedTypeData, testDataFormat.getRealFormat()));
        		selectedTypeData.setFormat(testDataFormat.getRealFormat());
        		ce.addEdit(CMTypeDataUtils.updateTypeDataValueForLocalTypeDatas(selectedTypeData));
        	}
        	
        	ce.addUndoDelegateEdit(new CMDelegate(){
    			public void execute() {
    	            m_gridTDStructure.update(CMIndexTDStructureUpdate.getInstance().getIndex());
    			}
    		});
        	//svonborries_29062007_ENABLE THIS OPTION TO REFRESH THE GRID WHEN THE TOOLBAR BUTTON IS PRESSED, THIS METHOD IS UNDER OBSERVATION
        	m_gridTDStructure.update(CMIndexTDStructureUpdate.getInstance().getIndex());
        	
        	if(ce.hasEdits())
            	CMUndoMediator.getInstance().doEdit(ce);
        }

	}

}
