package bi.view.dependencycombinationviews;

import bi.view.cells.CMCellEffectsInCombination;
import bi.view.cells.CMCellEquivalenceClassInCombination;
import bi.view.cells.CMGridRowColumn;
import bi.view.grids.CMBaseJSmartGrid;
import bi.view.grids.CMDefaultGridNavigationPolicy;

public class CMCombinationsGridNavPolicy extends CMDefaultGridNavigationPolicy{
	
	public CMCombinationsGridNavPolicy(CMBaseJSmartGrid p_Parent) {
		super(p_Parent);
	}
	public void goToNextCell(int row, int column) {		
		CMGridRowColumn x = findNextEditableCell(row, column);		
		((CMCombinationViews)getParent()).changeSelection(x.getRow(),x.getColumn(),false,false);
	}

	public CMGridRowColumn findNextEditableCell(int row, int column){			
		Object obj = ((CMCombinationViews)getParent()).getCmGridModel().getCellObjectAt(row,column);
		boolean cellEditable = false;
		while (!cellEditable){		
			if (row < ((CMCombinationViews)getParent()).getRowCount()-1){				
	    		row++;	
	    		obj = ((CMCombinationViews)getParent()).getCmGridModel().getCellObjectAt(row,column);
	    		if (obj instanceof CMCellEquivalenceClassInCombination || obj instanceof CMCellEffectsInCombination)
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
			
			if(column > ((CMCombinationViews)getParent()).getColumnCount()-1){	
				column=0;
				row=1;
			/*	cellEditable = ((CMCombinationViews)getParent()).getCmGridModel().isCellEditable(row,column);
				obj = ((CMCombinationViews)getParent()).getCmGridModel().getCellObjectAt(row,column);*/
			}
		}		
		return new CMGridRowColumn(row,column);
	}
}
