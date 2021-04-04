
/*******************************************************************************
Developed by BUSINESS SOFTWARE INNOVATIONS. .
Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
*******************************************************************************/


package bi.view.projectviews;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.tree.DefaultMutableTreeNode;

import model.BusinessRules;
import model.CMField;
import model.Project2;
import model.ProjectReference;
import model.Session2;
import model.util.CMModelEvent;
import model.util.CMModelListener;
import bi.view.actions.CMAction;
import bi.view.icons.CMIcon;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.utils.CMBaseJPanel;
import bi.view.utils.CMJEditorPaneFocusChangeable;
import bi.view.utils.CMToolBarButton;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.BorderFactory;
import javax.swing.border.BevelBorder;
import java.awt.BorderLayout;
import javax.swing.border.TitledBorder;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JScrollPane;
import java.awt.Dimension;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class CMPanelProjectView extends CMBaseJPanel implements ActionListener, CMModelListener{
//grueda30122004_end

	    private Project2 m_Project;
	    private ProjectReference m_ProjectReference;
	    private JPanel jPanelName;
		private JTextField jTextFieldName;
		private JLabel jLabelName;
		private CMToolBarButton CMToolBarButtonChangeName;
		private JPanel jPanelFilename;
		private JLabel jLabelFilePath;
		private JTextField jTextFieldFilePath;
		private JPanel jPanelCurrentUser;
		private JLabel jLabel;
		private JPanel jPanelCheckInOut;
		private JRadioButton jRadioButtonCheckIn;
		private JRadioButton jRadioButtonChekOut;
		private JPanel jPanelByUser ;
		private JLabel jLabel1;
		private JTextField jTextFieldByUser;
		private JPanel jPanelState ;
		private JPanel jPanelMainContainer ;
		private JPanel jPanelDescription ;
		private CMJEditorPaneFocusChangeable jEditorPaneDescription ;
		private JPanel jPanelBottom ;
		private JPanel jPanelLeft;
		private JPanel jPanelRight ;
		private JPanel jPanelTop;
		private JScrollPane jScrollPaneDescription ;
		private JLabel JLabelCurrentUser = null;
		private JLabel jLabelKey = null;
    /** Creates new form CMPanelProjectView */
    public CMPanelProjectView() {

    	super();//Agregar en todos los JPanel
        initialize();
    }

    /** This method is called from within the constructor to initialize the form. */
    private void initialize() {


        setLayout(new BorderLayout());
        setBorder(null);
       // add(getJButtonChangeName());
        this.setMinimumSize(new java.awt.Dimension(206,38));
        this.setSize(new java.awt.Dimension(516,381));
        this.setLocation(new java.awt.Point(0,0));
        this.add(getJPanelMainContainer(), java.awt.BorderLayout.CENTER);
        this.add(getJPanelLeft(), java.awt.BorderLayout.WEST);
        this.add(getJPanelRight(), java.awt.BorderLayout.EAST);
        this.add(getJPanelTop(), java.awt.BorderLayout.NORTH);
        ButtonGroup group = new ButtonGroup();
        group.add(getJRadioButtonCheckIn());
        group.add(getJRadioButtonChekOut());



        getJTextFieldName().setEditable(false);
    }

    public Project2 getM_Project(){
        return m_Project;
    }

    public void setM_Project2(Project2 p_Project, ProjectReference p_ProjectReference){
      if( p_Project == null || p_ProjectReference == null) {
        return;
      }
      if (p_Project!=null)
      {
          m_Project = p_Project;
          m_Project.addModelListener(this);
    	  getJEditorPaneDescription().setText(m_Project.getM_Description());
    	  getJTextFieldName().setText(m_Project.getName());
          if( m_Project.getAccessState().equals(BusinessRules.ACCESS_STATE_CHECKED_IN) ) {
        	  getJRadioButtonCheckIn().setSelected(true);
          	}
          	else if( m_Project.getAccessState().equals(BusinessRules.ACCESS_STATE_CHECKED_OUT) ) {
          		getJRadioButtonChekOut().setSelected(true);
          	}
          getJTextFieldByUser().setText(m_Project.getUser());
      }

      	m_ProjectReference = p_ProjectReference;
      	m_ProjectReference.addModelListener(this);
        getJTextFieldFilePath().setText(m_ProjectReference.getFilePath());
        JLabelCurrentUser.setText(CMApplication.frame.getTreeWorkspaceView().getM_Session2().getM_User());
        CMApplication.frame.setStateProjectSelected();
        this.updateViewStates();
        this.updateCommandoStates();
       //grueda30122004_end
    }

    public ProjectReference getM_ProjectReference(){
        return m_ProjectReference;
    }
    void updateCommandoStates(){
      Session2 session = CMApplication.frame.getTreeWorkspaceView().getM_Session2();
      if( m_Project.getAccessState().equals(BusinessRules.ACCESS_STATE_CHECKED_IN)) {

    	 //CMAction.CREATE.setEnabled(false);
     	 CMAction.ADDOPEN.setEnabled(false);
     	 CMAction.SAVE.setEnabled(false);
        /*
	   -Menus Deactivated:
	   -Create TestObject
	   -Add Test Object
	   -Save
       */
      }
      if( m_Project.getAccessState().equals(BusinessRules.ACCESS_STATE_CHECKED_OUT)
        && m_Project.getUser().equalsIgnoreCase(session.getM_User())
        /*&& !m_Frame.getCmApplication().getSessionManager().getM_WorkspaceManager().areCheckedOutLocalAndCheckedInTestObjectsUnderCurrentProject(m_Project)*/ ) {

     	// CMAction.CREATE.setEnabled(true);
     	 CMAction.ADDOPEN.setEnabled(true);
     	CMAction.SAVE.setEnabled(true);      }
      else{
     	// CMAction.CREATE.setEnabled(false);
     	 CMAction.ADDOPEN.setEnabled(false);
     	CMAction.SAVE.setEnabled(false);
      }

    }

    void updateViewStates() {
      Session2 session = CMApplication.frame.getTreeWorkspaceView().getM_Session2();
      if( m_Project.getAccessState().equals(BusinessRules.ACCESS_STATE_CHECKED_IN)){//|| m_Frame.getTreeWorkspaceView().isCurrentProjectContainsTestObjectsLocal()) {
        CMAction.RENAME_PROJECT.setEnabled(false);
        getJEditorPaneDescription().setEditable(false);
        getJRadioButtonCheckIn().setEnabled(true);
        getJRadioButtonChekOut().setEnabled(true);
      }
      if(CMApplication.frame.getTreeWorkspaceView().isCurrentProjectContainsTestObjectsLocal()) {
    	  CMAction.RENAME_PROJECT.setEnabled(false);
    	  getJEditorPaneDescription().setEditable(false);
      }
      if( m_Project.getAccessState().equals(BusinessRules.ACCESS_STATE_CHECKED_OUT) && !(CMApplication.frame.getTreeWorkspaceView().isCurrentProjectContainsTestObjectsLocal())) {
          if( m_Project.getUser().equalsIgnoreCase(session.getM_User())
              /*&& !m_Frame.getCmApplication().getSessionManager().getM_WorkspaceManager().areCheckedOutLocalAndCheckedInTestObjectsUnderCurrentProject(m_Project)*/) {
        	  CMAction.RENAME_PROJECT.setEnabled(true);
        	  getJEditorPaneDescription().setEditable(true);
            getJRadioButtonCheckIn().setEnabled(true);
            getJRadioButtonChekOut().setEnabled(true);
          }
          else {
        	  CMAction.RENAME_PROJECT.setEnabled(true);
        	  getJEditorPaneDescription().setEditable(false);
            getJRadioButtonCheckIn().setEnabled(false);
            getJRadioButtonChekOut().setEnabled(false);
          }
       }
    }


    public void jEditorDescriptionCaretUpdate(CaretEvent e) {

	if(!m_Project.getM_Description().equals(getJEditorPaneDescription().getText()))
    {
	  int saveUnsave=CMApplication.frame.getCmApplication().getSessionManager().getM_Save_UnsaveVariable()+1;
	  CMApplication.frame.getCmApplication().getSessionManager().setM_Save_UnsaveVariable(saveUnsave);
    }
//HCanedo_03012005_End
	CMApplication.frame.getTreeWorkspaceView().changedProjectDescription2(getJEditorPaneDescription().getText());
	  m_Project.setM_Description(getJEditorPaneDescription().getText());
    }

    //grueda30122004_begin
    public void actionPerformed(ActionEvent e) {
      String actionCommand = e.getActionCommand();
      if( actionCommand.equals(BusinessRules.ACCESS_STATE_CHECKED_IN) ) {
        if( !m_Project.getAccessState().equals(BusinessRules.ACCESS_STATE_CHECKED_IN)) {
          checkInRemoteProject();

        }
      }
      else if( actionCommand.equals(BusinessRules.ACCESS_STATE_CHECKED_OUT) ){
        if( !m_Project.getAccessState().equals(BusinessRules.ACCESS_STATE_CHECKED_OUT)) {
          checkOutRemoteProject();
        }
      }
    }

   public boolean checkOutRemoteProject() {
	   CMApplication.frame.setWaitCursor(true);
        ProjectReference projectReference = CMApplication.frame.getTreeWorkspaceView().getCurrentProjectReference();
        Session2 session = CMApplication.frame.getTreeWorkspaceView().getM_Session2();
        CMApplication.frame.getCmApplication().getSessionManager().getM_WorkspaceManager().checkOutRemoteProject(m_Project, projectReference, session );
        getJTextFieldByUser().setText(session.getM_User());
        this.updateViewStates();
        this.updateCommandoStates();
        //m_Project.setM_AccessState(BusinessRules.ACCESS_STATE_CHECKED_OUT);
        CMApplication.frame.getTreeWorkspaceView().save2();
        CMApplication.frame.setWaitCursor(false);
        return true;
    }

    public boolean checkInRemoteProject() {
    	CMApplication.frame.setWaitCursor(true);
        ProjectReference projectReference = CMApplication.frame.getTreeWorkspaceView().getCurrentProjectReference();
        Session2 session = CMApplication.frame.getTreeWorkspaceView().getM_Session2();
        DefaultMutableTreeNode selectedNode =  CMApplication.frame.getTreeWorkspaceView().getSelectedNode();
        if( m_Project.getAccessState().equals(BusinessRules.ACCESS_STATE_CHECKED_OUT)
            && m_Project.getUser().equalsIgnoreCase(session.getM_User()) )  {
        	CMApplication.frame.getCmApplication().getSessionManager().getM_WorkspaceManager().checkInRemoteProject(m_Project, projectReference, session);
           if(  CMApplication.frame.getTreeWorkspaceView().existProjectFile(projectReference.getFilePath())) {
        	   CMApplication.frame.getTreeWorkspaceView().overwriteProject2(m_Project, projectReference, selectedNode);
           }
           else {
              JOptionPane.showMessageDialog(CMApplication.frame, CMMessages.getString("INFO_NETWORK_DRIVE_NOT_AVAILABLE"));
           }
        }
        getJTextFieldByUser().setText(session.getM_User());
        this.updateViewStates();
        this.updateCommandoStates();
        //m_Project.setM_AccessState(BusinessRules.ACCESS_STATE_CHECKED_IN);
        CMApplication.frame.getTreeWorkspaceView().save2();
        CMApplication.frame.setWaitCursor(false);
        return true;
    }

    //grueda30122004_end
    //hmendez_06102005_begin
    public  List getOrder()//Agregar en todos los JPanel
    {
   	   Vector<Component> componentList = new Vector<Component>();
   	   //componentList.add(m_Frame.getTabbedPane());
       if (getCMToolBarButtonChangeName().isEnabled())
         componentList.add(getCMToolBarButtonChangeName());
       if (getJRadioButtonCheckIn().isEnabled())
  	     componentList.add(getJRadioButtonCheckIn());
       if (getJRadioButtonChekOut().isEnabled())
  	     componentList.add(getJRadioButtonChekOut());
       if (getJEditorPaneDescription().isEnabled())
 	     componentList.add(getJEditorPaneDescription());
   	   componentList.add(CMApplication.frame.getTreeWorkspaceView());
       return componentList;
	 }
    //hmendez_06102005_end


    //grueda30122004_end
	/* (non-Javadoc)
	 * @see model.util.CMModelListener#handleCMModelChange(model.util.CMModelEvent)
	 */
	public void handleCMModelChange(CMModelEvent p_evt) {
		// TODO Auto-generated method stub
		if (p_evt.getSource() instanceof Project2)
		{
			if (p_evt.getChangedField() == CMField.NAME)
			{
				getJTextFieldName().setText(m_Project.getName());
				getJTextFieldFilePath().setText(m_ProjectReference.getFilePath());
			}
		}
		if (p_evt.getSource() instanceof ProjectReference)
		{
			if (p_evt.getChangedField() == CMField.PROJECT)
			{
				if (this.m_Project!=null)
					this.m_Project.removeModelListener(this);
				this.m_Project = ((ProjectReference)p_evt.getSource()).getProject();
				if (this.m_Project!=null)
				{
					this.m_Project.addModelListener(this);
					getJTextFieldName().setText(m_Project.getName());
					getJTextFieldFilePath().setText(m_ProjectReference.getFilePath());
				}
			}
		}
	}

	/**
	 * This method initializes jPanelMainContainer
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelName() {
		if (jPanelName == null) {
			FlowLayout flowLayout = new FlowLayout();
			flowLayout.setAlignment(java.awt.FlowLayout.LEFT);
			jLabelName = new JLabel();
			jLabelName.setToolTipText(CMMessages.getString("LABEL_TOOLTIP_NAME"));
			jLabelName.setPreferredSize(new java.awt.Dimension(72,14));
			jLabelName.setText(CMMessages.getString("LABEL_NAME"));
			jPanelName = new JPanel();
			jPanelName.setPreferredSize(new java.awt.Dimension(315,30));
			jPanelName.setLayout(flowLayout);
			jPanelName.add(jLabelName, null);
			jPanelName.add(getJTextFieldName(), null);
			jPanelName.add(getCMToolBarButtonChangeName(), null);
		}
		return jPanelName;
	}

	/**
	 * This method initializes jTextField
	 *
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextFieldName() {
		if (jTextFieldName == null) {
			jTextFieldName = new JTextField();
			jTextFieldName.setText("");
			jTextFieldName.setPreferredSize(new java.awt.Dimension(200,20));
		}
		return jTextFieldName;
	}

	/**
	 * This method initializes CMToolBarButton
	 *
	 * @return bi.view.utils.CMToolBarButton
	 */
	private CMToolBarButton getCMToolBarButtonChangeName() {
		if (CMToolBarButtonChangeName == null) {
			CMToolBarButtonChangeName = new CMToolBarButton(CMAction.RENAME_PROJECT
					.getAction());
		}
		return CMToolBarButtonChangeName;
	}

	/**
	 * This method initializes jPanelFilename
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelFilename() {
		if (jPanelFilename == null) {
			FlowLayout flowLayout1 = new FlowLayout();
			flowLayout1.setAlignment(java.awt.FlowLayout.LEFT);
			jLabelFilePath = new JLabel();
			jLabelFilePath.setToolTipText(CMMessages.getString("LABEL_TOOLTIP_FILE_PATH"));
			jLabelFilePath.setPreferredSize(new java.awt.Dimension(72,14));
			jLabelFilePath.setText(CMMessages.getString("LABEL_FILE_PATH"));
			jPanelFilename = new JPanel();
			jPanelFilename.setLayout(flowLayout1);
			jPanelFilename.setPreferredSize(new java.awt.Dimension(300,30));
			jPanelFilename.add(jLabelFilePath, null);
			jPanelFilename.add(getJTextFieldFilePath(), null);
		}
		return jPanelFilename;
	}

	/**
	 * This method initializes jTextField
	 *
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextFieldFilePath() {
		if (jTextFieldFilePath == null) {
			jTextFieldFilePath = new JTextField();
			jTextFieldFilePath.setText("");
			jTextFieldFilePath.setPreferredSize(new Dimension(370, 20));
			jTextFieldFilePath.setMaximumSize(new Dimension(450, 30));
			jTextFieldFilePath.setHorizontalAlignment(javax.swing.JTextField.LEFT);
			jTextFieldFilePath.setComponentOrientation(java.awt.ComponentOrientation.UNKNOWN);
			jTextFieldFilePath.setEditable(false);
		}
		return jTextFieldFilePath;
	}

	/**
	 * This method initializes jPanelCurrentUser
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelCurrentUser() {
		if (jPanelCurrentUser == null) {
			JLabelCurrentUser = new JLabel();
			JLabelCurrentUser.setText("");
			JLabelCurrentUser.setPreferredSize(new Dimension(200, 20));
			JLabelCurrentUser.setFont(new Font("Tahoma", Font.BOLD, 11));
			JLabelCurrentUser.setIconTextGap(5);
			JLabelCurrentUser.setIcon(CMIcon.USER.getImageIcon());
			FlowLayout flowLayout2 = new FlowLayout();
			flowLayout2.setAlignment(java.awt.FlowLayout.LEFT);
			jLabel = new JLabel();
			jLabel.setFont(new java.awt.Font("SansSerif", java.awt.Font.PLAIN, 11));
			jLabel.setPreferredSize(new java.awt.Dimension(72,15));
			jLabel.setText(CMMessages.getString("LABEL_CURRENT_USER"));

			jPanelCurrentUser = new JPanel();
			jPanelCurrentUser.setPreferredSize(new java.awt.Dimension(312,30));
			jPanelCurrentUser.setLayout(flowLayout2);
			jPanelCurrentUser.add(jLabel, null);
			jPanelCurrentUser.add(JLabelCurrentUser, null);
		}
		return jPanelCurrentUser;
	}

	/**
	 * This method initializes jPanelCheckInOut
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelCheckInOut() {
		if (jPanelCheckInOut == null) {
			jPanelCheckInOut = new JPanel();
			jPanelCheckInOut.setLayout(new BoxLayout(getJPanelCheckInOut(), BoxLayout.Y_AXIS));
			jPanelCheckInOut.setPreferredSize(new java.awt.Dimension(150,50));
			jPanelCheckInOut.add(getJRadioButtonCheckIn(), null);
			jPanelCheckInOut.add(getJRadioButtonChekOut(), null);
		}
		return jPanelCheckInOut;
	}

	/**
	 * This method initializes jRadioButton
	 *
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButtonCheckIn() {
		if (jRadioButtonCheckIn == null) {
			jRadioButtonCheckIn = new JRadioButton();
			jRadioButtonCheckIn.setActionCommand(BusinessRules.ACCESS_STATE_CHECKED_IN);
			jRadioButtonCheckIn.setText(CMMessages.getString("LABEL_CHECKED_IN"));
			jRadioButtonCheckIn.setPreferredSize(new java.awt.Dimension(10,40));
			jRadioButtonCheckIn.setName("jRadioButtonCheckIn");
			jRadioButtonCheckIn.setSelected(true);

			jRadioButtonCheckIn.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					jLabelKey.setIcon(CMIcon.LOCK.getImageIcon());
				}
			});
			jRadioButtonCheckIn.addActionListener(this);
		}
		return jRadioButtonCheckIn;
	}

	/**
	 * This method initializes jRadioButton1
	 *
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButtonChekOut() {
		if (jRadioButtonChekOut == null) {
			jRadioButtonChekOut = new JRadioButton();
			jRadioButtonChekOut.setActionCommand(BusinessRules.ACCESS_STATE_CHECKED_OUT);
			jRadioButtonChekOut.setPreferredSize(new java.awt.Dimension(100,40));
			jRadioButtonChekOut.setName("jRadioButtonChekOut");
			jRadioButtonChekOut.setText(CMMessages.getString("LABEL_CHECKED_OUT"));
			jRadioButtonChekOut.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					jLabelKey.setIcon(CMIcon.UNLOCK.getImageIcon());
				}
			});
			jRadioButtonChekOut.addActionListener(this);
		}
		return jRadioButtonChekOut;
	}

	/**
	 * This method initializes jPanelByUser
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelByUser() {
		if (jPanelByUser == null) {
			FlowLayout flowLayout4 = new FlowLayout();
			flowLayout4.setAlignment(java.awt.FlowLayout.RIGHT);
			flowLayout4.setVgap(5);
			jLabel1 = new JLabel();
			jLabel1.setText(CMMessages.getString("LABEL_BY_THE_USER"));
			jPanelByUser = new JPanel();
			jPanelByUser.setPreferredSize(new java.awt.Dimension(200,50));
			jPanelByUser.setLayout(flowLayout4);
			jPanelByUser.add(jLabel1, null);
			jPanelByUser.add(getJTextFieldByUser(), null);
		}
		return jPanelByUser;
	}

	/**
	 * This method initializes jTextField1
	 *
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextFieldByUser() {
		if (jTextFieldByUser == null) {
			jTextFieldByUser = new JTextField();
			jTextFieldByUser.setText("");
			jTextFieldByUser.setPreferredSize(new java.awt.Dimension(120,20));
			jTextFieldByUser.setMaximumSize(new java.awt.Dimension(200,20));
			jTextFieldByUser.setEditable(false);
			jTextFieldByUser.setFocusable(false);
		}
		return jTextFieldByUser;
	}

	/**
	 * This method initializes jPanelState
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelState() {
		if (jPanelState == null) {
			jLabelKey = new JLabel();
			jLabelKey.setText("");

			jLabelKey.setHorizontalTextPosition(SwingConstants.CENTER);
			jLabelKey.setHorizontalAlignment(SwingConstants.CENTER);
			jLabelKey.setIconTextGap(0);
			jLabelKey.setLabelFor(getJPanelCheckInOut());
			jLabelKey.setPreferredSize(new Dimension(67, 32));
			FlowLayout flowLayout3 = new FlowLayout();
			flowLayout3.setAlignment(java.awt.FlowLayout.LEFT);
			flowLayout3.setVgap(0);
			jPanelState = new JPanel();
			jPanelState.setLayout(flowLayout3);
			jPanelState.setPreferredSize(new java.awt.Dimension(547,80));
			jPanelState.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED), CMMessages.getString("LABEL_ACCESS_STATE"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11), java.awt.Color.black));
			jPanelState.add(jLabelKey, null);
			jPanelState.add(getJPanelCheckInOut(), null);
			jPanelState.add(getJPanelByUser(), null);
		}
		return jPanelState;
	}

	/**
	 * This method initializes jPanelMainContainer
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelMainContainer() {
		if (jPanelMainContainer == null) {
			jPanelMainContainer = new JPanel();
			jPanelMainContainer.setLayout(new BoxLayout(getJPanelMainContainer(), BoxLayout.Y_AXIS));
			jPanelMainContainer.setMinimumSize(new java.awt.Dimension(286,128));
			jPanelMainContainer.add(getJPanelName(), null);
			jPanelMainContainer.add(getJPanelFilename(), null);
			jPanelMainContainer.add(getJPanelCurrentUser(), null);
			jPanelMainContainer.add(getJPanelState(), null);
			jPanelMainContainer.add(getJPanelDescription(), null);
			jPanelMainContainer.add(getJPanelBottom(), null);
		}
		return jPanelMainContainer;
	}

	/**
	 * This method initializes jPanel
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelDescription() {
		if (jPanelDescription == null) {
			jPanelDescription = new JPanel();
			jPanelDescription.setLayout(new BorderLayout());
			jPanelDescription.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), CMMessages.getString("LABEL_DESCRIPTION"), TitledBorder.LEADING, TitledBorder.TOP, new Font("SansSerif", 0, 11), new Color(60, 60, 60)));
			jPanelDescription.setPreferredSize(new java.awt.Dimension(616,40));
			jPanelDescription.add(getJScrollPaneDescription(), java.awt.BorderLayout.CENTER);
		}
		return jPanelDescription;
	}

	/**
	 * This method initializes CMJEditorPaneFocusChangeable
	 *
	 * @return bi.view.utils.CMJEditorPaneFocusChangeable
	 */
	private CMJEditorPaneFocusChangeable getJEditorPaneDescription() {
		if (jEditorPaneDescription == null) {
			jEditorPaneDescription = new CMJEditorPaneFocusChangeable();
			jEditorPaneDescription.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
			jEditorPaneDescription.setText("");
			jEditorPaneDescription.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyTyped(java.awt.event.KeyEvent e) {
						jEditorDescriptionCaretUpdate(null);
				}
			});
		}
		return jEditorPaneDescription;
	}

	/**
	 * This method initializes jPanelBottom
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelBottom() {
		if (jPanelBottom == null) {
			jPanelBottom = new JPanel();
			jPanelBottom.setPreferredSize(new java.awt.Dimension(10,100));
		}
		return jPanelBottom;
	}

	/**
	 * This method initializes jPanelLeft
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelLeft() {
		if (jPanelLeft == null) {
			jPanelLeft = new JPanel();
			jPanelLeft.setPreferredSize(new java.awt.Dimension(20,10));
		}
		return jPanelLeft;
	}

	/**
	 * This method initializes jPanelRight
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelRight() {
		if (jPanelRight == null) {
			jPanelRight = new JPanel();
		}
		return jPanelRight;
	}

	/**
	 * This method initializes jPanelTop
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelTop() {
		if (jPanelTop == null) {
			jPanelTop = new JPanel();
			jPanelTop.setPreferredSize(new java.awt.Dimension(20,20));
		}
		return jPanelTop;
	}

	/**
	 * This method initializes jScrollPaneDescription
	 *
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPaneDescription() {
		if (jScrollPaneDescription == null) {
			jScrollPaneDescription = new JScrollPane();
			jScrollPaneDescription.setViewportView(getJEditorPaneDescription());
		}
		return jScrollPaneDescription;
	}
}
