/*******************************************************************************
Developed by BUSINESS SOFTWARE INNOVATIONS.
Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
*******************************************************************************/
package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import model.util.CMCloneable;
import model.util.CMDescriptionBean;
import model.util.CMEquivalenceClassesBean;
import model.util.CMIdBean;
import model.util.CMIdComparator;
import model.util.CMModelEventHandler;
import model.util.CMModelListener;
import model.util.CMModelSource;
import model.util.CMNameBean;
import model.util.CMUserOrderBean;

import org.apache.log4j.Logger;

import bi.controller.ToolVendorManager;

/**
 * @persistent
 */
public class Element  implements Cloneable, CMDescriptionBean,CMNameBean, Comparable,CMIdBean, CMEquivalenceClassesBean, CMModelSource, CMUserOrderBean, CMCloneable {



    /**
     *@link aggregationByValue
     *@associates <{EquivalenceClass}>
     * @clientCardinality 1
     * @supplierCardinality 1..*
     * @directed
     */
    private Vector<EquivalenceClass> lnkEquivalenceClasses = new Vector<EquivalenceClass>(0);
    private int id = -1;
    private String name = "";

    private int stateOT = BusinessRules.STATEOT_DNET;

    private String ObjectTypesValue = "";



    private String description = "";
    private Date timestamp = new Date();
    private int userOrder =-1;

    private String label = "";
    private Structure lnkStructure = null;
	private transient Comparator defaultComparator;
	private transient CMModelEventHandler handler;

    /**
     * @deprecated
     */
    private transient Dependency lnkrevDependency;
   public Element() {
     this.timestamp = new Date();

   }
    public int getId(){
    	if (this.getLnkStructure()!=null)
    		return this.getLnkStructure().getLnkElements().indexOf(this);
    	else
    		return id;
        }

    public void setId(int id){
    	 this.id = id;
        }

    public String getName(){ return name; }

    public void setName(String name){ this.name = name; }



    private String getTechnology(){

    		if (this.getLnkStructure() != null)
    			return this.getLnkStructure().getTestObject().getToolVendorTechnology();
    		else
    			return "";

    }
    private String getToolVendor(){
    	if (this.getLnkStructure() != null){

    		return this.getLnkStructure().getTestObject().getToolVendor();

    	}
    	else
    	return "";
    	}

    public int getGUIObject(){ return stateOT; }

    public void setGUIObject(int stateOT){
    	this.stateOT = stateOT;
    }

    public String getGUIObjectName() {
    	Technology tech = ToolVendorManager.INSTANCE.findTechnologyByName(this.getToolVendor(),this.getTechnology());

    	if (tech!=null){
    		try{
    		    tech.getM_ObjectTypesValue().get(this.getGUIObject());
    		} catch (ArrayIndexOutOfBoundsException e) {
    			Logger.getLogger(this.getClass()).error("No Object Type for "+this.getGUIObject()+" Setting default value");
    			this.setGUIObject(0);
			}

    		this.ObjectTypesValue =  tech.getM_ObjectTypesValue().get(this.getGUIObject()).toString();

    	}

    	return ObjectTypesValue;
    	}


    public String getDescription(){ return description; }

    public void setDescription(String description){ this.description = description; }

    public Date getTimestamp(){ return timestamp; }

    public void setTimestamp(Date timestamp){ this.timestamp = timestamp; }


    public String getLabel(){ return label; }

    public void setLabel(String label){ this.label = label; }

    public Element makeClone() {
      Element newElement = null;
      try {
       newElement = new Element();
       newElement.setDescription(this.getDescription());
       newElement.setGUIObject(this.getGUIObject());
       newElement.setName(this.getName());
       newElement.setUserOrder(this.getUserOrder());
       newElement.setTimestamp((Date)this.timestamp.clone());
       for (EquivalenceClass equivalence : getEquivalenceClasses())
    	   newElement.addEquivalenceClass(equivalence.makeClone());

      } catch(Exception e) {
        e.printStackTrace();
      }
      return newElement;
    }


	public Structure getLnkStructure() {
		return lnkStructure;
	}
	public void setLnkStructure(Structure lnkStructure) {
		this.lnkStructure = lnkStructure;
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
	/* (non-Javadoc)
	 * @see model.util.CMEquivalenceClassesBean#getEquivalenceClasses()
	 */
	public List<EquivalenceClass> getEquivalenceClasses() {
		if (lnkEquivalenceClasses == null)
			lnkEquivalenceClasses = new Vector<EquivalenceClass>();
		ArrayList<EquivalenceClass> list = new ArrayList<EquivalenceClass>();
		list.addAll(lnkEquivalenceClasses);
		//by default the collection is sorted
		Collections.sort(list);
		return Collections.unmodifiableList(list);
	}
	/* (non-Javadoc)
	 * @see model.util.CMEquivalenceClassesBean#addEquivalenceClass(model.EquivalenceClass)
	 */
	public void addEquivalenceClass(EquivalenceClass p_equivalenceClass) {
		getEquivalenceClasses();
		if (!lnkEquivalenceClasses.contains(p_equivalenceClass))
		{
		     lnkEquivalenceClasses.add(p_equivalenceClass);
		     p_equivalenceClass.setLnkElement(this);
		     Collections.sort(lnkEquivalenceClasses);
		     getHandler().fireModelEventHappen(this,CMField.EQUIVALENCE_CLASSES);
		}
	}
	/* (non-Javadoc)
	 * @see model.util.CMEquivalenceClassesBean#removeEquivalenceClass(model.EquivalenceClass)
	 */
	public void removeEquivalenceClass(EquivalenceClass p_equivalenceClass) {
		getEquivalenceClasses();
		if (lnkEquivalenceClasses.contains(p_equivalenceClass))
		{
		     lnkEquivalenceClasses.remove(p_equivalenceClass);
		     p_equivalenceClass.setLnkElement(null);
		     getHandler().fireModelEventHappen(this,CMField.EQUIVALENCE_CLASSES);
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
	private CMModelEventHandler getHandler() {
		if (this.handler == null)
			this.handler = new CMModelEventHandler();
		return this.handler;
	}
	/**
	 * @param p_state_positive
	 * @return all Equivalence classes with certain state
	 */
	public List<EquivalenceClass> getEquivalenceClassesbyState(int p_state) {
		ArrayList<EquivalenceClass> list = new ArrayList<EquivalenceClass>();
		for (EquivalenceClass eq : this.getEquivalenceClasses())
			if (eq.getState()==p_state)
				list.add(eq);
		return list;
	}
	public  int getUserOrder() {
		//if the order is -1 it means it was not initialized
		if (this.userOrder == -1)
		{
			if (getLnkStructure()!=null)
			{
				this.userOrder = this.getLnkStructure().getElements().indexOf(this)+1;
			}
		}
		return this.userOrder;
	}

	public void setUserOrder(int p_userOrder) {
		if (p_userOrder != this.userOrder)
		{
			this.userOrder = p_userOrder;
			getHandler().fireModelEventHappen(this,CMField.USER_ORDER);
		}
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.getName();
	}

	public void fireModelEventHappen(CMField field) {
		getHandler().fireModelEventHappen(this, field);

	}
	public EquivalenceClass getEquivalenceClassbyId(int id2) {
		for (EquivalenceClass equivalenceClass : getEquivalenceClasses())
			if (equivalenceClass.getId() == id2)
				return equivalenceClass;
		return null;
	}
}
