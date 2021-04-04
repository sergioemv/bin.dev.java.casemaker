 /*******************************************************************************
Developed by BUSINESS SOFTWARE INNOVATIONS. .
Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
*******************************************************************************/

package model;
import java.util.Date;

import bi.controller.WorkspaceManager;
import bi.controller.utils.CMBaseObjectReader;
import bi.controller.utils.CMXMLFileState;

import model.util.CMFilePathBean;
import model.util.CMModelEventHandler;
import model.util.CMModelListener;
import model.util.CMModelSource;
import model.util.CMTimeStampBean;

public class TestObjectReference implements Cloneable, CMFilePathBean, CMTimeStampBean, CMModelSource {
    public TestObjectReference() {
    }

    public String getName(){
    	return getFileName().replaceAll(BusinessRules.FILE_TESTOBJECT_EXTENSION,"" );
    }


    public String getFileName(){ return m_FileName; }

    public void setFileName(String p_FileName){
    	m_Name = p_FileName;
    	//ccastedo begins 24-04-06
    	if (!p_FileName.endsWith(BusinessRules.FILE_TESTOBJECT_EXTENSION))
    		this.m_FileName = p_FileName+ BusinessRules.FILE_TESTOBJECT_EXTENSION;
    		else
    			this.m_FileName = p_FileName;

    	m_ChangedName=p_FileName;

    	this.getModelHandler().fireModelEventHappen(this, CMField.FILENAME);
    	//ccastedo ends 24-04-06
    }

    public String getPath(){ return /*m_Path+*/BusinessRules.TEST_OBJECTS_FOLDER; }

    public void setPath(String m_Path){
      this.m_Path = m_Path;
      this.getModelHandler().fireModelEventHappen(this, CMField.PATH);//ccastedo 24-04-06
    }

    public String getFilePath(){
    	return getPath()+BusinessRules.URL_SEPARATOR+getFileName();
    }

    public String getM_ProjectPath(){ return m_ProjectPath; }

    public void setM_ProjectPath(String m_ProjectPath){ this.m_ProjectPath = m_ProjectPath; }

    public void setM_Project(Project2 m_Project){
            this.m_Project = m_Project;
        }

    public String getM_AccessState(){ return m_AccessState; }

    public void setM_AccessState(String m_AccessState){ this.m_AccessState = m_AccessState; }

    public String getM_User(){ return m_User; }

    public void setM_User(String m_User){ this.m_User = m_User; }

    public Date getTimeStamp(){
      return this.m_TimeStamp;
    }

    public void setTimeStamp(Date p_TimeStamp){
      this.m_TimeStamp = p_TimeStamp;
    }

    public String getM_ChangedName(){
       return m_ChangedName;
    }

    public void setM_ChangedName(String p_ChangedName) {
	  m_ChangedName = p_ChangedName;
    }

    //grueda30122004_begin
    public TestObjectReference copy() {
      TestObjectReference b = null;
      try {
       b = (TestObjectReference)this.clone();
      } catch(CloneNotSupportedException e) {
        e.printStackTrace();
      }
      return b;
    }

    //grueda30122004_end
    private String m_Name = ""; //$NON-NLS-1$
    private String m_FileName = ""; //$NON-NLS-1$
    private String m_Path = ""; //$NON-NLS-1$
    private String m_FilePath = ""; //$NON-NLS-1$
///////////////////////modificaciones Harold Canedo Lopez/////////////////////
    private String m_ProjectPath=""; //$NON-NLS-1$
//////////////////////////////////////////////////////////////////////////////
    private Project2 m_Project = null;
    private String m_AccessState = BusinessRules.ACCESS_STATE_CHECKED_OUT;
    private String m_User = BusinessRules.ACCESS_USER_GUEST;
    private Date m_TimeStamp = new Date();
    private String m_ChangedName = ""; //$NON-NLS-1$

    //Ccastedo begins 24-04-06
    private transient TestObject testobject;
    private transient TestObject localTestObject;
    private transient CMModelEventHandler modelHandler;


    public TestObject getTestObject() {
		if (this.testobject==null)
		{
			if ((CMBaseObjectReader.getReadFileState(this.getFilePath())!=null) &&
				(CMBaseObjectReader.getReadFileState(this.getFilePath())!=CMXMLFileState.VALID))
				testobject = null;
			else
			{
				testobject = WorkspaceManager.getInstance().readTestObject2ByReference(this,m_ProjectPath);
				this.getModelHandler().fireModelEventHappen(this,CMField.PROJECT);
			}


		}
		return this.testobject;
	}

	public TestObject getLocalTestObject() {
		if (this.localTestObject==null)
			localTestObject = WorkspaceManager.getInstance().readTestObject2ByReference(this,m_ProjectPath);
		return this.localTestObject;
	}

	public TestObject reloadTestObject()
	{
		testobject = null;
		return getTestObject();
	}

	public void setTestObject(TestObject p_TestObject) {
		this.testobject = p_TestObject;
	}

    public CMModelEventHandler getModelHandler() {
		if (this.modelHandler== null)
			this.modelHandler = new CMModelEventHandler();
		return this.modelHandler;
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
	//Ccastedo ends 24-04-06

	public void fireModelEventHappen(CMField field) {
		getModelHandler().fireModelEventHappen(this, field);

	}
}
