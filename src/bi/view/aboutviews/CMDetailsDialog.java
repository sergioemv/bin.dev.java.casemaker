package bi.view.aboutviews;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import bi.view.icons.CMIcon;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.testdataviews.CMStyleModelHeader;
import bi.view.utils.CMBaseJDialog;

import com.eliad.swing.JSmartGrid;
import com.eliad.swing.JSmartGridHeader;

@SuppressWarnings("serial")
public class CMDetailsDialog extends CMBaseJDialog {

	private JPanel jContentPanel = null;
	private JScrollPane jGridDetailPanel = null;
	private JPanel jInformationPanel = null;
	private JPanel jButtonsPanel = null;
	private JButton jButtonOk = null;
	private JLabel jImageLabel = null;
	private JLabel jCopyRightLabel = null;
	private JLabel jLibraryLabel = null;
	
	private String copyrights;
	private String libraryVersion;
	private	CMFileDetailsGrid m_CMFileDetailsGrid= null;  
	private JSmartGridHeader m_GridHeader;  
	CMFileDetailsGridHeaderModel m_CMFileDetailsHeaderView;
	private JPanel jPanel = null;
	private JPanel jPanel1 = null;
	private JPanel jDetailPanel= null;
	private JPanel jPanel2 = null;
	private JPanel jPanel3 = null;
	private JPanel jPanel4 = null; 
	public CMDetailsDialog(String p_copyrights, String p_version) {
		super(CMApplication.frame);
		copyrights=p_copyrights;
		libraryVersion= p_version;
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        this.setContentPane(getJContentPanel());
        this.setTitle(CMMessages.getString("TITLE_DETAILS_DIALOG"));
        this.addKeyListenertoAll(this,this.getDefaultKeyListener());
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                closeDialog(evt);
            }
        });
        pack();			
	}
	private void closeDialog(WindowEvent evt) {
        setVisible(false);
        dispose();
    }

	@Override
	public JButton getDefaultButton() {
		return getJButtonOk();
	}

	@SuppressWarnings("unchecked")
	@Override
	protected List getOrder() {
		ArrayList order= new ArrayList();
		order.add(jButtonOk);
		return order;
	}

	@Override
	protected void fireButtonOk() {
		setVisible(false);
        dispose();
	}

	@Override
	protected void fireButtonCancel() {
		setVisible(false);
        dispose();
	}

	/**
	 * This method initializes jContentPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJContentPanel() {
		if (jContentPanel == null) {
			BorderLayout borderLayout = new BorderLayout();
			borderLayout.setHgap(0);
			borderLayout.setVgap(0);
			jContentPanel = new JPanel();
			jContentPanel.setLayout(borderLayout);
			jContentPanel.add(getJInformationPanel(), java.awt.BorderLayout.CENTER);
			jContentPanel.add(getJButtonsPanel(), java.awt.BorderLayout.SOUTH);
			jContentPanel.add(getJPanel(), java.awt.BorderLayout.EAST);
			jContentPanel.add(getJPanel1(), java.awt.BorderLayout.WEST);
			jContentPanel.add(getJDetailPanel(), java.awt.BorderLayout.NORTH);
		}
		return jContentPanel;
	}

	private JPanel getJDetailPanel() {
		if(jDetailPanel== null){
			jDetailPanel= new JPanel();
			jDetailPanel.setLayout(new BorderLayout());
			jDetailPanel.add(getJGridDetailPanel(),BorderLayout.CENTER);
			jDetailPanel.add(getJPanel2(), java.awt.BorderLayout.NORTH);
			jDetailPanel.add(getJPanel3(), java.awt.BorderLayout.EAST);
			jDetailPanel.add(getJPanel4(), java.awt.BorderLayout.WEST);
		}
		return jDetailPanel;
	}

	/**
	 * This method initializes jGridDetailPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private  JScrollPane getJGridDetailPanel() {
		if (jGridDetailPanel == null) {
			jGridDetailPanel = new JScrollPane(getM_CMFileDetailsGrid());
			jGridDetailPanel.setColumnHeaderView(getM_FileDetailsGridHeader());
			//jGridDetailPanel.setViewportView(getM_CMFileDetailsGrid());
		}
		return jGridDetailPanel;
	}

	/**
	 * This method initializes jInformationPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJInformationPanel() {
		if (jInformationPanel == null) {
			BorderLayout borderLayout1 = new BorderLayout();
			borderLayout1.setHgap(0);
			borderLayout1.setVgap(0);
			jInformationPanel = new JPanel();
			jInformationPanel.setLayout(borderLayout1);
			jInformationPanel.add(getJImageLabel(), java.awt.BorderLayout.NORTH);
			jInformationPanel.add(getJCopyRightLabel(), java.awt.BorderLayout.CENTER);
			jInformationPanel.add(getJLibraryLabel(), java.awt.BorderLayout.SOUTH);
		}
		return jInformationPanel;
	}

	private Component getJLibraryLabel() {
		if(jLibraryLabel== null){
			jLibraryLabel = new JLabel();
			jLibraryLabel.setText(getLibraryVersion());
			jLibraryLabel.setToolTipText(""); //$NON-NLS-1$
			jLibraryLabel.setFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 9));
			jLibraryLabel.setHorizontalAlignment(SwingConstants.CENTER);
			jLibraryLabel.setHorizontalTextPosition(SwingConstants.CENTER);

		}
		return jLibraryLabel;
	}

	private Component getJCopyRightLabel() {
		if(jCopyRightLabel== null){
			jCopyRightLabel = new JLabel();
			jCopyRightLabel.setText(getCopyrights());
			jCopyRightLabel.setToolTipText(""); //$NON-NLS-1$
			jCopyRightLabel.setFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 9));
			jCopyRightLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

		}
		return jCopyRightLabel;
	}

	private Component getJImageLabel() {
		if(jImageLabel== null){
			jImageLabel = new JLabel();
			jImageLabel.setText("");
			jImageLabel.setIcon( CMIcon.CASEMAKER_RIGHT.getImageIcon());
		}
		return jImageLabel;
	}

	/**
	 * This method initializes jButtonsPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJButtonsPanel() {
		if (jButtonsPanel == null) {
			FlowLayout flowLayout = new FlowLayout();
			flowLayout.setAlignment(java.awt.FlowLayout.RIGHT);
			flowLayout.setHgap(12);
			flowLayout.setVgap(12);
			jButtonsPanel = new JPanel();
			jButtonsPanel.setLayout(flowLayout);
			jButtonsPanel.setLayout(flowLayout);
			jButtonsPanel.add(getJButtonOk(), null);
		}
		return jButtonsPanel;
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
			jButtonOk.addActionListener(new ActionListener() {
	                public void actionPerformed(ActionEvent e) {
	                    setVisible(false);
	                    dispose();
	                }
	            });
		}
		return jButtonOk;
	}

	public String getCopyrights() {
		return copyrights;
	}

	public void setCopyrights(String copyrights) {
		this.copyrights = copyrights;
	}

	public String getLibraryVersion() {
		return libraryVersion;
	}

	public void setLibraryVersion(String libraryVersion) {
		this.libraryVersion = libraryVersion;
	}

	private CMFileDetailsGrid getM_CMFileDetailsGrid() {
		if(m_CMFileDetailsGrid== null){
			m_CMFileDetailsGrid= new CMFileDetailsGrid();
			m_CMFileDetailsGrid.setAutoCreateColumnHeader(false);
			m_CMFileDetailsGrid.setColumnHeader(getM_FileDetailsGridHeader());
		}
		return m_CMFileDetailsGrid;
	}
	private JSmartGridHeader getM_FileDetailsGridHeader() {
		if(m_GridHeader== null){
			m_CMFileDetailsHeaderView= new CMFileDetailsGridHeaderModel(m_CMFileDetailsGrid);
			m_GridHeader = new JSmartGridHeader(m_CMFileDetailsGrid,JSmartGrid.HORIZONTAL,m_CMFileDetailsHeaderView,null,new CMStyleModelHeader());
			m_GridHeader.setColumnAutoResizeMode(JSmartGridHeader.AUTO_RESIZE_ALL);
			m_GridHeader.setBackground(new Color(36,38,116));
			m_GridHeader.setSelectionBackgroundColor(new Color(36,38,116));
			m_GridHeader.setSelectionForegroundColor(new Color(252,254,252));
			m_GridHeader.setGridColor(new Color(196,194,196));
			m_GridHeader.setFont(new Font("Dialog",Font.PLAIN,12)); //$NON-NLS-1$
			m_GridHeader.setForeground(new Color(252,254,252));
			m_GridHeader.setBorder(BorderFactory.createRaisedBevelBorder());
			m_GridHeader.setRowResizable(false);
		}
		return m_GridHeader;
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setPreferredSize(new java.awt.Dimension(12,12));
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
			jPanel1.setPreferredSize(new java.awt.Dimension(12,12));
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
			jPanel2.setPreferredSize(new java.awt.Dimension(12,12));
		}
		return jPanel2;
	}

	/**
	 * This method initializes jPanel3	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jPanel3 = new JPanel();
			jPanel3.setPreferredSize(new java.awt.Dimension(12,12));
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
			jPanel4.setPreferredSize(new java.awt.Dimension(12,12));
		}
		return jPanel4;
	}
}  
