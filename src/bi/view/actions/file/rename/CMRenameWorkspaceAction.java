/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved. 
 */
package bi.view.actions.file.rename;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.Action;
import javax.swing.JOptionPane;

import model.Workspace2;
import model.edit.CMModelEditFactory;
import model.util.CMDelegate;
import bi.controller.SessionManager;
import bi.controller.WorkspaceManager;
import bi.view.edit.CMViewEditFactory;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.treeviews.nodes.CMWorkspaceNode;
import bi.view.undomanagementviews.CMCompoundEdit;
import bi.view.undomanagementviews.undoviews.CMUndoMediator;
import bi.view.utils.CMModalResult;

/**
 * @author smoreno
 *
 */
public class CMRenameWorkspaceAction extends CMRenameAction  {
/**
 * 
 */
public CMRenameWorkspaceAction() {
	super();
	putValue(Action.NAME, CMMessages.getString("RENAME_WORKSPACE_ACTION_NAME"));
	this.setEnabled(false);
}

	public void actionPerformed(ActionEvent p_e) {
		    Workspace2 workspace = WorkspaceManager.getSelectedWorkspace();
	        CMDialogNameChange cmd= new CMDialogNameChange();
	        cmd.getJTextFieldName().setText(workspace.getName());
	        cmd.getJTextFieldName().selectAll();
	        String newName = "";
	        do{
	        cmd.setVisible(true);
	        if (cmd.getModalResult() == CMModalResult.OK){
	        	 newName = cmd.getJTextFieldName().getText();
	        	if (isValidName(newName)){
	        		CMCompoundEdit ce = new CMCompoundEdit();
	        		//save the tree selection
	        		ce.addEdit(CMViewEditFactory.INSTANCE.createTreeSelectNodeEdit(CMApplication.frame.getTreeWorkspaceView().getTreePath(workspace)));
	        		//save the changed name
	        		ce.addEdit(CMModelEditFactory.INSTANCE.createChangeNameModelEdit(workspace,newName,
	        				new CMDelegate(){
								public void execute() {
									SessionManager.INSTANCE.saveSession2(SessionManager.getCurrentSession());
								}}));
	        		workspace.setName(newName);
	        		//save the changes 
	        		SessionManager.INSTANCE.saveSession2(SessionManager.getCurrentSession());
	        		CMUndoMediator.getInstance().doEdit(ce);
	        	}
	        	cmd.setVisible(false);
	        	return;
	        }
	        else
	        	return;
	     }while (!isValidName(newName));//while
	}

	/**
	*@autor smoreno
	 * @param p_newName
	 * @return
	 */
	protected boolean isValidName(String p_newName) {			
		return super.isValidName(p_newName)&&!existWorkspaceName(p_newName);
	}
	private boolean existWorkspaceName(String p_Name){
    	//Ccastedo begins 15-02-06      
        	boolean sw = false;
           	 List workspaces = CMApplication.frame.getTreeWorkspaceView().getM_Session2().getM_Workspaces();
             int sizeworkspaces = workspaces.size();
             for (int i=0;i<sizeworkspaces;i++){
             	Workspace2 workspace = (Workspace2)workspaces.get(i);
             	if (workspace.getName().equals(p_Name)){
             		JOptionPane.showMessageDialog(CMApplication.frame, CMMessages.getString("MESSAGE_ERROR_NAME_WORKSPACE_REPEAT"), CMMessages.getString("TITLE_MESSAGE_ERROR_NAME"), JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$ //$NON-NLS-2$      
             			return true;
             	}         	
             }
             return sw;    
        //Ccastedo ends 15-02-06
    }

	/* (non-Javadoc)
	 * @see bi.view.actions.CMEnabledAction#calculateEnabled()
	 */
	public boolean calculateEnabled() {
		return CMApplication.frame.getTreeWorkspaceView().getSelectedNode() instanceof CMWorkspaceNode;
	}

}
