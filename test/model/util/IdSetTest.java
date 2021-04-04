/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package model.util;

import junit.framework.TestCase;

/**
 * @author smoreno
 *
 */
public class IdSetTest extends TestCase {

	private IdSet idSet;

	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		idSet = new IdSet();
	}

	public void testClone() {


		idSet.registerId(2);
		idSet.registerId(4);
		IdSet clon = (IdSet) idSet.clone();
		assertEquals(true,clon.idExist(2));
		assertEquals(true,clon.idExist(4));
	}

	public void testDeleteId() {

			idSet.registerId(2);
			idSet.registerId(4);
			// TODO Auto-generated catch block

		idSet.deleteId(4);
		assertEquals(false,idSet.idExist(4));
	}

	public void testNextValidId() {
		idSet.registerId(2);
		idSet.registerId(4);
		idSet.registerId(5);
		idSet.registerId(6);
		idSet.registerId(1);

		assertEquals(3,idSet.nextValidId());
		idSet.registerId(3);
		assertEquals(7,idSet.nextValidId());
		//test for next valid id (pos)
		idSet.deleteId(2);
		idSet.deleteId(3);
		idSet.deleteId(5);
		assertEquals(2,idSet.nextValidId(0));
		assertEquals(2,idSet.nextValidId(2));
		assertEquals(3,idSet.nextValidId(3));
		assertEquals(5,idSet.nextValidId(4));
		assertEquals(5,idSet.nextValidId(5));
		assertEquals(7,idSet.nextValidId(6));
		assertEquals(1000,idSet.nextValidId(1000));
		idSet.registerId(0);
		idSet.registerId(-1);
		idSet.registerId(-1000);
		assertEquals(2,idSet.nextValidId(0));
		assertEquals(2,idSet.nextValidId(2));
		assertEquals(3,idSet.nextValidId(3));
		assertEquals(5,idSet.nextValidId(4));
		assertEquals(5,idSet.nextValidId(5));
		assertEquals(7,idSet.nextValidId(6));
		assertEquals(1000,idSet.nextValidId(1000));
	}

	public void testRegisterNextValidId() {
			idSet.registerId(2);
			idSet.registerId(4);
			idSet.registerId(5);
			idSet.registerId(6);
			idSet.registerNextValidId();
			idSet.registerNextValidId();
			idSet.deleteId(5);
			assertEquals(5,idSet.nextValidId());
	}

	public void testRegisterId() {
		idSet.registerId(2);
		idSet.registerId(4);
		//test if the idset will not store the same number twice
		assertEquals(2,idSet.size());
		idSet.registerId(4);
		assertEquals(2,idSet.size());
		idSet.registerId(3);
		assertEquals(3,idSet.size());
		idSet.deleteIds();
		//register 10000 ids
		for (int i = 1; i<=10000;i++)
			idSet.registerId(i);
		assertEquals(10000,idSet.size());

	}

}
