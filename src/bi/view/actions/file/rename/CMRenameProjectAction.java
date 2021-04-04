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
import model.ProjectReference;
import model.edit.CMModelEditFactory;
import model.util.CMDelegate;
import bi.controller.ProjectManager;
import bi.controller.SessionManager;
import bi.controller.WorkspaceManager;
import bi.view.actions.CMAction;
import bi.view.actions.CMEnabledAction;
import bi.view.edit.CMViewEditFactory;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.treeviews.nodes.CMProjectNode;
import bi.view.undomanagementviews.CMCompoundEdit;
import bi.view.undomanagementviews.undoviews.CMUndoMediator;
import bi.view.utils.CMModalResult;

/**
 * @author smoreno
 *
 */
public class CMRenameProjectAction extends CMRenameAction {
/**
 * 
 */
public CMRenameProjectAction() {
	super();
	putValue(Action.NAME, CMMessages.getString("RENAME_PROJECT_ACTION_NAME"));
}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent p_e) {
		CMProjectNode projectNodeInfo = CMApplication.frame.getTreeWorkspaceView().getSelectedProjectNodeInfo();
	    if( projectNodeInfo != null) {
	    	 if (!validatePreconditions(projectNodeInfo))
				   return;
			  final ProjectReference projectReference = projectNodeInfo.getProjectReference();
			  final Project2 project = projectNodeInfo.getProject();
			  CMDialogNameChange cmd= new CMDialogNameChange();
			  cmd.getJTextFieldName().setText(project.getName());
			  cmd.getJTextFieldName().selectAll();
			  String newName = "";
			  do{
				  cmd.setVisible(true);
				  if (cmd.getModalResult() == CMModalResult.OK){
					  newName = cmd.getJTextFieldName().getText();
					  if (isValidName(newName)){
						  CMCompoundEdit ce = new CMCompoundEdit();
					
						  ce.addEdit(CMViewEditFactory.INSTANCE.createTreeSelectNodeEdit(CMApplication.frame.getTreeWorkspaceView().getTreePath(projectReference)));
						  //rename the old path to the new
						  String newPath = createNewPath(newName,projectReference.getPath());
						  //delete the file of the project reference and set a new filename
						 ce.addEdit(CMModelEditFactory.INSTANCE.createChangeFilePathModelEdit(projectReference,newName,newPath,project,
								  new CMDelegate(){
									public void execute() {
										projectReference.setM_LocalProjectReference(null);
										ProjectManager.getInstance().writeProject2(project, projectReference);
										projectReference.reloadProject();
									}}));
						 if (new File(newPath).exists())
						 {
							 JOptionPane.showMessageDialog(CMApplication.frame, CMMessages.getString("RENAME_PROJECT_ERROR_FILE_ALREADY_EXIST"));
						  return;
						 }
						  deleteFile(projectReference.getFilePath());
						  renameDir(projectReference.getPath(),newPath);
						  deleteDir(new File(projectReference.getM_LocalProjectReference().getPath()));
						  projectReference.setPath(newPath);
						  projectReference.setFileName(newName);
						  project.setName(newName);
						  //delete also the local reference of the project						  
						  projectReference.setM_LocalProjectReference(null);
						  ProjectManager.getInstance().writeProject2(project, projectReference);
						  projectReference.reloadProject();
						 CMUndoMediator.getInstance().doEdit(ce);
						  return;
					  }
					  cmd.setVisible(false);
					  return;
				  } else
					  return;
			  }while (!isValidName(newName));
	    }//if projectNode !=null
	}
/**
	*@autor smoreno
	 * @param p_path
	 * @param p_newP
	 */
	private static boolean renameDir(String p_oldPath, String p_newPath) {
		File oldFile = new File(p_oldPath);
		File newFile = new File(p_newPath);
		if (newFile.exists())
		//	if  (!deleteDir(newFile))
				return false;
	   if (newFile != null)
		   return oldFile.renameTo(newFile);
	   return false;

	}
	 public static boolean deleteDir(File dir) {
	        if (dir.isDirectory()) {
	            String[] children = dir.list();
	            for (int i=0; i<children.length; i++) {
	                boolean success = deleteDir(new File(dir, children[i]));
	                if (!success) {
	                    return false;
	                }
	            }
	        }
	    
	        // The directory is now empty so delete it
	        return dir.delete();
	    }
/**
	*@autor smoreno
	 * @param p_newName
 * @param p_OldName 
	 * @return
	 */
	private String createNewPath(String p_newName, String p_OldPath) {
		 // Rename existing project path
	      File oldFile = new File(p_OldPath);
	      StringBuffer parentPath = new StringBuffer();
	      if( oldFile != null) {
	        File parentFile = oldFile.getParentFile();
	        if( parentFile != null) {
	            parentPath.append(WorkspaceManager.getInstance().findCorrectPath(parentFile));
				parentPath.append(BusinessRules.URL_SEPARATOR); //$NON-NLS-1$
				parentPath.append(p_newName);
	        }
	      }
		return parentPath.toString();
	}
/**
	*@autor smoreno
	 * @param p_projectNodeInfo
	 * @return
	 */
	private boolean validatePreconditions(CMProjectNode p_projectNodeInfo) {
		if (!p_projectNodeInfo.getProject().getAccessState().equals(BusinessRules.ACCESS_STATE_CHECKED_OUT)){
			JOptionPane.showMessageDialog(CMApplication.frame, CMMessages.getString("RENAME_PROJECT_NOT_CHECKED_OUT"));
			return false;
		}
		if(!p_projectNodeInfo.getProject().getUser().equalsIgnoreCase(SessionManager.getCurrentSession().getM_User()))
		{
			JOptionPane.showMessageDialog(CMApplication.frame, CMMessages.getString("RENAME_PROJECT_CHECKED_OUT_DIFFERENT_USER"));
			return false;
		}	
	     if (! new File(p_projectNodeInfo.getProjectReference().getFilePath()).exists()) {
	       JOptionPane.showMessageDialog(CMApplication.frame, CMMessages.getString("RENAME_PROJECT_FILE_NOT_PRESENT"));
	       return false;
	                }
		return true;
	}
/**
	*@autor smoreno
	 * @param p_filePath
	 */
	private void deleteFile(String p_filePath) {
	    File file = new File(p_filePath);
	      if( file != null) {
	        file.delete();
	      }
		
	}
/* (non-Javadoc)
 * @see bi.view.actions.file.rename.CMRenameAction#isValidName(java.lang.String)
 */
@Override
protected boolean isValidName(String p_newName) {
	return super.isValidName(p_newName)&&!existProjectName(p_newName);
}
private boolean existProjectName(String p_Name){
     if (WorkspaceManager.getInstance().existProjectReferenceInTheSession(p_Name,SessionManager.getCurrentSession())){
    	 JOptionPane.showMessageDialog(CMApplication.frame, CMMessages.getString("MESSAGE_ERROR_NAME_PROJECT_REPEAT"), CMMessages.getString("TITLE_MESSAGE_ERROR_NAME"), JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$ //$NON-NLS-2$
    	 return true;
     }
     else
    	 return false;
}
/* (non-Javadoc)
 * @see bi.view.actions.CMEnabledAction#calculateEnabled()
 */
public boolean calculateEnabled() {
	
	boolean isProjectNode = CMApplication.frame.getTreeWorkspaceView().getSelectedNode() instanceof CMProjectNode;
	 return ((CMEnabledAction)CMAction.ADDOPEN.getAction()).isEnabled()&&isProjectNode;
}
}