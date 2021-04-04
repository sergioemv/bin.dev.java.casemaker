/*******************************************************************************
Developed by BUSINESS INNOVATIONS.
Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
*******************************************************************************/

package  bi.view.utils;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import bi.view.lang.CMMessages;

import model.BusinessRules;

/**
 * Title:        CaseMaker
 * Description:  Test tool for the automatic generation of test cases.
 * Copyright:    Copyright (c) 2003
 * Company:      BUSINESS SOFTWARE INNOVATIONS
 * @author Gary Rueda Sandoval
 * @version 1.0
 */

public class CMRiskLevelView extends JPanel {
  CMBaseJComboBox jComboBoxRisklevel = new CMBaseJComboBox(this);
  JLabel jLabelRisklevel = new JLabel(CMMessages.getString("LABEL_RISK_LEVEL")); //$NON-NLS-1$

  public CMRiskLevelView() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  private void jbInit() throws Exception {
    jLabelRisklevel.setText(CMMessages.getString("RISK_LABEL"));
    jComboBoxRisklevel.addItem(BusinessRules.RISK_LEVEL_LABEL_ZERO);
    jComboBoxRisklevel.addItem(BusinessRules.RISK_LEVEL_LABEL_ONE);
    jComboBoxRisklevel.addItem(BusinessRules.RISK_LEVEL_LABEL_TWO);
    jComboBoxRisklevel.addItem(BusinessRules.RISK_LEVEL_LABEL_THREE);
    jComboBoxRisklevel.addItem(BusinessRules.RISK_LEVEL_LABEL_FOUR);
    jComboBoxRisklevel.addItem(BusinessRules.RISK_LEVEL_LABEL_FIVE);
    jComboBoxRisklevel.addItem(BusinessRules.RISK_LEVEL_LABEL_SIX);
    jComboBoxRisklevel.addItem(BusinessRules.RISK_LEVEL_LABEL_SEVEN);
    jComboBoxRisklevel.addItem(BusinessRules.RISK_LEVEL_LABEL_EIGHT);
    jComboBoxRisklevel.addItem(BusinessRules.RISK_LEVEL_LABEL_NEIN);
    jComboBoxRisklevel.addItem(BusinessRules.RISK_LEVEL_LABEL_TEN);

    jComboBoxRisklevel.setSelectedIndex(0);
    jComboBoxRisklevel.setPreferredSize(new java.awt.Dimension(40,20));
    setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEADING));
    setBounds(new java.awt.Rectangle(0, 0, 99, 26));
    add(jLabelRisklevel);
    add(jComboBoxRisklevel);
  }
  public int getSelectedRisklevel() {
    return jComboBoxRisklevel.getSelectedIndex();
  }

  public void setRisklevel(int p_Risklevel) {
    jComboBoxRisklevel.setSelectedIndex(p_Risklevel);
  }

  public JComboBox getComboBox() {
    return jComboBoxRisklevel;
  }

}