/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package model.util;

import java.util.List;

import model.Combination;

/**
 * @author smoreno
 *
 */
public interface CMCombinationsBean {

	public void addCombination(Combination p_Combination);
	public void removeCombination(Combination p_Combination);
	public List<? extends Combination> getCombinations();
}
