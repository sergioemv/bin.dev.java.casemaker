/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved. 
 */
package bi.controller.cmimport;

import java.util.ArrayList;
import java.util.List;

import javax.swing.undo.UndoableEdit;

import org.apache.log4j.Logger;

import model.Effect;
import model.State;
import model.Structure;
import model.edit.CMModelEditFactory;

import bi.controller.EffectManager;
import bi.controller.StructureManager;
import bi.view.lang.CMMessages;
import bi.view.undomanagementviews.CMCompoundEdit;

/**
 * @author smoreno
 *
 */
public class CMCSVEffectsImporter implements CMImporter {

	private CMCSVFileFormat format;
	private List<String> warningMessages;
	/**
	 * @param p_format
	 */
	public CMCSVEffectsImporter(CMCSVFileFormat p_format) {
		super();
		format = p_format;
	}

	/* (non-Javadoc)
	 * @see bi.controller.cmimport.CMImporter#doImport(java.util.List)
	 */
	public UndoableEdit doImport(List<List> p_importValues) {
		CMCompoundEdit ce = new CMCompoundEdit();
		getWarningMessages().clear();
		Structure structure = StructureManager.getSelectedStructure();
		for(List<String> row : p_importValues)
		{
			int fieldIndex = -1;
			Effect effect = null;
			for (String stringValue : row)
			{
				fieldIndex++;
				if (stringValue == null)
					continue;
				//create the effect with the correct name and id
				if (format.getFields().get(fieldIndex)==CMCSVField.EFFECT_ID)
				{
					//check if the effect id is already used
					if (structure.getLnkEffectbyName(stringValue)!=null)
					{
						effect = EffectManager.INSTANCE.createEffect(structure);
						getWarningMessages().add(CMMessages.getString("IMPORT_CSV_WARNING_NEW_EFFECT_ID_GENERATED")+stringValue);
						Logger.getLogger(this.getClass()).warn(CMMessages.getString("IMPORT_CSV_WARNING_NEW_EFFECT_ID_GENERATED")+stringValue);
					}else
						effect = EffectManager.INSTANCE.createEffect(structure,stringValue);
					ce.addEdit(EffectManager.INSTANCE.addEffect(structure,effect));
					continue;
				}
				if (effect == null)
					break;
				if (format.getFields().get(fieldIndex)==CMCSVField.EFFECT_DESCRIPTION)
				{
					ce.addEdit(CMModelEditFactory.INSTANCE.createChangeDescriptionModelEdit(effect,stringValue));
					effect.setDescription(stringValue);
					continue;
			     }
				if (format.getFields().get(fieldIndex)==CMCSVField.EFFECT_STATE)
				{
					int intValue;
					intValue = State.getStateByName(stringValue).intValue();
					ce.addEdit(CMModelEditFactory.INSTANCE.createChangeStateModelEdit(effect,State.getStateByName(stringValue)));
					effect.setState(intValue);
					continue;
			     }
				if (format.getFields().get(fieldIndex)==CMCSVField.EFFECT_RISKLEVEL)
				{
					int intValue;
					try {
						intValue = Integer.parseInt(stringValue);
					} catch (NumberFormatException e) {
						intValue=0;
					}
					ce.addEdit(CMModelEditFactory.INSTANCE.createChangeRiskLevelModelEdit(effect,intValue));
					effect.setRiskLevel(intValue);
					continue;
			     }

			}
		}
		return ce;
	}

	public List<String> getWarningMessages() {
		if (warningMessages == null)
			warningMessages = new ArrayList<String>();
		return warningMessages;
	}

}
