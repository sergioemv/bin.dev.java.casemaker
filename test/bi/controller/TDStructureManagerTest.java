package bi.controller;

import model.StructureTestData;
import junit.framework.TestCase;


public class TDStructureManagerTest extends TestCase {
	StructureTestData structure; 
	protected void setUp() throws Exception {
		super.setUp();
		structure = new StructureTestData();
		
	}

	public void testGenerateNewColumnName() {
		assertEquals("",TDStructureManager.INSTANCE.generateNewColumnName(null));
		assertEquals("Column Untitled1",TDStructureManager.INSTANCE.generateNewColumnName(structure));
		structure.getNewColumnsHeader().add("Column Untitled1");
		assertEquals("Column Untitled2",TDStructureManager.INSTANCE.generateNewColumnName(structure));
		structure.getNewColumnsHeader().add("Column Untitled2");
		structure.getNewColumnsHeader().add("Column Untitled3");
		structure.getNewColumnsHeader().add("Column Untitled4");
		structure.getNewColumnsHeader().add("Column Untitled5");
		structure.getNewColumnsHeader().add("Column Untitled6");
		structure.getNewColumnsHeader().add("Column Untitled7");
		structure.getNewColumnsHeader().add("Column Untitled8");
		structure.getNewColumnsHeader().add("Column Untitled9");
		assertEquals("Column Untitled10",TDStructureManager.INSTANCE.generateNewColumnName(structure));
	}

}
