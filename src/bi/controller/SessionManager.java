

package  bi.controller;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.OutputStream;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import model.ApplicationSetting;
import model.BusinessRules;
import model.ExternalApplication;
import model.ProjectReference;
import model.Session;
import model.Session2;
import model.Workspace2;

import org.apache.log4j.Logger;

import bi.controller.utils.CMBaseObjectReader;
import bi.view.mainframeviews.CMApplication;
import bi.view.testdataviews.CMIndexTDStructureUpdate;


public class SessionManager {
	public static final SessionManager INSTANCE = new SessionManager();
   public SessionManager() {

	   this.setM_InstallationDirectory(CMApplication.getInstallationDirectory());
	  structureManager = new StructureManager();

      m_ToolVendorManager = ToolVendorManager.INSTANCE;//My add....

      elementManager = ElementManager.INSTANCE;
      equivalenceClassManager = EquivalenceClassManager.INSTANCE;
      effectManager = EffectManager.INSTANCE;
      dependencyManager = DependencyManager.INSTANCE;
      m_WorkspaceManager = WorkspaceManager.getInstance();
      m_ErrorManager = ErrorManager.INSTANCE;


      equivalenceClassManager.setLnkDependencyManager(dependencyManager);
      elementManager.setLnkDependencyManager(dependencyManager);
      combinationManager = CombinationManager.INSTANCE;
      dependencyManager.setLnkCombinationManager(combinationManager);
      testCaseManager = TestCaseManager.INSTANCE;
      dependencyManager.setLnkTestCaseManager(testCaseManager);
      elementManager.setM_TestCaseManager(testCaseManager);
      elementManager.setM_CombinationManager(combinationManager);
      elementManager.setM_EquivalenceClassManager(equivalenceClassManager);
      equivalenceClassManager.setM_TestCaseManager(testCaseManager);
      equivalenceClassManager.setM_CombinationManager(combinationManager);
      effectManager.setM_EquivalenceClassManager(equivalenceClassManager);
      effectManager.setM_CombinationManager(combinationManager);
      m_WorkspaceManager.setM_StructureManager(structureManager);
      testCaseManager.setM_ElementManager(elementManager);

      testCaseManager.setM_DependencyManager(dependencyManager);
	  m_ErrorManager.setM_TestCaseManager(testCaseManager);

     testDataManager = new TDStructureManager();
      testDataManager.setM_CombinationManager(combinationManager);

        CMIndexTDStructureUpdate.getInstance().setTDStructureManager(this.getTDStructureManager());









        m_VersionManager = new VersionManager();
        m_WorkspaceManager.setM_VersionManager(m_VersionManager);

        m_WorkspaceManager.setM_SessionManager(this);
	/***
    ****Ends Freddy's code
    ***/

        testCaseManager.setM_ErrorManager(this.m_ErrorManager);//fcastro_13092004
        m_ApplicationSettingManager= ApplicationSettingManager.INSTANCE;
   }


   private TestCaseManager testCaseManager;
   private CombinationManager combinationManager;
   private DependencyManager dependencyManager;
   private EffectManager effectManager;
   private EquivalenceClassManager equivalenceClassManager;
   private ElementManager elementManager;
   private StructureManager structureManager;

   private ToolVendorManager m_ToolVendorManager;
   /**
    * @directed
    */
   private WorkspaceManager m_WorkspaceManager;
   private ErrorManager m_ErrorManager;
////////////////////////Harold Canedo Lopez atributos/////////////////////////
   private TDStructureManager testDataManager;

///////////////////////fin Harold Canedo Lopez Atributos/////////////////////
/**
* Freddy's code
** */


   /**
    * @clientCardinality 1
    * @supplierCardinality 1
    */
   private VersionManager m_VersionManager;
   private int m_Save_UnsaveVariable;
   private String m_InstallationDirectory;
   private ApplicationSettingManager m_ApplicationSettingManager;

   public static void transferImageFromJar(String src, String dest)
    {
        String destnorm=dest.replace('\\','/');
        JarInputStream in = null;
        JarEntry entry = null;
        try
        {
                in = new JarInputStream(new FileInputStream(BusinessRules.JARTARGET));
                if( in == null)
                {
                 return;
                }
                entry = in.getNextJarEntry();
                while (entry !=null)
                {
                    if (entry.getName().indexOf(src)>0)
                    {
                        File outFile = new File(destnorm);
                        if( outFile != null) {
                        if( outFile.getParentFile() != null) {
                            outFile.getParentFile().mkdirs();
                            }
                        }
                     OutputStream out = new FileOutputStream(destnorm);
                     byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			// Close the streams
			out.close();
                        return;
                    }
                    entry = in.getNextJarEntry();
                }
        }
        catch (Exception e)
        {
        	Logger.getLogger(SessionManager.class).error(e);
        };

    }

    /**
     * @param p_Filename
     * @return
     * @deprecated
     */
    public Session readSession(String p_Filename) {
       Object obj = null;
       try {
    	   //Ccastedo begins 25-01-06
           CMBaseObjectReader in = new CMBaseObjectReader(new FileReader(p_Filename),p_Filename);
       //    if (in.isvalidFile())
           obj = in.readObject();
            if( obj == null) {
            	    in.close();
            	    return null;
              }
         in.close();
       } catch(java.io.FileNotFoundException e1) {
    	   Logger.getLogger(this.getClass()).error(e1);
         return this.createSession();
       } catch(java.lang.ClassNotFoundException e2) {
    	   Logger.getLogger(this.getClass()).error(e2);
       } catch(java.io.InvalidClassException e3) {
    	   Logger.getLogger(this.getClass()).error(e3);
       } catch(java.io.StreamCorruptedException e4) {
    	   Logger.getLogger(this.getClass()).error(e4);
       } catch(java.io.OptionalDataException e5) {
    	   Logger.getLogger(this.getClass()).error(e5);
       } catch(java.io.IOException e6) {
    	   Logger.getLogger(this.getClass()).error(e6);
       }
       return (Session)obj;
    }

    public void saveSession(Session p_Session, String p_FileName) {
       try {
         JSX.ObjectWriter out = new JSX.ObjectWriter(new FileWriter(p_FileName));
         out.writeObject(p_Session);
         out.close();
       } catch(java.io.IOException e) {
    	   Logger.getLogger(this.getClass()).error(e);
       }
    }

    /**
     * @return
     * @deprecated
     */
    public Session createSession() {
      return new Session();
    }



    public Session2 readSession2(String p_Filename) {
       Object obj = null;
       Session2 session = null;
       try {
    	   //Ccastedo begins 25-01-06
           CMBaseObjectReader in = new CMBaseObjectReader(new FileReader(p_Filename),p_Filename);
         //  if (in.isvalidFile())
         	  obj = in.readObject();
            if( obj == null) {
            	    in.close();
            	    return null;
              }
            //Ccastedo ends 25-01-06
       //CC    JSX.ObjectReader in = new JSX.ObjectReader(new FileReader(p_Filename));
	//CC 	 obj = in.readObject();
         session = (Session2) obj;
         if( obj != null) {
           if( session.getM_ApplicationSetting() == null) {

             session.setM_ApplicationSetting(new ApplicationSetting());
           }


           boolean rewrite = this.m_WorkspaceManager.getM_VersionManager().updateSessionBasedOnTheVersion(session);
           if (rewrite)
           {
               this.writeSession2ToFile(session);
           }
		  for(int i =0; i<session.getM_ApplicationSetting().getM_ExternalApplications().size();i++)
          {
            ExternalApplication eas=(ExternalApplication)session.getM_ApplicationSetting().getM_ExternalApplications().elementAt(i);
            if(eas.getM_Param()==null)
            {
                eas.setM_Param("");
            }
          }


         }
         this.getApplicationSettingManager().setApplicationSetting(session.getM_ApplicationSetting());
         in.close();
       } catch(java.io.FileNotFoundException e1) {
    	   Logger.getLogger(this.getClass()).error(e1);
         Session2 session2=this.createSession2();
         this.getApplicationSettingManager().setApplicationSetting(session2.getM_ApplicationSetting());
         return session2;
       } catch(java.lang.ClassNotFoundException e2) {
    	   Logger.getLogger(this.getClass()).error(e2);
       } catch(java.io.InvalidClassException e3) {
    	   Logger.getLogger(this.getClass()).error(e3);
       } catch(java.io.StreamCorruptedException e4) {
    	   Logger.getLogger(this.getClass()).error(e4);
       } catch(java.io.OptionalDataException e5) {
    	   Logger.getLogger(this.getClass()).error(e5);
       } catch(java.io.IOException e6) {
    	   Logger.getLogger(this.getClass()).error(e6);
       }

       return session;
    }

    public void writeSession2ToFile(Session2 p_Session, String p_FileName) {
       try {
         File fileName=new File(p_FileName);
         JSX.ObjectWriter out = new JSX.ObjectWriter(new FileWriter(fileName));
         out.writeObject(p_Session);
         out.close();
       } catch(java.io.IOException e) {
    	   Logger.getLogger(this.getClass()).error(e);
       }
    }

    public void writeSession2ToFile(Session2 p_Session){
	  StringBuffer filePath = new StringBuffer();
    //  deleteLastQuoteInInstalationDirectory();
	  filePath.append(m_InstallationDirectory);
	  filePath.append(BusinessRules.URL_SEPARATOR);
	  filePath.append("cm.ini.xml");
    //  filePath.append('"');
      writeSession2ToFile(p_Session, filePath.toString());
    }

    public Session2 createSession2() {
      return new Session2();
    }

    public void writeNewSession2ToFile(){
      Session2 session = createSession2();
      writeSession2ToFile(session);
    }

    public void saveSession2(Session2 p_Session) {
      writeSession2ToFile(p_Session);
    }

    public String getExternalApplicationFilePath(String p_Name, Session2 p_Session) {
      int numOfApplications = p_Session.getM_ApplicationSetting().getM_ExternalApplications().size();
      for( int i = 0; i < numOfApplications; i++) {
        ExternalApplication externalApplication = (ExternalApplication) p_Session.getM_ApplicationSetting().getM_ExternalApplications().elementAt(i);
        if( externalApplication.getM_Name().equals(p_Name) ) {
          return externalApplication.getM_FilePath();
        }
      }
      return "";
    }
    ////////////////////////////////////////////////////////////////////////////
    public TestCaseManager getTestCaseManager(){ return testCaseManager; }

    public void setTestCaseManager(TestCaseManager testCaseManager){ this.testCaseManager = testCaseManager; }

    public CombinationManager getCombinationManager(){ return combinationManager; }

    public void setCombinationManager(CombinationManager combinationManager){ this.combinationManager = combinationManager; }

    public DependencyManager getDependencyManager(){ return dependencyManager; }

    public void setDependencyManager(DependencyManager dependencyManager){ this.dependencyManager = dependencyManager; }

    public EffectManager getEffectManager(){ return effectManager; }

    public void setEffectManager(EffectManager effectManager){ this.effectManager = effectManager; }

    public EquivalenceClassManager getEquivalenceClassManager(){ return equivalenceClassManager; }

    public void setEquivalenceClassManager(EquivalenceClassManager equivalenceClassManager){ this.equivalenceClassManager = equivalenceClassManager; }

    public ElementManager getElementManager(){ return elementManager; }

    public void setElementManager(ElementManager elementManager){ this.elementManager = elementManager; }

    public StructureManager getStructureManager(){ return structureManager; }

    public void setStructureManager(StructureManager structureManager){ this.structureManager = structureManager; }

    public WorkspaceManager getM_WorkspaceManager(){
            return m_WorkspaceManager;
        }

    public void setM_WorkspaceManager(WorkspaceManager m_WorkspaceManager){
            this.m_WorkspaceManager = m_WorkspaceManager;
        }

   public ErrorManager getM_ErrorManager(){
            return m_ErrorManager;
        }

    public void setM_ErrorManager(ErrorManager p_ErrorManager){
            this.m_ErrorManager = p_ErrorManager;
        }

/**
* Ends Freddy's code
* **/

////////////////////////////////////////Harold Canedo Lopez Metodos///////////
    public TDStructureManager getTDStructureManager()
    {
        return testDataManager;
    }

    public void setTDStructureManager(TDStructureManager testDataManager)
    {
        this.testDataManager = testDataManager;
    }

    public int getM_Save_UnsaveVariable(){
            return m_Save_UnsaveVariable;
        }

    public void setM_Save_UnsaveVariable(int m_Save_UnsaveVariable){
            this.m_Save_UnsaveVariable = m_Save_UnsaveVariable;

        }


   public String getM_InstallationDirectory(){
      return m_InstallationDirectory;
    }
    public void setM_InstallationDirectory(String p_Directory){
      m_InstallationDirectory = p_Directory;
    }


    public void setApplicationSettingManager(ApplicationSettingManager p_ApplicationSettingManager){
    	m_ApplicationSettingManager=p_ApplicationSettingManager;
    }

    public ApplicationSettingManager getApplicationSettingManager(){
    	return m_ApplicationSettingManager;
    }

    public void setToolVendorManager(ToolVendorManager m_ToolVendorManager){
    	this.m_ToolVendorManager = m_ToolVendorManager;
    }

    public ToolVendorManager getToolVendorManager(){
    	return m_ToolVendorManager;
    }

/**
* Ends Freddy's code
* **/
	/**
	*@param p_session
	 * @autor smoreno
	 * @return
	 */
	public int getNextWorkspaceId(Session2 p_session) {
		// TODO Auto-generated method stub
		return p_session.getM_Workspaces().size()-1;
	}

	/**
	*@autor smoreno
	 */
	public static Session2 getCurrentSession() {
		return CMApplication.frame.getTreeWorkspaceView().getM_Session2();

	}

	/**
	*@autor smoreno
	 * @param p_reference
	 * @return
	 */
	public Workspace2 getWorkspaceForProjectReference(ProjectReference p_reference) {
		for (Workspace2 workspace : getCurrentSession().getM_Workspaces())
			   if (workspace.getM_ProjectReferences().contains(p_reference))
				   return workspace;
		return null;
	}

	public  void writeSessionToFile() {
			writeSession2ToFile(getCurrentSession());
		
	}
}
