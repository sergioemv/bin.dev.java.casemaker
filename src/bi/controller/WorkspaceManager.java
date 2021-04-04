package bi.controller;

//grueda11092004_begin
//import model.Workspace;
//grueda11092004_end
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.undo.UndoableEdit;

import model.BRulesReference;
import model.BusinessRules;
import model.Combination;
import model.Element;
import model.EquivalenceClass;
import model.LocalProjectReference;
import model.Project2;
import model.ProjectReference;
import model.ReportRecord;
import model.ResultComparation;
import model.Session2;
import model.StdCombination;
import model.Structure;
import model.TDStructure;
import model.TDStructureReference;
import model.TestCase;
import model.TestDataFormat;
import model.TestDataSetReportUnit;
import model.TestObject;
import model.TestObjectReference;
import model.Variables;
import model.Workspace2;
import model.edit.CMModelEditFactory;

import org.apache.log4j.Logger;

import bi.controller.utils.CMBaseObjectReader;
import bi.controller.utils.CMFileValidator;
import bi.controller.utils.CMXMLFileState;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.testdataviews.CMIndexTDStructureUpdate;
import bi.view.treeviews.nodes.CMWorkspaceNode;
import bi.view.undomanagementviews.CMCompoundEdit;
import bi.view.undomanagementviews.undoviews.CMUndoMediator;
import bi.view.utils.CMFormatFactory;




public class WorkspaceManager {

    private static final WorkspaceManager INSTANCE = new WorkspaceManager();

    private VersionManager m_VersionManager;
    private StructureManager m_StructureManager;
    private SessionManager m_SessionManager;

	public WorkspaceManager() {
    }

    public static Workspace2 getSelectedWorkspace() {
    	   DefaultMutableTreeNode node = CMApplication.frame.getTreeWorkspaceView().getSelectedNode();
    	   DefaultMutableTreeNode parentNode;
    	   DefaultMutableTreeNode parentOfParentNode;
    	   DefaultMutableTreeNode targetNode;

    	   if( node != null) {
    		   if (node instanceof CMWorkspaceNode)
    			   return ((CMWorkspaceNode)node).getM_Workspace();
    	     parentNode = (DefaultMutableTreeNode) node.getParent();
    	     if( parentNode != null) {
    	    	 if (parentNode instanceof CMWorkspaceNode)
      			   return ((CMWorkspaceNode)parentNode).getM_Workspace();
    	       parentOfParentNode = (DefaultMutableTreeNode) parentNode.getParent();
    	       if( parentOfParentNode != null) {
    		     targetNode = (DefaultMutableTreeNode) parentOfParentNode.getParent();
    			 if( targetNode != null) {
    			   Object nodeInfo = targetNode.getUserObject();
    			   if( nodeInfo != null) {
    				 if( nodeInfo instanceof CMWorkspaceNode) {
    				   CMWorkspaceNode temp = (CMWorkspaceNode) nodeInfo;
    				   return temp.getM_Workspace();
    				 }
    			   }
    			 }
    	       }
    	     }
    	   }
    	   return null;
    	 }

    public TestObject createTestObject() {
        return new TestObject();
    }


    public void writeTestObjectToFile(TestObject p_TestObject, String p_FilePath) {
        //grueda30122004_begin
        if( p_TestObject == null || p_FilePath == null) {
          return;
        }
        //grueda30122004_end
        try {
            JSX.ObjectWriter out = new JSX.ObjectWriter(new FileWriter(p_FilePath.toString()));
            out.writeObject(p_TestObject);
            out.close();
        } catch (java.io.IOException e) {
        	Logger.getLogger(this.getClass()).error(e);
        }
    }


    //grueda11092004_begin
    public Project2 readProject(String p_Filename) {
      //grueda30122004_begin
      if( p_Filename == null) {
        return null;
      }
      //grueda30122004_end
    //grueda11092004_end
        Object obj = null;
        try {
        	//Ccastedo begins 25-01-06 Validation of XML File
            CMBaseObjectReader in = new CMBaseObjectReader(new FileReader(p_Filename), p_Filename);
         //   if (in.isvalidFile())
          	 obj = in.readObject();
             if( obj == null) {
             	    in.close();
             	    return null;
               }
             //Ccastedo ends 25-01-06

            //grueda30122004_begin
             Project2 p = (Project2) obj;
             if(p.getAccessState() == null){
               p.setAccessState(BusinessRules.ACCESS_STATE_CHECKED_IN);
             }
             if( p.getUser() == null) {
               p.setUser(BusinessRules.ACCESS_USER_GUEST);
             }
            //grueda30122004_begin
            //grueda16092004_begin
            boolean rewrite = m_VersionManager.updateProjectBasedOnTheVersion((Project2) obj, null);//svonborries_07012006
            if( rewrite) {
              writeProject2ToFile((Project2) obj, p_Filename);
            }
            //grueda16092004_end
            in.close();
        } catch (java.io.FileNotFoundException e1) {
        	Logger.getLogger(this.getClass()).error(e1);
            //grueda11092004_begin
            return ProjectManager.getInstance().createProject2();
            //grueda11092004_end
        } catch (java.lang.ClassNotFoundException e2) {
        	Logger.getLogger(this.getClass()).error(e2);
        } catch (java.io.InvalidClassException e3) {
        	Logger.getLogger(this.getClass()).error(e3);
        } catch (java.io.StreamCorruptedException e4) {
        	Logger.getLogger(this.getClass()).error(e4);
        } catch (java.io.OptionalDataException e5) {
        	Logger.getLogger(this.getClass()).error(e5);
        } catch (java.io.IOException e6) {
        	Logger.getLogger(this.getClass()).error(e6);
        }
        return (Project2)obj;
    }

    public void removeTestObjectReferenceInProject2(TestObjectReference p_TestObjectReference, Project2 p_Project) {
        //grueda3012004_begin
        if( p_TestObjectReference == null) {
          return;
        }
        //grueda30122004_end
        Project2 project =p_Project; //p_TestObjectReference.getM_Project();
        if (project.containsTestObjectReference(p_TestObjectReference)){//getM_TestObjectReferences().contains(p_TestObjectReference)) {
            project.removeTestObjectReference(p_TestObjectReference);//getM_TestObjectReferences().removeElement(p_TestObjectReference);
        }
    }
    public void deleteWorkspace2(Workspace2 p_Workspace) {
        Session2 session = p_Workspace.getM_Session();
        if (session.getM_Workspaces().contains(p_Workspace)) {
            session.removeWorkspace(p_Workspace);
        }
    }

    public StructureManager getM_StructureManager() {
        return m_StructureManager;
    }

    public void setM_StructureManager(StructureManager m_StructureManager) {
        this.m_StructureManager = m_StructureManager;
    }

    ///////////////////////// NEW FUNCTIONS
    //// CREATE /////////////////////////////////////////////////////////////////
    public Workspace2 createWorkspace2(Session2 session) {
        Workspace2 w = new Workspace2();
         return w;
    }


    public TestObjectReference createTestObjectReference() {
        return new TestObjectReference();
    }

    ////// ADD //////////////////////////////////////////////////////////////////////////////
    public void addWorkspace2ToSession2(Workspace2 p_Workspace2, Session2 p_Session2) {
        p_Workspace2.setM_Session(p_Session2);
        p_Session2.addWorkspace(p_Workspace2);
    }

    public UndoableEdit addProjectReferenceToWorkspace2(ProjectReference p_ProjectReference, Workspace2 p_Workspace2) {
    	CMCompoundEdit ce = new CMCompoundEdit();
    	ce.addEdit(CMModelEditFactory.INSTANCE.createAddProjectReferenceModelEdit(p_Workspace2,p_ProjectReference));
        p_ProjectReference.setM_Workspace(p_Workspace2);
        p_Workspace2.addProjectReference(p_ProjectReference);
        return ce;
    }

    public void addTestObjectReferenceToProject2(TestObjectReference p_TestObjectReference, Project2 p_Project2) {
        //grueda28082004_begin
        //grueda21092004_begin
        if(p_Project2 == null) {
          return;
        }
        //grueda21092004_end
        if( !p_Project2.containsTestObjectReference(p_TestObjectReference)){//getM_TestObjectReferences().contains(p_TestObjectReference) ){
          p_TestObjectReference.setM_Project(p_Project2);
          p_Project2.getTestObjectReferences().addElement(p_TestObjectReference);
        }
        //grueda28082004_end
    }

    //////////////////////////READ FROM FILE/////////////////////////////////////////////////
    //grueda04112004_begin
    public TestObject readTestObjectByReference(TestObjectReference p_TestObjectReference, ProjectReference p_ProjectReference, Session2 p_Session) {
      /*
        If( TOR is checked out local by the same user) THEN
	    Read Test Object from “prlocal+TOR”.
        Else If(TOR is checked out) THEN
	    Read Test Object from “pr+TOR”.
		Else if( TOR is checked in) THEN
 	    Read Test Object from “pr+TOR”
      */
        //grueda30122004_begin
        if( p_ProjectReference == null || p_TestObjectReference == null){
          return null;
        }
        //grueda30122004_end
		  StringBuffer absoluteFilePath = new StringBuffer();
		  absoluteFilePath.append(p_ProjectReference.getPath());
		  absoluteFilePath.append(BusinessRules.URL_SEPARATOR);
		  absoluteFilePath.append(p_TestObjectReference.getFilePath());
		  TestObject testObject = this.readTestObject2(absoluteFilePath.toString().replace('\\','/'));

		  return testObject;
    }

    //Ccastedo begins 20-04-06
    public TestObject readTestObject2ByReference(TestObjectReference p_TestObjectReference, String p_ProjectPath) {

          if(p_TestObjectReference == null){
            return null;
          }
          //grueda30122004_end
  		  StringBuffer absoluteFilePath = new StringBuffer();
  		  absoluteFilePath.append(p_ProjectPath);
  		  absoluteFilePath.append(BusinessRules.URL_SEPARATOR);
  		  absoluteFilePath.append(p_TestObjectReference.getFilePath());
  		  TestObject testObject = this.readTestObject2(absoluteFilePath.toString().replace('\\','/'));

  		  return testObject;
      }
    //Ccastedo ends 20-04-06

    //grueda04112004_begin
    public boolean isTestObjectCheckedOutLocalByTheSameUser(TestObject p_TestObject, Session2 p_Session){
      if( p_TestObject!=null&&p_TestObject.getAccessState().equals(BusinessRules.ACCESS_STATE_CHECKED_OUT_LOCAL)
          && p_TestObject.getUser().equalsIgnoreCase(p_Session.getM_User()) ) {
        return true;
      }
      else {
        return false;
      }
    }

    public boolean isTestObjectReferenceCheckOutLocal(TestObject p_TestObject){
      if( p_TestObject!=null&&/*p_TestObjectReference*/p_TestObject.getAccessState().equals(BusinessRules.ACCESS_STATE_CHECKED_OUT_LOCAL)){
         return true;
      }
      else {
        return false;
      }
    }
    //grueda16122004_end

    //grueda09112004_begin
	public boolean isTestObjectCheckedOutByAnother(TestObject p_TestObject, Session2 p_Session) {
      if( /*p_TestObjectReference*/p_TestObject.getAccessState().equals(BusinessRules.ACCESS_STATE_CHECKED_OUT)
          && !/*p_TestObjectReference*/p_TestObject.getUser().equalsIgnoreCase(p_Session.getM_User()) ) {
        return true;
      }
      else {
        return false;
      }
    }
    public boolean isTestObjectCheckedOutByTheSameUser(TestObject p_TestObject, Session2 p_Session) {
      if( p_TestObject.getAccessState().equals(BusinessRules.ACCESS_STATE_CHECKED_OUT)
          && p_TestObject.getUser().equalsIgnoreCase(p_Session.getM_User()) ) {
        return true;
      }
      else {
        return false;
      }
    }
    public boolean isTestObjectCheckedOutLocalByAnotherUser(TestObject p_TestObject, Session2 p_Session){
      if( p_TestObject.getAccessState().equals(BusinessRules.ACCESS_STATE_CHECKED_OUT_LOCAL)
          && !p_TestObject.getUser().equalsIgnoreCase(p_Session.getM_User()) ) {
        return true;
      }
      else {
        return false;
      }
    }
    //grueda09112004_end

    /**
     * Reads The projects from the current reference. if is not posible to read it tries with the
     * Local project reference
     * @param p_ProjectReference
     * @return
     */
    public Project2 readProject2ByReference(ProjectReference p_ProjectReference) {
         //grueda04112004_begin
        Project2 project = null;
        LocalProjectReference localProjectReference = null;
        //grueda29122004_begin
        if( p_ProjectReference == null) {
          return null;
        }
        //grueda29122004_end
        project = readProject2(p_ProjectReference.getFilePath());//svonborries_09012006
        if( project == null) {
          localProjectReference = p_ProjectReference.getM_LocalProjectReference();
          if( localProjectReference != null) {
            project = readProject2( localProjectReference.getFilePath());//svonborries_09012006
            if( project == null) {
              return null;
            }
            return project;
          }
          else {
		    return null;
          }
        }
        return project;
        //grueda04112004_end
    }

    public boolean existProjectReferenceInTheSession(String filePath, Session2 p_Session) {
        ProjectReference projectReference = null;
        Workspace2 workspace = null;
        int numOfWorkspaces = p_Session.getM_Workspaces().size();
        for (int j = 0; j < numOfWorkspaces; j++) {
            workspace = (Workspace2)p_Session.getM_Workspaces().get(j);
            int numOfProjectReferences = workspace.getM_ProjectReferences().size();
            for (int i = 0; i < numOfProjectReferences; i++) {
                projectReference = (ProjectReference)workspace.getM_ProjectReferences().get(i);
                if (projectReference.getName().equals(filePath)) {//getM_FilePath().equals(filePath)) {
                    return true;
                }
            }
        }
        return false;
    }

    //grueda22082004_begin
    //public boolean existTestObjectReferenceInTheProject(String filePath, Project2 p_Project) {
    public boolean existTestObjectReferenceInTheProject(String filePath, Project2 p_Project, ProjectReference p_ProjectReference) {
        TestObjectReference testObjectReference = null;
        StringBuffer absoluteFilePath = null;
        int numOfTestObjectReferences = p_Project.getTestObjectReferences().size();
        for (int i = 0; i < numOfTestObjectReferences; i++) {
            testObjectReference = (TestObjectReference)p_Project.getTestObjectReferences().elementAt(i);
            //if (testObjectReference.getM_FilePath().equals(fileName)) {
            absoluteFilePath = new StringBuffer();
            absoluteFilePath.append(p_ProjectReference.getPath());
            absoluteFilePath.append(BusinessRules.URL_SEPARATOR);
            absoluteFilePath.append(testObjectReference.getFilePath());
            if (absoluteFilePath.toString().equals(filePath)) {
                return true;
            }
        }
        return false;
    }
    //grueda22082004_end

    public boolean existTestObjectNameInTheProject(String p_Name, Project2 p_Project) {
        TestObjectReference testObjectReference = null;
        int numOfTestObjectReferences = p_Project.getTestObjectReferences().size();
        for (int i = 0; i < numOfTestObjectReferences; i++) {
            testObjectReference = (TestObjectReference)p_Project.getTestObjectReferences().elementAt(i);
            if (testObjectReference.getName().equals(p_Name)) {
                return true;
            }
        }
        return false;
    }

    public Project2 readProject2(String p_Filename) {
        Object obj = null;
        Project2 p = null;//svonborries_02022006
        FileReader reader=null;
        try
        {
        reader = new FileReader(p_Filename);
        reader.read();
        }catch (IOException e)
        {
        	//CMBaseObjectReader.setReadFileState(p_Filename,CMXMLFileState.NOTFOUND);
        	return null;
        }
        try {
//        	Ccastedo begins 25-01-06 Validation of XML File
            CMBaseObjectReader in = new CMBaseObjectReader(reader, p_Filename);
           // if (in.isvalidFile())
            obj = in.readObject();
            if( obj == null) {
            	    in.close();
            	    return null;
              }

             //Ccastedo ends 25-01-06

        //CC    CMBaseObjectReader in = new CMBaseObjectReader(new FileReader(p_Filename));
        //CC    obj = in.readObject();
            //grueda30122004_begin
             p = (Project2) obj;//svonborries_02022006
             if(p.getAccessState() == null){
               p.setAccessState(BusinessRules.ACCESS_STATE_CHECKED_IN);
             }
             if( p.getUser() == null) {
               p.setUser(BusinessRules.ACCESS_USER_GUEST);
             }
            //grueda30122004_end

            //grueda16092004_begin
             Logger.getLogger(this.getClass()).debug(p_Filename);
            boolean rewrite = m_VersionManager.updateProjectBasedOnTheVersion((Project2) obj, p_Filename);
            if( rewrite) {
              writeProject2ToFile((Project2) obj, p_Filename);
            }
            //grueda16092004_end
            in.close();
        } catch (java.io.FileNotFoundException e1) {
        	Logger.getLogger(this.getClass()).error(e1);
            return null;
        } catch (java.lang.ClassNotFoundException e2) {
        	Logger.getLogger(this.getClass()).error(e2);
            return null;
        } catch (java.io.InvalidClassException e3) {
        	Logger.getLogger(this.getClass()).error(e3);
            return null;
        } catch (java.io.StreamCorruptedException e4) {
        	Logger.getLogger(this.getClass()).error(e4);
            return null;
        } catch (java.io.OptionalDataException e5) {
        	Logger.getLogger(this.getClass()).error(e5);
            return null;
        } catch (java.io.IOException e6) {
        	Logger.getLogger(this.getClass()).error(e6);
            return null;
        }
        ///////////modificaciones Harold Adaptaciones a Project antiguos///////////////////////
        //svonborries_10012006_begin
        //smoreno do not validate on local projects

        if (((Project2)obj).getProjectReference()!=null)
        if (new File(((Project2)obj).getProjectReference().getFilePath()).canRead())
        {
        obj = (CMFileValidator.fixTestObjectReferences((Project2) obj,p_Filename)).clone();

        if(!CMFileValidator.DELETED_TESTOBJECTREFERENCES.equalsIgnoreCase("")){
        	writeProject2ToFile((Project2) obj, p_Filename);
        	JOptionPane.showMessageDialog(CMApplication.frame,CMMessages.getString("INFO_DELETED_TESTOBJECT_CORRUPTED_1")+
					"(" + ((Project2) obj).getName() + ")\n" + CMMessages.getString("INFO_DELETED_TESTOBJECT_CORRUPTED_2")
					+ "\n" + "\n" + '"' + CMFileValidator.DELETED_TESTOBJECTREFERENCES + '"'
					,CMMessages.getString("INFO_DELETED_TESTOBJECT_TITLE"),JOptionPane.INFORMATION_MESSAGE);
        	//svonborries_02022006_begin
        	CMFileValidator.DELETED_TESTOBJECTREFERENCES = "";
        	CMFileValidator.DELETED_TESTOBJECTREFERENCES_COUNT = 0;
        	//svonborries_02022006_end
		 }
        }
        //svonborries_10012006_end
        Project2 proj = (Project2)obj;
//      svonborries_05012006_begin
       /* if (proj.getM_Variables() == null) {
            proj.setM_Variables(new Variables());
        }*/
//      svonborries_05012006_end
        ////////////////////////////////////////////////////////////////////////////////////////
        return proj;
    }


    public TestObject readTestObject2(String p_Filename) {
        Object obj = null;
       try {
          FileReader fileReader = new FileReader(p_Filename);
          if(fileReader == null) {
              return null;
            }
          //Ccastedo begins 25-01-06
          p_Filename.replace('\\','/');
          CMBaseObjectReader in = new CMBaseObjectReader(fileReader,p_Filename);

         obj = in.readObject();

          if( obj == null) {
         	    in.close();
         	    return null;
           }

           boolean rewrite = m_VersionManager.updateTestObjectBasedOnTheVersion((TestObject) obj);
           if( in.isSuperclassMessageAppear() || rewrite) {
			  writeTestObjectToFile2((TestObject) obj, p_Filename);
           }

           in.close();
       } catch (java.io.FileNotFoundException e1) {
    	   Logger.getLogger(this.getClass()).error(e1);
           return null;
       } catch (java.lang.ClassNotFoundException e2) {
    	   Logger.getLogger(this.getClass()).error(e2);
           return null;
       } catch (java.io.InvalidClassException e3) {
    	   Logger.getLogger(this.getClass()).error(e3);
           return null;
       } catch (java.io.StreamCorruptedException e4) {
    	   Logger.getLogger(this.getClass()).error(e4);
           return null;
       } catch (java.io.OptionalDataException e5) {
    	   Logger.getLogger(this.getClass()).error(e5);
           return null;
       } catch (java.io.IOException e6) {
    	   Logger.getLogger(this.getClass()).error(e6);
           return null;
       }
       return (TestObject)obj;
   }

	////////// ADD FROM EXISTING FILE///////////////////////////////////////////
    public void addTestObjectToProject2(TestObject p_TestObject, String p_FilePath, Project2 p_Project) {
    	if( p_TestObject == null || p_Project == null){
          return;
    	}
        TestObjectReference testObjectReference = new TestObjectReference();
     //   testObjectReference.setM_FilePath(p_FilePath);           ccastedo commenst 24-04-06
        //grueda01092004_begin
        //testObjectReference.setM_Name(p_Project.getM_Name(), "");
    //    testObjectReference.setM_Name(p_Project.getName());   Ccastedo comments 24-04-06
        //grueda01092004_end
        Date d = p_TestObject.getTimeStamp();
        testObjectReference.setTimeStamp(d);
        testObjectReference.setM_Project(p_Project);
        p_Project.getTestObjectReferences().addElement(testObjectReference);
    }

    public void addTestObjectToProject2(String p_FilePath, Project2 p_Project) {
        TestObject testObject = this.readTestObject2(p_FilePath);
        this.addTestObjectToProject2(testObject, p_FilePath, p_Project);
    }

   //grueda02092004_begin
    //public TestObjectReference addTestObjectCloneToProject2(TestObject p_TestObject, Project2 p_Project, Session2 p_Session) {
    public TestObjectReference addTestObjectCloneToProject2(TestObject p_TestObject, ProjectReference p_ProjectReference, Project2 p_Project, Session2 p_Session) {
    	if(p_TestObject == null || p_ProjectReference == null || p_Session == null){
          return null;
    	}
        //ProjectReference projectReference = getProjectReferenceOfProject2InSession2(p_Project, p_Session);
    //grueda02092004_end
        TestObjectReference newTestObjectReference = new TestObjectReference();
        String projectPath = p_ProjectReference.getPath();
        StringBuffer testObjectPath = new StringBuffer();
        //grueda02092004_begin
        //testObjectPath.append(projectPath);
        //grueda02092004_end
        /*newTestObjectReference*/p_TestObject.setUser(p_Session.getM_User());
        /*newTestObjectReference*/p_TestObject.setAccessState(BusinessRules.ACCESS_STATE_CHECKED_IN);
        //grueda01092004_begin
        //newTestObjectReference.setM_Name(p_TestObject.getM_Name(), testObjectPath.toString());
        //   newTestObjectReference.setM_Name(p_TestObject.getName());    castedo comments 24-04-06
        //grueda01092004_end
        newTestObjectReference.setM_Project(p_Project);
        p_TestObject.setTimeStamp(newTestObjectReference.getTimeStamp());
        addTestObjectReferenceToProject2(newTestObjectReference, p_Project);

        StringBuffer newAbsolutePath = new StringBuffer();
        newAbsolutePath.append( p_ProjectReference.getPath());
        newAbsolutePath.append( BusinessRules.URL_SEPARATOR);
        newAbsolutePath.append( newTestObjectReference.getPath());
        File directory = new File(newAbsolutePath.toString());
        //grueda30122004_begin
        if (!directory.exists()) {
            directory.mkdir();
        }
        //grueda30122004_end

        return newTestObjectReference;
    }

    ////////////// Write To File ///////////////////////////////////////////////
    //grueda30122004_begin
    public boolean writeTestObjectToFile2(TestObject p_TestObject, String p_FilePath) {
        if( p_TestObject == null) {
          return false;
        }
        try {
            JSX.ObjectWriter out = new JSX.ObjectWriter(new FileWriter(p_FilePath.toString()));
            out.writeObject(p_TestObject);
            out.close();
            return true;
        } catch (java.io.IOException e) {
        	Logger.getLogger(this.getClass()).error(e);
            return false;
        }
    }

    //grueda30122004_end
    public void writeProject2ToFile(Project2 p_Project, String p_FilePath) {
        //grueda30122004_begin
        if(p_Project == null || p_FilePath == null) {
          return;
        }
        try {
            JSX.ObjectWriter out = new JSX.ObjectWriter(new FileWriter(p_FilePath.toString()));
            out.writeObject(p_Project);
            out.close();
        } catch (java.io.IOException e) {
        	Logger.getLogger(this.getClass()).error(e);
        }
    }
    public void writeProjectPathToFile(String p_Path){
      File file = new File(p_Path);
      if( file == null) {
        return;
      }
      if( !file.exists()) {
        file.mkdirs();
      }
    }
    public String getFilePathOfTestObjectInProject2(TestObject p_TestObject, Project2 p_Project) {
        int numOfTestObjectReference = p_Project.getTestObjectReferences().size();
        for (int i = 0; i < numOfTestObjectReference; i++) {
            TestObjectReference testObjectReference = (TestObjectReference)p_Project.getTestObjectReferences().elementAt(i);
            if (testObjectReference.getName() == p_TestObject.getName() &&
                testObjectReference.getTimeStamp() == p_TestObject.getTimeStamp()) {
                    String filePath = testObjectReference.getFilePath();
                    return filePath;
            }
        }
        return null;
    }

    public ProjectReference getProjectReferenceOfProject2InSession2(Project2 p_Project, Session2 p_Session) {
        int numOfWorkspaces = p_Session.getM_Workspaces().size();
        for (int i = 0; i < numOfWorkspaces; i++) {
            Workspace2 workspace = (Workspace2)p_Session.getM_Workspaces().get(i);
            int numOfProjectReferences = workspace.getM_ProjectReferences().size();
            for (int j = 0; j < numOfProjectReferences; j++) {
                ProjectReference projectReference = (ProjectReference)workspace.getM_ProjectReferences().get(j);
                if (projectReference.getName().equals(p_Project.getName()) &&
                    projectReference.getTimeStamp().equals(p_Project.getM_TimeStamp())) {
                        return projectReference;
                }
            }
        }
        return null;
    }

    //Ccastedo begins 21-04-06
    public TestObjectReference getTestObjectReferenceOfTestObjectInSession2(Project2 p_Project, TestObject p_TestObject, Session2 p_Session) {
        int numOfWorkspaces = p_Session.getM_Workspaces().size();
        for (int i = 0; i < numOfWorkspaces; i++) {
            Workspace2 workspace = (Workspace2)p_Session.getM_Workspaces().get(i);
            int numOfProjectReferences = workspace.getM_ProjectReferences().size();
            for (int j = 0; j < numOfProjectReferences; j++) {
                ProjectReference projectReference = (ProjectReference)workspace.getM_ProjectReferences().get(j);
                if (projectReference.getName().equals(p_Project.getName()) &&
                    projectReference.getTimeStamp().equals(p_Project.getM_TimeStamp())) {
                	for (int k = 0; k < numOfProjectReferences; k++) {
                		TestObjectReference testObjectReference = (TestObjectReference)projectReference.getProject().getTestObjectReferences().get(k);
                		if (testObjectReference.getName().equals(p_TestObject.getName()) &&
                				testObjectReference.getTimeStamp().equals(p_TestObject.getTimeStamp())) {
                					return testObjectReference;
                		}
                	}


                }
            }
        }
        return null;
    }
    //Ccastedo ends 21-04-06

//grueda05112004_begin
    public String getFilePathOfProject2InSession2(Project2 p_Project, Session2 p_Session, TestObjectReference p_TestObjectReference, TestObject p_TestObject) {
        ProjectReference localProjectReference = null;
        int numOfWorkspaces = p_Session.getM_Workspaces().size();
        for (int i = 0; i < numOfWorkspaces; i++) {
            Workspace2 workspace = (Workspace2)p_Session.getM_Workspaces().get(i);
            int numOfProjectReferences = workspace.getM_ProjectReferences().size();
            for (int j = 0; j < numOfProjectReferences; j++) {
                ProjectReference projectReference = (ProjectReference)workspace.getM_ProjectReferences().get(j);
                if (projectReference.getName().equals(p_Project.getName()) &&
                    projectReference.getTimeStamp().equals(p_Project.getM_TimeStamp())) {
                        if( isTestObjectCheckedOutLocalByTheSameUser(p_TestObject, p_Session)) {
                           localProjectReference = projectReference.getM_LocalProjectReference();
                          if( localProjectReference != null) {
                            return localProjectReference.getFilePath();
                          }
                          else {
                            return null;
                          }
                        }
                        else{
                          return projectReference.getFilePath();
                        }
                }
            }
        }
        return null;
    }

    public void saveTestObject2(TestObject p_TestObject, Project2 p_Project) {
        String filePath = this.getFilePathOfTestObjectInProject2(p_TestObject, p_Project);
        this.writeTestObjectToFile2(p_TestObject, filePath);
    }


    public int checkOutTestObject(TestObjectReference p_TestObjectReference,TestObject p_TestObject, Session2 m_Session) {
        if (/*p_TestObjectReference*/p_TestObject.getAccessState() == BusinessRules.ACCESS_STATE_CHECKED_IN) {
            /*p_TestObjectReference*/p_TestObject.setAccessState(BusinessRules.ACCESS_STATE_CHECKED_OUT);
            /*p_TestObjectReference*/p_TestObject.setUser(m_Session.getM_User());
            return BusinessRules.ACCESS_CHECK_OUT_ACCEPTED;
        }
        else {
            return BusinessRules.ACCESS_CHECK_OUT_REJECTED;
        }
    }

    public int checkInTestObject(TestObjectReference p_TestObjectReference,TestObject p_TestObject, Session2 m_Session) {
        if (/*p_TestObjectReference*/p_TestObject.getAccessState().equals(BusinessRules.ACCESS_STATE_CHECKED_OUT)) {
            if (/*p_TestObjectReference*/p_TestObject.getUser().equalsIgnoreCase(m_Session.getM_User())) {
                /*p_TestObjectReference*/p_TestObject.setAccessState(BusinessRules.ACCESS_STATE_CHECKED_IN);
                return BusinessRules.ACCESS_CHECK_IN_ACCEPTED;
            }
            else {
                return BusinessRules.ACCESS_CHECK_IN_USER_REJECTED;
            }
        }
        else {
            return BusinessRules.ACCESS_CHECK_IN_REJECTED;
        }
    }

    ////////////////////////////////Harold Canedo Lopez Metodos////////////////////
    public void addTestDataReferenceToTestObject(TDStructureReference p_TDStructureReference, TestObject p_TestObject) {
        //grueda29082004_begin
        if( !p_TestObject.getTDSTructureReference().contains(p_TDStructureReference) ){
			p_TDStructureReference.setM_TestObject(p_TestObject);
			p_TestObject.getTDSTructureReference().addElement(p_TDStructureReference);
        }
        //grueda29082004_end
    }

    public void addTDStructure(TDStructure p_TDStructure, TestObject p_TestObject, TestObjectReference p_TestObjectReference) {
        p_TDStructure.setM_TestObject(p_TestObject);
        p_TDStructure.setM_TestObjectReference(p_TestObjectReference);
    }

    public TDStructure createTDStructure() {
        return new TDStructure();
    }

    public TDStructureReference createTDStructureReference() {
        return new TDStructureReference();
    }

    public void deleteTDStructure(TDStructure p_TDStructure) {
   /*   TestObject testObject = p_TDStructure.getM_TestObject();
      if( testObject.getM_TDStructure().contains(p_TDStructure)) {
        testObject.getM_TDStructure().removeElement(p_TDStructure); old 25/03/04 TD file
      }*/
    }

    //grueda22082004_begin
    //public void saveTDStructure(TDStructure p_TDStructure, TestObject p_TestObject) {
    public void saveTDStructure(TDStructure p_TDStructure, TestObject p_TestObject, ProjectReference p_ProjectReference) {
        StringBuffer absoluteFilePath = new StringBuffer();
        absoluteFilePath.append(p_ProjectReference.getPath());
        absoluteFilePath.append(BusinessRules.URL_SEPARATOR);
        absoluteFilePath.append(BusinessRules.TEST_OBJECTS_FOLDER);
        absoluteFilePath.append(BusinessRules.URL_SEPARATOR);
        absoluteFilePath.append(p_TestObject.getName());
        absoluteFilePath.append(BusinessRules.URL_SEPARATOR);
        String filePath = ((TDStructureReference)p_TestObject.getTDSTructureReference().firstElement()).getM_FilePath();
        absoluteFilePath.append(filePath);

        String path = ((TDStructureReference)p_TestObject.getTDSTructureReference().firstElement()).getM_Path();
        StringBuffer absolutePath = new StringBuffer();
        absolutePath.append(p_ProjectReference.getPath());
        absolutePath.append(BusinessRules.URL_SEPARATOR);
        absolutePath.append(BusinessRules.TEST_OBJECTS_FOLDER);
        absolutePath.append(BusinessRules.URL_SEPARATOR);
        absolutePath.append(p_TestObject.getName());
        File f = new File(absolutePath.toString());
        f.mkdir();
        absolutePath.append(BusinessRules.URL_SEPARATOR);
	    absolutePath.append(path);
        f = new File(absolutePath.toString());
        f.mkdir();
        writeTDStructureToFile(p_TDStructure, absoluteFilePath.toString());
    }
    //grueda22082004_end

   //grueda30122004_begin
    public boolean writeTDStructureToFile(TDStructure p_TDStructure, String p_FilePath) {
        if( p_TDStructure == null) {
          return false;
        }
        TestObject to= p_TDStructure.getM_TestObject();
            TestObjectReference tor = p_TDStructure.getM_TestObjectReference();
        try {
           // TestObject to= p_TDStructure.getM_TestObject();
           // TestObjectReference tor = p_TDStructure.getM_TestObjectReference();
            p_TDStructure.setM_TestObject(null); //new 22.05.04
            p_TDStructure.setM_TestObjectReference(null); // new 22.05.04
            JSX.ObjectWriter out = new JSX.ObjectWriter(new FileWriter(p_FilePath.toString()));
            out.writeObject(p_TDStructure);
            out.close();
			p_TDStructure.setM_TestObject(to); //new 22.05.04
            p_TDStructure.setM_TestObjectReference(tor); // new 22.05.04
            return true;
        } catch (java.io.IOException e) {
        	Logger.getLogger(this.getClass()).error(e);
            p_TDStructure.setM_TestObject(to); //new 22.05.04
            p_TDStructure.setM_TestObjectReference(tor); // new 22.05.04
            return false;
        }
    }
   //grueda30122004_end
    private String getDateModified(long p_time )
    {
        Date d= new Date(p_time);
        TestDataFormat formatter= new TestDataFormat();
        formatter.setRealFormat("dd-MMM-yyyy H:mm:ss");
        String dateMod=CMFormatFactory.formatDate(formatter,d,d.toString());
		return dateMod;
    }
    private void fixTestDataSetReports(TDStructure tdstructure){
    	if(tdstructure.getM_TestDataSetReportUnit().getM_ImportReportRecords() == null && tdstructure.getM_TestDataSetReportUnit().getM_ReportRecords() == null){
        	tdstructure.getM_TestDataSetReportUnit().setM_ImportReportRecords(new Vector());
        	tdstructure.getM_TestDataSetReportUnit().setM_ReportRecords(new Vector());

    	if(tdstructure.getM_TestDataSetReportUnit().getPathReportsImport() != null){
    		Vector pathReportsImports=tdstructure.getM_TestDataSetReportUnit().getPathReportsImport();
    		Vector appRegister=tdstructure.getM_TestDataSetReportUnit().getApplicationName();
    		Vector paramRegister=tdstructure.getM_TestDataSetReportUnit().getParameterReport();
    		for (int i=0;i<pathReportsImports.size();i++) {
				String report = (String) pathReportsImports.elementAt(i);
				File f= new File(report);
				if(f.exists()){
					ReportRecord newRecord= new ReportRecord();
					newRecord.setM_FileName(f.getName());
					newRecord.setM_Path(f.getPath());
					newRecord.setM_Name(f.getName());
					newRecord.setM_TimeStamp(getDateModified(f.lastModified()));
					try {
						newRecord.setM_Application((String) appRegister.elementAt(i));
					} catch (Exception e) {
						newRecord.setM_Application(BusinessRules.CHART_APPLICATION_DENOMINATIVE);
					}
					try {
						newRecord.setM_Parameters((String) paramRegister.elementAt(i));
					} catch (Exception e) {
						newRecord.setM_Parameters("");
					}
					tdstructure.getM_TestDataSetReportUnit().getM_ImportReportRecords().addElement(newRecord);
				}
			}
    	}
    	 }
    }


    //grueda28082004_begin
    public TDStructure readTDStructure(String p_FilePath) {
    //grueda28082004_end
        Object obj = null;
        try {
        	//Ccastedo begins 25-01-06
        	FileReader l_file = new FileReader(p_FilePath);
            CMBaseObjectReader in = new CMBaseObjectReader(l_file,p_FilePath);
           // if (in.isvalidFile())
          	 obj = in.readObject();
             if( obj == null) {
             	    in.close();
             	    return null;
               }
             //Ccastedo ends 25-01-06



            fixTestDataSetReports((TDStructure)obj);
            in.close();
        } catch (java.io.FileNotFoundException e1) {
        	Logger.getLogger(this.getClass()).error(e1);
            // hacer TD file mostrar un mensaje cuando no exita el archivo
            TDStructure tdstructure = this.createTDStructure();

				CMIndexTDStructureUpdate.getInstance().getTDStructureManager().chargeVectorIds(tdstructure);



            if(tdstructure.getM_ResultComparation()==null)
				tdstructure.setM_ResultComparation(new ResultComparation());
           //hcanedo 12_07_04 begin
            if(tdstructure.getM_TestDataSetReportUnit()==null)
                tdstructure.setM_TestDataSetReportUnit(new TestDataSetReportUnit());
            //hcanedo 12_07_04 end
//hcanedo_21_09_2004_begin
            fixTestDataSetReports(tdstructure);
			/*if(tdstructure.getM_TestDataSetReportUnit().getNameReportsImport()==null || tdstructure.getM_TestDataSetReportUnit().getPathReportsImport()==null)
            {
                tdstructure.getM_TestDataSetReportUnit().setNameReportsImport(new Vector());
                tdstructure.getM_TestDataSetReportUnit().setPathReportsImport(new Vector());
            }
            if(tdstructure.getM_TestDataSetReportUnit().getParameterReport()==null)
            {
                tdstructure.getM_TestDataSetReportUnit().setParameterReport(new Vector());
            }*/
//hcanedo_21_09_2004_end

            return tdstructure;
        } catch (java.lang.ClassNotFoundException e2) {
        	Logger.getLogger(this.getClass()).error(e2);
        } catch (java.io.InvalidClassException e3) {
        	Logger.getLogger(this.getClass()).error(e3);
        } catch (java.io.StreamCorruptedException e4) {
        	Logger.getLogger(this.getClass()).error(e4);
        } catch (java.io.OptionalDataException e5) {
        	Logger.getLogger(this.getClass()).error(e5);
        } catch (java.io.IOException e6) {
        	Logger.getLogger(this.getClass()).error(e6);
        }
        //grueda_21062004_begin
       TDStructure structure = (TDStructure)obj;
        if( structure == null) {
            TDStructure tdstructure = this.createTDStructure();

				CMIndexTDStructureUpdate.getInstance().getTDStructureManager().chargeVectorIds(tdstructure);

            if(tdstructure.getM_ResultComparation()==null)
				tdstructure.setM_ResultComparation(new ResultComparation());
            //hcanedo 12_07_04 begin
            if(tdstructure.getM_TestDataSetReportUnit()==null)
                tdstructure.setM_TestDataSetReportUnit(new TestDataSetReportUnit());
            //hcanedo 12_07_04 end
//hcanedo_21_09_2004_begin
            fixTestDataSetReports(tdstructure);
		/*	if(tdstructure.getM_TestDataSetReportUnit().getNameReportsImport()==null || tdstructure.getM_TestDataSetReportUnit().getPathReportsImport()==null)
            {
                tdstructure.getM_TestDataSetReportUnit().setNameReportsImport(new Vector());
                tdstructure.getM_TestDataSetReportUnit().setPathReportsImport(new Vector());

            }
            if(tdstructure.getM_TestDataSetReportUnit().getParameterReport()==null)
            {
                tdstructure.getM_TestDataSetReportUnit().setParameterReport(new Vector());
            }*/
//hcanedo_21_09_2004_end

            return tdstructure;
        }
         //grueda_21062004_end
		if(structure.getM_ResultComparation()==null)
			structure.setM_ResultComparation(new ResultComparation());
            //hcanedo 12_07_04 begin
            if(structure.getM_TestDataSetReportUnit()==null)
                structure.setM_TestDataSetReportUnit(new TestDataSetReportUnit());
            //hcanedo 12_07_04 end
//hcanedo_21_09_2004_begin
            fixTestDataSetReports(structure);
	/*		if(structure.getM_TestDataSetReportUnit().getNameReportsImport()==null || structure.getM_TestDataSetReportUnit().getPathReportsImport()==null)
            {
                structure.getM_TestDataSetReportUnit().setNameReportsImport(new Vector());
                structure.getM_TestDataSetReportUnit().setPathReportsImport(new Vector());
            }
            if(structure.getM_TestDataSetReportUnit().getParameterReport()==null)
            {
                structure.getM_TestDataSetReportUnit().setParameterReport(new Vector());
            }*/
//hcanedo_21_09_2004_end
            //svonborries_05012006_begin
            if(structure.getM_Variables() == null){
            	structure.setM_Variables(new Variables());
            }
//          svonborries_05012006_end
         return structure;
    }

    //public TDStructure readTDStructure(String p_Filename) {
    public TDStructure readTDStructure(TDStructureReference p_TDStructureReference, ProjectReference p_ProjectReference) {
     StringBuffer absoluteFilePath = new StringBuffer();
     absoluteFilePath.append(p_ProjectReference.getPath());
     absoluteFilePath.append(BusinessRules.URL_SEPARATOR);
     absoluteFilePath.append(BusinessRules.TEST_OBJECTS_FOLDER);
     absoluteFilePath.append(BusinessRules.URL_SEPARATOR);
     absoluteFilePath.append(p_TDStructureReference.getM_TestObject().getName());
     absoluteFilePath.append(BusinessRules.URL_SEPARATOR);
     absoluteFilePath.append(p_TDStructureReference.getM_FilePath());
    //grueda28082004_end
        Object obj = null;
        try {
        	//Ccastedo begins 25-01-06
            CMBaseObjectReader in = new CMBaseObjectReader(new FileReader(absoluteFilePath.toString()),absoluteFilePath.toString());
      //      if (in.isvalidFile())
          	  obj = in.readObject();
             if( obj == null) {
             	    in.close();
             	    return null;
               }
             //Ccastedo ends 25-01-06

            //CC  CMBaseObjectReader in = new CMBaseObjectReader(new FileReader(absoluteFilePath.toString()));
            //CC   obj = in.readObject();

            fixTestDataSetReports((TDStructure)obj);
            in.close();
        } catch (java.io.FileNotFoundException e1) {
        	Logger.getLogger(this.getClass()).error(e1);
            // hacer TD file mostrar un mensaje cuando no exita el archivo
            TDStructure tdstructure = this.createTDStructure();

				CMIndexTDStructureUpdate.getInstance().getTDStructureManager().chargeVectorIds(tdstructure);

            if(tdstructure.getM_ResultComparation()==null)
				tdstructure.setM_ResultComparation(new ResultComparation());
           //hcanedo 12_07_04 begin
            if(tdstructure.getM_TestDataSetReportUnit()==null)
                tdstructure.setM_TestDataSetReportUnit(new TestDataSetReportUnit());
            //hcanedo 12_07_04 end
//hcanedo_21_09_2004_begin
            fixTestDataSetReports(tdstructure);
	/*		if(tdstructure.getM_TestDataSetReportUnit().getNameReportsImport()==null || tdstructure.getM_TestDataSetReportUnit().getPathReportsImport()==null)
            {
                tdstructure.getM_TestDataSetReportUnit().setNameReportsImport(new Vector());
                tdstructure.getM_TestDataSetReportUnit().setPathReportsImport(new Vector());
            }
            if(tdstructure.getM_TestDataSetReportUnit().getParameterReport()==null)
            {
                tdstructure.getM_TestDataSetReportUnit().setParameterReport(new Vector());
            }*/
//hcanedo_21_09_2004_end
            return tdstructure;
        } catch (java.lang.ClassNotFoundException e2) {
        	Logger.getLogger(this.getClass()).error(e2);
        } catch (java.io.InvalidClassException e3) {
        	Logger.getLogger(this.getClass()).error(e3);
        } catch (java.io.StreamCorruptedException e4) {
        	Logger.getLogger(this.getClass()).error(e4);
        } catch (java.io.OptionalDataException e5) {
        	Logger.getLogger(this.getClass()).error(e5);
        } catch (java.io.IOException e6) {
        	Logger.getLogger(this.getClass()).error(e6);
        }
        //grueda_21062004_begin
       TDStructure structure = (TDStructure)obj;
        if( structure == null) {
            TDStructure tdstructure = this.createTDStructure();

				CMIndexTDStructureUpdate.getInstance().getTDStructureManager().chargeVectorIds(tdstructure);

            if(tdstructure.getM_ResultComparation()==null)
				tdstructure.setM_ResultComparation(new ResultComparation());
            //hcanedo 12_07_04 begin
            if(tdstructure.getM_TestDataSetReportUnit()==null)
                tdstructure.setM_TestDataSetReportUnit(new TestDataSetReportUnit());
            //hcanedo 12_07_04 end
//hcanedo_21_09_2004_begin
            fixTestDataSetReports(tdstructure);
		/*	if(tdstructure.getM_TestDataSetReportUnit().getNameReportsImport()==null || tdstructure.getM_TestDataSetReportUnit().getPathReportsImport()==null)
            {
                tdstructure.getM_TestDataSetReportUnit().setNameReportsImport(new Vector());
                tdstructure.getM_TestDataSetReportUnit().setPathReportsImport(new Vector());
            }
            if(tdstructure.getM_TestDataSetReportUnit().getParameterReport()==null)
            {
                tdstructure.getM_TestDataSetReportUnit().setParameterReport(new Vector());
            }*/
//hcanedo_21_09_2004_end
            return tdstructure;
        }
         //grueda_21062004_end
		if(structure.getM_ResultComparation()==null)
			structure.setM_ResultComparation(new ResultComparation());
            //hcanedo 12_07_04 begin
            if(structure.getM_TestDataSetReportUnit()==null)
                structure.setM_TestDataSetReportUnit(new TestDataSetReportUnit());
            //hcanedo 12_07_04 end
//hcanedo_21_09_2004_begin
            fixTestDataSetReports(structure);
		/*	if(structure.getM_TestDataSetReportUnit().getNameReportsImport()==null || structure.getM_TestDataSetReportUnit().getPathReportsImport()==null)
            {
                structure.getM_TestDataSetReportUnit().setNameReportsImport(new Vector());
                structure.getM_TestDataSetReportUnit().setPathReportsImport(new Vector());
            }
            if(structure.getM_TestDataSetReportUnit().getParameterReport()==null)
            {
                structure.getM_TestDataSetReportUnit().setParameterReport(new Vector());
            }*/
//hcanedo_21_09_2004_end
            //svonborries_05012006_begin
            if(structure.getM_Variables() == null){
            	structure.setM_Variables(new Variables());
            }
//          svonborries_05012006_end
         return structure;
    }


    


   

   //grueda22082004_begin
   //grueda30122004_begin
  public boolean saveBRulesReference(BRulesReference reference, TestObjectReference p_TestObjectReference, ProjectReference p_ProjectReference) {
  //grueda30122004_end
    //public void saveBRulesReference(BRulesReference reference) {
       StringBuffer absoluteFileLocation = new StringBuffer();
        absoluteFileLocation.append(p_ProjectReference.getPath());
        absoluteFileLocation.append(BusinessRules.URL_SEPARATOR);
        //grueda_29082004_begin
        absoluteFileLocation.append(BusinessRules.TEST_OBJECTS_FOLDER);
        //grueda_29082004_end
        absoluteFileLocation.append(BusinessRules.URL_SEPARATOR);
        absoluteFileLocation.append(p_TestObjectReference.getName());
        //grueda30122004_begin
        File testObjectDirectory = new File(absoluteFileLocation.toString());
        testObjectDirectory.mkdir();
          absoluteFileLocation.append(BusinessRules.URL_SEPARATOR);
          absoluteFileLocation.append(reference.getFileLocation());
          StringBuffer absoluteFilePath = new StringBuffer();
          //grueda29082004_begin
          //absoluteFilePath.append(p_ProjectReference.getM_Path());
          absoluteFilePath.append(absoluteFileLocation.toString());
          testObjectDirectory = new File(absoluteFilePath.toString());
          testObjectDirectory.mkdir();
          //grueda29082004_end
          absoluteFilePath.append(BusinessRules.URL_SEPARATOR);
          //grueda29082004_begin
          //absoluteFilePath.append(reference.getFilePath());
          absoluteFilePath.append(reference.getFileName());
        //grueda29082004_end
        //File dir = new File(absoluteFileLocation.toString());
        File textFile = new File(absoluteFilePath.toString());
        //if (!dir.exists()) {
        //boolean success = dir.mkdir();
        //}
        boolean success = false;
        try {
              success = textFile.createNewFile();
              return true;
        } catch(java.io.IOException e) {
        	Logger.getLogger(this.getClass()).error(e);
          return false;
        }
        //grueda30122004_end
        /*
        if (!textFile.exists()) {
            try { textFile.createNewFile(); } catch (java.io.IOException e) { };
        }
        else {
            textFile.delete();
            try { textFile.createNewFile(); } catch (java.io.IOException e) { };
        }
        */
    }
    //grueda22082004_end

   

    public String getProjectName(String p_ProjectFileName){
      StringBuffer projectName = new StringBuffer();
      int lastIndex = p_ProjectFileName.indexOf(".cpr.xml");
      int i = 0;

      while ( i < lastIndex) {
        projectName.append(p_ProjectFileName.charAt(i));
        i++;
      }
      return projectName.toString();
    }
    //Result: Kopie;   //"c:\projects\Kopie"  "c:\projects"
    public String getDirectoryName(String p_AbsoluteProjectPath, String p_AbsoluteProjectPathOfParent){
      StringBuffer directoryName = new StringBuffer();
      if( p_AbsoluteProjectPathOfParent.equals("") ) {
        return p_AbsoluteProjectPath;
      }
      int length1 = p_AbsoluteProjectPath.length();
      int length2 = p_AbsoluteProjectPathOfParent.length();
      for( int i = 0; i < length1; i++) {
        char c = p_AbsoluteProjectPath.charAt(i);
        if( i >= length2) {
          directoryName.append(c);
        }
      }
      //grueda10102004_begin
      if( directoryName.toString().startsWith(BusinessRules.URL_SEPARATOR) || directoryName.toString().startsWith(System.getProperty("file.separator")) ){
        directoryName.deleteCharAt(0);
        return directoryName.toString();
      }
      else {
        return directoryName.toString();
      }
      //grueda10102004_end
    }

  //grueda20092004_begin
  public String buildAbsoluteRemoteTestObjectPath(ProjectReference p_ProjectReference, TestObjectReference p_TestObjectReference){
    return buildAbsoluteTestObjectPath(p_ProjectReference, p_TestObjectReference);
  }

  //grueda08112004_begin
  public boolean exists(String p_Path){
    File file = new File(p_Path);
    if( file.exists() ) {
      return true;
    }
    else {
      return false;
    }
  }

  //grueda26122004_begin
  public boolean checkInLocal(ProjectReference p_ProjectReference, TestObjectReference p_TestObjectReference, TestObjectReference p_LocalTestObjectReference,TestObject p_TestObject, Session2 p_Session){
    if( !exists(p_ProjectReference.getFilePath())) {
		  return false;
    }
  //grueda26122004_end
    String absoluteRemoteTestObjectPath =  buildAbsoluteRemoteTestObjectPath(p_ProjectReference, p_TestObjectReference);
    //grueda12122004_begin
    /*
    if( !exists(absoluteRemoteTestObjectPath)) {
      return false;
    }
    */
    //grueda12122004_end
    /*p_TestObjectReference*/p_TestObject.setAccessState(BusinessRules.ACCESS_STATE_CHECKED_IN);
    /*p_TestObjectReference*/p_TestObject.setUser(p_Session.getM_User());
    LocalProjectReference localProjectReference = p_ProjectReference.getM_LocalProjectReference();
    if( localProjectReference == null) {
      return false;
    }
    String absoluteLocalCompressDestinationFilePath = compressCompleteTestObject(localProjectReference,p_TestObjectReference); //create a zip file
    moveCompressedFile(absoluteLocalCompressDestinationFilePath, absoluteRemoteTestObjectPath);
    String absoluteRemoteCompressedTestObjectFilePath = buildAbsoluteRemoteCompressedTestObjectFilePath(p_ProjectReference, p_TestObjectReference);
    String absoluteRemoteProjectPath = p_ProjectReference.getPath();
	uncompressTestObjectFile(absoluteRemoteCompressedTestObjectFilePath, absoluteRemoteProjectPath);
    deleteCompressedFile(absoluteRemoteCompressedTestObjectFilePath);
    //New
    deleteLocalTestObject(localProjectReference, p_TestObjectReference);
    //grueda26122004_begin
    saveLocalProject(p_LocalTestObjectReference, localProjectReference,p_TestObject, p_Session.getM_User());
    p_LocalTestObjectReference = null;
    //grueda26122004_end
    return true;
  //grueda08112004_end

  }
    public void deleteDirectoryFiles(File dir) {
        File[] fileArray = dir.listFiles();
        try {
            if (fileArray != null) {
                for (int i = 0; i < fileArray.length; i++) {
                    if (fileArray[i].isFile()) {
						fileArray[i].delete();
                    }
                    else {
                        deleteDirectoryFiles(fileArray[i]);
                        fileArray[i].delete();
                    }
                }
            }
        } catch (SecurityException ex) {
        	Logger.getLogger(this.getClass()).error(ex.getMessage());
        }
    }

  public void deleteLocalProjectReferenceIsEmpty(ProjectReference p_ProjectReference,SessionManager p_SessionManager, Session2 p_Session){
     LocalProjectReference p_localProjectReference = p_ProjectReference.getM_LocalProjectReference();
    if( p_localProjectReference != null) {

        StringBuffer pathFolderTestObjects=new StringBuffer();
        pathFolderTestObjects.append(p_localProjectReference.getPath());
        pathFolderTestObjects.append(System.getProperty("file.separator"));
        pathFolderTestObjects.append(BusinessRules.TEST_OBJECTS_FOLDER);
        File file = new File(pathFolderTestObjects.toString());
        Object[] files=file.listFiles();
        if(files.length == 0){
            deleteLocalProjectFiles(p_localProjectReference);
			p_ProjectReference.setM_LocalProjectReference(null);
            p_SessionManager.saveSession2(p_Session);

        }
           /* p_localProjectReference=null;
        }*/
    }
  }

  public void deleteLocalProjectFiles(LocalProjectReference p_localProjectReference){
     deleteDirectoryFiles(new File(p_localProjectReference.getPath()));
     File folderProject=new File(p_localProjectReference.getPath());
     folderProject.delete();
  }
  //grueda26122004_begin
  public void saveLocalProject(TestObjectReference p_LocalTestObjectReference, ProjectReference p_LocalProjectReference,TestObject p_TestObject, String p_User) {
    if( p_LocalTestObjectReference == null) {
      return;
    }
    /*p_LocalTestObjectReference*/p_TestObject.setAccessState(BusinessRules.ACCESS_STATE_CHECKED_IN);
    /*p_LocalTestObjectReference*/p_TestObject.setUser(p_User);
    Project2 localProject = readProject2ByReference(p_LocalProjectReference);

    int numOfTestObjectReferences = localProject.getTestObjectReferences().size();
    if(numOfTestObjectReferences != 0){
    TestObjectReference testObjectReference = null;
    for( int i = 0; i < numOfTestObjectReferences; i++) {
      testObjectReference = (TestObjectReference) localProject.getTestObjectReferences().elementAt(i);
      if(testObjectReference.getName().equals(p_LocalTestObjectReference.getName())) {
        /*testObjectReference*/p_TestObject.setAccessState(BusinessRules.ACCESS_STATE_CHECKED_IN);
        /*testObjectReference*/p_TestObject.setUser(p_User);
      }
    }

    if( p_LocalProjectReference == null) {
      return;
    }
    File file = new File(p_LocalProjectReference.getPath());
    file.mkdirs();
    writeProject2ToFile(localProject, p_LocalProjectReference.getFilePath());
    }
    else{

    }
  }
  //grueda26122004_end

  //grueda26122004_begin
  public boolean checkOutLocal(Project2 project, ProjectReference p_ProjectReference, TestObjectReference p_TestObjectReference, TestObjectReference p_LocalTestObjectReference,TestObject p_TestObject, Session2 p_Session){
      //grueda10112004_begin
      if( !exists(p_ProjectReference.getFilePath())) {
        return false;
      }
      //grueda10112004_end
      String absoluteLocalCaseMakerPath = buildAbsoluteCaseMakerLocalDataPath();
	  /*p_TestObjectReference*/p_TestObject.setAccessState(BusinessRules.ACCESS_STATE_CHECKED_OUT_LOCAL);
      /*p_TestObjectReference*/p_TestObject.setUser(p_Session.getM_User());


      LocalProjectReference localProjectReference = this.buildLocalProjectReference(absoluteLocalCaseMakerPath, p_ProjectReference);
      p_ProjectReference.setM_LocalProjectReference(localProjectReference);

      Project2 p_Project = project;//p_TestObjectReference.getM_Project();
      if( p_Project.getAccessState().equals(BusinessRules.ACCESS_STATE_CHECKED_OUT) && p_Project.getUser().equalsIgnoreCase(p_Session.getM_User()))
      		writeProject2ToFile(p_Project, p_ProjectReference.getFilePath());
      m_SessionManager.writeSession2ToFile(p_Session);

	  String absoluteCompressProjectDestinationFilePath = compressCompleteProject(p_ProjectReference,p_TestObjectReference); //create a zip file
	  moveCompressedFile(absoluteCompressProjectDestinationFilePath, absoluteLocalCaseMakerPath);
      String absoluteLocalCompressedProjectFilePath = buildAbsoluteLocalCompressedProjectFilePath(absoluteLocalCaseMakerPath, p_ProjectReference);
	  uncompressProjectFile(absoluteLocalCompressedProjectFilePath, absoluteLocalCaseMakerPath);
      deleteCompressedFile(absoluteLocalCompressedProjectFilePath);
	  //grueda30122004_begin
	  if( p_LocalTestObjectReference != null){
			Project2 localProject = this.readProject2ByReference(localProjectReference);
			p_LocalTestObjectReference.setM_Project(localProject);
			p_LocalTestObjectReference.setTimeStamp(new Date());
 	      //  p_LocalTestObjectReference.setM_AccessState(BusinessRules.ACCESS_STATE_CHECKED_OUT_LOCAL);
            /*p_TestObjectReference*/p_TestObject.setUser(p_Session.getM_User());
	  }
      //grueda30122004_end

      return true;
  }
  //grueda26122004_end

  //grueda21102004_begin
  public LocalProjectReference buildLocalProjectReference(String p_AbsoluteLocalCaseMakerPath, ProjectReference p_ProjectReference) {
    LocalProjectReference localProjectReference = new LocalProjectReference();

    StringBuffer absoluteLocalPath = new StringBuffer();
    absoluteLocalPath.append(p_AbsoluteLocalCaseMakerPath);
    absoluteLocalPath.append(BusinessRules.URL_SEPARATOR);
    absoluteLocalPath.append(p_ProjectReference.getName());

   // localProjectReference.setName(p_ProjectReference.getName());//, absoluteLocalPath.toString());
    localProjectReference.setPath(absoluteLocalPath.toString());
    localProjectReference.setFileName(p_ProjectReference.getFileName());
    localProjectReference.setM_ProjectReference(p_ProjectReference);
    return localProjectReference;
  }
  //grueda21102004_end

  String buildAbsoluteLocalProjectPath( String p_AbsoluteLocalCaseMakerPath, ProjectReference p_ProjectReference) {
    StringBuffer s = new StringBuffer();
    s.append(p_AbsoluteLocalCaseMakerPath);
    s.append(BusinessRules.URL_SEPARATOR);
    s.append(p_ProjectReference.getName());
    s.append(BusinessRules.URL_SEPARATOR);
    s.append(p_ProjectReference.getFileName());
    return s.toString();
  }

  public String buildAbsoluteLocalCompressedProjectFilePath(String p_AbsoluteLocalCaseMakerPath, ProjectReference p_ProjectReference) {
      StringBuffer absoluteLocalCompressedFilePath = new StringBuffer();
      absoluteLocalCompressedFilePath.append(p_AbsoluteLocalCaseMakerPath);
      absoluteLocalCompressedFilePath.append(BusinessRules.URL_SEPARATOR);
      absoluteLocalCompressedFilePath.append(p_ProjectReference.getName());
      absoluteLocalCompressedFilePath.append(BusinessRules.ZIP_EXTENSION);
      return absoluteLocalCompressedFilePath.toString();
  }

  public String buildAbsoluteRemoteCompressedTestObjectFilePath(ProjectReference p_ProjectReference, TestObjectReference p_TestObjectReference) {
      StringBuffer absoluteLocalCompressedFilePath = new StringBuffer();
      absoluteLocalCompressedFilePath.append(p_ProjectReference.getPath());
      absoluteLocalCompressedFilePath.append(BusinessRules.URL_SEPARATOR);
      absoluteLocalCompressedFilePath.append(p_TestObjectReference.getPath());
      absoluteLocalCompressedFilePath.append(BusinessRules.URL_SEPARATOR);
      absoluteLocalCompressedFilePath.append(p_TestObjectReference.getName());
      absoluteLocalCompressedFilePath.append(BusinessRules.ZIP_EXTENSION);
      return absoluteLocalCompressedFilePath.toString();
  }


  public String buildAbsoluteLocalTestObjectPath(ProjectReference p_ProjectReference, TestObjectReference p_TestObjectReference){
    StringBuffer absoluteLocalTestObjectPath = new StringBuffer();
    absoluteLocalTestObjectPath.append(buildAbsoluteCaseMakerLocalDataPath());
    absoluteLocalTestObjectPath.append(BusinessRules.URL_SEPARATOR);
    absoluteLocalTestObjectPath.append(p_ProjectReference.getName());
    absoluteLocalTestObjectPath.append(BusinessRules.URL_SEPARATOR);
    absoluteLocalTestObjectPath.append(BusinessRules.TEST_OBJECTS_FOLDER);
    return absoluteLocalTestObjectPath.toString();
  }

  public void uncompressTestObjectFileToRemoteTarget(ZipEntry entry, ZipInputStream in , String p_TestObjectDirectoryName, String p_ProjectDirectoryName) throws IOException {
            // Get the entry name
            //String zipEntryName = ((ZipEntry)entries.nextElement()).getName();
            String filePath = entry.getName();
            String remoteDataFilePath = buildRemoteDataFilePath(filePath, p_TestObjectDirectoryName, p_ProjectDirectoryName);
			// Open the output file
			//String outFilename = "o";
            File outFile = new File(remoteDataFilePath);
            if( outFile != null) {
              if( outFile.getParentFile() != null) {
                outFile.getParentFile().mkdirs();
              }
            }
			OutputStream out = new FileOutputStream(remoteDataFilePath);

			// Transfer bytes from the ZIP file to the output file
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			// Close the streams
			out.close();

  }

  public void uncompressProjectFileToLocalTarget(ZipEntry entry, ZipInputStream in , String directoryName) throws IOException {
            // Get the entry name
            //String zipEntryName = ((ZipEntry)entries.nextElement()).getName();
            String filePath = entry.getName();
            String localDataFilePath = buildLocalDataFilePath(filePath, directoryName);
			// Open the output file
			//String outFilename = "o";
            File outFile = new File(localDataFilePath);
            if( outFile != null) {
              if( outFile.getParentFile() != null) {
                outFile.getParentFile().mkdirs();
              }
            }
			OutputStream out = new FileOutputStream(localDataFilePath);

			// Transfer bytes from the ZIP file to the output file
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			// Close the streams
			out.close();
  }


  public void uncompressProjectFile( String p_From, String p_To){
    //reads a ZIP file and decompresses the first entry.
    ZipInputStream in = null;
    ZipEntry entry = null;
    try {
        // Open the ZIP file
        //String inFilename = //"infile.zip";
        in = new ZipInputStream(new FileInputStream(p_From));
        // Get the first entry
        //ZipEntry entry = in.getNextEntry();
        // Enumerate each entry
        if( in == null) {
          return;
        }
        entry = in.getNextEntry();
        if( entry == null) {
          return;
        }
        //grueda17102004_begin
        String directoryName = findProjectDirectoryName(findFileName(entry.getName()));
        //grueda17102004_end
        while( entry != null) {
            uncompressProjectFileToLocalTarget(entry, in, directoryName);
            entry = in.getNextEntry();
        }
        in.close();
	} catch (IOException e) {
		Logger.getLogger(this.getClass()).error(e);
	}
  }

  public void uncompressTestObjectFile( String p_From, String p_ProjectDirectoryName){
    //reads a ZIP file and decompresses the first entry.
    ZipInputStream in = null;
    ZipEntry entry = null;
    try {
        // Open the ZIP file
        //String inFilename = //"infile.zip";
        in = new ZipInputStream(new FileInputStream(p_From));
        // Get the first entry
        //ZipEntry entry = in.getNextEntry();
        // Enumerate each entry
        if( in == null) {
          return;
        }
        entry = in.getNextEntry();
        if( entry == null) {
          return;
        }
        //grueda17102004_begin
        String relativeTestObjectPath = findTestObjectDirectoryName();
        //grueda17102004_end
        while( entry != null){
            uncompressTestObjectFileToRemoteTarget(entry, in, relativeTestObjectPath, p_ProjectDirectoryName);
            entry = in.getNextEntry();
       }
        in.close();
	} catch (IOException e) {
		Logger.getLogger(this.getClass()).error(e);
	}
  }

  //grueda17102004_begin

  public String buildRemoteDataFilePath(String p_FilePath, String p_TestObjectDirectory, String p_ProjectDirectoryName) {
    String fileName = findFileName(p_FilePath);
    String directoryName = findTestObjectDirectoryName(p_FilePath, p_TestObjectDirectory);
    StringBuffer localDataFolderBuffer = new StringBuffer(p_ProjectDirectoryName);
    localDataFolderBuffer.append(BusinessRules.URL_SEPARATOR);
    localDataFolderBuffer.append(directoryName);
    localDataFolderBuffer.append(BusinessRules.URL_SEPARATOR);
    localDataFolderBuffer.append(fileName);
    return localDataFolderBuffer.toString();
  }

  public String buildLocalDataFilePath(String p_FilePath, String p_ProjectDirectoryName) {
    String fileName = findFileName(p_FilePath);
    String directoryName = findProjectDirectoryName(p_FilePath, p_ProjectDirectoryName);
    String absoluteCaseMakerPath = this.m_SessionManager.getM_InstallationDirectory();
    StringBuffer localDataFolderBuffer = new StringBuffer(absoluteCaseMakerPath);
    localDataFolderBuffer.append(BusinessRules.URL_SEPARATOR);
    localDataFolderBuffer.append(BusinessRules.LOCAL_DATA_FOLDER);
    localDataFolderBuffer.append(BusinessRules.URL_SEPARATOR);
    localDataFolderBuffer.append(directoryName);
    localDataFolderBuffer.append(BusinessRules.URL_SEPARATOR);
    localDataFolderBuffer.append(fileName);
    return localDataFolderBuffer.toString();
  }

  public String findProjectDirectoryName(String p_ProjectFileName) {
    String[] splitString = p_ProjectFileName.split(BusinessRules.FILE_PROJECT_EXTENSION);
    return splitString[0];
  }
  //grueda22102004_begin
  public String findTestObjectDirectoryName() {
    return BusinessRules.TEST_OBJECTS_FOLDER;
  }
  //grueda22102004_end

  public String findTestObjectDirectoryName(String p_FilePath, String p_TestObjectDirectory) {
    StringBuffer directoryName = new StringBuffer();
    String fileSeparator = BusinessRules.URL_SEPARATOR;
    ArrayList splitString = new ArrayList();

    StringTokenizer parser = new StringTokenizer(p_FilePath,fileSeparator);
    int j = 0;
    while (parser.hasMoreTokens()) {
      String word = parser.nextToken();
      splitString.add(word);
    }

    int splitLength = splitString.size();
    for( int i = splitLength - 2; i >= 0; i--) {
      if( !splitString.get(i).equals(p_TestObjectDirectory)) {
        directoryName.insert(0,splitString.get(i));
        directoryName.insert(0,fileSeparator);
      }
      else {
        break;
      }
    }
    directoryName.insert(0, p_TestObjectDirectory);
    return directoryName.toString();
  }

  public String findProjectDirectoryName(String p_FilePath, String p_ProjectDirectory) {
    StringBuffer directoryName = new StringBuffer();
    String fileSeparator = BusinessRules.URL_SEPARATOR;
    ArrayList splitString = new ArrayList();

    StringTokenizer parser = new StringTokenizer(p_FilePath,fileSeparator);
    int j = 0;
    while (parser.hasMoreTokens()) {
      String word = parser.nextToken();
      splitString.add(word);
    }

    int splitLength = splitString.size();
    for( int i = splitLength - 2; i >= 0; i--) {
      if( !splitString.get(i).equals(p_ProjectDirectory)) {
        directoryName.insert(0,splitString.get(i));
        directoryName.insert(0,fileSeparator);
      }
      else {
        break;
      }
    }
    directoryName.insert(0, p_ProjectDirectory);
    return directoryName.toString();
  }


  public String findFileName(String p_FilePath) {
    int stringLength = p_FilePath.length();
    StringBuffer fileName = new StringBuffer();
    String fileSeparator = BusinessRules.URL_SEPARATOR;
    char fileSeparatorChar[] = fileSeparator.toCharArray();

    int i = 0;
    for( i = stringLength -1; i >= 0;  i--) {
      char c = p_FilePath.charAt(i);
      if( c == fileSeparatorChar[0]) {
        break;
      }
    }

    for( int j = i+1; j < stringLength; j++) {
      fileName.append(p_FilePath.charAt(j));
    }
    return fileName.toString();
  }
  //grueda17102004_end

  public String buildAbsoluteCaseMakerLocalDataPath(){
    StringBuffer localPath = new StringBuffer();
    localPath.append(m_SessionManager.getM_InstallationDirectory());
    localPath.append(BusinessRules.URL_SEPARATOR);
    localPath.append(BusinessRules.LOCAL_DATA_FOLDER);
    return localPath.toString();
  }

  public String buildAbsoluteTestObjectPath(ProjectReference p_ProjectReference, TestObjectReference p_TestObjectReference) {
    StringBuffer absoluteTestObjectPath = new StringBuffer();
    absoluteTestObjectPath.append(p_ProjectReference.getPath());
    absoluteTestObjectPath.append(BusinessRules.URL_SEPARATOR);
    absoluteTestObjectPath.append(p_TestObjectReference.getPath());
    return absoluteTestObjectPath.toString();
  }

  public String buildAbsoluteReportsPath(ProjectReference p_ProjectReference, TestObjectReference p_TestObjectReference) {
    StringBuffer absoluteReportsPath = new StringBuffer();
    absoluteReportsPath.append(buildAbsoluteTestObjectPath(p_ProjectReference, p_TestObjectReference));
    absoluteReportsPath.append(BusinessRules.URL_SEPARATOR);
    absoluteReportsPath.append(p_TestObjectReference.getName());
    absoluteReportsPath.append(BusinessRules.REPORT_CARPET_NAME);

    return absoluteReportsPath.toString();
  }

  public String buildAbsoluteTestObjectFilePath(String p_ProjectPath, TestObjectReference p_TestObjectReference){
    StringBuffer absoluteTestObjectFilePath = new StringBuffer();
    absoluteTestObjectFilePath.append(p_ProjectPath);
    absoluteTestObjectFilePath.append(BusinessRules.URL_SEPARATOR);
    absoluteTestObjectFilePath.append(p_TestObjectReference.getFilePath());
    return absoluteTestObjectFilePath.toString();
  }


  public String buildAbsoluteTestObjectFilePath(ProjectReference p_ProjectReference, TestObjectReference p_TestObjectReference){
    StringBuffer absoluteTestObjectFilePath = new StringBuffer();
    absoluteTestObjectFilePath.append(buildAbsoluteTestObjectPath(p_ProjectReference, p_TestObjectReference));
    absoluteTestObjectFilePath.append(BusinessRules.URL_SEPARATOR);
    absoluteTestObjectFilePath.append(p_TestObjectReference.getFileName());
    return absoluteTestObjectFilePath.toString();
  }

  public String buildAbsoluteBRulesPath(ProjectReference p_ProjectReference, TestObjectReference p_TestObjectReference) {
    StringBuffer absoluteBRulesPath = new StringBuffer();
    absoluteBRulesPath.append(buildAbsoluteTestObjectPath(p_ProjectReference, p_TestObjectReference));
    absoluteBRulesPath.append(BusinessRules.URL_SEPARATOR);
    absoluteBRulesPath.append(p_TestObjectReference.getName());
    absoluteBRulesPath.append(BusinessRules.URL_SEPARATOR);
    absoluteBRulesPath.append(BusinessRules.BUSINESSRULES_FOLDER_NAME);
    return absoluteBRulesPath.toString();
  }

  public String buildAbsoluteBRulesFilePath(ProjectReference p_ProjectReference, TestObjectReference p_TestObjectReference) {
    StringBuffer absoluteBRulesFilePath = new StringBuffer();
    absoluteBRulesFilePath.append(buildAbsoluteBRulesPath(p_ProjectReference, p_TestObjectReference));
    absoluteBRulesFilePath.append(BusinessRules.URL_SEPARATOR);
    absoluteBRulesFilePath.append(BusinessRules.BUSINESSRULES_FILE_NAME);
    absoluteBRulesFilePath.append(BusinessRules.BUSINESSRULES_FILE_EXTENSION);
    return absoluteBRulesFilePath.toString();
  }

  public String buildAbsoluteBRulesFilePath(String destination,TestObjectReference p_TestObjectReference) {
    StringBuffer absoluteBRulesFilePath = new StringBuffer();
    absoluteBRulesFilePath.append(destination);
    absoluteBRulesFilePath.append(BusinessRules.URL_SEPARATOR);
    absoluteBRulesFilePath.append(p_TestObjectReference.getPath());
    absoluteBRulesFilePath.append(BusinessRules.URL_SEPARATOR);
    absoluteBRulesFilePath.append(p_TestObjectReference.getName());
    absoluteBRulesFilePath.append(BusinessRules.URL_SEPARATOR);
    absoluteBRulesFilePath.append(BusinessRules.BUSINESSRULES_FOLDER_NAME);
    absoluteBRulesFilePath.append(BusinessRules.URL_SEPARATOR);
    absoluteBRulesFilePath.append(BusinessRules.BUSINESSRULES_FILE_NAME);
    absoluteBRulesFilePath.append(BusinessRules.BUSINESSRULES_FILE_EXTENSION);
    return absoluteBRulesFilePath.toString();
  }


  public String buildAbsoluteTestDataPath(ProjectReference p_ProjectReference, TestObjectReference p_TestObjectReference) {
    StringBuffer absoluteTestDataPath = new StringBuffer();
    absoluteTestDataPath.append(buildAbsoluteTestObjectPath(p_ProjectReference, p_TestObjectReference));
    absoluteTestDataPath.append(BusinessRules.URL_SEPARATOR);
    absoluteTestDataPath.append(p_TestObjectReference.getName());
    absoluteTestDataPath.append(BusinessRules.URL_SEPARATOR);
    absoluteTestDataPath.append(BusinessRules.TEST_DATA_FOLDER);
    return absoluteTestDataPath.toString();
  }

  public String buildAbsoluteTestDataReportsPath(ProjectReference p_ProjectReference, TestObjectReference p_TestObjectReference) {
    StringBuffer absoluteTestDataReportsPath = new StringBuffer();
    absoluteTestDataReportsPath.append(buildAbsoluteTestObjectPath(p_ProjectReference, p_TestObjectReference));
    absoluteTestDataReportsPath.append(BusinessRules.URL_SEPARATOR);
    absoluteTestDataReportsPath.append(p_TestObjectReference.getName());
    absoluteTestDataReportsPath.append(BusinessRules.URL_SEPARATOR);
    absoluteTestDataReportsPath.append(BusinessRules.TEST_DATA_FOLDER);
    absoluteTestDataReportsPath.append(BusinessRules.URL_SEPARATOR);
    absoluteTestDataReportsPath.append(BusinessRules.TESTDATA_REPORT_CARPET_NAME);
    return absoluteTestDataReportsPath.toString();
  }


  public String buildAbsoluteTestDataFilePath(ProjectReference p_ProjectReference, TestObjectReference p_TestObjectReference) {
    StringBuffer absoluteTestDataFilePath = new StringBuffer();
    absoluteTestDataFilePath.append(buildAbsoluteTestDataPath(p_ProjectReference, p_TestObjectReference));
    absoluteTestDataFilePath.append(BusinessRules.URL_SEPARATOR);
    absoluteTestDataFilePath.append(BusinessRules.TESTDATA_NAME_OBJECT_TESTDATA);
    absoluteTestDataFilePath.append(BusinessRules.FILE_TESTDATA_EXTENSION);
    return absoluteTestDataFilePath.toString();
  }

  //svonborries_05072007_begin
  public String buildAbsoluteTestDataFilePath(String p_From, TestObjectReference p_TestObjectReference, TDStructureReference p_structureReference) {
    StringBuffer absoluteTestDataFilePath = new StringBuffer();
    absoluteTestDataFilePath.append(p_From);
    absoluteTestDataFilePath.append(BusinessRules.URL_SEPARATOR);
    absoluteTestDataFilePath.append(p_TestObjectReference.getPath());
    absoluteTestDataFilePath.append(BusinessRules.URL_SEPARATOR);
    absoluteTestDataFilePath.append(p_TestObjectReference.getName());
    absoluteTestDataFilePath.append(BusinessRules.URL_SEPARATOR);
    if(p_structureReference == null || p_structureReference.getM_Path().equalsIgnoreCase(""))
    	absoluteTestDataFilePath.append(BusinessRules.TEST_DATA_FOLDER);
    else
    	absoluteTestDataFilePath.append(p_structureReference.getM_Path());
    absoluteTestDataFilePath.append(BusinessRules.URL_SEPARATOR);
    if(p_structureReference == null ||p_structureReference.getM_Name().equalsIgnoreCase(""))
    	absoluteTestDataFilePath.append(BusinessRules.TESTDATA_NAME_OBJECT_TESTDATA);
    else
    	absoluteTestDataFilePath.append(p_structureReference.getM_Name());
    absoluteTestDataFilePath.append(BusinessRules.FILE_TESTDATA_EXTENSION);
    return absoluteTestDataFilePath.toString();
  }
  //svonborries_05072007_end
  //grueda17122004_begin
  public void compressFile(String p_FileName, ZipOutputStream p_destinationStream) throws IOException{
        byte[] buf = new byte[1024];
        try {
			FileInputStream in = new FileInputStream(p_FileName);
			// Add ZIP entry to output stream.
			p_destinationStream.putNextEntry(new ZipEntry(p_FileName));
			// Transfer bytes from the file to the ZIP file
			int len;
			while ((len = in.read(buf)) > 0) {
			  p_destinationStream.write(buf, 0, len);
			}
			// Complete the entry
			p_destinationStream.closeEntry();
			in.close();
			StringBuffer log = new StringBuffer();
			log.append(p_FileName);
			log.append(" compressed.");
			Logger.getLogger(this.getClass()).info(log.toString());
        }catch(IOException e) {
        	Logger.getLogger(this.getClass()).error(e);
          return;
        }
  }
  //grueda17122004_end
  public String[] findAbsoluteFilePathsOfCompleteProject(ProjectReference p_ProjectReference, TestObjectReference p_TestObjectReference){
    String[] fileNames = new String[4];
    fileNames[0] = p_ProjectReference.getFilePath();
    fileNames[1] = buildAbsoluteTestObjectFilePath(p_ProjectReference, p_TestObjectReference);
    fileNames[2] = buildAbsoluteBRulesFilePath(p_ProjectReference, p_TestObjectReference);
    fileNames[3] = buildAbsoluteTestDataFilePath(p_ProjectReference, p_TestObjectReference);

    return fileNames;
  }

  public String[] findAbsoluteFilePathsOfCompleteTestObject(ProjectReference p_ProjectReference, TestObjectReference p_TestObjectReference){
    String[] fileNames = new String[3];
    fileNames[0] = buildAbsoluteTestObjectFilePath(p_ProjectReference, p_TestObjectReference);
    fileNames[1] = buildAbsoluteBRulesFilePath(p_ProjectReference, p_TestObjectReference);
    fileNames[2] = buildAbsoluteTestDataFilePath(p_ProjectReference, p_TestObjectReference);

    return fileNames;
  }


  public String findAbsoluteCompressTestObjectDestinationFilePath(ProjectReference p_ProjectReference, TestObjectReference p_TestObjectReference){
    StringBuffer compressDestinationFilePath = new StringBuffer();
    compressDestinationFilePath.append( buildAbsoluteTestObjectPath(p_ProjectReference, p_TestObjectReference));
    compressDestinationFilePath.append( BusinessRules.URL_SEPARATOR);
    compressDestinationFilePath.append( p_TestObjectReference.getName());
    compressDestinationFilePath.append( BusinessRules.ZIP_EXTENSION);
    return compressDestinationFilePath.toString();
  }

  public String findAbsoluteCompressProjectDestinationFilePath(ProjectReference p_ProjectReference, TestObjectReference p_TestObjectReference){
    StringBuffer compressDestinationFilePath = new StringBuffer();
    File file = new File(p_ProjectReference.getPath());

    compressDestinationFilePath.append(p_ProjectReference.getPath());
    compressDestinationFilePath.append( BusinessRules.ZIP_EXTENSION);
    return compressDestinationFilePath.toString();
  }

  //grueda17122004_begin
  public void deleteLocalTestObject(ProjectReference p_ProjectReference, TestObjectReference p_TestObjectReference) {
    StringBuffer testObjectDirectoryPath = new StringBuffer();
    StringBuffer testObjectFilePath = new StringBuffer();
    testObjectDirectoryPath.append( buildAbsoluteTestObjectPath(p_ProjectReference, p_TestObjectReference));
    testObjectDirectoryPath.append( BusinessRules.URL_SEPARATOR);
    testObjectDirectoryPath.append( p_TestObjectReference.getName());
    File testObjectDirectory = new File(testObjectDirectoryPath.toString());
    File[] files = testObjectDirectory.listFiles();
    if( files != null){
		//grueda29102004_begin
		for( int i = 0; i < files.length; i++) {
		  deleteFile(files[i].getPath());
		}
		//grueda29102004_end
    }
    deleteFile(testObjectDirectoryPath.toString());
    testObjectFilePath.append(testObjectDirectoryPath);
    testObjectFilePath.append(BusinessRules.FILE_TESTOBJECT_EXTENSION);
    deleteFile(testObjectFilePath.toString());
  }
  //grueda17122004_end

  public void deleteFile(String p_FilePath){
    File deletingFile = new File(p_FilePath);
    int numOfChildren = 0;
    if( deletingFile != null) {
      if( deletingFile.exists()) {
        File[] childrenFiles = deletingFile.listFiles();
        if( childrenFiles != null) {
			if( childrenFiles.length > 0) {
			  for( int i = 0; i < childrenFiles.length; i++) {
                //grueda29102004_begin
				deleteFile(childrenFiles[i].getPath());
                //grueda29102004_end
			  }
              deletingFile.delete();
			}
			else {
			  deletingFile.delete();
			  return;
			}
        }
        else {
          deletingFile.delete();
          return;
        }
      }
    }
  }


  String compressCompleteTestObject(ProjectReference p_ProjectReference, TestObjectReference p_TestObjectReference) {

    //Local muss be added...
    String[] absoluteFilePaths = findAbsoluteFilePathsOfCompleteTestObject(p_ProjectReference, p_TestObjectReference);
    String absoluteCompressDestinationFilePath = findAbsoluteCompressTestObjectDestinationFilePath(p_ProjectReference, p_TestObjectReference); //"outfile.zip";
    // Create a buffer for reading the files
    ZipOutputStream out = null;
    byte[] buf = new byte[1024];
    try {
        // Create the ZIP file
        out = new ZipOutputStream(new FileOutputStream(absoluteCompressDestinationFilePath));
         // Compress the files
        for (int i=0; i<absoluteFilePaths.length; i++) {
          compressFile(absoluteFilePaths[i], out);
        }
         // Complete the ZIP file
        out.close();
        Logger.getLogger(this.getClass()).debug("Complete Test Object has been compressed.");
      } catch (IOException e) {
    	  Logger.getLogger(this.getClass()).error(e);
      return absoluteCompressDestinationFilePath;
    }
    return absoluteCompressDestinationFilePath;
  }

  String compressCompleteProject(ProjectReference p_ProjectReference, TestObjectReference p_TestObjectReference){
    String[] absoluteFilePaths = findAbsoluteFilePathsOfCompleteProject(p_ProjectReference, p_TestObjectReference);
    String absoluteCompressDestinationFilePath = findAbsoluteCompressProjectDestinationFilePath(p_ProjectReference, p_TestObjectReference); //"outfile.zip";
    // Create a buffer for reading the files
    ZipOutputStream out = null;
    byte[] buf = new byte[1024];
    try {
        // Create the ZIP file
        out = new ZipOutputStream(new FileOutputStream(absoluteCompressDestinationFilePath));
         // Compress the files
        for (int i=0; i<absoluteFilePaths.length; i++) {
          compressFile(absoluteFilePaths[i], out);
        }
         // Complete the ZIP file
        out.close();
        Logger.getLogger(this.getClass()).debug("Complete Test Object has been compressed.");
      } catch (IOException e) {
    	  Logger.getLogger(this.getClass()).error(e);
      return absoluteCompressDestinationFilePath;
    }
    return absoluteCompressDestinationFilePath;
  }

   // Copies src file to dst file.
    // If the dst file does not exist, it is created
    void copy(File src, File dst) throws IOException {
        InputStream in = new FileInputStream(src);
        OutputStream out = new FileOutputStream(dst);

        // Transfer bytes from in to out
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
    }

    void deleteCompressedFile(String p_AbsoluteLocalCompressedFilePath){
      File compressedFile = new File(p_AbsoluteLocalCompressedFilePath);
      compressedFile.delete();
    }

    void moveCompressedFile(String p_FileOrDirectoryToBeMoved, String p_DestinationDirectory) {
      //Moving a File or Directory to Another Directory
      // File (or directory) to be moved
      File file = new File(p_FileOrDirectoryToBeMoved);
      if( file == null) {
        return;
      }
      if( !file.exists() ) {
        return;
      }
      // Destination directory
      File dir = new File(p_DestinationDirectory);
      if( dir != null) {
		  if( !dir.exists()) {
			dir.mkdirs();
		  }
      }
      else {
        return;
      }
      // Move file to new directory
      boolean success = file.renameTo(new File(dir, file.getName()));
      if (!success) {
        //File was not successfully moved
    	  Logger.getLogger(this.getClass()).info("File was not successfully moved");
      }
    }

    public  void makeDir(String p_Path) {
      File dir = new File(p_Path);
      dir.mkdirs();
    }
	public void importCompleteTestObjectsOfProject(File p_FromFile, String p_DestinationPath, Project2 p_Project) {
      int numOfTestObjectReferences = p_Project.getTestObjectReferences().size();
      TestObjectReference testObjectReference = null;
      StringBuffer dir = new StringBuffer();
      dir.append(p_DestinationPath);
      dir.append(BusinessRules.URL_SEPARATOR);
      dir.append(BusinessRules.TEST_OBJECTS_FOLDER);
      makeDir(dir.toString());
	  for( int i = 0; i < numOfTestObjectReferences; i++) {
	    testObjectReference = (TestObjectReference) p_Project.getTestObjectReferences().elementAt(i);
		importCompleteTestObjectOfProject(p_FromFile,p_DestinationPath,testObjectReference);

	  }
	}

	//	Ccastedo begins 06-03-06
	public boolean validateAllTestObjects(String p_FromFile, Project2 p_Project, int numOfTestObjectReferences, boolean areValid, boolean isImport){

    	TestObjectReference testObjectReference = null;
    	if (numOfTestObjectReferences>0  && areValid){
    		 testObjectReference = (TestObjectReference) p_Project.getTestObjectReferences().elementAt(numOfTestObjectReferences-1);
             String from = this.buildAbsoluteTestObjectFilePath(p_FromFile, testObjectReference);
             from.replace('\\','/');
     		 TestObject testObject = readTestObject2(from);
     		 if( testObject == null){
     			 sendingValidationMessage(2,from);
     			 return false;
     		 }
     		 else{
     			String tdFile = this.buildAbsoluteTestDataFilePath(p_FromFile, testObjectReference, (TDStructureReference) testObject.getTDSTructureReference().firstElement());
     			tdFile.replace('\\','/');
     			TDStructure tdStructure = readTDStructure(tdFile);
     		    if (tdStructure==null){
     		       	  String message="";
    		 		  message = CMBaseObjectReader.getReadFileState(tdFile).getMessage();
    		 		  if (CMBaseObjectReader.getAdditionalInfo(tdFile)!= null){
     		 			 message = message + CMBaseObjectReader.getAdditionalInfo(tdFile);
     		 		  }
    		 		  message = CMMessages.getString("CANNOT_OPEN_TESTDATA_FILE") + " " + testObjectReference.getFileName() + message + CMMessages.getString("QUESTION_DELETE");

    		 		  Object[] options = { "Yes", "No" };
    		 		  String operation = "";
    		 		  if (isImport)
    		 			  operation = CMMessages.getString("IMPORT_PROJECT_BUTTON");
    		 		  else
    		 			  operation = CMMessages.getString("ADD_PROJECT_BUTTON");

    		 		  int confirmation = JOptionPane.showOptionDialog(CMApplication.frame,message,operation + CMMessages.getString("LABEL_VALIDATION_MESSAGE"),JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE, null,options, options[1]);

    		           if( confirmation == JOptionPane.YES_OPTION){
    		        	   areValid = true;
    		        	   if (!isImport){
    		        		   deleteFile(tdFile);
    		        		   TDStructure tdstructure = this.createTDStructure();
								CMIndexTDStructureUpdate.getInstance().getTDStructureManager().chargeVectorIds(tdstructure);
    		                   if(tdstructure.getM_ResultComparation()==null)
    		       					tdstructure.setM_ResultComparation(new ResultComparation());

    		                   if(tdstructure.getM_TestDataSetReportUnit()==null)
    		                       tdstructure.setM_TestDataSetReportUnit(new TestDataSetReportUnit());

    		                   fixTestDataSetReports(tdstructure);
    		                   writeTDStructureToFile(tdstructure,tdFile);
    		                   CMBaseObjectReader.setReadFileState(tdFile,CMXMLFileState.VALID);
    		        		 //  createTDStructure();
    		        	   }
    		           }
    		           else
    		        	   areValid = false;
     		    }
     		 }
     		areValid = validateAllTestObjects(p_FromFile, p_Project,numOfTestObjectReferences-1, areValid, isImport);
    	}
    	 return areValid;
    }
	//  Ccastedo ends 06-03-06

	void importCompleteTestObjectOfProject(File p_FromFile, String p_DestinationPath, TestObjectReference p_TestObjectReference){
        String from = this.buildAbsoluteTestObjectFilePath(p_FromFile.getParent(), p_TestObjectReference);
		TestObject testObject = readTestObject2(from);
		if( testObject == null){
	      return;
		}

        StringBuffer to = new StringBuffer();
        to.append(p_DestinationPath);
        to.append(BusinessRules.URL_SEPARATOR);
        to.append(p_TestObjectReference.getFilePath());
        testObject.setAccessState(BusinessRules.ACCESS_STATE_CHECKED_IN);
        //cc moves down this     writeTestObjectToFile(testObject, to.toString());
		importCompleteBusinessRuleOfTestObject(p_FromFile, p_DestinationPath, p_TestObjectReference, testObject);
		importCompleteTestDataOfTestObject(p_FromFile, p_DestinationPath, p_TestObjectReference, testObject);
		//if (testObject.getM_TDSTructureReference() != null)//Ccastedo adds
			writeTestObjectToFile(testObject, to.toString());
	}

//	Ccastedo begins 10-02-06
	private void sendingValidationMessage(int p_File, String p_FileName){
		//p_File= 1 --> Project
		//p_File= 2 --> TestObject
		//p_File= 3 --> TestData
		String message="";
		if (CMBaseObjectReader.getReadFileState(p_FileName) == null)
			return;
		message = CMBaseObjectReader.getReadFileState(p_FileName).getMessage();
		if (CMBaseObjectReader.getAdditionalInfo(p_FileName)!= null){
			message = message + CMBaseObjectReader.getAdditionalInfo(p_FileName);
		}
			switch (p_File) {
			case 1:{
				message = CMMessages.getString("CANNOT_OPEN_PROJECT_FILE")+ "\n File name:" + findFileName(p_FileName) + message;
				break;
			}
			case 2:{
				message = CMMessages.getString("CANNOT_OPEN_TESTOBJECT_FILE")+ "\n File name:" + findFileName(p_FileName) + message;
				break;
			}
			case 3:{

				break;
			}
			}
	    if (p_File == 1 || p_File == 2)
	    	JOptionPane.showMessageDialog(CMApplication.frame,message,CMMessages.getString("ERROR_MESSAGE"),JOptionPane.ERROR_MESSAGE);
	}
	//Ccastedo ends 10-02-06

    void importCompleteBusinessRuleOfTestObject(File p_FromFile, String p_DestinationPath, TestObjectReference p_TestObjectReference, TestObject p_TestObject){
      BRulesReference bRulesReference = null;
      if( p_TestObject.getBRulesReference() == null){
        p_TestObject.setBRulesReference(new BRulesReference());
      }
      bRulesReference = p_TestObject.getBRulesReference();
      String from = this.buildAbsoluteBRulesFilePath(p_FromFile.getParent(), p_TestObjectReference);
      StringBuffer to = new StringBuffer();
      to.append(p_DestinationPath);
      to.append(BusinessRules.URL_SEPARATOR);
      to.append(p_TestObjectReference.getPath());
      to.append(BusinessRules.URL_SEPARATOR);
      to.append(p_TestObjectReference.getName());
      to.append(BusinessRules.URL_SEPARATOR);
      to.append(bRulesReference.getFileLocation());
      makeDir(to.toString());
      to.append(BusinessRules.URL_SEPARATOR);
      to.append(bRulesReference.getFileName());

      File brFile = new File(from);
      BREditorManager em = new BREditorManager();
	  String fileContent = em.readContentFromFile(brFile);
	  em.saveStringToFile(fileContent, new File(to.toString()));
    }

    void importCompleteTestDataOfTestObject(File p_FromFile, String p_DestinationPath, TestObjectReference p_TestObjectReference, TestObject p_TestObject){
      TDStructureReference tDStructureReference = null;
      if( p_TestObject.getTDSTructureReference() == null  || p_TestObject.getTDSTructureReference().size()==0) {
        Vector referenceVector = new Vector(0);
        tDStructureReference = new TDStructureReference();
        referenceVector.addElement(tDStructureReference);
      }
      else {
        tDStructureReference = (TDStructureReference) p_TestObject.getTDSTructureReference().firstElement();
      }
      String from = this.buildAbsoluteTestDataFilePath(p_FromFile.getParent(), p_TestObjectReference, tDStructureReference);
      StringBuffer to = new StringBuffer();
      to.append(p_DestinationPath);
      to.append(BusinessRules.URL_SEPARATOR);
      to.append(p_TestObjectReference.getPath());
      to.append(BusinessRules.URL_SEPARATOR);
      to.append(p_TestObjectReference.getName());
      to.append(BusinessRules.URL_SEPARATOR);
      to.append(tDStructureReference.getM_Path());
      makeDir(to.toString());
      to.append(BusinessRules.URL_SEPARATOR);
      to.append(tDStructureReference.getM_FileName());

      TDStructure tdStructure = readTDStructure(from);
      //Ccastedo begins 03-02-06
      if (tdStructure == null){
    	 // sendingValidationMessage(3,from);
//    	  String message="";
//		  message = CMBaseObjectReader.getReadFileState(from).getMessage();
//		  message = CMMessages.getString("CANNOT_OPEN_TESTDATA_FILE") + CMMessages.getString(message) + CMMessages.getString("QUESTION_DELETE");
//		  int confirmation = JOptionPane.showConfirmDialog(CMApplication.frame,message,CMMessages.getString("LABEL_VALIDATION_MESSAGE"),JOptionPane.YES_NO_OPTION); //$NON-NLS-1$ //$NON-NLS-2$
//          if( confirmation == JOptionPane.YES_OPTION) {
        	  TDStructure newTDStructure = new TDStructure();
        	  newTDStructure.setM_TestObject(p_TestObject);
        	  newTDStructure.setM_TestObjectReference(p_TestObjectReference);
        	  this.writeTDStructureToFile(newTDStructure, to.toString());
        	  CMBaseObjectReader.setReadFileState(from,CMXMLFileState.VALID);
        	  return;
          }
//          else{
//        	  CMApplication.frame.setWaitCursor(false);
//			   JOptionPane.showMessageDialog(CMApplication.frame, CMMessages.getString("CANNOT_OPEN_TESTOBJECT_FILE"));
//			  p_TestObject.setM_TDSTructureReference(null);// ccastedo adds
//        	  return;
//          }
//

      //Ccastedo ends 03-02-06

      /*boolean rewrite =*/this.getM_VersionManager().updateTestDataBasedOnVersion(tdStructure,p_TestObject);
     // if( rewrite) {
    	  this.writeTDStructureToFile(tdStructure, to.toString());
     // }
    }

    public String buildProjectDestinationPath( String p_DestinationPath, Project2 p_Project) {
      StringBuffer destinationProjectPath = new StringBuffer();
	  destinationProjectPath.append(p_DestinationPath);
	  destinationProjectPath.append(BusinessRules.URL_SEPARATOR);
	  destinationProjectPath.append(p_Project.getName());
      return destinationProjectPath.toString();
    }

    public String buildImportAbsoluteProjectFilePath(String p_DestinationProjectPath, File p_ImportingFile){
      StringBuffer destinationProjectFilePath = new StringBuffer();
      destinationProjectFilePath.append(p_DestinationProjectPath);
      destinationProjectFilePath.append(BusinessRules.URL_SEPARATOR);
      destinationProjectFilePath.append(p_ImportingFile.getName());
      return destinationProjectFilePath.toString();
    }

    public String findCorrectPath(File p_File) {
            if( p_File.getParentFile() == null) {
              StringBuffer sBuffer = new StringBuffer();
              sBuffer.append(p_File.getPath().replace('\\','/'));
              sBuffer.deleteCharAt(sBuffer.length()-1);
			  return sBuffer.toString();
            }
            else{
              return p_File.getPath().replace('\\','/');
            }
    }
    //grueda10112004_begin
    public boolean checkOutRemote(ProjectReference p_ProjectReference, TestObjectReference p_TestObjectReference,TestObject p_TestObject, Session2 p_Session) {
		if( !exists(p_ProjectReference.getFilePath())) {
		  return false;
		}
	    /*p_TestObjectReference*/p_TestObject.setAccessState(BusinessRules.ACCESS_STATE_CHECKED_OUT);
        /*p_TestObjectReference*/p_TestObject.setUser(p_Session.getM_User());
        return true;
    }
    //grueda30122004_begin
    public boolean checkInRemote(ProjectReference p_ProjectReference, TestObjectReference p_TestObjectReference,TestObject p_TestObject, Session2 p_Session) {
		if( !exists(p_ProjectReference.getFilePath())) {
		  return false;
		}
	    p_TestObject.setAccessState(BusinessRules.ACCESS_STATE_CHECKED_IN);
        p_TestObject.setUser(p_Session.getM_User());
        return true;
    }


    public VersionManager getM_VersionManager(){
            return m_VersionManager;
        }

    public void setM_VersionManager(VersionManager m_VersionManager){
            this.m_VersionManager = m_VersionManager;
        }
    //grueda20092004_begin
    public SessionManager getM_SessionManager(){
            return m_SessionManager;
        }

    public void setM_SessionManager(SessionManager p_SessionManager){
            this.m_SessionManager = p_SessionManager;
        }

    public boolean areCheckedOutLocalAndCheckedInTestObjectsUnderCurrentProject(Project2 p_Project, ProjectReference p_ProjectReference, Session2 p_Session) {
      int numOfTestObjectReferences = p_Project.getTestObjectReferences().size();
      TestObjectReference testObjectReference = null;
      for( int i = 0; i < numOfTestObjectReferences; i++) {
        testObjectReference = (TestObjectReference) p_Project.getTestObjectReferences().elementAt(i);
        TestObject testObject=readTestObjectByReference(testObjectReference,p_ProjectReference,p_Session);
        if( testObject !=null &&(/*testObjectReference*/testObject.getAccessState().equals(BusinessRules.ACCESS_STATE_CHECKED_OUT_LOCAL)
            || /*testObjectReference*/testObject.getAccessState().equals(BusinessRules.ACCESS_STATE_CHECKED_OUT) )) {
          return true;
        }
      }
      return false;
    }
        public boolean areCheckedOutLocalAndCheckedOutTestObjectsUnderCurrentProject(Project2 p_Project, ProjectReference p_ProjectReference, Session2 p_Session) {
      int numOfTestObjectReferences = p_Project.getTestObjectReferences().size();
      TestObjectReference testObjectReference = null;
      for( int i = 0; i < numOfTestObjectReferences; i++) {
        testObjectReference = (TestObjectReference) p_Project.getTestObjectReferences().elementAt(i);
        TestObject testObject=readTestObjectByReference(testObjectReference,p_ProjectReference,p_Session);
        if( testObject !=null &&(/*testObjectReference*/testObject.getAccessState().equals(BusinessRules.ACCESS_STATE_CHECKED_OUT_LOCAL)
            || /*testObjectReference*/testObject.getAccessState().equals(BusinessRules.ACCESS_STATE_CHECKED_OUT) )) {
          return true;
        }
      }
      return false;
    }
    //grueda07112004_end
    //grueda30122004_begin
    public TestObjectReference reloadTestObjectReferenceFromProject(ProjectReference p_ProjectReference, TestObjectReference p_TestObjectReference) {
     // Project2 project = readProject2ByReference(p_ProjectReference);
    	Project2 project = p_ProjectReference.reloadProject();
      int numOfTestObjectReferences;
      TestObjectReference testObjectReference = null;
      if( project != null) {
        numOfTestObjectReferences = project.getTestObjectReferences().size();
        for( int i = 0; i < numOfTestObjectReferences; i++) {
          testObjectReference = (TestObjectReference) project.getTestObjectReferences().elementAt(i);
          if( testObjectReference.getName().equals(p_TestObjectReference.getName())) {
            return testObjectReference;
          }
        }
      }
      return p_TestObjectReference;
    }

    public void checkInRemoteProject(Project2 p_Project, ProjectReference p_ProjectReference, Session2 p_Session){
      p_Project.setAccessState(BusinessRules.ACCESS_STATE_CHECKED_IN);
      p_Project.setUser(p_Session.getM_User());
      //writeProject2ToFile(p_Project, p_ProjectReference.getM_FilePath());
    }

    public void checkOutRemoteProject(Project2 p_Project, ProjectReference p_ProjectReference, Session2 p_Session){
      p_Project.setAccessState(BusinessRules.ACCESS_STATE_CHECKED_OUT);
      p_Project.setUser(p_Session.getM_User());
      //this.writeProject2ToFile(p_Project, p_ProjectReference.getM_FilePath());
    }


    //grueda30122004_end

//HCanedo_22112004_Begin
public boolean isUsedEquivalenceClass(Structure m_Structure, EquivalenceClass p_EC)
{
        //if(m_Structure!=null || p_EC==null) return false;
        
        List testCases = m_Structure.getTestCases();
        for(Iterator iterTestCases = testCases.iterator(); iterTestCases.hasNext();){
            TestCase testCase = (TestCase) iterTestCases.next();
            List equivalenceClasses = testCase.getEquivalenceClasses();
            if (equivalenceClasses.contains(p_EC)) {
                return true;
            }

            List combinations = testCase.getCombinations();
            for (Iterator iterCombinations = combinations.iterator(); iterCombinations.hasNext();) {
                Combination combination = (Combination) iterCombinations.next();
                if (combination instanceof StdCombination) {
                    continue;
                }
                if (isUsedInCombination(combination, p_EC)) {
                    return true;
                }
            }
        }
    
    return false;

}

private boolean isUsedInCombination(Combination combination, EquivalenceClass equivalenceClass) {
    List list = combination.getCombinations();
    if (combination.getEquivalenceClasses().contains(equivalenceClass)) {
        return true;
    } else if (list == null) {
        return false;
    } else {
        for (Iterator iterator = list.iterator(); iterator.hasNext();) {
            Combination nextCombination = (Combination) iterator.next();
            if (combination instanceof StdCombination) {
                continue;
            }
            if (isUsedInCombination(nextCombination, equivalenceClass)) {
                return true;
            }
        }
        return false;
    }
}

public boolean isUsedElement(Structure m_Structure, Element p_EL)
{
     if(m_Structure!=null){
        List equivalences = p_EL.getEquivalenceClasses();
        for (Iterator iterator = equivalences.iterator(); iterator.hasNext();) {
            EquivalenceClass equivalenceClass = (EquivalenceClass) iterator.next();
            if (!isUsedEquivalenceClass(m_Structure, equivalenceClass)) {
                return false;
            }
        }
        return !equivalences.isEmpty();
     }


     return true;
}
//HCanedo_22112004_End

	/**
	*@autor smoreno
	 * @return
	 */
	public static WorkspaceManager getInstance() {

		return INSTANCE;
	}

	public String generateNewWorkspaceName(Session2 p_Session) {
        String untitled = CMMessages.getString("LABEL_WORKSPACE_UNTITLED"); //$NON-NLS-1$
        StringBuffer sBuffer = null;
        int numOfWorkspaces = p_Session.getM_Workspaces().size();
        for (int i = 0; i < numOfWorkspaces; i++) {
            sBuffer = new StringBuffer();
            sBuffer.append(untitled);
            sBuffer.append(i + 1);
            if (!existWorkspaceName(sBuffer.toString(), p_Session)) {
                return sBuffer.toString();
            }
        }
        sBuffer = new StringBuffer();
        sBuffer.append(untitled);
        sBuffer.append(numOfWorkspaces + 1);
        return sBuffer.toString();
    }
	  public boolean existWorkspaceName(String p_Name, Session2 p_Session) {
	        int numOfWorkspaces = p_Session.getM_Workspaces().size();
	        Workspace2 workspace = null;
	        String existingName = null;
	        for (int i = 0; i < numOfWorkspaces; i++) {
	            workspace = (Workspace2)p_Session.getM_Workspaces().get(i);
	            existingName = workspace.getName();
	            if (existingName.equals(p_Name)) {
	                return true;
	            }
	        }
	        return false;
	    }

	/**
	*@autor smoreno
	 * @param p_workspace
	 * @param p_index
	 */
	public void moveWorkspace(Workspace2 p_workspace, int p_index) {
		// TODO Auto-generated method stub
		CMCompoundEdit ce = new CMCompoundEdit();
		Session2 session = p_workspace.getM_Session();
		if (session!=null)
		{
			ce.addEdit(CMModelEditFactory.INSTANCE.createMoveWorkspaceModelEdit(session,p_workspace,p_index));
			session.moveWorkspace(p_workspace,p_index);
		}
		if (ce.hasEdits())
			CMUndoMediator.getInstance().doEdit(ce);
	}

	/**
	*@autor smoreno
	 * @param p_reference
	 * @return
	 */
	public LocalProjectReference buildLocalProjectReference(ProjectReference p_reference) {
		return buildLocalProjectReference(buildAbsoluteCaseMakerLocalDataPath(),p_reference);
	}
}

