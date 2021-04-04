/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved. 
 */
package bi.view.actions.effect;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JPanel;

import bi.view.lang.CMMessages;
import bi.view.utils.CMBaseJDialog;
import bi.view.utils.CMOkCancelPanel;

/**
 * @author smoreno
 *
 */
public class CMEffectDialog extends CMBaseJDialog {
private JPanel jPanelDialog = null;
private CMOkCancelPanel jPanelButtons = null;
private CMEffectDialogPanel effectDialogPanel = null;
private boolean initialized;

/**
 * 
 */
public CMEffectDialog() {
	super();
	//	initialize();
}
	/**
 * This method initializes this
 * 
 */
public void initialize() {
        this.setSize(new java.awt.Dimension(486,335));
        this.setContentPane(getJPanelDialog());
        Dimension dlgSize = getPreferredSize();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screenSize.width - dlgSize.width) / 2 /*+ loc.x*/,
            (screenSize.height - dlgSize.height) / 2 /*+ loc.y*/);
        setModal(true);
        this.addKeyListenertoAll(this,this.getDefaultKeyListener());
        initialized = true;
}

/* (non-Javadoc)
 * @see bi.view.utils.CMBaseJDialog#show()
 */
@Override
public void show() {
	// TODO Auto-generated method stub
	if (!initialized)
		initialize();
	super.show();

}
	/* (non-Javadoc)
	 * @see bi.view.utils.CMBaseJDialog#getDefaultButton()
	 */
	@Override
	public JButton getDefaultButton() {
		return getJPanelButtons().getJButtonOk();
	}

	/* (non-Javadoc)
	 * @see bi.view.utils.CMBaseJDialog#getOrder()
	 */
	@Override
	protected List getOrder() {
		if (this.getEffectDialogPanel().getJTabbedPaneEffect().getSelectedIndex()==0)
			return Arrays.asList(getJEditorPaneDescription(),getJPanelButtons().getJButtonOk(),getJPanelButtons().getJButtonCancel());
		if (this.getEffectDialogPanel().getJTabbedPaneEffect().getSelectedIndex()==1)
			return Arrays.asList(getRequirementPanel().getJComboSelectedRequirement(),getRequirementPanel().getCMJListReqs(),
					getRequirementPanel().getJButtonAdd(),getRequirementPanel().getJButtonRemove(),getJPanelButtons().getJButtonOk(),
					getJPanelButtons().getJButtonCancel());
		if (this.getEffectDialogPanel().getJTabbedPaneEffect().getSelectedIndex()==2)
			return Arrays.asList(getGridExpectedResults(),getJPanelButtons().getJButtonOk(),getJPanelButtons().getJButtonCancel());
		
		return null;
	}

	/* (non-Javadoc)
	 * @see bi.view.utils.CMBaseJDialog#fireButtonOk()
	 */
	@Override
	protected void fireButtonOk() {
		getJPanelButtons().getJButtonOk().doClick();

	}

	/* (non-Javadoc)
	 * @see bi.view.utils.CMBaseJDialog#fireButtonCancel()
	 */
	@Override
	protected void fireButtonCancel() {
		getJPanelButtons().getJButtonCancel().doClick();

	}
	/**
	 * This method initializes jPanelDialog	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelDialog() {
		if (jPanelDialog == null) {
			jPanelDialog = new JPanel();
			jPanelDialog.setLayout(new BorderLayout());
			jPanelDialog.add(getJPanelButtons(), java.awt.BorderLayout.SOUTH);
			jPanelDialog.add(getEffectDialogPanel(), java.awt.BorderLayout.CENTER);
		}
		return jPanelDialog;
	}
	/**
	 * This method initializes jPanelButtons	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private CMOkCancelPanel getJPanelButtons() {
		if (jPanelButtons == null) {
			jPanelButtons = new CMOkCancelPanel(this);
		}
		return jPanelButtons;
	}
	/**
	 * This method initializes effectDialogPanel	
	 * 	
	 * @return bi.view.actions.effect.CMEffectDialogPanel	
	 */
	private CMEffectDialogPanel getEffectDialogPanel() {
		if (effectDialogPanel == null) {
			effectDialogPanel = new CMEffectDialogPanel();
			effectDialogPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, CMMessages.getString("EFFECT_DIALOG_GROUP_TITLE"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null)); //$NON-NLS-1$
			effectDialogPanel.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12)); //$NON-NLS-1$
		}
		return effectDialogPanel;
	}
	public CMExpectedResultsDialogPanel getExpectedResultsDialogPanel() {
		return this.getEffectDialogPanel().getExpectedResultsDialogPanel();
	}
	public CMExpectedResultsGrid getGridExpectedResults() {
		return this.getEffectDialogPanel().getGridExpectedResults();
	}
	public JEditorPane getJEditorPaneDescription() {
		return this.getEffectDialogPanel().getJEditorPaneDescription();
	}
	public CMRequirementDialogPanel getRequirementPanel() {
		return this.getEffectDialogPanel().getRequirementPanel();
	}
	public void setEffectDialogPanel(CMEffectDialogPanel p_effectDialogPanel) {
		this.effectDialogPanel = p_effectDialogPanel;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
