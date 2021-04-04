package bi.view.cells.editors;



import java.awt.Component;

import javax.swing.JCheckBox;

import com.eliad.model.GridContext;

public class CMGridCellEditorCheckBox extends CMBaseGridCellEditor {

	private JCheckBox m_checkBox = new JCheckBox();
	
	public CMGridCellEditorCheckBox(JCheckBox checkBox) {
		super(checkBox,null);
		m_checkBox = checkBox;		
	}	
	
	public Component getComponent(Object value, boolean isSelected, int row, int column, GridContext context) {	
		Component c = super.getComponent(value, isSelected, row, column, context);
		m_checkBox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);		
		return c;
	}
	
}

