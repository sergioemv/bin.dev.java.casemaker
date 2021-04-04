/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.view.cells.editors;

import javax.swing.JTextField;

import model.ExpectedResult;
import bi.view.cells.CMCellExpectedResultName;

/**
 * @author smoreno
 *
 */
public class CMGridCellEditorExpectedResultName extends CMBaseGridCellEditor {

	/**
	 * @param p_arg0
	 * @param p_cell
	 */
	public CMGridCellEditorExpectedResultName(JTextField p_arg0, CMCellExpectedResultName p_cell) {
		super(p_arg0, p_cell);
		// TODO Auto-generated constructor stub
	}
/* (non-Javadoc)
 * @see com.eliad.model.defaults.DefaultGridCellEditor#stopCellEditing()
 */
@Override
public boolean stopCellEditing() {


	if (getCell()!=null)
	{

		boolean c = super.stopCellEditing();

		  String value = ((JTextField)this.getComponent()).getText();
		  ((ExpectedResult)getCell().getModel()).setName(value);


		  return c;
	}
	return super.stopCellEditing();
}
}
