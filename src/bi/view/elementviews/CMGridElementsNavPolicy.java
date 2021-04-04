package bi.view.elementviews;

import bi.view.cells.CMCellElementDescription;
import bi.view.cells.CMCellEquivalenceClassEffects;
import bi.view.cells.CMGridRowColumn;
import bi.view.cells.headers.CMCellHeaderElementDescription;
import bi.view.cells.headers.CMCellHeaderElementGuiObject;
import bi.view.cells.headers.CMCellHeaderElementName;
import bi.view.cells.headers.CMCellHeaderEquivalenceClassDescription;
import bi.view.cells.headers.CMCellHeaderEquivalenceClassName;
import bi.view.cells.headers.CMCellHeaderEquivalenceClassEffects;
import bi.view.cells.headers.CMCellHeaderEquivalenceClassState;
import bi.view.cells.headers.CMCellHeaderEquivalenceClassValue;
import bi.view.grids.CMBaseJSmartGrid;
import bi.view.grids.CMDefaultGridNavigationPolicy;

public class CMGridElementsNavPolicy extends CMDefaultGridNavigationPolicy {

	public CMGridElementsNavPolicy(CMBaseJSmartGrid p_Parent) {
		super(p_Parent);
	}
	
public void goToNextCell(int row, int column) {
	
	CMGridRowColumn x = findNextEditableCell(row, column);	
	((CMGridElements)getParent()).changeSelection(x.getRow(),x.getColumn(),false,false);
}

public CMGridRowColumn findNextEditableCell(int row, int column){	
	boolean l_isCellEditable = false;	
	while (!l_isCellEditable){		
		Object obj= ((CMGridElements)getParent()).getCellObjectAt(row,column+1);
		
		if (obj instanceof CMCellHeaderElementDescription || obj instanceof CMCellHeaderEquivalenceClassValue
				|| obj instanceof CMCellHeaderElementName || obj instanceof CMCellHeaderElementGuiObject
				|| obj instanceof CMCellHeaderEquivalenceClassDescription || obj instanceof CMCellHeaderEquivalenceClassEffects
				|| obj instanceof CMCellHeaderEquivalenceClassName || obj instanceof CMCellHeaderEquivalenceClassState)
		{
			row++;
			column=0;
			l_isCellEditable = ((CMGridElements)getParent()).isCellEditable(row, column, true);
		}
		else{			
			if (row > ((CMGridElements)getParent()).getRowCount()-1){
				row = 0;
				column = 0;
			}
			else{
				if (column < ((CMGridElements)getParent()).getColumnCount()){
					obj= ((CMGridElements)getParent()).getCellObjectAt(row,column);
					if (obj instanceof CMCellElementDescription || obj instanceof CMCellEquivalenceClassEffects){
						row++;
						column=0;						
						l_isCellEditable = ((CMGridElements)getParent()).isCellEditable(row, column, true);
					}
					else{
						obj= ((CMGridElements)getParent()).getCellObjectAt(row,column+1);
						if (obj instanceof CMCellEquivalenceClassEffects){
							column++;
							l_isCellEditable = true;
							break;
						}
						else
							l_isCellEditable = ((CMGridElements)getParent()).isCellEditable(row, column+1, true);
						if (!l_isCellEditable)
							column++;
						else{
							column++;
							break;
						}
					}			
						
				}
				else{
					column=0;
				}
			}			
		}		
	}
	return new CMGridRowColumn(row,column);
}


}

