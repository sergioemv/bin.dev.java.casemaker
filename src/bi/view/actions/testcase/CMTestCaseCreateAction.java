/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved. 
 */
package bi.view.actions.testcase;

import java.awt.Event;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;

import model.Structure;
import model.TestCase;
import bi.controller.StructureManager;
import bi.controller.TestCaseManager;
import bi.controller.editcontrol.CMTestCaseEditController;
import bi.view.actions.CMAction;
import bi.view.actions.CMEnabledAction;
import bi.view.edit.CMViewEditFactory;
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
public class CMTestCaseCreateAction extends AbstractAction implements CMEnabledAction {
	public CMTestCaseCreateAction() {
		super(CMMessages.getString("POPUPMENU_ITEM_CREATE_TEST_CASE"));
	    // Set tool tip text
	    putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("BUTTON_TOOLTIP_CREATE_TEST_CASE"));
	    // Set an icon
	    putValue(Action.SMALL_ICON, CMAction.TESTCASE_CREATE.getIcon());
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("MENU_ITEM_CREATE_1_MNEMONIC").charAt(0));
	    putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, Event.CTRL_MASK));
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent p_e) {
		CMCompoundEdit ce = new CMCompoundEdit();
		Structure m_Structure = StructureManager.getSelectedStructure();
		if (!calculateEnabled()) return;
	 
		TestCase newTestCase =  new TestCase();
	    CMDefaultDialog dlg = new CMDefaultDialog();
		 dlg.setSize(CMTestCaseEditController.DEFAULT_DIALOG_SIZE);
		 CMTestCaseEditController caseEditController = new CMTestCaseEditController(newTestCase);	
		 dlg.setJPanelContained(caseEditController.getPanel());
		 dlg.setTitle((String) this.getValue(Action.NAME));
		 dlg.show();
		 CMApplication.frame.setWaitCursor(false);
		 if (dlg.getModalResult() == CMModalResult.OK){
		       newTestCase.setOriginType(TestCase.Origin.MANUAL);
		       newTestCase.setDependencies(null);
		       ce.addEdit(new CMComponentAwareEdit());
		       ce.addEdit(caseEditController.applyChanges());
		       ce.addEdit(TestCaseManager.INSTANCE.addTestCaseToStructure(m_Structure, newTestCase));
		       ce.addEdit(CMViewEditFactory.INSTANCE.createSelectTestCaseGridViewEdit(newTestCase));
		       CMUndoMediator.getInstance().doEdit(ce);
		       CMApplication.frame.getJSPlitPaneTestCaseStructureView().getM_CMDescriptionTestCaseViews().selectCell(newTestCase,null);
		  	  }
	
	      }

		
	
	public boolean calculateEnabled() {
		// TODO Auto-generated method stub
		return StructureManager.getSelectedStructure()!=null  && StructureManager.getSelectedStructure().getElements().size()>0;
	}

}
