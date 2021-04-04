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
public class CMHideShowTreeAction extends AbstractAction implements Action {
	public CMHideShowTreeAction() {
		super(CMMessages.getString("MENU_HIDE_SHOW_TREE_PROJECT"));    

	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("MENU_ITEM_HIDE_SHOW_TREE_MNEMONIC").charAt(0));
	    putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T, Event.CTRL_MASK));
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent p_e) {
		// TODO Auto-generated method stub
		if(CMApplication.frame.getJSplitPaneTreeTabs().getDividerLocation() == 0 || 
				CMApplication.frame.getJSplitPaneTreeTabs().getDividerLocation() == 1)
			CMApplication.frame.getJSplitPaneTreeTabs().setDividerLocation(300);
		else
			CMApplication.frame.getJSplitPaneTreeTabs().setDividerLocation(0);
	}

}
