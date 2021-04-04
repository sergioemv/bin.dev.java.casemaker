package model;

import java.util.Date;
/**
 ****************************************************************************** Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
public class ExternalApplication {
	public ExternalApplication(){
//hcanedo 14_08_2004_Begin
        this.setM_Param("");
//hcanedo 14_08_2004_End
    }

    public ExternalApplication(String name,String path,String param){
        this.setM_FilePath(path);
        this.setM_Name(name);
        //hcanedo_10_08_2004_Begin
        this.setM_Param(param);
        //hcanedo_10_08_2004_End
    }
    public String getM_Name(){
            return m_Name;
        }

    public void setM_Name(String m_Name){
            this.m_Name = m_Name;
        }

    public String getM_FilePath(){
            return m_FilePath;
        }

    public void setM_FilePath(String m_FilePath){
            this.m_FilePath = m_FilePath;
        }
public String toString() {

	return getM_Name();
}
    public String getM_Version(){ return m_Version; }

    public void setM_Version(String m_Version){ this.m_Version = m_Version; }

    public Date getM_TimeStamp(){ return m_TimeStamp; }

    public void setM_TimeStamp(Date m_TimeStamp){ this.m_TimeStamp = m_TimeStamp; }
//hcanedo_10_08_2004_Begin
    public String getM_Param(){ return m_Param; }

    public void setM_Param(String m_Param){ this.m_Param = m_Param; }
//hcanedo_10_08_2004_End
    private String m_Name;
    private String m_FilePath;
    private String m_Version = BusinessRules.SESSION_FILE_VERSION;
    private Date m_TimeStamp = new Date();
    //hcanedo_10_08_2004_Begin
    private String m_Param;
    //hcanedo_10_08_2004_End
}
