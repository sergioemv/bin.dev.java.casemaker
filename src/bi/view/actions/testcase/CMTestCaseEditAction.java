/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved. 
 */
package bi.view.actions.testcase;

import java.awt.Dimension;
import java.awt.Event;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;

import model.EquivalenceClass;
import model.TestCase;
import bi.controller.EquivalenceClassManager;
import bi.controller.TestCaseManager;
import bi.controller.editcontrol.CMEquivalenceClassEditController;
import bi.controller.editcontrol.CMTestCaseEditController;
import bi.view.actions.CMAction;
import bi.view.edit.CMViewEditFactory;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.undomanagementviews.CMCompoundEdit;
import bi.view.undomanagementviews.undoviews.CMComponentAwareEdit;
import bi.view.undomanagementviews.undoviews.CMUndoMediator;
import bi.view.utils.CMDefaultDialog;
import bi.view.utils.CMModalResult;

import com.eliad.model.GenericGridModel;

/**
 * @author smoreno
 *
 */
public class CMTestCaseEditAction extends AbstractAction implements Action {
	public CMTestCaseEditAction() {
		super(CMMessages.getString("POPUPMENU_ITEM_EDIT_TEST_CASE"));
	     // Set an icon
		putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("BUTTON_TOOLTIP_EDIT_TEST_CASE"));
	    putValue(Action.SMALL_ICON, CMAction.TESTCASE_EDIT.getIcon());
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("MENU_ITEM_EDIT_1_MNEMONIC").charAt(0));
	    putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, Event.CTRL_MASK));
	 	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent p_e) {
		TestCase selectedTestCase = TestCaseManager.getSelectedTestCase();
		
	    if( selectedTestCase != null) {
	    	EquivalenceClass equivalenceClass = TestCaseManager.getSelectedEquivalenceClass();
	    	  CMDefaultDialog dlg = new CMDefaultDialog();
			   dlg.setSize(CMTestCaseEditController.DEFAULT_DIALOG_SIZE);
			   CMTestCaseEditController caseEditController = new CMTestCaseEditController(selectedTestCase);	
			   caseEditController.setEquivalenceClass(equivalenceClass);
			   dlg.setJPanelContained(caseEditController.getPanel());
			   dlg.setTitle(selectedTestCase.getName()+" - "+this.getValue(Action.NAME));
			    dlg.show();
			    CMApplication.frame.setWaitCursor(false);
			    if (dlg.getModalResult() == CMModalResult.OK){
			    	CMCompoundEdit ce = new CMCompoundEdit();
			    	ce.addEdit(new CMComponentAwareEdit());
			    	ce.addEdit(caseEditController.applyChanges());
			    	ce.addEdit(CMViewEditFactory.INSTANCE.createSelectTestCaseGridViewEdit(selectedTestCase));
			    	CMUndoMediator.getInstance().doEdit(ce);
			    	CMApplication.frame.getCMTestCaseViews().getLnkCMElementViews().update();
			    }
			    dlg.dispose();
			    
	}
	    CMApplication.frame.getCMTestCaseViews().requestFocus();
	}
}
