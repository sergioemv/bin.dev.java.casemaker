package bi.view.dependencycombinationviews;

import bi.view.cells.CMCellDependencyName;
import bi.view.cells.CMCellElementDescription;
import bi.view.cells.CMCellEquivalenceClassDescription;
import bi.view.cells.CMGridRowColumn;
import bi.view.cells.headers.CMCellHeaderElementDescription;
import bi.view.cells.headers.CMCellHeaderElementName;
import bi.view.cells.headers.CMCellHeaderEquivalenceClassDescription;
import bi.view.cells.headers.CMCellHeaderEquivalenceClassName;
import bi.view.cells.headers.CMCellHeaderEquivalenceClassState;
import bi.view.cells.headers.CMCellHeaderEquivalenceClassValue;
import bi.view.grids.CMBaseJSmartGrid;
import bi.view.grids.CMDefaultGridNavigationPolicy;


public class CMDependencyElementGridNavPolicy extends CMDefaultGridNavigationPolicy {

		public CMDependencyElementGridNavPolicy(CMBaseJSmartGrid p_Parent) {
			super(p_Parent);
		}
		
	public void goToNextCell(int row, int column) {		
		CMGridRowColumn x = findNextEditableCell(row, column);	
		//((CMJSmartGridFocusChangeable)((CMDependencyElementViews)getParent())).changeSelection(x.getRow(),x.getColumn(),false,false);
		((CMDependencyElementViews)getParent()).changeSelection(x.getRow(),x.getColumn(),false,false);
	}

	public CMGridRowColumn findNextEditableCell(int row, int column){	
		boolean l_isCellEditable = false;
		Object obj1 = ((CMDependencyElementViews)getParent()).getCmGridModel().getCellObjectAt(row,column);
		if (obj1 instanceof CMCellDependencyName){
			row++;
			column = 5;
		}
		else{
			while (!l_isCellEditable){		
				Object obj= ((CMDependencyElementViews)getParent()).getCmGridModel().getCellObjectAt(row,column+1);
				if (obj == null){
					row++;
					column=0;
					l_isCellEditable = ((CMDependencyElementViews)getParent()).isCellEditable(row, column, true);
					
				}
				else{
					if (obj instanceof CMCellHeaderElementName ||  obj instanceof CMCellHeaderElementDescription
							|| obj instanceof CMCellHeaderEquivalenceClassDescription || obj instanceof CMCellHeaderEquivalenceClassValue						
							|| obj instanceof CMCellHeaderEquivalenceClassName || obj instanceof CMCellHeaderEquivalenceClassState)
					{
						row++;
						column=0;
						l_isCellEditable = ((CMDependencyElementViews)getParent()).isCellEditable(row, column, true);
						
					}
					else{						
						if (row > ((CMDependencyElementViews)getParent()).getRowCount()-3){
							row = 0;
							column = 0;
						}
						else{						
							if (column < ((CMDependencyElementViews)getParent()).getColumnCount()-1){
								obj= ((CMDependencyElementViews)getParent()).getCmGridModel().getCellObjectAt(row,column);
								if (obj instanceof CMCellElementDescription || obj instanceof CMCellEquivalenceClassDescription){
									if (obj instanceof CMCellEquivalenceClassDescription);
									else
										row++;
									column=5;						
									l_isCellEditable = ((CMDependencyElementViews)getParent()).isCellEditable(row, column, true);
									
								}
								else{
									l_isCellEditable = ((CMDependencyElementViews)getParent()).isCellEditable(row, column+1, true);
									if (!l_isCellEditable)
										column++;
									else{									
										column++;
										break;
									}
								}			
									
							}
							else{
								if ((row > ((CMDependencyElementViews)getParent()).getRowCount()-2) && (column < ((CMDependencyElementViews)getParent()).getColumnCount()-1)){
									row=0;
									column=0;
								}
								else
									column=0;
							}
						}			
					}		
				}
			}
		}
		
			
			
		return new CMGridRowColumn(row,column);
	}

//	private CMGridRowColumn findNextEditableDependencyCell(int row, int column) {
//		Object obj= ((CMDependencyElementViews)getParent()).getCmGridModel().getCellObjectAt(row,column);
//		boolean l_isCellEditable = false;
//		while (!l_isCellEditable){
//			if (obj instanceof CMCellHeaderDependencyName || obj instanceof CMCellDependencyName || obj instanceof CMCellSelectAllEquivalenceClassesOfElement || obj instanceof CMCellSelectEquivalenceClass){
//				l_isCellEditable = ((CMDependencyElementViews)getParent()).isCellEditable(row+1, column, true);
//				if (!l_isCellEditable)
//					row++;
//			}
//			else{
//				if (obj instanceof CMCellEmpty){
//					
//				}
//			}
//		}
//		
//		return new CMGridRowColumn(row,column);
//	}
//
//
	}

