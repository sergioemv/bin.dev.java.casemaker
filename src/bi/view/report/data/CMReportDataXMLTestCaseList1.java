package bi.view.report.data;

import java.util.Collections;
import java.util.Iterator;
import java.util.Vector;



import org.apache.xmlbeans.XmlObject;

import model.BusinessRules;

import model.Combination;
import model.Effect;
import model.EquivalenceClass;
import model.Project2;
import model.ProjectReference;
import model.Session2;
import model.StdCombination;
import model.Structure;
import model.TestCase;
import model.TestObjectReference;
import model.util.CMRiskLevelComparatorDesc;
import bi.controller.TestCaseManager;
import bi.view.icons.CMIcon;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;

import bi.view.report.data.testcaseslist.CombinationType;
import bi.view.report.data.testcaseslist.CombinationsType;
import bi.view.report.data.testcaseslist.EffectType;
import bi.view.report.data.testcaseslist.EffectsType;
import bi.view.report.data.testcaseslist.EquivalenceClassType;
import bi.view.report.data.testcaseslist.EquivalenceClassesType;
import bi.view.report.data.testcaseslist.StdCombinationType;
import bi.view.report.data.testcaseslist.StdCombinationsType;
import bi.view.report.data.testcaseslist.TestCaseListReportDocument;
import bi.view.report.data.testcaseslist.TestCaseType;
import bi.view.report.data.testcaseslist.TestCaseListReportDocument.TestCaseListReport;
import bi.view.report.data.testcaseslist.TestCaseListReportDocument.TestCaseListReport.Project;
import bi.view.report.data.testcaseslist.TestCaseListReportDocument.TestCaseListReport.TestCases;
import bi.view.report.data.testcaseslist.TestCaseListReportDocument.TestCaseListReport.TestObject;



class CMReportDataXMLTestCaseList1 extends CMAbstractReportDataXML {
	
	private Structure m_Structure;
	private Project2 m_Project;
    private boolean orderedByRiskLevel =false;
    private String[] COLORS_RISK = {"#00AA00", "#00CC00", "#00FF33", "#80FF80", "#B3FFB3", "#E1FE18", "#FFFF00", "#FFCECE", "#FF8080", "#FF3333", "#E80000"};

	public CMReportDataXMLTestCaseList1() {
		super();
	}
	

	public Project2 getM_Project() {
		return m_Project;
	}

	public void setProject(Project2 project) {
		m_Project = project;
	}

	public Structure getM_Structure() {
		return m_Structure;
	}

	public void setStructure(Structure structure) {
		m_Structure = structure;
	}

	

	public XmlObject createReportDataStructure() {
		TestCaseListReportDocument doc = TestCaseListReportDocument.Factory.newInstance();  
			logger.debug("Creating the Document Structure");
			try {
				m_Structure = (Structure) getParameter("STRUCTURE");
				m_Project = (Project2) getParameter("PROJECT");
			if (m_Project == null || m_Structure==null)
				throw new Exception("Parameters not initialized");
			} catch (Exception e){
				logger.error(e.getMessage());
				return null;
			}
			
			try {
		
		
			
			//add the root of the document
			doc.addNewTestCaseListReport();
			//add the head title 
			doc.getTestCaseListReport().setReportHeadTitle(CMMessages.getString("REPORT_HEAD_TITLE")+" - "+model.BusinessRules.APPLICATIONNAME+" "+model.BusinessRules.APPLICATIONVERSION);
			//add the title
			doc.getTestCaseListReport().setReportTitle(CMMessages.getString("REPORT_TITLE"));
			//add the header img
			doc.getTestCaseListReport().setHeaderImg(BusinessRules.REPORT_IMAGEFLD+"/"+CMIcon.CASEMAKER_LOGO.getFilename());
			//create the project node
			createProjectNode(doc.getTestCaseListReport().addNewProject());
			//create the test object node
			createTestObjectNode(doc.getTestCaseListReport().addNewTestObject());
			//create the date  
			doc.getTestCaseListReport().addNewDate().setLabel(CMMessages.getString("REPORT_DATE_LABEL"));
			doc.getTestCaseListReport().getDate().newCursor().setTextValue(new java.util.Date().toString());
			//create the user
			doc.getTestCaseListReport().addNewUserName().setLabel(CMMessages.getString("REPORT_USER_LABEL"));
			doc.getTestCaseListReport().getUserName().newCursor().setTextValue(System.getProperty("user.name"));
			//create the test cases summary
			createRiskLevelNodes(doc.getTestCaseListReport());
			//create the test cases detail
			createTestCasesNode(doc.getTestCaseListReport().addNewTestCases());
		
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			return null;
		}
		return doc;
		
	}

	private void createTestCasesNode(TestCases p_TestCasesNode) {
		p_TestCasesNode.setLabel(CMMessages.getString("REPORT_NUMBER_OF_TESTCASE_LABEL"));
		if (isOrderedByRiskLevel())
			createTestCaseNodesOrdered(p_TestCasesNode);
		else
			createTestCaseNodes(p_TestCasesNode);
	}

	private void createTestCaseNodes(TestCases testCasesNode) {
		Vector testCases =  (Vector) m_Structure.getLnkTestCases();
		for (Iterator i = testCases.iterator();i.hasNext();)
        	createTestCaseNode(testCasesNode.addNewTestCase(),(TestCase) i.next());
	}


	@SuppressWarnings("unchecked")
	private void createTestCaseNodesOrdered(TestCases testCasesNode) {
		Vector testCasesOrd =  (Vector) m_Structure.getLnkTestCases().clone();
		CMRiskLevelComparatorDesc comparator = new CMRiskLevelComparatorDesc();
		Collections.sort(testCasesOrd,comparator);
		        for (Iterator i = testCasesOrd.iterator();i.hasNext();)
		            	createTestCaseNode(testCasesNode.addNewTestCase(),(TestCase) i.next());
		 
	}

	private void createTestCaseNode(TestCaseType testCaseNode,TestCase testCase) {
		//put the id + state of the test case
		testCaseNode.addNewId().setLabel(CMMessages.getString("REPORT_TEST_CASE_LABEL"));
		testCaseNode.getId().newCursor().setTextValue(testCase.getName()+
																							testCase.getStateName()+
																							BusinessRules.SPACE+
																							CMMessages.getString("RISK_LABEL")+
																							BusinessRules.COLON+
																							BusinessRules.SPACE+
																							testCase.getRiskLevel());
		
		//put the risk level
		testCaseNode.addNewRiskLevel().setLabel(CMMessages.getString("RISK_LABEL"));
		testCaseNode.getRiskLevel().newCursor().setTextValue(new Integer(testCase.getRiskLevel()).toString());
		//put the generated description
		testCaseNode.addNewDescription().setLabel(CMMessages.getString("REPORT_TEST_CASE_DESCRIPTION_LABEL"));
		testCaseNode.getDescription().newCursor().setTextValue(testCase.getDescription());
		//put the user description
		testCaseNode.addNewUserDescription().setLabel(CMMessages.getString("REPORT_TEST_CASE_USER_DESCRIPTION_LABEL"));
		testCaseNode.getUserDescription().newCursor().setTextValue(testCase.getDescriptionEditable());
		//put the effects node
		createEffectsNode(testCaseNode.addNewEffects(),testCase);
		//put the Standart combinations node
		createStdCombinationsNode(testCaseNode.addNewStdCombinations(),testCase);
		//put the Combinations node
		createCombinationsNode(testCaseNode.addNewCombinations(),testCase);
		//put the equivalence Classes node
		createEquivalenceClassesNode(testCaseNode.addNewEquivalenceClasses(),testCase);
	}

	private void createEquivalenceClassesNode(EquivalenceClassesType equivalenceClassesNode, TestCase testCase) {
		equivalenceClassesNode.setLabel(CMMessages.getString("REPORT_EQUIVALENCE_CLASS_LIST_LABEL"));
		for (Iterator i = testCase.getRelatedEquivalenceClasses().iterator();i.hasNext();)
			createEquivalenceClassNode(equivalenceClassesNode.addNewEquivalenceClass(),(EquivalenceClass)i.next());
	}

	private void createEquivalenceClassNode(EquivalenceClassType equivalenceClassNode, EquivalenceClass equivalenceClass) {
		//put the name
		equivalenceClassNode.addNewName().setLabel(CMMessages.getString("REPORT_EQUIVALENCE_CLASS_LABEL"));
		equivalenceClassNode.getName().newCursor().setTextValue(equivalenceClass.getLnkElement().getName()+
																											 BusinessRules.DEPENDENCY_COMBINATION_SEPARATOR+
																											 equivalenceClass.getName()+
																											 equivalenceClass.getStateName()+
																											 BusinessRules.SPACE+
																											 CMMessages.getString("RISK_LABEL")+
																											 BusinessRules.COLON+
																											 BusinessRules.SPACE+
																											 equivalenceClass.getRiskLevel());
		//put the value
		equivalenceClassNode.addNewValue().setLabel(CMMessages.getString("REPORT_EQUIVALENCE_CLASS_VALUE_LABEL"));
		equivalenceClassNode.getValue().newCursor().setTextValue(equivalenceClass.getValue());
		//put the description
		equivalenceClassNode.addNewDescription().setLabel(CMMessages.getString("REPORT_EQUIVALENCE_CLASS_DESCRIPTION_LABEL"));
		equivalenceClassNode.getDescription().newCursor().setTextValue(equivalenceClass.getDescription());
		//put the cause effects
		createEffectsNode(equivalenceClassNode.addNewEffects(),equivalenceClass);
		
	}

	private void createEffectsNode(EffectsType effectsNode, EquivalenceClass equivalenceClass) {
		effectsNode.setLabel(CMMessages.getString("REPORT_COMBINATION_CAUSE_EFFECTS_LABEL"));
		String stringEffects ="";
		for (Iterator i = equivalenceClass.getEffects().iterator();i.hasNext();)
		{
			stringEffects = stringEffects+((Effect)i.next()).getName()+BusinessRules.CAUSE_EFFECT_SEPARATOR;
		}
		effectsNode.newCursor().setTextValue(stringEffects);
		
	}

	@SuppressWarnings("deprecation")
	private void createCombinationsNode(CombinationsType combinationsNode, TestCase testCase) {
		combinationsNode.setLabel(CMMessages.getString("REPORT_REFERENCES_OF_COMBINATIONS_LABEL"));
		for (Iterator i = testCase.getLnkCombinations().iterator();i.hasNext();)
		{
			Combination combination = (Combination)i.next();
			if (!(combination instanceof StdCombination)) {
				createCombinationNode(combinationsNode.addNewCombination(),combination);
			}
		}
		
	}

	private void createCombinationNode(CombinationType combinationNode, Combination combination) {
		//put the name + risk level 
		combinationNode.addNewName().setLabel(CMMessages.getString("REPORT_COMBINATION_LABEL"));
		combinationNode.getName().newCursor().setTextValue(combination.getDependency().getName()+
																										BusinessRules.DEPENDENCY_COMBINATION_SEPARATOR+
																										combination.getName()+
																										combination.getStateName()+
																										BusinessRules.SPACE+
																										CMMessages.getString("RISK_LABEL")+
																										BusinessRules.COLON+
																										BusinessRules.SPACE+
																										combination.getRiskLevel());
		//put the description
		combinationNode.addNewDescription().setLabel(CMMessages.getString("REPORT_COMBINATION_DESCRIPTION_LABEL"));
		combinationNode.getDescription().newCursor().setTextValue(combination.getDescription()+
																													BusinessRules.SPACE+
																													combination.getDescriptionEditable());
		//put the effects
		createEffectsNode(combinationNode.addNewEffects(),combination);
	}

	private void createEffectsNode(EffectsType effectsNode, Combination combination) {
		effectsNode.setLabel(CMMessages.getString("REPORT_COMBINATION_CAUSE_EFFECTS_LABEL"));
		String stringEffects ="";
		for (Iterator i = combination.getEffects().iterator();i.hasNext();)
		{
			stringEffects = stringEffects+((Effect)i.next()).getName()+BusinessRules.CAUSE_EFFECT_SEPARATOR;
		}
		effectsNode.newCursor().setTextValue(stringEffects);
	}

	private void createStdCombinationsNode(StdCombinationsType stdCombinationsNode, TestCase testCase) {
		stdCombinationsNode.setLabel(CMMessages.getString("REPORT_STD_COMBINATION_LIST_LABEL"));
		//only one stdCombination per Test Case
		StdCombination stdCombination =testCase.getStdCombination();
	    if (stdCombination != null) {
	        Vector visibleEquivalenceClasses = TestCaseManager.INSTANCE.getVisibleEquivalenceClassesOfStdCombination(testCase);
	        if (visibleEquivalenceClasses.size() > 0) {
	        	createStdCombinationNode(stdCombinationsNode.addNewStdCombination(),stdCombination);
	        }
	    }
		
	}

	private void createStdCombinationNode(StdCombinationType stdCombinationNode, StdCombination stdCombination) {
		//put the name 
		stdCombinationNode.addNewName().setLabel(CMMessages.getString("REPORT_STD_COMBINATION_LABEL"));
		stdCombinationNode.getName().newCursor().setTextValue(stdCombination.getName()+stdCombination.getStateName()
				+BusinessRules.SPACE+CMMessages.getString("RISK_LABEL")+BusinessRules.COLON+BusinessRules.SPACE+stdCombination.getRiskLevel());
		//put the description
		stdCombinationNode.addNewDescription().setLabel(CMMessages.getString("REPORT_STD_COMBINATION_DESCRIPTION_LABEL"));
		stdCombinationNode.getDescription().newCursor().setTextValue(stdCombination.getDescription()+BusinessRules.SPACE
				+stdCombination.getDescriptionEditable());
		
		
		
	}

	private void createEffectsNode(EffectsType effectsNode, TestCase testCase) {
		effectsNode.setLabel(CMMessages.getString("REPORT_CAUSE_EFFECT_LIST_LABEL"));
		for (Iterator i = testCase.getRelatedEffects().iterator();i.hasNext();)
			createEffectNode(effectsNode.addNewEffect(),(Effect)i.next());
	}

	private void createEffectNode(EffectType effectNode, Effect effect) {
		//set the name of the effect
		effectNode.addNewName().setLabel(CMMessages.getString("REPORT_CAUSE_EFFECT_LABEL"));
		effectNode.getName().newCursor().setTextValue(effect.getName());
		//	set the description of the effect
		effectNode.addNewDescription().setLabel(CMMessages.getString("REPORT_CAUSE_EFFECT_DESCRIPTION_LABEL"));
		effectNode.getDescription().newCursor().setTextValue(effect.getDescription());
	}

	private void createRiskLevelNodes(TestCaseListReport testCaseListReport) {
		//array with the count of test cases per risk level
		int[] array = new int[BusinessRules.RISK_NUM_OF_LEVELS+1];
	    int max = 0;
	    int count = 0;
		for (int i = 0; i<BusinessRules.RISK_NUM_OF_LEVELS ;i++)
		{
			array[i] = TestCaseManager.INSTANCE.getTestCasesByRiskLevel(i,m_Structure.getTestObject());
			   if (max < array[i]) 
		            max = array[i];
			   count = count + array[i];
			  testCaseListReport.addNewRiskLevel();
			  testCaseListReport.getRiskLevelArray(i).newCursor().setTextValue(new Integer(array[i]).toString());
		}
		//fill the structure with the value
		for (int j = 0; j<BusinessRules.RISK_NUM_OF_LEVELS ;j++)
		{
			testCaseListReport.getRiskLevelArray(j).setLabel(CMMessages.getString("RISK_LABEL") + BusinessRules.SPACE + j);
			testCaseListReport.getRiskLevelArray(j).setColor(COLORS_RISK[j]);
			testCaseListReport.getRiskLevelArray(j).setWidth(100*array[j]/count+ "%");
			
		}	
		
	}

	private void createTestObjectNode(TestObject testObject) {
		//set the name 
		testObject.addNewName();
		testObject.getName().setLabel(CMMessages.getString("REPORT_OBJECT_LABEL"));
		testObject.getName().newCursor().setTextValue(m_Structure.getTestObject().getName());
		//set the description
		testObject.addNewDescription();
		testObject.getDescription().setLabel(CMMessages.getString("REPORT_OBJECT_DESCRIPTION_LABEL"));
		testObject.getDescription().newCursor().setTextValue(m_Structure.getTestObject().getDescription());
		//set the precondition
		testObject.addNewPrecondition();
		testObject.getPrecondition().setLabel(CMMessages.getString("REPORT_OBJECT_PRECONDITIONS_LABEL"));
		testObject.getPrecondition().newCursor().setTextValue(m_Structure.getTestObject().getPreconditions());
		
	}

	private void createProjectNode(Project project) {
		//add the project label
		project.setLabel(CMMessages.getString("REPORT_PROJECT_LABEL"));
		//set the name of the project
		project.newCursor().setTextValue(this.m_Project.getName());
	}

	public boolean isOrderedByRiskLevel() {
		return orderedByRiskLevel;
	}

	public void setOrderedByRiskLevel(boolean orderedByRiskLevel) {
		this.orderedByRiskLevel = orderedByRiskLevel;
	}


	public String getName() {
		return CMMessages.getString("REPORT_FORMAT_TEST_CASE_LIST");
	}


	public String getReportFolder() {
		ProjectReference projectReference = CMApplication.frame.getTreeWorkspaceView().getCurrentProjectReference();
	    TestObjectReference testObjectReference = CMApplication.frame.getTreeWorkspaceView().getCurrentTestObjectReference();
	    model.TestObject testObject =CMApplication.frame.getTreeWorkspaceView().getCurrentTestObject();
	    Session2 session = CMApplication.frame.getTreeWorkspaceView().getM_Session2();
		if( CMApplication.frame.getCmApplication().getSessionManager().getM_WorkspaceManager().isTestObjectCheckedOutLocalByTheSameUser(testObject, session) ) {
		  projectReference = projectReference.getM_LocalProjectReference();
		}
	    String absoluteReportsPath = CMApplication.frame.getCmApplication().getSessionManager().getM_WorkspaceManager().buildAbsoluteReportsPath(projectReference, testObjectReference);
	    return absoluteReportsPath;
	}

//	public String getTypeString(int ) {
//		{
//			switch (getType()) {
//			//TODO put in the languaje files
//			case TEST_CASE_LIST_TYPE:
//				
//			case TEST_CASE_EXPORT_COMPUWARE:
//				return CMMessages.getString("REPORT_FORMAT_TEST_CASE_EXPORT_COMPUWARE");
//			case TEST_CASE_EXPORT_CSV:
//				return CMMessages.getString("REPORT_FORMAT_TEST_CASE_EXPORT_CSV");
//			case TEST_CASE_EXPORT_EXCEL:
//				return CMMessages.getString("REPORT_FORMAT_TEST_CASE_EXPORT_EXCEL");
//			case TEST_CASE_EXPORT_QADIRECTOR:
//				return CMMessages.getString("REPORT_FORMAT_TEST_CASE_QADIRECTOR");
//
//			default:
//				break;
//			}
//			return null;
//		}
//	}


	
}
