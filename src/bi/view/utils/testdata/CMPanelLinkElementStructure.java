/**
 * 24/11/2006
 * svonborries
 */
package bi.view.utils.testdata;

import java.awt.GridBagLayout;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.BorderLayout;
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
import javax.swing.ListSelectionModel;

import bi.view.lang.CMMessages;

import java.awt.FlowLayout;
import java.util.ArrayList;

import java.util.List;

/**
 * @author svonborries
 *
 */
public class CMPanelLinkElementStructure extends JPanel {

	private static final long serialVersionUID = 1L;
	private JPanel jPanelListContainers = null;
	private JPanel jPanel = null;
	private JPanel jPanelStructures = null;
	private JPanel jPanel1 = null;
	private JPanel jPanelElements = null;
	private JPanel jPanel2 = null;
	private JScrollPane jScrollPaneStructures = null;
	private JScrollPane jScrollPaneElements = null;
	private JList jListStructures = null;
	private JList jListElements = null;
	private JPanel jPanelBottonContent = null;
	private JLabel jLabelPreview = null;
	private JLabel jLabelPreviewValue = null;
	private JPanel jPanel3 = null;
	private JPanel jPanel4 = null;
	private JPanel jPanel5 = null;
	private JPanel jPanel6 = null;

	/**
	 * This is the default constructor
	 */
	public CMPanelLinkElementStructure() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(502, 287);
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED), CMMessages.getString("LINK_ELEMENTS_STRUCTURE_PANEL_TITLE"), TitledBorder.LEADING, TitledBorder.TOP, new Font("SansSerif", Font.BOLD, 10), SystemColor.activeCaption));
		this.add(getJPanelListContainers(), BorderLayout.NORTH);
		this.add(getJPanelBottonContent(), BorderLayout.SOUTH);
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
			jPanelListContainers.add(getJPanelStructures(), null);
			jPanelListContainers.add(getJPanel1(), null);
			jPanelListContainers.add(getJPanelElements(), null);
			jPanelListContainers.add(getJPanel2(), null);
		}
		return jPanelListContainers;
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
		}
		return jPanel;
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
			jPanelStructures.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED), CMMessages.getString("TESTDATA_LINKELEMENT_STRUCTURE"), TitledBorder.LEADING, TitledBorder.TOP, new Font("SansSerif", Font.BOLD, 10), SystemColor.activeCaption));
			jPanelStructures.setPreferredSize(new Dimension(191, 181));
			//jPanelStructures.setMaximumSize(new Dimension(191, 181));
			jPanelStructures.setMinimumSize(new Dimension(191, 181));
			//jPanelStructures.setPreferredSize(new Dimension(201, 191));
			//jPanelStructures.setMinimumSize(new Dimension(201, 191));
			//jPanelStructures.setMaximumSize(new Dimension(201, 191));
			
			jPanelStructures.add(getJScrollPaneStructures(), BorderLayout.CENTER);
		}
		return jPanelStructures;
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
	 * This method initializes jPanelElements	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelElements() {
		if (jPanelElements == null) {
			jPanelElements = new JPanel();
			jPanelElements.setLayout(new BorderLayout());
			jPanelElements.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED), CMMessages.getString("TESTDATA_LINKELEMENT_FIELD"), TitledBorder.LEADING, TitledBorder.TOP, new Font("SansSerif", Font.BOLD, 10), SystemColor.activeCaption));
			jPanelElements.setPreferredSize(new Dimension(191, 181));
			//jPanelElements.setMaximumSize(new Dimension(191, 181));
			jPanelElements.setMinimumSize(new Dimension(191, 181));
			//jPanelElements.setPreferredSize(new Dimension(201, 191));
			//jPanelElements.setMinimumSize(new Dimension(201, 191));
			//jPanelElements.setMaximumSize(new Dimension(201, 191));
			
			jPanelElements.add(getJScrollPaneElements(), BorderLayout.CENTER);
		}
		return jPanelElements;
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
			//jScrollPaneElements.setPreferredSize(new Dimension(201, 165));
			//jScrollPaneElements.setMinimumSize(new Dimension(201, 165));
			//jScrollPaneElements.setMaximumSize(new Dimension(201, 165));
			jScrollPaneElements.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
			jScrollPaneElements.setViewportView(getJListElements());
		}
		return jScrollPaneElements;
	}

	/**
	 * This method initializes jListStructures	
	 * 	
	 * @return javax.swing.JList	
	 */
	public JList getJListStructures() {
		if (jListStructures == null) {
			jListStructures = new JList();
			//jListStructures.setSize(new Dimension(168, 128));
			//jListStructures.setMaximumSize(new Dimension(178, 128));
			jListStructures.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			//jListStructures.setMaximumSize(new Dimension(161, 141));
			//jListStructures.setMinimumSize(new Dimension(141, 121));
			//jListStructures.setPreferredSize(new Dimension(161, 141));
			
			//jListStructures.setMinimumSize(new Dimension(178, 128));
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
			//jListElements.setPreferredSize(new Dimension(161, 141));
			//jListElements.setMinimumSize(new Dimension(141, 121));
			//jListElements.setMaximumSize(new Dimension(161, 141));
			jListElements.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		}
		return jListElements;
	}

	/**
	 * This method initializes jPanelBottonContent	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelBottonContent() {
		if (jPanelBottonContent == null) {
			jLabelPreviewValue = new JLabel();
			jLabelPreviewValue.setText("JLabel");
			jLabelPreviewValue.setFont(new java.awt.Font("SansSerif", java.awt.Font.ITALIC, 12));
			jLabelPreview = new JLabel();
			jLabelPreview.setText(CMMessages.getString("TESTDATA_LINKVALUELABEL"));
			jLabelPreview.setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 14));
			jPanelBottonContent = new JPanel();
			jPanelBottonContent.setLayout(new BoxLayout(getJPanelBottonContent(), BoxLayout.Y_AXIS));
			jPanelBottonContent.add(getJPanel5(), null);
			jPanelBottonContent.add(getJPanel3(), null);
			jPanelBottonContent.add(getJPanel6(), null);
			jPanelBottonContent.add(getJPanel4(), null);
		}
		return jPanelBottonContent;
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
			jPanel3.setPreferredSize(new Dimension(10, 15));
			jPanel3.setMaximumSize(new Dimension(10, 15));
			jPanel3.setMinimumSize(new Dimension(10, 15));
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
			jPanel4.setMinimumSize(new Dimension(10, 10));
			jPanel4.setMaximumSize(new Dimension(10, 10));
			jPanel4.setPreferredSize(new Dimension(10, 10));
		}
		return jPanel4;
	}

	public JLabel getJLabelPreviewValue() {
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
			flowLayout.setHgap(32);
			jPanel5 = new JPanel();
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
			flowLayout1.setHgap(32);
			jPanel6 = new JPanel();
			jPanel6.setMaximumSize(new Dimension(502, 26));
			jPanel6.setMinimumSize(new Dimension(402, 26));
			jPanel6.setPreferredSize(new Dimension(402, 26));
			jPanel6.setLayout(flowLayout1);
			jPanel6.add(getJLabelPreviewValue(), null);
		}
		return jPanel6;
	}

	public List getTabOrder(){
		List<JComponent> focusOrder= new ArrayList<JComponent>();
		focusOrder.add(jListStructures);
		focusOrder.add(jListElements);
		return focusOrder;
	}
	
}  //  @jve:decl-index=0:visual-constraint="10,10"
