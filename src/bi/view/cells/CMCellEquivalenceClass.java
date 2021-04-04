/* Developed by BUSINESS SOFTWARE INNOVATIONS.  .. Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved..*/

package  bi.view.cells;

import model.EquivalenceClass;
import bi.view.cells.renderers.CMBaseGridCellRenderer;
import bi.view.cells.renderers.CMCellHeaderEquivalenceClassRenderer;

import com.eliad.model.defaults.DefaultGridCellRenderer;
import com.eliad.swing.JSmartGrid;
public class CMCellEquivalenceClass extends CMBaseCell {
	
	public CMCellEquivalenceClass(JSmartGrid p_grid, Object p_model) {
		super(p_grid, p_model);

	}

	private boolean editable = true;
    

    //public static final DefaultGridCellEditor defaultEditor = new DefaultGridCellEditor(new JTextField());    
    public static final DefaultGridCellRenderer defaultRenderer = new CMBaseGridCellRenderer(); 
   // public static final DefaultGridCellRenderer defaultSpecialGridRenderer = new CMCellSpecialEquivalenceClassRenderer();
    public static final DefaultGridCellRenderer defaultGridHeaderRenderer = new CMCellHeaderEquivalenceClassRenderer();
    
    public EquivalenceClass getEquivalenceClass(){ return (EquivalenceClass)this.getModel(); }

    public void setEquivalenceClass(EquivalenceClass equivalenceClass){ this.setModel(equivalenceClass); }

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

    
}
