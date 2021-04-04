/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved. 
 */
package bi.view.actions;

import java.util.Vector;

/**
 * @author smoreno
 *
 */
public enum CMActionState {
	REPORT_PROCESSING;

	private Vector<CMAction> disabledActions;
	
	public Vector<CMAction> getDisabledActions()
	{
		if (disabledActions==null)
		{
			createDisabledActionsList();
		}
		return disabledActions;
	}

	/**
	*@autor smoreno
	 */
	private void createDisabledActionsList() {
		// TODO Auto-generated method stub
		disabledActions = new Vector<CMAction>();
		switch (this) {
		case REPORT_PROCESSING:
			disabledActions.addAll(CMActionGroup.REPORT.getActions());
			break;

		default:
			break;
		}
	}

}
