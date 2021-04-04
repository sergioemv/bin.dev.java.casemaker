/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.controller.editcontrol;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.text.JTextComponent;
import javax.swing.undo.UndoableEdit;

import model.Effect;
import model.ExpectedResult;
import model.Requirement;
import model.Structure;
import model.edit.CMModelEditFactory;
import bi.controller.StructureManager;
import bi.view.actions.effect.CMEffectDialogPanel;
import bi.view.cells.CMBaseCell;
import bi.view.cells.CMCellExpectedResultName;
import bi.view.cells.CMCellExpectedResultValue;
import bi.view.undomanagementviews.CMCompoundEdit;
import bi.view.utils.CMTwinBox;

import com.eliad.swing.GridEditingEvent;
import com.eliad.swing.GridEditingListener;

/**
 * @author smoreno
 * class that controls the edition of a effect
 */
public class CMEffectEditController extends CMDefaultEditController implements Comparable {
private CMEffectDialogPanel panel;
private CMRiskLevelEditController riskLevelEditController;
private CMStateEditController stateEditController;
private Effect effect;
private boolean modified;
private Structure structure;
private CMTwinBox shuttle;

public CMEffectEditController(Effect p_effect) {
	super();
	this.effect = p_effect;

}

/**
 * @return Returns the effect.
 */
public Effect getEffect() {
	return this.effect;
}
/**
 * @return Returns the panel.
 */
public CMEffectDialogPanel getPanel() {
	if (this.panel== null)
	{
		//initialize the panel with the properties of the effect
		this.panel = new CMEffectDialogPanel();
		//set the state and risk
		getRiskLevelEditController().setRiskLevelView(panel.getRiskLevelView());
		panel.getRiskLevelView().getComboBox().addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				setModified(true); 			
			}} );
		
		//panel.getStateView().setState(effect.getState());
		getStateEditController().setStateView(panel.getStateView());
		panel.getStateView().getJComboBoxState().addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				setModified(true); 			
			}} );
		
		panel.getJEditorPaneDescription().setText(effect.getDescription());
		panel.getJEditorPaneDescription().addKeyListener(new KeyAdapter(){
			/* (non-Javadoc)
			 * @see java.awt.event.KeyAdapter#keyTyped(java.awt.event.KeyEvent)
			 */
			@Override
			public void keyTyped(KeyEvent p_e) {

				setModified(true);
				if (getShuttle()!=null)
				{
				getShuttle().getJListAssigned().repaint();
				getShuttle().getJListAvailable().repaint();
				}
			}
		});

		if (getStructure()!=null)
		{
		 panel.getRequirementPanel().getAvailabelReqsModel().removeAllElements();
		 for (Requirement req : getStructure().getRequirements())
			 panel.getRequirementPanel().getAvailabelReqsModel().addElement(req);
		}
		 panel.getRequirementPanel().getReqListModel().clear();
		 panel.getRequirementPanel().getReqListModel().addListDataListener(new ListDataListener(){

			public void intervalAdded(ListDataEvent p_e) {
				// TODO Auto-generated method stub
				setModified(true);
			}

			public void intervalRemoved(ListDataEvent p_e) {
				// TODO Auto-generated method stub
				setModified(true);
			}

			public void contentsChanged(ListDataEvent p_e) {
				// TODO Auto-generated method stub
				setModified(true);
			}

		 });
		 ((JTextComponent)panel.getRequirementPanel().getJComboSelectedRequirement().getEditor().getEditorComponent()).setText(""); //$NON-NLS-1$
		 //set the assigned reqs
		 for (Requirement req :effect.getRequirements())
			 panel.getRequirementPanel().getReqListModel().addElement(req.getName());
		 //set the assigned expected results

		 for (ExpectedResult expRes : effect.getLnkExpectedResults())
		 {
			 List row = Arrays.asList(new CMCellExpectedResultName(panel.getGridExpectedResults(),expRes),new CMCellExpectedResultValue(panel.getGridExpectedResults(),expRes));
			 panel.getGridExpectedResults().getExpectedResultsGridModel().addRow(row.toArray());
		 }
		 panel.getGridExpectedResults().addGridEditingListener(new GridEditingListener(){

			public void editingStarted(GridEditingEvent p_arg0) {
				// TODO Auto-generated method stub
				setModified(true);
			}

			public void editingStopped(GridEditingEvent p_arg0) {
				// TODO Auto-generated method stub
				setModified(true);
			}

			public void editingCanceled(GridEditingEvent p_arg0) {
				setModified(true);

			}

		 });
	}
	return this.panel;
}

public boolean isModified() {
	return this.modified;
}

public void setModified(boolean p_modified) {
	this.modified = p_modified;
}

public Structure getStructure() {
	if (this.structure == null)
		this.structure =  StructureManager.getSelectedStructure();
	return this.structure;
}

public void setStructure(Structure p_structure) {
	this.structure = p_structure;
}

/**
 *  Updates this effect to match the contents of the panel
 * @return an undoable edit of the whole action
 */
public UndoableEdit applyChanges() {
	CMCompoundEdit ce = new CMCompoundEdit();
	getWarningMessages().clear();
	//set the state of the effect
	ce.addEdit(getStateEditController().applyChanges());
	getWarningMessages().addAll(getStateEditController().getWarningMessages());
	//set the risk level of the effect
	ce.addEdit(getRiskLevelEditController().applyChanges());
	getWarningMessages().addAll(getRiskLevelEditController().getWarningMessages());
	//	ccastedo begins 04.10.06	
	//ce.addEdit(updateEquivalenceClasses(panel.getStateView().getSelectedState(),panel.getRiskLevelView().getSelectedRisklevel()));
	//ce.addEdit(updateCombinations(panel.getStateView().getSelectedStateModel(),panel.getRiskLevelView().getSelectedRisklevel()));
	//ce.addEdit(updateTestCases(panel.getStateView().getSelectedStateModel(),panel.getRiskLevelView().getSelectedRisklevel()));
	//	ccastedo ends 04.10.06
	
	//	set the description of the effect
	ce.addEdit(CMModelEditFactory.INSTANCE.createChangeDescriptionModelEdit(effect,getPanel().getJEditorPaneDescription().getText()));
	effect.setDescription(getPanel().getJEditorPaneDescription().getText());
   //get the list of introduced requirements available and assigned
	List<String> availableReqNames = new ArrayList<String>();
	for (int i = 0; i<getPanel().getRequirementPanel().getAvailabelReqsModel().getSize();i++)
		availableReqNames.add( getPanel().getRequirementPanel().getAvailabelReqsModel().getElementAt(i).toString());
	List assignedReqNames  = Arrays.asList(getPanel().getRequirementPanel().getReqListModel().toArray());
	//add the new requirements to the structure
	for (Object assigned : assignedReqNames)
		if (structure.getRequirementByName((String)assigned)==null)
		{
			Requirement req = new Requirement((String)assigned,structure);
			ce.addEdit(CMModelEditFactory.INSTANCE.createAddRequirementModelEdit(structure,req));
		    structure.addRequirement(req);
		    req.setParentStructure(structure);
		}
	//remove all assigned requirements
	for (int i =effect.getRequirements().size(); i>=0;i--)
		if (i<effect.getRequirements().size())
		{
			Requirement req = effect.getRequirements().get(i);
			ce.addEdit(CMModelEditFactory.INSTANCE.createRemoveRequirementModelEdit(effect,req));
			effect.removeRequirement(req);
		}
	//add the assigned requirements to the effect
	//in order
	for (Object assigned : assignedReqNames)
	      {
			Requirement req = getStructure().getRequirementByName((String)assigned);
			ce.addEdit(CMModelEditFactory.INSTANCE.createAddRequirementModelEdit(effect,req));
		    effect.addRequirement(req);
		}
	//remove all expected results

	for (int i =effect.getLnkExpectedResults().size(); i>=0;i--)
		if (i<effect.getLnkExpectedResults().size())
		{
			ExpectedResult exp  = effect.getLnkExpectedResults().get(i);
			ce.addEdit(CMModelEditFactory.INSTANCE.createRemoveExpectedResultModelEdit(effect,exp));
			effect.removeExpectedResult(exp);
		}
	//get the list of new expected results added


	for (int i = 0; i<getPanel().getGridExpectedResults().getExpectedResultsGridModel().getRowCount();i++)
	{
	     String key = getPanel().getGridExpectedResults().getExpectedResultsGridModel().getValueAt(i,0).toString();
	     String value = getPanel().getGridExpectedResults().getExpectedResultsGridModel().getValueAt(i,1).toString();
	     //precondition: the key and the value must not be empty and not repeated names
	     if (((!key.equalsIgnoreCase("")))&& //$NON-NLS-1$
	    		 (effect.getExpectedResultbyName(key)==null))
	     {
	    	 //get the created the expected result
	    	 ExpectedResult expRes = (ExpectedResult) ((CMBaseCell)getPanel().getGridExpectedResults().getExpectedResultsGridModel().getValueAt(i,0)).getModel();
	    	 //add the expected result to the effect
	    	 ce.addEdit(CMModelEditFactory.INSTANCE.createAddExpectedResult(effect,expRes));
	    	 effect.addExpectedResult(expRes);
	     }
	}
	//remove unused requirements from the structure
//	ArrayList<Requirement> unusedReqs = new ArrayList<Requirement>();
	 for (int i = 0;i<structure.getRequirements().size();i++)//Requirement req : structure.getLnkRequirements())
	 {
		 Requirement req = structure.getRequirements().get(i);
		 if (!req.isUsed())
		 {
			 ce.addEdit(CMModelEditFactory.INSTANCE.createRemoveRequirementModelEdit(structure,req));
			 structure.removeRequirement(req);
		}
	 }

	return ce;
}

public CMTwinBox getShuttle() {
	return this.shuttle;
}

public void setShuttle(CMTwinBox p_shuttle) {
	this.shuttle = p_shuttle;
}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return effect.getName()+"  "+getPanel().getJEditorPaneDescription().getText(); //$NON-NLS-1$
	}

	public int compareTo(Object o) {
		if (effect!=null)
			if (o instanceof CMEffectEditController)
			return effect.compareTo(((CMEffectEditController)o).getEffect());
		
			return 0;
	}

	public CMRiskLevelEditController getRiskLevelEditController() {
		if (riskLevelEditController == null){
			riskLevelEditController = new CMRiskLevelEditController();
			riskLevelEditController.setRiskLevelBean(getEffect());
		}
			
		return riskLevelEditController;
	}

	public CMStateEditController getStateEditController() {
		if (stateEditController == null){
			stateEditController = new CMStateEditController();
			stateEditController.setStateBean(effect);
		}
		return stateEditController;
	}
}
