/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved. 
 */
package bi.view.treeviews.nodes;

import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;

import bi.view.treeviews.CMTreeWorkspaceView;

/**
 * @author smoreno
 *
 */
public abstract class CMNode extends DefaultMutableTreeNode  {
	private CMTreeWorkspaceView tree;
	/**
	 * 
	 */
	public CMNode(CMTreeWorkspaceView tree) {
		super();
		// TODO Auto-generated constructor stub
		this.tree = tree;
		
	}
	public CMTreeWorkspaceView getTree() {
		return this.tree;
	}

	public abstract Object getCMModel();
	
	protected DefaultMutableTreeNode findNode(List<DefaultMutableTreeNode> p_nodes, Object p_model) {
		for (DefaultMutableTreeNode node : p_nodes )
			if (node.getUserObject() instanceof CMNode)
			if (((CMNode)node.getUserObject()).getCMModel()==p_model)
				return node;
		return null;
	}
	/**
	 *  move the node to anther position
	*@autor smoreno
	 * @param p_index
	 */
	public abstract void move(int p_index) ;

	public CMNode getNode(Object model)
	{
		CMNode resnode = null;
		CMNode resnode1 = null;
		
		
		if (this.getCMModel() == model)
			return this;
		else
			if (children!=null)
			for (Object child : children){
				if (child instanceof CMNode)
				{
					resnode1 =  ((CMNode)child).getNode(model);
					if (resnode1!=null)
					{
						resnode = resnode1;
						return resnode;
						
					}
				}
			}
		return resnode;
	}
}
