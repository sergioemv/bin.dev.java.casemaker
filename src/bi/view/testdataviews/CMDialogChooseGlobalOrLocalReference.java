/**
 * 
 */
package bi.view.testdataviews;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextPane;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMFrameView;
import bi.view.utils.JCustomDialog;

/**
 * @author hcanedo
 *
 */
@SuppressWarnings("serial")
public class CMDialogChooseGlobalOrLocalReference extends JCustomDialog {
	private JPanel jPanelContent = null;
	private JPanel jPanelButtons = null;
	private JButton jButtonOk = null;
	private JButton jButtonCancel = null;
	private JPanel jPanelDialogContent = null;
	private JPanel jPanelRadioButtons = null;
	private JRadioButton jRadioButtonGlobal = null;
	private JRadioButton jRadioButtonLocal = null;
	private JPanel jPanelDescription = null;
	private JTextPane jTextPaneDescription = null;
	private ButtonGroup groupRadioButtons = null;
	private String selectDescription=new String();
	private boolean okSelected=false;
	private boolean localSelected=false;
	private CMFrameView m_Frame;
	/**
	 * @return Returns the groupRadioButtons.
	 */
	private ButtonGroup getGroupRadioButtons() {
		if(groupRadioButtons == null)
			groupRadioButtons= new ButtonGroup(); 
		return groupRadioButtons;
	}

	/**
	 * This method initializes 
	 * 
	 */
	public CMDialogChooseGlobalOrLocalReference(CMFrameView frame) {
		super(frame);
		m_Frame=frame;
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        this.setSize(new java.awt.Dimension(278,201));
        this.setTitle(CMMessages.getString("LINK_ELEMENTS_VALUE_DIALOG_TITLE"));  //$NON-NLS-1$
        this.setResizable(false);
        this.setModal(true);
        this.setContentPane(getJPanelContent());
        selectDescription=CMMessages.getString("LINK_ELEMENTS_VALUE_TAKE_GLOBAL_VALUE_DESCRIPTION");
		getJTextPaneDescription().setText(selectDescription);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension dlgSize = this.getPreferredSize();
        this.setLocation((screenSize.width - dlgSize.width) / 2 ,
            (screenSize.height - dlgSize.height) / 2 );

			
	}

	@SuppressWarnings("unchecked")
	protected List getOrder() {
		List focusOrder= new ArrayList();
		focusOrder.add(getJRadioButtonGlobal());
		focusOrder.add(getJRadioButtonLocal());
		focusOrder.add(getJButtonOk());
		focusOrder.add(getJButtonCancel());
		return focusOrder;
	}

	protected void PressJButtonCancel(Object object) {
		jButtonCancelActionPerformed(null);
	}

	protected void PressJButtonOk(Object object) {
		jButtonOkActionPerformed(null);
	}

	/**
	 * This method initializes jPanelContent	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelContent() {
		if (jPanelContent == null) {
			jPanelContent = new JPanel();
			jPanelContent.setLayout(new BorderLayout());
			jPanelContent.add(getJPanelButtons(), java.awt.BorderLayout.SOUTH);
			jPanelContent.add(getJPanelRadioButtons(), java.awt.BorderLayout.CENTER);
		}
		return jPanelContent;
	}

	/**
	 * This method initializes jPanelButtons	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelButtons() {
		if (jPanelButtons == null) {
			jPanelButtons = new JPanel();
			jPanelButtons.add(getJButtonOk(), null);
			jPanelButtons.add(getJButtonCancel(), null);
		}
		return jPanelButtons;
	}

	/**
	 * This method initializes jButtonOk	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonOk() {
		if (jButtonOk == null) {
			jButtonOk = new JButton();
			jButtonOk.setText(CMMessages.getString("BUTTON_OK")); 
			jButtonOk.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					jButtonOkActionPerformed(e);
				}
			});
		}
		return jButtonOk;
	}

	protected void jButtonOkActionPerformed(ActionEvent e) {
		int confirm=JOptionPane.NO_OPTION;
		if(isLocalSelected()){
			this.setVisible(false);
			confirm=JOptionPane.showConfirmDialog(m_Frame,CMMessages.getString("LINK_ELEMENTS_VALUE_DIALOG_MESSAGE"),CMMessages.getString("LINK_ELEMENTS_VALUE_WARNING_TITLE"),JOptionPane.INFORMATION_MESSAGE,JOptionPane.YES_NO_CANCEL_OPTION); //$NON-NLS-1$ //$NON-NLS-2$
		}
		if(confirm==JOptionPane.YES_OPTION){
		setOkSelected(true);
		dispose();
		}
		else if(confirm == JOptionPane.NO_OPTION){
			setLocalSelected(false);
			dispose();
			setOkSelected(false);
		}
		else{
			setVisible(true);
		}
		
	}

	/**
	 * This method initializes jButtonCancel	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonCancel() {
		if (jButtonCancel == null) {
			jButtonCancel = new JButton();
			jButtonCancel.setText(CMMessages.getString("BUTTON_CANCEL")); 
			jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					jButtonCancelActionPerformed(e);
				}
			});
		}
		return jButtonCancel;
	}

	protected void jButtonCancelActionPerformed(ActionEvent e) {
		dispose();
		setOkSelected(false);
	}

	/**
	 * This method initializes jPanelRadioButtons	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelRadioButtons() {
		if (jPanelDialogContent == null) {
			jPanelDialogContent = new JPanel();
			jPanelDialogContent.setLayout(new BorderLayout());
			jPanelDialogContent.add(getJPanelRadioButtons2(), java.awt.BorderLayout.CENTER);
		}
		return jPanelDialogContent;
	}

	/**
	 * This method initializes jPanelRadioButtons	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelRadioButtons2() {
		if (jPanelRadioButtons == null) {
			TitledBorder titledBorder = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), CMMessages.getString("LINK_ELEMENTS_VALUE_CHOOSER_SUBTITLE"), TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, null, null);
			titledBorder.setTitleColor(java.awt.Color.black);
			titledBorder.setTitle(CMMessages.getString("LINK_ELEMENTS_VALUE_CHOOSER_SUBTITLE")); //$NON-NLS-1$
			titledBorder.setTitleFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
			gridBagConstraints.gridheight = 1;
			gridBagConstraints.gridwidth = 2;
			gridBagConstraints.gridy = 3;
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridx = 1;
			gridBagConstraints2.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints2.ipadx = 1;
			gridBagConstraints2.ipady = 1;
			gridBagConstraints2.gridy = 1;
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 1;
			gridBagConstraints1.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints1.weightx = 0.0D;
			gridBagConstraints1.ipadx = 1;
			gridBagConstraints1.ipady = 1;
			gridBagConstraints1.gridy = 0;
			jPanelRadioButtons = new JPanel();
			jPanelRadioButtons.setLayout(new GridBagLayout());
			jPanelRadioButtons.setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 12));
			jPanelRadioButtons.setBorder(titledBorder);
			jPanelRadioButtons.add(getJRadioButtonGlobal(), gridBagConstraints1);
			jPanelRadioButtons.add(getJRadioButtonLocal(), gridBagConstraints2);
			jPanelRadioButtons.add(getJPanelDescription(), gridBagConstraints);
			getGroupRadioButtons().add(getJRadioButtonGlobal());
			getGroupRadioButtons().add(getJRadioButtonLocal());

		}
		return jPanelRadioButtons;
	}

	/**
	 * This method initializes jRadioButton1	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getJRadioButtonGlobal() {
		if (jRadioButtonGlobal == null) {
			jRadioButtonGlobal = new JRadioButton();
			selectDescription=CMMessages.getString("LINK_ELEMENTS_VALUE_TAKE_GLOBAL_VALUE_DESCRIPTION");
			getJTextPaneDescription().setText(selectDescription);
			jRadioButtonGlobal.setSelected(true);
			jRadioButtonGlobal.setText(CMMessages.getString("LINK_ELEMENTS_VALUE_GLOBAL_VALUE_LABEL"));  //$NON-NLS-1$
			jRadioButtonGlobal.setFont(new java.awt.Font("SansSerif", java.awt.Font.PLAIN, 12));
			jRadioButtonGlobal.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setLocalSelected(false);	
					selectDescription=CMMessages.getString("LINK_ELEMENTS_VALUE_TAKE_GLOBAL_VALUE_DESCRIPTION");
				}
			});
			jRadioButtonGlobal.addMouseListener(new java.awt.event.MouseAdapter() {   
				public void mouseEntered(java.awt.event.MouseEvent e) {    
					getJTextPaneDescription().setText(CMMessages.getString("LINK_ELEMENTS_VALUE_TAKE_GLOBAL_VALUE_DESCRIPTION")); 
				}
				public void mouseExited(java.awt.event.MouseEvent e) {
					getJTextPaneDescription().setText(selectDescription);
				}
			});
			jRadioButtonGlobal.addFocusListener(new java.awt.event.FocusAdapter() {   
				public void focusLost(java.awt.event.FocusEvent e) {    
					getJTextPaneDescription().setText(selectDescription);
				}
				public void focusGained(java.awt.event.FocusEvent e) {
					getJTextPaneDescription().setText(CMMessages.getString("LINK_ELEMENTS_VALUE_TAKE_GLOBAL_VALUE_DESCRIPTION")); 
				}
			});
		}
		return jRadioButtonGlobal;
	}

	/**
	 * This method initializes jRadioButton2	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getJRadioButtonLocal() {
		if (jRadioButtonLocal == null) {
			jRadioButtonLocal = new JRadioButton();
			jRadioButtonLocal.setText(CMMessages.getString("LINK_ELEMENTS_VALUE_LOCAL_VALUE_LABEL"));  //$NON-NLS-1$
			jRadioButtonLocal.setFont(new java.awt.Font("SansSerif", java.awt.Font.PLAIN, 12));
			jRadioButtonLocal.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setLocalSelected(true);
					selectDescription=CMMessages.getString("LINK_ELEMENTS_VALUE_TAKE_LOCAL_VALUE_DESCRIPTION"); 
				}
			});
			jRadioButtonLocal.addMouseListener(new java.awt.event.MouseAdapter() {   
				public void mouseEntered(java.awt.event.MouseEvent e) {    
					getJTextPaneDescription().setText(CMMessages.getString("LINK_ELEMENTS_VALUE_TAKE_LOCAL_VALUE_DESCRIPTION")); 
				}
				public void mouseExited(java.awt.event.MouseEvent e) {
					getJTextPaneDescription().setText(selectDescription);
				}
			});
			jRadioButtonLocal.addFocusListener(new java.awt.event.FocusAdapter() {   
				public void focusLost(java.awt.event.FocusEvent e) {    
					getJTextPaneDescription().setText(selectDescription);
				}
				public void focusGained(java.awt.event.FocusEvent e) {
					getJTextPaneDescription().setText(CMMessages.getString("LINK_ELEMENTS_VALUE_TAKE_LOCAL_VALUE_DESCRIPTION")); 
				}
			});
		}
		return jRadioButtonLocal;
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
			jPanelDescription.setBorder(javax.swing.BorderFactory.createCompoundBorder(null, null));
			jPanelDescription.setPreferredSize(new java.awt.Dimension(260,50));
			jPanelDescription.add(getJTextPaneDescription(), java.awt.BorderLayout.CENTER);
		}
		return jPanelDescription;
	}

	/**
	 * This method initializes jTextPaneDescription	
	 * 	
	 * @return javax.swing.JTextPane	
	 */
	private JTextPane getJTextPaneDescription() {
		if (jTextPaneDescription == null) {
			jTextPaneDescription = new JTextPane();
			jTextPaneDescription.setBackground(this.getBackground());
			jTextPaneDescription.setFont(new java.awt.Font("SansSerif", java.awt.Font.PLAIN, 12));
			jTextPaneDescription.setEditable(false);
			jTextPaneDescription.setPreferredSize(new java.awt.Dimension(0,0));
		}
		return jTextPaneDescription;
	}

	/**
	 * @return Returns the okSelected.
	 */
	public boolean isOkSelected() {
		return okSelected;
	}

	/**
	 * @param okSelected The okSelected to set.
	 */
	public void setOkSelected(boolean okSelected) {
		this.okSelected = okSelected;
	}

	/**
	 * @return Returns the localSelected.
	 */
	public boolean isLocalSelected() {
		return localSelected;
	}

	/**
	 * @param localSelected The localSelected to set.
	 */
	public void setLocalSelected(boolean localSelected) {
		this.localSelected = localSelected;
	}

}  //  @jve:decl-index=0:visual-constraint="90,20"
