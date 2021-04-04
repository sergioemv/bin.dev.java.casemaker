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
import model.util.CMEffectsBean;
import model.util.CMIdBean;
import model.util.CMIdComparator;
import model.util.CMModelEventHandler;
import model.util.CMModelListener;
import model.util.CMModelSource;
import model.util.CMRiskLevelBean;
import model.util.CMStateBean;
import model.util.CMValueBean;

import org.apache.log4j.Logger;

import bi.controller.StructureManager;
import bi.view.lang.CMMessages;
public class EquivalenceClass implements Cloneable,CMStateBean,CMRiskLevelBean,CMIdBean,CMEffectsBean, Comparable, CMValueBean, CMDescriptionBean,CMModelSource, CMCloneable {
    private transient CMModelEventHandler handler;
	private int id = -1;
    private String name = "";
    private String description = "";
    private int state = CMStateBean.STATE_POSITIVE;
    private Date timestamp = new Date();

    /**
     * @link aggregation
     * @clientCardinality 1
     * @supplierCardinality 0..*
     * @associates <{Effect}>
     * @directed
     */
    private Vector<Effect> lnkEffects = new Vector<Effect>(0);

    /**
     * @clientCardinality 1
     * @supplierCardinality 1
     * @directed
     * @label belongs to
     */
    private Element lnkElement = null;
    private String value = "";
    private int m_RiskLevel;
    private transient Comparator defaultComparator;
    public EquivalenceClass() {
      this.timestamp = new Date();
      this.lnkEffects = new Vector<Effect>(0);
    }
    public int getId(){
            return id;
        }

    public void setId(int id){
            this.id = id;
            this.name = generateName(id);
        }

    public String getName(){ return name; }

    public void setName(String name){ this.name = name; }

    public String getDescription(){ return description; }

    public void setDescription(String description){ this.description = description; }

    public int getState(){ return state; }

    public String getStateName() {
        return Arrays.asList(State.values()).get(state).toString();

    }

    public void setState(int state){
    	if (state!=this.state) {
    	this.state = state;
    	getHandler().fireModelEventHappen(this, CMField.STATE);
    	}
    }

    public Date getTimestamp(){ return timestamp; }

    public void setTimestamp(Date timestamp){ this.timestamp = timestamp; }

    public List<Effect> getEffects(){
    	if (lnkEffects == null)
    		lnkEffects = new Vector<Effect>(0);
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

    public void setLnkEffects(Vector<Effect> lnkEffects){
            this.lnkEffects = lnkEffects;
        }
    private String generateName(int id) {
      StringBuffer idString = new StringBuffer();
      idString.append(id);
      int length = idString.length();
        for(int i = 0; i < BusinessRules.ID_LENGTH-length; i++) {
          idString.insert(0,BusinessRules.ID_FILLER_CHARACTER);
        }
      idString.insert(0,CMMessages.getString("EQUIVALENCECLASS_PREFIX"));
      return  idString.toString();
    }


    public Element getLnkElement(){
            return lnkElement;
        }

    public void setLnkElement(Element lnkElement){
            this.lnkElement = lnkElement;
        }

    public String getValue(){ return value; }

    public void setValue(String value){ this.value = value; }

    public EquivalenceClass makeClone() {
      EquivalenceClass clonedEC = null;
      try {
       clonedEC = (EquivalenceClass)this.clone();
       clonedEC.setTimestamp((Date)this.timestamp.clone());

       clonedEC.setLnkEffects(null);
       if (clonedEC.getLnkElement().getLnkStructure() == StructureManager.getSelectedStructure())
    	   for (Effect effect : getEffects())
    		   if (!clonedEC.getEffects().contains(effect))
    			   clonedEC.addEffect(effect);
       else
    	   		clonedEC.setLnkElement(null);
      } catch(CloneNotSupportedException e) {
        e.printStackTrace();
      }
      return clonedEC;
    }

    public int getRiskLevel() {
      return m_RiskLevel;
    }

    public void setRiskLevel(int p_RiskLevel) {
      m_RiskLevel = p_RiskLevel;
    }

    public String toString(){
    	return lnkElement.getName()+"."+name;
    }


public int compareTo(Object p_o) {
	if (p_o instanceof EquivalenceClass)
	{
		EquivalenceClass eq = (EquivalenceClass) p_o;
		if (this.getLnkElement().compareTo(eq.getLnkElement())!=0)
			return this.getLnkElement().compareTo(eq.getLnkElement());
		else
			return getDefaultComparator().compare(this,eq);
	}
	return 0;
}
/**
 * @return Returns the defaultComparator.
 */
public Comparator getDefaultComparator() {
	if (this.defaultComparator==null)
		//by default compare by id
		this.defaultComparator= new CMIdComparator();
	return this.defaultComparator;
}
/**
*@autor smoreno
 * @param p_stringValue
 */
public void setState(State p_state) {
	this.setState(p_state.intValue());

}
/* (non-Javadoc)
 * @see model.util.CMEffectsBean#addEffect(model.Effect)
 */
public void addEffect(Effect p_effect) {
	this.getEffects();
	if (p_effect.isValid()){
		this.lnkEffects.add(p_effect);
		this.getHandler().fireModelEventHappen(this,CMField.EFFECTS);
	}
	else
		Logger.getLogger(this.getClass()).error("Trying to add an invalid effect!");

}
/* (non-Javadoc)
 * @see model.util.CMEffectsBean#removeEffect(model.Effect)
 */
public void removeEffect(Effect p_effect) {
	this.getEffects();
	this.lnkEffects.remove(p_effect);
	this.getHandler().fireModelEventHappen(this,CMField.EFFECTS);

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
	this.getHandler().addModelListener(p_listener);

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
 * @see model.util.CMEffectsBean#addEffect(model.Effect, int)
 */
public void addEffect(Effect p_effect, int p_index) {
	this.getEffects();
	this.lnkEffects.add(p_index,p_effect);
	this.getHandler().fireModelEventHappen(this,CMField.EFFECTS);

}


public List<? extends CMRiskLevelBean> getChildRiskLevels() {
	List<CMRiskLevelBean> listRL = new ArrayList<CMRiskLevelBean>();
	if (getLnkElement() !=null)
		if (getLnkElement().getLnkStructure()!=null){
//			 all combinations that contains this effect
			for (Dependency dependency: getLnkElement().getLnkStructure().getDependencies())
				for (Combination combination : dependency.getCombinations()){
					 if (combination.getEquivalenceClasses().contains(this))
						 listRL.add(combination);
					 for (Combination childCombination : combination.getCombinationsRecursiv())
						 if (childCombination.getEquivalenceClasses().contains(this))
							 listRL.add(childCombination);
					 }
			for (TestCase testCase : getLnkElement().getLnkStructure().getTestCases()){
				if (testCase.getEquivalenceClasses().contains(this))
					listRL.add(testCase);
			}
		}
	return listRL;
}
public List<? extends CMRiskLevelBean> getParentRiskLevels() {

	return this.getEffects();
}
public List<? extends CMStateBean> getChildStates() {
	List<CMStateBean> listST = new ArrayList<CMStateBean>();
	if (getLnkElement() !=null)
		if (getLnkElement().getLnkStructure()!=null){
//			 all combinations that contains this effect
			for (Dependency dependency: getLnkElement().getLnkStructure().getDependencies())
				for (Combination combination : dependency.getCombinations()){
					 if (combination.getEquivalenceClasses().contains(this))
						 listST.add(combination);
					 for (Combination childCombination : combination.getCombinationsRecursiv())
						 if (childCombination.getEquivalenceClasses().contains(this))
							 listST.add(childCombination);
					 }
			for (TestCase testCase : getLnkElement().getLnkStructure().getTestCases()){
				if (testCase.getEquivalenceClasses().contains(this))
					listST.add(testCase);
			}
		}
	return listST;
}
public List<? extends CMStateBean> getParentStates() {

	return this.getEffects();
}
public void fireModelEventHappen(CMField field) {
	getHandler().fireModelEventHappen(this, field);

}
}
