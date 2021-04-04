package bi.view.errorviews;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import model.CMError;
import bi.view.cells.CMErrorClosedByCellView;
import bi.view.cells.CMErrorClosingDateCellView;
import bi.view.cells.CMErrorDescriptionCellView;
import bi.view.cells.CMErrorErrorClassCellView;
import bi.view.cells.CMErrorIssueDateCellView;
import bi.view.cells.CMErrorIssuedByCellView;
import bi.view.cells.CMErrorNameCellView;
import bi.view.cells.CMErrorPriorityCellView;
import bi.view.cells.CMErrorStateCellView;
import bi.view.cells.CMErrorTestCasesCellView;

import com.eliad.swing.JSmartGridHeader;

/**
 ****************************************************************************** Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
    ///////////////////////////////////////////////////////////////////////////
	public class CMErrorGridViewColumnHeaderListener extends MouseAdapter {
      private boolean sortingOrder = true;
			public void mouseClicked(MouseEvent evt) {
				int columnIndex = ((JSmartGridHeader)evt.getSource()).getFirstSelectedColumn();
                if( columnIndex == -1) {
                  return;
                }

                CMErrorGridView activeGrid = (CMErrorGridView) ((JSmartGridHeader)evt.getSource()).getColumnActiveGrid();
                CMError selectedError = activeGrid.getSelectedCMError();
                if( selectedError == null) {
                  return;
                }
                CMErrorGridModel gridModel = activeGrid.getM_CMErrorGridModel();
				// Sort all the rows in descending order based on the
    			// values in the second column of the model
                sortingOrder = !sortingOrder;
                sortAllRowsBy(gridModel, columnIndex, sortingOrder);
                int rowIndex = activeGrid.getCMErrorCellViewIndex(selectedError);
                if( rowIndex >= 0) {
				  activeGrid.selectCMErrorCellView(rowIndex);
                }
			}

			// Regardless of sort order (ascending or descending), null values always appear last.
    		// colIndex specifies a column in model.
			public void sortAllRowsBy(CMErrorGridModel p_GridModel, int p_ColIndex, boolean p_SortingOrder) {
				Vector data = p_GridModel.getDataVector();

				Collections.sort(data, new CMErrorGridViewColumnSorter(p_ColIndex, p_SortingOrder));
			    p_GridModel.setDataVector(data);
                p_GridModel.fireGridModelChanged();
			}


		// This comparator is used to sort vectors of data
			public class CMErrorGridViewColumnSorter implements Comparator {
				int colIndex;
				boolean ascending;
                int rowIndex = 0;
				CMErrorGridViewColumnSorter(int colIndex, boolean ascending) {
					this.colIndex = colIndex;
					this.ascending = ascending;
				}
				public int compare(Object a, Object b) {
					Vector v1 = (Vector)a;
					Vector v2 = (Vector)b;
					Object o1 = this.getValueOfObject(v1.get(colIndex));
					Object o2 = this.getValueOfObject(v2.get(colIndex));
			
					// Treat empty strains like nulls
					if (o1 instanceof String && ((String)o1).length() == 0) {
						o1 = null;
					}
					if (o2 instanceof String && ((String)o2).length() == 0) {
						o2 = null;
					}
					// Sort nulls so they appear last, regardless
					// of sort order
					if (o1 == null && o2 == null) {
						return 0;
					} else if (o1 == null) {
						return 1;
					} else if (o2 == null) {
						return -1;
					} else if (o1 instanceof Comparable) {
						if (ascending) {
                            int value =  ((Comparable)o1).compareTo(o2);
                            return value;
						} else {
							int value = ((Comparable)o2).compareTo(o1);
                            return value;
						}
					} else {
						if (ascending) {
							return o1.toString().compareTo(o2.toString());
						} else {
							return o2.toString().compareTo(o1.toString());
						}
					}
				}

                private Object getValueOfObject(Object p_Obj) {
					  if( p_Obj == null) { return null; }
					  if( p_Obj instanceof CMErrorNameCellView) {
						CMErrorNameCellView nameView = (CMErrorNameCellView) p_Obj;
						return nameView.getM_CMErrorCellView().getM_CMError().getM_Name();
					  }
					  else if( p_Obj instanceof CMErrorDescriptionCellView) {
						CMErrorDescriptionCellView descriptionView = (CMErrorDescriptionCellView) p_Obj;
						return descriptionView.getM_CMErrorCellView().getM_CMError().getM_Description();
					  }
					  else if( p_Obj instanceof CMErrorStateCellView) {
						CMErrorStateCellView stateView = (CMErrorStateCellView) p_Obj;
						return stateView.getM_CMErrorCellView().getM_CMError().getM_State();
					  }
					  else if( p_Obj instanceof CMErrorErrorClassCellView) {
						CMErrorErrorClassCellView errorClassView = (CMErrorErrorClassCellView) p_Obj;
						return errorClassView.getM_CMErrorCellView().getM_CMError().getM_ErrorClass();
					  }
					  else if( p_Obj instanceof CMErrorPriorityCellView) {
						CMErrorPriorityCellView priorityView = (CMErrorPriorityCellView) p_Obj;
						return priorityView.getM_CMErrorCellView().getM_CMError().getM_Priority();
					  }
					  else if( p_Obj instanceof CMErrorIssueDateCellView) {
						CMErrorIssueDateCellView issueDateView = (CMErrorIssueDateCellView) p_Obj;
						return issueDateView.getM_CMErrorCellView().getM_CMError().getM_IssueDate();
					  }
					  else if( p_Obj instanceof CMErrorIssuedByCellView) {
						CMErrorIssuedByCellView issuedByView = (CMErrorIssuedByCellView) p_Obj;
						return issuedByView.getM_CMErrorCellView().getM_CMError().getM_IssuedBy();
					  }
					  else if( p_Obj instanceof CMErrorClosingDateCellView) {
						CMErrorClosingDateCellView closingDateView = (CMErrorClosingDateCellView) p_Obj;
						return closingDateView.getM_CMErrorCellView().getM_CMError().getM_ClosingDate();
					  }
					  else if( p_Obj instanceof CMErrorClosedByCellView) {
						CMErrorClosedByCellView closedByView = (CMErrorClosedByCellView) p_Obj;
						return closedByView.getM_CMErrorCellView().getM_CMError().getM_ClosedBy();
					  }
					  else if( p_Obj instanceof CMErrorTestCasesCellView) {
						CMErrorTestCasesCellView testCasesView = (CMErrorTestCasesCellView) p_Obj;
						Vector testCases = testCasesView.getM_CMErrorCellView().getM_CMError().getM_TestCases();
						return CMErrorGridModel.constructStringListOfTestCases(testCases);
					  }
					  else {
						return null;
					  }
                }
			}
            ////////////////////////////////////////////////////////////////////
		}





