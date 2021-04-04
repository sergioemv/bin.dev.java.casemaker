/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved. 
 */
package bi.view.actions.dependency;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

import bi.view.lang.CMMessages;

/**
 * @author smoreno
 *
 */
 public class CMDependencyGenerateCombAction extends AbstractAction implements
		Action {
	 public CMDependencyGenerateCombAction() {
		super(CMMessages.getString("POPUPMENU_ITEM_GENERATE_COMBINATIONS"));    
	    // Set tool tip text
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("MENU_ITEM_GENERATE_COMBINATIONS_MNEMONIC").charAt(0));
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent p_e) {
		// TODO Auto-generated method stub
	}


}
