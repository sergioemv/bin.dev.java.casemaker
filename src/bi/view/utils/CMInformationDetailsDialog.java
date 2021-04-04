package bi.view.utils;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Arrays;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.SoftBevelBorder;

import bi.view.icons.CMIcon;
import bi.view.lang.CMMessages;

public class CMInformationDetailsDialog extends CMBaseJDialog {

	private JPanel jPanelContainer = null;
	private JPanel jPanelUpperTitle = null;
	private JPanel jPanelButtons = null;
	private JButton jButtonOk = null;
	private JButton jButtonDetails = null;
	private JLabel jLabelMainmessage = null;
	private JPanel jPanelButtonsOk = null;
	private JPanel jPanelButtonsDetails = null;
	private boolean messagesVisible = false;
	private JScrollPane jScrollPaneMessages = null;
	private JTextPane jTextPaneMessajes = null;
	/**
	 * This method initializes 
	 * 
	 */
	public CMInformationDetailsDialog() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {

		setSize(new Dimension(369, 117));
        this.setResizable(false);
        this.setModal(true);
        this.setContentPane(getJPanelContainer());
        this.addKeyListenertoAll(this, getDefaultKeyListener());
			
	}

	@Override
	protected void fireButtonCancel() {
		getJButtonOk().doClick();

	}

	@Override
	protected void fireButtonOk() {
		getJButtonOk().doClick();

	}

	@Override
	public JButton getDefaultButton() {
		
		return getJButtonOk();
	}

	@Override
	protected List getOrder() {
		
		
		return  (Arrays.asList(getJButtonOk(),getJButtonDetails()));
	}

	/**
	 * This method initializes jPanelContainer	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelContainer() {
		if (jPanelContainer == null) {
			jPanelContainer = new JPanel();
			jPanelContainer.setLayout(new BoxLayout(getJPanelContainer(), BoxLayout.Y_AXIS));
			jPanelContainer.setPreferredSize(new Dimension(371, 180));
			jPanelContainer.add(getJPanelUpperTitle(), null);
			jPanelContainer.add(getJPanelButtons(), null);
			jPanelContainer.add(getJScrollPaneMessages(), null);
		}
		return jPanelContainer;
	}

	/**
	 * This method initializes jPanelUpperTitle	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelUpperTitle() {
		if (jPanelUpperTitle == null) {
			FlowLayout flowLayout1 = new FlowLayout();
			flowLayout1.setHgap(5);
			flowLayout1.setVgap(15);
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints.gridx = -1;
			gridBagConstraints.gridy = -1;
			gridBagConstraints.insets = new Insets(10, 0, 10, 0);
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.fill = GridBagConstraints.BOTH;
			gridBagConstraints3.weighty = 1.0;
			gridBagConstraints3.gridx = 0;
			gridBagConstraints3.gridy = 2;
			gridBagConstraints3.weightx = 1.0;
			jLabelMainmessage = new JLabel();
			jLabelMainmessage.setText("<html>"+CMMessages.getString("INFODIALOG_QUESTION")+"</html>"); //$NON-NLS-1$
			jLabelMainmessage.setFont(new Font("Dialog", Font.PLAIN, 11));
			jLabelMainmessage.setIcon(CMIcon.WARNING.getImageIcon());
		//	jLabelMainmessage.setIcon(new ImageIcon(getClass().getResource("/bi/view/mainframeviews/icons/Information.gif")));
			jPanelUpperTitle = new JPanel();
			jPanelUpperTitle.setLayout(flowLayout1);
			jPanelUpperTitle.setPreferredSize(new Dimension(357, 50));
			jPanelUpperTitle.add(jLabelMainmessage, null);
		}
		return jPanelUpperTitle;
	}

	/**
	 * This method initializes jPanelButtons	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelButtons() {
		if (jPanelButtons == null) {
			jPanelButtons = new JPanel();
			jPanelButtons.setLayout(new BorderLayout());
			jPanelButtons.setPreferredSize(new Dimension(371, 33));
			jPanelButtons.setMinimumSize(new Dimension(371, 23));
			jPanelButtons.add(getJPanelButtonsOk(), BorderLayout.CENTER);
			jPanelButtons.add(getJPanelButtonsDetails(), BorderLayout.EAST);
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
			jButtonOk.setText("OK"); //$NON-NLS-1$
			jButtonOk.setPreferredSize(new Dimension(90, 23));
			jButtonOk.setMinimumSize(new Dimension(90, 23));
			jButtonOk.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setVisible(false);
				}
			});
		}
		return jButtonOk;
	}

	/**
	 * This method initializes jButtonDetails	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonDetails() {
		if (jButtonDetails == null) {
			jButtonDetails = new JButton();
			jButtonDetails.setText(CMMessages.getString("INFODIALOG_DETAILS1")); //$NON-NLS-1$

			jButtonDetails.setPreferredSize(new Dimension(90, 23));
			jButtonDetails.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					
					if (isMessagesVisible()){
						jButtonDetails.setText(CMMessages.getString("INFODIALOG_DETAILS1"));			 //$NON-NLS-1$
						setPreferredSize(new Dimension(getSize().width, 117));
						
						getJTextPaneMessajes().setPreferredSize(new Dimension(0,0));
						getJScrollPaneMessages().setPreferredSize(new Dimension(0,0));
						pack();
					}
					else{
						jButtonDetails.setText(CMMessages.getString("INFODIALOG_DETAILS2")); //$NON-NLS-1$
						setPreferredSize(new Dimension(getSize().width, 400));
						//getJScrollPaneMessages().setVisible(true);
						getJScrollPaneMessages().setPreferredSize(new Dimension(100,400));
						getJTextPaneMessajes().setPreferredSize(new Dimension(100,400));
				//		getJListMessages().setModel(new DefaultListModel());
				
				//		for(int i= 0;i<100;i++)
				//			((DefaultListModel)getJListMessages().getModel()).addElement(i);
						
						pack();
					}
					setMessagesVisible(!isMessagesVisible());
				}
			});
		}
		return jButtonDetails;
	}

	/**
	 * This method initializes jPanelButtonsOk	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelButtonsOk() {
		if (jPanelButtonsOk == null) {
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.insets = new Insets(10, 139, 10, 45);
			gridBagConstraints2.gridy = 0;
			gridBagConstraints2.gridx = 0;
			jPanelButtonsOk = new JPanel();
			jPanelButtonsOk.setLayout(new GridBagLayout());
			jPanelButtonsOk.setPreferredSize(new Dimension(49, 33));
			jPanelButtonsOk.add(getJButtonOk(), gridBagConstraints2);
		}
		return jPanelButtonsOk;
	}

	/**
	 * This method initializes jPanelButtonsDetails	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelButtonsDetails() {
		if (jPanelButtonsDetails == null) {
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridwidth = 0;
			gridBagConstraints1.insets = new Insets(0, 0, 0, 0);
			gridBagConstraints1.anchor = GridBagConstraints.EAST;
			gridBagConstraints1.gridheight = 0;
			jPanelButtonsDetails = new JPanel();
			jPanelButtonsDetails.setLayout(new GridBagLayout());
			jPanelButtonsDetails.setPreferredSize(new Dimension(120, 33));
			jPanelButtonsDetails.setMinimumSize(new Dimension(93, 23));
			jPanelButtonsDetails.add(getJButtonDetails(), gridBagConstraints1);
		}
		return jPanelButtonsDetails;
	}

	private boolean isMessagesVisible() {
		return messagesVisible;
	}

	private void setMessagesVisible(boolean messagesVisible) {
		this.messagesVisible = messagesVisible;
	}

	/**
	 * This method initializes jScrollPaneMessages	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPaneMessages() {
		if (jScrollPaneMessages == null) {
			jScrollPaneMessages = new JScrollPane();
			jScrollPaneMessages.setPreferredSize(new Dimension(0, 0));
			jScrollPaneMessages.setViewportBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
			jScrollPaneMessages.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			jScrollPaneMessages.setViewportView(getJTextPaneMessajes());
			jScrollPaneMessages.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			
		}
		return jScrollPaneMessages;
	}

	/**
	 * This method initializes jTextPaneMessajes	
	 * 	
	 * @return javax.swing.JTextPane	
	 */
	public JTextPane getJTextPaneMessajes() {
		if (jTextPaneMessajes == null) {
			jTextPaneMessajes = new JTextPane();
			//jTextPaneMessajes.setText("oasdnfoasndfopasndf paosjdfpoa ijfp asoidfj ajsdfoiansdofnapdfn andfoandpofinadpo finadp ofiand pofainfd a\noasdnfoasndfopasndf paosjdfpoa ijfp asoidfj ajsdfoiansdofnapdfn andfoandpofinadpo finadp ofiand pofainfd a\noasdnfoasndfopasndf paosjdfpoa ijfp asoidfj ajsdfoiansdofnapdfn andfoandpofinadpo finadp ofiand pofainfd a\noasdnfoasndfopasndf paosjdfpoa ijfp asoidfj ajsdfoiansdofnapdfn andfoandpofinadpo finadp ofiand pofainfd a\noasdnfoasndfopasndf paosjdfpoa ijfp asoidfj ajsdfoiansdofnapdfn andfoandpofinadpo finadp ofiand pofainfd a\noasdnfoasndfopasndf paosjdfpoa ijfp asoidfj ajsdfoiansdofnapdfn andfoandpofinadpo finadp ofiand pofainfd a\noasdnfoasndfopasndf paosjdfpoa ijfp asoidfj ajsdfoiansdofnapdfn andfoandpofinadpo finadp ofiand pofainfd a\noasdnfoasndfopasndf paosjdfpoa ijfp asoidfj ajsdfoiansdofnapdfn andfoandpofinadpo finadp ofiand pofainfd a\noasdnfoasndfopasndf paosjdfpoa ijfp asoidfj ajsdfoiansdofnapdfn andfoandpofinadpo finadp ofiand pofainfd a\noasdnfoasndfopasndf paosjdfpoa ijfp asoidfj ajsdfoiansdofnapdfn andfoandpofinadpo finadp ofiand pofainfd a\noasdnfoasndfopasndf paosjdfpoa ijfp asoidfj ajsdfoiansdofnapdfn andfoandpofinadpo finadp ofiand pofainfd a\noasdnfoasndfopasndf paosjdfpoa ijfp asoidfj ajsdfoiansdofnapdfn andfoandpofinadpo finadp ofiand pofainfd a\noasdnfoasndfopasndf paosjdfpoa ijfp asoidfj ajsdfoiansdofnapdfn andfoandpofinadpo finadp ofiand pofainfd a\noasdnfoasndfopasndf paosjdfpoa ijfp asoidfj ajsdfoiansdofnapdfn andfoandpofinadpo finadp ofiand pofainfd a\noasdnfoasndfopasndf paosjdfpoa ijfp asoidfj ajsdfoiansdofnapdfn andfoandpofinadpo finadp ofiand pofainfd a\noasdnfoasndfopasndf paosjdfpoa ijfp asoidfj ajsdfoiansdofnapdfn andfoandpofinadpo finadp ofiand pofainfd a\noasdnfoasndfopasndf paosjdfpoa ijfp asoidfj ajsdfoiansdofnapdfn andfoandpofinadpo finadp ofiand pofainfd a\noasdnfoasndfopasndf paosjdfpoa ijfp asoidfj ajsdfoiansdofnapdfn andfoandpofinadpo finadp ofiand pofainfd a\noasdnfoasndfopasndf paosjdfpoa ijfp asoidfj ajsdfoiansdofnapdfn andfoandpofinadpo finadp ofiand pofainfd a\noasdnfoasndfopasndf paosjdfpoa ijfp asoidfj ajsdfoiansdofnapdfn andfoandpofinadpo finadp ofiand pofainfd a\noasdnfoasndfopasndf paosjdfpoa ijfp asoidfj ajsdfoiansdofnapdfn andfoandpofinadpo finadp ofiand pofainfd a\noasdnfoasndfopasndf paosjdfpoa ijfp asoidfj ajsdfoiansdofnapdfn andfoandpofinadpo finadp ofiand pofainfd a\noasdnfoasndfopasndf paosjdfpoa ijfp asoidfj ajsdfoiansdofnapdfn andfoandpofinadpo finadp ofiand pofainfd a\noasdnfoasndfopasndf paosjdfpoa ijfp asoidfj ajsdfoiansdofnapdfn andfoandpofinadpo finadp ofiand pofainfd a\noasdnfoasndfopasndf paosjdfpoa ijfp asoidfj ajsdfoiansdofnapdfn andfoandpofinadpo finadp ofiand pofainfd a\noasdnfoasndfopasndf paosjdfpoa ijfp asoidfj ajsdfoiansdofnapdfn andfoandpofinadpo finadp ofiand pofainfd a\noasdnfoasndfopasndf paosjdfpoa ijfp asoidfj ajsdfoiansdofnapdfn andfoandpofinadpo finadp ofiand pofainfd a\noasdnfoasndfopasndf paosjdfpoa ijfp asoidfj ajsdfoiansdofnapdfn andfoandpofinadpo finadp ofiand pofainfd a\noasdnfoasndfopasndf paosjdfpoa ijfp asoidfj ajsdfoiansdofnapdfn andfoandpofinadpo finadp ofiand pofainfd a\noasdnfoasndfopasndf paosjdfpoa ijfp asoidfj ajsdfoiansdofnapdfn andfoandpofinadpo finadp ofiand pofainfd a\noasdnfoasndfopasndf paosjdfpoa ijfp asoidfj ajsdfoiansdofnapdfn andfoandpofinadpo finadp ofiand pofainfd a\noasdnfoasndfopasndf paosjdfpoa ijfp asoidfj ajsdfoiansdofnapdfn andfoandpofinadpo finadp ofiand pofainfd a\n");
			//jTextPaneMessajes.setContentType("text/html");
			jTextPaneMessajes.setFont(new Font("Courier New", Font.PLAIN, 12));
			jTextPaneMessajes.setEditable(false);
		}
		return jTextPaneMessajes;
	}

}  //  @jve:decl-index=0:visual-constraint="47,10"
