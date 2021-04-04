package model;

import java.util.Comparator;
import java.util.Date;
import java.util.Vector;

import model.util.CMIdBean;
import model.util.CMIdComparator;
import bi.view.lang.CMMessages;

/**
 * Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
public class CMError implements CMIdBean,Comparable<CMError>{
    public CMError(String m_Description) {
        this.m_Description = m_Description;
    }

    public CMError() { }

    public String getM_Description() { return m_Description; }

    public void setM_Description(String m_Description) { this.m_Description = m_Description; }

    public String getM_State() { return m_State; }

    public void setM_State(String m_State) { this.m_State = m_State; }

    public String getM_ErrorClass() { return m_ErrorClass; }

    public void setM_ErrorClass(String m_ErrorClass) { this.m_ErrorClass = m_ErrorClass; }

    public String getM_Priority() { return m_Priority; }

    public void setM_Priority(String m_Priority) { this.m_Priority = m_Priority; }

    public Date getM_IssueDate() { return m_IssueDate; }

    public void setM_IssueDate(Date m_IssueDate) { this.m_IssueDate = m_IssueDate; }

    public String getM_IssuedBy() { return m_IssuedBy; }

    public void setM_IssuedBy(String m_IssuedBy) { this.m_IssuedBy = m_IssuedBy; }

    public Date getM_ClosingDate() { return m_ClosingDate; }

    public void setM_ClosingDate(Date m_ClosingDate) { this.m_ClosingDate = m_ClosingDate; }

    public String getM_ClosedBy() { return m_ClosedBy; }

    public void setM_ClosedBy(String m_ClosedBy) { this.m_ClosedBy = m_ClosedBy; }

    public int getId() {
        return m_Id;
    }

    public void setId(int p_Id) {
        m_Id = p_Id;
        m_Name = generateName(p_Id);
    }

    public String getM_Name() {
        return m_Name;
    }

    public void setM_Name(String p_Name) {
        m_Name = p_Name;
    }

    private String generateName(int id) {
        StringBuffer idString = new StringBuffer();
        idString.append(id);
        int length = idString.length();
        for (int i = 0; i < BusinessRules.ID_LENGTH - length; i++) {
            idString.insert(0, BusinessRules.ID_FILLER_CHARACTER);
        }
        idString.insert(0, CMMessages.getString("ERROR_PREFIX"));
        return idString.toString();
    }

//HCanedo_30112004_Begin
    public String getM_AssignTo() {
        return m_AssignTo;
    }

    public void setM_AssignTo(String m_AssignTo) {
        this.m_AssignTo = m_AssignTo;
    }

    public Date getM_AssignDate() {
    	if (m_AssignDate == null)
    		m_AssignDate = new Date();
        return m_AssignDate;
    }

    public void setM_AssignDate(Date m_AssignDate) {
        this.m_AssignDate = m_AssignDate;
    }
 //HCanedo_30112004_End
    public Vector<TestCase> getM_TestCases() {
    	if (m_TestCases == null)
    		m_TestCases = new Vector<TestCase>();
        return m_TestCases;
    }

    public void setM_TestCases(Vector p_TestCases) {
        this.m_TestCases = p_TestCases;
    }
    public Comparator<CMError> getDefaultComparator() {
		if (this.defaultComparator==null)
			//by default orderer by Id
			this.defaultComparator = new CMIdComparator();
		return this.defaultComparator;
	}
    @Override
    public String toString() {
    	// TODO Auto-generated method stub
    	return this.getM_Name()+" "+getM_Description();
    }
    private String m_Description = "";
    private String m_State = BusinessRules.ERROR_STATE_OPEN;
    private String m_ErrorClass = BusinessRules.ERROR_CLASS_A;
    private String m_Priority = BusinessRules.ERROR_PRIORITY_HIGH;
    private Date m_IssueDate = new Date();
    private String m_IssuedBy = "";
    private Date m_ClosingDate = new Date();
    private String m_ClosedBy = "";
    private int m_Id = -1;
    private String m_Name = "";
    private Date m_TimeStamp = new Date();
 //HCanedo_30112004_Begin
    private String m_AssignTo;
    private Date m_AssignDate;
//HCanedo_30112004_End
    private Vector<TestCase> m_TestCases = new Vector(0);
    private transient Comparator defaultComparator;
	public int compareTo(CMError o) {
		// TODO Auto-generated method stub
		 return getDefaultComparator().compare(this,o);
	}
}
