/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.view.actions.element;

import java.awt.Event;
import java.awt.event.ActionEvent;

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
import bi.view.cells.CMCellElementDescription;
import bi.view.cells.CMCellElementName;
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
public class CMElementCreateAction extends AbstractAction implements CMEnabledAction {

/**
 *  Action for creating new elements
 */
 public CMElementCreateAction() {
	super(CMMessages.getString("POPUPMENU_ITEM_CREATE_ELEMENT"));
    // Set tool tip text
    putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("BUTTON_TOOLTIP_CREATE_ELEMENT"));
    putValue(Action.SMALL_ICON, CMAction.ELEMENT_CREATE.getIcon());
    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("MENU_ITEM_CREATE_1_MNEMONIC").charAt(0));
    putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, Event.CTRL_MASK));

}
	public void actionPerformed(ActionEvent p_e) {
		if (!calculateEnabled()) return;
		CMApplication.frame.setWaitCursor(true);
		//get the row and column selected
		final int row = CMApplication.frame.getElementsGrid().getSelectionModel().getFirstSelectedRow();
		final int col = CMApplication.frame.getElementsGrid().getSelectionModel().getFirstSelectedColumn();
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
		ce.addRedoDelegateEdit(new CMDelegate(){
			/* (non-Javadoc)
			 * @see model.util.CMDelegate#execute()
			 */
			public void execute() {
				// TODO Auto-generated method stub
				//select the inserted element
				CMApplication.frame.getElementsGrid().changeSelection(CMApplication.frame.getElementsGrid().getRowCount()-1,0,false,false);

			}
		});
		//select the inserted element name
		CMApplication.frame.getElementsGrid().changeSelection(CMApplication.frame.getElementsGrid().getRowCount()-1,0,false,false);

		CMUndoMediator.getInstance().doEdit(ce);
		CMApplication.frame.getElementsGrid().requestFocus();
		CMApplication.frame.setWaitCursor(false);
	}
	/* (non-Javadoc)
	 * @see bi.view.actions.CMEnabledAction#calculateEnabled()
	 */
	public boolean calculateEnabled() {

		if (CMApplication.frame.getTreeWorkspaceView().getCurrentTestObject()!=null)
			{
				if ((CMApplication.frame.getTreeWorkspaceView().getCurrentTestObject().getAccessState().equalsIgnoreCase(BusinessRules.ACCESS_STATE_CHECKED_IN))||
					(!CMApplication.frame.getTreeWorkspaceView().getCurrentTestObject().getUser().equalsIgnoreCase(SessionManager.getCurrentSession().getM_User())))
					return false;
			}


		return isCellToCreateanElement() || (CMApplication.frame.getElementsGrid().getModel().getRowCount()== 0);
	}

	private boolean isCellToCreateanElement(){

		int r=CMApplication.frame.getElementsGrid().getFirstSelectedRow();
		int c=CMApplication.frame.getElementsGrid().getFirstSelectedColumn();

		Object object = CMApplication.frame.getElementsGrid().getCellObjectAt(r, c);

		if(object instanceof CMCellHeaderElementName || object instanceof CMCellHeaderElementDescription
        		|| object instanceof CMCellHeaderElementGuiObject || object instanceof CMCellElementName
        		|| object instanceof CMCellElementDescription
        ){
			return true;
		}
		else
			return false;
	}

}
