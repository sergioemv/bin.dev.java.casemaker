package bi.controller.editcontrol;


import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.undo.UndoableEdit;

import model.Combination;
import model.StdCombination;
import model.Structure;
import model.TestCase;
import model.edit.CMModelEditFactory;
import bi.controller.StructureManager;
import bi.controller.TestCaseManager;
import bi.view.actions.combination.CMPanelCombination;
import bi.view.icons.CMIcon;
import bi.view.lang.CMMessages;
import bi.view.undomanagementviews.CMCompoundEdit;

public class CMCombinationEditController extends CMDefaultEditController {
	
	private Structure structure;
	private CMPanelCombination panel;
	private Combination combination;
	private CMRiskLevelEditController riskLevelEditController;
	private CMStateEditController stateEditController;
	public static final Dimension DEFAULT_DIALOG_SIZE = new Dimension(621, 530);
	private CMEffectsEditController effectsController;
	
	public CMCombinationEditController(Structure structure, Combination combination) {
		this.structure = structure;
		this.combination = combination;
		
	//	loadPanelProperties();
	}

	/**
	 * fill the properties of the equivalence class in the panel 
	 */
	private final void loadPanelProperties() {

		if (combination==null)return;
		if (combination.getDescription()!="")
			getPanel().getJTextAreaGeneratedDescription().setText(combination.getDescription());
		else
			((CMPanelCombination)getPanel()).getJPanelGeneratedDescription().setVisible(false);
		getPanel().getJTextAreaDescription().setText(combination.getDescriptionEditable());
		
		if (combination.getOriginType()==Combination.Origin.MANUAL) {
			getPanel().getjLabelOrigin().setIcon(CMIcon.EQUIVALENCECLASS_IN_MANUAL_COMBINATION.getImageIcon());
			getPanel().getjLabelOrigin().setText(CMMessages.getString("COMBINATION_MANUAL_ORIGIN"));
		}
		if (combination.getOriginType()==Combination.Origin.PERMUTATION) {
			getPanel().getjLabelOrigin().setIcon(CMIcon.EQUIVALENCECLASS_IN_COMBINATION.getImageIcon());
			getPanel().getjLabelOrigin().setText(CMMessages.getString("COMBINATION_PERMUTATION_ORIGIN"));
		}
		if (combination.getOriginType()==Combination.Origin.ALLPAIRS) {
			getPanel().getjLabelOrigin().setIcon(CMIcon.EQUIVALENCECLASS_IN_ALLPAIRS_COMBINATION.getImageIcon());
			getPanel().getjLabelOrigin().setText(CMMessages.getString("COMBINATION_ALLPAIRS_ORIGIN"));
		}
		

			
		getStateEditController().setStateView(getPanel().getJCMState());
	    getRiskLevelEditController().setRiskLevelView(getPanel().getJCMRiskLevelView());
	    getPanel().setEffectsEditPanel(getEffectsController().getPanel());
	    fillAllowedTestCaseList();
	    fillAssignedTestCaseList();
	    if (combination instanceof StdCombination){
	    	getPanel().getJTabbedPaneCombination().setIconAt(0, CMIcon.STDCOMBINATION.getImageIcon());
	    	getPanel().getJTabbedPaneCombination().remove(1);
	    	getEffectsController().setVisible(false);
	    	getStateEditController().setVisible(false);
	    	getRiskLevelEditController().setVisible(false);
	    	getPanel().getJPanelUpperPanel().setVisible(false);
	    }
	}

	public CMPanelCombination getPanel() {
		if (panel == null){
			panel = new CMPanelCombination();
			loadPanelProperties();
			
		}
		return panel;
	}

	public void setPanel(CMPanelCombination panel) {
		this.panel = panel;
	}

	private CMEffectsEditController getEffectsController() {
		if (effectsController == null && structure !=null)
			effectsController = new CMEffectsEditController(combination, structure);
		return effectsController;
	}

	public UndoableEdit applyChanges(){
		CMCompoundEdit ce = new CMCompoundEdit();
		getWarningMessages().clear();
		if (combination== null ) return ce;
		ce.addEdit(updateTestCasesAssigmentWithPanel());
		ce.addEdit(CMModelEditFactory.INSTANCE.createChangeStateModelEdit(combination, getPanel().getJCMState().getSelectedStateModel()));
		combination.setState(getPanel().getJCMState().getSelectedStateModel());
		ce.addEdit(CMModelEditFactory.INSTANCE.createChangeRiskLevelModelEdit(combination, getPanel().getJCMRiskLevelView().getSelectedRisklevel()));
		combination.setRiskLevel(getPanel().getJCMRiskLevelView().getSelectedRisklevel());
		ce.addEdit(getEffectsController().applyChanges());
		getWarningMessages().addAll(getEffectsController().getWarningMessages());
		//ce.addEdit(CMModelEditFactory.INSTANCE.createChangeDescriptionModelEdit(combination,getPanel().getJTextAreaDescription().getText()));
		ce.addEdit(CMModelEditFactory.INSTANCE.createChangeDescriptionEditableModelEdit(combination,getPanel().getJTextAreaDescription().getText()));
		combination.setDescriptionEditable(getPanel().getJTextAreaDescription().getText());	
		ce.addEdit(getStateEditController().applyChanges());
		getWarningMessages().addAll(getStateEditController().getWarningMessages());
		ce.addEdit(getRiskLevelEditController().applyChanges());
		getWarningMessages().addAll(getRiskLevelEditController().getWarningMessages());
		return ce;
	}
	
	public UndoableEdit updateTestCasesAssigmentWithPanel(){
		CMCompoundEdit ce = new CMCompoundEdit();
		Structure structure = StructureManager.getSelectedStructure();		
		List newTestCasesWithCombination = getPanel().getPanelTestCasesAssigment().getAssignedList();
	    
	    if(!newTestCasesWithCombination.equals(StructureManager.getSelectedStructure().getTestCases(combination))) {
	
	        for (TestCase testCase : structure.getLnkTestCases())
	        	if (testCase.getCombinations().contains(combination)){
	        		ce.addEdit(CMModelEditFactory.INSTANCE.createRemoveCombinationModelEdit(testCase, combination));
	        		testCase.removeCombination(combination);
	        	}
	        for (TestCase testCase : (List<TestCase>)newTestCasesWithCombination){
	        	if (!testCase.getCombinations().contains(combination)){
	        		ce.addEdit(CMModelEditFactory.INSTANCE.createAddCombinationModelEdit(testCase, combination));
	        		testCase.addCombination(combination);
	        	}
	        }
	    }
	  
	    return ce;
	}
	
	public void fillAllowedTestCaseList() {
		List<TestCase>  allowedTestCases = new ArrayList<TestCase>();
	    getPanel().getPanelTestCasesAssigment().clearAllowedList();
		for (TestCase testCase : StructureManager.getSelectedStructure().getLnkTestCases())
	      if( !testCase.getCombinations().contains(combination)) {
	    	if (combination instanceof StdCombination){
	    		allowedTestCases.add(testCase);
	    	}
	    	else
	        if( TestCaseManager.INSTANCE.canCombinationBeAssignedToTestCase(StructureManager.getSelectedStructure(), testCase, combination)) {
	          if( TestCaseManager.INSTANCE.canCombinationBeAssignedToTestCaseConsideringTheState(testCase, combination)) {	            
				if( !allowedTestCases.contains(testCase)) {
					allowedTestCases.add(testCase);
	            }
	          }
	        }
	        
	      }
	    getPanel().getPanelTestCasesAssigment().setAvailableList(allowedTestCases);
	  
	  //  jPanelShuttleAssignTestCase.setLeftList(clones, views);
	  }

	  public void fillAssignedTestCaseList() {
	    List<TestCase>  testCasesWithCombination = new ArrayList<TestCase>();
	    for(TestCase testCase : StructureManager.getSelectedStructure().getLnkTestCases()) {
	      if( testCase.getCombinations().contains(combination) ) {
	        if( !testCasesWithCombination.contains(testCase)) {
	        	testCasesWithCombination.add(testCase);
	        }
	      }
	    }
	    getPanel().getPanelTestCasesAssigment().setAssignedList(testCasesWithCombination);
	    //jPanelShuttleAssignTestCase.setRightList(clones, views);
	  }

	public CMRiskLevelEditController getRiskLevelEditController() {
		if (riskLevelEditController == null){
			riskLevelEditController = new CMRiskLevelEditController();
			riskLevelEditController.setRiskLevelBean(combination);
		}
		return riskLevelEditController;
	}

	public CMStateEditController getStateEditController() {
		if (stateEditController == null){
			stateEditController = new CMStateEditController();
			stateEditController.setStateBean(combination);
		}
		return stateEditController;
	}
}
