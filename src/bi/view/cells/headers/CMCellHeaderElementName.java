/* Developed by BUSINESS SOFTWARE INNOVATIONS.  .. Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved..*/

package  bi.view.cells.headers;

import bi.view.cells.CMCellElement;
import bi.view.lang.CMMessages;

import com.eliad.swing.JSmartGrid;
public class CMCellHeaderElementName extends CMCellElement {
 

    public CMCellHeaderElementName(JSmartGrid p_grid, Object p_model) {
		super(p_grid, p_model);
		this.setRenderer(defaultGridHeaderRenderer);
	}

	public String getName(){ return name; }
    
    public String toString() {
    	return name;
    }

    public void setName(String name){ this.name = name; }

    private String name = CMMessages.getString("LABEL_ELEMENT"); //$NON-NLS-1$
}
