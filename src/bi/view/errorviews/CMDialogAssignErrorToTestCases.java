/*******************************************************************************
****************************************************************************** Developed by BUSINESS SOFTWARE INNOVATIONS. .
****************************************************************************** Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
*******************************************************************************/

package bi.view.errorviews;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import model.BusinessRules;
import model.CMError;
import model.Structure;
import model.TestCase;
import bi.view.lang.CMMessages;
import bi.view.utils.CMBaseJDialog;
import bi.view.utils.CMShuttle;

import com.borland.jbcl.layout.XYConstraints;
import com.borland.jbcl.layout.XYLayout;

/**
 * Title:        CaseMaker
 * Description:  Test tool for the automatic generation of test cases.
 * Copyright:    Copyright (c) 2003
 * Company:      BUSINESS SOFTWARE INNOVATIONS
 * @author Gary Rueda Sandoval
 * @version 1.0
 */

public class CMDialogAssignErrorToTestCases extends  CMBaseJDialog {
  JPanel panelDialog = new JPanel();
  XYLayout xYLayout1 = new XYLayout();
  JButton jButtonOK = new JButton();
  JButton jButtonCancel = new JButton();
  JPanel jPanelCMErrorBox = new JPanel();
  Border border1;
  TitledBorder titledBorder1;
  XYLayout xYLayout2 = new XYLayout();
  CMShuttle jPanelShuttle = new CMShuttle(CMMessages.getString("LABEL_AVAILABLE_TEST_CASES"),CMMessages.getString("LABEL_CURRENT_ERROR_ASSIGNED_IN")); //$NON-NLS-1$ //$NON-NLS-2$
  JLabel jLabelName = new JLabel();
  JTextField jTextFieldName = new JTextField();
  TitledBorder titledBorder2;
  TitledBorder titledBorder3;
  ////////////////////////////////////////
  boolean eventJButtonCancelClicked = false;
  boolean eventJButtonOKClicked = false;
  CMError selectedCMError = null;
  Structure   selectedStructure = null;

  public CMDialogAssignErrorToTestCases(CMError selectedCMError,Structure structure) {
    super();
    this.selectedCMError = selectedCMError;
    this.selectedStructure   = structure;
    try {
      initGUI();
      pack();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
    this.addKeyListenertoAll(this,this.getDefaultKeyListener());
  }

  void initGUI() throws Exception {
    border1 = BorderFactory.createEtchedBorder(Color.white,new Color(165, 163, 151));
    titledBorder1 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(165, 163, 151)),CMMessages.getString("LABEL_ERROR")); //$NON-NLS-1$
    titledBorder2 = new TitledBorder("");
    titledBorder3 = new TitledBorder("");
    panelDialog.setLayout(xYLayout1);
    jButtonOK.setText(CMMessages.getString("BUTTON_OK")); //$NON-NLS-1$
    jButtonCancel.setText(CMMessages.getString("BUTTON_CANCEL")); //$NON-NLS-1$
    jPanelCMErrorBox.setBorder(titledBorder1);
    jPanelCMErrorBox.setLayout(xYLayout2);
    this.setResizable(false);
    this.setTitle(CMMessages.getString("LABEL_CASEMAKER_ERROR_ASSIGNMENT")); //$NON-NLS-1$
    jLabelName.setText(CMMessages.getString("LABEL_NAME")); //$NON-NLS-1$
    jTextFieldName.setEditable(false);
    jTextFieldName.setText(selectedCMError.getM_Name());
		///////////////////////////////////////////////////////////////////////////
    this.setLeftListView();
    this.setRightListView();
    setBounds(new java.awt.Rectangle(0, 0, 625, 238));
    xYLayout1.setHeight(238);
    xYLayout1.setWidth(625);
    setModal(true);
		///////////////////////////////////////////////////////////////////////////

    getContentPane().setLayout(xYLayout1);
    getContentPane().add(panelDialog);
    panelDialog.add(jPanelCMErrorBox,       new XYConstraints(5, 2, 610, 200));
    jPanelCMErrorBox.add(jTextFieldName, new XYConstraints(44, 1, 109, -1));
    jPanelCMErrorBox.add(jLabelName, new XYConstraints(6, 1, -1, -1));
    jPanelCMErrorBox.add(jPanelShuttle,   new XYConstraints(2, 30, 594, 143));
    panelDialog.add(jButtonOK, new XYConstraints(255, 205, 50, 27));
    panelDialog.add(jButtonCancel, new XYConstraints(315, 205, 70, 27));

    jButtonOK.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        eventJButtonOKActionPerformed(e);
      }
    });
    jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        eventJButtonCancelActionPerformed(e);
      }
    });
  }

      /**Overridden so we can exit when window is closed*/
  protected void processWindowEvent(WindowEvent e) {
    if (e.getID() == WindowEvent.WINDOW_CLOSING) {
      eventJButtonCancelClicked = true;
      cancel();
    }
    super.processWindowEvent(e);
  }
  /**Close the dialog*/
  void cancel() {
    dispose();
  }


  boolean isEventJButtonCancelClicked() {
    return eventJButtonCancelClicked;
  }

  public boolean isEventJButtonOKClicked() {
    return eventJButtonOKClicked;
  }

  void eventJButtonOKActionPerformed(ActionEvent e) {
   /* Vector testCasesRightList = jPanelShuttle.getRightList();
    if(testCasesRightList != null) {
      int numOfTestCases = testCasesRightList.size();
      for( int i = 0; i < numOfTestCases; i++) {
	    TestCase testCase = (TestCase) testCasesRightList.elementAt(i);
		if( !testCase.getM_CMErrors().contains(selectedCMError)) {
          int index = selectedStructure.getLnkTestCases().indexOf(testCase);
          TestCase realTestCase = (TestCase) selectedStructure.getLnkTestCases().elementAt(index);
		  realTestCase.getM_CMErrors().addElement(selectedCMError);
          //NEW 13.04.2004 by Gary
          selectedCMError.getM_TestCases().addElement(realTestCase);
		}
      }
    }

    Vector testCasesLeftList = jPanelShuttle.getLeftList();
    if( testCasesLeftList != null) {
      int numOfTestCases = testCasesLeftList.size();
      for( int i = 0; i < numOfTestCases; i++) {
        TestCase testCase = (TestCase) testCasesLeftList.elementAt(i);
        int index = selectedStructure.getLnkTestCases().indexOf(testCase);
        TestCase realTestCase = (TestCase) selectedStructure.getLnkTestCases().elementAt(index);
        if( realTestCase.getM_CMErrors().contains(selectedCMError)) {
	      realTestCase.getM_CMErrors().removeElement(selectedCMError);
          //NEW 13.04.2004 by Gary.
          selectedCMError.getM_TestCases().removeElement(realTestCase);
        }
      }
		}*///fcastro_23082004
    eventJButtonOKClicked = true;
    dispose();
  }

  void eventJButtonCancelActionPerformed(ActionEvent e) {
    eventJButtonCancelClicked = true;
    cancel();
  }

  private Vector createViews(Vector cloneElements) {
    Vector views = new Vector(0);
    StringBuffer sBuffer = null;
    int numElements = cloneElements.size();
    TestCase testCase = null;
    for( int i = 0; i < numElements; i++) {
      testCase = (TestCase) cloneElements.elementAt(i);
      sBuffer = new StringBuffer();
      sBuffer.append(testCase.getName());
      sBuffer.append(BusinessRules.SPACE);
      sBuffer.append(BusinessRules.SPACE);
      sBuffer.append(testCase.getDescriptionWithOutCheck());
      views.addElement(sBuffer.toString());
    }
    return views;
  }

  private void setLeftListView() {
    Vector allowedTestCases = new Vector(0);
    int numOfTestCases = selectedStructure.getLnkTestCases().size();
    for( int i = 0; i < numOfTestCases; i ++) {
      TestCase testCase = (TestCase) selectedStructure.getLnkTestCases().elementAt(i);
      if( testCase.getM_CMErrors() == null) {
        testCase.setM_CMErrors(new Vector(0));
      }
      if( !testCase.getM_CMErrors().contains(selectedCMError) ) {
        allowedTestCases.add(testCase);
	  }
    }
    Vector clones = (Vector) allowedTestCases.clone();
    Vector views = createViews(clones);
    jPanelShuttle.setLeftList(clones, views);
  }

  private void setRightListView() {
    Vector allowedTestCases = new Vector(0);
    int numOfTestCases = selectedStructure.getLnkTestCases().size();
    for( int i = 0; i < numOfTestCases; i ++) {
      TestCase testCase = (TestCase) selectedStructure.getLnkTestCases().elementAt(i);
      if( testCase.getM_CMErrors() == null) {
        testCase.setM_CMErrors(new Vector(0));
      }
	  if( testCase.getM_CMErrors().contains(selectedCMError) ) {
	    allowedTestCases.add(testCase);
	  }
    }
    Vector clones = (Vector) allowedTestCases.clone();
    Vector views = createViews(clones);
    jPanelShuttle.setRightList(clones, views);
  }
//fcastro_23082004_begin
  public Vector getRightList(){
    return jPanelShuttle.getRightList();
  }
    //fcastro_23082004_end

protected List getOrder() {
	Vector componentList = new Vector(0); 
    if (jPanelShuttle.getLeftJList().getModel().getSize()>0)
	  componentList.add(jPanelShuttle.getLeftJList());
	componentList.add(jPanelShuttle.getAddButton());
    if (jPanelShuttle.getRightJList().getModel().getSize()>0)
	  componentList.add(jPanelShuttle.getRightJList());
	componentList.add(jPanelShuttle.getDeleteButton());
	componentList.add(jButtonOK);
	componentList.add(jButtonCancel);
	
	
	return componentList;
}

protected void fireButtonOk() {
	
	this.eventJButtonOKActionPerformed(null);
	
}

protected void fireButtonCancel() {
	this.eventJButtonCancelActionPerformed(null);
	
}

public JButton getDefaultButton() {
	return this.jButtonOK;
}
}