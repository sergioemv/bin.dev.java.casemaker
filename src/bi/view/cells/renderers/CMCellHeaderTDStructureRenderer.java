package bi.view.cells.renderers;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import com.eliad.model.GridContext;
import com.eliad.model.defaults.DefaultGridCellRenderer;


public class CMCellHeaderTDStructureRenderer extends DefaultGridCellRenderer{
	
	public Component getComponent(Object value, boolean isSelected, boolean hasFocus, boolean isEditable, int row, int column, GridContext context) {
		Component c = super.getComponent(value, isSelected, hasFocus, isEditable, row, column, context);
		
		setBackground(new Color(36,38,116));
		setForeground(new Color(252,254,252));
        setFont(new Font("Dialog",Font.PLAIN,12));
		setHorizontalAlignment(JLabel.LEFT);
	
        if( !isSelected)

        		setBorder(BorderFactory.createRaisedBevelBorder());


        return c;
	}

}
