package bi.view.testdataviews;

/**
 ****************************************************************************** Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
public class CMComplexObjectToDeleted {
    public CMComplexObjectToDeleted(int i, int j, Object obj) {
        posi=i;
        posj=j;
        m_Object=obj;
    }

    public Object getM_Object(){ return m_Object; }

    public void setM_Object(Object m_Object){ this.m_Object = m_Object; }

    public int getPosj(){
            return posj;
        }

    public void setPosj(int posj){
            this.posj = posj;
        }

    public int getPosi(){
            return posi;
        }

    public void setPosi(int posi){
            this.posi = posi;
        }

    private Object m_Object;
    private int posj;
    private int posi;
}
