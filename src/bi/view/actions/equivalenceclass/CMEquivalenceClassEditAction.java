/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved. 
 */
package bi.view.actions.equivalenceclass;

import java.awt.Event;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Action;
import javax.swing.KeyStroke;

import model.CMField;
import model.Dependency;
import model.EquivalenceClass;
import model.util.CMModelEvent;
import model.util.CMModelEventHandler;
import bi.controller.ElementManager;
import bi.controller.EquivalenceClassManager;
import bi.controller.TestCaseManager;
import bi.controller.editcontrol.CMEquivalenceClassEditController;
import bi.view.actions.CMAbstractAction;
import bi.view.actions.CMAction;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.undomanagementviews.CMCompoundEdit;
import bi.view.undomanagementviews.undoviews.CMComponentAwareEdit;
import bi.view.undomanagementviews.undoviews.CMUndoMediator;
import bi.view.utils.CMDefaultDialog;
import bi.view.utils.CMModalResult;

/**
 * @author smoreno
 *
 */
public class CMEquivalenceClassEditAction extends CMAbstractAction {
	
	
	public CMEquivalenceClassEditAction() {
		super(CMMessages.getString("POPUPMENU_ITEM_EDIT_EQUIVALENCE_CLASS"));
	    // Set tool tip text
	    putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("BUTTON_TOOLTIP_EDIT_EQUIALENCE_CLASS"));
	    // Set an icon
	    putValue(Action.SMALL_ICON, CMAction.EQUIVALENCECLASS_EDIT.getIcon());
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("MENU_ITEM_EDIT_MNEMONIC").charAt(0));
	    putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, Event.CTRL_MASK));
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void execute() {
		CMApplication.frame.setWaitCursor(true);
		getWarningMessages().clear();
		 EquivalenceClass equivalenceClass = EquivalenceClassManager.getSelectedEquivalenceClass();	   
		   CMDefaultDialog dlg = new CMDefaultDialog();
		   dlg.setSize(CMEquivalenceClassEditController.DEFAULT_DIALOG_SIZE);
		   CMEquivalenceClassEditController editControl = new CMEquivalenceClassEditController(equivalenceClass.getLnkElement(), equivalenceClass);
		   dlg.setJPanelContained(editControl.getPanel());
		   dlg.setTitle(equivalenceClass.getName()+" - "+this.getValue(Action.NAME));
		    dlg.show();
		    CMApplication.frame.setWaitCursor(false);
		    if (dlg.getModalResult() == CMModalResult.OK){
		    	CMCompoundEdit ce = new CMCompoundEdit();
		    	ce.addEdit(new CMComponentAwareEdit());
		    	CMModelEventHandler.setNotifyEnabled(false);
		    	ce.addEdit(editControl.applyChanges());
		    	CMModelEventHandler.setNotifyEnabled(true);
		    	List<Object> modObjects = new ArrayList<Object>();
				ce.fillModifiedObjects(modObjects);
				updateViews(modObjects);
		    	getWarningMessages().addAll(editControl.getWarningMessages());
		    	CMUndoMediator.getInstance().doMassiveEdit(ce);
						
		    }
		    dlg.dispose();
		   CMApplication.frame.getElementsGrid().requestFocus();
	}



}
