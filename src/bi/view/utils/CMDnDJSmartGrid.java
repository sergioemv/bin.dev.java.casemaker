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

import org.apache.log4j.Logger;

import bi.view.grids.CMBaseJSmartGrid;

/**
 * @author smoreno
 *
 */
public abstract class CMDnDJSmartGrid extends CMBaseJSmartGrid
implements DragSourceListener, DropTargetListener, DragGestureListener {
	public class CMTransferable implements Transferable {
		private Object obj;
		/**
		 * @param p_target
		 */
		public CMTransferable(Object p_target) {
			obj = p_target;
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
			// TODO Auto-generated method stub
			return p_flavor.equals(localObjectFlavor);
		}

		/* (non-Javadoc)
		 * @see java.awt.datatransfer.Transferable#getTransferData(java.awt.datatransfer.DataFlavor)
		 */
		public Object getTransferData(DataFlavor p_flavor)
				throws UnsupportedFlavorException, IOException {
			if (isDataFlavorSupported (p_flavor))
	            return obj;
	        else
	           throw new UnsupportedFlavorException(p_flavor);
		}

	}

	protected static DataFlavor localObjectFlavor;
    static {
        try {
            localObjectFlavor =
                new DataFlavor (DataFlavor.javaJVMLocalObjectMimeType);
        } catch (ClassNotFoundException cnfe) { cnfe.printStackTrace(); }
    }
    static DataFlavor[] supportedFlavors = { localObjectFlavor };
    DragSource dragSource;
    DropTarget dropTarget;
    private Object dropTargetCell;
    protected int draggedCol = -1;
    protected int draggedRow = -1;
/**
 *
 */
public CMDnDJSmartGrid() {
	super();
	dragSource = new DragSource();
    DragGestureRecognizer dgr =
    dragSource.createDefaultDragGestureRecognizer (this,DnDConstants.ACTION_MOVE,this);
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

	       dropTargetCell = null;
	       draggedCol = -1;
	       draggedRow = -1;
	       repaint();
	       Logger.getLogger(this.getClass()).debug ("Drop ended "); //$NON-NLS-1$
	}

	/* (non-Javadoc)
	 * @see java.awt.dnd.DropTargetListener#dragEnter(java.awt.dnd.DropTargetDragEvent)
	 */
	public void dragEnter(DropTargetDragEvent dtde) {

	        if (dtde.getSource() != dropTarget)
	        {
	            dtde.rejectDrag();
	            Logger.getLogger(this.getClass()).debug ("Drag Rejected "); //$NON-NLS-1$
	        }
	        else {
	            dtde.acceptDrag(DnDConstants.ACTION_COPY_OR_MOVE);
	            Logger.getLogger(this.getClass()).debug ("Drag Accepted "); //$NON-NLS-1$

	        }
	}

	/* (non-Javadoc)
	 * @see java.awt.dnd.DropTargetListener#dragOver(java.awt.dnd.DropTargetDragEvent)
	 */
	public void dragOver(DropTargetDragEvent dtde) {
		//no drag on self
		if (dtde.getSource() != dropTarget)
	        dtde.rejectDrag();
		Point dragPoint = dtde.getLocation();
		int column = columnAtPoint(dragPoint);
        int row = rowAtPoint(dragPoint);
        if ((column == -1)||(row==-1))
            dropTargetCell = null;
        else
        	dropTargetCell = getValueAt(row,column);
        //change the selecion so the scroll goes with it
   //     if (row != -1 && column != -1)
   //     	this.changeSelection(row,column,false,false);
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
	public  abstract void drop(DropTargetDropEvent p_dtde);

	/* (non-Javadoc)
	 * @see java.awt.dnd.DragGestureListener#dragGestureRecognized(java.awt.dnd.DragGestureEvent)
	 */
	public void dragGestureRecognized(DragGestureEvent dge) {

	        // find object at this x,y
			if (isResizing())
				return;
	        Point clickPoint = dge.getDragOrigin();
	        int column = columnAtPoint(clickPoint);
	        int row = rowAtPoint(clickPoint);
	        if ((column == -1)||(row==-1))
	            return;
	        Object target = getModel().getValueAt(row,column);
	        Transferable trans = new CMTransferable (target);
	        draggedCol = column;
	        draggedRow = row;
	        dragSource.startDrag (dge,DragSource.DefaultMoveDrop,
	                              trans, this);
	        Logger.getLogger(this.getClass()).debug ("starting drag of "+target+" in row "+row+" and col "+column); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	public Object getDropTargetCell() {
		return this.dropTargetCell;
	}

	public int getDraggedCol() {
		return this.draggedCol;
	}

	public int getDraggedRow() {
		return this.draggedRow;
	}

}
