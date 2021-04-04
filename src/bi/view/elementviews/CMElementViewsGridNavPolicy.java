package bi.view.elementviews;

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


public class CMElementViewsGridNavPolicy extends CMDefaultGridNavigationPolicy {

		public CMElementViewsGridNavPolicy(CMBaseJSmartGrid p_Parent) {
			super(p_Parent);
		}
		
	public void goToNextCell(int row, int column) {		
		CMGridRowColumn x = findNextEditableCell(row, column);	
		//((CMJSmartGridFocusChangeable)((CMDependencyElementViews)getParent())).changeSelection(x.getRow(),x.getColumn(),false,false);
		((CMElementViews)getParent()).changeSelection(x.getRow(),x.getColumn(),false,false);
	}

	public CMGridRowColumn findNextEditableCell(int row, int column){	
		boolean l_isCellEditable = false;
		while (!l_isCellEditable){		
			Object obj= ((CMElementViews)getParent()).getCellObjectAt(row,column);
			if (obj instanceof CMCellHeaderElementName ||  obj instanceof CMCellHeaderElementDescription
					|| obj instanceof CMCellHeaderEquivalenceClassDescription || obj instanceof CMCellHeaderEquivalenceClassValue						
					|| obj instanceof CMCellHeaderEquivalenceClassName || obj instanceof CMCellHeaderEquivalenceClassState)
			{
				row++;
				column=0;
				l_isCellEditable = ((CMElementViews)getParent()).isCellEditable(row, column, true);
				
			}
			else{				
				if (row > ((CMElementViews)getParent()).getRowCount()-1){
					row = 0;
					column = 0;
				}
				else{
					
					if (column < ((CMElementViews)getParent()).getColumnCount()-1){						
						if (obj instanceof CMCellElementDescription || obj instanceof CMCellEquivalenceClassDescription){							
							row++;
							column=0;						
							l_isCellEditable = ((CMElementViews)getParent()).isCellEditable(row, column, true);
							
						}
						else{
							l_isCellEditable = ((CMElementViews)getParent()).isCellEditable(row, column+1, true);
							if (!l_isCellEditable)
								column++;
							else{									
								column++;
								break;
							}
						}			
							
					}
					else{
						if ((row >= ((CMElementViews)getParent()).getRowCount()-1) && (column >= ((CMElementViews)getParent()).getColumnCount()-1)){
							row=0;
							column=0;
						}
						else{
							row++;
							column=0;
						}
						l_isCellEditable = ((CMElementViews)getParent()).isCellEditable(row, column, true);	
							
					}
				}			
			}		
		}
				
			
			
		return new CMGridRowColumn(row,column);
	}
}