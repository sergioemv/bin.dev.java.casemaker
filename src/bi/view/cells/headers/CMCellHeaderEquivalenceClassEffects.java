/* Developed by BUSINESS SOFTWARE INNOVATIONS.  .. Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved..*/

package  bi.view.cells.headers;
import bi.view.cells.CMCellEquivalenceClass;
import bi.view.lang.CMMessages;

import com.eliad.swing.JSmartGrid;

public class CMCellHeaderEquivalenceClassEffects extends CMCellEquivalenceClass {
  

    public CMCellHeaderEquivalenceClassEffects(JSmartGrid p_grid, Object p_model) {
		super(p_grid, p_model);
		setRenderer(defaultGridHeaderRenderer);
	}

	public String getName(){ return name; }

    public void setName(String name){ this.name = name; }

    private String name = CMMessages.getString("LABEL_CAUSE_EFFECTS"); //$NON-NLS-1$
    
    public String toString() {
    
    	return name;
    }
}
