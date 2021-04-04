package model;

/**
 ****************************************************************************** Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
public class EffectsForUndoRedo {
    /**
     * @deprecated
     * @param p_Index
     * @param p_Effect
     */
    public EffectsForUndoRedo(int p_Index, Effect p_Effect) {
        this.indexEffect=p_Index;
        this.m_Effect=p_Effect;
    }

    public int getIndexEffect(){
            return indexEffect;
        }

    public void setIndexEffect(int indexEffect){
            this.indexEffect = indexEffect;
        }

    public Effect getM_Effect(){ return m_Effect; }

    public void setM_Effect(Effect m_Effect){ this.m_Effect = m_Effect; }

    private int indexEffect;
    private Effect m_Effect;
}
