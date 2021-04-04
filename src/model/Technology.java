package model;

import java.util.Date;
import java.util.Vector;
/**
 **************************************** Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2005 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
public class Technology implements Cloneable{
	
	
	public Technology(int id, String name, Vector objectTypesValue, Date timestamp) {
		  this.id = id;
	      this.m_Name = name;	      
	      this.m_ObjectTypesValue = objectTypesValue;	      
	      this.m_TimeStamp = timestamp;	      
	     
	      this.setM_Name(name);        	    
	      this.setM_ObjectTypesValue(objectTypesValue);
    }
	
	public Technology(String name) {	
		this.m_Name = name;	
	     this.m_TimeStamp = new Date();
	   //  this.m_ObjectTypesValue = new Vector(0);
	   }
	
	public String toString() {
	      return m_Name;
	    }
	
	public int getId(){
        return id;
    }

	public void setId(int id){
        this.id = id;
    }    
    
	public void setToolVendorTechnology(Technology m_Technology){
     	this.m_Technology = m_Technology;
     //	m_ToolVendor.setM_Technology(m_Technology);
     }
      public Technology getToolVendorTechnology(){    	  
     	 return m_Technology;
      }
      
    public Vector getM_ObjectTypesValue(){
        return m_ObjectTypesValue;
    }

    public void setM_ObjectTypesValue(Vector m_ObjectTypesValue){
        this.m_ObjectTypesValue = m_ObjectTypesValue;
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

       
    private int id = -1;
    private String m_Name;       
    
    private Technology m_Technology;
    
    private Vector m_ObjectTypesValue = new Vector(0);    
    
    private String m_Version = BusinessRules.SESSION_FILE_VERSION;
    private Date m_TimeStamp = new Date();       
   
}


