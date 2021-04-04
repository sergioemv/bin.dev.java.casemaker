/*******************************************************************************
Developed by BUSINESS SOFTWARE INNOVATIONS. .
Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
*******************************************************************************/


package  bi.view.mainframeviews;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.DefaultHighlighter;

import bi.view.lang.CMMessages;
import bi.view.utils.CMBaseJDialog;
import bi.view.utils.CMOkCancelPanel;

public class CMLicenseInputRequestView extends CMBaseJDialog{
    /** Creates new form JDialog */
    public CMLicenseInputRequestView() {
        super();
        initialize();
        this.addKeyListenertoAll(this,this.getDefaultKeyListener());
        //initGUI();
    }

    /** This method is called from within the constructor to initialize the form. */
//    private void initGUI() {
//
//        getContentPane().setLayout(new java.awt.BorderLayout());
//      //  getContentPane().add(jPanel3, java.awt.BorderLayout.NORTH);
//      //  getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);
//      //  getContentPane().add(jPanel1, java.awt.BorderLayout.SOUTH);
//        setTitle("Licensing Information"); //$NON-NLS-1$
//        this.setContentPane(jPanel3);
//        setSize(355, 233);
//        setFont(new java.awt.Font("Dialog",java.awt.Font.ITALIC,12)); //$NON-NLS-1$
//        setResizable(true);
//        setModal(true);
//        jLabel1.setText(CMMessages.getString("PROMPT_PLEASE_INPUT_YOUR_LICENSE_KEY")); //$NON-NLS-1$
//        jPanel3.add(jLabel1);
//        jPanel3.add(getJPanelContainer(), null);
//        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.Y_AXIS));
//        jPanel2.add(licenseTextField);
//        jPanel2.add(jPanel4);
//        jPanel2.add(jEditorPane1);
//        jEditorPane1.setEditable(false);
//        licenseTextField.setText(""); //$NON-NLS-1$
//        licenseTextField.setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 12)); //$NON-NLS-1$
//        licenseTextField.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(),
//        CMMessages.getString("LABEL_LICENSE_KEY"), javax.swing.border.TitledBorder.LEADING, javax.swing.border.TitledBorder.TOP, new java.awt.Font("SansSerif", 0, 11), //$NON-NLS-1$ //$NON-NLS-2$
//        new java.awt.Color(60, 60, 60)));
//		Object[] arguments = {
//		  m_Code
//		};
//		String result = java.text.MessageFormat.format(CMMessages.getString("INFO_HOW_TO_GET_LICENSE_KEY"), arguments); //$NON-NLS-1$
//        jEditorPane1.setText(result);
//        jEditorPane1.setEditable(true);
//        jEditorPane1.setBackground(new java.awt.Color(206, 223, 247));
//        jEditorPane1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(
//        new java.awt.Color(153, 153, 153), 1), "", javax.swing.border.TitledBorder.LEADING, javax.swing.border.TitledBorder.TOP, //$NON-NLS-1$
//        new java.awt.Font("SansSerif", 0, 11), new java.awt.Color(60, 60, 60))); //$NON-NLS-1$
//        this.addKeyListenertoAll(this,this.getDefaultKeyListener());
//}


    /**
	 * This method initializes this
	 *
	 */
	private void initialize() {
        this.setSize(new java.awt.Dimension(306,271));
        this.setTitle("Licensing Information");
        this.setContentPane(getJPanelContainer());
        this.setModal(true);

	}

	/** Closes the dialog */
    private void closeDialog(WindowEvent evt) {
        setVisible(false);
        dispose();
    }




    public String getLicenseTextField() {
      return getJTextFieldLicense().getText();
    }

    private String code;
	private JPanel jPanelContainer = null;
	private JPanel jPaneInput = null;
	private JLabel jLabelInputYourLicense = null;
	private JPanel jPanelInputLicense = null;
	private JTextField jTextFieldLicense = null;
	private JEditorPane jEditorPaneExplanation = null;
	private CMOkCancelPanel okCancelPanel = null;
	private DefaultHighlighter defaultHighlighter = null;  //  @jve:decl-index=0:visual-constraint=""
	protected List getOrder() {
		Vector<Component> componentList = new Vector<Component>(0);
		componentList.add(this.getJTextFieldLicense());
		componentList.add(this.getOkCancelPanel().getJButtonOk());
		componentList.add(this.getOkCancelPanel().getJButtonCancel());
		return componentList;
	}

	protected void fireButtonOk() {
		this.getOkCancelPanel().getJButtonOk().doClick();

	}

	protected void fireButtonCancel() {
		this.getOkCancelPanel().getJButtonCancel().doClick();

	}

	public JButton getDefaultButton() {
		return this.getOkCancelPanel().getJButtonOk();
	}

	/**
	 * This method initializes jPanelContainer
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelContainer() {
		if (jPanelContainer == null) {
			jPanelContainer = new JPanel();
			jPanelContainer.setLayout(new BorderLayout());
			jPanelContainer.add(getJPanelLabelInput(), java.awt.BorderLayout.NORTH);
			jPanelContainer.add(getJEditorPaneExplanation(), java.awt.BorderLayout.CENTER);
			jPanelContainer.add(getOkCancelPanel(), java.awt.BorderLayout.SOUTH);
		}
		return jPanelContainer;
	}

	/**
	 * This method initializes jPanelLabelInput
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelLabelInput() {
		if (jPaneInput == null) {
			jLabelInputYourLicense = new JLabel();
			jLabelInputYourLicense.setText(CMMessages.getString("PROMPT_PLEASE_INPUT_YOUR_LICENSE_KEY")); //$NON-NLS-1$
			jLabelInputYourLicense.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
			jLabelInputYourLicense.setBackground(java.awt.SystemColor.inactiveCaptionText);
			jLabelInputYourLicense.setPreferredSize(new java.awt.Dimension(138,20));
			jPaneInput = new JPanel();
			jPaneInput.setLayout(new BorderLayout());
			jPaneInput.add(jLabelInputYourLicense, java.awt.BorderLayout.NORTH);
			jPaneInput.add(getJPanelInputLicense(), java.awt.BorderLayout.CENTER);
		}
		return jPaneInput;
	}

	/**
	 * This method initializes jPanelInputLicense
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelInputLicense() {
		if (jPanelInputLicense == null) {
			jPanelInputLicense = new JPanel();
			jPanelInputLicense.setLayout(new BorderLayout());
			jPanelInputLicense.setPreferredSize(new java.awt.Dimension(10,60));
			jPanelInputLicense.setBorder(javax.swing.BorderFactory.createTitledBorder(null, CMMessages.getString("LABEL_LICENSE_KEY"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null)); //$NON-NLS-1$
			jPanelInputLicense.setBackground(java.awt.SystemColor.WHITE);
			jPanelInputLicense.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
			jPanelInputLicense.add(getJTextFieldLicense(), java.awt.BorderLayout.CENTER);
		}
		return jPanelInputLicense;
	}

	/**
	 * This method initializes jTextFieldLicense
	 *
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextFieldLicense() {
		if (jTextFieldLicense == null) {
			jTextFieldLicense = new JTextField();
			jTextFieldLicense.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
		}
		return jTextFieldLicense;
	}

	/**
	 * This method initializes jEditorPaneExplanation
	 *
	 * @return javax.swing.JEditorPane
	 */
	private JEditorPane getJEditorPaneExplanation() {
		if (jEditorPaneExplanation == null) {
			jEditorPaneExplanation = new JEditorPane();
			jEditorPaneExplanation.setBackground(java.awt.SystemColor.inactiveCaptionText);
			jEditorPaneExplanation.setContentType("text/plain");
			jEditorPaneExplanation.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
			jEditorPaneExplanation.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 11));
			jEditorPaneExplanation.setEditable(false);
			jEditorPaneExplanation.setHighlighter(getDefaultHighlighter());
			jEditorPaneExplanation.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED)));
			}
		return jEditorPaneExplanation;
	}

	/**
	 * This method initializes okCancelPanel
	 *
	 * @return bi.view.utils.CMOkCancelPanel
	 */
	private CMOkCancelPanel getOkCancelPanel() {
		if (okCancelPanel == null) {
			okCancelPanel = new CMOkCancelPanel(this);
		}
		return okCancelPanel;
	}

	/**
	 * @param p_string
	 */
	public void setCode(String p_string) {
		this.code = p_string;
		Object[] arguments = {
				  code
				};
				String result = java.text.MessageFormat.format(CMMessages.getString("INFO_HOW_TO_GET_LICENSE_KEY"), arguments); //$NON-NLS-1$
				getJEditorPaneExplanation().setText(result);
	}

	/**
	 * This method initializes defaultHighlighter	
	 * 	
	 * @return javax.swing.text.DefaultHighlighter	
	 */
	private DefaultHighlighter getDefaultHighlighter() {
		if (defaultHighlighter == null) {
			defaultHighlighter = new DefaultHighlighter();
		}
		return defaultHighlighter;
	}
}  //  @jve:decl-index=0:visual-constraint="10,10"
