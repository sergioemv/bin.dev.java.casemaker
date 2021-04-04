package bi.view.actions.file.rename;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;

import bi.view.lang.CMMessages;
import bi.view.utils.CMBaseJDialog;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Component;

import bi.view.utils.CMOkCancelPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.FlowLayout;

/**
 * Simple dialog to catch a new name entrance
 * @author smoreno
 * 
 */
public class CMDialogNameChange extends CMBaseJDialog {

	private JPanel jPanelMainContainer = null;
	private CMOkCancelPanel okCancelPanel = null;
	private JPanel jPanelText = null;
	private JLabel JLabelNewName = null;
	private JTextField jTextFieldName = null;

	/**
	 * This method initializes the dialog
	 *
	 */
	public CMDialogNameChange() {
		super();
		initialize();
		addKeyListenertoAll(this,this.getDefaultKeyListener());
	}

	/**
	 * This method initializes this
	 *
	 */
	private void initialize() {
        this.setSize(new java.awt.Dimension(344,109));
        this.setModal(true);
        this.setTitle(CMMessages.getString("RENAME_DIALOG_TITLE"));
        this.setContentPane(getJPanelMainContainer());
        this.setResizable(false);

	}

	@Override
	protected void fireButtonCancel() {
		getOkCancelPanel().getJButtonCancel().doClick();

	}

	@Override
	protected void fireButtonOk() {
		getOkCancelPanel().getJButtonOk().doClick();

	}

	@Override
	public JButton getDefaultButton() {

		return getOkCancelPanel().getJButtonOk();
	}

	@Override
	protected List getOrder() {
		ArrayList<Component> list = new ArrayList<Component>();
		list.add(getJTextFieldName());
		list.add(getOkCancelPanel().getJButtonOk());
		list.add(getOkCancelPanel().getJButtonCancel());
		return list;
	}

	/**
	 * This method initializes jPanelMainContainer
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelMainContainer() {
		if (jPanelMainContainer == null) {
			jPanelMainContainer = new JPanel();
			jPanelMainContainer.setLayout(new BorderLayout());
			jPanelMainContainer.add(getOkCancelPanel(), java.awt.BorderLayout.SOUTH);
			jPanelMainContainer.add(getJPanelText(), java.awt.BorderLayout.CENTER);
		}
		return jPanelMainContainer;
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
	 * This method initializes jPanelText
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelText() {
		if (jPanelText == null) {
			FlowLayout flowLayout = new FlowLayout();
			flowLayout.setVgap(10);
			JLabelNewName = new JLabel();
			JLabelNewName.setText(CMMessages.getString("RENAME_DIALOG_PROMPT")); //$NON-NLS-1$
			jPanelText = new JPanel();
			jPanelText.setLayout(flowLayout);
			jPanelText.add(JLabelNewName, null);
			jPanelText.add(getJTextFieldName(), null);
		}
		return jPanelText;
	}

	/**
	 * This method initializes jTextFieldName
	 *
	 * @return javax.swing.JTextField
	 */
	public JTextField getJTextFieldName() {
		if (jTextFieldName == null) {
			jTextFieldName = new JTextField();
			jTextFieldName.setPreferredSize(new java.awt.Dimension(230,19));
		}
		return jTextFieldName;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
