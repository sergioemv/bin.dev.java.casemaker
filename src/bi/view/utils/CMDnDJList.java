/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.view.utils;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
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
import java.util.HashSet;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.log4j.Logger;

import bi.view.icons.CMIcon;

/**
 * @author smoreno
 *
 */
public class CMDnDJList extends JList implements DragSourceListener,
		DropTargetListener, DragGestureListener, ListSelectionListener {
	private static Image dragImage ;
	private Icon leftIcon;

	/**
	 * @author smoreno
	 *
	 */
	public class CMCellAnimator extends Thread {

		Object[] selections;
	     long startTime;
	     long stopTime;
	     public CMCellAnimator (Object[] s) {
	         selections = s;
	     }
	     public void run() {
	         startTime = System.currentTimeMillis();
	         stopTime = startTime + ANIMATION_DURATION;
	         while (System.currentTimeMillis() < stopTime) {
	              colorizeSelections();
	              repaint();
	              try { Thread.sleep (ANIMATION_REFRESH); }
	              catch (InterruptedException ie) {}
	         }
	         // one more, at 100% selected color
	         colorizeSelections();
	         repaint();
	     }
	     public void colorizeSelections() {
	    	    // calculate % completion relative to start/stop times
	    	    float elapsed = (float) (System.currentTimeMillis() - startTime);
	    	    float completeness = Math.min ((elapsed/ANIMATION_DURATION), 1.0f);
	    	    // calculate scaled color
	    	    float colorizedForeComps[] = new float[3];
	    	    float colorizedBackComps[] = new float[3];
	    	    for (int i=0; i<3; i++) {
	    	        colorizedForeComps[i] =
	    	           foregroundComps[i] +
	    	           (completeness *
	    	            (foregroundSelectionComps[i] - foregroundComps[i]));
	    	        colorizedBackComps[i] =
	    	           backgroundComps[i] +
	    	           (completeness *
	    	            (backgroundSelectionComps[i] - backgroundComps[i]));
	    	    }
	    	    colorizedSelectionForeground =
	    	       new Color (colorizedForeComps[0],
	    	                  colorizedForeComps[1],
	    	                  colorizedForeComps[2]);

	    	    colorizedSelectionBackground =
	    	       new Color (colorizedBackComps[0],
	    	       colorizedBackComps[1],
	    	       colorizedBackComps[2]);
	    	}


	}

	/**
	 * @author smoreno
	 *
	 */
	public   class CMDnDJListCellRenderer extends DefaultListCellRenderer 
			 {

			boolean isTargetCell;
		   boolean isLastItem;
		   public CMDnDJListCellRenderer() {
		       super();
		       dragImage =CMIcon.UP.getImageIcon().getImage();
		   }

            
            public Component getListCellRendererComponent (JList list,Object value, int index, boolean isSelected, boolean hasFocus) {
			 isTargetCell = (value == dropTargetCell);
			 isLastItem = (index == list.getModel().getSize()-1);
			 boolean showSelected = isSelected & (dropTargetCell == null);
			 Component returnMe =
		           getListCellRendererComponentWithIcon (list, value, index,
		        		   showSelected, hasFocus);
			 if (showSelected) {
		           returnMe.setForeground (colorizedSelectionForeground);
		           if (returnMe instanceof JComponent)
		               ((JComponent) returnMe).setOpaque(true);
		           }
			 return returnMe;
		 }
		
		private Component getListCellRendererComponentWithIcon(JList list, Object value, int index, boolean showSelected, boolean hasFocus) {

			setComponentOrientation(list.getComponentOrientation());
			if (showSelected) {
			    setBackground(list.getSelectionBackground());
			    setForeground(list.getSelectionForeground());
			}
			else {
			    setBackground(list.getBackground());
			    setForeground(list.getForeground());
			}

			if (((CMDnDJList)list).getLeftIcon() !=null){
			    setIcon(((CMDnDJList)list).getLeftIcon());

			}
			 setText((value == null) ? "" : value.toString());
			setEnabled(list.isEnabled());
			setFont(list.getFont());

		        Border border = null;
		        if (hasFocus) {
		            if (showSelected) {
		                border = UIManager.getBorder("List.focusSelectedCellHighlightBorder");
		            }
		            if (border == null) {
		                border = UIManager.getBorder("List.focusCellHighlightBorder");
		            }
		        } else {
		            border = noFocusBorder;
		        }
			setBorder(border);

			return this;
		    }


		public void paintComponent (Graphics g) {
			 //superpaintComponent(g);
			 if (isTargetCell) {
				 g.setColor(Color.green);
				 g.drawImage(dragImage,getSize().width-20,0,16,16,null);
				 g.drawLine (0, 0, getSize().width, 0);
			 }
}

	}

	/**
	 * @author smoreno
	 *
	 */
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

	private static java.util.Random rand = new java.util.Random();
	static Color listForeground, listBackground,
    listSelectionForeground, listSelectionBackground;
	static float[] foregroundComps, backgroundComps,
    	foregroundSelectionComps, backgroundSelectionComps;
static {        UIDefaults uid = UIManager.getLookAndFeel().getDefaults();
    	listForeground = uid.getColor ("List.foreground");
    	listBackground = uid.getColor ("List.background");
    	listSelectionForeground = uid.getColor ("List.selectionForeground");
    	listSelectionBackground = uid.getColor ("List.selectionBackground");
    	foregroundComps =listForeground.getRGBColorComponents(null);
    	foregroundSelectionComps =listSelectionForeground.getRGBColorComponents(null);
    	backgroundComps =listBackground.getRGBColorComponents(null);
    	backgroundSelectionComps =listSelectionBackground.getRGBColorComponents(null);
}
public Color colorizedSelectionForeground,
colorizedSelectionBackground;

public static final int ANIMATION_DURATION = 250;
public static final int ANIMATION_REFRESH = 50;

	private static DataFlavor localObjectFlavor;
	static{
		try {
			localObjectFlavor = new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	private static DataFlavor[] supportedFlavors = {localObjectFlavor};
	private DragSource dragSource;
	private DropTarget dropTarget;
	private Object dropTargetCell;
	private int draggedIndex = -1;
	private boolean reorderEnabled;
	/**
	 * default constructor
	 */
	public CMDnDJList() {
		super();
		setCellRenderer(new CMDnDJListCellRenderer());
		//setModel(new DefaultListModel());
		dragSource = new DragSource();
		DragGestureRecognizer dgr =
            dragSource.createDefaultDragGestureRecognizer (this,DnDConstants.ACTION_MOVE,this);
		dropTarget = new DropTarget (this, this);
		addListSelectionListener (this);

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
	       draggedIndex = -1;
	       repaint();
	}

	/* (non-Javadoc)
	 * @see java.awt.dnd.DropTargetListener#dragEnter(java.awt.dnd.DropTargetDragEvent)
	 */
	public void dragEnter(DropTargetDragEvent p_dtde) {
		if (p_dtde.getSource() != dropTarget)
            p_dtde.rejectDrag();
        else {
            p_dtde.acceptDrag(DnDConstants.ACTION_COPY_OR_MOVE);
        }


	}

	/* (non-Javadoc)
	 * @see java.awt.dnd.DropTargetListener#dragOver(java.awt.dnd.DropTargetDragEvent)
	 */
	public void dragOver(DropTargetDragEvent p_dtde) {
		 // figure out which cell it's over, no drag to self
	    if (p_dtde.getSource() != dropTarget){
	    	p_dtde.rejectDrag();
	    	Logger.getLogger(this.getClass()).error("rejecting for bad source (" +
	        		p_dtde.getSource().getClass().getName() + ")");
	    }
	    Point dragPoint = p_dtde.getLocation();
	    int index = locationToIndex (dragPoint);
	    if (index == -1)
	        dropTargetCell = null;
	    else
	        dropTargetCell = getModel().getElementAt(index);
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

		if (p_dtde.getSource() != dropTarget) {
			Logger.getLogger(this.getClass()).error("rejecting for bad source (" +
	        		p_dtde.getSource().getClass().getName() + ")");
	        p_dtde.rejectDrop();
	        return;
	    }
	    Point dropPoint = p_dtde.getLocation();
	    int index = locationToIndex (dropPoint);
	    boolean dropped = false;
	    try {
	        if ((index == -1) || (index == draggedIndex)) {
	            p_dtde.rejectDrop();
	            return;
	        }
	        p_dtde.acceptDrop (DnDConstants.ACTION_MOVE);
	        Object dragged =
	        	p_dtde.getTransferable().getTransferData(localObjectFlavor);
	        boolean sourceBeforeTarget = (draggedIndex < index);
	         DefaultListModel mod = (DefaultListModel) getModel();
	        mod.remove (draggedIndex);
	        mod.add ((sourceBeforeTarget ? index-1 : index), dragged);
	        dropped = true;
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    p_dtde.dropComplete (dropped);


	}

	/* (non-Javadoc)
	 * @see java.awt.dnd.DragGestureListener#dragGestureRecognized(java.awt.dnd.DragGestureEvent)
	 */
	public void dragGestureRecognized(DragGestureEvent p_dge) {
		if (isReorderEnabled()) return;
		Point clickPoint = p_dge.getDragOrigin();
        int index = locationToIndex(clickPoint);
        if (index == -1)
            return;
        Object target = getModel().getElementAt(index);
        Transferable trans = new CMTransferable (target);
        draggedIndex = index;
        dragSource.startDrag (p_dge,DragSource.DefaultMoveDrop,
                              trans, this);
	}
	private boolean isReorderEnabled() {
		
		return reorderEnabled;
	}
	/* (non-Javadoc)
	 * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
	 */
	public void valueChanged(ListSelectionEvent p_e) {
		if (! p_e.getValueIsAdjusting()) {
            HashSet selections = new HashSet();
            for (int i=0; i < getModel().getSize(); i++) {
                if (getSelectionModel().isSelectedIndex(i))
                    selections.add (new Integer(i));
            }
            CMCellAnimator animator = new CMCellAnimator (selections.toArray());
            animator.start();
        }


	}
	public Icon getLeftIcon() {
		return this.leftIcon;
	}
	public void setLeftIcon(Icon p_leftIcon) {
		this.leftIcon = p_leftIcon;
	}
	protected void setReorderEnabled(boolean reorderEnabled) {
		this.reorderEnabled = reorderEnabled;
	}

}
