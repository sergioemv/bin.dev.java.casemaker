/*******************************************************************************
Developed by BUSINESS SOFTWARE INNOVATIONS. .
Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
*******************************************************************************/
 

package model;
import java.util.Vector;
import java.util.Date;

import bi.view.lang.CMMessages;




@Deprecated //use Workspace2 instead
public class Workspace implements Cloneable {
    public Workspace() {
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

    public Vector getM_Projects(){ return m_Projects; }

    public void setM_Projects(Vector m_Projects){ this.m_Projects = m_Projects; }

    public String getM_Path(){ return m_Path; }

    public void setM_Path(String m_Path){ this.m_Path = m_Path; }

    public String getM_Filename(){ return m_Filename; }

    public void setM_Filename(String m_Filename){ this.m_Filename = m_Filename; }

    public Date getM_TimeStamp(){ return m_TimeStamp; }

    public void setM_TimeStamp(Date m_TimeStamp){ this.m_TimeStamp = m_TimeStamp; }

    public String getM_Description(){ return m_Description; }

    public void setM_Description(String m_Description){ this.m_Description = m_Description; }

    public String getM_Name(){ return m_Name; }

    public void setM_Name(String m_Name){ this.m_Name = m_Name; }

    public String toString() {  return m_Name;   }

    public Session getM_Session(){
            return m_Session;
        }

    public void setM_Session(Session m_Session){
            this.m_Session = m_Session;
        }

    /**
     * @link aggregationByValue
     * @directed
     * @clientCardinality 1
     * @supplierCardinality 0..*
     * @associates <{Project}> 
     */
    private Vector m_Projects = new Vector(0);
    private String m_Path = CMMessages.getString("LABEL_UNKNOWN_1"); //$NON-NLS-1$
    private String m_Filename = ""; //$NON-NLS-1$
    private Date m_TimeStamp = new Date();
    private String m_Description = ""; //$NON-NLS-1$
    private String m_Name = CMMessages.getString("LABEL_WORKSPACE"); //$NON-NLS-1$

    /**
     * @directed 
     */
    private Session m_Session;
}
