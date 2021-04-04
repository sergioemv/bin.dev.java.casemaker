/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.view.utils;

import java.awt.FlowLayout;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.jidesoft.swing.JideButton;

import bi.view.icons.CMIcon;
import bi.view.lang.CMMessages;
import java.awt.Dimension;

/**
 * @author smoreno
 *
 */
public class CMOkCancelPanel extends JPanel {

	private JButton jButtonOk = null;
	private JButton jButtonCancel = null;
	private CMBaseJDialog parentDialog;
/**
 *
 */
public CMOkCancelPanel() {
	super();
	initialize();
}
	/**
	 *
	 */
	public CMOkCancelPanel(CMBaseJDialog p_parentDialog) {
		super();
		initialize();
		this.parentDialog = p_parentDialog;

	}

	/**
	 * This method initializes this
	 *
	 */
	private void initialize() {
        this.setSize(new Dimension(250, 46));
        FlowLayout flowLayout = new FlowLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		flowLayout.setHgap(7);
		flowLayout.setVgap(10);
		this.setLayout(flowLayout);
		this.setSize(250, 45);
		this.setPreferredSize(new java.awt.Dimension(100,40));
		this.add(getJButtonOk(), null);
		this.add(getJButtonCancel(), null);
	}


	/**
	 * This method initializes jButtonOk
	 *
	 * @return javax.swing.JButton
	 */
	public JButton getJButtonOk() {
		if (jButtonOk == null) {
			jButtonOk = new JButton();
			jButtonOk.setText(CMMessages.getString("BUTTON_OK")); //$NON-NLS-1$
			jButtonOk.setPreferredSize(new java.awt.Dimension(100,26));
			//jButtonOk.setButtonStyle(JideButton.TOOLBOX_STYLE);
			jButtonOk.setMnemonic(0);
			jButtonOk.setIcon(CMIcon.OK.getImageIcon());
			jButtonOk.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					parentDialog.setModalResult(CMModalResult.OK);
					parentDialog.setVisible(false);
				}
			});
		}
		return jButtonOk;
	}

	/**
	 * This method initializes jButtonCancel
	 *
	 * @return javax.swing.JButton
	 */
	public JButton getJButtonCancel() {
		if (jButtonCancel == null) {
			jButtonCancel = new JButton();
			jButtonCancel.setText(CMMessages.getString("BUTTON_CANCEL")); //$NON-NLS-1$
			jButtonCancel.setPreferredSize(new java.awt.Dimension(100,26));
			//jButtonCancel.setButtonStyle(JideButton.TOOLBAR_STYLE);
			jButtonCancel.setIcon(CMIcon.CANCEL.getImageIcon());
			jButtonCancel.setMnemonic(0);

			jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					parentDialog.setModalResult(CMModalResult.CANCEL);
					parentDialog.setVisible(false);
				}
			});
		}
		return jButtonCancel;
	}
/**
 * @param p_parentDialog The parentDialog to set.
 */
public void setParentDialog(CMBaseJDialog p_parentDialog) {
	this.parentDialog = p_parentDialog;
}
/**
 * @return
 */
public List getTabOrder() {
	return Arrays.asList(jButtonOk,jButtonCancel);
}
}  //  @jve:decl-index=0:visual-constraint="10,10"
