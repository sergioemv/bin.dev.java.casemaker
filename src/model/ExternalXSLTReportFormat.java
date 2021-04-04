package model;

import java.util.Date;
import java.util.Vector;

import org.apache.log4j.Logger;
/**
 ****************************************************************************** Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
public class ExternalXSLTReportFormat {
	public ExternalXSLTReportFormat(){
        m_Name= new Vector();
        m_FilePath= new Vector();
        m_Extension= new Vector();

       	String filePathXSLT= (BusinessRules.RESOURCE_FOLDER+BusinessRules.URL_SEPARATOR+BusinessRules.TESTDATA_REPORT_CSV_FILE);
       	Logger.getLogger(this.getClass()).debug(BusinessRules.RESOURCE_FOLDER+BusinessRules.URL_SEPARATOR+BusinessRules.TESTDATA_REPORT_CSV_FILE);
     /*   int index= filePathXSLT.indexOf(":");
        filePathXSLT =filePathXSLT.substring(index+2,filePathXSLT.length());*/
        String nameXSLT=BusinessRules.REPORT_XSLT_DEFAULT;
		this.m_FilePath.addElement(filePathXSLT);
        this.m_Name.addElement(nameXSLT);
        this.m_Extension.addElement(BusinessRules.FORMAT_CSV);
    }

    public ExternalXSLTReportFormat(String name,String path, String ext){
        m_Name= new Vector();
        m_FilePath= new Vector();
        m_Extension= new Vector();
        this.m_FilePath.addElement(path);
        this.m_Name.addElement(name);
        this.m_Extension.addElement(BusinessRules.FORMAT_CSV);
    }

    public String findExtensionByName(String p_Name)
    {
		if(m_Name.contains(p_Name))
        {
            int index= m_Name.indexOf(p_Name);
            return m_Extension.elementAt(index).toString();
        }
        else
        {
            return "";
        }
    }

	 public String findFilePathByName(String p_Name)
    {
		if(m_Name.contains(p_Name))
        {
            int index= m_Name.indexOf(p_Name);
            return m_FilePath.elementAt(index).toString();
        }
        else
        {
            return "";
        }
    }
    public void  registerNewXSLTReport(String p_name, String p_FilePath, String p_Ext)
    {
        m_Name.addElement(p_name);
        m_FilePath.addElement(p_FilePath);
        m_Extension.addElement(p_Ext);
    }

    public void changeNameXSLTReport(String old_Name, String new_Name)
    {
		if(m_Name.contains(old_Name))
        {
            int index = m_Name.indexOf(old_Name);
            m_Name.setElementAt(new_Name,index);
        }
    }

    public void changeExtensionXSLTReport(String p_name, String new_Ext)
    {
		if(m_Name.contains(p_name))
        {
            int index = m_Name.indexOf(p_name);
            m_Extension.setElementAt(new_Ext,index);
        }
    }
//hcanedo_21_09_2004_begin
    public void correctFilePathDefaultXSLTReportFormat()
    {
        String oldpath=m_FilePath.elementAt(0).toString();
        int index=oldpath.lastIndexOf("//");
        if(index > -1){
        	StringBuffer newPath= new StringBuffer();
        	newPath.append( oldpath.substring(0,index));
        	newPath.append("/");
        	newPath.append(oldpath.substring(index+2));
        	m_FilePath.setElementAt(newPath.toString(),0);
        }

     }
//hcanedo_21_09_2004_end
    public int size()
    {
        return m_Name.size();
    }

    public boolean existsNameReport(String p_name)
    {
        return m_Name.contains(p_name);
    }

    public boolean remove(String p_name)
    {
       int index= m_Name.indexOf(p_name);
       if(index >-1)
       {
        	m_Name.remove(index);
            m_FilePath.remove(index);
            m_Extension.remove(index);
            return true;
       }
       else
        	return false;
    }

    public Object clone()
    {
        ExternalXSLTReportFormat clon = new ExternalXSLTReportFormat();
        clon.setM_Name((Vector)this.getM_Name().clone());
        clon.setM_FilePath((Vector)this.getM_FilePath().clone());
        clon.setM_Extension((Vector)this.getM_Extension().clone());
        return clon;
    }
    public Vector getM_Name(){ return m_Name; }

    public void setM_Name(Vector m_Name){ this.m_Name = m_Name; }

    public Vector getM_FilePath(){ return m_FilePath; }

    public void setM_FilePath(Vector m_FilePath){ this.m_FilePath = m_FilePath; }

    public String getM_Version(){ return m_Version; }

    public void setM_Version(String m_Version){ this.m_Version = m_Version; }

    public Date getM_TimeStamp(){ return m_TimeStamp; }

    public void setM_TimeStamp(Date m_TimeStamp){ this.m_TimeStamp = m_TimeStamp; }

    public Vector getM_Extension(){ return m_Extension; }

    public void setM_Extension(Vector m_Extension){ this.m_Extension = m_Extension; }

    private Vector m_Name= new Vector();
    private Vector m_FilePath= new Vector();
    private String m_Version = BusinessRules.SESSION_FILE_VERSION;
    private Date m_TimeStamp = new Date();
    private Vector m_Extension= new Vector();
}
