/*******************************************************************************
Developed by BUSINESS SOFTWARE INNOVATIONS. .
Copyright (c)2003 Daz und Hilterscheid Unternehmensberatung. All rights reserved.
*******************************************************************************/


package  bi.view.mainframeviews;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import model.BusinessRules;
import model.Session2;
import model.Structure;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.plaf.office2003.Office2003Painter;
import com.jidesoft.utils.PortingUtils;
import com.jidesoft.utils.SystemInfo;
import com.jidesoft.wizard.WizardStyle;

import bi.controller.SessionManager;
import bi.view.actions.CMActionEnabler;
import bi.view.lang.CMMessages;


public class CMApplication {


    public static CMFrameView frame;
	private static CMMessages msg;
    CMSplashWindow spl;
    /**
     * Initiate Casemaker
     */
    public CMApplication() {



    }


    public static void main(String[] argv) {
       // set up system Look&Feel
        try {
        	//set up the logging level
    		//BasicConfigurator.configure();
        	PropertyConfigurator.configure(getInstallationDirectory()+"/log.properties");
        	com.jidesoft.utils.Lm.verifyLicense("BusinessInnovatios", "CaseMaker",
			"ihfmy1hoeSBs7npeXUaQpY2UwAFyMZg1");
        	WizardStyle.setStyle(WizardStyle.WIZARD97_STYLE);

        	 PortingUtils.prerequisiteChecking();
             Office2003Painter.setNative(SystemInfo.isWindowsXP());
          	LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
			//set the default exception handler
			Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler(){

				public void uncaughtException(Thread t, Throwable e) {
					Logger.getLogger(this.getClass()).error(e.getMessage());


				//in case of an out of memory error theres not much to do but to exit
		    		if (e instanceof OutOfMemoryError){
		    			CMApplication.frame.getStatusLabel().setText("Out Of Memory");
		    			JOptionPane.showMessageDialog(CMApplication.frame,Arrays.asList("Cannot continue: Out of Memory","Casemaker will exit now").toArray(),"Casemaker - Critical",JOptionPane.OK_OPTION); //$NON-NLS-1$ //$NON-NLS-2$
		    			System.exit(0);
		    		}
		    		else
		    			e.printStackTrace();
		    		CMApplication.frame.repaint();
				}

	});


        }
        catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"An Error ocurred \n" + e.getMessage());
            return;
        }
        try {
			CMMessages.initBundles();
		} catch (NullPointerException e) {
			 JOptionPane.showMessageDialog(null,"An Error ocurred \n" + e.getMessage());
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			 JOptionPane.showMessageDialog(null,"An Error ocurred \n" + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			 JOptionPane.showMessageDialog(null,"An Error ocurred \n" + e.getMessage());
			e.printStackTrace();
		}
        CMApplication app = new CMApplication();
        // Splash screen...
        findInstallationDirectory();

        sessionManager = SessionManager.INSTANCE;
	    CMSplashWindow spl = new CMSplashWindow(m_InstallationDirectory) ;
        spl.run() ;
	    spl.setVisible(true) ;
        /////////////////////////////////////
        CMLicenseView licenseView = new CMLicenseView(app);


        //licenseView.checkLicense(true);	
		
        


        spl.startProgressDrawer();//fcastro_20092004
        frame = new CMFrameView(app);
        frame.getTreeWorkspaceView().requestFocus();
        frame.getTreeWorkspaceView().selectRootNode();
        ////////////////////////////////////////
        //Center the frame on screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = frame.getSize();
        frameSize.height = ((frameSize.height > screenSize.height) ? screenSize.height : frameSize.height);
        frameSize.width = ((frameSize.width > screenSize.width) ? screenSize.width : frameSize.width);
        frame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        spl.updateProgress();//fcastro_20092004
        // wait 2 seconds before remove splash screen
        spl.setAlwaysOnTop(true);

        spl.hide(2);
        
        spl.dispose();
        frame.setVisible(true);
        CMActionEnabler actionEnabler = new CMActionEnabler();
         actionEnabler.start();
    }



 private static  void findInstallationDirectory(){
	String applicationPath = (new File("").getAbsolutePath());
 //       BusinessRules.applicationPath = applicationPath;
    m_InstallationDirectory =applicationPath.replace('\\','/');
  }

  public static String getInstallationDirectory()
  {
	  return new File("").getAbsolutePath();
  }

  public SessionManager getSessionManager(){
          return sessionManager;
      }


  public Structure getStructure(){
          return structure;
      }

  public void setStructure(Structure structure){
          this.structure = structure;
      }

  /////////////////////////////////////////////
  private static SessionManager sessionManager = null;
  private Structure structure = null;
  private static String m_InstallationDirectory = null;

  ///////////////////////////////////////////////

  public CMFrameView getFrame(){
	  return this.frame;
  }
}
