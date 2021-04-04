package bi.view.cells.renderers;


import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.SystemColor;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import bi.view.cells.CMCellElementDescription;
import bi.view.cells.CMCellElementName;
import bi.view.cells.CMCellEmpty;
import bi.view.cells.CMCellEquivalenceClassDescription;
import bi.view.cells.CMCellEquivalenceClassEffects;
import bi.view.cells.CMCellEquivalenceClassName;
import bi.view.cells.CMCellEquivalenceClassState;
import bi.view.cells.CMCellEquivalenceClassValue;
import bi.view.cells.CMCellHeaderTestCase;
import bi.view.cells.headers.CMCellHeaderElementDescription;
import bi.view.cells.headers.CMCellHeaderElementName;
import bi.view.cells.headers.CMCellHeaderEquivalenceClassDescription;
import bi.view.cells.headers.CMCellHeaderEquivalenceClassEffects;
import bi.view.cells.headers.CMCellHeaderEquivalenceClassName;
import bi.view.cells.headers.CMCellHeaderEquivalenceClassState;
import bi.view.cells.headers.CMCellHeaderEquivalenceClassValue;
import bi.view.elementviews.CMElementViews;
import bi.view.grids.CMBaseJSmartGrid;
import bi.view.testcaseviews.CMDescriptionTestCaseViews;
import bi.view.testcaseviews.CMTestCaseViews;

import com.eliad.model.GridContext;
import com.eliad.model.defaults.DefaultGridCellRenderer;


public class CMTestCaseCellRenderer extends DefaultGridCellRenderer {	
    
	

	public CMTestCaseCellRenderer() {
    
    }
    
    public Component getComponent(Object value, boolean isSelected, boolean hasFocus, boolean isEditable, int row, int column, GridContext context) {
		Component c = super.getComponent(value, isSelected, hasFocus, isEditable, row, column, context);
		CMTestCaseViews testCasesGrid=(CMTestCaseViews)context.getComponent();
		
		if (((testCasesGrid.getFirstSelectedColumn() == column)&&(((CMBaseJSmartGrid)context.getComponent()).isFocusOwner()))
			&& (testCasesGrid.getM_CMElementAndTestCaseViews().getM_CMTestCaseStructureView().getM_CMDescriptionTestCaseViews().getFirstSelectedRow()==column))
			setBackground(SystemColor.inactiveCaptionText);
		else{
			CMDescriptionTestCaseViews descriptionGridView = ((CMTestCaseViews)context.getComponent()).getM_CMElementAndTestCaseViews().getM_CMTestCaseStructureView().getM_CMDescriptionTestCaseViews();
			  if ((descriptionGridView.getFirstSelectedRow()==column)&& (descriptionGridView.isFocusOwner()))
			  {
				  setBackground(SystemColor.inactiveCaptionText);
			  }else
				  setBackground(SystemColor.WHITE);
			}
		
		
		CMElementViews elementsViewGrid = ((CMTestCaseViews)context.getComponent()).getLnkCMElementViews();
		int r = elementsViewGrid.getFirstSelectedRow();
		int col = elementsViewGrid.getFirstSelectedColumn();
		Object obj = elementsViewGrid.getCellObjectAt(r,col);
		if (elementsViewGrid.isFocusOwner() && ((obj instanceof CMCellEquivalenceClassName) || (obj instanceof CMCellEquivalenceClassDescription) || (obj instanceof CMCellEquivalenceClassEffects)
				|| (obj instanceof CMCellEquivalenceClassState) || (obj instanceof CMCellEquivalenceClassValue)))
		{			
		  if (r==row)
		  {
			  setBackground(SystemColor.inactiveCaptionText);			  
		  }else
			  setBackground(SystemColor.WHITE);
		}
		else{
			if (elementsViewGrid.isFocusOwner() && ((obj instanceof CMCellHeaderEquivalenceClassName) || (obj instanceof CMCellHeaderEquivalenceClassDescription) || (obj instanceof CMCellHeaderEquivalenceClassEffects)
			|| (obj instanceof CMCellHeaderEquivalenceClassState) || (obj instanceof CMCellHeaderEquivalenceClassValue)
			|| (obj instanceof CMCellElementDescription) || (obj instanceof CMCellEmpty) || (obj instanceof CMCellElementName)
			|| (obj instanceof CMCellHeaderElementDescription) || (obj instanceof CMCellHeaderElementName)
			)){
				setBackground(SystemColor.WHITE);
				
			}
			
		}
		if (value instanceof CMCellHeaderTestCase){
			setBackground(new Color(36,38,116));
	        setForeground(new Color(252,254,252));
			setHorizontalAlignment(JLabel.LEFT);
			setFont(new Font("Dialog",Font.PLAIN,12));
			Cursor cursor = this.getCursor();
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            
	        if( !isSelected) {
				setBorder(BorderFactory.createRaisedBevelBorder());
	        }
		}
	
		return c;
    }

	   
}	
  