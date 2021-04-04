/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.view.treeviews.nodes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.ExpandVetoException;
import javax.swing.tree.TreePath;

import model.CMField;
import model.Session2;
import model.Workspace2;
import model.util.CMModelEvent;
import model.util.CMModelListener;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.treeviews.CMTreeWorkspaceView;


/**
 * @author smoreno
 *
 */
public class CMRootNode extends CMNode implements CMModelListener, TreeSelectionListener, TreeWillExpandListener {

	private Session2 session;
	/**
	 * @param p_string
	 */
	public CMRootNode(CMTreeWorkspaceView tree, Session2 p_session2) {
		super(tree);
		this.setUserObject(this);
		this.session = p_session2;
		tree.addTreeSelectionListener(this);
		tree.addTreeWillExpandListener(this);
	}

	public List getChildren()
	{
		if (children!=null)
		return Collections.unmodifiableList(children);
		else
			return null;
	}

	/* (non-Javadoc)
	 * @see bi.view.treeviews.nodes.CMNode#getCMModel()
	 */
	@Override
	public Object getCMModel() {
		return getSession();
	}
	/**
	 * @return Returns the session.
	 */
	public Session2 getSession() {
		return this.session;
	}

	/* (non-Javadoc)
	 * @see model.util.CMModelListener#handleCMModelChange(model.util.CMModelEvent)
	 */
	public void handleCMModelChange(CMModelEvent p_evt) {
		if (p_evt.getSource() instanceof Session2)
		{
			if (p_evt.getChangedField() == CMField.WORKSPACES)
				refreshWorkspaceNodes();
			}

	}
	/**
	*@autor smoreno
	 */
	public void refreshWorkspaceNodes() {
		if (session!=null)
		{
			List<DefaultMutableTreeNode> l_nodes = new ArrayList<DefaultMutableTreeNode>();
			TreePath[] paths = getTree().getVisiblePaths();
			if (children!=null)
				l_nodes.addAll(children);
			//remove those nodes from the root node
			for (DefaultMutableTreeNode node : l_nodes)
			{
					((DefaultTreeModel)getTree().getModel()).removeNodeFromParent(node);
				}
			//add the nodes in the correct order
		     for (Workspace2 workspace :  session.getM_Workspaces())
	           {
	        	   DefaultMutableTreeNode node = findNode(l_nodes,workspace);
	        	   if (node == null)
	        		    node = createWorkspaceNode2(workspace);
	        	   ((DefaultTreeModel)getTree().getModel()).insertNodeInto(node,this,getChildCount());
	           }
		     //expand all the nodes as they were before
		    for (TreePath path : Arrays.asList(paths))
		    	getTree().expandPath(path.getParentPath());
		}
}
	public DefaultMutableTreeNode createWorkspaceNode2(Workspace2 p_Workspace) {

        return new CMWorkspaceNode(this.getTree(),p_Workspace);
    }
	public void createWorkspaceNodes() {
        if (session!=null) {
             DefaultMutableTreeNode workspaceNode = null;
            for (Workspace2 workspace :  session.getM_Workspaces()) {
                workspaceNode = createWorkspaceNode2(workspace);
                add(workspaceNode);
            }
        }
    }
	/* (non-Javadoc)
	 * @see javax.swing.tree.DefaultMutableTreeNode#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return CMMessages.getString("LABEL_WORKSPACES"); //$NON-NLS-1$
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.TreeSelectionListener#valueChanged(javax.swing.event.TreeSelectionEvent)
	 */
	public void valueChanged(TreeSelectionEvent p_e) {
		// TODO Auto-generated method stub

		if ((p_e.getNewLeadSelectionPath()!=p_e.getOldLeadSelectionPath())&&p_e.getNewLeadSelectionPath()!=null)
			if (p_e.getNewLeadSelectionPath().getLastPathComponent().equals(this))
			{
				//set the tabs for the rigth pane
			    CMApplication.frame.addEmptyTabs();
			    CMApplication.frame.setStateWorkspaceRootSelected();
			}
	}

	/* (non-Javadoc)
	 * @see bi.view.treeviews.nodes.CMNode#move(int)
	 */
	@Override
	public void move(int p_index) {
		throw new UnsupportedOperationException("Cannot Move this node");

	}

	public void treeWillCollapse(TreeExpansionEvent event) throws ExpandVetoException {
		// TODO Auto-generated method stub

	}

	public void treeWillExpand(TreeExpansionEvent event) throws ExpandVetoException {
		TreePath path = event.getPath();
	    DefaultMutableTreeNode node = (DefaultMutableTreeNode)path.getLastPathComponent();
	    if (node!=null && node.equals(this))
	    {
	    	CMApplication.frame.setWaitCursor(true);
	    	int numOfWorkpaceNodes = getChildCount();
            Workspace2 workspace = null;
            CMWorkspaceNode workspaceNodeInfo = null;
            for (int i = 0; i < numOfWorkpaceNodes; i++) {
                workspaceNodeInfo = (CMWorkspaceNode)node.getChildAt(i);
                workspaceNodeInfo.createProjectNodes();
            }
            CMApplication.frame.setWaitCursor(false);
	    }
	}
}
