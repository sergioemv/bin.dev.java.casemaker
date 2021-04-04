package bi.view.cells;
import model.ITypeData;

import com.eliad.swing.JSmartGrid;

/**
 ****************************************************************************** Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
public class CMCellTDStructureGlobal extends CMBaseCell {
	public CMCellTDStructureGlobal(JSmartGrid p_grid, ITypeData typeData) {
		super(p_grid, typeData);		
	}

	public String getName()
    {
        return toString();
    }
    
	public String toString() {
		if (this.getModel()!=null)
		      return ((ITypeData)this.getModel()).getGlobal();
			else
				return "";
    }
	
	
}
