/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved. 
 */
package bi.view.actions.dependency;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import model.CMField;
import model.Dependency;
import model.util.CMModelEvent;
import model.util.CMModelEventHandler;
import bi.controller.DependencyManager;
import bi.view.actions.CMAction;
import bi.view.actions.CMEnabledAction;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.undomanagementviews.undoviews.CMUndoMediator;

/**
 * @author smoreno
 *
 */
public class CMDependencyDeleteAction extends AbstractAction implements CMEnabledAction {
	public CMDependencyDeleteAction() {
		super(CMMessages.getString("POPUPMENU_ITEM_DELETE_DEPENDENCY"));
	    // Set tool tip text
	    putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("BUTTON_TOOLTIP_DELETE_DEPENDENCY"));
	    // Set an icon
	    putValue(Action.SMALL_ICON, CMAction.DEPENDENCY_DELETE.getIcon());
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("MENU_ITEM_DELETE_CAUSEEFFECT_MNEMONIC").charAt(0));
	    putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_DELETE,0 ));
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent p_e) {
		 Dependency selectedDependency = DependencyManager.getSelectedDependency();
		 if (selectedDependency == null) return;
	     if (JOptionPane.showConfirmDialog(CMApplication.frame, CMMessages.getString("QUESTION_DELETE_DEPENDENCY"),
		                CMMessages.getString("LABEL_DELETE_DEPENDENCY"), JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) 
	    	 return;
	         try{
	        	 CMModelEventHandler.setNotifyEnabled(false);
	        	 CMUndoMediator.getInstance().doMassiveEdit(
	    	 DependencyManager.INSTANCE.deleteDependency(selectedDependency, selectedDependency.getLnkStructure()));
	        	 CMModelEventHandler.setNotifyEnabled(true);
	        	 CMModelEvent evt = new CMModelEvent(selectedDependency,CMField.COMBINATIONS);
	 			CMApplication.frame.getPanelDependencyCombiView().getM_CMCombinationViews().handleCMModelChange(evt);
	 			CMApplication.frame.getPanelDependencyCombiView().getM_CMDependencyElementView().handleCMModelChange(evt);
	 			CMModelEvent evt1 = new CMModelEvent(selectedDependency.getLnkStructure(),CMField.DEPENDENCIES);
	 			CMApplication.frame.getPanelDependencyCombiView().getCMGridDependencies().handleCMModelChange(evt1);
	         } catch (Exception e){
	        	 JOptionPane.showMessageDialog(CMApplication.frame, "An Error Happened : " +e.getMessage());
	         }
	      CMApplication.frame.getPanelDependencyCombiView().getCMGridDependencies().requestFocus();
	}
	public boolean calculateEnabled() {
		
		return DependencyManager.getSelectedDependency() != null &&  DependencyManager.getSelectedDependency().getLnkStructure()!=null;
	}

}
