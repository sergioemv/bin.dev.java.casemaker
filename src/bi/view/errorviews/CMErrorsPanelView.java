package bi.view.errorviews;

import javax.swing.JPanel;

import model.Session2;
import model.TestObject;
import model.TestObjectReference;
/**
 ****************************************************************************** Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
public class CMErrorsPanelView extends JPanel {
    /** Creates new form CMErrorsPanelView */
    public CMErrorsPanelView() {
        initGUI();
    }

    /** This method is called from within the constructor to initialize the form. */
    private void initGUI() {
        setLayout(new java.awt.BorderLayout());
    }
    public TestObject getM_TestObject(){
        return m_TestObject;
    }

    public void setM_TestObject(TestObject p_TestObject, TestObjectReference p_TestObjectReference, Session2 p_Session){
        m_Session = p_Session;
        m_TestObject = p_TestObject;
        m_TestObjectReference = p_TestObjectReference;
    }


	  public void enableViews(boolean p_Enable){
           this.setEnabled(p_Enable);
	  }

    private Session2 m_Session;
    private TestObject m_TestObject;
    private TestObjectReference m_TestObjectReference;

}
