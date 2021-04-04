/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved. 
 */
package bi.view.actions.file.reorder;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.tree.TreeNode;

import model.Project2;
import bi.controller.ProjectManager;
import bi.view.actions.CMEnabledAction;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.treeviews.nodes.CMTestObjectNode;

/**
 * @author smoreno
 *
 */
public class CMReorderTestObjectsAction extends AbstractAction implements
		Action, CMEnabledAction {
	public CMReorderTestObjectsAction() {
		super(CMMessages.getString("TESTDATA_REORDER_TESTOBJECT"));    
	    // Set tool tip text
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("TESTDATA_REORDER_TESTOBJECT").charAt(0));
	    setEnabled(false);
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent p_e) {
		 /*
		 *BUG 339
		 *Dscription: keine Reorganisation der Testobjekte möglich
		 *Realizado por: Harold Canedo Lopez
		 */
				//smoreno corrections
		        Project2 currentProject=ProjectManager.getSelectedProject();
		        
		        if(currentProject!=null && (currentProject.getTestObjectReferences().size()== 
		        	((TreeNode)CMApplication.frame.getTreeWorkspaceView().getTreePath(currentProject.getProjectReference()).getLastPathComponent()).getChildCount())){
					if(!CMApplication.frame.getTreeWorkspaceView().isCurrentProjectContainsTestObjectsLocal()){
						CMApplication.frame.getTreeWorkspaceView().showReorderTestObjectDialog();
		            }
		            else{
		                JOptionPane.showMessageDialog(CMApplication.frame,CMMessages.getString("PROJECT_CONTAINS_TESTOBJECT_LOCALS"),CMMessages.getString("TESTDATASETREPORTS_TITLE_INFORMATION"),JOptionPane.INFORMATION_MESSAGE);
		            }
		        }
		        else{
		            JOptionPane.showMessageDialog(CMApplication.frame,CMMessages.getString("PROJECT_NODE_WRONG_NUM_OF_TESTOBJECT_NODES"),CMMessages.getString("TESTDATASETREPORTS_TITLE_INFORMATION"),JOptionPane.INFORMATION_MESSAGE);
		        }
		       
		    }
	/* (non-Javadoc)
	 * @see bi.view.actions.CMEnabledAction#calculateEnabled()
	 */
	public boolean calculateEnabled() {
		return CMApplication.frame.getTreeWorkspaceView().getSelectedNode().getUserObject() instanceof CMTestObjectNode;
	}

	

}
