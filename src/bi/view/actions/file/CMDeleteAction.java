/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.view.actions.file;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Iterator;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import model.BusinessRules;
import model.Project2;
import model.TestObjectReference;
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
import bi.view.treeviews.nodes.CMTestObjectNode;
import bi.view.undomanagementviews.CMCompoundEdit;
import bi.view.undomanagementviews.undoviews.CMUndoMediator;

/**
 * @author smoreno
 *
 */
public class CMDeleteAction extends AbstractAction implements Action,CMEnabledAction {
	public CMDeleteAction() {
		super(CMMessages.getString("MENU_ITEM_DELETE_1"));
	    // Set tool tip text
	    putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("BUTTON_TOOLTIP_DELETE"));
	    putValue(Action.SMALL_ICON, CMAction.DELETE.getIcon());
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("MENU_ITEM_DELETE_1_MNEMONIC").charAt(0));
	    putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_DELETE, 0));
	    setEnabled(false);
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent p_e) {
	    int confirmation = 0;
	    CMApplication.frame.eventMouseClicked(null);
	    	CMCompoundEdit ce = new CMCompoundEdit();
		    if( CMApplication.frame.getTreeWorkspaceView().getSelectedWorkspace2() != null) {
				confirmation = JOptionPane.showConfirmDialog(CMApplication.frame,CMMessages.getString("QUESTION_DELETE_WORKSPACE"),CMMessages.getString("LABEL_DELETE_WORKSPACE"),JOptionPane.YES_NO_OPTION); //$NON-NLS-1$ //$NON-NLS-2$
				if( confirmation == JOptionPane.YES_OPTION) {
					ce.addEdit(CMModelEditFactory.INSTANCE.createDeleteWorkspaceModelEdit(CMApplication.frame.getTreeWorkspaceView().getSelectedWorkspace2()));
					WorkspaceManager.getInstance().deleteWorkspace2(CMApplication.frame.getTreeWorkspaceView().getSelectedWorkspace2());
					ce.addEdit(CMViewEditFactory.INSTANCE.createTreeSelectNodeEdit(new TreePath(CMApplication.frame.getTreeWorkspaceView().getM_RootNode().getPath())));
					CMApplication.frame.getTreeWorkspaceView().selectRootNode();
				}
		    }
		    else if( CMApplication.frame.getTreeWorkspaceView().getSelectedProject2() != null) {
		    	if (!calculateEnabled())
		    		return;
		    	boolean l_AreChildrenCheckIn = areChildrenCheckIn(CMApplication.frame.getTreeWorkspaceView().getSelectedProject2());
		    	if (l_AreChildrenCheckIn){
		    		confirmation = JOptionPane.showConfirmDialog(CMApplication.frame,CMMessages.getString("QUESTION_DELETE_PROJECT"),CMMessages.getString("LABEL_DELETE_PROJECT"),JOptionPane.YES_NO_OPTION); //$NON-NLS-1$ //$NON-NLS-2$

		    		if( confirmation == JOptionPane.YES_OPTION) {
						//get the parent node for further selection
						DefaultMutableTreeNode node = (DefaultMutableTreeNode) CMApplication.frame.getTreeWorkspaceView().getSelectedNode().getParent();
						ce.addEdit(ProjectManager.getInstance().removeProject(CMApplication.frame.getTreeWorkspaceView().getSelectedProject2().getProjectReference()));
						ce.addEdit(CMViewEditFactory.INSTANCE.createTreeSelectNodeEdit(new TreePath(node.getPath())));
						CMApplication.frame.getTreeWorkspaceView().selectNode(node);
					}
		    	}
		    	else{
		    		JOptionPane.showMessageDialog(CMApplication.frame,CMMessages.getString("CANNOT_DELETE_PROJECT"),CMMessages.getString("LABEL_CASEMAKER"),JOptionPane.ERROR_MESSAGE);
		    	}

		    }
		    else if( CMApplication.frame.getTreeWorkspaceView().getSelectedTestObject2() != null) {
		    	if (!calculateEnabled())
		    		return;
				confirmation = JOptionPane.showConfirmDialog(CMApplication.frame,CMMessages.getString("QUESTION_DELETE_TEST_OBJECT"),CMMessages.getString("LABEL_DELETE_TEST_OBJECT"),JOptionPane.YES_NO_OPTION); //$NON-NLS-1$ //$NON-NLS-2$
				if( confirmation == JOptionPane.YES_OPTION) {
					CMApplication.frame.getTreeWorkspaceView().removeTestObject(CMApplication.frame.getTreeWorkspaceView().getCurrentProject());
				}
		    }
		    if (ce.hasEdits())
		    	CMUndoMediator.getInstance().doEdit(ce);
	}

	//Ccastedo begins 10-04-06
	private boolean areChildrenCheckIn(Project2 p_Project){
		boolean l_States = true;
		for (Iterator iter = p_Project.getTestObjectReferences().iterator(); iter.hasNext();) {
			TestObjectReference l_TestObjectReference = (TestObjectReference) iter.next();
			model.TestObject l_TestObject = (model.TestObject)CMApplication.frame.getTreeWorkspaceView().getM_WorkspaceManager().readTestObjectByReference(l_TestObjectReference,p_Project.getProjectReference(),CMApplication.frame.getTreeWorkspaceView().getM_Session2());
			if ( l_TestObject != null)
				if (!(l_TestObject.getAccessState().equalsIgnoreCase(BusinessRules.ACCESS_STATE_CHECKED_IN))&&
						(l_TestObject.getUser().equalsIgnoreCase(SessionManager.getCurrentSession().getM_User()))){
					l_States = false;
					break;
				}
		}

		return l_States;
	}
	//ccastedo ends 10-04-06

	/* (non-Javadoc)
	 * @see bi.view.actions.CMEnabledAction#calculateEnabled()
	 */
	public boolean calculateEnabled() {
		// TODO Auto-generated method stub
		if ((CMApplication.frame.getTreeWorkspaceView().getSelectedWorkspace2() != null))
		return true;

		if ( CMApplication.frame.getTreeWorkspaceView().getSelectedProject2() != null)
			if (((CMApplication.frame.getTreeWorkspaceView().getSelectedProject2().getAccessState().equalsIgnoreCase(BusinessRules.ACCESS_STATE_CHECKED_IN))&&
				(CMApplication.frame.getTreeWorkspaceView().getSelectedProject2().getUser().equalsIgnoreCase(SessionManager.getCurrentSession().getM_User())))
				||!(CMApplication.frame.getTreeWorkspaceView().getSelectedProject2().getUser().equalsIgnoreCase(SessionManager.getCurrentSession().getM_User())))
				return true;
		//Ccastedo begins 10-04-06
		if ( CMApplication.frame.getTreeWorkspaceView().getSelectedTestObject2() != null)
		{
			CMTestObjectNode testObjectNodeInfo = CMApplication.frame.getTreeWorkspaceView().getSelectedTestObjectNodeInfo();
			CMProjectNode projectNode = (CMProjectNode) CMApplication.frame.getTreeWorkspaceView().getCurrentProjectNode();

			if (testObjectNodeInfo!=null && projectNode!=null)
			{
				//if the test object reference can be readed

				boolean fileExist = ((new File(projectNode.getProjectReference().getPath()+
						BusinessRules.URL_SEPARATOR+
						testObjectNodeInfo.geTestObjectReference().getFilePath())).exists());

				boolean testObjectCHInSameUser = testObjectNodeInfo.getTestObject().getAccessState().equals(BusinessRules.ACCESS_STATE_CHECKED_IN) &&
				testObjectNodeInfo.getTestObject().getUser().equalsIgnoreCase(SessionManager.getCurrentSession().getM_User());

				boolean projectChOutSameUser = projectNode.getProject().getAccessState().equals(BusinessRules.ACCESS_STATE_CHECKED_OUT)&&
				projectNode.getProject().getUser().equalsIgnoreCase(SessionManager.getCurrentSession().getM_User());

				return testObjectCHInSameUser&&fileExist&&projectChOutSameUser;

			}
			else
			{

				return false;
			}



		}

		//Ccastedo ends 10-04-06
		return false;
	}

}
