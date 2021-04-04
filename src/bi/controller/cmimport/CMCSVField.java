/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved. 
 */
package bi.controller.cmimport;

import java.util.Arrays;
import java.util.Collection;
import java.util.Vector;

import bi.view.lang.CMMessages;

/**
 *  Enumerates all the fields that are recognized by the import process
 * @author smoreno
 *
 */
public enum CMCSVField {

	ELEMENT_NAME,
	ELEMENT_DESCRIPTION,
	EQUIVALENCECLASS_NAME,
	EQUIVALENCECLASS_DESCRIPTION,
	EQUIVALENCECLASS_VALUE,
	EQUIVALENCECLASS_STATE,
	EQUIVALENCECLASS_RISKLEVEL,
	EQUIVALENCECLASS_EFFECTS,
	EFFECT_ID,
	EFFECT_DESCRIPTION,
	EFFECT_STATE,
	EFFECT_RISKLEVEL;
	public CSVFieldFormat getFormat()
	{
		switch (this) {
		case EQUIVALENCECLASS_STATE:
			return CSVFieldFormat.STATE;
		case EQUIVALENCECLASS_RISKLEVEL:
			return CSVFieldFormat.RISKLEVEL;
		case EQUIVALENCECLASS_EFFECTS:
			return CSVFieldFormat.COMMASEP;
		case EFFECT_STATE:
			return CSVFieldFormat.STATE;
		case EFFECT_RISKLEVEL:
			return CSVFieldFormat.RISKLEVEL;
		case EFFECT_ID:
			return CSVFieldFormat.EFFECT_ID;
		default:
			return CSVFieldFormat.ANY;
		}
		
	}
	/* (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
			switch (this) {
			case ELEMENT_NAME:
				return CMMessages.getString("LABEL_ELEMENT");
			case ELEMENT_DESCRIPTION:
				return CMMessages.getString("LABEL_DESCRIPTION");
			case EQUIVALENCECLASS_DESCRIPTION:
				return CMMessages.getString("LABEL_DESCRIPTION");
			case EQUIVALENCECLASS_NAME:
				return CMMessages.getString("LABEL_EQUIVALENCE_CLASS");
			case EQUIVALENCECLASS_STATE:
				return CMMessages.getString("STATE_COMBOBOX_LABEL");
			case EQUIVALENCECLASS_RISKLEVEL:
				return CMMessages.getString("LABEL_RISK_LEVEL");
			case EQUIVALENCECLASS_EFFECTS:
				return CMMessages.getString("LABEL_CAUSE_EFFECTS");
			case EQUIVALENCECLASS_VALUE:
				return CMMessages.getString("LABEL_VALUE");
			case EFFECT_ID:
				return CMMessages.getString("LABEL_CAUSE_EFFECT");
			case EFFECT_DESCRIPTION:
				return CMMessages.getString("LABEL_DESCRIPTION");
			case EFFECT_RISKLEVEL:
				return CMMessages.getString("LABEL_RISK_LEVEL");
			case EFFECT_STATE:
				return CMMessages.getString("STATE_COMBOBOX_LABEL");
			default:
				return "";
			}
	}
	/**
	 * @author smoreno
	 *
	 */		
	public enum CSVFieldFormat {
		ANY,
		STATE,
		RISKLEVEL,
		COMMASEP,
		EFFECT_ID;
		 public String getDefault()
		 {
			 switch (this) {
			case ANY:
				return new String();
			case STATE:
				return "+";
			case RISKLEVEL:
				return "0";
			case COMMASEP:
				return "";
			case EFFECT_ID:
				return "CE001";
			default:
				return  null;
			}
		 }
		public Collection<String> getAllowedValues()
		{
			switch (this) {
			case STATE:
				return new Vector<String>(Arrays.asList("+","-","F","I"));
			case RISKLEVEL:
				return new Vector<String>(Arrays.asList("0","1","2","3","4","5","6","7","8","9","10"));
			default:
				return  null;
			}
		}
		public String getValidPattern()
		{
			switch (this) {
			case EFFECT_ID:
				return "CE.*";
			default:
				return "";
			}
			
		}
	}
}
