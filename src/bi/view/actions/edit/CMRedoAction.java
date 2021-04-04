/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.view.actions.edit;

import java.awt.Event;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;
import javax.swing.undo.CannotUndoException;

import org.apache.log4j.Logger;

import bi.view.actions.CMAction;
import bi.view.actions.CMEnabledAction;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;

/**
 * @author smoreno
 *
 */
public class CMRedoAction extends AbstractAction implements CMEnabledAction {
	public CMRedoAction() {
		super(CMMessages.getString("MENU_ITEM_REDO"));
	    // Set tool tip text
	    putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("BUTTON_TOOLTIP_REDO"));
	    putValue(Action.SMALL_ICON, CMAction.REDO.getIcon());
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("MENU_ITEM_REDO_MNEMONIC").charAt(0));
	    putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Y, Event.CTRL_MASK));

	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent p_e) {

//
		         try {
		        	 CMApplication.frame.setWaitCursor(true);
			         CMApplication.frame.eventMouseClicked(null);
			         CMApplication.frame.getStatusLabel().setText("Redoing changes...");
			         CMApplication.frame.setEnabled(false);
		        	 if(calculateEnabled())
			                CMApplication.frame.getM_CMUndoMediator().getUndoManager().redo();
			        } catch(CannotUndoException cue) {
			            Logger.getLogger(this.getClass()).error("Cannot redo "+cue.getMessage());
			            cue.printStackTrace();
			        } finally{

				CMApplication.frame.getStatusLabel().setText("Ready");
				CMApplication.frame.setEnabled(true);
		        CMApplication.frame.setWaitCursor(false);

			}


	}
	public boolean calculateEnabled() {
		return CMApplication.frame.getM_CMUndoMediator().getUndoManager().canRedo();
	}

}
