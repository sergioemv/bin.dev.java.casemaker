/*******************************************************************************
Developed by BUSINESS SOFTWARE INNOVATIONS. .
Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
*******************************************************************************/
package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import model.util.CMCloneable;
import model.util.CMDescriptionBean;
import model.util.CMIdBean;
import model.util.CMIdComparator;
import model.util.CMModelEventHandler;
import model.util.CMModelListener;
import model.util.CMModelSource;
import model.util.CMRequirementBean;
import model.util.CMRiskLevelBean;
import model.util.CMStateBean;
import bi.controller.StructureManager;
import bi.view.lang.CMMessages;

/**
 * @persistent
 */
public class Effect  implements CMModelSource, Cloneable, CMDescriptionBean,CMIdBean, CMStateBean, CMRiskLevelBean, Comparable, CMRequirementBean, CMCloneable {

	private Structure lnkStructure;
	private transient CMModelEventHandler handler;
	private int id = -1;
	private String name = ""; //$NON-NLS-1$
	private String description = ""; //$NON-NLS-1$
	private Date timestamp = new Date();
	 /**
     * @clientCardinality 1
     * @supplierCardinality 0..*
     * @associates <{EquivalenceClass}>
     * @directed
     */
    private Vector<EquivalenceClass> lnkEquivalenceClasses = new Vector<EquivalenceClass>(0);
	private transient Comparator defaultComparator;
    /**
     * @clientCardinality 1
     * @supplierCardinality 0..*
     * @associates <{Requirement}>
     * @directed
     */
	private List<Requirement> lnkRequirements;
	/**
     * @clientCardinality 1
     * @supplierCardinality 0..*
     * @associates <{ExpectedResults}>
     * @directed
     */
	private List<ExpectedResult> lnkExpectedResults;

    private int m_State=STATE_POSITIVE;
    private int m_RiskLevel=0;

	//constructor
    public Effect() {
    	//always put the current date in the creation of the cause effect
    	this.timestamp = new Date();
    }
    //methods
    public int getId(){
            return id;
    }

    public void setId(int id){
        this.id = id;
        this.name = generateName(id);
    }
    private String generateName(int p_id) {
      StringBuffer idString = new StringBuffer();
      idString.append(p_id);
      int length = idString.length();
        for(int i = 0; i < BusinessRules.ID_LENGTH-length; i++) {
          idString.insert(0,BusinessRules.ID_FILLER_CHARACTER);
        }
      idString.insert(0,CMMessages.getString("CAUSE_EFFECT_PREFIX")); //$NON-NLS-1$
      return  idString.toString();
    }
    public String getName(){
      return name;
    }

    public String getDescription(){ return description; }

    public void setDescription(String description){ 
    	this.description = description; 
    	addExpectedResult(new ExpectedResult(getName(), getDescription(), this));
    	}

    public Date getTimestamp(){ return timestamp; }

    private void setTimestamp(Date timestamp){ this.timestamp = timestamp; }

    public Vector getLnkEquivalenceClasses(){
            return lnkEquivalenceClasses;
        }

    private void setLnkEquivalenceClasses(Vector<EquivalenceClass> lnkEquivalenceClasses){
            this.lnkEquivalenceClasses = lnkEquivalenceClasses;
        }

    public Effect makeClone() {
      Effect b = null;
      try {
       b = (Effect)this.clone();
       b.setTimestamp((Date) this.timestamp.clone());
       b.clear();
       for (ExpectedResult exp : this.getLnkExpectedResults())
    	   b.addExpectedResult(exp);
       for (Requirement exp : this.getRequirements())
    	   b.addRequirement(exp);
       if (this.getLnkStructure()!=null)
       if (this.getLnkStructure() == StructureManager.getSelectedStructure())
       {
    	   for (Requirement req : this.getRequirements())
    		   b.addRequirement(req);
       }else
    	   for (Requirement req : this.getRequirements())
    	   {
    		   Requirement reqClone = req.makeClone();
    		   this.getLnkStructure().addRequirement(reqClone);
    		   b.addRequirement(reqClone);
    	   }
      } catch(CloneNotSupportedException e) {
        e.printStackTrace();
      }
      return b;
    }
    public void clear(){
    	lnkEquivalenceClasses = null;
    	lnkExpectedResults = null;
    	lnkRequirements = null;
    }
    /**
     * This method is used to Sort in CMUtilClassComparator
     * @author hcanedo
     * @version 1.0
     * @since 14/06/2005 05:28:10 P.M.
     */
    public String toString(){
    	return getName();
    }
    @SuppressWarnings("unchecked")
	public int compareTo(Object p_o) {
		return getDefaultComparator().compare(this,p_o);
	}

	/**
	 * @return Returns the defaultComparator.
	 */
	public Comparator getDefaultComparator() {
		if (this.defaultComparator==null)
			//by default orderer by Id
			this.defaultComparator = new CMIdComparator();
		return this.defaultComparator;
	}

	/* (non-Javadoc)
	 * @see model.util.CMRequirementBean#getLnkRequirements()
	 */
	public List<Requirement> getRequirements() {
		if (this.lnkRequirements== null)
			this.lnkRequirements = new ArrayList<Requirement>();
		return Collections.unmodifiableList(this.lnkRequirements);
	}
	public List<ExpectedResult> getLnkExpectedResults() {
		if (this.lnkExpectedResults == null)
			this.lnkExpectedResults = new ArrayList<ExpectedResult>();
		return Collections.unmodifiableList(this.lnkExpectedResults);
	}
	/* (non-Javadoc)
	 * @see model.util.CMRequirementBean#addRequirement(model.Requirement)
	 */
	public void addRequirement(Requirement p_requirement) {
		getRequirements();
		this.lnkRequirements.add(p_requirement);
		this.getHandler().fireModelEventHappen(this, CMField.REQUIREMENTS);
	}
	/* (non-Javadoc)
	 * @see model.util.CMRequirementBean#removeRequirement(model.Requirement)
	 */
	public void removeRequirement(Requirement p_requirement) {
		getRequirements();
		this.lnkRequirements.remove(p_requirement);
		this.getHandler().fireModelEventHappen(this, CMField.REQUIREMENTS);

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
		if (this.handler == null)
			this.handler = new CMModelEventHandler();
		return this.handler;
	}
	public void addExpectedResult(ExpectedResult expRes){
		getLnkExpectedResults();
		this.lnkExpectedResults.add(expRes);
		expRes.setParentEffect(this);
		this.getHandler().fireModelEventHappen(this, CMField.EXPECTED_RESULTS);
	}
	public void removeExpectedResult(ExpectedResult expectedResults){
		getLnkExpectedResults();
		this.lnkExpectedResults.remove(expectedResults);
		expectedResults.setParentEffect(null);
		this.getHandler().fireModelEventHappen(this, CMField.EXPECTED_RESULTS);
	}
	public ExpectedResult getExpectedResultbyName(String p_name)
	{
		for (ExpectedResult exp : getLnkExpectedResults())
			if (p_name.equalsIgnoreCase(exp.getName()))
				return exp;
		return null;
	}
	/**
	 * @param p_selectedStructure
	 * @return
	 */
	public boolean isUsed() {
		Structure structure = getLnkStructure();
		if (structure == null) return false;
		for (Element element : structure.getLnkElements())
			for (EquivalenceClass equivalence : element.getEquivalenceClasses())
				if (equivalence.getEffects().contains(this))
					return true;
		for (Dependency dependency : structure.getLnkDependencies())
			for (Combination combination : dependency.getLnkCombinations())
				if (combination.getEffects().contains(this))
					return true;
		for (TestCase testCase : structure.getLnkTestCases())
			if (testCase.getRelatedEffects().contains(this))
				return true;

		return false;
	}
	public Structure getLnkStructure() {
		return this.lnkStructure;
	}
	public void setLnkStructure(Structure p_lnkStructure) {
		this.lnkStructure = p_lnkStructure;
	}
	public void setState(int p_state) {
		m_State=p_state;
	}

	public void setState(State p_state) {
		m_State=p_state.intValue();

	}

	public String getStateName() {
		 return Arrays.asList(State.values()).get(m_State).toString();
	}

	public int getState() {
		return m_State;
	}

	public void setRiskLevel(int level) {
		m_RiskLevel=level;
	}

	public int getRiskLevel() {
		return m_RiskLevel;
	}
	public List<CMRiskLevelBean> getChildRiskLevels() {
		Structure structure = StructureManager.getSelectedStructure();
		List<CMRiskLevelBean> listRL = new ArrayList<CMRiskLevelBean>();
		if (structure!=null){
			//all equialence classes that contains this effect
			for (Element element: structure.getElements())
				for (EquivalenceClass equivalenceClass : element.getEquivalenceClasses())
					 if (equivalenceClass.getEffects().contains(this))
						 listRL.add(equivalenceClass);
			// all combinations that contains this effect
			for (Dependency dependency: structure.getDependencies())
				for (Combination combination : dependency.getCombinations()){
					 if (combination.getEffects().contains(this))
						 listRL.add(combination);
					 for (Combination childCombination : combination.getCombinationsRecursiv())
						 if (childCombination.getEffects().contains(this))
							 listRL.add(childCombination);
				}
		}

		return listRL;
	}
	public List<CMRiskLevelBean> getParentRiskLevels() {

		return new ArrayList<CMRiskLevelBean>();
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<? extends CMStateBean> getChildStates() {
		Structure structure = StructureManager.getSelectedStructure();
		List<CMStateBean> listST = new ArrayList<CMStateBean>();
		if (structure!=null){
			//all equialence classes that contains this effect
			for (Element element: structure.getElements())
				for (EquivalenceClass equivalenceClass : element.getEquivalenceClasses())
					 if (equivalenceClass.getEffects().contains(this))
						 listST.add(equivalenceClass);
			// all combinations that contains this effect
			for (Dependency dependency: structure.getDependencies())
				for (Combination combination : dependency.getCombinations()){
					 if (combination.getEffects().contains(this))
						 listST.add(combination);
					 for (Combination childCombination : combination.getCombinationsRecursiv())
						 if (childCombination.getEffects().contains(this))
							 listST.add(childCombination);
				}
		}

		return listST;
	}
	public List<? extends CMStateBean> getParentStates() {
		// TODO Auto-generated method stub
		return new ArrayList<CMStateBean>();
	}
	//an effect is valid only if the parent structure is not null and contains this effect
	public boolean isValid() {
		return (this.getLnkStructure()!=null)&&(this.getLnkStructure().getEffects().contains(this));
	}
	public void fireModelEventHappen(CMField field) {
		getHandler().fireModelEventHappen(this, field);

	}
}
