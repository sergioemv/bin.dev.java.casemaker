/**
 * Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package model;

/**
 * @author svonborries
 *
 */
public class ResultStructureTestData extends StructureTestData {

	private StructureTestData m_StructureTestData = null;

	/**
	 * @return Returns the m_StructureTestData.
	 * svonborries
	 */
	public StructureTestData getStructureTestDataLinked() {
		return m_StructureTestData;
	}

	/**
	 * @param structureTestData The m_StructureTestData to set.
	 * svonborries
	 */
	public void setStructureTestDataLinked(StructureTestData structureTestData) {
		m_StructureTestData = structureTestData;
	}
	
}
