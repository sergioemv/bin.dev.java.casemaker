package bi.view.businessrulesviews.brimport.wizardpane;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.EtchedBorder;

import bi.view.icons.CMIcon;
import bi.view.lang.CMMessages;

@SuppressWarnings("serial") //$NON-NLS-1$
public class CMProgressImportPane extends JPanel {
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
	private JPanel jProgressPanel = null;
	private JProgressBar jProgressBar = null;
	private JLabel jInformationLabel = null;
	private JLabel jBlankLabel = null;
	private JLabel jBlankLabel2 = null;
	private JPanel jPanelDetails = null;
	private JButton jButtonDetails = null;
	/**
	 * This method initializes 
	 * 
	 */
	public CMProgressImportPane() {
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
			jDescriptionLabel.setText(CMMessages.getString("BR_IMPORT_WIZARD_WAITING")); //$NON-NLS-1$
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
			jTitleLabel.setText(CMMessages.getString("BR_IMPORT_WIZARD_TITLE_PROGRESS")); //$NON-NLS-1$
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
			jContentPanel.add(getJSpacePanel2(), java.awt.BorderLayout.EAST);
			jContentPanel.add(getJProgressPanel(), java.awt.BorderLayout.CENTER);
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
	 * This method initializes jProgressPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJProgressPanel() {
		if (jProgressPanel == null) {
			GridLayout gridLayout1 = new GridLayout();
			gridLayout1.setRows(9);
			gridLayout1.setColumns(1);
			jBlankLabel2 = new JLabel();
			jBlankLabel2.setText(""); //$NON-NLS-1$
			jBlankLabel = new JLabel();
			jBlankLabel.setText(""); //$NON-NLS-1$
			jInformationLabel = new JLabel();
			jInformationLabel.setText(""); //$NON-NLS-1$
			jProgressPanel = new JPanel();
			jProgressPanel.setLayout(gridLayout1);
			jProgressPanel.add(jBlankLabel, null);
			jProgressPanel.add(jBlankLabel2, null);
			jProgressPanel.add(getJProgressBar(), null);
			jProgressPanel.add(getJPanelDetails(), null);
		}
		return jProgressPanel;
	}

	/**
	 * This method initializes jProgressBar	
	 * 	
	 * @return javax.swing.JProgressBar	
	 */
	private JProgressBar getJProgressBar() {
		if (jProgressBar == null) {
			jProgressBar = new JProgressBar();
		}
		return jProgressBar;
	}
 
	public void setProgressText(String s) {
        jInformationLabel.setText(s);
    }
    
    public void setProgressValue(int i) {
        jProgressBar.setValue(i);
    }

	public List getOrder() {
		List focusOrder= new ArrayList();

		return focusOrder;
	}

	/**
	 * This method initializes jPanelDetails	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelDetails() {
		if (jPanelDetails == null) {
			FlowLayout flowLayout4 = new FlowLayout();
			flowLayout4.setAlignment(FlowLayout.RIGHT);
			flowLayout4.setHgap(10);
			jPanelDetails = new JPanel();
			jPanelDetails.setPreferredSize(new Dimension(61, 52));
			jPanelDetails.setLayout(flowLayout4);
			jPanelDetails.add(jInformationLabel, null);
			jPanelDetails.add(getJButtonDetails(), null);
		}
		return jPanelDetails;
	}

	/**
	 * This method initializes jButtonDetails	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonDetails() {
		if (jButtonDetails == null) {
			jButtonDetails = new JButton();
			jButtonDetails.setText("Details");
			jButtonDetails.setVisible(false);
		}
		return jButtonDetails;
	}


}
