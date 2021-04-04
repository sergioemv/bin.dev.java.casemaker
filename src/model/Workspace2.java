/*******************************************************************************
Developed by BUSINESS SOFTWARE INNOVATIONS. .
Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
*******************************************************************************/


package model;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import model.util.CMDescriptionBean;
import model.util.CMModelEventHandler;
import model.util.CMModelListener;
import model.util.CMModelSource;
import model.util.CMNameBean;
import bi.view.lang.CMMessages;





public class Workspace2 implements Cloneable, CMNameBean, CMModelSource, CMDescriptionBean {
    public Workspace2() {
    }
    private Date m_TimeStamp = new Date();
    private String m_Description = "";

    /**
     * @directed
     */
    private Session2 m_Session = null;

    /**
     * @link aggregationByValue
     * @associates <{model.ProjectReference}>
     * @clientCardinality 1
     * @supplierCardinality 0..*
     */
    private Vector<ProjectReference> m_ProjectReferences = new Vector<ProjectReference>(0);
    private String m_Name = CMMessages.getString("LABEL_WORKSPACE"); //$NON-NLS-1$
	private transient CMModelEventHandler modelHandler;
    private transient HashMap<ProjectReference,Project2> projectMap = new HashMap<ProjectReference,Project2>();


    public Object clone() {
      Object b = null;
      try {
       b = super.clone();
      } catch(CloneNotSupportedException e) {
        e.printStackTrace();
      }
      return b;
    }

    public String toString() {  return getName();  }

    public Date getM_TimeStamp(){ return m_TimeStamp; }

    public void setM_TimeStamp(Date m_TimeStamp){ this.m_TimeStamp = m_TimeStamp; }

    public String getDescription(){
    	if (m_Description == null)
    		m_Description = new String();
    	return m_Description; }

    public void setDescription(String m_Description){
    	this.m_Description = m_Description;
    	this.getModelHandler().fireModelEventHappen(this, CMField.DESCRIPTION);
    	}

    public Session2 getM_Session(){
            return m_Session;
        }

    public void setM_Session(Session2 m_Session){
            this.m_Session = m_Session;
        }

    //smoreno changed to read only to avoid unnotified changes on the vector
    public List<ProjectReference> getM_ProjectReferences(){
            return Collections.unmodifiableList(m_ProjectReferences);
        }


    public String getName(){ return m_Name; }

    public void setName(String m_Name){
    	this.m_Name = m_Name;
    	this.getModelHandler().fireModelEventHappen(this,CMField.NAME);
    	}



	/* (non-Javadoc)
	 * @see model.util.CMModelSource#addModelListener(model.util.CMModelListener)
	 */
	public void addModelListener(CMModelListener p_listener) {
		getModelHandler().addModelListener(p_listener);
	}

	/* (non-Javadoc)
	 * @see model.util.CMModelSource#removeModelListener(model.util.CMModelListener)
	 */
	public void removeModelListener(CMModelListener p_listener) {
		getModelHandler().removeModelListener(p_listener);

	}

	public CMModelEventHandler getModelHandler() {
		if (this.modelHandler==null)
			this.modelHandler = new CMModelEventHandler();
		return this.modelHandler;
	}

	/**
	*@autor smoreno
	 * @return
	 */
	public void removeProjectReference(ProjectReference ref) {
		this.m_ProjectReferences.remove(ref);
		this.getModelHandler().fireModelEventHappen(this,CMField.PROJECT_REFERENCES);
	}

	/**
	*@autor smoreno
	 * @param p_projectReference
	 */
	public void addProjectReference(ProjectReference p_projectReference) {
		this.m_ProjectReferences.add(p_projectReference);
		this.getModelHandler().fireModelEventHappen(this,CMField.PROJECT_REFERENCES);

	}

	/**
	*@autor smoreno
	 * @param p_projectRefence
	 * @param p_index
	 */
	public void addProjectReference(ProjectReference p_projectRefence, int p_index) {
		this.m_ProjectReferences.insertElementAt(p_projectRefence, p_index);
		this.getModelHandler().fireModelEventHappen(this,CMField.PROJECT_REFERENCES);

	}

	public void fireModelEventHappen(CMField field) {
		getModelHandler().fireModelEventHappen(this, field);

	}
}
