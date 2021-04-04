package bi.view.cells.editors;

import javax.swing.JTextField;
import javax.swing.undo.UndoableEdit;
import model.Element;
import model.edit.CMModelEditFactory;
import bi.view.cells.CMCellElement;
import bi.view.undomanagementviews.undoviews.CMUndoMediator;

@SuppressWarnings("serial")
public class CMGridCellEditorElementDescription extends CMBaseGridCellEditor {

	public CMGridCellEditorElementDescription() {
		super(new JTextField(), null);
		
	}
public boolean stopCellEditing() {

	if (getCell()!=null)
	{
		  Element element = ((CMCellElement)getCell()).getElement();
		  String value = ((JTextField)this.getComponent()).getText();  
		  if (!value.equalsIgnoreCase(element.getDescription())){
			  UndoableEdit undoableEdit = CMModelEditFactory.INSTANCE.createChangeDescriptionModelEdit(element, value);
			  element.setDescription(value);
			  CMUndoMediator.getInstance().doEdit(undoableEdit);
		  }		  

	}
	return super.stopCellEditing();
}

}
