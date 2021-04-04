package bi.view.cells;

import java.awt.Color;
import java.awt.Component;

import com.eliad.model.GridContext;
import com.eliad.model.defaults.DefaultGridCellRenderer;

public class CMCellColorRendererGray extends DefaultGridCellRenderer{
	 public Component getComponent(Object value, boolean isSelected, boolean hasFocus, boolean isEditable, int row, int column, GridContext context) {
			Component c = super.getComponent(value, isSelected, hasFocus, isEditable, row, column, context);
			c.setBackground(new Color(235, 235, 228));
			return c;
	 }
}
