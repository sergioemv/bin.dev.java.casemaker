/*******************************************************************************
Developed by BUSINESS SOFTWARE INNOVATIONS. .
Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
*******************************************************************************/

package bi.view.workspaceviews;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.tree.TreePath;

import model.CMField;
import model.Workspace2;
import model.edit.CMModelEditFactory;
import model.util.CMModelEvent;
import model.util.CMModelListener;
import bi.view.actions.CMAction;
import bi.view.edit.CMViewEditFactory;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.mainframeviews.CMFrameView;
import bi.view.undomanagementviews.CMCompoundEdit;
import bi.view.undomanagementviews.undoviews.CMUndoMediator;
import bi.view.utils.CMBaseJPanel;
import bi.view.utils.CMJEditorPaneFocusChangeable;
import bi.view.utils.CMToolBarButton;

public class CMPanelWorkspaceView extends CMBaseJPanel implements CMModelListener {
//	hmendez_06102005_end
    private CMFrameView m_Frame;
    private Workspace2 m_Workspace;
    private boolean caretUpdatesAllowed = true;
	private JPanel jPanelNameContainer = null;
	private JTextField jTextFieldName = null;
	private JLabel jLabel = null;
	private CMToolBarButton JButtonChangeName = null;
	private JPanel jPanelDescriptionContainer = null;
	private CMJEditorPaneFocusChangeable jEditorPaneDescription = null;
	private JPanel jPanelBottom = null;
	private JPanel jPanelMainContainer = null;
	private JPanel jPanelLeft = null;
	private JPanel jPanelRight = null;
	private JPanel jPanelTop = null;
	private JPanel jPanelmiddle = null;
	private JScrollPane jScrollPaneDescription = null;
	/** Creates new form CMPanelWorkspaceView */
    public CMPanelWorkspaceView() {
    	super();
    	m_Frame = CMApplication.frame;
        initGUI();
    }

    /** This method is called from within the constructor to initialize the form. */
    private void initGUI() {
       // jEditorDescription.setText(""); //$NON-NLS-1$
       // jEditorDescription.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        //jEditorDescription.setText("Description");
        //jTextFieldName.setText(""); //$NON-NLS-1$
        setLayout(new BorderLayout());
        setBounds(new java.awt.Rectangle(0, 0, 599, 300));
        this.setMinimumSize(new java.awt.Dimension(400,115));
        setBorder(null);
        //add(jLabelFilePath);
        this.add(getJPanelMainContainer(), java.awt.BorderLayout.CENTER);
        //this.add(jPanelDescriptionGroupBox, java.awt.BorderLayout.SOUTH);
        this.add(getJPanelLeft(), java.awt.BorderLayout.WEST);
       //jEditorDescription.
        this.add(getJPanelRight(), java.awt.BorderLayout.EAST);
        this.add(getJPanelTop(), java.awt.BorderLayout.NORTH);
    }

    public void setM_Workspace(Workspace2 p_Workspace){
      jTextFieldName.setText(p_Workspace.getName());
      getJEditorPaneDescription().setText(p_Workspace.getDescription());
      //remove listener from old workspace
      if (m_Workspace !=null)
     		m_Workspace.removeModelListener(this);
       m_Workspace = p_Workspace;
       //add listener to new workspace
       m_Workspace.addModelListener(this);
    }

    public void updateWorkspaceDescription() {
//HCanedo_03012005_Begin
        if (m_Workspace!=null&&caretUpdatesAllowed)
    	if(!m_Workspace.getDescription().equals(getJEditorPaneDescription().getText()))
        {
	      int saveUnsave=m_Frame.getCmApplication().getSessionManager().getM_Save_UnsaveVariable()+1;
          m_Frame.getCmApplication().getSessionManager().setM_Save_UnsaveVariable(saveUnsave);
          CMCompoundEdit ce = new CMCompoundEdit();
          ce.addEdit(CMModelEditFactory.INSTANCE.createChangeDescriptionModelEdit(m_Workspace,getJEditorPaneDescription().getText()));
          ce.addEdit(CMViewEditFactory.INSTANCE.createTreeSelectNodeEdit(
        		  new TreePath(CMApplication.frame.getTreeWorkspaceView().getM_RootNode().getNode(m_Workspace))));
          CMUndoMediator.getInstance().doEdit(ce);
          m_Workspace.setDescription(getJEditorPaneDescription().getText());
        }
//HCanedo_03012005_End

    }

//    public void changedValidName(String name){
//		jTextFieldName.setText(name);
//		m_Frame.getTreeWorkspaceView().changedWorkspaceName2(name);
//    }



    //hmendez_06102005_begin
    public  List getOrder()//Agregar en todos los JPanel
    {
   	   Vector componentList = new Vector();
   	   //componentList.add(this);
   	   componentList.add(getJButtonChangeName());
   	   componentList.add(getJEditorPaneDescription());
   	   componentList.add(m_Frame.getTreeWorkspaceView());
       return componentList;
	 }

	/* (non-Javadoc)
	 * @see model.util.CMModelListener#handleCMModelChange(model.util.CMModelEvent)
	 */
	public void handleCMModelChange(CMModelEvent p_evt) {
		if (p_evt.getChangedField()== CMField.DESCRIPTION){

			if (!getJEditorPaneDescription().getText().equals(m_Workspace.getDescription()))
			{
				getJEditorPaneDescription().setText(m_Workspace.getDescription());
				getJEditorPaneDescription().selectAll();
			}
		}
		if (p_evt.getChangedField()== CMField.NAME){

			if (!jTextFieldName.getText().equals(m_Workspace.getName()))
				this.jTextFieldName.setText(m_Workspace.getName());
		}

	}

	/**
	 * This method initializes jPanelContainer
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelContainer() {
		if (jPanelNameContainer == null) {
			FlowLayout flowLayout1 = new FlowLayout();
			flowLayout1.setAlignment(java.awt.FlowLayout.LEFT);
			flowLayout1.setVgap(5);
			jLabel = new JLabel();
			jLabel.setToolTipText(CMMessages.getString("LABEL_NAME"));
			jLabel.setName("jLabel");
			jLabel.setPreferredSize(new java.awt.Dimension(72,14));
			jLabel.setText(CMMessages.getString("LABEL_NAME"));
			jPanelNameContainer = new JPanel();
			jPanelNameContainer.setLayout(flowLayout1);
			jPanelNameContainer.setMinimumSize(new java.awt.Dimension(10,60));
			jPanelNameContainer.setPreferredSize(new java.awt.Dimension(10,30));
			//jPanelDescriptionContainer.add()
			jPanelNameContainer.add(jLabel, null);
			jPanelNameContainer.add(getJTextFieldName(), null);
			jPanelNameContainer.add(getJButtonChangeName(), null);
		}
		return jPanelNameContainer;
	}

	/**
	 * This method initializes jTextField
	 *
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextFieldName() {
		if (jTextFieldName == null) {
			jTextFieldName = new JTextField();
			jTextFieldName.setEditable(false);
			jTextFieldName.setName("jTextFieldName");
			jTextFieldName.setPreferredSize(new java.awt.Dimension(220,20));
		}
		return jTextFieldName;
	}

	/**
	 * This method initializes CMToolBarButton
	 *
	 * @return bi.view.utils.CMToolBarButton
	 */
	private CMToolBarButton getJButtonChangeName() {
		if (JButtonChangeName == null) {
			JButtonChangeName = new CMToolBarButton(CMAction.RENAME_WORKSPACE
					.getAction());
			JButtonChangeName.setName("JButtonChangeName");
		}
		return JButtonChangeName;
	}

	/**
	 * This method initializes jPanel
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanelDescriptionContainer == null) {
			jPanelDescriptionContainer = new JPanel();
			jPanelDescriptionContainer.setLayout(new BorderLayout());
			jPanelDescriptionContainer.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), CMMessages.getString("LABEL_DESCRIPTION"), TitledBorder.LEADING, TitledBorder.TOP, new Font("SansSerif", 0, 11), new Color(60, 60, 60)));
			jPanelDescriptionContainer.setPreferredSize(new java.awt.Dimension(200,100));
			jPanelDescriptionContainer.setMinimumSize(new java.awt.Dimension(200,100));
			jPanelDescriptionContainer.add(getJScrollPaneDescription(), java.awt.BorderLayout.CENTER);
		}
		return jPanelDescriptionContainer;
	}

	/**
	 * This method initializes CMJEditorPaneFocusChangeable
	 *
	 * @return bi.view.utils.CMJEditorPaneFocusChangeable
	 */
	private CMJEditorPaneFocusChangeable getJEditorPaneDescription() {
		if (jEditorPaneDescription == null) {
			jEditorPaneDescription = new CMJEditorPaneFocusChangeable();
			jEditorPaneDescription.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
			jEditorPaneDescription.addKeyListener( new KeyAdapter(){
		    	   /* (non-Javadoc)
		    	 * @see java.awt.event.KeyAdapter#keyTyped(java.awt.event.KeyEvent)
		    	 */
		    	@Override
		    	public void keyTyped(KeyEvent p_e) {
		    			updateWorkspaceDescription();
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
			jPanelBottom.setMinimumSize(new java.awt.Dimension(10,200));
			jPanelBottom.setPreferredSize(new java.awt.Dimension(10,250));
		}
		return jPanelBottom;
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
			jPanelMainContainer.add(getJPanelContainer(), null);
			jPanelMainContainer.add(getJPanelmiddle(), null);
			jPanelMainContainer.add(getJPanel(), null);
			jPanelMainContainer.add(getJPanelBottom(), null);
		}
		return jPanelMainContainer;
	}

	/**
	 * This method initializes jPanelLeft
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelLeft() {
		if (jPanelLeft == null) {
			jPanelLeft = new JPanel();
			jPanelLeft.setMinimumSize(new java.awt.Dimension(30,10));
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
			jPanelRight.setPreferredSize(new java.awt.Dimension(10,10));
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
			jPanelTop.setPreferredSize(new java.awt.Dimension(10,20));
		}
		return jPanelTop;
	}

	/**
	 * This method initializes jPanelmiddle
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelmiddle() {
		if (jPanelmiddle == null) {
			jPanelmiddle = new JPanel();
		}
		return jPanelmiddle;
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
