/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved. 
 */
package bi.view.actions.error;

import java.awt.Dimension;
import java.awt.Event;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;

import model.CMError;
import model.Structure;
import bi.controller.StructureManager;
import bi.view.actions.CMAction;
import bi.view.errorviews.CMErrorDialogView;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;

import com.eliad.model.GenericGridModel;

/**
 * @author smoreno
 *
 */
public class CMErrorCreateAction extends AbstractAction implements Action {
	public CMErrorCreateAction() {
		super(CMMessages.getString("POPUPMENU_ITEM_CREATE_ERROR"));    
	    // Set tool tip text
	    putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("BUTTON_TOOLTIP_CREATE_NEW_ERROR"));
	    putValue(Action.SMALL_ICON, CMAction.ERROR_CREATE.getIcon());
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("MENU_ITEM_CREATE_MNEMONIC").charAt(0));
	    putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, Event.CTRL_MASK));
	    
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent p_e) {
		// TODO Auto-generated method stub
	    CMErrorDialogView dlg = createCMDialogError();
        dlg.setM_CMError(null);
        dlg.show();
        if (dlg.jButtonOKClicked()) {
            CMError newError = dlg.getM_CMError();           
            int index = ((GenericGridModel)CMApplication.frame.getCMErrorGridView().getModel()).getRowCount();
            CMApplication.frame.getM_CMUndoMediator().addErrorAt(index, newError); //$NON-NLS-1$
            CMApplication.frame.setStateTestObjectTestManagementErrorAdded();
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
