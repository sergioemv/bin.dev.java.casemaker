/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.view.treeviews.nodes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.ExpandVetoException;
import javax.swing.tree.TreePath;

import model.CMField;
import model.ProjectReference;
import model.Session2;
import model.Workspace2;
import model.util.CMModelEvent;
import model.util.CMModelListener;
import bi.controller.SessionManager;
import bi.controller.WorkspaceManager;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.treeviews.CMTreeWorkspaceView;
import bi.view.workspaceviews.CMPanelWorkspaceView;

public class CMWorkspaceNode extends CMNode implements CMModelListener, TreeSelectionListener, TreeWillExpandListener{
    private Workspace2 m_Workspace = null;
	private CMPanelWorkspaceView panelWorkspace;

    public CMWorkspaceNode(CMTreeWorkspaceView tree, Workspace2 p_Workspace) {
    	super(tree);
        m_Workspace = p_Workspace;
        //listen to changes in the workspace
        m_Workspace.addModelListener(this);

        //compatibilty
        this.setUserObject(this);
        tree.addTreeSelectionListener(this);
        tree.addTreeWillExpandListener(this);
    }

    public Workspace2 getM_Workspace() {
        return m_Workspace;
    }

    public void setM_Workspace(Workspace2 p_Workspace) {
        m_Workspace = p_Workspace;
    }

    public String toString() {
        return m_Workspace.getName();
    }

	/* (non-Javadoc)
	 * @see model.util.CMModelListener#handleCMModelChange(model.util.CMModelEvent)
	 */
	public void handleCMModelChange(CMModelEvent p_evt) {
		if (p_evt.getSource() instanceof Workspace2)
			if(p_evt.getChangedField() == CMField.PROJECT_REFERENCES)
				refreshProjectsNodes();
			if(p_evt.getChangedField() == CMField.NAME){
					this.getTree().treeDidChange();
				}

	}

	/* (non-Javadoc)
	 * @see bi.view.treeviews.nodes.CMNode#getCMModel()
	 */
	@Override
	public Object getCMModel() {
		return getM_Workspace();
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.TreeSelectionListener#valueChanged(javax.swing.event.TreeSelectionEvent)
	 */
	public void valueChanged(TreeSelectionEvent p_e) {
		//if the selection has changed
		if ((p_e.getNewLeadSelectionPath()!=p_e.getOldLeadSelectionPath())&&p_e.getNewLeadSelectionPath()!=null)
			if (p_e.getNewLeadSelectionPath().getLastPathComponent().equals(this))
				//set the tabs for the rigth pane
			{
				CMApplication.frame.addWorkspaceTabs(getPanelWorkspace());
				CMApplication.frame.setStateOneTestObjects();
				CMApplication.frame.setStateWorkspaceSelected();
				CMApplication.frame.getTabbedPane().setEnabled(true);
			}
	}

	/**
	*@autor smoreno
	 * @return
	 */
	private CMPanelWorkspaceView getPanelWorkspace() {
		// TODO Auto-generated method stub
		if (this.panelWorkspace==null)
		{
			this.panelWorkspace = new CMPanelWorkspaceView();
			this.panelWorkspace.setM_Workspace(this.m_Workspace);
		}
		CMApplication.frame.initializeTheUndoManager();//svonborries_30072007
		return this.panelWorkspace;
	}

	/* (non-Javadoc)
	 * @see bi.view.treeviews.nodes.CMNode#move(int)
	 */
	@Override
	public void move(int p_index) {
		WorkspaceManager.getInstance().moveWorkspace(m_Workspace,p_index);

	}
	/**
	 * Verifies and updates if the workspace node has all the project nodes
	 * correctly added to it
	 */
	public void refreshProjectsNodes() {
		if (m_Workspace!=null)
		{
			List<DefaultMutableTreeNode> l_nodes = new ArrayList<DefaultMutableTreeNode>();
			DefaultMutableTreeNode selectionNode =null;
			TreePath[] paths = getTree().getVisiblePaths();
			if (children!=null)
				l_nodes.addAll(children);
			//remove those nodes from the root node
			for (DefaultMutableTreeNode node : l_nodes)
			{
					((DefaultTreeModel)getTree().getModel()).removeNodeFromParent(node);
				}
			//add the nodes in the correct order
		     for (ProjectReference projectReference : m_Workspace.getM_ProjectReferences())
	           {
	        	   DefaultMutableTreeNode node = findNode(l_nodes,projectReference);
	        	   if (node == null )
	        		    node = createProjectNode2(projectReference);
//	        	   if (projectReference.getProject()==null)
//	        		   continue;
	        	   ((DefaultTreeModel)getTree().getModel()).insertNodeInto(node,this,getChildCount());
	           }
		    for (TreePath path : Arrays.asList(paths))
		    	getTree().expandPath(path.getParentPath());

		}
}

    public DefaultMutableTreeNode createProjectNode2(ProjectReference p_ProjectReference) {
        return new CMProjectNode(getTree(),p_ProjectReference);
    }

	public void treeWillCollapse(TreeExpansionEvent event) throws ExpandVetoException {
		// TODO Auto-generated method stub

	}

	public void treeWillExpand(TreeExpansionEvent event) throws ExpandVetoException {
		TreePath path = event.getPath();
	    DefaultMutableTreeNode node = (DefaultMutableTreeNode)path.getLastPathComponent();
	    if (node!=null && node.equals(this))
	    {
	    	  //Clicked on "+" of Workspace  for(i= 0 to N Projects)  CreateTestObjectNodes()
	    	CMApplication.frame.setWaitCursor(true);
            int numOfProjectNodes = node.getChildCount();
            CMProjectNode projectNodeInfo = null;
            for (int i = 0; i < numOfProjectNodes; i++) {
            	projectNodeInfo = (CMProjectNode)node.getChildAt(i);
                projectNodeInfo.addChildrenNodes();
             //   createTestObjectNodes2(projectNode, projectReference);
            }
            CMApplication.frame.setWaitCursor(false);
	    }

	}
	public void createProjectNodes() {
        Vector<ProjectReference> invalidProjectReferences = new Vector<ProjectReference>(0);
        ProjectReference projectReference = null;
        if (getM_Workspace() != null) {
            int numOfProjectReferences = getM_Workspace().getM_ProjectReferences().size();
            projectReference = null;
            DefaultMutableTreeNode projectReferenceNode = null;
            for (int i = 0; i < numOfProjectReferences; i++) {
                projectReference = (ProjectReference)getM_Workspace().getM_ProjectReferences().get(i);
                if (!istProjectReferenceUnderWorkspaceNode(projectReference)) {
                    projectReferenceNode = createProjectNode(projectReference);
                    if (projectReferenceNode != null) {
                        add(projectReferenceNode);
                    }
                    else {
                        invalidProjectReferences.addElement(projectReference);
                    }
                }
            }
            showMessageOnInvalidProjectReferences(invalidProjectReferences);
            //deleteInvalidProjectReferences(p_Workspace, invalidProjectReferences, m_Session2);
        }
    }

    private boolean istProjectReferenceUnderWorkspaceNode(ProjectReference p_ProjectReference) {
                int numOfChildren = getChildCount();
                CMProjectNode projectNodeInfo = null;
                for (int i = 0; i < numOfChildren; i++) {
                    DefaultMutableTreeNode projectNode = (DefaultMutableTreeNode)getChildAt(i);
                    projectNodeInfo = (CMProjectNode)projectNode;
                    if (projectNodeInfo.getProjectReference().getFilePath().equals(p_ProjectReference.getFilePath())) {
                        return true;
                    }
                }
                return false;
        }

    public DefaultMutableTreeNode createProjectNode(ProjectReference p_ProjectReference) {
        if (p_ProjectReference != null) {
            return new CMProjectNode(this.getTree(),p_ProjectReference);
        }
        else {
            return null;
        }
    }


    public void showMessageOnInvalidProjectReferences(Vector p_InvalidProjectReferences) {
        ProjectReference projectReference = null;
        int numOfInvalidProjectReferences = p_InvalidProjectReferences.size();
        if (numOfInvalidProjectReferences > 0) {
            StringBuffer sBuffer = new StringBuffer();
            for (int i = 0; i < numOfInvalidProjectReferences; i++) {
                projectReference = (ProjectReference)p_InvalidProjectReferences.elementAt(i);
                sBuffer.append(projectReference.getFilePath());
                sBuffer.append("\n"); //$NON-NLS-1$
            }
            Object[] arguments = {
			  sBuffer.toString()
			};
            String message = java.text.MessageFormat.format(CMMessages.getString("INFO_PROJECT_NOT_FOUND"), arguments); //$NON-NLS-1$
            JOptionPane.showMessageDialog(CMApplication.frame, message);
        }
    }
    public void deleteInvalidProjectReferences(Workspace2 p_Workspace, Vector p_InvalidProjectReferences, Session2 p_Session) {
        int numOfInvalidProjectReferences = p_InvalidProjectReferences.size();
        ProjectReference projectReference = null;
        for (int i = 0; i < numOfInvalidProjectReferences; i++) {
            projectReference = (ProjectReference)p_InvalidProjectReferences.elementAt(i);
            if (p_Workspace.getM_ProjectReferences().contains(projectReference)) {
                p_Workspace.removeProjectReference(projectReference);
            }
        }
        if (numOfInvalidProjectReferences > 0) {
            SessionManager.INSTANCE.writeSession2ToFile(p_Session);
        }
    }

}