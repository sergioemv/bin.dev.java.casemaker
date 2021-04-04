package model;
import java.io.File;

/**
 ****************************************************************************** Developed by BUSINESS INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
public class BRulesReference {
     //grueda01092004_begin
     /*
     public BRulesReference() {
        StringBuffer fileNameBuf = new StringBuffer();
        fileNameBuf.append(BusinessRules.BUSINESSRULES_FILE_NAME);
        fileNameBuf.append(BusinessRules.BUSINESSRULES_FILE_EXTENSION);
        this.fileName=fileNameBuf.toString();

    }
    */

    //public BRulesReference(TestObjectReference m_TObjectReference) {
    public BRulesReference() {
        StringBuffer stringBuffer = new StringBuffer();
        String p_ProjectPath=null;
        File project=null;
        String p_ProjectLocation = null;
        stringBuffer.append(BusinessRules.BUSINESSRULES_FILE_NAME);
        stringBuffer.append(BusinessRules.BUSINESSRULES_FILE_EXTENSION);
        this.fileName=stringBuffer.toString();
       //grueda29082004_begin
        //this.testObjectName=m_TObjectReference.getM_ChangedName();
        //this.testObjectsFolderPath=m_TObjectReference.getM_Path();
        stringBuffer = new StringBuffer();
        /*
        stringBuffer.append(this.testObjectsFolderPath);
        stringBuffer.append(BusinessRules.URL_SEPARATOR);
        stringBuffer.append(this.testObjectName);
        stringBuffer.append(BusinessRules.URL_SEPARATOR);
        */
        //grueda29082004_end
        stringBuffer.append(BusinessRules.BUSINESSRULES_FOLDER_NAME);
        this.fileLocation=stringBuffer.toString();
        stringBuffer.append(BusinessRules.URL_SEPARATOR);
        stringBuffer.append(this.fileName);
        this.filePath=stringBuffer.toString();
        //grueda29082004_begin
        /*
        p_ProjectPath = m_TObjectReference.getM_ProjectPath();
		project = new File(p_ProjectPath);
		p_ProjectLocation = project.getParent();
        this.projectLocation=p_ProjectLocation;
        */
        //grueda29082004_end
    }

    public void updatePaths(TestObjectReference TObjectReference){
        /*
         StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(BusinessRules.BUSINESSRULES_FILE_NAME);
        stringBuffer.append(BusinessRules.BUSINESSRULES_FILE_EXTENSION);
        this.fileName=stringBuffer.toString();
        this.testObjectName=TObjectReference.getM_ChangedName();
        this.testObjectsFolderPath=TObjectReference.getM_Path();
        stringBuffer = new StringBuffer();
        stringBuffer.append(this.testObjectsFolderPath);
        stringBuffer.append(BusinessRules.URL_SEPARATOR);
        stringBuffer.append(this.testObjectName);
        stringBuffer.append(BusinessRules.URL_SEPARATOR);
        stringBuffer.append(BusinessRules.BUSINESSRULES_FOLDER_NAME);
        this.fileLocation=stringBuffer.toString();
        stringBuffer.append(BusinessRules.URL_SEPARATOR);
        stringBuffer.append(this.fileName);
        this.filePath=stringBuffer.toString();
        */
    }
    //grueda16092004_begin
    public void update() {
        StringBuffer stringBuffer = new StringBuffer();
        String p_ProjectPath=null;
        File project=null;
        String p_ProjectLocation = null;
        stringBuffer.append(BusinessRules.BUSINESSRULES_FILE_NAME);
        stringBuffer.append(BusinessRules.BUSINESSRULES_FILE_EXTENSION);
        this.fileName=stringBuffer.toString();
        stringBuffer = new StringBuffer();
        stringBuffer.append(BusinessRules.BUSINESSRULES_FOLDER_NAME);
        this.fileLocation=stringBuffer.toString();
        stringBuffer.append(BusinessRules.URL_SEPARATOR);
        stringBuffer.append(this.fileName);
        this.filePath=stringBuffer.toString();
        this.projectLocation = "";
        this.testObjectsFolderPath = "";
    }
    //grueda16092004_end

    public String getFilePath(){ return filePath; }

    public void setFilePath(String filePath){ this.filePath = filePath; }

    public String getFileName(){ return fileName; }

    public void setFileName(String fileName){ this.fileName = fileName; }

    public String getTestObjectsFolderPath(){
            return testObjectsFolderPath;
        }

    public void setTestObjectsFolderPath(String testObjectsFolderPath){
            this.testObjectsFolderPath = testObjectsFolderPath;
        }

    public String getTestObjectName(){ return testObjectName; }

    public void setTestObjectName(String testObjectName){ this.testObjectName = testObjectName; }

    /**
     * 
     * @return
     */
    public int getFileSyntax(){
            return fileSyntax;
        }

    public void setFileSyntax(int fileSyntax){
            this.fileSyntax = fileSyntax;
        }

    public String getFileLocation(){
            return fileLocation;
        }

    public void setFileLocation(String fileLocation){
            this.fileLocation = fileLocation;
        }

    public String getProjectLocation(){ return projectLocation; }

    public void setProjectLocation(String projectLocation){ this.projectLocation = projectLocation; }

	private String fileLocation ="";//folder in wich the file is located
    private String filePath="";//complete path to file
    private String fileName="";//Business Rule file's name
    private String testObjectsFolderPath="";//Folder where project's TestObjects are
    private String testObjectName="";//The name of the Test Object
    private int fileSyntax=0;
    private String projectLocation;
}
