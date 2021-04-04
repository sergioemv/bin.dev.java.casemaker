package bi.controller;

import model.Element;
import model.EquivalenceClass;
import model.Structure;
import junit.framework.TestCase;

public class EquivalenceClassManagerTest extends TestCase {
	private Structure structure;
	@Override
	protected void setUp() throws Exception {

		structure.addElement(new Element());
		structure.getElements().get(0).addEquivalenceClass(new EquivalenceClass ());
		structure.getElements().get(0).addEquivalenceClass(new EquivalenceClass ());
		structure.addElement(new Element());
		structure.getElements().get(1).addEquivalenceClass(new EquivalenceClass ());
		structure.getElements().get(1).addEquivalenceClass(new EquivalenceClass ());
		structure.addElement(new Element());
		structure.getElements().get(2).addEquivalenceClass(new EquivalenceClass ());
		structure.getElements().get(2).addEquivalenceClass(new EquivalenceClass ());

		super.setUp();
	}

}
