/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.view.cells;

import model.ExpectedResult;

import com.eliad.swing.JSmartGrid;

/**
 * @author smoreno
 *
 */
public class CMCellExpectedResultName extends CMBaseCell {

	/**
	 * @param p_grid
	 * @param p_model
	 */
	public CMCellExpectedResultName(JSmartGrid p_grid, ExpectedResult p_model) {

		super(p_grid, p_model);

	}
/* (non-Javadoc)
 * @see java.lang.Object#toString()
 */
@Override
public String toString() {
	
	return ((ExpectedResult)getModel()).getName();
}
}