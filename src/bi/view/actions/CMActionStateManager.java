/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 D�az und Hilterscheid Unternehmensberatung. All rights reserved. 
 */
package bi.view.actions;


/**
 * @author smoreno
 *
 */
public class CMActionStateManager {

	public synchronized static void setState(CMActionState state, boolean enabled)
	{
		for (CMAction action : state.getDisabledActions())
				action.setEnabled(enabled);
	}

	
}
