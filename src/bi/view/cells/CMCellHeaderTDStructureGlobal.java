package bi.view.cells;

import com.eliad.swing.JSmartGrid;


/**
 ****************************************************************************** Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
public class CMCellHeaderTDStructureGlobal extends CMCellTDStructure {
	public CMCellHeaderTDStructureGlobal(JSmartGrid p_grid, Object p_model) {
		super(p_grid, p_model);
		//set the default renderer for this 
		this.setRenderer(defaultGridHeaderRenderer);
	}

	public String getName(){ return name; }
    
    public String toString() {
    	return name;
    }

    public void setName(String name){ this.name = name; }

    private String name = "Global";//CMMessages.getString("LABEL_DESCRIPTION"); //$NON-NLS-1$
   
    /*public CMCellHeaderTDStructureGlobal(TDStructure testData) {
        super(testData);
    }

    public String getName(){ return name; }

    public void setName(String name){ this.name = name; }

    private String name="Global";*/
}
