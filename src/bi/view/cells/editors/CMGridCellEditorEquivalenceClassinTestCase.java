package bi.view.cells.editors;



import java.awt.Component;

import javax.swing.JCheckBox;

import com.eliad.model.GridContext;
import com.eliad.model.defaults.DefaultGridCellEditor;

public class CMGridCellEditorEquivalenceClassinTestCase extends DefaultGridCellEditor {
	private Object m_value;
	private JCheckBox m_checkBox = new JCheckBox();
	private GridContext m_context;
	
	public CMGridCellEditorEquivalenceClassinTestCase(JCheckBox checkBox) {
		super(checkBox);
		m_checkBox = checkBox;		
	
	}	

	public Component getComponent(Object value, boolean isSelected, int row, int column, GridContext context) {	
		Component c = super.getComponent(value, isSelected, row, column, context);
		setM_context(context);
		m_value = value;		
		m_checkBox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);		
		return c;
	}

	public GridContext getM_context() {
		return m_context;
	}

	public void setM_context(GridContext m_context) {
		this.m_context = m_context;
	}
	
}

