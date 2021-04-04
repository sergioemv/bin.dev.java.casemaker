package bi.view.cells.renderers;

import java.awt.Component;

import javax.swing.JLabel;

import com.eliad.model.GridContext;
import com.eliad.model.defaults.DefaultGridCellRenderer;


public class CMCenteredCellRendener extends DefaultGridCellRenderer
{
	public Component getComponent(Object value, boolean isSelected, boolean hasFocus, boolean isEditable, int row, int column, GridContext context) {
		Component c = super.getComponent(value, isSelected, hasFocus, isEditable, row, column, context);
        this.setHorizontalAlignment(JLabel.CENTER);
		return c;
 }
}
