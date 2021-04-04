/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package model.util;

import java.util.List;

import model.Dependency;

/**
 * @author smoreno
 *
 */
public interface CMDependencyBean {

	public List<Dependency> getDependencies();
	public void addDependency(Dependency dependency);
	public void removeDependency(Dependency dependency);
}
