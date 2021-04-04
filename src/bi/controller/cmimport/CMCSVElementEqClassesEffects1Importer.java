/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved. 
 */
package bi.controller.cmimport;

import java.util.ArrayList;
import java.util.List;

import javax.swing.undo.UndoableEdit;

import model.Effect;
import model.Element;
import model.EquivalenceClass;
import model.State;
import model.Structure;
import model.edit.CMModelEditFactory;
import bi.controller.ElementManager;
import bi.controller.EquivalenceClassManager;
import bi.controller.StructureManager;
import bi.controller.editcontrol.CMRiskLevelEditController;
import bi.controller.editcontrol.CMStateEditController;
import bi.view.lang.CMMessages;
import bi.view.undomanagementviews.CMCompoundEdit;
import bi.view.utils.CMRiskLevelView;
import bi.view.utils.CMStateView;

import com.Ostermiller.util.CSVParser;



/**
 * @author smoreno
 *
 */
public class CMCSVElementEqClassesEffects1Importer implements CMImporter {
	private CMCSVFileFormat format ;
	private List<String> warningMessages;  
	/* (non-Javadoc)
	 * @see bi.controller.cmimport.CMImporter#doImport(java.util.List)
	 */
	public UndoableEdit doImport(List<List> p_importValues) {
		CMCompoundEdit ce = new CMCompoundEdit();
		getWarningMessages().clear();
		Structure structure = StructureManager.getSelectedStructure();
		//get the elements
		Element element = null;
		for(List<String> row : p_importValues)
		{
			if ((row.size()>0)&&!row.get(0).toString().equalsIgnoreCase(""))
			{
				element = ElementManager.INSTANCE.createElement(structure);
				//TODO : put the elements on the position specified
				ce.addEdit(StructureManager.INSTANCE.addElementToStructure(structure,element));
				String elementName = row.get(0);
				
				
				//check for repetition of the element name
				if (ElementManager.INSTANCE.elementNameExists(structure,elementName))
						elementName = ElementManager.INSTANCE.generateNewElementName(structure,elementName);
				//set the element name
				ce.addEdit(CMModelEditFactory.INSTANCE.createChangeNameModelEdit(element,elementName));
				element.setName(elementName);
			}
				//set the element description
			
			if ((row.size()>1)&&!row.get(1).toString().equalsIgnoreCase(""))
			{
				String elementDescription = row.get(1);
				ce.addEdit(CMModelEditFactory.INSTANCE.createChangeDescriptionModelEdit(element,elementDescription));
				element.setDescription(elementDescription);
			}
			//create the equivalence class
			if ((row.size()>2))
			if (element != null)
				ce.addEdit(importEquivalenceClass(element,row.subList(2,row.size())));
		}

		return ce;
	}

	/**
	*@autor smoreno
	 * @param p_element
	 * @param p_list
	 * @return
	 */
	private UndoableEdit importEquivalenceClass(Element p_element, List<String> p_list) {
		CMCompoundEdit ce = new CMCompoundEdit();
		EquivalenceClass equivalenceClass = EquivalenceClassManager.INSTANCE.createEquivalenceClass(p_element);
//		add the equivalence class to the element
		ce.addEdit(CMModelEditFactory.INSTANCE.createAddEquivalenceClassModelEdit(p_element,equivalenceClass));
		p_element.addEquivalenceClass(equivalenceClass);
		int fieldIndex =1; 
		//for each field of equivalence classes
		for (String stringValue : p_list)
		{
			fieldIndex++;
			if (stringValue == null)
				continue;
			if (format.getFields().get(fieldIndex)==CMCSVField.EQUIVALENCECLASS_STATE)
			{
				ce.addEdit(CMModelEditFactory.INSTANCE.createChangeStateModelEdit(equivalenceClass,State.getStateByName(stringValue)));
				equivalenceClass.setState(State.getStateByName(stringValue));
				continue;
			}
			if (format.getFields().get(fieldIndex)==CMCSVField.EQUIVALENCECLASS_RISKLEVEL)
			{
				ce.addEdit(CMModelEditFactory.INSTANCE.createChangeRiskLevelModelEdit(equivalenceClass,Integer.parseInt(stringValue)));
				//do not need to validate combinations nor test cases 'casue it never has them
				equivalenceClass.setRiskLevel(Integer.parseInt(stringValue));
				continue;
			}
			if (format.getFields().get(fieldIndex)==CMCSVField.EQUIVALENCECLASS_VALUE)
			{
				ce.addEdit(CMModelEditFactory.INSTANCE.createEquivalenceClassChangeValueModelEdit(equivalenceClass,stringValue));
				equivalenceClass.setValue(stringValue);
				continue;
			}
			if (format.getFields().get(fieldIndex)==CMCSVField.EQUIVALENCECLASS_EFFECTS)
			{
				ce.addEdit(importEquivalenceClassEffects(equivalenceClass,stringValue));
				continue;
			}
			
		}
		
		
		
		return ce;
	}

	/**
	*@autor smoreno
	 * @param p_equivalenceClass
	 * @param p_stringValue
	 * @return
	 */
	private UndoableEdit importEquivalenceClassEffects(EquivalenceClass p_equivalenceClass, String p_stringValue) {
	    CMCompoundEdit ce = new CMCompoundEdit();
	    Structure structure = StructureManager.getSelectedStructure();
	    //parse the effects using simple comma separated values
	    String[][] values = CSVParser.parse(p_stringValue,',');
	    if (values!=null)
	    for (String id : values[0])
	    	{
	    		//get the effect of that id
	    	    Effect effect = structure.getLnkEffectbyName(id);
	    	    if (effect!=null)
	    	    {
	    	    	if (!p_equivalenceClass.getEffects().contains(effect)){
	    	    		ce.addEdit(CMModelEditFactory.INSTANCE.createAddEffectModelEdit(p_equivalenceClass,effect));
	    	    		p_equivalenceClass.addEffect(effect);
	    	    		CMRiskLevelEditController riskLevelEditController = new CMRiskLevelEditController();
	    	    		riskLevelEditController.setRiskLevelView(new CMRiskLevelView());
	    	    		riskLevelEditController.setRiskLevelBean(effect);
	    	    		ce.addEdit(riskLevelEditController.applyChanges());
	    	    		getWarningMessages().addAll(riskLevelEditController.getWarningMessages());
	    	    		CMStateEditController stateEditController = new CMStateEditController();
	    	    		stateEditController.setStateView(new CMStateView());
	    	    		stateEditController.setStateBean(effect);
	    	    		ce.addEdit(stateEditController.applyChanges());
	    	    		getWarningMessages().addAll(stateEditController.getWarningMessages());
	    	    		
	    	    	}
	    	    		else
	    	    			getWarningMessages().add(CMMessages.getString("IMPORT_CSV_WARNING_REPEATED_EFFECT_ID")+id);
	    	    }
	    	    //warn about the ignored id
	    	    else
	    	    	getWarningMessages().add(CMMessages.getString("IMPORT_CSV_WARNING_BAD_EFFECT_ID")+id);
	    	}
		return ce;
	}

	public CMCSVElementEqClassesEffects1Importer(CMCSVFileFormat p_format) {
		super();
		// TODO Auto-generated constructor stub
		this.format = p_format;
	}

	public List<String> getWarningMessages() {
		if (warningMessages == null)
			warningMessages = new ArrayList<String>();
		return warningMessages;
	}

}
