/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package model.util;

import model.CMField;

/**
 * @author smoreno
 *
 */
public interface CMModelSource {
	public void addModelListener(CMModelListener listener);
	public void removeModelListener(CMModelListener listener);
	public void fireModelEventHappen(CMField field);
}
