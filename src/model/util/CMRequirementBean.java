/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved. 
 */
package model.util;

import java.util.List;

import model.Requirement;

/**
 * @author smoreno
 * public interface for all objects that has related requirements
 */
public interface CMRequirementBean {

	public List<Requirement> getRequirements();
	public void addRequirement(Requirement p_requirement);
	public void removeRequirement(Requirement p_requirement);
}
