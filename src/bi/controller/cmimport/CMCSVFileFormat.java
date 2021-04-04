/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved. 
 */
package bi.controller.cmimport;

import java.util.Arrays;
import java.util.List;

import bi.view.lang.CMMessages;

/**
 * enumerates the file format that are recognized ( as a set of csv fields)
 * @author smoreno
 *
 */
public enum CMCSVFileFormat {

	ELEMENTS_EQUIVALENCE_EFFECTS_1,
	EFFECTS;
	private CMImporter importer = null; 
	/**
	 * @return Returns the importer.
	 */
	CMImporter getImporter() {
		if (this.importer==null)
		{
			switch (this) {
			case ELEMENTS_EQUIVALENCE_EFFECTS_1:
				this.importer = new CMCSVElementEqClassesEffects1Importer(this);
				break;
			case EFFECTS:
				this.importer = new CMCSVEffectsImporter(this);
				break;

			default:
				break;
			}
		}
		return this.importer;
	}
	public List<CMCSVField> getFields()
	{
		switch (this) {
		case ELEMENTS_EQUIVALENCE_EFFECTS_1:
			return Arrays.asList(CMCSVField.ELEMENT_NAME,
										CMCSVField.ELEMENT_DESCRIPTION,
										CMCSVField.EQUIVALENCECLASS_STATE,
										CMCSVField.EQUIVALENCECLASS_RISKLEVEL,
										CMCSVField.EQUIVALENCECLASS_VALUE,
										CMCSVField.EQUIVALENCECLASS_EFFECTS);
		case EFFECTS:
			return Arrays.asList(CMCSVField.EFFECT_ID,
										CMCSVField.EFFECT_DESCRIPTION,
										CMCSVField.EFFECT_RISKLEVEL,
										CMCSVField.EFFECT_STATE);
		default:
			return null;
		}
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		switch (this) {
		case ELEMENTS_EQUIVALENCE_EFFECTS_1:
			return (CMMessages.getString("IMPORT_CSV_ELEMENT_EQUIVALENCE_EFFECT_NAME"));
		case EFFECTS:
			return (CMMessages.getString("IMPORT_CSV_EFFECTS_NAME"));

		default:
			return "";
		}
	}
	
	public List<CMImportPosition> getValidPositions()
	{
		switch (this) {
		case ELEMENTS_EQUIVALENCE_EFFECTS_1:
			return Arrays.asList(CMImportPosition.AFTER_LAST);
		case EFFECTS:
			return Arrays.asList(CMImportPosition.DEFAULT);

		default:
			return null;
		}
		
	}
}
