/*******************************************************************************
Developed by BUSINESS SOFTWARE INNOVATIONS. .
Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
*******************************************************************************/
package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import model.util.CMCombinationsBean;
import model.util.CMDescriptionBean;
import model.util.CMDescriptionEditableBean;
import model.util.CMElementsBean;
import model.util.CMEquivalenceClassesBean;
import model.util.CMIdBean;
import model.util.CMIdComparator;
import model.util.CMModelEventHandler;
import model.util.CMModelListener;
import model.util.CMModelSource;
import model.util.CMOriginTypeBean.Origin;

import bi.controller.DependencyManager;
import bi.view.lang.CMMessages;

/**
 * @persistent
 */
public class Dependency implements Comparable, CMIdBean, CMCombinationsBean,CMElementsBean, CMModelSource, CMEquivalenceClassesBean,
       CMDescriptionBean, CMDescriptionEditableBean{
    private int id = -1;
    private String name = "";
    private String description = "";
    //Ccastedo begins 11-11-05
    private String descriptionEditable = "";
    //ccastedo ends 11-11-05
    private Date timestamp = new Date();

    /**
     * @clientCardinality 1
     * @supplierCardinality 2..*
     * @associates <{Element}>
     * @link aggregation
     * @directed*/
    private Vector<Element> lnkElements = new Vector<Element>(0);

    /**
     * @link aggregation
     * @clientCardinality 1
     * @supplierCardinality 0..*
     * @directed
     * @associates <{EquivalenceClass}>
     */
    private Vector<EquivalenceClass> lnkEquivalenceClasses = new Vector<EquivalenceClass>(0);

    /**
     * @clientCardinality 1
     * @supplierCardinality 0..*
     * @directed
     * @link aggregationByValue
     * @associates <{Combination}>
     */
    private Vector<Combination> lnkCombinations = new Vector<Combination>(0);

    private Structure lnkStructure = null;
    private transient Comparator defaultComparator;
    private transient CMModelEventHandler handler;


    public Dependency() {
     this.timestamp = new Date();
     this.lnkElements = new Vector(0);
		 this.lnkCombinations = new Vector(0);
     this.lnkEquivalenceClasses = new Vector(0);
    }
    public int getId(){
            return id;
        }

    public void setId(int id){
            this.id = id;
            this.name = generateName(id);
        }

   private String generateName(int id) {
      StringBuffer idString = new StringBuffer();
      idString.append(id);
      int length = idString.length();
        for(int i = 0; i < BusinessRules.ID_LENGTH-length; i++) {
          idString.insert(0,BusinessRules.ID_FILLER_CHARACTER);
        }
      idString.insert(0,CMMessages.getString("DEPENDENCY_PREFIX"));
      return  idString.toString();
    }


    public String getName(){ return name; }

    public void setName(String name){ this.name = name; }

    public String getDescription(){
    	/**
    	 * @author hmendez
    	 * @date 13122005
    	 * @description used to rebuild the Dependencies descriptions
    	 */
    	//get the calculated description according to the model
    	String l_generatedDescription = getGeneratedDescription();
    	//compare with the actual description
    	if (!description.equalsIgnoreCase(l_generatedDescription))
    		//if they are different set the description
    		setDescription(l_generatedDescription);
    	/**
    	 * hmendez_end
    	 */
    	return description; }

    private String getGeneratedDescription() {
        DependencyManager l_dependencyManager = new DependencyManager();
        return l_dependencyManager.getDescriptionBasedOnExistingElements(this.lnkElements);
	}
    public String getDescriptionWithOutCheck(){
       return description;
    }
	public void setDescription(String description){ this.description = description; }

    //castedo bigins 11-11-05
    public String getDescriptionEditable(){
    	if (descriptionEditable==null)
    		descriptionEditable = "";
    	return descriptionEditable; }

    public void setDescriptionEditable(String descriptionEditable){ this.descriptionEditable = descriptionEditable; }
    //ccastedo ends 11-11-05

    public Date getTimestamp(){ return timestamp; }

    public void setTimestamp(Date timestamp){ this.timestamp = timestamp; }

    public Vector<Element> getLnkElements() {
    	if (lnkElements == null)
    		lnkElements = new Vector<Element>();
    	return lnkElements; }

    public void setLnkElements(Vector<Element> lnkElements) { this.lnkElements = lnkElements; }

    public Vector<EquivalenceClass> getLnkEquivalenceClasses(){
            return lnkEquivalenceClasses;
        }

    public void setLnkEquivalenceClasses(Vector<EquivalenceClass> lnkEquivalenceClasses){
            this.lnkEquivalenceClasses = lnkEquivalenceClasses;
        }

    public Vector<Combination> getLnkCombinations(){
    	if (this.lnkCombinations == null)
    		this.lnkCombinations = new Vector<Combination>();
            return lnkCombinations;
        }

    public void setLnkCombinations(Vector<Combination> lnkCombinations){
            this.lnkCombinations = lnkCombinations;
        }

    public void insertCombinationsAt(Vector combinations, int index) {
      int numOfCombinations = combinations.size();
			Combination combination = null;
      for( int i = 0; i < numOfCombinations; i++) {
        combination = (Combination) combinations.elementAt(i);
      	lnkCombinations.insertElementAt(combination,index);
        index++;
      }
    }



	public Structure getLnkStructure() {
		return lnkStructure;
	}

	public void setLnkStructure(Structure lnkStructure) {
		this.lnkStructure = lnkStructure;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
				return this.getName()+this.getDescription();
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(T)
	 */
	public int compareTo(Object p_o) {
		return getDefaultComparator().compare(this,p_o);
	}

	/**
	 * @return Returns the orderHandler.
	 */


	private Comparator getDefaultComparator() {
		if (this.defaultComparator==null)
			this.defaultComparator = new CMIdComparator();
		return this.defaultComparator;
	}
	/**
	 * @param p_state_positive
	 * @return
	 */
	public List<Combination> getCombinationsByState(int p_state) {
		List<Combination> combs = new ArrayList<Combination>();
		for (Combination combination : getLnkCombinations())
			if (combination.getState() == p_state)
				combs.add(combination);
		return combs;
	}
	public CMModelEventHandler getHandler() {
		if (this.handler == null)
			this.handler = new CMModelEventHandler();
		return this.handler;
	}
	/* (non-Javadoc)
	 * @see model.util.CMCombinationsBean#addCombination(model.Combination)
	 */
	public void addCombination(Combination p_Combination) {
		this.getCombinations();
		if (!this.lnkCombinations.contains(p_Combination))
		{
			this.lnkCombinations.add(p_Combination);
			Collections.sort(lnkCombinations);
			p_Combination.setDependency(this);
			this.getHandler().fireModelEventHappen(this,CMField.COMBINATIONS);
		}
	}


	/* (non-Javadoc)
	 * @see model.util.CMCombinationsBean#removeCombination(model.Combination)
	 */
	public void removeCombination(Combination p_Combination) {
		this.getCombinations();
		if (this.lnkCombinations.contains(p_Combination))
		{
			this.lnkCombinations.remove(p_Combination);
			p_Combination.setDependency(null);
			this.getHandler().fireModelEventHappen(this,CMField.COMBINATIONS);
		}
	}
	/* (non-Javadoc)
	 * @see model.util.CMCombinationsBean#getCombinations()
	 */
	public List<Combination> getCombinations() {
		if (lnkCombinations == null)
			lnkCombinations= new Vector<Combination>();
		List<Combination> combs = new ArrayList<Combination>();
		combs.addAll(lnkCombinations);
		return Collections.unmodifiableList(combs);
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
	/* (non-Javadoc)
	 * @see model.util.CMElementsBean#addElement(model.Element)
	 */
	public void addElement(Element p_Element) {
		getElements();
		if (!lnkElements.contains(p_Element))
		{
			lnkElements.add(p_Element);
			this.getHandler().fireModelEventHappen(this,CMField.ELEMENTS);
		}

	}
	/* (non-Javadoc)
	 * @see model.util.CMElementsBean#removeElement(model.Element)
	 */
	public void removeElement(Element p_Element) {
		getElements();
		if (lnkElements.contains(p_Element))
		{
			lnkElements.remove(p_Element);
			this.getHandler().fireModelEventHappen(this,CMField.ELEMENTS);
		}

	}
	/* (non-Javadoc)
	 * @see model.util.CMElementsBean#getElements()
	 */
	public List<Element> getElements() {
		if (this.lnkElements == null)
			this.lnkElements = new Vector<Element>();
		List<Element> elems = new ArrayList<Element>();
		elems.addAll(lnkElements);
		return Collections.unmodifiableList(elems);
	}
	/* (non-Javadoc)
	 * @see model.util.CMEquivalenceClassesBean#getEquivalenceClasses()
	 */
	public List<EquivalenceClass> getEquivalenceClasses() {
		if (lnkEquivalenceClasses == null)
			lnkEquivalenceClasses = new Vector<EquivalenceClass>();
		List<EquivalenceClass> equivs = new ArrayList<EquivalenceClass>();
		equivs.addAll(lnkEquivalenceClasses);
		return Collections.unmodifiableList(equivs);
	}

	public List<EquivalenceClass> getEquivalenceClasses(Element element){
		List<EquivalenceClass> eqs = new ArrayList<EquivalenceClass>();
		for (EquivalenceClass equivalenceClass : getEquivalenceClasses())
			if (element.getEquivalenceClasses().contains(equivalenceClass))
				eqs.add(equivalenceClass);
		return eqs;

	}
	/* (non-Javadoc)
	 * @see model.util.CMEquivalenceClassesBean#addEquivalenceClass(model.EquivalenceClass)
	 */
	public void addEquivalenceClass(EquivalenceClass p_equivalenceClass) {
		getEquivalenceClasses();
		if (!this.lnkEquivalenceClasses.contains(p_equivalenceClass))
		{
			lnkEquivalenceClasses.add(p_equivalenceClass);
			this.getHandler().fireModelEventHappen(this,CMField.EQUIVALENCE_CLASSES);
		}
	}
	/* (non-Javadoc)
	 * @see model.util.CMEquivalenceClassesBean#removeEquivalenceClass(model.EquivalenceClass)
	 */
	public void removeEquivalenceClass(EquivalenceClass p_equivalenceClass) {
		getEquivalenceClasses();
		if (this.lnkEquivalenceClasses.contains(p_equivalenceClass))
		{
			lnkEquivalenceClasses.remove(p_equivalenceClass);
			this.getHandler().fireModelEventHappen(this,CMField.EQUIVALENCE_CLASSES);
		}


	}

		/* (non-Javadoc)
		 * @see model.util.CMEquivalenceClassesBean#addEquivalenceClass(model.EquivalenceClass, int)
		 */
		public void addEquivalenceClass(EquivalenceClass p_equivalenceClass, int p_Position) {
			getEquivalenceClasses();
			if (!this.lnkEquivalenceClasses.contains(p_equivalenceClass))
			{
				if ((this.lnkEquivalenceClasses.size() > p_Position)&& (p_Position>-1))
					this.lnkEquivalenceClasses.add(p_Position,p_equivalenceClass);
				else
					this.lnkEquivalenceClasses.add(p_equivalenceClass);

				this.getHandler().fireModelEventHappen(this, CMField.EQUIVALENCE_CLASSES);
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
				this.getHandler().fireModelEventHappen(this, CMField.ELEMENTS);
			}

		}


		public Origin getTypeofCombinations() {
			if (getLnkCombinations().size()==0)
				return null;
			else
				return getLnkCombinations().get(0).getOriginType();
		}
		public void fireModelEventHappen(CMField field) {
			getHandler().fireModelEventHappen(this, field);

		}

	}

