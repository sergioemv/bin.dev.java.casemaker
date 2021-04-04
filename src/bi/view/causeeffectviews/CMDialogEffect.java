/*******************************************************************************
Developed by BUSINESS SOFTWARE INNOVATIONS. .
Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
*******************************************************************************/


package bi.view.causeeffectviews;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import model.Effect;
import model.Structure;
import bi.controller.EffectManager;
import bi.view.lang.CMMessages;
import bi.view.utils.CMBaseJDialog;
import bi.view.utils.CMJEditorPaneFocusChangeable;
import bi.view.utils.CMRiskLevelView;
import bi.view.utils.CMStateView;

import com.borland.jbcl.layout.XYConstraints;
import com.borland.jbcl.layout.XYLayout;

/**
 * Title:        CaseMaker
 * Description:
 * Copyright:    Copyright (c) 2003
 * Company:      BUSINESS SOFTWARE INNOVATIONS
 * @author
 * @version 1.0
 */

public class CMDialogEffect extends CMBaseJDialog {
  JPanel panel1 = new JPanel();
  XYLayout xYLayout1 = new XYLayout();
  JLabel jLabelID = new JLabel();
  JButton jButtonOK = new JButton();
  JButton jButtonCancel = new JButton();
  JTextField jTextFieldID = new JTextField();
  TitledBorder titledBorder1;
  XYLayout xYLayout2 = new XYLayout();
  boolean jButtonCancelClicked = false;
  boolean jButtonOKClicked = false;
  CMJEditorPaneFocusChangeable m_CauseEffectDescriptionView = new CMJEditorPaneFocusChangeable();
  JPanel jPanelDescriptionBox = new JPanel();
  Border border1;
  TitledBorder titledBorder2;
  Border border2;
  TitledBorder titledBorder3;
  JPanel jPanelCauseEffectBox = new JPanel();
  Border border3;
  TitledBorder titledBorder4;

  private JScrollPane jScrollPane1 = new JScrollPane();
  private Effect m_Effect;
  private EffectManager effectManager;
  private Structure m_structure;
//HCanedo_13032006_begin
  private CMStateView jCMState = new CMStateView();
  private CMRiskLevelView jCMRiskLevelView = new CMRiskLevelView();
//HCanedo_13032006_end
  public CMDialogEffect(Structure p_structure) {
    super();
    this.effectManager = EffectManager.INSTANCE;
    this.m_structure = p_structure;
    try {
      initGUI();
      pack();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  void initGUI() throws Exception {
    titledBorder1 = new TitledBorder("");
    border1 = BorderFactory.createEtchedBorder(Color.white,new Color(165, 163, 151));
    titledBorder2 = new TitledBorder(border1,CMMessages.getString("LABEL_DESCRIPTION")); //$NON-NLS-1$
    border2 = BorderFactory.createEtchedBorder(Color.white,new Color(165, 163, 151));
    titledBorder3 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(165, 163, 151)),CMMessages.getString("LABEL_CAUSE_EFFECT")); //$NON-NLS-1$
    border3 = BorderFactory.createEtchedBorder(Color.white,new Color(165, 163, 151));
    titledBorder4 = new TitledBorder(border3,CMMessages.getString("LABEL_CAUSE_EFFECT")); //$NON-NLS-1$
    jScrollPane1.getViewport().add(m_CauseEffectDescriptionView);

    panel1.setLayout(xYLayout1);
    this.setModal(true);
    this.setResizable(false);
    this.getContentPane().setLayout(xYLayout2);
    this.setTitle(CMMessages.getString("LABEL_CASEMAKER_CAUSE_EFFECT")); //$NON-NLS-1$


    jButtonOK.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButtonOK_actionPerformed(e);
      }
    });
    jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButtonCancel_actionPerformed(e);
      }
    });
    xYLayout2.setWidth(723);
    xYLayout2.setHeight(412);
    m_CauseEffectDescriptionView.setBorder(BorderFactory.createLoweredBevelBorder());
    m_CauseEffectDescriptionView.setToolTipText("");
    jPanelDescriptionBox.setBorder(titledBorder2);
    jTextFieldID.setBackground(Color.lightGray);
    jTextFieldID.setEditable(false);
    jPanelCauseEffectBox.setBorder(titledBorder4);
    this.getContentPane().add(panel1,  new XYConstraints(0, 0, 400, -1));
    this.getContentPane().add(jButtonCancel, new XYConstraints(365, 378, -1, -1));
    this.getContentPane().add(jButtonOK, new XYConstraints(260, 379, -1, -1));
    this.getContentPane().add(jScrollPane1,  new XYConstraints(25, 66, 671, 280));
    this.getContentPane().add(jPanelDescriptionBox,   new XYConstraints(12, 49, 696, 310));
    this.getContentPane().add(jTextFieldID, new XYConstraints(41, 21, 118, -1));
    this.getContentPane().add(jLabelID, new XYConstraints(18, 23, -1, -1));
//HCanedo_13032006_begin
    this.getContentPane().add(jCMState, new XYConstraints(253,15,114,30));
    this.getContentPane().add(jCMRiskLevelView, new XYConstraints(423,15,114,30));
//HCanedo_13032006_end
    this.getContentPane().add(jPanelCauseEffectBox,       new XYConstraints(5, 3, 711, 364));

    jLabelID.setText(CMMessages.getString("LABEL_ID")); //$NON-NLS-1$
    jButtonOK.setText(CMMessages.getString("BUTTON_OK")); //$NON-NLS-1$
    jButtonCancel.setText(CMMessages.getString("BUTTON_CANCEL")); //$NON-NLS-1$

    jButtonCancelClicked = false;
    jButtonOKClicked = false;
    this.addKeyListenertoAll(this,this.getDefaultKeyListener());
  }

    /**Overridden so we can exit when window is closed*/
  protected void processWindowEvent(WindowEvent e) {
    if (e.getID() == WindowEvent.WINDOW_CLOSING) {
      jButtonCancelClicked = true;
      cancel();
    }
    super.processWindowEvent(e);
  }
  /**Close the dialog*/
  void cancel() {
    dispose();
  }


  public boolean jButtonCancelClicked() {
    return jButtonCancelClicked;
  }

  public boolean jButtonOKClicked() {
    return jButtonOKClicked;
  }

  void jButtonOK_actionPerformed(ActionEvent e) {
    jButtonOKClicked = true;
    if(m_Effect == null) {
    	m_Effect = effectManager.createEffect(m_structure);
    	m_Effect.setDescription(m_CauseEffectDescriptionView.getText());
//HCanedo_14032006_begin
    	m_Effect.setState(jCMState.getSelectedState());
    	m_Effect.setRiskLevel(jCMRiskLevelView.getSelectedRisklevel());
//HCanedo_14032006_end    	
    }
    else {
			m_Effect.setDescription(m_CauseEffectDescriptionView.getText());
//HCanedo_14032006_begin
			m_Effect.setState(jCMState.getSelectedState());
	    	m_Effect.setRiskLevel(jCMRiskLevelView.getSelectedRisklevel());
//HCanedo_14032006_end
    }
     dispose();
  }

  void jButtonCancel_actionPerformed(ActionEvent e) {
    jButtonCancelClicked = true;
    cancel();
  }
  public Effect getEffect(){ return m_Effect; }

  public void setEffect(Effect p_Effect){
    m_Effect = p_Effect;
		this.jTextFieldID.setText(m_Effect.getName());
    this.m_CauseEffectDescriptionView.setText(m_Effect.getDescription());
//HCanedo_13032006_begin    
    this.jCMState.setState(m_Effect.getState());
    this.jCMRiskLevelView.setRisklevel(m_Effect.getRiskLevel());
//HCanedo_13032006_end
  }

protected List getOrder() {
	Vector componentList = new Vector(0);
//HCanedo_13032006_begin
	componentList.add(jCMState.getJComboBoxState());
	componentList.add(jCMRiskLevelView.getComboBox());
//HCanedo_13032006_end
	componentList.add(this.m_CauseEffectDescriptionView);
	componentList.add(jButtonOK);
	componentList.add(jButtonCancel);
	return componentList;
}

protected void fireButtonOk() {
	this.jButtonOK_actionPerformed(null);
	
}

protected void fireButtonCancel() {
	this.jButtonCancel_actionPerformed(null);
}

public JButton getDefaultButton() {
	return this.jButtonOK;
}
}