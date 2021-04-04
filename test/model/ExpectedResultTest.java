/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package model;

import java.text.DecimalFormatSymbols;

import junit.framework.TestCase;

/**
 * @author smoreno
 *
 */
public class ExpectedResultTest extends TestCase {

	/*
	 * Test method for 'model.ExpectedResult.setValue(String)'
	 */
	public void testSetValue() {
		ExpectedResult eresult = new ExpectedResult();
		DecimalFormatSymbols dfs = new DecimalFormatSymbols();
		dfs.setGroupingSeparator('.');
		dfs.setDecimalSeparator(',');
		eresult.setTestObjectSymbols(dfs);
		eresult.setValue("1000"); //$NON-NLS-1$
		assertEquals("1.000",eresult.getValue()); //$NON-NLS-1$
		eresult.setValue("10.0.0,455"); //$NON-NLS-1$
		assertEquals("1.000,455",eresult.getValue()); //$NON-NLS-1$
		eresult.setValue("10.0.0,4.5.5"); //$NON-NLS-1$
		assertEquals("1.000,455",eresult.getValue()); //$NON-NLS-1$
		eresult.setValue("10.0.0,4.5.50000.00"); //$NON-NLS-1$
		assertEquals("1.000,455000000",eresult.getValue()); //$NON-NLS-1$
		eresult.setValue("1,0.0.0,4.5.50000.00"); //$NON-NLS-1$
		assertEquals("1,000455000000",eresult.getValue()); //$NON-NLS-1$
		eresult.setValue("1.0.0.00.0,0.0.0,4.,5.50,0.0,0.00"); //$NON-NLS-1$
		assertEquals("100.000,000455000000",eresult.getValue()); //$NON-NLS-1$
		//test de numeros negativos
		eresult.setValue("-1.0.0.00.0,0.0.0,4.,5.50,0.0,0.00"); //$NON-NLS-1$
		assertEquals("-100.000,000455000000",eresult.getValue()); //$NON-NLS-1$
		eresult.setValue("-1000"); //$NON-NLS-1$
		assertEquals("-1.000",eresult.getValue()); //$NON-NLS-1$
		eresult.setValue("10-00"); //$NON-NLS-1$
		assertEquals("10",eresult.getValue()); //$NON-NLS-1$
		eresult.setValue("1000,00-12"); //$NON-NLS-1$
		assertEquals("1.000,0012",eresult.getValue()); //$NON-NLS-1$
		eresult.setValue("  1000"); //$NON-NLS-1$
		assertEquals("1.000",eresult.getValue()); //$NON-NLS-1$
		eresult.setValue("1000  "); //$NON-NLS-1$
		assertEquals("1.000",eresult.getValue()); //$NON-NLS-1$
		eresult.setValue("10 00"); //$NON-NLS-1$
		assertEquals("1.000",eresult.getValue()); //$NON-NLS-1$
	}

}
