/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.view.report.data;



import org.apache.xmlbeans.XmlObject;

import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.report.data.depcomblist.CauseEffectType;
import bi.view.report.data.depcomblist.CauseEffectsType;
import bi.view.report.data.depcomblist.CombinationType;
import bi.view.report.data.depcomblist.DependencyType;
import bi.view.report.data.depcomblist.ElementType;
import bi.view.report.data.depcomblist.EquivalenceClassType;
import bi.view.report.data.depcomblist.ProjectType;
import bi.view.report.data.depcomblist.ReportDependencyDocument;
import bi.view.report.data.depcomblist.TestObjectType;
import bi.view.report.data.depcomblist.ReportDependencyDocument.ReportDependency;



import model.Combination;
import model.Dependency;
import model.Effect;
import model.Element;
import model.EquivalenceClass;
import model.Project2;
import model.ProjectReference;
import model.Session2;
import model.Structure;
import model.TestObject;
import model.TestObjectReference;


/**
 * @author smoreno
 *
 */
public class CMReportDataXMLDepCombList1 extends CMAbstractReportDataXML {

	private Dependency m_Dependency;
	private Project2 m_Project;
	private Structure m_Structure;

	/**
	 *
	 */
	public CMReportDataXMLDepCombList1() {
		super();
	}

	/* (non-Javadoc)
	 * @see bi.view.report.data.CMAbstractReportDataXML#createReportDataStructure()
	 */
	@Override
	public XmlObject createReportDataStructure() {
		logger.debug("Creating the Document Structure");
		try {
		m_Structure = (Structure) getParameter("STRUCTURE");
		m_Project = (Project2) getParameter("PROJECT");
		m_Dependency = (Dependency) getParameter("DEPENDENCY");
		if (m_Project == null || m_Structure==null || m_Dependency ==null)
			throw new Exception("Parameters not initialized");
		} catch (Exception e){
			logger.error(e.getMessage());
			return null;
		}
		ReportDependencyDocument doc = ReportDependencyDocument.Factory.newInstance();
		//create root node
		doc.addNewReportDependency().setLabelDC(CMMessages.getString("REPORT_DEPENDENCY_COMBINATION_LIST_TITLE"));
		//create the project node
		createProjectNode(doc.getReportDependency().addNewProject());
		//create the test object node
		createTestObjectNode(doc.getReportDependency().addNewTestObject());
		//create elements node
		createElementNodes(doc.getReportDependency());
		//create the dependency node
		createDependencyNode(doc.getReportDependency().addNewDependency());
		//create the user
		doc.getReportDependency().addNewUserName().setLabelDC(CMMessages.getString("REPORT_USER_LABEL"));
		doc.getReportDependency().getUserName().newCursor().setTextValue(System.getProperty("user.name"));
		//	create the date
		doc.getReportDependency().addNewDate().setLabelDC(CMMessages.getString("REPORT_DATE_LABEL"));
		doc.getReportDependency().getDate().newCursor().setTextValue(new java.util.Date().toString());

		return doc;
	}

	/**
	*@autor smoreno
	 * @param p_type
	 */
	private void createDependencyNode(DependencyType p_dependencyNode) {
		p_dependencyNode.setLabelDC(CMMessages.getString("MENU_DEPENDENCY"));
		//set the id
		p_dependencyNode.addNewId().newCursor().setTextValue(new Integer(m_Dependency.getId()).toString());
		//set the name
		p_dependencyNode.addNewName().newCursor().setTextValue(m_Dependency.getName());
		//create the combination nodes
		createCombinationNodes(p_dependencyNode);

	}

	/**
	*@autor smoreno
	 * @param p_dependencyNode
	 */
	private void createCombinationNodes(DependencyType p_dependencyNode) {
		for (Combination combination : m_Dependency.getLnkCombinations())
			createCombinationNode(p_dependencyNode.addNewCombination(),combination);
	}

	/**
	*@autor smoreno
	 * @param p_type
	 * @param p_combination
	 */
	private void createCombinationNode(CombinationType p_CombinationNode, Combination p_combination) {
		//set the id
		p_CombinationNode.addNewId().newCursor().setTextValue(new Integer(p_combination.getId()).toString());
		//set the name
		p_CombinationNode.addNewName().newCursor().setTextValue(p_combination.getName());
		//create effecst node
		createEffectsNode(p_CombinationNode.addNewCauseEffects(),p_combination);
		//create equivalence class references
		createEquivalenceClassReferenceNodes(p_CombinationNode,p_combination);
	}

	/**
	*@autor smoreno
	 * @param p_combinationNode
	 * @param p_combination
	 */
	private void createEquivalenceClassReferenceNodes(CombinationType p_combinationNode, Combination p_combination) {
		for (EquivalenceClass equivalenceClass : p_combination.getEquivalenceClassesRecursiv())
			p_combinationNode.addNewEqClassReference().setUniqueId("E"+equivalenceClass.getLnkElement().getId()+"EQ"+equivalenceClass.getId());
	}

	/**
	*@autor smoreno
	 * @param p_CauseEffectsNode
	 * @param p_combination
	 */
	private void createEffectsNode(CauseEffectsType p_CauseEffectsNode, Combination p_combination) {
		p_CauseEffectsNode.setLabelDC(CMMessages.getString("CAUSE_EFFECT_PREFIX"));
		for (Effect effect : p_combination.getEffects())
			  createCauseEffectNode(p_CauseEffectsNode.addNewCauseEffect(),effect);
	}

	/**
	*@autor smoreno
	 * @param p_type
	 * @param p_effect
	 */
	private void createCauseEffectNode(CauseEffectType p_EffectNode, Effect p_effect) {
		p_EffectNode.addNewName().newCursor().setTextValue(p_effect.getName());
	}

	/**
	*@autor smoreno
	 * @param p_reportDependency
	 */
	private void createElementNodes(ReportDependency p_reportDependency) {
		for (Element element : m_Dependency.getLnkElements())
				createElementNode(p_reportDependency.addNewElement(),element);
	}

	/**
	*@autor smoreno
	 * @param p_type
	 * @param p_element
	 */
	private void createElementNode(ElementType p_ElementNode, Element p_element) {
		//add the id
		//p_ElementNode.addNewId().newCursor().setTextValue(new Integer(p_element.getId()).toString()); cc change this
		p_ElementNode.addNewId().newCursor().setTextValue(new Integer(m_Dependency.getLnkElements().indexOf(p_element)).toString());
		//add the name
		p_ElementNode.addNewName().setLabelDC(CMMessages.getString("REPORT_TESTCASELIST_TO_EXCEL_TABLE_ELEMENT_LABEL"));
		p_ElementNode.getName().newCursor().setTextValue(p_element.getName());
//		set the description
		p_ElementNode.addNewDescription().setLabelDC(CMMessages.getString("REPORT_TESTCASELIST_TO_EXCEL_TABLE_DESCRIPTION_LABEL"));
		p_ElementNode.getDescription().newCursor().setTextValue(p_element.getDescription());
		//create equivalence classes node
		createEquivalenceClassesNodes(p_ElementNode,p_element);
	}

	/**
	*@autor smoreno
	 * @param p_elementNode
	 * @param p_element
	 */
	@SuppressWarnings("deprecation")
	private void createEquivalenceClassesNodes(ElementType p_elementNode, Element p_element) {
		for (EquivalenceClass equivalenceClass : p_element.getEquivalenceClasses())
			if (m_Dependency.getLnkEquivalenceClasses().contains(equivalenceClass))
				createEquivalenceClassNode(p_elementNode.addNewEquivalenceClass(),equivalenceClass);
	}

	/**
	*@autor smoreno
	 * @param p_type
	 * @param p_equivalenceClass
	 */
	private void createEquivalenceClassNode(EquivalenceClassType p_equivalenceClassNode, EquivalenceClass p_equivalenceClass) {
		//create the unique id
		p_equivalenceClassNode.setUniqueId("E"+new Integer(p_equivalenceClass.getLnkElement().getId()).toString()+"EQ"+new Integer(p_equivalenceClass.getId()).toString());
		//create the id
		p_equivalenceClassNode.addNewId().setLabelDC(CMMessages.getString("REPORT_TESTCASELIST_TO_EXCEL_TABLE_EQUIVALENCECLASS_NAME_LABEL"));
		p_equivalenceClassNode.getId().newCursor().setTextValue(p_equivalenceClass.getName());
		//create the state
		p_equivalenceClassNode.addNewState().setLabelDC(CMMessages.getString("REPORT_ERROR_STATE_LABEL_VALUE"));
		p_equivalenceClassNode.getState().newCursor().setTextValue(p_equivalenceClass.getStateName());
		//create the value
		p_equivalenceClassNode.addNewValue().setLabelDC(CMMessages.getString("REPORT_TESTCASELIST_TO_EXCEL_TABLE_EQUIVALENCECLASS_VALUE_LABEL"));
		p_equivalenceClassNode.getValue().newCursor().setTextValue(p_equivalenceClass.getValue());
		//create the description
		p_equivalenceClassNode.addNewDescription().setLabelDC(CMMessages.getString("REPORT_TESTCASELIST_TO_EXCEL_TABLE_DESCRIPTION_LABEL"));
		p_equivalenceClassNode.getDescription().newCursor().setTextValue(p_equivalenceClass.getDescription());

	}

	/**
	*@autor smoreno
	 * @param p_type
	 */
	private void createTestObjectNode(TestObjectType p_TestObjectNode) {
		//set the name
		p_TestObjectNode.addNewName().setLabelDC(CMMessages.getString("REPORT_OBJECT_LABEL"));
		p_TestObjectNode.getName().newCursor().setTextValue(m_Structure.getTestObject().getName());
		//set the description
		p_TestObjectNode.addNewDescription().newCursor().setTextValue(m_Structure.getTestObject().getDescription());
		p_TestObjectNode.getDescription().setLabelDC(CMMessages.getString("REPORT_OBJECT_DESCRIPTION_LABEL"));
	}

	/**
	*@autor smoreno
	 * @param p_type
	 */
	private void createProjectNode(ProjectType p_projectNode) {
			p_projectNode.addNewName().setLabelDC(CMMessages.getString("REPORT_PROJECT_LABEL"));
			p_projectNode.getName().newCursor().setTextValue(m_Project.getName());
	}

	/* (non-Javadoc)
	 * @see bi.view.report.data.CMReportDataDependency#setM_Dependency(model.Dependency)
	 */
	public void setDependency(Dependency p_dependency) {
		this.m_Dependency = p_dependency;

	}

	/* (non-Javadoc)
	 * @see bi.view.report.data.CMReportDataProject#setM_Project(model.Project2)
	 */
	public void setProject(Project2 p_project) {
		this.m_Project = p_project;

	}

	/* (non-Javadoc)
	 * @see bi.view.report.data.CMReportData#getName()
	 */
	public String getName() {
		return CMMessages.getString("REPORT_DATA_DEPENDENCY_COMBINATION");
	}

	/* (non-Javadoc)
	 * @see bi.view.report.data.CMReportDataStructure#setM_Structure(model.Structure)
	 */
	public void setStructure(Structure p_structure) {
		this.m_Structure = p_structure;

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