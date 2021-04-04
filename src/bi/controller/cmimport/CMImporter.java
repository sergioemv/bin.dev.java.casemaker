/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved. 
 */
package bi.controller.cmimport;

import java.util.List;

import javax.swing.undo.UndoableEdit;


/**
 * @author smoreno
 *
 */
 interface CMImporter {

	/**
	 * From the list of list execute a import according to the instantiated algoritm  
	*@autor smoreno
	 * @param importValues
	 */
	UndoableEdit doImport(List<List> importValues);
	
	List<String> getWarningMessages();
}
