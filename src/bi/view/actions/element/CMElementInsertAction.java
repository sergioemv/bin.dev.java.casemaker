/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.view.actions.element;

import java.awt.event.ActionEvent;
import java.util.Arrays;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;

import model.BusinessRules;
import model.Element;
import model.Structure;
import model.edit.CMModelEditFactory;
import model.util.CMDelegate;
import bi.controller.ElementManager;
import bi.controller.SessionManager;
import bi.controller.StructureManager;
import bi.view.actions.CMAction;
import bi.view.actions.CMEnabledAction;
import bi.view.cells.headers.CMCellHeaderElementDescription;
import bi.view.cells.headers.CMCellHeaderElementGuiObject;
import bi.view.cells.headers.CMCellHeaderElementName;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.undomanagementviews.CMCompoundEdit;
import bi.view.undomanagementviews.undoviews.CMComponentAwareEdit;
import bi.view.undomanagementviews.undoviews.CMUndoMediator;

/**
 * @author smoreno
 *
 */
public class CMElementInsertAction extends AbstractAction implements Action,CMEnabledAction {
	private Object[] allowedCells = {CMCellHeaderElementDescription.class,
												CMCellHeaderElementGuiObject.class,
												CMCellHeaderElementName.class};
/**
 *
 */
public CMElementInsertAction() {
	super(CMMessages.getString("POPUPMENU_ITEM_INSERT_ELEMENT"));
//	 Set tool tip text
    putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("POPUPMENU_ITEM_INSERT_ELEMENT"));
    putValue(Action.LONG_DESCRIPTION, "Inserts a new element in the test object in the current grid position");
    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("MENU_ITEM_INSERT_MNEMONIC").charAt(0));
    putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_INSERT, 0));
    putValue(Action.SMALL_ICON, CMAction.ELEMENT_INSERT.getIcon());


}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent p_e) {
		if (!calculateEnabled()) return;
		CMApplication.frame.setWaitCursor(true);
		//get the row and column selected
		final int row = CMApplication.frame.getElementsGrid().getSelectionModel().getFirstSelectedRow();
		final int col = CMApplication.frame.getElementsGrid().getSelectionModel().getFirstSelectedColumn();
		//get the selected element
		Element selectedElement = CMApplication.frame.getElementsGrid().getCurrentElement();
		//get the selected structure
		Structure structure = StructureManager.getSelectedStructure();
		//view update for the undo
		CMCompoundEdit ce = new CMCompoundEdit();
		ce.addEdit(new CMComponentAwareEdit());
		ce.addUndoDelegateEdit(new CMDelegate(){
			/* (non-Javadoc)
			 * @see model.util.CMDelegate#execute()
			 */
			public void execute() {
				// TODO Auto-generated method stub
//				update the grids
				CMApplication.frame.getElementsGrid().deleteAllElementViews();
				CMApplication.frame.getElementsGrid().addAllElementViews();
				CMApplication.frame.getJSPlitPaneTestCaseStructureView().getM_CMElementAndTestCaseViews().update();
				CMApplication.frame.getJSplitPaneStdCombinationStructureView().getM_CMElementAndStdCombinationViews().update();
				//select the inserted element
				CMApplication.frame.getElementsGrid().changeSelection(row,col,false,false);

			}
		});
		//create a new element
		Element newElement = new Element();
		//set the id for the new element
		int newId = ElementManager.INSTANCE.getNextId(structure);
		ce.addEdit(CMModelEditFactory.INSTANCE.createChangeIdModelEdit(newElement,newId));
		newElement.setId(newId);
		//set the new name for the element
		String newName = ElementManager.INSTANCE.generateNewElementName(structure);
		ce.addEdit(CMModelEditFactory.INSTANCE.createChangeNameModelEdit(newElement,newName));
		newElement.setName(newName);
		//Add the element to the structure
		ce.addEdit(CMModelEditFactory.INSTANCE.createAddElementModelEdit(structure,newElement));
		structure.addElement(newElement);
		//change the user order of the element
		ce.addEdit(CMModelEditFactory.INSTANCE.createChangeUserOrderModelEdit(newElement,selectedElement.getUserOrder()));
		newElement.setUserOrder(selectedElement.getUserOrder());
		//move the elements order ahead
		for (int i = structure.getElements().size()-1;i>=0;i--)
		{
			Element element = structure.getElements().get(i);
			if (!element.equals(newElement))
			if (element.getUserOrder()>=newElement.getUserOrder())
			{
				ce.addEdit(CMModelEditFactory.INSTANCE.createChangeUserOrderModelEdit(element,element.getUserOrder()+1));
				element.setUserOrder(element.getUserOrder()+1);
			}
		}
		//redo update of the grids
		ce.addRedoDelegateEdit(new CMDelegate(){
			/* (non-Javadoc)
			 * @see model.util.CMDelegate#execute()
			 */
			public void execute() {
				// TODO Auto-generated method stub
//				update the grids
				CMApplication.frame.getElementsGrid().deleteAllElementViews();
				CMApplication.frame.getElementsGrid().addAllElementViews();
				CMApplication.frame.getJSPlitPaneTestCaseStructureView().getM_CMElementAndTestCaseViews().update();
				CMApplication.frame.getJSplitPaneStdCombinationStructureView().getM_CMElementAndStdCombinationViews().update();
				//select the inserted element
				CMApplication.frame.getElementsGrid().changeSelection(row,col,false,false);

			}
		});
		//update the grids
		CMApplication.frame.getElementsGrid().deleteAllElementViews();
		CMApplication.frame.getElementsGrid().addAllElementViews();
		CMApplication.frame.getJSPlitPaneTestCaseStructureView().getM_CMElementAndTestCaseViews().update();
		CMApplication.frame.getJSplitPaneStdCombinationStructureView().getM_CMElementAndStdCombinationViews().update();
		//select the inserted element
		CMApplication.frame.getElementsGrid().changeSelection(row,col,false,false);
		//CMApplication.frame.getM_CMGridElements().insertElement();
		CMUndoMediator.getInstance().doEdit(ce);
		CMApplication.frame.getElementsGrid().requestFocus();
		CMApplication.frame.setWaitCursor(false);
	}
	public boolean calculateEnabled() {
		if (CMApplication.frame.getTreeWorkspaceView().getCurrentTestObject()!=null)
			{
				if ((CMApplication.frame.getTreeWorkspaceView().getCurrentTestObject().getAccessState().equalsIgnoreCase(BusinessRules.ACCESS_STATE_CHECKED_IN))||
					(!CMApplication.frame.getTreeWorkspaceView().getCurrentTestObject().getUser().equalsIgnoreCase(SessionManager.getCurrentSession().getM_User())))
					return false;
			}
		int row = CMApplication.frame.getElementsGrid().getSelectionModel().getFirstSelectedRow();
		int col = CMApplication.frame.getElementsGrid().getSelectionModel().getFirstSelectedColumn();
		return (col!=-1&&row!=-1)
		&&(CMApplication.frame.getElementsGrid().getColumnCount()>col &&CMApplication.frame.getElementsGrid().getRowCount()>row)
		&&(Arrays.asList(allowedCells).contains(CMApplication.frame.getElementsGrid().getValueAt(row,col).getClass()));
	}
}
