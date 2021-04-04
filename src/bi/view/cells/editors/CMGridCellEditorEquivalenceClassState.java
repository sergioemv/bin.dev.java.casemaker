package bi.view.cells.editors;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;

import model.EquivalenceClass;
import model.State;
import model.util.CMModelEventHandler;
import bi.controller.editcontrol.CMStateEditController;
import bi.view.actions.CMAbstractAction;
import bi.view.cells.CMBaseCell;
import bi.view.cells.CMStateWrapper;
import bi.view.undomanagementviews.CMCompoundEdit;
import bi.view.undomanagementviews.undoviews.CMUndoMediator;
import bi.view.utils.CMBaseJComboBox;
import bi.view.utils.CMStateView;


public class CMGridCellEditorEquivalenceClassState extends CMBaseGridCellEditor {

	/**
	 * Faulty State of the equivalence Class
	 */
	CMStateWrapper faulty = new CMStateWrapper(State.FAULTY);

	/**
	 * Irrelevant State of the equivalence Class
	 */
	CMStateWrapper irrelevant = new CMStateWrapper(State.IRRELEVANT);

	/**
	 * Negative State of the Equivalence Class
	 */
	CMStateWrapper negative = new CMStateWrapper(State.NEGATIVE);

	/**
	 * Positive State of the Equivalence Class
	 */
	CMStateWrapper positive = new CMStateWrapper(State.POSITIVE);

	 CMStateWrapper[] states = {faulty,irrelevant,negative,positive};
	ComboBoxModel combomodel = new CMStateComboModel(this);

	CMBaseJComboBox statesCombo = null;
	
	private CMStateEditController stateEditController;
	public CMGridCellEditorEquivalenceClassState() {

		super(new CMBaseJComboBox(new Vector(0)), null);
		// initialize the combo
		statesCombo =((CMBaseJComboBox) getComponent());
		statesCombo.setModel(combomodel);


	}

	public boolean stopCellEditing() {
		CMCompoundEdit ce = new CMCompoundEdit();
		if (getCell() != null) {
			EquivalenceClass equivalenceClass = (EquivalenceClass) getCell()
					.getModel();
			getStateEditController().setStateBean(equivalenceClass);
			int newState = ((JComboBox) this.getComponent()).getSelectedIndex();
			getStateEditController().getStateView().setState(newState);
			CMModelEventHandler.setNotifyEnabled(false);
			ce.addEdit(getStateEditController().applyChanges());
			CMUndoMediator.getInstance().doMassiveEdit(ce);
			CMModelEventHandler.setNotifyEnabled(true);
			List<Object> modObjects = new ArrayList<Object>();
			ce.fillModifiedObjects(modObjects);
			CMAbstractAction.updateViews(modObjects);
			this.getComponent().repaint();
			super.stopCellEditing();
			if (getStateEditController().getWarningMessages().size()>0)
				showWarningMessages(getStateEditController().getWarningMessages().toArray());
	
		}
		return super.stopCellEditing();
	}


	
	public void setCell(CMBaseCell cell) {
		// put the cell value
		super.setCell(cell);
		//update the editor state
		statesCombo.setParent(getCell().getGrid());
		((CMBaseJComboBox) getComponent()).removeAllItems();
		((CMBaseJComboBox) getComponent()).addItem(faulty);
		((CMBaseJComboBox) getComponent()).addItem(irrelevant);
		((CMBaseJComboBox) getComponent()).addItem(negative);
		((CMBaseJComboBox) getComponent()).addItem(positive);
		combomodel.setSelectedItem(cell);


	}

	public CMStateEditController getStateEditController() {
		if (stateEditController == null){
			stateEditController = new CMStateEditController();
			stateEditController.setStateView(new CMStateView());
		}
		return stateEditController;
	}
	
}
