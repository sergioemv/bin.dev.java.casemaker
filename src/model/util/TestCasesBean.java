/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package model.util;

import java.util.List;

import model.TestCase;

/**
 * @author smoreno
 *
 */
public interface TestCasesBean {

	public List<TestCase> getTestCases();
	public void addTestCase(TestCase testCase);
	public void removeTestCase(TestCase testCase);

}
