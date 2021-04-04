/**
 * Developed by BUSINESS SOFTWARE INNOVATIONS. .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */

package bi.view.testobjectviews;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.EventObject;
import java.util.List;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.tree.DefaultMutableTreeNode;
import model.BusinessRules;
import model.CMField;
import model.Project2;
import model.ProjectReference;
import model.Session2;
import model.Technology;
import model.TestObject;
import model.TestObjectReference;
import model.ToolVendor;
import model.util.CMModelEvent;
import model.util.CMModelListener;
import bi.controller.ProjectManager;
import bi.controller.SessionManager;
import bi.controller.ToolVendorManager;
import bi.controller.WorkspaceManager;
import bi.view.actions.CMAction;
import bi.view.icons.CMIcon;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.treeviews.nodes.CMProjectNode;
import bi.view.treeviews.nodes.CMTestObjectNode;
import bi.view.utils.CMBaseJComboBox;
import bi.view.utils.CMBaseJPanel;
import bi.view.utils.CMJEditorPaneFocusChangeable;
import bi.view.utils.CMToolBarButton;
import java.awt.Dimension;

public class CMPanelTestObjectView extends CMBaseJPanel implements ActionListener, CMModelListener{

	private static final long serialVersionUID = 1L;
    private TestObject m_TestObject;  //  @jve:decl-index=0:
    private TestObjectReference m_TestObjectReference = null;
    private Session2 m_Session = null;  //  @jve:decl-index=0:
    private ProjectReference m_ProjectReference = null;
    private WorkspaceManager m_WorkspaceManager = null;
    private TestObjectReference m_LocalTestObjectReference = null;  //  @jve:decl-index=0:
    public String toolVendorTech="";
    public String m_ToolVendor="";
    private ToolVendor m_ToolVendorfile;
    private boolean cbToolVendorChanged = true;
    private boolean cbTechnologyChanged = true;
    private JPanel jPanelName = null;
	private JTextField jTextField = null;
	private JLabel jLabelName = null;
	private CMToolBarButton JButtonChangeName = null;
	private JPanel jPanelCurrentUser = null;
	private JLabel jLabelCurrentUser = null;
	private JPanel jPanelChecks = null;
	private JRadioButton jRadioButtonCheckOut = null;
	private JRadioButton jRadioButtonCheckIn = null;
	private JRadioButton jRadioButtonLocalCheckOut = null;
	private JPanel jPanelByUser = null;
	private JLabel jLabelByUser = null;
	private JTextField jTextFieldByUser = null;
	private JPanel jPanelAccessState = null;
	private JPanel jPanelDescription = null;
	private JScrollPane jScrollPaneDescription = null;
	private CMJEditorPaneFocusChangeable jEditorPaneDescription = null;
	private JPanel jPanelPreconditions = null;
	private JScrollPane jScrollPanePreconditions = null;
	private CMJEditorPaneFocusChangeable CMJEditorPaneFocusChangeable = null;
	private JPanel jPanelMainContainer = null;
	private JPanel jPanelTop = null;
	private JPanel jPanelLeft = null;
	private JPanel jPanelRight = null;
	private JPanel jPanelOtherProperties = null;
	private JPanel jPanelSeparators = null;
	private JPanel jPanelOtherPropertiesSub = null;
	private JPanel jPanelTechnologhy = null;
	private JPanel jPanelOrigin = null;
	private JCheckBox jCheckBoxSystemSeparator = null;
	private JPanel jPanelThousandPanel = null;
	private CMBaseJComboBox jComboBoxMiles = null;
	private JPanel jPanelDecimal = null;
	private CMBaseJComboBox jComboBoxDecimal = null;
	private JLabel jLabelToolVendor = null;
	private JPanel jPanelToolVendor = null;
	private CMBaseJComboBox jComboBoxToolVendor = null;
	private JLabel jLabelTechnologhy = null;
	private CMBaseJComboBox jComboBoxToolVendorTech = null;
	private JPanel jPanelTech2 = null;
	private JCheckBox jCheckBoxFlowTestObject = null;
	private JPanel jPanelDefaultSeparators = null;
	private JPanel jPanelSeparatorCombos = null;
	private JLabel jLabel = null;
	private JLabel jLabel1 = null;
	private JPanel jPanelLabelToolVendor = null;
	private JPanel jPanelLabelTechnology = null;
	private JPanel jPanelLabelThousandSep = null;
	private JPanel jPanelLabelThousand = null;
	private JLabel JLabelCurrentUserText = null;
	private JLabel jLabelKey = null;
	/** Creates new form CMPanelTestObjectView */

    public CMPanelTestObjectView() {
    	super();//Agregar en todos los JPanel
        m_WorkspaceManager = WorkspaceManager.getInstance();
        initialize();
    }

    /** This method is called from within the constructor to initialize the form. */
    private void initialize() {
    	cbTechnologyChanged = true;

        setLayout(new BorderLayout());
        setBorder(null);
        setBounds(new java.awt.Rectangle(0, 0, 605, 543));
        this.add(getJPanelMainContainer(), java.awt.BorderLayout.CENTER);
        this.add(getJPanelTop(), java.awt.BorderLayout.NORTH);
        this.add(getJPanelLeft(), java.awt.BorderLayout.WEST);
        this.add(getJPanelRight(), java.awt.BorderLayout.EAST);
        ButtonGroup group = new ButtonGroup();
        group.add(getJRadioButtonCheckIn());
        group.add(getJRadioButtonCheckOut());
        group.add(getJRadioButtonLocalCheckOut());

       }

    public void fillToolVendorComboBox(){
    	    cbToolVendorChanged = false;
    	    getJComboBoxToolVendor().removeAllItems();
    		Vector m_ToolVendors = m_WorkspaceManager.getM_SessionManager().getApplicationSettingManager().getApplicationSetting().getM_ToolVendors();
 	  		int numofToolVendors = m_ToolVendors.size();

	    	for (int i=0; i < numofToolVendors; i++){
	    		ToolVendor m_ToolVendorMatch = (ToolVendor)(m_ToolVendors.elementAt(i));
		   		String cbItemToolVendor =  m_ToolVendorMatch.getM_Name();
		   		getJComboBoxToolVendor().addItem(cbItemToolVendor);
	    	}
    	cbToolVendorChanged=true;
	}

    public void fillTechnologyComboBox(){
    	cbTechnologyChanged=false;
    	getJComboBoxToolVendorTech().removeAllItems();
    	if (m_ToolVendorfile != null){
    		int sizeTechnology = m_ToolVendorfile.getM_Technologies().size();

    	    	for (int i=0; i < sizeTechnology; i++){
    	    		Technology m_Technology= (Technology)(m_ToolVendorfile.getM_Technologies().elementAt(i));
    	    		String cbItem =  m_Technology.getM_Name();
    	    		cbTechnologyChanged=false;
    	    		getJComboBoxToolVendorTech().addItem(cbItem);
    	    	}
    	}
    	else{

    	}

    	cbTechnologyChanged=true;
}



    public TestObject getTestObject() {
        return m_TestObject;
    }

    //grueda26122004_begin
    //grueda22082004_begin
    public void setTestObject(TestObject p_TestObject) //, TestObjectReference p_TestObjectReference, Session2 p_Session){
    {
        if (m_TestObject != null)
        	m_TestObject.removeModelListener(this);
    	m_TestObject = p_TestObject;
    	m_TestObject.addModelListener(this);

    }

    public void setTestObject(Project2 p_Project, TestObject p_TestObject, TestObjectReference p_TestObjectReference, Session2 p_Session,
        ProjectReference p_ProjectReference, TestObjectReference p_LocalTestObjectReference) {
    	  if (m_TestObject != null)
          	m_TestObject.removeModelListener(this);
          m_Session = p_Session;
          m_TestObject = p_TestObject;
          m_TestObject.addModelListener(this);
          m_TestObjectReference = p_TestObjectReference;
          m_LocalTestObjectReference = p_LocalTestObjectReference;
          m_ProjectReference = p_ProjectReference;

          if ((m_TestObject == null) || (m_Session == null) || (m_TestObjectReference == null)) {
                return;
            }

          getJTextField().setText(m_TestObject.getName());
          getJEditorPaneDescription().setText(m_TestObject.getDescription());
          this.getCMJEditorPaneFocusChangeable().setText(m_TestObject.getPreconditions());
          JLabelCurrentUserText.setText(m_Session.getM_User());
          getJTextFieldByUser().setText( m_TestObject.getUser());
          setNumberSeparator(p_TestObject);
         getJCheckBoxFlowTestObject().setEnabled(p_TestObject.getOrigin()!=null);
          getJCheckBoxFlowTestObject().setSelected(p_TestObject.getOrigin()!=null);

            if (m_TestObject.getAccessState().equals(BusinessRules.ACCESS_STATE_CHECKED_IN)) {
            	getJRadioButtonCheckIn().setSelected(true);
            }
            if ( m_TestObject.getAccessState().equals(BusinessRules.ACCESS_STATE_CHECKED_OUT)) {
                if (m_LocalTestObjectReference != null) {
                    if (
                        m_TestObject.getAccessState().equals(BusinessRules.ACCESS_STATE_CHECKED_OUT_LOCAL)) {
                    	getJRadioButtonLocalCheckOut().setSelected(true);
                    }
                    else {
                    	 getJRadioButtonCheckOut().setSelected(true);
                    }
                }
                else {
                	 getJRadioButtonCheckOut().setSelected(true);
                }
            }

            if ( m_TestObject.getAccessState().equals(BusinessRules.ACCESS_STATE_CHECKED_OUT_LOCAL)) {
            	getJRadioButtonLocalCheckOut().setSelected(true);
            }

            CMApplication.frame.setAccessRights(m_TestObjectReference, m_Session, this);

    }

    public void setNumberSeparator(TestObject p_TestObject){
        String decimalSeparator=p_TestObject.getDecimalSeparator();
        String mileSeparator=p_TestObject.getMilesSeparator();


        getJComboBoxDecimal().setSelectedItem(decimalSeparator);
        getJComboBoxMiles().setSelectedItem(mileSeparator);

        getJCheckBoxSystemSeparator().setSelected(p_TestObject.isDefaultSeparator());

    }


    //integration_fcastro_17082004_begin
    public void newNameEntered(EventObject e) { //fcastro_20082004
        //System.out.println("newNameEntered"); //debug porpouse
        if (m_TestObject != null && (!getJTextField().getText().trim().equals(this.m_TestObject.getName()))) { //fcastro_20082004
            if (getJTextField().getText().trim().equals("")) {
                JOptionPane.showMessageDialog(CMApplication.frame, CMMessages.getString("MESSAGE_ERROR_NAME_TESTOBJECT_EMPTY"),
                    CMMessages.getString("TITLE_MESSAGE_ERROR_TESTOBJECT_NAME"), JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$ //$NON-NLS-2$
                if (e != null && (e.getSource() instanceof JMenu)) {
                    JMenu menu = (JMenu)e.getSource();
                    menu.setPopupMenuVisible(false);
                }
                getJTextField().setText(m_TestObject.getName());
                CMApplication.frame.requestFocus();
            }
            else {

                //grueda05102004_begin
                if (validateName(getJTextField().getText(), CMApplication.frame.getTreeWorkspaceView().getCurrentProject())){//m_TestObjectReference.getM_Project())) {
                    //grueda25102004_begin
                	CMApplication.frame.getTreeWorkspaceView().changedTestObjectNameWhileChangingSelection(getJTextField().getText().trim(),
                        m_TestObject, m_TestObjectReference);

                    m_TestObjectReference.setM_ChangedName(getJTextField().getText().trim());//Ccastedo 21-04-06

                    int saveUnsave = CMApplication.frame.getCmApplication().getSessionManager().getM_Save_UnsaveVariable() + 1;
                    CMApplication.frame.getCmApplication().getSessionManager().setM_Save_UnsaveVariable(saveUnsave);
                    //HCanedo_03012005_End
                    //grueda05102004_end
                }
                else {
                    JOptionPane.showMessageDialog(CMApplication.frame, CMMessages.getString("MENSSAGE_ERROR_SAME_NAME_TESTOBJECT"),
                        CMMessages.getString("TITLE_MESSAGE_ERROR_TESTOBJECT_NAME"), JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$ //$NON-NLS-2$
                    if (e != null && (e.getSource() instanceof JMenu)) {
                        JMenu menu = (JMenu)e.getSource();
                        menu.setPopupMenuVisible(false);
                    }
                    getJTextField().setText(m_TestObject.getName());
                    CMApplication.frame.requestFocus();
                }
            }
        }
    }

    public void changeValidName(String name){
    	getJTextField().setText(name);
    	CMApplication.frame.getTreeWorkspaceView().changedTestObjectNameWhileChangingSelection(name, m_TestObject, m_TestObjectReference);
        m_TestObjectReference.setM_ChangedName(name);
    }
    //integration_fcastro_17082004_end
    public void jEditorDescriptionCaretUpdate(CaretEvent e) {
        //HCanedo_03012005_Begin
        if (!m_TestObject.getDescription().equals(getJEditorPaneDescription().getText())) {
            int saveUnsave = CMApplication.frame.getCmApplication().getSessionManager().getM_Save_UnsaveVariable() + 1;
            CMApplication.frame.getCmApplication().getSessionManager().setM_Save_UnsaveVariable(saveUnsave);
        }
        //HCanedo_03012005_End
        CMApplication.frame.getTreeWorkspaceView().changedTestObjectDescription2(getJEditorPaneDescription().getText());
        m_TestObject.setDescription(getJEditorPaneDescription().getText());
    }

    public void jEditorPanePreconditionsCaretUpdate(CaretEvent e) {
        //HCanedo_03012005_Begin
        if (!m_TestObject.getPreconditions().equals(getCMJEditorPaneFocusChangeable().getText())) {
            int saveUnsave = CMApplication.frame.getCmApplication().getSessionManager().getM_Save_UnsaveVariable() + 1;
            CMApplication.frame.getCmApplication().getSessionManager().setM_Save_UnsaveVariable(saveUnsave);
        }
        //HCanedo_03012005_End
        CMApplication.frame.getTreeWorkspaceView().changedTestObjectPreconditions(getJEditorPaneDescription().getText());
        m_TestObject.setPreconditions(getCMJEditorPaneFocusChangeable().getText());
    }

    public boolean checkOutRemote(TestObjectReference p_TestObjectReference, TestObject p_TestIObject) {
    	CMApplication.frame.setWaitCursor(true);
        ProjectReference projectReference = CMApplication.frame.getTreeWorkspaceView().getCurrentProjectReference();
        boolean successfull = m_WorkspaceManager.checkOutRemote(projectReference, p_TestObjectReference,
            p_TestIObject, m_Session);

        CMApplication.frame.setWaitCursor(false);
        if (!successfull) {
            return false;
        }
        getJTextFieldByUser().setText(m_Session.getM_User());

        return true;
    }

    public boolean checkInRemote(TestObjectReference p_TestObjectReference, TestObject p_TestObject) {
    	CMApplication.frame.setWaitCursor(true);
        ProjectReference projectReference = CMApplication.frame.getTreeWorkspaceView().getCurrentProjectReference();
        boolean successfull = m_WorkspaceManager.checkInRemote(projectReference, p_TestObjectReference,
            p_TestObject, m_Session);

        CMApplication.frame.setWaitCursor(false);
        if (!successfull) {
            return false;
        }

        return true;
    }

    public void reloadCurrentTestObject(ProjectReference p_ProjectReference, TestObjectReference p_TestObjectReference,
        Session2 p_Session, TestObjectReference p_LocalTestObjectReference) {
            //TestObject testObject = m_WorkspaceManager.readTestObjectByReference(p_TestObjectReference, p_ProjectReference, p_Session);
            TestObjectReference testObjectReference =
                m_WorkspaceManager.reloadTestObjectReferenceFromProject(p_ProjectReference, p_TestObjectReference);

            TestObject testObject = CMApplication.frame.getTreeWorkspaceView().rebuildSelectedTestObjectNode(testObjectReference,
                p_ProjectReference, p_Session);
            Project2 project=CMApplication.frame.getTreeWorkspaceView().getCurrentProject();
            setTestObject(project,testObject, testObjectReference, p_Session, p_ProjectReference, p_LocalTestObjectReference);
    }

    public boolean checkInLocal(TestObjectReference p_TestObjectReference, TestObjectReference p_LocalTestObjectReference,
        TestObject p_TestObject) {

            WorkspaceManager workspaceManager = CMApplication.frame.getCmApplication().getSessionManager().getM_WorkspaceManager();
            ProjectReference projectReference = CMApplication.frame.getTreeWorkspaceView().getCurrentProjectReference();
            CMApplication.frame.setWaitCursor(true);
            boolean successful = workspaceManager.checkInLocal(projectReference, p_TestObjectReference,
                p_LocalTestObjectReference, p_TestObject, m_Session);
            m_LocalTestObjectReference = null;

            CMApplication.frame.setWaitCursor(false);
            if (!successful) {
                JOptionPane.showMessageDialog(CMApplication.frame, CMMessages.getString("INFO_NETWORK_DRIVE_NOT_AVAILABLE"));
                return false;
            }
            else {
                return true;
            }
    }

    public boolean checkOutLocal(TestObjectReference p_TestObjectReference, TestObjectReference p_LocalTestObjectReference,
        TestObject p_TestObject) {
            WorkspaceManager workspaceManager = CMApplication.frame.getCmApplication().getSessionManager().getM_WorkspaceManager();
            ProjectReference projectReference = CMApplication.frame.getTreeWorkspaceView().getCurrentProjectReference();
            Project2 project=CMApplication.frame.getTreeWorkspaceView().getCurrentProject();
            CMApplication.frame.setWaitCursor(true);
            boolean successfull = workspaceManager.checkOutLocal(project, projectReference, p_TestObjectReference,
                p_LocalTestObjectReference, p_TestObject, m_Session);
            CMApplication.frame.setWaitCursor(false);
            if (!successfull) {
                return false;
            }
            return true;
    }


    public void assignTheLastSelectedRadioButton(TestObjectReference p_TestObjectReference) {
    	TestObject to = m_TestObject;
    	if (to == null)
    		to = WorkspaceManager.getInstance().readTestObjectByReference(p_TestObjectReference,m_ProjectReference,SessionManager.getCurrentSession());
        if (to.getAccessState().equals(BusinessRules.ACCESS_STATE_CHECKED_IN)) {
        	getJRadioButtonCheckIn().setSelected(true);
        }
        if (to.getAccessState().equals(BusinessRules.ACCESS_STATE_CHECKED_OUT)) {
        	 getJRadioButtonCheckOut().setSelected(true);
        }
        if (to.getAccessState().equals(BusinessRules.ACCESS_STATE_CHECKED_OUT_LOCAL)) {
        	getJRadioButtonLocalCheckOut().setSelected(true);
        }
    }

    public void checkInRemoteGeneral(TestObjectReference p_TestObjectReference, TestObject p_TestObject) {
        if (!checkInRemote(p_TestObjectReference, p_TestObject)) {
            assignTheLastSelectedRadioButton(p_TestObjectReference);
        }
        else {
            this.getJRadioButtonCheckIn().setSelected(true);
            CMApplication.frame.getTreeWorkspaceView().assignSelectedTestObjectNodeWith(m_TestObject, p_TestObjectReference,
                this.m_Session, m_ProjectReference, m_LocalTestObjectReference);
            CMApplication.frame.getTreeWorkspaceView().save2();
            CMApplication.frame.enableViews(false, this, p_TestObjectReference, m_Session);
            updateAccessViewsStates();
            setAccessViewsEnabled(true);
            CMApplication.frame.setStateTestObjectReadOnly();
        }
    }

    //grueda26122004_begin
    public void checkInLocalGeneral(TestObjectReference p_TestObjectReference, TestObjectReference p_LocalTestObjectReference,
        TestObject p_TestObject) {
            if (!checkInLocal(p_TestObjectReference, p_LocalTestObjectReference, p_TestObject)) {
                assignTheLastSelectedRadioButton(p_TestObjectReference);
            }
            else {
            	getJRadioButtonCheckIn().setSelected(true);
            	CMApplication.frame.getTreeWorkspaceView().assignSelectedTestObjectNodeWith(m_TestObject, p_TestObjectReference, m_Session,
                    m_ProjectReference, m_LocalTestObjectReference);
                CMApplication.frame.getTreeWorkspaceView().save2();
                CMApplication.frame.enableViews(false, this, p_TestObjectReference, m_Session);
                updateAccessViewsStates();
                setAccessViewsEnabled(true);
                CMApplication.frame.setStateTestObjectReadOnly();
            }
    }

    //grueda26122004_end
    public void checkOutRemoteGeneral(TestObjectReference p_TestObjectReference, TestObject p_TestObject) {
        if (!checkOutRemote(p_TestObjectReference, p_TestObject)) {
            assignTheLastSelectedRadioButton(p_TestObjectReference);
        }
        else {
        	 getJRadioButtonCheckOut().setSelected(true);
        	 CMApplication.frame.getTreeWorkspaceView().assignSelectedTestObjectNodeWith(m_TestObject, p_TestObjectReference,
                this.m_Session, m_ProjectReference, m_LocalTestObjectReference);
        	 CMApplication.frame.getTreeWorkspaceView().save2();
            updateAccessViewsStates();
            CMApplication.frame.enableViews(true, this, p_TestObjectReference, m_Session);
            CMApplication.frame.setStateOneTestObjects();


            CMAction.COPY.setEnabled(true);
            CMAction.CUT.setEnabled(true);
        }
    }

    //grueda26122004_begin
    public void checkOutLocalGeneral(TestObjectReference p_TestObjectReference, TestObjectReference p_LocalTestObjectReference,
        TestObject p_TestObject) {
            if (!checkOutLocal(p_TestObjectReference, p_LocalTestObjectReference, p_TestObject)) {
                assignTheLastSelectedRadioButton(p_TestObjectReference);
            }
            else {
                this.getJRadioButtonLocalCheckOut().setSelected(true);
                CMApplication.frame.getTreeWorkspaceView().assignSelectedTestObjectNodeWith(m_TestObject, p_TestObjectReference,
                    this.m_Session, m_ProjectReference, m_LocalTestObjectReference);
                CMApplication.frame.getTreeWorkspaceView().save2();
                updateAccessViewsStates();
                CMApplication.frame.setStateTestObjectLocalCheckedOut();

                CMAction.COPY.setEnabled(true);
                CMAction.CUT.setEnabled(true);

            }
    }

    //grueda26122004_end
    public boolean exists(String p_Path) {
        File file = new File(p_Path);
        if (file.exists()) {
            return true;
        }
        else {
            return false;
        }
    }

    public void actionPerformed(ActionEvent e) {
       // newNameEntered(e);
        String actionCommand = e.getActionCommand();
        if (exists(m_ProjectReference.getFilePath())) {
            if (actionCommand.equals(BusinessRules.ACCESS_STATE_CHECKED_IN)) {
                if (! /*m_TestObjectReference */m_TestObject.getAccessState().equals(BusinessRules.ACCESS_STATE_CHECKED_IN)) {
                    if (/*m_TestObjectReference*/ m_TestObject.getAccessState().equals(BusinessRules.ACCESS_STATE_CHECKED_OUT)) {
                        if (m_LocalTestObjectReference != null) {
                            if (! m_LocalTestObjectReference/*
                                m_TestObject*/.getM_AccessState().equals(BusinessRules.ACCESS_STATE_CHECKED_OUT_LOCAL)) {
                                    checkInRemoteGeneral(m_TestObjectReference, m_TestObject);
                            }
                            else {
                                m_LocalTestObjectReference = m_TestObjectReference.copy();
                                checkInLocalGeneral(m_TestObjectReference, m_LocalTestObjectReference, m_TestObject);
                                m_LocalTestObjectReference = null;
                            }
                        }
                        else {
                            checkInRemoteGeneral(m_TestObjectReference, m_TestObject);
                        }
                    }
                    else if (/*m_TestObjectReference*/ m_TestObject.getAccessState().equals(BusinessRules.ACCESS_STATE_CHECKED_OUT_LOCAL)) {
                        m_LocalTestObjectReference = m_TestObjectReference.copy();
                        checkInLocalGeneral(m_TestObjectReference, m_LocalTestObjectReference, m_TestObject);
                        m_LocalTestObjectReference = null;
                    }
                    else if (m_LocalTestObjectReference != null) {
                        if (/*m_LocalTestObjectReference*/ m_TestObject.getAccessState().equals(BusinessRules.ACCESS_STATE_CHECKED_OUT_LOCAL)) {
                            m_LocalTestObjectReference = m_TestObjectReference.copy();
                            checkInLocalGeneral(m_TestObjectReference, m_LocalTestObjectReference, m_TestObject);
                            m_LocalTestObjectReference = null;
                        }
                    }
                }
            }
            else if (actionCommand.equals(BusinessRules.ACCESS_STATE_CHECKED_OUT)) {
            	m_ProjectReference = CMApplication.frame.getTreeWorkspaceView().getCurrentProjectReference();
                reloadCurrentTestObject(m_ProjectReference, m_TestObjectReference, m_Session, m_LocalTestObjectReference);
                if (! /*m_TestObjectReference*/ m_TestObject.getAccessState().equals(BusinessRules.ACCESS_STATE_CHECKED_OUT)) {
                    if (/*m_TestObjectReference*/ m_TestObject.getAccessState().equals(BusinessRules.ACCESS_STATE_CHECKED_IN)) {
                        checkOutRemoteGeneral(m_TestObjectReference, m_TestObject);
                    }
                    else {
                        assignTheLastSelectedRadioButton(m_TestObjectReference);
                    }
                }
                else {
                    assignTheLastSelectedRadioButton(m_TestObjectReference);
                }
            }
            else if (actionCommand.equals(BusinessRules.ACCESS_STATE_CHECKED_OUT_LOCAL)) {
            	CMApplication.frame.getTreeWorkspaceView().save2();
            	m_ProjectReference = CMApplication.frame.getTreeWorkspaceView().getCurrentProjectReference();
                reloadCurrentTestObject(m_ProjectReference, m_TestObjectReference, m_Session, m_LocalTestObjectReference);
                if (!/*m_TestObjectReference*/ m_TestObject.getAccessState().equals(BusinessRules.ACCESS_STATE_CHECKED_OUT_LOCAL)) {
                    if (/*m_TestObjectReference*/ m_TestObject.getAccessState().equals(BusinessRules.ACCESS_STATE_CHECKED_IN) ||
                        /*m_TestObjectReference.getM_AccessState().equals(BusinessRules.ACCESS_STATE_CHECKED_OUT)*/
                        m_WorkspaceManager.isTestObjectCheckedOutByTheSameUser(m_TestObject, m_Session)) {
                            m_LocalTestObjectReference = m_TestObjectReference.copy();
                            m_TestObject.setAccessState(BusinessRules.ACCESS_STATE_CHECKED_OUT_LOCAL);
                            m_TestObject.setUser(m_Session.getM_User());
                            Project2 project=CMApplication.frame.getTreeWorkspaceView().getCurrentProject();
                            CMApplication.frame.getTreeWorkspaceView().writeTestObject2(project, m_TestObject, m_TestObjectReference,
                                m_ProjectReference);
                            checkOutLocalGeneral(m_TestObjectReference, m_LocalTestObjectReference, m_TestObject);
                    }
                    else {
                        this.assignTheLastSelectedRadioButton(m_TestObjectReference);
                    }
                }
                else {
                    this.assignTheLastSelectedRadioButton(m_TestObjectReference);
                }
            }
        }
        else {
            JOptionPane.showMessageDialog(CMApplication.frame, CMMessages.getString("INFO_NETWORK_DRIVE_NOT_AVAILABLE"));
            this.assignTheLastSelectedRadioButton(m_TestObjectReference);
        }
    }
	public void reloadAfterChangeName(){
        reloadCurrentTestObject(m_ProjectReference, m_TestObjectReference, m_Session, m_LocalTestObjectReference);
    }
    //grueda26122004_end
    public void setViewsEnabled(boolean p_Enabled, TestObjectReference p_TestObjectReference, Session2 p_Session) {
        //grueda07112004_begin
        if (p_Enabled == true) {
        }
        else {
    	    CMAction.RENAME_TESTOBJECT.setEnabled(p_Enabled);
        }
        //grueda07112004_end
        getJEditorPaneDescription().setEditable(p_Enabled);
        getJEditorPaneDescription().setEnabled(p_Enabled);
        getCMJEditorPaneFocusChangeable().setEditable(p_Enabled);
        getCMJEditorPaneFocusChangeable().setEnabled(p_Enabled);
        getJRadioButtonCheckIn().setEnabled(p_Enabled);
        getJRadioButtonCheckOut().setEnabled(p_Enabled);
        //grueda02112004_begin
        getJRadioButtonLocalCheckOut().setEnabled(p_Enabled);

        getJCheckBoxSystemSeparator().setEnabled(p_Enabled);
        if(p_Enabled){
            if(!getJCheckBoxSystemSeparator().isSelected()){
            	getJComboBoxDecimal().setEnabled(p_Enabled);
            	getJComboBoxMiles().setEnabled(p_Enabled);
            	getJComboBoxToolVendor().setEnabled(p_Enabled);
            	getJComboBoxToolVendorTech().setEnabled(p_Enabled);
            }
            else{
            	getJComboBoxDecimal().setEnabled(false);
                getJComboBoxMiles().setEnabled(false);
            }
        }
        else{
        	getJComboBoxDecimal().setEnabled(p_Enabled);
            getJComboBoxMiles().setEnabled(p_Enabled);
            getJComboBoxToolVendor().setEnabled(p_Enabled);
            getJComboBoxToolVendorTech().setEnabled(p_Enabled);
        }

        if (p_Enabled) {
            if (getJRadioButtonLocalCheckOut().isSelected()) {
            	getJRadioButtonCheckIn().setEnabled(true);
                getJRadioButtonCheckOut().setEnabled(false);
            }
        }

    }

    public void setDefaultSeparator(boolean enabled){
    	getJCheckBoxSystemSeparator().setSelected(enabled);
    }

    public void updateAccessViewsStates() {
        if (getJRadioButtonCheckIn().isSelected()) {
        	 getJRadioButtonCheckOut().setEnabled(true);
            //grueda02112004_begin
        	 getJRadioButtonLocalCheckOut().setEnabled(true);
            //grueda02112004_end
        }
        else if ( getJRadioButtonCheckOut().isSelected()) {
        	getJRadioButtonCheckIn().setEnabled(true);
            //grueda02112004_begin
        	getJRadioButtonLocalCheckOut().setEnabled(true);
            //grueda02112004_end
        }
        else if (getJRadioButtonLocalCheckOut().isSelected()) {
        	getJRadioButtonCheckIn().setEnabled(true);
            getJRadioButtonCheckOut().setEnabled(false);
        }
    }

    //grueda25102004_end
    public void setAccessViewsEnabled(boolean p_Enabled) {
    	getJRadioButtonCheckIn().setEnabled(p_Enabled);
        getJRadioButtonCheckOut().setEnabled(p_Enabled);
        getJRadioButtonLocalCheckOut().setEnabled(p_Enabled);
    }



    //grueda05102004_begin
    public boolean validateName(String p_Name, Project2 p_Project)
    //grueda05102004_end
    {
        p_Name = p_Name.trim();
        boolean notexistName = true;
        if (p_Project == null) {
            return notexistName;
        }
        for (int i = 0; i < p_Project.getTestObjectReferences().size(); i++) {
            TestObjectReference testObjectReference = (TestObjectReference)p_Project.getTestObjectReferences().elementAt(i);
            if (!m_TestObjectReference.equals(testObjectReference)) {
                String name = testObjectReference.getName();
                if (p_Name.equalsIgnoreCase(name))
                    notexistName = false;
            }
        }
        return notexistName;
    }



//        if (/*this.m_TestObjectReference*/ m_TestObject.getM_AccessState().equals(BusinessRules.ACCESS_STATE_CHECKED_OUT)) {
//            if (project.getAccessState().equals(BusinessRules.ACCESS_STATE_CHECKED_OUT) &&
//                project.getUser().equalsIgnoreCase(this.m_Session.getM_User())) {
//        	    CMAction.RENAME_TESTOBJECT.setEnabled(true);
//            }
//            else {
//        	    CMAction.RENAME_TESTOBJECT.setEnabled(false);
//            }
//        }
//        else {
//    	    CMAction.RENAME_TESTOBJECT.setEnabled(false);
//        }
 //   }

    //grueda26122004_end
    //integration_fcastro_17082004_end
    //HCanedo_22112004_Begin
    public void setCopyTestObject() {
        CMCutCopyTestObject.getInstance().setisCutTestObject(false);
        ProjectReference currentProjectReference = ProjectManager.getSelectedProjectReference();
        TestObjectReference currentTestObjectReference = CMApplication.frame.getTreeWorkspaceView().getCurrentTestObjectReference();
        TestObject currentTestObject = CMApplication.frame.getTreeWorkspaceView().getCurrentTestObject();
        String absoluteTestObjectPath = "";
        if (!currentTestObject.getAccessState().equalsIgnoreCase(BusinessRules.ACCESS_STATE_CHECKED_OUT_LOCAL))
        {


        absoluteTestObjectPath = CMApplication.frame.getCmApplication().getSessionManager().getM_WorkspaceManager().buildAbsoluteTestObjectFilePath(currentProjectReference,
            currentTestObjectReference);
        }
        else
        {
           absoluteTestObjectPath = CMApplication.frame.getCmApplication().getSessionManager().getM_WorkspaceManager().buildAbsoluteLocalTestObjectPath(currentProjectReference, currentTestObjectReference)
           +BusinessRules.URL_SEPARATOR+
        	   ((CMTestObjectNode)CMApplication.frame.getTreeWorkspaceView().getCurrentTestObjectNode().getUserObject()).getLocalTestObjectReference().getFileName();
        }
        CMCutCopyTestObject.getInstance().setpathTestObject(absoluteTestObjectPath);
    }

    public void setCutTestObject() {
        CMCutCopyTestObject.getInstance().setisCutTestObject(true);
        ProjectReference currentProjectReference = CMApplication.frame.getTreeWorkspaceView().getCurrentProjectReference();
        TestObjectReference currentTestObjectReference = CMApplication.frame.getTreeWorkspaceView().getCurrentTestObjectReference();
        TestObject currentTestObject = CMApplication.frame.getTreeWorkspaceView().getCurrentTestObject();
        String absoluteTestObjectPath = "";
        if (!currentTestObject.getAccessState().equalsIgnoreCase(BusinessRules.ACCESS_STATE_CHECKED_OUT_LOCAL))
        {


        absoluteTestObjectPath = CMApplication.frame.getCmApplication().getSessionManager().getM_WorkspaceManager().buildAbsoluteTestObjectFilePath(currentProjectReference,
            currentTestObjectReference);
        }
        else
        {
           absoluteTestObjectPath = CMApplication.frame.getCmApplication().getSessionManager().getM_WorkspaceManager().buildAbsoluteLocalTestObjectPath(currentProjectReference, currentTestObjectReference)
           +BusinessRules.URL_SEPARATOR+
        	   ((CMTestObjectNode)CMApplication.frame.getTreeWorkspaceView().getCurrentTestObjectNode().getUserObject()).getLocalTestObjectReference().getFileName();
        }




        CMCutCopyTestObject.getInstance().setpathTestObject(absoluteTestObjectPath);
        DefaultMutableTreeNode testObjectNode = CMApplication.frame.getTreeWorkspaceView().getSelectedNode();
        CMCutCopyTestObject.getInstance().setTestObjectNode(testObjectNode);
        Object projectNodeInfo=CMApplication.frame.getTreeWorkspaceView().getCurrentProjectNode();
        CMCutCopyTestObject.getInstance().setM_ProjectInfoNode((CMProjectNode) projectNodeInfo);
    }

    public void pasteTestobject() {
        if (!CMCutCopyTestObject.getInstance().getisCutTestObject()) {
            File testObjectToCopy = new File(CMCutCopyTestObject.getInstance().getpathTestObject());
            CMApplication.frame.getTreeWorkspaceView().copyTestObjectInToProyect(testObjectToCopy);
        }
        else {
        	ProjectReference projectReference = CMApplication.frame.getTreeWorkspaceView().getCurrentProjectReference();
        	if (projectReference== null)
        		return;
        	//dont cut and paste into the same project
        	//System.out.println(CMCutCopyTestObject.getInstance().getpathTestObject());
        	for (TestObjectReference toRef : projectReference.getProject().getTestObjectReferences())
        	{
        		String prefix = "";
        		if (toRef.getM_AccessState().equalsIgnoreCase(BusinessRules.ACCESS_STATE_CHECKED_OUT_LOCAL))
        			prefix = projectReference.getM_LocalProjectReference().getPath().replace('\\','/')+BusinessRules.URL_SEPARATOR;
        		else
        			prefix = projectReference.getPath().replace('\\','/')+BusinessRules.URL_SEPARATOR;
        		//System.out.println((prefix+toRef.getFilePath().replace('\\','/')));
        		if ((prefix+toRef.getFilePath().replace('\\','/')).equalsIgnoreCase(CMCutCopyTestObject.getInstance().getpathTestObject().replace('\\','/')))
        			return;

        	}
            File testObjectToCopy = new File(CMCutCopyTestObject.getInstance().getpathTestObject());
            boolean copySuccess = CMApplication.frame.getTreeWorkspaceView().copyTestObjectInToProyect(testObjectToCopy);
            if (copySuccess) {
                DefaultMutableTreeNode testObjectNode = CMCutCopyTestObject.getInstance().getTestObjectNode();
                CMApplication.frame.getTreeWorkspaceView().selectNode(testObjectNode);
                CMApplication.frame.getTreeWorkspaceView().removeTestObject(CMCutCopyTestObject.getInstance().getM_ProjectInfoNode().getProject());
                CMCutCopyTestObject.getInstance().setisCutTestObject(false);
            }
        }
    }


    public void jCheckBoxSystemSeparatorStateChanged(ChangeEvent e) {
        if(getJCheckBoxSystemSeparator().isSelected())
        {
        	getJComboBoxDecimal().setSelectedIndex(0);
			getJComboBoxMiles().setSelectedIndex(1);
			getJComboBoxDecimal().setEnabled(false);
            getJComboBoxMiles().setEnabled(false);
        }
        else{
        	getJComboBoxDecimal().setEnabled(true);
            getJComboBoxMiles().setEnabled(true);
        }
        changeNumberCharacterSeparator();
    }

    public void jComboBoxDecimalActionPerformed(ActionEvent e) {
        if(getJComboBoxDecimal().getSelectedIndex()==0)
        {
        	getJComboBoxMiles().setSelectedIndex(1);
            int saveUnsave = CMApplication.frame.getCmApplication().getSessionManager().getM_Save_UnsaveVariable() + 1;
            CMApplication.frame.getCmApplication().getSessionManager().setM_Save_UnsaveVariable(saveUnsave);

        }
        else
        {
        	getJComboBoxMiles().setSelectedIndex(0);
        }
        changeNumberCharacterSeparator();
    }

    public void jComboBoxMilesActionPerformed(ActionEvent e) {
        if(getJComboBoxMiles().getSelectedIndex()==0)
        {
        	getJComboBoxDecimal().setSelectedIndex(1);
            int saveUnsave = CMApplication.frame.getCmApplication().getSessionManager().getM_Save_UnsaveVariable() + 1;
            CMApplication.frame.getCmApplication().getSessionManager().setM_Save_UnsaveVariable(saveUnsave);
        }
        else
        {
        	getJComboBoxDecimal().setSelectedIndex(0);
        }
        changeNumberCharacterSeparator();
    }

    //My adds
    public void jComboBoxToolVendorActionPerformed(ActionEvent e) {
    	if (getJComboBoxToolVendor().getItemCount()!= 0 ){
    		String tvendor = getJComboBoxToolVendor().getSelectedItem().toString();

    		if (m_TestObject != null){
    			String oldToolVendor = m_TestObject.getToolVendor();
            	if (oldToolVendor != null){//Ccastedo
            		if (!oldToolVendor.equals(tvendor)){
                		if(cbToolVendorChanged){
                			int confirmation = JOptionPane.showConfirmDialog(CMApplication.frame,CMMessages.getString("WANTS_TO_CHANGE_TOOLVENDOR"),CMMessages.getString("TITLE_DELETE_EXTERNAL_APPLICATION"),JOptionPane.YES_NO_OPTION); //$NON-NLS-1$ //$NON-NLS-2$
                            if( confirmation == JOptionPane.YES_OPTION) {

                            	m_TestObject.setToolVendor(tvendor);
                            	this.m_ToolVendor=tvendor;
                            	getJComboBoxToolVendor().setActionCommand(tvendor);

                            	CMApplication.frame.getTreeWorkspaceView().save2();
                            	 fillTechnologyComboBox();
                            	 m_TestObject.setToolVendorTechnology(getJComboBoxToolVendorTech().getSelectedItem().toString());
                            }
                            else{
                            	getJComboBoxToolVendor().setSelectedItem(m_TestObject.getToolVendor());//jComboBoxToolVendorTech.getSelectedItem().toString());

                            }
                		}
            	}

            }


    		}

    	}
    }

    public void jComboBoxToolVendorTechActionPerformed(ActionEvent e) {

    	if (getJComboBoxToolVendorTech().getItemCount()!= 0 ){
    		String tVendorTech = getJComboBoxToolVendorTech().getSelectedItem().toString();
    	 if (m_TestObject != null){
    		String oldToolVendorTech = m_TestObject.getToolVendorTechnology();
    		if (!oldToolVendorTech.equals(tVendorTech)){
    			if(cbTechnologyChanged){
           		int confirmation = JOptionPane.showConfirmDialog(CMApplication.frame,CMMessages.getString("WANTS_TO_CHANGE_TOOLVENDORTECH"),CMMessages.getString("TITLE_DELETE_EXTERNAL_APPLICATION"),JOptionPane.YES_NO_OPTION); //$NON-NLS-1$ //$NON-NLS-2$
                  if( confirmation == JOptionPane.YES_OPTION) {
                  	toolVendorTech = tVendorTech;
              		m_TestObject.setToolVendorTechnology(tVendorTech);
              	//	getJComboBoxToolVendorTech().setActionCommand(tVendorTech);
              		int saveUnsave = CMApplication.frame.getCmApplication().getSessionManager().getM_Save_UnsaveVariable() + 1;
              		CMApplication.frame.getCmApplication().getSessionManager().setM_Save_UnsaveVariable(saveUnsave);
                    //hmendez_29122005_begin
//                    int l_elementsSize =m_TestObject.getStructure().getLnkElements().size();
//              		for (int i=0;i<l_elementsSize;i++)
//              		{
//              		  CMApplication.frame.getM_CMGridElements().updateElement(((Element)m_TestObject.getStructure().getLnkElements().get(i)));
//               		}
//                    //hmendez_29122005_end
//
               	 }
    			}
    	     }
    	 }
    }


    }


    public void setToolVendor(TestObject p_TestObject){
    	String ToolVendor=p_TestObject.getToolVendor();

    		String ToolVendorTech=p_TestObject.getToolVendorTechnology();
            fillToolVendorComboBox();

            cbToolVendorChanged = false;
            getJComboBoxToolVendor().setSelectedItem(ToolVendor);
            cbToolVendorChanged = true;


            ToolVendorManager toolvendormanager = ToolVendorManager.INSTANCE;

        	String toolven= getJComboBoxToolVendor().getSelectedItem().toString();
        	m_ToolVendorfile = (ToolVendor)(toolvendormanager.findToolVendorByName(toolven));

           // jComboBoxToolVendorTech.setSelectedItem(ToolVendorTech);*/
            fillTechnologyComboBox();
            cbTechnologyChanged=false;
            getJComboBoxToolVendorTech().setSelectedItem(ToolVendorTech);
            cbTechnologyChanged=true;

            getJComboBoxToolVendorTech().setSelectedItem(ToolVendorTech);




    }

   public void updatePanelTestObjecView(){
			   fillToolVendorComboBox();
			   cbTechnologyChanged=true;
		       //fillTechnologyComboBox();
    }

   public Technology getTechnology(){
	   String algo  = getJComboBoxToolVendorTech().getSelectedItem().toString();
	   Technology tech = null;
	   if (algo.equals(null)){

	   }
	   else{
		   tech = (Technology)getJComboBoxToolVendorTech().getSelectedItem();
	   }

	   return tech;
   }

    private void changeNumberCharacterSeparator(){
        m_TestObject.setDecimalSeparator(getJComboBoxDecimal().getSelectedItem().toString());
        m_TestObject.setMilesSeparator(getJComboBoxMiles().getSelectedItem().toString());
        boolean cselected = getJCheckBoxSystemSeparator().isSelected();
        if (m_TestObject.isDefaultSeparator()!= cselected)
        	m_TestObject.setDefaultSeparator(cselected);
    }


    //HCanedo_22112004_End
    /////////////////////////////////////////////////////////////////////////////////////////////////
    //hmendez_06102005_begin
    public  List getOrder()//Agregar en todos los JPanel
    {
   	   Vector<Component> componentList = new Vector<Component>();
   	   //componentList.add(m_Frame.getTabbedPane());
   	   if (getJButtonChangeName().isEnabled())
         componentList.add(getJButtonChangeName());
   	   if (getJRadioButtonCheckIn().isEnabled())
         componentList.add(getJRadioButtonCheckIn());
   	   if ( getJRadioButtonCheckOut().isEnabled())
         componentList.add( getJRadioButtonCheckOut());
   	   if (getJRadioButtonLocalCheckOut().isEnabled())
         componentList.add(getJRadioButtonLocalCheckOut());
   	   if (getJEditorPaneDescription().isEnabled())
         componentList.add(getJEditorPaneDescription());
   	   if (getCMJEditorPaneFocusChangeable().isEnabled())
	     componentList.add(getCMJEditorPaneFocusChangeable());
   	   if (getJCheckBoxSystemSeparator().isEnabled())
	     componentList.add(getJCheckBoxSystemSeparator());
   	   if (getJComboBoxMiles().isEnabled())
         componentList.add(getJComboBoxMiles());
   	   if (getJComboBoxDecimal().isEnabled())
         componentList.add(getJComboBoxDecimal());
   	   if (getJComboBoxToolVendor().isEnabled())
   	      componentList.add(getJComboBoxToolVendor());
   	   if (getJComboBoxToolVendorTech().isEnabled())
   	     componentList.add(getJComboBoxToolVendorTech());
   	   componentList.add(CMApplication.frame.getTreeWorkspaceView());
       return componentList;
	 }
      public void handleCMModelChange(CMModelEvent p_evt) {
  		if (p_evt.getSource() instanceof TestObject)
  		{
  			if (p_evt.getChangedField() == CMField.ORIGIN)
  			{
  				getJCheckBoxFlowTestObject().setEnabled(((TestObject)p_evt.getSource()).getOrigin()!=null);
  				getJCheckBoxFlowTestObject().setSelected(((TestObject)p_evt.getSource()).getOrigin()!=null);
  				//jTextFieldFilePath.setText(m_TestObjectReference.getM_FilePath());
  			}
  		}
		if (p_evt.getSource() instanceof Project2)
		{
			if (p_evt.getChangedField() == CMField.NAME)
			{
				getJTextField().setText(m_TestObject.getName());
				//jTextFieldFilePath.setText(m_TestObjectReference.getM_FilePath());
			}
		}
		if (p_evt.getSource() instanceof TestObjectReference)
		{
			if (p_evt.getChangedField() == CMField.PROJECT)
			{
				this.m_TestObject.removeModelListener(this);
				this.m_TestObject = m_TestObjectReference.getTestObject();
				if (this.m_TestObject!=null)
				{
					this.m_TestObject.addModelListener(this);
					getJTextField().setText(m_TestObject.getName());
					//jTextFieldFilePath.setText(m_TestObjectReference.getM_FilePath());
				}
			}
		}
	}
    //Ccastedo ends 20-04-06

	/**
	 * This method initializes jPanelName
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelName() {
		if (jPanelName == null) {
			JLabelCurrentUserText = new JLabel();
			JLabelCurrentUserText.setFont(new Font("Tahoma", Font.BOLD, 11));
			JLabelCurrentUserText.setIcon(CMIcon.USER.getImageIcon());
			JLabelCurrentUserText.setIconTextGap(5);
			JLabelCurrentUserText.setText("");
			JLabelCurrentUserText.setPreferredSize(new Dimension(200, 20));
			FlowLayout flowLayout = new FlowLayout();
			flowLayout.setAlignment(java.awt.FlowLayout.LEFT);
			jLabelName = new JLabel();
			jLabelName.setToolTipText(CMMessages.getString("LABEL_NAME"));
			jLabelName.setPreferredSize(new java.awt.Dimension(72,14));
			jLabelName.setText(CMMessages.getString("LABEL_NAME"));
			jPanelName = new JPanel();
			jPanelName.setPreferredSize(new java.awt.Dimension(315,30));
			jPanelName.setLayout(flowLayout);
			jPanelName.add(jLabelName, null);
			jPanelName.add(getJTextField(), null);
			jPanelName.add(getJButtonChangeName(), null);
		}
		return jPanelName;
	}

	/**
	 * This method initializes jTextField
	 *
	 * @return javax.swing.JTextField
	 */
	public JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.setText("");
			jTextField.setPreferredSize(new java.awt.Dimension(200,20));
			jTextField.setEditable(false);
			jTextField.setFocusable(false);
		}
		return jTextField;
	}

	/**
	 * This method initializes CMToolBarButton
	 *
	 * @return bi.view.utils.CMToolBarButton
	 */
	private CMToolBarButton getJButtonChangeName() {
		if (JButtonChangeName == null) {
			JButtonChangeName = new CMToolBarButton(CMAction.RENAME_TESTOBJECT
					.getAction());
		}
		return JButtonChangeName;
	}

	/**
	 * This method initializes jPanelCurrentUser
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelCurrentUser() {
		if (jPanelCurrentUser == null) {
			FlowLayout flowLayout1 = new FlowLayout();
			flowLayout1.setAlignment(java.awt.FlowLayout.LEFT);
			jLabelCurrentUser = new JLabel();
			jLabelCurrentUser.setFont(new Font("Tahoma", Font.PLAIN, 11));
			jLabelCurrentUser.setPreferredSize(new java.awt.Dimension(72,14));
			jLabelCurrentUser.setText(CMMessages.getString("LABEL_CURRENT_USER"));
			jPanelCurrentUser = new JPanel();
			jPanelCurrentUser.setPreferredSize(new java.awt.Dimension(287,30));
			jPanelCurrentUser.setLayout(flowLayout1);
			jPanelCurrentUser.add(jLabelCurrentUser, null);
			jPanelCurrentUser.add(JLabelCurrentUserText, null);
		}
		return jPanelCurrentUser;
	}

	/**
	 * This method initializes jPanelChecks
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelChecks() {
		if (jPanelChecks == null) {
			jPanelChecks = new JPanel();
			jPanelChecks.setLayout(new BoxLayout(getJPanelChecks(), BoxLayout.Y_AXIS));
			jPanelChecks.add(getJRadioButtonCheckIn(), null);
			jPanelChecks.add(getJRadioButtonCheckOut(), null);
			jPanelChecks.add(getJRadioButtonLocalCheckOut(), null);
		}
		return jPanelChecks;
	}

	/**
	 * This method initializes jRadioButton
	 *
	 * @return javax.swing.JRadioButton
	 */
	public JRadioButton getJRadioButtonCheckOut() {
		if (jRadioButtonCheckOut == null) {
			jRadioButtonCheckOut = new JRadioButton();
			jRadioButtonCheckOut.setActionCommand(BusinessRules.ACCESS_STATE_CHECKED_OUT);
			jRadioButtonCheckOut.setText(CMMessages.getString("LABEL_CHECKED_OUT"));
			jRadioButtonCheckOut.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					jLabelKey.setIcon(CMIcon.UNLOCK.getImageIcon());
				}
			});
			jRadioButtonCheckOut.addActionListener(this);
		}
		return jRadioButtonCheckOut;
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
	 * This method initializes jRadioButton
	 *
	 * @return javax.swing.JRadioButton
	 */
	public JRadioButton getJRadioButtonLocalCheckOut() {
		if (jRadioButtonLocalCheckOut == null) {
			jRadioButtonLocalCheckOut = new JRadioButton();
			jRadioButtonLocalCheckOut.setActionCommand(BusinessRules.ACCESS_STATE_CHECKED_OUT_LOCAL);
			jRadioButtonLocalCheckOut.setText(CMMessages.getString("LABEL_CHECKED_OUT_LOCAL"));
			jRadioButtonLocalCheckOut.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					jLabelKey.setIcon(CMIcon.UNLOCKLOCAL.getImageIcon());
				}
			});
			jRadioButtonLocalCheckOut.addActionListener(this);
		}
		return jRadioButtonLocalCheckOut;
	}

	/**
	 * This method initializes jPanelByUser
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelByUser() {
		if (jPanelByUser == null) {
			jLabelByUser = new JLabel();
			jLabelByUser.setText(CMMessages.getString("LABEL_BY_THE_USER"));
			jLabelByUser.setPreferredSize(new java.awt.Dimension(72,14));
			jPanelByUser = new JPanel();
			jPanelByUser.setPreferredSize(new java.awt.Dimension(207,70));
			jPanelByUser.add(jLabelByUser, null);
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
	 * This method initializes jPanel
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelAccessState() {
		if (jPanelAccessState == null) {
			jLabelKey = new JLabel();
			jLabelKey.setPreferredSize(new Dimension(67, 32));
			jLabelKey.setHorizontalAlignment(SwingConstants.CENTER);
			jLabelKey.setHorizontalTextPosition(SwingConstants.CENTER);
			jLabelKey.setIconTextGap(0);
			jLabelKey.setText("");
			FlowLayout flowLayout2 = new FlowLayout();
			flowLayout2.setAlignment(java.awt.FlowLayout.LEFT);
			jPanelAccessState = new JPanel();
			jPanelAccessState.setLayout(flowLayout2);
			jPanelAccessState.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), CMMessages.getString("LABEL_ACCESS_STATE"), TitledBorder.LEADING, TitledBorder.TOP, new Font("SansSerif", 0, 11), new Color(60, 60, 60)));
			jPanelAccessState.add(jLabelKey, null);
			jPanelAccessState.add(getJPanelChecks(), null);
			jPanelAccessState.add(getJPanelByUser(), null);
		}
		return jPanelAccessState;
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
			jPanelDescription.setPreferredSize(new java.awt.Dimension(575,150));
			jPanelDescription.setMaximumSize(new java.awt.Dimension(2147483647,150));
			jPanelDescription.setMinimumSize(new java.awt.Dimension(37,42));
			jPanelDescription.add(getJScrollPaneDescription(), java.awt.BorderLayout.CENTER);
		}
		return jPanelDescription;
	}

	/**
	 * This method initializes jScrollPane
	 *
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPaneDescription() {
		if (jScrollPaneDescription == null) {
			jScrollPaneDescription = new JScrollPane();
			jScrollPaneDescription.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
			jScrollPaneDescription.setViewportView(getJEditorPaneDescription());
		}
		return jScrollPaneDescription;
	}

	/**
	 * This method initializes CMJEditorPaneFocusChangeable
	 *
	 * @return bi.view.utils.CMJEditorPaneFocusChangeable
	 */
	private CMJEditorPaneFocusChangeable getJEditorPaneDescription() {
		if (jEditorPaneDescription == null) {
			jEditorPaneDescription = new CMJEditorPaneFocusChangeable();
			jEditorPaneDescription.setText("");
			jEditorPaneDescription.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
			jEditorPaneDescription.addCaretListener(new javax.swing.event.CaretListener() {
				public void caretUpdate(javax.swing.event.CaretEvent e) {
					jEditorDescriptionCaretUpdate(e);
				}
			});
		}
		return jEditorPaneDescription;
	}

	/**
	 * This method initializes jPanel
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelPreconditions() {
		if (jPanelPreconditions == null) {
			jPanelPreconditions = new JPanel();
			jPanelPreconditions.setLayout(new BorderLayout());
			jPanelPreconditions.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), CMMessages.getString("LABEL_PRECONDITIONS"), TitledBorder.LEADING, TitledBorder.TOP, new Font("SansSerif", 0, 11), new Color(60, 60, 60)));
			jPanelPreconditions.setPreferredSize(new java.awt.Dimension(575,150));
			jPanelPreconditions.setMaximumSize(new java.awt.Dimension(2147483647,150));
			jPanelPreconditions.setMinimumSize(new java.awt.Dimension(37,42));
			jPanelPreconditions.add(getJScrollPanePreconditions(), java.awt.BorderLayout.CENTER);
		}
		return jPanelPreconditions;
	}

	/**
	 * This method initializes jScrollPane
	 *
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPanePreconditions() {
		if (jScrollPanePreconditions == null) {
			jScrollPanePreconditions = new JScrollPane();
			jScrollPanePreconditions.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
			jScrollPanePreconditions.setViewportView(getCMJEditorPaneFocusChangeable());
		}
		return jScrollPanePreconditions;
	}

	/**
	 * This method initializes CMJEditorPaneFocusChangeable
	 *
	 * @return bi.view.utils.CMJEditorPaneFocusChangeable
	 */
	private CMJEditorPaneFocusChangeable getCMJEditorPaneFocusChangeable() {
		if (CMJEditorPaneFocusChangeable == null) {
			CMJEditorPaneFocusChangeable = new CMJEditorPaneFocusChangeable();
			CMJEditorPaneFocusChangeable.setText("");
			CMJEditorPaneFocusChangeable
					.addCaretListener(new javax.swing.event.CaretListener() {
						public void caretUpdate(javax.swing.event.CaretEvent e) {
							jEditorPanePreconditionsCaretUpdate(e);
						}
					});
			CMJEditorPaneFocusChangeable.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		}
		return CMJEditorPaneFocusChangeable;
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
			jPanelMainContainer.add(getJPanelName(), null);
			jPanelMainContainer.add(getJPanelCurrentUser(), null);
			jPanelMainContainer.add(getJPanelAccessState(), null);
			jPanelMainContainer.add(getJPanelDescription(), null);
			jPanelMainContainer.add(getJPanelPreconditions(), null);
			jPanelMainContainer.add(getJPanelOtherProperties(), null);

		}
		return jPanelMainContainer;
	}

	/**
	 * This method initializes jPanelTop
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelTop() {
		if (jPanelTop == null) {
			jPanelTop = new JPanel();
			jPanelTop.setPreferredSize(new java.awt.Dimension(10,20));
		}
		return jPanelTop;
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
	 * This method initializes jPanelOtherPropertire
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelOtherProperties() {
		if (jPanelOtherProperties == null) {
			jPanelOtherProperties = new JPanel();
			jPanelOtherProperties.setLayout(new BoxLayout(getJPanelOtherProperties(), BoxLayout.X_AXIS));
			jPanelOtherProperties.setPreferredSize(new java.awt.Dimension(0,140));
			jPanelOtherProperties.add(getJPanelSeparators(), null);
			jPanelOtherProperties.add(getJPanelOtherPropertiesSub(), null);
		}
		return jPanelOtherProperties;
	}

	/**
	 * This method initializes jPanelSeparators
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelSeparators() {
		if (jPanelSeparators == null) {
			jPanelSeparators = new JPanel();
			jPanelSeparators.setMinimumSize(new java.awt.Dimension(50,96));
			jPanelSeparators.setLayout(new BoxLayout(getJPanelSeparators(), BoxLayout.Y_AXIS));
			jPanelSeparators.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED), CMMessages.getString("TESTOBJECT_PANEL_SEPARATOR_TITLE"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11), java.awt.Color.black));
			jPanelSeparators.setPreferredSize(new java.awt.Dimension(0,60));
			jPanelSeparators.add(getJPanelDefaultSeparators(), null);
			jPanelSeparators.add(getJPanelSeparatorCombos(), null);
		}
		return jPanelSeparators;
	}

	/**
	 * This method initializes jPanelTechnologhy
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelOtherPropertiesSub() {
		if (jPanelOtherPropertiesSub == null) {
			jPanelOtherPropertiesSub = new JPanel();
			jPanelOtherPropertiesSub.setLayout(new BoxLayout(getJPanelOtherPropertiesSub(), BoxLayout.Y_AXIS));
			jPanelOtherPropertiesSub.setPreferredSize(new java.awt.Dimension(0,0));
			jPanelOtherPropertiesSub.add(getJPanelTechnologhy(), null);
			jPanelOtherPropertiesSub.add(getJPanelOrigin(), null);
		}
		return jPanelOtherPropertiesSub;
	}

	/**
	 * This method initializes jPanelTechnologhy
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelTechnologhy() {
		if (jPanelTechnologhy == null) {
			jLabelTechnologhy = new JLabel();
		    jLabelTechnologhy.setText(CMMessages.getString("LABEL_ToolVendorTech"));//"Technology");
		    jLabelTechnologhy.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabelToolVendor = new JLabel();

			jLabelToolVendor.setText(CMMessages.getString("LABEL_ToolVendor"));//"Tool Vendor");
			jLabelToolVendor.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			GridLayout gridLayout1 = new GridLayout();
			gridLayout1.setRows(2);
			gridLayout1.setColumns(2);
			jPanelTechnologhy = new JPanel();
			jPanelTechnologhy.setLayout(gridLayout1);
			jPanelTechnologhy.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED), CMMessages.getString("TESTOBJECT_PANEL_TOOLVENDOR_TITLE"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11), java.awt.Color.black));
			jPanelTechnologhy.setPreferredSize(new java.awt.Dimension(0,30));
			jPanelTechnologhy.add(getJPanelLabelToolVendor(), null);
			jPanelTechnologhy.add(getJPanelToolVendor(), null);
			jPanelTechnologhy.add(getJPanelLabelTechnology(), null);
			jPanelTechnologhy.add(getJPanelTech2(), null);
		}
		return jPanelTechnologhy;
	}

	/**
	 * This method initializes jPanelOrigin
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelOrigin() {
		if (jPanelOrigin == null) {
			FlowLayout flowLayout7 = new FlowLayout();
			flowLayout7.setVgap(0);
			flowLayout7.setAlignment(java.awt.FlowLayout.CENTER);
			jPanelOrigin = new JPanel();
			jPanelOrigin.setLayout(flowLayout7);
			jPanelOrigin.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED), CMMessages.getString("TESTOBJECT_PANEL_ORIGIN_TITLE"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11), java.awt.Color.black));
			jPanelOrigin.setPreferredSize(new java.awt.Dimension(0,0));
			jPanelOrigin.add(getJCheckBoxFlowTestObject(), null);
		}
		return jPanelOrigin;
	}

	/**
	 * This method initializes jCheckBox
	 *
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBoxSystemSeparator() {
		if (jCheckBoxSystemSeparator == null) {
			jCheckBoxSystemSeparator = new JCheckBox();
		    jCheckBoxSystemSeparator.setText(CMMessages.getString("LABEL_DEFAULT_SEPARATOR"));//"usar separadores por defecto");
		    jCheckBoxSystemSeparator.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
		    jCheckBoxSystemSeparator.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
		    jCheckBoxSystemSeparator.setMargin(new java.awt.Insets(0, 0, 0, 0));
		    jCheckBoxSystemSeparator.addChangeListener(new ChangeListener(){public void stateChanged(ChangeEvent e){jCheckBoxSystemSeparatorStateChanged(e);}});

		}
		return jCheckBoxSystemSeparator;
	}

	/**
	 * This method initializes jPanelThousandPanel
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelThousandPanel() {
		if (jPanelThousandPanel == null) {
			FlowLayout flowLayout3 = new FlowLayout();
			flowLayout3.setAlignment(java.awt.FlowLayout.LEFT);
			flowLayout3.setVgap(9);
			flowLayout3.setHgap(2);
			jPanelThousandPanel = new JPanel();
			jPanelThousandPanel.setLayout(flowLayout3);
			jPanelThousandPanel.add(getJComboBoxMiles(), null);
		}
		return jPanelThousandPanel;
	}

	/**
	 * This method initializes CMBaseJComboBox
	 *
	 * @return bi.view.utils.CMBaseJComboBox
	 */
	private CMBaseJComboBox getJComboBoxMiles() {
		if (jComboBoxMiles == null) {
			jComboBoxMiles = new CMBaseJComboBox(
					BusinessRules.NUMBER_CHARACTERS_SEPARATOR);
			 jComboBoxMiles.setPreferredSize(new java.awt.Dimension(60, 20));
		        jComboBoxMiles.setMinimumSize(new java.awt.Dimension(60, 20));
		        jComboBoxMiles.setSelectedIndex(1);
		        jComboBoxMiles.setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 12));
		        jComboBoxMiles.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){jComboBoxMilesActionPerformed(e);}});
		}
		return jComboBoxMiles;
	}

	/**
	 * This method initializes jPanelDecimal
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelDecimal() {
		if (jPanelDecimal == null) {
			FlowLayout flowLayout4 = new FlowLayout();
			flowLayout4.setAlignment(java.awt.FlowLayout.LEFT);
			flowLayout4.setVgap(9);
			flowLayout4.setHgap(2);
			jPanelDecimal = new JPanel();
			jPanelDecimal.setPreferredSize(new java.awt.Dimension(64,30));
			jPanelDecimal.setLayout(flowLayout4);
			jPanelDecimal.add(getJComboBoxDecimal(), null);
		}
		return jPanelDecimal;
	}

	/**
	 * This method initializes CMBaseJComboBox
	 *
	 * @return bi.view.utils.CMBaseJComboBox
	 */
	private CMBaseJComboBox getJComboBoxDecimal() {
		if (jComboBoxDecimal == null) {
			jComboBoxDecimal = new CMBaseJComboBox(BusinessRules.NUMBER_CHARACTERS_SEPARATOR);
		    jComboBoxDecimal.setMinimumSize(new java.awt.Dimension(60, 20));
	        jComboBoxDecimal.setPreferredSize(new java.awt.Dimension(60, 20));
			jComboBoxDecimal.setSelectedIndex(0);
	        jComboBoxDecimal.setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 12));
	        jComboBoxDecimal.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){jComboBoxDecimalActionPerformed(e);}});
		}
		return jComboBoxDecimal;
	}

	/**
	 * This method initializes jPanelToolVendor
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelToolVendor() {
		if (jPanelToolVendor == null) {
			FlowLayout flowLayout5 = new FlowLayout();
			flowLayout5.setAlignment(java.awt.FlowLayout.LEFT);
			flowLayout5.setVgap(4);
			jPanelToolVendor = new JPanel();
			jPanelToolVendor.setMinimumSize(new java.awt.Dimension(120,24));
			jPanelToolVendor.setLayout(flowLayout5);
			jPanelToolVendor.add(getJComboBoxToolVendor(), null);
		}
		return jPanelToolVendor;
	}

	/**
	 * This method initializes CMBaseJComboBox
	 *
	 * @return bi.view.utils.CMBaseJComboBox
	 */
	private CMBaseJComboBox getJComboBoxToolVendor() {
		if (jComboBoxToolVendor == null) {
			jComboBoxToolVendor = new CMBaseJComboBox(this);
		    jComboBoxToolVendor.setPreferredSize(new java.awt.Dimension(100, 20));
		    jComboBoxToolVendor.setMinimumSize(new java.awt.Dimension(100, 20));
		    jComboBoxToolVendor.setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 10));
		    ActionListener actionListener = new ActionListener(){	public void actionPerformed(ActionEvent e){jComboBoxToolVendorActionPerformed(e);}};
	        jComboBoxToolVendor.addActionListener(actionListener);

		}
		return jComboBoxToolVendor;
	}

	/**
	 * This method initializes CMBaseJComboBox
	 *
	 * @return bi.view.utils.CMBaseJComboBox
	 */
	private CMBaseJComboBox getJComboBoxToolVendorTech() {
		if (jComboBoxToolVendorTech == null) {
			jComboBoxToolVendorTech = new CMBaseJComboBox(this);
			 jComboBoxToolVendorTech.setPreferredSize(new java.awt.Dimension(100, 20));
		        jComboBoxToolVendorTech.setMinimumSize(new java.awt.Dimension(100, 20));
		        jComboBoxToolVendorTech.setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 10));
		        jComboBoxToolVendorTech.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){jComboBoxToolVendorTechActionPerformed(e);}});

		}
		return jComboBoxToolVendorTech;
	}

	/**
	 * This method initializes jPanelTech2
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelTech2() {
		if (jPanelTech2 == null) {
			FlowLayout flowLayout6 = new FlowLayout();
			flowLayout6.setAlignment(java.awt.FlowLayout.LEFT);
			flowLayout6.setVgap(4);
			jPanelTech2 = new JPanel();
			jPanelTech2.setLayout(flowLayout6);
			jPanelTech2.add(getJComboBoxToolVendorTech(), null);
		}
		return jPanelTech2;
	}

	/**
	 * This method initializes jCheckBoxFlowTestObject
	 *
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBoxFlowTestObject() {
		if (jCheckBoxFlowTestObject == null) {
			jCheckBoxFlowTestObject = new JCheckBox(CMAction.TESTOBJECT_ORDERED_FLOWS.getAction());
		}
		return jCheckBoxFlowTestObject;
	}

	/**
	 * This method initializes jPanelDefaultSeparators
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelDefaultSeparators() {
		if (jPanelDefaultSeparators == null) {
			FlowLayout flowLayout8 = new FlowLayout();
			flowLayout8.setAlignment(java.awt.FlowLayout.CENTER);
			jPanelDefaultSeparators = new JPanel();
			jPanelDefaultSeparators.setLayout(flowLayout8);
			jPanelDefaultSeparators.add(getJCheckBoxSystemSeparator(), null);
		}
		return jPanelDefaultSeparators;
	}

	/**
	 * This method initializes jPanelSeparatorCombos
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelSeparatorCombos() {
		if (jPanelSeparatorCombos == null) {
			jLabel1 = new JLabel();
			jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel1.setText(CMMessages.getString("LABEL_DECIMAL_SEPARATOR"));
			jLabel1.setHorizontalTextPosition(SwingConstants.CENTER);
			jLabel = new JLabel();
			jLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel.setText(CMMessages.getString("LABEL_MILES_SEPARATOR"));
			GridLayout gridLayout2 = new GridLayout();
			gridLayout2.setRows(2);
			gridLayout2.setColumns(2);
			jPanelSeparatorCombos = new JPanel();
			jPanelSeparatorCombos.setLayout(gridLayout2);
			jPanelSeparatorCombos.add(getJPanelLabelThousandSep(), null);
			jPanelSeparatorCombos.add(getJPanelThousandPanel(), null);
			jPanelSeparatorCombos.add(getJPanelLabelThousand(), null);
			jPanelSeparatorCombos.add(getJPanelDecimal(), null);
		}
		return jPanelSeparatorCombos;
	}

	/**
	 * This method initializes jPanelLabelToolVendor
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelLabelToolVendor() {
		if (jPanelLabelToolVendor == null) {
			FlowLayout flowLayout9 = new FlowLayout();
			flowLayout9.setAlignment(FlowLayout.RIGHT);
			flowLayout9.setVgap(6);
			jPanelLabelToolVendor = new JPanel();
			jPanelLabelToolVendor.setLayout(flowLayout9);
			jPanelLabelToolVendor.add(jLabelToolVendor, null);
		}
		return jPanelLabelToolVendor;
	}

	/**
	 * This method initializes jPanelLabelTechnology
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelLabelTechnology() {
		if (jPanelLabelTechnology == null) {
			FlowLayout flowLayout10 = new FlowLayout();
			flowLayout10.setVgap(6);
			flowLayout10.setAlignment(FlowLayout.RIGHT);
			jPanelLabelTechnology = new JPanel();
			jPanelLabelTechnology.setLayout(flowLayout10);
			jPanelLabelTechnology.add(jLabelTechnologhy, null);
		}
		return jPanelLabelTechnology;
	}

	/**
	 * This method initializes jPanelLabelThousandSep
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelLabelThousandSep() {
		if (jPanelLabelThousandSep == null) {
			FlowLayout flowLayout11 = new FlowLayout();
			flowLayout11.setVgap(10);
			flowLayout11.setHgap(2);
			flowLayout11.setAlignment(FlowLayout.RIGHT);
			jPanelLabelThousandSep = new JPanel();
			jPanelLabelThousandSep.setLayout(flowLayout11);
			jPanelLabelThousandSep.add(jLabel, null);
		}
		return jPanelLabelThousandSep;
	}

	/**
	 * This method initializes jPanelLabelThousand
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelLabelThousand() {
		if (jPanelLabelThousand == null) {
			FlowLayout flowLayout12 = new FlowLayout();
			flowLayout12.setAlignment(FlowLayout.RIGHT);
			flowLayout12.setHgap(2);
			flowLayout12.setVgap(10);
			jPanelLabelThousand = new JPanel();
			jPanelLabelThousand.setLayout(flowLayout12);
			jPanelLabelThousand.add(jLabel1, null);
		}
		return jPanelLabelThousand;
	}

	public boolean isCbTechnologyChanged() {
		return cbTechnologyChanged;
	}

	public void setM_TestObjectReference(TestObjectReference testObjectReference) {
		m_TestObjectReference = testObjectReference;
	}
}
