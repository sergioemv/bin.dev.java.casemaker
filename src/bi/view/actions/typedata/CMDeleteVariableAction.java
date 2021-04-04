/**
 *
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
public class CMDeleteVariableAction extends AbstractAction implements Action {

	private CMFrameView m_frame;
	private CMGridTDStructure m_gridTDStructure;


	public CMDeleteVariableAction(){
		super(CMMessages.getString("TESTDATA_DELETE_VARIABLE"));
		putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("TESTDATA_DELETE_VARIABLE"));
	    putValue(Action.SMALL_ICON, CMAction.TESTDATA_DELETE_VARIABLE.getIcon());
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("DELETE_VARIABLE_MNEMONIC").charAt(0));
	    //putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V,Event.CTRL_MASK));
	}

	public void actionPerformed(ActionEvent e) {
		
		CMCompoundEdit ce = new CMCompoundEdit();

		this.m_frame = CMApplication.frame;
		this.m_gridTDStructure = m_frame.getGridTDStructure();
		if(m_frame.isIsPanelTestDataSelected())
			m_gridTDStructure = m_frame.getPanelTestDataView().getM_CMGridTDStructure();
		
		ce.addUndoDelegateEdit(new CMDelegate(){
			public void execute() {
        		m_gridTDStructure.update(CMIndexTDStructureUpdate.getInstance().getIndex());
			}
		});
		ce.addEdit(new CMComponentAwareEdit());

        int confirmation = JOptionPane.YES_OPTION;
        confirmation = JOptionPane.showConfirmDialog(m_frame, CMMessages.getString("TESTDATA_MESSAGE_DELETE_VARIABLES"),
            CMMessages.getString("TESTDATA_TITLE_MENSSAGE_DELETE_STRUCTURE"), JOptionPane.YES_NO_OPTION);
        if (confirmation == JOptionPane.YES_OPTION) {
        	ITypeData typeData = m_gridTDStructure.getSelectedTypeData();
        	CMDefaultValue defaultValue = new CMDefaultValue("");
        	ce.addEdit(CMModelEditFactory.INSTANCE.createChangeValueInTypeDataModelEdit(typeData, defaultValue));
        	typeData.setValue(defaultValue);
        	if(!m_frame.isIsPanelTestDataSelected())
        		ce.addEdit(CMTypeDataUtils.updateTypeDataValueForLocalTypeDatas(typeData));
        	
        	ce.addUndoDelegateEdit(new CMDelegate(){
    			public void execute() {
            		m_gridTDStructure.update(CMIndexTDStructureUpdate.getInstance().getIndex());
    			}
    		});
        	
        //cc	m_gridTDStructure.update(CMIndexTDStructureUpdate.getInstance().getIndex());
        	
        	if(ce.hasEdits())
            	CMUndoMediator.getInstance().doEdit(ce);
        }
	}

}
