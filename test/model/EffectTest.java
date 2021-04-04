/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package model;

import junit.framework.TestCase;

/**
 * @author smoreno
 *
 */
public class EffectTest extends TestCase {

	private Effect effect;

	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		this.effect = new Effect();
		super.setUp();
	}

	/*
	 * Test method for 'model.Effect.setId(int)'
	 */
	public void testSetId() {
		this.effect.setId(1);
		assertEquals(this.effect.getId(),1);
		assertEquals(this.effect.getName(),"CE001"); //$NON-NLS-1$
		this.effect.setId(999);
		assertEquals(this.effect.getId(),999);
		assertEquals(this.effect.getName(),"CE999"); //$NON-NLS-1$
	}

	/*
	 * Test method for 'model.Effect.setDescription(String)'
	 */
	public void testSetDescription() {
		this.effect.setDescription("This is a Test Description"); //$NON-NLS-1$
		assertEquals(this.effect.getDescription(),"This is a Test Description"); //$NON-NLS-1$
	}

	/*
	 * Test method for 'model.Effect.makeClone()'
	 */
	public void testMakeClone() {
		Effect l_effect = this.effect.makeClone();
		assertEquals(l_effect.getDescription(),effect.getDescription());
		assertEquals(l_effect.getName(),effect.getName());
		assertEquals(l_effect.getId(),effect.getId());
		assertEquals(l_effect.getTimestamp().toString(),effect.getTimestamp().toString());
		assertEquals(l_effect.getLnkEquivalenceClasses(),null);
	}

	/*
	 * Test method for 'model.Effect.toString()'
	 */
	public void testToString() {
		assertEquals(this.effect.getName(), this.effect.toString());
	}

}
