/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.view.actions.edit;

import java.awt.Component;
import java.awt.Event;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;

import model.BusinessRules;
import bi.controller.EffectManager;
import bi.controller.ElementManager;
import bi.controller.EquivalenceClassManager;
import bi.controller.SessionManager;
import bi.view.actions.CMAction;
import bi.view.actions.CMEnabledAction;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;

/**
 * @author smoreno
 *
 */
public class CMCopyAction extends AbstractAction implements Action,CMEnabledAction {
	public CMCopyAction() {
		super(CMMessages.getString("MENU_ITEM_COPY"));
	    // Set tool tip text
	    putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("MENU_ITEM_COPY"));
	    putValue(Action.SMALL_ICON, CMAction.COPY.getIcon());
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("MENU_ITEM_COPY_MNEMONIC").charAt(0));
	    putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, Event.CTRL_MASK));

	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent p_e) {
		CMApplication.frame.eventMouseClicked(null);
		CMApplication.frame.setWaitCursor(true);
	    Component selectedComponent = CMApplication.frame.getSelectedComponent();
	    if( selectedComponent == CMApplication.frame.getPanelElementsView()) {
	      if( CMApplication.frame.getElementsGrid().getSelectedElement() != null) {
	    	  ElementManager.INSTANCE.copyElement();
	      }
	      else if(CMApplication.frame.getElementsGrid().getSelectedEquivalenceClass() != null){
	    	  EquivalenceClassManager.INSTANCE.copyEquivalenceClass();
	      }

	    }
	     else{
	    	if( selectedComponent == CMApplication.frame.getPanelTestDataView()) {
	      		if( CMApplication.frame.getPanelTestDataView().getM_CMTDStructure().getSelectedStructure() != null) {
	      			CMApplication.frame.getPanelTestDataView().getM_CMTDStructure().copyStructure();
	      		}
	    	}
			else{
	            if(selectedComponent == CMApplication.frame.getPanelTestObjectView())
	            {
	            	CMApplication.frame.getPanelTestObjectView().setCopyTestObject();
	            }
	            else{
	            	if(selectedComponent== CMApplication.frame.getPanelCauseEffectsView()){
	            		EffectManager.INSTANCE.copyCauseEffect();
	            	}
	            	else{
	            		if (selectedComponent==CMApplication.frame.getBusinessRulesPanelView()) {
							CMApplication.frame.getBusinessRulesPanelView().getCMBusinessRuleEditor().copy(p_e);
						}
	            	}

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
		    if( selectedComponent == CMApplication.frame.getPanelElementsView()) {
				if ((CMApplication.frame.getTreeWorkspaceView().getCurrentTestObject().getAccessState().equalsIgnoreCase(BusinessRules.ACCESS_STATE_CHECKED_IN))||
					(!CMApplication.frame.getTreeWorkspaceView().getCurrentTestObject().getUser().equalsIgnoreCase(SessionManager.getCurrentSession().getM_User())))
					return false;

		      }
		return this.enabled;
	}
}
