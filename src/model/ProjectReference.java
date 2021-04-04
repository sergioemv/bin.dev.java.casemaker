/*******************************************************************************
Developed by BUSINESS SOFTWARE INNOVATIONS. .
Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
*******************************************************************************/

package model;

import java.util.Date;

import model.util.CMFilePathBean;
import model.util.CMModelEventHandler;
import model.util.CMModelListener;
import model.util.CMModelSource;
import model.util.CMTimeStampBean;

import org.apache.log4j.Logger;

import bi.controller.SessionManager;
import bi.controller.WorkspaceManager;
import bi.controller.utils.CMBaseObjectReader;
import bi.controller.utils.CMXMLFileState;

public class ProjectReference implements CMFilePathBean, CMTimeStampBean, CMModelSource {
	// Smoreno the name is calculated from the filename
	//private transient String m_Name = "";
    private String m_FileName = "";
    private String m_Path = "";

    //Smoreno the file path is calculated from the path + filename
   // private transient String m_FilePath = "";
    private Workspace2 m_Workspace = null;
    private Date m_TimeStamp = new Date();

    private String m_LocalFilePath = "";
    private transient LocalProjectReference m_LocalProjectReference = null;
    private transient Project2 project;
    private transient Project2 localProject;
    private transient CMModelEventHandler modelHandler;

    public ProjectReference() {
    }

    public String getName(){
    	return getFileName().substring(0,getFileName().length()-BusinessRules.FILE_PROJECT_EXTENSION.length());
//     	if (getProject()!=null)
//    		return getProject().getName();
//    	else
//    		return null;
     	}

    //public void setName(String m_Name){
    	//, String p_ProjectPath){

  //    this.m_Name = m_Name;
   // }
// Smoreno: MVC the model cannot writte into the model!
//      StringBuffer sFileName = new StringBuffer();
//      sFileName.append(this.m_Name);
//      sFileName.append(BusinessRules.FILE_PROJECT_EXTENSION);
//      this.m_FileName = sFileName.toString();
//
//      this.m_Path = p_ProjectPath;
//
//      StringBuffer sFilePath = new StringBuffer();
//      sFilePath.append(m_Path);
//      sFilePath.append(BusinessRules.URL_SEPARATOR);
//      sFilePath.append(m_FileName);
//      m_FilePath = sFilePath.toString();
//   }

//    public void changeName(String p_Name, String p_ProjectPath) {
//      this.m_Name = p_Name;
//
//      StringBuffer sFileName = new StringBuffer();
//      sFileName.append(this.m_Name);
//      sFileName.append(BusinessRules.FILE_PROJECT_EXTENSION);
//      this.m_FileName = sFileName.toString();
//
//      m_Path = p_ProjectPath;
//      StringBuffer sFilePath = new StringBuffer();
//      sFilePath.append(m_Path);
//      sFilePath.append(BusinessRules.URL_SEPARATOR);
//      sFilePath.append(m_FileName);
//      m_FilePath = sFilePath.toString();
//    }



    public String getFileName(){ return m_FileName; }

    public void setFileName(String p_FileName){
    	if (!p_FileName.endsWith(BusinessRules.FILE_PROJECT_EXTENSION))
    		this.m_FileName = p_FileName+ BusinessRules.FILE_PROJECT_EXTENSION;
    		else
    			this.m_FileName = p_FileName;
    	this.getModelHandler().fireModelEventHappen(this, CMField.FILENAME);
//    	 Smoreno: MVC the model cannot writte into the model!
//      StringBuffer sBuffer = new StringBuffer();
//      sBuffer.append(this.m_Path);
//      sBuffer.append(BusinessRules.URL_SEPARATOR);
//      sBuffer.append(m_FileName);
//      this.m_FilePath = sBuffer.toString();
    }

    public String getPath(){ return m_Path; }

    public void setPath(String m_Path){
      this.m_Path = m_Path;
//    Smoreno: MVC the model cannot writte into the model!
//      StringBuffer sBuffer = new StringBuffer();
//      sBuffer.append(this.m_Path);
//      sBuffer.append(BusinessRules.URL_SEPARATOR);
//      sBuffer.append(m_FileName);
//      this.m_FilePath = sBuffer.toString();
      this.getModelHandler().fireModelEventHappen(this, CMField.PATH);
    }

    public String getFilePath(){
      return getPath()+BusinessRules.URL_SEPARATOR+getFileName();
    }

  // public void setM_FilePath(String m_FilePath){ this.m_FilePath = m_FilePath; }

    public Workspace2 getM_Workspace(){
    	if (m_Workspace == null)
    		m_Workspace = SessionManager.INSTANCE.getWorkspaceForProjectReference(this);
            return m_Workspace;
        }

    public void setM_Workspace(Workspace2 m_Workspace){
            this.m_Workspace = m_Workspace;
        }

    public Date getTimeStamp(){
      return this.m_TimeStamp;
    }

    public void setTimeStamp(Date p_TimeStamp){
      this.m_TimeStamp = p_TimeStamp;
    }

    public String getM_ChangedName(){
//    	if (m_ChangedName=="")
//    		m_ChangedName = getName();
// no need for that field to only change the name!
      return getName();
    }

//    public void setM_ChangedName(String p_ChangedName) {
//	  m_ChangedName = p_ChangedName;
//    }

    public String getM_LocalFilePath(){
            return m_LocalFilePath;
        }

    public void setM_LocalFilePath(String m_LocalFilePath){
            this.m_LocalFilePath = m_LocalFilePath;
        }
   //grueda18102004_begin
    public LocalProjectReference getM_LocalProjectReference(){
    	if (m_LocalProjectReference == null)
    	{
    		m_LocalProjectReference = WorkspaceManager.getInstance().buildLocalProjectReference(this);

    	}
            return m_LocalProjectReference;
        }

    public void setM_LocalProjectReference(LocalProjectReference m_LocalProjectReference){
            this.m_LocalProjectReference = m_LocalProjectReference;
        }

	public Project2 getProject() {
		if (this.project==null)
		{
			if (((CMBaseObjectReader.getReadFileState(this.getFilePath())!=null) &&
				(CMBaseObjectReader.getReadFileState(this.getFilePath())!=CMXMLFileState.VALID)))
				//&& (CMBaseObjectReader.getReadFileState(this.getFilePath())!=CMXMLFileState.NOTFOUND))
				{
				Logger.getLogger(this.getClass()).debug(this.getFileName() +" esta en "+CMBaseObjectReader.getReadFileState(this.getFilePath()));
				project = null;}
			else
			{
				project = WorkspaceManager.getInstance().readProject2ByReference(this);
				if (this.project!=null)
					this.getModelHandler().fireModelEventHappen(this,CMField.PROJECT);
			}


		}
		return this.project;
	}

	public Project2 getLocalProject() {
		if (this.localProject==null)
		{
				localProject = WorkspaceManager.getInstance().readProject2ByReference(this.getM_LocalProjectReference());
			}
		return this.localProject;
		//	localProject = WorkspaceManager.getInstance().readProject2ByReference(this.getM_LocalProjectReference());

	}

	public Project2 reloadProject()
	{
		project = null;
		return getProject();
	}

	public void setProject(Project2 p_project) {
		this.project = p_project;
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

	public void fireModelEventHappen(CMField field) {
		getModelHandler().fireModelEventHappen(this, field);

	}

    //grueda18102004_end

}
