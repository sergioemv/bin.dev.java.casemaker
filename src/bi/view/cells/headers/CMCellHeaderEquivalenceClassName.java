/* Developed by BUSINESS SOFTWARE INNOVATIONS.  .. Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved..*/

package  bi.view.cells.headers;
import bi.view.cells.CMCellEquivalenceClass;
import bi.view.lang.CMMessages;

import com.eliad.swing.JSmartGrid;

public class CMCellHeaderEquivalenceClassName extends CMCellEquivalenceClass {
    
    
    public CMCellHeaderEquivalenceClassName(JSmartGrid p_grid, Object p_model) {
		super(p_grid, p_model);
		setRenderer(defaultGridHeaderRenderer);
	}

	public String getName(){ return name; }

    public void setName(String name){ this.name = name; }
    
    public String toString() {
    	return this.getName();
    }

    private String name = CMMessages.getString("LABEL_EQUIVALENCE_CLASS"); //$NON-NLS-1$

	
}
