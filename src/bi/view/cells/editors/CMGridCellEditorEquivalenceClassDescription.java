package bi.view.cells.editors;

import javax.swing.JTextField;
import javax.swing.undo.UndoableEdit;
import model.EquivalenceClass;
import model.edit.CMModelEditFactory;
import bi.view.undomanagementviews.undoviews.CMUndoMediator;

@SuppressWarnings("serial")
public class CMGridCellEditorEquivalenceClassDescription extends
		CMBaseGridCellEditor {

	public CMGridCellEditorEquivalenceClassDescription() {
		super(new JTextField(),null);
	}
	
	public boolean stopCellEditing() {
		if (getCell()!=null)
		{
			 EquivalenceClass equivalenceClass = (EquivalenceClass) this.getCell().getModel();
			 String value = ((JTextField)getComponent()).getText(); 
			 if (!value.equalsIgnoreCase(equivalenceClass.getDescription())){
				 UndoableEdit ue = CMModelEditFactory.INSTANCE.createChangeDescriptionModelEdit(equivalenceClass, value);
				 equivalenceClass.setDescription(value);
				 CMUndoMediator.getInstance().doEdit(ue);
			 }
		}
		return super.stopCellEditing();
	}

}
