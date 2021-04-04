package bi.controller;

import java.io.File;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.undo.UndoableEdit;

import model.BusinessRules;
import model.LocalProjectReference;
import model.Project2;
import model.ProjectReference;
import model.Workspace2;
import model.edit.CMModelEditFactory;
import bi.view.actions.file.rename.CMRenameAction;
import bi.controller.utils.CMFileLocation;
import bi.controller.utils.CMInvalidFileLocationException;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.treeviews.nodes.CMProjectNode;
import bi.view.undomanagementviews.CMCompoundEdit;
import bi.view.utils.CMFileFilter;

public class ProjectManager {
    private JFileChooser m_FC;
	private static final ProjectManager INSTANCE = new ProjectManager();
	public ProjectManager() {
		super();
		   m_FC = new JFileChooser();
	        m_FC.addChoosableFileFilter(CMFileFilter.CPR.getFilter());
		// TODO Auto-generated constructor stub
	}
	public static Project2 getSelectedProject() {
		   DefaultMutableTreeNode node = CMApplication.frame.getTreeWorkspaceView().getSelectedNode();
		   DefaultMutableTreeNode parentNode;
		   DefaultMutableTreeNode targetNode;
		   if( node != null) {
			   if (node instanceof CMProjectNode)
				   return ((CMProjectNode)node).getProject();
		     parentNode = (DefaultMutableTreeNode) node.getParent();
		     if( parentNode != null){
		    	 if (parentNode instanceof CMProjectNode)
					   return ((CMProjectNode)parentNode).getProject();
		     targetNode = (DefaultMutableTreeNode) parentNode.getParent();
		       if( targetNode != null) {
				   if( targetNode instanceof CMProjectNode) {
				     CMProjectNode temp = (CMProjectNode) targetNode;
					 return temp.getProject();
				   }
		         }
		       }
		     }
		   
		   return null;
	}
	public static ProjectReference getSelectedProjectReference() {
		   DefaultMutableTreeNode node = CMApplication.frame.getTreeWorkspaceView().getSelectedNode();
		   DefaultMutableTreeNode parentNode;
		   DefaultMutableTreeNode targetNode;
		   if( node != null) {
			   if (node instanceof CMProjectNode)
				   return ((CMProjectNode)node).getProjectReference();
		     parentNode = (DefaultMutableTreeNode) node.getParent();
		     if( parentNode != null){
		    	 if (parentNode instanceof CMProjectNode)
					   return ((CMProjectNode)parentNode).getProjectReference();
		     targetNode = (DefaultMutableTreeNode) parentNode.getParent();
		       if( targetNode != null) {
				   if( targetNode instanceof CMProjectNode) {
				     CMProjectNode temp = (CMProjectNode) targetNode;
					 return temp.getProjectReference();
				   }
		         }
		       }
		     }
		   
		   return null;
	}
	public ProjectReference createProjectReference() {
        return new ProjectReference();
    }

    public Project2 createProject2() {
        return new Project2();
    }
    public static ProjectManager getInstance()
    {
    	return INSTANCE;
    }
    //	Smoreno refectorization separating model from view 
    public UndoableEdit addNewProject2(Workspace2 p_workspace) {
      CMCompoundEdit ce = new CMCompoundEdit();
      if( p_workspace != null ){
    	  
          boolean pathExists = false;
          JFileChooser l_FC = new JFileChooser();
          l_FC.setDialogTitle(p_workspace.getName()+" - "+CMMessages.getString("CREATE_PROJECT_TITLE"));
	      l_FC.addChoosableFileFilter(CMFileFilter.CPR.getFilter());   
		  l_FC.setFileSelectionMode(JFileChooser.FILES_ONLY);
		  l_FC.setAcceptAllFileFilterUsed(false);
		  l_FC.setDialogType(JFileChooser.CUSTOM_DIALOG);
		  l_FC.setApproveButtonText(CMMessages.getString("CREATE_PROJECT_TITLE"));
		  l_FC.setApproveButtonMnemonic(2);
		  l_FC.setApproveButtonToolTipText(CMMessages.getString("CREATE_PROJECT_TITLE"));
          while( !pathExists) {
			  File file = getProjectFileFromUser(l_FC);
			  
			  if( file != null ) {
				  //Ccastedo begins 18-04-06
				  CMFileLocation l_FileLocation = new CMFileLocation();				  
				  try {
					l_FileLocation.validateProjectLocation(file.getParentFile());
				} catch (CMInvalidFileLocationException e) {
					String[] message = {CMMessages.getString("CANNOT_CREATE_PROJECT") , e.getMessage()};
	            	JOptionPane.showMessageDialog(CMApplication.frame, message, p_workspace+" - Create Project", JOptionPane.ERROR_MESSAGE);
	            	CMApplication.frame.setWaitCursor(false);
	            	return ce;
				}
		
		            	
					  StringBuffer sFileName = new StringBuffer();
					  sFileName.append(file.getName());
					  sFileName.append(BusinessRules.FILE_PROJECT_EXTENSION);
					  StringBuffer filePath = new StringBuffer();               
	                  String pathString = file.getPath();
	                  pathString = pathString.replace('\\','/');
					  filePath.append(pathString);                 
					  filePath.append(BusinessRules.URL_SEPARATOR); //$NON-NLS-1$
					  filePath.append(sFileName);      
					  if( !existPath(pathString)) {
						  ////////////////
						  //create the model objects
				     	  Project2 newProject = ProjectManager.getInstance().createProject2();
						  ProjectReference newProjectReference = ProjectManager.getInstance().createProjectReference();
						  //////////////////
	                    
						 CMApplication.frame.setWaitCursor(true);
	                    //initial state = check out
	                     ce.addEdit(CMModelEditFactory.INSTANCE.createChangeAccessStateModelEdit(newProject, BusinessRules.ACCESS_STATE_CHECKED_OUT));
	                     newProject.setAccessState(BusinessRules.ACCESS_STATE_CHECKED_OUT);
	                     //user = session user
	                     ce.addEdit(CMModelEditFactory.INSTANCE.createChangeUserModelEdit(newProject,SessionManager.getCurrentSession().getM_User()));
	                     newProject.setUser(SessionManager.getCurrentSession().getM_User());
	                    //set the path & filename choosed 
	                     ce.addEdit(CMModelEditFactory.INSTANCE.createChangeFilePathModelEdit(newProjectReference,sFileName.toString(), pathString,null,null));
	     				  newProjectReference.setPath(pathString);
						  newProjectReference.setFileName(sFileName.toString());
						  //set the name of the project = filename
						  ce.addEdit(CMModelEditFactory.INSTANCE.createChangeNameModelEdit(newProject, file.getName()));
						  newProject.setName(file.getName());
						  //set the time stamp = project time stamp
						  ce.addEdit(CMModelEditFactory.INSTANCE.createChangeTimeStampModelEdit(newProjectReference, newProject.getM_TimeStamp()));
						  newProjectReference.setTimeStamp(newProject.getM_TimeStamp());
						  //add a runtime relation between the project and the reference
						  ce.addEdit(CMModelEditFactory.INSTANCE.createBindProjectAndReferenceModelEdit(newProject,newProjectReference));
						  newProject.setProjectReference(newProjectReference);
						  newProjectReference.setProject(newProject);
						  //add the project reference to the workspace
						  ce.addEdit(WorkspaceManager.getInstance().addProjectReferenceToWorkspace2(newProjectReference,p_workspace));
						  writeProject2(newProject, newProjectReference);
						  pathExists = true;
	                      CMApplication.frame.setWaitCursor(false);	//fcastro_20092004
					  }
					  else {
						pathExists = false;
						JOptionPane.showMessageDialog(CMApplication.frame, CMMessages.getString("INFO_CHOOSE_ANOTHER_PROJECT_NAME")); //$NON-NLS-1$
					  }
				  
				 
				  
			  }//if file !=null
              else  return ce;            
          }
      }
      return ce;
     }
    //Ccastedo ends 15-02-06
    public boolean existPath(String p_Path) {
        File file = new File(p_Path);
        if (file.exists()) {
            return true;
        }
        else {
            return false;
        }
    }
    public File getProjectFileFromUser(JFileChooser p_fc) {
    	//hmendez_03112005_edit_begin
    	boolean l_repeat =true;
    	while (l_repeat){
          int returnVal = p_fc.showDialog(CMApplication.frame, p_fc.getApproveButtonText());
          java.io.File file;
          if (returnVal == JFileChooser.APPROVE_OPTION) {
              file = p_fc.getSelectedFile();
              l_repeat = isProjectReferencePathinWorkspaces(file.getName());           
              if (l_repeat){
            	  JOptionPane.showMessageDialog(CMApplication.frame, CMMessages.getString("MESSAGE_ERROR_NAME_PROJECT_REPEAT"),
            			  CMMessages.getString("TITLE_MESSAGE_ERROR_NAME"), JOptionPane.ERROR_MESSAGE);            	
              }
              
              else{
            	  l_repeat = CMRenameAction.existProhibitedCharactersInName(file.getName());
            	  if (l_repeat) return null; 
            	  if(file.getName().length()<=128){
                      return file;            	
                  }
                  else
                  {//showConfirmDialog((java.awt.Component)
                	  JOptionPane.showMessageDialog((java.awt.Component)
          		            null, CMMessages.getString("INFO_PROJECT_FILE_NAME_TO_LONG"), 
          		          CMMessages.getString("TITLE_MESSAGE_ERROR_NAME"), JOptionPane.ERROR_MESSAGE);
    	          }
              }
              }
          else {
              return null;
          }
    	}
    	return null;
//    	hmendez_03112005_edit_end
    }
    public void writeProject2(Project2 p_Project, ProjectReference p_ProjectReference) {
        File file = new File(p_ProjectReference.getPath());
        file.mkdirs();
        WorkspaceManager.getInstance().writeProject2ToFile(p_Project, p_ProjectReference.getFilePath());
        //always write the new local project 
        try{
        	if (!(p_ProjectReference instanceof LocalProjectReference))
        	{
        		File file2 = new File(p_ProjectReference.getM_LocalProjectReference().getPath());
        		file2.mkdirs();
        		WorkspaceManager.getInstance().writeProject2ToFile(p_Project,p_ProjectReference.getM_LocalProjectReference().getFilePath());
        	}
        } catch (Exception e)
        {
        	e.printStackTrace();
        }
        SessionManager.INSTANCE.writeSession2ToFile(SessionManager.getCurrentSession());
    }
    //Ccastedo begins 15-02-06
    private boolean isProjectReferencePathinWorkspaces(String p_FileName){
    	boolean sw = false;
       	 List workspaces = SessionManager.getCurrentSession().getM_Workspaces();
         int sizeworkspaces = workspaces.size();
         for (int i=0;i<sizeworkspaces;i++){
         	Workspace2 workspace = (Workspace2)workspaces.get(i);
         	List projects = workspace.getM_ProjectReferences();
         	int sizeproyects = projects.size();
         	for (int j=0; j<sizeproyects;j++){
         		ProjectReference projectReference = (ProjectReference)projects.get(j);	
         		if (projectReference.getName().equals(p_FileName)){
         			sw=true;      
         			return true;
         		}
         	}
         }
         return sw;
    }
//  Smoreno refactor separate model from view
    public UndoableEdit removeProject(ProjectReference reference) {
        CMCompoundEdit ce = new CMCompoundEdit();
        CMApplication.frame.setWaitCursor(true);
        Workspace2 workspace = reference.getM_Workspace();
        if (workspace.getM_ProjectReferences().contains(reference)) {
        	ce.addEdit(CMModelEditFactory.INSTANCE.createDeleteProjectReferenceModelEdit(reference));
            workspace.removeProjectReference(reference);
            reference.setM_Workspace(null);
            SessionManager.INSTANCE.writeSession2ToFile(SessionManager.getCurrentSession());
        }       
        CMApplication.frame.setWaitCursor(false);
        return ce;
    }
    
}
