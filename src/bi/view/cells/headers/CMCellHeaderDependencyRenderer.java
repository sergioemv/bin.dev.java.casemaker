package bi.view.cells.headers;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.BorderFactory;

import com.eliad.model.GridContext;
import com.eliad.model.defaults.DefaultGridCellRenderer;

public class CMCellHeaderDependencyRenderer extends DefaultGridCellRenderer{
	
	public Component getComponent(Object value, boolean isSelected, boolean hasFocus, boolean isEditable, int row, int column, GridContext context) {
		Component c = super.getComponent(value, isSelected, hasFocus, isEditable, row, column, context);	      

		if ( value instanceof CMCellHeaderDependencyName){
			setBackground(new Color(156,154,252));
	        setForeground(new Color(252,254,252));
			setFont(new Font("Dialog",Font.PLAIN,12));
				
			if( !isSelected) 
				setBorder(BorderFactory.createRaisedBevelBorder());
        
		}		
        return c;
	}
}
