/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.view.actions.element;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import model.BusinessRules;
import model.CMField;
import model.Dependency;
import model.Element;
import model.util.CMDelegate;
import model.util.CMModelEvent;
import model.util.CMModelEventHandler;
import bi.controller.ElementManager;
import bi.controller.SessionManager;
import bi.controller.TestCaseManager;
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
@SuppressWarnings("serial")
public class CMElementDeleteAction extends AbstractAction implements CMEnabledAction {
	private int index = 0;
	private int col = 0;
	private boolean askUser;
	private Object[] allowedCells  = {CMCellHeaderElementDescription.class,
												CMCellHeaderElementGuiObject.class,
												CMCellHeaderElementName.class,
												CMCellElementName.class,
												CMCellElementDescription.class,
												CMCellHeaderElementGuiObject.class
												};
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	/**
	 *
	 */
	public CMElementDeleteAction() {

	   super(CMMessages.getString("POPUPMENU_ITEM_DELETE_ELEMENT"));
	    // Set tool tip text
	    putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("BUTTON_TOOLTIP_DELETE_ELEMENT"));
	    putValue(Action.LONG_DESCRIPTION, "Deletes a element in the test object");
	    // Set an icon
	    putValue(Action.SMALL_ICON,  CMAction.ELEMENT_DELETE.getIcon());
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("MENU_ITEM_DELETE_MNEMONIC").charAt(0));
	    putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_DELETE,0));

	}
	public void actionPerformed(ActionEvent p_e) {
		if (!calculateEnabled()) return;
		final CMCompoundEdit ce = new CMCompoundEdit();
		
		
		
		CMApplication.frame.eventMouseClicked(null);
		CMApplication.frame.setWaitCursor(true);
		final Element element = CMApplication.frame.getElementsGrid().getSelectedElement();
		index = CMApplication.frame.getElementsGrid().getSelectionModel().getLeadRow();
		col = CMApplication.frame.getElementsGrid().getSelectionModel().getLeadColumn();
		//ccastedo begins 14.08.07
		ce.addEdit(new CMComponentAwareEdit());
		ce.addUndoDelegateEdit(new CMDelegate(){
		
			public void execute() {		
				CMApplication.frame.getElementsGrid().changeSelection(index+1,col,false,false);
			}
		});
		//ccastedo ends 14.08.07
		Object obj = CMApplication.frame.getElementsGrid().getCellObjectAt(index,col);
		if (obj instanceof CMCellElementName || obj instanceof CMCellElementDescription){
			index--;
        }
		if (isAskUser())
	    if ( JOptionPane.showConfirmDialog(CMApplication.frame,CMMessages.getString("QUESTION_DELETE_ELEMENT"),
	    		CMMessages.getString("LABEL_DELETE_ELEMENT"),JOptionPane.YES_NO_OPTION)!= JOptionPane.YES_OPTION)
	    		return;
	        try {

			        	CMModelEventHandler.setNotifyEnabled(false);
						CMApplication.frame.setWaitCursor(true);
						CMApplication.frame.setEnabled(false);
						ce.addEdit(ElementManager.INSTANCE.deleteElement(element));
						List<Object> modObjects = new ArrayList<Object>();
						ce.fillModifiedObjects(modObjects);
						updateViews(modObjects);
						CMApplication.frame.setWaitCursor(false);
						CMModelEventHandler.setNotifyEnabled(true);
						CMUndoMediator.getInstance().doMassiveEdit(ce);

						CMApplication.frame.setEnabled(true);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

        CMApplication.frame.setWaitCursor(false);
        //CMApplication.frame.getM_CMGridElements().requestFocus();
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

	private void updateViews(List<Object>  modifiedObjects) {
		ElementManager.reloadElementViews();
		TestCaseManager.INSTANCE.reloadTestCaseViews();
		Dependency lastModDep = null;

		for (Object ob : modifiedObjects){
			if (ob instanceof Dependency)
				lastModDep =(Dependency) ob;

		}
		if (lastModDep!=null){
			CMModelEvent evt = new CMModelEvent(lastModDep,CMField.COMBINATIONS);
			CMApplication.frame.getPanelDependencyCombiView().getM_CMCombinationViews().handleCMModelChange(evt);
			CMApplication.frame.getPanelDependencyCombiView().getM_CMDependencyElementView().handleCMModelChange(evt);
		}
	}
	public boolean isAskUser() {
		return askUser;
	}
	public void setAskUser(boolean askUser) {
		this.askUser = askUser;
	}
}
