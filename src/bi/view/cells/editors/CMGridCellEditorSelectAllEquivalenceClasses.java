package bi.view.cells.editors;

import javax.swing.JCheckBox;

import model.EquivalenceClass;
import model.State;
import model.edit.CMModelEditFactory;
import bi.view.cells.CMCellSelectAllEquivalenceClassesOfElement;
import bi.view.undomanagementviews.CMCompoundEdit;
import bi.view.undomanagementviews.undoviews.CMComponentAwareEdit;
import bi.view.undomanagementviews.undoviews.CMUndoMediator;

public class CMGridCellEditorSelectAllEquivalenceClasses extends CMGridCellEditorCheckBox {

	
	public CMGridCellEditorSelectAllEquivalenceClasses() {
		super(new JCheckBox());
	
	}
	public boolean stopCellEditing() {
		if (getCell() != null)
		{	
			CMCompoundEdit ce = new CMCompoundEdit();
			ce.addEdit(new CMComponentAwareEdit());
			CMCellSelectAllEquivalenceClassesOfElement cell = (CMCellSelectAllEquivalenceClassesOfElement)getCellEditorValue();
			boolean value = cell.getSelected().booleanValue(); 
		if (	value)
			//deselect
			for (EquivalenceClass equivalenceClass: cell.getElement().getEquivalenceClasses()) {
				if (cell.getDependency().getEquivalenceClasses().contains(equivalenceClass)) {
					ce.addEdit(CMModelEditFactory.INSTANCE.createRemoveEquivalenceClassModelEdit(cell.getDependency(), equivalenceClass));
					cell.getDependency().removeEquivalenceClass(equivalenceClass);
				}	
			}
		else {
		for (EquivalenceClass equivalenceClass2: cell.getElement().getEquivalenceClassesbyState(State.POSITIVE.intValue()))
			if (!cell.getDependency().getEquivalenceClasses().contains(equivalenceClass2)) {
				ce.addEdit(CMModelEditFactory.INSTANCE.createAddEquivalenceClassModelEdit(cell.getDependency(), equivalenceClass2));
				cell.getDependency().addEquivalenceClass(equivalenceClass2);
			}	
			//select			
		
		}

		CMUndoMediator.getInstance().doEdit(ce);	
		}
 
		return super.stopCellEditing();
	}
}
