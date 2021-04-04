/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.view.actions.file.reorder;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

import bi.view.icons.CMIcon;
import bi.view.lang.CMMessages;
import bi.view.utils.CMBaseJDialog;
import bi.view.utils.CMDnDJList;
import bi.view.utils.CMOkCancelPanel;
import bi.view.utils.CMToolBarButton;

/**
 * @author smoreno
 *
 */
public class CMReorderDialog extends CMBaseJDialog {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private CMOkCancelPanel buttonjPanel = null;
	private JScrollPane orderjScrollPane = null;
	private JPanel orderButtonjPanel = null;
	private CMDnDJList orderjList = null;

	private JToolBar orderjToolBar = null;
	private CMToolBarButton fistPositionjButton = null;
	private CMToolBarButton prevPositionjButton = null;
	private CMToolBarButton nextPositionjButton = null;
	private CMToolBarButton lastPositionjButton = null;
	private DefaultListModel orderListModel = null;  //  @jve:decl-index=0:visual-constraint=""
	/**
	 * @throws HeadlessException
	 */
	public CMReorderDialog() throws HeadlessException {
		super();
		initialize();
	}


	/**
	 * This method initializes this
	 *
	 * @return void
	 */
	private void initialize() {
		this.setSize(316, 200);
		this.setMinimumSize(new java.awt.Dimension(220,200));
		this.setTitle(CMMessages.getString("REORDER_DIALOGTITLE")); //$NON-NLS-1$
		this.setContentPane(getJContentPane());
		this.setModal(true);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension dlgSize = this.getPreferredSize();
	    this.setLocation((screenSize.width - dlgSize.width) / 2, (screenSize.height - dlgSize.height) / 2);
	    this.addKeyListenertoAll(this,this.getDefaultKeyListener());
	}

	/**
	 * This method initializes jContentPane
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getButtonjPanel(), java.awt.BorderLayout.SOUTH);
			jContentPane.add(getOrderjScrollPane(), java.awt.BorderLayout.CENTER);
			jContentPane.add(getOrderButtonjPanel(), java.awt.BorderLayout.EAST);
		}
		return jContentPane;
	}

	/**
	 * This method initializes buttonjPanel
	 *
	 * @return javax.swing.JPanel
	 */
	private CMOkCancelPanel getButtonjPanel() {
		if (buttonjPanel == null) {
			buttonjPanel = new CMOkCancelPanel(this);
		}
		return buttonjPanel;
	}

	/**
	 * This method initializes orderjScrollPane
	 *
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getOrderjScrollPane() {
		if (orderjScrollPane == null) {
			orderjScrollPane = new JScrollPane();
			orderjScrollPane.setPreferredSize(new java.awt.Dimension(250,10));
			orderjScrollPane.setViewportView(getOrderjList());
		}
		return orderjScrollPane;
	}

	/**
	 * This method initializes orderButtonjPanel
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getOrderButtonjPanel() {
		if (orderButtonjPanel == null) {
			BorderLayout borderLayout = new BorderLayout();
			borderLayout.setHgap(0);
			borderLayout.setVgap(0);
			orderButtonjPanel = new JPanel();
			orderButtonjPanel.setLayout(borderLayout);
			orderButtonjPanel.setPreferredSize(new java.awt.Dimension(28,10));
			orderButtonjPanel.add(getOrderjToolBar(), java.awt.BorderLayout.CENTER);
		}
		return orderButtonjPanel;
	}

	/**
	 * This method initializes orderjList
	 *
	 * @return javax.swing.JList
	 */
	public JList getOrderjList() {
		if (orderjList == null) {
			orderjList = new CMDnDJList();
			orderjList.setModel(getOrderListModel());
		}
		return orderjList;
	}




	/**
	 * This method initializes orderjToolBar
	 *
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getOrderjToolBar() {
		if (orderjToolBar == null) {
			orderjToolBar = new JToolBar();
			orderjToolBar.setOrientation(javax.swing.JToolBar.VERTICAL);
			orderjToolBar.setPreferredSize(new java.awt.Dimension(0,0));
			orderjToolBar.setFloatable(false);
			orderjToolBar.setComponentOrientation(java.awt.ComponentOrientation.RIGHT_TO_LEFT);
			orderjToolBar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
			orderjToolBar.setRequestFocusEnabled(false);
			orderjToolBar.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
			orderjToolBar.add(getFistPositionjButton());
			orderjToolBar.addSeparator(new Dimension(0,10));
			orderjToolBar.add(getPrevPositionjButton());
			orderjToolBar.addSeparator(new Dimension(0,10));
			orderjToolBar.add(getNextPositionjButton());
			orderjToolBar.addSeparator(new Dimension(0,10));
			orderjToolBar.add(getLastPositionjButton());
		}
		return orderjToolBar;
	}

	/**
	 * This method initializes fistPositionjButton
	 *
	 * @return javax.swing.JButton
	 */
	private JButton getFistPositionjButton() {
		if (fistPositionjButton == null) {
			fistPositionjButton = new CMToolBarButton();
			fistPositionjButton.setIcon(CMIcon.ORDER_FIRST.getImageIcon());//$NON-NLS-1$
			fistPositionjButton.setFocusPainted(false);
			fistPositionjButton.setToolTipText(CMMessages.getString("REORDER_DIALOG_MOVE_FIRST_BUTTON")); //$NON-NLS-1$
			fistPositionjButton.setBorderPainted(false);
			fistPositionjButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					int index = orderjList.getSelectedIndex();
					if( index == -1 )
					JOptionPane.showMessageDialog( null, CMMessages.getString("REORDER_DIALOG_ERROR_SELECT_OBJECT") ); //$NON-NLS-1$
					else if( index > 0 )
					{
					Object temp = orderListModel.remove( index );
					orderListModel.add( 0, temp );
					orderjList.setSelectedIndex(0 );
					}


				}
			});
		}
		return fistPositionjButton;
	}

	/**
	 * This method initializes prevPositionjButton
	 *
	 * @return javax.swing.JButton
	 */
	private JButton getPrevPositionjButton() {
		if (prevPositionjButton == null) {
			prevPositionjButton = new CMToolBarButton();
			prevPositionjButton.setIcon(CMIcon.ORDER_PREV.getImageIcon()); //$NON-NLS-1$
			prevPositionjButton.setMnemonic(java.awt.event.KeyEvent.VK_UP);
			prevPositionjButton.setToolTipText(CMMessages.getString("REORDER_DIALOG_PREVIOUS_BUTTON")); //$NON-NLS-1$
			prevPositionjButton.setBorderPainted(false);
			prevPositionjButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					int index = orderjList.getSelectedIndex();
					if( index == -1 )
					JOptionPane.showMessageDialog( null, CMMessages.getString("REORDER_DIALOG_ERROR_SELECT_OBJECT") ); //$NON-NLS-1$
					else if( index > 0 )
					{
					Object temp = orderListModel.remove( index );
					orderListModel.add( index - 1, temp );
					orderjList.setSelectedIndex( index - 1 );
					}
				}
			});
		}
		return prevPositionjButton;
	}

	/**
	 * This method initializes nextPositionjButton
	 *
	 * @return javax.swing.JButton
	 */
	private JButton getNextPositionjButton() {
		if (nextPositionjButton == null) {
			nextPositionjButton = new CMToolBarButton();
			nextPositionjButton.setIcon(CMIcon.ORDER_NEXT.getImageIcon()); //$NON-NLS-1$
			nextPositionjButton.setMnemonic(java.awt.event.KeyEvent.VK_DOWN);
			nextPositionjButton.setToolTipText(CMMessages.getString("REORDER_DIALOG_NEXT_BUTTON")); //$NON-NLS-1$
			nextPositionjButton.setBorderPainted(false);
			nextPositionjButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					int index = orderjList.getSelectedIndex();
					if( index == -1 )
					JOptionPane.showMessageDialog( null, CMMessages.getString("REORDER_DIALOG_ERROR_SELECT_OBJECT") ); //$NON-NLS-1$
					else if(index < orderListModel.size() - 1  )
					{
					Object temp = orderListModel.remove( index );
					orderListModel.add( index + 1, temp );
					orderjList.setSelectedIndex( index + 1 );
					}

				}
			});
		}
		return nextPositionjButton;
	}

	/**
	 * This method initializes lastPositionjButton
	 *
	 * @return javax.swing.JButton
	 */
	private JButton getLastPositionjButton() {
		if (lastPositionjButton == null) {
			lastPositionjButton = new CMToolBarButton();
			lastPositionjButton.setIcon(CMIcon.ORDER_LAST.getImageIcon());
			lastPositionjButton.setBounds(new java.awt.Rectangle(16,1,16,16));
			lastPositionjButton.setToolTipText(CMMessages.getString("REORDER_DIALOG_LAST_BUTTON")); //$NON-NLS-1$
			lastPositionjButton.setBorderPainted(false);
			lastPositionjButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					int index = orderjList.getSelectedIndex();
					if( index == -1 )
					JOptionPane.showMessageDialog( null, CMMessages.getString("REORDER_DIALOG_ERROR_SELECT_OBJECT") ); //$NON-NLS-1$
					else if(index < orderListModel.size() - 1  )
					{
					Object temp = orderListModel.remove( index );
					orderListModel.add( orderListModel.size() , temp );
					orderjList.setSelectedIndex( orderListModel.size()  );
					}
				}
			});
		}
		return lastPositionjButton;
	}

	/* (non-Javadoc)
	 * @see bi.view.utils.CMBaseJDialog#getDefaultButton()
	 */
	@Override
	public JButton getDefaultButton() {

		return getButtonjPanel().getJButtonOk();
	}

	/* (non-Javadoc)
	 * @see bi.view.utils.CMBaseJDialog#getOrder()
	 */
	@Override
	protected List getOrder() {

		return new ArrayList<Component>(Arrays.asList(getOrderjList(),
				getFistPositionjButton(),
				getPrevPositionjButton(),
				getNextPositionjButton(),
				getLastPositionjButton(),
				getButtonjPanel().getJButtonOk(),
				getButtonjPanel().getJButtonCancel()));
	}

	/* (non-Javadoc)
	 * @see bi.view.utils.CMBaseJDialog#fireButtonOk()
	 */
	@Override
	protected void fireButtonOk() {
		getButtonjPanel().getJButtonOk().doClick();

	}

	/* (non-Javadoc)
	 * @see bi.view.utils.CMBaseJDialog#fireButtonCancel()
	 */
	@Override
	protected void fireButtonCancel() {
		getButtonjPanel().getJButtonCancel().doClick();

	}


	/**
	 * This method initializes orderListModel
	 *
	 * @return javax.swing.DefaultListModel
	 */
	private DefaultListModel getOrderListModel() {
		if (orderListModel == null) {
			orderListModel = new DefaultListModel();
		}
		return orderListModel;
	}

    public void AddObjectsToOrder(List p_Objects)
    {
    	getOrderListModel().clear();
    	for (Object ob : p_Objects)
    		getOrderListModel().addElement(ob);
    }
    public List getOrderedObjects()
    {
    	return Arrays.asList(getOrderListModel().toArray());
    }

}  //  @jve:decl-index=0:visual-constraint="10,10"
