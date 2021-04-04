package bi.view.cells;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.SystemColor;

import com.eliad.model.GridContext;
import com.eliad.model.defaults.DefaultGridCellRenderer;

public class CMCellSpecialEquivalenceClassRenderer extends DefaultGridCellRenderer{
	public Component getComponent(Object value, boolean isSelected, boolean hasFocus, boolean isEditable, int row, int column, GridContext context) {
		
		Component c = super.getComponent(value, isSelected, hasFocus, isEditable, row, column, context);	      

		if( value instanceof CMCellEquivalenceClassName || value instanceof CMCellDependencyName ||
                value instanceof CMCellEffectsInCombination) {            	
            setBackground(new Color(235, 235, 228));
       }
		else if (value instanceof CMCellEquivalenceClassState || value instanceof CMCellEquivalenceClassValue || value instanceof CMCellEquivalenceClassDescription){
			setBackground(SystemColor.WHITE);
		}
       else if( value instanceof CMCellEquivalenceClassInCombination) {
         setFont(new Font("Serif",Font.BOLD,12));
       }
		
		if(value instanceof CMCellEquivalenceClassName)
        {
            setForeground(new Color(0,0,0));
	  		setFont(new Font("Dialog",Font.PLAIN,12));
        }
		else if( value instanceof CMCellEquivalenceClassInCombination) {
            setFont(new Font("Serif",Font.BOLD,12));
          }
        return c;
	}
}
