/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.view.utils;

import java.awt.Point;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragGestureRecognizer;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.IOException;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import bi.view.treeviews.nodes.CMNode;
import bi.view.treeviews.nodes.CMWorkspaceNode;

/**
 * @author smoreno
 *
 */
public class CMDnDJTree extends JTree implements DragSourceListener, DropTargetListener, DragGestureListener  {
	/**
	 * @author smoreno
	 *
	 */
	public class CMTransferable implements Transferable {
		private TreeNode node;

		/**
		 * @param p_draggedNode
		 */
		public CMTransferable(TreeNode p_draggedNode) {
			this.node = p_draggedNode;
		}

		/* (non-Javadoc)
		 * @see java.awt.datatransfer.Transferable#getTransferDataFlavors()
		 */
		public DataFlavor[] getTransferDataFlavors() {
			return supportedFlavors;
		}

		/* (non-Javadoc)
		 * @see java.awt.datatransfer.Transferable#isDataFlavorSupported(java.awt.datatransfer.DataFlavor)
		 */
		public boolean isDataFlavorSupported(DataFlavor p_flavor) {
			return (p_flavor.equals (localObjectFlavor));
		}

		/* (non-Javadoc)
		 * @see java.awt.datatransfer.Transferable#getTransferData(java.awt.datatransfer.DataFlavor)
		 */
		public Object getTransferData(DataFlavor p_flavor)
				throws UnsupportedFlavorException, IOException {
			if (isDataFlavorSupported (p_flavor))
				return node;
			else
				throw new UnsupportedFlavorException(p_flavor);
			}


	}

	/**
	 * @author smoreno
	 *
	 */

	static DataFlavor localObjectFlavor;
	static {
		try {
					localObjectFlavor = new DataFlavor (DataFlavor.javaJVMLocalObjectMimeType);
				} catch (ClassNotFoundException cnfe) {
					cnfe.printStackTrace(); }
				}
	static DataFlavor[] supportedFlavors = { localObjectFlavor };
	DragSource dragSource;
	DropTarget dropTarget;
	protected TreeNode dropTargetNode = null;
	protected TreeNode draggedNode = null;

	/**
	 *
	 */
	public CMDnDJTree() {
		super();
		//setCellRenderer (new CMDnDTreeCellRenderer());
		//setModel (new DefaultTreeModel(new DefaultMutableTreeNode("default")));
		dragSource = new DragSource();
		DragGestureRecognizer dgr = dragSource.createDefaultDragGestureRecognizer (this,DnDConstants.ACTION_MOVE,this);
		dropTarget = new DropTarget (this, this);
	}

	/* (non-Javadoc)
	 * @see java.awt.dnd.DragSourceListener#dragEnter(java.awt.dnd.DragSourceDragEvent)
	 */
	public void dragEnter(DragSourceDragEvent p_dsde) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see java.awt.dnd.DragSourceListener#dragOver(java.awt.dnd.DragSourceDragEvent)
	 */
	public void dragOver(DragSourceDragEvent p_dsde) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see java.awt.dnd.DragSourceListener#dropActionChanged(java.awt.dnd.DragSourceDragEvent)
	 */
	public void dropActionChanged(DragSourceDragEvent p_dsde) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see java.awt.dnd.DragSourceListener#dragExit(java.awt.dnd.DragSourceEvent)
	 */
	public void dragExit(DragSourceEvent p_dse) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see java.awt.dnd.DragSourceListener#dragDropEnd(java.awt.dnd.DragSourceDropEvent)
	 */
	public void dragDropEnd(DragSourceDropEvent p_dsde) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see java.awt.dnd.DropTargetListener#dragEnter(java.awt.dnd.DropTargetDragEvent)
	 */
	public void dragEnter(DropTargetDragEvent p_dtde) {
		p_dtde.acceptDrag(DnDConstants.ACTION_COPY_OR_MOVE);
	}

	/* (non-Javadoc)
	 * @see java.awt.dnd.DropTargetListener#dragOver(java.awt.dnd.DropTargetDragEvent)
	 */
	public void dragOver(DropTargetDragEvent p_dtde) {
		Point dragPoint = p_dtde.getLocation();
		TreePath path = getPathForLocation (dragPoint.x, dragPoint.y);
		try {
			if ((path == null)|| ((TreeNode) path.getLastPathComponent()).getClass()!=p_dtde.getTransferable().getTransferData(localObjectFlavor).getClass())
			   dropTargetNode = null;
   else
			   dropTargetNode = (TreeNode) path.getLastPathComponent();
		} catch (UnsupportedFlavorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		repaint();

	}

	/* (non-Javadoc)
	 * @see java.awt.dnd.DropTargetListener#dropActionChanged(java.awt.dnd.DropTargetDragEvent)
	 */
	public void dropActionChanged(DropTargetDragEvent p_dtde) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see java.awt.dnd.DropTargetListener#dragExit(java.awt.dnd.DropTargetEvent)
	 */
	public void dragExit(DropTargetEvent p_dte) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see java.awt.dnd.DropTargetListener#drop(java.awt.dnd.DropTargetDropEvent)
	 */
	public void drop(DropTargetDropEvent p_dtde) {
		Point dropPoint = p_dtde.getLocation();
		// int index = locationToIndex (dropPoint);
		TreePath path = getPathForLocation (dropPoint.x, dropPoint.y);
		if (path==null){
			//if not inside one node reject the drop
			p_dtde.rejectDrop();
			dropTargetNode = null;
			return;
		}
		boolean dropped = false;
		try {
			Object droppedObject = p_dtde.getTransferable().getTransferData(localObjectFlavor);
			DefaultMutableTreeNode droppedNode = null;
			if (droppedObject instanceof MutableTreeNode) {
				// remove from old location
				droppedNode = (DefaultMutableTreeNode) droppedObject;

				DefaultMutableTreeNode dropNode =
					(DefaultMutableTreeNode) path.getLastPathComponent();
				if (droppedNode.getUserObject().getClass()!=dropNode.getUserObject().getClass()){
					p_dtde.rejectDrop();
					dropTargetNode = null;
				    return;}
					//accept the drop and check the content
//				get the parent of the node
		     	DefaultMutableTreeNode parent =(DefaultMutableTreeNode) dropNode.getParent();
		        	int index = parent.getIndex (dropNode);

		        	 if (droppedNode instanceof CMNode){
		        		 p_dtde.acceptDrop (DnDConstants.ACTION_MOVE);
		        		 ((CMNode) droppedNode).move(index);
		        		 p_dtde.dropComplete (true);
				        	dropTargetNode = null;
		        	 }
				}


		}catch (Exception e) {

  e.printStackTrace();
}
dropTargetNode = null;
p_dtde.rejectDrop();

	}

	/* (non-Javadoc)
	 * @see java.awt.dnd.DragGestureListener#dragGestureRecognized(java.awt.dnd.DragGestureEvent)
	 */
	public void dragGestureRecognized(DragGestureEvent p_dge) {
		Point clickPoint = p_dge.getDragOrigin();
		TreePath path = getPathForLocation (clickPoint.x, clickPoint.y);
		if (path == null) {
			return;
		}
		draggedNode =  (TreeNode) path.getLastPathComponent();
		if (((DefaultMutableTreeNode)draggedNode).getUserObject() instanceof  CMWorkspaceNode)
		{
			Transferable trans = new CMTransferable (draggedNode);
			dragSource.startDrag (p_dge,DragSource.DefaultMoveDrop,trans, this);
		}
		}

	public TreeNode getDropTargetNode() {
		return this.dropTargetNode;
	}

	public TreeNode getDraggedNode() {
		return this.draggedNode;
	}



}
