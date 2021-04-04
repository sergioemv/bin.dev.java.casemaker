/*
 * Revision: Created: 02-03-2005
 *
 * Developed by BUSINESS SOFTWARE INNOVATIONS. Copyright (c)2003 Díaz und Hilterscheid
 * Unternehmensberatung. All rights reserved.
 *
 */
package bi.view.testdataviews.grid;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.eliad.swing.JSmartGrid;
import com.eliad.swing.JSmartGridHeader;

/**
 * @author Franz Nava
 * @version Revision: Date: 02-03-2005 06:17:42 PM
 */
public class ColumnHeaderListener extends MouseAdapter {

    /**
     * Holds the sorter order, i. e., if it is ascendent or descendent.
     */
    private boolean order = true;

    /**
     * Holds the last column accessed.
     */
    private static int column = -1;

    /**
     * Sort all the rows in descending order based on the
     * values in the column clicked of the model.
     *
     * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
     */
    public void mouseClicked(MouseEvent e) {
        int column = ((JSmartGridHeader) e.getSource()).getFirstSelectedColumn();
        if (column == -1) {
            return;
        }

        JSmartGrid activeGrid = (JSmartGrid) ((JSmartGridHeader) e.getSource()).getColumnActiveGrid();
        GridModelTestData gridModel = (GridModelTestData) activeGrid.getModel();

        if (gridModel.getRowCount() == 0) {
            return;
        }
        if (column != ColumnHeaderListener.column) {
            order = true;
            ColumnHeaderListener.column = column;
        }

        List selected = gridModel.getElements(activeGrid.getSelectedRows());

        order = !order;
        sortAllRowsBy(gridModel, column, order);

        activeGrid.clearSelection();
        for (Iterator iterator = selected.iterator(); iterator.hasNext();) {
            int index = gridModel.getIndex(iterator.next());
            if (index >= 0) {
                activeGrid.changeSelection(index, 0, true, false);
            }
        }
    }

    /**
     * Regardless of sort order (ascending or descending), null values always appear last.
     * <code>column</code> specifies a column in model.
     *
     * @param gridModel
     * @param column
     * @param order
     */
    @SuppressWarnings("unchecked")
	public void sortAllRowsBy(GridModelTestData gridModel, int column, boolean order) {
        List data = gridModel.getDataModel();

        switch (column) {
        case ColumnHeaderModel.TESTDATA_NAME:
            Collections.sort(data, new TestDataNameComparator(order));
            break;
        case ColumnHeaderModel.TESTDATA_LEVEL:
            Collections.sort(data, new TestDataRiskComparator(order));
            break;
        case ColumnHeaderModel.TESTDATA_SET:
            Collections.sort(data, new TestDataSetComparator(order));
            break;
            //svonborries_05042006_begin
        case ColumnHeaderModel.TESTDATA_TESTCASE:
        	Collections.sort(data, new TestDataTestCaseComparator(order));
        	break;
        	//svonborries_05042006_end
        default:
            break;
        }

        gridModel.setDataModel(data);
        gridModel.fireGridRowsChanged(0, data.size());
    }
}