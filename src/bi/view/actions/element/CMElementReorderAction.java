/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.view.actions.element;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;

import model.Element;
import model.Structure;
import model.edit.CMModelEditFactory;
import model.util.CMDelegate;
import model.util.CMUserOrderBean;
import bi.controller.StructureManager;
import bi.controller.TestCaseManager;
import bi.view.actions.CMAction;
import bi.view.actions.CMEnabledAction;
import bi.view.actions.file.reorder.CMReorderDialog;
import bi.view.icons.CMIcon;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.undomanagementviews.CMCompoundEdit;
import bi.view.undomanagementviews.undoviews.CMComponentAwareEdit;
import bi.view.undomanagementviews.undoviews.CMUndoMediator;
import bi.view.utils.CMDnDJList;
import bi.view.utils.CMModalResult;

/**
 * @author smoreno
 *
 */
public class CMElementReorderAction extends AbstractAction implements CMEnabledAction {

/**
 *  Action for creating new elements
 */
 public CMElementReorderAction() {
	super(CMMessages.getString("ELEMENT_REORDER_ACTION_NAME"));     //$NON-NLS-1$
    // Set tool tip text
    putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("ELEMENT_REORDER_ACTION_DESCRIPTION")); //$NON-NLS-1$
    putValue(Action.SMALL_ICON, CMAction.ELEMENT_REORDER.getIcon());
    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("ELEMENT_REORDER_ACTION_MNEMONIC").charAt(0)); //$NON-NLS-1$

}
	public void actionPerformed(ActionEvent p_e) {
		if (!calculateEnabled()) return;
		Structure structure = StructureManager.getSelectedStructure();
		CMReorderDialog dlg = new CMReorderDialog();
		//setup the dialog
		dlg.setTitle(structure.getTestObject().getName()+" - "+this.getValue(Action.NAME)); //$NON-NLS-1$
		((CMDnDJList)dlg.getOrderjList()).setLeftIcon(CMIcon.ELEMENT.getImageIcon()); //$NON-NLS-1$
		//add the elements to reorder
		dlg.AddObjectsToOrder(structure.getElements(CMUserOrderBean.COMPARATOR));
		//show the dialog to reorder elements
		dlg.show();
		//if the user clicks ok
		if (dlg.getModalResult() == CMModalResult.OK)
		{
			CMApplication.frame.setWaitCursor(true);
			CMCompoundEdit ce = new CMCompoundEdit();
			ce.addEdit(new CMComponentAwareEdit());
			ce.addUndoDelegateEdit(new CMDelegate(){
				public void execute() {
					CMApplication.frame.getElementsGrid().deleteAllElementViews();
					CMApplication.frame.getElementsGrid().addAllElementViews();
					TestCaseManager.INSTANCE.reloadTestCaseViews();
					CMApplication.frame.getCMStdCombinationViews().getM_CMElementAndStdCombinationViews().update();
				}});
			List correctOrder = dlg.getOrderedObjects();
			//change the user order attibutte for each element
			for (Element element : structure.getElements()){
				if (element.getUserOrder() != correctOrder.indexOf(element))
				{
				ce.addEdit(CMModelEditFactory.INSTANCE.createChangeUserOrderModelEdit(element,correctOrder.indexOf(element)));
				element.setUserOrder(correctOrder.indexOf(element));
				}
			}
			ce.addRedoDelegateEdit(new CMDelegate(){
				public void execute() {
					CMApplication.frame.getElementsGrid().deleteAllElementViews();
					CMApplication.frame.getElementsGrid().addAllElementViews();
					TestCaseManager.INSTANCE.reloadTestCaseViews();
					CMApplication.frame.getCMStdCombinationViews().getM_CMElementAndStdCombinationViews().update();
				}

			});
			CMApplication.frame.getElementsGrid().deleteAllElementViews();
			CMApplication.frame.getElementsGrid().addAllElementViews();
			TestCaseManager.INSTANCE.reloadTestCaseViews();
			CMApplication.frame.getCMStdCombinationViews().getM_CMElementAndStdCombinationViews().update();
			if (ce.hasEdits())
				CMUndoMediator.getInstance().doEdit(ce);

		}
		CMApplication.frame.setWaitCursor(false);
	    CMApplication.frame.getElementsGrid().requestFocus();
	}
	/* (non-Javadoc)
	 * @see bi.view.actions.CMEnabledAction#calculateEnabled()
	 */
	public boolean calculateEnabled() {
		return ((CMEnabledAction)CMAction.IMPORT_CSV.getAction()).calculateEnabled();
	}


}
