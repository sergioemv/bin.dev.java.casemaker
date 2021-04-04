/* Developed by BUSINESS SOFTWARE INNOVATIONS.  .. Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved..*/

package  bi.view.cells;
import bi.view.cells.renderers.CMEquivalenceClassCellRenderer;

import com.eliad.model.defaults.DefaultGridCellEditor;
import com.eliad.swing.JSmartGrid;

public class CMCellEquivalenceClassName extends CMCellEquivalenceClass {
	
   
    public CMCellEquivalenceClassName(JSmartGrid p_grid, Object p_model) {
		super(p_grid, p_model);
		setEditable(false);
		setRenderer(defaultRenderer);
	}
	public static final DefaultGridCellEditor defaultEditor = null; 
    public static final CMEquivalenceClassCellRenderer defaultRenderer = new CMEquivalenceClassCellRenderer();
    public String toString() {
    	return getEquivalenceClass().getName();
    }

}
