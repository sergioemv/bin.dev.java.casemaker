package model;

import java.util.Date;
import java.util.Vector;
/**
 **************************************** Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2005 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
public class ToolVendor implements Cloneable{
	
	
	public ToolVendor(int id, String name, Vector technologies,/* Vector objectTypesValue,*/ Date timestamp) {
		  this.id = id;
	      this.m_Name = name;
	      this.m_Technologies = technologies;
	  //    this.m_ObjectTypesValue = objectTypesValue;	      
	      this.m_TimeStamp = timestamp;	     
	      this.setM_Name(name);  	      
	      this.setM_Technology(technologies);
	     // this.setM_ObjectTypesValue(objectTypesValue);
    }
	public ToolVendor(String name, String path,String param) {
		this.m_Name = name;
	      this.m_FilePath = path;
	    //  this.m_Param = param;	
	      this.setM_FilePath(path);
	    //  this.setM_Param(param);
	   }
	
	public ToolVendor() {
		//this.setM_Param("");
	     this.m_TimeStamp = new Date();
	   //  this.m_ObjectTypesValue = new Vector(0);
	   }
	
	public ToolVendor(String name) {
		 this.m_Name = name;
		 this.setM_Name(name);  
		 this.m_Technologies = new Vector(0);
		 //this.m_ObjectTypesValue = new Vector(0);
	   }
	
	public int getId(){
        return id;
    }

	public void setId(int id){
        this.id = id;
    }
    
    public Vector getM_Technologies(){
            return m_Technologies;
        }

    public void setM_Technology(Vector m_Technologies){
          this.m_Technologies = m_Technologies;            
        }    
    
    
	public void setToolVendorTechnologySelected(Technology m_Technology){
     	this.m_Technology = m_Technology;
     //	m_ToolVendor.setM_Technology(m_Technology);
     }
      public Technology getToolVendorTechnologySelected(){    	  
     	 return m_Technology;
      }
      
    public String getM_FilePath(){
            return m_FilePath;
        }

    public void setM_FilePath(String m_FilePath){
            this.m_FilePath = m_FilePath;
        }

    public String getM_Name(){
        return m_Name;
    }

    public void setM_Name(String m_Name){
        this.m_Name = m_Name;
    }
    public String getM_Version(){ return m_Version; }

    public void setM_Version(String m_Version){ this.m_Version = m_Version; }

    public Date getM_TimeStamp(){ return m_TimeStamp; }

    public void setM_TimeStamp(Date m_TimeStamp){ this.m_TimeStamp = m_TimeStamp; }

  /*  public String getM_Param(){ return m_Param; }

    public void setM_Param(String m_Param){ this.m_Param = m_Param; }
    
    */
    public void readToolVendorFile(String cmdline){
    	
	  

}
    
    private int id = -1;
    private String m_Name=" ";
    private String m_FilePath=" ";
    
    private Vector m_Technologies = new Vector(0);   
    private Technology m_Technology = null;
   // private Vector m_ObjectTypesValue = new Vector(0);    
    
    private String m_Version = BusinessRules.SESSION_FILE_VERSION;
    private Date m_TimeStamp = new Date();    
 //   private String m_Param;
   
}

