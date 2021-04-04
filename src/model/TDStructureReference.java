package model;
import java.util.Date;

/**
 ****************************************************************************** Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
public class TDStructureReference {
    public TDStructureReference() {
    }

    public Date getM_TimeStamp(){ return m_TimeStamp; }

    public void setM_TimeStamp(Date m_TimeStamp){ this.m_TimeStamp = m_TimeStamp; }

    public TestObject getM_TestObject(){ return m_TestObject; }

    public void setM_TestObject(TestObject m_TestObject){ this.m_TestObject = m_TestObject; }

    public String getM_TestObjectPath(){ return m_TestObjectPath; }

    public void setM_TestObjectPath(String m_TestObjectPath){ this.m_TestObjectPath = m_TestObjectPath; }

    public String getM_FilePath(){ return m_FilePath; }

    public void setM_FilePath(String m_FilePath){ this.m_FilePath = m_FilePath; }

    public String getM_Path(){
    	if (m_Path == null)
    		m_Path = "";
    	return m_Path; }

    public void setM_Path(String m_Path){ this.m_Path = m_Path; }

    public String getM_FileName(){ return m_FileName; }

    public void setM_FileName(String m_FileName){ this.m_FileName = m_FileName; }

    public String getM_Name(){ 
    	if(m_Name == null)
    		m_Name = "";
    	return m_Name; }

     public void setM_Name(String p_Name,String p_TestObjectPath, String p_TestObjectName)
    {
        //grueda29082004_begin
      this.m_Name = p_Name;
      //setM_TestObjectPath(p_TestObjectPath);
      StringBuffer sFileName = new StringBuffer();
      sFileName.append(this.m_Name);
      sFileName.append(BusinessRules.FILE_TESTDATA_EXTENSION);
      this.m_FileName = sFileName.toString();

      StringBuffer sPath = new StringBuffer();
      //sPath.append(p_TestObjectPath);
      //sPath.append(BusinessRules.URL_SEPARATOR); //$NON-NLS-1$
      //sPath.append(p_TestObjectName);
      //sPath.append(BusinessRules.URL_SEPARATOR); //$NON-NLS-1$
      sPath.append(BusinessRules.TEST_DATA_FOLDER);
      this.m_Path = sPath.toString();

      StringBuffer sFilePath = new StringBuffer();
      sFilePath.append(m_Path);
      sFilePath.append(BusinessRules.URL_SEPARATOR); //$NON-NLS-1$
      sFilePath.append(m_FileName);
      m_FilePath = sFilePath.toString();
      //grueda29082004_end
    }

    //grueda16092004_begin
    public void update()
    {
      StringBuffer sFileName = new StringBuffer();
      sFileName.append(this.m_Name);
      sFileName.append(BusinessRules.FILE_TESTDATA_EXTENSION);
      this.m_FileName = sFileName.toString();

      StringBuffer sPath = new StringBuffer();
      sPath.append(BusinessRules.TEST_DATA_FOLDER);
      this.m_Path = sPath.toString();

      StringBuffer sFilePath = new StringBuffer();
      sFilePath.append(m_Path);
      sFilePath.append(BusinessRules.URL_SEPARATOR);
      sFilePath.append(m_FileName);
      m_FilePath = sFilePath.toString();
      m_TestObjectPath = "";
    }
    //grueda16092004_end


    private Date m_TimeStamp;
    private TestObject m_TestObject;
    private String m_TestObjectPath;
    private String m_FilePath;
    private String m_Path;
    private String m_FileName;
    private String m_Name;   
	
////  Ccastedo begins
//	private boolean isValidFile=true;
//	private boolean validated=false;
//	
//	public boolean isValidFile() {
//		return isValidFile;
//	}
//
//	public void setValidFile(boolean isValidFile) {
//		this.isValidFile = isValidFile;
//	}
//
//	public boolean isValidated() {
//		return validated;
//	}
//
//	public void setValidated(boolean validated) {
//		this.validated = validated;
//	}
//	//	Ccastedo ends 
}
