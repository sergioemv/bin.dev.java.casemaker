/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.view.treeviews.nodes;

import java.awt.Cursor;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.ExpandVetoException;
import javax.swing.tree.TreePath;

import bi.controller.SessionManager;
import bi.controller.WorkspaceManager;
import bi.controller.utils.CMRawFileObjectReader;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.projectviews.CMPanelProjectView;
import bi.view.treeviews.CMTreeWorkspaceView;
import model.CMField;
import model.Project2;
import model.ProjectReference;
import model.TestObject;
import model.TestObjectReference;
import model.util.CMModelEvent;
import model.util.CMModelListener;

public class CMProjectNode extends CMNode implements CMModelListener, TreeSelectionListener, TreeWillExpandListener{
private ProjectReference projectReference = null;
private CMPanelProjectView panelProject;
  public CMProjectNode(CMTreeWorkspaceView tree,ProjectReference p_ProjectReference){
	  super(tree);
	  projectReference = p_ProjectReference;
	  this.setUserObject(this);
	  projectReference.addModelListener(this);
	  tree.addTreeSelectionListener(this);
	  tree.addTreeWillExpandListener(this);
  }
  public ProjectReference getProjectReference() {
    return projectReference;
  }
  public void setM_ProjectReference(ProjectReference p_ProjectReference) {
	  if (projectReference!=null)
		  projectReference.removeModelListener(this);

    projectReference = p_ProjectReference;
    projectReference.addModelListener(this);

  }

  public String toString(){
    return projectReference.getName();
  }
/* (non-Javadoc)
 * @see bi.view.treeviews.nodes.CMNode#getCMModel()
 */
@Override
public Object getCMModel() {
	return getProjectReference();
}
/* (non-Javadoc)
 * @see bi.view.treeviews.nodes.CMNode#move(int)
 */
@Override
public void move(int p_index) {
	// TODO Auto-generated method stub

}
/* (non-Javadoc)
 * @see model.util.CMModelListener#handleCMModelChange(model.util.CMModelEvent)
 */
public void handleCMModelChange(CMModelEvent p_evt) {
	if (p_evt.getSource() instanceof ProjectReference)
	{
		if (p_evt.getChangedField() == CMField.PATH)
		{
			this.setUserObject(this.toString());
			this.getTree().treeDidChange();
		}
	}
}
/**
*@autor smoreno
 * @return
 */
public Project2 getProject() {
	return getProjectReference().getProject();
}
/**
*@autor smoreno
 * @return
 */
public Project2 getLocalProject() {
	return getProjectReference().getLocalProject();
}
/* (non-Javadoc)
 * @see javax.swing.event.TreeSelectionListener#valueChanged(javax.swing.event.TreeSelectionEvent)
 */
public void valueChanged(TreeSelectionEvent p_e) {
	//if the selection has changed

	if (p_e.getNewLeadSelectionPath()!=null&&(p_e.getNewLeadSelectionPath()!=p_e.getOldLeadSelectionPath()))
		if (p_e.getNewLeadSelectionPath().getLastPathComponent().equals(this))
		{
			CMApplication.frame.setCursor(new Cursor(Cursor.WAIT_CURSOR));
			getProjectReference().reloadProject();

        	if (getTree().isFileWrong(this)||this.getProject()==null){
        	 		StringBuffer absoluteFilePath = new StringBuffer();
        	 		absoluteFilePath.append(getProjectReference().getFilePath());
        	 		getTree().showValidationMessage(1,absoluteFilePath.toString());
        	 		getTree().selectNode((DefaultMutableTreeNode) this.getParent());
        	 		return;
        	}
			//add the project tab
			CMApplication.frame.addProjectTab(getPanelProject());
			CMApplication.frame.getTabbedPane().setEnabled(true);
			//CMApplication.frame.setStateProjectSelected();
			CMApplication.frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
}
/**
*@autor smoreno
 * @return
 */
private CMPanelProjectView getPanelProject() {
	// TODO Auto-generated method stub
	if (panelProject == null)
	{
		panelProject = new CMPanelProjectView();
		panelProject.setM_Project2(this.getProject(),this.getProjectReference());
	}
	CMApplication.frame.initializeTheUndoManager();//svonborries_30072007
	return this.panelProject;
}
public void treeWillCollapse(TreeExpansionEvent event) throws ExpandVetoException {


}
public void treeWillExpand(TreeExpansionEvent event) throws ExpandVetoException {
	TreePath path = event.getPath();
    DefaultMutableTreeNode node = (DefaultMutableTreeNode)path.getLastPathComponent();
    if (node!=null && node.equals(this))
    {
    	 int numOfTestObjectNodes = getChildCount();
          CMTestObjectNode testObjectNode = null;
          CMApplication.frame.setWaitCursor(true);
         for (int i = 0; i < numOfTestObjectNodes; i++) {
             testObjectNode = (CMTestObjectNode)getChildAt(i);
             testObjectNode.addChildrenNodes();
         }
         CMApplication.frame.setWaitCursor(false);
    }

}
/**
 * Adds the Test Object nodes that belongs to this project
 */
public void addChildrenNodes() {
	//invalid test object references
    Vector invalidTestObjectReferences = new Vector(0);
    int numOfLocalTestObjectReferences = 0;
    if (getProjectReference() != null) {
        Project2 project = getProject();
        Project2 localProject = getLocalProject();
        if (project != null) {
            int numOfTestObjectReferences = project.getTestObjectReferences().size();
            if (localProject != null) {
                numOfLocalTestObjectReferences = localProject.getTestObjectReferences().size();
            }
            TestObjectReference testObjectReference = null;
            TestObjectReference localTestObjectReference = null;
            CMTestObjectNode testObjectNodeInfo = null;
            //obtain the total number of project references
            for (int i = 0; i < numOfTestObjectReferences; i++) {
                testObjectReference = (TestObjectReference)project.getTestObjectReferences().elementAt(i);
                //if there are not a child node of the same test object reference
                if (!isTestObjectNodeChild(testObjectReference)) {
                	if (i < numOfLocalTestObjectReferences) {
                    	//obtain the local test object reference if exist
                        localTestObjectReference = localProject.getTestObjectReferences().elementAt(i);
                    }
                    else {
                        localTestObjectReference = null;
                    }
                    //creates three temporal test objects
                  TestObject tempTestObject = CMRawFileObjectReader.getInstance().readTestObject(testObjectReference, getProjectReference());
                  TestObject localTempTestObject = CMRawFileObjectReader.getInstance().readTestObject(testObjectReference, getProjectReference());

                    TestObjectReference tempTestObjectReference = null;
                    //if is checked out LOCAL or the local TestObject is checked out local
                    if (WorkspaceManager.getInstance().isTestObjectCheckedOutLocalByTheSameUser(tempTestObject, SessionManager.getCurrentSession())
                        || WorkspaceManager.getInstance().isTestObjectReferenceCheckOutLocal(localTempTestObject)) {
                    	//choose
                            if (WorkspaceManager.getInstance().isTestObjectReferenceCheckOutLocal(localTempTestObject)) {
                                    tempTestObjectReference = localTestObjectReference;
                            }
                            else {
                                tempTestObjectReference = testObjectReference;
                            }
                            TestObject tempTestObjectX =   CMRawFileObjectReader.getInstance().readTestObject(tempTestObjectReference, getProjectReference());

                            if (tempTestObjectX != null) {
                            	//create the node
                            	testObjectNodeInfo=new CMTestObjectNode(this.getTree(), testObjectReference, tempTestObjectX, localTestObjectReference);
                            	testObjectNodeInfo.setIsTempTestObject(true);
                            	//testObjectReferenceNode = new DefaultMutableTreeNode(testObjectNodeInfo);
                            }
                    }//if is not check out local
                    else {
                    	TestObject tempTestObjectX =   CMRawFileObjectReader.getInstance().readTestObject(testObjectReference, getProjectReference());

                        if (tempTestObjectX != null) {
                               testObjectNodeInfo=new CMTestObjectNode(this.getTree(), testObjectReference, tempTestObjectX, localTestObjectReference);
                              testObjectNodeInfo.setIsTempTestObject(true);
							  //testObjectReferenceNode = new DefaultMutableTreeNode(testObjectNodeInfo);
                        }
                    }
                    if (testObjectNodeInfo != null) {
                    	add(testObjectNodeInfo);
                    }
                    else {
                        //grueda04112004_begin
                        //invalidTestObjectReferences.addElement(testObjectReference);
                        //grueda04112004_end
                    }
                } //if
            } //for
        }
        showMessageOnInvalidTestObjectReferences(invalidTestObjectReferences);
    }
}

/**
 * Checks if the node has  a node with the name of the test object reference
 * @param p_TestObjectReference
 * @return
 */
private boolean isTestObjectNodeChild(TestObjectReference p_TestObjectReference) {
            int numOfChildren = getChildCount();
            CMTestObjectNode testObjectNodeInfo = null;
            for (int i = 0; i < numOfChildren; i++) {
                DefaultMutableTreeNode testObjectNode = (DefaultMutableTreeNode)getChildAt(i);
                testObjectNodeInfo = (CMTestObjectNode)testObjectNode.getUserObject();
                if (testObjectNodeInfo != null) {
                    TestObjectReference testObjectReference1 = testObjectNodeInfo.geTestObjectReference();
                    if (testObjectReference1 != null && p_TestObjectReference != null) {
                        if (testObjectReference1.getName().equals(p_TestObjectReference.getName())) {
                            return true;
                        }
                    }
                }
            }
            return false;
    }


public void showMessageOnInvalidTestObjectReferences(Vector p_InvalidTestObjectReferences) {
    TestObjectReference testObjectReference = null;
    int numOfInvalidTestObjectReferences = p_InvalidTestObjectReferences.size();
    if (numOfInvalidTestObjectReferences > 0) {
        StringBuffer sBuffer = new StringBuffer();
        for (int i = 0; i < numOfInvalidTestObjectReferences; i++) {
            testObjectReference = (TestObjectReference)p_InvalidTestObjectReferences.elementAt(i);
            sBuffer.append(testObjectReference.getFilePath());
            sBuffer.append("\n"); //$NON-NLS-1$
        }
        Object[] arguments = {
		  sBuffer.toString()
		};
        String message = java.text.MessageFormat.format(CMMessages.getString("INFO_TEST_OBJECT_NOT_FOUND"), arguments); //$NON-NLS-1$
        JOptionPane.showMessageDialog(CMApplication.frame, message);
    }
}
}


