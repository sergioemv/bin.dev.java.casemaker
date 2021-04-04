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
public class CMExitAction extends AbstractAction implements Action {
	public CMExitAction() {
		super(CMMessages.getString("MENU_ITEM_EXIT"));    
	    // Set tool tip text
	    putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("BUTTON_TOOLTIP_PASTE"));
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("MENU_ITEM_EXIT_MNEMONIC").charAt(0));
	    putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, Event.CTRL_MASK));
	    
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent p_e) {
		// TODO Auto-generated method stub
		CMApplication.frame.exitForm(null);
	}

}
