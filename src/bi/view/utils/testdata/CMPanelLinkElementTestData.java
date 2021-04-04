/**
 * 24/11/2006
 * svonborries
 */
package bi.view.utils.testdata;

import java.awt.GridBagLayout;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.SystemColor;

import javax.swing.BoxLayout;


import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;

import bi.view.lang.CMMessages;

import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ListSelectionModel;

/**
 * @author svonborries
 *
 */
public class CMPanelLinkElementTestData extends JPanel {

	private static final long serialVersionUID = 1L;
	private JPanel jPanelListContainers = null;
	private JPanel jPanelBottomComponents = null;
	private JPanel jPanel = null;
	private JPanel jPanelTestDatas = null;
	private JPanel jPanel1 = null;
	private JPanel jPanelStructures = null;
	private JPanel jPanel2 = null;
	private JPanel jPanelElements = null;
	private JPanel jPanel3 = null;
	private JScrollPane jScrollPaneTestDatas = null;
	private JScrollPane jScrollPaneStructures = null;
	private JScrollPane jScrollPaneElements = null;
	private JList jListTestDatas = null;
	private JList jListStructures = null;
	private JList jListElements = null;
	private JLabel jLabelPreview = null;
	private JPanel jPanel31 = null;
	private JLabel jLabelPreviewValue = null;
	private JPanel jPanel4 = null;
	private JPanel jPanel5 = null;
	private JPanel jPanel6 = null;

	/**
	 * This is the default constructor
	 */
	public CMPanelLinkElementTestData() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(653, 292);
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED), CMMessages.getString("LINK_ELEMENTS_TESTDATA_PANEL_TITLE"), TitledBorder.LEADING, TitledBorder.TOP, new Font("SansSerif", Font.BOLD, 10), SystemColor.activeCaption));
		this.add(getJPanelListContainers(), BorderLayout.NORTH);
		this.add(getJPanelBottomComponents(), BorderLayout.SOUTH);
	}

	/**
	 * This method initializes jPanelListContainers	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelListContainers() {
		if (jPanelListContainers == null) {
			jPanelListContainers = new JPanel();
			jPanelListContainers.setLayout(new BoxLayout(getJPanelListContainers(), BoxLayout.X_AXIS));
			jPanelListContainers.add(getJPanel(), null);
			jPanelListContainers.add(getJPanelTestDatas(), null);
			jPanelListContainers.add(getJPanel1(), null);
			jPanelListContainers.add(getJPanelStructures(), null);
			jPanelListContainers.add(getJPanel2(), null);
			jPanelListContainers.add(getJPanelElements(), null);
			jPanelListContainers.add(getJPanel3(), null);
		}
		return jPanelListContainers;
	}

	/**
	 * This method initializes jPanelBottomComponents	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelBottomComponents() {
		if (jPanelBottomComponents == null) {
			jLabelPreviewValue = new JLabel();
			jLabelPreviewValue.setFont(new Font("SansSerif", Font.ITALIC, 12));
			jLabelPreviewValue.setText("JLabel");
			jLabelPreview = new JLabel();
			jLabelPreview.setFont(new Font("SansSerif", Font.BOLD, 14));
			jLabelPreview.setText(CMMessages.getString("TESTDATA_LINKVALUELABEL"));
			jPanelBottomComponents = new JPanel();
			jPanelBottomComponents.setLayout(new BoxLayout(getJPanelBottomComponents(), BoxLayout.Y_AXIS));
			jPanelBottomComponents.add(getJPanel5(), null);
			jPanelBottomComponents.add(getJPanel31(), null);
			jPanelBottomComponents.add(getJPanel6(), null);
			jPanelBottomComponents.add(getJPanel4(), null);
		}
		return jPanelBottomComponents;
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
			jPanel.setMinimumSize(new Dimension(10, 10));
		}
		return jPanel;
	}

	/**
	 * This method initializes jPanelTestDatas	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelTestDatas() {
		if (jPanelTestDatas == null) {
			jPanelTestDatas = new JPanel();
			jPanelTestDatas.setLayout(new BorderLayout());
			jPanelTestDatas.setPreferredSize(new Dimension(201, 191));
			jPanelTestDatas.setMinimumSize(new Dimension(201, 191));
			jPanelTestDatas.setMaximumSize(new Dimension(201, 191));
			jPanelTestDatas.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED), CMMessages.getString("TESTDATA_LINKELEMENT_GLOBAL"), TitledBorder.LEADING, TitledBorder.TOP, new Font("SansSerif", Font.BOLD, 10), SystemColor.activeCaption));
			jPanelTestDatas.add(getJScrollPaneTestDatas(), BorderLayout.CENTER);
		}
		return jPanelTestDatas;
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
	 * This method initializes jPanelStructures	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelStructures() {
		if (jPanelStructures == null) {
			jPanelStructures = new JPanel();
			jPanelStructures.setLayout(new BorderLayout());
			jPanelStructures.setPreferredSize(new Dimension(201, 191));
			jPanelStructures.setMinimumSize(new Dimension(201, 191));
			jPanelStructures.setMaximumSize(new Dimension(201, 191));
			jPanelStructures.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED), CMMessages.getString("TESTDATA_LINKELEMENT_STRUCTURE"), TitledBorder.LEADING, TitledBorder.TOP, new Font("SansSerif", Font.BOLD, 10), SystemColor.activeCaption));
			jPanelStructures.add(getJScrollPaneStructures(), BorderLayout.CENTER);
		}
		return jPanelStructures;
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
		}
		return jPanel2;
	}

	/**
	 * This method initializes jPanelElements	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelElements() {
		if (jPanelElements == null) {
			jPanelElements = new JPanel();
			jPanelElements.setLayout(new BorderLayout());
			jPanelElements.setPreferredSize(new Dimension(201, 191));
			jPanelElements.setMinimumSize(new Dimension(201, 191));
			jPanelElements.setMaximumSize(new Dimension(201, 191));
			jPanelElements.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED), CMMessages.getString("TESTDATA_LINKELEMENT_FIELD"), TitledBorder.LEADING, TitledBorder.TOP, new Font("SansSerif", Font.BOLD, 10), SystemColor.activeCaption));
			jPanelElements.add(getJScrollPaneElements(), BorderLayout.CENTER);
		}
		return jPanelElements;
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
			jPanel3.setPreferredSize(new Dimension(10, 10));
			jPanel3.setMinimumSize(new Dimension(10, 10));
		}
		return jPanel3;
	}

	/**
	 * This method initializes jScrollPaneTestDatas	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPaneTestDatas() {
		if (jScrollPaneTestDatas == null) {
			jScrollPaneTestDatas = new JScrollPane();
			//jScrollPaneTestDatas.setPreferredSize(new Dimension(201, 165));
			//jScrollPaneTestDatas.setMinimumSize(new Dimension(201, 165));
			//jScrollPaneTestDatas.setMaximumSize(new Dimension(201, 165));
			jScrollPaneTestDatas.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
			jScrollPaneTestDatas.setViewportView(getJListTestDatas());
		}
		return jScrollPaneTestDatas;
	}

	/**
	 * This method initializes jScrollPaneStructures	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPaneStructures() {
		if (jScrollPaneStructures == null) {
			jScrollPaneStructures = new JScrollPane();
			//jScrollPaneStructures.setPreferredSize(new Dimension(201, 165));
			//jScrollPaneStructures.setMinimumSize(new Dimension(201, 165));
			//jScrollPaneStructures.setMaximumSize(new Dimension(201, 165));
			jScrollPaneStructures.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
			jScrollPaneStructures.setViewportView(getJListStructures());
		}
		return jScrollPaneStructures;
	}

	/**
	 * This method initializes jScrollPaneElements	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPaneElements() {
		if (jScrollPaneElements == null) {
			jScrollPaneElements = new JScrollPane();
			//jScrollPaneElements.setMaximumSize(new Dimension(201, 165));
			//jScrollPaneElements.setMinimumSize(new Dimension(201, 165));
			//jScrollPaneElements.setPreferredSize(new Dimension(201, 165));
			jScrollPaneElements.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
			jScrollPaneElements.setViewportView(getJListElements());
		}
		return jScrollPaneElements;
	}

	/**
	 * This method initializes jListTestDatas	
	 * 	
	 * @return javax.swing.JList	
	 */
	public JList getJListTestDatas() {
		if (jListTestDatas == null) {
			jListTestDatas = new JList();
			//jListTestDatas.setSize(new Dimension(168, 128));
			jListTestDatas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			//jListTestDatas.setMinimumSize(new Dimension(168, 128));
			//jListTestDatas.setMaximumSize(new Dimension(168, 128));
			//jListTestDatas.setPreferredSize(new Dimension(168, 128));
		}
		return jListTestDatas;
	}

	/**
	 * This method initializes jListStructures	
	 * 	
	 * @return javax.swing.JList	
	 */
	public JList getJListStructures() {
		if (jListStructures == null) {
			jListStructures = new JList();
			//jListStructures.setMaximumSize(new Dimension(168, 128));
			jListStructures.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			//jListStructures.setPreferredSize(new Dimension(168, 128));
			//jListStructures.setSize(new Dimension(168, 128));
			//jListStructures.setMinimumSize(new Dimension(168, 128));
		}
		return jListStructures;
	}

	/**
	 * This method initializes jListElements	
	 * 	
	 * @return javax.swing.JList	
	 */
	public JList getJListElements() {
		if (jListElements == null) {
			jListElements = new JList();
			//jListElements.setSize(new Dimension(168, 128));
			//jListElements.setMinimumSize(new Dimension(168, 128));
			//jListElements.setMaximumSize(new Dimension(168, 128));
			//jListElements.setPreferredSize(new Dimension(168, 128));
		}
		return jListElements;
	}

	/**
	 * This method initializes jPanel31	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel31() {
		if (jPanel31 == null) {
			jPanel31 = new JPanel();
			jPanel31.setLayout(new GridBagLayout());
			jPanel31.setMaximumSize(new Dimension(10, 15));
			jPanel31.setPreferredSize(new Dimension(10, 15));
			jPanel31.setMinimumSize(new Dimension(10, 15));
		}
		return jPanel31;
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
			jPanel4.setMinimumSize(new Dimension(10, 10));
			jPanel4.setMaximumSize(new Dimension(10, 10));
			jPanel4.setPreferredSize(new Dimension(10, 10));
		}
		return jPanel4;
	}
	
	public JLabel getLabelPreviewValue(){
		return jLabelPreviewValue;
	}

	/**
	 * This method initializes jPanel5	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel5() {
		if (jPanel5 == null) {
			FlowLayout flowLayout = new FlowLayout();
			flowLayout.setAlignment(java.awt.FlowLayout.LEFT);
			flowLayout.setHgap(21);
			jPanel5 = new JPanel();
			jPanel5.setPreferredSize(new Dimension(253, 29));
			jPanel5.setMinimumSize(new Dimension(253, 29));
			jPanel5.setMaximumSize(new Dimension(650, 29));
			jPanel5.setLayout(flowLayout);
			jPanel5.add(jLabelPreview, null);
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
			FlowLayout flowLayout1 = new FlowLayout();
			flowLayout1.setAlignment(java.awt.FlowLayout.LEFT);
			flowLayout1.setHgap(22);
			jPanel6 = new JPanel();
			jPanel6.setPreferredSize(new Dimension(650, 26));
			jPanel6.setMaximumSize(new Dimension(650, 26));
			jPanel6.setMinimumSize(new Dimension(650, 26));
			jPanel6.setLayout(flowLayout1);
			jPanel6.add(getLabelPreviewValue(), null);
		}
		return jPanel6;
	}
	
	public List getTabOrder(){
		List<JComponent> focusOrder= new ArrayList<JComponent>();
		focusOrder.add(jListTestDatas);
		focusOrder.add(jListStructures);
		focusOrder.add(jListElements);
		return focusOrder;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
