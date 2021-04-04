package bi.view.utils.testdata;

import java.awt.GridBagLayout;
import javax.swing.JPanel;
import java.awt.GridBagConstraints;
import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.SwingConstants;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;
import java.awt.SystemColor;
import javax.swing.ListSelectionModel;
import javax.swing.JSplitPane;

import bi.view.lang.CMMessages;

import java.awt.Cursor;
import java.util.ArrayList;
import java.util.List;

public class CMPanelFormulasSelect extends JPanel {

	private static final long serialVersionUID = 1L;
	private JPanel panelJLists = null;
	private JPanel panelCategories = null;
	private JScrollPane scrollCategories = null;
	private JList listCategories = null;
	private JPanel panelFormulas = null;
	private JScrollPane scrollFormulas = null;
	private JList listFormulas = null;
	private JPanel panelInfo = null;
	private JLabel labelCanonicalName = null;
	private JPanel jPanel = null;
	private JPanel jPanel1 = null;
	private JPanel jPanel2 = null;
	private JPanel jPanel4 = null;
	private JTextArea jTextAreaDescription = null;
	private JSplitPane jSplitPane = null;
	private JSplitPane jSplitPane1 = null;
	private JSplitPane jSplitPane2 = null;
	private JPanel jPanelPreview = null;
	private JLabel jLabelPreview = null;
	private JTextArea jTextAreaPreview = null;
	/**
	 * This is the default constructor
	 */
	public CMPanelFormulasSelect() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(629, 270);
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(629, 270));
		this.setMinimumSize(new Dimension(629, 270));
		this.setMaximumSize(new Dimension(629, 270));
		this.add(getJSplitPane2(), BorderLayout.CENTER);
	}

	/**
	 * This method initializes panelJLists	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPanelJLists() {
		if (panelJLists == null) {
			panelJLists = new JPanel();
			panelJLists.setLayout(new BoxLayout(getPanelJLists(), BoxLayout.X_AXIS));
			panelJLists.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
			panelJLists.setPreferredSize(new Dimension(568, 162));
			panelJLists.setMinimumSize(new Dimension(568, 162));
			panelJLists.setMaximumSize(new Dimension(568, 162));
			panelJLists.add(getJPanel(), null);
			panelJLists.add(getPanelCategories(), null);
			panelJLists.add(getJPanel1(), null);
			panelJLists.add(getPanelFormulas(), null);
			panelJLists.add(getJPanel2(), null);
		}
		return panelJLists;
	}

	/**
	 * This method initializes panelCategories	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPanelCategories() {
		if (panelCategories == null) {
			panelCategories = new JPanel();
			panelCategories.setLayout(new BorderLayout());
			panelCategories.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED), CMMessages.getString("TESTDATA_CATEGORY_FUNCTION"), TitledBorder.LEADING, TitledBorder.TOP, new Font("SansSerif", Font.BOLD, 10), SystemColor.activeCaption));
			panelCategories.setPreferredSize(new Dimension(272, 158));
			panelCategories.setMinimumSize(new Dimension(272, 158));
			panelCategories.add(getScrollCategories(), BorderLayout.CENTER);
		}
		return panelCategories;
	}

	/**
	 * This method initializes scrollCategories	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getScrollCategories() {
		if (scrollCategories == null) {
			scrollCategories = new JScrollPane();
			scrollCategories.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
			scrollCategories.setViewportView(getListCategories());
		}
		return scrollCategories;
	}

	/**
	 * This method initializes listCategories	
	 * 	
	 * @return javax.swing.JList	
	 */
	public JList getListCategories() {
		if (listCategories == null) {
			listCategories = new JList();
			listCategories.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			listCategories.setMinimumSize(new Dimension(247, 105));
			listCategories.setMaximumSize(new Dimension(267, 128));
			listCategories.setPreferredSize(new Dimension(267, 128));
		}
		
		return listCategories;
	}
	/**
	 * This method initializes panelFormulas	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPanelFormulas() {
		if (panelFormulas == null) {
			panelFormulas = new JPanel();
			panelFormulas.setLayout(new BorderLayout());
			panelFormulas.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED), CMMessages.getString("TESTDATA_NAME_FORMULA"), TitledBorder.LEADING, TitledBorder.TOP, new Font("SansSerif", Font.BOLD, 10), SystemColor.activeCaption));
			panelFormulas.setPreferredSize(new Dimension(272, 158));
			panelFormulas.setMinimumSize(new Dimension(272, 158));
			panelFormulas.add(getScrollFormulas(), BorderLayout.CENTER);
		}
		return panelFormulas;
	}

	/**
	 * This method initializes scrollFormulas	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getScrollFormulas() {
		if (scrollFormulas == null) {
			scrollFormulas = new JScrollPane();
			scrollFormulas.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
			scrollFormulas.setMinimumSize(new Dimension(260, 147));
			scrollFormulas.setViewportView(getListFormulas());
		}
		return scrollFormulas;
	}

	/**
	 * This method initializes listFormulas	
	 * 	
	 * @return javax.swing.JList	
	 */
	public JList getListFormulas() {
		if (listFormulas == null) {
			listFormulas = new JList();
			listFormulas.setModel(new DefaultListModel());
			listFormulas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		}
		return listFormulas;
	}

	/**
	 * This method initializes panelInfo	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPanelInfo() {
		if (panelInfo == null) {
			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			gridBagConstraints7.fill = GridBagConstraints.BOTH;
			gridBagConstraints7.gridx = 0;
			gridBagConstraints7.gridy = 2;
			gridBagConstraints7.ipadx = 358;
			gridBagConstraints7.ipady = 7;
			gridBagConstraints7.weightx = 1.0;
			gridBagConstraints7.weighty = 1.0;
			gridBagConstraints7.insets = new Insets(0, 26, 1, 26);
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.gridx = 0;
			gridBagConstraints6.ipadx = 358;
			gridBagConstraints6.ipady = 7;
			gridBagConstraints6.gridy = 1;
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.insets = new Insets(0, 26, 0, 0);
			gridBagConstraints5.gridy = 0;
			gridBagConstraints5.anchor = GridBagConstraints.WEST;
			gridBagConstraints5.gridx = 0;
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.fill = GridBagConstraints.BOTH;
			gridBagConstraints4.gridy = 1;
			gridBagConstraints4.weightx = 1.0;
			gridBagConstraints4.weighty = 1.0;
			gridBagConstraints4.gridx = 1;
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.insets = new Insets(0, 26, 0, 15);
			gridBagConstraints2.gridy = 2;
			gridBagConstraints2.weightx = 1.0;
			gridBagConstraints2.anchor = GridBagConstraints.WEST;
			gridBagConstraints2.gridx = 0;
			labelCanonicalName = new JLabel();
			labelCanonicalName.setText("JLabel");
			labelCanonicalName.setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 14));
			labelCanonicalName.setHorizontalAlignment(SwingConstants.LEADING);
			panelInfo = new JPanel();
			panelInfo.setLayout(new GridBagLayout());
			panelInfo.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
			panelInfo.setPreferredSize(new Dimension(629, 100));
			panelInfo.setMinimumSize(new Dimension(629, 100));
			panelInfo.setMaximumSize(new Dimension(629, 100));
			panelInfo.add(labelCanonicalName, gridBagConstraints5);
			panelInfo.add(getJPanel4(), gridBagConstraints6);
			panelInfo.add(getJTextAreaDescription(), gridBagConstraints7);
		}
		return panelInfo;
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
			jPanel.setPreferredSize(new Dimension(10, 10));
		}
		return jPanel;
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
		}
		return jPanel1;
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
			jPanel2.setPreferredSize(new Dimension(10, 10));
		}
		return jPanel2;
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

	public void setJTextAreaDescription(JTextArea textAreaDescription) {
		jTextAreaDescription = textAreaDescription;
	}

	public JLabel getLabelCanonicalName() {
		return labelCanonicalName;
	}

	public void setLabelCanonicalName(JLabel labelCanonicalName) {
		this.labelCanonicalName = labelCanonicalName;
	}

	/**
	 * This method initializes jSplitPane	
	 * 	
	 * @return javax.swing.JSplitPane	
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
			jSplitPane.setBottomComponent(getPanelInfo());
			jSplitPane.setTopComponent(getPanelJLists());
		}
		return jSplitPane;
	}

	/**
	 * This method initializes jSplitPane1	
	 * 	
	 * @return javax.swing.JSplitPane	
	 */
	public JSplitPane getJSplitPane1() {
		if (jSplitPane1 == null) {
			jSplitPane1 = new JSplitPane();
			jSplitPane1.setOrientation(JSplitPane.VERTICAL_SPLIT);
			jSplitPane1.setTopComponent(getJSplitPane());
			
		}
		return jSplitPane1;
	}

	/**
	 * This method initializes jSplitPane2	
	 * 	
	 * @return javax.swing.JSplitPane	
	 */
	private JSplitPane getJSplitPane2() {
		if (jSplitPane2 == null) {
			jSplitPane2 = new JSplitPane();
			jSplitPane2.setOrientation(JSplitPane.VERTICAL_SPLIT);
			jSplitPane2.setBottomComponent(getJPanelPreview());
			jSplitPane2.setTopComponent(getJSplitPane1());
		}
		return jSplitPane2;
	}

	/**
	 * This method initializes jPanelPreview	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelPreview() {
		if (jPanelPreview == null) {
			GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
			gridBagConstraints21.fill = GridBagConstraints.BOTH;
			gridBagConstraints21.gridy = 1;
			gridBagConstraints21.weightx = 1.0;
			gridBagConstraints21.weighty = 1.0;
			gridBagConstraints21.anchor = GridBagConstraints.WEST;
			gridBagConstraints21.insets = new Insets(0, 30, 5, 30);
			gridBagConstraints21.gridx = 0;
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.insets = new Insets(0, 30, 0, 327);
			gridBagConstraints1.gridy = 0;
			gridBagConstraints1.anchor = GridBagConstraints.WEST;
			gridBagConstraints1.gridx = 0;
			jLabelPreview = new JLabel();
			jLabelPreview.setFont(new Font("SansSerif", Font.BOLD, 14));
			jLabelPreview.setText(CMMessages.getString("TESTDATA_FORMULAPREVIEW_LABEL"));
			jPanelPreview = new JPanel();
			jPanelPreview.setLayout(new GridBagLayout());
			jPanelPreview.setMaximumSize(new Dimension(629, 80));
			jPanelPreview.setMinimumSize(new Dimension(629, 80));
			jPanelPreview.setPreferredSize(new Dimension(629, 80));
			jPanelPreview.add(jLabelPreview, gridBagConstraints1);
			jPanelPreview.add(getJTextAreaPreview(), gridBagConstraints21);
		}
		return jPanelPreview;
	}

	/**
	 * This method initializes jTextAreaPreview	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	public JTextArea getJTextAreaPreview() {
		if (jTextAreaPreview == null) {
			jTextAreaPreview = new JTextArea();
			jTextAreaPreview.setBackground(this.getBackground());
			jTextAreaPreview.setFont(new Font("Courier", Font.PLAIN, 13));
			jTextAreaPreview.setEditable(false);
			jTextAreaPreview.setDisabledTextColor(new Color(212, 208, 200));
			jTextAreaPreview.setCaretColor(new Color(212, 208, 200));
			jTextAreaPreview.setWrapStyleWord(true);
			jTextAreaPreview.setLineWrap(true);
			jTextAreaPreview.setCursor(new Cursor(Cursor.TEXT_CURSOR));
		}
		return jTextAreaPreview;
	}
	
	public List getTabOrder(){
		List<JComponent> focusOrder= new ArrayList<JComponent>();
		focusOrder.add(listCategories);
		focusOrder.add(listFormulas);
//		try {
//			CMPanelFormulaParameters panel = (CMPanelFormulaParameters)getJSplitPane1().getBottomComponent();
//			for(Object component: panel.getTabOrder()){
//				focusOrder.add((JComponent) component);
//			}
//		} catch (Exception e) {
//			// TODO: 	CHECK THIS METHOD!!!
//		}
		
		return focusOrder;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
