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
public class StructureTest extends TestCase {

	private Structure structure;

	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		this.structure = new Structure();

		super.setUp();
	}

	/*
	 * Test method for 'model.Structure.getLnkEffectbyName(String)'
	 */
	public void testGetLnkEffectbyName() {
		Effect l_effect = new Effect();
		l_effect.setId(45);
		this.structure.addEffect(l_effect);
		Effect l_effect1 = new Effect();
		l_effect1.setId(100);
		this.structure.addEffect(l_effect1);

		assertNotNull(this.structure.getLnkEffectbyName("CE045")); //$NON-NLS-1$
		assertNotNull(this.structure.getLnkEffectbyName("CE100")); //$NON-NLS-1$
	}

	public void testGetLnkEffects() {
		Effect l_effect1 = new Effect();
		l_effect1.setId(100);
		this.structure.addEffect(l_effect1);
		Effect l_effect = new Effect();
		l_effect.setId(45);
		this.structure.addEffect(l_effect);
		assertNotNull(this.structure.getEffects());
		//ensure the order of the cause effects
		assertEquals(this.structure.getEffects().get(0).getId(),45);

	}

}
