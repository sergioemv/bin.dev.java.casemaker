/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved. 
 */
package bi.view.actions.file.rename;

import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;

import bi.view.actions.CMAction;
import bi.view.actions.CMEnabledAction;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;

/**
 * @author smoreno
 *
 */
public abstract class CMRenameAction extends AbstractAction implements CMEnabledAction {
	public static final ArrayList<String> INVALID_CHARS = new ArrayList<String>(Arrays.asList("?","*",":","<","/",">","|","%","#","\\","\"","."));
	/**
	 * 
	 */
	public CMRenameAction() {
		super();
		putValue(Action.SMALL_ICON, CMAction.RENAME_WORKSPACE.getIcon());
		//putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F2, 0));
		// TODO Auto-generated constructor stub
	}

	public static boolean existProhibitedCharactersInName(String p_name)
    {	boolean existProhibitedCharacters = false;
    	String invalidChars=""; 
		for (String invalidChar : INVALID_CHARS)
			if (p_name.indexOf(invalidChar)>=0)
			{
				existProhibitedCharacters=true;
				if (invalidChars.length()>0)
					invalidChars=invalidChars+","+invalidChar;
				else
					invalidChars=invalidChars+invalidChar;
			}
        if (existProhibitedCharacters){
        	String message="";
			message = CMMessages.getString("MESSAGE_NAME_INVALID_CHAR_MESSAGE") + invalidChars;
			JOptionPane.showMessageDialog(CMApplication.frame,message , CMMessages.getString("TITLE_MESSAGE_ERROR_NAME"), JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$ //$NON-NLS-2$
			return true;
        }
       return false;
    }
    protected boolean isEmptyName(String p_Name){
        if (p_Name.equals("")) {
                JOptionPane.showMessageDialog(CMApplication.frame, CMMessages.getString("MESSAGE_ERROR_NAME_EMPTY"), CMMessages.getString("TITLE_MESSAGE_ERROR_NAME"), JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$ //$NON-NLS-2$
                return true;
        }
        else
            return false;

    }
    protected boolean isNameToLong(String p_name)
    {
        if(p_name.length()>128){
        	  JOptionPane.showMessageDialog((java.awt.Component)
  		            null, CMMessages.getString("INFO_PROJECT_FILE_NAME_TO_LONG"), 
  		          CMMessages.getString("TITLE_MESSAGE_ERROR_NAME"), JOptionPane.ERROR_MESSAGE);
        	return true;            	
        }
        else
        {
        	return false;
        }    	
    	
    }
    protected boolean isValidName(String p_newName) {	
		return !existProhibitedCharactersInName(p_newName)&&!isEmptyName(p_newName)&&!isNameToLong(p_newName);
	}
}
