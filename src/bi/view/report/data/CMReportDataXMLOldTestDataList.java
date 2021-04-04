package bi.view.report.data;

import model.ITypeData;
import model.ProjectReference;
import model.Session2;
import model.StructureTestData;
import model.TDStructure;
import model.TestData;
import model.TestDataSet;
import model.TestObject;
import model.TestObjectReference;
import noNamespace.ReportCSVDocument;
import noNamespace.LineReport2Document.LineReport2;
import noNamespace.LineReportDocument.LineReport;
import noNamespace.ReportCSVDocument.ReportCSV;
import org.apache.xmlbeans.XmlObject;
import bi.controller.VariablesManager;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.testdataviews.CMIndexTDStructureUpdate;

class CMReportDataXMLOldTestDataList extends CMAbstractReportDataXML{

	private TDStructure tdStructure;
	private int indexTD;
	private int indexStructure;
	
	public CMReportDataXMLOldTestDataList() {
		super();
	}

	public XmlObject createReportDataStructure() {
		try {
			tdStructure = (TDStructure) getParameter("TDSTRUCTURE");
			indexTD = (Integer) getParameter("INDEXTD");
			indexStructure = (Integer) getParameter("INDEXSTRUCTURE");
		if (tdStructure == null )
			throw new Exception("Parameters not initialized");
		} catch (Exception e){
			logger.error(e.getMessage());
			return null;
		}
		ReportCSVDocument csvDoc = ReportCSVDocument.Factory.newInstance();
		

        TestDataSet testdataset = (TestDataSet)tdStructure.getM_TestDataSet().elementAt(CMIndexTDStructureUpdate.getInstance().getindexTestDataSet());
        ReportCSV reportcsv = csvDoc.addNewReportCSV();
        LineReport firstLine = reportcsv.addNewLineReport();
        
        firstLine.setNameTestDataSet(testdataset.getName());
        firstLine.setNameTestCase("TC");
		
		TestData testData = (TestData)testdataset.getM_TestDataCombinations().getM_TestDatas().elementAt(indexTD);
        StructureTestData stuc = (StructureTestData)testData.getM_TDStructure().getM_StructureTestData().elementAt(indexStructure);
        for (int i = 0; i < stuc.getTypeData().size(); i++) {
        	ITypeData typedata = (ITypeData)stuc.getTypeData().elementAt(i);
        	createLabelTypeDataNameNode(firstLine.addNewLineReport2(),typedata);       	
        }
        
        for (int i = 0; i < testdataset.getM_TestDataCombinations().getM_TestDatas().size(); i++) {
        	TestData testdata = (TestData)testdataset.getM_TestDataCombinations().getM_TestDatas().elementAt(i);
        	for (int j = 0; j < testData.getM_TDStructure().getM_StructureTestData().size(); j++) {
        		StructureTestData structureTD = (StructureTestData)testdata.getM_TDStructure().getM_StructureTestData().elementAt(j);
        		
        		String nameStrucutre = stuc.getName();
        	    if (nameStrucutre.equals(structureTD.getName())) {
        	    	createLineReportNode(reportcsv.addNewLineReport(),testdataset,testdata,stuc);        	    	
        	    }
            	
        	}
        	
        }
    	
		return csvDoc;
	}
	
	private void createLabelTypeDataNameNode(LineReport2 labelTypeDataNameNode, ITypeData typedata) {
		labelTypeDataNameNode.setNameElement(typedata.getName());
	}
	
	private void createLineReportNode(LineReport testdataNode, TestDataSet testdataset, TestData testdata, StructureTestData stuc2) {		
		TestData td=(TestData)testdataset.getM_TestDataCombinations().getM_TestDatas().elementAt(indexTD);
		StructureTestData stuc = (StructureTestData)td.getM_TDStructure().getM_StructureTestData().elementAt(indexStructure);
	    String tdsName = testdata.getName() + "." + stuc2.getName();	                	
	    testdataNode.setNameTestDataSet(tdsName);
	    testdataNode.setNameTestCase(testdata.getM_TestCaseinTestData());
	    for (int i = 0; i < stuc.getTypeData().size(); i++) {
	    	createLineReport2Node(testdataNode.addNewLineReport2(),(ITypeData)stuc.getTypeData().elementAt(i));			            
        }
	   	
	}	
	
	public void createLineReport2Node(LineReport2 typedataNode, ITypeData typedata){			
            String preffix = changeReservedCharacterInXML(typedata.getPrefix());
            String value = changeReservedCharacterInXML(typedata.getFormattedValue());
            String suffix = changeReservedCharacterInXML(typedata.getSuffix());
            
            String formulaPreffix = VariablesManager.returnImplicitExplicitVariable(preffix,tdStructure);
            if (!formulaPreffix.equalsIgnoreCase("")){
            	preffix = formulaPreffix;
            }
            String formulaSuffix = VariablesManager.returnImplicitExplicitVariable(suffix,tdStructure);
            if(!formulaSuffix.equalsIgnoreCase("")){
            	suffix = formulaSuffix;
            }
            typedataNode.setNameElement(preffix + value + suffix);
           
	}	
	
	public String changeReservedCharacterInXML(String p_changeString) {
        String amp = "&amp;";
        String lt = "&lt;";
        String gt = "&gt;";
        String quot = "&quot;";
        String apos = "&apos;";
        String oldamp = "&";
        String oldlt = "<";
        String oldgt = ">";
        String oldquot = "\"";
        String oldapos = "'";
        String result = p_changeString.replaceAll(oldamp, amp);
        result = result.replaceAll(oldlt, lt);
        result = result.replaceAll(oldgt, gt);
        result = result.replaceAll(oldquot, quot);
        result = result.replaceAll(oldapos, apos);
        return result;
    }
	public String getName() {
		return CMMessages.getString("REPORT_DATA_OLD_TEST_DATA");
	}	

	public void setTDStructure(TDStructure tdStructure) {
		this.tdStructure = tdStructure;
	}

	public void setIndexTD(int p_indexTD) {
		this.indexTD = p_indexTD;
	}

	public void setIndexStructure(int p_indexStructure) {
		this.indexStructure = p_indexStructure;
	}

	public String getReportFolder() {
		ProjectReference projectReference = CMApplication.frame.getTreeWorkspaceView().getCurrentProjectReference();
	    TestObjectReference testObjectReference = CMApplication.frame.getTreeWorkspaceView().getCurrentTestObjectReference();
	    TestObject testObject =CMApplication.frame.getTreeWorkspaceView().getCurrentTestObject();
	    Session2 session = CMApplication.frame.getTreeWorkspaceView().getM_Session2();
		if( CMApplication.frame.getCmApplication().getSessionManager().getM_WorkspaceManager().isTestObjectCheckedOutLocalByTheSameUser(testObject, session) ) {
		  projectReference = projectReference.getM_LocalProjectReference();
		}
	    String absoluteReportsPath = CMApplication.frame.getCmApplication().getSessionManager().getM_WorkspaceManager().buildAbsoluteTestDataReportsPath(projectReference, testObjectReference);
	   
	    return absoluteReportsPath;		
	}	

}