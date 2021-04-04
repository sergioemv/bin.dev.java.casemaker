/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved. 
 */
package bi.view.actions.help;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;

import model.BusinessRules;
import model.Session2;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;

/**
 * @author smoreno
 *
 */
public class CMOnTheWebAction extends AbstractAction implements Action {
	public CMOnTheWebAction() {
		super(CMMessages.getString("MENU_ITEM_ON_THE_WEB"));
	    // Set tool tip text
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent p_e) {
		// TODO Auto-generated method stub
	
		    Session2 session = CMApplication.frame.getTreeWorkspaceView().getM_Session2();
		    String appPath = CMApplication.frame.getCmApplication().getSessionManager().getExternalApplicationFilePath(BusinessRules.INTERNET_APPLICATION_DENOMINATIVE, session);
		    goToURL(appPath, CMMessages.getString("WEBSITE_CASEMAKER")); //$NON-NLS-1$
	

	}
	void goToURL(String p_Viewer, String p_URL){
	    //hmendez_02122005_begin
	    StringBuffer completePath = new StringBuffer();
	    File app = new File (p_Viewer);
		if(app!=null && app.canRead()){
		  completePath.append(p_Viewer);
		  completePath.append(" "); //$NON-NLS-1$
		  completePath.append(p_URL);
		  try {
			Process theProcess = Runtime.getRuntime().exec(completePath.toString());
		  }
		  catch(IOException exception) {
		    exception.printStackTrace();
		  }
	    }
		else
		{
			if (!CMApplication.frame.runDefaultBrowser(p_URL))
			{
	          JOptionPane.showMessageDialog(null,app.getName()+" "+CMMessages.getString("LABEL_APPLICATION_NOT_FOUND"),
	                CMMessages.getString("TITLE_APPLICATION_NOT_FOUND"), JOptionPane.ERROR_MESSAGE);
			}
	
		}
		
	  }
}
