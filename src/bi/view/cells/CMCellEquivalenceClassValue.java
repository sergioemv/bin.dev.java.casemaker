/* Developed by BUSINESS SOFTWARE INNOVATIONS.  .. Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved..*/

package  bi.view.cells;
import com.eliad.swing.JSmartGrid;

public class CMCellEquivalenceClassValue extends CMCellEquivalenceClass {
	 public CMCellEquivalenceClassValue(JSmartGrid p_grid, Object p_model) {
		super(p_grid, p_model);
		
	}

	//public static final CMEquivalenceClassCellRenderer defaultRenderer = new CMEquivalenceClassCellRenderer();
    
    public String toString() {
    	return getEquivalenceClass().getValue();
    }

}
