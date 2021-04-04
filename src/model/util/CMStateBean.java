/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved. 
 */
package model.util;

import java.util.List;

import model.State;

/**
 * represents a model object that has state (with setter and getter)
 * @author smoreno
 *
 */
public interface CMStateBean {

	public static final int    STATE_POSITIVE = 3;
	public static final int    STATE_NEGATIVE = 2;
	public static final int    STATE_IRRELEVANT = 1;
	public static final int    STATE_FAULTY = 0;
	public static final String STATE_POSITIVE_LABEL = "+";
	public static final String STATE_NEGATIVE_LABEL = "-";
	public void setState(int p_state);
	public void setState(State p_state);
	public String getStateName();
	public int getState();
	public List<? extends CMStateBean> getChildStates();
	public List<? extends CMStateBean> getParentStates();
}
