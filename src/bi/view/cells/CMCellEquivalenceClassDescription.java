/* Developed by BUSINESS SOFTWARE INNOVATIONS.  .. Copyright (c)2003 D�az und Hilterscheid Unternehmensberatung. All rights reserved..*/

package  bi.view.cells;
import com.eliad.swing.JSmartGrid;

public class CMCellEquivalenceClassDescription extends CMCellEquivalenceClass {
	 public CMCellEquivalenceClassDescription(JSmartGrid p_grid, Object p_model) {
		super(p_grid, p_model);
	
		
	}

	  
    public String toString() {
    	return getEquivalenceClass().getDescription();
    }

}
