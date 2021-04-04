package bi.view.report;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.Icon;
import javax.swing.JOptionPane;
import javax.xml.transform.TransformerException;

import model.ExternalApplication;
import model.Session2;
import model.TestCaseExternalReports;

import org.apache.log4j.Logger;

import bi.view.icons.CMIcon;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.report.data.CMReportData;
import bi.view.report.data.EReportDataSource;
import bi.view.utils.event.CMProgressEvent;
import bi.view.utils.event.CMProgressListener;

/**
 * @author smoreno
 * Reponsabilities
 *  show the dialog for choosing the report based on the available formats
 *  create the report 
 *  open the report -- only this will be executed on his own thread
 *  Show an error message if a report cannot be generated
 */
public class CMReportRunner extends Thread {
	public static Process currentReportProcess;
	private  List<CMReport> reports = new ArrayList<CMReport>();	
	private Icon inputDialogIcon = CMIcon.SUMMARY.getImageIcon(); 
	private boolean showReport = true;
	/**
	*@autor smoreno
	 */
	
	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
			super.run();
					 for (CMReport report : getReports())
						try
						{
						  File filerep = report.generate();
						  if (isShowReport()) {
							  Session2 session = CMApplication.frame.getTreeWorkspaceView().getM_Session2();
							  String extAppPath = CMApplication.frame.getCmApplication().getSessionManager().getExternalApplicationFilePath(report.getFormat().getDefaultApp(),session);
							  int numOfApplications = session.getM_ApplicationSetting().getM_ExternalApplications().size();
							  String param = new String();
						      for( int i = 0; i < numOfApplications; i++) {
						        ExternalApplication externalApplication = (ExternalApplication) session.getM_ApplicationSetting().getM_ExternalApplications().elementAt(i);
						        if( externalApplication.getM_Name().equals(report.getFormat().getDefaultApp()) ) {
						          if(!externalApplication.getM_Param().equalsIgnoreCase("")){
						        	  param = externalApplication.getM_Param();
						        	  
						          }
						        }
						      }
							  
							  openReport(filerep, extAppPath, param);
						  }
						} catch (Exception e)
						{
							String[] msg  = {CMMessages.getString("REPORT_MESSAGES_CANNOT_OPEN"),CMMessages.getString("REPORT_MESSAGES_REASON")+e.getMessage(),CMMessages.getString("REPORT_MESSAGES_STYLESHEET")+report.getFormat().getFilePath()}; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
							JOptionPane.showMessageDialog(CMApplication.frame,msg, report.getFormat().getName()+CMMessages.getString("REPORT_MESSAGES_OPEN_REPORT"),JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$
							Logger.getLogger(this.getClass()).error(e.getMessage());
							e.printStackTrace();
							continue;
						}finally
						{
							System.gc();
							CMApplication.frame.getStatusLabel().setText(CMMessages.getString("STATUS_BAR_READY")); //$NON-NLS-1$
						}
		
	}
	



	//show formats for the report
	public TestCaseExternalReports showReportFormats(EReportDataSource reportType)
	    {

		   Object[] possibilities = CMApplication.frame.getCmApplication().getSessionManager().getApplicationSettingManager().
			getApplicationSetting().getTestCaseReports(reportType.ordinal()).toArray();
		   if (possibilities.length>1)
		   return  (TestCaseExternalReports)JOptionPane.showInputDialog(CMApplication.frame,CMMessages.getString("PROMP_CHOOSE_STYLE_OF_TEST_CASE_LIST"),CMMessages.getString("LABEL_CASEMAKER"),JOptionPane.PLAIN_MESSAGE,	inputDialogIcon, possibilities,	possibilities[0]); //$NON-NLS-1$ //$NON-NLS-2$;
		   else return (TestCaseExternalReports) possibilities[0];
		};
public TestCaseExternalReports showReportFormats(List<EReportDataSource> reportTypes)
	    {
			List<TestCaseExternalReports> list = new ArrayList<TestCaseExternalReports>();
			for (EReportDataSource dataSource : reportTypes)
				list.addAll(CMApplication.frame.getCmApplication().getSessionManager().getApplicationSettingManager().
						getApplicationSetting().getTestCaseReports(dataSource.ordinal()));
			removeNotNecessaryReports(list);			
			
		   Object[] possibilities = list.toArray();
		   if (possibilities.length>1)
		   return  (TestCaseExternalReports)JOptionPane.showInputDialog(CMApplication.frame,CMMessages.getString("PROMP_CHOOSE_STYLE_OF_TEST_CASE_LIST"),CMMessages.getString("LABEL_CASEMAKER"),JOptionPane.PLAIN_MESSAGE,	inputDialogIcon, possibilities,	possibilities[0]); //$NON-NLS-1$ //$NON-NLS-2$;
		   else return (TestCaseExternalReports) possibilities[0];
		};
		
		
	private void removeNotNecessaryReports(List<TestCaseExternalReports> list){
		if (CMApplication.frame.getTreeWorkspaceView().getCurrentTestObject().getOrigin()==null){
			int cont = 0;
			for (Iterator i = list.iterator();i.hasNext();)
			{
				TestCaseExternalReports report = (TestCaseExternalReports) i.next();			
				
				if (report.getName().equalsIgnoreCase(CMMessages.getString("REPORT_TESTCASE_LIST_WORFLOW"))){						
					list.remove(cont);					
					break;
				}
				
				cont++;
			}
		}
		else{
			int cont = 0;
			while (cont<list.size()){
				TestCaseExternalReports report = (TestCaseExternalReports) list.get(cont);			
				
				if (report.getName().equalsIgnoreCase(CMMessages.getString("REPORT_TESTCASE_LIST_EXCEL_1"))){						
					list.remove(cont);		
					cont=-1;
				}
				if (report.getName().equalsIgnoreCase(CMMessages.getString("REPORT_TESTCASE_LIST_ORDERED_BY_NUMBER"))){						
					list.remove(cont);
					cont=-1;
					
				}
				if (report.getName().equalsIgnoreCase(CMMessages.getString("REPORT_TESTCASE_LIST_ORDERED_BY_RISK_LEVELS"))){						
					list.remove(cont);
					cont=-1;
				}
				if (report.getName().equalsIgnoreCase(CMMessages.getString("REPORT_TESTCASE_EXPORT_TO_QADIRECTORXML"))){						
					list.remove(cont);	
					cont=-1;
				}
				if (report.getName().equalsIgnoreCase(CMMessages.getString("REPORT_TESTCASE_EXPORT_TO_CSV"))){						
					list.remove(cont);
					cont=-1;
				}	
				cont++;					
			}
		
		}
	}
	/**
	 * generate a report for a specific format with a specific data
	 * @param format
	 * @param reportDataSourceType
	 */
	public void generateReport(TestCaseExternalReports reportXSLT, String outputFile)
	{
		if( reportXSLT  != null) {
		    CMApplication.frame.setWaitCursor(true);
		//    CMReportData reportData =   EReportDataSource.values()[reportXSLT.getReportDataSourceName()].getReportData();
		    CMReportData reportData =  EReportDataSource.valueOf(reportXSLT.getReporDataSourceType().name()).getReportData();
		    final CMReportImpl report = new CMReportImpl(reportData,reportXSLT,outputFile);		  		    	
		   generateReport(report);
	  	  }
	}


	//Ccastedo begin 24-03-05
	public  void generateReport(EReportDataSource reportDataSource, String outputFilename)
	{
		TestCaseExternalReports reportXSLT = showReportFormats(reportDataSource);
		if( reportXSLT  != null) {
		    CMReportData reportData = null;
		   	reportData =   reportDataSource.getReportData();
		    final CMReport report = new CMReportImpl(reportData,reportXSLT,outputFilename);
		    generateReport(report);
		}

	}
	
	//open the report
	private void openReport(File filerep, String extAppPath, String param) throws IOException, TransformerException {


		//check if can open the default external application
		 File app = new File (extAppPath);
		 if (app!=null && app.canRead())
		 {
			 StringBuffer completePath = new StringBuffer();
			 completePath.append(extAppPath);
			 completePath.append(" "); //$NON-NLS-1$
			 if (filerep == null )
				 return;
			 filerep.setReadOnly();
			 String filePath = filerep.getPath().replace('\\','/');
			 StringBuffer filePathBuffer = new StringBuffer();
			//append quotes if nessesary
			 if( filePath.indexOf(" ") > -1) { //$NON-NLS-1$
				 filePathBuffer.append("\"file:"); //$NON-NLS-1$
				 filePathBuffer.append(filePath);
				 filePathBuffer.append("\""); //$NON-NLS-1$
			 	}
			 else {
				 filePathBuffer.append(filePath);
			 }
			 completePath.append(filePathBuffer.toString());
			 Logger.getLogger(this.getClass()).debug(completePath.toString());

			 try {
				 		if (currentReportProcess!=null)
				 			currentReportProcess.destroy();
				 		if(!param.equalsIgnoreCase("")){
				 			//extAppPath = extAppPath + " " + param;
				 			ProcessBuilder pb1 = new ProcessBuilder(extAppPath, param);
				 			currentReportProcess = pb1.start();
				 		}
				 		else{
				 			ProcessBuilder pb = new ProcessBuilder(extAppPath,filePathBuffer.toString());
					 		currentReportProcess = pb.start();
				 		}
				 		
			 	}
			 			catch(IOException exception) {
			 					exception.printStackTrace();
			 			}
		 		}
		 else
		 {
			 if (filerep == null )
				 return;
			 CMApplication.frame.runDefaultBrowser(filerep.toString());
		 }
	}
	

	public static CMReportRunner getInstance()
	{
		return new CMReportRunner();
	}

	
	
	public List<CMReport> getReports() {
		if (reports == null)
			reports = new ArrayList<CMReport>();
		return reports;
	}




	public void generateReport(List<EReportDataSource> sources, String outputFile) {
		TestCaseExternalReports reportXSLT = showReportFormats(sources);
		if( reportXSLT  != null) {
		   
		    CMReportData reportData = null;
		   //	reportData =  EReportDataSource.values()[reportXSLT.getReportDataSourceId()].getReportData();
		   	reportData =  EReportDataSource.valueOf(reportXSLT.getReporDataSourceType().name()).getReportData();
		    final CMReport report = new CMReportImpl(reportData,reportXSLT,outputFile);
		    generateReport(report);
		}
	}




	public void generateReport(final CMReport report) {
		 CMApplication.frame.setWaitCursor(true);
	    report.addProgressListener(new CMProgressListener(){
			public synchronized void progressEventHappen(CMProgressEvent p_rev) {
				CMApplication.frame.getStatusBar().getLabelStatus().setText(p_rev.getProgress().toString()+" - "+report.getFormat().getName()); //$NON-NLS-1$
				}
	    });
	    getReports().clear();
	    getReports().add(report);
	    System.gc();
	    start();
	    CMApplication.frame.setWaitCursor(false);
	}
	
	public void generateReports(final List<CMReport> reports) {
		 CMApplication.frame.setWaitCursor(true);
		 getReports().clear();
		 for (final CMReport report : reports) {
	    report.addProgressListener(new CMProgressListener(){
			public synchronized void progressEventHappen(CMProgressEvent p_rev) {
				CMApplication.frame.getStatusBar().getLabelStatus().setText(p_rev.getProgress().toString()+" - "+report.getFormat().getName()); //$NON-NLS-1$
				}
	    });
	    getReports().add(report);
		 }
	    System.gc();
	    start();
	    CMApplication.frame.setWaitCursor(false);
	}




	private boolean isShowReport() {
		return showReport;
	}




	public void setShowReport(boolean showReport) {
		this.showReport = showReport;
	}

}
