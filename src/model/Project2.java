

package model;
import java.util.Date;
import java.util.Vector;

import model.util.CMAccessStateBean;
import model.util.CMModelEventHandler;
import model.util.CMModelListener;
import model.util.CMModelSource;
import model.util.CMNameBean;
import model.util.CMUserBean;
import bi.controller.SessionManager;
import bi.controller.WorkspaceManager;
import bi.view.lang.CMMessages;

/**
 * @stereotype file
 */
public class Project2 implements Cloneable, CMXMLFile, CMAccessStateBean, CMUserBean, CMNameBean, CMModelSource {
	private transient CMModelEventHandler modelHandler;
	private String m_Description = ""; //$NON-NLS-1$
    private String m_Name = CMMessages.getString("LABEL_PROJECT_UNTITLED"); //$NON-NLS-1$

    /**
     * @link aggregationByValue
     * @associates <{TestObjectReference}>
     * @clientCardinality 1
     * @supplierCardinality 0..*
     */
    private Vector<TestObjectReference> m_TestObjectReferences = new Vector<TestObjectReference>(0);
    private Date m_TimeStamp = new Date();
    private Variables m_Variables;
    private String m_Version = BusinessRules.PROJECT_FILE_VERSION;
    private String m_AccessState = BusinessRules.ACCESS_STATE_CHECKED_OUT;
    private String m_User = BusinessRules.ACCESS_USER_GUEST;
    private transient ProjectReference projectReference;

    public Project2() {
        this.m_Variables = new Variables();//svonborries_05012006

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

    public String getM_Description(){ return m_Description; }

    public void setM_Description(String m_Description){ this.m_Description = m_Description; }

    public String getName(){ return m_Name; }

    public void setName(String p_Name){
        this.m_Name = p_Name;
       this.getModelHandler().fireModelEventHappen(this,CMField.NAME);
    }

    public String toString() {  return m_Name;   }

    public Vector<TestObjectReference> getTestObjectReferences(){
            return m_TestObjectReferences;
        }

    public void setTestObjectReferences(Vector m_TestObjectReferences){
            this.m_TestObjectReferences = m_TestObjectReferences;
        }

    public Date getM_TimeStamp(){ return m_TimeStamp; }

    public void setM_TimeStamp(Date m_TimeStamp){ this.m_TimeStamp = m_TimeStamp; }
//////////////////////////Metodos Harold Canedo Lopez///////////////////////////
    //svonborries_05012006_begin
    public Variables getM_Variables()
    {
        return m_Variables;
    }

    public void setM_Variables(Variables m_Variables)
    {
        this.m_Variables = m_Variables;
    }
    //svonborries_05012006_end
    //grueda22082004_begin
    public String getVersion(){
            return m_Version;
        }

    public void setM_Version(String m_Version){
            this.m_Version = m_Version;
        }

    public String getAccessState(){ return m_AccessState; }

    public void setAccessState(String m_AccessState){ this.m_AccessState = m_AccessState; }

    public String getUser(){ return m_User; }

    public void setUser(String m_User){ this.m_User = m_User; }

    public boolean containsTestObjectReference(TestObjectReference p_TestObjectReferece){
    	boolean result= false;
    	for(int i=0; i<m_TestObjectReferences.size();i++){
    		TestObjectReference currentReference=(TestObjectReference)m_TestObjectReferences.elementAt(i);
    		if(currentReference.getFilePath().equalsIgnoreCase(p_TestObjectReferece.getFilePath()))
    			return true;
    	}
    	return result;
    }
    public TestObjectReference getSameTestObjectReference(TestObjectReference p_TestObjectReferece){
    	TestObjectReference result= null;
    	for(int i=0; i<m_TestObjectReferences.size();i++){
    		TestObjectReference currentReference=(TestObjectReference)m_TestObjectReferences.elementAt(i);
    		if(currentReference.getFilePath().equalsIgnoreCase(p_TestObjectReferece.getFilePath())){
    			return currentReference;
    		}
    	}
    	return result;
    }
    public boolean removeTestObjectReference(TestObjectReference p_TestObjectReferece){
    	boolean result= false;
    	for(int i=0; i<m_TestObjectReferences.size();i++){
    		TestObjectReference currentReference=(TestObjectReference)m_TestObjectReferences.elementAt(i);
    		if(currentReference.getFilePath().equalsIgnoreCase(p_TestObjectReferece.getFilePath())){
    			m_TestObjectReferences.removeElement(currentReference);
    			return true;
    		}
    	}
    	return result;
    }
    //grueda30122004_end
//////////////////////// fin Metodos Harold Canedo Lopez///////////////////////////

//	//Ccastedo begins 08-02-06
	public String getCurrentVersion(){
		return BusinessRules.PROJECT_FILE_VERSION;
	}
//	//	Ccastedo ends 08-02-06

    /**
	 * @return Returns the projectReference.
	 */
	public ProjectReference getProjectReference() {
		if (this.projectReference==null)
			this.projectReference = WorkspaceManager.getInstance().getProjectReferenceOfProject2InSession2(this,SessionManager.getCurrentSession());
		return this.projectReference;
	}

	public void setProjectReference(ProjectReference p_projectReference) {
		//validate the project reference?
		this.projectReference = p_projectReference;
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

	private CMModelEventHandler getModelHandler() {
		if (this.modelHandler==null)
			modelHandler = new CMModelEventHandler();
		return this.modelHandler;
	}

	public void fireModelEventHappen(CMField field) {
		getModelHandler().fireModelEventHappen(this, field);

	}
}
