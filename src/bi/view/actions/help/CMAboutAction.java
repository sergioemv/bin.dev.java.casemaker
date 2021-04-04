/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved. 
 */
package bi.view.actions.help;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

import bi.view.aboutviews.CMAboutDialog;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;

/**
 * @author smoreno
 *
 */
public class CMAboutAction extends AbstractAction implements Action {
	public CMAboutAction() {
		super(CMMessages.getString("MENU_ITEM_ABOUT_CASE_MAKER"));    
	    // Set tool tip text
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("MENU_ITEM_ABOUT_MNEMONIC").charAt(0));
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent p_e) {
		// TODO Auto-generated method stub
		CMAboutDialog aboutDialog = new CMAboutDialog(CMApplication.frame, true);
		 Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	        Dimension dlgSize = aboutDialog.getSize();
	        aboutDialog.setLocation((screenSize.width - dlgSize.width) / 2 /*+ loc.x*/,
	            (screenSize.height - dlgSize.height) / 2 /*+ loc.y*/);
        aboutDialog.setVisible(true);
    }
	

}
