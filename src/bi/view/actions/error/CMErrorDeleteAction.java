/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved. 
 */
package bi.view.actions.error;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import model.CMError;
import bi.view.actions.CMAction;
import bi.view.cells.CMErrorCellView;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;

/**
 * @author smoreno
 *
 */
public class CMErrorDeleteAction extends AbstractAction implements Action {
	public CMErrorDeleteAction() {
		super(CMMessages.getString("POPUPMENU_ITEM_DELETE_ERROR"));
	    // Set tool tip text
	    putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("BUTTON_TOOLTIP_DELETE_ERROR"));
	    // Set an icon
	    putValue(Action.SMALL_ICON, CMAction.ERROR_DELETE.getIcon());
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("MENU_ITEM_DELETE_MNEMONIC").charAt(0));
	    putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_DELETE, 0));
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent p_e) {
	        int index = CMApplication.frame.getCMErrorGridView().getSelectedCMErrorCellView();
	        if (index >= 0) {
	            int confirmation = JOptionPane.showConfirmDialog(CMApplication.frame, CMMessages.getString("QUESTION_DELETE_ERROR"),
	                CMMessages.getString("LABEL_DELETE_ERROR"), JOptionPane.YES_NO_OPTION); //$NON-NLS-1$ //$NON-NLS-2$
	            if (confirmation == JOptionPane.YES_OPTION) {
	                CMErrorCellView selectedView = CMApplication.frame.getCMErrorGridView().getSelectedCMErrorCellView(index);
	                CMError error = selectedView.getM_CMError();
	                CMApplication.frame.getM_CMUndoMediator().deleteErrorAt(index, error); //$NON-NLS-1$
	                CMApplication.frame.setStateTestObjectTestManagementErrorDeleted();
	            }
	        }


	}

}
