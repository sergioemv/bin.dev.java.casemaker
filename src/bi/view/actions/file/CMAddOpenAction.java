/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.view.actions.file;

import java.awt.Event;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import model.BusinessRules;

import bi.controller.SessionManager;
import bi.view.actions.CMAction;
import bi.view.actions.CMEnabledAction;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.treeviews.nodes.CMProjectNode;

/**
 * @author smoreno
 *
 */
public class CMAddOpenAction extends AbstractAction implements Action,
		TreeSelectionListener, CMEnabledAction {
	public CMAddOpenAction() {
		super(CMMessages.getString("MENU_ITEM_ADD_PROJECT"));
	    // Set tool tip text
	    putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("MENU_ITEM_ADD_PROJECT"));
	    putValue(Action.SMALL_ICON, CMAction.ADDOPEN.getIcon());
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("MENU_ITEM_ADD_PROJECT_MNEMONIC").charAt(0));
	    putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, Event.CTRL_MASK));
	    this.setEnabled(false);
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent p_e) {
		    CMApplication.frame.eventMouseClicked(null);
		    DefaultMutableTreeNode selectedNode = CMApplication.frame.getTreeWorkspaceView().getSelectedNode();
		    if( selectedNode != null) {
				if( CMApplication.frame.getTreeWorkspaceView().getSelectedWorkspace2() != null) {
					CMApplication.frame.getTreeWorkspaceView().addProjectFromFile2();
				}
				else if( CMApplication.frame.getTreeWorkspaceView().getSelectedProject2() != null) {
					CMApplication.frame.getTreeWorkspaceView().addTestObjectFromFile2();
				}
		    }
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.TreeSelectionListener#valueChanged(javax.swing.event.TreeSelectionEvent)
	 */
	public void valueChanged(TreeSelectionEvent p_e) {
		  DefaultMutableTreeNode selectedNode = CMApplication.frame.getTreeWorkspaceView().getSelectedNode();
		    if(( selectedNode != null)&&(selectedNode!=CMApplication.frame.getTreeWorkspaceView().getM_RootNode())) {
				if( CMApplication.frame.getTreeWorkspaceView().getSelectedWorkspace2() != null) {
					putValue(Action.NAME, CMMessages.getString("MENU_ITEM_ADD_PROJECT"));
					putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("MENU_ITEM_ADD_PROJECT"));
				}
				else  {
					putValue(Action.NAME, CMMessages.getString("ADD_TEST_OBJECT"));
					putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("ADD_TEST_OBJECT"));
				}
		    }


	}
	/* (non-Javadoc)
	 * @see bi.view.actions.CMEnabledAction#calculateEnabled()
	 */
	public boolean calculateEnabled() {
		DefaultMutableTreeNode selectedNode = CMApplication.frame.getTreeWorkspaceView().getSelectedNode();
	    if(( selectedNode != null)&&(selectedNode!=CMApplication.frame.getTreeWorkspaceView().getM_RootNode())) {
			if( CMApplication.frame.getTreeWorkspaceView().getSelectedWorkspace2() != null) {
					return true;
			}
			if( CMApplication.frame.getTreeWorkspaceView().getSelectedProject2() != null)
			{
				if (new File(((CMProjectNode)selectedNode).getProjectReference().getFilePath()).canRead())
				{

					CMProjectNode projectNode = (CMProjectNode)selectedNode ;
					return projectNode .getProject().getAccessState().equals(BusinessRules.ACCESS_STATE_CHECKED_OUT)&&
					projectNode.getProject().getUser().equalsIgnoreCase(SessionManager.getCurrentSession().getM_User());
				}
				else
					return false;

			}
	    }
	    return false;
	}

}
