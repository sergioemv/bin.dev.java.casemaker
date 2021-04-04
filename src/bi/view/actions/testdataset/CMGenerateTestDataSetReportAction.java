/**
 * Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.view.actions.testdataset;

import java.awt.Component;
import java.awt.Event;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import model.BusinessRules;
import model.ProjectReference;
import model.Session2;
import model.StructureTestData;
import model.TDStructure;
import model.TestCaseExternalReports;
import model.TestData;
import model.TestDataSet;
import model.TestObject;
import model.TestObjectReference;
import bi.controller.ProjectManager;
import bi.controller.StructureManager;
import bi.view.actions.CMAction;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.report.CMReport;
import bi.view.report.CMReportImpl;
import bi.view.report.CMReportRunner;
import bi.view.report.data.CMReportData;
import bi.view.report.data.EReportDataSource;
import bi.view.testdataviews.CMIndexTDStructureUpdate;

/**
 * @author ccastedo
 *
 */
@SuppressWarnings("serial")
public class CMGenerateTestDataSetReportAction extends AbstractAction implements
		Action {
	
	
	public CMGenerateTestDataSetReportAction(){
		super(CMMessages.getString("TESTDATASET_CONTEXMENU_CSV"));
		putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("BUTTON_TOOLTIP_TESTDATASET_REPORT_CSV"));
	    putValue(Action.SMALL_ICON, CMAction.TESTDATASET_GENERATE_TESTDATASET_REPORT.getIcon());
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("GENERATE_TEST_DATA_SET_REPORT_MNEMONIC").charAt(0));
	    putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_G, Event.CTRL_MASK));
	}

	public void actionPerformed(ActionEvent e) {
		CMApplication.frame.setWaitCursor(true);
		Component comp = CMApplication.frame.getFocusOwner(); 
	  
	  
	  	  
	  	Vector nameStructure = new Vector();
        Vector indexTestData = new Vector();
        Vector indexStructure = new Vector();
        TDStructure tdStructure = CMApplication.frame.getPanelTDStructureView().getTDStructure();
        TestDataSet testdataset = (TestDataSet)tdStructure.getM_TestDataSet().elementAt(CMIndexTDStructureUpdate.getInstance().getindexTestDataSet());

        estractStructureForTestDataSet(testdataset, nameStructure, indexTestData, indexStructure);
        
        List<CMReport> reports = new ArrayList<CMReport>();
        TestDataSet testdataSet = null;
        for (int i = 0; i < nameStructure.size(); i++) {         	
        	int indexTd = indexTestData.elementAt(i).hashCode();
        	int indexStruc = indexStructure.elementAt(i).hashCode();
        	
        	int index = 0;
        	int indexTestDataSet = CMApplication.frame.getPanelTestDataSetView().getM_CMScrollPaneTestDataSetDescription().getM_CMTestDataSetView().getSelectionModel().getLeadRow();
        	testdataSet = (TestDataSet)tdStructure.getM_TestDataSet().elementAt(indexTestDataSet);
        	TestCaseExternalReports format = CMApplication.frame.getCmApplication().getSessionManager().getApplicationSettingManager().
  			getApplicationSetting().getTestCaseReportByName(testdataset.getM_ReportFormat());
            String newName = getAbsoluteTestDataSetReportsPath(testdataSet.getName(), nameStructure.elementAt(i).toString(), format);
        	if (existReportName(tdStructure, newName)){
        		 Object[] options =
                 {CMMessages.getString("TESTDATA_MENSSAGE_BUTTON_NEW"),CMMessages.getString("TESTDATA_MENSSAGE_BUTTON_REWRITE")};
                 int n = JOptionPane.showOptionDialog(CMApplication.frame,
                 CMMessages.getString("TESTDATA_MENSSAGE_INIT_FILE") + ": "+ nameStructure.elementAt(i).toString() + " " + CMMessages.getString("TESTDATA_MENSSAGE_SUFFIX_FILE"),
                 CMMessages.getString("TESTDATA_MENSSAGE_TITLE"),
                 JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
                 if (n == JOptionPane.YES_OPTION) {
            	    while (existReportName(tdStructure, newName)){    		
                        index++;
                        newName = generateNewName(newName, index);                  
                    }            	    
                 }
            }
          CMReportData reportdata = format.getReporDataSourceType().createReportData();
          reportdata.setParameter("INDEXTD", indexTd);
          reportdata.setParameter("INDEXSTRUCTURE", indexStruc);
          reportdata.setParameter("TDSTRUCTURE", CMApplication.frame.getPanelTDStructureView().getTDStructure());
          CMReport report = new CMReportImpl(reportdata,format,newName);
		  reports.add(report);
          	
        	if (comp!=null)
      		   comp.requestFocus();
        }    
        
        //add the Dorma report
        TestCaseExternalReports format = CMApplication.frame.getCmApplication().getSessionManager().getApplicationSettingManager().
			getApplicationSetting().getTestCaseReportByName("CSVDorma");
        String newName = getAbsoluteTestDataSetReportsPath(testdataSet.getName(), "DORMA", format);
        CMReportData reportdata = format.getReporDataSourceType().createReportData();
        reportdata.setParameter("STRUCTURE", StructureManager.getSelectedStructure());
        reportdata.setParameter("PROJECT", ProjectManager.getSelectedProject());
        CMReport report = new CMReportImpl(reportdata,format,newName);
		reports.add(report);
        
        CMReportRunner reportRunner = CMReportRunner.getInstance();
        reportRunner.setShowReport(false);
        reportRunner.generateReports(reports);
        
        CMApplication.frame.setWaitCursor(false); 		 
    	
	}
	
	private boolean existReportName(TDStructure tdStructure, String nameStructure){		
		File output = new File(nameStructure);
        if (output.isFile())
       	 return true;           
       
    	return false;
	}
	
	public String generateNewName(String p_FileName, int p_index) {
		StringBuffer fileNameBuffer = new StringBuffer(p_FileName);
        int index = p_FileName.lastIndexOf("."); //$NON-NLS-1$
        StringBuffer sBuffer = new StringBuffer();
        sBuffer.append("_");
        sBuffer.append(p_index);
        fileNameBuffer.insert(index, sBuffer.toString());
        return fileNameBuffer.toString();
    }
	
	@SuppressWarnings("unchecked")
	public void estractStructureForTestDataSet(TestDataSet testdataset, Vector nameStructure, Vector indexTestData,
	        Vector indexStructure) {
	            Vector p_testDatas = testdataset.getM_TestDataCombinations().getM_TestDatas();
	            for (int i = 0; i < p_testDatas.size(); i++) {
	                TestData p_testdata = (TestData)p_testDatas.elementAt(i);
	                Vector p_StructureTestData = p_testdata.getM_TDStructure().getM_StructureTestData();
	                for (int j = 0; j < p_StructureTestData.size(); j++) {
	                    StructureTestData p_Structure = (StructureTestData)p_StructureTestData.elementAt(j);
	                    String name = p_Structure.getName();
	                    if (!nameStructure.contains(name)) {
	                        nameStructure.addElement(name);
	                        indexTestData.addElement(new Integer(i));
	                        indexStructure.addElement(new Integer(j));
	                    }
	                }
	            }
	}
	
	public String generateNameTestDataReportCSV2(String element) {
        StringBuffer name = new StringBuffer(element);
        TestDataSet auxtds = (TestDataSet) CMApplication.frame.getPanelTDStructureView().getTDStructure().getM_TestDataSet().elementAt(CMIndexTDStructureUpdate.getInstance().getindexTestDataSet());
        Session2 auxsession2 = CMApplication.frame.getTreeWorkspaceView().getM_Session2();
       
        String namexslt = auxtds.getM_ReportFormat();
        int indexxslt = getIndexFromExternalXSLTReportFormat(namexslt,auxsession2);
        TestCaseExternalReports auxxslt =(TestCaseExternalReports)auxsession2.getM_ApplicationSetting().getTestCaseReports(4).elementAt(indexxslt);
        String extReportFormat = auxxslt.getExtension();
        if (extReportFormat.trim() != "") {
            name.append(".");
            name.append(extReportFormat);
        }
       
        return name.toString();
    }
	
	private int getIndexFromExternalXSLTReportFormat(String searchName, Session2 p_session){
 		int index = 0;
 		Vector testDataSetReports= p_session.getM_ApplicationSetting().getTestCaseReports(4);
 		for(int i =0; i < testDataSetReports.size();i++){
			TestCaseExternalReports report= (TestCaseExternalReports)testDataSetReports.elementAt(i);
			if(report.getName().equalsIgnoreCase(searchName)){
				index = i;
				break;
			}				
		}
 		return index;
 	}
	
	public  String getAbsoluteTestDataSetReportsPath(String testdatasetName, String ReportTDName, TestCaseExternalReports reportFormat) {
		ProjectReference projectReference = CMApplication.frame.getTreeWorkspaceView().getCurrentProjectReference();
	    TestObjectReference testObjectReference = CMApplication.frame.getTreeWorkspaceView().getCurrentTestObjectReference();
	    TestObject testObject =CMApplication.frame.getTreeWorkspaceView().getCurrentTestObject();
	    Session2 session = CMApplication.frame.getTreeWorkspaceView().getM_Session2();
		if( CMApplication.frame.getCmApplication().getSessionManager().getM_WorkspaceManager().isTestObjectCheckedOutLocalByTheSameUser(testObject, session) ) {
		  projectReference = projectReference.getM_LocalProjectReference();
		}
	    String absoluteReportsPath = CMApplication.frame.getCmApplication().getSessionManager().getM_WorkspaceManager().buildAbsoluteTestDataReportsPath(projectReference, testObjectReference);
	  	    
	    absoluteReportsPath = absoluteReportsPath + BusinessRules.URL_SEPARATOR + testdatasetName+ BusinessRules.URL_SEPARATOR + ReportTDName+
	    "." + reportFormat.getExtension();
	    return absoluteReportsPath;
	}
	
//	public static String getAbsoluteTestDataSetReportsPath() {
//		ProjectReference projectReference = CMApplication.frame.getTreeWorkspaceView().getCurrentProjectReference();
//	    TestObjectReference testObjectReference = CMApplication.frame.getTreeWorkspaceView().getCurrentTestObjectReference();
//	    TestObject testObject =CMApplication.frame.getTreeWorkspaceView().getCurrentTestObject();
//	    Session2 session = CMApplication.frame.getTreeWorkspaceView().getM_Session2();
//		if( CMApplication.frame.getCmApplication().getSessionManager().getM_WorkspaceManager().isTestObjectCheckedOutLocalByTheSameUser(testObject, session) ) {
//		  projectReference = projectReference.getM_LocalProjectReference();
//		}
//	    String absoluteReportsPath = CMApplication.frame.getCmApplication().getSessionManager().getM_WorkspaceManager().buildAbsoluteTestDataReportsPath(projectReference, testObjectReference);
//	    TestDataSet testdataset = (TestDataSet)ReportManager.getTDStructure().getM_TestDataSet().elementAt(CMIndexTDStructureUpdate.getInstance().getindexTestDataSet());
//	    
//	    absoluteReportsPath = absoluteReportsPath + BusinessRules.URL_SEPARATOR + testdataset.getName()+ BusinessRules.URL_SEPARATOR + ReportManager.getTdReportName()+
//	    "." + reportFormat.getExtension();
//	    return absoluteReportsPath;
//	}
}
