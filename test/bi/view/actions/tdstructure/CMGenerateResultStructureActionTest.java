/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.view.actions.tdstructure;

import java.util.ArrayList;
import java.util.List;

import model.Effect;
import model.ExpectedResult;
import junit.framework.TestCase;

/**
 * @author smoreno
 *
 */
public class CMGenerateResultStructureActionTest extends TestCase {


	private CMGenerateResultStructureAction action;

	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		action = new CMGenerateResultStructureAction();
	}



	public void testCombineExpectedResult() {
		List<Effect> effects = new ArrayList();
		Effect effect = new Effect();
		Effect effect2 = new Effect();
		effects.add(effect);
		effects.add(effect2);
		ExpectedResult exp1 = new ExpectedResult();
		exp1.setName("UN");
		exp1.setValue("500");
		ExpectedResult exp2 = new ExpectedResult();
		exp2.setName("UN");
		exp2.setValue("1000");
		ExpectedResult exp3 = new ExpectedResult();
		exp3.setName("UN");
		exp3.setValue("500A");
		effect.addExpectedResult(exp1);
		effect.addExpectedResult(exp2);
		effect2.addExpectedResult(exp3);
		assertEquals("5,001,000",action.combineExpectedResult(exp1,exp2,effects).getValue());
		assertEquals("500500A",action.combineExpectedResult(exp1,exp3,effects).getValue());
		exp3.setName("OTRO");
		assertEquals("1,500",action.combineExpectedResult(exp1,exp2,effects).getValue());

	}


}
