/**
 * Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.view.actions.testdataset.reports;

import java.awt.event.ActionEvent;
import java.io.IOException;
import javax.swing.AbstractAction;
import javax.swing.Action;
import model.ReportRecord;
import bi.view.actions.CMAction;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.mainframeviews.CMFrameView;
import bi.view.testdataviews.CMScrollPaneOutput;

/**
 * @author ccastedo
 *
 */
@SuppressWarnings("serial")
public class CMOpenReportFolderAction extends AbstractAction implements Action {
	
	private CMFrameView m_frame;
	private CMScrollPaneOutput m_panelTestDataSetReportsView;

	
	public CMOpenReportFolderAction(){
		super(CMMessages.getString("TESTDATASET_REPORTS_OPEN_FOLDER"));
		putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("TESTDATASET_REPORTS_OPEN_FOLDER"));
	    putValue(Action.SMALL_ICON, CMAction.TESTDATASETREPORT_OPEN_REPORT_FOLDER.getIcon());
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("TESTDATASET_REPORTS_OPEN_FOLDER_MNEMONIC").charAt(0));	 
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		
		this.m_frame = CMApplication.frame;
		this.m_panelTestDataSetReportsView = m_frame.getPanelTestDataSetReportsView();
		openReportsFolders();		
	}
	    
    public void openReportsFolders() 
    {    	
    	String folderPath="";
    	int[] forOpenFiles = m_panelTestDataSetReportsView.getM_CMGridOutputs().getSelectedRows();
    	
        for(int i=0; i< forOpenFiles.length; i++)
        {           
        	ReportRecord visual=(ReportRecord)m_panelTestDataSetReportsView.getM_CMGridOutputs().getM_ReportRecords().elementAt(forOpenFiles[i]);                
            folderPath=visual.getM_Path();     
        }
        if (folderPath != ""){
        	folderPath=folderPath.replace('/', '\\');
        	int index = folderPath.lastIndexOf('\\');
        	if (index<0)
        		return;
        	folderPath = folderPath.substring(0,index);        	
        }else
        	return;
       
		
    	try {   
    		String os = System.getProperty("os.name");
    		 
            if(os.startsWith("Windows")) {
            	Runtime.getRuntime().exec("explorer.exe "+folderPath);
            } else if(os.startsWith("Linux")) {
            	Runtime.getRuntime().exec("konqueror file:///" + folderPath);///CHECKKKKK it
            } else {
                throw new IOException("unknown operating system: " + os);
            }         
			
		} catch (IOException e) {			
			e.printStackTrace();
		}
    }    
}
