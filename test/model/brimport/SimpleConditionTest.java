package model.brimport;

import junit.framework.TestCase;

public class SimpleConditionTest extends TestCase {

	public void testValidIdentifier() {
		SimpleCondition condition  = new SimpleCondition();
		assertEquals(condition.validIdentifier("ELE_NA"), true);
		assertEquals(condition.validIdentifier("1ELENA"), false);
		assertEquals(condition.validIdentifier("2ELE(NA"), false);
		assertEquals(condition.validIdentifier("ELENA_"), true);
		assertEquals(condition.validIdentifier("_ELENA"), false);
		assertEquals(condition.validIdentifier("E_L_E-NA"), false);
		assertEquals(condition.validIdentifier("20"), true);
		assertEquals(condition.validIdentifier("100.1"), true);
	}

}