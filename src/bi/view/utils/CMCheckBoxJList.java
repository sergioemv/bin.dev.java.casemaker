/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.view.utils;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Iterator;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * @author smoreno
 *
 */
public class CMCheckBoxJList extends JList implements ListSelectionListener {

	public class CMCheckBoxListCellRenderer extends JComponent implements
	ListCellRenderer {
DefaultListCellRenderer defaultComp;
JCheckBox checkbox;

public CMCheckBoxListCellRenderer() {
    setLayout (new BorderLayout());
    setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    defaultComp = new DefaultListCellRenderer();
    checkbox = new JCheckBox();
    add (checkbox, BorderLayout.WEST);
    add (defaultComp, BorderLayout.CENTER);

}


public Component getListCellRendererComponent(JList list,
                                              Object value,
                                              int index,
                                              boolean isSelected,
                                              boolean cellHasFocus){
defaultComp.getListCellRendererComponent (list, value, index,
                                          isSelected, cellHasFocus);
checkbox.setSelected (isSelected);
Component[] comps = getComponents();
for (int i=0; i<comps.length; i++) {
     comps[i].setForeground (listForeground);
     comps[i].setBackground (listBackground);
     if (cellHasFocus)

    	 defaultComp.setBackground(list.getBackground());
}
return this;
}
}



    static Color listForeground, listBackground;
    static {
       UIDefaults uid = UIManager.getLookAndFeel().getDefaults();
       listForeground =uid.getColor ("List.foreground"); //$NON-NLS-1$
       listBackground =uid.getColor ("List.background"); //$NON-NLS-1$
    }

    HashSet selectionCache = new HashSet();
    int toggleIndex = -1;
    boolean toggleWasSelected;
	protected boolean secondClick = false;

    public CMCheckBoxJList() {
       super();
       setCellRenderer (new CMCheckBoxListCellRenderer());
       addListSelectionListener (this);
       addMouseListener(new MouseAdapter(){
    	   /* (non-Javadoc)
    	 * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
    	 */
    	@Override
    	public void mouseClicked(MouseEvent p_e) {
    		super.mouseClicked(p_e);
    		 int index = locationToIndex(p_e.getPoint());

             if (1 == getSelectionCache().size() && getSelectionModel().isSelectedIndex(index)
            		 && secondClick) {
            		 valueChanged(new ListSelectionEvent(p_e.getSource(),index,index,false));            		 
             }
             secondClick = true;
    	}
       });
       addKeyListener(new KeyAdapter(){
    	   /* (non-Javadoc)
    	 * @see java.awt.event.KeyAdapter#keyPressed(java.awt.event.KeyEvent)
    	 */
    	@Override
    	public void keyPressed(KeyEvent p_e) {
    		// TODO Auto-generated method stub
    		super.keyPressed(p_e);
    		if (p_e.getKeyCode() == KeyEvent.VK_SPACE)
    		{
    			int index = getSelectedIndex();
				valueChanged(new ListSelectionEvent(p_e.getSource(),index ,index,false));
				secondClick = true;
				 if (1 == getSelectionCache().size() && getSelectionModel().isSelectedIndex(index)
	            		 && secondClick) {
	            		 valueChanged(new ListSelectionEvent(p_e.getSource(),index,index,false));            		 
	             }
				 
    		}
    	}
       });

    }



	/* (non-Javadoc)
	 * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
	 */
	public void valueChanged(ListSelectionEvent lse) {
		// TODO Auto-generated method stub
	    if (! lse.getValueIsAdjusting()) {
	        removeListSelectionListener (this);

	        // determine if this selection has added or removed items
	        HashSet newSelections = new HashSet();
	        int size = getModel().getSize();
	        for (int i=0; i<size; i++) {
	             if (getSelectionModel().isSelectedIndex(i)) {
	                 newSelections.add (new Integer(i));

	             }
	        }

	        // turn on everything that was selected previously
	        Iterator it = selectionCache.iterator();
	        while (it.hasNext()) {
	            int index = ((Integer) it.next()).intValue();
	            	getSelectionModel().addSelectionInterval(index, index);
	        }

	        // add or remove the delta
	        it = newSelections.iterator();
	        while (it.hasNext()) {
	            Integer nextInt = (Integer) it.next();
	            int index = nextInt.intValue();
	            if (selectionCache.contains (nextInt))
	                getSelectionModel().removeSelectionInterval (index, index);
	   else
	                getSelectionModel().addSelectionInterval (index, index);
	        }
	        getSelectionModel();
	        // save selections for next time
	        selectionCache.clear();
	        for (int i=0; i<size; i++) {
	             if (getSelectionModel().isSelectedIndex(i)) {
	                 selectionCache.add (new Integer(i));
	             }
	        }
	        
	        addListSelectionListener (this);
	        secondClick = false;
	    }
	}

 

	public void setSelectionCache(HashSet p_selectionCache) {
		this.selectionCache = p_selectionCache;
	}



	public HashSet getSelectionCache() {
		return this.selectionCache;
	}

}
