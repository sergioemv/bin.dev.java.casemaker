/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved. 
 */
package bi.view.actions.combination;

import java.awt.Event;
import javax.swing.Action;
import javax.swing.KeyStroke;
import model.Combination;
import model.Dependency;
import model.edit.CMModelEditFactory;
import bi.controller.CombinationManager;
import bi.controller.DependencyManager;
import bi.controller.editcontrol.CMCombinationEditController;
import bi.view.actions.CMAbstractAction;
import bi.view.actions.CMAction;
import bi.view.actions.CMEnabledAction;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.undomanagementviews.CMCompoundEdit;
import bi.view.undomanagementviews.undoviews.CMUndoMediator;
import bi.view.utils.CMDefaultDialog;
import bi.view.utils.CMModalResult;

/**
 * @author smoreno
 *
 */
@SuppressWarnings("serial")
public class CMCombinationCreateAction extends CMAbstractAction implements CMEnabledAction {
	public CMCombinationCreateAction() {
		super(CMMessages.getString("POPUPMENU_ITEM_CREATE_COMBINATION"));
	    // Set tool tip text
	    putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("BUTTON_TOOLTIP_CREATE_COMBINATION"));
	    // Set an icon
	    putValue(Action.SMALL_ICON, CMAction.COMBINATION_CREATE.getIcon());
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("MENU_ITEM_CREATE_MNEMONIC").charAt(0));
	    putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, Event.CTRL_MASK));
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void execute() {
		    if (!calculateEnabled()) return;
			final Dependency selectedDependency = DependencyManager.getSelectedDependency();
			 CMDefaultDialog dlg = new CMDefaultDialog();
			   dlg.setSize(CMCombinationEditController.DEFAULT_DIALOG_SIZE);
			   Combination combination = CombinationManager.INSTANCE.createCombination(selectedDependency,new CMCompoundEdit());
			   CMCombinationEditController editControl = new CMCombinationEditController(selectedDependency.getLnkStructure(), combination);
			   dlg.setJPanelContained(editControl.getPanel());
			   dlg.setTitle(combination.getName()+" - "+this.getValue(Action.NAME));
			   dlg.show();
			    CMApplication.frame.setWaitCursor(false);
			    if (dlg.getModalResult() == CMModalResult.OK){
			    	CMCompoundEdit ce = new CMCompoundEdit();
			    	ce.addEdit(CMModelEditFactory.INSTANCE.createAddCombinationModelEdit(selectedDependency, combination));
			    	selectedDependency.addCombination(combination);
			    	ce.addEdit(editControl.applyChanges());
			    	getWarningMessages().addAll(editControl.getWarningMessages());
			    	ce.addEdit(CMModelEditFactory.INSTANCE.createChangeOriginTypeModelEdit(combination, Combination.Origin.MANUAL));
			    	combination.setOriginType(Combination.Origin.MANUAL);		    	
			    	CMUndoMediator.getInstance().doEdit(ce);			
			    }
			    dlg.dispose();
			
			   
			    int column = CMApplication.frame.getCMGridCombinationViews().getColumnCount()-1;
			    CMApplication.frame.getCMGridCombinationViews().changeSelection(0, column, false, false);
			    CMApplication.frame.getCMGridCombinationViews().requestFocus();

	}
	public boolean calculateEnabled() {
		Dependency dependency = DependencyManager.getSelectedDependency();
		if (dependency != null)
			if (dependency.getCombinations().size()==0)
				return true;
			else
				if (dependency.getCombinations().get(0).getOriginType()!=Combination.Origin.PERMUTATION)
					return true;
		return false;
	}

}
