package bi.controller;

import java.io.Reader;
import java.io.StringReader;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import model.ExpectedResult;
import model.Requirement;
import model.State;
import model.brmodel.AggregationFactRule;
import model.brmodel.AndBlock;
import model.brmodel.AssociationFactRule;
import model.brmodel.AttributeFactRule;
import model.brmodel.BooleanBlock;
import model.brmodel.BusinessAction;
import model.brmodel.BusinessObject;
import model.brmodel.FormalPolicy;
import model.brmodel.GeneralizationFactRule;
import model.brmodel.InferenceRule;
import model.brmodel.MathematicalCalculationRule;
import model.brmodel.OrBlock;
import model.brmodel.PosRelationalPair;
import model.brmodel.RelationalPair;
import model.brmodel.RoleFactRule;
import bi.controller.brparser.requirementsresultparser.ParseException;
import bi.controller.brparser.requirementsresultparser.RequirementResultValues;




public class BRParserDataManager {
     public FormalPolicy createFormalPolicy() {
        return new FormalPolicy();
    }

    public void addMathematicalCalculationRule(FormalPolicy formalPolicy, String subject,String expression) {
		BusinessObject ruleSubject;
		ruleSubject=this.insertObjectsFromChain(formalPolicy,null,subject);
		assignValue(ruleSubject, expression);
        MathematicalCalculationRule mathRule=new MathematicalCalculationRule();
		mathRule.setSubject(ruleSubject);
        mathRule.setSubjectExpression(subject);
        mathRule.setMathExpression(expression);
        formalPolicy.addMathematicalCalculationRule(mathRule);
        
    }

    public BusinessObject insertObjectsFromChain(FormalPolicy p_FormalPolicy,BusinessObject father,String objectChain) {
		String restOfChain;
        String objectName;
        int indexOfDot=-1;
		BusinessObject businessObject;
        if(objectChain==null){
            return father;
        }
        else{
            indexOfDot=objectChain.indexOf('.');
			if(indexOfDot!=-1){
				objectName=objectChain.substring(0,indexOfDot);
                restOfChain=objectChain.substring((indexOfDot+1),objectChain.length());
            }
            else{
                objectName=objectChain;
                restOfChain=null;
            }
            businessObject=this.getBusinessObjectByName(p_FormalPolicy,objectName);
            if(businessObject==null){
            	businessObject=new BusinessObject();
            	businessObject.setName(objectName);
                p_FormalPolicy.addBusinessObject(businessObject);
            }
            if(father!=null){
				this.addAttributeToBusinessObject(father,businessObject);
            }
            return insertObjectsFromChain(p_FormalPolicy,businessObject,restOfChain);

        }
    }

    public BusinessObject insertObjectsFromNative(FormalPolicy p_FormalPolicy,String object) {

		BusinessObject businessObject;
        businessObject=this.getBusinessObjectByName(p_FormalPolicy,object);
        if(businessObject==null){
           	businessObject=new BusinessObject();
           	businessObject.setName(object);
           p_FormalPolicy.addBusinessObject(businessObject);
        }
       return businessObject;
    }
    public BusinessObject getBusinessObjectByName(FormalPolicy p_FormalPolicy,String name) {
        List<BusinessObject> p_BusinessObjects = p_FormalPolicy.getM_BusinessObjects();
        for(int i=0;i<p_BusinessObjects.size();i++){
			if(name.equals(((BusinessObject)p_BusinessObjects.get(i)).getName())){
				return (BusinessObject)p_BusinessObjects.get(i);
            }
        }
        return null;
    }


    public void addAttributeToBusinessObject(BusinessObject father,BusinessObject attribute){
        Vector attributes = father.getAttributes();
		for(int i=0;i<attributes.size();i++){
            if(attribute.getName().equals(((BusinessObject)attributes.elementAt(i)).getName())){
				return;
            }
        }
        attributes.addElement(attribute);
    }

    public void addAttributesToBusinessObject(FormalPolicy p_FormalPolicy,BusinessObject attributeOwner, Vector attributeList) {
		BusinessObject p_BusinessObject;
        for(int i=0;i<attributeList.size();i++){
			p_BusinessObject=this.getBusinessObjectByName(p_FormalPolicy,(String)attributeList.elementAt(i));
            if(p_BusinessObject==null){
                p_BusinessObject=this.createBusinessObject(p_FormalPolicy,(String)attributeList.elementAt(i));
            }
            this.addAttributeToBusinessObject(attributeOwner,p_BusinessObject);
        }

    }

    public BusinessObject createBusinessObject(FormalPolicy p_FormalPolicy, String objectName) {
		BusinessObject p_BusinessObject=new BusinessObject();
        p_BusinessObject.setName(objectName);
        p_FormalPolicy.addBusinessObject(p_BusinessObject);
        return p_BusinessObject;

    }

    public void assignValue(BusinessObject valueOwner, String value) {
    	Vector values=valueOwner.getValues();
        for(int i=0;i<values.size();i++){
            if(value.equals((String)values.elementAt(i))){
                return;
            }
        }
        values.addElement(value);

    }

    public void createAttributeFactRule(FormalPolicy p_FormalPolicy,BusinessObject attributeOwner,BusinessObject attribute){
		AttributeFactRule attRule;
        attRule = new AttributeFactRule(attribute,attributeOwner);
        p_FormalPolicy.addAttributeFactRule(attRule);
    }

    public void createAttributeFactRule(FormalPolicy p_FormalPolicy,BusinessObject attributeOwner,Vector attributes){
		AttributeFactRule attRule;
        BusinessObject bObject;
        Vector attributeObjects=new Vector();
        for(int i=0;i<attributes.size();i++){
			bObject=this.getBusinessObjectByName(p_FormalPolicy,(String)attributes.elementAt(i));
            attributeObjects.addElement(bObject);
        }
        attRule = new AttributeFactRule(attributeOwner,attributeObjects);
        p_FormalPolicy.addAttributeFactRule(attRule);
    }

    public void createGeneralizationFactRule(FormalPolicy p_FormalPolicy,String subClass,String superClass){
        BusinessObject father;
        BusinessObject son;
        GeneralizationFactRule genRule;
       	father= this.getBusinessObjectByName(p_FormalPolicy,superClass);
        if(father==null){
            father=this.createBusinessObject(p_FormalPolicy,superClass);
        }
        son=this.getBusinessObjectByName(p_FormalPolicy,subClass);
        if(son==null){
            son=this.createBusinessObject(p_FormalPolicy,subClass);
        }
        this.assignSubClass(father,son);
		genRule=new GeneralizationFactRule(son,father);
        p_FormalPolicy.addGeneralizationFactRule(genRule);


    }

    public void assignSubClass(BusinessObject father,BusinessObject son){
        Vector fatherSubClasses = father.getSubClasses();
        BusinessObject bObject;
        for(int i=0;i<fatherSubClasses.size();i++){
			bObject=(BusinessObject)fatherSubClasses.elementAt(i);
            if(son.getName().equals(bObject.getName())){
                return;
            }
        }
        fatherSubClasses.addElement(son);
        son.getSuperClasses().addElement(father);
    }

    public void createRoleFactRule(FormalPolicy p_FormalPolicy,String objectName,String roleName){
        BusinessObject role=null;
        BusinessObject object=null;
        role=this.getBusinessObjectByName(p_FormalPolicy,roleName);
        object=this.getBusinessObjectByName(p_FormalPolicy,objectName);
        if(object==null){
            object=this.createBusinessObject(p_FormalPolicy,objectName);
        }

        if(role==null){
            role=this.createBusinessObject(p_FormalPolicy,roleName);
			role.getObjectsWithThisRole().addElement(object);
            object.getRolesOfThisObject().addElement(role);
            return;
        }
		this.assignBusinessRole(object,role);
		RoleFactRule newRule=new RoleFactRule(object,role);
        p_FormalPolicy.addRoleFactRule(newRule);

    }


    public void assignBusinessRole(BusinessObject object,BusinessObject role){
        Vector objectsWithTheRole =  role.getObjectsWithThisRole();
        BusinessObject obj=null;
        for(int i=0;i<objectsWithTheRole.size();i++){
			obj=(BusinessObject)objectsWithTheRole.elementAt(i);
            if(obj.getName().equals(object.getName())){
                return;
            }
        }
        objectsWithTheRole.addElement(object);
        object.getRolesOfThisObject().addElement(role);
    }

    public void createAggregationFactRule(FormalPolicy p_FormalPolicy,String subject,String actionObject){
        BusinessObject particleObject=null;
        BusinessObject composedObject=null;
        particleObject=this.getBusinessObjectByName(p_FormalPolicy,actionObject);
        composedObject=this.getBusinessObjectByName(p_FormalPolicy,subject);
        if(particleObject==null){
            particleObject=this.createBusinessObject(p_FormalPolicy,actionObject);
        }

        if(composedObject==null){
            composedObject=this.createBusinessObject(p_FormalPolicy,subject);
			composedObject.getComposedBy().addElement(particleObject);
            particleObject.getComposes().addElement(composedObject);
            return;
        }
		this.assignComposition(composedObject,particleObject);
		AggregationFactRule newRule=new AggregationFactRule(composedObject,particleObject);
        p_FormalPolicy.addAggregationFactRule(newRule);
    }
    public void assignComposition(BusinessObject composedObject,BusinessObject particleObject){
		Vector composedByParticleObject = particleObject.getComposes();
        BusinessObject obj=null;
        for(int i=0;i<composedByParticleObject.size();i++){
			obj=(BusinessObject)composedByParticleObject.elementAt(i);
            if(obj.getName().equals(composedObject.getName())){
                return;
            }
        }
        composedByParticleObject.addElement(composedObject);
        composedObject.getComposedBy().addElement(particleObject);
    }
    public void createAssociationFactRule(FormalPolicy p_FormalPolicy,String subject,String verb,String actionObject){
  		BusinessObject modifierObject=null;
        BusinessObject modifiedObject=null;
        modifierObject=this.getBusinessObjectByName(p_FormalPolicy,actionObject);
        modifiedObject=this.getBusinessObjectByName(p_FormalPolicy,subject);
        if(modifierObject==null){
            modifierObject=this.createBusinessObject(p_FormalPolicy,actionObject);
        }

        if(modifiedObject==null){
            modifiedObject=this.createBusinessObject(p_FormalPolicy,subject);
			modifiedObject.getModifiedBy().addElement(modifierObject);
            modifiedObject.getModifiedByVerbs().addElement(verb);
            modifierObject.getModifies().addElement(modifiedObject);
            modifierObject.getModifiesVerbs().addElement(verb);
            return;
        }
		this.assignModifiers(modifiedObject,modifierObject,verb);
		AssociationFactRule newRule=new AssociationFactRule(modifiedObject,modifierObject,verb);
        p_FormalPolicy.addAssociationFactRule(newRule);

    }

    public void assignModifiers(BusinessObject modifiedObject,BusinessObject modifierObject,String verb){
		Vector modifiedByModifierObject = modifierObject.getModifies();
        Vector modifiesVerbs = modifierObject.getModifiesVerbs();
        BusinessObject obj=null;
        for(int i=0;i<modifiedByModifierObject.size();i++){
			obj=(BusinessObject)modifiedByModifierObject.elementAt(i);
            if((obj.getName().equals(modifiedObject.getName()))&&(verb.equals((String)modifiesVerbs.elementAt(i)))){
                return;
            }
        }
        modifiedByModifierObject.addElement(modifiedObject);
        modifiesVerbs.addElement(verb);
        modifiedObject.getModifiedBy().addElement(modifierObject);
        modifiedObject.getModifiedByVerbs().addElement(verb);
    }

    public InferenceRule createInferenceRule(Object truePart, Object falsePart,OrBlock booleanStructure) {
        InferenceRule rule = new InferenceRule(truePart,falsePart,booleanStructure);
        return rule;
    }

    public void addInferenceRule(FormalPolicy p_FormalPolicy, InferenceRule rule) {
        Vector involvedObjects= new Vector();
        Vector combinationsBActionPairs = new Vector();
        generateInferenceRuleStats(rule,involvedObjects);
        //getCombinationsFromInferenceRule(rule,null,combinationsBActionPairs,null);
        //rule.setCombinationsBActionPairs(combinationsBActionPairs);
        rule.setBusinessObjectsInvolved(involvedObjects);
        rule.setBusinessObjectsCount(involvedObjects.size());
        p_FormalPolicy.addInferenceRule(rule);
    }
    public void generateInferenceRuleStats(Object object,Vector involvedObjects){
		if(object instanceof BusinessAction || object == null){
            return;
        }
        else{
            if(object instanceof InferenceRule){
				getInvolvedObjects(((InferenceRule)object).getBooleanStructure(),involvedObjects);
                generateInferenceRuleStats(((InferenceRule)object).getTruePart(),involvedObjects);
                generateInferenceRuleStats(((InferenceRule)object).getFalsePart(),involvedObjects);
                return;
            }
            else{
                return;
            }
        }
    }

	public  void getInvolvedObjects(BooleanBlock booleanStructure, Vector involvedObjectsSoFar){
		RelationalPair pair;
        Vector block = booleanStructure.getBlock();
        for(int i=0;i<block.size();i++){
			if(block.elementAt(i) instanceof RelationalPair){
                pair=(RelationalPair)block.elementAt(i);
				updateSoFar(pair.getTheObject(),involvedObjectsSoFar);
            }
            else{
				getInvolvedObjects((BooleanBlock)block.elementAt(i),involvedObjectsSoFar);
            }
        }
    }

    public void updateSoFar(BusinessObject theObject,Vector involvedSoFar){
		BusinessObject bObject;
        for(int i=0;i<involvedSoFar.size();i++){
			bObject=(BusinessObject)involvedSoFar.elementAt(i);
            if(theObject.getName().equals(bObject.getName())){
                return;
            }
        }
        involvedSoFar.addElement(theObject);
    }

    public BusinessAction createAndAddBusinessAction(FormalPolicy p_FormalPolicy,String description){
        BusinessAction bAction;
        bAction=new BusinessAction(description);
        p_FormalPolicy.addBusinessAction(bAction);
        return bAction;
    }

    public BusinessAction getBusinessActionByDescription(FormalPolicy p_FormalPolicy,String description) {
        List<BusinessAction> bActions = p_FormalPolicy.getM_BusinessActions();
        String existentDescription;
        for(int i=0;i<bActions.size();i++){
            existentDescription=((BusinessAction)bActions.get(i)).getDescription();
            if(existentDescription.equals(description)){
                return (BusinessAction)bActions.get(i);
            }
        }
        return null;
    }

     public Vector concatVectors(Vector head, Vector tail){
        for(int i=0;i<tail.size();i++){
            head.addElement(((Vector)tail.elementAt(i)).clone());
        }
        return head;
    }
     /**
     * @Deprecated use addStateToBusinessAction() or addRiskLevelToBusinessAction instead
     */
	public void addStateAndRiskLevelToBusinessAction(BusinessAction action, int state, int riskLevelAction) {
		action.setState(state);
		action.setRisklevel(riskLevelAction);

	}
/**
* @Deprecated use changeStateToBusinessAction() or changeRiskLevelToBusinessAction instead
*/
	public void changeStateAndRiskLevelToBusinessAction(BusinessAction action, int state, int riskLevelAction) {
		// TODO Validate if the State or RiskLevel is leaster that current State or risklevel
		action.setState(state);
		action.setRisklevel(riskLevelAction);
	}

	public int getStateForBusinessAction(String image) {
		if(image.startsWith("NEGATIVE"))
			return model.State.NEGATIVE.intValue();
		if(image.startsWith("IRRELEVANT"))
			return State.IRRELEVANT.intValue();
		if(image.startsWith("FAULTY"))
			return State.FAULTY.intValue();
		if(image.startsWith("POSITIVE"))
			return State.POSITIVE.intValue();
		return  -1;
	}

	public boolean isValidRiskLevelValue(String onlyNumber) {
		Vector<String> riskLevelValues=new Vector<String>(Arrays.asList("0","1","2","3","4","5","6","7","8","9","10"));
		return riskLevelValues.contains(onlyNumber);
	}


	public void addStateToBusinessAction(BusinessAction action, int state) {

		action.setState(state);

	}

	public void changeStateToBusinessAction(BusinessAction action, int state) {
//		 TODO Auto-generated method stub
		action.setState(state);

	}

	public Vector<Requirement> getRequirementFromString(String values) throws ParseException  {
		Reader reader = new StringReader(values);
		parserValues.ReInit(reader);
		parserValues.start();
		return parserValues.getRequirements();
	}

	public Vector<ExpectedResult> getResultFromString(String values) throws ParseException  {
		Reader reader = new StringReader(values);
		parserValues.ReInit(reader);
		parserValues.start();
		return parserValues.getResults();
	}

	public void addRiskLevelToBusinessAction(BusinessAction action, int risklevelValue) {
		action.setRisklevel(risklevelValue);

	}

	public void addRequirementToBusinessAction(BusinessAction action, Vector requirementValues) {
		action.setRequirement(requirementValues);

	}

	public void addResultToBusinessAction(BusinessAction action, Vector resultValues) {
		action.setResult(resultValues);

	}

	public void changeRiskLevelToBusinessAction(BusinessAction action, int risklevelValue) {
		// TODO Auto-generated method stub
		action.setRisklevel(risklevelValue);

	}

	public void changeRequirementToBusinessAction(BusinessAction action, Vector requirementValues) {
		Vector actuals=(Vector) action.getRequirement().clone();
		Vector changed=new Vector();//(Vector) action.getRequirement().clone();
		for (Iterator iter = requirementValues.iterator(); iter.hasNext();) {
			Requirement req = (Requirement) iter.next();
			for (Iterator iterator = actuals.iterator(); iterator.hasNext();) {
				Requirement reqActual = (Requirement) iterator.next();
				if(req.toString().trim().equals(reqActual.toString().trim())){
					changed.addElement(reqActual);
				}
			}
		}
		//TODO log Changed
		actuals.removeAll(changed);
		actuals.addAll(requirementValues);
		action.setRequirement(actuals);
	}

	public void changeResultToBusinessAction(BusinessAction action, Vector resultValues) {
		Vector actuals=(Vector) action.getResult().clone();
		Vector changed=new Vector();
		for (Iterator iter = resultValues.iterator(); iter.hasNext();) {
			ExpectedResult res = (ExpectedResult) iter.next();
			for (Iterator iterator = actuals.iterator(); iterator.hasNext();) {
				ExpectedResult resActual = (ExpectedResult) iterator.next();
				if(res.getName().trim().equals(resActual.getName().trim())){
					changed.addElement(resActual);
				}
			}
		}
		//TODO log Changed
		actuals.removeAll(changed);
		actuals.addAll(resultValues);
		action.setResult(actuals);
	}
	private static RequirementResultValues parserValues= new RequirementResultValues(new StringReader(""));
	/*public OrBlock resolveSameElementInExpression(OrBlock booleanStructure) {
		Vector block=booleanStructure.getBlock();
		Vector rearmedBlock= new Vector();
		for (Object object : block) {
			if (object instanceof AndBlock) {
				AndBlock andBlock = (AndBlock) object;
				rearmedBlock.addElement(convertAndBlockWithSameElements(andBlock));
			}
			else if (object instanceof OrBlock) {
				OrBlock orBlock = (OrBlock) object;
				rearmedBlock.addElement(resolveSameElementInExpression(orBlock));
			}
			else{
				rearmedBlock.addElement(object);
			}
		}
		booleanStructure.setBlock(rearmedBlock);
		return booleanStructure;
		
	}
	public Object convertAndBlockWithSameElements(AndBlock p_AndBlock){
		Vector block=p_AndBlock.getBlock();
		Vector rearmedBlock= new Vector(); 
		for (Object object : block) {
			if (object instanceof PosRelationalPair) {
				PosRelationalPair posRelationalPair = (PosRelationalPair) object;
				validateRelationalPair(posRelationalPair, rearmedBlock);
			}
			else if (object instanceof OrBlock) {
				OrBlock orblock = (OrBlock) object;
				rearmedBlock.addElement(resolveSameElementInExpression(orblock));
			}
			else if (object instanceof AndBlock) {
				AndBlock andblock = (AndBlock) object;
				rearmedBlock.addElement(convertAndBlockWithSameElements(andblock));
			}
		}
		if(rearmedBlock.size()==1)
			return rearmedBlock.firstElement();
		p_AndBlock.setBlock(rearmedBlock);
		return p_AndBlock;
	}

	private void validateRelationalPair(PosRelationalPair posRelationalPair, Vector rearmedBlock) {
		BusinessObject bObj=posRelationalPair.getTheObject();
		String name=bObj.getName();
		String value= posRelationalPair.getTheValue();
		boolean changeExist=false;
		for (Object object : rearmedBlock) {
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
			rearmedBlock.addElement(posRelationalPair);
		}
		
	}

	private void changeBusinessObjectValues(BusinessObject businessObj, String existValue, String value, String newExistValue) {
		Vector values= businessObj.getValues();
		values.remove(existValue);
		values.remove(value);
		values.addElement(newExistValue);
		
	}*/

	public OrBlock resolveSameElementInExpression(OrBlock booleanStructure) {
		Vector block=booleanStructure.getBlock();
		Vector rearmedBlock= new Vector();
		for (Object object : block) {
			if (object instanceof AndBlock) {
				AndBlock andBlock = (AndBlock) object;
				rearmedBlock.addElement(convertAndBlockWithSameElements(andBlock));
			}
			else if (object instanceof OrBlock) {
				OrBlock orBlock = (OrBlock) object;
				rearmedBlock.addElement(resolveSameElementInExpression(orBlock));
			}
			else{
				rearmedBlock.addElement(object);
			}
		}
		booleanStructure.setBlock(rearmedBlock);
		return booleanStructure;

	}
	public Object convertAndBlockWithSameElements(AndBlock p_AndBlock){
		Vector block=p_AndBlock.getBlock();
		Vector rearmedBlock= new Vector();
		for (Object object : block) {
			if (object instanceof PosRelationalPair) {
				PosRelationalPair posRelationalPair = (PosRelationalPair) object;
				validateRelationalPair(posRelationalPair, rearmedBlock);
			}
			else if (object instanceof OrBlock) {
				OrBlock orblock = (OrBlock) object;
				rearmedBlock.addElement(resolveSameElementInExpression(orblock));
			}
			else if (object instanceof AndBlock) {
				AndBlock andblock = (AndBlock) object;
				rearmedBlock.addElement(convertAndBlockWithSameElements(andblock));
			}
			
				
		}
		if(rearmedBlock.size()==1)
			return rearmedBlock.firstElement();
		p_AndBlock.setBlock(rearmedBlock);
		return p_AndBlock;
	}

	private void validateRelationalPair(PosRelationalPair posRelationalPair, Vector rearmedBlock) {
		BusinessObject bObj=posRelationalPair.getTheObject();
		String name=bObj.getName();
		String value= posRelationalPair.getTheValue();
		boolean changeExist=false;
		for (Object object : rearmedBlock) {
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
			rearmedBlock.addElement(posRelationalPair);
		}
		
	}
	

	private void changeBusinessObjectValues(BusinessObject businessObj, String existValue, String value, String newExistValue) {
		Vector values= businessObj.getValues();
		values.remove(existValue);
		values.remove(value);
		values.addElement(newExistValue);
		
	}
}
