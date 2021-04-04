/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.view.actions.edit;

import java.awt.Component;
import java.awt.Event;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;

import model.BusinessRules;
import model.StructureTestData;
import bi.controller.EffectManager;
import bi.controller.ElementManager;
import bi.controller.EquivalenceClassManager;
import bi.controller.SessionManager;
import bi.view.actions.CMAction;
import bi.view.actions.CMEnabledAction;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.testdataviews.CMIndexTDStructureUpdate;
import bi.view.treeviews.nodes.CMProjectNode;

/**
 * @author smoreno
 *
 */
public class CMCutAction extends AbstractAction implements CMEnabledAction  {
	public CMCutAction() {
		super(CMMessages.getString("MENU_ITEM_CUT"));
	    // Set tool tip text
	    putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("BUTTON_TOOLTIP_CUT"));
	    putValue(Action.SMALL_ICON, CMAction.CUT.getIcon());
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("MENU_ITEM_CUT_MNEMONIC").charAt(0));
	    putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, Event.CTRL_MASK));

	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent p_e) {
		CMApplication.frame.setWaitCursor(true);
		CMApplication.frame.eventMouseClicked(null);//integration_fcastro_17082004
	    Component selectedComponent = CMApplication.frame.getSelectedComponent();
	    if( selectedComponent == CMApplication.frame.getPanelElementsView()) {
	      if( CMApplication.frame.getElementsGrid().getSelectedElement() != null) {
	    	  ElementManager.INSTANCE.cutElement();
	      }
	      else if(CMApplication.frame.getElementsGrid().getSelectedEquivalenceClass() != null){
	    	 EquivalenceClassManager.INSTANCE.cutEquivalenceClass();
	      }
	    }
		//HCanedo 25.06.04
	    else{
	    	if( selectedComponent == CMApplication.frame.getPanelTestDataView()) {
	      		if( CMApplication.frame.getPanelTestDataView().getM_CMTDStructure().getSelectedStructure() != null) {
//	hcanedo_21102004_begin
					StructureTestData cutStructure=CMApplication.frame.getPanelTestDataView().getM_CMTDStructure().getSelectedStructure();
	                int indexCut=CMIndexTDStructureUpdate.getInstance().getIndex();
	                CMApplication.frame.getPanelTestDataView().getM_CMTDStructure().cutStructure();
	                CMApplication.frame.getGridTDStructure().deleteTDStructurefromTestData(cutStructure,indexCut);
//	hcanedo_21102004_end
					CMApplication.frame.getPanelTestDataView().update();
					CMApplication.frame.getPanelTestDataSetView().update();
	     		 }
	    	 }
//	HCanedo_22112004_Begin
			else{
	            if(selectedComponent == CMApplication.frame.getPanelTestObjectView())
	            {
	            	CMApplication.frame.getPanelTestObjectView().setCutTestObject();
	            }
	            else{
	            	if(selectedComponent== CMApplication.frame.getPanelCauseEffectsView()){
	            		EffectManager.INSTANCE.cutCauseEffect();
	            	}
	            	//HCanedo_27032006_begin
	            	else{
	            		if (selectedComponent==CMApplication.frame.getBusinessRulesPanelView()) {
	            			CMApplication.frame.getBusinessRulesPanelView().getCMBusinessRuleEditor().cut(p_e);
						}
	            	}
	            	//HCanedo_27032006_end
	            }
	        }

	    }

				CMApplication.frame.setWaitCursor(false);


	  }
	/* (non-Javadoc)
	 * @see bi.view.actions.CMEnabledAction#calculateEnabled()
	 */
	public boolean calculateEnabled() {
		  Component selectedComponent = CMApplication.frame.getSelectedComponent();
		  if (selectedComponent != null)
		  {
			  if(selectedComponent == CMApplication.frame.getPanelTestObjectView())
	            {
				  	CMProjectNode node = CMApplication.frame.getTreeWorkspaceView().getCurrentProjectNode();
				  	return (new File(node.getProjectReference().getFilePath()).canRead()) &&
				  	node.getProject().getAccessState().equals(BusinessRules.ACCESS_STATE_CHECKED_OUT)&&
					node.getProject().getUser().equalsIgnoreCase(SessionManager.getCurrentSession().getM_User());

	            }
			    if( selectedComponent == CMApplication.frame.getPanelElementsView()) {
					if ((CMApplication.frame.getTreeWorkspaceView().getCurrentTestObject().getAccessState().equalsIgnoreCase(BusinessRules.ACCESS_STATE_CHECKED_IN))||
						(!CMApplication.frame.getTreeWorkspaceView().getCurrentTestObject().getUser().equalsIgnoreCase(SessionManager.getCurrentSession().getM_User())))
						return false;

			      }
		  }
		  return this.enabled;

	}
}
