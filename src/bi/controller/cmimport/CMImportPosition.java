/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved. 
 */
package bi.controller.cmimport;

import bi.view.lang.CMMessages;

/**
 * @author smoreno
 *
 */
public enum CMImportPosition {

	CURRENT,
	BEFORE_FIRST,
	AFTER_LAST,
	DEFAULT;
	/* (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		switch (this) {
		case CURRENT:
			return CMMessages.getString("IMPORT_CSV_EFFECTS_POSITION_CURRENT");
		case AFTER_LAST:
			return CMMessages.getString("IMPORT_CSV_EFFECTS_POSITION_AFTER_LAST");
		case BEFORE_FIRST:
			return CMMessages.getString("IMPORT_CSV_EFFECTS_POSITION_BEFORE_FIRST");
		case DEFAULT:
			return CMMessages.getString("IMPORT_CSV_EFFECTS_POSITION_DEFAULT");
		default:
			return "";
		}
		
	}
	
}
