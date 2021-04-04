package bi.view.cells.renderers;


import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.SystemColor;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import bi.view.cells.CMCellEquivalenceClassDescription;
import bi.view.cells.CMCellEquivalenceClassEffects;
import bi.view.cells.CMCellEquivalenceClassName;
import bi.view.cells.CMCellEquivalenceClassState;
import bi.view.cells.CMCellEquivalenceClassValue;
import bi.view.elementviews.CMElementViews;
import bi.view.grids.CMBaseJSmartGrid;
import bi.view.stdcombinationviews.CMDescriptionStdCombinationViews;
import bi.view.stdcombinationviews.CMStdCombinationViews;

import com.eliad.model.GridContext;
import com.eliad.model.defaults.DefaultGridCellRenderer;


public class CMStdCombinationCellRenderer extends DefaultGridCellRenderer {	
    



	public CMStdCombinationCellRenderer() {
    
    }
    
    public Component getComponent(Object value, boolean isSelected, boolean hasFocus, boolean isEditable, int row, int column, GridContext context) {
		Component c = super.getComponent(value, isSelected, hasFocus, isEditable, row, column, context);
		CMStdCombinationViews stdCombGrid=(CMStdCombinationViews)context.getComponent();
		
		if (((stdCombGrid.getFirstSelectedColumn() == column)&&(((CMBaseJSmartGrid)context.getComponent()).isFocusOwner()))
			&&(stdCombGrid.getM_CMElementAndStdCombinationViews().getM_CMStdCombinationStructureView().getM_CMDescriptionStdCombinationViews().getFirstSelectedRow()==column))
			setBackground(SystemColor.inactiveCaptionText);
		else{
			CMDescriptionStdCombinationViews descriptionGridView = ((CMStdCombinationViews)context.getComponent()).getM_CMElementAndStdCombinationViews().getM_CMStdCombinationStructureView().getM_CMDescriptionStdCombinationViews();
					
			  if (descriptionGridView.getFirstSelectedRow()==column && (descriptionGridView.isFocusOwner()))
			  {
				  setBackground(SystemColor.inactiveCaptionText);
			  }else
				  setBackground(SystemColor.WHITE);
			
		}
		
		CMElementViews ElementsViewGrid = ((CMStdCombinationViews)context.getComponent()).getLnkCMElementViews();
		int r = ElementsViewGrid.getFirstSelectedRow();
		int col = ElementsViewGrid.getFirstSelectedColumn();
		Object obj = ElementsViewGrid.getCellObjectAt(r,col);
		if (ElementsViewGrid.isFocusOwner() && ((obj instanceof CMCellEquivalenceClassName) || (obj instanceof CMCellEquivalenceClassDescription) || (obj instanceof CMCellEquivalenceClassEffects)
				|| (obj instanceof CMCellEquivalenceClassState) || (obj instanceof CMCellEquivalenceClassValue)))
		{
		  if (r==row)
		  {
			  setBackground(SystemColor.inactiveCaptionText);
		  }else
			  setBackground(SystemColor.WHITE);
		}	
		
		if (value instanceof bi.view.cells.headers.CMCellHeaderStdCombination){
			setBackground(new Color(36,38,116));
	        setForeground(new Color(252,254,252));
			setHorizontalAlignment(JLabel.LEFT);
			setFont(new Font("Dialog",Font.PLAIN,12));
			Cursor cursor = this.getCursor();
            this.setCursor(new Cursor(Cursor.HAND_CURSOR));
            
	        if( !isSelected) {
				setBorder(BorderFactory.createRaisedBevelBorder());
	        }
		}
			
		
		return c;
    }

	   
}	
  