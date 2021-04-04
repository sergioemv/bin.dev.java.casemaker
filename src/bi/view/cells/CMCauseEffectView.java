/* Developed by BUSINESS SOFTWARE INNOVATIONS.  .. Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved..*/

package  bi.view.cells;

import model.Effect;
import java.util.Vector;

public class CMCauseEffectView extends Vector {
    public CMCauseEffectView(Effect p_Effect) {
      super(0);
      this.m_Effect = p_Effect;
    }

    public Effect getM_Effect(){
            return m_Effect;
        }

    public void setM_Effect(Effect m_Effect){
            this.m_Effect = m_Effect;
        }

    public CMCauseEffectNameView getM_CMCauseEffectNameView(){
            return m_CMCauseEffectNameView;
        }

    public void setM_CMCauseEffectNameView(CMCauseEffectNameView m_CMCauseEffectNameView){
            this.m_CMCauseEffectNameView = m_CMCauseEffectNameView;
        }

    public CMCauseEffectDescriptionView getM_CMCauseEffectDescriptionView(){
            return m_CMCauseEffectDescriptionView;
        }

    public void setM_CMCauseEffectDescriptionView(CMCauseEffectDescriptionView m_CMCauseEffectDescriptionView){
            this.m_CMCauseEffectDescriptionView = m_CMCauseEffectDescriptionView;
        }
    //fcastro_19102004_begin
    public void setM_CMCauseEffectUsedView(CMCauseEffectUsedView m_CMCauseEffectUsedView){
        this.m_CMCauseEffectUsedView = m_CMCauseEffectUsedView;
    }
    public CMCauseEffectUsedView getM_CMCauseEffectUsedView(){
        return m_CMCauseEffectUsedView;
    }
    //fcastro_10102004_end

    /**
     * @clientCardinality 1
     * @supplierCardinality 1
     * @link aggregationByValue
     * @directed 
     */
    private CMCauseEffectNameView m_CMCauseEffectNameView;

    /**
     * @clientCardinality 1
     * @supplierCardinality 1
     * @directed
     * @link aggregationByValue 
     */
    private CMCauseEffectDescriptionView m_CMCauseEffectDescriptionView;
//fcastro_19102004_begin
	private CMCauseEffectUsedView m_CMCauseEffectUsedView;
//fcastro_19102004_end
    /**
     * @clientCardinality 1
     * @supplierCardinality 1
     * @directed 
     */
    private Effect m_Effect;
//HCanedo_14032006_begin    
    private CMCauseEffectRiskLevelView m_CMCauseEffectRiskLevelView;
    private CMCauseEffectStateView m_CMCauseEffectStateView;
	/**
	 * @return Returns the m_CMCauseEffectRiskLevelView.
	 */
	public CMCauseEffectRiskLevelView getM_CMCauseEffectRiskLevelView() {
		return m_CMCauseEffectRiskLevelView;
	}

	/**
	 * @param causeEffectRiskLevelView The m_CMCauseEffectRiskLevelView to set.
	 */
	public void setM_CMCauseEffectRiskLevelView(
			CMCauseEffectRiskLevelView causeEffectRiskLevelView) {
		m_CMCauseEffectRiskLevelView = causeEffectRiskLevelView;
	}

	/**
	 * @return Returns the m_CMCauseEffectStateView.
	 */
	public CMCauseEffectStateView getM_CMCauseEffectStateView() {
		return m_CMCauseEffectStateView;
	}

	/**
	 * @param causeEffectStateView The m_CMCauseEffectStateView to set.
	 */
	public void setM_CMCauseEffectStateView(
			CMCauseEffectStateView causeEffectStateView) {
		m_CMCauseEffectStateView = causeEffectStateView;
	}
//HCanedo_14032006_end	
}
