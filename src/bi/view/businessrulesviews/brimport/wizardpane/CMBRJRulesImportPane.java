package bi.view.businessrulesviews.brimport.wizardpane;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JCheckBox;
import java.awt.GridBagConstraints;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;

import bi.view.lang.CMMessages;

public class CMBRJRulesImportPane extends CMBRImportParametersPane {

	private JPanel jPanelAdditionalOptions = null;
	private JCheckBox jCheckBoxIgnoreCasts = null;
	private JCheckBox jCheckBoxNativeReplacements = null;
	private JPanel jPanelVoc = null;
	private JLabel jLabelVoc = null;
	private JTextField jTextFieldVoc = null;
	private JButton jButtonSearchVoc = null;

	/**
	 * This method initializes
	 *
	 */
	public CMBRJRulesImportPane() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 *
	 */
	private void initialize() {
        this.setSize(new Dimension(453, 334));
        this.add(getJPanelAdditionalOptions(), BorderLayout.SOUTH);

	}

	/**
	 * This method initializes jPanelAdditionalOptions
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelAdditionalOptions() {
		if (jPanelAdditionalOptions == null) {
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.gridx = 0;
			gridBagConstraints11.anchor = GridBagConstraints.WEST;
			gridBagConstraints11.gridwidth = 2;
			gridBagConstraints11.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints11.gridheight = 0;
			gridBagConstraints11.ipady = 1;
			gridBagConstraints11.ipadx = 1;
			gridBagConstraints11.weightx = 1.0;
			gridBagConstraints11.gridy = 2;
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 1;
			gridBagConstraints1.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints1.gridy = 0;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints.gridy = 0;
			jPanelAdditionalOptions = new JPanel();
			jPanelAdditionalOptions.setLayout(new GridBagLayout());
			jPanelAdditionalOptions.setPreferredSize(new Dimension(10, 80));
			jPanelAdditionalOptions.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), CMMessages.getString("IMPORT_CSV_OPTIONS_DIALOG_IMPORT_AS_PANEL"), TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Tahoma", Font.PLAIN, 11), new Color(0, 70, 213)));
			jPanelAdditionalOptions.add(getJCheckBoxIgnoreCasts(), gridBagConstraints);
			jPanelAdditionalOptions.add(getJCheckBoxNativeReplacements(), gridBagConstraints1);
			jPanelAdditionalOptions.add(getJPanelVoc(), gridBagConstraints11);
		}
		return jPanelAdditionalOptions;
	}

	/**
	 * This method initializes jCheckBoxIgnoreCasts
	 *
	 * @return javax.swing.JCheckBox
	 */
	public JCheckBox getJCheckBoxIgnoreCasts() {
		if (jCheckBoxIgnoreCasts == null) {
			jCheckBoxIgnoreCasts = new JCheckBox();
			jCheckBoxIgnoreCasts.setText(CMMessages.getString("LABEL_IGNORE_CASTS"));
			jCheckBoxIgnoreCasts.setActionCommand("");
			jCheckBoxIgnoreCasts.setSelected(true);
		}
		return jCheckBoxIgnoreCasts;
	}

	/**
	 * This method initializes jCheckBoxNativeReplacements
	 *
	 * @return javax.swing.JCheckBox
	 */
	public JCheckBox getJCheckBoxNativeReplacements() {
		if (jCheckBoxNativeReplacements == null) {
			jCheckBoxNativeReplacements = new JCheckBox();
			jCheckBoxNativeReplacements.setText(CMMessages.getString("LABEL_PROMPT_REPLACE_NATIVE_KEYWORDS"));
			jCheckBoxNativeReplacements.setSelected(true);
		}
		return jCheckBoxNativeReplacements;
	}

	/**
	 * This method initializes jPanelVoc
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelVoc() {
		if (jPanelVoc == null) {
			FlowLayout flowLayout = new FlowLayout();
			flowLayout.setAlignment(java.awt.FlowLayout.LEFT);
			jLabelVoc = new JLabel();
			jLabelVoc.setText(CMMessages.getString("LABEL_USE_VOCABULARY_FILE"));
			jPanelVoc = new JPanel();
			jPanelVoc.setLayout(flowLayout);
			jPanelVoc.setPreferredSize(new Dimension(20, 30));
			jPanelVoc.add(jLabelVoc, null);
			jPanelVoc.add(getJTextFieldVoc(), null);
			jPanelVoc.add(getJButtonSearchVoc(), null);
		}
		return jPanelVoc;
	}

	/**
	 * This method initializes jTextFieldVoc
	 *
	 * @return javax.swing.JTextField
	 */
	public JTextField getJTextFieldVoc() {
		if (jTextFieldVoc == null) {
			jTextFieldVoc = new JTextField();
			jTextFieldVoc.setPreferredSize(new Dimension(225, 19));
			jTextFieldVoc.setEditable(false);
		}
		return jTextFieldVoc;
	}

	/**
	 * This method initializes jButtonSearchVoc
	 *
	 * @return javax.swing.JButton
	 */
	public JButton getJButtonSearchVoc() {
		if (jButtonSearchVoc == null) {
			jButtonSearchVoc = new JButton();
			jButtonSearchVoc.setText(CMMessages.getString("LABEL_BROWSE_APPLICATION"));
			jButtonSearchVoc.setPreferredSize(new Dimension(90, 23));
		}
		return jButtonSearchVoc;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
