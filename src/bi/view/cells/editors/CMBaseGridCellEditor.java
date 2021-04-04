package bi.view.cells.editors;

import javax.swing.Action;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import bi.view.actions.CMAction;
import bi.view.cells.CMBaseCell;
import bi.view.utils.CMInformationDetailsDialog;

import com.eliad.model.defaults.DefaultGridCellEditor;

public class CMBaseGridCellEditor extends DefaultGridCellEditor {

	private CMBaseCell cell = null;

	public CMBaseGridCellEditor(JTextField arg0, CMBaseCell cell) {
		
		super(arg0);
		this.cell = cell;
	}

	public CMBaseGridCellEditor(JCheckBox arg0, CMBaseCell cell) {
		super(arg0);
		this.cell = cell;
		// TODO Auto-generated constructor stub
	}

	public CMBaseGridCellEditor(JComboBox arg0, CMBaseCell cell) {
		super(arg0);
		this.cell = cell;
		// TODO Auto-generated constructor stub
	}

	public CMBaseCell getCell() {
		return cell;
	}

	public void setCell(CMBaseCell cell) {
		this.cell = cell;
	}
public Object getCellEditorValue() {

	return this.getCell();
}
/* (non-Javadoc)
 * @see com.eliad.model.defaults.DefaultGridCellEditor#stopCellEditing()
 */
protected void showWarningMessages(Object[] messages) {
	CMInformationDetailsDialog dlg = new CMInformationDetailsDialog();
	StringBuilder stringBuilder = new StringBuilder();
	for (Object string : messages){
		stringBuilder.append("- ");
		stringBuilder.append(string);
		stringBuilder.append("\n");
		stringBuilder.append("\n");
	}
	dlg.getJTextPaneMessajes().setText(stringBuilder.toString());
	dlg.setTitle((String) CMAction.EQUIVALENCECLASS_EDIT.getAction().getValue(Action.SHORT_DESCRIPTION));
	dlg.setVisible(true);
}
}
