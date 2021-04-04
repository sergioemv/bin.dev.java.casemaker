/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.view.actions.effect;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;

import bi.view.utils.CMBaseJDialog;
import bi.view.utils.CMOkCancelPanel;

/**
 * @author smoreno
 *
 */
public class CMEffectsEditDialog extends CMBaseJDialog {

	private JPanel jPaneContent = null;
	private CMOkCancelPanel panelOkCancel = null;
	private CMEffectsEditPanel editEffectsPanel = null;

	/**
	 *
	 */
	public CMEffectsEditDialog() throws HeadlessException{
		super();
		initialize();
		// TODO Auto-generated constructor stub
	}

	public CMEffectsEditDialog(CMEffectsEditPanel panel) {
		super();
		this.editEffectsPanel = panel;
		initialize();
	}

	/**
	 * This method initializes this
	 *
	 */
	private void initialize() {
        this.setSize(new java.awt.Dimension(615,512));
        this.setComponentOrientation(java.awt.ComponentOrientation.LEFT_TO_RIGHT);
        this.setTitle("test");
        this.setResizable(true);
        this.setMinimumSize(new java.awt.Dimension(603,387));
        this.setContentPane(getJPaneContent());
        this.setModal(true);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension dlgSize = this.getPreferredSize();
	    this.setLocation((screenSize.width - dlgSize.width) / 2, (screenSize.height - dlgSize.height) / 2);
        this.addKeyListenertoAll(this.getContentPane(),this.getDefaultKeyListener());

	}

	/* (non-Javadoc)
	 * @see bi.view.utils.CMBaseJDialog#getDefaultButton()
	 */
	@Override
	public JButton getDefaultButton() {
		return this.getPanelOkCancel().getJButtonOk();
	}

	/* (non-Javadoc)
	 * @see bi.view.utils.CMBaseJDialog#getOrder()
	 */
	@Override
	protected List getOrder() {
		List order = new ArrayList();
	//	order.addAll(getEditEffectsPanel().getShuttleEffects().getTabOrder());
		order.addAll(getEditEffectsPanel().getTabOrder());
		order.addAll(getPanelOkCancel().getTabOrder());
		return order;
	}

	/* (non-Javadoc)
	 * @see bi.view.utils.CMBaseJDialog#fireButtonOk()
	 */
	@Override
	protected void fireButtonOk() {
		this.getPanelOkCancel().getJButtonOk().doClick();

	}

	/* (non-Javadoc)
	 * @see bi.view.utils.CMBaseJDialog#fireButtonCancel()
	 */
	@Override
	protected void fireButtonCancel() {
		this.getPanelOkCancel().getJButtonCancel().doClick();

	}

	/**
	 * This method initializes jPaneContent
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPaneContent() {
		if (jPaneContent == null) {
			jPaneContent = new JPanel();
			jPaneContent.setLayout(new BorderLayout());
			jPaneContent.add(getPanelOkCancel(), java.awt.BorderLayout.SOUTH);
			//jPaneContent.add(getEditEffectsPanel(), java.awt.BorderLayout.CENTER);
			jPaneContent.add(getEditEffectsPanel(), BorderLayout.CENTER);
		}
		return jPaneContent;
	}

	/**
	 * This method initializes panelOkCancel
	 *
	 * @return bi.view.utils.CMOkCancelPanel
	 */
	private CMOkCancelPanel getPanelOkCancel() {
		if (panelOkCancel == null) {
			panelOkCancel = new CMOkCancelPanel(this);
		}
		return panelOkCancel;
	}

	/**
	 * This method initializes editEffectsPanel
	 *
	 * @return bi.view.actions.effect.CMEditEffectsPanel
	 */
	public CMEffectsEditPanel getEditEffectsPanel() {
		if (editEffectsPanel == null) {
			editEffectsPanel = new CMEffectsEditPanel();
		}
		return editEffectsPanel;
	}

	public JList getJListLeft() {
		return this.getEditEffectsPanel().getJListAssigned();
	}

	public JList getJListRight() {
		return this.getEditEffectsPanel().getJListAvailable();
	}

	public void setLeftList(Vector p_v, Vector p_views) {
		this.getEditEffectsPanel().setAssignedList(p_v);
	}

	public void setRightList(Vector p_v, Vector p_views) {
		this.getEditEffectsPanel().setAvailableList(p_v);
	}

	public List getAssignedList() {
		return this.editEffectsPanel.getAssignedList();
	}

	public List getAvailableList() {
		return this.editEffectsPanel.getAvailableList();
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
