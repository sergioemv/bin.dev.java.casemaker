package bi.view.cells.renderers;

import java.awt.Color;
import java.awt.Component;

import com.eliad.model.GridContext;

public class CMEquivalenceClassEffectCellRenderer extends
	 CMBaseGridCellRenderer {

	public CMEquivalenceClassEffectCellRenderer() {
		super();
		// TODO Auto-generated constructor stub
	}
	 public Component getComponent(Object value, boolean isSelected, boolean hasFocus, boolean isEditable, int row, int column, GridContext context) {
			Component c = super.getComponent(value, isSelected, hasFocus, isEditable, row, column, context);
			setBackground(new Color(235, 235, 228));
			return c;
	 }
}
