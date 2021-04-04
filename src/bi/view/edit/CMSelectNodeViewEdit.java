/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved. 
 */
package bi.view.edit;

import javax.swing.tree.TreePath;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoableEdit;

import org.apache.log4j.Logger;

import bi.view.mainframeviews.CMApplication;
import bi.view.treeviews.CMTreeWorkspaceView;

/**
 * @author smoreno
 *
 */
public class CMSelectNodeViewEdit extends AbstractUndoableEdit implements
		UndoableEdit {

	private TreePath path;
	private CMTreeWorkspaceView tree;
	private TreePath oldPath;

	/**
	 * @param p_node 
	 * 
	 */
	public CMSelectNodeViewEdit(TreePath p_path) {
		super();
		this.path = p_path;
		this.tree = CMApplication.frame.getTreeWorkspaceView();
		oldPath = tree.getSelectionModel().getSelectionPath();
	}
	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#undo()
	 */
	@Override
	public void undo() throws CannotUndoException {
		super.undo();
		tree.selectNode(oldPath);
	
		
	}
	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#redo()
	 */
	@Override
	public void redo() throws CannotRedoException {
		// TODO Auto-generated method stub
		super.redo();
	    tree.selectNode(path);
	    tree.requestFocus();
	    Logger.getLogger(this.getClass()).info("Selecction "+tree.isPathSelected(path));
	}

}
