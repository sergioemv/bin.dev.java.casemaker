/*******************************************************************************
Developed by BUSINESS SOFTWARE INNOVATIONS. .
Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
*******************************************************************************/

package  bi.view.utils;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import bi.view.lang.CMMessages;

import model.State;
import model.util.CMStateBean;

/**
 * Title:        CaseMaker
 * Description:  Test tool for the automatic generation of test cases.
 * Copyright:    Copyright (c) 2003
 * Company:      BUSINESS SOFTWARE INNOVATIONS
 * @author Gary Rueda Sandoval
 * @version 1.0
 */

public class CMStateView extends JPanel {
  private CMBaseJComboBox jComboBoxState;
  private JLabel jLabelState = new JLabel();

  public CMStateView() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  public CMStateView(CMBaseJComboBox box) {
	  try {
		  jComboBoxState = box;
	      jbInit();
	    }
	    catch(Exception e) {
	      e.printStackTrace();
	    }
}
private void jbInit() throws Exception {
    jLabelState.setText(CMMessages.getString("STATE_COMBOBOX_LABEL"));
    getJComboBoxState().addItem(CMMessages.getString("STATE_FAULTY_LABEL"));
    getJComboBoxState().addItem(CMMessages.getString("STATE_IRRELEVANT_LABEL"));
    getJComboBoxState().addItem(CMStateBean.STATE_NEGATIVE_LABEL);
    getJComboBoxState().addItem(CMStateBean.STATE_POSITIVE_LABEL);
    getJComboBoxState().setSelectedIndex(3);
    getJComboBoxState().setPreferredSize(new java.awt.Dimension(40,20));
    setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEADING));
    setBounds(new java.awt.Rectangle(0, 0, 78, 26));
    add(jLabelState);
    add(getJComboBoxState());
  }
  public State getSelectedStateModel(){
	  Object selectedItem = (String) getJComboBoxState().getSelectedItem();
	  if( selectedItem.equals(CMMessages.getString("STATE_FAULTY_LABEL")) )    {  return State.FAULTY; }
	    else if( selectedItem.equals(CMMessages.getString("STATE_IRRELEVANT_LABEL")) ) { return State.IRRELEVANT; }
	    else if( selectedItem.equals(CMStateBean.STATE_NEGATIVE_LABEL) ) { return State.NEGATIVE; }
	    else { return State.POSITIVE; }
  }
  
  public int getSelectedState() {
    Object selectedItem = (String) getJComboBoxState().getSelectedItem();
    if( selectedItem.equals(CMMessages.getString("STATE_FAULTY_LABEL")) )    {  return CMStateBean.STATE_FAULTY; }
    else if( selectedItem.equals(CMMessages.getString("STATE_IRRELEVANT_LABEL")) ) { return CMStateBean.STATE_IRRELEVANT; }
    else if( selectedItem.equals(CMStateBean.STATE_NEGATIVE_LABEL) ) { return CMStateBean.STATE_NEGATIVE; }
    else { return CMStateBean.STATE_POSITIVE; }
  }
  public void setState(int state) {
    if(state == CMStateBean.STATE_FAULTY ) {
    	getJComboBoxState().setSelectedIndex(0);
    }
    else if(state == CMStateBean.STATE_IRRELEVANT) {
    	getJComboBoxState().setSelectedIndex(1);
    }
    else if(state == CMStateBean.STATE_NEGATIVE) {
    	getJComboBoxState().setSelectedIndex(2);
    }
    else if(state == CMStateBean.STATE_POSITIVE) {
    	getJComboBoxState().setSelectedIndex(3);
    }
  }
  public JComboBox getJComboBoxState()
  {
	  if (jComboBoxState == null)
		  jComboBoxState = new CMBaseJComboBox(this);
    return jComboBoxState;
  }
}