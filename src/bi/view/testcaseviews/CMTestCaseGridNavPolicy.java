package bi.view.testcaseviews;

import bi.view.cells.CMCellEquivalenceClassInTestCase;
import bi.view.cells.CMGridRowColumn;
import bi.view.grids.CMBaseJSmartGrid;
import bi.view.grids.CMDefaultGridNavigationPolicy;

public class CMTestCaseGridNavPolicy extends CMDefaultGridNavigationPolicy{
	
	public CMTestCaseGridNavPolicy(CMBaseJSmartGrid p_Parent) {
		super(p_Parent);
	}
	public void goToNextCell(int row, int column) {		
		CMGridRowColumn x = findNextEditableCell(row, column);		
		((CMTestCaseViews)getParent()).changeSelection(x.getRow(),x.getColumn(),false,false);
	}

	public CMGridRowColumn findNextEditableCell(int row, int column){			
		Object obj = ((CMTestCaseViews)getParent()).getCMGridModel().getCellObjectAt(row,column);
		boolean cellEditable = false;
		while (!cellEditable){		
			if (row < ((CMTestCaseViews)getParent()).getRowCount()-1){				
	    		row++;	
	    		obj = ((CMTestCaseViews)getParent()).getCMGridModel().getCellObjectAt(row,column);
	    		if (obj instanceof CMCellEquivalenceClassInTestCase)
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
			
			if(column > ((CMTestCaseViews)getParent()).getColumnCount()-1){	
				column=0;
				row=1;
			/*	cellEditable = ((CMCombinationViews)getParent()).getCmGridModel().isCellEditable(row,column);
				obj = ((CMCombinationViews)getParent()).getCmGridModel().getCellObjectAt(row,column);*/
			}
		}		
		return new CMGridRowColumn(row,column);
	}
}
