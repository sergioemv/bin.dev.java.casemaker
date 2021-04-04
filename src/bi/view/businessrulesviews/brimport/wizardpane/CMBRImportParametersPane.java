package bi.view.businessrulesviews.brimport.wizardpane;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

import bi.view.icons.CMIcon;
import bi.view.lang.CMMessages;
import java.awt.Insets;
import java.awt.event.KeyEvent;


/**
* This code was generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* *************************************
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED
* for this machine, so Jigloo or this code cannot be used legally
* for any corporate or commercial purpose.
* *************************************
*/
@SuppressWarnings("serial") //$NON-NLS-1$
public class CMBRImportParametersPane extends JPanel {
	private JPanel jDescriptionContentPanel = null;
	private JPanel jDescriptionPanel = null;
	private JLabel jDescriptionLabel = null;
	private JPanel jTitlePanel = null;
	private JLabel jTitleLabel = null;
	private JPanel jSpacePanel = null;
	private JPanel jImagePanel = null;
	private JLabel jImageLabel = null;
	private JPanel jContentPanel = null;
	private JPanel jSpacePanel1 = null;
	private JPanel jSpacePanel2 = null;
	private JPanel jImportPanel = null;
	private JLabel jPathLabel = null;
	private JTextField jPathTextField = null;
	private JButton jBrowseButton = null;
	private JLabel jChooseFileGrammarLabel = null;
	private JPanel jRadioButtonsPanel = null;
	private JRadioButton jEnglishGrammarRadioButton = null;
	private JRadioButton jGermanGrammarRadioButton = null;
	private JCheckBox jSaveCheckBox = null;
	private JTextField jSaveTextField = null;
	private JButton jSaveAsButton = null;
	private JLabel jWarningLabel = null;
	private JLabel jFilePathLabel = null;
	private JLabel jPathLabelBom = null;
	private JButton jBrowseButtonBom = null;
	private JTextField jPathTextFieldBom = null;
	/**
	 * This method initializes 
	 * 
	 */
	public CMBRImportParametersPane() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(284,266));
		this.setSize(new java.awt.Dimension(420,319));
		add(getJDescriptionContentPanel(), java.awt.BorderLayout.NORTH);
		add(getJContentPanel(), java.awt.BorderLayout.CENTER);

			
	}

	
	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJDescriptionContentPanel() {
		if (jDescriptionContentPanel == null) {
			jDescriptionContentPanel = new JPanel();
			jDescriptionContentPanel.setLayout(new BorderLayout());
			jDescriptionContentPanel.add(getJDescriptionPanel(), java.awt.BorderLayout.CENTER);
			jDescriptionContentPanel.add(getJImagePanel(), java.awt.BorderLayout.EAST);
		}
		return jDescriptionContentPanel;
	}

	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJDescriptionPanel() {
		if (jDescriptionPanel == null) {
			jDescriptionLabel = new JLabel();
			jDescriptionLabel.setBackground(Color.white);
			jDescriptionLabel.setText(CMMessages.getString("BR_IMPORT_WIZARD_SELECT_BR_FILE")); //$NON-NLS-1$
			jDescriptionLabel.setFont(new Font("Dialog", Font.PLAIN, 12)); //$NON-NLS-1$
			BorderLayout borderLayout = new BorderLayout();
			borderLayout.setHgap(0);
			borderLayout.setVgap(0);
			jDescriptionPanel = new JPanel();
			jDescriptionPanel.setLayout(borderLayout);
			jDescriptionPanel.setBackground(Color.white);
			jDescriptionPanel.add(jDescriptionLabel, java.awt.BorderLayout.CENTER);
			jDescriptionPanel.add(getJTitlePanel(), java.awt.BorderLayout.NORTH);
			jDescriptionPanel.add(getJSpacePanel(), java.awt.BorderLayout.WEST);
		}
		return jDescriptionPanel;
	}

	/**
	 * This method initializes jPanel2	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJTitlePanel() {
		if (jTitlePanel == null) {
			jTitleLabel = new JLabel();
			jTitleLabel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			jTitleLabel.setFont(new Font("Dialog", Font.BOLD, 12)); //$NON-NLS-1$
			jTitleLabel.setText(CMMessages.getString("BR_IMPORT_WIZARD_DEFAULT_IMPORT_TITLE")); //$NON-NLS-1$
			jTitleLabel.setBackground(Color.white);
			FlowLayout flowLayout = new FlowLayout();
			flowLayout.setHgap(7);
			flowLayout.setAlignment(FlowLayout.LEFT);
			flowLayout.setVgap(7);
			jTitlePanel = new JPanel();
			jTitlePanel.setLayout(flowLayout);
			jTitlePanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			jTitlePanel.setBackground(Color.white);
			jTitlePanel.add(jTitleLabel, null);
		}
		return jTitlePanel;
	}

	/**
	 * This method initializes jPanel3	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJSpacePanel() {
		if (jSpacePanel == null) {
			FlowLayout flowLayout1 = new FlowLayout();
			flowLayout1.setHgap(0);
			flowLayout1.setVgap(0);
			jSpacePanel = new JPanel();
			jSpacePanel.setPreferredSize(new Dimension(12, 0));
			jSpacePanel.setLayout(flowLayout1);
			jSpacePanel.setBackground(Color.white);
		}
		return jSpacePanel;
	}

	/**
	 * This method initializes jPanel4	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJImagePanel() {
		if (jImagePanel == null) {
			jImageLabel = new JLabel();
			jImageLabel.setIcon(CMIcon.BUSINESS_RULES_WIZLOGO.getImageIcon()); //$NON-NLS-1$
			jImageLabel.setText(""); //$NON-NLS-1$
			jImagePanel = new JPanel();
			jImagePanel.setLayout(new BorderLayout());
			jImagePanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			jImagePanel.setPreferredSize(new Dimension(75, 66));
			jImagePanel.add(jImageLabel, java.awt.BorderLayout.CENTER);
		}
		return jImagePanel;
	}

	/**
	 * This method initializes jPanel5	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJContentPanel() {
		if (jContentPanel == null) {
			BorderLayout borderLayout1 = new BorderLayout();
			borderLayout1.setHgap(0);
			borderLayout1.setVgap(5);
			jContentPanel = new JPanel();
			jContentPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
			jContentPanel.setLayout(borderLayout1);
			jContentPanel.add(getJSpacePanel1(), java.awt.BorderLayout.WEST);
			jContentPanel.add(getJImportPanel(), java.awt.BorderLayout.CENTER);
			jContentPanel.add(getJSpacePanel2(), java.awt.BorderLayout.EAST);
		}
		return jContentPanel;
	}

	/**
	 * This method initializes jPanel8	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJSpacePanel1() {
		if (jSpacePanel1 == null) {
			FlowLayout flowLayout2 = new FlowLayout();
			flowLayout2.setHgap(0);
			flowLayout2.setVgap(0);
			jSpacePanel1 = new JPanel();
			jSpacePanel1.setPreferredSize(new Dimension(10, 0));
			jSpacePanel1.setLayout(flowLayout2);
		}
		return jSpacePanel1;
	}

	/**
	 * This method initializes jPanel9	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJSpacePanel2() {
		if (jSpacePanel2 == null) {
			FlowLayout flowLayout3 = new FlowLayout();
			flowLayout3.setHgap(0);
			flowLayout3.setVgap(0);
			jSpacePanel2 = new JPanel();
			jSpacePanel2.setPreferredSize(new Dimension(10, 0));
			jSpacePanel2.setLayout(flowLayout3);
		}
		return jSpacePanel2;
	}

	/**
	 * This method initializes jImportPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJImportPanel() {
		if (jImportPanel == null) {
			GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
			gridBagConstraints9.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints9.gridy = 4;
			gridBagConstraints9.weightx = 1.0;
			gridBagConstraints9.insets = new Insets(0, 5, 0, 5);
			gridBagConstraints9.gridx = 1;
			GridBagConstraints gridBagConstraints71 = new GridBagConstraints();
			gridBagConstraints71.gridx = 2;
			gridBagConstraints71.gridy = 4;
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.gridx = 0;
			gridBagConstraints11.gridy = 4;
			jPathLabelBom = new JLabel();
			jPathLabelBom.setText("BOM File Path:");
			jPathLabelBom.setVisible(false);
			GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
			gridBagConstraints8.ipadx = 0;
			gridBagConstraints8.anchor = java.awt.GridBagConstraints.WEST;
			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			gridBagConstraints7.gridx = 2;
			gridBagConstraints7.insets = new java.awt.Insets(5,0,0,0);
			gridBagConstraints7.gridy = 12;
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints6.gridy = 12;
			gridBagConstraints6.weightx = 1.0;
			gridBagConstraints6.insets = new java.awt.Insets(5,5,0,5);
			gridBagConstraints6.gridx = 1;
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.gridx = 0;
			gridBagConstraints5.insets = new java.awt.Insets(5,0,0,0);
			gridBagConstraints5.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints5.gridy = 12;
			jFilePathLabel = new JLabel();
			jFilePathLabel.setText(CMMessages.getString("BR_IMPORT_WIZARD_FILE_PATH_LABEL")); //$NON-NLS-1$
			jFilePathLabel.setEnabled(false);
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridx = 0;
			gridBagConstraints4.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints4.gridwidth = 3;
			gridBagConstraints4.insets = new java.awt.Insets(5,0,0,0);
			gridBagConstraints4.gridy = 11;
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.gridx = 0;
			gridBagConstraints3.insets = new java.awt.Insets(20,0,0,0);
			gridBagConstraints3.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints3.gridwidth = 3;
			gridBagConstraints3.gridy = 10;
			jWarningLabel = new JLabel();
			jWarningLabel.setText(CMMessages.getString("BR_IMPORT_WIZARD_WARNING")); //$NON-NLS-1$
			GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
			gridBagConstraints21.gridx = 0;
			gridBagConstraints21.gridwidth = 2;
			gridBagConstraints21.gridheight = 3;
			gridBagConstraints21.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints21.fill = java.awt.GridBagConstraints.NONE;
			gridBagConstraints21.insets = new java.awt.Insets(5,0,0,0);
			gridBagConstraints21.gridy = 7;
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridx = 0;
			gridBagConstraints2.gridheight = 1;
			gridBagConstraints2.gridwidth = 3;
			gridBagConstraints2.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints2.insets = new java.awt.Insets(20,0,0,0);
			gridBagConstraints2.gridy = 6;
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 2;
			gridBagConstraints1.ipadx = 0;
			gridBagConstraints1.gridy = 0;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints.gridy = 0;
			gridBagConstraints.weightx = 1.0;
			gridBagConstraints.ipadx = 0;
			gridBagConstraints.ipady = 0;
			gridBagConstraints.insets = new java.awt.Insets(0,5,0,5);
			gridBagConstraints.gridx = 1;
			jChooseFileGrammarLabel = new JLabel();
			jChooseFileGrammarLabel.setText(CMMessages.getString("BR_IMPORT_WIZARD_FILE_LANGUAJE_LABEL")); //$NON-NLS-1$
			jPathLabel = new JLabel();
			jPathLabel.setText(CMMessages.getString("BR_IMPORT_WIZARD_FILE_PATH_LABEL")); //$NON-NLS-1$
			jImportPanel = new JPanel();
			jImportPanel.setLayout(new GridBagLayout());
			jImportPanel.add(jPathLabel, gridBagConstraints8);
			jImportPanel.add(getJPathTextField(), gridBagConstraints);
			jImportPanel.add(getJBrowseButton(), gridBagConstraints1);
			jImportPanel.add(jChooseFileGrammarLabel, gridBagConstraints2);
			jImportPanel.add(getJRadioButtonsPanel(), gridBagConstraints21);
			jImportPanel.add(jWarningLabel, gridBagConstraints3);
			jImportPanel.add(getJSaveCheckBox(), gridBagConstraints4);
			jImportPanel.add(jFilePathLabel, gridBagConstraints5);
			jImportPanel.add(getJSaveTextField(), gridBagConstraints6);
			jImportPanel.add(getJSaveAsButton(), gridBagConstraints7);
			jImportPanel.add(jPathLabelBom, gridBagConstraints11);
			jImportPanel.add(getJBrowseButtonBom(), gridBagConstraints71);
			jImportPanel.add(getJPathTextFieldBom(), gridBagConstraints9);
		/*	SpringLayout layout = new SpringLayout();
			jImportPanel.setLayout(layout);
			jImportPanel.add(jPathLabel);
			jImportPanel.add(getJPathTextField());
			jImportPanel.add(getJBrowseButton());
			jImportPanel.add(jChooseFileGrammarLabel, null);
			jImportPanel.add(getJRadioButtonsPanel(), null);
			jImportPanel.add(jWarningLabel, null);
			jImportPanel.add(getJSaveCheckBox(), null);
			jImportPanel.add(jFilePathLabel, null);
			jImportPanel.add(getJSaveTextField(), null);
			jImportPanel.add(getJSaveAsButton(), null);
			
			layout.putConstraint(SpringLayout.WEST, jPathLabel,0,SpringLayout.WEST, jImportPanel);
			layout.putConstraint(SpringLayout.NORTH, jPathLabel,22,SpringLayout.NORTH, jImportPanel);

			layout.putConstraint(SpringLayout.WEST, getJPathTextField(),5,SpringLayout.EAST, jPathLabel);
			layout.putConstraint(SpringLayout.NORTH, getJPathTextField(),20,SpringLayout.NORTH, jImportPanel);

			layout.putConstraint(SpringLayout.WEST, getJBrowseButton(),5,SpringLayout.EAST, getJPathTextField());
			layout.putConstraint(SpringLayout.NORTH, getJBrowseButton(),18,SpringLayout.NORTH, jImportPanel);

			layout.putConstraint(SpringLayout.WEST, jChooseFileGrammarLabel,0,SpringLayout.WEST, jImportPanel);
			layout.putConstraint(SpringLayout.NORTH, jChooseFileGrammarLabel,40,SpringLayout.SOUTH, jPathLabel);

			layout.putConstraint(SpringLayout.WEST, getJRadioButtonsPanel(),0,SpringLayout.WEST, jImportPanel);
			layout.putConstraint(SpringLayout.NORTH, getJRadioButtonsPanel(),5,SpringLayout.SOUTH, jChooseFileGrammarLabel);
			
			layout.putConstraint(SpringLayout.WEST, jWarningLabel,0,SpringLayout.WEST, jImportPanel);
			layout.putConstraint(SpringLayout.NORTH, jWarningLabel,40,SpringLayout.SOUTH, getJRadioButtonsPanel());

			layout.putConstraint(SpringLayout.WEST, getJSaveCheckBox(),-2,SpringLayout.WEST, jImportPanel);
			layout.putConstraint(SpringLayout.NORTH, getJSaveCheckBox(),5,SpringLayout.SOUTH, jWarningLabel);

			layout.putConstraint(SpringLayout.WEST, jFilePathLabel,0,SpringLayout.WEST, jImportPanel);
			layout.putConstraint(SpringLayout.NORTH, jFilePathLabel,5,SpringLayout.SOUTH, getJSaveCheckBox());

			layout.putConstraint(SpringLayout.WEST, getJSaveTextField(),5,SpringLayout.EAST, jPathLabel);
			layout.putConstraint(SpringLayout.NORTH, getJSaveTextField(),5,SpringLayout.SOUTH, getJSaveCheckBox());
	
			layout.putConstraint(SpringLayout.EAST, getJSaveAsButton(),0,SpringLayout.EAST, jImportPanel);
			layout.putConstraint(SpringLayout.NORTH, getJSaveAsButton(),5,SpringLayout.SOUTH, getJSaveCheckBox());
	
			layout.putConstraint(SpringLayout.EAST, jImportPanel,0, SpringLayout.EAST, getJBrowseButton());
			layout.putConstraint(SpringLayout.NORTH, jImportPanel,5, SpringLayout.NORTH, getJPathTextField());*/
		}
		return jImportPanel;
	}

	/**
	 * This method initializes jPathTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJPathTextField() {
		if (jPathTextField == null) {
			jPathTextField = new JTextField();
			jPathTextField.setEditable(false);
		}
		return jPathTextField;
	}

	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	JButton getJBrowseButton() {
		if (jBrowseButton == null) {
			jBrowseButton = new JButton();
			jBrowseButton.setPreferredSize(new java.awt.Dimension(90,23));
			jBrowseButton.setText(CMMessages.getString("LABEL_BROWSE_APPLICATION")); //$NON-NLS-1$
		}
		return jBrowseButton;
	}

	public void setSelectedFile(File selectedFile) {
		if(selectedFile!= null)
			getJPathTextField().setText(selectedFile.toString());
		else{
			getJPathTextField().setText("");
		}
	}


	public void setSelectedFileBom(File selectedFile) {
		if(selectedFile!= null)
			getJPathTextFieldBom().setText(selectedFile.toString());
		else{
			getJPathTextFieldBom().setText("");
		}
	}

	
	/**
	 * This method initializes jRadioButtonsPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJRadioButtonsPanel() {
		if (jRadioButtonsPanel == null) {
			jRadioButtonsPanel = new JPanel();
			jRadioButtonsPanel.setLayout(new BoxLayout(getJRadioButtonsPanel(), BoxLayout.Y_AXIS));
			jRadioButtonsPanel.add(getJEnglishGrammarRadioButton(), null);
			jRadioButtonsPanel.add(getJGermanGrammarRadioButton(), null);
			ButtonGroup group = new ButtonGroup();
		    group.add(getJEnglishGrammarRadioButton());
		    group.add(getJGermanGrammarRadioButton());
		}
		return jRadioButtonsPanel;
	}

	/**
	 * This method initializes jEnglishGrammarRadioButton	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	 JRadioButton getJEnglishGrammarRadioButton() {
		if (jEnglishGrammarRadioButton == null) {
			jEnglishGrammarRadioButton = new JRadioButton();
			jEnglishGrammarRadioButton.setText(CMMessages.getString("BI_STRUCTURED_ENGLISH"));//"Business Innovations Structured English"); //$NON-NLS-1$
			jEnglishGrammarRadioButton.setSelected(true);
		}
		return jEnglishGrammarRadioButton;
	}

	/**
	 * This method initializes jGermanGrammarRadioButton	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	JRadioButton getJGermanGrammarRadioButton() {
		if (jGermanGrammarRadioButton == null) {
			jGermanGrammarRadioButton = new JRadioButton();
			jGermanGrammarRadioButton.setText(CMMessages.getString("BI_STRUCTURED_GERMAN"));//"Business Innovations Structured German"); //$NON-NLS-1$
		}
		return jGermanGrammarRadioButton;
	}

	/**
	 * This method initializes jSaveCheckBox	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	JCheckBox getJSaveCheckBox() {
		if (jSaveCheckBox == null) {
			jSaveCheckBox = new JCheckBox();
			jSaveCheckBox.setText(CMMessages.getString("BR_IMPORT_WIZARD_SAVE_CURRENT_BRFILE")); //$NON-NLS-1$
		}
		return jSaveCheckBox;
	}

	/**
	 * This method initializes jSaveTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJSaveTextField() {
		if (jSaveTextField == null) {
			jSaveTextField = new JTextField();
			jSaveTextField.setPreferredSize(jPathTextField.getPreferredSize());
			jSaveTextField.setMaximumSize(jPathTextField.getMaximumSize());
			jSaveTextField.setMinimumSize(jPathTextField.getMinimumSize());
			jSaveTextField.setEnabled(false);
			jSaveTextField.setEditable(false);
		}
		return jSaveTextField;
	}

	/**
	 * This method initializes jSaveAsButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	 JButton getJSaveAsButton() {
		if (jSaveAsButton == null) {
			jSaveAsButton = new JButton();
			jSaveAsButton.setPreferredSize(new java.awt.Dimension(90,23));
			jSaveAsButton.setEnabled(false);
			jSaveAsButton.setText(CMMessages.getString("BR_IMPORT_WIZARD_SAVE_AS_BUTTON")); //$NON-NLS-1$
		}
		return jSaveAsButton;
	}

	 void setStateOfSaveAsComponents(){
		 if(jSaveCheckBox.isSelected()){
				jFilePathLabel.setEnabled(true);
				jSaveTextField.setEnabled(true);
				jSaveAsButton.setEnabled(true);
			}
			else{
				jFilePathLabel.setEnabled(false);
				jSaveTextField.setEnabled(false);
				jSaveAsButton.setEnabled(false);
			}
	 }

	public void setSaveAsSelectedFile(File selectedFile) {
		getJSaveTextField().setText(selectedFile.toString());
		
	}

	public List getOrder() {
		List focusOrder= new ArrayList();
		focusOrder.add(jBrowseButton);
		focusOrder.add(jEnglishGrammarRadioButton);
		focusOrder.add(jGermanGrammarRadioButton);
		focusOrder.add(jSaveCheckBox);
		focusOrder.add(jSaveAsButton);
		return focusOrder;
	}

	/**
	 * This method initializes jBrowseButtonBom	
	 * 	
	 * @return javax.swing.JButton	
	 */
	public JButton getJBrowseButtonBom() {
		if (jBrowseButtonBom == null) {
			jBrowseButtonBom = new JButton();
			jBrowseButtonBom.setPreferredSize(new Dimension(90, 23));
			jBrowseButtonBom.setMnemonic(KeyEvent.VK_UNDEFINED);
			jBrowseButtonBom.setText(CMMessages.getString("BR_IMPORT_WIZARD_BROWSE_BUTTON"));
			jBrowseButtonBom.setVisible(false);
			jBrowseButtonBom.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
				}
			});
		}
		return jBrowseButtonBom;
	}

	/**
	 * This method initializes jPathTextFieldBom	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	public JTextField getJPathTextFieldBom() {
		if (jPathTextFieldBom == null) {
			jPathTextFieldBom = new JTextField();
			jPathTextFieldBom.setEditable(false);
			jPathTextFieldBom.setVisible(false);
		}
		return jPathTextFieldBom;
	}

	//portiz_26102007_begin
	/**
	 * Show/hide the bom data components
	 */
	public void showHideBomData(boolean __bShow){
		jPathLabelBom.setVisible(__bShow);
		jPathTextFieldBom.setVisible(__bShow);
		getJBrowseButtonBom().setVisible(__bShow);
		
		
	}
	//portiz_26102007_end	
	
}  //  @jve:decl-index=0:visual-constraint="10,10"