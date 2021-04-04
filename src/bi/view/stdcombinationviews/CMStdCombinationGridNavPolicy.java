package bi.view.stdcombinationviews;

import bi.view.cells.CMCellEquivalenceClassInStdCombination;
import bi.view.cells.CMGridRowColumn;
import bi.view.grids.CMBaseJSmartGrid;
import bi.view.grids.CMDefaultGridNavigationPolicy;

public class CMStdCombinationGridNavPolicy extends CMDefaultGridNavigationPolicy{
	
	public CMStdCombinationGridNavPolicy(CMBaseJSmartGrid p_Parent) {
		super(p_Parent);
	}
	public void goToNextCell(int row, int column) {		
		CMGridRowColumn x = findNextEditableCell(row, column);		
		((CMStdCombinationViews)getParent()).changeSelection(x.getRow(),x.getColumn(),false,false);
	}

	public CMGridRowColumn findNextEditableCell(int row, int column){			
		Object obj = ((CMStdCombinationViews)getParent()).getCmGridModel().getCellObjectAt(row,column);
		boolean cellEditable = false;
		while (!cellEditable){		
			if (row < ((CMStdCombinationViews)getParent()).getRowCount()-1){				
	    		row++;	
	    		obj = ((CMStdCombinationViews)getParent()).getCmGridModel().getCellObjectAt(row,column);
	    		if (obj instanceof CMCellEquivalenceClassInStdCombination)
	    			cellEditable = true;
	    		else
	    			cellEditable = false;
	    	/*	cellEditable = ((CMCombinationViews)getParent()).getCmGridModel().isCellEditable(row,column);
	    		obj = ((CMCombinationViews)getParent()).getCmGridModel().getCellObjectAt(row,column);*/
	    	}
	    	else{
	    		row=0;
	    		column++;
	    	/*	cellEditable = ((CMCombinationViews)getParent()).getCmGridModel().isCellEditable(row,column);
	    		obj = ((CMCombinationViews)getParent()).getCmGridModel().getCellObjectAt(row,column);*/
	    	}
			
			if(column > ((CMStdCombinationViews)getParent()).getColumnCount()-1){	
				column=0;
				row=1;
			/*	cellEditable = ((CMCombinationViews)getParent()).getCmGridModel().isCellEditable(row,column);
				obj = ((CMCombinationViews)getParent()).getCmGridModel().getCellObjectAt(row,column);*/
			}
		}		
		return new CMGridRowColumn(row,column);
	}
}
