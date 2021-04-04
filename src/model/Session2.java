/*******************************************************************************
Developed by BUSINESS SOFTWARE INNOVATIONS. .
Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
*******************************************************************************/

package model;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import model.util.CMModelEventHandler;
import model.util.CMModelListener;
import model.util.CMModelSource;


/**
 * @stereotype file
 */
public class Session2 implements Cloneable, CMXMLFile,CMModelSource {
	private Date m_TimeStamp = new Date();

    /**
     * @link aggregationByValue
     * @associates <{model.Workspace2}>
     * @clientCardinality 1
     * @supplierCardinality 0..*
     */
    private Vector<Workspace2> m_Workspaces = new Vector<Workspace2>(0);
    private String m_Version = BusinessRules.SESSION_FILE_VERSION;
    private String m_User = BusinessRules.ACCESS_USER_GUEST;

    /**
     * @link aggregationByValue
     * @clientCardinality 1
     * @supplierCardinality 1
     */
    private ApplicationSetting m_ApplicationSetting = new ApplicationSetting();
    private String windowsDirectory=null;
    private transient CMModelEventHandler handler = new CMModelEventHandler();
    public Session2() {
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

    public String getVersion(){ return m_Version; }

    public void setM_Version(String m_Version){ this.m_Version = m_Version; }
    //modified to return a read only vector to gain control over the modifications on the real one
	// to add or delete use the corresponding methods
    public List<Workspace2> getM_Workspaces(){
    		//Collections.sort(m_Workspaces);
            return Collections.unmodifiableList(m_Workspaces);
        }

    public void setM_User(String p_User) {
      m_User = p_User;
    }

    public String getM_User() {
	  return m_User;
    }

    public ApplicationSetting getM_ApplicationSetting(){
            return m_ApplicationSetting;
        }

    public void setM_ApplicationSetting(ApplicationSetting m_ApplicationSetting){
            this.m_ApplicationSetting = m_ApplicationSetting;
        }

    public String getWindowsDirectory(){ return windowsDirectory; }

    public void setWindowsDirectory(String windowsDirectory){ this.windowsDirectory = windowsDirectory; }


//	//Ccastedo begins 08-02-06
	public String getCurrentVersion(){
		return BusinessRules.SESSION_FILE_VERSION;
	}
//	//	Ccastedo ends 08-02-06

	public void addWorkspace(Workspace2 p_o) {
		 this.m_Workspaces.addElement(p_o);
		 getHandler().fireModelEventHappen(this,CMField.WORKSPACES);
	}

	public void removeWorkspace(Workspace2 p_o) {
		this.m_Workspaces.removeElement(p_o);
		getHandler().fireModelEventHappen(this,CMField.WORKSPACES);
	}

	/* (non-Javadoc)
	 * @see model.CMModelSource#addModelListener(model.CMModelListener)
	 */
	public void addModelListener(CMModelListener p_listener) {
		getHandler().addModelListener(p_listener);
	}

	/* (non-Javadoc)
	 * @see model.CMModelSource#removeModelListener(model.CMModelListener)
	 */
	public void removeModelListener(CMModelListener p_listener) {
		getHandler().removeModelListener(p_listener);
	}

	public CMModelEventHandler getHandler() {
		if (this.handler==null)
			handler = new CMModelEventHandler();
		return this.handler;
	}

	/**
	*@autor smoreno
	 * @param p_workspace2
	 * @param p_index
	 */
	public void addWorkspace(Workspace2 p_workspace2, int p_index) {
		this.m_Workspaces.insertElementAt(p_workspace2,p_index);
		getHandler().fireModelEventHappen(this,CMField.WORKSPACES);
	}
	/**
	 *  moves the workspace to another position on the vector
	*@autor smoreno
	 * @param p_workspace2
	 * @param p_index
	 */
	public void moveWorkspace(Workspace2 p_workspace2, int p_index) {
		if (!this.getM_Workspaces().contains(p_workspace2))
			throw new UnsupportedOperationException("Cannot move workspace if not in session");
		this.m_Workspaces.remove(p_workspace2);
		this.m_Workspaces.insertElementAt(p_workspace2,p_index);
		getHandler().fireModelEventHappen(this,CMField.WORKSPACES);
	}

	public void fireModelEventHappen(CMField field) {
		getHandler().fireModelEventHappen(this, field);

	}
}
