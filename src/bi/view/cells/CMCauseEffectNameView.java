/* Developed by BUSINESS SOFTWARE INNOVATIONS.  .. Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved..*/

package  bi.view.cells;

import com.eliad.swing.JSmartGrid;

public class CMCauseEffectNameView extends CMCellCauseEffect {


    public CMCauseEffectNameView(JSmartGrid p_grid, Object p_model) {
		super(p_grid, p_model);
	}

	public CMCauseEffectView getM_CMCauseEffectView(){
            return m_CMCauseEffectView;
        }

    public void setM_CMCauseEffectView(CMCauseEffectView m_CMCauseEffectView){
            this.m_CMCauseEffectView = m_CMCauseEffectView;
        }

    /**
     * @directed
     */
    private CMCauseEffectView m_CMCauseEffectView;
}
