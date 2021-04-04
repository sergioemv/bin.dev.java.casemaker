package bi.controller.editcontrol;


import java.awt.Dimension;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.swing.undo.UndoableEdit;


import model.CMError;
import model.Combination;
import model.Effect;
import model.EquivalenceClass;
import model.StdCombination;
import model.Structure;
import model.TestCase;
import model.edit.CMModelEditFactory;
import bi.controller.CombinationManager;
import bi.controller.StructureManager;
import bi.view.actions.testcase.CMPanelTestCase;
import bi.view.undomanagementviews.CMCompoundEdit;

public class CMTestCaseEditController extends CMDefaultEditController {
	
	//private TestCasesBean parent;
	private CMPanelTestCase panel;
	private TestCase testCase;
	private EquivalenceClass equivalenceClass;
	private CMRiskLevelEditController riskLevelEditController;
	private CMStateEditController stateEditController;
	public static final Dimension DEFAULT_DIALOG_SIZE = new Dimension(621, 530);
	
	public CMTestCaseEditController(TestCase testCase) {
		//this.parent = parent;
		this.testCase = testCase;
		
	//	loadPanelProperties();
	}

	/**
	 * fill the properties of the equivalence class in the panel 
	 */
	private final void loadPanelProperties() {

		if (testCase==null)return;
		if (testCase.getDescription()!="")
			getPanel().getJTextAreaGeneratedDescription().setText(testCase.getDescription());
		else
			((CMPanelTestCase)getPanel()).getJPanelGeneratedDescription().setVisible(false);
		getPanel().getJTextAreaDescription().setText(testCase.getDescriptionEditable());
	

			
		getStateEditController().setStateView(getPanel().getJCMState());
	    getRiskLevelEditController().setRiskLevelView(getPanel().getJCMRiskLevelView());

	    for (Effect effect : testCase.getRelatedEffects())
	    	getPanel().getEffectsListModel().addElement(effect.getName() + " " + effect.getDescription());
	    
	    //fill the errors
	   getPanel().getCMPanelErrorsAssigment().setAssignedList(testCase.getErrors());
	   Structure structure = StructureManager.getSelectedStructure();
	   List<CMError> errors = structure.getErrors();
	   errors.removeAll(testCase.getErrors());
			   getPanel().getCMPanelErrorsAssigment().setAvailableList(errors);
        //fill the standart combinations
	    List<StdCombination> stdcombList = (List<StdCombination>) structure.getCombinations();
	    if (testCase.getStdCombination()!=null)
	    	stdcombList.remove(testCase.getStdCombination());
	    getPanel().getCMPanelStdCombinationsAssigment().setAvailableList(stdcombList);
	    if (testCase.getStdCombination()!=null){
	    	getPanel().getCMPanelStdCombinationsAssigment().setAssignedList(Arrays.asList(testCase.getStdCombination()));
	    	getPanel().getCMPanelStdCombinationsAssigment().getJListRight().setVisible(false);
	    }
	    //fill the combinations 
	    if (getEquivalenceClass()==null ||  testCase.getEquivalenceClasses().contains(getEquivalenceClass())) {
	    	getPanel().getJTabbedPaneCombination().remove(getPanel().getJPanelCombinations());
	    	return;
	    }
	    getPanel().getPanelCombinationAssignnment().setAssignedList(testCase.getCombinations(getEquivalenceClass()));
	    List m_EquivalenceClassCombinations = CombinationManager.INSTANCE.getEquivalenceClassCombinations(getEquivalenceClass(), testCase, structure);
	    getPanel().getPanelCombinationAssignnment().setAvailableList(m_EquivalenceClassCombinations);
	    
	    
	}

	public CMPanelTestCase getPanel() {
		if (panel == null){
			panel = new CMPanelTestCase();
			loadPanelProperties();
			
		}
		return panel;
	}

	public void setPanel(CMPanelTestCase panel) {
		this.panel = panel;
	}


	public UndoableEdit applyChanges(){
		CMCompoundEdit ce = new CMCompoundEdit();
		getWarningMessages().clear();
		if (testCase== null ) return ce;
		
		ce.addEdit(CMModelEditFactory.INSTANCE.createChangeStateModelEdit(testCase, getPanel().getJCMState().getSelectedStateModel()));
		testCase.setState(getPanel().getJCMState().getSelectedStateModel());
		ce.addEdit(CMModelEditFactory.INSTANCE.createChangeRiskLevelModelEdit(testCase, getPanel().getJCMRiskLevelView().getSelectedRisklevel()));
		testCase.setRiskLevel(getPanel().getJCMRiskLevelView().getSelectedRisklevel());
		ce.addEdit(CMModelEditFactory.INSTANCE.createChangeDescriptionEditableModelEdit(testCase,getPanel().getJTextAreaDescription().getText()));
		testCase.setDescriptionEditable(getPanel().getJTextAreaDescription().getText());	
		ce.addEdit(getStateEditController().applyChanges());
		getWarningMessages().addAll(getStateEditController().getWarningMessages());
		ce.addEdit(getRiskLevelEditController().applyChanges());
		getWarningMessages().addAll(getRiskLevelEditController().getWarningMessages());
		//changes in error assignment
		ce.addEdit(updateErrorAssigmentWithPanel());
		//changes in std combinations
		ce.addEdit(updateSTDCombinationAssigmentWithPanel());
		//changes in combinations
		ce.addEdit(updateCombinationsWithPanel());
//		changes in std combinations
		//ce.addEdit(updateSTDCombinationAssigmentWithPanel());
		return ce;
	}
	private UndoableEdit updateCombinationsWithPanel() {
		CMCompoundEdit ce = new CMCompoundEdit();
		//Structure structure = StructureManager.getSelectedStructure();		
		List newCombinationsWithTestCase = getPanel().getPanelCombinationAssignnment().getAssignedList();
	    
	    if(!newCombinationsWithTestCase.equals(testCase.getCombinations())) {
	
	        for (Combination combination : testCase.getCombinations()){
	        	if(!(combination instanceof StdCombination)){
	        		ce.addEdit(CMModelEditFactory.INSTANCE.createRemoveCombinationModelEdit(testCase, combination));
	        		testCase.removeCombination(combination);
	        	}
	        		
	        	}
	        for (Combination combination : (List<Combination>)newCombinationsWithTestCase){
	        	if (!testCase.getCombinations().contains(combination)){
	        		ce.addEdit(CMModelEditFactory.INSTANCE.createAddCombinationModelEdit(testCase, combination));
	        		testCase.addCombination(combination);
	        	}
	        }
	    }
	  
	    return ce;

	}

	public UndoableEdit updateErrorAssigmentWithPanel(){
		CMCompoundEdit ce = new CMCompoundEdit();
		Structure structure = StructureManager.getSelectedStructure();		
		List newErrorsWithTestCase = getPanel().getCMPanelErrorsAssigment().getAssignedList();
	    
	    if(!newErrorsWithTestCase.equals(testCase.getErrors())) {
	
	        for (CMError error : structure.getErrors())
	        	if (testCase.getErrors().contains(error)){
	        		ce.addEdit(CMModelEditFactory.INSTANCE.createRemoveErrorModelEdit(testCase, error));
	        		testCase.removeError(error);
	        	}
	        for (CMError error : (List<CMError>)newErrorsWithTestCase){
	        	if (!testCase.getErrors().contains(error)){
	        		ce.addEdit(CMModelEditFactory.INSTANCE.createAddErrorModelEdit(testCase, error));
	        		testCase.addError(error);
	        	}
	        }
	    }
	  
	    return ce;
	}
	
	public UndoableEdit updateSTDCombinationAssigmentWithPanel(){
		CMCompoundEdit ce = new CMCompoundEdit();
		List stdCombinations = getPanel().getCMPanelStdCombinationsAssigment().getAssignedList();
		while (testCase.getStdCombination()!=null){
			ce.addEdit(CMModelEditFactory.INSTANCE.createRemoveCombinationModelEdit(testCase, testCase.getStdCombination()));
			testCase.removeCombination(testCase.getStdCombination());
		}
		if (stdCombinations.size()==1){
			ce.addEdit(CMModelEditFactory.INSTANCE.createAddCombinationModelEdit(testCase,(Combination) stdCombinations.get(0)));
				testCase.addCombination((Combination) stdCombinations.get(0)); 
		}
	    return ce;
	}
	
	
	

	public CMRiskLevelEditController getRiskLevelEditController() {
		if (riskLevelEditController == null){
			riskLevelEditController = new CMRiskLevelEditController();
			riskLevelEditController.setRiskLevelBean(testCase);
		}
		return riskLevelEditController;
	}

	public CMStateEditController getStateEditController() {
		if (stateEditController == null){
			stateEditController = new CMStateEditController();
			stateEditController.setStateBean(testCase);
		}
		return stateEditController;
	}

	public EquivalenceClass getEquivalenceClass() {
		return equivalenceClass;
	}

	public void setEquivalenceClass(EquivalenceClass equivalenceClass) {
		this.equivalenceClass = equivalenceClass;
	}
}
