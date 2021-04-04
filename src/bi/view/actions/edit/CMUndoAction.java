/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 D�az und Hilterscheid Unternehmensberatung. All rights reserved.
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
public class CMUndoAction extends AbstractAction implements CMEnabledAction
{
	public CMUndoAction() {
		super(CMMessages.getString("MENU_ITEM_UNDO"));
	    // Set tool tip text
	    putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("BUTTON_TOOLTIP_UNDO"));
	    putValue(Action.SMALL_ICON, CMAction.UNDO.getIcon());
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("MENU_ITEM_UNDO_MNEMONIC").charAt(0));
	    putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, Event.CTRL_MASK));

	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent p_e) {
				            try {
					            CMApplication.frame.setEnabled(false);
					            CMApplication.frame.setWaitCursor(true);
					            CMApplication.frame.getStatusLabel().setText("Undoing changes...");
					            CMApplication.frame.eventMouseClicked(null);
					            if(calculateEnabled())
					                CMApplication.frame.getM_CMUndoMediator().getUndoManager().undo();
						        } catch(CannotUndoException cue) {
						            Logger.getLogger(this.getClass()).error("Error on Undo"+ cue.getMessage());
						            cue.printStackTrace();
						        } finally{
								 CMApplication.frame.setEnabled(true);
							 CMApplication.frame.getStatusLabel().setText("Ready");
					        CMApplication.frame.repaint();
					        CMApplication.frame.setWaitCursor(false);


						}
}
	public boolean calculateEnabled() {
		return CMApplication.frame.getM_CMUndoMediator().getUndoManager().canUndo();
	}

}
