/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved. 
 */
package bi.view.actions;

import java.util.Arrays;
import java.util.Vector;

/**
 * @author smoreno
 *
 */
public enum CMActionGroup {
	REPORT;
	private Vector<CMAction> actions;
	public Vector<CMAction> getActions()
	{
		if (actions==null)
		{
			createActionList();
		}
		return actions;
	}
	/**
	*@autor smoreno
	 */
	private void createActionList() {
		
		switch (this) {
		case REPORT:
			actions = new Vector<CMAction>(Arrays.asList(CMAction.DEPENDENCY_REPORT_EXCEL,
																		CMAction.ERROR_REPORT_LIST,
																		CMAction.TESTCASE_REPORT_COMPUWARE,
																		CMAction.TESTCASE_REPORT_CSV,
																		CMAction.TESTCASE_REPORT_LIST1,
																		CMAction.TESTCASE_REPORT_LIST2,
																		CMAction.TESTCASE_REPORT_QADIRECTOR));
			break;

		default:
			break;
		}
		
	}
}
