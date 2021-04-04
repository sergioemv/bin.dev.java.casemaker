/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.view.actions.equivalenceclass;

import java.awt.Event;
import java.util.Arrays;

import javax.swing.Action;
import javax.swing.KeyStroke;

import model.BusinessRules;
import model.Element;
import model.EquivalenceClass;
import model.edit.CMModelEditFactory;
import model.util.CMDelegate;
import bi.controller.ElementManager;
import bi.controller.EquivalenceClassManager;
import bi.controller.SessionManager;
import bi.controller.editcontrol.CMEquivalenceClassEditController;
import bi.view.actions.CMAbstractAction;
import bi.view.actions.CMAction;
import bi.view.actions.CMEnabledAction;
import bi.view.cells.CMCellElementDescription;
import bi.view.cells.CMCellElementDescriptionEmpty;
import bi.view.cells.CMCellElementGuiObject;
import bi.view.cells.CMCellElementName;
import bi.view.cells.CMCellEquivalenceClassDescription;
import bi.view.cells.CMCellEquivalenceClassName;
import bi.view.cells.CMCellEquivalenceClassState;
import bi.view.cells.CMCellEquivalenceClassValue;
import bi.view.cells.headers.CMCellHeaderElementDescription;
import bi.view.cells.headers.CMCellHeaderElementGuiObject;
import bi.view.cells.headers.CMCellHeaderElementName;
import bi.view.cells.headers.CMCellHeaderEquivalenceClassDescription;
import bi.view.cells.headers.CMCellHeaderEquivalenceClassEffects;
import bi.view.cells.headers.CMCellHeaderEquivalenceClassName;
import bi.view.cells.headers.CMCellHeaderEquivalenceClassState;
import bi.view.cells.headers.CMCellHeaderEquivalenceClassValue;
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
public class CMEquivalenceClassCreateAction extends CMAbstractAction implements
		 CMEnabledAction {
	private final Object[] allowedCells = {
			CMCellHeaderElementDescription.class,
			CMCellHeaderElementGuiObject.class,
			CMCellHeaderElementName.class,
			CMCellElementName.class,
			CMCellElementDescription.class,
			CMCellElementGuiObject.class,
			CMCellElementDescriptionEmpty.class,
			CMCellEquivalenceClassDescription.class,
			CMCellEquivalenceClassName.class,
			CMCellEquivalenceClassState.class,
			CMCellEquivalenceClassValue.class,
			CMCellHeaderEquivalenceClassDescription.class,
			CMCellHeaderEquivalenceClassValue.class,
			CMCellHeaderEquivalenceClassEffects.class,
			CMCellHeaderEquivalenceClassName.class,
			CMCellHeaderEquivalenceClassState.class
			};
	public CMEquivalenceClassCreateAction() {
		super(CMMessages.getString("POPUPMENU_ITEM_CREATE_EQUIVALENCE_CLASS"));
	    // Set tool tip text
	    putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("POPUPMENU_ITEM_CREATE_EQUIVALENCE_CLASS"));
	    // Set an icon
	    putValue(Action.SMALL_ICON, CMAction.EQUIVALENCECLASS_CREATE.getIcon());
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("MENU_EQUIVALENCE_CLASS_MNEMONIC").charAt(0));
	    putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, Event.CTRL_MASK));
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void execute() {
		if (!calculateEnabled()) return;
			CMApplication.frame.setWaitCursor(true);
		   final Element element = ElementManager.getSelectedElement();
		   final EquivalenceClass equivalenceClass = EquivalenceClassManager.INSTANCE.createEquivalenceClass(element);
		   CMDefaultDialog dlg = new CMDefaultDialog();
		   dlg.setSize(CMEquivalenceClassEditController.DEFAULT_DIALOG_SIZE);
		   CMEquivalenceClassEditController editControl = new CMEquivalenceClassEditController(element, equivalenceClass);
		   dlg.setJPanelContained(editControl.getPanel());
		   dlg.setTitle(element.getName()+" - "+this.getValue(Action.NAME));
		   editControl.getPanel().getJTextFieldValue().requestFocus();
		   editControl.getPanel().getJTextFieldValue().setFocusable(true);
		    dlg.show();
		    CMApplication.frame.setWaitCursor(false);
		    if (dlg.getModalResult() == CMModalResult.OK){
		    	CMCompoundEdit ce = new CMCompoundEdit();
				//get the row and column selected
				final int row = CMApplication.frame.getElementsGrid().getSelectionModel().getFirstSelectedRow();
				final int col = CMApplication.frame.getElementsGrid().getSelectionModel().getFirstSelectedColumn();

		    	ce.addUndoDelegateEdit(new CMDelegate(){
					/* (non-Javadoc)
					 * @see model.util.CMDelegate#execute()
					 */
					public void execute() {
						// TODO Auto-generated method stub
//						update the grids
						CMApplication.frame.getElementsGrid().deleteAllElementViews();
						CMApplication.frame.getElementsGrid().addAllElementViews();
						CMApplication.frame.getJSPlitPaneTestCaseStructureView().getM_CMElementAndTestCaseViews().update();
						CMApplication.frame.getJSplitPaneStdCombinationStructureView().getM_CMElementAndStdCombinationViews().update();
						//select the inserted element
						CMApplication.frame.getElementsGrid().changeSelection(row,col,false,false);

					}
				});

		    	ce.addEdit(new CMComponentAwareEdit());
		    	ce.addEdit(CMModelEditFactory.INSTANCE.createAddEquivalenceClassModelEdit(element, equivalenceClass));
		    	element.addEquivalenceClass(equivalenceClass);
		    	ce.addEdit(editControl.applyChanges());
		    	getWarningMessages().addAll(editControl.getWarningMessages());
				ce.addRedoDelegateEdit(new CMDelegate(){
					/* (non-Javadoc)
					 * @see model.util.CMDelegate#execute()
					 */
					public void execute() {
						// TODO Auto-generated method stub
//						update the grids
						CMApplication.frame.getElementsGrid().deleteAllElementViews();
						CMApplication.frame.getElementsGrid().addAllElementViews();
						CMApplication.frame.getJSPlitPaneTestCaseStructureView().getM_CMElementAndTestCaseViews().update();
						CMApplication.frame.getJSplitPaneStdCombinationStructureView().getM_CMElementAndStdCombinationViews().update();
						//select the inserted element
						CMApplication.frame.getElementsGrid().selectCell(equivalenceClass,CMCellEquivalenceClassValue.class);

					}
				});
				//update the grids
				CMApplication.frame.getElementsGrid().deleteAllElementViews();
				CMApplication.frame.getElementsGrid().addAllElementViews();
				CMApplication.frame.getJSPlitPaneTestCaseStructureView().getM_CMElementAndTestCaseViews().update();
				CMApplication.frame.getJSplitPaneStdCombinationStructureView().getM_CMElementAndStdCombinationViews().update();
				//select the inserted element name
				CMApplication.frame.getElementsGrid().selectCell(equivalenceClass,CMCellEquivalenceClassValue.class);

		    	CMUndoMediator.getInstance().doEdit(ce);


		    }
	   dlg.dispose();
	   // CMApplication.frame.getM_CMGridElements().createEquivalenceClass_2();
	    CMApplication.frame.getElementsGrid().requestFocus();

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
		&&(Arrays.asList(allowedCells ).contains(CMApplication.frame.getElementsGrid().getValueAt(row,col).getClass()));
	}
}
