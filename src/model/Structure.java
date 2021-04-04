/*******************************************************************************
Developed by BUSINESS SOFTWARE INNOVATIONS. .
Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
*******************************************************************************/
package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import model.util.CMCombinationsBean;
import model.util.CMDependencyBean;
import model.util.CMEffectsBean;
import model.util.CMElementsBean;
import model.util.CMErrorsBean;
import model.util.CMModelEventHandler;
import model.util.CMModelListener;
import model.util.CMModelSource;
import model.util.CMRequirementBean;
import model.util.TestCasesBean;

/**
 * @persistent
 */
public class Structure implements CMEffectsBean, CMRequirementBean,
CMModelSource, CMElementsBean, TestCasesBean, CMDependencyBean, CMCombinationsBean, CMErrorsBean  {

	private transient CMModelEventHandler handler;
    private Vector<Element> lnkElements = new Vector<Element>(0);

    private int id = -1;
    private String name = ""; //$NON-NLS-1$
    private String description = ""; //$NON-NLS-1$
    private Date timestamp = new Date();
    private List<Requirement> lnkRequirements;
    /**
     * @link aggregationByValue
     * @clientCardinality 1
     * @supplierCardinality 0..*
     * @associates <{Effect}>
     * @directed
     */
    private List<Effect> lnkEffects = new Vector<Effect>(0);
    /**
     * @clientCardinality 1
     * @supplierCardinality 0..*
     * @link aggregationByValue
     * @directed
     * @associates <{model.Dependency}>
     */
    private Vector<Dependency> lnkDependencies = new Vector<Dependency>(0);
    /**
     * @link aggregationByValue
     * @directed
     * @associates <{model.TestCase}>
     * @clientCardinality 1
     * @supplierCardinality 0..*
     */
    private Vector<TestCase> lnkTestCases = new Vector<TestCase>(0);

    private List<StdCombination> lnkStdCombinations;
    private TestObject m_TestObject;

    /**
     * @associates <{TestCaseGroup}>
     */
    private  List<TestCaseGroup> m_TestCaseGroups ;


    /**
     * @link aggregationByValue
     * @supplierCardinality 0..*
     */
    private Vector<CMError> m_CMErrors = new Vector<CMError>(0);

    public Structure() {

    }
    public int getId(){
            return id;
        }

    public void setId(int id){
            this.id = id;
        }

    public String getName(){ return name; }

    public void setName(String name){ this.name = name; }

    public String getDescription(){ return description; }

    public void setDescription(String description){ this.description = description; }

    public Date getTimestamp(){
    	if (timestamp == null)
    		timestamp = new Date();
    	return timestamp; }

    public void setTimestamp(Date timestamp){ this.timestamp = timestamp; }

    public Vector<Element> getLnkElements() {
    	if (this.lnkElements == null)
    		this.lnkElements = new Vector<Element>();
    return lnkElements; }

	public List<Effect> getEffects(){
    	  //lazy initialization
    	if (lnkEffects == null)
    		lnkEffects = new Vector<Effect>(0);
    	List<Effect> effects = new ArrayList<Effect>();
    	effects.addAll(lnkEffects);
    	//validate the effect
    	for (Effect effect : effects)
    		if (effect.getLnkStructure()!=this)
    			effect.setLnkStructure(this);
    	Collections.sort(effects);
    	//TODO after all changes can be made by notification
    	//return Collections.unmodifiableList(lnkEffects);
        return  Collections.unmodifiableList(effects);
        }

    public Vector<Dependency> getLnkDependencies(){
    	if (lnkDependencies == null)
    		lnkDependencies = new Vector<Dependency>();
            return lnkDependencies;
        }


    public Vector<TestCase> getLnkTestCases(){
    	if (lnkTestCases == null)
    		lnkTestCases = new Vector<TestCase>();
            return lnkTestCases;
        }


//    public Vector<StdCombination> getLnkStdCombinations(){
//    	if (lnkStdCombinations == null)
//    		lnkStdCombinations = new ArrayList<Combination>();
//            return lnkStdCombinations;
//        }


    public TestObject getTestObject(){
            return m_TestObject;
        }

    public void setTestObject(TestObject m_TestObject){
            this.m_TestObject = m_TestObject;
        }

    public List<TestCaseGroup> getM_TestCaseGroups(){
    	if (m_TestCaseGroups== null || m_TestCaseGroups.size()!=4){
    	m_TestCaseGroups = new Vector<TestCaseGroup>();
		m_TestCaseGroups.add(new PositiveTestCaseGroup());
		m_TestCaseGroups.add(new NegativeTestCaseGroup());
		m_TestCaseGroups.add(new FaultyTestCaseGroup());
		m_TestCaseGroups.add(new IrrelevantTestCaseGroup());
    	}
            return Collections.unmodifiableList( m_TestCaseGroups);
        }

    public Vector<CMError> getM_CMErrors(){
    	if (m_CMErrors == null)
    		m_CMErrors = new Vector<CMError>();

        return m_CMErrors;
    }


	/**
	*@autor smoreno
	 * @param p_id
	 * @return
	 */
	public Effect getLnkEffectbyName(String p_id) {
		for (Effect effect : getEffects())
			if (effect.getName().equalsIgnoreCase(p_id))
				return effect;
		return null;
	}
	public List<Requirement> getRequirements() {
		if (this.lnkRequirements== null)
			this.lnkRequirements = new ArrayList<Requirement>();
		return Collections.unmodifiableList(this.lnkRequirements);
	}

	public Requirement getRequirementByName(String p_name)
	{
		for(Requirement req : this.getRequirements())
			if (req.getName().equalsIgnoreCase(p_name))
				return req;
		return null;
	}
	/* (non-Javadoc)
	 * @see model.util.CMModelSource#addModelListener(model.util.CMModelListener)
	 */
	public void addModelListener(CMModelListener p_listener) {
		this.getHandler().addModelListener(p_listener);
	}
	/* (non-Javadoc)
	 * @see model.util.CMModelSource#removeModelListener(model.util.CMModelListener)
	 */
	public void removeModelListener(CMModelListener p_listener) {
		this.getHandler().removeModelListener(p_listener);
	}
    /**
	 * @return Returns the handler.
	 */
	public CMModelEventHandler getHandler() {
		if (this.handler== null)
			this.handler = new CMModelEventHandler();
		return this.handler;
	}
	/* (non-Javadoc)
	 * @see model.util.CMEffectsBean#addEffect(model.Effect)
	 */
	public void addEffect(Effect p_effect) {
		this.getEffects();
		if (!lnkEffects.contains(p_effect)){
			p_effect.setLnkStructure(this);
			this.lnkEffects.add(p_effect);
			Collections.sort(lnkEffects);
			this.getHandler().fireModelEventHappen(this,CMField.EFFECTS);
		}

	}
	/* (non-Javadoc)
	 * @see model.util.CMEffectsBean#removeEffect(model.Effect)
	 */
	public void removeEffect(Effect p_effect) {
		this.getEffects();
		if (lnkEffects.contains(p_effect))
		{
			this.lnkEffects.remove(p_effect);
			this.getHandler().fireModelEventHappen(this,CMField.EFFECTS);
		}
		p_effect.setLnkStructure(null);

	}
	/* (non-Javadoc)
	 * @see model.util.CMRequirementBean#addRequirement()
	 */
	public void addRequirement( Requirement p_requirement) {
		getRequirements();
		if (!this.lnkRequirements.contains(p_requirement)){
			this.lnkRequirements.add(p_requirement);
			p_requirement.setParentStructure(this);
			this.getHandler().fireModelEventHappen(this,CMField.REQUIREMENTS);
		}

	}
	/* (non-Javadoc)
	 * @see model.util.CMRequirementBean#removeRequirement()
	 */
	public void removeRequirement(Requirement p_requirement) {
		getRequirements();
		if (this.lnkRequirements.contains(p_requirement))
		{
		this.lnkRequirements.remove(p_requirement);

		this.getHandler().fireModelEventHappen(this,CMField.REQUIREMENTS);
		}
		p_requirement.setParentStructure(null);
	}
	/* (non-Javadoc)
	 * @see model.util.CMEffectsBean#addEffect(model.Effect, int)
	 */
	public void addEffect(Effect p_effect, int p_index) {
		this.getEffects();
		if (!this.lnkEffects.contains(p_effect))
		{
			this.lnkEffects.add(p_index,p_effect);
			this.getHandler().fireModelEventHappen(this,CMField.EFFECTS);
		}
	}
	/**
	 * @param p_state_positive
	 * @return
	 */
	public List<TestCase> getTestCasesByState(int p_state) {
		ArrayList<TestCase> list = new ArrayList<TestCase>();
		for (TestCase testCase : this.getLnkTestCases())
			if (testCase.getState()==p_state)
				list.add(testCase);
		return list;
	}
	/* (non-Javadoc)
	 * @see model.util.CMElementsBean#addElement(model.Element)
	 */
	public void addElement(Element p_Element) {
		getElements();
		if (!this.lnkElements.contains(p_Element))
		{
			this.lnkElements.add(p_Element);
			p_Element.setLnkStructure(this);
			Collections.sort(lnkElements);
			this.getHandler().fireModelEventHappen(this,CMField.ELEMENTS);
		}

	}
	/* (non-Javadoc)
	 * @see model.util.CMElementsBean#removeElement(model.Element)
	 */
	public void removeElement(Element p_Element) {
		getElements();
		if (this.lnkElements.contains(p_Element))
		{
			this.lnkElements.remove(p_Element);
			p_Element.setLnkStructure(null);
			this.getHandler().fireModelEventHappen(this,CMField.ELEMENTS);
		}

	}
	/* (non-Javadoc)
	 * @see model.util.CMElementsBean#getElements()
	 */
	public List<Element> getElements() {
		if (lnkElements == null)
			lnkElements = new Vector<Element>();
		List<Element> elems = new ArrayList<Element>();
		elems.addAll(lnkElements);
		return Collections.unmodifiableList(elems);
	}
	/**
	 * return the elements in the order defined by the comparator
	 * @param p_comparator
	 * @return
	 */
	public List<Element> getElements(Comparator p_comparator) {
		ArrayList<Element> listElems = new ArrayList<Element>();
		if (lnkElements == null)
			lnkElements = new Vector<Element>();
		listElems.addAll(lnkElements);
		Collections.sort(listElems,p_comparator);
		return Collections.unmodifiableList(listElems);
	}
	/* (non-Javadoc)
	 * @see model.util.TestCasesBean#getTestCases()
	 */
	public List<TestCase> getTestCases() {
		if (lnkTestCases == null)
			lnkTestCases = new Vector<TestCase>();
		List<TestCase> testCases = new ArrayList<TestCase>();
		testCases.addAll(lnkTestCases);
		return Collections.unmodifiableList(testCases);
	}
	/* (non-Javadoc)
	 * @see model.util.TestCasesBean#addTestCase(model.TestCase)
	 */
	public void addTestCase(TestCase p_testCase) {
		getTestCases();
		if (!lnkTestCases.contains(p_testCase))
		{
			lnkTestCases.add(p_testCase);
			p_testCase.setStructure(this);
			Collections.sort(lnkTestCases);
			this.getHandler().fireModelEventHappen(this,CMField.TESTCASES);
		}

	}
	/* (non-Javadoc)
	 * @see model.util.TestCasesBean#removeTestCase(model.TestCase)
	 */
	public void removeTestCase(TestCase p_testCase) {
		getTestCases();
		if (lnkTestCases.contains(p_testCase))
		{
			lnkTestCases.remove(p_testCase);
			p_testCase.setStructure(null);
			this.getHandler().fireModelEventHappen(this,CMField.TESTCASES);
		}

	}
	/* (non-Javadoc)
	 * @see model.util.CMDependencyBean#getDependencies()
	 */
	public List<Dependency> getDependencies() {
		if (lnkDependencies == null)
			lnkDependencies = new Vector<Dependency>();
		List<Dependency> dependencies = new ArrayList<Dependency>();
		dependencies.addAll(lnkDependencies);
		return Collections.unmodifiableList(dependencies);
	}
	/* (non-Javadoc)
	 * @see model.util.CMDependencyBean#addDependency(model.Dependency)
	 */
	public void addDependency(Dependency p_dependency) {
		getDependencies();
		if (!lnkDependencies.contains(p_dependency))
		{
			lnkDependencies.add(p_dependency);
			p_dependency.setLnkStructure(this);
			//sort the dependencies
			Collections.sort(lnkDependencies);
			this.getHandler().fireModelEventHappen(this,CMField.DEPENDENCIES);
		}

	}
	/* (non-Javadoc)
	 * @see model.util.CMDependencyBean#removeDependency(model.Dependency)
	 */
	public void removeDependency(Dependency p_dependency) {
		getDependencies();
		if (lnkDependencies.contains(p_dependency))
		{
			lnkDependencies.remove(p_dependency);
			this.getHandler().fireModelEventHappen(this,CMField.DEPENDENCIES);
		}
		p_dependency.setLnkStructure(null);


	}
	/* (non-Javadoc)
	 * @see model.util.StdCombinationsBean#getStdCombinations()
	 */
	public List<? extends Combination> getCombinations() {
		if (lnkStdCombinations == null)
			lnkStdCombinations = new ArrayList<StdCombination>();
		List<StdCombination> stdCombinations = new ArrayList<StdCombination>();
		stdCombinations.addAll((Collection<? extends StdCombination>) lnkStdCombinations);
		return stdCombinations;
	}
	/* (non-Javadoc)
	 * @see model.util.StdCombinationsBean#addStdCombination(model.StdCombination)
	 */
	public void addCombination(Combination p_stdCombination) {
		getCombinations();
		if (!lnkStdCombinations.contains(p_stdCombination) && p_stdCombination instanceof StdCombination){
			lnkStdCombinations.add((StdCombination) p_stdCombination);
			Collections.sort(lnkStdCombinations);
			this.getHandler().fireModelEventHappen(this,CMField.STDCOMBINATIONS);
		}
		}



	/* (non-Javadoc)
	 * @see model.util.StdCombinationsBean#removeStdCombination(model.StdCombination)
	 */
	public void removeCombination(Combination p_stdCombination) {
		getCombinations();
		if (lnkStdCombinations.contains(p_stdCombination)){
			lnkStdCombinations.remove(p_stdCombination);
			this.getHandler().fireModelEventHappen(this,CMField.STDCOMBINATIONS);
		}

	}
	/* (non-Javadoc)
	 * @see model.util.CMElementsBean#addElement(model.Element, int)
	 */
	public void addElement(Element p_element, int p_Position) {
		getElements();
		if (!this.lnkElements.contains(p_element))
		{
			if ((this.lnkElements.size() > p_Position)&& (p_Position>-1))
				this.lnkElements.add(p_Position,p_element);
			else
				this.lnkElements.add(p_element);

			p_element.setLnkStructure(this);
			this.getHandler().fireModelEventHappen(this, CMField.ELEMENTS);
		}

	}


	//svonborries_07062006_begin
	public boolean hasExpectedResults(){
		for (int i = 0; i < getLnkTestCases().size(); i++) {
			TestCase testCase = getLnkTestCases().elementAt(i);
			for (Iterator iter = testCase.getRelatedEffects().iterator(); iter.hasNext();) {
    			Effect effect = (Effect) iter.next();
    			if(effect.getLnkExpectedResults().size()>0)
    				return true;
			}
		}
		return false;
	}
//	svonborries_07062006_end
	/**
	 * @param combination
	 * @return a list of test cases with a specified combination
	 */
	public List<TestCase> getTestCases(Combination combination) {
		List<TestCase> testCases = new ArrayList<TestCase>();
		for (TestCase testCase : getTestCases())
			if (testCase.getCombinations().contains(combination))
				testCases.add(testCase);
		return testCases;
	}
		public void clear() {
	        lnkElements = null;
	        lnkEffects = null;
	        lnkDependencies = null;
	        lnkTestCases = null;
	        lnkStdCombinations = null;
	        // Do not delete the errors just the test case asigment
	        for (CMError error : getM_CMErrors())
	        	error.setM_TestCases(null);



	    }
		public List<Element> getNonEmptyElements() {
			List<Element> elements = new ArrayList<Element>();
			for (Element element : getElements())
				if (element.getEquivalenceClasses().size()>0)
					elements.add(element);
			return elements;
		}
	public Effect getEffectbyName(String name2) {
			for (Effect effect : getEffects())
				if (effect.getName().equalsIgnoreCase(name2))
					return effect;
			return null;
		}
	public void fireModelEventHappen(CMField field) {
		getHandler().fireModelEventHappen(this, field);

	}
	/**
	 * returns all test cases that matchs the equivalence classes
	 * @param equivList
	 * @return
	 */
	public List<TestCase> getTestCases(List<EquivalenceClass> equivList) {
		List<TestCase> testCasesList = new ArrayList<TestCase>();
		for (TestCase testCase: getTestCases()) 
		    if (testCase.getEquivalenceClasses().equals(equivList)) 
		    	testCasesList.add(testCase);		
		return testCasesList;
	}
	public void addError(CMError error) {
		if (!getErrors().contains(error)){
			m_CMErrors.add(error);
			this.getHandler().fireModelEventHappen(this, CMField.ERRORS);
		}
		
	}
	public List<CMError> getErrors() {
		if (m_CMErrors == null)
			m_CMErrors = new Vector<CMError>();
		List<CMError> errors = new ArrayList<CMError>();
		errors.addAll(m_CMErrors);
		return errors;
	}
	public void removeError(CMError error) {
		if (getErrors().contains(error)){
			m_CMErrors.remove(error);
			this.getHandler().fireModelEventHappen(this, CMField.ERRORS);
		}
		
	}
}
