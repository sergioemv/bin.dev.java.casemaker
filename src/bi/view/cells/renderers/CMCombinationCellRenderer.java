package bi.view.cells.renderers;


import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.SystemColor;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import model.Combination;
import bi.view.cells.CMCellEffectsInCombination;
import bi.view.cells.headers.CMCellHeaderCombination;
import bi.view.cells.headers.CMCellHeaderEffectsInCombination;
import bi.view.dependencycombinationviews.CMCombinationViews;
import bi.view.grids.CMBaseJSmartGrid;

import com.eliad.model.GridContext;
import com.eliad.model.defaults.DefaultGridCellRenderer;


public class CMCombinationCellRenderer extends DefaultGridCellRenderer {	
    
	
    
    public Component getComponent(Object value, boolean isSelected, boolean hasFocus, boolean isEditable, int row, int column, GridContext context) {
		Component c = super.getComponent(value, isSelected, hasFocus, isEditable, row, column, context);
		Color background = new Color(36,38,116);
		if (value instanceof CMCellHeaderCombination)
			if (((CMCellHeaderCombination)value).getCombination().getOriginType() == Combination.Origin.MANUAL)
				background = new Color(156,154,252);
		
			CMCombinationViews combinationgrid =(CMCombinationViews) context.getComponent();
			if ((combinationgrid.getFirstSelectedColumn() == column)&&(((CMBaseJSmartGrid)context.getComponent()).isFocusOwner()))
			
				setBackground(SystemColor.inactiveCaptionText);
			else{
				setBackground(SystemColor.WHITE);				
			}
						
		if (value instanceof CMCellHeaderCombination || value instanceof CMCellHeaderEffectsInCombination){
			setBackground(background);
	        setForeground(new Color(252,254,252));
			setHorizontalAlignment(JLabel.LEFT);
			setFont(new Font("Dialog",Font.PLAIN,12));
			Cursor cursor = this.getCursor();
            this.setCursor(new Cursor(Cursor.HAND_CURSOR));
            
	        if( !isSelected) {
				setBorder(BorderFactory.createRaisedBevelBorder());
	        }
		}
			
		if( value instanceof CMCellEffectsInCombination) {
            setBackground(new Color(235, 235, 228));
            if (value.toString().equalsIgnoreCase("")) {
            	setHorizontalAlignment(JLabel.CENTER);
            	setBorder(BorderFactory.createEtchedBorder(1));
            }
            else {
            	setHorizontalAlignment(JLabel.LEFT);
            	setBorder(BorderFactory.createEmptyBorder());
            }
            if (isSelected)
            	setBorder(BorderFactory.createLineBorder(Color.BLACK));
          }
		
		return c;
    }

	   
}	
  