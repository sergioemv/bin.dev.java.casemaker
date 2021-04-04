package bi.view.businessrulesviews.brimport.wizardpane;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;

import bi.view.icons.CMIcon;
import bi.view.lang.CMMessages;

public class CMImportWarningPane extends JPanel {

	private JScrollPane jWarningScrollPane = null;
	private JTextArea jWarningTextArea = null;
	private JPanel jMessagePanel = null;
	private JTextArea jMessageTextArea = null;
	private JPanel jIconPanel = null;
	private JLabel jIconLabel = null;
	private JPanel jSpacePanel = null;
	private JPanel jSpacePanel1 = null;
	private JPanel jSpacePanel2 = null;
	private JPanel jSpacePanel3 = null;
	private JPanel jSpacePanel4 = null;
	private JPanel jSpacePanel5 = null;

	/**
	 * This method initializes 
	 * 
	 */
	public CMImportWarningPane() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		BorderLayout borderLayout = new BorderLayout();
		borderLayout.setHgap(0);
		borderLayout.setVgap(5);
		setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		setLayout(borderLayout);
		add(getJScrollPane(), java.awt.BorderLayout.CENTER);
		add(getJPanel1(), java.awt.BorderLayout.NORTH);
		add(getJPanel7(), java.awt.BorderLayout.WEST);
		add(getJPanel8(), java.awt.BorderLayout.EAST);	
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */

	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane() {
		if (jWarningScrollPane == null) {
			jWarningScrollPane = new JScrollPane();
			jWarningScrollPane.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
			jWarningScrollPane.setViewportView(getJTextArea());
		}
		return jWarningScrollPane;
	}

	/**
	 * This method initializes jTextArea	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getJTextArea() {
		if (jWarningTextArea == null) {
			jWarningTextArea = new JTextArea();
			jWarningTextArea.setBounds(new Rectangle(28, 224, 418, 83));
			jWarningTextArea.setEnabled(false);
			jWarningTextArea.setFont(new Font("SansSerif", Font.PLAIN, 12)); //$NON-NLS-1$
			jWarningTextArea.setForeground(this.getForeground());
			jWarningTextArea.setCaretColor(new Color(0, 0, 0));
			jWarningTextArea.setDisabledTextColor(new Color(0, 0, 0));
			jWarningTextArea.setEditable(false);
			jWarningTextArea.setBackground(this.getBackground());
		}
		return jWarningTextArea;
	}

	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel1() {
		if (jMessagePanel == null) {
			BorderLayout borderLayout1 = new BorderLayout();
			borderLayout1.setHgap(0);
			borderLayout1.setVgap(10);
			jMessagePanel = new JPanel();
			jMessagePanel.setPreferredSize(new Dimension(422, 110));
			jMessagePanel.setLayout(borderLayout1);
			jMessagePanel.add(getJTextArea1(), java.awt.BorderLayout.CENTER);
			jMessagePanel.add(getJPanel2(), java.awt.BorderLayout.WEST);
			jMessagePanel.add(getJPanel4(), java.awt.BorderLayout.NORTH);
			jMessagePanel.add(getJPanel5(), java.awt.BorderLayout.SOUTH);
			jMessagePanel.add(getJPanel6(), java.awt.BorderLayout.EAST);
		}
		return jMessagePanel;
	}

	/**
	 * This method initializes jTextArea1	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getJTextArea1() {
		if (jMessageTextArea == null) {
			jMessageTextArea = new JTextArea();
			jMessageTextArea.setBounds(new Rectangle(28, 224, 418, 83));
			jMessageTextArea.setEnabled(false);
			jMessageTextArea.setFont(new Font("SansSerif", Font.PLAIN, 12)); //$NON-NLS-1$
			jMessageTextArea.setForeground(this.getForeground());
			jMessageTextArea.setPreferredSize(new Dimension(350, 15));
			jMessageTextArea.setText(CMMessages.getString("BUSINESS_RULES_IMPORT_MESSAGE_PROBLEMS")); //$NON-NLS-1$
			jMessageTextArea.setCaretColor(new Color(0, 0, 0));
			jMessageTextArea.setDisabledTextColor(new Color(0, 0, 0));
			jMessageTextArea.setEditable(false);
			jMessageTextArea.setLineWrap(true);
			jMessageTextArea.setWrapStyleWord(true);
			jMessageTextArea.setBackground(this.getBackground());
		}
		return jMessageTextArea;
	}

	/**
	 * This method initializes jPanel2	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel2() {
		if (jIconPanel == null) {
			jIconLabel = new JLabel();
			jIconLabel.setPreferredSize(new Dimension(48, 48));
			jIconLabel.setText(""); //$NON-NLS-1$
			jIconLabel.setIcon(CMIcon.WARNING.getImageIcon()); //$NON-NLS-1$
			jIconPanel = new JPanel();
			jIconPanel.setLayout(new BorderLayout());
			jIconPanel.setPreferredSize(new Dimension(60, 48));
			jIconPanel.add(jIconLabel, java.awt.BorderLayout.CENTER);
			jIconPanel.add(getJPanel3(), java.awt.BorderLayout.WEST);
		}
		return jIconPanel;
	}

	/**
	 * This method initializes jPanel3	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel3() {
		if (jSpacePanel == null) {
			jSpacePanel = new JPanel();
			jSpacePanel.setPreferredSize(new Dimension(12, 0));
		}
		return jSpacePanel;
	}

	/**
	 * This method initializes jPanel4	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel4() {
		if (jSpacePanel1 == null) {
			jSpacePanel1 = new JPanel();
		}
		return jSpacePanel1;
	}

	/**
	 * This method initializes jPanel5	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel5() {
		if (jSpacePanel2 == null) {
			jSpacePanel2 = new JPanel();
		}
		return jSpacePanel2;
	}

	/**
	 * This method initializes jPanel6	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel6() {
		if (jSpacePanel3 == null) {
			jSpacePanel3 = new JPanel();
			jSpacePanel3.setPreferredSize(new Dimension(12, 0));
		}
		return jSpacePanel3;
	}

	/**
	 * This method initializes jPanel7	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel7() {
		if (jSpacePanel4 == null) {
			FlowLayout flowLayout = new FlowLayout();
			flowLayout.setHgap(0);
			flowLayout.setVgap(0);
			jSpacePanel4 = new JPanel();
			jSpacePanel4.setPreferredSize(new Dimension(10, 0));
			jSpacePanel4.setLayout(flowLayout);
		}
		return jSpacePanel4;
	}

	/**
	 * This method initializes jPanel8	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel8() {
		if (jSpacePanel5 == null) {
			FlowLayout flowLayout1 = new FlowLayout();
			flowLayout1.setHgap(0);
			flowLayout1.setVgap(0);
			jSpacePanel5 = new JPanel();
			jSpacePanel5.setPreferredSize(new Dimension(10, 0));
			jSpacePanel5.setLayout(flowLayout1);
		}
		return jSpacePanel5;
	}

	public void setLog(String log) {
		jWarningTextArea.setText(log);
	}

	public List getOrder() {
		List focusOrder= new ArrayList();
		return focusOrder;
	}
}
