/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package model;

import model.util.CMStateBean;
import junit.framework.TestCase;

/**
 * @author smoreno
 *
 */
public class DependencyTest extends TestCase {


	private Dependency dependency;

	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		this.dependency = new Dependency();
	}

	/*
	 * Test method for 'model.Dependency.getCombinationsByState(int)'
	 */
	public void testGetCombinationsByState() {
		Combination co1 = new Combination();
		Combination co2 = new Combination();
		Combination co3 = new Combination();
		Combination co4 = new Combination();
		Combination co5 = new Combination();
		Combination co6 = new Combination();

		co1.setState(CMStateBean.STATE_NEGATIVE);
		co2.setState(CMStateBean.STATE_NEGATIVE);
		co3.setState(CMStateBean.STATE_FAULTY);
		co4.setState(CMStateBean.STATE_FAULTY);
		co5.setState(CMStateBean.STATE_NEGATIVE);
		co6.setState(CMStateBean.STATE_POSITIVE);

		this.dependency.addCombination(co1);
		this.dependency.addCombination(co2);
		this.dependency.addCombination(co3);
		this.dependency.addCombination(co4);
		this.dependency.addCombination(co5);
		this.dependency.addCombination(co6);

		assertEquals(2,this.dependency.getCombinationsByState(CMStateBean.STATE_FAULTY).size());
		assertEquals(1,this.dependency.getCombinationsByState(CMStateBean.STATE_POSITIVE).size());
		assertEquals(3,this.dependency.getCombinationsByState(CMStateBean.STATE_NEGATIVE).size());
	}


}
