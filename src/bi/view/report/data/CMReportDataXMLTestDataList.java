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

import org.apache.xmlbeans.XmlObject;
import bi.controller.VariablesManager;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.report.data.testdatasetlist.ReportTestDataSetDocument;
import bi.view.report.data.testdatasetlist.StructureTestDataType;
import bi.view.report.data.testdatasetlist.TestDataSetType;
import bi.view.report.data.testdatasetlist.TestDataType;
import bi.view.report.data.testdatasetlist.TypeDataType;
import bi.view.report.data.testdatasetlist.TypeDatasNameType;
import bi.view.testdataviews.CMIndexTDStructureUpdate;

class CMReportDataXMLTestDataList extends CMAbstractReportDataXML {

	private TDStructure tdStructure;
	private int indexTD;
	private int indexStructure;
	
	public CMReportDataXMLTestDataList() {
		super();
	}

	public XmlObject createReportDataStructure() {
		logger.debug("Creating the Document Structure");
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
		ReportTestDataSetDocument doc = ReportTestDataSetDocument.Factory.newInstance();
		
        TestDataSet testdataset = (TestDataSet)tdStructure.getM_TestDataSet().elementAt(CMIndexTDStructureUpdate.getInstance().getindexTestDataSet());

    	createTestDataSetNode(doc.addNewReportTestDataSet().addNewTestDataSet(),testdataset);
    	
		return doc;
	}

	private void createTestDataSetNode(TestDataSetType testdatasetNode, TestDataSet testdataset) {			
		testdatasetNode.setName(testdataset.getName());
		testdatasetNode.setLabelTC("TC");
		
		TestData testData = (TestData)testdataset.getM_TestDataCombinations().getM_TestDatas().elementAt(indexTD);
        StructureTestData stuc = (StructureTestData)testData.getM_TDStructure().getM_StructureTestData().elementAt(indexStructure);
        for (int i = 0; i < stuc.getTypeData().size(); i++) {
        	ITypeData typedata = (ITypeData)stuc.getTypeData().elementAt(i);
        	createLabelTypeDataNameNode(testdatasetNode.addNewLabelTypeDatasName(),typedata);       	
        }
        for (int i = 0; i < testdataset.getM_TestDataCombinations().getM_TestDatas().size(); i++) {
        	TestData testdata = (TestData)testdataset.getM_TestDataCombinations().getM_TestDatas().elementAt(i);
        	for (int j = 0; j < testdata.getM_TDStructure().getM_StructureTestData().size(); j++) {
        		StructureTestData structureTD = (StructureTestData)testdata.getM_TDStructure().getM_StructureTestData().elementAt(j);
        		
        		String nameStrucutre = stuc.getName();
        	    if (nameStrucutre.equals(structureTD.getName())) {
        	    	createTestDataNode(testdatasetNode.addNewTestData(),testdataset,testdata,structureTD);
        	    }
            	
        	}
        	
        }
		
		
	}
	private void createLabelTypeDataNameNode(TypeDatasNameType labelTypeDataNameNode, ITypeData typedata) {
		labelTypeDataNameNode.setName(typedata.getName());
	}
	
	private void createTestDataNode(TestDataType testdataNode, TestDataSet testdataset, TestData testdata, StructureTestData stuc2) {		
		//TestData td=(TestData)testdataset.getM_TestDataCombinations().getM_TestDatas().elementAt(indexTD);
		//StructureTestData stuc = (StructureTestData)td.getM_TDStructure().getM_StructureTestData().elementAt(indexStructure);
	    String tdsName = testdata.getName() + "." + stuc2.getName();	                	
	    testdataNode.setTDNameStructureTDName(tdsName);
	    testdataNode.setTestCaseInTestData(testdata.getM_TestCaseinTestData());	
	    createStructureTestDataNode(testdataNode.addNewStructureTestData(), stuc2);			
	}

	public void createStructureTestDataNode(StructureTestDataType structureTestDataNode, StructureTestData stuc){
		for (int i = 0; i < stuc.getTypeData().size(); i++) {
			createTypeDataNode(structureTestDataNode.addNewTypeData(),(ITypeData)stuc.getTypeData().elementAt(i));			            
        }      
		        
                
          
        
	}
	
	public void createTypeDataNode(TypeDataType typedataNode, ITypeData typedata){		
			
            //String preffix = changeReservedCharacterInXML(typedata.getPrefix());
            //String value = changeReservedCharacterInXML(typedata.getFormattedValue());
            //String suffix = changeReservedCharacterInXML(typedata.getSuffix());
		
		String preffix = typedata.getPrefix();
		String value = typedata.getFormattedValue();
		String suffix = typedata.getSuffix();
            
            String formulaPreffix = VariablesManager.returnImplicitExplicitVariable(preffix,tdStructure);
            if (!formulaPreffix.equalsIgnoreCase("")){
            	preffix = formulaPreffix;
            }
            String formulaSuffix = VariablesManager.returnImplicitExplicitVariable(suffix,tdStructure);
            if(!formulaSuffix.equalsIgnoreCase("")){
            	suffix = formulaSuffix;
            }
            typedataNode.setPreffix(preffix);
            typedataNode.setValue(value);
            typedataNode.setSuffix(suffix);
           
	}	
	
//	public String changeReservedCharacterInXML(String p_changeString) {
//        String amp = "&amp;";
//        String lt = "&lt;";
//        String gt = "&gt;";
//        String quot = "&quot;";
//        String apos = "&apos;";
//        String oldamp = "&";
//        String oldlt = "<";
//        String oldgt = ">";
//        String oldquot = "\"";
//        String oldapos = "'";
//        String result = p_changeString.replaceAll(oldamp, amp);
//        result = result.replaceAll(oldlt, lt);
//        result = result.replaceAll(oldgt, gt);
//        result = result.replaceAll(oldquot, quot);
//        result = result.replaceAll(oldapos, apos);
//        return result;
//    }
	
	public String getName() {
		return CMMessages.getString("REPORT_DATA_TEST_DATA");
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
