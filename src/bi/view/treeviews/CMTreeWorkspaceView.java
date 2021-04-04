/**
 ******************************************************************************
 * Developed by BUSINESS SOFTWARE INNOVATIONS All rights reserved.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.view.treeviews;

import java.awt.Event;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.ToolTipManager;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import model.BRulesReference;
import model.BusinessRules;
import model.LocalProjectReference;
import model.Project2;
import model.ProjectReference;
import model.Session2;
import model.TDStructure;
import model.TDStructureReference;
import model.TestObject;
import model.TestObjectReference;
import model.Workspace2;

import org.apache.log4j.Logger;

import bi.controller.BREditorManager;
import bi.controller.LicenseManager;
import bi.controller.ProjectManager;
import bi.controller.SessionManager;
import bi.controller.WorkspaceManager;
import bi.controller.utils.CMBaseObjectReader;
import bi.controller.utils.CMFileLocation;
import bi.controller.utils.CMInvalidFileLocationException;
import bi.controller.utils.CMXMLFileState;
import bi.view.actions.file.rename.CMRenameAction;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.mainframeviews.CMFrameView;
import bi.view.testdataviews.CMIndexTDStructureUpdate;
import bi.view.testobjectviews.CMPanelTestObjectView;
import bi.view.treeviews.nodes.CMBusinessRulesNode;
import bi.view.treeviews.nodes.CMProjectNode;
import bi.view.treeviews.nodes.CMResultsComparationNode;
import bi.view.treeviews.nodes.CMRootNode;
import bi.view.treeviews.nodes.CMTestCasesNode;
import bi.view.treeviews.nodes.CMTestDataNode;
import bi.view.treeviews.nodes.CMTestManagementNode;
import bi.view.treeviews.nodes.CMTestObjectNode;
import bi.view.treeviews.nodes.CMWorkspaceNode;
import bi.view.utils.CMDnDJTree;
import bi.view.utils.CMFileFilter;
import bi.view.utils.CMFormatFactory;
//grueda10022005_end
public class CMTreeWorkspaceView extends CMDnDJTree  {
    private CMRootNode m_RootNode;
    private Session2 m_Session2;
    WorkspaceManager m_WorkspaceManager;
    private SessionManager m_SessionManager;
    private CMFrameView m_Frame;
    private DefaultTreeModel m_TreeModel;

    private boolean enteredValueChanged = false; //fcastro_20082004


    public CMTreeWorkspaceView(CMFrameView frame) {
        m_Frame = frame;
        initGUI();
        this.addKeyListener(this.getDefaultKeyListener());

    }
    public KeyListener getDefaultKeyListener() {
    	return defaultKeyListener;
    }
    private void giveFocusToNewObject()
    {
       	  if(m_Frame.getTreeWorkspaceView().getSelectedNode().getUserObject() instanceof CMTestDataNode){
       		switch (this.m_Frame.getTabbedPane().getSelectedIndex()) {
    		case 0:m_Frame.getPanelTDStructureView().getScrollPaneStructureDescriptionView().getM_cmStructureView().requestFocus();
    			break;
    		case 1:m_Frame.getPanelTestDataView().getM_CMScrollPaneTestDataCombinationsDescription().getM_CMTestDataCombinationView().requestFocus();
    			break;
    		case 2:m_Frame.getPanelTestDataSetView().getM_CMScrollPaneTestDataSetDescription().getM_CMTestDataSetView().requestFocus();
    			break;
    		case 3:m_Frame.getPanelTestDataSetReportsView().getM_CMGridOutputs().requestFocus();
    			break;
    		/*case 4:
                break;*///hmendez_14102005->No CTRL+N Event
    		default:
    			break;
    		}
       	   }
        	   else if(m_Frame.getTreeWorkspaceView().getSelectedNode().getUserObject() instanceof CMTestCasesNode){
       	   	   switch (this.m_Frame.getTabbedPane().getSelectedIndex()){
       	   	   case 0:this.m_Frame.getElementsGrid().requestFocus();
       	   		   break;
       		   case 1:m_Frame.getCauseEffectStructureView().getM_CMCauseEffectStructureGridView().requestFocus();
        		   break;
       		   case 2:m_Frame.getPanelDependencyCombiView().getCMGridDependencies().requestFocus();
       		       break;
       		   case 3:m_Frame.getJSPlitPaneTestCaseStructureView().getM_CMDescriptionTestCaseViews().requestFocus();
       		       break;
       		   case 4:m_Frame.getJSplitPaneStdCombinationStructureView().getM_CMDescriptionStdCombinationViews().requestFocus();
       		       break;
       	     }
       	   }
       	   else if(m_Frame.getTreeWorkspaceView().getSelectedNode().getUserObject() instanceof CMTestManagementNode){
       		  m_Frame.getErrorScrollView().getM_CMErrorGridView().requestFocus();
       	   }
    }
	private KeyListener defaultKeyListener = new KeyAdapter()
	{
		public void keyPressed(KeyEvent e) {
			if ((e.getKeyCode()==KeyEvent.VK_N)&&(e.isControlDown()))
			{
			   giveFocusToNewObject();
			}
			//show the popup menu
			if ((e.getKeyCode()==KeyEvent.VK_F10)&&(e.isShiftDown())){
				//TODO how to obtain the selection location of a Jtree
				TreePath path = null;
				int posx = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner().getLocation().x;
				int posy = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner().getLocation().y;
				m_Frame.getJPopupMenuFile().show(m_Frame.getTreeWorkspaceView(),posx,posy);
				m_Frame.getJPopupMenuFile().requestFocus();
			}
		}
  };
	private TestObject rebuildTO;
	private boolean fileCorrupted;
//hmendez_14102005_end
    public void initGUI() {

        if (m_Frame != null) {
            m_WorkspaceManager = m_Frame.getCmApplication().getSessionManager().getM_WorkspaceManager();
            m_SessionManager = m_Frame.getCmApplication().getSessionManager();
        }
        if (m_SessionManager != null) {
        	//read the sessiono
            m_Session2 = m_SessionManager.readSession2("cm.ini.xml"); //$NON-NLS-1$
            m_Session2.setM_User(System.getProperty("user.name")); //$NON-NLS-1$
        }
        m_RootNode = new CMRootNode(this,m_Session2); //$NON-NLS-1$
        m_TreeModel = new DefaultTreeModel(m_RootNode);
        setModel(m_TreeModel);
        this.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        //Enable tool tips.
        ToolTipManager.sharedInstance().registerComponent(this);
        setCellRenderer(new CMDefaultNodeRenderer(this, this));
        setShowsRootHandles(true);
        setAutoscrolls(true);
        addTreeSelectionListener(
            new TreeSelectionListener() {
                public void valueChanged(TreeSelectionEvent e) { thisValueChangedCC(e);/* cc coments this..    thisValueChanged2(e);*/ }
            });
        this.putClientProperty("JTree.lineStyle", "Angled"); //$NON-NLS-1$ //$NON-NLS-2$
        m_RootNode.createWorkspaceNodes();
       // m_Session2.addModelListener(this);
        m_Session2.addModelListener(m_RootNode);
        addMouseListener(
            new MouseAdapter() {
                public void mouseClicked(MouseEvent e) { thisMouseClicked(e); }
            }); //BUG 339 keine Reorganisation der Testobjekte möglich Harold Canedo Lopez

//        addTreeWillExpandListener(
//            new TreeWillExpandListener() {
//                public void treeWillCollapse(TreeExpansionEvent e) {  }
//                public void treeWillExpand(TreeExpansionEvent e) throws ExpandVetoException {
//
//                }
//            });

    }

    public Workspace2 getSelectedWorkspace2() {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)this.getLastSelectedPathComponent();
        if (node != null) {
            Object nodeInfo = node.getUserObject();
            if (nodeInfo != null) {
                if (nodeInfo instanceof CMWorkspaceNode) {
                    CMWorkspaceNode temp = (CMWorkspaceNode)nodeInfo;
                    return temp.getM_Workspace();
                }
                else {
                    return null;
                }
            }
            else {
                return null;
            }
        }
        else {
            return null;
        }
    }

    public Project2 getSelectedProject2() {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)this.getLastSelectedPathComponent();
        if (node != null) {
                if (node instanceof CMProjectNode) {
                    CMProjectNode temp = (CMProjectNode)node;
                    return temp.getProject();
                }
                else {
                    return null;
                }
            }
            else {
                return null;
            }


    }

    public TestObject getSelectedTestObject2() {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)this.getLastSelectedPathComponent();
        if (node != null) {
            Object nodeInfo = node.getUserObject();
            if (nodeInfo != null) {
                if (nodeInfo instanceof CMTestObjectNode) {
                    CMTestObjectNode temp = (CMTestObjectNode)nodeInfo;
                    return temp.getTestObject();
                }
                else {
                    return null;
                }
            }
            else {
                return null;
            }
        }
        else {
            return null;
        }
    }


    public DefaultMutableTreeNode createTestObjectNode2(TestObjectReference p_TestObjectReference,
        TestObjectReference p_LocalTestObjectReference, ProjectReference p_ProjectReference) {
            //grueda22082004_end
            //grueda04112004_begin
            ProjectReference projectReference = null;
            //grueda27122004_begin
            TestObjectReference tempTestObjectReference = null;
            //grueda27122004_end
            TestObject testObject = null;
            TestObject testObjectByReference = m_WorkspaceManager.readTestObjectByReference(p_TestObjectReference,
                p_ProjectReference, m_Session2);
            TestObject testObjectByLocalReference =
                m_WorkspaceManager.readTestObjectByReference(p_LocalTestObjectReference, p_ProjectReference.getM_LocalProjectReference(), m_Session2);
            //grueda16122004_begin
            if (m_WorkspaceManager.isTestObjectCheckedOutLocalByTheSameUser(testObjectByReference,
                m_Session2) || m_WorkspaceManager.isTestObjectReferenceCheckOutLocal(testObjectByLocalReference)) {
                    //grueda27122004_begin
                    if (m_WorkspaceManager.isTestObjectReferenceCheckOutLocal(testObjectByLocalReference)) {
                            tempTestObjectReference = p_LocalTestObjectReference;
                    }
                    else {
                        tempTestObjectReference = p_TestObjectReference;
                    }
                    testObject = m_WorkspaceManager.readTestObjectByReference(tempTestObjectReference,
                        p_ProjectReference.getM_LocalProjectReference(), m_Session2);
                    //grueda27122004_end
                    if (testObject != null) {
                        DefaultMutableTreeNode testObjectNode =
                            new DefaultMutableTreeNode(
                            new CMTestObjectNode(this, p_TestObjectReference, testObject, p_LocalTestObjectReference));
                        addChildrenOfTestObjectNode(testObjectNode, p_TestObjectReference, testObject,
                            p_ProjectReference.getM_LocalProjectReference());
                        return testObjectNode;
                    }
                    else {
                        return null;
                    }
            }
            else {
                testObject = m_WorkspaceManager.readTestObjectByReference(p_TestObjectReference,
                    p_ProjectReference, m_Session2);
                if (testObject != null) {
                    DefaultMutableTreeNode testObjectNode =
                        new DefaultMutableTreeNode(
                        new CMTestObjectNode(this,p_TestObjectReference, testObject, p_LocalTestObjectReference));
                    addChildrenOfTestObjectNode(testObjectNode, p_TestObjectReference, testObject, p_ProjectReference);
                    return testObjectNode;
                }
                else {
                    return null;
                }
            }
            //grueda04112004_end
            //grueda16122004_end
    }

    //grueda22082004_begin
    //public void addChildrenOfTestObjectNode(DefaultMutableTreeNode p_TestObjectNode, TestObjectReference
    // p_TestObjectReference, TestObject p_TestObject) {
    public void addChildrenOfTestObjectNode(DefaultMutableTreeNode p_TestObjectNode, TestObjectReference p_TestObjectReference,
        TestObject p_TestObject, ProjectReference p_ProjectReference) {
            BRulesReference bRulesReference = p_TestObject.getBRulesReference();
            //grueda22082004_end
            //grueda30122004_begin
            String existingBusinessRulesAbsoluteFilePath =
                m_WorkspaceManager.buildAbsoluteBRulesFilePath(p_ProjectReference, p_TestObjectReference);
            File brFile = new File(existingBusinessRulesAbsoluteFilePath);
            BREditorManager businessRulesEditorManager = new BREditorManager();
            String fileContent = businessRulesEditorManager.readContentFromFile(brFile);
            //grueda30122004_end
            DefaultMutableTreeNode testObjectBusinessRulesNode =
                new DefaultMutableTreeNode(
                new CMBusinessRulesNode(p_TestObjectReference, p_TestObject, bRulesReference, fileContent));
            DefaultMutableTreeNode testObjectTestCasesNode =
                new DefaultMutableTreeNode(new CMTestCasesNode(p_TestObjectReference, p_TestObject));
            TDStructure readTDStructure = readTDstructureforReference(p_TestObject, p_TestObjectReference, p_ProjectReference);
            //grueda22082004_begin
            TDStructureReference structureReference =
                (TDStructureReference)p_TestObject.getTDSTructureReference().firstElement();
            //grueda22082004_end
            DefaultMutableTreeNode testObjectTestDataNode =
                new DefaultMutableTreeNode(
                new CMTestDataNode(p_TestObjectReference, p_TestObject, structureReference, readTDStructure));
            DefaultMutableTreeNode testObjectResultComparationNode =
                new DefaultMutableTreeNode(
                new CMResultsComparationNode(p_TestObjectReference, p_TestObject,
                structureReference, readTDStructure));
            DefaultMutableTreeNode testObjectTestManagement =
                new DefaultMutableTreeNode(new CMTestManagementNode(p_TestObjectReference, p_TestObject));
            p_TestObjectNode.add(testObjectBusinessRulesNode);
            p_TestObjectNode.add(testObjectTestCasesNode);
            p_TestObjectNode.add(testObjectTestDataNode);
            p_TestObjectNode.add(testObjectResultComparationNode);
            p_TestObjectNode.add(testObjectTestManagement);
    }

    //grueda30122004_begin
    public TestObject rebuildCurrentTestObjectNode(TestObjectReference p_TestObjectReference,
        ProjectReference p_ProjectReference, Session2 p_Session) {
            DefaultMutableTreeNode testObjectNode = getCurrentTestObjectNode();
            //reads the test object again
            TestObject testObject = reloadTestObject(p_TestObjectReference, p_ProjectReference, p_Session);

            //Ccastedo 24-06-01
            if (testObject ==null){
            	Logger.getLogger(this.getClass()).error("Could not reload the current test object");
            	return null;
            }
            //Ccastedo ends 24-06-01

            TestObjectReference testObjectReference =
                m_WorkspaceManager.reloadTestObjectReferenceFromProject(p_ProjectReference, p_TestObjectReference);
            CMTestObjectNode testObjectNodeInfo = (CMTestObjectNode)testObjectNode.getUserObject();
            if (testObject == null || testObjectReference == null) {
                return testObjectNodeInfo.getTestObject();
            }
            testObjectNodeInfo.setM_TestObjectReference(testObjectReference);
            testObjectNodeInfo.setM_TestObject(testObject);
            testObjectNodeInfo.setIsTestObjectNodeWithData(true);
            if (testObject != null) {
                reloadChildrenOfTestObjectNode(testObjectNode, testObjectReference, testObject, p_ProjectReference);
            }
            return testObject;
    }

    public void assignSelectedTestObjectNodeWith(TestObject p_TestObject, TestObjectReference p_TestObjectReference,
        Session2 p_Session, ProjectReference p_ProjectReference, TestObjectReference p_LocalTestObjectReference) {
            DefaultMutableTreeNode testObjectNode = getSelectedNode();
            CMTestObjectNode testObjectNodeInfo = (CMTestObjectNode)testObjectNode.getUserObject();
            if (p_TestObjectReference == null || p_TestObject == null) {
                return;
            }
            testObjectNodeInfo.setM_TestObjectReference(p_TestObjectReference);
            testObjectNodeInfo.setM_TestObject(p_TestObject);
            testObjectNodeInfo.setM_LocalTestObjectReference(p_LocalTestObjectReference);
            Project2 project=getCurrentProject();
            m_Frame.getPanelTestObjectView().setTestObject(project,p_TestObject, p_TestObjectReference, p_Session,
                p_ProjectReference, p_LocalTestObjectReference);
    }

    public TestObject rebuildSelectedTestObjectNode(TestObjectReference p_TestObjectReference,
        ProjectReference p_ProjectReference, Session2 p_Session) {
            DefaultMutableTreeNode testObjectNode = getSelectedNode();

            TestObject testObject = reloadTestObject(p_TestObjectReference, p_ProjectReference, p_Session);
            TestObjectReference testObjectReference=null;
            CMTestObjectNode testObjectNodeInfo = null;
            if (testObject != null){
            	testObjectReference =
                    m_WorkspaceManager.reloadTestObjectReferenceFromProject(p_ProjectReference, p_TestObjectReference);
                    testObjectNodeInfo = (CMTestObjectNode)testObjectNode.getUserObject();

            }
            if (testObject == null || testObjectReference == null) {
                /*return null;//CCastedo comments this    testObjectNodeInfo.getTestObject();*/
            }
            testObjectNodeInfo.setM_TestObjectReference(testObjectReference);
            testObjectNodeInfo.setM_TestObject(testObject);
            testObjectNodeInfo.setIsTestObjectNodeWithData(true);
            if (testObject != null) {
                reloadChildrenOfTestObjectNode(testObjectNode, testObjectReference, testObject, p_ProjectReference);
            }
            return testObject;
    }

    public TestObject rebuildLastSelectedTestObjectNode(TreeSelectionEvent e, Session2 p_Session) {
        Object oldNodeInfo = getOldNodeInfo(e);
        if (!(oldNodeInfo instanceof CMTestObjectNode)) {
            return null;
        }
        DefaultMutableTreeNode testObjectNode = this.getOldNode(e);
        if (testObjectNode == null) {
            return null;
        }
        CMTestObjectNode testObjectNodeInfo = (CMTestObjectNode)testObjectNode.getUserObject();
        if (testObjectNodeInfo == null) {
            return null;
        }
        //if(  theLastSelectedTestObjectNode is CheckedOUt then readLocalProjectReference and LocalTestObjectReference)
        TestObjectReference testObjectReference = testObjectNodeInfo.geTestObjectReference();
    //cc    TestObject testobject = testObjectNodeInfo.getM_TestObject(); ccastedo 25-04-06
        TestObject testobject = testObjectReference.reloadTestObject();// ccastedo 25-04-06


        ProjectReference projectReference = this.getOldProjectReference(e);
        if (projectReference == null) {
            return null;
        }
        if (m_WorkspaceManager.isTestObjectCheckedOutLocalByTheSameUser(testobject, p_Session)) {
            projectReference = projectReference.getM_LocalProjectReference();
        }
        testObjectReference = m_WorkspaceManager.reloadTestObjectReferenceFromProject(projectReference, testObjectReference);
        TestObject testObject = reloadTestObject(testObjectReference, projectReference, p_Session);
        //Ccastedo begins 26-01-06
        if (testObject == null){
        	return null;
        }

        //Ccastedo ends 24-06-01
        testObjectNodeInfo = (CMTestObjectNode)testObjectNode.getUserObject();
        if (testObject == null || testObjectReference == null) {
            return null; // ccastedo comments this    testObjectNodeInfo.getM_TestObject();
        }
        testObjectNodeInfo.setM_TestObjectReference(testObjectReference);
        String changedDescription = testObjectNodeInfo.getTestObject().getDescription();
        String changedPreconditions = testObjectNodeInfo.getTestObject().getPreconditions();
        testObject.setDescription(changedDescription);
        testObject.setPreconditions(changedPreconditions);
        testObjectNodeInfo.setM_TestObject(testObject);
        if (testObject != null) {
            reloadChildrenOfTestObjectNode(testObjectNode, testObjectReference, testObject, projectReference);
        }
        return testObject;
    }

    public Project2 rebuildLastSelectedProjectNode(TreeSelectionEvent e, Session2 p_Session) {
        Object oldNodeInfo = getOldNodeInfo(e);
        if (!(oldNodeInfo instanceof CMProjectNode)) {
            return null;
        }
        CMProjectNode projectNode = (CMProjectNode) this.getOldNode(e);
        if (projectNode == null) {
            return null;
        }
        ProjectReference projectReference = projectNode.getProjectReference();
        Project2 project = null;
        if (projectNode.getProject() != null)
        {
        	String changedDescription = projectNode.getProject().getM_Description();
        	project = projectReference.reloadProject();
        	project.setM_Description(changedDescription);
        }
        return project;
    }
//no use for this!
//    public Project2 rebuildSelectedProjectNode( ProjectReference p_ProjectReference, Session2 p_Session) {
//        if (p_ProjectReference == null) {
//            return null;
//        }
//        Object info = getSelectedProjectNodeInfo();
//        if (!(info instanceof CMProjectNode)) {
//            return null;
//        }
//        CMProjectNode projectNode = (CMProjectNode) getSelectedNode();
//        if (projectNode == null) {
//            return null;
//        }
//        return p_ProjectReference.reloadProject();
//        CMProjectNode projectNodeInfo = (CMProjectNode)projectNode.getUserObject();
//        if (projectNodeInfo == null) {
//            return null;
//        }

     //   if (project == null) {
    //        return projectNodeInfo.getM_Project();
     //   }
     //   projectNodeInfo = (CMProjectNode)projectNode.getUserObject();
      // projectNodeInfo.setM_Project(project);
//        return project;
//    }

//    public Project2 reloadProject(ProjectReference p_ProjectReference, Session2 p_Session) {
//        Project2 project = m_WorkspaceManager.readProject2ByReference(p_ProjectReference);
//        return project;
//    }

    public TestObject reloadTestObject(TestObjectReference p_TestObjectReference,
        ProjectReference p_ProjectReference, Session2 p_Session) {
            TestObject testObject = m_WorkspaceManager.readTestObjectByReference(p_TestObjectReference,
                p_ProjectReference, m_Session2);
            return testObject;
    }

    public void reloadChildrenOfTestObjectNode(DefaultMutableTreeNode p_TestObjectNode,
        TestObjectReference p_TestObjectReference, TestObject p_TestObject, ProjectReference p_ProjectReference) {
            BRulesReference bRulesReference = p_TestObject.getBRulesReference();
            String existingBusinessRulesAbsoluteFilePath =
                m_WorkspaceManager.buildAbsoluteBRulesFilePath(p_ProjectReference, p_TestObjectReference);
            File brFile = new File(existingBusinessRulesAbsoluteFilePath);
            BREditorManager businessRulesEditorManager = new BREditorManager();
            String fileContent = businessRulesEditorManager.readContentFromFile(brFile);
            DefaultMutableTreeNode node = p_TestObjectNode;
            node = node.getNextNode();
            Logger.getLogger(this.getClass()).debug(node.getUserObject().getClass());
            if (node.getUserObject() instanceof CMTestObjectNode)
            	node = node.getNextNode();
            Logger.getLogger(this.getClass()).debug(node.getUserObject().getClass());
            DefaultMutableTreeNode testObjectBusinessRulesNode = node; //new DefaultMutableTreeNode(new
            // TestObjectBusinessRulesNodeInfo(p_TestObjectReference, p_TestObject, bRulesReference, fileContent));
            CMBusinessRulesNode testObjectBusinessRulesNodeInfo =
            (CMBusinessRulesNode)testObjectBusinessRulesNode.getUserObject();
            testObjectBusinessRulesNodeInfo.setM_BusinessRules(fileContent);
            testObjectBusinessRulesNodeInfo.setM_TestObject(p_TestObject);
            testObjectBusinessRulesNodeInfo.setM_TestObjectReference(p_TestObjectReference);
            testObjectBusinessRulesNodeInfo.setM_BRulesReference(bRulesReference);
            node = node.getNextNode();
            DefaultMutableTreeNode testObjectTestCasesNode = node; //new DefaultMutableTreeNode(new
            // TestObjectTestCasesNodeInfo(p_TestObjectReference, p_TestObject));
            CMTestCasesNode testObjectTestCasesNodeInfo =
                (CMTestCasesNode)testObjectTestCasesNode.getUserObject();
            testObjectTestCasesNodeInfo.setM_TestObject(p_TestObject);
            testObjectTestCasesNodeInfo.setM_TestObjectReference(p_TestObjectReference);
            TDStructure readTDStructure = readTDstructureforReference(p_TestObject, p_TestObjectReference, p_ProjectReference);
            TDStructureReference structureReference =
                (TDStructureReference)p_TestObject.getTDSTructureReference().firstElement();
            node = node.getNextNode();
            DefaultMutableTreeNode testObjectTestDataNode = node; //new DefaultMutableTreeNode(new
            // TestObjectTestDataNodeInfo(p_TestObjectReference, p_TestObject, structureReference, readTDStructure));
            CMTestDataNode testObjectTestDataNodeInfo =
                (CMTestDataNode)testObjectTestDataNode.getUserObject();
            testObjectTestDataNodeInfo.setM_TDStructure(readTDStructure);

            testObjectTestDataNodeInfo.setM_TDStructureReference(structureReference);

            testObjectTestDataNodeInfo.setM_TestObject(p_TestObject);
            testObjectTestDataNodeInfo.setM_TestObjectReference(p_TestObjectReference);
            node = node.getNextNode();
            DefaultMutableTreeNode testObjectResultComparationNode = node; //new DefaultMutableTreeNode(new
            // TestObjectResultsComparationNodeInfo(p_TestObjectReference, p_TestObject, structureReference, readTDStructure));
            CMResultsComparationNode testObjectResultsComparationNodeInfo =
                (CMResultsComparationNode)testObjectResultComparationNode.getUserObject();
            testObjectResultsComparationNodeInfo.setM_TestObject(p_TestObject);
            testObjectResultsComparationNodeInfo.setM_TDStructure(readTDStructure);
            testObjectResultsComparationNodeInfo.setM_TDStructureReference(structureReference);
            testObjectResultsComparationNodeInfo.setM_TestObjectReference(p_TestObjectReference);
            node = node.getNextNode();
            DefaultMutableTreeNode testObjectTestManagement = node; //new DefaultMutableTreeNode(new
            // TestObjectTestManagementNodeInfo(p_TestObjectReference, p_TestObject));
            CMTestManagementNode testObjectTestManagementNodeInfo =
                (CMTestManagementNode)testObjectTestManagement.getUserObject();
            testObjectTestManagementNodeInfo.setM_TestObject(p_TestObject);
            testObjectTestManagementNodeInfo.setM_TestObjectReference(p_TestObjectReference);
    }

    //grueda30122004_end
    public CMProjectNode getSelectedProjectNodeInfo() {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)this.getLastSelectedPathComponent();

            if (node != null) {
                if (node instanceof CMProjectNode) {
                    return (CMProjectNode)node;
                }
                else {
                    return null;
                }
            }
            else {
                return null;
            }
        }


    public CMTestObjectNode getSelectedTestObjectNodeInfo() {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)this.getLastSelectedPathComponent();
        if (node != null) {
            Object nodeInfo = node.getUserObject();
            if (nodeInfo != null) {
                if (nodeInfo instanceof CMTestObjectNode) {
                    return (CMTestObjectNode)nodeInfo;
                }
                else {
                    return null;
                }
            }
            else {
                return null;
            }
        }
        else {
            return null;
        }
    }






    //grueda05092004_begin
    public File selectProjectPath(Workspace2 p_selectedWorkspace, String p_Title, String p_ButtonTitle) {
        JFileChooser projectFileChooser = new JFileChooser();
        //projectFileChooser.addChoosableFileFilter(new CMProjectFilter());
        projectFileChooser.setAcceptAllFileFilterUsed(false);
        projectFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        projectFileChooser.setDialogTitle(p_selectedWorkspace.getName() +" - "+p_Title);
        projectFileChooser.setDialogType(JFileChooser.CUSTOM_DIALOG);

        int returnVal = projectFileChooser.showDialog(m_Frame,p_ButtonTitle);
        java.io.File file;

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            file = projectFileChooser.getSelectedFile();
            return file;
        }
        else {
            return null;
        }
    }

    //grueda05092004_end
    public File selectProjectFile(Workspace2 p_selectedWorkspace, String p_Title, String p_ButtonTitle) {
        JFileChooser projectFileChooser = new JFileChooser();
        projectFileChooser.addChoosableFileFilter(CMFileFilter.CPR.getFilter());
        projectFileChooser.setAcceptAllFileFilterUsed(false);
        projectFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        projectFileChooser.setDialogTitle(p_selectedWorkspace.getName() +" - "+p_Title);
        projectFileChooser.setDialogType(JFileChooser.CUSTOM_DIALOG);

        int returnVal = projectFileChooser.showDialog(m_Frame,p_ButtonTitle);
        java.io.File file;
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            file = projectFileChooser.getSelectedFile();
            if (CMRenameAction.existProhibitedCharactersInName(file.getName().replaceAll(BusinessRules.FILE_PROJECT_EXTENSION,"")))
            	return null;
            return file;
        }
        else {
            return null;
        }
    }

    public String nameProjectandFileEquals(String p_path, String p_name) {
        int index =p_path.lastIndexOf(BusinessRules.URL_SEPARATOR)+1;
        StringBuffer fileName = new StringBuffer(p_path.substring(index));
        fileName.append(BusinessRules.FILE_TESTOBJECT_EXTENSION);
        String fileName2 = fileName.toString();
        if (!fileName2.equals(p_name))
            return fileName2;
        else
            return p_name;
    }

    public String changeFilePath(String p_Path, String p_FileName) {
        StringBuffer changedFilePath = new StringBuffer(p_Path);
        changedFilePath.append(BusinessRules.URL_SEPARATOR);
        changedFilePath.append(p_FileName);
        return changedFilePath.toString();
    }

    public String fileOutExtencion(String p_fileName) {
        int index = p_fileName.indexOf(".");
        return p_fileName.substring(0, index);
    }

    //grueda13102004_begin
    public void importProject() {
	  Workspace2 selectedWorkspace = getSelectedWorkspace2();
      boolean projectExistsIntoTheWorkspace = false;
      while( !projectExistsIntoTheWorkspace ) {
		  File file = selectProjectFile(selectedWorkspace, CMMessages.getString("IMPORT_PROJECT_BUTTON"),CMMessages.getString("IMPORT_PROJECT_BUTTON"));

		  if( file != null)
          {
            //hmendez_09112005_begin
			if (file.exists())
			{
            //hmendez_09112005_end
              String name=file.getName();
          	  name = name.substring(0,name.indexOf("."));
          	Logger.getLogger(this.getClass()).debug(name);
            	if( !m_WorkspaceManager.existProjectReferenceInTheSession(name/*file.getPath().replace('\\','/')*/, m_Session2) )
                {
            		// 	Ccastedo begins 07-02-06
            		String path1 = m_WorkspaceManager.findCorrectPath(file);
                    Project2 openedProject = m_WorkspaceManager.readProject2(path1);
                    if (openedProject == null){
                    	showValidationMessage(1,path1);
        	            m_Frame.setWaitCursor(false);
                    	return;
                    }
                    else{
                    	importCompleteProjectFromFile(file, selectedWorkspace,false);
                        projectExistsIntoTheWorkspace = true;
                    }

                }
                else {
				  JOptionPane.showMessageDialog(this.m_Frame, CMMessages.getString("INFO_PROJECT_CAN_NOT_BE_IMPORTED")); //$NON-NLS-1$
                  importCompleteProjectFromFile(file, selectedWorkspace,true);
                  projectExistsIntoTheWorkspace = true;
                }
      		//hmendez_09112005_begin
	        }
			else{
			     JOptionPane.showMessageDialog((java.awt.Component)
		  		            null, CMMessages.getString("EXTERNAL_REPORTS_MENSSAGE_FILE_DOESNT_EXIST"),
		  		          CMMessages.getString("TITLE_MESSAGE_ERROR_NAME"), JOptionPane.ERROR_MESSAGE);

			}
			//hmendez_09112005_end
            }
          else {
            projectExistsIntoTheWorkspace = true;
          }
      }
    }
    //grueda13102004_end
    //grueda05092004_begin

    private String createNewProjectName(Session2 p_Session){
       String untitled = CMMessages.getString("LABEL_PROJECT_UNTITLED"); //$NON-NLS-1$
      StringBuffer sBuffer = null;
      int numOfWorkspaces = p_Session.getM_Workspaces().size();
        for (int j = 0; j < numOfWorkspaces; j++) {
            Workspace2 workspace = (Workspace2)p_Session.getM_Workspaces().get(j);
            int numOfProjectReferences = workspace.getM_ProjectReferences().size();
     		 for( int i = 0; i < numOfProjectReferences; i++) {
        		sBuffer = new StringBuffer();
        		sBuffer.append(untitled);
        		sBuffer.append(i+1);
        		if(!m_WorkspaceManager.existProjectReferenceInTheSession(sBuffer.toString(), p_Session)) {
	      			return sBuffer.toString();
        		}
      		}
        }
      sBuffer = new StringBuffer();
      sBuffer.append(untitled);
      sBuffer.append(1);
      return sBuffer.toString();
    }

    public void importCompleteProjectFromFile(File p_ImportingFile, Workspace2 p_Workspace, boolean changeName) {
        //grueda27102004_begin
        m_Frame.setWaitCursor(true);
        File destinationPath = null;

        destinationPath = this.selectProjectPath(p_Workspace,CMMessages.getString("IMPORT_PROJECT_SELECT_SAVE_PATH"),CMMessages.getString("IMPORT_PROJECT_SAVE_PROJECT_BUTTON"));
        Session2 currentSession = p_Workspace.getM_Session();
        if (destinationPath != null) {
            String path1 = m_WorkspaceManager.findCorrectPath(p_ImportingFile);
            Project2 openedProject = m_WorkspaceManager.readProject2(path1);

            //Ccastedo begins 07-02-06
            if (openedProject == null){
            //	sendingValidationMessage(1,path1);
	            m_Frame.setWaitCursor(false);
            	return;
            }
            openedProject.setAccessState(BusinessRules.ACCESS_STATE_CHECKED_OUT);
            openedProject.setUser(m_Session2.getM_User());

			if(changeName){
                openedProject.setName(createNewProjectName(currentSession));
            }
			String path2 = m_WorkspaceManager.findCorrectPath(destinationPath);
			//Ccastedo begins 17-04-06


            	String destinationProjectPath = m_WorkspaceManager.buildProjectDestinationPath( path2, openedProject);
                //String destinationProjectFilePath = m_WorkspaceManager.buildImportAbsoluteProjectFilePath( destinationProjectPath, p_ImportingFile);
                String destinationProjectFilePath = destinationProjectPath+BusinessRules.URL_SEPARATOR+openedProject.getName()+BusinessRules.FILE_PROJECT_EXTENSION;
     		    if( !m_WorkspaceManager.existProjectReferenceInTheSession(destinationProjectFilePath, currentSession) ){
     		    	File l_ParentFile = p_ImportingFile.getParentFile();
    	            StringBuffer sBuffer = new StringBuffer();
    	            sBuffer.append(l_ParentFile.getPath().replace('\\','/'));
     		    	boolean areChildValid = validateAllChilds(sBuffer.toString().replace('\\','/'),openedProject, true);
     		    	if (areChildValid){
     		    		    File projectDirectoryFile = new File(destinationProjectPath.toString());
     		    		   CMFileLocation l_FileLocation = new CMFileLocation();
     		  			try{
     		              l_FileLocation.validateProjectLocation(destinationPath);
     		             l_FileLocation.validateProjectLocation(projectDirectoryFile);
     		  			} catch (CMInvalidFileLocationException e){
     		  				String[] message = {CMMessages.getString("CANNOT_IMPORT_PROJECT") , e.getMessage()};
     		  				JOptionPane.showMessageDialog(CMApplication.frame, message, p_Workspace + "- Import project", JOptionPane.ERROR_MESSAGE);
     		  				  m_Frame.setWaitCursor(false);
     		  				return;
     		  			}
     		    		m_WorkspaceManager.importCompleteTestObjectsOfProject(p_ImportingFile, destinationProjectPath, openedProject);
     	 		        if (openedProject  != null)
     	 		              	addProject2(openedProject, destinationProjectFilePath, openedProject.getName()+BusinessRules.FILE_PROJECT_EXTENSION, destinationProjectPath);
     		     	    	}
               }


      }
      m_Frame.setWaitCursor(false);
    }

            //TODO If the destination directory of the import already exists do not allow to continue
    private boolean validateAllChilds(String p_FromFile, Project2 p_Project, boolean isImport){
    	boolean areValid = true;
    	int numOfTestObjectReferences = p_Project.getTestObjectReferences().size();
    	areValid = m_WorkspaceManager.validateAllTestObjects(p_FromFile,p_Project,numOfTestObjectReferences, areValid,isImport);
    	return areValid;
    }
    //  Ccastedo ends 06-03-06

	public void addProjectFromFile2() {
	  Workspace2 selectedWorkspace = getSelectedWorkspace2();
      boolean projectExistsIntoTheWorkspace = false;
      while( !projectExistsIntoTheWorkspace ) {
		  File file = selectProjectFile(selectedWorkspace,CMMessages.getString("ADD_PROJECT_BUTTON"),CMMessages.getString("ADD_PROJECT_BUTTON") );
		  if( file != null)
          {

			if (file.exists())
			{

				if(file.canWrite())
				{
					m_Frame.setWaitCursor(true);
					String absoluteProjectFilePath = file.getPath().replace('\\','/'); //"c:\projects\Kopie\p1.cpr.xml"
					String projectFileName = file.getName(); //"p1.cpr.xml"
					String absoluteProjectPath = file.getParent();   //"c:\projects\Kopie"
					String absoluteProjectPathOfParent = "";       //"c:\projects"

					File parentFile = file.getParentFile();
					if( parentFile != null) {

                  absoluteProjectPathOfParent = parentFile.getParent();

                }

                String projectName = m_WorkspaceManager.getProjectName(projectFileName);  //Result: "p1";
                String projectDirectoryName = m_WorkspaceManager.getDirectoryName(absoluteProjectPath, absoluteProjectPathOfParent); //Result:Kopie
                Logger.getLogger(this.getClass()).debug(projectName);
                Logger.getLogger(this.getClass()).debug(projectDirectoryName);
                if( !projectName.equals(projectDirectoryName) ) {
					int confirmation = JOptionPane.showConfirmDialog(this.m_Frame,CMMessages.getString("QUESTION_IMPORT_PROJECT_FILE"),CMMessages.getString("PROJECT_TITLE_IMPORT_PROJECT_FILE"),JOptionPane.YES_NO_OPTION);
					if( confirmation == JOptionPane.YES_OPTION) {
					  importCompleteProjectFromFile(file, selectedWorkspace,false);
					}
                    m_Frame.setWaitCursor(false);
					return;
                }

				if( !m_WorkspaceManager.existProjectReferenceInTheSession(projectName,m_Session2))//absoluteProjectFilePath, m_Session2) )
                {
					Project2 openedProject =  m_WorkspaceManager.readProject2(absoluteProjectFilePath);

		            if (openedProject == null){
		            	showValidationMessage(1,absoluteProjectFilePath);
			            m_Frame.setWaitCursor(false);
		            	return;
		            }
		            File l_ParentFile = file.getParentFile();
		            StringBuffer sBuffer = new StringBuffer();
		            sBuffer.append(l_ParentFile.getPath().replace('\\','/'));


		            boolean areChildValid = validateAllChilds(sBuffer.toString(),openedProject,false);
	 		    	if (areChildValid){
	 		    		 addProject2(openedProject, absoluteProjectFilePath, projectFileName, absoluteProjectPath);
	 		    	}
	 		    	projectExistsIntoTheWorkspace = true;
                    m_Frame.setWaitCursor(false);
				}
                else {
                  projectExistsIntoTheWorkspace = false;
                  m_Frame.setWaitCursor(false);//fcastro_20092004
                  JOptionPane.showMessageDialog(this.m_Frame, CMMessages.getString("INFO_PROJECT_CAN_NOT_BE_ADDED")); //$NON-NLS-1$
                }
          	}
            else
            {
                JOptionPane.showMessageDialog(m_Frame,CMMessages.getString("PROJECT_READ_ONLY_MENSSAGE"),CMMessages.getString("PROJECT_TITLE_ERROR_READONLY"),  JOptionPane.ERROR_MESSAGE);
			    int confirmation = JOptionPane.showConfirmDialog(this.m_Frame,CMMessages.getString("QUESTION_IMPORT_PROJECT_FILE"),CMMessages.getString("PROJECT_TITLE_IMPORT_PROJECT_FILE"),JOptionPane.YES_NO_OPTION);
			    if( confirmation == JOptionPane.YES_OPTION) {
                    m_Frame.setWaitCursor(true);//fcastro_20092004
                  importCompleteProjectFromFile(file, selectedWorkspace,false);
                  m_Frame.setWaitCursor(false);//fcastro_20092004
				}
                return;
            }
		  //hmendez_09112005_begin
          }
		  else
		  {
			  JOptionPane.showMessageDialog((java.awt.Component)
	  		            null, CMMessages.getString("EXTERNAL_REPORTS_MENSSAGE_FILE_DOESNT_EXIST"),
	  		          CMMessages.getString("TITLE_MESSAGE_ERROR_NAME"), JOptionPane.ERROR_MESSAGE);
		  }
		  //hmendez_09112005_end
		  }

          else {
            projectExistsIntoTheWorkspace = true;
          }
      }
    }


    public void addProject2(Project2 p_Project, String p_FilePath, String p_FileName, String p_Path) {
        p_FilePath=p_FilePath.replace('\\','/');
        p_Path=p_Path.replace('\\','/');
      Workspace2 selectedWorkspace = getSelectedWorkspace2();
      DefaultMutableTreeNode selectedWorkspaceNode = getSelectedNode();
      if( selectedWorkspace != null && selectedWorkspaceNode != null && p_Project != null) {
		  ProjectReference newProjectReference = ProjectManager.getInstance().createProjectReference();
          int index = p_FileName.indexOf(BusinessRules.FILE_PROJECT_EXTENSION);

          StringBuffer sBuffer = new StringBuffer();
		  for( int i = 0; i < index; i++) {
            sBuffer.append(p_FileName.charAt(i));
          }
         // newProjectReference.setName(sBuffer.toString());//,p_Path);
          newProjectReference.setFileName(p_FileName);
          newProjectReference.setTimeStamp(p_Project.getM_TimeStamp());
         // newProjectReference.setM_FilePath(p_FilePath);
          newProjectReference.setPath(p_Path);
          //Smoreno: Save for the import
          if (!(new File(newProjectReference.getFilePath()).exists()))
        	  ProjectManager.getInstance().writeProject2(p_Project, newProjectReference); //SAVE!

		  m_WorkspaceManager.addProjectReferenceToWorkspace2(newProjectReference,selectedWorkspace);

		  ProjectManager.getInstance().writeProject2(p_Project, newProjectReference); //SAVE!

		  CMProjectNode newProjectReferenceNode = (CMProjectNode) this.getTreePath(newProjectReference).getLastPathComponent();

		//  DefaultMutableTreeNode newProjectReferenceNode = createProjectNode2(newProjectReference);
		//  m_TreeModel.insertNodeInto(newProjectReferenceNode, selectedWorkspaceNode, selectedWorkspaceNode.getChildCount());
          //NEW:
		  //TODO : this must be done tru notification
          //createTestObjectNodes2(newProjectReferenceNode, newProjectReference);
		  newProjectReferenceNode.addChildrenNodes();
		  selectNode(newProjectReferenceNode);
      }
    }

	public void addTestObjectFromFile2() {
      String newName = null;
      TestObjectReference newTestObjectReference = null;
      boolean testObjectExistsIntoTheProject = false;
      DefaultMutableTreeNode selectedProjectReferenceNode = getSelectedNode();

      while( !testObjectExistsIntoTheProject ) {
		  File file = selectTestObjectFile();
		  if( file != null) {
			  //hmendez_09112005_begin
			  if (file.exists())
			  {
			  //hmendez_09112005_begin
            	m_Frame.setWaitCursor(true); //fcastro_20092004
				String filePath = file.getPath().replace('\\','/');
                //grueda22082004_begin
                TestObject openedTestObject = m_WorkspaceManager.readTestObject2(filePath);
                //grueda22082004_end
                //grueda30122004_begin
                if (openedTestObject == null) {
                	showValidationMessage(2,filePath);
		            m_Frame.setWaitCursor(false);
                    return;
                }
                //grueda30122004_end
                //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                String existingTestObjectAbsolutePath = file.getParent();

                String existingTestDataFilePath=((TDStructureReference)openedTestObject.getTDSTructureReference().firstElement()).getM_FilePath();

                String fileName = getFileNameWithouExtension(file.getName());

                StringBuffer existingTestDataAbsoluteFilePath = new StringBuffer();
                existingTestDataAbsoluteFilePath.append(existingTestObjectAbsolutePath);
                existingTestDataAbsoluteFilePath.append(BusinessRules.URL_SEPARATOR);
                existingTestDataAbsoluteFilePath.append(fileName);
                existingTestDataAbsoluteFilePath.append(BusinessRules.URL_SEPARATOR);
                existingTestDataAbsoluteFilePath.append(existingTestDataFilePath);

                TDStructure newTDStructure = m_WorkspaceManager.readTDStructure(existingTestDataAbsoluteFilePath.toString());

    			//Ccastedo begins 07-02-06
    			if (newTDStructure == null){
    			//	sendingValidationMessage(3,existingTestDataAbsoluteFilePath.toString());
    				String message="";
  		 		    message = CMBaseObjectReader.getReadFileState(existingTestDataAbsoluteFilePath.toString()).getMessage();
  		 		    if (CMBaseObjectReader.getAdditionalInfo(existingTestDataAbsoluteFilePath.toString())!= null){
   		 			   message = message + CMBaseObjectReader.getAdditionalInfo(existingTestDataAbsoluteFilePath.toString());
   		 		    }
  		 		    String l_TestObjectName = openedTestObject.getName();
  		 	  	    message = CMMessages.getString("CANNOT_OPEN_TESTDATA_FILE") + " " + l_TestObjectName + message + CMMessages.getString("QUESTION_DELETE");

  		 	  	    Object[] options = { "Yes", "No" };
  		 	  	    String operation = CMMessages.getString("ADD_TEST_OBJECT");
		 		    int confirmation = JOptionPane.showOptionDialog(CMApplication.frame,message,operation + CMMessages.getString("LABEL_VALIDATION_MESSAGE"),JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE, null,options, options[1]);
                    if( confirmation == JOptionPane.YES_OPTION) {

        				CMBaseObjectReader.setReadFileState(existingTestDataAbsoluteFilePath.toString(),CMXMLFileState.VALID);
        				TDStructure tdStructure = new TDStructure();
        				tdStructure.setM_TestObject(openedTestObject);
        				tdStructure.setM_TestObjectReference(newTestObjectReference);

        				TreePath currentSelection = getSelectionPath();
        				DefaultMutableTreeNode node = null;
        		        if( currentSelection != null) {
        		          node = (DefaultMutableTreeNode)(currentSelection.getLastPathComponent());
        		        }
                    	DefaultMutableTreeNode nodeAux= (DefaultMutableTreeNode)node.getParent();
                    	Enumeration  childs =nodeAux.children();
                		while(childs.hasMoreElements())
                		{
                            DefaultMutableTreeNode childsNode = (DefaultMutableTreeNode) childs.nextElement();
                            Object nodeinfo2=childsNode.getUserObject();
                        	if (nodeinfo2 instanceof CMResultsComparationNode) {
                        		CMResultsComparationNode resultcomprisoninfo = (CMResultsComparationNode) nodeinfo2;
                        		resultcomprisoninfo.setM_TDStructure(tdStructure);
        					}
                         }
                		newTDStructure = tdStructure;

                    }
                    else{
                    	//Hacer que no adicione el to
                    	 m_Frame.setWaitCursor(false);

                         testObjectExistsIntoTheProject = false;

       				    // JOptionPane.showMessageDialog(this.m_Frame, CMMessages.getString("CANNOT_OPEN_TESTOBJECT_FILE"));

                    	return;


                    }
                    }
    			//CCastedo ends 07-02-06

                /////////////////////////////////////////////////////////////////////////////////


                Project2 selectedProject = getSelectedProject2();
                //grueda22082004_begin
                ProjectReference selectedProjectReference = getSelectedProjectReference();
                //grueda22082004_end

               //grueda22082004_begin
				//if( !m_WorkspaceManager.existTestObjectReferenceInTheProject(filePath, selectedProject) ) {
				if( !m_WorkspaceManager.existTestObjectReferenceInTheProject(filePath, selectedProject, selectedProjectReference) ) {
					//TestObject openedTestObject = m_WorkspaceManager.readTestObject2(filePath);
				//grueda22082004_end
                    if( !m_WorkspaceManager.existTestObjectNameInTheProject(fileName,selectedProject) ) {
                      newName = fileName;
                    }
                    else {
                      newName = this.generateNewTestObjectName(selectedProject);
                    }
                    openedTestObject.setName(newName);
                    newTestObjectReference = m_WorkspaceManager.addTestObjectCloneToProject2(openedTestObject, selectedProjectReference, selectedProject, m_Session2);
                    if( newTestObjectReference == null){
                      return;
                    }
					// grueda30122004_begin
                    if(! writeAddedTestObjectFromFile(openedTestObject, selectedProjectReference, selectedProject, newTestObjectReference)){

                      m_Frame.setWaitCursor(false);
                      return;
                    }
					//grueda30122004_end

                    ////////////////////////////////////////////////////////
                    newTestObjectReference.setM_ChangedName(newName);//ccastedo 24-04-06
             //       newTestObjectReference.setFileName(newName); //ccastedo 24-04-06
                    //////////////////////////////////////////////
/// Freddy's code
					//if(openedTestObject.getM_BRulesReference()!=null){
                        //grueda22082004_begin
						//m_WorkspaceManager.updateBRulesReference(openedTestObject,newTestObjectReference, selectedProjectReference);
                        //grueda22082004_end
                    //}
/// ends Freddy's code
//////////////////////////////////////////adecuando TDstructure al nuevo proyecto////////////////////////////////////////////////////////////////
                 /**
             * codigo que faltaba hCanedo15.07.04_begin
             * */
            //String parentFileTD=file.getParent();

            //grueda30122004_begin
            Vector tdStructureReferences =  openedTestObject.getTDSTructureReference();
            if( tdStructureReferences == null){
              return;
            }
            if( tdStructureReferences.size() <= 0 ){
              return;
            }

            //grueda02092004_begin
            /*      Este código no tiene efecto????  Preguntar a Harold!!!
            if(filePathTD.indexOf(parentFileTD)==-1)
            {
                TDStructureReference tdsr=(TDStructureReference)openedTestObject.getM_TDSTructureReference().firstElement();
                tdsr.setM_Name(tdsr.getM_Name(),parentFileTD,openedTestObject.getM_Name());
            }
            */
            //grueda02092004_end
            ////////hcanedo15.07.04_end

                //grueda22082004_begin
    			//TDStructure newTDStructure =(TDStructure)readTDstructureforReference(openedTestObject,newTestObjectReference);// hacer TD file hay que abrir el td file

    			//////////////////////////////////////////////////////////////////////////////////////////////////

    			m_WorkspaceManager.getM_VersionManager().updateTestDataBasedOnVersion(newTDStructure,openedTestObject);
                //grueda30122004_begin
                if( newTDStructure != null) {
					//grueda22082004_end
					//rearmedTDStructureReference(openedTestObject,newTestObjectReference);
					m_WorkspaceManager.addTDStructure(newTDStructure,openedTestObject, newTestObjectReference);
					//grueda22082004_begin
					//saveTDStructure(newTDStructure);
					saveTDStructure(newTDStructure, selectedProjectReference);
					//grueda22082004_end
					m_WorkspaceManager.addTDStructure(newTDStructure,openedTestObject, newTestObjectReference);
                }
                //grueda30122004_end
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                  //grueda22082004_begin
                  //grueda30122004_begin
                  BRulesReference businessRulesReference = openedTestObject.getBRulesReference();
                  if( businessRulesReference != null) {
					  StringBuffer existingBusinessRulesAbsoluteFilePath = new StringBuffer();
					  existingBusinessRulesAbsoluteFilePath.append(existingTestObjectAbsolutePath);
					  existingBusinessRulesAbsoluteFilePath.append(BusinessRules.URL_SEPARATOR);
					  existingBusinessRulesAbsoluteFilePath.append(fileName);
					  existingBusinessRulesAbsoluteFilePath.append(BusinessRules.URL_SEPARATOR);
					  existingBusinessRulesAbsoluteFilePath.append(businessRulesReference.getFilePath());

					  BREditorManager businessRulesEditorManager = new BREditorManager();
                      StringBuffer businessRulesAbsoluteFilePath = new StringBuffer();
                      //grueda30122004_begin
                      businessRulesAbsoluteFilePath.append(selectedProjectReference.getPath());
                      businessRulesAbsoluteFilePath.append(BusinessRules.URL_SEPARATOR);
                      businessRulesAbsoluteFilePath.append(newTestObjectReference.getPath());
                      businessRulesAbsoluteFilePath.append(BusinessRules.URL_SEPARATOR);
                      businessRulesAbsoluteFilePath.append(openedTestObject.getName());
                      File f = new File(businessRulesAbsoluteFilePath.toString());
                      f.mkdir();
                      businessRulesAbsoluteFilePath.append(BusinessRules.URL_SEPARATOR);
                      businessRulesAbsoluteFilePath.append(businessRulesReference.getFileLocation());
                      f = new File(businessRulesAbsoluteFilePath.toString());
                      f.mkdir();

                      businessRulesAbsoluteFilePath.append(BusinessRules.URL_SEPARATOR);
                      businessRulesAbsoluteFilePath.append(businessRulesReference.getFileName());

                      File brFile = new File(existingBusinessRulesAbsoluteFilePath.toString());
					  String fileContent = businessRulesEditorManager.readContentFromFile(brFile);
					  businessRulesEditorManager.saveStringToFile(fileContent, new File(businessRulesAbsoluteFilePath.toString()));
                      //grueda30122004_end
                  }
                  //grueda30122004_end
					//DefaultMutableTreeNode newTestObjectReferenceNode = createTestObjectNode2(newTestObjectReference, openedTestObject);
                    //grueda17122004_begin
					DefaultMutableTreeNode newTestObjectReferenceNode = createTestObjectNode2(newTestObjectReference, openedTestObject, selectedProjectReference, null);
                    //grueda17122004_end
                  //grueda22082004_end
                    //grueda30122004_begin
                    if( newTestObjectReferenceNode != null) {
					  m_TreeModel.insertNodeInto(newTestObjectReferenceNode, selectedProjectReferenceNode, selectedProjectReferenceNode.getChildCount());
					  selectNode(newTestObjectReferenceNode);

                      testObjectExistsIntoTheProject = true;
                    }
                    //grueda3012004_end
				}
                else {
                    m_Frame.setWaitCursor(false);//fcastro_20092004

                  testObjectExistsIntoTheProject = false;
				  JOptionPane.showMessageDialog(this.m_Frame, CMMessages.getString("INFO_TEST_OBJECT_CAN_NOT_BE_ADDED")); //$NON-NLS-1$
                }
                //fcastro_20092004_begin
                if (m_Frame.isWaitCursorOn()) {
                    m_Frame.setWaitCursor(false);
                }
				//fcastro_20092004_end
                //hmendez_09112005_begin
			  }
			  else
			  {
				  JOptionPane.showMessageDialog((java.awt.Component)
		  		            null, CMMessages.getString("EXTERNAL_REPORTS_MENSSAGE_FILE_DOESNT_EXIST"),
		  		          CMMessages.getString("TITLE_MESSAGE_ERROR_NAME"), JOptionPane.ERROR_MESSAGE);
			  }
			  //hmendez_09112005_end
		  }
          else {
            testObjectExistsIntoTheProject = true;
          }
      }
    }

	public void showValidationMessage(int p_File, String p_FileName){
		//p_File= 1 --> Project
		//p_File= 2 --> TestObject
		//p_File= 3 --> TestData
		String message="";
		if (CMBaseObjectReader.getReadFileState(p_FileName) == null)
			return;
		if (CMBaseObjectReader.getAdditionalInfo(p_FileName)!= null){
			message = message + CMBaseObjectReader.getAdditionalInfo(p_FileName);
		}
			switch (p_File) {
			case 1:{
				if (CMBaseObjectReader.getAdditionalInfo(p_FileName)!= null){
		 			 message = message + CMBaseObjectReader.getAdditionalInfo(p_FileName);
		 		}

				message = CMMessages.getString("CANNOT_OPEN_PROJECT_FILE")+ CMMessages.getString("LABEL_FILENAME") + m_WorkspaceManager.findFileName(p_FileName) + message;
				break;
			}
			case 2:{

				message = CMMessages.getString("CANNOT_OPEN_TESTOBJECT_FILE")+ CMMessages.getString("LABEL_FILENAME") + m_WorkspaceManager.findFileName(p_FileName) + message;
				break;
			}
			case 3:{
				break;
			}
			}

			if (p_File == 1 || p_File ==2)
				//JOptionPane.showMessageDialog(CMApplication.frame, message);
				JOptionPane.showMessageDialog(CMApplication.frame,message,CMMessages.getString("ERROR_MESSAGE"),JOptionPane.ERROR_MESSAGE);

			m_Frame.setWaitCursor(false);
	}
	//Ccastedo ends 10-02-06

    //grueda30122004_begin
    public boolean writeAddedTestObjectFromFile(TestObject p_TestObject, ProjectReference p_ProjectReference, Project2 p_Project, TestObjectReference p_TestObjectReference){
			  StringBuffer newAbsoluteFilePath = new StringBuffer();
			  newAbsoluteFilePath.append( p_ProjectReference.getPath());
			  newAbsoluteFilePath.append(BusinessRules.URL_SEPARATOR);
			  if (p_TestObjectReference.getFileName().equalsIgnoreCase(""))
				  p_TestObjectReference.setFileName(p_TestObject.getName());
			  newAbsoluteFilePath.append(p_TestObjectReference.getFilePath());

			  boolean success = false;
			  if( p_Project.getAccessState().equals(BusinessRules.ACCESS_STATE_CHECKED_OUT)
				  && p_Project.getUser().equalsIgnoreCase(m_Session2.getM_User()) ) {
				  //File file = new File(absolutePath.toString());
				  //file.mkdir();
				  success = m_WorkspaceManager.writeTestObjectToFile2(p_TestObject, newAbsoluteFilePath.toString() );
				  if( success) {
					ProjectManager.getInstance().writeProject2(p_Project, p_ProjectReference);
                    //OLD: m_WorkspaceManager.writeProject2ToFile(p_Project, p_ProjectReference.getM_FilePath());
				  }
				  else{
					Logger.getLogger(this.getClass()).error("Could not write the added the testobject");
					JOptionPane.showMessageDialog(m_Frame, CMMessages.getString("INFO_NETWORK_DRIVE_NOT_AVAILABLE"));
					return false;
				  }
			  }
			  else{
				JOptionPane.showMessageDialog(this.m_Frame, "Current User may not create or import any Test Object of the current Project");
				return false;
			  }
			  return true;
    }
    //grueda30122004_end
    public File selectTestObjectFile() {
        JFileChooser testObjectFileChooser = new JFileChooser();
        testObjectFileChooser.addChoosableFileFilter(CMFileFilter.CTO.getFilter());
        testObjectFileChooser.setAcceptAllFileFilterUsed(false);
        testObjectFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int returnVal = testObjectFileChooser.showOpenDialog(m_Frame);
        java.io.File file;
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            file = testObjectFileChooser.getSelectedFile();
            return file;
        }
        else {
            return null;
        }
    }

    public String getFileNameWithouExtension(String p_CompleteFileName) {
        int index = p_CompleteFileName.indexOf("."); //$NON-NLS-1$
        StringBuffer sBuffer = new StringBuffer();
        if (index > 0) {
            for (int i = 0; i < index; i++) {
                sBuffer.append(p_CompleteFileName.charAt(i));
            }
            return sBuffer.toString();
        }
        else {
            return p_CompleteFileName;
        }
    }

    public String generateNewTestObjectName(Project2 p_Project) {
        String untitled = CMMessages.getString("LABEL_TEST_OBJECT_UNTITLED"); //$NON-NLS-1$
        StringBuffer sBuffer = null;
        int numOfTestObjectReferences = p_Project.getTestObjectReferences().size();
        for (int i = 0; i < numOfTestObjectReferences; i++) {
            sBuffer = new StringBuffer();
            sBuffer.append(untitled);
            sBuffer.append(i + 1);
            if (!existTestObjectName(sBuffer.toString(), p_Project)) {
                return sBuffer.toString();
            }
        }
        sBuffer = new StringBuffer();
        sBuffer.append(untitled);
        sBuffer.append(numOfTestObjectReferences + 1);
        return sBuffer.toString();
    }





    public boolean existTestObjectName(String p_Name, Project2 p_Project) {
        int numOfTestObjectReferences = p_Project.getTestObjectReferences().size();
        TestObjectReference testObjectReference = null;
        String existingName = null;
        for (int i = 0; i < numOfTestObjectReferences; i++) {
            testObjectReference = (TestObjectReference)p_Project.getTestObjectReferences().elementAt(i);
            existingName = testObjectReference.getName();
            if (existingName.equals(p_Name)) {
                return true;
            }
        }
        return false;
    }

    public void addNewTestObject() {
        CMProjectNode nodeInfo = getSelectedProjectNodeInfo();
        if (nodeInfo != null) {
            ProjectReference selectedProjectReference = nodeInfo.getProjectReference();
            Project2 selectedProject = nodeInfo.getProject();
            DefaultMutableTreeNode selectedProjectReferenceNode = getSelectedNode();
            if (selectedProjectReference != null && selectedProjectReferenceNode != null) {
                TestObjectReference newTestObjectReference = m_WorkspaceManager.createTestObjectReference();
                TestObject newTestObject = m_WorkspaceManager.createTestObject();
                String newName = generateNewTestObjectName(selectedProject);
                //grueda22082004_begin
                //newTestObjectReference.setM_Name(newName, selectedProjectReference.getM_Path());

                ////////////////////////////////////////////////////////////////////////
              //  newTestObjectReference.setM_Name(newName); //ccastedo 24-04-06
                newTestObjectReference.setFileName(newName); //ccastedo 24-04-06
                ////////////////////////////////////////////////////////////////////////

                newTestObject.setName(newName);


                newTestObject.setTestObjectReference(newTestObjectReference);//Ccastedo 21-04-06
                //My add........
                newTestObject.setToolVendor("compuware");

                newTestObject.setToolVendorTechnology("DotNET");
                //grueda22082004_end
                newTestObjectReference.setTimeStamp(newTestObject.getTimeStamp());

			  /*newTestObjectReference*/

                newTestObject.setUser(m_Session2.getM_User());
                m_WorkspaceManager.addTestObjectReferenceToProject2(newTestObjectReference, selectedProject);
                //grueda30122004_begin
                //Freddy's code
                BRulesReference newBRulesReference = new BRulesReference();

                ///////////////////////////////////Creando un TDStructure ///////////////////////////////////////////////////////
                //Hacer Cambios Harold TD File aumentar la creacion de refenrecias a TD file
                TDStructureReference newTDStructureReference = m_WorkspaceManager.createTDStructureReference();
                TDStructure newTDStructure = m_WorkspaceManager.createTDStructure();
                newTDStructureReference.setM_Name(newTDStructure.getM_Name(), newTestObjectReference.getPath(), newName);
                newTDStructureReference.setM_TimeStamp(newTDStructure.getM_TimeStamp());
                m_WorkspaceManager.addTestDataReferenceToTestObject(newTDStructureReference, newTestObject);
                m_WorkspaceManager.addTDStructure(newTDStructure, newTestObject, newTestObjectReference);
                //grueda22082004_begin
                //saveTDStructure(newTDStructure);
                //grueda22082004_end
                m_WorkspaceManager.addTDStructure(newTDStructure, newTestObject, newTestObjectReference);
                if (!writeNewTestObject2(newTestObject, newTestObjectReference, selectedProjectReference)) {
                    return;
                }
                m_WorkspaceManager.saveBRulesReference(newBRulesReference, newTestObjectReference, selectedProjectReference);
                saveTDStructure(newTDStructure, selectedProjectReference);
                //grueda30122004_end
                /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                //grueda22082004_begin
                //DefaultMutableTreeNode newTestObjectReferenceNode = createTestObjectNode2(newTestObjectReference, newTestObject);
                DefaultMutableTreeNode newTestObjectReferenceNode =
                    createNewTestObjectNode2(newTestObjectReference, newTestObject, newTDStructureReference,
                    newTDStructure, newBRulesReference);
                //grueda22082004_end
                m_TreeModel.insertNodeInto(newTestObjectReferenceNode, selectedProjectReferenceNode,
                    selectedProjectReferenceNode.getChildCount());
                //grueda17122004_begin
                //grueda21082004_begin
                //writeORoverwriteTestObject(newTestObject, newTestObjectReference, selectedProjectReference);
                //writeTestObject2(newTestObject, newTestObjectReference, selectedProjectReference);
                //grueda21082004_end
                //grueda17122004_end
                selectNode(newTestObjectReferenceNode);
                /////////////ver Harold crear TDStructure
            }
        }
    }

    public void removeTestObject(Project2 p_Project) {
        //grueda27102004_begin
        m_Frame.setWaitCursor(true);
        //grueda27102004_end
        CMTestObjectNode selectedTestObjectNodeInfo = getSelectedTestObjectNodeInfo();
        TestObjectReference selectedTestObjectReference = selectedTestObjectNodeInfo.geTestObjectReference();
        DefaultMutableTreeNode selectedTestObjectReferenceNode = getSelectedNode();
        if (selectedTestObjectReference != null && selectedTestObjectReferenceNode != null) {
            //grueda30082004_begin
            //deleteNode(selectedTestObjectReferenceNode);
            //grueda30082004_end
            Project2 project = p_Project;//getCurrentProject();//selectedTestObjectReference.getM_Project();
            m_WorkspaceManager.removeTestObjectReferenceInProject2(selectedTestObjectReference, project);
            ProjectReference projectReference = m_WorkspaceManager.getProjectReferenceOfProject2InSession2(project,
                m_Session2);

            //ccastedo begins 01.06.07
            String brFilePath = m_WorkspaceManager.buildAbsoluteBRulesFilePath(projectReference, selectedTestObjectReference);
            File brFile = new File(brFilePath);
            BREditorManager bem = new BREditorManager();
            bem.saveStringToFile("",brFile);

            ProjectManager.getInstance().writeProject2(project, projectReference);
            //update the new selected NodeInfo, which should be a ProjectNode
            //grueda30082004_begin
            deleteNode(selectedTestObjectReferenceNode);
            //grueda30082004_end
           // DefaultMutableTreeNode selectedProjectNode = getSelectedNode();
          //  CMProjectNode projectNodeInfo = (CMProjectNode)selectedProjectNode.getUserObject();
           // projectNodeInfo.setM_Project(project);
        }
        //grueda27102004_begin
        m_Frame.setWaitCursor(false);
        //grueda27102004_end
    }


  //    public void deleteWorkspace2() {
//        //grueda27102004_begin
//        m_Frame.setWaitCursor(true);
//        //grueda27102004_end
//        Workspace2 selectedWorkspace = this.getSelectedWorkspace2();
//
//        DefaultMutableTreeNode selectedWorkspaceNode = getSelectedNode();
//        if (selectedWorkspaceNode != null && selectedWorkspace != null) {
//            //grueda30082004_begin
//            //deleteNode(selectedWorkspaceNode);
//            //grueda30082004_end
//            m_WorkspaceManager.deleteWorkspace2(selectedWorkspace);
//            m_SessionManager.writeSession2ToFile(m_Session2);
//            //grueda30082004_begin
//            selectedWorkspace.removeModelListener(this);
//            deleteNode(selectedWorkspaceNode);
//            //grueda30082004_end
//        }
//        //grueda27102004_begin
//        m_Frame.setWaitCursor(false);
//        //grueda27102004_end
//    }

    //grueda30122004_begin

    /*
    public void saveProject2(Project2 p_Project, ProjectReference p_ProjectReference) {
		//FILE: .cpr.xml
        StringBuffer filePath = new StringBuffer(p_ProjectReference.getM_FilePath());
        File file = new File(filePath.toString());

        if( file.exists()) {
          m_WorkspaceManager.writeProject2ToFile(p_Project,filePath.toString());
        }
        else {
          this.saveProjectAs2(p_Project, p_ProjectReference);
        }
        //grueda22082004_begin
        //this.saveSession();
        this.saveSession2();
        //grueda22082004_end
    }
    */

    //grueda30122004_end


    public boolean existTestObjectFile(String p_FilePath) {
        File file = new File(p_FilePath);
        if (file.exists()) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean existProjectFile(String p_FilePath) {
        File file = new File(p_FilePath);
        if (file.exists()) {
            return true;
        }
        else {
            return false;
        }
    }

    //grueda22082004_begin
    //public void overwriteTestObject2(TestObject p_TestObject, TestObjectReference p_TestObjectReference){
    public void overwriteTestObject2(Project2 p_Project,TestObject p_TestObject, TestObjectReference p_TestObjectReference, ProjectReference p_ProjectReference){
      StringBuffer absoluteFilePath = new StringBuffer();
      absoluteFilePath.append(p_ProjectReference.getPath());
      absoluteFilePath.append(BusinessRules.URL_SEPARATOR);
      absoluteFilePath.append(p_TestObjectReference.getFilePath());
      //File file = new File(p_TestObjectReference.getM_FilePath());
     // File file = new File(absoluteFilePath.toString());
      //grueda22082004_end
      //if (file.renameTo(new File(absoluteFilePath.toString()+"temp")))
      //{
    //	  Logger.getLogger(this.getClass()).info("file renamed");
     // }
      //Logger.getLogger(this.getClass()).info("deleting file "+file.getAbsolutePath());
      //file.delete();
      if (!writeTestObject2(p_Project, p_TestObject, p_TestObjectReference, p_ProjectReference))
      {
    	  //recover the old file
    	  Logger.getLogger(this.getClass()).info("The test object was not wrote");
      }
      //grueda21082004_end
    }

    //grueda22082004_begin
    public void renameExistingTestObject(StringBuffer absoluteFilePath, StringBuffer absolutePath, StringBuffer newAbsoluteFilePath, StringBuffer newAbsolutePath) {
      File oldFile = new File(absoluteFilePath.toString());
      if(oldFile.exists()){
        oldFile.renameTo(new File(newAbsoluteFilePath.toString()));
      }

      File oldFolder = new File(absolutePath.toString());
      if( oldFolder.exists()) {
        oldFolder.renameTo(new File(newAbsolutePath.toString()));
      }
    }
   //grueda22082004_end


	public void overwriteProject2(Project2 p_Project, ProjectReference p_ProjectReference, DefaultMutableTreeNode p_OldNode) {
      StringBuffer parentPath = null;
      // Delete existing File
      File file = new File(p_ProjectReference.getFilePath());
      if( file != null) {
        file.delete();
      }
      // Rename existing project path
      File path = new File(p_ProjectReference.getPath());
      if( path != null) {
        parentPath = new StringBuffer();
        File parentFile = path.getParentFile();
        if( parentFile != null) {
            parentPath.append(m_WorkspaceManager.findCorrectPath(parentFile));
			parentPath.append(BusinessRules.URL_SEPARATOR); //$NON-NLS-1$
			parentPath.append(p_ProjectReference.getM_ChangedName());
			File renamedPath = new File(parentPath.toString());
			if( renamedPath != null) {
			  path.renameTo(renamedPath);
			}
        }
      }
      //Rewrite renamed Project
      //p_ProjectReference.changeName(p_ProjectReference.getM_ChangedName(), parentPath.toString());
      p_ProjectReference.setFileName(p_ProjectReference.getM_ChangedName());
      p_ProjectReference.setPath(parentPath.toString());
      p_Project.setName(p_ProjectReference.getM_ChangedName());
      ProjectManager.getInstance().writeProject2(p_Project, p_ProjectReference);

    }

    public void updateTestObjectNodes2(DefaultMutableTreeNode p_Node, String p_Name, String p_Path) {
        int numOfChildren = p_Node.getChildCount();
        TestObjectReference testObjectReference = null;
        DefaultMutableTreeNode childNode = null;
        for (int i = 0; i < numOfChildren; i++) {
            childNode = (DefaultMutableTreeNode)p_Node.getChildAt(i);
            CMTestObjectNode nodeInfo = (CMTestObjectNode)childNode.getUserObject();
            testObjectReference = (TestObjectReference)nodeInfo.geTestObjectReference();
            //grueda21082004_begin
            //testObjectReference.setM_Name(testObjectReference.getM_Name(), p_Path);

            //////////////////////////////////////////////////////////////////////////////
        //    testObjectReference.setM_Name(p_Name);//ccastedo 24-04-06
            testObjectReference.setFileName(p_Name);//ccastedo 24-04-06
            /////////////////////////////////////////////////////////////////////////////

            //grueda21082004_end
            updateTestDataNode(childNode, testObjectReference.getPath(), testObjectReference.getName()); //harold Canedo lopez
        }
    }

    //grueda22082004_begin
    //public void writeTestObject2(TestObject p_TestObject, TestObjectReference p_TestObjectReference) {
    public boolean writeTestObject2(Project2 p_Project, TestObject p_TestObject, TestObjectReference p_TestObjectReference, ProjectReference p_ProjectReference) {
      StringBuffer absolutePath = new StringBuffer();
      absolutePath.append(p_ProjectReference.getPath());
      absolutePath.append(BusinessRules.URL_SEPARATOR);
      absolutePath.append(p_TestObjectReference.getPath());
      StringBuffer absoluteFilePath = new StringBuffer();
      absoluteFilePath.append(p_ProjectReference.getPath());
      absoluteFilePath.append(BusinessRules.URL_SEPARATOR);
      absoluteFilePath.append(p_TestObjectReference.getFilePath());

       Project2 project =p_Project;/*getProyectForLastSelectedNode();/*getCurrentProject();// p_TestObjectReference.getM_Project();*/

       if(project == null){
    	  project=p_ProjectReference.getProject();
    	  if(project == null){
    		  Logger.getLogger(this.getClass()).debug("El proyecto es Null al escribir el test object");
    		  return false;
    	  }
      }


    	  boolean success = false;
		  File file = new File(absolutePath.toString());
		  file.mkdir();
		  success = m_WorkspaceManager.writeTestObjectToFile2(p_TestObject,absoluteFilePath.toString());
          if( success ) {
            if( project.getAccessState().equals(BusinessRules.ACCESS_STATE_CHECKED_OUT) && project.getUser().equalsIgnoreCase(m_Session2.getM_User()))
            	ProjectManager.getInstance().writeProject2(project, p_ProjectReference);
          }
          else{
        	  Logger.getLogger(this.getClass()).error("Could not write  the testobject");
            JOptionPane.showMessageDialog(m_Frame, CMMessages.getString("INFO_NETWORK_DRIVE_NOT_AVAILABLE"));
            return false;
          }
          return success;
    }

    //grueda30122004_begin
    public boolean writeNewTestObject2(TestObject p_TestObject, TestObjectReference p_TestObjectReference, ProjectReference p_ProjectReference) {
      StringBuffer absolutePath = new StringBuffer();
      absolutePath.append(p_ProjectReference.getPath());
      absolutePath.append(BusinessRules.URL_SEPARATOR);
      absolutePath.append(p_TestObjectReference.getPath());
      StringBuffer absoluteFilePath = new StringBuffer();
      absoluteFilePath.append(p_ProjectReference.getPath());
      absoluteFilePath.append(BusinessRules.URL_SEPARATOR);
      absoluteFilePath.append(p_TestObjectReference.getFilePath());

      Project2 project =getCurrentProject();//p_TestObjectReference.getM_Project();
      boolean success = false;
      if( project.getAccessState().equals(BusinessRules.ACCESS_STATE_CHECKED_OUT)
          && project.getUser().equalsIgnoreCase(m_Session2.getM_User()) ) {
		  File file = new File(absolutePath.toString());
		  file.mkdir();
		  success = m_WorkspaceManager.writeTestObjectToFile2(p_TestObject,absoluteFilePath.toString());
          if( success) {
        	  ProjectManager.getInstance().writeProject2(project, p_ProjectReference);
          }
          else{
        	  Logger.getLogger(this.getClass()).error("Could not write the new the testobject");
            JOptionPane.showMessageDialog(m_Frame, CMMessages.getString("INFO_NETWORK_DRIVE_NOT_AVAILABLE"));
            return false;
          }
      }
      else{
        JOptionPane.showMessageDialog(this.m_Frame, "Current User may not create or import any Test Object of the current Project");
        return false;
      }
      return true;
    }
    //grueda30122004_end

    //grueda22082004_begin
    public void saveTestObject2() {
        CMTestObjectNode selectedTestObjectNodeInfo = this.getSelectedTestObjectNodeInfo();
        // saveTestObjectReference(selectedTestObjectReference);
    }

    //grueda22082004_end
    public void saveWorkspace2() {
        this.saveSession2();
    }

    public void save2() {
  	    LicenseManager LM = new LicenseManager();
        if (!LM.getClientType().equals("Stand-Alone"))
        {
      	  if(!LM.notifyBeforeSave())
      	  {
            return;
      	  }
        }
        m_Frame.setWaitCursor(true);
        //grueda27102004_end
        //grueda25102004_begin
        DefaultMutableTreeNode node = getSelectedNode();
        ProjectReference currentProjectReference = this.getCurrentProjectReference();
        Project2 currentProject=this.getCurrentProject();
        if (node != null) {

            if (node instanceof CMProjectNode) {
                saveCurrentSelectedProjectInTheTree((CMProjectNode)node, node);
                //grueda30122004_begin
               // CMProjectNode projectNodeInfo = (CMProjectNode)node;
                //Project2 project = projectNodeInfo.getM_Project();
                //ProjectReference projectReference = projectNodeInfo.getM_ProjectReference();
                //m_Frame.getPanelProjectView().setM_Project2(project, projectReference);
                //grueda30122004_end
            }
            Object nodeInfo = node.getUserObject();
            if (nodeInfo != null) {

                if (nodeInfo instanceof CMTestObjectNode) {
                    CMTestObjectNode temp = (CMTestObjectNode)nodeInfo;
                    boolean returnToOldNode = checkIfTestObjectNameChanged(temp, currentProject); //integration_fcastro_17082004
                   // Project2 currentProject = getCurrentProject();//temp.getM_TestObjectReference().getM_Project();
                    m_WorkspaceManager.addTestObjectReferenceToProject2(temp.geTestObjectReference(), currentProject);
                    //grueda06112004_begin
                    if (m_WorkspaceManager.isTestObjectCheckedOutLocalByTheSameUser( temp.getTestObject(), m_Session2)) {
                        currentProjectReference = currentProjectReference.getM_LocalProjectReference();
                    }
                    //grueda06112004_end
                    //grueda30122004_begin
                    saveCurrentSelectedTestObjectInTheTree(currentProject, temp.getTestObject(), temp.geTestObjectReference(),
                        currentProjectReference, node);
                    //grueda30122004_end
                }
                else if (nodeInfo instanceof CMBusinessRulesNode) {
                    CMBusinessRulesNode temp = (CMBusinessRulesNode)nodeInfo;
                    m_Frame.saveBRules();
                    //grueda06112004_begin
                    if (m_WorkspaceManager.isTestObjectCheckedOutLocalByTheSameUser( temp.getM_TestObject(), m_Session2)) {
                        currentProjectReference = currentProjectReference.getM_LocalProjectReference();
                    }
                    //grueda06112004_end
                    //grueda30122004_begin
                    saveCurrentSelectedTestObjectInTheTree(currentProject, temp.getM_TestObject(), temp.getM_TestObjectReference(),
                        currentProjectReference, node);
                    //grueda30122004_end
                }
                else if (nodeInfo instanceof CMTestCasesNode) {
                    CMTestCasesNode temp = (CMTestCasesNode)nodeInfo;
                    //grueda06112004_begin
                    if (m_WorkspaceManager.isTestObjectCheckedOutLocalByTheSameUser(temp.getM_TestObject(), m_Session2)) {
                        currentProjectReference = currentProjectReference.getM_LocalProjectReference();
                    }
                    //grueda06112004_end
                    //grueda30122004_begin
                    saveCurrentSelectedTestObjectInTheTree(currentProject, temp.getM_TestObject(), temp.getM_TestObjectReference(),
                        currentProjectReference, node);
                    //grueda30122004_end
                }
                else if (nodeInfo instanceof CMTestDataNode) {
                    CMTestDataNode temp = (CMTestDataNode)nodeInfo;
                    //grueda06112004_begin
                    if (m_WorkspaceManager.isTestObjectCheckedOutLocalByTheSameUser( temp.getM_TestObject(), m_Session2)) {
                        currentProjectReference = currentProjectReference.getM_LocalProjectReference();
                    }
                    //grueda06112004_end
                    saveCurrentSelectedTestDataInTheTree(temp.getM_TDStructure(), temp.getM_TDStructureReference(),
                        currentProjectReference);
                    //grueda30122004_begin
                    saveCurrentSelectedTestObjectInTheTree(currentProject, temp.getM_TestObject(), temp.getM_TestObjectReference(),
                        currentProjectReference, node);
                    //grueda30122004_end
                }
                else if (nodeInfo instanceof CMResultsComparationNode) {
                    CMResultsComparationNode temp = (CMResultsComparationNode)nodeInfo;
                    //grueda06112004_begin
                    if (m_WorkspaceManager.isTestObjectCheckedOutLocalByTheSameUser( temp.getM_TestObject(), m_Session2)) {
                        currentProjectReference = currentProjectReference.getM_LocalProjectReference();
                    }
                    //grueda06112004_end
                    saveCurrentSelectedTestDataInTheTree(temp.getM_TDStructure(), temp.getM_TDStructureReference(),
                        currentProjectReference);
                    //grueda30122004_begin
                    saveCurrentSelectedTestObjectInTheTree(currentProject, temp.getM_TestObject(), temp.getM_TestObjectReference(),
                        currentProjectReference, node);
                    //grueda30122004_end
                }
                else if (nodeInfo instanceof CMTestManagementNode) {
                    CMTestManagementNode temp = (CMTestManagementNode)nodeInfo;
                    //grueda06112004_begin
                    if (m_WorkspaceManager.isTestObjectCheckedOutLocalByTheSameUser( temp.getM_TestObject(), m_Session2)) {
                        currentProjectReference = currentProjectReference.getM_LocalProjectReference();
                    }
                    //grueda06112004_end
                    //grueda30122004_begin
                    saveCurrentSelectedTestObjectInTheTree(currentProject, temp.getM_TestObject(), temp.getM_TestObjectReference(),
                        currentProjectReference, node);
                    //grueda30122004_end
                }
                else if (nodeInfo instanceof CMWorkspaceNode) {
                    saveCurrentSelectedWorkspaceInTheTree((CMWorkspaceNode)nodeInfo);
                }
                else {
                    ; //nothing
                }
            }
        }
        //grueda25102004_end
        //grueda27102004_begin
        m_Frame.setWaitCursor(false);
        //grueda27102004_end
        m_SessionManager.setM_Save_UnsaveVariable(0);
        m_Frame.getTreeWorkspaceView().requestFocus();
    }

    public Object getOldNodeInfo(TreeSelectionEvent e) {
        TreePath oldSelection = e.getOldLeadSelectionPath();
        DefaultMutableTreeNode oldNode = null;
        Object oldNodeInfo = null;
        if (oldSelection != null) {
            oldNode = (DefaultMutableTreeNode)(oldSelection.getLastPathComponent());
        }
        if (oldNode != null) {
            oldNodeInfo = oldNode.getUserObject();
        }
        return oldNodeInfo;
    }

    public DefaultMutableTreeNode getOldNode(TreeSelectionEvent e) {
        TreePath oldSelection = e.getOldLeadSelectionPath();
        DefaultMutableTreeNode oldNode = null;
        Object oldNodeInfo = null;
        if (oldSelection != null) {
            oldNode = (DefaultMutableTreeNode)(oldSelection.getLastPathComponent());
        }
        return oldNode;
    }

    //grueda05102004_begin
    public ProjectReference getOldProjectReference(TreeSelectionEvent e) {
        DefaultMutableTreeNode node = this.getOldNode(e);
        if (node == null) {
            return null;
        }
        while (node != m_RootNode && node != null) {

            if (node != null) {
                if (node instanceof CMProjectNode) {
                    CMProjectNode temp = (CMProjectNode)node;
                    return temp.getProjectReference();
                }
                else {
                    node = (DefaultMutableTreeNode)node.getParent();
                }
            }
            else {
                return null;
            }
        }
        return null;
    }
    public Project2 getProject2ForLastSelectedNode(TreeSelectionEvent e) {
        DefaultMutableTreeNode node = this.getOldNode(e);
        if (node == null) {
            return null;
        }
        while (node != m_RootNode && node != null) {

            if (node != null) {
                if (node instanceof CMProjectNode) {
                    CMProjectNode temp = (CMProjectNode)node;
                    return temp.getProject();
                }
                else {
                    node = (DefaultMutableTreeNode)node.getParent();
                }
            }
            else {
                return null;
            }
        }
        return null;
    }
    //grueda05102004_end
    public void saveCurrentSelectedProjectInTheTree(CMProjectNode p_ProjectNodeInfo, DefaultMutableTreeNode p_Node) {
        //grueda30122004_begin
        if (p_ProjectNodeInfo.getProject().getAccessState().equals(BusinessRules.ACCESS_STATE_CHECKED_OUT) &&
            p_ProjectNodeInfo.getProject().getUser().equalsIgnoreCase(this.m_Session2.getM_User())) {
                if (existProjectFile(p_ProjectNodeInfo.getProjectReference().getFilePath())) {
                    overwriteProject2(p_ProjectNodeInfo.getProject(), p_ProjectNodeInfo.getProjectReference(), p_Node);
                }
                else { //grueda14122004_begin
                    //writeProject2(p_ProjectNodeInfo.getM_Project(), p_ProjectNodeInfo.getM_ProjectReference());
                	Logger.getLogger(this.getClass()).error("Could not write the current project");
                    JOptionPane.showMessageDialog(m_Frame, CMMessages.getString("INFO_NETWORK_DRIVE_NOT_AVAILABLE"));
                    //grueda14122004_end
                }
        }
    }

    public void saveCurrentSelectedWorkspaceInTheTree(CMWorkspaceNode p_WorkspaceNodeInfo) {
        m_SessionManager.writeSession2ToFile(m_Session2);
    }

    //grueda30122004_begin
    //grueda22082004_begin
    //public void saveCurrentSelectedTestObjectInTheTree(TestObject p_TestObject, TestObjectReference p_TestObjectReference) {
    public void saveCurrentSelectedTestObjectInTheTree(Project2 p_Project,TestObject p_TestObject, TestObjectReference p_TestObjectReference,
        ProjectReference p_ProjectReference, DefaultMutableTreeNode p_SelectedNode) {
            //grueda22082004_end
            //grueda30122004_end
            //grueda30122004_begin
            //grueda25102004_end
            writeORoverwriteTestObject(p_Project, p_TestObject, p_TestObjectReference, p_ProjectReference, p_SelectedNode);
            //grueda25102004_end
            //grueda30122004_end
    }

    //integration_fcastro_17082004_begin
    public boolean checkIfTestObjectNameChanged(CMTestObjectNode nodeInfo, Project2 p_Project) {
        //grueda30122004_begin
        if (nodeInfo == null) {
            return false;
        }
        if (nodeInfo.getTestObject() == null || nodeInfo.geTestObjectReference() == null) {
            return false;
        }
        //grueda30122004_end
        TestObject testObject = ((CMTestObjectNode)nodeInfo).getTestObject();
        TestObjectReference reference = ((CMTestObjectNode)nodeInfo).geTestObjectReference();
        CMPanelTestObjectView panelTestObjects = this.m_Frame.getPanelTestObjectView();
        String panelName = panelTestObjects.getJTextField().getText().trim();
        if (!panelName.equals(testObject.getName())) {
            if (panelName.equals("")) {
                JOptionPane.showMessageDialog(m_Frame, CMMessages.getString("MESSAGE_ERROR_NAME_TESTOBJECT_EMPTY"),
                    CMMessages.getString("TITLE_MESSAGE_ERROR_TESTOBJECT_NAME"), JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$ //$NON-NLS-2$
                return true; //back to old node
            }
            else {
                //grueda05102004_begin
                if (panelTestObjects.validateName(panelName, p_Project)){//reference.getM_Project())) {
                    //grueda05102004_end
                    changedTestObjectNameWhileChangingSelection(panelName, testObject, reference);

                    //////////////////////////////////////////////////////////////////////////
                    reference.setM_ChangedName(panelName); //ccastedo 24-04-06
                   // reference.setFileName(panelName); //ccastedo 24-04-06
                    /////////////////////////////////////////////////////////////////////////

                    return false; //no need to go to old selection
                }
                else {
                    JOptionPane.showMessageDialog(m_Frame, CMMessages.getString("MENSSAGE_ERROR_SAME_NAME_TESTOBJECT"),
                        CMMessages.getString("TITLE_MESSAGE_ERROR_TESTOBJECT_NAME"), JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$ //$NON-NLS-2$
                    return true; //back to old node
                }
            }
        }
        return false;
    }

    public void changedTestObjectNameWhileChangingSelection(String newName, TestObject testObject,
        TestObjectReference testObjectReference) {
            if (testObjectReference != null && testObject != null) {
                testObject.setName(newName);
                testObjectReference.setM_ChangedName(newName);
                //grueda19092004_begin
                //overwriteTestData(testObject);
                //m_WorkspaceManager.updateBRPathWhenTOChangedName(testObject,testObjectReference);//Freddy's code
                //grueda19092004_end
                this.treeDidChange();
            }
    }

    //integration_17082004_end
    public void reloadTestObjectReferenceInChildrens(DefaultMutableTreeNode node, CMTestObjectNode nodeInfo, TestObjectReference p_TestObjectReference, TestObject p_TestObject)
    {
      if( nodeInfo != null && node != null &&p_TestObjectReference !=null && p_TestObject != null)
      {
		  		DefaultMutableTreeNode selectedTestObjectReferenceNode = node;
		  	   		Enumeration  childs =selectedTestObjectReferenceNode.children();
            		while(childs.hasMoreElements())
            		{
                       //grueda24112004_begin
                        DefaultMutableTreeNode childsNode = (DefaultMutableTreeNode) childs.nextElement();
                        Object temp2 = childsNode.getUserObject();
						if(temp2 instanceof CMBusinessRulesNode ){
                            CMBusinessRulesNode info=(CMBusinessRulesNode)temp2;
                            info.setM_TestObjectReference(p_TestObjectReference);
                            info.setM_TestObject(p_TestObject);
                        }
                        if(temp2 instanceof CMResultsComparationNode ){
                            CMResultsComparationNode info=(CMResultsComparationNode)temp2;
                            info.setM_TestObjectReference(p_TestObjectReference);
                            info.setM_TestObject(p_TestObject);
                        }
                        if(temp2 instanceof CMTestCasesNode ){
                            CMTestCasesNode info=(CMTestCasesNode)temp2;
                            info.setM_TestObjectReference(p_TestObjectReference);
                            info.setM_TestObject(p_TestObject);
                        }
                        if(temp2 instanceof CMTestDataNode ){
                            CMTestDataNode info=(CMTestDataNode)temp2;
                            info.setM_TestObjectReference(p_TestObjectReference);
                            info.setM_TestObject(p_TestObject);
                        }
                        if(temp2 instanceof CMTestManagementNode ){
                            CMTestManagementNode info=(CMTestManagementNode)temp2;
                            info.setM_TestObjectReference(p_TestObjectReference);
                            info.setM_TestObject(p_TestObject);
                        }
            		}
		  		}
    }

    public boolean/*void*/ saveTheLastSelectedTestObjectInTheTree(TreeSelectionEvent e, ProjectReference p_ProjectReference) {//integration_fcastro_17082004 changed return type
      //grueda30122004_begin
      ProjectReference realProjectReference = null;
      if( p_ProjectReference == null) {
        p_ProjectReference = getOldProjectReference(e);
      }
      Project2 oldProject=getProject2ForLastSelectedNode(e);
      //grueda30122004_end
	  Object oldNodeInfo = getOldNodeInfo(e);
      //grueda30122004_begin
      DefaultMutableTreeNode oldNode = this.getOldNode(e);

      //grueda30122004_end
      boolean returnToOldNode =false;//integration_fcastro_17082004
      //grueda22082004_begin
      //grueda05102004_begin
      //Project2 currentProject = getCurrentProject();
      //grueda05112004_begin
      //grueda05102004_end
      //grueda22082004_end

      if( oldNodeInfo != null) {
        DefaultMutableTreeNode testObjectNode=getCurrentTestObjectNode(oldNode);
        CMTestObjectNode temp2= null;
        if(testObjectNode!=null){
        Object testObjectNodeInfo=testObjectNode.getUserObject();
        if(testObjectNodeInfo instanceof CMTestObjectNode){
			 temp2 = (CMTestObjectNode) testObjectNodeInfo;
        }
        }
        else{
            if( oldNodeInfo instanceof CMTestObjectNode) {
				temp2 = (CMTestObjectNode) oldNodeInfo;
                testObjectNode=oldNode;
            }

        }
        if(temp2 != null)
        	reloadTestObjectReferenceInChildrens(testObjectNode,temp2,temp2.geTestObjectReference(),temp2.getTestObject());

          if( oldNodeInfo instanceof CMTestObjectNode) {
           // TestObjectNodeInfo temp = (TestObjectNodeInfo) oldNodeInfo;
        	  TestObject rebuilTO = null;
        	   boolean sw = false;
			  if( !m_WorkspaceManager.isTestObjectCheckedOutByTheSameUser(temp2.getTestObject(), m_Session2)
						 && !m_WorkspaceManager.isTestObjectCheckedOutLocalByTheSameUser(temp2.getTestObject(), m_Session2)) {
				  rebuilTO = rebuildLastSelectedTestObjectNode(e, m_Session2);
				sw= true;
			  }
			   if (sw && rebuilTO==null){
				   return false;
			   }
            returnToOldNode = checkIfTestObjectNameChanged((CMTestObjectNode)oldNodeInfo, oldProject);//integration_fcastro_17082004

            //grueda30122004_begin
            //grueda05102004_begin
            //Project2 oldProject = temp2.getM_TestObjectReference().getM_Project();
            //grueda05102004_end
            //grueda30122004_end
			m_WorkspaceManager.addTestObjectReferenceToProject2(temp2.geTestObjectReference(), oldProject);
            //grueda05112004_begin
			if( m_WorkspaceManager.isTestObjectCheckedOutLocalByTheSameUser(temp2.getTestObject(), m_Session2) ) {
				realProjectReference = p_ProjectReference.getM_LocalProjectReference();
			  }
			else {
				realProjectReference = p_ProjectReference;
			}
            //grueda05112004_end
            //grueda30122004_begin
			writeORoverwriteTestObject(oldProject, temp2.getTestObject(), temp2.geTestObjectReference(), realProjectReference, oldNode);
            //grueda30122004_end
          }
          else if( oldNodeInfo instanceof CMBusinessRulesNode) {
            //TestObjectBusinessRulesNodeInfo temp = (TestObjectBusinessRulesNodeInfo) oldNodeInfo;
            if( !m_WorkspaceManager.isTestObjectCheckedOutByTheSameUser(temp2.getTestObject(), m_Session2)
						 && !m_WorkspaceManager.isTestObjectCheckedOutLocalByTheSameUser(temp2.getTestObject(), m_Session2)) {
				rebuildLastSelectedTestObjectNode(e, m_Session2);
			  }

           //grueda05112004_begin
			if( m_WorkspaceManager.isTestObjectCheckedOutLocalByTheSameUser(temp2.getTestObject(), m_Session2) ) {
				realProjectReference = p_ProjectReference.getM_LocalProjectReference();
			  }
			else {
				realProjectReference = p_ProjectReference;
			}
            //grueda05112004_end
            //grueda30122004_begin
            m_Frame.saveBRules();
			writeORoverwriteTestObject(oldProject, temp2.getTestObject(), temp2.geTestObjectReference(), realProjectReference, oldNode);
            //grueda30122004_end
          }
          else if( oldNodeInfo instanceof CMTestCasesNode) {
        //    TestObjectTestCasesNodeInfo temp = (TestObjectTestCasesNodeInfo) oldNodeInfo;
			  if( !m_WorkspaceManager.isTestObjectCheckedOutByTheSameUser(temp2.getTestObject(), m_Session2)
						 && !m_WorkspaceManager.isTestObjectCheckedOutLocalByTheSameUser(temp2.getTestObject(), m_Session2)) {
				rebuildLastSelectedTestObjectNode(e, m_Session2);
			  }

           //grueda05112004_begin
			if( m_WorkspaceManager.isTestObjectCheckedOutLocalByTheSameUser(temp2.getTestObject(), m_Session2) ) {
				realProjectReference = p_ProjectReference.getM_LocalProjectReference();
			  }
			else {
				realProjectReference = p_ProjectReference;
			}
            //grueda05112004_end
            //grueda30122004_begin
			writeORoverwriteTestObject(oldProject, temp2.getTestObject(), temp2.geTestObjectReference(), realProjectReference, oldNode);
            //grueda30122004_end
          }
          else if( oldNodeInfo instanceof CMTestDataNode) {
           CMTestDataNode temp = (CMTestDataNode) oldNodeInfo;
			  if( !m_WorkspaceManager.isTestObjectCheckedOutByTheSameUser(temp2.getTestObject(), m_Session2)
						 && !m_WorkspaceManager.isTestObjectCheckedOutLocalByTheSameUser(temp2.getTestObject(), m_Session2)) {
				rebuildLastSelectedTestObjectNode(e, m_Session2);
			  }

           //grueda05112004_begin
			if( m_WorkspaceManager.isTestObjectCheckedOutLocalByTheSameUser(temp2.getTestObject(), m_Session2) ) {
				realProjectReference = p_ProjectReference.getM_LocalProjectReference();
			  }
			else {
				realProjectReference = p_ProjectReference;
			}
            //grueda05112004_end
            //grueda30122004_begin
            saveCurrentSelectedTestDataInTheTree(temp.getM_TDStructure(), temp.getM_TDStructureReference(), realProjectReference);
			writeORoverwriteTestObject(oldProject, temp2.getTestObject(), temp2.geTestObjectReference(), realProjectReference, oldNode);
            //grueda30122004_end
          }
          else if( oldNodeInfo instanceof CMResultsComparationNode) {
            CMResultsComparationNode temp = (CMResultsComparationNode) oldNodeInfo;
			  if( !m_WorkspaceManager.isTestObjectCheckedOutByTheSameUser(temp2.getTestObject(), m_Session2)
						 && !m_WorkspaceManager.isTestObjectCheckedOutLocalByTheSameUser(temp2.getTestObject(), m_Session2)) {
				rebuildLastSelectedTestObjectNode(e, m_Session2);
			  }

           //grueda05112004_begin
			if( m_WorkspaceManager.isTestObjectCheckedOutLocalByTheSameUser(temp2.getTestObject(), m_Session2) ) {
				realProjectReference = p_ProjectReference.getM_LocalProjectReference();
			  }
			else {
				realProjectReference = p_ProjectReference;
			}
            //grueda05112004_end
            //grueda30122004_begin
            saveCurrentSelectedTestDataInTheTree(temp.getM_TDStructure(), temp.getM_TDStructureReference(), realProjectReference);
			writeORoverwriteTestObject(oldProject, temp2.getTestObject(), temp2.geTestObjectReference(), realProjectReference, oldNode);
            //grueda30122004_end
          }
          else if( oldNodeInfo instanceof CMTestManagementNode) {
           // TestObjectTestManagementNodeInfo temp = (TestObjectTestManagementNodeInfo) oldNodeInfo;
			  if( !m_WorkspaceManager.isTestObjectCheckedOutByTheSameUser(temp2.getTestObject(), m_Session2)
						 && !m_WorkspaceManager.isTestObjectCheckedOutLocalByTheSameUser(temp2.getTestObject(), m_Session2)) {
				rebuildLastSelectedTestObjectNode(e, m_Session2);
			  }

           //grueda05112004_begin
			if( m_WorkspaceManager.isTestObjectCheckedOutLocalByTheSameUser(temp2.getTestObject(), m_Session2) ) {
				realProjectReference = p_ProjectReference.getM_LocalProjectReference();
			  }
			else {
				realProjectReference = p_ProjectReference;
			}
            //grueda05112004_end
            //grueda30122004_begin
			writeORoverwriteTestObject(oldProject, temp2.getTestObject(), temp2.geTestObjectReference(), realProjectReference, oldNode);
            //grueda30122004_end
          }
      }
      return returnToOldNode;//integration_fcastro_17082004
      //grueda05112004_end
    }

    //Ccastedo begin24-06-01
    public boolean saveTheLastSelectedTestObjectInTheTreeCC(TreeSelectionEvent e, ProjectReference p_ProjectReference) {
        ProjectReference realProjectReference = null;
        if( p_ProjectReference == null) {
          p_ProjectReference = getOldProjectReference(e);
        }
        Project2 oldProject=getProject2ForLastSelectedNode(e);
        Object oldNodeInfo = getOldNodeInfo(e);
        DefaultMutableTreeNode oldNode = this.getOldNode(e);
        boolean returnToOldNode =false;
        if( oldNodeInfo != null) {
            DefaultMutableTreeNode testObjectNode=getCurrentTestObjectNode(oldNode);
            CMTestObjectNode temp2= null;
            if(testObjectNode!=null){
            	Object testObjectNodeInfo=testObjectNode.getUserObject();
                if(testObjectNodeInfo instanceof CMTestObjectNode){
        			 temp2 = (CMTestObjectNode) testObjectNodeInfo;
                }
            }
            else{
                if( oldNodeInfo instanceof CMTestObjectNode) {
    				temp2 = (CMTestObjectNode) oldNodeInfo;
                    testObjectNode=oldNode;
                }
            }
            if(temp2 != null)
            	reloadTestObjectReferenceInChildrens(testObjectNode,temp2,temp2.geTestObjectReference(),temp2.getTestObject());
            if( oldNodeInfo instanceof CMTestObjectNode) {
            	  TestObject rebuilTO = null;
            	   boolean sw = false;
    			    if( !m_WorkspaceManager.isTestObjectCheckedOutByTheSameUser(temp2.getTestObject(), m_Session2)
    						 && !m_WorkspaceManager.isTestObjectCheckedOutLocalByTheSameUser(temp2.getTestObject(), m_Session2)) {
    				 				rebuilTO = rebuildLastSelectedTestObjectNode(e, m_Session2);
    				 				sw= true;
    			   }
    			   if (sw && rebuilTO==null){
    				   return false;
    			   }
    			   returnToOldNode = checkIfTestObjectNameChanged((CMTestObjectNode)oldNodeInfo, oldProject);
            		m_WorkspaceManager.addTestObjectReferenceToProject2(temp2.geTestObjectReference(), oldProject);
            		if( m_WorkspaceManager.isTestObjectCheckedOutLocalByTheSameUser(temp2.getTestObject(), m_Session2) ) {
        				realProjectReference = p_ProjectReference.getM_LocalProjectReference();
        			}
        			else {
        				realProjectReference = p_ProjectReference;
        			}
        			writeORoverwriteTestObject(oldProject, temp2.getTestObject(), temp2.geTestObjectReference(), realProjectReference, oldNode);
              }
              else if( oldNodeInfo instanceof CMBusinessRulesNode) {
            	  TestObject rebuilTO = null;
           	   	  boolean sw = false;
                  if( !m_WorkspaceManager.isTestObjectCheckedOutByTheSameUser(temp2.getTestObject(), m_Session2)
      						 && !m_WorkspaceManager.isTestObjectCheckedOutLocalByTheSameUser(temp2.getTestObject(), m_Session2)) {
      				rebuilTO = rebuildLastSelectedTestObjectNode(e, m_Session2);
      				sw=true;
      			  }
                  if (sw && rebuilTO==null){
   				   return false;
   			      }
                  if( m_WorkspaceManager.isTestObjectCheckedOutLocalByTheSameUser(temp2.getTestObject(), m_Session2) ) {
        				realProjectReference = p_ProjectReference.getM_LocalProjectReference();
        			}
        			else {
        				realProjectReference = p_ProjectReference;
        			}
                  	m_Frame.saveBRules();
                  	writeORoverwriteTestObject(oldProject, temp2.getTestObject(), temp2.geTestObjectReference(), realProjectReference, oldNode);
              }
              else if( oldNodeInfo instanceof CMTestCasesNode) {
            	  TestObject rebuilTO = null;
           	   	  boolean sw = false;
    			  if( !m_WorkspaceManager.isTestObjectCheckedOutByTheSameUser(temp2.getTestObject(), m_Session2)
    						 && !m_WorkspaceManager.isTestObjectCheckedOutLocalByTheSameUser(temp2.getTestObject(), m_Session2)) {
    				  	rebuilTO = rebuildLastSelectedTestObjectNode(e, m_Session2);
    				  	sw = true;
    			  }
    			  if (sw && rebuilTO==null){
      				   return false;
      			   }
    			  if( m_WorkspaceManager.isTestObjectCheckedOutLocalByTheSameUser(temp2.getTestObject(), m_Session2) ) {
      				realProjectReference = p_ProjectReference.getM_LocalProjectReference();
      			  }
      			  else {
      				  realProjectReference = p_ProjectReference;
      			  }
      			  writeORoverwriteTestObject(oldProject, temp2.getTestObject(), temp2.geTestObjectReference(), realProjectReference, oldNode);
              }
              else if( oldNodeInfo instanceof CMTestDataNode) {
                  CMTestDataNode temp = (CMTestDataNode) oldNodeInfo;
                  TestObject rebuilTO = null;
           	   	  boolean sw = false;
       			  if( !m_WorkspaceManager.isTestObjectCheckedOutByTheSameUser(temp2.getTestObject(), m_Session2)
       						 && !m_WorkspaceManager.isTestObjectCheckedOutLocalByTheSameUser(temp2.getTestObject(), m_Session2)) {
       				  		rebuilTO = rebuildLastSelectedTestObjectNode(e, m_Session2);
       				  			sw=true;
       			  }
       			  if (sw && rebuilTO==null){
         				   return false;
         		  }
       			  if( m_WorkspaceManager.isTestObjectCheckedOutLocalByTheSameUser(temp2.getTestObject(), m_Session2) ) {
       			    	realProjectReference = p_ProjectReference.getM_LocalProjectReference();
       			  }
       			  else {
       			    	realProjectReference = p_ProjectReference;
       			  }
                   saveCurrentSelectedTestDataInTheTree(temp.getM_TDStructure(), temp.getM_TDStructureReference(), realProjectReference);
       			   writeORoverwriteTestObject(oldProject, temp2.getTestObject(), temp2.geTestObjectReference(), realProjectReference, oldNode);
              }
              else if( oldNodeInfo instanceof CMResultsComparationNode) {
                  CMResultsComparationNode temp = (CMResultsComparationNode) oldNodeInfo;
                  TestObject rebuilTO = null;
           	   	  boolean sw = false;
      			  if( !m_WorkspaceManager.isTestObjectCheckedOutByTheSameUser(temp2.getTestObject(), m_Session2)
      						 && !m_WorkspaceManager.isTestObjectCheckedOutLocalByTheSameUser(temp2.getTestObject(), m_Session2)) {
      				  	rebuilTO = rebuildLastSelectedTestObjectNode(e, m_Session2);
      				  	sw = true;
      			  }
      			  if (sw && rebuilTO==null){
      				   return false;
      		      }
      			  if( m_WorkspaceManager.isTestObjectCheckedOutLocalByTheSameUser(temp2.getTestObject(), m_Session2) ) {
      				   realProjectReference = p_ProjectReference.getM_LocalProjectReference();
      			  }
      			  else {
      			    	realProjectReference = p_ProjectReference;
      			  }
                  saveCurrentSelectedTestDataInTheTree(temp.getM_TDStructure(), temp.getM_TDStructureReference(), realProjectReference);
      			 writeORoverwriteTestObject(oldProject, temp2.getTestObject(), temp2.geTestObjectReference(), realProjectReference, oldNode);
              }
              else if( oldNodeInfo instanceof CMTestManagementNode) {
            	  TestObject rebuilTO = null;
           	   	  boolean sw = false;
    			  if( !m_WorkspaceManager.isTestObjectCheckedOutByTheSameUser(temp2.getTestObject(), m_Session2)
    						 && !m_WorkspaceManager.isTestObjectCheckedOutLocalByTheSameUser(temp2.getTestObject(), m_Session2)) {
    				  		rebuilTO = rebuildLastSelectedTestObjectNode(e, m_Session2);
    				  		sw = true;
    			  }
    			  if (sw && rebuilTO==null){
     				   return false;
     		      }
    			  if( m_WorkspaceManager.isTestObjectCheckedOutLocalByTheSameUser(temp2.getTestObject(), m_Session2) ) {
    				  realProjectReference = p_ProjectReference.getM_LocalProjectReference();
    			  }
    			  else {
    				  realProjectReference = p_ProjectReference;
    			  }
    			  writeORoverwriteTestObject(oldProject, temp2.getTestObject(), temp2.geTestObjectReference(), realProjectReference, oldNode);
              }
        }
        return returnToOldNode;
      }
    //Ccastedo ends 24-06-01

    //grueda30122004_begin
    //grueda22082004_begin
    //public void writeORoverwriteTestObject(TestObject p_TestObject, TestObjectReference p_TestObjectReference) {
    public void writeORoverwriteTestObject(Project2 p_Project, TestObject p_TestObject, TestObjectReference p_TestObjectReference, ProjectReference p_ProjectReference, DefaultMutableTreeNode p_Node) {
      //grueda21092004_begin
      if( p_ProjectReference == null) {
        return;
      }
      //grueda21092004_end
   //grueda22082004_end
   //grueda30122004_end
      if( m_Session2.getM_User().equalsIgnoreCase(/*p_TestObjectReference*/p_TestObject.getUser()) ){
                    //grueda22082004_begin
                    String newTestObjectName = p_TestObjectReference.getM_ChangedName();
                    Logger.getLogger(this.getClass()).debug(newTestObjectName);
                    String oldTestObjectName = p_TestObjectReference.getName();
                    Logger.getLogger(this.getClass()).debug(oldTestObjectName);
                    if( !newTestObjectName.equals(oldTestObjectName)){
				      StringBuffer absoluteFilePath = new StringBuffer();
                      StringBuffer absolutePath = new StringBuffer();
                      StringBuffer newAbsoluteFilePath = new StringBuffer();
                      StringBuffer newAbsolutePath = new StringBuffer();

                      absoluteFilePath.append(p_ProjectReference.getPath());
                      absoluteFilePath.append(BusinessRules.URL_SEPARATOR);
                      absoluteFilePath.append(p_TestObjectReference.getPath());
                      absoluteFilePath.append(BusinessRules.URL_SEPARATOR);
                      absoluteFilePath.append(oldTestObjectName);
                      absoluteFilePath.append(BusinessRules.FILE_TESTOBJECT_EXTENSION);

                      absolutePath.append(p_ProjectReference.getPath());
                      absolutePath.append(BusinessRules.URL_SEPARATOR);
                      absolutePath.append(p_TestObjectReference.getPath());
                      absolutePath.append(BusinessRules.URL_SEPARATOR);
                      absolutePath.append(oldTestObjectName);

                      newAbsoluteFilePath.append(p_ProjectReference.getPath());
                      newAbsoluteFilePath.append(BusinessRules.URL_SEPARATOR);
                      newAbsoluteFilePath.append(p_TestObjectReference.getPath());
                      newAbsoluteFilePath.append(BusinessRules.URL_SEPARATOR);
                      newAbsoluteFilePath.append(newTestObjectName);
                      newAbsoluteFilePath.append(BusinessRules.FILE_TESTOBJECT_EXTENSION);

                      newAbsolutePath.append(p_ProjectReference.getPath());
                      newAbsolutePath.append(BusinessRules.URL_SEPARATOR);
                      newAbsolutePath.append(p_TestObjectReference.getPath());
                      newAbsolutePath.append(BusinessRules.URL_SEPARATOR);
                      newAbsolutePath.append(newTestObjectName);

                      Logger.getLogger(this.getClass()).debug(absoluteFilePath.toString());
                      Logger.getLogger(this.getClass()).debug(absolutePath.toString());
                      Logger.getLogger(this.getClass()).debug(newAbsoluteFilePath.toString());
                      Logger.getLogger(this.getClass()).debug(newTestObjectName);
                      p_TestObjectReference.setM_ChangedName(newTestObjectName);

                      TestObjectReference testObjectReferenceInProject=p_Project.getSameTestObjectReference(p_TestObjectReference);
                      try{
                    	  ////////////////////////////////////////////////////////////////////////////////////
                   //   testObjectReferenceInProject.setM_Name(newTestObjectName);
                    	  testObjectReferenceInProject.setFileName(newTestObjectName);
                    	  ///////////////////////////////////////////////////////////////////////////////////////
                      }
                      catch(NullPointerException ex){
                    	  ex.printStackTrace();
                      }
                      ////////////////////////////////////////////////////////////////////////////////////////
                //      p_TestObjectReference.setM_Name(newTestObjectName);//ccastedo 24-04-06
                      p_TestObjectReference.setFileName(newTestObjectName);//ccastedo 24-04-06
                      ///////////////////////////////////////////////////////////////////////////////////////

                      p_TestObject.setName(newTestObjectName);
                      //My add...
                      p_TestObject.setToolVendor(p_TestObject.getToolVendor());
                      p_TestObject.setToolVendorTechnology(p_TestObject.getToolVendorTechnology());


                      if( existTestObjectFile(absoluteFilePath.toString())) {
                        renameExistingTestObject(absoluteFilePath, absolutePath, newAbsoluteFilePath, newAbsolutePath);
                        overwriteTestObject2(p_Project, p_TestObject, p_TestObjectReference, p_ProjectReference);
                        CMApplication.frame.getPanelTestObjectView().setM_TestObjectReference(p_TestObjectReference);
                        //writeTestObject2(p_TestObject, p_TestObjectReference, p_ProjectReference);
                    }
                    else {
                        //grueda15122004_begin
					    //writeTestObject2(p_TestObject, p_TestObjectReference, p_ProjectReference);
                        if(!p_TestObject.getAccessState().equals(BusinessRules.ACCESS_STATE_CHECKED_IN)){
                        	Logger.getLogger(this.getClass()).error("Could not read  the testobject... tryin for network resolution on local check out");
                        	JOptionPane.showMessageDialog(m_Frame, CMMessages.getString("INFO_NETWORK_DRIVE_NOT_AVAILABLE"));
                        	applyErrorNetworkResolutionForCurrentTestObject(p_Project, p_ProjectReference, p_TestObjectReference,  p_TestObject, p_Node);
                        }
                        //grueda15122004_end
 				  	  }
                    }
                    else {
				      StringBuffer absoluteFilePath = new StringBuffer();
                      absoluteFilePath.append(p_ProjectReference.getPath());
                      absoluteFilePath.append(BusinessRules.URL_SEPARATOR);
                      absoluteFilePath.append(p_TestObjectReference.getFilePath());
                      Logger.getLogger(this.getClass()).debug(absoluteFilePath.toString());
                      if( existTestObjectFile(absoluteFilePath.toString())) {
			            overwriteTestObject2(p_Project, p_TestObject, p_TestObjectReference, p_ProjectReference);
                      }
					  else {
                        //grueda15122004_begin
					    //writeTestObject2(p_TestObject, p_TestObjectReference, p_ProjectReference);
                        if(!p_TestObject.getAccessState().equals(BusinessRules.ACCESS_STATE_CHECKED_IN)){
                        	Logger.getLogger(this.getClass()).error("Could not read  the testobject... tryin for network resolution on local check out");
                        	JOptionPane.showMessageDialog(m_Frame, CMMessages.getString("INFO_NETWORK_DRIVE_NOT_AVAILABLE"));
                        	applyErrorNetworkResolutionForCurrentTestObject(p_Project, p_ProjectReference, p_TestObjectReference,  p_TestObject, p_Node);
                        }
                        //grueda15122004_end
 				  	  }
                    }
                    //grueda22082004_end
       }
    }
    //grueda30122004_begin
    public void saveTheLastSelectedProjectInTheTree(TreeSelectionEvent e, ProjectReference p_ProjectReference, Session2 p_Session){
	  Object oldNodeInfo = getOldNodeInfo(e);
      DefaultMutableTreeNode oldNode = getOldNode(e);
      if( oldNodeInfo != null) {
          if( oldNodeInfo instanceof CMProjectNode) {
			  //Project2 project = reloadProject(p_ProjectReference, p_Session);
              Project2 project = rebuildLastSelectedProjectNode(e, p_Session);
              if (project == null) return;
			//grueda30122004_end
            CMProjectNode temp = (CMProjectNode) oldNodeInfo;
            //grueda30122004_begin
            if(temp.getProject().getAccessState().equals(BusinessRules.ACCESS_STATE_CHECKED_OUT)
                && temp.getProject().getUser().equalsIgnoreCase(this.m_Session2.getM_User()) ) {
                if( existProjectFile(temp.getProjectReference().getFilePath()) ) {
                    if( project.getAccessState().equals(BusinessRules.ACCESS_STATE_CHECKED_OUT) && project.getUser().equalsIgnoreCase(m_Session2.getM_User()))
                   		overwriteProject2(temp.getProject(), temp.getProjectReference(), oldNode);
                }
                else{
                  Logger.getLogger(this.getClass()).error("Could not write the last selected project");
                  JOptionPane.showMessageDialog(m_Frame, CMMessages.getString("INFO_NETWORK_DRIVE_NOT_AVAILABLE"));
				  //grueda30122004_begin
				  //writeProject2(temp.getM_Project(), temp.getM_ProjectReference());
				  //grueda30122004_end

                }
            }
         }
      }
    }

    public void saveTheLastSelectedWorkspaceInTheTree(TreeSelectionEvent e){
	  Object oldNodeInfo = getOldNodeInfo(e);
      if( oldNodeInfo != null) {
          if( oldNodeInfo instanceof CMWorkspaceNode) {
 			m_SessionManager.writeSession2ToFile(m_Session2);
          }
      }
    }

    	/*
    * BUG 339
    *Description:keine Reorganisation der Testobjekte möglich
    *Realizado por:  Harold Canedo lopez
    */

    //fcastro20082004_begin
    public void thisMouseClicked(MouseEvent e) {
        //this.m_Frame.eventMouseClicked(e);//integration_fcastro_17082004//removed fcastro_20082004
        //integration_fcastro_17082004_begin
        if (enteredValueChanged) {
            enteredValueChanged = false;
        }
        else {
            m_Frame.eventMouseClicked(e); //lets main frame know a click was made fcatro_20082004
            //hmendez_16112005_begin
            Object actualNodeInfo =null;
            if(this.getSelectedNode()!=null){
            //hmendez_16112005_end
              actualNodeInfo = this.getSelectedNode().getUserObject();
            }
            if (actualNodeInfo != null) {
                if (actualNodeInfo instanceof CMTestObjectNode) {
                	Project2 currentProject= getCurrentProject();
                    checkIfTestObjectNameChanged((CMTestObjectNode)actualNodeInfo, currentProject);
                }
            }
        }

        if (e!=null)
        if (e.getModifiers() == Event.META_MASK) {
            DefaultMutableTreeNode node = this.getSelectedNode();
            if (node != null) {
                Object nodeInfo = node.getUserObject();
                if (nodeInfo != null) {
                        this.m_Frame.getJPopupMenuFile().show(this, e.getX(), e.getY());
                }
            }
        }
    }

    //fcastro_10082004_end
    public DefaultMutableTreeNode getCurrentTestObjectNode(DefaultMutableTreeNode p_node){
	   DefaultMutableTreeNode node = p_node;
       if( node == null) {
         return null;
       }
       while( node != m_RootNode && node != null) {
			 Object nodeInfo = node.getUserObject();
			 if( nodeInfo != null) {
			   if( nodeInfo instanceof CMTestObjectNode) {
				 return node;
			   }
               else {
                 node = (DefaultMutableTreeNode) node.getParent();
               }
			 }
             else{
               return null;
             }
       }
       return null;
     }



    public void thisValueChangedCC(TreeSelectionEvent e) {
    	//smoreno -- do not allow null selection
    	if (e.getNewLeadSelectionPath()==null)
    		if (e.getOldLeadSelectionPath()==null)
    		{
    			selectRootNode();
    			return;
    		}
    		else
    		{
    			this.selectionModel.setSelectionPath(e.getOldLeadSelectionPath());
    			return;
    		}
    	//smoreno end
        ProjectReference currentProjectReference = getCurrentProjectReference();
        m_Frame.eventMouseClicked(e);//to let main frame know a click was made
        enteredValueChanged = true;
        m_Frame.setWaitCursor(true);
        ProjectReference oldProjectReference = getOldProjectReference(e);

        rebuildTO = null;
        boolean sw=false;

        boolean returnToOldNode = false;
        if (!fileCorrupted){
        	saveTheLastSelectedProjectInTheTree(e, oldProjectReference, m_Session2);
      	    returnToOldNode = saveTheLastSelectedTestObjectInTheTreeCC(e,oldProjectReference);
      	   	saveTheLastSelectedWorkspaceInTheTree(e);
        }

        TreePath currentSelection = getSelectionPath();
        DefaultMutableTreeNode node = null;
        if( currentSelection != null) {
          node = (DefaultMutableTreeNode)(currentSelection.getLastPathComponent());
        }

        if (node == null){
  		m_Frame.setWaitCursor(false);
          return;
        }

        Object nodeInfo = node.getUserObject();

        if( nodeInfo != null) {
            	if( nodeInfo instanceof CMTestObjectNode) {
            	CMTestObjectNode temp = (CMTestObjectNode) nodeInfo;
    			m_Frame.setStateTestObjectSelected();
    			Logger.getLogger(this.getClass()).debug(temp.getTestObject().getName() + ":  \n    "); //$NON-NLS-1$
                try {
    				if(temp.getTestObject().getDecimalSeparator().equals(",")){
    					CMFormatFactory.setTestObjectLocale(new Locale("de","DE"));
    				}
    				else{
    					CMFormatFactory.setTestObjectLocale(new Locale("en", "US"));
    				}
    			} catch (Exception e1) {
    			}

    		    if( m_WorkspaceManager.isTestObjectCheckedOutLocalByTheSameUser(temp.getTestObject(), m_Session2)
                    || m_WorkspaceManager.isTestObjectReferenceCheckOutLocal(temp.getTestObject())) {
    						currentProjectReference = currentProjectReference.getM_LocalProjectReference();
    			}

                if(temp.isIsTempTestObject() || (!m_WorkspaceManager.isTestObjectCheckedOutByTheSameUser(temp.getTestObject(), m_Session2)
                     && !m_WorkspaceManager.isTestObjectCheckedOutLocalByTheSameUser(temp.getTestObject(), m_Session2))) {
                			    rebuildTO = rebuildSelectedTestObjectNode(temp.geTestObjectReference(), currentProjectReference, m_Session2);
                   				temp.setIsTempTestObject(false);
               }

                 if (isFileWrong(temp)){
                	 StringBuffer absoluteFilePath = new StringBuffer();
              		 absoluteFilePath.append(getCurrentProjectReference().getPath());
              		 absoluteFilePath.append(BusinessRules.URL_SEPARATOR);
              		 absoluteFilePath.append(temp.geTestObjectReference().getFilePath());
                	 showValidationMessage(2,absoluteFilePath.toString());
                	 fileCorrupted = true;
                	 changeToProjectNode();
                	 m_Frame.setWaitCursor(false);
                	 return;
                 }

                 fileCorrupted = false;
                 selectTestObjectTabbedPane(temp.getPanelTestObject(),temp.getTestObject(), temp.geTestObjectReference(),currentProjectReference, temp.getLocalTestObjectReference());
                 if(returnToOldNode){
                    selectNode(getOldNode(e));
                 }
            }
            else if( nodeInfo instanceof CMBusinessRulesNode) {
            	CMBusinessRulesNode temp = (CMBusinessRulesNode) nodeInfo;
    			 m_Frame.setStateTestObjectBusinessRulesSelected();

    		     if( m_WorkspaceManager.isTestObjectCheckedOutLocalByTheSameUser(temp.getM_TestObject(), m_Session2) ) {
    				currentProjectReference = currentProjectReference.getM_LocalProjectReference();
    			 }

                 if( !m_WorkspaceManager.isTestObjectCheckedOutByTheSameUser(temp.getM_TestObject(), m_Session2)
                     && !m_WorkspaceManager.isTestObjectCheckedOutLocalByTheSameUser(temp.getM_TestObject(), m_Session2)) {
                	 		rebuildTO = rebuildCurrentTestObjectNode(temp.getM_TestObjectReference(), currentProjectReference, m_Session2);
                 }

                 if (isFileWrong(temp)){
                	 fileCorrupted = true;
                	 m_Frame.setWaitCursor(false);
                	 return;
                 }

                 fileCorrupted = false;
                selectTestObjectBusinessRulesTabbedPane(temp.getM_TestObject(), temp.getM_TestObjectReference(), currentProjectReference);

            }
            else if( nodeInfo instanceof CMTestCasesNode) {
            	CMTestCasesNode temp = (CMTestCasesNode) nodeInfo;
    			m_Frame.setStateTestObjectTestCasesSelected();

    		    if( m_WorkspaceManager.isTestObjectCheckedOutLocalByTheSameUser(temp.getM_TestObject(), m_Session2) ) {
    				currentProjectReference = currentProjectReference.getM_LocalProjectReference();
    			}

                if( !m_WorkspaceManager.isTestObjectCheckedOutByTheSameUser(temp.getM_TestObject(), m_Session2)
                     && !m_WorkspaceManager.isTestObjectCheckedOutLocalByTheSameUser(temp.getM_TestObject(), m_Session2)) {
                	Logger.getLogger(this.getClass()).info("Rebuilding TestObject");
                	rebuildTO = rebuildCurrentTestObjectNode(temp.getM_TestObjectReference(), currentProjectReference, m_Session2);
                }

                if (isFileWrong(temp)){
                	Logger.getLogger(this.getClass()).info("Test CAse node corrupted");
                	fileCorrupted = true;
                	m_Frame.setWaitCursor(false);
                	return;
                }
                fileCorrupted = false;
                selectTestObjectTestCasesTabbedPane(temp.getM_TestObject(), temp.getM_TestObjectReference());
            }
            else if( nodeInfo instanceof CMTestDataNode) {
            	CMTestDataNode temp = (CMTestDataNode) nodeInfo;
    			m_Frame.setStateTestObjectTestDataSelected();

    		    if( m_WorkspaceManager.isTestObjectCheckedOutLocalByTheSameUser(temp.getM_TestObject(), m_Session2) ) {
    				currentProjectReference = currentProjectReference.getM_LocalProjectReference();
    			}
                if( !m_WorkspaceManager.isTestObjectCheckedOutByTheSameUser(temp.getM_TestObject(), m_Session2)
                     && !m_WorkspaceManager.isTestObjectCheckedOutLocalByTheSameUser(temp.getM_TestObject(), m_Session2)) {
                		rebuildTO = rebuildCurrentTestObjectNode(temp.getM_TestObjectReference(), currentProjectReference, m_Session2);
                }

                CMTestDataNode l_TestObjectTestDataNodeInfo = (CMTestDataNode)temp;
                StringBuffer absoluteFilePath = new StringBuffer();
                absoluteFilePath.append(getCurrentProjectReference().getPath());
                absoluteFilePath.append(BusinessRules.URL_SEPARATOR);
                absoluteFilePath.append(BusinessRules.TEST_OBJECTS_FOLDER);
                absoluteFilePath.append(BusinessRules.URL_SEPARATOR);
                absoluteFilePath.append(temp.getM_TDStructureReference().getM_TestObject().getName());
                absoluteFilePath.append(BusinessRules.URL_SEPARATOR);
                absoluteFilePath.append(temp.getM_TDStructureReference().getM_FilePath());
                if (isFileWrong(temp)){
                	fileCorrupted = true;
                	String message = CMMessages.getString("CANNOT_OPEN_TESTDATA_FILE")+ " " + l_TestObjectTestDataNodeInfo.getM_TestObject().getName()+CMBaseObjectReader.getReadFileState(absoluteFilePath.toString()).getMessage()
                    + CMMessages.getString("QUESTION_DELETE");
                	Object[] options = { "Yes", "No" };
  		 		    int confirmation = JOptionPane.showOptionDialog(CMApplication.frame,message,CMMessages.getString("LABEL_DELETE_TESTDATA"),JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE, null,options, options[1]);
                    if( confirmation == JOptionPane.YES_OPTION) {
                  	  sw = true;
                    }
                    else{
                 	   changeToProjectNode();
                 	  m_Frame.setWaitCursor(false);
                   	   return;
                    }

                }

      		    if (sw){
      		    	temp.setM_TDStructure(new TDStructure());
                	temp.getM_TDStructure().setM_TestObject(temp.getM_TestObject());
                	temp.getM_TDStructure().setM_TestObjectReference(temp.getM_TestObjectReference());
                	temp.getM_TDStructure().setM_Version(BusinessRules.TESTDATA_FILE_VERSION);
                	CMBaseObjectReader.setReadFileState(absoluteFilePath.toString(),CMXMLFileState.VALID);
                   	DefaultMutableTreeNode nodeAux= (DefaultMutableTreeNode) node.getParent();
                	Enumeration  childs =nodeAux.children();
            		while(childs.hasMoreElements())
            		{
                        DefaultMutableTreeNode childsNode = (DefaultMutableTreeNode) childs.nextElement();
                        Object nodeinfo2=childsNode.getUserObject();
                    	if (nodeinfo2 instanceof CMResultsComparationNode) {
                    		CMResultsComparationNode resultcomprisoninfo = (CMResultsComparationNode) nodeinfo2;
                    		resultcomprisoninfo.setM_TDStructure(temp.getM_TDStructure());
    					}
                     }
      		    }

      		  if (temp.getM_TDStructure() == null){
              	//if (!CMApplication.frame.isIsPanelTestDataSelected())
      			    TDStructure newTDStructure = new TDStructure();
      			    newTDStructure = readTDstructureforReference(getCurrentTestObject(), getCurrentTestObjectReference(), currentProjectReference);
      			  	temp.setM_TDStructure(newTDStructure);
              		//temp.setM_TDStructure(CMApplication.frame.getGridTDStructure().getTDStructure());
      			  	////////////////////////////////////////////////////////////////////////////////
      			  temp.getM_TDStructure().setM_TestObject(temp.getM_TestObject());
      			  temp.getM_TDStructure().setM_TestObjectReference(temp.getM_TestObjectReference());
      			  temp.getM_TDStructure().setM_Version(BusinessRules.TESTDATA_FILE_VERSION);
      			  CMBaseObjectReader.setReadFileState(absoluteFilePath.toString(),CMXMLFileState.VALID);
      			  DefaultMutableTreeNode nodeAux= (DefaultMutableTreeNode) node.getParent();
      			  Enumeration  childs =nodeAux.children();
      			  while(childs.hasMoreElements())
      			  {
                      DefaultMutableTreeNode childsNode = (DefaultMutableTreeNode) childs.nextElement();
                      Object nodeinfo2=childsNode.getUserObject();
                  	  if (nodeinfo2 instanceof CMTestDataNode) {
                  		CMTestDataNode tdninfo = (CMTestDataNode) nodeinfo2;
                  		tdninfo.setM_TDStructure(temp.getM_TDStructure());
  					  }
                   }
              }
      		    fileCorrupted = false;
                selectTestObjectTestDataTabbedPane(temp.getM_TestObject(), temp.getM_TestObjectReference(),temp.getM_TDStructure());

					CMIndexTDStructureUpdate.getInstance().getTDStructureManager().chargeVectorIds(temp.getM_TDStructure());

            }
            else if( nodeInfo instanceof CMResultsComparationNode) {
            	CMResultsComparationNode temp = (CMResultsComparationNode) nodeInfo;
    		//	m_Frame.setStateTestObjectResultsComparationSelected();

    		    if( m_WorkspaceManager.isTestObjectCheckedOutLocalByTheSameUser(temp.getM_TestObject(), m_Session2) ) {
    				currentProjectReference = currentProjectReference.getM_LocalProjectReference();
    			}

                if( !m_WorkspaceManager.isTestObjectCheckedOutByTheSameUser(temp.getM_TestObject(), m_Session2)
                     && !m_WorkspaceManager.isTestObjectCheckedOutLocalByTheSameUser(temp.getM_TestObject(), m_Session2)) {
                		rebuildTO = rebuildCurrentTestObjectNode(temp.getM_TestObjectReference(), currentProjectReference, m_Session2);
                		sw = true;
                }
                CMResultsComparationNode l_TestObjectResultsComparationNodeInfo = (CMResultsComparationNode)temp;
                StringBuffer absoluteFilePath = new StringBuffer();
                absoluteFilePath.append(getCurrentProjectReference().getPath());
                absoluteFilePath.append(BusinessRules.URL_SEPARATOR);
                absoluteFilePath.append(BusinessRules.TEST_OBJECTS_FOLDER);
                absoluteFilePath.append(BusinessRules.URL_SEPARATOR);
                absoluteFilePath.append(temp.getM_TDStructureReference().getM_TestObject().getName());
                absoluteFilePath.append(BusinessRules.URL_SEPARATOR);
                absoluteFilePath.append(temp.getM_TDStructureReference().getM_FilePath());
                if (isFileWrong(temp)){
                	fileCorrupted = true;
                	String message = CMMessages.getString("CANNOT_OPEN_TESTDATA_FILE")+ " " + l_TestObjectResultsComparationNodeInfo.getM_TestObject().getName()+CMBaseObjectReader.getReadFileState(absoluteFilePath.toString()).getMessage()
                    + CMMessages.getString("QUESTION_DELETE");

                	Object[] options = { "Yes", "No" };
  		 		    int confirmation = JOptionPane.showOptionDialog(CMApplication.frame,message,CMMessages.getString("LABEL_DELETE_TESTDATA"),JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE, null,options, options[1]);
                    if( confirmation == JOptionPane.YES_OPTION) {
                  	  sw = true;
                    }
                    else{
                 	   changeToProjectNode();
                 	  m_Frame.setWaitCursor(false);
                   	   return;
                    }

                }

                if (sw){
      		    	temp.setM_TDStructure(new TDStructure());
                	temp.getM_TDStructure().setM_TestObject(temp.getM_TestObject());
                	temp.getM_TDStructure().setM_TestObjectReference(temp.getM_TestObjectReference());
                	temp.getM_TDStructure().setM_Version(BusinessRules.TESTDATA_FILE_VERSION);
                	CMBaseObjectReader.setReadFileState(absoluteFilePath.toString(),CMXMLFileState.VALID);
                	DefaultMutableTreeNode nodeAux= (DefaultMutableTreeNode) node.getParent();
                	Enumeration  childs =nodeAux.children();
            		while(childs.hasMoreElements())
            		{
                        DefaultMutableTreeNode childsNode = (DefaultMutableTreeNode) childs.nextElement();
                        Object nodeinfo2=childsNode.getUserObject();
                    	if (nodeinfo2 instanceof CMTestDataNode) {
                    		CMTestDataNode tdninfo = (CMTestDataNode) nodeinfo2;
                    		tdninfo.setM_TDStructure(temp.getM_TDStructure());
    					}
                     }
      		    }

                if (temp.getM_TDStructure() == null){
                	TDStructure newTDStructure = new TDStructure();
      			    newTDStructure = readTDstructureforReference(getCurrentTestObject(), getCurrentTestObjectReference(), currentProjectReference);
      			  	temp.setM_TDStructure(newTDStructure);
      			  	////////////////////////////////////////////////////
      			  temp.getM_TDStructure().setM_TestObject(temp.getM_TestObject());
      			  temp.getM_TDStructure().setM_TestObjectReference(temp.getM_TestObjectReference());
      			  temp.getM_TDStructure().setM_Version(BusinessRules.TESTDATA_FILE_VERSION);
      			  CMBaseObjectReader.setReadFileState(absoluteFilePath.toString(),CMXMLFileState.VALID);
      			  DefaultMutableTreeNode nodeAux= (DefaultMutableTreeNode) node.getParent();
      			  Enumeration  childs =nodeAux.children();
      			  while(childs.hasMoreElements())
      			  {
                      DefaultMutableTreeNode childsNode = (DefaultMutableTreeNode) childs.nextElement();
                      Object nodeinfo2=childsNode.getUserObject();
                  	  if (nodeinfo2 instanceof CMTestDataNode) {
                  		CMTestDataNode tdninfo = (CMTestDataNode) nodeinfo2;
                  		tdninfo.setM_TDStructure(temp.getM_TDStructure());
  					  }
                   }
      			  	/////////////////////////////////////////////////////
                }
                fileCorrupted = false;
                selectTestObjectResultsComparationTabbedPane(temp.getM_TestObject(), temp.getM_TestObjectReference(), temp.getM_TDStructure());

            }
            else if( nodeInfo instanceof CMTestManagementNode) {
            	CMTestManagementNode temp = (CMTestManagementNode) nodeInfo;
    			m_Frame.setStateTestObjectTestManagementSelected();
    		    if( m_WorkspaceManager.isTestObjectCheckedOutLocalByTheSameUser(temp.getM_TestObject(), m_Session2) ) {
    				currentProjectReference = currentProjectReference.getM_LocalProjectReference();
    			}
                if( !m_WorkspaceManager.isTestObjectCheckedOutByTheSameUser(temp.getM_TestObject(), m_Session2)
                     && !m_WorkspaceManager.isTestObjectCheckedOutLocalByTheSameUser(temp.getM_TestObject(), m_Session2)) {
                		rebuildTO = rebuildCurrentTestObjectNode(temp.getM_TestObjectReference(), currentProjectReference, m_Session2);
                		//sw = true;
                }
                if (isFileWrong(temp)){
                	fileCorrupted = true;
                	m_Frame.setWaitCursor(false);
                	return;
                }
                fileCorrupted = false;

                selectTestObjectTestManagementTabbedPane(temp.getM_TestObject(), temp.getM_TestObjectReference());
            }
            m_Frame.setWaitCursor(false);
            m_Frame.getCmApplication().getSessionManager().setM_Save_UnsaveVariable(0);
            this.requestFocus();
        }
      }


    public boolean isFileWrong(Object temp){
    	boolean isFileWrong = false;
    	boolean isTestObjectWrong = false;
        StringBuffer absoluteFilePath = new StringBuffer();
        if (ProjectManager.getSelectedProjectReference()==null)
        	return false;
 		absoluteFilePath.append(ProjectManager.getSelectedProjectReference().getPath());
 		absoluteFilePath.append(BusinessRules.URL_SEPARATOR);
 		if ((temp instanceof CMProjectNode)){
 			absoluteFilePath = new StringBuffer();
 			absoluteFilePath.append(((CMProjectNode)temp).getProjectReference().getFilePath());
 		}

 		if (temp instanceof CMTestObjectNode){
 			CMTestObjectNode l_TestObjectNodeInfo = (CMTestObjectNode)temp;
    		absoluteFilePath.append(l_TestObjectNodeInfo.geTestObjectReference().getFilePath());
    		if (CMBaseObjectReader.getReadFileState(absoluteFilePath.toString()) != CMXMLFileState.VALID)
    			isTestObjectWrong = true;
 		}
    	if (temp instanceof CMBusinessRulesNode){
    		CMBusinessRulesNode l_TestObjectBusinessRulesNodeInfo = (CMBusinessRulesNode)temp;
    		absoluteFilePath.append(l_TestObjectBusinessRulesNodeInfo.getM_TestObjectReference().getFilePath());
    		if (CMBaseObjectReader.getReadFileState(absoluteFilePath.toString()) != CMXMLFileState.VALID)
    			isTestObjectWrong = true;
    	}
    	if (temp instanceof CMTestCasesNode){
    		CMTestCasesNode l_TestObjectTestCasesNodeInfo = (CMTestCasesNode)temp;
    		absoluteFilePath.append(l_TestObjectTestCasesNodeInfo.getM_TestObjectReference().getFilePath());
    		if (CMBaseObjectReader.getReadFileState(absoluteFilePath.toString()) != CMXMLFileState.VALID)
    			isTestObjectWrong = true;
		}
    	if (temp instanceof CMTestDataNode){
    		CMTestDataNode l_TestObjectTestDataNodeInfo = (CMTestDataNode)temp;

    		if (CMBaseObjectReader.getReadFileState(absoluteFilePath+l_TestObjectTestDataNodeInfo.getM_TestObjectReference().getFilePath().toString()) != CMXMLFileState.VALID){
    			isTestObjectWrong = true;
    			absoluteFilePath.append(l_TestObjectTestDataNodeInfo.getM_TestObjectReference().getFilePath());

    		}
    		else{
    			absoluteFilePath.append(BusinessRules.TEST_OBJECTS_FOLDER);
                absoluteFilePath.append(BusinessRules.URL_SEPARATOR);
                absoluteFilePath.append(l_TestObjectTestDataNodeInfo.getM_TDStructureReference().getM_TestObject().getName());
                absoluteFilePath.append(BusinessRules.URL_SEPARATOR);
    			absoluteFilePath.append(l_TestObjectTestDataNodeInfo.getM_TDStructureReference().getM_FilePath());
    		}

		}
    	if (temp instanceof CMResultsComparationNode){
    		CMResultsComparationNode l_TestObjectResultsComparationNodeInfo = (CMResultsComparationNode)temp;

    		if (CMBaseObjectReader.getReadFileState(absoluteFilePath+l_TestObjectResultsComparationNodeInfo.getM_TestObjectReference().getFilePath().toString()) != CMXMLFileState.VALID){
    			isTestObjectWrong = true;
    			absoluteFilePath.append(l_TestObjectResultsComparationNodeInfo.getM_TestObjectReference().getFilePath());
    		}
    		else{
    			absoluteFilePath.append(BusinessRules.TEST_OBJECTS_FOLDER);
                absoluteFilePath.append(BusinessRules.URL_SEPARATOR);
                absoluteFilePath.append(l_TestObjectResultsComparationNodeInfo.getM_TDStructureReference().getM_TestObject().getName());
                absoluteFilePath.append(BusinessRules.URL_SEPARATOR);
    			absoluteFilePath.append(l_TestObjectResultsComparationNodeInfo.getM_TDStructureReference().getM_FilePath());
    		}

    	}
    	if (temp instanceof CMTestManagementNode){
    		CMTestManagementNode l_TestObjectTestManagementNodeInfo = (CMTestManagementNode)temp;
    		absoluteFilePath.append(l_TestObjectTestManagementNodeInfo.getM_TestObjectReference().getFilePath());
    	}

       absoluteFilePath.toString().replace('\\','/');
 	   long l_maxSizeofFile = (long) ((CMApplication.frame.getCmApplication().getSessionManager().getApplicationSettingManager().getApplicationSetting().getM_MaxSizeofXMLFiles()) *1024 * 1024);
       if (CMBaseObjectReader.fileSize(new File(absoluteFilePath.toString()))> l_maxSizeofFile)
       	CMBaseObjectReader.setReadFileState(absoluteFilePath.toString(),CMXMLFileState.BIGGERFILE);
       else{
       	if (CMBaseObjectReader.getReadFileState(absoluteFilePath.toString()) == CMXMLFileState.BIGGERFILE)
       		CMBaseObjectReader.deleteFileFromMap(absoluteFilePath.toString());//setReadFileState(absoluteFilePath.toString(),CMXMLFileState.VALID);
       }

 		if (CMBaseObjectReader.getReadFileState(absoluteFilePath.toString()) == CMXMLFileState.NEWVERSION)
		  {
 		   if (isTestObjectWrong){
 			  fileCorrupted = true;
 			  changeToProjectNode();
 		   }

           isFileWrong = true;

		  }
     else
       	if (CMBaseObjectReader.getReadFileState(absoluteFilePath.toString()) == CMXMLFileState.CORRUPTEDXML){
       		if (isTestObjectWrong){
       			fileCorrupted = true;
       			changeToProjectNode();
       		}

       		isFileWrong = true;
         	}
       	else{
       		if (CMBaseObjectReader.getReadFileState(absoluteFilePath.toString()) == CMXMLFileState.BIGGERFILE){
       			if (isTestObjectWrong){
       				fileCorrupted = true;
       				changeToProjectNode();
       			}

       			isFileWrong = true;
       		}
       	}
 		return isFileWrong;
    }
    // Ccastedo ends 17-03-06

    private void changeToProjectNode(){

   // 	selectNode(getOldNode(e));
    	selectNode(getCurrentTestObjectNode());
    	fireTreeCollapsed(getSelectionPath());
    	selectNode(getCurrentProjectNode());
    	fileCorrupted = false;
	    m_Frame.setWaitCursor(false);
        m_Frame.getCmApplication().getSessionManager().setM_Save_UnsaveVariable(0);
        this.requestFocus();
    }
    private void changeToLastNode(TreeSelectionEvent e){
       fileCorrupted  =true;
       selectNode(getOldNode(e));
	   m_Frame.setWaitCursor(false);
       m_Frame.getCmApplication().getSessionManager().setM_Save_UnsaveVariable(0);
       this.requestFocus();
    }

//    public void changedWorkspaceName2(String p_Name) {
//        Workspace2 workspace = getSelectedWorkspace2();
//        if (workspace != null) {
//            workspace.setName(p_Name);
//            this.treeDidChange();
//        }
//    }

    public void changedWorkspaceDescription2(String p_Description) {
        Workspace2 workspace = getSelectedWorkspace2();
        if (workspace != null) {
            workspace.setDescription(p_Description);
            this.treeDidChange();
        }
    }

//    public void changedProjectName2(String p_Name) {
//
//      CMProjectNode projectNodeInfo = getSelectedProjectNodeInfo();
//      //grueda30122004_begin
//      if( projectNodeInfo != null) {
//		  ProjectReference projectReference = projectNodeInfo.getM_ProjectReference();
//		  Project2 project = projectNodeInfo.getM_Project();
//		  if( projectReference != null && project != null) {
//			//grueda21082004_begin
//			  //OLD: projectReference.setM_Name(p_Name);
//			 projectReference.setM_ChangedName(p_Name);
//			 project.setName(p_Name);
//			//grued21082004_end
//			  this.treeDidChange();
//		  }
//      }
      //grueda30122004_end
//    }

    //grueda22082004_begin
    //public void changedTestObjectName2(String p_Name) {
    public void changedTestObjectName2(String p_Name, ProjectReference p_ProjectReference) {
        this.treeDidChange();
        //grueda22082004_end

    /*
      TestObjectNodeInfo testObjectNodeInfo = getSelectedTestObjectNodeInfo();
      TestObjectReference testObjectReference = testObjectNodeInfo.getM_TestObjectReference();
      TestObject testObject = testObjectNodeInfo.getM_TestObject();
      if( testObjectReference != null && testObject != null) {
          //grueda21082004_begin
          testObjectReference.setM_ChangedName(p_Name);
		  //testObject.setM_Name(p_Name);
          //Aquí solo se memoriza en la memoria pero no todavía en el archivo!!!
          //overwriteTestData(testObject);//harold canedo lopez
          //m_WorkspaceManager.updateBRPathWhenTOChangedName(testObject,testObjectReference);//Freddy's code
          //grueda21082004_end
		  this.treeDidChange();
      }*/

        //grueda22082004_end
    }

    public void changedProjectDescription2(String p_Description) {
        CMProjectNode projectNodeInfo = getSelectedProjectNodeInfo();
        //grueda30122004_begin
        if (projectNodeInfo != null) {
            ProjectReference projectReference = projectNodeInfo.getProjectReference();
            Project2 project = projectNodeInfo.getProject();
            if (project != null) {
                project.setM_Description(p_Description);
                this.treeDidChange();
            }
        }
        //grueda30122004_end
    }

    public void changedTestObjectDescription2(String p_Description) {
        CMTestObjectNode testObjectNodeInfo = getSelectedTestObjectNodeInfo();
        //grueda30122004_begin
        if (testObjectNodeInfo != null) {
            TestObjectReference testObjectReference = testObjectNodeInfo.geTestObjectReference();
            TestObject testObject = testObjectNodeInfo.getTestObject();
            if (testObject != null) {
                testObject.setDescription(p_Description);
                this.treeDidChange();
            }
        }
        //grueda30122004_end
    }

    public void changedTestObjectPreconditions(String p_Preconditions) {
        CMTestObjectNode testObjectNodeInfo = getSelectedTestObjectNodeInfo();
        //grueda30122004_begin
        if (testObjectNodeInfo != null) {
            TestObjectReference testObjectReference = testObjectNodeInfo.geTestObjectReference();
            TestObject testObject = testObjectNodeInfo.getTestObject();
            if (testObject != null) {
                testObject.setPreconditions(p_Preconditions);
                this.treeDidChange();
            }
        }
        //grueda30122004_end
    }

    public void saveSession2() {
        //FILE: .ini
        //m_SessionManager.saveSession2(m_Session2,"cm.ini.xml");
    }

    //grueda26122004_begin
    //grueda22082004_begin
    public void selectTestObjectTabbedPane(CMPanelTestObjectView panelTestObject, TestObject p_TestObject, TestObjectReference p_TestObjectReference,
        ProjectReference p_ProjectReference, TestObjectReference p_LocalTestObjectReference) {
            m_Frame.addTestObjectTabs(panelTestObject,p_TestObject, p_TestObjectReference, m_Session2, p_ProjectReference,
                p_LocalTestObjectReference);
    }

    //grueda22082004_end
    //grueda26122004_end
    //grueda22082004_begin
    //public void selectTestObjectBusinessRulesTabbedPane(TestObject p_TestObject, TestObjectReference p_TestObjectReference) {
    public void selectTestObjectBusinessRulesTabbedPane(TestObject p_TestObject, TestObjectReference p_TestObjectReference,
        ProjectReference p_ProjectReference) {
            //m_Frame.addTestObjectBusinessRulesTabs(p_TestObject, p_TestObjectReference, m_Session2);
            m_Frame.addTestObjectBusinessRulesTabs(p_TestObject, p_TestObjectReference, m_Session2, p_ProjectReference);
            //grueda22082004_end
    }

    public void selectTestObjectTestCasesTabbedPane(TestObject p_TestObject, TestObjectReference p_TestObjectReference) {
        m_Frame.addTestObjectTestCasesTabs(p_TestObject, p_TestObjectReference, m_Session2);
    }

    public void selectTestObjectTestDataTabbedPane(TestObject p_TestObject, TestObjectReference p_TestObjectReference,
        TDStructure p_TDStructure) {
            m_Frame.addTestObjectTestDataTabs(p_TestObject, p_TestObjectReference, m_Session2, p_TDStructure);
    }

    public void selectTestObjectResultsComparationTabbedPane(TestObject p_TestObject,
        TestObjectReference p_TestObjectReference, TDStructure p_TDStructure) {
            m_Frame.addTestObjectResultsComparationTabs(p_TestObject, p_TestObjectReference, m_Session2, p_TDStructure);
    }

    public void selectTestObjectTestManagementTabbedPane(TestObject p_TestObject, TestObjectReference p_TestObjectReference) {
        m_Frame.addTestObjectTestManagementTabs(p_TestObject, p_TestObjectReference, m_Session2);
    }

    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    public CMRootNode getM_RootNode() {
        return m_RootNode;
    }


    //grueda17122004_begin
    //grueda22082004_begin
    //public DefaultMutableTreeNode createTestObjectNode2(TestObjectReference p_TestObjectReference,TestObject p_TestObject) {
    public DefaultMutableTreeNode createTestObjectNode2(TestObjectReference p_TestObjectReference, TestObject p_TestObject,
        ProjectReference p_ProjectReference, TestObjectReference p_LocalTestObjectReference) {
            //grueda22082004_end

      /* OLD: 05.02.2004
      return new DefaultMutableTreeNode(new TestObjectNodeInfo( p_TestObjectReference, p_TestObject));
      */

            DefaultMutableTreeNode testObjectNode =
                new CMTestObjectNode(this, p_TestObjectReference, p_TestObject, p_LocalTestObjectReference);
            //grueda22082004_begin
            //addChildrenOfTestObjectNode(testObjectNode, p_TestObjectReference, p_TestObject);
            addChildrenOfTestObjectNode(testObjectNode, p_TestObjectReference, p_TestObject, p_ProjectReference);
            //grueda22082004_end
            return testObjectNode;
    }

    //grueda17122004_end
    //grueda22082004_begin
    public DefaultMutableTreeNode createNewTestObjectNode2(TestObjectReference p_TestObjectReference, TestObject p_TestObject,
        TDStructureReference p_TDStructureReference, TDStructure p_TDStructure, BRulesReference p_BRulesReference) {
            //grueda17122004_begin
    	CMTestObjectNode testObjectNode =new CMTestObjectNode(this, p_TestObjectReference, p_TestObject, null);
            //grueda17122004_end
            DefaultMutableTreeNode testObjectBusinessRulesNode =
                createNewTestObjectBRulesNode(p_TestObjectReference, p_TestObject, p_BRulesReference);
            DefaultMutableTreeNode testObjectTestCasesNode =
                createNewTestObjectTestCasesNode(p_TestObjectReference, p_TestObject);
            DefaultMutableTreeNode testObjectTestDataNode =
                createNewTestObjectTestDataNode(p_TestObjectReference, p_TestObject, p_TDStructureReference, p_TDStructure);
            DefaultMutableTreeNode testObjectResultComparisonNode =
                createNewTestObjectResultsComparisonNode(p_TestObjectReference, p_TestObject,
                p_TDStructureReference, p_TDStructure);
            DefaultMutableTreeNode testObjectTestManagement =
                createNewTestObjectTestManagementNode(p_TestObjectReference, p_TestObject);
            testObjectNode.add(testObjectBusinessRulesNode);
            testObjectNode.add(testObjectTestCasesNode);
            testObjectNode.add(testObjectTestDataNode);
            testObjectNode.add(testObjectResultComparisonNode);
            testObjectNode.add(testObjectTestManagement);
            return testObjectNode;
    }

    //grueda30122004_begin
    public DefaultMutableTreeNode createNewTestObjectBRulesNode(TestObjectReference p_TestObjectReference,
        TestObject p_TestObject, BRulesReference p_BRulesReference) {
            return new DefaultMutableTreeNode(
                new CMBusinessRulesNode(p_TestObjectReference, p_TestObject, p_BRulesReference, ""));
    }

    //grueda30122004_end
    public DefaultMutableTreeNode createNewTestObjectTestCasesNode(TestObjectReference p_TestObjectReference,
        TestObject p_TestObject) {
            return new DefaultMutableTreeNode(new CMTestCasesNode(p_TestObjectReference, p_TestObject));
    }

    public DefaultMutableTreeNode createNewTestObjectTestDataNode(TestObjectReference p_TestObjectReference,
        TestObject p_TestObject, TDStructureReference p_TDStructureReference, TDStructure p_TDStructure) {
            return new DefaultMutableTreeNode(
                new CMTestDataNode(p_TestObjectReference, p_TestObject, p_TDStructureReference, p_TDStructure));
    }

    public DefaultMutableTreeNode createNewTestObjectResultsComparisonNode(TestObjectReference p_TestObjectReference,
        TestObject p_TestObject, TDStructureReference p_TDStructureReference, TDStructure p_TDStructure) {
            return new DefaultMutableTreeNode(
                new CMResultsComparationNode(p_TestObjectReference, p_TestObject,
                p_TDStructureReference, p_TDStructure));
    }

    public DefaultMutableTreeNode createNewTestObjectTestManagementNode(TestObjectReference p_TestObjectReference,
        TestObject p_TestObject) {
            return new DefaultMutableTreeNode(new CMTestManagementNode(p_TestObjectReference, p_TestObject));
    }

    //grueda22082004_end
    public TestObject getSelectedTestObject() {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)this.getLastSelectedPathComponent();
        if (node != null) {
            Object nodeInfo = node.getUserObject();
            if (nodeInfo != null) {
                if (nodeInfo instanceof TestObject) {
                    TestObject selectedTestObject = (TestObject)nodeInfo;
                    return selectedTestObject;
                }
                else {
                    return null;
                }
            }
            else {
                return null;
            }
        }
        else {
            return null;
        }
    }

    public DefaultMutableTreeNode getSelectedNode() {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)this.getLastSelectedPathComponent();
        return node;
    }

    public void selectNode(DefaultMutableTreeNode p_Node) {
        if (p_Node != null) {
              setSelectionPath(new TreePath(p_Node.getPath()));
        }
    }
    public void selectNode(TreePath p_path) {

    	if ( p_path.getParentPath()!=null)
    		expandChildren((DefaultMutableTreeNode) p_path.getParentPath().getLastPathComponent());
           setSelectionPath(p_path);

    }

    public void deleteNode(DefaultMutableTreeNode p_Node) {
        if (p_Node != null) {
            MutableTreeNode parent = (MutableTreeNode)(p_Node.getParent());
            if (parent != null) {
                //grueda30082004_begin
                p_Node.setUserObject(null);
                //grueda30082004_end
                m_TreeModel.removeNodeFromParent(p_Node);
                selectNode((DefaultMutableTreeNode)parent);
            }
        }
    }

    public void expandChildren(DefaultMutableTreeNode start) {
        TreePath tp = new TreePath(start.getPath());
        this.expandPath(tp);
    }

    public void changedTestObjectDescription(String p_Description) {
        TestObject testObject = getSelectedTestObject2();
        if (testObject != null) {
            testObject.setDescription(p_Description);
            this.treeDidChange();
        }
    }
    public Session2 getM_Session2() {
        return m_Session2;
    }

    public void setM_Session2(Session2 m_Session2) {
        this.m_Session2 = m_Session2;
    }

    //grueda09112004_begin
    public WorkspaceManager getM_WorkspaceManager() {
        return this.m_WorkspaceManager;
    }

    public TestObjectReference deriveTestObjectReference(Object value) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
        TestObjectReference testObjectReference = null;
        if (node != null) {
            Object nodeInfo = node.getUserObject();
            if (nodeInfo != null) {
                if (nodeInfo instanceof CMTestObjectNode) {
                    CMTestObjectNode testObjectNodeInfo = (CMTestObjectNode)nodeInfo;
                    testObjectReference = testObjectNodeInfo.geTestObjectReference();
                    return testObjectReference;
                }
                else {
                    return null;
                }
            }
            else {
                return null;
            }
        }
        else {
            return null;
        }
    }

    //grueda09112004_end
    //grueda26122004_begin
    public TestObjectReference deriveLocalTestObjectReference(Object value) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
        TestObjectReference testObjectReference = null;
        if (node != null) {
            Object nodeInfo = node.getUserObject();
            if (nodeInfo != null) {
                if (nodeInfo instanceof CMTestObjectNode) {
                    CMTestObjectNode testObjectNodeInfo = (CMTestObjectNode)nodeInfo;
                    testObjectReference = testObjectNodeInfo.getLocalTestObjectReference();
                    return testObjectReference;
                }
                else {
                    return null;
                }
            }
            else {
                return null;
            }
        }
        else {
            return null;
        }
    }

    //grueda26122004_end
    public TestObject deriveTestObject(Object value) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
        TestObject testObject = null;
        if (node != null) {
            Object nodeInfo = node.getUserObject();
            if (nodeInfo != null) {
                if (nodeInfo instanceof CMTestObjectNode) {
                    CMTestObjectNode testObjectNodeInfo = (CMTestObjectNode)nodeInfo;
                    testObject = testObjectNodeInfo.getTestObject();
                    return testObject;
                }
                else {
                    return null;
                }
            }
            else {
                return null;
            }
        }
        else {
            return null;
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////
    //hcanedo_23082004_begin
    public void setSelectedTDStructure(TDStructure p_TDStructure) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)this.getLastSelectedPathComponent();
        if (node != null) {
            Object nodeInfo = node.getUserObject();
            if (nodeInfo != null)
                if (nodeInfo instanceof CMTestDataNode) {
                    CMTestDataNode nodeaux = (CMTestDataNode)nodeInfo;
                    //	TDStructure selectedTDStructure = (TDStructure)nodeaux.getM_TestObject().getM_TDStructure().elementAt(0); old TD file
                    nodeaux.setM_TDStructure(p_TDStructure);
                }
        }
    }

    //hcanedo_23082004_end
    public TDStructure getSelectedTDStructure() {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)this.getLastSelectedPathComponent();
        if (node != null) {
            Object nodeInfo = node.getUserObject();
            if (nodeInfo != null) {
                if (nodeInfo instanceof CMTestDataNode) {
                    CMTestDataNode nodeaux = (CMTestDataNode)nodeInfo;
                    //	TDStructure selectedTDStructure = (TDStructure)nodeaux.getM_TestObject().getM_TDStructure().elementAt(0); old TD file
                    TDStructure selectedTDStructure = nodeaux.getM_TDStructure();
                    return selectedTDStructure;
                }
                else {
                    return null;
                }
            }
            else {
                return null;
            }
        }
        else {
            return null;
        }
    }

    //hcanedo_23082004_begin
    public void setSelectedResultComparation(TDStructure p_TDStructure) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)this.getLastSelectedPathComponent();
        if (node != null) {
            Object nodeInfo = node.getUserObject();
            if (nodeInfo != null)
                if (nodeInfo instanceof CMResultsComparationNode) {
                    CMResultsComparationNode nodeaux = (CMResultsComparationNode)nodeInfo;
                    nodeaux.setM_TDStructure(p_TDStructure);
                }
        }
    }

    //hcanedo_23082004_end
    public TDStructure getSelectedResultComparation() {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)this.getLastSelectedPathComponent();
        if (node != null) {
            Object nodeInfo = node.getUserObject();
            if (nodeInfo != null) {
                if (nodeInfo instanceof CMResultsComparationNode) {
                    CMResultsComparationNode nodeaux = (CMResultsComparationNode)nodeInfo;
                    //	TDStructure selectedTDStructure = (TDStructure)nodeaux.getM_TestObject().getM_TDStructure().elementAt(0); old TD file
                    TDStructure selectedTDStructure = nodeaux.getM_TDStructure();
                    return selectedTDStructure;
                }
                else {
                    return null;
                }
            }
            else {
                return null;
            }
        }
        else {
            return null;
        }
    }

    public void changedTDStructureName(String p_Name) {
        TDStructure testData = getSelectedTDStructure();
        if (testData != null) {
            testData.setM_Name(p_Name);
            this.treeDidChange();
        }
    }

    public void changedTDStructureDescription(String p_Description) {
        TDStructure testData = getSelectedTDStructure();
        if (testData != null) {
            testData.setM_Description(p_Description);
            this.treeDidChange();
        }
    }

    public void selectTDStructureTabbedPane(TDStructure p_TDStructure) {
        m_Frame.addTDStructureTabs(p_TDStructure, p_TDStructure.getM_TestObjectReference());
    }

    //grueda22082004_begin
    //public void saveTDStructure(TDStructure p_TDStructure) {
    public void saveTDStructure(TDStructure p_TDStructure, ProjectReference p_ProjectReference) {
        //grueda22082004_end
        //File: .ctd
        //  this.saveTestObject(p_TDStructure.getM_TestObject());
        //grueda22082004_begin
        //this.m_WorkspaceManager.saveTDStructure(p_TDStructure,p_TDStructure.getM_TestObject());
        this.m_WorkspaceManager.saveTDStructure(p_TDStructure, p_TDStructure.getM_TestObject(), p_ProjectReference);
        //grueda22082004_end
    }

    public void deleteTDStructure() {
        DefaultMutableTreeNode selectedTDStructureNode = getSelectedNode().getNextNode();
        TDStructure selectedTDStructure = null;
        if (selectedTDStructureNode != null) {
            Object nodeInfo = selectedTDStructureNode.getUserObject();
            if (nodeInfo != null) {
                if (nodeInfo instanceof TDStructure) {
                    selectedTDStructure = (TDStructure)nodeInfo;
                }
            }
        }
        if (selectedTDStructure != null && selectedTDStructureNode != null) {
            //grueda30082004_begin
            //deleteNode(selectedTDStructureNode);
            //grueda30082004_end
            m_WorkspaceManager.deleteTDStructure(selectedTDStructure);
            //grueda30082004_begin
            deleteNode(selectedTDStructureNode);
            //grueda30082004_end
        }
    }

    //grueda30122004_begin
    //grueda22082004_begin
    //public TDStructure readTDstructureforReference(TestObject p_TestObject, TestObjectReference p_TestObjectReference)
    public TDStructure readTDstructureforReference(TestObject p_TestObject, TestObjectReference p_TestObjectReference,
        ProjectReference p_ProjectReference)
        //grueda22082004_end
        {
            if (p_TestObject.getTDSTructureReference() == null) {
                return createEmptyTestDataStructure(p_TestObject, p_TestObjectReference, p_ProjectReference);
            }
            else {
                //grueda28082004_begin
                //TDStructure newTDStructure = m_WorkspaceManager.readTDStructure(((TDStructureReference)p_TestObject.getM_TDSTructureReference().firstElement()).getM_FilePath());
                if (p_TestObject.getTDSTructureReference().size() <= 0) {
                    return createEmptyTestDataStructure(p_TestObject, p_TestObjectReference, p_ProjectReference);
                }
                else {
                    TDStructureReference tdStructureReference =
                        (TDStructureReference)p_TestObject.getTDSTructureReference().firstElement();
                    TDStructure newTDStructure = m_WorkspaceManager.readTDStructure(tdStructureReference, p_ProjectReference);
                    //Ccastedo begins 26-01-06
                    if (newTDStructure == null){
                    	return null;
                    }
                    //Ccastedo ends 26-01-06

                    boolean rewrite=m_WorkspaceManager.getM_VersionManager().updateTestDataBasedOnVersion(newTDStructure,p_TestObject);
                    if(rewrite){
                    	m_WorkspaceManager.saveTDStructure(newTDStructure,p_TestObject,p_ProjectReference);
                    }
                    //grueda28082004_end
                    //m_WorkspaceManager.addTDStructure(newTDStructure, p_TestObject, p_TestObjectReference);
                    //grueda22082004_begin
                    //saveTDStructure(newTDStructure);
                    //saveTDStructure(newTDStructure, p_ProjectReference);
                    //grueda22082004_end
                    m_WorkspaceManager.addTDStructure(newTDStructure, p_TestObject, p_TestObjectReference);
                    ///invertir el orden de estas dos lineas.
                    return newTDStructure;
                }
            }
    }

    public TDStructure createEmptyTestDataStructure(TestObject p_TestObject, TestObjectReference p_TestObjectReference,
        ProjectReference p_ProjectReference) {
            p_TestObject.setTDSTructureReference(new Vector(0));
            TDStructureReference newTDStructureReference = m_WorkspaceManager.createTDStructureReference();
            TDStructure newTDStructure = m_WorkspaceManager.createTDStructure();
            newTDStructureReference.setM_Name(newTDStructure.getM_Name(), p_TestObjectReference.getPath(),
                p_TestObject.getName());
            newTDStructureReference.setM_TimeStamp(newTDStructure.getM_TimeStamp());
            m_WorkspaceManager.addTestDataReferenceToTestObject(newTDStructureReference, p_TestObject);
            m_WorkspaceManager.addTDStructure(newTDStructure, p_TestObject, p_TestObjectReference);
            //grueda22082004_begin
            //saveTDStructure(newTDStructure);
            saveTDStructure(newTDStructure, p_ProjectReference);
            //grueda22082004_end
            m_WorkspaceManager.addTDStructure(newTDStructure, p_TestObject, p_TestObjectReference);
            return newTDStructure;
 }
 //grueda30122004_end

//grueda22082004_begin
 public void saveCurrentSelectedTestDataInTheTree(TDStructure p_TDStructure, TDStructureReference p_TDStructureReference, ProjectReference p_ProjectReference)
 {
        //grueda21092004_begin
        if( p_ProjectReference == null) {
          return;
        }
        //grueda21092004_end
        StringBuffer absoluteFilePath = new StringBuffer();
        absoluteFilePath.append(p_ProjectReference.getPath());
        absoluteFilePath.append(BusinessRules.URL_SEPARATOR);
        absoluteFilePath.append(BusinessRules.TEST_OBJECTS_FOLDER);
        absoluteFilePath.append(BusinessRules.URL_SEPARATOR);
        absoluteFilePath.append(p_TDStructureReference.getM_TestObject().getName());
        //grueda15122004_begin
        if( existTDStructureFile(absoluteFilePath.toString()) ) {
			absoluteFilePath.append(BusinessRules.URL_SEPARATOR);
            absoluteFilePath.append(p_TDStructureReference.getM_Path());
			absoluteFilePath.append(BusinessRules.URL_SEPARATOR);
			absoluteFilePath.append(p_TDStructureReference.getM_Name());
			absoluteFilePath.append(BusinessRules.FILE_TESTDATA_EXTENSION);
            writeTDStructure2(p_TDStructure, p_TDStructureReference, p_ProjectReference);
        }
        else {
        	Logger.getLogger(this.getClass()).error("Could not save  the current test data");
            JOptionPane.showMessageDialog(m_Frame, CMMessages.getString("INFO_NETWORK_DRIVE_NOT_AVAILABLE"));
        }

        //grueda15122004_end

 }
//grueda22082004_end
    public boolean existTDStructureFile(String p_FilePath) {
        File file = new File(p_FilePath);
        if (file.exists()) {
            return true;
        }
        else {
            return false;
        }
    }

    //grueda22082004_begin
    //public void writeTDStructure2(TDStructure p_TDStructure, TDStructureReference p_TDStructureReference)
    public void writeTDStructure2(TDStructure p_TDStructure, TDStructureReference p_TDStructureReference, ProjectReference p_ProjectReference)
    {
      StringBuffer absolutePath = new StringBuffer();
      absolutePath.append(p_ProjectReference.getPath());
      absolutePath.append(BusinessRules.URL_SEPARATOR);
      absolutePath.append(BusinessRules.TEST_OBJECTS_FOLDER);
      absolutePath.append(BusinessRules.URL_SEPARATOR);
      absolutePath.append(p_TDStructureReference.getM_TestObject().getName());
      absolutePath.append(BusinessRules.URL_SEPARATOR);
      absolutePath.append(p_TDStructureReference.getM_Path());

      StringBuffer absoluteFilePath = new StringBuffer();
      absoluteFilePath.append(p_ProjectReference.getPath());
      absoluteFilePath.append(BusinessRules.URL_SEPARATOR);
      absoluteFilePath.append(BusinessRules.TEST_OBJECTS_FOLDER);
      absoluteFilePath.append(BusinessRules.URL_SEPARATOR);
      absoluteFilePath.append(p_TDStructureReference.getM_TestObject().getName());
      absoluteFilePath.append(BusinessRules.URL_SEPARATOR);
      absoluteFilePath.append(p_TDStructureReference.getM_FilePath());

	  File file = new File(absolutePath.toString());
      file.mkdir();
	  m_WorkspaceManager.writeTDStructureToFile(p_TDStructure, absoluteFilePath.toString());
		  //File file = new File(p_TDStructureReference.getM_Path());
		  //file.mkdir();
		  //m_WorkspaceManager.writeTDStructureToFile(p_TDStructure, p_TDStructureReference.getM_FilePath());
    }
    //grueda22082004_end

   public int numberOfTestObjectNodesInCurrentProjectNode() {
        DefaultMutableTreeNode node = this.getSelectedNode();
        MutableTreeNode parent = (MutableTreeNode)(node.getParent());
        if (parent != null) {
            node = (DefaultMutableTreeNode)parent;
        }

        if (node != null) {
            if (node instanceof CMProjectNode) {
            	Logger.getLogger(this.getClass()).debug("NumeroDeNodos" + node.getChildCount());
                return node.getChildCount();
            }
        }
        return -1;
    }

    public boolean isCurrentProjectContainsTestObjectsLocal() {
        DefaultMutableTreeNode node = this.getSelectedNode();
        Object nodeInfo = node.getUserObject();
        if (!(nodeInfo instanceof CMProjectNode)&&!(node instanceof CMProjectNode)) {
            MutableTreeNode parent = (MutableTreeNode)(node.getParent());
            if (parent != null) {
                node = (DefaultMutableTreeNode)parent;
            }
            nodeInfo = node.getUserObject();
        }
        if (node != null) {
            if (node instanceof CMProjectNode) {
                Enumeration childs = node.children();
                while (childs.hasMoreElements()) {
                    DefaultMutableTreeNode childNode = (DefaultMutableTreeNode)childs.nextElement();
                    Object childNodeInfo = childNode.getUserObject();
                    if (childNodeInfo instanceof CMTestObjectNode) {
                        CMTestObjectNode testObjectNodeInfo = (CMTestObjectNode)childNodeInfo;
                        TestObject currentTestObject = testObjectNodeInfo.getTestObject();
                        if (currentTestObject.getAccessState().equals(BusinessRules.ACCESS_STATE_CHECKED_OUT_LOCAL))
                            return true;
                    }
                }
                return false;
            }
        }
        return true;
    }

   public void showReorderTestObjectDialog()
    {
         DefaultMutableTreeNode node = this.getSelectedNode();

        	if (node != null)
        	{
				Object nodeInfo = node.getUserObject();
				if( nodeInfo != null)
                {

					if( nodeInfo instanceof CMTestObjectNode)
                    {
                        CMTestObjectNode testObjectNodeInfo=(CMTestObjectNode)nodeInfo;
                        Vector nodes=orderTestObjectNode();
                        Project2 project = getProjectForCurrentTestObjectNode(node);
                        if (project != null) {
                            CMDialogReorderTestObjects cmd = new CMDialogReorderTestObjects(m_Frame, project, nodes);
                            cmd.show();
                            if (!cmd.cancelselected) {
                                save2();
                                reorderTestObjectNodes(nodes);
                            }

                        }
                    }
                }
        	}
    }
   public Project2 getProjectForCurrentTestObjectNode(DefaultMutableTreeNode p_Node) {
    DefaultMutableTreeNode node = p_Node;
    MutableTreeNode parent = (MutableTreeNode)(node.getParent());
    if (parent != null) {
        node = (DefaultMutableTreeNode)parent;
    }
    if (node != null) {
        if (node instanceof CMProjectNode) {
            CMProjectNode temp = (CMProjectNode)node;
            ProjectReference selectedProjectReference = temp.getProjectReference();
            Project2 selectedProject = temp.getProject();
            return selectedProject;
        }
    }
    return null;
}
   public void deleteNodeForReOrder(DefaultMutableTreeNode p_Node) {
        if (p_Node != null) {
            MutableTreeNode parent = (MutableTreeNode)(p_Node.getParent());
            if (parent != null) {
                m_TreeModel.removeNodeFromParent(p_Node);
                //  selectNode((DefaultMutableTreeNode)parent);
            }
        }
    }

    public Vector orderTestObjectNode()
    {
        Vector testObjectNodes= new Vector(0);
        DefaultMutableTreeNode node = this.getSelectedNode();
        MutableTreeNode parent = (MutableTreeNode)(node.getParent());
            if (parent != null)
            {
                node= (DefaultMutableTreeNode)parent;
            }
        if( node != null)
      {
        	if( node instanceof CMProjectNode)
        	{
				CMProjectNode temp = (CMProjectNode) node;
		  		ProjectReference selectedProjectReference = temp.getProjectReference();
		  		Project2 selectedProject = temp.getProject();
		  		DefaultMutableTreeNode selectedProjectReferenceNode = node;
		  		if( selectedProjectReference != null && selectedProjectReferenceNode != null)
          		{
            		Enumeration  childs =selectedProjectReferenceNode.children();
            		while(childs.hasMoreElements())
            		{
                       /*//grueda24112004_begin
                        DefaultMutableTreeNode testObjectNode = (DefaultMutableTreeNode) childs.nextElement();
                        Object temp2 = testObjectNode.getUserObject();
                        TestObjectNodeInfo testObjectNodeInfo = (TestObjectNodeInfo) temp2;
               			testObjectNodes.addElement(testObjectNodeInfo);//.getM_TestObjectReference());
                        //grueda24112004_end*/

                        testObjectNodes.addElement(childs.nextElement());
                    }
                }
            }
        }
        return testObjectNodes;
    }

    public void reorderTestObjectNodes(Vector p_nodes) {
        DefaultMutableTreeNode node = this.getSelectedNode();
        MutableTreeNode parent = (MutableTreeNode)(node.getParent());
        if (parent != null) {
            node = (DefaultMutableTreeNode)parent;
        }
        if (node != null) {
            if (node instanceof CMProjectNode) {
                CMProjectNode temp = (CMProjectNode)node;
                ProjectReference selectedProjectReference = temp.getProjectReference();
                Project2 selectedProject = temp.getProject();
                DefaultMutableTreeNode selectedProjectReferenceNode = node;
                for (int j = 0; j < p_nodes.size(); j++)
                    deleteNodeForReOrder((DefaultMutableTreeNode)p_nodes.elementAt(j));
                node.removeAllChildren();
                this.repaint();
                this.treeDidChange();
                if (selectedProjectReference != null && selectedProjectReferenceNode != null) {
                    for (int i = 0; i < p_nodes.size(); i++) {
                        DefaultMutableTreeNode aux;
                        Object testObjectNode = p_nodes.elementAt(i);
                        if (testObjectNode instanceof DefaultMutableTreeNode) {
                            aux = (DefaultMutableTreeNode)testObjectNode;
                            m_TreeModel.insertNodeInto(aux, selectedProjectReferenceNode,
                                selectedProjectReferenceNode.getChildCount());
                            // selectNode(aux);
                        }
                    }
                    this.expandChildren(node);
                }
            }
        }
    }

    public void updateTestDataNode(DefaultMutableTreeNode p_Node, String p_Path, String p_TestObjectName) {
        int numOfChildren = p_Node.getChildCount();
        //grueda22082004_begin
        TDStructureReference tdStructureReference = null;
        DefaultMutableTreeNode childNode = null;
        for (int i = 0; i < numOfChildren; i++) {
            childNode = (DefaultMutableTreeNode)p_Node.getChildAt(i);
            if (childNode.getUserObject() instanceof CMTestDataNode) {
                CMTestDataNode nodeInfo = (CMTestDataNode)childNode.getUserObject();
                tdStructureReference = (TDStructureReference)nodeInfo.getM_TDStructureReference();
                //tdStructureReference.setM_Name(tdStructureReference.getM_Name(), p_Path,p_TestObjectName);
            }
        }
        //grueda22082004_end
    }

    public void rearmedTDStructureReference(TestObject p_TestObject, TestObjectReference p_TestObjectReference) {
        try {
            TDStructureReference tdstructureReference =
                (TDStructureReference)p_TestObject.getTDSTructureReference().firstElement();
            tdstructureReference.setM_Name(tdstructureReference.getM_Name(), p_TestObjectReference.getPath(),
                p_TestObject.getName());
        }
        catch (Exception ex) {
        }
    }

    /////////////////////////////////////fin Harold Canedo Lopez////////////////////////////
    //grueda13092004_begin
    public ProjectReference getSelectedProjectReference() {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)this.getLastSelectedPathComponent();
        if (node != null) {
                if (node instanceof CMProjectNode) {
                    CMProjectNode temp = (CMProjectNode)node;
                    return temp.getProjectReference();
                }
                else {
                    return null;
                }
            }
            else {
                return null;
            }
        }

    public ProjectReference getCurrentProjectReference() {
        DefaultMutableTreeNode node = getSelectedNode();
        if (node == null) {
            return null;
        }
        while (node != m_RootNode && node != null) {
                if (node instanceof CMProjectNode) {
                    CMProjectNode temp = (CMProjectNode)node;
                    return temp.getProjectReference();
                }
                else {
                    node = (DefaultMutableTreeNode)node.getParent();
                }
            }
        return null;
    }

    public Project2 getCurrentProject() {
        DefaultMutableTreeNode node = getSelectedNode();
        if (node == null) {
            return null;
        }
        while (node != m_RootNode && node !=null ) {
                if (node instanceof CMProjectNode) {
                    CMProjectNode temp = (CMProjectNode)node;
                    return temp.getProject();
                }
                else {
                    node = (DefaultMutableTreeNode)node.getParent();
                }
            }

        return null;
    }

    public CMProjectNode getCurrentProjectNode() {
        DefaultMutableTreeNode node = getSelectedNode();
        if (node == null) {
            return null;
        }
        while (node != m_RootNode && node != null) {
                if (node instanceof CMProjectNode) {
                    return (CMProjectNode) node;
                }
                else {
                    node = (DefaultMutableTreeNode)node.getParent();
                }
            }


        return null;
    }

    public TestObjectReference getCurrentTestObjectReference() {
        DefaultMutableTreeNode node = getSelectedNode();
        if (node == null) {
            return null;
        }
        while (node != m_RootNode && node != null) {
            Object nodeInfo = node.getUserObject();
            if (nodeInfo != null) {
                if (nodeInfo instanceof CMTestObjectNode) {
                    CMTestObjectNode temp = (CMTestObjectNode)nodeInfo;
                    return temp.geTestObjectReference();
                }
                else {
                    node = (DefaultMutableTreeNode)node.getParent();
                }
            }
            else {
                return null;
            }
        }
        return null;
    }

    public TestObject getCurrentTestObject() {
        DefaultMutableTreeNode node = getSelectedNode();
        if (node == null) {
            return null;
        }
        while (node != m_RootNode && node != null) {
            Object nodeInfo = node.getUserObject();
            if (nodeInfo != null) {
                if (nodeInfo instanceof CMTestObjectNode) {
                    CMTestObjectNode temp = (CMTestObjectNode)nodeInfo;
                    return temp.getTestObject();
                }
                else {
                    node = (DefaultMutableTreeNode)node.getParent();
                }
            }
            else {
                return null;
            }
        }
        return null;
    }

    public DefaultMutableTreeNode getCurrentTestObjectNode() {
        DefaultMutableTreeNode node = getSelectedNode();
        if (node == null) {
            return null;
        }
        while (node != m_RootNode && node != null) {
            Object nodeInfo = node.getUserObject();
            if (nodeInfo != null) {
                if (nodeInfo instanceof CMTestObjectNode) {
                    return node;
                }
                else {
                    node = (DefaultMutableTreeNode)node.getParent();
                }
            }
            else {
                return null;
            }
        }
        return null;
    }

    //grueda31122004_begin
    public TDStructure getCurrentTestData(DefaultMutableTreeNode p_Node) {
        //DefaultMutableTreeNode node = getSelectedNode();
        DefaultMutableTreeNode node = p_Node;
        CMTestDataNode temp = null;
        if (node == null) {
            return null;
        }
        Object nodeInfo = null;
        while (node != null) {
            nodeInfo = node.getUserObject();
            if (nodeInfo != null) {
                if (!(nodeInfo instanceof CMTestDataNode)) {
                    node = (DefaultMutableTreeNode)node.getNextNode();
                }
                else {
                    temp = (CMTestDataNode)nodeInfo;
                    return temp.getM_TDStructure();
                }
            }
            else {
                return null;
            }
        }
        return null;
    }

    public String getCurrentBusinessRules(DefaultMutableTreeNode p_Node) {
        //DefaultMutableTreeNode node = getSelectedNode();
        DefaultMutableTreeNode node = p_Node;
        CMBusinessRulesNode temp = null;
        if (node == null) {
            return null;
        }
        Object nodeInfo = null;
        while (node != null) {
            nodeInfo = node.getUserObject();
            if (nodeInfo != null) {
                if (!(nodeInfo instanceof CMBusinessRulesNode)) {
                    node = (DefaultMutableTreeNode)node.getNextNode();
                }
                else {
                    temp = (CMBusinessRulesNode)nodeInfo;
                    return temp.getM_BusinessRules();
                }
            }
            else {
                return null;
            }
        }
        return null;
    }

    //grueda31122004_end
    //grueda13092004_end
    //HCanedo_22112004_Begin
    public boolean copyTestObjectInToProyect(File p_TestobjectFile) {
        try {
            String newName = null;
            TestObjectReference newTestObjectReference = null;
            boolean testObjectExistsIntoTheProject = false;
            DefaultMutableTreeNode selectedProjectReferenceNode = getSelectedNode();
            while (!testObjectExistsIntoTheProject) {
                File file = p_TestobjectFile;
                if (file != null) {
                    m_Frame.setWaitCursor(true);
                    String filePath = file.getPath().replace('\\','/');

                    TestObject openedTestObject = m_WorkspaceManager.readTestObject2(filePath);
                    if( openedTestObject == null){
                      return false;
                    }
                    //Ccastedo begins 03-02-06
//                    openedTestObject.setValidated(true);
//                    openedTestObject.setValidFlile(true);
            		//Ccastedo begins 03-02-06

                    String fileName = getFileNameWithouExtension(file.getName());
                    String testObjectName=openedTestObject.getName();
                    Project2 selectedProject = getSelectedProject2();
                    ProjectReference selectedProjectReference = getSelectedProjectReference();
                    //		if( !m_WorkspaceManager.existTestObjectReferenceInTheProject(filePath, selectedProject, selectedProjectReference) ) {
                    if (!m_WorkspaceManager.existTestObjectNameInTheProject(fileName, selectedProject)) {
                        newName = fileName;
                    }
                    else {
                        newName = generateCopyTestObjectName(selectedProject, fileName);
                    }
                    openedTestObject.setName(newName);
                    newTestObjectReference = m_WorkspaceManager.addTestObjectCloneToProject2(openedTestObject,
                        selectedProjectReference, selectedProject, m_Session2);
                    //grueda30122004_begin
                    if (!writeAddedTestObjectFromFile(openedTestObject, selectedProjectReference, selectedProject,
                        newTestObjectReference)) {
                            return false;
                    }
                    //grueda30122004_end

                    /////////////////////////////////////////////////////////////////////////////
                    newTestObjectReference.setM_ChangedName(newName);//ccastedo 24-04-06
              //      newTestObjectReference.setFileName(newName);//ccastedo 24-04-06
                    //////////////////////////////////////////////////////////////////////////////

                    String existingTestObjectAbsolutePath = file.getParent();
                    String existingTestDataFilePath = ((TDStructureReference)openedTestObject.getTDSTructureReference().firstElement()).getM_FilePath();
                    StringBuffer existingTestDataAbsoluteFilePath = new StringBuffer();
                    existingTestDataAbsoluteFilePath.append(existingTestObjectAbsolutePath);
                    existingTestDataAbsoluteFilePath.append(BusinessRules.URL_SEPARATOR);
                    existingTestDataAbsoluteFilePath.append(testObjectName);//openedTestObject.getM_Name());
                    existingTestDataAbsoluteFilePath.append(BusinessRules.URL_SEPARATOR);
                    existingTestDataAbsoluteFilePath.append(existingTestDataFilePath);
                    TDStructure newTDStructure = m_WorkspaceManager.readTDStructure(existingTestDataAbsoluteFilePath.toString()); // hacer
                    m_WorkspaceManager.getM_VersionManager().updateTestDataBasedOnVersion(newTDStructure,openedTestObject);
                    // TD file hay que abrir el td file
                    m_WorkspaceManager.addTDStructure(newTDStructure, openedTestObject, newTestObjectReference);
                    saveTDStructure(newTDStructure, selectedProjectReference);
                    m_WorkspaceManager.addTDStructure(newTDStructure, openedTestObject, newTestObjectReference);
                    BRulesReference businessRulesReference = openedTestObject.getBRulesReference();
                    if (businessRulesReference != null) {
                        StringBuffer existingBusinessRulesAbsoluteFilePath = new StringBuffer();
                        existingBusinessRulesAbsoluteFilePath.append(existingTestObjectAbsolutePath);
                        existingBusinessRulesAbsoluteFilePath.append(BusinessRules.URL_SEPARATOR);
                        existingBusinessRulesAbsoluteFilePath.append(testObjectName);//openedTestObject.getM_Name());
                        existingBusinessRulesAbsoluteFilePath.append(BusinessRules.URL_SEPARATOR);
                        existingBusinessRulesAbsoluteFilePath.append(businessRulesReference.getFilePath());
                        BREditorManager businessRulesEditorManager = new BREditorManager();
                        StringBuffer businessRulesAbsoluteFilePath = new StringBuffer();
                        businessRulesAbsoluteFilePath.append(selectedProjectReference.getPath());
                        businessRulesAbsoluteFilePath.append(BusinessRules.URL_SEPARATOR);
                        businessRulesAbsoluteFilePath.append(newTestObjectReference.getPath());
                        businessRulesAbsoluteFilePath.append(BusinessRules.URL_SEPARATOR);
                        businessRulesAbsoluteFilePath.append(openedTestObject.getName());
                        businessRulesAbsoluteFilePath.append(BusinessRules.URL_SEPARATOR);
                        businessRulesAbsoluteFilePath.append(businessRulesReference.getFileLocation());
                        File brPath = new File(businessRulesAbsoluteFilePath.toString());
                        brPath.mkdirs();
                        businessRulesAbsoluteFilePath.append(BusinessRules.URL_SEPARATOR);
                        businessRulesAbsoluteFilePath.append(businessRulesReference.getFileName());
                        File brFile = new File(existingBusinessRulesAbsoluteFilePath.toString());
                        String fileContent = businessRulesEditorManager.readContentFromFile(brFile);
                        businessRulesEditorManager.saveStringToFile(fileContent,
                            new File(businessRulesAbsoluteFilePath.toString()));
                    }
                    //grueda17122004_begin
                    DefaultMutableTreeNode newTestObjectReferenceNode =
                        createTestObjectNode2(newTestObjectReference, openedTestObject, selectedProjectReference, null);
                    //grueda17122004_end
                    m_TreeModel.insertNodeInto(newTestObjectReferenceNode, selectedProjectReferenceNode,
                        selectedProjectReferenceNode.getChildCount());
                    selectNode(newTestObjectReferenceNode);
                    testObjectExistsIntoTheProject = true;
                    if (m_Frame.isWaitCursorOn()) {
                        m_Frame.setWaitCursor(false);
                    }
                }
                else {
                    testObjectExistsIntoTheProject = true;
                }
            }
            return true;
        }
        catch (NullPointerException ex) {
            JOptionPane.showMessageDialog(m_Frame, CMMessages.getString("CHECK_CONECTION_TO_NET"),
                CMMessages.getString("TITLE_APPLICATION_NOT_FOUND"), JOptionPane.ERROR_MESSAGE);
            return false;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public String generateCopyTestObjectName(Project2 p_Project, String p_FileName) {
        String copy = CMMessages.getString("LABEL_TEST_OBJECT_COPY"); //$NON-NLS-1$
        String copyOf = CMMessages.getString("LABEL_TEST_OBJECT_COPY_OF"); //$NON-NLS-1$
        StringBuffer sBuffer = null;
        int numOfTestObjectReferences = p_Project.getTestObjectReferences().size();
        for (int i = 0; i < numOfTestObjectReferences; i++) {
            sBuffer = new StringBuffer();
            sBuffer.append(copy);
            sBuffer.append("_");
            sBuffer.append(i + 1);
            sBuffer.append("_");
            sBuffer.append(copyOf);
            sBuffer.append("_");
            sBuffer.append(p_FileName);
            if (!existTestObjectName(sBuffer.toString(), p_Project)) {
                return sBuffer.toString();
            }
        }
        sBuffer = new StringBuffer();
        sBuffer.append(copy);
        sBuffer.append("_");
        sBuffer.append(copyOf);
        sBuffer.append("_");
        sBuffer.append(p_FileName);
        return sBuffer.toString();
    }

    //HCanedo_22112004_End
    //hcanedo_09112004_end
    //grueda15122004_begin
    public void applyErrorNetworkResolutionForCurrentTestObject(Project2 p_Project,ProjectReference p_ProjectReference,
        TestObjectReference p_TestObjectReference, TestObject p_TestObject, DefaultMutableTreeNode p_Node) {
            //grueda10112004_begin
            //if( !m_WorkspaceManager.exists(p_ProjectReference.getM_FilePath())) {
            //  return false;
            //}
            //grueda10112004_end
            Object nodeInfo = p_Node.getUserObject();
            boolean entered = false;
            if (nodeInfo != null && nodeInfo instanceof CMTestObjectNode) {
                entered = ((CMTestObjectNode)nodeInfo).isTestObjectNodeWithData();

            }
            if (entered) {
                String absoluteLocalCaseMakerPath = m_WorkspaceManager.buildAbsoluteCaseMakerLocalDataPath();

	  /*p_TestObjectReference*/

                p_TestObject.setAccessState(BusinessRules.ACCESS_STATE_CHECKED_OUT_LOCAL);
                LocalProjectReference localProjectReference =
                    m_WorkspaceManager.buildLocalProjectReference(absoluteLocalCaseMakerPath, p_ProjectReference);
                p_ProjectReference.setM_LocalProjectReference(localProjectReference);
               // Project2 p_Project = p_TestObjectReference.getM_Project();
                m_WorkspaceManager.writeProjectPathToFile(localProjectReference.getPath());
                m_WorkspaceManager.writeProject2ToFile(p_Project, localProjectReference.getFilePath());
                overwriteTestObject2(p_Project, p_TestObject, p_TestObjectReference, localProjectReference);
                TDStructure existingTDStructure = getCurrentTestData(p_Node);
                saveTDStructure(existingTDStructure, localProjectReference);
                String existingBusinessRules = getCurrentBusinessRules(p_Node);
                BREditorManager businessRulesEditorManager = new BREditorManager();
                String targetBusinessRulesAbsolutePath =
                    m_WorkspaceManager.buildAbsoluteBRulesPath(localProjectReference, p_TestObjectReference);
                File targetPath = new File(targetBusinessRulesAbsolutePath);
                if (targetPath != null) {
                    targetPath.mkdirs();
                }
                String targetBusinessRulesAbsoluteFilePath =
                    m_WorkspaceManager.buildAbsoluteBRulesFilePath(localProjectReference, p_TestObjectReference);
                businessRulesEditorManager.saveStringToFile(existingBusinessRules,
                    new File(targetBusinessRulesAbsoluteFilePath));
                Session2 p_Session = SessionManager.getCurrentSession();//p_ProjectReference.getM_Workspace().getM_Session();
                m_SessionManager.writeSession2ToFile(p_Session);
                m_Frame.getPanelTestObjectView().setTestObject(p_Project, p_TestObject, p_TestObjectReference, p_Session,
                    p_ProjectReference, null);
                m_Frame.getPanelTestObjectView().getJRadioButtonLocalCheckOut().setSelected(true);
                m_Frame.getPanelTestObjectView().updateAccessViewsStates();
                m_Frame.setStateTestObjectLocalCheckedOut();
                //grueda15122004_end
            }
    }






    //grueda10022005_end
    public void setProjectReferenceTabTestObjectAfterCheckinLocal() {
        ProjectReference currentProjectReference = getCurrentProjectReference();
        m_Frame.setWaitCursor(true); //fcastro_20092004
        TreePath currentSelection = getSelectionPath();
        DefaultMutableTreeNode node = null;
        if (currentSelection != null) {
            node = (DefaultMutableTreeNode)(currentSelection.getLastPathComponent());
        }
        if (node == null) {
            m_Frame.setWaitCursor(false); //fcastro_20092004
            return;
        }
        Object nodeInfo = node.getUserObject();
        if (nodeInfo != null) {
            if (nodeInfo instanceof CMTestObjectNode) {
                CMTestObjectNode temp = (CMTestObjectNode)nodeInfo;
                selectTestObjectTabbedPane(temp.getPanelTestObject(), temp.getTestObject(), temp.geTestObjectReference(),
                    currentProjectReference, temp.getLocalTestObjectReference());
            }
        }
    }
    public void saveCurrentProyect(){

    	try {
			DefaultMutableTreeNode currentProyectNode=getCurrentProjectNode();
			CMProjectNode currentProjectnodeInfo=(CMProjectNode)currentProyectNode;
			saveCurrentSelectedProjectInTheTree(currentProjectnodeInfo,currentProyectNode);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

	/**
	*@autor smoreno
	 */
	public void selectRootNode() {
		this.selectNode(this.getM_RootNode());

	}
//	 return only paths of nodes that are expanded.
	 public TreePath[] getVisiblePaths() {
	        TreeNode root = (TreeNode)this.getModel().getRoot();

	        // Create array to hold the treepaths
	        List list = new ArrayList();

	        // Traverse tree from root adding treepaths for all nodes to list
	        getPaths(this, new TreePath(root), list);

	        // Convert list to array
	        return (TreePath[])list.toArray(new TreePath[list.size()]);
	    }

	    public void getPaths(JTree tree, TreePath parent, List list) {
	        // Return if node is not expanded
	        if (!tree.isVisible(parent)) {
	            return;
	        }

	        // Add node to list
	        list.add(parent);

	        // Create paths for all children
	        TreeNode node = (TreeNode)parent.getLastPathComponent();
	        if (node.getChildCount() >0) {
	            for (Enumeration e=node.children(); e.hasMoreElements(); ) {
	                TreeNode n = (TreeNode)e.nextElement();
	                TreePath path = parent.pathByAddingChild(n);
	                getPaths(tree, path, list);

	            }
	        }
	    }
		/**
		*@autor smoreno
		 * @param p_workspace
		 * @return
		 */
		public TreePath getTreePath(Object p_object) {
			return new TreePath(this.m_RootNode.getNode(p_object).getPath());
		}
}

