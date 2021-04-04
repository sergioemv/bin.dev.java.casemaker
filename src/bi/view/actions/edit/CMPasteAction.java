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
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.tree.DefaultMutableTreeNode;

import model.BusinessRules;
import bi.controller.EffectManager;
import bi.controller.ElementManager;
import bi.controller.EquivalenceClassManager;
import bi.controller.SessionManager;
import bi.controller.utils.CMClipBoard;
import bi.view.actions.CMAction;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.testdataviews.CMCutCopyPasteStructures;
import bi.view.treeviews.nodes.CMProjectNode;

/**
 * @author smoreno
 *
 */
public class CMPasteAction extends AbstractAction implements Action {
	public CMPasteAction() {
		super(CMMessages.getString("MENU_ITEM_PASTE"));     //$NON-NLS-1$
	    // Set tool tip text
	    putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("BUTTON_TOOLTIP_PASTE")); //$NON-NLS-1$
	    putValue(Action.SMALL_ICON, CMAction.PASTE.getIcon());
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("MENU_ITEM_PASTE_MNEMONIC").charAt(0)); //$NON-NLS-1$
	    putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, Event.CTRL_MASK));

	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent p_e) {
		   CMApplication.frame.setWaitCursor(true);
		CMApplication.frame.eventMouseClicked(null);

		  Component selectedComponent = CMApplication.frame.getSelectedComponent();
		  if( selectedComponent == CMApplication.frame.getPanelElementsView()) {

			  if(CMClipBoard.getInstance().getElement()!=null)
		  			ElementManager.INSTANCE.pasteElement();
		  	  if(CMClipBoard.getInstance().getEquivalenceClass()!=null)
		  		EquivalenceClassManager.INSTANCE.pasteEquivalenceClass();

		  }
		  else{
		  	if( selectedComponent == CMApplication.frame.getPanelTestDataView()) {
		      	if( CMCutCopyPasteStructures.getInstance().getStructureCutOrCopy() != null) {
		      		CMApplication.frame.getPanelTestDataView().getM_CMTDStructure().pasteStructure();
		      		CMApplication.frame.getPanelTestDataView().update();
		      	}
		  	}
				else{
		          if(selectedComponent == CMApplication.frame.getPanelProjectView())
		          {
		        	  DefaultMutableTreeNode selectedNode = CMApplication.frame.getTreeWorkspaceView().getSelectedNode();
		        	  if( CMApplication.frame.getTreeWorkspaceView().getSelectedProject2() != null)
		  			{
		  				if (new File(((CMProjectNode)selectedNode).getProjectReference().getFilePath()).canRead())
		  				{
		  					CMProjectNode projectNode = (CMProjectNode)selectedNode ;
		  					if (projectNode .getProject().getAccessState().equals(BusinessRules.ACCESS_STATE_CHECKED_OUT)&&
		  					projectNode.getProject().getUser().equalsIgnoreCase(SessionManager.getCurrentSession().getM_User()))
		  							CMApplication.frame.getPanelTestObjectView().pasteTestobject();
		  					else
		  						JOptionPane.showMessageDialog(CMApplication.frame,CMMessages.getString("CANNOT_MODIFY_PROJECT_CHECKIN"),CMMessages.getString("LABEL_CASEMAKER"),JOptionPane.ERROR_MESSAGE);
		  				}	else
		  					JOptionPane.showMessageDialog(CMApplication.frame,CMMessages.getString("INFO_NETWORK_DRIVE_NOT_AVAILABLE"),CMMessages.getString("LABEL_CASEMAKER"),JOptionPane.INFORMATION_MESSAGE);
		  			}

		          }
		          else{
		          	if(selectedComponent== CMApplication.frame.getPanelCauseEffectsView()){
		          		EffectManager.INSTANCE.pasteCauseEffect();
		          	}
		          	//HCanedo_27032006_begin
		          	else{
		          		if (selectedComponent==CMApplication.frame.getBusinessRulesPanelView()) {
							CMApplication.frame.getBusinessRulesPanelView().getCMBusinessRuleEditor().paste(p_e);
						}
		          	}
		          	//HCanedo_27032006_end
		          }
		      }
		  }
		  CMApplication.frame.setWaitCursor(false);

	}


}
