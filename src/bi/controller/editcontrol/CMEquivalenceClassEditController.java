package bi.controller.editcontrol;


import java.awt.Dimension;

import javax.swing.undo.UndoableEdit;

import model.Element;
import model.EquivalenceClass;
import model.edit.CMModelEditFactory;
import bi.view.actions.equivalenceclass.CMPanelEquivalenceClass;
import bi.view.undomanagementviews.CMCompoundEdit;

public class CMEquivalenceClassEditController extends CMDefaultEditController{
	
	private Element element;
	private CMPanelEquivalenceClass panel;
	public static final Dimension DEFAULT_DIALOG_SIZE = new Dimension(621, 530);
	private EquivalenceClass equivalenceClass;
	private CMEffectsEditController effectsController;
	private CMRiskLevelEditController riskLevelEditController;
	private CMStateEditController stateEditController;
	
	public CMEquivalenceClassEditController(Element p_element, EquivalenceClass equivalenceClass) {
		this.element = p_element;
		this.equivalenceClass = equivalenceClass;
	    //loadPanelProperties();
	}

	/**
	 * fill the properties of the equivalence class in the panel 
	 */
	private final void loadPanelProperties() {
		if (equivalenceClass == null) return;
		getPanel().getJTextAreaDescription().setText(equivalenceClass.getDescription());
		getPanel().getJTextFieldValue().setText(equivalenceClass.getValue());
		getPanel().getJTextFieldValue().selectAll();
	    //getPanel().getJCMState().setState(equivalenceClass.getState());
		getStateEditController().setStateView(getPanel().getJCMState());
	  //  getPanel().getJCMRiskLevelView().setRisklevel(equivalenceClass.getRiskLevel());
	    getRiskLevelEditController().setRiskLevelView(getPanel().getJCMRiskLevelView());
	    getPanel().setEffectsEditPanel(getEffectsController().getPanel());		
	}

	public CMPanelEquivalenceClass getPanel() {
		if (panel == null){
			panel = new CMPanelEquivalenceClass();
			loadPanelProperties();
		}
		return panel;
	}

	public void setPanel(CMPanelEquivalenceClass panel) {
		this.panel = panel;
	}

	private CMEffectsEditController getEffectsController() {
		if (effectsController == null)
			effectsController = new CMEffectsEditController(equivalenceClass,element.getLnkStructure());
		return effectsController;
	}

	public UndoableEdit applyChanges(){
		CMCompoundEdit ce = new CMCompoundEdit();
		
		if (equivalenceClass== null || element == null) return ce;
		ce.addEdit(CMModelEditFactory.INSTANCE.createChangeStateModelEdit(equivalenceClass, getPanel().getJCMState().getSelectedStateModel()));
		equivalenceClass.setState(getPanel().getJCMState().getSelectedStateModel());
		ce.addEdit(CMModelEditFactory.INSTANCE.createChangeRiskLevelModelEdit(equivalenceClass,getPanel().getJCMRiskLevelView().getSelectedRisklevel()));
		equivalenceClass.setRiskLevel(getPanel().getJCMRiskLevelView().getSelectedRisklevel());
		
		ce.addEdit(getEffectsController().applyChanges());
		getWarningMessages().addAll(getEffectsController().getWarningMessages());
		ce.addEdit(CMModelEditFactory.INSTANCE.createChangeDescriptionModelEdit(equivalenceClass,getPanel().getJTextAreaDescription().getText()));
		equivalenceClass.setDescription(getPanel().getJTextAreaDescription().getText());
		ce.addEdit(CMModelEditFactory.INSTANCE.createChangeValueModelEdit(equivalenceClass,getPanel().getJTextFieldValue().getText()));
		equivalenceClass.setValue(getPanel().getJTextFieldValue().getText());
		
		ce.addEdit(getStateEditController().applyChanges());
		getWarningMessages().addAll(getStateEditController().getWarningMessages());

		ce.addEdit(getRiskLevelEditController().applyChanges());
		getWarningMessages().addAll(getRiskLevelEditController().getWarningMessages());
		
		return ce;
	}

	public CMRiskLevelEditController getRiskLevelEditController() {
		if (riskLevelEditController == null){
			riskLevelEditController = new CMRiskLevelEditController();
			riskLevelEditController.setRiskLevelBean(equivalenceClass);
		}
		return riskLevelEditController;
	}

	public CMStateEditController getStateEditController() {
		if (stateEditController == null){
			stateEditController = new CMStateEditController();
			stateEditController.setStateBean(equivalenceClass);
		}
		return stateEditController;
	}
	
	
}
