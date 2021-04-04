/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.view.treeviews.nodes;

import java.io.File;
import java.util.Locale;

import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.ExpandVetoException;
import javax.swing.tree.TreePath;

import model.BusinessRules;
import model.CMField;
import model.ProjectReference;
import model.Session2;
import model.TestObject;
import model.TestObjectReference;
import model.util.CMModelEvent;
import model.util.CMModelListener;

import org.apache.log4j.Logger;

import bi.controller.SessionManager;
import bi.controller.WorkspaceManager;
import bi.controller.utils.CMBaseObjectReader;
import bi.controller.utils.CMXMLFileState;
import bi.view.mainframeviews.CMApplication;
import bi.view.testobjectviews.CMPanelTestObjectView;
import bi.view.treeviews.CMTreeWorkspaceView;
import bi.view.utils.CMFormatFactory;

public class CMTestObjectNode extends CMNode implements CMModelListener, TreeSelectionListener, TreeWillExpandListener{
  public CMTestObjectNode(CMTreeWorkspaceView tree, TestObjectReference p_TestObjectReference, TestObject p_TestObject, TestObjectReference p_LocalTestObjectReference){
	super(tree);
	m_TestObjectReference = p_TestObjectReference;
    m_LocalTestObjectReference = p_LocalTestObjectReference;
    m_TestObject = p_TestObject;
    this.setUserObject(this);
    m_TestObjectReference.addModelListener(this);
    tree.addTreeSelectionListener(this);
    tree.addTreeWillExpandListener(this);
  }
  public TestObjectReference geTestObjectReference(){
    return m_TestObjectReference;
  }
  public void setM_TestObjectReference(TestObjectReference p_TestObjectReference) {
    m_TestObjectReference = p_TestObjectReference;
    m_TestObjectReference.addModelListener(this);
  }

  public TestObjectReference getLocalTestObjectReference(){
    return m_LocalTestObjectReference;
  }
  public void setM_LocalTestObjectReference(TestObjectReference p_TestObjectReference) {
	  if (m_LocalTestObjectReference!=null)
	    	m_LocalTestObjectReference.removeModelListener(this);
    m_LocalTestObjectReference = p_TestObjectReference;
    if (m_LocalTestObjectReference!=null)
    	m_LocalTestObjectReference.addModelListener(this);


  }


  public void setM_TestObject(TestObject p_TestObject) {
    m_TestObject = p_TestObject;
  }
  public TestObject getTestObject() {
    return m_TestObject;
  }
  public String toString() {
    if( m_TestObject != null) {
      return m_TestObject.getName();
    }
    else {
      return m_TestObjectReference.getName();
    }
  }

  public boolean isIsTempTestObject(){ return isTempTestObject; }

  public void setIsTempTestObject(boolean isTempTestObject){ this.isTempTestObject = isTempTestObject; }
  public boolean isTestObjectNodeWithData() {
        return isTestObjectNodeWithData;
    }

    public void setIsTestObjectNodeWithData(boolean p_value) {
        if (!isTestObjectNodeWithData) {
            isTestObjectNodeWithData = p_value;
        }
        Logger.getLogger(this.getClass()).debug(isTestObjectNodeWithData);
    }

  boolean isTestObjectNodeWithData = false;
  private TestObjectReference m_TestObjectReference = null;
  private TestObject m_TestObject = null;
  private TestObjectReference m_LocalTestObjectReference = null;
  private boolean isTempTestObject=false;


  private CMPanelTestObjectView panelTestObject;

  public void handleCMModelChange(CMModelEvent p_evt) {
		if (p_evt.getSource() instanceof TestObjectReference)
		{
			if (p_evt.getChangedField() == CMField.PATH)
			{
				this.setUserObject(this.toString());
				this.getTree().treeDidChange();
			}
		}
	}

  public void valueChanged(TreeSelectionEvent p_e) {
	  //getM_TestObjectReference().reloadTestObject();

	}

  public CMPanelTestObjectView getPanelTestObject() {
		// TODO Auto-generated method stub
		if (panelTestObject == null)
		{
			panelTestObject = new CMPanelTestObjectView();
			panelTestObject.setTestObject(this.getTestObject());
		}
		return this.panelTestObject;
	}

  /* (non-Javadoc)
   * @see bi.view.treeviews.nodes.CMNode#getCMModel()
   */
  @Override
  public Object getCMModel() {
  	return geTestObjectReference();
  }
  /* (non-Javadoc)
   * @see bi.view.treeviews.nodes.CMNode#move(int)
   */
  @Override
  public void move(int p_index) {
  	// TODO Auto-generated method stub

  }

  public TestObject getLocalTestObject() {
		return geTestObjectReference().getLocalTestObject();
	}

  public void addChildrenNodes() {
	    if (getChildCount() == 0) {
	        DefaultMutableTreeNode testObjectBusinessRulesNode =
	            new DefaultMutableTreeNode(new CMBusinessRulesNode(null, getTestObject(), null, null));
	        DefaultMutableTreeNode testObjectTestCasesNode =
	            new DefaultMutableTreeNode(new CMTestCasesNode(null, getTestObject()));
	        DefaultMutableTreeNode testObjectTestDataNode =
	            new DefaultMutableTreeNode(new CMTestDataNode(null, getTestObject(), null, null));
	        DefaultMutableTreeNode testObjectResultComparationNode =
	            new DefaultMutableTreeNode(new CMResultsComparationNode(null, getTestObject(), null, null));
	        DefaultMutableTreeNode testObjectTestManagement =
	            new DefaultMutableTreeNode(new CMTestManagementNode(null, getTestObject()));
	        add(testObjectBusinessRulesNode);
	        add(testObjectTestCasesNode);
	        add(testObjectTestDataNode);
	        add(testObjectResultComparationNode);
	        add(testObjectTestManagement);
	    }
  }
public void treeWillCollapse(TreeExpansionEvent event) throws ExpandVetoException {
	// TODO Auto-generated method stub

}
public void treeWillExpand(TreeExpansionEvent event) throws ExpandVetoException {

	TreePath path = event.getPath();
    DefaultMutableTreeNode node = (DefaultMutableTreeNode)path.getLastPathComponent();
    if (node!=null && node.equals(this))
    {
        CMApplication.frame.setWaitCursor(true);
        DefaultMutableTreeNode projectNode = (DefaultMutableTreeNode)node.getParent();
        CMProjectNode projectNodeInfo = (CMProjectNode)projectNode;
        ProjectReference currentProjectReference = projectNodeInfo.getProjectReference();
        if (WorkspaceManager.getInstance().isTestObjectCheckedOutLocalByTheSameUser( getTestObject(), SessionManager.getCurrentSession())
            || WorkspaceManager.getInstance().isTestObjectReferenceCheckOutLocal(getTestObject())) {
                currentProjectReference = currentProjectReference.getM_LocalProjectReference();
        }

        //Ccastedo begins 16-03-06
         StringBuffer l_AbsoluteFilePath = new StringBuffer();
 		 l_AbsoluteFilePath.append(currentProjectReference.getPath());
 		 l_AbsoluteFilePath.append(BusinessRules.URL_SEPARATOR);
 		 l_AbsoluteFilePath.append(geTestObjectReference().getFilePath());

 	   long l_maxSizeofFile = (long) ((CMApplication.frame.getCmApplication().getSessionManager().getApplicationSettingManager().getApplicationSetting().getM_MaxSizeofXMLFiles()) *1024 * 1024);
       if (CMBaseObjectReader.fileSize(new File(l_AbsoluteFilePath.toString()))> l_maxSizeofFile)
       	CMBaseObjectReader.setReadFileState(l_AbsoluteFilePath.toString(),CMXMLFileState.BIGGERFILE);
       else{
       	if (CMBaseObjectReader.getReadFileState(l_AbsoluteFilePath.toString()) == CMXMLFileState.BIGGERFILE)
       		CMBaseObjectReader.setReadFileState(l_AbsoluteFilePath.toString(),CMXMLFileState.VALID);
       }

 	  if (CMBaseObjectReader.getReadFileState(l_AbsoluteFilePath.toString()) == CMXMLFileState.NEWVERSION)
		  {
 			getTree().showValidationMessage(2,l_AbsoluteFilePath.toString());
 			CMApplication.frame.setWaitCursor(false);
 			throw new ExpandVetoException(event);
		  }
     else
       	if (CMBaseObjectReader.getReadFileState(l_AbsoluteFilePath.toString()) == CMXMLFileState.CORRUPTEDXML){
       		getTree().showValidationMessage(2,l_AbsoluteFilePath.toString());
       		CMApplication.frame.setWaitCursor(false);
       		throw new ExpandVetoException(event);
         	}
       	else{
       		if (CMBaseObjectReader.getReadFileState(l_AbsoluteFilePath.toString()) == CMXMLFileState.BIGGERFILE){
       			getTree().showValidationMessage(2,l_AbsoluteFilePath.toString());
       			CMApplication.frame.setWaitCursor(false);
       			throw new ExpandVetoException(event);
       		}
       	}
        //Ccastedo ends 16-03-06

        TestObject testObject = getTestObject();
        if (testObject != null) {
            if ((testObject.getTDSTructureReference().size() == 0) ||
                (!WorkspaceManager.getInstance().isTestObjectCheckedOutByTheSameUser( getTestObject(), SessionManager.getCurrentSession())
                && !WorkspaceManager.getInstance().isTestObjectCheckedOutLocalByTheSameUser( getTestObject(), SessionManager.getCurrentSession()))) {

            	boolean isrebuild = rebuildTestObjectNode(node, geTestObjectReference(),
                        currentProjectReference, SessionManager.getCurrentSession());
                    setIsTestObjectNodeWithData(isrebuild);
                    if (!isTestObjectNodeWithData()) {
                    	getTree().showValidationMessage(2,l_AbsoluteFilePath.toString());
                        CMApplication.frame.setWaitCursor(false);
                        throw new ExpandVetoException(event);
                    }
            }
            try {
				if(getTestObject().getDecimalSeparator().equals(",")){
					CMFormatFactory.setTestObjectLocale(new Locale("de","DE"));
				}
				else{
					CMFormatFactory.setTestObjectLocale(new Locale("en", "US"));
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
        }
    }
    CMApplication.frame.setWaitCursor(false);
    }
public boolean rebuildTestObjectNode(DefaultMutableTreeNode p_TestObjectNode, TestObjectReference p_TestObjectReference,
        ProjectReference p_ProjectReference, Session2 p_Session) {
            TestObject testObject = getTree().reloadTestObject(p_TestObjectReference, p_ProjectReference, p_Session);
            //Ccastedo begins 24-01-06
            if (testObject == null){
            	return false;
            }
            //Ccastedo ends 24-01-06

            TestObjectReference testObjectReference =
                WorkspaceManager.getInstance().reloadTestObjectReferenceFromProject(p_ProjectReference, p_TestObjectReference);
            CMTestObjectNode testObjectNodeInfo = (CMTestObjectNode)p_TestObjectNode.getUserObject();
            if (testObject == null || testObjectReference == null) {
                return false;
            }
            testObjectNodeInfo.setM_TestObjectReference(testObjectReference);
            testObjectNodeInfo.setM_TestObject(testObject);
            if (testObject != null) {
                getTree().reloadChildrenOfTestObjectNode(p_TestObjectNode, testObjectReference, testObject, p_ProjectReference);
                return true;
            }
            else {
                return false;
            }
    }

}