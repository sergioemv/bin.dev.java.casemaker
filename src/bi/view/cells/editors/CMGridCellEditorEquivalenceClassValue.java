package bi.view.cells.editors;

import javax.swing.JTextField;
import javax.swing.undo.UndoableEdit;
import model.EquivalenceClass;
import model.edit.CMModelEditFactory;
import bi.view.undomanagementviews.undoviews.CMUndoMediator;

@SuppressWarnings("serial")
public class CMGridCellEditorEquivalenceClassValue extends CMBaseGridCellEditor {

	public CMGridCellEditorEquivalenceClassValue() {
		super(new JTextField(), null);
		
	}

	public boolean stopCellEditing() {
		
		if (getCell()!=null)
		{
			  //String value= deleteCharacterSpecialSemiColon(((JTextField)this.getComponent()).getText());
			String value= ((JTextField)this.getComponent()).getText();
			EquivalenceClass equivalenceClass = (EquivalenceClass)getCell().getModel();
			if (!value.equalsIgnoreCase(equivalenceClass.getValue())){
			     UndoableEdit ue = CMModelEditFactory.INSTANCE.createChangeValueModelEdit(equivalenceClass, value);
				 equivalenceClass.setValue(value);
				 CMUndoMediator.getInstance().doEdit(ue);
			}
			 
		}
		return super.stopCellEditing();
	}
	
}
