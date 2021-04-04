/**
 * 09/02/2007
 * svonborries
 */
package bi.view.utils.testdata;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.SystemColor;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextArea;

import bi.view.lang.CMMessages;

import model.BusinessRules;


/**
 * @author svonborries
 *
 */
public class CMPanelAssignFormat extends JPanel {

	private static final long serialVersionUID = 1L;
	private JPanel jPanelFormats = null;
	private JPanel jPanelDescription = null;
	private JPanel jPanel = null;
	private JPanel jPanelCategory = null;
	private JPanel jPanel1 = null;
	private JPanel jPanelParameters = null;
	private JPanel jPanel2 = null;
	private JScrollPane jScrollPaneCategory = null;
	private JScrollPane jScrollPaneParameter = null;
	private JList jListCategory = null;
	private JPanel jPanelParameterContainer = null;
	private JPanel jPanelExample = null;
	private JPanel jPanelDecimal = null;
	private JPanel jPanel3 = null;
	private JPanel jPanel4 = null;
	private JPanel jPanelSymbol = null;
	private JPanel jPanel5 = null;
	private JPanel jPanelFormat = null;
	private JTextField jTextFieldExample = null;
	private JLabel jLabelDecimal = null;
	private JSpinner jSpinnerDecimal = null;
	private JLabel jLabelSymbol = null;
	private JComboBox jComboBoxSymbol = null;
	private JPanel jPanel6 = null;
	private JPanel jPanel7 = null;
	private JScrollPane jScrollPaneFormat = null;
	private JList jListFormat = null;
	private JPanel jPanel8 = null;
	private JPanel jPanel9 = null;
	private JPanel jPanel10 = null;
	private JPanel jPanel11 = null;
	private JPanel jPanel12 = null;
	private JPanel jPanel13 = null;
	private Integer value = new Integer(2);
    private Integer min = new Integer(0);
    private Integer max = new Integer(15);
    private Integer step = new Integer(1);
    private SpinnerNumberModel model = new SpinnerNumberModel(value, min, max, step);
	private JTextArea jTextAreaDescription = null;
	/**
	 * This is the default constructor
	 */
	public CMPanelAssignFormat() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(386, 400);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED), CMMessages.getString("TESTDATA_ASSIGN_FORMAT_DIALOG_PANELTITLE"), TitledBorder.LEADING, TitledBorder.TOP, new Font("SansSerif", Font.BOLD, 10), SystemColor.activeCaption));
		this.add(getJPanelFormats(), null);
		this.add(getJPanelDescription(), null);
	}

	/**
	 * This method initializes jPanelFormats	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelFormats() {
		if (jPanelFormats == null) {
			jPanelFormats = new JPanel();
			jPanelFormats.setLayout(new BoxLayout(getJPanelFormats(), BoxLayout.X_AXIS));
			jPanelFormats.add(getJPanel(), null);
			jPanelFormats.add(getJPanelCategory(), null);
			jPanelFormats.add(getJPanel1(), null);
			jPanelFormats.add(getJPanelParameters(), null);
			jPanelFormats.add(getJPanel2(), null);
		}
		return jPanelFormats;
	}

	/**
	 * This method initializes jPanelDescription	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelDescription() {
		if (jPanelDescription == null) {
			jPanelDescription = new JPanel();
			jPanelDescription.setLayout(new BoxLayout(getJPanelDescription(), BoxLayout.Y_AXIS));
			jPanelDescription.setMaximumSize(new Dimension(374, 55));
			jPanelDescription.setMinimumSize(new Dimension(374, 55));
			jPanelDescription.setPreferredSize(new Dimension(374, 50));
			jPanelDescription.add(getJPanel8(), null);
			jPanelDescription.add(getJPanel9(), null);
			jPanelDescription.add(getJPanel10(), null);
		}
		return jPanelDescription;
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
			jPanel.setPreferredSize(new Dimension(15, 10));
			jPanel.setMaximumSize(new Dimension(15, 2147483647));
		}
		return jPanel;
	}

	/**
	 * This method initializes jPanelCategory	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelCategory() {
		if (jPanelCategory == null) {
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.fill = GridBagConstraints.BOTH;
			gridBagConstraints.weighty = 1.0;
			gridBagConstraints.weightx = 1.0;
			jPanelCategory = new JPanel();
			jPanelCategory.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED), CMMessages.getString("TESTDATA_FORMAT_CATEGORY"), TitledBorder.LEADING, TitledBorder.TOP, new Font("SansSerif", Font.BOLD, 10), SystemColor.activeCaption));
			jPanelCategory.setLayout(new GridBagLayout());
			jPanelCategory.setPreferredSize(new Dimension(160, 315));
			jPanelCategory.setMaximumSize(new Dimension(160, 315));
			jPanelCategory.setMinimumSize(new Dimension(160, 315));
			jPanelCategory.add(getJScrollPaneCategory(), gridBagConstraints);
		}
		return jPanelCategory;
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
			jPanel1.setPreferredSize(new Dimension(15, 10));
			jPanel1.setMaximumSize(new Dimension(15, 2147483647));
		}
		return jPanel1;
	}

	/**
	 * This method initializes jPanelParameters	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelParameters() {
		if (jPanelParameters == null) {
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.fill = GridBagConstraints.BOTH;
			gridBagConstraints1.weighty = 1.0;
			gridBagConstraints1.weightx = 1.0;
			jPanelParameters = new JPanel();
			jPanelParameters.setLayout(new GridBagLayout());
			jPanelParameters.setMinimumSize(new Dimension(205, 315));
			jPanelParameters.setMaximumSize(new Dimension(205, 315));
			jPanelParameters.setPreferredSize(new Dimension(205, 315));
			jPanelParameters.add(getJScrollPaneParameter(), gridBagConstraints1);
		}
		return jPanelParameters;
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
			jPanel2.setPreferredSize(new Dimension(15, 10));
			jPanel2.setMaximumSize(new Dimension(15, 2147483647));
		}
		return jPanel2;
	}

	/**
	 * This method initializes jScrollPaneCategory	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPaneCategory() {
		if (jScrollPaneCategory == null) {
			jScrollPaneCategory = new JScrollPane();
			//jScrollPaneCategory.setMaximumSize(new Dimension(160, 0));
			//jScrollPaneCategory.setMinimumSize(new Dimension(160,65));
			//jScrollPaneCategory.setPreferredSize(new Dimension(160, 65));
			jScrollPaneCategory.setViewportView(getJListCategory());
		}
		return jScrollPaneCategory;
	}

	/**
	 * This method initializes jScrollPaneParameter	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPaneParameter() {
		if (jScrollPaneParameter == null) {
			jScrollPaneParameter = new JScrollPane();
			jScrollPaneParameter.setViewportView(getJPanelParameterContainer());
		}
		return jScrollPaneParameter;
	}

	/**
	 * This method initializes jListCategory	
	 * 	
	 * @return javax.swing.JList	
	 */
	public JList getJListCategory() {
		if (jListCategory == null) {
			jListCategory = new JList();
			//jListCategory.setSize(new Dimension(100, 180));
			//jListCategory.setMaximumSize(new Dimension(100, 180));
			//jListCategory.setPreferredSize(new Dimension(100, 180));
		}
		return jListCategory;
	}

	/**
	 * This method initializes jPanelParameterContainer	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelParameterContainer() {
		if (jPanelParameterContainer == null) {
			jPanelParameterContainer = new JPanel();
			jPanelParameterContainer.setLayout(new BoxLayout(getJPanelParameterContainer(), BoxLayout.Y_AXIS));
			jPanelParameterContainer.add(getJPanelExample(), null);
			jPanelParameterContainer.add(getJPanel3(), null);
			jPanelParameterContainer.add(getJPanelDecimal(), null);
			jPanelParameterContainer.add(getJPanel4(), null);
			jPanelParameterContainer.add(getJPanelSymbol(), null);
			jPanelParameterContainer.add(getJPanel5(), null);
			jPanelParameterContainer.add(getJPanelFormat(), null);
		}
		return jPanelParameterContainer;
	}

	/**
	 * This method initializes jPanelExample	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelExample() {
		if (jPanelExample == null) {
			jPanelExample = new JPanel();
			jPanelExample.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED), CMMessages.getString("TESTDATA_FORMAT_EXAMPLE"), TitledBorder.LEADING, TitledBorder.TOP, new Font("SansSerif", Font.BOLD, 10), SystemColor.activeCaption));
			jPanelExample.setLayout(new BorderLayout());
			jPanelExample.setPreferredSize(new Dimension(90, 48));
			jPanelExample.setMinimumSize(new Dimension(90, 7));
			jPanelExample.setMaximumSize(new Dimension(200, 48));
			jPanelExample.add(getJTextFieldExample(), BorderLayout.CENTER);
		}
		return jPanelExample;
	}

	/**
	 * This method initializes jPanelDecimal	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelDecimal() {
		if (jPanelDecimal == null) {
			jLabelDecimal = new JLabel();
			jLabelDecimal.setText(CMMessages.getString("TESTDATA_FORMAT_POS_DECIMAL"));
			jPanelDecimal = new JPanel();
			jPanelDecimal.setLayout(new BoxLayout(getJPanelDecimal(), BoxLayout.X_AXIS));
			jPanelDecimal.setMinimumSize(new Dimension(200, 18));
			jPanelDecimal.setMaximumSize(new Dimension(200, 18));
			jPanelDecimal.setPreferredSize(new Dimension(200, 18));
			jPanelDecimal.add(jLabelDecimal, null);
			jPanelDecimal.add(getJPanel6(), null);
			jPanelDecimal.add(getJSpinnerDecimal(), null);
		}
		return jPanelDecimal;
	}
	
	public JLabel getJLabelDecimal(){
		return jLabelDecimal;
	}

	/**
	 * This method initializes jPanel3	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jPanel3 = new JPanel();
			jPanel3.setLayout(new GridBagLayout());
			jPanel3.setMaximumSize(new Dimension(2147483647, 15));
			jPanel3.setMinimumSize(new Dimension(10, 15));
			jPanel3.setPreferredSize(new Dimension(10, 15));
		}
		return jPanel3;
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
			jPanel4.setMaximumSize(new Dimension(10, 15));
			jPanel4.setMinimumSize(new Dimension(10, 15));
			jPanel4.setPreferredSize(new Dimension(10, 15));
		}
		return jPanel4;
	}

	/**
	 * This method initializes jPanelSymbol	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelSymbol() {
		if (jPanelSymbol == null) {
			jLabelSymbol = new JLabel();
			jLabelSymbol.setText(CMMessages.getString("TESTDATA_FORMAT_SIMBOL"));
			jPanelSymbol = new JPanel();
			jPanelSymbol.setLayout(new BoxLayout(getJPanelSymbol(), BoxLayout.X_AXIS));
			jPanelSymbol.setPreferredSize(new Dimension(200, 22));
			jPanelSymbol.setMinimumSize(new Dimension(200, 22));
			jPanelSymbol.setMaximumSize(new Dimension(200, 22));
			jPanelSymbol.add(jLabelSymbol, null);
			jPanelSymbol.add(getJPanel7(), null);
			jPanelSymbol.add(getJComboBoxSymbol(), null);
		}
		return jPanelSymbol;
	}
	
	public JLabel getJLabelSymbol(){
		return jLabelSymbol;
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
			jPanel5.setPreferredSize(new Dimension(10, 15));
			jPanel5.setMinimumSize(new Dimension(10, 15));
			jPanel5.setMaximumSize(new Dimension(10, 15));
		}
		return jPanel5;
	}

	/**
	 * This method initializes jPanelFormat	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	public JPanel getJPanelFormat() {
		if (jPanelFormat == null) {
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.fill = GridBagConstraints.BOTH;
			gridBagConstraints2.weighty = 1.0;
			gridBagConstraints2.weightx = 1.0;
			jPanelFormat = new JPanel();
			jPanelFormat.setLayout(new GridBagLayout());
			jPanelFormat.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED), CMMessages.getString("TESTDATA_FORMAT_FORMAT"), TitledBorder.LEADING, TitledBorder.TOP, new Font("SansSerif", Font.BOLD, 10), SystemColor.activeCaption));
			jPanelFormat.setPreferredSize(new Dimension(200, 180));
			jPanelFormat.setMinimumSize(new Dimension(200, 180));
			jPanelFormat.setMaximumSize(new Dimension(200, 180));
			jPanelFormat.add(getJScrollPaneFormat(), gridBagConstraints2);
		}
		return jPanelFormat;
	}

	/**
	 * This method initializes jTextFieldExample	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	public JTextField getJTextFieldExample() {
		if (jTextFieldExample == null) {
			jTextFieldExample = new JTextField();
		}
		return jTextFieldExample;
	}

	/**
	 * This method initializes jComboBoxDecimal	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	public JSpinner getJSpinnerDecimal() {
		if (jSpinnerDecimal == null) {
			jSpinnerDecimal = new JSpinner(model);
		}
		return jSpinnerDecimal;
	}

	/**
	 * This method initializes jComboBoxSymbol	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	public JComboBox getJComboBoxSymbol() {
		if (jComboBoxSymbol == null) {
			jComboBoxSymbol = new JComboBox(BusinessRules.TESTDATA_FORMAT_MONEYSIGN);
		}
		return jComboBoxSymbol;
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
			jPanel6.setPreferredSize(new Dimension(10, 10));
			jPanel6.setMaximumSize(new Dimension(10, 10));
		}
		return jPanel6;
	}

	/**
	 * This method initializes jPanel7	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel7() {
		if (jPanel7 == null) {
			jPanel7 = new JPanel();
			jPanel7.setLayout(new GridBagLayout());
			jPanel7.setPreferredSize(new Dimension(10, 10));
			jPanel7.setMaximumSize(new Dimension(10, 10));
		}
		return jPanel7;
	}

	/**
	 * This method initializes jScrollPaneFormat	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPaneFormat() {
		if (jScrollPaneFormat == null) {
			jScrollPaneFormat = new JScrollPane();
			//jScrollPaneFormat.setPreferredSize(new Dimension(50, 50));
			jScrollPaneFormat.setViewportView(getJListFormat());
		}
		return jScrollPaneFormat;
	}

	/**
	 * This method initializes jListFormat	
	 * 	
	 * @return javax.swing.JList	
	 */
	public JList getJListFormat() {
		if (jListFormat == null) {
			jListFormat = new JList();
			//jListFormat.setSize(new Dimension(50, 50));
			//jListFormat.setMaximumSize(new Dimension(50, 50));
			//jListFormat.setMinimumSize(new Dimension(50, 50));
			//jListFormat.setPreferredSize(new Dimension(50, 50));
			jListFormat.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		}
		return jListFormat;
	}

	/**
	 * This method initializes jPanel8	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel8() {
		if (jPanel8 == null) {
			jPanel8 = new JPanel();
			jPanel8.setLayout(new GridBagLayout());
			jPanel8.setPreferredSize(new Dimension(10, 10));
			jPanel8.setMaximumSize(new Dimension(10, 10));
			jPanel8.setMinimumSize(new Dimension(10, 10));
		}
		return jPanel8;
	}

	/**
	 * This method initializes jPanel9	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel9() {
		if (jPanel9 == null) {
			jPanel9 = new JPanel();
			jPanel9.setLayout(new BoxLayout(getJPanel9(), BoxLayout.X_AXIS));
			jPanel9.add(getJPanel11(), null);
			jPanel9.add(getJPanel12(), null);
			jPanel9.add(getJPanel13(), null);
		}
		return jPanel9;
	}

	/**
	 * This method initializes jPanel10	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel10() {
		if (jPanel10 == null) {
			jPanel10 = new JPanel();
			jPanel10.setLayout(new GridBagLayout());
			jPanel10.setMinimumSize(new Dimension(10, 10));
			jPanel10.setPreferredSize(new Dimension(10, 10));
			jPanel10.setMaximumSize(new Dimension(10, 10));
		}
		return jPanel10;
	}

	/**
	 * This method initializes jPanel11	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel11() {
		if (jPanel11 == null) {
			jPanel11 = new JPanel();
			jPanel11.setLayout(new GridBagLayout());
			jPanel11.setMaximumSize(new Dimension(15, 15));
			jPanel11.setPreferredSize(new Dimension(15, 15));
			jPanel11.setMinimumSize(new Dimension(15, 15));
		}
		return jPanel11;
	}

	/**
	 * This method initializes jPanel12	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel12() {
		if (jPanel12 == null) {
			jPanel12 = new JPanel();
			jPanel12.setLayout(new BorderLayout());
			jPanel12.add(getJTextAreaDescription(), BorderLayout.CENTER);
		}
		return jPanel12;
	}

	/**
	 * This method initializes jPanel13	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel13() {
		if (jPanel13 == null) {
			jPanel13 = new JPanel();
			jPanel13.setLayout(new GridBagLayout());
			jPanel13.setPreferredSize(new Dimension(15, 15));
			jPanel13.setMaximumSize(new Dimension(15, 15));
			jPanel13.setMinimumSize(new Dimension(15, 15));
		}
		return jPanel13;
	}

	/**
	 * This method initializes jTextAreaDescription	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	public JTextArea getJTextAreaDescription() {
		if (jTextAreaDescription == null) {
			jTextAreaDescription = new JTextArea();
			jTextAreaDescription.setCaretColor(new Color(212, 208, 200));
			jTextAreaDescription.setBackground(this.getBackground());
			jTextAreaDescription.setLineWrap(true);
			jTextAreaDescription.setWrapStyleWord(true);
			jTextAreaDescription.setEditable(false);
			jTextAreaDescription.setFont(new Font("SansSerif", Font.PLAIN, 12));
			jTextAreaDescription.setDisabledTextColor(new Color(212, 208, 200));
			
		}
		return jTextAreaDescription;
	}
	
	public List getTabOrder(){
		List<JComponent> focusOrder= new ArrayList<JComponent>();
		focusOrder.add(jListCategory);
		if(getJSpinnerDecimal().isVisible() == true)
			focusOrder.add(jSpinnerDecimal);
		if(getJComboBoxSymbol().isVisible() == true)
			focusOrder.add(jComboBoxSymbol);
		if(getJListFormat().isVisible() == true)
			focusOrder.add(jListFormat);
		return focusOrder;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
