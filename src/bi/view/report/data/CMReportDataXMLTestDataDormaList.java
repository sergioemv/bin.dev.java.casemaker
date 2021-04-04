/**
 * 
 */
package bi.view.report.data;


import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import model.BusinessRules;
import model.Effect;
import model.EquivalenceClass;
import model.Project2;
import model.ProjectReference;
import model.Session2;
import model.Structure;
import model.TestCase;
import model.TestObjectReference;

import org.apache.xmlbeans.XmlObject;

import bi.controller.tdparser.expectedresultparser.ExpectedResultParser;
import bi.controller.tdparser.expectedresultparser.ParseException;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.report.data.testcaseslist.EffectType;
import bi.view.report.data.testcaseslist.EffectsType;
import bi.view.report.data.testcaseslist.EquivalenceClassType;
import bi.view.report.data.testcaseslist.EquivalenceClassesType;
import bi.view.report.data.testcaseslist.TestCaseListReportDocument;
import bi.view.report.data.testcaseslist.TestCaseType;
import bi.view.report.data.testcaseslist.TestCaseListReportDocument.TestCaseListReport.TestCases;

/**
 * @author svonborries
 *
 */
public class CMReportDataXMLTestDataDormaList extends CMAbstractReportDataXML {
	
	
	private Structure m_Structure;
	private Project2 m_Project;
	
	public CMReportDataXMLTestDataDormaList(){
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
	

	/* (non-Javadoc)
	 * @see bi.view.report.data.CMAbstractReportDataXML#createReportDataStructure()
	 */
	@Override
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
			createTestCaseNodes(p_TestCasesNode);
	}
	
	
	private void createTestCaseNodes(TestCases testCasesNode) {
		Vector testCases =  (Vector) m_Structure.getLnkTestCases();
		for (Iterator i = testCases.iterator();i.hasNext();)
        	createTestCaseNode(testCasesNode.addNewTestCase(),(TestCase) i.next());
	}
	
	private void createTestCaseNode(TestCaseType testCaseNode,TestCase testCase) {
		//put the id + state of the test case
		testCaseNode.addNewId().setLabel(CMMessages.getString("REPORT_TEST_CASE_LABEL"));
		testCaseNode.getId().newCursor().setTextValue(testCase.getName()+ testCase.getStateName()+ BusinessRules.SPACE+
			CMMessages.getString("RISK_LABEL")+ BusinessRules.COLON+ BusinessRules.SPACE+ testCase.getRiskLevel());
		
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
		//put the equivalence Classes node
		createEquivalenceClassesNode(testCaseNode.addNewEquivalenceClasses(),testCase);
	}
	
	
	private void createEquivalenceClassesNode(EquivalenceClassesType equivalenceClassesNode, TestCase testCase) {
		equivalenceClassesNode.setLabel(CMMessages.getString("REPORT_EQUIVALENCE_CLASS_LIST_LABEL"));
		for (Iterator i = testCase.getRelatedEquivalenceClasses().iterator();i.hasNext();)
			checkIfEquivalenceClassHasManyColumns(equivalenceClassesNode,(EquivalenceClass)i.next());
	}
	
	
	private void checkIfEquivalenceClassHasManyColumns(EquivalenceClassesType equivalenceClassesNode, EquivalenceClass equivalenceClass){
		EquivalenceClass cloneEC = equivalenceClass.makeClone();
		cloneEC.setName(equivalenceClass.getValue());
		String string = cloneEC.getValue();
		StringReader reader = new StringReader(string.trim());
		ExpectedResultParser parser = new ExpectedResultParser(reader);
		try {
			parser.start();
			List<String> columnHeaders = parser.getColumnHeader();
			List<String> columnValues = parser.getColumnValue();
			if(columnHeaders.size()>=0 && columnValues.size() >=0){
				List<EquivalenceClass> ecNews = new ArrayList<EquivalenceClass>();
				int lastEquivalence = columnHeaders.size();
				int iteration = 0;
				for (Iterator iter = columnHeaders.iterator(); iter.hasNext();) {
					iteration++;
					String element = (String) iter.next();
					if(iteration == lastEquivalence){
						EquivalenceClass ec = equivalenceClass.makeClone();
						ec.setName(element);
						ecNews.add(ec);
					}
					else{
						EquivalenceClass ec = equivalenceClass.makeClone();
						ec.setName(element);
						ec.setLnkEffects(null);
						ecNews.add(ec);
					}
					
				}
				for (int i = 0; i < ecNews.size(); i++) {
					((EquivalenceClass)ecNews.get(i)).setValue(columnValues.get(i));
				}
				for (Iterator iter = ecNews.iterator(); iter.hasNext();) {
					EquivalenceClass element = (EquivalenceClass) iter.next();
					createEquivalenceClassNode(equivalenceClassesNode.addNewEquivalenceClass(),element);
				}
				
				
			}
			else
				createEquivalenceClassNode(equivalenceClassesNode.addNewEquivalenceClass(),cloneEC);
		} catch (ParseException e) {
			createEquivalenceClassNode(equivalenceClassesNode.addNewEquivalenceClass(),cloneEC);
		}
	}
	
	
	private void createEquivalenceClassNode(EquivalenceClassType equivalenceClassNode, EquivalenceClass equivalenceClass) {
		//put the name
		equivalenceClassNode.addNewName().setLabel(CMMessages.getString("REPORT_EQUIVALENCE_CLASS_LABEL"));
		equivalenceClassNode.getName().newCursor().setTextValue(equivalenceClass.getName());
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
		for (Iterator iter = equivalenceClass.getEffects().iterator(); iter.hasNext();) {
			Effect element = (Effect) iter.next();
			stringEffects = stringEffects+ element.getName() + BusinessRules.CAUSE_EFFECT_SEPARATOR;
			createEffectNode(effectsNode.addNewEffect(), element);
		}
//		for (Iterator i = equivalenceClass.getEffects().iterator();i.hasNext();)
//		{
//			createEffectNode(effectsNode.addNewEffect(),(Effect)i);
//			stringEffects = stringEffects+((Effect)i.next()).getName()+BusinessRules.CAUSE_EFFECT_SEPARATOR;
//		}
//		effectsNode.newCursor().setTextValue(stringEffects);
//		
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
	

	
	

	/* (non-Javadoc)
	 * @see bi.view.report.data.CMReportData#getName()
	 */
	public String getName() {
		return CMMessages.getString("REPORT_FORMAT_TEST_CASE_LIST");
	}

	/* (non-Javadoc)
	 * @see bi.view.report.data.CMReportData#getReportFolder()
	 */
	public String getReportFolder() {
		ProjectReference projectReference = CMApplication.frame.getTreeWorkspaceView().getCurrentProjectReference();
	    TestObjectReference testObjectReference = CMApplication.frame.getTreeWorkspaceView().getCurrentTestObjectReference();
	    model.TestObject testObject =CMApplication.frame.getTreeWorkspaceView().getCurrentTestObject();
	    Session2 session = CMApplication.frame.getTreeWorkspaceView().getM_Session2();
		if( CMApplication.frame.getCmApplication().getSessionManager().getM_WorkspaceManager().isTestObjectCheckedOutLocalByTheSameUser(testObject, session) ) {
		  projectReference = projectReference.getM_LocalProjectReference();
		}
	    String absoluteReportsPath = CMApplication.frame.getCmApplication().getSessionManager().getM_WorkspaceManager().buildAbsoluteTestDataReportsPath(projectReference, testObjectReference);
	    return absoluteReportsPath;
	}

}
