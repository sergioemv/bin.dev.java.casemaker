/**
 * 13/12/2006
 * svonborries
 */
package bi.view.utils.testdata;

import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import java.awt.Dimension;
import java.awt.Font;

import java.awt.SystemColor;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import java.awt.GridBagConstraints;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import java.awt.Point;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import bi.view.lang.CMMessages;

import java.awt.BorderLayout;

import java.awt.Insets;

/**
 * @author svonborries
 *
 */
public class CMPanelVariable extends JPanel {

	private static final long serialVersionUID = 1L;
	private JPanel jPanel = null;
	private JPanel jPanelVariables = null;
	private JPanel jPanel1 = null;
	private JPanel jPanelDescriptionVariables = null;
	private JPanel jPanel2 = null;
	private JScrollPane jScrollPaneVariables = null;
	private JScrollPane jScrollPaneDescriotionVariable = null;
	private JList jListVariables = null;
	private JPanel jPanel3 = null;
	private JPanel jPanelValue = null;
	private JLabel jLabelValue = null;
	private JTextField jTextFieldValue = null;
	private JPanel jPanelType = null;
	private JPanel jPanelFormat = null;
	private JPanel jPanelDescription = null;
	private JScrollPane jScrollPanePanelDescription = null;
	private JTextArea jTextAreaDescription = null;
	private JLabel jLabelType = null;
	private JTextField jTextFieldType = null;
	private JLabel jLabelFormat = null;
	private JTextField jTextFieldFormat = null;
	private JPanel jPanel4 = null;
	private JPanel jPanel5 = null;
	private JPanel jPanel6 = null;
	/**
	 * This is the default constructor
	 */
	public CMPanelVariable() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED), CMMessages.getString("TESTDATA_TITLE_DIALOG_ADD_VARIABLES"), TitledBorder.LEADING, TitledBorder.TOP, new Font("SansSerif", Font.BOLD, 10), SystemColor.activeCaption));
		this.setSize(new Dimension(455, 319));
		this.setLocation(new Point(0, 0));
		this.add(getJPanel(), null);
		this.add(getJPanelVariables(), null);
		this.add(getJPanel1(), null);
		this.add(getJPanelDescriptionVariables(), null);
		this.add(getJPanel2(), null);
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new GridBagLayout());
			jPanel.setPreferredSize(new Dimension(20, 20));
		}
		return jPanel;
	}

	/**
	 * This method initializes jPanelVariables	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelVariables() {
		if (jPanelVariables == null) {
			jPanelVariables = new JPanel();
			jPanelVariables.setLayout(new BorderLayout());
			jPanelVariables.setMinimumSize(new Dimension(140, 152));
			jPanelVariables.setPreferredSize(new Dimension(140, 152));
			jPanelVariables.add(getJScrollPaneVariables(), BorderLayout.CENTER);
		}
		return jPanelVariables;
	}

	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(new GridBagLayout());
			jPanel1.setPreferredSize(new Dimension(20, 20));
		}
		return jPanel1;
	}

	/**
	 * This method initializes jPanelDescriptionVariables	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelDescriptionVariables() {
		if (jPanelDescriptionVariables == null) {
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.fill = GridBagConstraints.BOTH;
			gridBagConstraints1.weighty = 1.0;
			gridBagConstraints1.weightx = 1.0;
			jPanelDescriptionVariables = new JPanel();
			jPanelDescriptionVariables.setLayout(new GridBagLayout());
			jPanelDescriptionVariables.setPreferredSize(new Dimension(550, 173));
			jPanelDescriptionVariables.add(getJScrollPaneDescriotionVariable(), gridBagConstraints1);
		}
		return jPanelDescriptionVariables;
	}

	/**
	 * This method initializes jPanel2	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setLayout(new GridBagLayout());
			jPanel2.setPreferredSize(new Dimension(20, 20));
		}
		return jPanel2;
	}

	/**
	 * This method initializes jScrollPaneVariables	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPaneVariables() {
		if (jScrollPaneVariables == null) {
			jScrollPaneVariables = new JScrollPane();
			jScrollPaneVariables.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(
			        new java.awt.Color(153, 153, 153), 1),CMMessages.getString("TESTDATA_AVAILABLES_VARIABLES")
			        , javax.swing.border.TitledBorder.LEADING, javax.swing.border.TitledBorder.TOP,
			        new Font("SansSerif", Font.BOLD, 10), SystemColor.activeCaption));
			//jScrollPaneVariables.setMaximumSize(new Dimension(100, 269));
			//jScrollPaneVariables.setMinimumSize(new Dimension(95, 269));
			jScrollPaneVariables.setViewportView(getJListVariables());
		}
		return jScrollPaneVariables;
	}

	/**
	 * This method initializes jScrollPaneDescriotionVariable	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPaneDescriotionVariable() {
		if (jScrollPaneDescriotionVariable == null) {
			jScrollPaneDescriotionVariable = new JScrollPane();
			jScrollPaneDescriotionVariable.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(
			        new java.awt.Color(153, 153, 153), 1),CMMessages.getString("TESTDATA_DESCRIPTION_TDSTRUCTURE")
			        , javax.swing.border.TitledBorder.LEADING, javax.swing.border.TitledBorder.TOP,
			        new Font("SansSerif", Font.BOLD, 10), SystemColor.activeCaption));
			jScrollPaneDescriotionVariable.setViewportView(getJPanel3());
		}
		return jScrollPaneDescriotionVariable;
	}

	/**
	 * This method initializes jListVariables	
	 * 	
	 * @return javax.swing.JList	
	 */
	public JList getJListVariables() {
		if (jListVariables == null) {
			jListVariables = new JList();
			jListVariables.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			//jListVariables.setMaximumSize(new Dimension(95, 269));
			//jListVariables.setPreferredSize(new Dimension(95, 269));
			//jListVariables.setSize(new Dimension(95, 269));
			//jListVariables.setMinimumSize(new Dimension(95, 269));
		}
		return jListVariables;
	}

	/**
	 * This method initializes jPanel3	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jPanel3 = new JPanel();
			jPanel3.setLayout(new BoxLayout(getJPanel3(), BoxLayout.Y_AXIS));
			jPanel3.add(getJPanelValue(), null);
			jPanel3.add(getJPanel6(), null);
			jPanel3.add(getJPanelType(), null);
			jPanel3.add(getJPanel4(), null);
			jPanel3.add(getJPanelFormat(), null);
			jPanel3.add(getJPanel5(), null);
			jPanel3.add(getJPanelDescription(), null);
		}
		return jPanel3;
	}

	/**
	 * This method initializes jPanelValue	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelValue() {
		if (jPanelValue == null) {
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints2.gridx = 1;
			gridBagConstraints2.gridy = 0;
			gridBagConstraints2.weightx = 1.0;
			gridBagConstraints2.insets = new Insets(5, 0, 5, 0);
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.insets = new Insets(8, 5, 8, 25);
			gridBagConstraints.gridy = 0;
			gridBagConstraints.anchor = GridBagConstraints.WEST;
			gridBagConstraints.gridx = 0;
			jLabelValue = new JLabel();
			jLabelValue.setText(CMMessages.getString("TESTDATA_VALUE"));
			jPanelValue = new JPanel();
			jPanelValue.setLayout(new GridBagLayout());
			jPanelValue.setPreferredSize(new Dimension(231, 20));
			jPanelValue.add(jLabelValue, gridBagConstraints);
			jPanelValue.add(getJTextFieldValue(), gridBagConstraints2);
		}
		return jPanelValue;
	}

	/**
	 * This method initializes jTextFieldValue	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	public JTextField getJTextFieldValue() {
		if (jTextFieldValue == null) {
			jTextFieldValue = new JTextField();
			jTextFieldValue.setPreferredSize(new Dimension(177, 20));
			jTextFieldValue.setMinimumSize(new Dimension(177, 20));
			jTextFieldValue.setBackground(SystemColor.activeCaptionBorder);
			jTextFieldValue.setEditable(false);
			jTextFieldValue.setMaximumSize(new Dimension(177, 2147483647));
		}
		return jTextFieldValue;
	}

	/**
	 * This method initializes jPanelType	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelType() {
		if (jPanelType == null) {
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints4.gridy = 0;
			gridBagConstraints4.ipady = 1;
			gridBagConstraints4.weightx = 1.0;
			gridBagConstraints4.gridx = 1;
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.insets = new Insets(2, 5, 5, 25);
			gridBagConstraints3.gridy = 0;
			gridBagConstraints3.gridx = 0;
			jLabelType = new JLabel();
			jLabelType.setText(CMMessages.getString("TESTDATA_TYPE"));
			jPanelType = new JPanel();
			jPanelType.setLayout(new GridBagLayout());
			jPanelType.add(jLabelType, gridBagConstraints3);
			jPanelType.add(getJTextFieldType(), gridBagConstraints4);
		}
		return jPanelType;
	}

	/**
	 * This method initializes jPanelFormat	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelFormat() {
		if (jPanelFormat == null) {
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints6.gridy = 0;
			gridBagConstraints6.ipady = 1;
			gridBagConstraints6.weightx = 1.0;
			gridBagConstraints6.gridx = 1;
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.insets = new Insets(2, 5, 5, 15);
			gridBagConstraints5.gridy = 0;
			gridBagConstraints5.gridx = 0;
			jLabelFormat = new JLabel();
			jLabelFormat.setText(CMMessages.getString("TESTDATA_FORMAT"));
			jPanelFormat = new JPanel();
			jPanelFormat.setLayout(new GridBagLayout());
			jPanelFormat.add(jLabelFormat, gridBagConstraints5);
			jPanelFormat.add(getJTextFieldFormat(), gridBagConstraints6);
		}
		return jPanelFormat;
	}

	/**
	 * This method initializes jPanelDescription	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelDescription() {
		if (jPanelDescription == null) {
			jPanelDescription = new JPanel();
			jPanelDescription.setLayout(new BorderLayout());
			jPanelDescription.add(getJScrollPanePanelDescription(), BorderLayout.CENTER);
		}
		return jPanelDescription;
	}

	/**
	 * This method initializes jScrollPanePanelDescription	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPanePanelDescription() {
		if (jScrollPanePanelDescription == null) {
			jScrollPanePanelDescription = new JScrollPane();
			jScrollPanePanelDescription.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(
			        new java.awt.Color(153, 153, 153), 1),
			        CMMessages.getString("TESTDATA_DESCRIPTION_TDSTRUCTURE"), javax.swing.border.TitledBorder.LEADING, javax.swing.border.TitledBorder.TOP, //$NON-NLS-1$
			        new Font("SansSerif", Font.BOLD, 10), SystemColor.activeCaption));
			jScrollPanePanelDescription.setPreferredSize(new Dimension(255, 146));
			jScrollPanePanelDescription.setViewportView(getJTextAreaDescription());
		}
		return jScrollPanePanelDescription;
	}

	/**
	 * This method initializes jTextAreaDescription	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	public JTextArea getJTextAreaDescription() {
		if (jTextAreaDescription == null) {
			jTextAreaDescription = new JTextArea();
			jTextAreaDescription.setBackground(this.getBackground());
			jTextAreaDescription.setLineWrap(true);
			jTextAreaDescription.setWrapStyleWord(true);
			jTextAreaDescription.setPreferredSize(new Dimension(275, 122));
			jTextAreaDescription.setSize(new Dimension(205, 122));
			jTextAreaDescription.setEditable(false);
			jTextAreaDescription.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		}
		return jTextAreaDescription;
	}

	/**
	 * This method initializes jTextFieldType	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	public JTextField getJTextFieldType() {
		if (jTextFieldType == null) {
			jTextFieldType = new JTextField();
			jTextFieldType.setPreferredSize(new Dimension(177, 20));
			jTextFieldType.setEditable(false);
			jTextFieldType.setBackground(SystemColor.activeCaptionBorder);
			jTextFieldType.setMaximumSize(new Dimension(177, 2147483647));
		}
		return jTextFieldType;
	}

	/**
	 * This method initializes jTextFieldFormat	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	public JTextField getJTextFieldFormat() {
		if (jTextFieldFormat == null) {
			jTextFieldFormat = new JTextField();
			jTextFieldFormat.setPreferredSize(new Dimension(177, 20));
			jTextFieldFormat.setBackground(SystemColor.activeCaptionBorder);
			jTextFieldFormat.setEditable(false);
			jTextFieldFormat.setMaximumSize(new Dimension(177, 2147483647));
		}
		return jTextFieldFormat;
	}

	/**
	 * This method initializes jPanel4	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel4() {
		if (jPanel4 == null) {
			jPanel4 = new JPanel();
			jPanel4.setLayout(new GridBagLayout());
			jPanel4.setPreferredSize(new Dimension(15, 15));
		}
		return jPanel4;
	}

	/**
	 * This method initializes jPanel5	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel5() {
		if (jPanel5 == null) {
			jPanel5 = new JPanel();
			jPanel5.setLayout(new GridBagLayout());
			jPanel5.setPreferredSize(new Dimension(15, 15));
		}
		return jPanel5;
	}

	/**
	 * This method initializes jPanel6	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel6() {
		if (jPanel6 == null) {
			jPanel6 = new JPanel();
			jPanel6.setLayout(new GridBagLayout());
			jPanel6.setPreferredSize(new Dimension(15, 15));
		}
		return jPanel6;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
