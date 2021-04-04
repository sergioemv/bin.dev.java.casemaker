/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved. 
 */
package bi.view.actions.help;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import model.BusinessRules;
import model.Session2;
import bi.controller.SessionManager;
import bi.view.actions.CMAction;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;

/**
 * @author smoreno
 *
 */
public class CMHelpAction extends AbstractAction implements Action {
/**
 * 
 */
public CMHelpAction() {
	super(CMMessages.getString("MENU_ITEM_APPLICATION_CONTENTS"));
    // Set tool tip text
    putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("BUTTON_TOOLTIP_HELP"));
    // Set an icon
    putValue(Action.SMALL_ICON, CMAction.HELP.getIcon());
    putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1,0));

}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent p_e) {
		// TODO Auto-generated method stub
		CMApplication.frame.eventMouseClicked(null);
		    Session2 session = CMApplication.frame.getTreeWorkspaceView().getM_Session2();
		    String appPath = SessionManager.INSTANCE.getExternalApplicationFilePath(BusinessRules.INTERNET_APPLICATION_DENOMINATIVE, session);
		    File app = new File (appPath);
			if(app!=null && app.canRead()){
		    	CMApplication.frame.viewFile(appPath,BusinessRules.DOCUMENT_HELP_CONTENTS);
		    }
		    else{
		        JOptionPane.showMessageDialog(null,app.getName()+" "+CMMessages.getString("LABEL_APPLICATION_NOT_FOUND"),CMMessages.getString("TITLE_APPLICATION_NOT_FOUND"),JOptionPane.ERROR_MESSAGE);
		    }


	}

}
