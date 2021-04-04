/*******************************************************************************
Developed by BUSINESS SOFTWARE INNOVATIONS. .
Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
*******************************************************************************/

package model;
import java.util.Date;

import bi.controller.SessionManager;
import bi.controller.WorkspaceManager;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import java.util.Vector;

import model.util.CMAccessStateBean;
import model.util.CMModelEventHandler;
import model.util.CMModelListener;
import model.util.CMModelSource;
import model.util.CMNameBean;
import model.util.CMUserBean;

/**
 *  Base unit for testing it has relations to Test Cases , Business Rules and Test Data
 * @stereotype file
 */
public class TestObject implements Cloneable, CMXMLFile, CMAccessStateBean, CMUserBean, CMNameBean, CMModelSource {
	/**
	 *  Notifier for model changes
	 */
	private transient CMModelEventHandler modelHandler;
	/**
	 *  Describes how to reach this object from a file
	 */
	private transient TestObjectReference m_TestObjectReference;
	/**
	 * Holds all Test Cases Data
	 */
	private Structure m_Structure;
    /**
     *  Preserves the creation date and hour
     */
    private Date m_TimeStamp = new Date();
    /**
     * Holds a general description of the test object<br>
     */
    private String m_Description = " "; //$NON-NLS-1$
    /**
     * Holds the name of the test object<br>
     */
    private String m_Name;
    /**
     * Holds the description of the preconditions for this Test Object
     */
    private String m_Preconditions = " "; //$NON-NLS-1$
    /**
     *  Holds the thousands separator for this test object
     */
    private String m_MilesSeparator;
    /**
     *  Says if the test object uses the default LOCALE separators
     */
    private boolean m_DefaultSeparator = false;
    /**
     * Holds the decimal separator for this test object
     */
    private String m_DecimalSeparator;
    /**
     *  The name of the tool vendor of the elements of the test object
     */
    private String m_ToolVendor;
    /**
     *  The name of the technollogy of the tool vendor used for the elements of the test object
     */
    private String m_ToolVendorTechnology;

    /**
     *  Holds the information about the access state of the Test Object
     */
    private String m_AccessState;

    /**
     *  Holds the user that putted the last state of the Test Object
     */
    private String m_User;
    /**
     *  Holds the reference to the Business Rules file
     */
    private BRulesReference m_BRulesReference=null	;

    /**
     * Holds the references to all structures of Test Data
     * @link aggregationByValue
     */
    private Vector m_TDSTructureReference = new Vector(0);

    /**
     *  The actual Version of the Test Object
     */
    private String m_Version = BusinessRules.TESTOBJECT_FILE_VERSION;

    /**
     *  The origin of the Test Object if it was imported from another source
     */
    private TestObjectOrigin m_Origin;

    /**
	 * @deprecated
	 *   The attribute has been moved to the test object reference
	 *   preserved for backward compatibility<br>
	 */
    @SuppressWarnings("unused")
	private transient String m_Path ; //$NON-NLS-1$
    /**
	 * @deprecated
	 *   The attribute has been moved to the test object reference
	 *   preserved for backward compatibility<br>
	 */
    @SuppressWarnings("unused")
    private transient String m_Filename ; //$NON-NLS-1$

    /**
	 * @deprecated
	 *   The attribute has been deleted
	 *   preserved for backward compatibility<br>
	 */
    @SuppressWarnings("unused")
    private transient String m_State;// = CMMessages.getString("LABEL_UNLOCKED"); //$NON-NLS-1$
    /**
	 * @deprecated
	 *   The attribute has been deleted
	 *   preserved for backward compatibility<br>
	 */
    @SuppressWarnings("unused")
    private transient String m_BRFilePath =null;
    /**
	 * @deprecated
	 *   The attribute has been deleted
	 *   preserved for backward compatibility<br>
	 */
    @SuppressWarnings("unused")
    private transient int m_BRFileSyntax=-1;
    /**
	 * @deprecated
	 *   The attribute has been deleted
	 *   preserved for backward compatibility<br>
	 */
    @SuppressWarnings("unused")
    private transient TDStructureReference lnkTDStructureReference;

    public TestObject() {
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

    public Structure getStructure(){
    	if (m_Structure == null){
    		 m_Structure = new Structure();
    	      m_Structure.setTestObject(this);
    	}
            return m_Structure;
      }

    public void setStructure(Structure m_Structure){
            this.m_Structure = m_Structure;
        }
    public Date getTimeStamp(){ return m_TimeStamp; }

    public void setTimeStamp(Date m_TimeStamp){ this.m_TimeStamp = m_TimeStamp; }

    public String getDescription(){ return m_Description; }

    public void setDescription(String m_Description){ this.m_Description = m_Description; }

    public String getName(){
    	if (m_Name == null)
    		m_Name = CMMessages.getString("LABEL_TEST_OBJECT_UNTITLED"); //$NON-NLS-1$
    	return m_Name; }

    public void setName(String m_Name){
    	this.m_Name = m_Name;
    	this.getModelHandler().fireModelEventHappen(this,CMField.NAME);//Ccastedo 20-04-06
    }

    public String toString() {  return m_Name;   }

    public String getPreconditions(){
            return m_Preconditions;
        }

    public void setPreconditions(String p_Preconditions){
            this.m_Preconditions = p_Preconditions;
        }

    public Vector getTDSTructureReference(){
    	if(m_TDSTructureReference == null)
    		m_TDSTructureReference = new Vector();
    	return m_TDSTructureReference; }

    public void setTDSTructureReference(Vector m_TDSTructureReference){ this.m_TDSTructureReference = m_TDSTructureReference; }

    public BRulesReference getBRulesReference(){
            return m_BRulesReference;
        }

    public void setBRulesReference(BRulesReference m_BRulesReference){
            this.m_BRulesReference = m_BRulesReference;
        }
    public String getVersion(){
            return m_Version;
        }

    public void setVersion(String m_Version){
            this.m_Version = m_Version;
        }

    public String getAccessState(){
    	if (m_AccessState == null)
    		m_AccessState = BusinessRules.ACCESS_STATE_CHECKED_OUT;
    	return m_AccessState; }

    public void setAccessState(String m_AccessState){ this.m_AccessState = m_AccessState; }

    public String getUser(){
    	if (m_User == null)
    	 m_User = BusinessRules.ACCESS_USER_GUEST;
    	return m_User; }

    public void setUser(String m_User){ this.m_User = m_User;}

    public String getMilesSeparator(){
    	if (isDefaultSeparator())
    		return BusinessRules.DEFAULT_MILES_SEPARATOR;
    	if (m_MilesSeparator == null)
    		m_MilesSeparator = BusinessRules.DEFAULT_MILES_SEPARATOR;
    	return m_MilesSeparator; }

    public void setMilesSeparator(String m_MilesSeparator){ this.m_MilesSeparator = m_MilesSeparator; }

    public boolean isDefaultSeparator(){ return m_DefaultSeparator; }

    public void setDefaultSeparator(boolean p_DefaultSeparator){ this.m_DefaultSeparator = p_DefaultSeparator; }

    public String getDecimalSeparator(){
    	if (isDefaultSeparator())
    		return BusinessRules.DEFAULT_DECIMAL_SEPARATOR;
    	if (m_DecimalSeparator == null)
    		m_DecimalSeparator=BusinessRules.DEFAULT_DECIMAL_SEPARATOR;
    	return m_DecimalSeparator; }

    public void setDecimalSeparator(String m_DecimalSeparator){ this.m_DecimalSeparator = m_DecimalSeparator; }

    public String getToolVendor(){
    	if (m_ToolVendor == null)
    		m_ToolVendor = BusinessRules.DEFAULT_ToolVendor;
    	return m_ToolVendor; }

    public void setToolVendor(String m_ToolVendor){ this.m_ToolVendor = m_ToolVendor; }

    public String getToolVendorTechnology(){
    	 if(m_ToolVendorTechnology== null)
             m_ToolVendorTechnology=BusinessRules.DEFAULT_ToolVendor_Technology;

    	return m_ToolVendorTechnology; }

    public void setToolVendorTechnology(String m_ToolVendorTechnology){
    	this.m_ToolVendorTechnology = m_ToolVendorTechnology; }

	public String getCurrentVersion(){
		return BusinessRules.TESTOBJECT_FILE_VERSION;
	}

	public TestObjectReference getTestObjectReference() {
		if (this.m_TestObjectReference==null)
			this.m_TestObjectReference = WorkspaceManager.getInstance().getTestObjectReferenceOfTestObjectInSession2(CMApplication.frame.getTreeWorkspaceView().getCurrentProject(),this,SessionManager.getCurrentSession());
		return this.m_TestObjectReference;
	}

	public void setTestObjectReference(TestObjectReference p_TestObjectReference) {
		this.m_TestObjectReference = p_TestObjectReference;
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

	public TestObjectOrigin getOrigin() {
	//The Origin should stay null if not explicity initialized
	//	if (m_Origin== null)
	//		m_Origin = new TestObjectOrigin();
		return m_Origin;
	}

	public void setOrigin(TestObjectOrigin origin) {
		m_Origin = origin;
		 getModelHandler().fireModelEventHappen(this, CMField.ORIGIN);
	}

	public void fireModelEventHappen(CMField field) {
		getModelHandler().fireModelEventHappen(this, field);

	}

}
