/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.view.actions.equivalenceclass;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import model.CMField;
import model.Dependency;
import model.EquivalenceClass;
import model.util.CMModelEvent;
import model.util.CMModelEventHandler;
import bi.controller.ElementManager;
import bi.controller.EquivalenceClassManager;
import bi.controller.TestCaseManager;
import bi.view.actions.CMAbstractAction;
import bi.view.actions.CMAction;
import bi.view.actions.CMEnabledAction;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.undomanagementviews.CMCompoundEdit;
import bi.view.undomanagementviews.undoviews.CMUndoMediator;

/**
 * @author smoreno
 *
 */
public class CMEquivalenceClassDeleteAction extends AbstractAction implements
		CMEnabledAction {
	private boolean askUser=true;
	public CMEquivalenceClassDeleteAction() {
		super(CMMessages.getString("POPUPMENU_ITEM_DELETE_EQUIVALENCE_CLASS"));
	    // Set tool tip text
	    putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("BUTTON_TOOLTIP_DELETE_EQUIVALENCE_CLASS"));
	    // Set an icon
	    putValue(Action.SMALL_ICON, CMAction.EQUIVALENCECLASS_DELETE.getIcon());
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("MENU_ITEM_DELETE_ALL_MNEMONIC").charAt(0));
	    putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_DELETE, 0));
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent p_e) {

			if (!calculateEnabled()) return;
			EquivalenceClass selectedEquivalenceClass = EquivalenceClassManager.getSelectedEquivalenceClass();
			if(isAskUser())
		    if( JOptionPane.showConfirmDialog(CMApplication.frame,CMMessages.getString("QUESTION_DELETE_EQUIVALENCE_CLASS"),
		    		CMMessages.getString("LABEL_DELETE_EQUIVALENCE_CLASS"),JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) 
		    		return;
		    
		    	CMCompoundEdit ce = new CMCompoundEdit();
		    	try {
		    		CMModelEventHandler.setNotifyEnabled(false);
					CMApplication.frame.setWaitCursor(true);
					CMApplication.frame.setEnabled(false);
					ce.addEdit(EquivalenceClassManager.INSTANCE.removeEquivalenceClass(selectedEquivalenceClass));
					CMApplication.frame.setEnabled(true);
					List<Object> modObjects = new ArrayList<Object>();
					ce.fillModifiedObjects(modObjects);
					CMAbstractAction.updateViews(modObjects);
					CMApplication.frame.setWaitCursor(false);
					CMModelEventHandler.setNotifyEnabled(true);
					CMUndoMediator.getInstance().doMassiveEdit(ce);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(CMApplication.frame, "An error ocurred :"+ e.getMessage());
					e.printStackTrace();
				}
		    
		    CMApplication.frame.getElementsGrid().requestFocus();



	}
	
	public boolean calculateEnabled() {
		return EquivalenceClassManager.getSelectedEquivalenceClass()!=null;

	}
	public boolean isAskUser() {
		return askUser;
	}
	public void setAskUser(boolean askUser) {
		this.askUser = askUser;
	}

}
