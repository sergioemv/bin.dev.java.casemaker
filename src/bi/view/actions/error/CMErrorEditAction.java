/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved. 
 */
package bi.view.actions.error;

import java.awt.Dimension;
import java.awt.Event;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;

import model.CMError;
import model.Structure;

import bi.controller.StructureManager;
import bi.view.actions.CMAction;
import bi.view.cells.CMErrorCellView;
import bi.view.errorviews.CMErrorDialogView;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;

/**
 * @author smoreno
 *
 */
public class CMErrorEditAction extends AbstractAction implements Action {
	public CMErrorEditAction() {
		super(CMMessages.getString("POPUPMENU_ITEM_EDIT_ERROR"));
	    // Set tool tip text
	    putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("BUTTON_TOOLTIP_EDIT_ERROR"));
	    // Set an icon
	    putValue(Action.SMALL_ICON, CMAction.ERROR_EDIT.getIcon());
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("MENU_ITEM_EDIT_MNEMONIC").charAt(0));
	    putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, Event.CTRL_MASK));
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent p_e) {
		// TODO Auto-generated method stub
		
	        int index = CMApplication.frame.getCMErrorGridView().getSelectedCMErrorCellView();
	        if (index >= 0) {
	            CMErrorCellView selectedView = CMApplication.frame.getCMErrorGridView().getSelectedCMErrorCellView(index);
	            editCMErrorCellView(selectedView);
	        }
	    
	}
	private void editCMErrorCellView(CMErrorCellView p_CMErrorCellView) {
        CMErrorDialogView dlg = createCMDialogError();
        CMError error = p_CMErrorCellView.getM_CMError();
        Vector oldData = new Vector(0);
        ///////////////saving old data for undo option///////fcastro_01072004
        oldData.addElement(new String(error.getM_Description()));
        oldData.addElement(new String(error.getM_State()));
        oldData.addElement(new String(error.getM_ErrorClass()));
        oldData.addElement(new String(error.getM_Priority()));
        oldData.addElement(new String(error.getM_ClosedBy()));
//HCanedo_30112004_Begin
		if(error.getM_ClosingDate() != null)
        	oldData.addElement(error.getM_ClosingDate().clone());
        else
			oldData.addElement(null);
//HCanedo_30112004_End
        oldData.addElement(error.getM_IssueDate().clone());
        oldData.addElement(new String(error.getM_IssuedBy()));
//HCanedo_30112004_Begin
		if(error.getM_AssignTo() !=null)
			oldData.addElement(new String(error.getM_AssignTo()));
        else
			oldData.addElement(new String());
        if(error.getM_AssignDate() != null)
        	oldData.addElement(error.getM_AssignDate().clone());
        else
            oldData.addElement(null);

//HCanedo_30112004_End
        ////////////////////
        //String oldDescription = error.getM_Description();fcastro_01072004
        dlg.setM_CMError(error);
        dlg.show();
        if (dlg.jButtonOKClicked()) {
            int index = CMApplication.frame.getCMErrorGridView().getSelectedCMErrorCellView();
            if (index >= 0) {
                error = dlg.getM_CMError();
                Vector newData = new Vector(0);
                newData.addElement(new String(dlg.getDescription()));
                newData.addElement(new String(dlg.getState()));
                newData.addElement(new String(dlg.getErrorClass()));
                newData.addElement(new String(dlg.getPriority()));
                newData.addElement(new String(dlg.getClosedBy()));
//HCanedo_30112004_Begin
				if(dlg.getClosingDate() != null)
                	newData.addElement(dlg.getClosingDate().clone());
                else
					newData.addElement(null);
//HCanedo_30112004_End
                newData.addElement(dlg.getIssueDate().clone());
                newData.addElement(new String(dlg.getIssuedBy()));
//HCanedo_30112004_Begin
				newData.addElement(new String(dlg.getAssigTo()));
                newData.addElement(dlg.getAsssignDate());
                Structure selectedStructure = StructureManager.getSelectedStructure();
                Vector testCases = selectedStructure.getLnkTestCases();
                Vector rightList = (Vector)dlg.getRightList().clone();
                Vector leftList =(Vector) dlg.getLeftList().clone();
//HCanedo_30112004_End
                // m_CMErrorGridModel.fireGridRowsChanged(index, 1);fcastro_01072004
                CMApplication.frame.getM_CMUndoMediator().editError(index, oldData, newData, error,
                    testCases, rightList, leftList);
            }
        }
    }
	 private CMErrorDialogView createCMDialogError() {
	     Structure selectedStructure = StructureManager.getSelectedStructure();
        CMErrorDialogView dlg = new CMErrorDialogView(selectedStructure);
        Dimension dlgSize = dlg.getSize();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();	        
        dlg.setLocation((screenSize.width - dlgSize.width) / 2, (screenSize.height - dlgSize.height) / 2);
        dlg.setModal(true);
        return dlg;
    }
}
