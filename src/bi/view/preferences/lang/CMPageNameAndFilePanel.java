package bi.view.preferences.lang;

import java.awt.GridBagLayout;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import java.awt.Dimension;
import java.io.File;

import javax.swing.JButton;

import bi.view.lang.CMMessages;
import bi.view.utils.CMFileFilter;

public class CMPageNameAndFilePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel jLabelName = null;
	private JTextField jTextFieldName = null;
	private JTextField jTextFieldFileName = null;
	private JPanel jPanelSpacer1 = null;
	private JPanel jPanelSpacer2 = null;
	private JLabel jLabelImportFromFile = null;
	private JButton jButton = null;
	private JPanel jPanelSpacer3 = null;

	/**
	 * This is the default constructor
	 */
	public CMPageNameAndFilePanel() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 *
	 * @return void
	 */
	private void initialize() {
		GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
		gridBagConstraints5.gridx = 1;
		gridBagConstraints5.gridy = 2;
		GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
		gridBagConstraints4.gridx = 2;
		gridBagConstraints4.gridy = 7;
		GridBagConstraints gridBagConstraints31 = new GridBagConstraints();
		gridBagConstraints31.gridx = 1;
		gridBagConstraints31.anchor = GridBagConstraints.WEST;
		gridBagConstraints31.gridy = 3;
		jLabelImportFromFile = new JLabel();
		jLabelImportFromFile.setText(CMMessages.getString("LABEL_IMPORT_LANGUAGE_FROM_FILE")); //$NON-NLS-1$
		GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
		gridBagConstraints21.gridx = 3;
		gridBagConstraints21.gridy = 5;
		GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
		gridBagConstraints11.gridx = 0;
		gridBagConstraints11.gridy = 6;
		GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
		gridBagConstraints3.fill = GridBagConstraints.BOTH;
		gridBagConstraints3.gridy = 7;
		gridBagConstraints3.weightx = 1.0;
		gridBagConstraints3.gridx = 1;
		GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
		gridBagConstraints1.fill = GridBagConstraints.BOTH;
		gridBagConstraints1.gridy = 1;
		gridBagConstraints1.weightx = 1.0;
		gridBagConstraints1.gridx = 1;
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.gridy = 0;
		jLabelName = new JLabel();
		jLabelName.setText(CMMessages.getString("LABEL_LANGUAGE_NAME")); //$NON-NLS-1$
		this.setSize(300, 200);
		this.setLayout(new GridBagLayout());
		this.add(jLabelName, gridBagConstraints);
		this.add(getJTextFieldName(), gridBagConstraints1);
		this.add(getJTextFieldFileName(), gridBagConstraints3);
		this.add(getJPanelSpacer1(), gridBagConstraints11);
		this.add(getJPanelSpacer2(), gridBagConstraints21);
		this.add(jLabelImportFromFile, gridBagConstraints31);
		this.add(getJButton(), gridBagConstraints4);
		this.add(getJPanelSpacer3(), gridBagConstraints5);
	}

	/**
	 * This method initializes jTextFieldName
	 *
	 * @return javax.swing.JTextField
	 */
	public JTextField getJTextFieldName() {
		if (jTextFieldName == null) {
			jTextFieldName = new JTextField();
		}
		return jTextFieldName;
	}

	protected void searchFile() {
		// TODO Auto-generated method stub

	}

	/**
	 * This method initializes jTextFieldFileName
	 *
	 * @return javax.swing.JTextField
	 */
	public JTextField getJTextFieldFileName() {
		if (jTextFieldFileName == null) {
			jTextFieldFileName = new JTextField();
			jTextFieldFileName.setEditable(false);
		}
		return jTextFieldFileName;
	}

	/**
	 * This method initializes jPanelSpacer1
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelSpacer1() {
		if (jPanelSpacer1 == null) {
			jPanelSpacer1 = new JPanel();
			jPanelSpacer1.setLayout(new GridBagLayout());
			jPanelSpacer1.setPreferredSize(new Dimension(20, 0));
		}
		return jPanelSpacer1;
	}

	/**
	 * This method initializes jPanelSpacer2
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelSpacer2() {
		if (jPanelSpacer2 == null) {
			jPanelSpacer2 = new JPanel();
			jPanelSpacer2.setLayout(new GridBagLayout());
			jPanelSpacer2.setPreferredSize(new Dimension(20, 0));
		}
		return jPanelSpacer2;
	}

	/**
	 * This method initializes jButton
	 *
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("..."); //$NON-NLS-1$
			jButton.setPreferredSize(new Dimension(30, 20));
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					File file  = searchLangFile();
					if (file!=null)
						getJTextFieldFileName().setText(file.getAbsoluteFile().toString());
				}
			});
		}
		return jButton;
	}

	protected File searchLangFile() {

	        JFileChooser fileChooser = new JFileChooser();
	        fileChooser.setAcceptAllFileFilterUsed(false);
	        fileChooser.setDialogTitle(CMMessages.getString("LABEL_OPEN_LANGUAGE_FILE")); //$NON-NLS-1$
	        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
	        int returnVal = fileChooser.showOpenDialog(this);
	        File selectedFile = null;
	        if (returnVal == 0) {
	            selectedFile = fileChooser.getSelectedFile();
	            if(!selectedFile.exists()){
					JOptionPane.showMessageDialog(this,CMMessages.getString("BR_IMPORT_WIZARD_FILE_DOESNT_EXIST"),CMMessages.getString("ERROR_MESSAGE"),JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$ //$NON-NLS-2$
	                return null;
				}

	            return selectedFile;
			}
	        else{
	            return null;
	        }
	    }

	/**
	 * This method initializes jPanelSpacer3
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelSpacer3() {
		if (jPanelSpacer3 == null) {
			jPanelSpacer3 = new JPanel();
			jPanelSpacer3.setLayout(new GridBagLayout());
			jPanelSpacer3.setPreferredSize(new Dimension(0, 10));
		}
		return jPanelSpacer3;
	}

}
