package bi.view.businessrulesviews.dialogs;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import bi.view.icons.CMIcon;
import bi.view.lang.CMMessages;
import bi.view.utils.CMBaseJDialog;

public class CMLogVisioImportProblems extends CMBaseJDialog {
	private JPanel jPanelContent = null;
	private JPanel jPanelButtons = null;
	private JButton jButtonOk = null;
	private JScrollPane jScrollPaneContents = null;
	private JPanel jPanelList= null;
	private JPanel jPanelWest = null;
	private JPanel jPanelEast = null;
	private JPanel jPanelSource=null;
	private JPanel jPanelWest3 = null;
	private JTextArea jTextAreaLog = null;
	private JPanel jPanelNorth = null;
	private JPanel jPanelSouth = null;
	private JTextArea jTextSource=null;
	private JLabel jLabelIcon = null;
	private JPanel jPanelWest4 = null;
	private JPanel jPanelEast1 = null;
	
	

	public CMLogVisioImportProblems() {
		super();
		initialize();
	}

	public void show(){
		
		super.show();
	}
	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		
        this.setSize(new java.awt.Dimension(430,412));
        this.setModal(true);
        this.setResizable(true);
        this.setContentPane(getJPanelContent());
        addKeyListenertoAll(this, this.getDefaultKeyListener());	
	}

	

	

	@Override
	public JButton getDefaultButton() {
		return jButtonOk;
	}

	@Override
	protected List getOrder() {
		List focusOrder= new ArrayList();
		focusOrder.add(jTextAreaLog);
		focusOrder.add(jButtonOk);
		return focusOrder;
	}

	@Override
	protected void fireButtonOk() {
		jButtonOkactionPerformed();

	}

	@Override
	protected void fireButtonCancel() {
		jButtonOkactionPerformed();
	}

	/**
	 * This method initializes jPanelContent	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelContent() {
		if (jPanelContent == null) {
			BorderLayout borderLayout2 = new BorderLayout();
			borderLayout2.setVgap(0);
			jPanelContent = new JPanel();
			jPanelContent.setLayout(borderLayout2);
			jPanelContent.add(getJPanelButtons(), java.awt.BorderLayout.SOUTH);
			jPanelContent.add(getJPaneList(), java.awt.BorderLayout.CENTER);
		}
		return jPanelContent;
	}

	private Component getJPaneList() {
		if(jPanelList== null){
		BorderLayout borderLayout1 = new BorderLayout();
		borderLayout1.setHgap(0);
		borderLayout1.setVgap(5);
		jPanelList = new JPanel();
		jPanelList.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
		jPanelList.setLayout(borderLayout1);
		jPanelList.add(getJScrollPaneContents(), java.awt.BorderLayout.CENTER);
		jPanelList.add(getJPanelSource(), java.awt.BorderLayout.NORTH);
		jPanelList.add(getJPanelWest(), java.awt.BorderLayout.WEST);
		jPanelList.add(getJPanelEast(), java.awt.BorderLayout.EAST);
		}
		return jPanelList;
	}

	private JPanel getJPanelSource() {
		if(jPanelSource == null){
			BorderLayout borderLayout = new BorderLayout();
			borderLayout.setHgap(0);
			borderLayout.setVgap(10);
			jPanelSource= new JPanel();
			jPanelSource.setPreferredSize(new java.awt.Dimension(422,110));
			jPanelSource.setLayout(borderLayout);
			jPanelSource.add(getjTextSource(), java.awt.BorderLayout.CENTER);
			jPanelSource.add(getJPanelWest3(), java.awt.BorderLayout.WEST);
			jPanelSource.add(getJPanelNorth(), java.awt.BorderLayout.NORTH);
			jPanelSource.add(getJPanelSouth(), java.awt.BorderLayout.SOUTH);
			jPanelSource.add(getJPanelEast1(), java.awt.BorderLayout.EAST);
		}
		return jPanelSource;
	}

	private Component getjTextSource() {
		if(jTextSource== null){
			jTextSource = new JTextArea();
			jTextSource.setEditable(false);
			jTextSource.setEnabled(false);
			jTextSource.setWrapStyleWord(true);
			jTextSource.setLineWrap(true);
			jTextSource.setFont(new java.awt.Font("SansSerif", java.awt.Font.PLAIN, 12)); //$NON-NLS-1$
			jTextSource.setBounds(new java.awt.Rectangle(28, 224, 418, 83));
			jTextSource.setCaretColor(new java.awt.Color(0, 0, 0));
			jTextSource.setBackground(this.getBackground());//new java.awt.Color(212, 208, 200));
			jTextSource.setForeground(this.getForeground());
			jTextSource.setDisabledTextColor(new java.awt.Color(0, 0, 0));
			jTextSource.setPreferredSize(new java.awt.Dimension(350,15));
			jTextSource.setText(CMMessages.getString("BUSINESS_RULES_IMPORT_MESSAGE_PROBLEMS")); //$NON-NLS-1$

		}
		return jTextSource;
	}

	/**
	 * This method initializes jPanelButtons	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelButtons() {
		if (jPanelButtons == null) {
			FlowLayout flowLayout = new FlowLayout();
			flowLayout.setHgap(7);
			flowLayout.setVgap(10);
			jPanelButtons = new JPanel();
			jPanelButtons.setLayout(flowLayout);
			jPanelButtons.add(getJButtonOk());
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
			jButtonOk.setText(CMMessages.getString("BUTTON_OK")); //$NON-NLS-1$
			jButtonOk.setPreferredSize(new java.awt.Dimension(90,23));
			jButtonOk.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					jButtonOkactionPerformed();
				}
			});
		}
		return jButtonOk;
	}

	protected void jButtonOkactionPerformed() {
		this.dispose();
	}

	

	/**
	 * This method initializes jScrollPaneContents	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPaneContents() {
		if (jScrollPaneContents == null) {
			jScrollPaneContents = new JScrollPane();
			jScrollPaneContents.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
			jScrollPaneContents.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
			jScrollPaneContents.setViewportView(getJTextAreaLog());
		}
		return jScrollPaneContents;
	}

		/**
	 * This method initializes jPanelWest	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelWest() {
		if (jPanelWest == null) {
			FlowLayout flowLayout2 = new FlowLayout();
			flowLayout2.setHgap(0);
			flowLayout2.setVgap(0);
			jPanelWest = new JPanel();
			jPanelWest.setLayout(flowLayout2);
			jPanelWest.setPreferredSize(new java.awt.Dimension(10,0));
		}
		return jPanelWest;
	}

	/**
	 * This method initializes jPanelEast	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelEast() {
		if (jPanelEast == null) {
			FlowLayout flowLayout3 = new FlowLayout();
			flowLayout3.setHgap(0);
			flowLayout3.setVgap(0);
			jPanelEast = new JPanel();
			jPanelEast.setLayout(flowLayout3);
			jPanelEast.setPreferredSize(new java.awt.Dimension(10,0));
		}
		return jPanelEast;
	}

	/**
	 * This method initializes jPanelWest3	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelWest3() {
		if (jPanelWest3 == null) {
			jLabelIcon = new JLabel();
			jLabelIcon.setText("");
			jLabelIcon.setPreferredSize(new java.awt.Dimension(48,48));
			jLabelIcon.setIcon(CMIcon.WARNING.getImageIcon());
			jPanelWest3 = new JPanel();
			jPanelWest3.setLayout(new BorderLayout());
			jPanelWest3.setPreferredSize(new java.awt.Dimension(60,48));
			jPanelWest3.add(jLabelIcon, java.awt.BorderLayout.CENTER);
			jPanelWest3.add(getJPanelWest4(), java.awt.BorderLayout.WEST);
		}
		return jPanelWest3;
	}

	/**
	 * This method initializes jTextAreaLog	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getJTextAreaLog() {
		if (jTextAreaLog == null) {
			jTextAreaLog = new JTextArea();
			jTextAreaLog.setEditable(false);
			jTextAreaLog.setEnabled(false);
			jTextAreaLog.setFont(new java.awt.Font("SansSerif", java.awt.Font.PLAIN, 12)); //$NON-NLS-1$
			jTextAreaLog.setBounds(new java.awt.Rectangle(28, 224, 418, 83));
			jTextAreaLog.setCaretColor(new java.awt.Color(0, 0, 0));
			jTextAreaLog.setBackground(this.getBackground());//new java.awt.Color(212, 208, 200));
			jTextAreaLog.setForeground(this.getForeground());
			jTextAreaLog.setDisabledTextColor(new java.awt.Color(0, 0, 0));
		}
		return jTextAreaLog;
	}

	/**
	 * This method initializes jPanelNorth	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelNorth() {
		if (jPanelNorth == null) {
			jPanelNorth = new JPanel();
		}
		return jPanelNorth;
	}

	/**
	 * This method initializes jPanelSouth	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelSouth() {
		if (jPanelSouth == null) {
			jPanelSouth = new JPanel();
		}
		return jPanelSouth;
	}

	public void setLog(String log) {
		jTextAreaLog.setText(log);
	}

	/**
	 * This method initializes jPanelWest4	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelWest4() {
		if (jPanelWest4 == null) {
			jPanelWest4 = new JPanel();
			jPanelWest4.setPreferredSize(new java.awt.Dimension(12,0));
		}
		return jPanelWest4;
	}

	/**
	 * This method initializes jPanelEast1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelEast1() {
		if (jPanelEast1 == null) {
			jPanelEast1 = new JPanel();
			jPanelEast1.setPreferredSize(new java.awt.Dimension(12,0));
		}
		return jPanelEast1;
	}
	
}  //  @jve:decl-index=0:visual-constraint="32,13"