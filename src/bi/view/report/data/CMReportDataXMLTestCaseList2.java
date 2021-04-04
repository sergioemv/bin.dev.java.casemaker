package bi.view.report.data;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;

import model.BusinessRules;
import model.Combination;
import model.Effect;
import model.Element;
import model.EquivalenceClass;
import model.ExpectedResult;
import model.Project2;
import model.ProjectReference;
import model.Requirement;
import model.Session2;
import model.StdCombination;
import model.Structure;
import model.TestCase;
import model.TestObject;
import model.TestObjectReference;
import model.Workspace2;
import model.util.CMElementWorkFlowComparator;
import model.util.CMUserOrderBean;

import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlString;



import bi.controller.CombinationManager;
import bi.controller.TestCaseManager;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.report.data.testcaselist2.CauseEffectType;
import bi.view.report.data.testcaselist2.CauseEffectsType;
import bi.view.report.data.testcaselist2.ElementType;
import bi.view.report.data.testcaselist2.EqClassReferenceType;
import bi.view.report.data.testcaselist2.EquivalenceClassType;
import bi.view.report.data.testcaselist2.ExpectedResultType;
import bi.view.report.data.testcaselist2.ProjectType;
import bi.view.report.data.testcaselist2.ReportTestCasesDocument;
import bi.view.report.data.testcaselist2.ReqNumsType;
import bi.view.report.data.testcaselist2.TestCaseType;
import bi.view.report.data.testcaselist2.TestObjectType;
import bi.view.report.data.testcaselist2.ReportTestCasesDocument.ReportTestCases;


class CMReportDataXMLTestCaseList2 extends CMAbstractReportDataXML{

	private Structure structure;
	private Project2 project;
	private Workspace2 workspace;

	public CMReportDataXMLTestCaseList2() {
		super();
	}

	public XmlObject createReportDataStructure() {
			logger.debug("Creating the Document Structure");
			try {
				structure = (Structure) getParameter("STRUCTURE");
				project = (Project2) getParameter("PROJECT");
				workspace = (Workspace2) getParameter("WORKSPACE");
			if (project == null || structure==null || workspace==null)
				throw new Exception("Parameters not initialized");
			} catch (Exception e){
				logger.error(e.getMessage());
				return null;
			}
		
		ReportTestCasesDocument doc = ReportTestCasesDocument.Factory.newInstance();
		//set the label for the title
		doc.addNewReportTestCases().setLabelCSV(CMMessages.getString("REPORT_TESTCASES_EXPORT_CSV_TITLE")); //$NON-NLS-1$
		doc.getReportTestCases().setLabelQA(CMMessages.getString("REPORT_TESTCASES_EXPORT_QADIRECTOR_TITLE")); //$NON-NLS-1$
		doc.getReportTestCases().setLabelTCL1(CMMessages.getString("REPORT_TITLE")); //$NON-NLS-1$
		doc.getReportTestCases().setLabelTCL2(CMMessages.getString("REPORT_TITLE")); //$NON-NLS-1$
		doc.getReportTestCases().addNewCMVersion().newCursor().setTextValue(model.BusinessRules.APPLICATIONVERSION +" Build "+model.BusinessRules.BUILDDATE +"."+model.BusinessRules.BUILDNUMBER);
		//doc.getReportTestCases().
		//set the workspace

		doc.getReportTestCases().addNewWorkSpace().addNewName().newCursor().setTextValue(workspace.getName());
		doc.getReportTestCases().getWorkSpace().getName().setLabelTCL2(CMMessages.getString("REPORT_TESTDIRECTOR_LABEL_VALUE_SUBJECT")); //$NON-NLS-1$
		//		create the project node
		createProjectNode(doc.getReportTestCases().addNewProject());
		//create the test object node
		createTestObjectNode(doc.getReportTestCases().addNewTestObject());

		//create the element nodes
		createElementsNodes(doc.getReportTestCases());
		//create the test Cases nodes
		createTestCaseNodes(doc.getReportTestCases());
		//create the user
		doc.getReportTestCases().addNewUserName().setLabelTCL1(CMMessages.getString("REPORT_USER_LABEL")); //$NON-NLS-1$
		doc.getReportTestCases().getUserName().setLabelTCL2(CMMessages.getString("REPORT_USER_LABEL")); //$NON-NLS-1$
		doc.getReportTestCases().getUserName().newCursor().setTextValue(System.getProperty("user.name")); //$NON-NLS-1$
		//	create the date
		doc.getReportTestCases().addNewDate().setLabelTCL1(CMMessages.getString("REPORT_DATE_LABEL")); //$NON-NLS-1$
		doc.getReportTestCases().getDate().setLabelTCL2(CMMessages.getString("REPORT_DATE_LABEL")); //$NON-NLS-1$
		doc.getReportTestCases().getDate().newCursor().setTextValue(new java.util.Date().toString());
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		doc.getReportTestCases().getDate().setQAFormat(formatter.format(new java.util.Date()));
		//create the origin node
		if (structure.getTestObject().getOrigin() != null){
			doc.getReportTestCases().addNewOrigin();
			//dont forget to replace \ for /
			doc.getReportTestCases().getOrigin().newCursor().setTextValue("file:///"+structure.getTestObject().getOrigin().toString().replace('/','\\')); //$NON-NLS-1$
			//doc.getReportTestCases().getOrigin().newCursor().setTextValue("file:///C:/Test.vdx");
		}
		return doc;
	}

	private void createElementsNodes(ReportTestCases reportTestCasesNode) {
		for (Element element : structure.getElements(CMUserOrderBean.COMPARATOR))
		{
			if (element.getEquivalenceClasses().size()==0)
				continue;
		     createElementNode(reportTestCasesNode.addNewElement(),element);
		}
	}

	private void createTestCaseNodes(ReportTestCases reportTestCasesNode) {
		for (Iterator i = structure.getLnkTestCases().iterator();i.hasNext();)
			createTestCaseNode(reportTestCasesNode.addNewTestCase(),(TestCase)i.next());
	}

	private void createTestCaseNode(TestCaseType testCaseNode, TestCase testCase) {
		//set the id
		testCaseNode.addNewId().newCursor().setTextValue(new Integer(testCase.getId()).toString());
		//set the name
		testCaseNode.addNewName().setLabelQA(CMMessages.getString("REPORT_TESTCASES_EXPORT_QADIRECTOR_TEST_CASE_NAME_LABEL"));
		testCaseNode.getName().setLabelTCL2(CMMessages.getString("REPORT_TESTDIRECTOR_LABEL_VALUE_TESTNAME"));
		testCaseNode.getName().newCursor().setTextValue(testCase.getName());
		//set the Description
		testCaseNode.addNewDescription().setLabelQA(CMMessages.getString("REPORT_TESTDIRECTOR_LABEL_VALUE_DESCRIPTION"));
		testCaseNode.getDescription().setLabelTCL2(CMMessages.getString("REPORT_TESTDIRECTOR_LABEL_VALUE_DESCRIPTION"));
		testCaseNode.getDescription().newCursor().setTextValue(testCase.getDescription());
		//set the user descritpion
		testCaseNode.addNewUserDescription().setLabelQA(CMMessages.getString("REPORT_TESTCASES_USER_DESCRIPTION_LABEL"));
		testCaseNode.getUserDescription().setLabelTCL2(CMMessages.getString("REPORT_TESTCASES_USER_DESCRIPTION_LABEL_1"));
		testCaseNode.getUserDescription().newCursor().setTextValue(testCase.getDescriptionEditable());
		//create state node
		testCaseNode.addNewState().setLabelTCL1(CMMessages.getString("REPORT_TESTCASELIST_TO_EXCEL_TABLE_EQUIVALENCECLASS_STATUS_LABEL"));
		testCaseNode.getState().setLabelTCL2(CMMessages.getString("REPORT_TESTCASELIST_TO_EXCEL_TABLE_EQUIVALENCECLASS_STATUS_LABEL"));
		testCaseNode.getState().newCursor().setTextValue(testCase.getStateName());
		//create risk level node
		testCaseNode.addNewRiskLevel().setLabelTCL2(CMMessages.getString("REPORT_TESTCASELIST2_TO_EXCEL_RISKLEVEL_LABEL"));
		testCaseNode.getRiskLevel().newCursor().setTextValue(new Integer(testCase.getRiskLevel()).toString());
		//create element ref nodes
		createElementReferenceNodes(testCaseNode,testCase);
		//create equivalence class ref
		createEqClassReferenceNodes(testCaseNode,testCase);
		//create reqnums node
		createReqNumsNode(testCaseNode.addNewReqNums(),testCase);
		//create effects node
		createEffectsNode(testCaseNode.addNewCauseEffects(),testCase);

	}

	/**
	*@autor smoreno
	 * @param p_testCaseNode
	 * @param p_testCase
	 */
	@SuppressWarnings("unchecked")
	private void createElementReferenceNodes(TestCaseType p_testCaseNode, TestCase p_testCase) {
		List<Element> elements  = (List<Element>)p_testCase.getRelatedElements();
		if (getStructure().getTestObject().getOrigin()==null)
			Collections.sort(elements,CMUserOrderBean.COMPARATOR);
		else
			Collections.sort(elements,new CMElementWorkFlowComparator(p_testCase));
		for (Element element : elements)
			p_testCaseNode.addNewElementReference().setId(new Integer(element.getId()).toString());

	}

	/**
	*@autor smoreno
	 * @param p_effects
	 * @param p_testCase
	 */
	private void createEffectsNode(CauseEffectsType p_effectsNode, TestCase p_testCase) {
		p_effectsNode.setLabelTCL2(CMMessages.getString("REPORT_TESTDIRECTOR_LABEL_VALUE_STEPEXPECTED_RESULTS"));
		for (Effect effect : p_testCase.getRelatedEffects())
			  createCauseEffectNode(p_effectsNode.addNewCauseEffect(),effect);

	}
	private void createEffectsNode(CauseEffectsType p_effectsNode, EquivalenceClass p_equivalenceClass) {
		p_effectsNode.setLabelTCL2(CMMessages.getString("REPORT_TESTDIRECTOR_LABEL_VALUE_STEPEXPECTED_RESULTS"));
		p_effectsNode.setLabelTCL1(CMMessages.getString("REPORT_TESTCASELIST_TO_EXCEL_TABLE_EQUIVALENCECLASS_CAUSE_EFFECT_LABEL"));
		for (Effect effect : p_equivalenceClass.getEffects())
			  createCauseEffectNode(p_effectsNode.addNewCauseEffect(),effect);

	}

	@SuppressWarnings("unchecked")
	private void createReqNumsNode(ReqNumsType reqNumsNode, TestCase testCase) {
		Set reqNums = new TreeSet();
		reqNumsNode.setLabelTCL2(CMMessages.getString("REPORT_TESTCASELIST2_TO_EXCEL_TABLE_TESTCASE_REQNUMS_LABEL"));
		//by default take all related equivalence classes (including indirectly related) to look in their descriptions
		for (Iterator i = testCase.getRelatedEquivalenceClasses().iterator();i.hasNext();)
			reqNums.addAll(findReqNums(((EquivalenceClass)i.next()).getDescription()));
		//by default take all related effects (including indirectly related) to look in their descriptions
		for (Iterator i = testCase.getRelatedEffects().iterator();i.hasNext();)
			reqNums.addAll(findReqNums(((Effect)i.next()).getDescription()));
		//take the list of requirements as input also
		for (Effect effect : testCase.getRelatedEffects() )
			for (Requirement req : effect.getRequirements())
			reqNums.add(req.toString());
		//create the reqnum nodes
		for (Iterator i = reqNums.iterator();i.hasNext();)
			reqNumsNode.addNewReqNum().newCursor().setTextValue((String)i.next());
	}

	/**
	 *  Searchs a string for a REQ# appearences returning a set of numbers that appear after this token
	 * @param description
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Set findReqNums(String description) {
		Set reqNums = new TreeSet();
		int index =0 ;
		while (index!=-1 && description.indexOf("#REQ_",index)!=-1)
		{
			index = description.indexOf("#REQ_",index)+"#REQ_".length();

			String num = new String();
			while (index<description.length()&& Character.isDigit(description.charAt(index)))
			{
				num = num+description.charAt(index);
				index++;
			}
			if (!num.equalsIgnoreCase(""))
				reqNums.add(num);
		}
		return reqNums;
	}

	private void createEqClassReferenceNodes(TestCaseType testCaseNode, TestCase testCase) {
	//	if ()
		for (EquivalenceClass equivalenceClass : testCase.getRelatedEquivalenceClasses())
			createEqClassReferenceNode(testCaseNode.addNewEqClassReference(),equivalenceClass);
	}

	/**
	*@autor smoreno
	 * @param p_type
	 * @param p_equivalenceClass
	 */
	private void createEqClassReferenceNode(EqClassReferenceType p_eqClassrefNode, EquivalenceClass p_equivalenceClass) {
		p_eqClassrefNode.setUniqueId("E"+new Integer(p_equivalenceClass.getLnkElement().getId()).toString()+"EQ"+new Integer(p_equivalenceClass.getId()).toString());
		p_eqClassrefNode.setElementId(new Integer(p_equivalenceClass.getLnkElement().getId()).toString());

	}

	private void createElementNode(ElementType elementNode, Element element) {
		//set the id
		elementNode.addNewId().newCursor().setTextValue(new Integer(element.getId()).toString());
		//set the name
		elementNode.addNewName().setLabelTCL1(CMMessages.getString("REPORT_TESTCASELIST_TO_EXCEL_TABLE_ELEMENT_LABEL"));
		elementNode.getName().setLabelTCL2(CMMessages.getString("REPORT_TESTDIRECTOR_LABEL_VALUE_STEP"));
		elementNode.getName().newCursor().setTextValue(element.getName());
		//set the description
		elementNode.addNewDescription().setLabelTCL1(CMMessages.getString("REPORT_TESTCASELIST_TO_EXCEL_TABLE_DESCRIPTION_LABEL"));
		elementNode.getDescription().newCursor().setTextValue(element.getDescription());
		//create equivalence classes node
		createEquivalenceClassesNodes(elementNode,element);

	}

	private void createEquivalenceClassesNodes(ElementType elementNode, Element element) {

		for (EquivalenceClass equivalenceClass : element.getEquivalenceClasses())
			createEquivalenceClassNode(elementNode.addNewEquivalenceClass(),equivalenceClass);

	}

	private void createEquivalenceClassNode(EquivalenceClassType equivalenceClassNode, EquivalenceClass equivalenceClass) {
		//create the unique id
		equivalenceClassNode.setUniqueId("E"+new Integer(equivalenceClass.getLnkElement().getId()).toString()+"EQ"+new Integer(equivalenceClass.getId()).toString());
		//create the id
		equivalenceClassNode.addNewId().setLabelTCL1(CMMessages.getString("REPORT_TESTCASELIST_TO_EXCEL_TABLE_EQUIVALENCECLASS_NAME_LABEL"));
		equivalenceClassNode.getId().newCursor().setTextValue(equivalenceClass.getName());
		//create the state
		equivalenceClassNode.addNewState().setLabelTCL1(CMMessages.getString("REPORT_TESTCASELIST_TO_EXCEL_TABLE_EQUIVALENCECLASS_STATUS_LABEL"));
		equivalenceClassNode.getState().newCursor().setTextValue(equivalenceClass.getStateName());
		//create the value
		equivalenceClassNode.addNewValue().setLabelTCL1(CMMessages.getString("REPORT_TESTCASELIST_TO_EXCEL_TABLE_EQUIVALENCECLASS_VALUE_LABEL"));
		equivalenceClassNode.getValue().newCursor().setTextValue(equivalenceClass.getValue());
		//create the description
		equivalenceClassNode.addNewDescription().setLabelTCL1(CMMessages.getString("REPORT_TESTCASELIST_TO_EXCEL_TABLE_EQUIVALENCECLASS_DESCRIPTION_LABEL"));
		equivalenceClassNode.getDescription().setLabelTCL2(CMMessages.getString("REPORT_TESTDIRECTOR_LABEL_VALUE_STEPDESCRIPTION"));
		equivalenceClassNode.getDescription().newCursor().setTextValue(equivalenceClass.getDescription());
		//create the Cause effects
		createEffectsNode(equivalenceClassNode.addNewCauseEffects(), equivalenceClass);
		//create the assignments
		createAssignmentNodes(equivalenceClassNode,equivalenceClass);

	}

	private void createAssignmentNodes(EquivalenceClassType equivalenceClassNode, EquivalenceClass equivalenceClass) {

		for (Iterator i = structure.getLnkTestCases().iterator();i.hasNext();)
				createAssignmentNode(equivalenceClassNode.addNewAssignment(),equivalenceClass,(TestCase)i.next());
	}

	@SuppressWarnings("deprecation")
	private void createAssignmentNode(XmlString assignmentNode, EquivalenceClass equivalenceClass, TestCase testCase) {
		 StringBuffer contentBuffer = new StringBuffer();
		 if (testCase.getLnkEquivalenceClasses().contains(equivalenceClass)) {
             contentBuffer.append(equivalenceClass.getName());
             contentBuffer.append(equivalenceClass.getStateName());
         }
		 else if (CombinationManager.INSTANCE.getCombinationsWithEquivalenceClassInTestCase(equivalenceClass, testCase,
	                structure).size() > 0) {
	                    Vector combinations = CombinationManager.INSTANCE.getCombinationsWithEquivalenceClassInTestCase(equivalenceClass,
	                        testCase, structure);
	                    int numOfCombinations = combinations.size();
	                    Combination combination;
	                    for (int k = 0; k < numOfCombinations; k++) {
	                        combination = (Combination)combinations.elementAt(k);
	                        contentBuffer.append(combination.getDependency().getName());
	                        contentBuffer.append(BusinessRules.DEPENDENCY_COMBINATION_SEPARATOR);
	                        contentBuffer.append(combination.getName());
	                        contentBuffer.append(combination.getStateName());
	                        if (k < numOfCombinations - 1) {
	                            contentBuffer.append(BusinessRules.COMMA);
	                        }
	                    }
	            }
	            else if (testCase.getStdCombination(equivalenceClass)  != null) {
	                Vector visibleEquivalenceClasses = TestCaseManager.INSTANCE.getVisibleEquivalenceClassesOfStdCombination(testCase);
	                if (visibleEquivalenceClasses.contains(equivalenceClass)) {
	                    StdCombination stdCombination = testCase.getStdCombination(equivalenceClass) ;
	                    contentBuffer.append(stdCombination.getName());
	                    contentBuffer.append(stdCombination.getStateName());
	                }

	            }
	          assignmentNode.newCursor().setTextValue(contentBuffer.toString());
	}


	private void createCauseEffectNode(CauseEffectType effectNode, Effect effect) {
		effectNode.setLabelTCL1(CMMessages.getString("REPORT_TESTCASELIST_TO_EXCEL_TABLE_EQUIVALENCECLASS_CAUSE_EFFECT_LABEL"));
		effectNode.addNewName().setLabelTCL2(CMMessages.getString("REPORT_TESTCASELIST_TO_EXCEL_TABLE_EQUIVALENCECLASS_CAUSE_EFFECT_LABEL_NAME"));
		effectNode.getName().newCursor().setTextValue(effect.getName());
		effectNode.addNewDescription().setLabelTCL2(CMMessages.getString("REPORT_TESTCASELIST_TO_EXCEL_TABLE_EQUIVALENCECLASS_CAUSE_EFFECT_LABEL_DESCRIPTION"));
		effectNode.getDescription().newCursor().setTextValue(effect.getDescription());
		for (ExpectedResult result : effect.getLnkExpectedResults())
			createExpectedResultNode(effectNode.addNewExpectedResult(),result);
	}

	private void createExpectedResultNode(ExpectedResultType expectedResultNode, ExpectedResult result) {
		expectedResultNode.addNewName().newCursor().setTextValue(result.getName());
		expectedResultNode.addNewValue().newCursor().setTextValue(result.getValue());
		if (result.getNumberValue()!=null)
			expectedResultNode.addNewDataType().newCursor().setTextValue("Number");
		else
			expectedResultNode.addNewDataType().newCursor().setTextValue("String");
	}

	private void createTestObjectNode(TestObjectType testObjectNode) {
		//set the name
		testObjectNode.addNewName().setLabelTCL1(CMMessages.getString("REPORT_OBJECT_LABEL"));
		testObjectNode.getName().setLabelQA(CMMessages.getString("REPORT_TESTCASES_EXPORT_QADIRECTOR_TEST_OBJECT_NAME_LABEL"));
		testObjectNode.getName().setLabelTCL2(CMMessages.getString("REPORT_OBJECT_LABEL"));
		testObjectNode.getName().newCursor().setTextValue(structure.getTestObject().getName());
		//set the description
		testObjectNode.addNewDescription().newCursor().setTextValue(structure.getTestObject().getDescription());
	}

	private void createProjectNode(ProjectType projectNode) {
		//add the project name
		projectNode.addNewName().setLabelTCL1(CMMessages.getString("REPORT_PROJECT_LABEL"));
		projectNode.getName().setLabelTCL2(CMMessages.getString("REPORT_PROJECT_LABEL"));
		projectNode.getName().newCursor().setTextValue(project.getName());
	}

	public String getName() {

		return CMMessages.getString("REPORT_DATA_TEST_CASE_LIST_2");
	}

	public void setStructure(Structure structure) {
		this.structure = structure;

	}

	public void setProject(Project2 project) {
		this.project = project;

	}

	public void setWorkspace(Workspace2 workspace) {
		this.workspace = workspace;

	}

	private Structure getStructure() {
		return this.structure;
	}

	public String getReportFolder() {
		ProjectReference projectReference = CMApplication.frame.getTreeWorkspaceView().getCurrentProjectReference();
	    TestObjectReference testObjectReference = CMApplication.frame.getTreeWorkspaceView().getCurrentTestObjectReference();
	    TestObject testObject =CMApplication.frame.getTreeWorkspaceView().getCurrentTestObject();
	    Session2 session = CMApplication.frame.getTreeWorkspaceView().getM_Session2();
		if( CMApplication.frame.getCmApplication().getSessionManager().getM_WorkspaceManager().isTestObjectCheckedOutLocalByTheSameUser(testObject, session) ) {
		  projectReference = projectReference.getM_LocalProjectReference();
		}
	    String absoluteReportsPath = CMApplication.frame.getCmApplication().getSessionManager().getM_WorkspaceManager().buildAbsoluteReportsPath(projectReference, testObjectReference);
	    return absoluteReportsPath;
	}
	
}
