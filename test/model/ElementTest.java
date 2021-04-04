package model;

import junit.framework.TestCase;


public class ElementTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}



	public void testRemoveEquivalenceClass() {
		
		Element element = new Element();
		EquivalenceClass eq = new EquivalenceClass();
		element.addEquivalenceClass(new EquivalenceClass());
		element.addEquivalenceClass(eq);
		assertEquals(true, element.getEquivalenceClasses().contains(eq));
		element.removeEquivalenceClass(eq);
		assertEquals(false, element.getEquivalenceClasses().contains(eq));
		
		
	}

}
