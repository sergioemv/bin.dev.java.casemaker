package bi.controller.testcase.generator;

import java.util.ArrayList;

import junit.framework.TestCase;
import model.Dependency;
import model.Element;
import model.EquivalenceClass;
import model.Structure;
import model.TestObject;
import bi.view.mainframeviews.CMApplication;

public class CMPositiveEquivalenceClassOneWiseGeneratorTest extends TestCase {

	private Structure structure;
	
	protected void setUp() throws Exception {
		super.setUp();
		structure = new Structure();
		structure.setTestObject(new TestObject());
		CMApplication cmap = new CMApplication();
	}

	public void testGenerate() {
		//add two elements
		structure.addElement(new Element());
		structure.addElement(new Element());
		//add one equivalence class per element
		structure.getElements().get(0).addEquivalenceClass(new EquivalenceClass());
		structure.getElements().get(1).addEquivalenceClass(new EquivalenceClass());
		//generate onewise 
		CMTestCaseGenerator gen = new CMPositiveEquivalenceClassOneWiseGenerator();
		gen.setDependencies(new ArrayList<Dependency>());
		gen.setStructure(structure);
		try {
			gen.generate();
		} catch (CMTestCaseGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(1,structure.getTestCases().size());
	
	}

}
