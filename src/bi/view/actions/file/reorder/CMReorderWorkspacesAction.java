/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.view.actions.file.reorder;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import model.CMField;
import model.Session2;
import model.Workspace2;
import model.edit.CMModelEditFactory;
import model.util.CMModelEventHandler;
import bi.controller.SessionManager;
import bi.view.actions.CMEnabledAction;
import bi.view.edit.CMViewEditFactory;
import bi.view.icons.CMIcon;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.treeviews.nodes.CMRootNode;
import bi.view.treeviews.nodes.CMWorkspaceNode;
import bi.view.undomanagementviews.CMCompoundEdit;
import bi.view.undomanagementviews.undoviews.CMUndoMediator;
import bi.view.utils.CMDnDJList;
import bi.view.utils.CMModalResult;

/**
 * @author smoreno
 *
 */
public class CMReorderWorkspacesAction extends AbstractAction implements Action,CMEnabledAction {
	public CMReorderWorkspacesAction() {
		super(CMMessages.getString("FILE_REORDER_WORKSPACES_TITLE")); //$NON-NLS-1$
	    // Set tool tip text
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("FILE_REORDER_WORKSPACES_TITLE").charAt(2)); //$NON-NLS-1$
	   // putValue(Action.ACCELERATOR_KEY,KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, Event.CTRL_MASK));
	    setEnabled(false);
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent p_e) {

		CMReorderDialog dlg = new CMReorderDialog();
		List<Workspace2> workspaces = new ArrayList<Workspace2>( SessionManager.getCurrentSession().getM_Workspaces());
		Session2 session = SessionManager.getCurrentSession();
		dlg.AddObjectsToOrder(workspaces);
		dlg.setTitle(CMMessages.getString("LABEL_WORKSPACES")+" - "+this.getValue(Action.NAME)); //$NON-NLS-1$ //$NON-NLS-2$
		((CMDnDJList)dlg.getOrderjList()).setLeftIcon(CMIcon.WORKSPACE.getImageIcon());
		dlg.setVisible(true);


		if (dlg.getModalResult() == CMModalResult.OK)
		{
			List correctOrder = dlg.getOrderedObjects();
			CMCompoundEdit ce = new CMCompoundEdit();
			CMModelEventHandler.setNotifyEnabled(false);
			TreePath path = CMApplication.frame.getTreeWorkspaceView().getSelectionPath();
			ce.addEdit(CMViewEditFactory.INSTANCE.createTreeSelectNodeEdit(path));
			//remove all the workspaces
			for (Workspace2 workspace : workspaces)
			{
				ce.addEdit(CMModelEditFactory.INSTANCE.createDeleteWorkspaceModelEdit(workspace));
				session.removeWorkspace(workspace);
			}
			//add them in the correct order
			for (Object ob : correctOrder)
				if (workspaces.contains(ob)){
				ce.addEdit(CMModelEditFactory.INSTANCE.createAddWorkspaceModelEdit(session,(Workspace2) ob));
				session.addWorkspace((Workspace2) ob);
				}
			CMModelEventHandler.setNotifyEnabled(true);
			//fire the event
			session.getHandler().fireModelEventHappen(session,CMField.WORKSPACES);
			//to enhance the undo and redo performance add a big reorder edit and not single add / remove edits
			ce.addEdit(CMViewEditFactory.INSTANCE.createTreeSelectNodeEdit(path));
			CMApplication.frame.getTreeWorkspaceView().selectNode(path);
			if (ce.hasEdits())
				CMUndoMediator.getInstance().doEdit(ce);
		}
	}
	/* (non-Javadoc)
	 * @see bi.view.actions.CMEnabledAction#calculateEnabled()
	 */
	public boolean calculateEnabled() {
		DefaultMutableTreeNode node = CMApplication.frame.getTreeWorkspaceView().getSelectedNode();
		boolean goodNode = node instanceof CMRootNode || node instanceof CMWorkspaceNode;
		return CMApplication.frame.getTreeWorkspaceView().getM_RootNode().getChildCount()>1&&goodNode;
	}

}
