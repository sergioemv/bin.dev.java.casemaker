/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.view.actions.dependency;

import java.awt.Event;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import model.Dependency;
import model.Structure;
import model.edit.CMModelEditFactory;
import model.util.CMDelegate;

import org.apache.log4j.Logger;

import bi.controller.DependencyManager;
import bi.controller.StructureManager;
import bi.controller.editcontrol.CMCombinationEditController;
import bi.controller.editcontrol.CMDependencyEditController;
import bi.view.actions.CMAction;
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
public class CMDependencyCreateAction extends AbstractAction implements Action {
	public CMDependencyCreateAction() {
		super(CMMessages.getString("POPUPMENU_ITEM_CREATE_DEPENDENCY"));
	    // Set tool tip text
	    putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("BUTTON_TOOLTIP_CREATE_DEPENDENCY"));
	    // Set an icon
	    putValue(Action.SMALL_ICON, CMAction.DEPENDENCY_CREATE.getIcon());
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("MENU_ITEM_CREATE_MNEMONIC").charAt(0));
	    putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, Event.CTRL_MASK));
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent p_e) {
		 Structure structure = StructureManager.getSelectedStructure();


		 CMDefaultDialog dlg = new CMDefaultDialog();
		   dlg.setSize(CMCombinationEditController.DEFAULT_DIALOG_SIZE);
		   final Dependency dependency = DependencyManager.INSTANCE.createDependency(structure);

		   CMDependencyEditController editControl = new CMDependencyEditController(structure, dependency);
		   dlg.setJPanelContained(editControl.getPanel());
		   dlg.setTitle(dependency.getName()+" - "+this.getValue(Action.NAME));
		   dlg.show();
		    CMApplication.frame.setWaitCursor(false);
		    if (dlg.getModalResult() == CMModalResult.OK){
		    	CMCompoundEdit ce = new CMCompoundEdit();
		    	try {
					ce.addEdit(editControl.applyChanges());
					ce.addEdit(CMModelEditFactory.INSTANCE.createAddDependencyModelEdit(structure,dependency));
			    	structure.addDependency(dependency);
			    	ce.addRedoDelegateEdit(new CMDelegate(){

						public void execute() {
							DependencyManager.INSTANCE.selectDependencyView(dependency);
						}});
			    	CMUndoMediator.getInstance().doEdit(ce);
			    	// select the new dependency
			    	DependencyManager.INSTANCE.selectDependencyView(dependency);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(CMApplication.frame, e.getMessage(), this.getValue(Action.NAME).toString(),JOptionPane.ERROR_MESSAGE);
					Logger.getLogger(this.getClass()).error(e.getMessage());
				}


		    }

	}

}
