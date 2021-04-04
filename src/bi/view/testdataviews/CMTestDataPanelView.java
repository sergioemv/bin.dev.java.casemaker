package bi.view.testdataviews;

import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Session2;
import model.TestObject;
import model.TestObjectReference;
import bi.view.lang.CMMessages;
/**
 ****************************************************************************** Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
public class CMTestDataPanelView extends JPanel {
    /** Creates new form CMTestDataPanelView */
    public CMTestDataPanelView() {
        initGUI();
    }

    /** This method is called from within the constructor to initialize the form. */
    private void initGUI() {
        jLabel1.setText(CMMessages.getString("LABEL_DISABLED")); //$NON-NLS-1$
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setFont(new java.awt.Font("SansSerif", java.awt.Font.ITALIC, 18)); //$NON-NLS-1$
        setLayout(new java.awt.BorderLayout());
        add(jLabel1, java.awt.BorderLayout.CENTER);
    }
    public TestObject getM_TestObject(){
        return m_TestObject;
    }

    public void setM_TestObject(TestObject p_TestObject, TestObjectReference p_TestObjectReference, Session2 p_Session){
        m_Session = p_Session;
        m_TestObject = p_TestObject;
        m_TestObjectReference = p_TestObjectReference;
    }

    private Session2 m_Session;
    private TestObject m_TestObject;
    private TestObjectReference m_TestObjectReference;
    private JLabel jLabel1 = new JLabel();
}
