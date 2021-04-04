/*******************************************************************************
Developed by BUSINESS SOFTWARE INNOVATIONS. .
Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
*******************************************************************************/


package  bi.view.aboutviews;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import model.License;
import bi.controller.LicenseManager;
import bi.view.icons.CMIcon;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.mainframeviews.CMFrameView;
import bi.view.utils.CMBaseJDialog;

@SuppressWarnings("serial")
public class CMAboutDialog extends CMBaseJDialog {
    private String title = CMMessages.getString("LABEL_ABOUT"); //$NON-NLS-1$
  //  private String product = CMMessages.getString("LABEL_PRODUCT"); //$NON-NLS-1$
    private String version = "1.0";
    private String LibraryVersion = "";
  //  private String copyright = CMMessages.getString("LABEL_COPYRIGHT"); //$NON-NLS-1$
  //  private String comments = CMMessages.getString("LABEL_PRODUCT_COMMENTS"); //$NON-NLS-1$
    private JPanel myContentPane = null;//new JPanel();
    private JLabel copLabel = null;//new JLabel();
    private JLabel coplibraryversionLabel =null;// new JLabel();    
    private JPanel btnPanel = null;//new JPanel();
    private JButton okButton = null;//new JButton();
    private JLabel image =null;// new JLabel();
    //private BorderLayout formLayout = new BorderLayout();
   // private GridBagLayout contentPaneLayout = new GridBagLayout();
    private CMFrameView m_CMFrameView = null;

    private JButton detailsButton= null;
	private JPanel jPanelPrincipal = null; 
    /** Creates new About Dialog */
    public CMAboutDialog(CMFrameView parent, boolean modal) {
        super(parent, false);
        m_CMFrameView = parent;
        initialize();
    }

    public CMAboutDialog() {
        super(CMApplication.frame);
        setModal(true);
        initialize();
    }

    /** This method is called from within the constructor to initialize the dialog. */
    private void initialize() {             
    	this.setTitle(title);
    	this.setContentPane(getJPanelPrincipal());
        this.setResizable(false);
        this.addKeyListenertoAll(this,this.getDefaultKeyListener());
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                closeDialog(evt);
            }
            
        });
        
        pack();
    }

    /** Closes the dialog */
    private void closeDialog(WindowEvent evt) {
        setVisible(false);
        dispose();
    }

	@SuppressWarnings("unchecked")
	protected List getOrder() {
		ArrayList order= new ArrayList();
		order.add(getOkButton());
		order.add(getDetailsButton());
		return order;
	}

	protected void fireButtonOk() {
		  setVisible(false);
          dispose();
		
	}

	protected void fireButtonCancel() {
		  setVisible(false);
          dispose();
		
	}

	public JButton getDefaultButton() {
		JButton defaultB=getOkButton();
		defaultB.requestFocus();
		return defaultB;
	}

	private JButton getDetailsButton() {
		if(detailsButton == null){
			detailsButton = new JButton();
			detailsButton.setText(CMMessages.getString("BUTTON_DETAILS")); //$NON-NLS-1$
			detailsButton.setPreferredSize(new java.awt.Dimension(90,23));
			detailsButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					detailsButtonActionPerformed();
				}
			});
		}
		return detailsButton;
	}

	protected void detailsButtonActionPerformed() {
		CMDetailsDialog cmd=new CMDetailsDialog(copLabel.getText(),coplibraryversionLabel.getText());
		cmd.setVisible(true);
	}

	private JButton getOkButton() {
		if(okButton== null){
			okButton= new  JButton();
			okButton.setText(CMMessages.getString("BUTTON_OK")); //$NON-NLS-1$
			okButton.setPreferredSize(new java.awt.Dimension(90,23));
			okButton.requestFocus();
	        okButton.addActionListener(new ActionListener() {
	                public void actionPerformed(ActionEvent e) {
	                    setVisible(false);
	                    dispose();
	                }
	            });
		}
		return okButton;
	}

	private JPanel getBtnPanel() {
		if(btnPanel== null){
			btnPanel= new JPanel();
			btnPanel.setLayout(new BoxLayout(btnPanel,BoxLayout.X_AXIS));
			Dimension prefSize = new Dimension(12,47);
			btnPanel.add(new Box.Filler(prefSize, prefSize, prefSize));
			btnPanel.add(getDetailsButton());
	        btnPanel.add(Box.createHorizontalGlue());
	        btnPanel.add(getOkButton());
	       btnPanel.add(new Box.Filler(prefSize, prefSize, prefSize));
		}
		return btnPanel;
	}

	private String getCopyrightsLabel(){
		String m_InstallationDirectory = m_CMFrameView.getCmApplication().getSessionManager().getM_InstallationDirectory();
    	LicenseManager LM = new LicenseManager();
        String clientName = null;
        String serialNumber = null;
        String ClientType = LM.getClientType();
        version = "Ver. "+model.BusinessRules.APPLICATIONVERSION +" Build "+model.BusinessRules.BUILDDATE +"."+model.BusinessRules.BUILDNUMBER;
        LibraryVersion = "License "+ClientType+" Ver. "+LM.getLicenseVersion();
        if (ClientType.equals("Stand-Alone"))
        {	       	
          License existingLicense = LM.getExistingLicense(m_InstallationDirectory);
		  if( existingLicense != null) {
            clientName = LM.getExistingClientName(existingLicense);
            serialNumber = LM.getExistingSerialNumber(existingLicense);
          }
          else {
            clientName = CMMessages.getString("LABEL_UNKNOWN_CLIENT"); //$NON-NLS-1$
            serialNumber = CMMessages.getString("LABEL_UNKNOWN"); //$NON-NLS-1$
          }
        }  
        else
        {
           clientName = "NetClient";
           serialNumber = "SERVER";    	   
        }
        StringBuffer sBuffer = new StringBuffer();
        sBuffer.append(version);
		sBuffer.append("  ");
        sBuffer.append(CMMessages.getString("LABEL_LICENSED_FOR")); //$NON-NLS-1$
        sBuffer.append(clientName);
        sBuffer.append("  ");
        sBuffer.append(CMMessages.getString("LABEL_SERIAL_NUMBER")); //$NON-NLS-1$
        sBuffer.append(serialNumber);
        return sBuffer.toString();
	}

	private JPanel getMyContentPane() {
		if(myContentPane== null){
			myContentPane= new JPanel();
			myContentPane.setLayout(new java.awt.BorderLayout());
	        myContentPane.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 5));
	        myContentPane.setFont(new java.awt.Font("Garamond",java.awt.Font.PLAIN,12));
	        myContentPane.add(getCopLabel(), java.awt.BorderLayout.CENTER);
	        myContentPane.add(getCoplibraryversionLabel(), BorderLayout.SOUTH);
		}
		return myContentPane;
	}

	private JLabel getCopLabel() {
		if(copLabel == null){
			copLabel= new JLabel();
			copLabel.setText(getCopyrightsLabel());
	        copLabel.setToolTipText(""); //$NON-NLS-1$
	        copLabel.setFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 9));
	        copLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		}
		return copLabel;
	}

	private JLabel getCoplibraryversionLabel() {
		if(coplibraryversionLabel==null){
			coplibraryversionLabel= new JLabel();
			coplibraryversionLabel.setText(LibraryVersion);
	        coplibraryversionLabel.setToolTipText(""); //$NON-NLS-1$
	        coplibraryversionLabel.setFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 9));
	        coplibraryversionLabel.setHorizontalAlignment(SwingConstants.CENTER);
	        coplibraryversionLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		}
		return coplibraryversionLabel;
	}

	private JLabel getImage() {
		if(image== null){
			
			image= new JLabel();
			image.setText("");
	        image.setIcon(CMIcon.ABOUT.getImageIcon());
	        image.setAlignmentY(0.0f);
	        image.setBorder(null);
	        image.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
	        image.setPreferredSize(new java.awt.Dimension(514,304));
	        image.setMinimumSize(new java.awt.Dimension(514,304));
	        image.setMaximumSize(new java.awt.Dimension(514,304));
		}
		return image;
	}

	/**
	 * This method initializes jPanelPrincipal	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelPrincipal() {
		if (jPanelPrincipal == null) {
			jPanelPrincipal = new JPanel();
			jPanelPrincipal.setLayout(new BorderLayout());
			jPanelPrincipal.add(getImage(), java.awt.BorderLayout.NORTH);
			jPanelPrincipal.add(getMyContentPane(), java.awt.BorderLayout.CENTER);
			jPanelPrincipal.add(getBtnPanel(), java.awt.BorderLayout.SOUTH);
		}
		return jPanelPrincipal;
	}
}
