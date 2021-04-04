/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved. 
 */
package bi.view.actions.file;

import java.awt.Event;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;

import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;

/**
 * @author smoreno
 *
 */
public class CMImportProjectAction extends AbstractAction implements Action {
	public CMImportProjectAction() {
		super(CMMessages.getString("MENU_ITEM_IMPORT_PROJECT"));    
	    // Set tool tip text
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("MENU_ITEM_IMPORT_PROJECT_MNEMONIC").charAt(0));
	    putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, Event.CTRL_MASK));
	    this.setEnabled(false);
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent p_e) {
			if( CMApplication.frame.getTreeWorkspaceView().getSelectedWorkspace2() != null) {
				CMApplication.frame.getTreeWorkspaceView().importProject();
			}
	    
	}

}
