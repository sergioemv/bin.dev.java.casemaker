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
import model.util.CMDescriptionBean;
import model.util.CMDescriptionEditableBean;
import model.util.CMElementWorkFlowComparator;
import model.util.CMEquivalenceClassesBean;
import model.util.CMErrorsBean;
import model.util.CMIdBean;
import model.util.CMIdComparator;
import model.util.CMModelEventHandler;
import model.util.CMModelListener;
import model.util.CMModelSource;
import model.util.CMOriginTypeBean;
import model.util.CMRiskLevelBean;
import model.util.CMStateBean;
import model.util.CMUserOrderBean;
import bi.controller.StructureManager;
import bi.controller.TestCaseManager;
import bi.view.lang.CMMessages;
public class TestCase implements Cloneable,CMRiskLevelBean, CMCombinationsBean, CMModelSource, CMDescriptionBean, CMOriginTypeBean,
CMEquivalenceClassesBean,CMIdBean,CMDependencyBean, Comparable, CMStateBean, CMDescriptionEditableBean, CMErrorsBean {

    /**
     *@link aggregation
     *@associates <{EquivalenceClass}>
     * @clientCardinality 1
     * @supplierCardinality 0..*
     * @directed
     */
    private Vector<EquivalenceClass> lnkEquivalenceClasses = new Vector<EquivalenceClass>(0);

    /**
     *@link aggregation
     *@associates <{Combination}>
     * @clientCardinality 1
     * @supplierCardinality 0..*
     * @directed
     */
    private Vector<Combination> lnkCombinations = new Vector<Combination>(0);
    private int id = -1;
    private String name = "";
   //my add
    private int stateOT = BusinessRules.STATEOT_DNET;

    private String description = "";
    private int state = CMStateBean.STATE_POSITIVE;
    private Date timestamp = new Date();
    private TestCaseGroup m_TestCaseGroup;
    private String m_OriginType;
    private String descriptionEditable = "";

    /**
     * @associates <{Dependency}>
     */
    private Vector<Dependency> m_Dependencies = new Vector<Dependency>(0);
    private int m_RiskLevel = 0;

    /**
     * @directed
     * @clientCardinality 1
     */
    private Vector<CMError> m_CMErrors ;

    private Structure structure;
    private transient CMModelEventHandler handler;

	private transient Comparator defaultComparator;

    public TestCase() {
     this.timestamp = new Date();
	 m_CMErrors = new Vector(0);
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
      idString.insert(0,CMMessages.getString("TESTCASE_PREFIX"));
      return  idString.toString();
    }

    public String getName(){ return name; }

    public void setName(String name){ this.name = name; }

    /**
	 * @author hmendez
	 * @date 13122005
	 * @description used to rebuild the Test Cases descriptions(returns the description without checking if is correct
	 */
    public String getDescriptionWithOutCheck(){ 
    	return description;
    }
   	/**
	 * hmendez_end
	 */


    public String getDescription(){
    	/**
    	 * @author hmendez
    	 * @date 13122005
    	 * @description used to rebuild the Test Cases descriptions
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
    	return description;
    }

    private String getGeneratedDescription() {
    	List<Element> elements  = (List<Element>)getRelatedElements();
    	Structure structure = getStructure();
		if (getStructure().getTestObject().getOrigin()==null)
			Collections.sort(elements,CMUserOrderBean.COMPARATOR);
		else
			Collections.sort(elements,new CMElementWorkFlowComparator(this));
        StringBuffer sBuffer = new StringBuffer();
        List combinationsAlreadyFound = new ArrayList<Combination>();

        int l_addedToTheDescription = 0;
        Vector l_equivalenceClasses = new Vector();
        String l_String = "";
        for(Element element : elements) {
        	if (element.getEquivalenceClasses().size()==0) continue;
        	if (element.getLnkStructure()==null) continue;
            l_equivalenceClasses.clear();
            for (EquivalenceClass equivalenceClass : element.getEquivalenceClasses()){
                if (getEquivalenceClasses().contains(equivalenceClass)) {
                	l_equivalenceClasses.addElement(equivalenceClass);
                }
                else if (existsCombination(equivalenceClass)) {
                        List combinations = getCombinations(equivalenceClass);
                        l_String = createCombinationsString(combinations, combinationsAlreadyFound);
                        if (l_addedToTheDescription>0 && l_String.length()>0)
                        {
                          sBuffer.append(BusinessRules.SPACE);
                          sBuffer.append(BusinessRules.AND);
                          sBuffer.append(BusinessRules.SPACE);
                        }
                        if (combinations.size()==1 && l_String.length()>0)
                        {
                    		l_String = BusinessRules.OPEN_SQUARE_BRACKET+l_String+BusinessRules.CLOSED_SQUARE_BRACKET;
                        }
                         sBuffer.append(l_String);
                        l_addedToTheDescription++;
                }
                else if (getStdCombination(equivalenceClass) != null) {
                        StdCombination stdCombination = getStdCombination(equivalenceClass);
                        l_String = createStdCombinationString(stdCombination, combinationsAlreadyFound);
                        //sBuffer.append(getTestCaseReportBuilder().createStdCombinationString(stdCombination, combinationsAlreadyFound));
                        if (l_addedToTheDescription>0 && l_String.length()>0)
                        {
                          sBuffer.append(BusinessRules.SPACE);
                          sBuffer.append(BusinessRules.AND);
                          sBuffer.append(BusinessRules.SPACE);
                        }
                        sBuffer.append(l_String);
                        l_addedToTheDescription++;
                }
            }//hmendez_13122005_begin
            for (int j=0;j<l_equivalenceClasses.size();j++)
            {
            	if (j==0 && l_addedToTheDescription>0)
            	{
              	  sBuffer.append(BusinessRules.SPACE);
            	  sBuffer.append(BusinessRules.AND);
            	  sBuffer.append(BusinessRules.SPACE);
            	}
            	sBuffer.append(BusinessRules.OPEN_SQUARE_BRACKET);
            	sBuffer.append(createEquivalenceClassString((EquivalenceClass)l_equivalenceClasses.get(j)));
            	sBuffer.append(BusinessRules.CLOSED_SQUARE_BRACKET);
            	if (j+1<l_equivalenceClasses.size()){
            	  sBuffer.append(BusinessRules.SPACE);
            	  sBuffer.append(BusinessRules.AND);
            	  sBuffer.append(BusinessRules.SPACE);
            	}
                l_addedToTheDescription++;
            }//hmendez_13122005_end
        }
        return sBuffer.toString();
    }//hmendez_08122005_end

	public void setDescription(String description){ this.description = description; }

    public int getState(){ return state; }

    public String getStateName() {
      if( state == STATE_POSITIVE) {
        return STATE_POSITIVE_LABEL;
      }
      else if( state == STATE_NEGATIVE) {
        return STATE_NEGATIVE_LABEL;
      }
      else if( state == STATE_FAULTY) {
        return CMMessages.getString("STATE_FAULTY_LABEL");
      }
      else {
        return CMMessages.getString("STATE_IRRELEVANT_LABEL");
      }
    }

    public void setState(int state){ this.state = state; }

    public Date getTimestamp(){ return timestamp; }

    public void setTimestamp(Date timestamp){ this.timestamp = timestamp; }

    /**
     * @return
     * @deprecated
     * @since 3.6
     */
    public Vector<EquivalenceClass> getLnkEquivalenceClasses(){
    	if (lnkEquivalenceClasses == null)
    		lnkEquivalenceClasses = new Vector();
            return lnkEquivalenceClasses;
        }

    public void setLnkEquivalenceClasses(Vector lnkEquivalenceClasses){
            this.lnkEquivalenceClasses = lnkEquivalenceClasses;
        }

    /**
     * @deprecated use getCombinations instead
     * @return
     */
    public Vector<Combination> getLnkCombinations(){
    	if (lnkCombinations == null)
    		lnkCombinations = new Vector();
            return lnkCombinations;
        }

    public void setLnkCombinations(Vector lnkCombinations){
            this.lnkCombinations = lnkCombinations;
        }

    public TestCaseGroup getTestCaseGroup(){
    	if (this.m_TestCaseGroup == null)
    		this.m_TestCaseGroup = new TestCaseGroup();
            return m_TestCaseGroup;
        }

    public void setTestCaseGroup(TestCaseGroup m_TestCaseGroup){
            this.m_TestCaseGroup = m_TestCaseGroup;
            this.getHandler().fireModelEventHappen(this,CMField.TESTCASEGROUP);
        }

    public Origin getOriginType(){
    	if (this.m_OriginType == null)
    		this.m_OriginType = "";
    	return Origin.getByName(m_OriginType);
    	}

    public void setOriginType(Origin m_OriginType){
    	if (m_OriginType == null)//ccastedo adds this condition 07.02.07
    		this.m_OriginType = "";
    	else
    		this.m_OriginType = m_OriginType.toString();
    	this.getHandler().fireModelEventHappen(this,CMField.ORIGIN_TYPE);
    }

    public Vector getDependencies(){
    	if (this.m_Dependencies == null)
    		this.m_Dependencies = new Vector();
            return m_Dependencies;
        }

    public void setDependencies(Vector m_Dependencies){
            this.m_Dependencies = m_Dependencies;
            this.getHandler().fireModelEventHappen(this,CMField.DEPENDENCIES);
        }

    public int getRiskLevel() {
      return m_RiskLevel;
    }

    public void setRiskLevel(int p_RiskLevel) {
      m_RiskLevel = p_RiskLevel;
    }
    
    public List<CMError> getErrors(){
    	if (m_CMErrors == null)
    		m_CMErrors = new Vector<CMError>();
    	List<CMError> errors = new ArrayList<CMError>();
    	errors.addAll(m_CMErrors);
    	return errors;
    }

    public Vector getM_CMErrors(){
            return m_CMErrors;
        }

    public void setM_CMErrors(Vector m_CMErrors){
            this.m_CMErrors = m_CMErrors;
        }

    public String getDescriptionEditable(){
    	//lazy initialization
    	if (descriptionEditable == null)
    		descriptionEditable = new String();
    	return descriptionEditable; }

    public void setDescriptionEditable(String descriptionEditable){ this.descriptionEditable = descriptionEditable; }

    public TestCase makeClone() {
        TestCase b = null;
        try {
         b = (TestCase)this.clone();
         b.setTimestamp((Date)this.timestamp.clone());
         Vector equivalenceClassesClone = (Vector) this.lnkEquivalenceClasses.clone();
         Vector combinationsClone = (Vector) this.lnkCombinations.clone();
         b.setLnkEquivalenceClasses(equivalenceClassesClone);
         b.setLnkCombinations(combinationsClone);
        } catch(CloneNotSupportedException e) {
          e.printStackTrace();
        }
        return b;
      }



	/**
	 *  Checks if the test case has combinations or equivalence classes or std combinations
	 * @return
	 */
	public boolean isEmpty() {
		boolean hasEClassInCombinations = false;
		for (Iterator i = this.getCombinations().iterator();i.hasNext();)
		{
			if (!((Combination)i.next()).isEmpty())
			{
				hasEClassInCombinations = true;
				break;
			}
		}
		return ((!hasEClassInCombinations)&&(this.getLnkEquivalenceClasses().size()==0));
	}

	/**
	 *  Get all the elements related to a Test Case (no repeats and sorted)
	 * @return
	 */

	public Collection<Element> getRelatedElements() {
		Vector<Element> elements  = new Vector<Element>();
		//equivalence classes
		for (EquivalenceClass  equivalenceClass : this.getEquivalenceClasses())
			if (!elements.contains(equivalenceClass.getLnkElement()) && equivalenceClass.getLnkElement() !=null)
					elements.add(equivalenceClass.getLnkElement());

		//combinations
		for (Combination combination : this.getCombinations())
			for (Element element : combination.getRelatedElements())
			 if (!elements.contains(element) && element != null)
				elements.add(element);

		//sort the elements
		Collections.sort(elements);
		return elements;
	}

	public String getUserAndGeneratedDescription() {
		return this.getDescription()+"\n"+this.getDescriptionEditable();
	}

	/**
	 *  Gets all the related effects of a Test cases ordered and without duplications
	 * @return
	 */
	public Collection<Effect> getRelatedEffects() {

		Vector<Effect> effects = new Vector<Effect>();
		//from related combinations
		for (Combination combination : this.getCombinations())
		{
			for (Effect effect : combination.getEffects())
				if (!effects.contains(effect))
					effects.add(effect)  ;
			//from equivalence classes of the combinations
			if (!(combination instanceof StdCombination))
			for (EquivalenceClass equivalenceClass : combination.getEquivalenceClassesRecursiv())
				for (Effect effect : equivalenceClass.getEffects())
					if (!effects.contains(effect))
						effects.add(effect);

		}
		//from related equivalence classes
		for (EquivalenceClass equivalenceClass : this.getEquivalenceClasses())
			for (Effect effect : equivalenceClass.getEffects())
				if (!effects.contains(effect))
					effects.add(effect);

		//from visible equivalence classes in std combinations

		for (EquivalenceClass equivalenceClass : TestCaseManager.INSTANCE.getVisibleEquivalenceClassesOfStdCombination(this))
			for (Effect effect : equivalenceClass.getEffects())
				if (!effects.contains(effect))
					effects.add(effect);
		Collections.sort(effects);
		return effects;
	}
	/**
	 * Get all  the equivalence classes includimg the indirectly related ordered and without duplication
	 * @return
	 */
	public Collection<EquivalenceClass> getRelatedEquivalenceClasses()
	{
		Vector<EquivalenceClass> equivalenceClasses = new Vector<EquivalenceClass>();
		//all directly related
		equivalenceClasses.addAll(this.getEquivalenceClasses());
		//from related combinations
		for (Combination combination : getCombinations())
		{	if (!(combination instanceof StdCombination))
				for (EquivalenceClass equivalenceClass : combination.getEquivalenceClassesRecursiv())
					if (!equivalenceClasses.contains(equivalenceClass))
							equivalenceClasses.add(equivalenceClass);
		}
		//from equvalence classes of std combinations
		    for (Iterator i = TestCaseManager.INSTANCE.getVisibleEquivalenceClassesOfStdCombination(this).iterator(); i.hasNext();)
		    	equivalenceClasses.add((EquivalenceClass) i.next());

		Collections.sort(equivalenceClasses);
		return equivalenceClasses;
	}

	/* (non-Javadoc)
	 * @see model.util.CMCombinationsBean#addCombination(model.Combination)
	 */
	public void addCombination(Combination p_Combination) {
		getCombinations();
		if (!lnkCombinations.contains(p_Combination))
		{
			this.lnkCombinations.add(p_Combination);
			this.getHandler().fireModelEventHappen(this,CMField.COMBINATIONS);
		}

	}

	/* (non-Javadoc)
	 * @see model.util.CMCombinationsBean#removeCombination(model.Combination)
	 */
	public void removeCombination(Combination p_Combination) {
		getCombinations();
		if (lnkCombinations.contains(p_Combination))
		{
			this.lnkCombinations.remove(p_Combination);
			this.getHandler().fireModelEventHappen(this,CMField.COMBINATIONS);
		}
	}

	/* (non-Javadoc)
	 * @see model.util.CMCombinationsBean#getM_Combinations()
	 */
	public List<Combination> getCombinations() {
		ArrayList<Combination> combinations = new ArrayList<Combination>();
		if (lnkCombinations == null)
			lnkCombinations = new Vector<Combination>();
		combinations.addAll(lnkCombinations);
		return Collections.unmodifiableList(combinations);
	}

	/* (non-Javadoc)
	 * @see model.util.CMModelSource#addModelListener(model.util.CMModelListener)
	 */
	public void addModelListener(CMModelListener p_listener) {
		getHandler().addModelListener(p_listener);

	}

	/* (non-Javadoc)
	 * @see model.util.CMModelSource#removeModelListener(model.util.CMModelListener)
	 */
	public void removeModelListener(CMModelListener p_listener) {
		getHandler().removeModelListener(p_listener);

	}

	private CMModelEventHandler getHandler() {
		if (this.handler == null)
			this.handler = new CMModelEventHandler();
		return this.handler;
	}

	/* (non-Javadoc)
	 * @see model.util.CMEquivalenceClassesBean#getEquivalenceClasses()
	 */
	public List<EquivalenceClass> getEquivalenceClasses() {
		if (lnkEquivalenceClasses == null)
			lnkEquivalenceClasses = new Vector<EquivalenceClass>();
		ArrayList<EquivalenceClass> list = new ArrayList<EquivalenceClass>();
		list.addAll(lnkEquivalenceClasses);
		return list;
	}

	/* (non-Javadoc)
	 * @see model.util.CMEquivalenceClassesBean#addEquivalenceClass(model.EquivalenceClass)
	 */
	public void addEquivalenceClass(EquivalenceClass p_equivalenceClass) {
		getEquivalenceClasses();
		if (!lnkEquivalenceClasses.contains(p_equivalenceClass))
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
		if (lnkEquivalenceClasses.contains(p_equivalenceClass))
		{
			lnkEquivalenceClasses.remove(p_equivalenceClass);
			this.getHandler().fireModelEventHappen(this,CMField.EQUIVALENCE_CLASSES);
		}

	}

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
	 * @see model.util.CMDependencyBean#addDependency(model.Dependency)
	 */
	public void addDependency(Dependency p_dependency) {
		getDependencies();
		if (!m_Dependencies.contains(p_dependency))
		{
			m_Dependencies.add(p_dependency);
			this.getHandler().fireModelEventHappen(this,CMField.DEPENDENCIES);
		}

	}

	/* (non-Javadoc)
	 * @see model.util.CMDependencyBean#removeDependency(model.Dependency)
	 */
	public void removeDependency(Dependency p_dependency) {
		getDependencies();
		if (m_Dependencies.contains(p_dependency))
		{
			m_Dependencies.remove(p_dependency);
			this.getHandler().fireModelEventHappen(this,CMField.DEPENDENCIES);
		}

	}


	public void setState(State p_state) {
		state = p_state.intValue();
	}
	 @Override
	public String toString() {
		return getName()+getStateName()+" "+getDescription();
	}

	public List<? extends CMRiskLevelBean> getChildRiskLevels() {
		return new ArrayList<CMRiskLevelBean>();
	}

	public List<? extends CMRiskLevelBean> getParentRiskLevels() {
		List<CMRiskLevelBean> list = new ArrayList<CMRiskLevelBean>();
		list.addAll(getCombinations());
		list.addAll(getEquivalenceClasses());
		return list;
	}

	public List<? extends CMStateBean> getChildStates() {
		return new ArrayList<CMStateBean>();
	}

	public List<? extends CMStateBean> getParentStates() {
		List<CMStateBean> list = new ArrayList<CMStateBean>();
		list.addAll(getCombinations());
		list.addAll(getEquivalenceClasses());
		return list;
	}

	public Structure getStructure() {
		if (structure == null)
			structure = StructureManager.getSelectedStructure();
		return structure;
	}

	void setStructure(Structure structure) {
		this.structure = structure;
	}
	public boolean existsCombination(EquivalenceClass p_EquivalenceClass) {
	       int numOfCombinations = getCombinations().size();
	       Combination combination = null;
	       for (int i = 0; i < numOfCombinations; i++) {
	           combination = (Combination) getCombinations().get(i);
	           if (!(combination instanceof StdCombination)) { // New!!!!
	               if (combination.contains(p_EquivalenceClass)) {
	                   return true;
	               }
	           }
	       }
	       return false;
	   }
	public List<Combination> getCombinations(EquivalenceClass p_EquivalenceClass) {
	       ArrayList<Combination> combinationsInTestCaseInEquivalenceClass = new ArrayList<Combination>();
	       for (Combination combination : getCombinations()) {
	           if (!(combination instanceof StdCombination)) {
	               if (combination.contains(p_EquivalenceClass)) {
	                   if (!combinationsInTestCaseInEquivalenceClass.contains(combination)) {
	                       combinationsInTestCaseInEquivalenceClass.add(combination);
	                   }
	               }
	           }
	       }
	       return combinationsInTestCaseInEquivalenceClass;
	   }
	private String createCombinationsString(List p_Combinations, List p_CombinationsUsed) {
	    StringBuffer sBuffer = new StringBuffer();
	    int numOfCombinations = p_Combinations.size();
	    Combination combination = null;
	    for (int i = 0; i < numOfCombinations; i++) {
	        combination = (Combination)p_Combinations.get(i);
	        if (!p_CombinationsUsed.contains(combination)) {
	            p_CombinationsUsed.add(combination);
	            //hmendez_06122005_begin
            	if (numOfCombinations>1)
            		sBuffer.append(BusinessRules.OPEN_SQUARE_BRACKET);
                sBuffer.append(createCombinationString(combination));
            	if (numOfCombinations>1)
            	  sBuffer.append(BusinessRules.CLOSED_SQUARE_BRACKET);
            	if (i+1<numOfCombinations)
            	{
            	  sBuffer.append(BusinessRules.SPACE);
            	  sBuffer.append(BusinessRules.AND);
            	  sBuffer.append(BusinessRules.SPACE);
                  sBuffer.append(BusinessRules.NEW_LINE);
            	}
            	//hmendez_06122005_end
	        }
	    }
	    return sBuffer.toString();
	}
	private String createCombinationString(Combination p_Combination) {
	     StringBuffer sBuffer = new StringBuffer();
//	   sBuffer.append(BusinessRules.NEW_LINE);//fcastro_20092004
	           Dependency dependency = p_Combination.getDependency();
	           if (dependency != null) {
	               sBuffer.append(dependency.getName());
	               sBuffer.append(BusinessRules.DEPENDENCY_COMBINATION_SEPARATOR);
	           }
	           sBuffer.append(p_Combination.getName());
	           sBuffer.append(p_Combination.getStateName());
	           sBuffer.append(BusinessRules.COLON);
	           sBuffer.append(BusinessRules.SPACE);
	           sBuffer.append(p_Combination.getDescription());
	        	//sBuffer.append(BusinessRules.SPACE); hmendez_06122005
	           //sBuffer.append(BusinessRules.NEW_LINE); hmendez_06122005
		        return sBuffer.toString();
		    }
		String createEquivalenceClassString(EquivalenceClass p_EquivalenceClass) {
		    StringBuffer sBuffer = new StringBuffer();
		    //sBuffer.append(BusinessRules.NEW_LINE);hmendez_15122005
		    sBuffer.append(p_EquivalenceClass.getLnkElement().getName());
		    sBuffer.append(BusinessRules.EQUAL);
		    sBuffer.append(BusinessRules.DOUBLE_QUOTE);
		    sBuffer.append(p_EquivalenceClass.getValue());
		    sBuffer.append(BusinessRules.DOUBLE_QUOTE);
		    //sBuffer.append(BusinessRules.NEW_LINE);hmendez_15122005
		    return sBuffer.toString();
		}
		public StdCombination getStdCombination() {
	      //returns the first (and only std combination assigned)
	        for (Combination combination: getCombinations()) {
	            if (combination instanceof StdCombination) {
	                return (StdCombination)combination;
	            }
	        }
	        return null;
	    }
		public StdCombination getStdCombination(EquivalenceClass p_EquivalenceClass) {
		            StdCombination stdCombination = getStdCombination();
		            if (stdCombination != null) {
		                if (stdCombination.getEquivalenceClasses().contains(p_EquivalenceClass)) {
		                    return stdCombination;
		                }
		                else {
		                    return null;
		                }
		            }
		            else {
		                return null;
		            }
		    }
		private String createStdCombinationString(StdCombination p_Combination, List p_CombinationsUsed) {
	        StringBuffer sBuffer = new StringBuffer();
	        if (!p_CombinationsUsed.contains(p_Combination)) {
	            p_CombinationsUsed.add(p_Combination);
	            //sBuffer.append(BusinessRules.NEW_LINE);hmendez_14122005
	            sBuffer.append(BusinessRules.OPEN_SQUARE_BRACKET);
	            sBuffer.append(p_Combination.getName());
	            sBuffer.append(BusinessRules.COLON);
	            sBuffer.append(BusinessRules.SPACE);
	            int numOfEquivalenceClasses = p_Combination.getEquivalenceClasses().size();
	            EquivalenceClass equivalenceClass = null;
	            for (int i = 0; i < numOfEquivalenceClasses; i++) {
	                equivalenceClass = (EquivalenceClass)p_Combination.getEquivalenceClasses().get(i);
	                sBuffer.append(equivalenceClass.getLnkElement().getName());
	                sBuffer.append(BusinessRules.EQUAL);
	                sBuffer.append(BusinessRules.DOUBLE_QUOTE);
	                sBuffer.append(equivalenceClass.getValue());
	                sBuffer.append(BusinessRules.DOUBLE_QUOTE);
	                if (i+1<numOfEquivalenceClasses)
	                {
	                  sBuffer.append(BusinessRules.SPACE);
	                  sBuffer.append(BusinessRules.OR);
	                  sBuffer.append(BusinessRules.SPACE);
	                }
	            }
	            sBuffer.append(BusinessRules.CLOSED_SQUARE_BRACKET);
	            sBuffer.append(BusinessRules.SPACE);
	            //sBuffer.append(BusinessRules.NEW_LINE);hmendez_14122005
	        }
		    return sBuffer.toString();
		}

		public void fireModelEventHappen(CMField field) {
			getHandler().fireModelEventHappen(this, field);

		}

		public void addError(CMError error) {
			
			if (!getErrors().contains(error)){
				m_CMErrors.add(error);
				this.getHandler().fireModelEventHappen(this, CMField.ERRORS);
				Collections.sort(m_CMErrors);
			}
			
		}

		public void removeError(CMError error) {
			if (getErrors().contains(error)){
				m_CMErrors.remove(error);
				this.getHandler().fireModelEventHappen(this, CMField.ERRORS);
			}
		}
}
