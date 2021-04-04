/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.view.cells.editors;

import java.awt.Color;

import javax.swing.JMenuItem;
import javax.swing.JTextField;

import model.ExpectedResult;
import bi.controller.StructureManager;
import bi.view.actions.effect.CMExpectedResultsGrid;
import bi.view.cells.CMBaseCell;
import bi.view.lang.CMMessages;

/**
 * @author smoreno
 *
 */
public class CMGridCellEditorExpectedResultValue extends CMBaseGridCellEditor {

	/**
	 * @param p_arg0
	 * @param p_cell
	 */
	public CMGridCellEditorExpectedResultValue(JTextField p_arg0, CMBaseCell p_cell) {
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
		;
		  String value = ((JTextField)this.getComponent()).getText();
		  if (!value.equals(((ExpectedResult)getCell().getModel()).getValue()))
			  ((ExpectedResult)getCell().getModel()).setValue(value);
		  if (!value.equalsIgnoreCase(((ExpectedResult)getCell().getModel()).getValue())){

			  ((CMExpectedResultsGrid)this.getCell().getGrid()).getJPopupMenuVisualNotifier().removeAll();
			  ((CMExpectedResultsGrid)this.getCell().getGrid()).getJPopupMenuVisualNotifier().add(
					  CMMessages.getString("ER_NUMBER_FORMATTED_FROM")
					  +" "+value+" "+
					  CMMessages.getString("ER_NUMBER_FORMATTED_TO")+" "+
					  ((ExpectedResult)getCell().getModel()).getValue()+" - "+
					  CMMessages.getString("ER_NUMBER_FORMATTED_DECSEP")+" = '"+
					  StructureManager.getSelectedStructure().getTestObject().getDecimalSeparator()+"' |"+
					  CMMessages.getString("ER_NUMBER_FORMATTED_THOSEP")+" = '"+
					  StructureManager.getSelectedStructure().getTestObject().getMilesSeparator()+"'");
			  ((JMenuItem)((CMExpectedResultsGrid)this.getCell().getGrid()).getJPopupMenuVisualNotifier().getComponent(0)).setForeground((Color.red));			  
			  ((JMenuItem)((CMExpectedResultsGrid)this.getCell().getGrid()).getJPopupMenuVisualNotifier().getComponent(0)).setEnabled(false);
			  ((CMExpectedResultsGrid)this.getCell().getGrid()).getJPopupMenuVisualNotifier().show(getCell().getGrid().getParent().getParent().getParent(), 2, 14);
		
			  getCell().getGrid().requestFocus();
		  }
		  return c;
	}
	return super.stopCellEditing();
}

}
