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
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import model.BusinessRules;
import model.ProjectReference;
import model.Workspace2;
import model.edit.CMModelEditFactory;
import bi.controller.ProjectManager;
import bi.controller.SessionManager;
import bi.controller.WorkspaceManager;
import bi.view.actions.CMAction;
import bi.view.actions.CMEnabledAction;
import bi.view.edit.CMViewEditFactory;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.treeviews.nodes.CMProjectNode;
import bi.view.treeviews.nodes.CMWorkspaceNode;
import bi.view.undomanagementviews.CMCompoundEdit;
import bi.view.undomanagementviews.undoviews.CMUndoMediator;

/**
 * @author smoreno
 *
 */
public class CMCreateAction extends AbstractAction implements Action, TreeSelectionListener, CMEnabledAction {
	private Object newObject;
	public CMCreateAction() {
		super(CMMessages.getString("MENU_ITEM_CREATE_WORKSPACE"));    
	    // Set tool tip text
	    putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("MENU_ITEM_CREATE_WORKSPACE"));
	    putValue(Action.SMALL_ICON, CMAction.CREATE.getIcon());
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("MENU_ITEM_CREATE_MNEMONIC").charAt(0));
	    putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, Event.CTRL_MASK));

	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent p_e) {
		// TODO Auto-generated method stub

			CMApplication.frame.eventMouseClicked(null);//integration_fcastro_17082004
		    DefaultMutableTreeNode selectedNode = CMApplication.frame.getTreeWorkspaceView().getSelectedNode();
		    CMCompoundEdit ce = new CMCompoundEdit();
		    if( selectedNode != null) {
				if( selectedNode == CMApplication.frame.getTreeWorkspaceView().getM_RootNode()) {

					//create a workspace and add to the current session
					ce.addEdit(createWorkspace());
					//save the node selection
					ce.addEdit(CMViewEditFactory.INSTANCE.createTreeSelectNodeEdit(CMApplication.frame.getTreeWorkspaceView().getTreePath(newObject)));		
							CMApplication.frame.getTreeWorkspaceView().selectNode(new TreePath(CMApplication.frame.
							getTreeWorkspaceView().getM_RootNode().getNode(newObject).getPath()));		
				}
				else if(  CMApplication.frame.getTreeWorkspaceView().getSelectedWorkspace2() != null) {
					//ce.addEdit(createProject(CMApplication.frame.getTreeWorkspaceView().getSelectedWorkspace2()));
					ce=(CMCompoundEdit) ProjectManager.getInstance().addNewProject2(CMApplication.frame.getTreeWorkspaceView().getSelectedWorkspace2());
//					
					if (!ce.hasEdits())
						return;
					//select the new node
					ProjectReference newReference = CMApplication.frame.getTreeWorkspaceView().getSelectedWorkspace2().getM_ProjectReferences().get(CMApplication.frame.getTreeWorkspaceView().getSelectedWorkspace2().getM_ProjectReferences().size()-1);
					if (newReference!=null&&CMApplication.frame.
							getTreeWorkspaceView().getM_RootNode().getNode(newReference)!=null)
					{
					ce.addEdit(CMViewEditFactory.INSTANCE.createTreeSelectNodeEdit(new TreePath(CMApplication.frame.
							getTreeWorkspaceView().getM_RootNode().getNode(newReference).getPath())));
					CMApplication.frame.getTreeWorkspaceView().selectNode(new TreePath(CMApplication.frame.
							getTreeWorkspaceView().getM_RootNode().getNode(newReference).getPath()));
					}
				}
				else if(  CMApplication.frame.getTreeWorkspaceView().getSelectedProject2() != null) {
					 CMApplication.frame.getTreeWorkspaceView().addNewTestObject();
				}
				if (ce.hasEdits())
					CMUndoMediator.getInstance().doEdit(ce);
				 CMApplication.frame.getTreeWorkspaceView().repaint();
		    }

		  
	}

	/**
	*@return 
	 * @autor smoreno
	 */
	private CMCompoundEdit createWorkspace() {
		// TODO Auto-generated method stub
		CMCompoundEdit ce = new CMCompoundEdit();
		newObject = WorkspaceManager.getInstance().createWorkspace2(SessionManager.getCurrentSession());
		Workspace2 workspace = (Workspace2) newObject;
		String newName = WorkspaceManager.getInstance().generateNewWorkspaceName(SessionManager.getCurrentSession());
		ce.addEdit(CMModelEditFactory.INSTANCE.createChangeNameModelEdit(workspace,newName));
		 workspace.setName(newName);
		 ce.addEdit(CMModelEditFactory.INSTANCE.createAddWorkspaceModelEdit(SessionManager.getCurrentSession(),workspace));
		 WorkspaceManager.getInstance().addWorkspace2ToSession2(workspace,SessionManager.getCurrentSession());
		 return ce;
	}
	/* (non-Javadoc)
	 * @see javax.swing.event.TreeSelectionListener#valueChanged(javax.swing.event.TreeSelectionEvent)
	 */
	public void valueChanged(TreeSelectionEvent p_e) {
		// TODO Auto-generated method stub

		 DefaultMutableTreeNode selectedNode = CMApplication.frame.getTreeWorkspaceView().getSelectedNode();
		    if( selectedNode != null) {
				if( selectedNode == CMApplication.frame.getTreeWorkspaceView().getM_RootNode()) {
					putValue(Action.NAME, CMMessages.getString("MENU_ITEM_CREATE_WORKSPACE"));
					putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("MENU_ITEM_CREATE_WORKSPACE"));
				}
				else if(  CMApplication.frame.getTreeWorkspaceView().getSelectedWorkspace2() != null) {
					putValue(Action.NAME, CMMessages.getString("POPUPMENU_ITEM_CREATE_PROJECT"));
					putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("POPUPMENU_ITEM_CREATE_PROJECT"));
	
				}
				else if(  CMApplication.frame.getTreeWorkspaceView().getSelectedProject2() != null) {
					putValue(Action.NAME, CMMessages.getString("MENU_ITEM_CREATE_TEST_OBJECT"));
					putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("MENU_ITEM_CREATE_TEST_OBJECT"));
	
				}
		    }
	}
	/* (non-Javadoc)
	 * @see bi.view.actions.CMEnabledAction#calculateEnabled()
	 */
	public boolean calculateEnabled() {
		DefaultMutableTreeNode selectedNode = CMApplication.frame.getTreeWorkspaceView().getSelectedNode();
		if( selectedNode == CMApplication.frame.getTreeWorkspaceView().getM_RootNode()) {
			return true;
		} else
	   if (selectedNode instanceof CMWorkspaceNode)
		   return true;
	   else
		  if  (selectedNode instanceof CMProjectNode) {
		   CMProjectNode pnode = (CMProjectNode) selectedNode;
			return pnode.getProject()!=null &&(pnode.getProject().getAccessState().equals(BusinessRules.ACCESS_STATE_CHECKED_OUT))&&
			pnode.getProject().getUser().equalsIgnoreCase(SessionManager.getCurrentSession().getM_User());
		}
		return false;
	}

}
