/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved. 
 */
package model;

import model.util.CMStateBean;
import bi.view.lang.CMMessages;

/**
 * @author smoreno
 *
 */
public enum State{

	FAULTY,
	IRRELEVANT,
	NEGATIVE,
	POSITIVE;
	/* (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		switch (this) {
		case POSITIVE:
			return CMStateBean.STATE_POSITIVE_LABEL;
		case NEGATIVE:
			return CMStateBean.STATE_NEGATIVE_LABEL;
		case FAULTY:
			return CMMessages.getString("STATE_FAULTY_LABEL");
		case IRRELEVANT:
			return CMMessages.getString("STATE_IRRELEVANT_LABEL");
		default:
			return "";
		}
	}
	public int intValue()
	{
		return this.ordinal();
	}
	public static State getStateByName(String value)
	{
		for (State state : State.values())
			if (state.toString().equalsIgnoreCase(value))
				return state;
		return null;
	}
	public static int compare(int state, int state2) {

		if (state==state2)
			return 0;
		if (state<state2)
			return 1;
		if (state>state2)
			return -1;
		return 0;
	}
	
}
