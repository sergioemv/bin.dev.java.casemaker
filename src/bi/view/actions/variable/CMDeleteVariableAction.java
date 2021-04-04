/**
 * Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.view.actions.variable;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;
import bi.view.actions.CMAction;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.mainframeviews.CMFrameView;
import bi.view.testdataviews.CMVariables;

/**
 * @author svonborries
 *
 */
@SuppressWarnings("serial")
public class CMDeleteVariableAction extends AbstractAction implements Action {

	private CMFrameView m_frame;
    private CMVariables m_panelVariablesView;
	
	public CMDeleteVariableAction(){
		super(CMMessages.getString("TESTDATA_DELETE_VARIABLE"));
		putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("TESTDATA_DELETE_VARIABLE"));
	    putValue(Action.SMALL_ICON, CMAction.TESTDATA_VARIABLE_DELETE.getIcon());
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("DELETE_VARIABLE_MNEMONIC_2").charAt(0));
	    putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_DELETE,0));
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		
		this.m_frame = CMApplication.frame;
		this.m_panelVariablesView = m_frame.getPanelVariablesView();
		
        m_panelVariablesView.deleteActionPerformed();

	}

}
