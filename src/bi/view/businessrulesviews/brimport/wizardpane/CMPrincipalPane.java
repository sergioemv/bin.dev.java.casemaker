package bi.view.businessrulesviews.brimport.wizardpane;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionListener;

import bi.view.businessrulesviews.brimport.ECMBusinessRulesImport;
import bi.view.icons.CMIcon;
import bi.view.lang.CMMessages;

@SuppressWarnings("serial") //$NON-NLS-1$
public class CMPrincipalPane extends JPanel {


	private JPanel jDescriptionContentPanel = null;
	private JPanel jDescriptionPanel = null;
	private JLabel jDescriptionLabel = null;
	private JPanel jTitlePanel = null;
	private JLabel jTitleLabel = null;
	private JPanel jSpacePanel = null;
	private JPanel jImagePanel = null;
	private JLabel jImageLabel = null;
	private JPanel jListContentPanel = null;
	private JScrollPane jListScrollPane = null;
	private JList jOptionList = null;
	private JPanel jChoosePanel = null;
	private JLabel jChooseLabel = null;
	private JPanel jSpacePanel1 = null;
	private JPanel jSpacePanel2 = null;
	private JPanel jSpacePanel3 = null;

	/**
	 * This method initializes 
	 * 
	 */
	public CMPrincipalPane() {
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
		add(getJDescriptionContentPanel(), java.awt.BorderLayout.NORTH);
		add(getJListContentPanel(), java.awt.BorderLayout.CENTER);

			
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	

	/**
	 * This method initializes jPanel2	
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
	 * This method initializes jPanel3	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJDescriptionPanel() {
		if (jDescriptionPanel == null) {
			BorderLayout borderLayout = new BorderLayout();
			borderLayout.setHgap(0);
			borderLayout.setVgap(0);
			jDescriptionPanel = new JPanel();
			jDescriptionPanel.setLayout(borderLayout);
			jDescriptionPanel.setBackground(Color.white);
			jDescriptionPanel.add(getJDescriptionLabel(), java.awt.BorderLayout.CENTER);
			jDescriptionPanel.add(getJTitlePanel(), java.awt.BorderLayout.NORTH);
			jDescriptionPanel.add(getJSpacePanel(), java.awt.BorderLayout.WEST);
		}
		return jDescriptionPanel;
	}

	JLabel getJDescriptionLabel(){
		if(jDescriptionLabel == null){
			jDescriptionLabel = new JLabel();
			jDescriptionLabel.setBackground(Color.white);
			jDescriptionLabel.setText(""); //$NON-NLS-1$
			jDescriptionLabel.setFont(new Font("Dialog", Font.PLAIN, 12)); //$NON-NLS-1$
		}
		return jDescriptionLabel;
	}
	/**
	 * This method initializes jPanel4	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJTitlePanel() {
		if (jTitlePanel == null) {
			jTitleLabel = new JLabel();
			jTitleLabel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			jTitleLabel.setFont(new Font("Dialog", Font.BOLD, 12)); //$NON-NLS-1$
			jTitleLabel.setText(CMMessages.getString("BUSINESS_RULES_IMPORTSELECT")); //$NON-NLS-1$
			jTitleLabel.setBackground(Color.white);
			FlowLayout flowLayout1 = new FlowLayout();
			flowLayout1.setHgap(7);
			flowLayout1.setAlignment(FlowLayout.LEFT);
			flowLayout1.setVgap(7);
			jTitlePanel = new JPanel();
			jTitlePanel.setLayout(flowLayout1);
			jTitlePanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			jTitlePanel.setBackground(Color.white);
			jTitlePanel.add(jTitleLabel, null);
		}
		return jTitlePanel;
	}

	/**
	 * This method initializes jPanel5	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJSpacePanel() {
		if (jSpacePanel == null) {
			FlowLayout flowLayout2 = new FlowLayout();
			flowLayout2.setHgap(0);
			flowLayout2.setVgap(0);
			jSpacePanel = new JPanel();
			jSpacePanel.setPreferredSize(new Dimension(12, 0));
			jSpacePanel.setLayout(flowLayout2);
			jSpacePanel.setBackground(Color.white);
		}
		return jSpacePanel;
	}

	/**
	 * This method initializes jPanel6	
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
	 * This method initializes jPanel7	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJListContentPanel() {
		if (jListContentPanel == null) {
			BorderLayout borderLayout1 = new BorderLayout();
			borderLayout1.setHgap(0);
			borderLayout1.setVgap(5);
			jListContentPanel = new JPanel();
			jListContentPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
			jListContentPanel.setLayout(borderLayout1);
			jListContentPanel.add(getJListScrollPane(), java.awt.BorderLayout.CENTER);
			jListContentPanel.add(getJChoosePanel(), java.awt.BorderLayout.NORTH);
			jListContentPanel.add(getJSpacePanel2(), java.awt.BorderLayout.WEST);
			jListContentPanel.add(getJSpacePanel3(), java.awt.BorderLayout.EAST);
		}
		return jListContentPanel;
	}

	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJListScrollPane() {
		if (jListScrollPane == null) {
			jListScrollPane = new JScrollPane();
			jListScrollPane.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
			jListScrollPane.setViewportView(getJOptionList());
		}
		return jListScrollPane;
	}

	/**
	 * This method initializes jList	
	 * 	
	 * @return javax.swing.JList	
	 */
	 JList getJOptionList() {
		if (jOptionList == null) {
			jOptionList = new JList();
			jOptionList.setModel(new DefaultListModel());
			for (ECMBusinessRulesImport importer : ECMBusinessRulesImport.values())
				((DefaultListModel)jOptionList.getModel()).addElement(importer);
	        jOptionList.setCellRenderer(new CMImportListCellRenderer()); 
			jOptionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			jOptionList.setSelectedIndex(0);
			
		}
		return jOptionList;
	}

	/**
	 * This method initializes jPanel8	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJChoosePanel() {
		if (jChoosePanel == null) {
			jChooseLabel = new JLabel();
			jChooseLabel.setFont(new Font("Dialog", Font.PLAIN, 12)); //$NON-NLS-1$
			jChooseLabel.setText(CMMessages.getString("BUSINESS_RULES_IMPORTSELECT_SOURCE")); //$NON-NLS-1$
			jChoosePanel = new JPanel();
			jChoosePanel.setLayout(new BorderLayout());
			jChoosePanel.add(jChooseLabel, java.awt.BorderLayout.CENTER);
			jChoosePanel.add(getJSpacePanel1(), java.awt.BorderLayout.WEST);
		}
		return jChoosePanel;
	}

	/**
	 * This method initializes jPanel9	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJSpacePanel1() {
		if (jSpacePanel1 == null) {
			jSpacePanel1 = new JPanel();
			jSpacePanel1.setPreferredSize(new Dimension(10, 0));
		}
		return jSpacePanel1;
	}

	/**
	 * This method initializes jPanel10	
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
	 * This method initializes jPanel11	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJSpacePanel3() {
		if (jSpacePanel3 == null) {
			FlowLayout flowLayout4 = new FlowLayout();
			flowLayout4.setHgap(0);
			flowLayout4.setVgap(0);
			jSpacePanel3 = new JPanel();
			jSpacePanel3.setPreferredSize(new Dimension(10, 0));
			jSpacePanel3.setLayout(flowLayout4);
		}
		return jSpacePanel3;
	}

	class CMImportListCellRenderer extends JLabel implements ListCellRenderer {
   		/**
 * 
 */
private static final long serialVersionUID = 1L;

     // This is the only method defined by ListCellRenderer.
     // We just reconfigure the JLabel each time we're called.


	public Component getListCellRendererComponent(
       JList list,
       Object value,            // value to display
       int index,               // cell index
       boolean isSelected,      // is the cell selected
       boolean cellHasFocus)    // the list and the cell have the focus
     {
		ECMBusinessRulesImport importer = ((ECMBusinessRulesImport)value);
	
         setText(importer.getName());
         setIcon(importer.getIconImage());
           if (isSelected) {
             setBackground(list.getSelectionBackground());
               setForeground(list.getSelectionForeground());
           }
         else {
               setBackground(list.getBackground());
               setForeground(list.getForeground());
           }
           setEnabled(list.isEnabled());
           setFont(list.getFont());
         setOpaque(true);
         return this;
     }
     
}

	public void addListSelectionListener(ListSelectionListener descriptor) {
		jOptionList.addListSelectionListener(descriptor);
		
	}
	
	


	public List getOrder() {
		List focusOrder= new ArrayList();
		focusOrder.add(jOptionList);
		return focusOrder;
	}

}
