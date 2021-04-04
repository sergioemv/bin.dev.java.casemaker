package bi.view.report.data;

import java.util.Iterator;
import java.util.Vector;

import model.BusinessRules;
import model.CMError;
import model.Project2;
import model.ProjectReference;
import model.Session2;
import model.Structure;
import model.TestCase;
import model.TestObject;
import model.TestObjectReference;

import org.apache.xmlbeans.XmlObject;

import bi.controller.ErrorManager;
import bi.view.icons.CMIcon;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;

import bi.view.report.data.errorlist.ErrorListReportDocument;
import bi.view.report.data.errorlist.ErrorType;
import bi.view.report.data.errorlist.ErrorsType;
import bi.view.report.data.errorlist.ProjectType;
import bi.view.report.data.errorlist.TestCaseType;
import bi.view.report.data.errorlist.TestCasesType;
import bi.view.report.data.errorlist.TestObjectType;

class CMReportDataXMLErrorList extends CMAbstractReportDataXML  {

	private Structure structure;
	private Project2 project;

	public CMReportDataXMLErrorList() {
		super();
	}

	public XmlObject createReportDataStructure() {
		logger.debug("Creating the Document Structure");
		try {
		structure = (Structure) getParameter("STRUCTURE");
		project = (Project2) getParameter("PROJECT");
		if (project == null || structure==null)
			throw new Exception("Parameters not initialized");
		} catch (Exception e){
			logger.error(e.getMessage());
			return null;
		}
		
		ErrorListReportDocument doc = ErrorListReportDocument.Factory.newInstance();
		//set the head of the report
		doc.addNewErrorListReport().setReportHeadTitle(CMMessages.getString("REPORT_HEAD_TITLE")+" - "+model.BusinessRules.APPLICATIONNAME+" "+model.BusinessRules.APPLICATIONVERSION);
		doc.getErrorListReport().setReportTitle(CMMessages.getString("REPORT_ERROR_TITLE_LABEL"));
		doc.getErrorListReport().setHeaderImg(BusinessRules.REPORT_IMAGEFLD+"/"+CMIcon.CASEMAKER_LOGO.getFilename());
		//create the project node
		createProjectNode(doc.getErrorListReport().addNewProject());
		//create the test object node
		createTestObjectNode(doc.getErrorListReport().addNewTestObject());
		//create the date  
		doc.getErrorListReport().addNewDate().setLabel(CMMessages.getString("REPORT_DATE_LABEL"));
		doc.getErrorListReport().getDate().newCursor().setTextValue(new java.util.Date().toString());
		//create the user
		doc.getErrorListReport().addNewUserName().setLabel(CMMessages.getString("REPORT_USER_LABEL"));
		doc.getErrorListReport().getUserName().newCursor().setTextValue(System.getProperty("user.name"));
		//create the Errors node
		createErrorsNode(doc.getErrorListReport().addNewErrors());
		return doc;
	}

	private void createErrorsNode(ErrorsType errorsNode) {
		for (Iterator i = structure.getM_CMErrors().iterator();i.hasNext();)
			createErrorNode(errorsNode.addNewError(), (CMError) i.next());
	}

	private void createErrorNode(ErrorType errorNode, CMError error) {
		//put the name
		errorNode.addNewName().setLabel(CMMessages.getString("REPORT_ERROR_NAME_LABEL_VALUE"));
		errorNode.getName().newCursor().setTextValue(error.getM_Name());
		//put the description
		errorNode.addNewDescription().setLabel(CMMessages.getString("REPORT_ERROR_DESCRIPTION_LABEL_VALUE"));
		errorNode.getDescription().newCursor().setTextValue(error.getM_Description());
		//put the State
		errorNode.addNewState().setLabel(CMMessages.getString("REPORT_ERROR_STATE_LABEL_VALUE"));
		errorNode.getState().newCursor().setTextValue(error.getM_State());
		//put the priority
		errorNode.addNewPriority().setLabel(CMMessages.getString("REPORT_ERROR_PRIORITY_LABEL_VALUE"));
		errorNode.getPriority().newCursor().setTextValue(error.getM_Priority());
		//put the issue date
		errorNode.addNewIssueDate().setLabel(CMMessages.getString("REPORT_ERROR_ISSUEDATE_LABEL_VALUE"));
		if (error.getM_IssueDate()!=null)
		errorNode.getIssueDate().newCursor().setTextValue(error.getM_AssignDate().toString());
		//put the issued by
		errorNode.addNewIssuedBy().setLabel(CMMessages.getString("REPORT_ERROR_ISSUEDBY_LABEL_VALUE"));
		errorNode.getIssuedBy().newCursor().setTextValue(error.getM_IssuedBy());
		//put the Closing date
		errorNode.addNewClosingDate().setLabel(CMMessages.getString("REPORT_ERROR_CLOSINGDATE_LABEL_VALUE"));
		if (error.getM_ClosingDate()!=null)
		errorNode.getClosingDate().newCursor().setTextValue(error.getM_ClosingDate().toString());
		//put the closed by 
		errorNode.addNewClosedBy().setLabel(CMMessages.getString("REPORT_ERROR_CLOSEDBY_LABEL_VALUE"));
		errorNode.getClosedBy().newCursor().setTextValue(error.getM_ClosedBy());
		//create the test cases node
		createTestCasesNode(errorNode.addNewTestCases(),error);
	}

	private void createTestCasesNode(TestCasesType testCasesNode, CMError error) {
		testCasesNode.setLabel(CMMessages.getString("REPORT_ERROR_TESTCASES_LABEL"));
		 Vector testCasesWithTheError = ErrorManager.INSTANCE.getTestCasesWithTheError(error, structure);
		for (Iterator i = testCasesWithTheError.iterator();i.hasNext();)
			createTestCaseNode(testCasesNode.addNewTestCase(),(TestCase)i.next());
		
	}

	private void createTestCaseNode(TestCaseType testCaseNode, TestCase testCase) {
		testCaseNode.addNewName().setLabel(CMMessages.getString("REPORT_TEST_CASE_LABEL"));
		testCaseNode.getName().newCursor().setTextValue(testCase.getName()+
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
		
	}

	private void createTestObjectNode(TestObjectType testObjectNode) {
		//set the name 
		testObjectNode.addNewName();
		testObjectNode.getName().setLabel(CMMessages.getString("REPORT_OBJECT_LABEL"));
		testObjectNode.getName().newCursor().setTextValue(structure.getTestObject().getName());
		//set the description
		testObjectNode.addNewDescription();
		testObjectNode.getDescription().setLabel(CMMessages.getString("REPORT_OBJECT_DESCRIPTION_LABEL"));
		testObjectNode.getDescription().newCursor().setTextValue(structure.getTestObject().getDescription());
		//set the precondition
		testObjectNode.addNewPrecondition();
		testObjectNode.getPrecondition().setLabel(CMMessages.getString("REPORT_OBJECT_PRECONDITIONS_LABEL"));
		testObjectNode.getPrecondition().newCursor().setTextValue(structure.getTestObject().getPreconditions());
	
	}

	private void createProjectNode(ProjectType projectNode) {
		//add the project label
		projectNode.setLabel(CMMessages.getString("REPORT_PROJECT_LABEL"));
		//set the name of the project
		projectNode.newCursor().setTextValue(this.project.getName());

		
	}

	public String getName() {
		return CMMessages.getString("REPORT_DATA_ERROR_LIST");
	}

	public void setStructure(Structure structure) {
		this.structure = structure;
		
	}

	public void setProject(Project2 selectedProject) {
		this.project = selectedProject;
		
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
