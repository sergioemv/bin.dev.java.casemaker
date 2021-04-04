/* Developed by BUSINESS SOFTWARE INNOVATIONS.  .. Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved..*/

package  bi.view.cells;

import com.eliad.swing.JSmartGrid;
public class CMCellElementName extends CMCellElement {
    
    public CMCellElementName(JSmartGrid p_grid, Object p_model) {
		super(p_grid, p_model);
		
	}

	public String toString() {
    	return this.getElement().getName();
    }
}
