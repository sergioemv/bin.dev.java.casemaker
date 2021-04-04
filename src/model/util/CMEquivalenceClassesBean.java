/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package model.util;

import java.util.List;

import model.EquivalenceClass;

/**
 * @author smoreno
 *
 */
public interface CMEquivalenceClassesBean {

	public List<EquivalenceClass> getEquivalenceClasses();
	public void addEquivalenceClass(EquivalenceClass p_equivalenceClass);
	public void removeEquivalenceClass(EquivalenceClass p_equivalenceClass);

}
