/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.view.actions.effect;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.util.Arrays;
import java.util.HashMap;

import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

import model.ExpectedResult;

import org.apache.log4j.Logger;

import bi.view.cells.CMBaseCell;
import bi.view.cells.CMCellExpectedResultName;
import bi.view.cells.CMCellExpectedResultValue;
import bi.view.grids.CMBaseJSmartGrid;
import bi.view.grids.CMDefaultGridNavigationPolicy;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;

import com.eliad.model.GenericGridModel;
import com.eliad.model.defaults.DefaultStyleModel;
import com.eliad.swing.DefaultHeaderModel;
import com.eliad.swing.JSmartGridHeader;

/**
 * @author smoreno
 *
 */
public class CMExpectedResultsGrid extends CMBaseJSmartGrid {

	private GenericGridModel expectedResultsGridModel = null;  //  @jve:decl-index=0:visual-constraint=""
	private JSmartGridHeader gridExpectedResultsHeader;
	private DefaultHeaderModel  hmodel;
	private DefaultStyleModel defaultStyleModel = null;  //  @jve:decl-index=0:visual-constraint=""
	private JPopupMenu jPopupMenuVisualNotifier = null;  //  @jve:decl-index=0:visual-constraint="31,5"

/**
 *
 */
public CMExpectedResultsGrid() {
	super();
	initGUI();
}

/**
 *
 */
private void initGUI() {
//	setSelectionUnit(com.eliad.swing.JSmartGrid.UNIT_CELL);
	setToolTipText(CMMessages.getString("EXPECTED_RESULTS_GRID_TOOLTIP")); //$NON-NLS-1$
	setSelectionCellBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.orange,3));
	setFocusHighlightBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.orange,2));
	//setAutoCreateRowHeader(true);
	//gridExpectedResults.setAutoCreateColumnHeader(true);
	setColumnMargin(0);
	setSelectionPolicy(com.eliad.swing.JSmartGrid.POLICY_SINGLE);
	setModel(getExpectedResultsGridModel());
	//setColumnHeader(getGridColumnHeader(this));
	setStyleModel(getDefaultStyleModel());
	setGridNavigationPolicy(new CMDefaultGridNavigationPolicy(this));
    initializeCellRenderers();
    initializeCellEditors();
    addKeyListener(new KeyAdapter(){
    	public void keyPressed(java.awt.event.KeyEvent e) { repaint();
    	if (getJPopupMenuVisualNotifier().isVisible())
    		getJPopupMenuVisualNotifier().setVisible(false);};
    });
   //setGridNavigationPolicy(defaultGridNav);

}
/**
 * This method initializes genericGridModel
 *
 * @return com.eliad.model.GenericGridModel
 */
public GenericGridModel getExpectedResultsGridModel() {
	if (expectedResultsGridModel == null) {
		expectedResultsGridModel = new GenericGridModel(0,2);

	}
	return expectedResultsGridModel;
}
/**
 * @param p_gridExpectedResults
 * @return
 */

/**
 * This method initializes defaultStyleModel
 *
 * @return com.eliad.model.defaults.DefaultStyleModel
 */
private DefaultStyleModel getDefaultStyleModel() {
	if (defaultStyleModel == null) {
		defaultStyleModel = new DefaultStyleModel();

	}
	return defaultStyleModel;
}

/**
 *  Adds a new row in the bottom of the grid
 */
public void addNewRow()
{
	if (!checkExpectedResultsPreconditions())
		return;
	ExpectedResult eResult = new ExpectedResult(); //$NON-NLS-1$ //$NON-NLS-2$
	java.util.List<CMBaseCell> cells = Arrays.asList(new CMCellExpectedResultName(this,eResult),new CMCellExpectedResultValue(this,eResult)); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	this.getExpectedResultsGridModel().addRow(cells.toArray());
	this.getSelectionModel().clearSelection();
	changeSelection(this.getExpectedResultsGridModel().getRowCount()-1,0,false,false);
	this.requestFocus();
}
/**
 * @return
 */
private boolean checkExpectedResultsPreconditions() {

	//check for empty keys
	String sep = "**********%%%%@@@@***********/////////********"; //$NON-NLS-1$
	String keys = sep;
	for (int i = 0; i<this.getRowCount();i++)
	{

		if (getValueAt(i,0).toString().equalsIgnoreCase("")) //$NON-NLS-1$
		{
			JOptionPane.showMessageDialog(CMApplication.frame,CMMessages.getString("EXPECTED_RESULTS_CANNOT_INSERT_EMPTY_NAME"),CMMessages.getString("EXPECTED_RESULTS_ERROR_TITLE"),JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$ //$NON-NLS-2$
			return false;
		}
		//check for repeated keys
		if (keys.contains(sep+getValueAt(i,0).toString()+sep))
		{
			JOptionPane.showMessageDialog(CMApplication.frame,CMMessages.getString("EXPECTED_RESULTS_CANNOT_INSERT_DUPLPICATE_NAME"),CMMessages.getString("EXPECTED_RESULTS_ERROR_TITLE"),JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$ //$NON-NLS-2$
			return false;
		}
		else
			keys = keys + getValueAt(i,0).toString() + sep;
	}
	//check for repeated keys
	return true;
}

protected HashMap getCellClasses() {
	HashMap map = new HashMap();
	map.put(CMCellExpectedResultName.class,new JTextField());
	map.put(CMCellExpectedResultValue.class,new JTextField());
	//map.put()
	return map;
}

/**
 * Tries to delete the selected row
 */
public void deleteSelectedRow() {
	int selectedIndex = this.getSelectionModel().getLastSelectedRow();
	try
	{
	this.getExpectedResultsGridModel().removeRows(selectedIndex,1);
	selectedIndex--;
	if (selectedIndex>=0)
		changeSelection(selectedIndex,0,false,false);
	this.requestFocus();
	} catch (ArrayIndexOutOfBoundsException e) {
		//catched
		Logger.getLogger(this.getClass()).warn("Empty Selection"); //$NON-NLS-1$
	}
}

/**
 * This method initializes jPopupMenuVisualNotifier	
 * 	
 * @return javax.swing.JPopupMenu	
 */
public JPopupMenu getJPopupMenuVisualNotifier() {
	if (jPopupMenuVisualNotifier == null) {
		jPopupMenuVisualNotifier = new JPopupMenu();
		jPopupMenuVisualNotifier.setBackground(Color.ORANGE);
		
	}
	return jPopupMenuVisualNotifier;
}


}
