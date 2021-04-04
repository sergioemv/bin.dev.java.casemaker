package bi.view.utils;

import java.awt.AWTException;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import bi.view.grids.CMBaseJSmartGrid;

public class CMBaseJComboBox extends JComboBox {
	private MouseListener componentMouseListener = new MouseAdapter(){
		public void mousePressed(MouseEvent e) {
			setRetainFocus(false);
			}
		
	};
	
private boolean retainFocus = false;
private AbstractAction nextItemAction = new AbstractAction("Next item in combo"){

	public void actionPerformed(ActionEvent e) {
		requestFocus();
		showPopup();
		selectNext();
		requestFocusInWindow();
		
	}};
private AbstractAction prevItemAction = new AbstractAction("Prev item in Combo")
{
	public void actionPerformed(ActionEvent e) {
		requestFocus();
		showPopup();
		selectPrev();	
		requestFocusInWindow();
	}	
};
private AbstractAction showItemsAction = new AbstractAction("Show items")
{

	public void actionPerformed(ActionEvent e) {
		requestFocusInWindow();
		showPopup();
	}
	
};
private FocusListener focusAdapter = new FocusAdapter()
{
	public void focusLost(FocusEvent e) {
	
		

		if (!e.isTemporary())
		{
			if (parent instanceof CMBaseJSmartGrid)
			{
			
			if (retainFocus)
			{
			requestFocus();
			Robot robot=null;
				try {
					robot = new Robot();
					//TODO disable the repaint of the component
					setIgnoreRepaint(true);
		        	//provisory solution of focusLost in gridTDStructure, it affects only the combos in the grid
		        	//it should be replaced with an editor for each Cell svonborries_15092006 
					/*if(e.getOppositeComponent() instanceof CMGridTDStructure){
						return;
					}*///ccastedo comments this....22.09.06
					robot.keyPress(KeyEvent.VK_SPACE);
					setIgnoreRepaint(false);
		    
					} catch (AWTException e1) {

						e1.printStackTrace();
					}
				}
			}
		}
	}
};
private Component parent;
private KeyListener keyAdapter = new KeyAdapter()
{
	public void keyPressed(KeyEvent e) {
		//if the key is not a mamaged key do not retain the focus
	     if (((e.getKeyCode()!= KeyEvent.VK_DOWN)&&(e.getKeyCode()!= KeyEvent.VK_UP))&&
	    		 (e.getKeyCode()!= KeyEvent.VK_SPACE))
	     	{
	    	 retainFocus = false;
	     	}
	}
};
protected int popupWidth;
private ActionListener actionListener = new ActionListener()
{

	public void actionPerformed(ActionEvent e) {
		//if theres a mouse event the focus is not retained
		if (e.getModifiers() == ActionEvent.MOUSE_EVENT_MASK)
			retainFocus = false;
	}
};


		private void selectPrev() {
			int idx  = this.getSelectedIndex();
			idx--;
			if (idx>=0)
				this.setSelectedIndex(idx);
			
		}
			

   
	public CMBaseJComboBox(Component parent) {
		super();
		if (this.getParent()!=null)
		this.parent = this.getParent();
		else
			this.parent = parent;
		
		initialize();
	
	}

	private void initialize() {
		popupWidth = 0;
		this.getActionMap().put(nextItemAction  .getValue(Action.NAME),nextItemAction);
		this.getInputMap(JComponent.WHEN_FOCUSED).put(
	        KeyStroke.getKeyStroke(KeyEvent.VK_DOWN,0), nextItemAction.getValue(Action.NAME));		
		
		this.getActionMap().put(prevItemAction  .getValue(Action.NAME),prevItemAction);
		this.getInputMap(JComponent.WHEN_FOCUSED).put(
	        KeyStroke.getKeyStroke(KeyEvent.VK_UP,0), prevItemAction.getValue(Action.NAME));
		
		this.getInputMap(JComponent.WHEN_FOCUSED).put(
		        KeyStroke.getKeyStroke(KeyEvent.VK_2,0), nextItemAction.getValue(Action.NAME));		
		
		this.getActionMap().put(prevItemAction  .getValue(Action.NAME),prevItemAction);
		this.getInputMap(JComponent.WHEN_FOCUSED).put(
		        KeyStroke.getKeyStroke(KeyEvent.VK_1,0), prevItemAction.getValue(Action.NAME));
			
		this.getActionMap().put(showItemsAction   .getValue(Action.NAME),showItemsAction);
		this.getInputMap(JComponent.WHEN_FOCUSED).put(
	        KeyStroke.getKeyStroke(KeyEvent.VK_SPACE,0), showItemsAction.getValue(Action.NAME));
		
		
		this.setKeySelectionManager(new CMKeySelectionManager());
		this.setLightWeightPopupEnabled(true);
		this.addFocusListener(focusAdapter );
		this.addKeyListener(keyAdapter);
	     this.addActionListener(actionListener );
		
	}

	public void selectNext() {
		
		int idx  = this.getSelectedIndex();
		idx++;
		if (idx<this.getItemCount())
			this.setSelectedIndex(idx);
		
	}

	public CMBaseJComboBox(Object[] items) {
		super(items);
		initialize();
	}

	public CMBaseJComboBox(Vector items) {
		super(items);
		initialize();
	}

	public CMBaseJComboBox(ComboBoxModel aModel) {
		super(aModel);
		initialize();
	}
	

	class CMKeySelectionManager implements JComboBox.KeySelectionManager {
        long lastKeyTime = 0;
        String pattern = "";
    
        public int selectionForKey(char aKey, ComboBoxModel model) {
            // Find index of selected item
            int selIx = 01;
            Object sel = model.getSelectedItem();
            if (sel != null) {
                for (int i=0; i<model.getSize(); i++) {
                    if (sel.equals(model.getElementAt(i))) {
                        selIx = i;
                        break;
                    }
                }
            }
    
            // Get the current time
            long curTime = System.currentTimeMillis();
    
            // If last key was typed less than 300 ms ago, append to current pattern
            if (curTime - lastKeyTime < 300) {
                pattern += ("" + aKey).toLowerCase();
            } else {
                pattern = ("" + aKey).toLowerCase();
            }
    
            // Save current time
            lastKeyTime = curTime;
    
            // Search forward from current selection
            for (int i=selIx+1; i<model.getSize(); i++) {
                String s = model.getElementAt(i).toString().toLowerCase();
                if (s.startsWith(pattern)) {
                    return i;
                }
            }
    
            // Search from top to current selection
            for (int i=0; i<selIx ; i++) {
                if (model.getElementAt(i) != null) {
                    String s = model.getElementAt(i).toString().toLowerCase();
                    if (s.startsWith(pattern)) {
                        return i;
                    }
                }
            }
            return -1;
        }

}
	public boolean isRetainFocus() {
		return retainFocus;
	}



	public void setRetainFocus(boolean retainFocus) {
		this.retainFocus = retainFocus;
	}



	public void setParent(Component parent) {
		this.parent = parent;
	}



	
	public void setPopupWidth(int width) {
    popupWidth = width;
  }
 
  public Dimension getPopupSize() {
    Dimension size = getSize();
    if (popupWidth < 1) popupWidth = size.width;
    return new Dimension(popupWidth, size.height);
  }
	
	
}



