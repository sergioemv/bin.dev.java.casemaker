package bi.view.cells.editors;

import javax.swing.JCheckBox;

import model.Dependency;
import model.EquivalenceClass;
import model.edit.CMModelEditFactory;
import bi.view.cells.CMCellSelectEquivalenceClass;
import bi.view.undomanagementviews.CMCompoundEdit;
import bi.view.undomanagementviews.undoviews.CMComponentAwareEdit;
import bi.view.undomanagementviews.undoviews.CMUndoMediator;

public class CMGridCellEditorSelectEquivalenceClass extends CMGridCellEditorCheckBox {
	
	
	public CMGridCellEditorSelectEquivalenceClass() {
		super(new JCheckBox());	
	}
public boolean stopCellEditing() {
	if (getCell() != null)
	{	
		CMCompoundEdit ce  = new CMCompoundEdit();
		ce.addEdit(new CMComponentAwareEdit());
		EquivalenceClass equivalenceClass = (EquivalenceClass)getCell().getModel();
		Dependency dependency = ((CMCellSelectEquivalenceClass)getCell()).getDependency();
		boolean value = ((CMCellSelectEquivalenceClass)getCellEditorValue()).getSelected().booleanValue(); 
		//select
	if (!	value) {
		ce.addEdit(CMModelEditFactory.INSTANCE.createAddEquivalenceClassModelEdit(dependency, equivalenceClass));
		dependency.addEquivalenceClass(equivalenceClass);
	}
		//deselect
	else {
		ce.addEdit(CMModelEditFactory.INSTANCE.createRemoveEquivalenceClassModelEdit(dependency, equivalenceClass));
		dependency.removeEquivalenceClass(equivalenceClass);
	}
	CMUndoMediator.getInstance().doEdit(ce);
	}
	
	return super.stopCellEditing();
}
    
}
