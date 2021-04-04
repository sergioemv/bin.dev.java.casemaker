/* Developed by BUSINESS SOFTWARE INNOVATIONS.  .. Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved..*/

package  bi.view.cells;

import com.eliad.swing.JSmartGrid;

public class CMCauseEffectDescriptionView extends CMCellCauseEffect {

   

	public CMCauseEffectDescriptionView(JSmartGrid p_grid, Object p_model) {
		super(p_grid, p_model);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getEffect().getDescription();
	}
}
