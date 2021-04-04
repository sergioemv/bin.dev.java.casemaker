package bi.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.JOptionPane;

import model.ApplicationSetting;
import model.BusinessRules;
import model.Combination;
import model.Dependency;
import model.Effect;
import model.Element;
import model.EquivalenceClass;
import model.ExpectedResult;
import model.Requirement;
import model.State;
import model.Structure;
import model.TestObject;
import model.brmodel.AndBlock;
import model.brmodel.BooleanBlock;
import model.brmodel.BusinessAction;
import model.brmodel.BusinessObject;
import model.brmodel.CombinationsBAPair;
import model.brmodel.FormalPolicy;
import model.brmodel.InferenceRule;
import model.brmodel.OrBlock;
import model.brmodel.PosRelationalPair;
import model.brmodel.RawDependency;
import model.brmodel.RelationalPair;
import model.edit.CMModelEditFactory;
import model.util.CMEffectsBean;
import model.util.CMModelEventHandler;

import org.apache.log4j.Logger;

import bi.controller.combination.generator.AllPairsGeneratorUpdater;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.mainframeviews.CMFrameView;
import bi.view.undomanagementviews.CMCompoundEdit;
import bi.view.undomanagementviews.undoviews.CMUndoMediator;


public class BRImportProcessManager {
    private boolean flowReflected= false;
	
    //public void importObjects(TestObject p_TestObject, ApplicationSetting p_ApplicationSetting, String p_FromFilePath,CMFrameView p_Frame) throws FileNotFoundException {
    private int sourceSyntax = -1;
    private BRParserDataManager m_BRParserDataManager = new BRParserDataManager();
    private int conditionId = 0;
    private FormalPolicy m_FormalPolicy;
    private ToolVendorManager m_ToolVendorManager;

    public void importObjects(TestObject p_TestObject, ApplicationSetting p_ApplicationSetting) throws FileNotFoundException {
        FormalPolicy p_FormalPolicy;
        flowReflected= (p_TestObject.getOrigin() != null);
        File brFile = new File(CMApplication.frame.findAbsoluteBRReportsPath());
        int fileSyntax = p_TestObject.getBRulesReference().getFileSyntax();

        this.getM_BRParseProcessManager().doCheck(new FileReader (brFile), fileSyntax, false);
        switch (fileSyntax) {
            case 0: {
                    p_FormalPolicy = this.getM_BRParseProcessManager().getEnglishFormalPolicy();
                    break;
                }
            case 1: {
                    p_FormalPolicy = this.getM_BRParseProcessManager().getGermanFormalPolicy();
                    break;
                }
            default:
                return;
        }
        this.sourceSyntax = p_FormalPolicy.getSourceSyntax();
        if(flowReflected)
        	deleteOtherFromFormalPolicy(p_FormalPolicy);
        this.m_FormalPolicy = p_FormalPolicy;
        Vector selectedObjects = this.selectObjectsToImport(p_FormalPolicy.getM_BusinessObjects());
        Vector selectedObjectToAssociation=this.selectedObjectToAssociatonFact(p_FormalPolicy.getM_BusinessObjects(), selectedObjects);
        this.conditionId = 0;
        updateTestObject(p_TestObject.getStructure(), selectedObjects,selectedObjectToAssociation, p_FormalPolicy, fileSyntax,p_ApplicationSetting);
    }
    public void updateTestObject(Structure p_Structure,Vector selectedObjects,Vector selectedObjectToAssociation, FormalPolicy p_FormalPolicy,int fileSyntax, ApplicationSetting p_ApplicationSetting){
		//clear the structure
    	CMModelEventHandler.setNotifyEnabled(false);
    	p_Structure.clear();
    	
    	// insert all the elements and equivalence classes found on the selected objects defined as business Objects
        insertElements(p_Structure,selectedObjects,fileSyntax);
        //insert all effects found on the formal policy defined as business Actions
        insertEffects(p_Structure,p_FormalPolicy.getM_BusinessActions());
        //insert all dependencies found in the formal policy defines as inference rules
        insertDependencies(p_Structure,p_FormalPolicy.getM_InferenceRules(), p_ApplicationSetting);
        //
        insertAssociations(p_Structure,selectedObjectToAssociation);
        insertDirectEffects(p_Structure,p_FormalPolicy.getM_InferenceRules());
        validateDependencies(p_Structure);
        CMModelEventHandler.setNotifyEnabled(true);
     }
    public void insertElements(Structure p_Structure,List selectedObjects,int fileSyntax){
        for(int i=0;i<selectedObjects.size();i++)
            p_Structure.addElement(createElement((BusinessObject)selectedObjects.get(i),p_Structure,fileSyntax));
    }
	public void insertEffects(Structure p_Structure,List<BusinessAction> name){
	    for(int i=0;i<name.size();i++){
            p_Structure.addEffect(createEffect((BusinessAction)name.get(i),p_Structure));
        }
    }
    public Effect createEffect(BusinessAction bAction, Structure p_Structure) {
        Effect newEffect;
        newEffect = EffectManager.INSTANCE.createEffect(p_Structure);
        newEffect.setDescription(bAction.getDescription());
        newEffect.setRiskLevel(bAction.getRisklevel());
        newEffect.setState(bAction.getState());
        for (ExpectedResult res : bAction.getResult())
        	newEffect.addExpectedResult(res);
        for (Requirement req : bAction.getRequirement())
        	newEffect.addRequirement(req);
        return newEffect;
    }
	@SuppressWarnings("unchecked")
	public Vector selectObjectsToImport(List<BusinessObject> name) {
        Vector selectedObjects = new Vector();
        BusinessObject p_BusinessObject;
        for (int i = 0; i < name.size(); i++) {
            p_BusinessObject = (BusinessObject)name.get(i);
            if(p_BusinessObject.getAttributes().size() >0){
            	modifiedBusinessObjectAllowAtributeFact(p_BusinessObject);
            }
            if (p_BusinessObject.getSuperClasses().size()>0) {
            	modifiedBusinesObjectAllowGeneralizationFact(p_BusinessObject);
			}
            if(p_BusinessObject.getRolesOfThisObject().size() >0){
            	modifiedBusinessObjectAllowRoleFact(p_BusinessObject);
            }
            if(p_BusinessObject.getComposedBy().size() >0 ){
            	modifiedBusinessObjectAllowAgregationFact(p_BusinessObject);
            }
            if (p_BusinessObject.getValues().size() > 0) {
                selectedObjects.addElement(p_BusinessObject);
            }
        }
        return selectedObjects;
    }

    @SuppressWarnings("unchecked")
	public Vector selectedObjectToAssociatonFact(List<BusinessObject> name, Vector selectedObjectToImport){
    	Vector selectedObject= new Vector();
    	BusinessObject p_BusinessObject;
        for (int i = 0; i < name.size(); i++) {
        	p_BusinessObject = (BusinessObject)name.get(i);
        	if (p_BusinessObject.getModifiedBy().size() >0 ) {
        		if(selectedObjectHasValues(selectedObjectToImport,p_BusinessObject))
        			selectedObject.addElement(p_BusinessObject);
        	}
        }
    	return selectedObject;
    }
    private boolean selectedObjectHasValues(Vector selectedObjectToImport, BusinessObject businessObject) {
		if (selectedObjectToImport.contains(businessObject)) {
			for (Iterator iter = businessObject.getModifiedBy().iterator(); iter
					.hasNext();) {
				BusinessObject modifier = (BusinessObject) iter.next();
				if(!selectedObjectToImport.contains(modifier))
					return false;
			}
			return true;
		}
		return false;
    }

	@SuppressWarnings("unchecked")
	private void modifiedBusinessObjectAllowAtributeFact(BusinessObject p_BusinessObject){
    	for (Iterator iter = p_BusinessObject.getAttributes().iterator(); iter.hasNext();) {
			BusinessObject attribute = (BusinessObject) iter.next();
			if(!containsIgnoreCaseString(p_BusinessObject.getValues(),attribute.getName()))
				p_BusinessObject.getValues().addElement(attribute.getName());
		}
    	p_BusinessObject.setAttributes(new Vector());
    }

    @SuppressWarnings("unchecked")
	private void modifiedBusinesObjectAllowGeneralizationFact(BusinessObject p_BusinessObject){
    	for (Iterator iter = p_BusinessObject.getSuperClasses().iterator(); iter.hasNext();) {
			BusinessObject superClass = (BusinessObject) iter.next();
			modifiedBusinessObjectAllowAtributeFact(superClass);
			modifiedBusinessObjectAllowAgregationFact(superClass);
			modifiedBusinessObjectAllowRoleFact(superClass);
			for (Iterator iterator = superClass.getValues().iterator(); iterator.hasNext();) {
				String value = (String) iterator.next();
				if(!containsIgnoreCaseString(p_BusinessObject.getValues(),value))
					p_BusinessObject.getValues().addElement(value);
			}
		}
    	p_BusinessObject.setSuperClasses(new Vector());
    }

    @SuppressWarnings("unchecked")
	private void modifiedBusinessObjectAllowRoleFact(BusinessObject p_BusinessObject){
    	for (Iterator iter = p_BusinessObject.getRolesOfThisObject().iterator(); iter.hasNext();) {
			BusinessObject role = (BusinessObject) iter.next();
			modifiedBusinessObjectAllowAtributeFact(role);
			modifiedBusinessObjectAllowAgregationFact(role);
			modifiedBusinesObjectAllowGeneralizationFact(role);
			for (Iterator iterator = role.getValues().iterator(); iterator.hasNext();) {
				String value = (String) iterator.next();
				if(!containsIgnoreCaseString(p_BusinessObject.getValues(),value));
				p_BusinessObject.getValues().addElement(value);
			}
		}
    	p_BusinessObject.setRolesOfThisObject(new Vector());
    }

    @SuppressWarnings("unchecked")
	private void modifiedBusinessObjectAllowAgregationFact(BusinessObject p_BusinessObject){
    	for (Iterator iter = p_BusinessObject.getComposedBy().iterator(); iter.hasNext();) {
			BusinessObject composer = (BusinessObject) iter.next();
			if(!containsIgnoreCaseString(p_BusinessObject.getValues(),composer.getName()))
				p_BusinessObject.getValues().addElement(composer.getName());
		}
    	p_BusinessObject.setComposedBy(new Vector());
    }

    private boolean containsIgnoreCaseString(Vector p_toResearch, String p_ToFind){
    	if(p_toResearch.contains(p_ToFind))
    		return true;
    	else{
    		for (Iterator iter = p_toResearch.iterator(); iter.hasNext();) {
				String value = (String) iter.next();
				if(value.equalsIgnoreCase(p_ToFind))
					return true;
			}
    		return false;
    	}
    }

    @SuppressWarnings("unchecked")
	public void insertAssociations(Structure p_Structure,Vector p_AssociationsObjects){
    	Vector elementsForAssociations= new Vector();
    	Vector businessObjectInvolved= new Vector();
    	Dependency newDependency;
    	for (Iterator iter = p_AssociationsObjects.iterator(); iter.hasNext();) {
			BusinessObject associationObject = (BusinessObject) iter.next();
			businessObjectInvolved.addAll(associationObject.getModifiedBy());
			businessObjectInvolved.addElement(associationObject);
			elementsForAssociations=findElementsForDependency(businessObjectInvolved,p_Structure.getLnkElements());
	        newDependency = DependencyManager.INSTANCE.createDependency(p_Structure);
	        newDependency.setLnkElements(elementsForAssociations);
	        String dependencyDescription = getDescriptionBasedOnExistingElements(newDependency);
	        newDependency.setDescription(dependencyDescription);
	        if(!existDependency(p_Structure, newDependency))
	        	p_Structure.addDependency(newDependency);
		}
    }

    private boolean existDependency(Structure structure, Dependency newDependency) {
		for (Iterator iter = structure.getLnkDependencies().iterator(); iter.hasNext();) {
			Dependency existDependency = (Dependency) iter.next();
			if(existDependency.getLnkElements().containsAll(newDependency.getLnkElements()))
				return true;
		}
		return false;
	}

//HCanedo_07032006_end
    public Element createElement(BusinessObject bObject, Structure p_Structure, int fileSyntax) {
        Element newElement;
        newElement = ElementManager.INSTANCE.createElement(p_Structure);
        newElement.setName(bObject.getName());
        createEquivalenceClassesForElement(newElement, bObject.getValues(), fileSyntax);
        return newElement;
    }

    @SuppressWarnings("unchecked")
	public void createEquivalenceClassesForElement(Element newElement, Vector values, int fileSyntax) {
        for (Object eq : values){
        	EquivalenceClass eqClass =EquivalenceClassManager.INSTANCE.createEquivalenceClass(newElement);
        	eqClass.setValue((String)eq);
        	newElement.addEquivalenceClass(eqClass);
        }
    }


    @SuppressWarnings("unchecked")
	public Vector sendLast(List eqClasses, int fileSyntax) {
        EquivalenceClass eqClass;
        EquivalenceClass aux = null;
        Vector sentLast = new Vector();
        switch (fileSyntax) {
            case 0: {
                    for (int i = 0; i < eqClasses.size(); i++) {
                        eqClass = (EquivalenceClass)eqClasses.get(i);
                        if (eqClass.getValue().equals(BusinessRules.OTHER_ENGLISH_WORD)) {
                            aux = eqClass;
                        }
                        else {
                            sentLast.addElement(eqClass);
                        }
                    }
                }
            case 1: {
                    for (int i = 0; i < eqClasses.size(); i++) {
                        eqClass = (EquivalenceClass)eqClasses.get(i);
                        if (eqClass.getValue().equals(BusinessRules.OTHER_GERMAN_WORD)) {
                            aux = eqClass;
                        }
                        else {
                            sentLast.addElement(eqClass);
                        }
                    }
                }
        }
        sentLast.addElement(aux);
        return sentLast;
    }



    @SuppressWarnings("unchecked")
	public void insertDirectEffects(Structure p_Structure, List<InferenceRule> p_InferenceRules) {
        Vector directCombiPairs = new Vector();
        InferenceRule theRule;
        Vector combinationsBAPairs;
        CombinationsBAPair combiPair;
        Vector elementsList = p_Structure.getLnkElements();
        //Vector equivalenceClasses;
        Element element;
        BusinessObject businessObject;
        for (int i = 0; i < p_InferenceRules.size(); i++) {
            theRule = p_InferenceRules.get(i);
            combinationsBAPairs = theRule.getCombinationsBActionPairs();
            for (int j = 0; j < combinationsBAPairs.size(); j++) {
                combiPair = (CombinationsBAPair)combinationsBAPairs.elementAt(j);
                if (combiPair.getCombination().size() == 1) {
                    directCombiPairs.addElement(combiPair);
                }
            }
        }
        Vector groupedByElement = new Vector();
        for (int i = 0; i < elementsList.size(); i++) {
            element = (Element)elementsList.elementAt(i);
            String elementName = element.getName();
            for (int j = 0; j < directCombiPairs.size(); j++) {
                combiPair = (CombinationsBAPair)directCombiPairs.elementAt(j);
                businessObject = (BusinessObject)combiPair.getDifferentObjectsInvolved().elementAt(0);
                if (businessObject.getName().equals(elementName)) {
                    groupedByElement.addElement(combiPair);
                }
            }
            if (groupedByElement.size() > 0) {
                assignDirectEffectsByElement(groupedByElement, element, p_Structure);
            }
            groupedByElement = new Vector();
        }
    }

    public void assignDirectEffectsByElement(Vector groupedByElement, Element element, Structure structure) {
        List elementEqClasses = element.getEquivalenceClasses();
        EquivalenceClass eqClass;
       // EquivalenceClass elementEqClass;
        Effect effect;
        RelationalPair relPair;
        BusinessAction action;
      //  BusinessAction elseAction;
        CombinationsBAPair combiPair;
        for (int i = 0; i < groupedByElement.size(); i++) {
            combiPair = (CombinationsBAPair)groupedByElement.elementAt(i);
//HCanedo_21032006_begin
            //action = combiPair.getAction();
            Vector actions=combiPair.getAction();
            relPair = (RelationalPair)combiPair.getCombination().elementAt(0);
            eqClass = findEquivalenceClass(relPair.getTheValue(), elementEqClasses);
            if (eqClass != null){
            	int eqClassIndex = elementEqClasses.indexOf(eqClass);
                if (actions != null) {//action
                	for (Iterator iter = actions.iterator(); iter.hasNext();) {
    					 action = (BusinessAction) iter.next();
    					 effect = findEffect(action.getDescription(), structure.getEffects());
    					 boolean change=addEffectIfNotPresent(eqClass, effect);
    					 if(change){
    						 changeEquivalenceClassStateAndRiskLevel(eqClass, effect);
    					 }
                	}
                }
            }

        }
    }


    @SuppressWarnings("unchecked")
	public void createOthersValue(Element newElement) {
        Vector elementEqClasses = new Vector(0);
        elementEqClasses.addAll(newElement.getEquivalenceClasses());//cc getLnkEquivalenceClasses();
        EquivalenceClass eqClass;
        int fileSyntax = this.getSourceSyntax();
        for (int i = 0; i < elementEqClasses.size(); i++) {
            eqClass = (EquivalenceClass)elementEqClasses.get(i);
            switch (fileSyntax) {
                case 0: {
                        if (eqClass.getValue().equals(BusinessRules.OTHER_ENGLISH_WORD)) {
                            elementEqClasses = sendLast(elementEqClasses, fileSyntax);
                            for (int j = 0; j < elementEqClasses.size(); j++)
                            	newElement.addEquivalenceClass((EquivalenceClass)elementEqClasses.elementAt(j));//cc setLnkEquivalenceClasses(elementEqClasses);
                            return;
                        }
                    }
                case 1: {
                        if (eqClass.getValue().equals(BusinessRules.OTHER_GERMAN_WORD)) {
                            elementEqClasses = sendLast(elementEqClasses, fileSyntax);
                            for (int j = 0; j < elementEqClasses.size(); j++)
                            	newElement.addEquivalenceClass((EquivalenceClass)elementEqClasses.elementAt(j));//cc setLnkEquivalenceClasses(elementEqClasses);

                            //cc newElement.setLnkEquivalenceClasses(elementEqClasses);
                            return;
                        }
                    }
            }
        }
        eqClass = EquivalenceClassManager.INSTANCE.createEquivalenceClass(newElement);
        switch (fileSyntax) {
            case 0: {
                    eqClass.setValue(BusinessRules.OTHER_ENGLISH_WORD);
                    break;
                }
            case 1: {
                    eqClass.setValue(BusinessRules.OTHER_GERMAN_WORD);
                }
        }
        eqClass.setLnkElement(newElement);
        newElement.addEquivalenceClass(eqClass);
        elementEqClasses.add(eqClass);       
    }
    //grueda_18072004_begin
    @SuppressWarnings("unchecked")
	public void insertDependencies(Structure p_Structure,
			List<InferenceRule> inferenceRules, ApplicationSetting p_ApplicationSetting){

        Vector groupedCombiPairsList = new Vector();
        Vector rawDependenciesList = new Vector();
        Vector combiPairsList = new Vector();
        Vector combiPairAll = new Vector();
        Vector sameElementIfBock= new Vector();
        Vector elementsForDependency;
        String dependencyDescription;
        RawDependency rawDependency;
        Dependency newDependency;

        // For each inference rule
       for(InferenceRule inferenceRule : inferenceRules){
    	   // create the other values
            otherValueCreation(inferenceRule, null, p_Structure);
            getCombinationsFromInferenceRule(inferenceRule, null, combiPairsList,sameElementIfBock, p_Structure);
            inferenceRule.setCombinationsBActionPairs(combiPairsList);
            concatVectors(combiPairAll, combiPairsList);
        }
        groupCombiPairsByElementCount(combiPairAll, groupedCombiPairsList);
        generateRawDependenciesList(groupedCombiPairsList, rawDependenciesList);
        for (int j = 0; j < rawDependenciesList.size(); j++) {
            rawDependency = (RawDependency)rawDependenciesList.elementAt(j);
            elementsForDependency = findElementsForDependency(rawDependency.getBusinessObjectsInvolved(),
                p_Structure.getLnkElements());
            newDependency = DependencyManager.INSTANCE.createDependency(p_Structure);
            newDependency.setLnkElements(elementsForDependency);
            dependencyDescription = getDescriptionBasedOnExistingElements(newDependency);
            newDependency.setDescription(dependencyDescription);
            getEqClassesForDependency(newDependency.getLnkEquivalenceClasses(), rawDependency, p_Structure);
			generateCombinations(newDependency,p_ApplicationSetting,p_Structure,rawDependency);

            assignEffectsToCombinations(newDependency, p_Structure, rawDependency.getCombiPairs());
            if(newDependency.getCombinations().size() > 0)
            	p_Structure.addDependency(newDependency);
        }
    }

    public void otherValueCreation(InferenceRule theRule, Vector processedBoolExpression, Structure p_Structure) {

        OrBlock boolStructure = theRule.getBooleanStructure();
        Vector actProcessedBoolExpressionTruePart;
        Vector actProcessedBoolExpressionElsePart;
        Object truePart = theRule.getTruePart();
        Object falsePart = theRule.getFalsePart();
        actProcessedBoolExpressionTruePart = this.collectOr(processedBoolExpression, boolStructure);
//HCanedo_21032006_begin
        if (truePart instanceof Vector){//BusinessAction) {
            checkOtherValueCreationInCombinations(actProcessedBoolExpressionTruePart, p_Structure);
        }
//HCanedo_21032006_end
        else {
            otherValueCreation((InferenceRule)truePart, actProcessedBoolExpressionTruePart, p_Structure);
        }
        if (falsePart != null) {
            Object elseBoolStructure = this.generateFalsePartBoolStructure(boolStructure);
            actProcessedBoolExpressionElsePart = this.collectAnd(processedBoolExpression, (AndBlock)elseBoolStructure);
            //actProcessedBoolExpressionElsePart = this.collectOr(processedBoolExpression,(OrBlock)elseBoolStructure);
//HCanedo_21032006_begin
            if (falsePart instanceof Vector){//BusinessAction) {
                checkOtherValueCreationInCombinations(actProcessedBoolExpressionElsePart, p_Structure);
            }
//HCanedo_21032006_end
            else {
                otherValueCreation((InferenceRule)falsePart, actProcessedBoolExpressionElsePart, p_Structure);
            }
        }
    }

    public void checkOtherValueCreationInCombinations(Vector combinationsList, Structure p_Structure) {
       Vector normalizedCombinations;
        normalizedCombinations = normalizeCombinations(combinationsList, p_Structure);
    }

    @SuppressWarnings("unchecked")
	public void getCombinationsFromInferenceRule(InferenceRule theRule,
			Vector processedBoolExpression, Vector response, Vector sameElementIfBock, Structure p_Structure) {
        OrBlock boolStructure = theRule.getBooleanStructure();
        Vector actProcessedBoolExpressionTruePart;
        Vector actProcessedBoolExpressionElsePart;
        Object truePart = theRule.getTruePart();
        Object falsePart = theRule.getFalsePart();
        int condition = this.getConditionId();
        actProcessedBoolExpressionTruePart = this.collectOr(processedBoolExpression, boolStructure);

//HCanedo_21032006_begin
        if (truePart instanceof Vector){//BusinessAction) {
            Vector normalizedCombinations = normalizeCombinations(actProcessedBoolExpressionTruePart,p_Structure);
            for (int i = 0; i < normalizedCombinations.size(); i++) {
                response.addElement(
                    new CombinationsBAPair((Vector/*BusinessAction*/)truePart, (Vector)normalizedCombinations.elementAt(i), condition));
            }
        }
//HCanedo_21032006_end
        else {
            getCombinationsFromInferenceRule((InferenceRule)truePart, actProcessedBoolExpressionTruePart, response,sameElementIfBock, p_Structure);
        }
        if (falsePart != null) {
            AndBlock elseBoolStructure = (AndBlock)this.generateFalsePartBoolStructure(boolStructure);
            actProcessedBoolExpressionElsePart = this.collectAnd(processedBoolExpression, elseBoolStructure);
//HCanedo_21032006_begin
            if (falsePart instanceof Vector){//BusinessAction) {
                Vector normalizedCombinations = normalizeCombinations(actProcessedBoolExpressionElsePart, p_Structure);
                 for (int i = 0; i < normalizedCombinations.size(); i++) {
                    response.addElement(
                        new CombinationsBAPair((Vector/*BusinessAction*/)falsePart, (Vector)normalizedCombinations.elementAt(i), condition));
                }
            }
//HCanedo_21032006_end
            else {
                getCombinationsFromInferenceRule((InferenceRule)falsePart, actProcessedBoolExpressionElsePart,
                    response,sameElementIfBock, p_Structure);
            }
        }
        this.conditionId++;
    }


	@SuppressWarnings("unchecked")
	public Vector collectOr(Vector collector, OrBlock orBlock) {
        Vector v = new Vector();
        Vector p;
        Vector block = orBlock.getBlock();
        if (collector == null) {
            for (int i = 0; i < block.size(); i++) {
                if (block.elementAt(i)instanceof RelationalPair) {
                    p = new Vector();
                    p.addElement(block.elementAt(i));
                    v.addElement(p);
                }
                else {
                    if (((BooleanBlock)block.elementAt(i)).getClassName().equals("AndBlock")) {
                        v = concatVectors(v, collectAnd(null, (AndBlock)block.elementAt(i)));
                    }
                    else {
                        v = concatVectors(v, collectOr(null, (OrBlock)block.elementAt(i)));
                    }
                }
            }
        }
        else {
            for (int i = 0; i < block.size(); i++) {
                if (block.elementAt(i)instanceof RelationalPair) {
                    for (int j = 0; j < collector.size(); j++) {
                        p = (Vector)((Vector)collector.elementAt(j)).clone();
                        p.addElement(block.elementAt(i));
                        v.addElement(p);
                    }
                }
                else {
                    if (((BooleanBlock)block.elementAt(i)).getClassName().equals("AndBlock")) {
                        v = concatVectors(v, collectAnd(collector, (AndBlock)block.elementAt(i)));
                    }
                    else {
                        v = concatVectors(v, collectOr(collector, (OrBlock)block.elementAt(i)));
                    }
                }
            }
        }
        return v;
    }

    @SuppressWarnings("unchecked")
	public Vector collectAnd(Vector collector, AndBlock andBlock) {
        Vector v;
        Vector block = andBlock.getBlock();
        if (collector == null) {
            v = new Vector();
            v.addElement(new Vector());
            for (int i = 0; i < block.size(); i++) {
                if (block.elementAt(i)instanceof RelationalPair) {
                    for (int j = 0; j < v.size(); j++) {
                        ((Vector)v.elementAt(j)).addElement(block.elementAt(i));
                    }
                }
                else {
                    if (((BooleanBlock)block.elementAt(i)).getClassName().equals("OrBlock")) {
                        v = collectOr(v, (OrBlock)block.elementAt(i));
                    }
                    else {
                        v = collectAnd(v, (AndBlock)block.elementAt(i));
                    }
                }
            }
            return v;
        }
        else {
            Vector collectorCopy = makeCollectorCopy(collector);
            for (int i = 0; i < block.size(); i++) {
                if (block.elementAt(i)instanceof RelationalPair) {
                    for (int j = 0; j < collectorCopy.size(); j++) {
                        ((Vector)collectorCopy.elementAt(j)).addElement(block.elementAt(i));
                    }
                }
                else {
                    if (((BooleanBlock)block.elementAt(i)).getClassName().equals("OrBlock")) {
                        collectorCopy = collectOr(collectorCopy, (OrBlock)block.elementAt(i));
                    }
                    else {
                        collectorCopy = collectAnd(collectorCopy, (AndBlock)block.elementAt(i));
                    }
                }
            }
            return collectorCopy;
        }
    }

    public Object generateFalsePartBoolStructure(Object boolStructure) {
        BooleanBlock negBoolStructure;
        PosRelationalPair relPair;
        Vector block = ((BooleanBlock)boolStructure).getBlock();
        if (((BooleanBlock)boolStructure).getClassName().equals("OrBlock")) {
            negBoolStructure = new AndBlock();
        }
        else {
            negBoolStructure = new OrBlock();
        }
        for (int i = 0; i < block.size(); i++) {
            if (block.elementAt(i)instanceof PosRelationalPair) {
                relPair = (PosRelationalPair)block.elementAt(i);
                negBoolStructure.addIncoming(relPair.getNegClone());
            }
            else {
                negBoolStructure.addIncoming(generateFalsePartBoolStructure(block.elementAt(i)));
            }
        }
        return negBoolStructure;
    }

    public Vector normalizeCombinations(Vector combinationList, Structure p_Structure) {
        Vector normalizedCombinations = new Vector(0);
        for (int i = 0; i < combinationList.size(); i++) {
            Vector rawCombination = (Vector)combinationList.elementAt(i);
            if(flowReflected)
            	normalizedCombinations = concatVectors(normalizedCombinations, normalizeCombination2(rawCombination, p_Structure));
            else
            	normalizedCombinations = concatVectors(normalizedCombinations, normalizeCombination(rawCombination,p_Structure));
        }
        return normalizedCombinations;
    }

    public Vector normalizeCombination(Vector rawCombination, Structure p_Structure) {
      //  Vector elementNormalizedCombinations;
        Vector normalizedCombinations = new Vector(0);
       // Vector pairsOfElement;
        rawCombination = normalizeSameElementsIfBlock(rawCombination, p_Structure);
        Vector structureElements = p_Structure.getLnkElements();
        for (int i = 0; i < structureElements.size(); i++) {
            Element element = (Element)structureElements.elementAt(i);
            Vector pairsByElement = getValuesByElement(rawCombination, element);
            if (pairsByElement.size() > 0) {
                Vector valuesToBeIncluded = getValuesToBeIncluded(pairsByElement, element);
                normalizedCombinations = updateNormalizedCombinations(normalizedCombinations, valuesToBeIncluded,
                    this.m_BRParserDataManager.getBusinessObjectByName(this.m_FormalPolicy, element.getName()));
            }
        }
        return normalizedCombinations;
    }

    private Vector normalizeSameElementsIfBlock(Vector rawCombination, Structure p_Structure) {
		Vector newRawCombination= new Vector();

    	for (Object object : rawCombination) {
			if (object instanceof PosRelationalPair) {
				PosRelationalPair relationalPair = (PosRelationalPair) object;
				validateIfElementAlreadyExist(newRawCombination, cloneRelationPair(relationalPair));
			}
		}
    	updateElements(newRawCombination, p_Structure);

    	if (newRawCombination.size()>0){
    		for (int i=0;i<rawCombination.size();i++){
    			boolean exist=false;
        		for (int j=0;j<newRawCombination.size();j++){
        			PosRelationalPair obj=null;
    				PosRelationalPair obj1=null;
        			try {
        				obj=(PosRelationalPair)newRawCombination.elementAt(j);
        				obj1 = (PosRelationalPair)rawCombination.elementAt(i);

					} catch (Exception e) {
						// TODO: handle exception
					}
        			if (obj!= null && obj1!=null)
        				if (obj.getTheValue().equals(obj1.getTheValue())){
                			exist=true;
                		}
        		}
        		if (!exist)
        				newRawCombination.add(rawCombination.elementAt(i));

        	}
    	}
    	else{
    		/*for (int i=0;i<rawCombination.size();i++){
    			if (rawCombination.elementAt(i) instanceof PosRelationalPair)
    				newRawCombination.add(rawCombination.elementAt(i));
    			else{
    				if (((NegRelationalPair)rawCombination.elementAt(i)).getTheValue().equals("Other"))
    					newRawCombination.add(rawCombination.elementAt(i));
    			}
    		}*/
    		newRawCombination.addAll((Collection) rawCombination.clone());

    	}

    	
    	for (int i=0;i<rawCombination.size();i++){
    		if (!newRawCombination.contains(rawCombination.elementAt(i))){
    			newRawCombination.add(rawCombination.elementAt(i));
    		}
    	}
		return newRawCombination;
	}

	private PosRelationalPair cloneRelationPair(PosRelationalPair relationalPair) {
		PosRelationalPair clone= new PosRelationalPair();
		clone.setTheValue(new String(relationalPair.getTheValue()));
		clone.setTheObject(cloneBusinessObject(relationalPair.getTheObject()));
		return clone;
	}

	private BusinessObject cloneBusinessObject(BusinessObject theObject) {
		BusinessObject clone= new BusinessObject();
		if(theObject != null){
		if(theObject.getAttributes() != null)
		clone.setAttributes((Vector)theObject.getAttributes().clone());
		if(theObject.getComposedBy() != null)
		clone.setComposedBy((Vector)theObject.getComposedBy().clone());
		if(theObject.getComposes() != null)
		clone.setComposes((Vector)theObject.getComposes().clone());
		if(theObject.getModifiedBy() != null)
		clone.setModifiedBy((Vector)theObject.getModifiedBy().clone());
		if(theObject.getModifiedByVerbs() != null)
		clone.setModifiedByVerbs((Vector)theObject.getModifiedByVerbs().clone());
		if(theObject.getModifies() != null)
		clone.setModifies((Vector)theObject.getModifies().clone());
		if(theObject.getModifiesVerbs() != null)
		clone.setModifiesVerbs((Vector)theObject.getModifiesVerbs().clone());
		if(theObject.getName() != null)
		clone.setName(new String(theObject.getName()));
		if(theObject.getObjectsWithThisRole() != null)
		clone.setObjectsWithThisRole((Vector)theObject.getObjectsWithThisRole().clone());
		if(theObject.getRolesOfThisObject() != null)
		clone.setRolesOfThisObject((Vector)theObject.getRolesOfThisObject().clone());
		if(theObject.getSubClasses() != null)
		clone.setSubClasses((Vector)theObject.getSubClasses().clone());
		if(theObject.getSuperClasses() != null)
		clone.setSuperClasses((Vector)theObject.getSuperClasses().clone());
		if(theObject.getValues() != null)
		clone.setValues(cloneVector(theObject.getValues()));
		return clone;
		}
		else
			return theObject;
	}

	private Vector cloneVector(Vector values) {
		Vector newValues= new Vector();
		for (Object object : values) {
			newValues.addElement(new String((String)object));
		}
		return newValues;
	}

	private void updateElements(Vector rawCombination, Structure p_Structure) {
		Vector structureElements = p_Structure.getLnkElements();
        for (int i = 0; i < structureElements.size(); i++) {
            Element element = (Element)structureElements.elementAt(i);
            for (int j = 0; j < rawCombination.size(); j++) {
                RelationalPair relPair = (RelationalPair)rawCombination.elementAt(j);
                String objectName = relPair.getTheObject().getName();
                if (objectName.equals(element.getName())) {
                    List<EquivalenceClass> eqclasses=element.getEquivalenceClasses();//.contains(relPair.getTheValue());
                    boolean exist=false;
                    for (EquivalenceClass class1 : eqclasses) {
						if(!exist && class1.getValue().equals(relPair.getTheValue()))
							exist= true;
					}
                    if(!exist){
                    	EquivalenceClass eqClass = EquivalenceClassManager.INSTANCE.createEquivalenceClass(element);
                        eqClass.setValue(relPair.getTheValue());
                        eqClass.setLnkElement(element);
                        //cc element.getLnkEquivalenceClasses().addElement(eqClass);
                        element.addEquivalenceClass(eqClass);                       
                    }
                }
            }
        }

	}

	private void validateIfElementAlreadyExist(Vector newRawCombination, PosRelationalPair posRelationalPair) {
		BusinessObject bObj=posRelationalPair.getTheObject();
		String name=bObj.getName();
		String value= posRelationalPair.getTheValue();
		boolean changeExist=false;

		for (Object object : newRawCombination) {
			if (object instanceof PosRelationalPair) {
				PosRelationalPair existPosRelationalPair = (PosRelationalPair) object;
				BusinessObject businessObj=existPosRelationalPair.getTheObject();
				String existname=businessObj.getName();
				if(existname.trim().equals(name.trim())){
					String existValue=existPosRelationalPair.getTheValue();
					String newExistValue= existValue+" AND "+value;
					changeBusinessObjectValues(businessObj,existValue,value,newExistValue);
					existPosRelationalPair.setTheValue(newExistValue);
					changeExist=true;
				}
			}
		}
		if(!changeExist){
			newRawCombination.addElement(posRelationalPair);
		}

	}

	private void changeBusinessObjectValues(BusinessObject businessObj, String existValue, String value, String newExistValue) {
		Vector values= businessObj.getValues();
		values.remove(existValue);
		values.remove(value);
		values.addElement(newExistValue);

	}

	/////////////////////////////////////////////////////////7
    public Vector normalizeCombination2(Vector rawCombination, Structure p_Structure) {
        Vector elementNormalizedCombinations;
        Vector normalizedCombinations = new Vector(0);
        Vector pairsOfElement;
        Vector structureElements =  p_Structure.getLnkElements();
        for (int i = 0; i < /*structureElements*/rawCombination.size(); i++) {
            Element element =null; //(Element)structureElements.elementAt(i);
        	RelationalPair relPair =(RelationalPair)rawCombination.elementAt(i);
            Vector pairsByElement=new Vector();
            element= getValuesByElement2(structureElements, relPair,pairsByElement, element);//getValuesByElement(rawCombination, element);
            if (pairsByElement.size() > 0) {
            	rawCombination.remove(i);
            	i--;
                Vector valuesToBeIncluded = getValuesToBeIncluded(pairsByElement, element);
                normalizedCombinations = updateNormalizedCombinations(normalizedCombinations, valuesToBeIncluded,
                    this.m_BRParserDataManager.getBusinessObjectByName(this.m_FormalPolicy, element.getName()));
            }
        }
        return normalizedCombinations;
    }
    private Element getValuesByElement2(Vector structureElements, RelationalPair relPair, Vector pairsByElement, Element element) {
    	//Vector pairsByElement = new Vector(0);
    	Element selected=null;
        for (int i = 0; i < structureElements.size(); i++) {
        	element =(Element)structureElements.elementAt(i);
            String objectName = relPair.getTheObject().getName();
            if (objectName.equals(element.getName())) {
               // rawCombination.remove(i);
                pairsByElement.addElement(relPair);
                selected=element;
               // return pairsByElement;
               // i--;
            }
        }
        return selected;
	}
///////////////////////////////////7777
    @SuppressWarnings("unchecked")
	public Vector updateNormalizedCombinations(Vector normalizedCombinations, Vector valuesToBeIncluded,
        BusinessObject theObject) {
            Vector relPairsToInclude = new Vector(0);
            Vector update = new Vector(0);
            for (int i = 0; i < valuesToBeIncluded.size(); i++) {
                relPairsToInclude.addElement(new PosRelationalPair(theObject, (String)valuesToBeIncluded.elementAt(i)));
            }
            if (normalizedCombinations.size() == 0) {
                for (int i = 0; i < relPairsToInclude.size(); i++) {
                    normalizedCombinations.addElement(new Vector());
                    ((Vector)normalizedCombinations.lastElement()).addElement(relPairsToInclude.elementAt(i));
                }
            }
            else {
                for (int i = 0; i < normalizedCombinations.size(); i++) {
                    Vector combination = (Vector)normalizedCombinations.elementAt(i);
                    for (int j = 0; j < relPairsToInclude.size(); j++) {
                        update.addElement((Vector)combination.clone());
                        ((Vector)update.lastElement()).addElement(relPairsToInclude.elementAt(j));
                    }
                }
                normalizedCombinations = (Vector)update.clone();
            }
            return normalizedCombinations;
    }

    @SuppressWarnings("unchecked")
	public Vector getValuesByElement(Vector rawCombination, Element element) {
        Vector pairsByElement = new Vector(0);
        for (int i = 0; i < rawCombination.size(); i++) {
            RelationalPair relPair = (RelationalPair)rawCombination.elementAt(i);
            String objectName = relPair.getTheObject().getName();
            if (objectName.equalsIgnoreCase(element.getName())) {
            	rawCombination.remove(i);
                pairsByElement.addElement(relPair);
                i--;
            }
        }
        return pairsByElement;
    }

    @SuppressWarnings("unchecked")
	public Vector getValuesToBeIncluded(Vector pairsByElement, Element element) {
        Vector valuesToBeIncluded = new Vector(0);
        Vector valuesByElement = new Vector(0);
        Vector elementEqClasses =  new Vector(0);
        elementEqClasses.addAll(element.getEquivalenceClasses());//cc element.getLnkEquivalenceClasses();
        EquivalenceClass eqClass;
        for (int i = 0; i < pairsByElement.size(); i++) {
            RelationalPair pair = (RelationalPair)pairsByElement.elementAt(i);
            if (pair.getClassName().equals("PosRelationalPair")) {
                valuesToBeIncluded.addElement(((RelationalPair)pair).getTheValue());
                return valuesToBeIncluded;
            }
        }

        for (int i = 0; i < pairsByElement.size(); i++) {
            RelationalPair pair = (RelationalPair)pairsByElement.elementAt(i);
            valuesByElement.addElement(pair.getTheValue());
        }
        /*for (int i = 0; i < elementEqClasses.size(); i++) {
            eqClass = (EquivalenceClass)elementEqClasses.elementAt(i);//cc elementAt(i);
            if (!valuesByElement.contains(eqClass.getValue())) {
                valuesToBeIncluded.addElement(eqClass.getValue());
            }
        }*/
        if (!flowReflected /*cc && valuesToBeIncluded.size() == 0*/) {
            this.createOthersValue(element);
            elementEqClasses.removeAllElements();
            elementEqClasses.addAll(element.getEquivalenceClasses());
            eqClass = (EquivalenceClass)elementEqClasses.lastElement();
            if (eqClass.getValue().equals(BusinessRules.OTHER_ENGLISH_WORD) ||
                eqClass.getValue().equals(BusinessRules.OTHER_GERMAN_WORD)) {
                    valuesToBeIncluded.addElement(new String(eqClass.getValue()));
            }
        }
        return valuesToBeIncluded;
    }


    @SuppressWarnings("unchecked")
	public Vector groupByEffects(Vector combinations, Combination baseCombination) {
        Vector groupedCombinations = new Vector(0);
        groupedCombinations.addElement(baseCombination);
        List baseCombinationEffects = baseCombination.getEffects();
        int numOfBaseCombinationEffects = baseCombinationEffects.size();
        int combinationCounter = 0;
        while (combinationCounter < combinations.size()) {
            Combination combination = (Combination)combinations.elementAt(combinationCounter);
            List combinationEffects = combination.getEffects();
            boolean addToGroup = true;
            if (combinationEffects.size() == baseCombinationEffects.size()) {
                for (int i = 0; i < numOfBaseCombinationEffects; i++) {
                    if (!combinationEffects.contains(baseCombinationEffects.get(i))) {
                        addToGroup = false;
                        break;
                    }
                }
            }
            else {
                addToGroup = false;
            }
            if (addToGroup) {
                groupedCombinations.addElement(combinations.remove(combinationCounter));
                combinationCounter--;
            }
            combinationCounter++;
        }
        return groupedCombinations;
    }

    @SuppressWarnings("unchecked")
	public Vector buildSearchMergingPattern(Vector groupOfCombinations, Combination combination,
        EquivalenceClass mergingEquivalenceClass) {
            Vector searchMergingPattern = new Vector(0);
            EquivalenceClass equivalenceClass = null;
            Element selectedElement = mergingEquivalenceClass.getLnkElement();
            if (combination.getCombinations().size() > 0) {
                int numOfChildrenCombinations = combination.getCombinations().size();
                int numOfEquivalenceClassesInPartOfSearchMergingPattern = 0;
                Combination childCombination = null;
                Vector partOfSearchMergingPattern = null;
                int numOfEquivalenceClassesInCombination = combination.getEquivalenceClasses().size();
                for (int i = 0; i < numOfEquivalenceClassesInCombination; i++) {
                    equivalenceClass = (EquivalenceClass)combination.getEquivalenceClasses().get(i);
                    //// WITHIN OTHER ELEMENTS
                    if (equivalenceClass.getLnkElement() != selectedElement) {
                        searchMergingPattern.addElement(equivalenceClass);
                    }
                }
                for (int i = 0; i < numOfChildrenCombinations; i++) {
                    childCombination = (Combination)combination.getCombinations().get(i);
                    partOfSearchMergingPattern = buildSearchMergingPattern(groupOfCombinations, childCombination,
                        mergingEquivalenceClass);
                    numOfEquivalenceClassesInPartOfSearchMergingPattern = partOfSearchMergingPattern.size();
                    for (int j = 0; j < numOfEquivalenceClassesInPartOfSearchMergingPattern; j++) {
                        equivalenceClass = (EquivalenceClass)partOfSearchMergingPattern.elementAt(j);
                        searchMergingPattern.addElement(equivalenceClass);
                    }
                }
                return searchMergingPattern;
            }
            else {
                int numOfEquivalenceClassesInCombination = 0;
                numOfEquivalenceClassesInCombination = combination.getEquivalenceClasses().size();
                for (int i = 0; i < numOfEquivalenceClassesInCombination; i++) {
                    equivalenceClass = (EquivalenceClass)combination.getEquivalenceClasses().get(i);
                    //// WITHIN OTHER ELEMENTS
                    if (equivalenceClass.getLnkElement() != selectedElement) {
                        searchMergingPattern.addElement(equivalenceClass);
                    }
                }
                return searchMergingPattern;
            }
    }

    @SuppressWarnings("unchecked")
	public Vector searchForMergingCombinations(Vector groupOfCombinations, EquivalenceClass equivalenceClass,
        Combination selectedCombination) {
            Vector mergingCombinations = new Vector(0);
            int numOfCombinations = groupOfCombinations.size();
            Vector searchMergingPattern = buildSearchMergingPattern(groupOfCombinations,
                selectedCombination, equivalenceClass);
            searchMergingPattern.addElement(equivalenceClass);
            int numOfSearchEquivalenceClasses = searchMergingPattern.size();
            for (int i = 0; i < numOfCombinations; i++) {
                Combination combination = (Combination)groupOfCombinations.elementAt(i);
                if (selectedCombination != combination) {
                    if (isAMergingCombination(combination, searchMergingPattern)) {
                        mergingCombinations.addElement(combination);
                    }
                }
            }
            return mergingCombinations;
    }

    public boolean AreEquivalenceClassesNotInSearchPattern(Combination combination, Vector searchMergingPattern) {
        EquivalenceClass equivalenceClassInCombination = null;
        if (combination.getCombinations().size() > 0) {
            int numOfEquivalenceClassesInCombination = combination.getEquivalenceClasses().size();
            for (int i = 0; i < numOfEquivalenceClassesInCombination; i++) {
                equivalenceClassInCombination = (EquivalenceClass)combination.getEquivalenceClasses().get(i);
                if (!searchMergingPattern.contains(equivalenceClassInCombination)) {
                    return true;
                }
            }
            int numOfChildrenCombinations = combination.getCombinations().size();
            for (int i = 0; i < numOfChildrenCombinations; i++) {
                Combination childCombination = (Combination)combination.getCombinations().get(i);
                if (AreEquivalenceClassesNotInSearchPattern(childCombination, searchMergingPattern)) {
                    return true;
                }
            }
            return false;
        }
        else {
            int numOfEquivalenceClassesInCombination = combination.getEquivalenceClasses().size();
            for (int i = 0; i < numOfEquivalenceClassesInCombination; i++) {
                equivalenceClassInCombination = (EquivalenceClass)combination.getEquivalenceClasses().get(i);
                if (!searchMergingPattern.contains(equivalenceClassInCombination)) {
                    return true;
                }
            }
            return false;
        }
    }

    public boolean isAMergingCombination(Combination combination, Vector searchMergingPattern) {
        EquivalenceClass equivalenceClassInSearchPattern = null;
        EquivalenceClass equivalenceClassInCombination = null;
        int numOfEquivalenceClassesInSearchMergingPattern = searchMergingPattern.size();
        int numOfEquivalenceClassesInCombination = combination.getEquivalenceClasses().size();
        for (int i = 0; i < numOfEquivalenceClassesInSearchMergingPattern; i++) {
            equivalenceClassInSearchPattern = (EquivalenceClass)searchMergingPattern.elementAt(i);
            if (!combination.contains(equivalenceClassInSearchPattern)) {
                return false;
            }
        }
        if (AreEquivalenceClassesNotInSearchPattern(combination, searchMergingPattern)) {
            return false;
        }
        else {
            return true;
        }
    }

    public void assignEffectsToCombinations(Dependency newDependency, Structure structure, Vector combiPairs) {
        Vector rawCombination;
        Vector dependencyCombinations = newDependency.getLnkCombinations();
        int numOfDependencyCombinations = dependencyCombinations.size();
        BusinessAction action = null;
        //BusinessAction elseAction = null;
        Combination dependencyCombination;
        RelationalPair relPair;
        CombinationsBAPair combiPair;
        Effect effect;
        Element element;
        EquivalenceClass eqClass;
        String value;
        boolean combinationMatch = true;
        int numOfCombiPairs = combiPairs.size();
        for (int i = 0; i < numOfCombiPairs; i++) {
            combiPair = (CombinationsBAPair)combiPairs.elementAt(i);
            rawCombination = combiPair.getCombination();
//HCanedo_21032006_begin
            Vector actions=combiPair.getAction();
            //action = combiPair.getAction();
            if (actions != null) {//action
            	for (Iterator iter = actions.iterator(); iter.hasNext();) {
					 action = (BusinessAction) iter.next();
					 effect = findEffect(action.getDescription(), (List)structure.getEffects());
					 for (int j = 0; j < numOfDependencyCombinations; j++) {
						 combinationMatch = true;
						 dependencyCombination = (Combination)dependencyCombinations.elementAt(j);
						 int rawCombinationSize = rawCombination.size();
						 for (int z = 0; z < rawCombinationSize; z++) {
							 relPair = (RelationalPair)rawCombination.elementAt(z);
							 value = relPair.getTheValue();
							 element = findElement(relPair.getTheObject().getName(), newDependency.getLnkElements());
							 eqClass = findEquivalenceClass(value, element.getEquivalenceClasses());
                 		      if (!dependencyCombination.contains(eqClass)) {
                 		    	  combinationMatch = false;
                 		    	  break;
                 		      }
						 }
						 if (combinationMatch) {
							 boolean change=addEffectIfNotPresent(dependencyCombination, effect);
							 if(change){
								 changeCombinationStateAndRiskLevel(dependencyCombination, effect);
							 }
							 break;
						 }
					 }
            	}
//HCanedo_21032006_end
            }
        }
    }

    public Combination selectCombination(Vector eqClasses, Vector combinations) {
        Combination combination;
        boolean allFound = true;
        int numCombinations = combinations.size();
        int numEqClasses = eqClasses.size();
        for (int i = 0; i < numCombinations; i++) {
            allFound = true;
            combination = (Combination)combinations.elementAt(i);
            for (int j = 0; j < numEqClasses; j++) {
                if (!(combination.contains((EquivalenceClass)eqClasses.elementAt(j)))) {
                    allFound = false;
                }
            }
            if (allFound) {
                return combination;
            }
        }
        return null;
    }
    //fcastro_20092004_begin

	public Combination createCombination(CombinationsBAPair combiPair,Structure p_Structure,Dependency newDependency){
		Combination newCombination;
        Effect effect;

		newCombination = new Combination();
      	newCombination.setOriginType(Combination.Origin.PERMUTATION);//_TYPE_MANUAL);
      	newCombination.setDescription("");
      	newCombination.setState(3);
        newCombination.setRiskLevel(0);


        Vector rawCombination = combiPair.getCombination();
        int combinationSize = rawCombination.size();
        for(int i=0;i<combinationSize;i++){
            RelationalPair relPair = (RelationalPair)rawCombination.elementAt(i);
        	Element element = findElement(relPair.getTheObject().getName(),p_Structure.getLnkElements());
			EquivalenceClass eqClass = findEquivalenceClass(relPair.getTheValue(),element.getEquivalenceClasses());
            CombinationManager.INSTANCE.addEquivalenceClassToCombination(eqClass,newCombination);
        }
        return newCombination;
    }

    public Combination checkIfAlreadyPresent(Combination newCombination,Vector existingCombinations){
        Combination existingCombination;
      //  EquivalenceClass eqClass;
        int numOfExistingCombinations = existingCombinations.size();

        for(int i = 0;i<numOfExistingCombinations;i++){
            existingCombination = (Combination)existingCombinations.elementAt(i);
            if(CombinationManager.INSTANCE.areEqual(newCombination,existingCombination)){
                return existingCombination;
            }
        }
        return null;
    }

	@SuppressWarnings("unchecked")
	public void generateCombinations(Dependency newDependency,ApplicationSetting p_ApplicationSetting,Structure p_Structure,RawDependency p_RawDependency){
	Combination alreadyPresent = null;
    newDependency.setLnkCombinations(new Vector(0));
	Vector rawCombinations = p_RawDependency.getCombiPairs();
    int numOfCombinationsToCreate = p_RawDependency.getCombiPairs().size();
    for(int i=0;i<numOfCombinationsToCreate;i++){
		CombinationsBAPair rawCombination = (CombinationsBAPair)rawCombinations.elementAt(i);
        Combination newCombination = createCombination(rawCombination,p_Structure,newDependency);
//HCanedo_21032006_begin
        for (Iterator iter = rawCombination.getAction().iterator(); iter.hasNext();) {
			BusinessAction action = (BusinessAction) iter.next();
			Effect effect = findEffect(/*rawCombination.getAction().getDescription()*/action.getDescription(),p_Structure.getEffects());
			alreadyPresent = checkIfAlreadyPresent(newCombination,newDependency.getLnkCombinations());
			if(alreadyPresent==null ){
				newCombination.addEffect(effect);
				changeCombinationStateAndRiskLevel(newCombination, effect);
					newCombination.setId(CombinationManager.INSTANCE.getNextId(newDependency));
					newCombination.setDependency(newDependency);
					newDependency.getLnkCombinations().addElement(newCombination);
			}
			else{
				boolean change=addEffectIfNotPresent(alreadyPresent,effect);
				if(change){
					 changeCombinationStateAndRiskLevel(alreadyPresent, effect);
				 }
			}
        }
//HCanedo_21032006_end
   	}
    if(newDependency.getLnkCombinations().size()>p_ApplicationSetting.getM_MaxNumOfCombinations()){
    	TestObject selectedTestObject= CMApplication.frame.getTreeWorkspaceView().getCurrentTestObject();
    	if(p_ApplicationSetting.getM_MaxNumOfCombinations()!= BusinessRules.MAX_NUMBER_OF_COMBINATIONS){
    		int i=JOptionPane.showConfirmDialog(CMApplication.frame, CMMessages.getString("BR_INCREASE_TO_MAXNUMBER_OF_COMBINATIONS"),selectedTestObject.getName()+" - "+CMMessages.getString("MENU_ITEM_GENERATE_COMBINATIONS"),JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
    		if(i==JOptionPane.YES_OPTION)
    			p_ApplicationSetting.setM_MaxNumOfCombinations(BusinessRules.MAX_NUMBER_OF_COMBINATIONS);
    	}
    	if(newDependency.getLnkCombinations().size()>p_ApplicationSetting.getM_MaxNumOfCombinations()){
    		int i=JOptionPane.showConfirmDialog(CMApplication.frame, CMMessages.getString("BR_GENERATE_BY_ALLPAIRS"),selectedTestObject.getName()+" - "+CMMessages.getString("MENU_ITEM_GENERATE_COMBINATIONS"),JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
        	if(i==JOptionPane.YES_OPTION){
        		CMCompoundEdit ce = new CMCompoundEdit();
        		AllPairsGeneratorUpdater updater = new AllPairsGeneratorUpdater(newDependency);
        		updater.updateCombinationsByAllPairs(newDependency,BusinessRules.MAX_NUMBER_OF_COMBINATIONS,p_Structure, ce);
        		CMUndoMediator.getInstance().doEdit(ce);
        	}
    		if(newDependency.getLnkCombinations().size()>p_ApplicationSetting.getM_MaxNumOfCombinations()){
    			Logger.getLogger(this.getClass()).debug("Exceeded max number of combinations");
    			newDependency.setLnkCombinations(new Vector(0));
    		}
    	}

    }



    }
    private boolean isCombinationWithNegativeEQ(Combination p_Combination) {
		List<EquivalenceClass> eqclases= p_Combination.getEquivalenceClasses();
		for (Iterator iter = eqclases.iterator(); iter.hasNext();) {
			EquivalenceClass eq = (EquivalenceClass) iter.next();
			if(eq.getState() != State.POSITIVE.intValue()){
				return true;
			}
		}
		return false;
	}

	//fcastro_20092004_end


    @SuppressWarnings("unchecked")
	public void getEqClassesForDependency(Vector eqClassesForDependency, RawDependency rawDependency, Structure structure) {
       // Vector combination;
        Vector elementList = structure.getLnkElements();
        BusinessObject businessObject;
        Element element;
        EquivalenceClass eqClass;
        RelationalPair relPair;
        String value;
        Vector combiPairs = rawDependency.getCombiPairs();
        Vector rawCombination;
        CombinationsBAPair combiPair;
        int numCombiPairs = combiPairs.size();
        for (int i = 0; i < numCombiPairs; i++) {
            combiPair = (CombinationsBAPair)combiPairs.elementAt(i);
            rawCombination = combiPair.getCombination();
            int rawCombinationSize = rawCombination.size();
            for (int j = 0; j < rawCombinationSize; j++) {
                relPair = (RelationalPair)rawCombination.elementAt(j);
                businessObject = relPair.getTheObject();
                element = this.findElement(businessObject.getName(), elementList);
                value = relPair.getTheValue();
                eqClass = this.findEquivalenceClass(value, element.getEquivalenceClasses());
                if (!eqClassesForDependency.contains(eqClass)) {
                    eqClassesForDependency.addElement(eqClass);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
	public Vector concatVectors(Vector head, Vector tail) {
        for (int i = 0; i < tail.size(); i++) {
            head.addElement(tail.elementAt(i));
        }
        return head;
    }

    public Vector makeCollectorCopy(Vector collector) {
        Vector copy = new Vector(0);
        for (int i = 0; i < collector.size(); i++) {
            Vector combinationCopy = new Vector();
            Vector combination = (Vector)collector.elementAt(i);
            for (int j = 0; j < combination.size(); j++) {
                combinationCopy.addElement(((RelationalPair)combination.elementAt(j)));
            }
            copy.addElement(combinationCopy);
        }
        return copy;
    }

    @SuppressWarnings("unchecked")
	public void groupCombiPairsByElementCount(Vector combiPairsList, Vector groupedCombiPairsList) {
        Vector groupedCombiPairs;
        int maxElementCount = getMaxElementCount(combiPairsList);
        CombinationsBAPair combiPair;
        for (int i = 2; i <= maxElementCount; i++) {
            groupedCombiPairs = new Vector();
            for (int j = 0; j < combiPairsList.size(); j++) {
                combiPair = (CombinationsBAPair)combiPairsList.elementAt(j);
                if (combiPair.getDifferentObjectsCount() == i) {
                    groupedCombiPairs.addElement(combiPair);
                }
            }
            groupedCombiPairsList.addElement(groupedCombiPairs);
        }
    }

    public void generateRawDependenciesList(Vector groupedCombiPairsList, Vector rawDependenciesList) {
        for (int i = 0; i < groupedCombiPairsList.size(); i++) {
            appendRawDependencies((Vector)groupedCombiPairsList.elementAt(i), i + 2, rawDependenciesList);
        }
    }

    @SuppressWarnings("unchecked")
	public void appendRawDependencies(Vector uniSizeCombiPairList, int combinationsSize, Vector rawDependenciesList) {
        int i = 0;
        CombinationsBAPair combiPair;
        RawDependency newRawDependency;
        while (i < uniSizeCombiPairList.size()) {
            combiPair = (CombinationsBAPair)uniSizeCombiPairList.elementAt(i);
            while (combiPair.isUsed() && i < (uniSizeCombiPairList.size() - 1)) {
                i++;
                combiPair = (CombinationsBAPair)uniSizeCombiPairList.elementAt(i);
            }
            if (!combiPair.isUsed()) {
                combiPair.setUsed(true);
                newRawDependency = new RawDependency(combiPair);
                for (int j = i + 1; j < uniSizeCombiPairList.size(); j++) {
                    combiPair = (CombinationsBAPair)uniSizeCombiPairList.elementAt(j);
                    if (!flowReflected && (!combiPair.isUsed()) && (newRawDependency.sameObjects(combiPair))) {
                        combiPair.setUsed(true);
                        newRawDependency.getCombiPairs().addElement(combiPair);
                    }
                }
                rawDependenciesList.addElement(newRawDependency);
            }
            i++;
        }
    }

    public int getMaxElementCount(Vector combiPairsList) {
        int max = 0;
        CombinationsBAPair combiPair;
        for (int i = 0; i < combiPairsList.size(); i++) {
            combiPair = (CombinationsBAPair)combiPairsList.elementAt(i);
            if (combiPair.getDifferentObjectsCount() > max) {
                max = combiPair.getDifferentObjectsCount();
            }
        }
        return max;
    }

    public String getDescriptionBasedOnExistingElements(Dependency dependency) {
        Vector dependentElements = dependency.getLnkElements();
        StringBuffer descriptionView = new StringBuffer();
        int numElements = dependentElements.size();
        for (int i = 0; i < numElements; i++) {
            Element element = (Element)dependentElements.elementAt(i);
            descriptionView.append(element.getName());
            if (i < (numElements - 1)) {
                descriptionView.append(" | ");
            }
        }
        descriptionView.insert(0, CMMessages.getString("LABEL_DEPENDENT_ELEMENTS_DESCRIPTION"));
        return descriptionView.toString();
    }

    public Effect findEffect(String description, List effectsInStructure) {
        Effect effect;
        for (int effects = 0; effects < effectsInStructure.size(); effects++) {
            effect = (Effect)effectsInStructure.get(effects);
            if (effect.getDescription().equals(description)) {
                return effect;
            }
        }
        return null;
    }

    public Vector findElementsForDependency(Vector businessObjects, Vector elements) {
        BusinessObject businessObject;
        Element element;
        Vector elementsForDependency = new Vector();
        for (int i = 0; i < businessObjects.size(); i++) {
            businessObject = (BusinessObject)businessObjects.elementAt(i);
            element = findElement(businessObject.getName(), elements);
            if (element != null) {
                elementsForDependency.addElement(element);
            }
        }
        return elementsForDependency;
    }

    public Element findElement(String name, Vector elementsList) {
        Element element;
        for (int elements = 0; elements < elementsList.size(); elements++) {
            element = (Element)elementsList.elementAt(elements);
            if (name.equals(element.getName())) {
                return element;
            }
        }
        return null;
    }

    public EquivalenceClass findEquivalenceClass(String value, List equivalenceClasses) {
        EquivalenceClass eqClass;
        for (int i = 0; i < equivalenceClasses.size(); i++) {
            eqClass = (EquivalenceClass)equivalenceClasses.get(i);
            if (eqClass.getValue().equals(value)) {
                return eqClass;
            }
        }
        return null;
    }

    public boolean addEffectIfNotPresent(CMEffectsBean effectBean, Effect newEffect) {
        Effect effect;
        for (int i = 0; i < effectBean.getEffects().size(); i++) {
            effect = effectBean.getEffects().get(i);
            if (effect.getDescription().equals(newEffect.getDescription())) {
                return false;
            }
        }
        effectBean.addEffect(newEffect);
        return true;
    }

    private BRParseProcessManager getM_BRParseProcessManager() {
        return BRParseProcessManager.INSTANCE;
    }



    public BREditorManager getM_BREditorManager() {
        return m_BREditorManager;
    }

    public void setM_BREditorManager(BREditorManager m_BREditorManager) {
        this.m_BREditorManager = m_BREditorManager;
    }

    public int getSourceSyntax() {
        return sourceSyntax;
    }

    public void setSourceSyntax(int sourceSyntax) {
        this.sourceSyntax = sourceSyntax;
    }

    public ToolVendorManager getM_ToolVendorManager() {
        return m_ToolVendorManager;
    }

    public void setM_ToolVendorManager(ToolVendorManager m_ToolVendorManager) {
        this.m_ToolVendorManager = m_ToolVendorManager;
    }

    /**
     * @clientCardinality 1
     * @supplierCardinality 1
     */
    private BREditorManager m_BREditorManager;

    public int getConditionId() {
        return conditionId;
    }

    public void setConditionId(int conditionId) {
        this.conditionId = conditionId;
    }

    private void deleteOtherFromFormalPolicy(FormalPolicy p_Policy){
    	Vector inferenceRulesChanged= new Vector();
    	for (Iterator iter = p_Policy.getM_InferenceRules().iterator(); iter.hasNext();) {
			InferenceRule rule = (InferenceRule) iter.next();
				Vector newCreatedRules = new Vector();
				convertRule(rule,null,newCreatedRules);
				inferenceRulesChanged.addAll(newCreatedRules);

		}
    	p_Policy.setM_InferenceRules(inferenceRulesChanged);
    }
	private InferenceRule createCloneInferenceRule(InferenceRule father, Object element) {
		if(father != null){
		InferenceRule clon= new InferenceRule();
		clon.setBooleanStructure(father.getBooleanStructure());
		clon.setBusinessObjectsCount(father.getBusinessObjectsCount());
		clon.setBusinessObjectsInvolved(father.getBusinessObjectsInvolved());
		clon.setCombinationsBActionPairs(father.getCombinationsBActionPairs());
		clon.setFalsePart(null);
		clon.setTruePart(element);
		return clon;
		}
		else
			return (InferenceRule)element;
	}

	private InferenceRule convertRule(InferenceRule rule,InferenceRule father ,Vector convertedRules) {
    	InferenceRule possitive= null;
    	InferenceRule negative= null;
    	//Caso Base Arbol Vacio
    	if(rule== null)
    		return null;
    	//Caso Base Un Solo Nodo
    	if(!(rule.getTruePart()instanceof InferenceRule) &&!(rule.getFalsePart()instanceof InferenceRule)){
    		convertedRules.add(rule);
    		return rule;
    	}
    	else{   Vector possitivesWays= new Vector();
    				if(rule.getTruePart() instanceof InferenceRule)
    					possitive=convertRule((InferenceRule) rule.getTruePart(),rule,possitivesWays);
    				else if(rule.getTruePart() != null)
    					possitivesWays.add(rule.getTruePart());
    				Vector convertedRules2= new Vector();
    				for (Iterator iter = possitivesWays.iterator(); iter.hasNext();) {
    					Object element =  iter.next();
    					InferenceRule clone=createCloneInferenceRule(rule,element);
    					convertedRules2.add(clone);
    				}
    				possitivesWays= convertedRules2;

        		Vector negativeWays= new Vector();
        		if(rule.getFalsePart() instanceof InferenceRule)
        			negative=convertRule((InferenceRule) rule.getFalsePart(),rule,negativeWays);
        		else if(rule.getFalsePart() != null)
        			negativeWays.add(rule.getFalsePart());

        		convertedRules.addAll(negativeWays);
        		convertedRules.addAll(possitivesWays);
        		return rule;

        }
	}
	/**
     * @clientCardinality 1
     * @supplierCardinality 1
     */

    public void changeEquivalenceClassStateAndRiskLevel(EquivalenceClass eq, Effect effect){
    	String eqState= eq.getStateName();
    	String effectState= effect.getStateName();
    	boolean changeState= compareLevelsOfStates(eqState, effectState);
    	if(changeState){
    		eq.setState( State.getStateByName(effectState));
    	}
    	if(effect.getRiskLevel() > eq.getRiskLevel()){
    		eq.setRiskLevel(effect.getRiskLevel());
    	}
    	//TODO Add a logger to show a report if changed
    }
    private boolean compareLevelsOfStates(String originalState, String toChangeState) {
    	String[] values={"I","F","-","+"};
    	int gradeofOriginal=0;
    	int gradeofChange=0;
		for (int i = 0; i < values.length; i++) {
			if(values[i].equals(originalState))
				gradeofOriginal=i;
			if(values[i].equals(toChangeState))
				gradeofChange=i;
		}
		return gradeofOriginal > gradeofChange;
	}

	public void changeCombinationStateAndRiskLevel(Combination combi, Effect effect){
		String combiState= combi.getStateName();
    	String effectState= effect.getStateName();
    	boolean changeState= compareLevelsOfStates(combiState, effectState);
    	if(changeState){
    		combi.setState( State.getStateByName(effectState));
    	}
    	if(effect.getRiskLevel() > combi.getRiskLevel()){
    		combi.setRiskLevel(effect.getRiskLevel());
    	}
    	//TODO Add a logger to show a report if changed
    }



	public void validateDependencies(Structure structure) {
		Vector<Dependency> dependenies= structure.getLnkDependencies();
		Vector<Combination> toDeleteCombinations= new Vector<Combination>();
		Vector<Dependency> toDeleteDependencies= new Vector<Dependency>();
		for (Iterator iter = dependenies.iterator(); iter.hasNext();) {
			Dependency dependency = (Dependency) iter.next();
			Vector<Combination> combinations= dependency.getLnkCombinations();
			for (Combination combination : combinations) {
				if(isCombinationWithNegativeEQ(combination)){
					toDeleteCombinations.addElement(combination);
				}
				else{
					modifyRiskLevelByEquivalenceClass(combination);
				}
			}
			boolean success=combinations.removeAll(toDeleteCombinations);
			if(success){
				if(combinations.size()<=0){
					toDeleteDependencies.add(dependency);
				}
				else{
					renameCombinations(combinations, dependency);
				}
			}
		}
	}

	private void modifyRiskLevelByEquivalenceClass(Combination combination) {
		List<EquivalenceClass> eqClasses=combination.getEquivalenceClasses();
		for (EquivalenceClass eqClass : eqClasses) {
			if(eqClass.getRiskLevel() > combination.getRiskLevel()){
				combination.setRiskLevel(eqClass.getRiskLevel());
			}
		}
	}


	private void renameCombinations(Vector<Combination> combinations, Dependency dependency) {

		dependency.setLnkCombinations(new Vector<Combination>());
		Vector<Combination> renamedCombinations= new Vector<Combination>();
		for (Combination combination : combinations) {
			combination.setId(CombinationManager.INSTANCE.getNextId(dependency));
			renamedCombinations.addElement(combination);
		}
		dependency.setLnkCombinations(renamedCombinations);
	}
}
