/*******************************************************************************
Developed by BUSINESS SOFTWARE INNOVATIONS. .
Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
*******************************************************************************/
package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.JOptionPane;

import model.util.CMCombinationsBean;
import model.util.CMDescriptionBean;
import model.util.CMDescriptionEditableBean;
import model.util.CMEffectsBean;
import model.util.CMEquivalenceClassesBean;
import model.util.CMIdBean;
import model.util.CMIdComparator;
import model.util.CMModelEventHandler;
import model.util.CMModelListener;
import model.util.CMModelSource;
import model.util.CMOriginTypeBean;
import model.util.CMRiskLevelBean;
import model.util.CMStateBean;

import org.apache.log4j.Logger;

import bi.controller.CombinationManager;
import bi.view.lang.CMMessages;

public class Combination implements Cloneable, Comparable<Combination>, CMStateBean,CMRiskLevelBean, CMEffectsBean, CMModelSource,CMCombinationsBean,
CMEquivalenceClassesBean,CMDescriptionBean, CMOriginTypeBean, CMDescriptionEditableBean, CMIdBean{

	private transient CMModelEventHandler handler;
    private List<Effect> lnkEffects;
    private List<Combination> lnkCombinations;
    protected int id = -1 ;
    protected String name = "";
    private String description = "";
    private int state = CMStateBean.STATE_POSITIVE;

    private String descriptionEditable = "";
    private List<EquivalenceClass> lnkEquivalenceClasses;
    private Combination lnkCombinationParent = null;
    private Dependency m_Dependency;
    private int m_RiskLevel = 0;
    private String m_OriginType;
	private transient Comparator<Combination> defaultComparator;
    //Obsolete Properties
    @Deprecated
    private transient String dependencyName = "";
    @Deprecated
    private transient Date timestamp = new Date();
    @Deprecated
    private transient List<EquivalenceClass>  lnkMergedEquivalenceClasses;
    @Deprecated
    private transient List<Integer> cantOfSameMergedEquivalenceClasses;


    public Combination() {}
    public int getId(){
            return this.id;
        }

    public void setId(int id){
            this.id = id;
            this.name = generateName(id);
        }

   protected String generateName(int id) {
      StringBuffer idString = new StringBuffer();
      idString.append(id);
      int length = idString.length();
        for(int i = 0; i < BusinessRules.ID_LENGTH-length; i++) {
          idString.insert(0,BusinessRules.ID_FILLER_CHARACTER);
        }
      idString.insert(0,CMMessages.getString("COMBINATION_PREFIX"));
      return  idString.toString();
    }

    public String getName(){ return this.name; }

    public void setName(String name){ this.name = name; }

    public String getDescription(){
    	/**
    	 * @author hmendez
    	 * @date 13122005
    	 * @description used to rebuild the Combinations descriptions
    	 */
       	//get the calculated description according to the model
    	String l_generatedDescription = getGeneratedDescription();
    	//compare with the actual description
    	if (!this.description.equalsIgnoreCase(l_generatedDescription))
    		//if they are different set the description
    		setDescription(l_generatedDescription);
    	/**
    	 * hmendez_end
    	 */
    	return this.description;
    }
    /**
	 * @author hmendez
	 * @date 13122005
	 * @description used to avoid regenerated descriptions(returns the description without checking if is correct)
	 */
    public String getDescriptionWithOutCheck(){
    	return this.description;
    }
   	/**
	 * hmendez_end
	 */
    private String getGeneratedDescription() {

		return CombinationManager.INSTANCE.rebuildCombinationDescription(this);
	}
	public void setDescription(String description){ this.description = description; }

    public String getDescriptionEditable(){ return this.descriptionEditable; }

    public void setDescriptionEditable(String descriptionEditable){ this.descriptionEditable = descriptionEditable; }

    public int getState(){ return this.state; }

    public String getStateName() {
      if( this.state == STATE_POSITIVE) {
        return STATE_POSITIVE_LABEL;
      }
      else if( this.state == STATE_NEGATIVE) {
        return STATE_NEGATIVE_LABEL;
      }
      else if( this.state == STATE_FAULTY) {
        return CMMessages.getString("STATE_FAULTY_LABEL");
      }
      else {
        return CMMessages.getString("STATE_IRRELEVANT_LABEL");
      }
    }

    public void setState(int state){ this.state = state; }

    public List<Effect> getEffects(){
    	if (lnkEffects == null)
    		lnkEffects = new ArrayList<Effect>(0);
    	List<Effect> effects = new ArrayList<Effect>();
    	//check if the effect is on the structure, if not it must be removed
    		effects.addAll(lnkEffects);
    		for (Effect effect : effects)
    			if (!effect.isValid())
    				lnkEffects.remove(effect);
    		effects.clear();
    		effects.addAll(lnkEffects);
    	//check the correct order of the effects
    		Collections.sort(effects);
         return Collections.unmodifiableList(effects);
        }


   

    public int getRiskLevel(Combination p_Left, Combination p_Right) {
      if( p_Left.getRiskLevel() > p_Right.getRiskLevel() ) {
        return p_Left.getRiskLevel();
      }
      else {
        return p_Right.getRiskLevel();
      }
    }

    public int getState(Combination p_Left, Combination p_Right){
      int resultState = CMStateBean.STATE_POSITIVE;
      if( p_Left.getState() == CMStateBean.STATE_IRRELEVANT || p_Right.getState() == CMStateBean.STATE_IRRELEVANT){
        if( !( p_Left.getState() == CMStateBean.STATE_IRRELEVANT && p_Right.getState() == CMStateBean.STATE_IRRELEVANT)){
		     Object[] arguments = {
				 p_Left.getName(),
				 p_Left.getStateName(),
				 p_Right.getName(),
                 p_Right.getStateName(),
                 CMMessages.getString("STATE_IRRELEVANT_LABEL")
			 };
           String result = java.text.MessageFormat.format(
                           "({1}) merged with ({3}) results ({4})", arguments);
          JOptionPane.showMessageDialog(null,result);
        }
        resultState = CMStateBean.STATE_IRRELEVANT;
      }
      else if( p_Left.getState() == CMStateBean.STATE_FAULTY || p_Right.getState() == CMStateBean.STATE_FAULTY){
       if( !( p_Left.getState() == CMStateBean.STATE_FAULTY && p_Right.getState() == CMStateBean.STATE_FAULTY)){
		     Object[] arguments = {
				 p_Left.getName(),
				 p_Left.getStateName(),
				 p_Right.getName(),
                 p_Right.getStateName(),
                 CMMessages.getString("STATE_FAULTY_LABEL")
			 };
           String result = java.text.MessageFormat.format(
                           "({1}) merged with ({3}) results ({4})", arguments);
          JOptionPane.showMessageDialog(null,result);
        }

        resultState = CMStateBean.STATE_FAULTY;
      }
      else if( p_Left.getState() == CMStateBean.STATE_NEGATIVE || p_Right.getState() == CMStateBean.STATE_NEGATIVE){
       if( !( p_Left.getState() == CMStateBean.STATE_NEGATIVE && p_Right.getState() == CMStateBean.STATE_NEGATIVE)){
		     Object[] arguments = {
				 p_Left.getName(),
				 p_Left.getStateName(),
				 p_Right.getName(),
                 p_Right.getStateName(),
                 CMStateBean.STATE_NEGATIVE_LABEL
			 };
           String result = java.text.MessageFormat.format(
                           "({1}) merged with ({3}) results ({4})", arguments);
          JOptionPane.showMessageDialog(null,result);
        }

        resultState = CMStateBean.STATE_NEGATIVE;
      }
      else if( p_Left.getState() == CMStateBean.STATE_POSITIVE || p_Right.getState() == CMStateBean.STATE_POSITIVE){
       if( !( p_Left.getState() == CMStateBean.STATE_POSITIVE && p_Right.getState() == CMStateBean.STATE_POSITIVE)){
		     Object[] arguments = {
				 p_Left.getName(),
				 p_Left.getStateName(),
				 p_Right.getName(),
                 p_Right.getStateName(),
                 CMStateBean.STATE_POSITIVE_LABEL
			 };
           String result = java.text.MessageFormat.format(
                           "({1}) merged with ({3}) results ({4})", arguments);
          JOptionPane.showMessageDialog(null,result);
        }

        resultState = CMStateBean.STATE_POSITIVE;
      }
      return resultState;
    }


  public boolean contains(EquivalenceClass equivalenceClass) {
	         if (equivalenceClass == null) return false;
			 if( this.getEquivalenceClasses().contains(equivalenceClass)) {
				 return true;
			 }
			 else {
         Combination childCombination = null;
         
				 for( Combination combi : getCombinations()) {
					 if (combi != null)
					 if( combi.contains(equivalenceClass) ) {
						 return true;
					 }
				 }
				 return false;
			 }
  }
  public boolean containsInChildrenCombinations(EquivalenceClass equivalenceClass){
		Combination childCombination = null;
         int numOfChildrenCombinations = getCombinations().size();
				 for( int i = 0; i < numOfChildrenCombinations; i++) {
					 childCombination = getCombinations().get(i);
					 if( childCombination.contains(equivalenceClass) ) {
						 return true;
					 }
				 }
				 return false;
  }


	public List<EquivalenceClass> getEquivalenceClassesRecursiv() {
          List<EquivalenceClass>  allEquivalenceClasses = new ArrayList<EquivalenceClass>();
          allEquivalenceClasses.addAll(getEquivalenceClasses());
          allEquivalenceClasses.addAll(getMergedEquivalenceClasses());
          return allEquivalenceClasses;
  }


    public Combination getLnkCombinationParent(){
            return this.lnkCombinationParent;
        }

    public void setLnkCombinationParent(Combination lnkCombinationParent){
            this.lnkCombinationParent = lnkCombinationParent;
        }

    public List<EquivalenceClass> getMergedEquivalenceClasses(){
    	List<EquivalenceClass> mergedEquivalenceClasses = new ArrayList<EquivalenceClass>();
    	for (Combination combi : getCombinationsRecursiv())
    		for (EquivalenceClass equivalenceClass : combi.getEquivalenceClasses())
    				if (!mergedEquivalenceClasses.contains(equivalenceClass))
    					mergedEquivalenceClasses.add(equivalenceClass);
            return mergedEquivalenceClasses;
        }

    public boolean isParentOf(Combination childCombination) {
      if( this == childCombination.getLnkCombinationParent()) {
      	return true;
      }
      Combination nextParent = childCombination.getLnkCombinationParent();
      while( nextParent != null) {
        nextParent = nextParent.getLnkCombinationParent();
        if( this == nextParent) {
          return true;
        }
      }
      return false;
    }

    public Dependency getDependency(){
            return this.m_Dependency;
        }

    public void setDependency(Dependency m_Dependency){
            this.m_Dependency = m_Dependency;
        }

    public int getRiskLevel() {
      return this.m_RiskLevel;
    }

    public void setRiskLevel(int p_RiskLevel) {
      this.m_RiskLevel = p_RiskLevel;
    }

    public Origin getOriginType(){
            if( this.m_OriginType == null) {
              this.m_OriginType = Origin.MANUAL.toString();
            }

            return Origin.getByName(m_OriginType);
        }

    public void setOriginType(Origin m_OriginType){
            this.m_OriginType = m_OriginType.toString();
            this.getHandler().fireModelEventHappen(this, CMField.ORIGIN_TYPE);
        }


	/**
	 *  Check if the combination (or any of its children) has any assigned equivalence classes
	 * @return
	 */
	public boolean isEmpty() {
		for (Iterator i = getCombinations().iterator();i.hasNext();)
		{
			if (!((Combination)i.next()).isEmpty())
				return false;
		}
		return this.getEquivalenceClasses().size()==0;
	}
	/**
	 * Get the List of elements from the equivalence classes and child combinations
	 * @return
	 */
	public Vector<Element> getRelatedElements() {
		Vector<Element> elements = new Vector<Element>();
		for (EquivalenceClass  equivalenceClass : this.getEquivalenceClasses())
			if (!elements.contains(equivalenceClass.getLnkElement()) &&  equivalenceClass.getLnkElement()!=null)
				elements.add(equivalenceClass.getLnkElement());

		//child combinations
		for (Iterator<Combination> i = this.getCombinations().iterator();i.hasNext();)
		{
			//recursive call
			List<Element> childCombElements = i.next().getRelatedElements();
			for (Iterator<Element> j = childCombElements.iterator();j.hasNext();)
			{
				Element childCombElement =  j.next();
				if (!elements.contains(childCombElement) && childCombElement !=null)
					elements.add(childCombElement);
			}
		}
		Collections.sort(elements);
		return elements;
	}
	/* (non-Javadoc)
	 * @see model.CMStateModel#setState(model.State)
	 */
	public void setState(State p_state) {
		state = p_state.intValue();

	}
	/* (non-Javadoc)
	 * @see model.util.CMEffectsBean#addEffect(model.Effect)
	 */
	public void addEffect(Effect p_effect) {
		this.getEffects();
		if ((!this.lnkEffects.contains(p_effect))&&p_effect.isValid()){
			this.lnkEffects.add(p_effect);
//			svonborries_05052008
			//check if the effect is assigned to the merged combination and add it
			if(lnkCombinations != null && lnkCombinations.size()>0){
				for(Combination comb: lnkCombinations){
					if(!comb.getEffects().contains(p_effect)){
						comb.addEffect(p_effect);
						comb.getHandler().fireModelEventHappen(comb, CMField.EFFECTS);
					}
				}
			}
			this.getHandler().fireModelEventHappen(this,CMField.EFFECTS);
		}else
			Logger.getLogger(this.getClass()).error("Effect not added");


	}
	/* (non-Javadoc)
	 * @see model.util.CMEffectsBean#removeEffect(model.Effect)
	 */
	public void removeEffect(Effect p_effect) {
		this.getEffects();
		if (this.lnkEffects.contains(p_effect))
		{
			this.lnkEffects.remove(p_effect);
//			svonborries_05052008
			//check if the effect is assigned to the merged combination and delete it
			if(lnkCombinations != null && lnkCombinations.size()>0){
				for(Combination comb: lnkCombinations){
					if(comb.getEffects().contains(p_effect)){
						comb.removeEffect(p_effect);
						comb.getHandler().fireModelEventHappen(comb, CMField.EFFECTS);
					}
				}
			}
			this.getHandler().fireModelEventHappen(this,CMField.EFFECTS);
		}

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
		if (this.handler==null)
			this.handler = new CMModelEventHandler();
		return this.handler;
	}
	/* (non-Javadoc)
	 * @see model.util.CMEffectsBean#addEffect(model.Effect, int)
	 */
	public void addEffect(Effect p_effect, int p_index) {
		this.getEffects();
		if ((!lnkEffects.contains(p_effect))&&p_effect.isValid()){
		this.lnkEffects.add(p_index,p_effect);
//		svonborries_05052008
		//check if the effect is assigned to the merged combination and add it
		if(lnkCombinations != null && lnkCombinations.size()>0){
			for(Combination comb: lnkCombinations){
				if(!comb.getEffects().contains(p_effect)){
					comb.addEffect(p_effect);
					comb.getHandler().fireModelEventHappen(comb, CMField.EFFECTS);
				}
			}
		}
		this.getHandler().fireModelEventHappen(this,CMField.EFFECTS);
		}else
			Logger.getLogger(this.getClass()).error("Effect not added ");
	}
	/* (non-Javadoc)
	 * @see model.util.CMCombinationsBean#addCombination(model.Combination)
	 */
	public void addCombination(Combination p_Combination) {
		getCombinations();
		if (!this.lnkCombinations.contains(p_Combination) && p_Combination!=this)
		{
			this.lnkCombinations.add(p_Combination);
			p_Combination.setLnkCombinationParent(this);
			this.getHandler().fireModelEventHappen(this, CMField.COMBINATIONS);
		}
	}
	/* (non-Javadoc)
	 * @see model.util.CMCombinationsBean#removeCombination(model.Combination)
	 */
	public void removeCombination(Combination p_Combination) {
		getCombinations();
		if (this.lnkCombinations.contains(p_Combination))
		{
			this.lnkCombinations.remove(p_Combination);

			this.getHandler().fireModelEventHappen(this, CMField.COMBINATIONS);
		}
		p_Combination.setLnkCombinationParent(null);

	}
	/* (non-Javadoc)
	 * @see model.util.CMCombinationsBean#getCombinations()
	 */
	public List<Combination> getCombinations() {
		ArrayList<Combination> combinations = new ArrayList<Combination>();
		if (lnkCombinations == null)
			lnkCombinations = new ArrayList<Combination>();
		//check for possible errors
		if (lnkCombinations.contains(this))
			lnkCombinations.remove(this);
		if (lnkCombinations.contains(lnkCombinationParent))
			lnkCombinations.remove(lnkCombinationParent);
		combinations.addAll(lnkCombinations);
  		return combinations;
	}
	/* (non-Javadoc)
	 * @see model.util.CMEquivalenceClassesBean#getEquivalenceClasses()
	 */
	public List<EquivalenceClass> getEquivalenceClasses() {
		if (this.lnkEquivalenceClasses == null)
			lnkEquivalenceClasses = new Vector<EquivalenceClass>();
  		return lnkEquivalenceClasses;
	}
	/* (non-Javadoc)
	 * @see model.util.CMEquivalenceClassesBean#addEquivalenceClass(model.EquivalenceClass)
	 */
	public void addEquivalenceClass(EquivalenceClass p_equivalenceClass) {
		getEquivalenceClasses();
		if (!this.lnkEquivalenceClasses.contains(p_equivalenceClass)){
			this.lnkEquivalenceClasses.add(p_equivalenceClass);
			Collections.sort(lnkEquivalenceClasses);
			this.getHandler().fireModelEventHappen(this,CMField.EQUIVALENCE_CLASSES);
		}

	}
	/* (non-Javadoc)
	 * @see model.util.CMEquivalenceClassesBean#removeEquivalenceClass(model.EquivalenceClass)
	 */
	public void removeEquivalenceClass(EquivalenceClass p_equivalenceClass) {
		getEquivalenceClasses();
		if (this.lnkEquivalenceClasses.contains(p_equivalenceClass)){
			this.lnkEquivalenceClasses.remove(p_equivalenceClass);
			this.getHandler().fireModelEventHappen(this,CMField.EQUIVALENCE_CLASSES);
		}


	}
	
	/* (non-Javadoc)
	 * @see model.util.CMEquivalenceClassesBean#addEquivalenceClass(model.EquivalenceClass, int)
	 */
	public void addEquivalenceClass(EquivalenceClass p_equivalenceClass, int p_Position) {
		getCombinations();
		if (!this.lnkEquivalenceClasses.contains(p_equivalenceClass))
		{
			if ((this.lnkEquivalenceClasses.size() > p_Position)&& (p_Position>-1))
				this.lnkEquivalenceClasses.add(p_Position,p_equivalenceClass);
			else
				this.lnkEquivalenceClasses.add(p_equivalenceClass);

			this.getHandler().fireModelEventHappen(this, CMField.EQUIVALENCE_CLASSES);
		}
	}


	public List<CMRiskLevelBean> getChildRiskLevels(){
		List<CMRiskLevelBean> listRL = new ArrayList<CMRiskLevelBean>();
		if (getDependency()!=null)
			if (getDependency().getLnkStructure()!=null)
				for (TestCase testCase: getDependency().getLnkStructure().getTestCases())
					if (testCase.getCombinations().contains(this))
						listRL.add(testCase);
		return listRL;
 	}
	public List<CMRiskLevelBean> getParentRiskLevels() {
		List<CMRiskLevelBean> listRL = new ArrayList<CMRiskLevelBean>();
		listRL.addAll(getEffects());
		listRL.addAll(getEquivalenceClasses());
		return listRL;
	}
	public List<Combination> getCombinationsRecursiv() {
		List<Combination> listCombinations = new ArrayList<Combination>();
		for (Combination combination : getCombinations()){
			listCombinations.add(combination);
			if (combination.getCombinations().size()>0)
				listCombinations.addAll(combination.getCombinationsRecursiv());
		}
		return listCombinations;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		if (getDependency()!=null)
			return getDependency().getName()+"."+getName();
		else
			return getName();
	}
	public List<? extends CMStateBean> getChildStates() {
		List<CMStateBean> listST = new ArrayList<CMStateBean>();
		if (getDependency()!=null)
			if (getDependency().getLnkStructure()!=null)
				for (TestCase testCase: getDependency().getLnkStructure().getTestCases())
					if (testCase.getCombinations().contains(this))
						listST.add(testCase);
		return listST;
	}
	public List<? extends CMStateBean> getParentStates() {
		List<CMStateBean> listST = new ArrayList<CMStateBean>();
		listST.addAll(getEffects());
		listST.addAll(getEquivalenceClasses());
		return listST;
	}
	/**
	 * get the parent depedency if the combination is merged searches in the parent combinations
	 * @return
	 */
	public Dependency getDependencyRoot() {
		if (getDependency()!=null)
			return getDependency();
		if (getLnkCombinationParent()!=null)
			return getLnkCombinationParent().getDependencyRoot();
		return null;
	}
	public void fireModelEventHappen(CMField field) {
		getHandler().fireModelEventHappen(this, field);

	}

	public Comparator<Combination> getDefaultComparator() {
		if (this.defaultComparator==null)
			//by default compare by id
			this.defaultComparator= new CMIdComparator();
		return this.defaultComparator;
	}

	public int compareTo(Combination o) {
		return getDefaultComparator().compare(this,o);
	}
	public boolean isUsed() {
		if (this.getDependencyRoot()==null || this.getDependencyRoot().getLnkStructure()==null)
				return false;
		for (TestCase testCase : getDependencyRoot().getLnkStructure().getTestCases())
			if (testCase.getCombinations().contains(this))
				return true;
		return false;
	}

}


