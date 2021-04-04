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

import model.CMField;
import model.Dependency;
import model.Structure;
import model.util.CMModelEvent;
import model.util.CMModelEventHandler;

import org.apache.log4j.Logger;
import org.jdesktop.swingworker.SwingWorker;

import bi.controller.StructureManager;
import bi.controller.editcontrol.CMDependencyEditController;
import bi.view.actions.CMAction;
import bi.view.actions.CMEnabledAction;
import bi.view.icons.CMIcon;
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
public class CMDependencyEditAction extends AbstractAction implements CMEnabledAction {
	public CMDependencyEditAction() {
		super(CMMessages.getString("POPUPMENU_ITEM_EDIT_DEPENDENCY"));
	    // Set tool tip text
	    putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("BUTTON_TOOLTIP_EDIT_DEPENDENCY"));
	    // Set an icon
	    putValue(Action.SMALL_ICON, CMAction.DEPENDENCY_EDIT.getIcon());
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("MENU_ITEM_EDIT_MNEMONIC").charAt(0));
	    putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, Event.CTRL_MASK));
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent p_e) {
		// TODO Auto-generated method stub
		final Dependency selectedDependency = CMApplication.frame.getPanelDependencyCombiView().getCMGridDependencies().getSelectedDependency();
		 Structure structure = StructureManager.getSelectedStructure();


		 CMDefaultDialog dlg = new CMDefaultDialog();
		 if (selectedDependency != null) {
		 dlg.setSize(CMDependencyEditController.DEFAULT_DIALOG_SIZE);
		 final CMDependencyEditController editControl = new CMDependencyEditController(structure, selectedDependency);
		   dlg.setJPanelContained(editControl.getPanel());
		   dlg.setTitle(selectedDependency.getName()+" - "+this.getValue(Action.NAME));
		   dlg.show();
		 CMApplication.frame.setWaitCursor(false);
		    if (dlg.getModalResult() == CMModalResult.OK){
		    	SwingWorker worker = new SwingWorker(){

					@Override
					protected Object doInBackground() throws Exception {
						CMApplication.frame.getStatusLabel().setText("Changing Dependency... ");
						CMApplication.frame.getStatusLabel().setIcon(CMIcon.LOADING.getImageIcon());
						CMApplication.frame.setWaitCursor(true);
						CMApplication.frame.setEnabled(false);
						doEditDependency(editControl);
						CMApplication.frame.setEnabled(true);
						CMApplication.frame.getStatusLabel().setText("Ready");
						CMApplication.frame.getStatusLabel().setIcon(null);
						CMApplication.frame.setWaitCursor(false);
						return null;
					}};
					worker.execute();

		    }

        }

	}

	private void doEditDependency(final CMDependencyEditController editController){
		 final CMModelEvent evt = new CMModelEvent(editController.getStructure(),CMField.DEPENDENCIES);
		CMCompoundEdit ce = new CMCompoundEdit();
    	try {

			CMModelEventHandler.setNotifyEnabled(false);
    		ce.addEdit(editController.applyChanges());
    		CMModelEventHandler.setNotifyEnabled(true);
   		CMApplication.frame.getPanelDependencyCombiView().getCMGridDependencies().handleCMModelChange(evt);
    		CMApplication.frame.getPanelDependencyCombiView().getM_CMCombinationViews().handleCMModelChange(evt);
    		CMApplication.frame.getPanelDependencyCombiView().getM_CMDependencyElementView().handleCMModelChange(evt);
    		CMApplication.frame.getPanelDependencyCombiView().getCMGridDependencies().changeSelection(editController.getDependency());

			CMUndoMediator.getInstance().doMassiveEdit(ce);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(CMApplication.frame, e.getMessage(), this.getValue(Action.NAME).toString(),JOptionPane.ERROR_MESSAGE);
			Logger.getLogger(this.getClass()).error(e.getMessage());
		}
	}
	public boolean calculateEnabled() {
		return CMApplication.frame.getPanelDependencyCombiView().getCMGridDependencies().getSelectedDependency()!=null;
	}

}
