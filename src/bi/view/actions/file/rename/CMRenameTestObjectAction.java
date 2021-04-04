/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.view.actions.file.rename;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.Action;
import javax.swing.JOptionPane;

import model.BusinessRules;
import model.Project2;
import model.TestObject;
import model.TestObjectReference;
import bi.controller.SessionManager;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.treeviews.nodes.CMProjectNode;
import bi.view.treeviews.nodes.CMTestObjectNode;
import bi.view.utils.CMModalResult;

/**
 * @author smoreno
 *
 */
public class CMRenameTestObjectAction extends CMRenameAction implements Action {


	public void actionPerformed(ActionEvent p_e) {
	//	boolean isValidTestObjectName = true;
		CMTestObjectNode testObjectNodeInfo = CMApplication.frame.getTreeWorkspaceView().getSelectedTestObjectNodeInfo();
		if( testObjectNodeInfo != null){

	    	 if (!validatePreconditions(testObjectNodeInfo))
	    		 return;
	    	  final TestObjectReference testObjectReference = testObjectNodeInfo.geTestObjectReference();
			  final TestObject testObject = testObjectNodeInfo.getTestObject();
			  CMDialogNameChange cmd= new CMDialogNameChange();
			  cmd.getJTextFieldName().setText(testObject.getName());
			  cmd.getJTextFieldName().selectAll();

			  String newName = "";
			  do{
				  cmd.setVisible(true);
				  if (cmd.getModalResult() == CMModalResult.OK){
					  newName = cmd.getJTextFieldName().getText();
						  if (isValidName(newName)){
							   CMApplication.frame.getPanelTestObjectView().changeValidName(newName);
							   testObjectReference.setM_ChangedName(newName);
							   CMApplication.frame.getTreeWorkspaceView().save2();							   
							   CMApplication.frame.getPanelTestObjectView().reloadAfterChangeName();
							  return;
						  }
						  cmd.setVisible(false);
						   testObjectReference.reloadTestObject();
						  return;
					//  }

				  } else
					  return;
			  }while (!isValidName(newName));
		}

	}
	private boolean validatePreconditions(CMTestObjectNode testObjectNodeInfo) {
		//preconditions to rename
		if (!testObjectNodeInfo.getTestObject().getAccessState().equals(BusinessRules.ACCESS_STATE_CHECKED_OUT)){
			JOptionPane.showMessageDialog(CMApplication.frame, CMMessages.getString("RENAME_TESTOBJECT_NOT_CHECKED_OUT"));
			return false;
		}
		if(!testObjectNodeInfo.getTestObject().getUser().equalsIgnoreCase(SessionManager.getCurrentSession().getM_User()))
		{
			JOptionPane.showMessageDialog(CMApplication.frame, CMMessages.getString("RENAME_TESTOBJECT_CHECKED_OUT_DIFFERENT_USER"));
			return false;
		}

		return true;
	}
	/* (non-Javadoc)
	 * @see bi.view.actions.file.rename.CMRenameAction#isValidName(java.lang.String)
	 */
	@Override
	protected boolean isValidName(String p_newName) {
		return super.isValidName(p_newName)&&!existTestObjectName(p_newName);
	}

	 private boolean existTestObjectName(String p_Name){


	        Project2 project= CMApplication.frame.getTreeWorkspaceView().getCurrentProject();
	        if (!CMApplication.frame.getPanelTestObjectView().validateName(p_Name,project))
	        {
	        	JOptionPane.showMessageDialog(CMApplication.frame, CMMessages.getString("MESSAGE_ERROR_NAME_TESTOBJECT_REPEAT"), CMMessages.getString("TITLE_MESSAGE_ERROR_NAME"), JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$ //$NON-NLS-2$
	        	return true;
	        }
	        return false;
	    }

	/* (non-Javadoc)
	 * @see bi.view.actions.CMEnabledAction#calculateEnabled()
	 */
	public boolean calculateEnabled() {
		CMTestObjectNode testObjectNodeInfo = CMApplication.frame.getTreeWorkspaceView().getSelectedTestObjectNodeInfo();
		CMProjectNode projectNode = (CMProjectNode) CMApplication.frame.getTreeWorkspaceView().getCurrentProjectNode();

		if (testObjectNodeInfo!=null && projectNode!=null)
		{
			//if the test object reference can be readed

			boolean fileExist = ((new File(projectNode.getProjectReference().getPath()+
					BusinessRules.URL_SEPARATOR+
					testObjectNodeInfo.geTestObjectReference().getFilePath())).exists());

			boolean testObjectCHOutSameUser = testObjectNodeInfo.getTestObject().getAccessState().equals(BusinessRules.ACCESS_STATE_CHECKED_OUT) &&
			testObjectNodeInfo.getTestObject().getUser().equalsIgnoreCase(SessionManager.getCurrentSession().getM_User());

			boolean projectChOutSameUser = projectNode.getProject().getAccessState().equals(BusinessRules.ACCESS_STATE_CHECKED_OUT)&&
			projectNode.getProject().getUser().equalsIgnoreCase(SessionManager.getCurrentSession().getM_User());

			return testObjectCHOutSameUser&&fileExist&&projectChOutSameUser;

		}
		else
		{

			return false;
		}


	}


}
