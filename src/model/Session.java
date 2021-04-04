/*******************************************************************************
Developed by BUSINESS SOFTWARE INNOVATIONS. .
Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
*******************************************************************************/ 

package model;
import java.util.Date;
import java.util.Vector;
@Deprecated //use session2 instead
public class Session implements Cloneable {
    public Session() {
    }

    public Object clone() {
      Object b = null;
      try {
       b = super.clone();
      } catch(CloneNotSupportedException e) {
        e.printStackTrace();
      }
      return b;
    }

    public Date getM_TimeStamp(){ return m_TimeStamp; }

    public void setM_TimeStamp(Date m_TimeStamp){ this.m_TimeStamp = m_TimeStamp; }

    public Vector getM_Workspaces(){
            return m_Workspaces;
        }

    public void setM_Workspace(Vector m_Workspaces){
            this.m_Workspaces = m_Workspaces;
        }

    /**
     * @link aggregationByValue 
     * @directed
     * @clientCardinality 1
     * @supplierCardinality 0..*
     * @associates <{Workspace}>
     */
    private Vector m_Workspaces = new Vector(0);
    private Date m_TimeStamp = new Date();
}
