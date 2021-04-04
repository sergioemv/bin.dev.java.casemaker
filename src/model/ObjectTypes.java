package model;

import java.util.Date;
/**
 **************************************** Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2005 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
public class ObjectTypes implements Cloneable{
		
	public ObjectTypes(int id, String name, Date timestamp) {
		  this.id = id;
	      this.m_Name = name;	 	          
	      this.m_TimeStamp = timestamp;	 	     
	    //  this.setM_Name(name);     	     
    }
	
	/*public ObjectTypes(String name) {
		this.m_Name = name;
	     this.m_TimeStamp = new Date();	    
	   }*/
	
	public ObjectTypes(int id,String name) {
		this.id = id;
		this.m_Name = name;
	    this.m_TimeStamp = new Date();	    
	   }
	
	public int getId(){
        return id;
    }

	public void setId(int id){
        this.id = id;
    }    
   
    public String getM_Name(){
        return m_Name;
    }
public String toString() {
	
	return getM_Name();
}
  /*  public void setM_Name(String m_Name){
        this.m_Name = m_Name;
    }*/
    
    public String getM_Version(){ return m_Version; }

    public void setM_Version(String m_Version){ this.m_Version = m_Version; }

    public Date getM_TimeStamp(){ return m_TimeStamp; }

    public void setM_TimeStamp(Date m_TimeStamp){ this.m_TimeStamp = m_TimeStamp; }

       
    private int id = -1;
    private String m_Name;       
      
    private String m_Version = BusinessRules.SESSION_FILE_VERSION;
    private Date m_TimeStamp = new Date();       
   
}


