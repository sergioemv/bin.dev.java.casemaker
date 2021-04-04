package bi.view.utils;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.SystemColor;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import org.apache.log4j.Logger;

import com.jidesoft.plaf.UIDefaultsLookup;
import com.jidesoft.swing.JideButton;
import com.jidesoft.swing.PartialGradientLineBorder;
import com.jidesoft.swing.PartialLineBorder;
import com.jidesoft.swing.PartialSide;

import bi.view.icons.CMIcon;
import bi.view.lang.CMMessages;

public class CMTwinBox extends JPanel {

	private static final long serialVersionUID = 1L;
	private JPanel jPanelLeft = null;
	private JPanel jPanelRight = null;
	private JideButton jButtonLtoR = null;
	private JideButton jButtonRtoL = null;
	private JideButton jButtonAllLtoR = null;
	private JideButton jButtonAllRtoL = null;
	private JPanel jPanelButtons = null;
	private JPanel jPanelsep1 = null;
	private JPanel jPanelSep = null;
	private JPanel jPanelSep3 = null;
	private JScrollPane jScrollPaneLeft = null;
	private JScrollPane jScrollPaneRight = null;
	private CMDnDJList jListLeft = null;
	private CMDnDJList jListRight = null;
	private ListDataListener listChangeListener = new ListDataListener(){
	private boolean ordering;
		public void contentsChanged(ListDataEvent e) {
			//sortList((DefaultListModel)e.getSource());

		}

		public void intervalAdded(ListDataEvent e) {
			if (!ordering)
				sortList((DefaultListModel)e.getSource());

		}

		public void intervalRemoved(ListDataEvent e) {
			if (!ordering)
				sortList((DefaultListModel)e.getSource());

		}
		protected void sortList(DefaultListModel listModel) {
			Vector list = new Vector();
			ordering = true;
			for (int i = 0; i<listModel.getSize();i++)
				list.add(listModel.get(i));
			listModel.clear();
			 Collections.sort(list);
			for (Object obj : list)
				listModel.addElement(obj);
			ordering = false;
		}
	};
//	private CMArrayTransferHandler arrayListHandler = new CMArrayTransferHandler();

	/**
	 * This is the default constructor
	 */
	public CMTwinBox() {
		super();
		initialize();
	}


	/**
	 * This method initializes this
	 *
	 * @return void
	 */
	private void initialize() {
		this.setSize(416, 176);
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.setPreferredSize(new Dimension(250, 100));
		this.setMinimumSize(new Dimension(250, 100));
		this.add(getJPanelRight(), null);
		this.add(getJPanelButtons(), null);
		this.add(getJPanelLeft(), null);

	}

	/**
	 * This method initializes jPanelLeft
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelLeft() {
		if (jPanelLeft == null) {
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.fill = GridBagConstraints.BOTH;
			gridBagConstraints6.gridy = 0;
			gridBagConstraints6.weightx = 1.0;
			gridBagConstraints6.weighty = 1.0;
			gridBagConstraints6.gridx = 0;
			jPanelLeft = new JPanel();
			jPanelLeft.setLayout(new GridBagLayout());
			jPanelLeft.setPreferredSize(new Dimension(100, 10));
			//EFFECTS_PANEL_ASSIGNED_TITLE
			jPanelLeft.setBorder(BorderFactory.createTitledBorder(new PartialLineBorder(Color.gray, 1, true), CMMessages.getString("EFFECTS_PANEL_ASSIGNED_TITLE"), TitledBorder.LEADING, TitledBorder.ABOVE_TOP, new Font("Dialog", Font.PLAIN, 11), SystemColor.activeCaption));
			jPanelLeft.add(getJScrollPaneLeft(), gridBagConstraints6);
		}
		return jPanelLeft;
	}

	/**
	 * This method initializes jPanelRight
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelRight() {
		if (jPanelRight == null) {
			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			gridBagConstraints7.fill = GridBagConstraints.BOTH;
			gridBagConstraints7.gridy = 0;
			gridBagConstraints7.weightx = 1.0;
			gridBagConstraints7.weighty = 1.0;
			gridBagConstraints7.gridx = 0;
			jPanelRight = new JPanel();
			jPanelRight.setLayout(new GridBagLayout());
			jPanelRight.setPreferredSize(new Dimension(100, 0));
			//EFFECTS_PANEL_AVAILABLE_TITLE
			jPanelRight.setBorder(BorderFactory.createTitledBorder(new PartialLineBorder(Color.gray, 1, true), CMMessages.getString("EFFECTS_PANEL_AVAILABLE_TITLE"), TitledBorder.LEADING, TitledBorder.ABOVE_TOP, new Font("Dialog", Font.PLAIN, 11), SystemColor.activeCaption));
			jPanelRight.add(getJScrollPaneRight(), gridBagConstraints7);
		}
		return jPanelRight;
	}

	/**
	 * This method initializes jButtonLtoR
	 *
	 * @return javax.swing.JButton
	 */
	public JButton getJButtonLtoR() {
		if (jButtonLtoR == null) {
			jButtonLtoR = new JideButton();
			jButtonLtoR.setIcon(CMIcon.ARROW_RIGHT.getImageIcon());
			jButtonLtoR.setMaximumSize(new Dimension(25, 25));
			jButtonLtoR.setMinimumSize(new Dimension(25, 25));
			jButtonLtoR.setMnemonic(KeyEvent.VK_UNDEFINED);
			jButtonLtoR.setPreferredSize(new Dimension(25, 25));
			jButtonLtoR.setButtonStyle(JideButton.FLAT_STYLE);
			jButtonLtoR.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Object[] selected = getJListAvailable().getSelectedValues();
					if (selected.length == 0) return;
					for (Object sel : selected){
						((DefaultListModel)getJListAssigned().getModel()).addElement(sel);
						((DefaultListModel)getJListAvailable().getModel()).removeElement(sel);
					}
				}
			});
		}
		return jButtonLtoR;
	}




	/**
	 * This method initializes jButtonRtoL
	 *
	 * @return javax.swing.JButton
	 */
	public JButton getJButtonRtoL() {
		if (jButtonRtoL == null) {
			jButtonRtoL = new JideButton();
			jButtonRtoL.setIcon(CMIcon.ARROW_LEFT.getImageIcon());
			jButtonRtoL.setMaximumSize(new Dimension(25, 25));
			jButtonRtoL.setMinimumSize(new Dimension(25, 25));
			jButtonRtoL.setMnemonic(KeyEvent.VK_UNDEFINED);
			jButtonRtoL.setPreferredSize(new Dimension(25, 25));
			jButtonRtoL.setButtonStyle(JideButton.FLAT_STYLE);
			jButtonRtoL.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Object[] selected = getJListAssigned().getSelectedValues();
					if (selected.length == 0) return;
					for (Object sel : selected){
						((DefaultListModel)getJListAvailable().getModel()).addElement(sel);
						((DefaultListModel)getJListAssigned().getModel()).removeElement(sel);
					}
						}
			});
		}
		return jButtonRtoL;
	}

	/**
	 * This method initializes jButtonAllLtoR
	 *
	 * @return javax.swing.JButton
	 */
	public JButton getJButtonAllLtoR() {
		if (jButtonAllLtoR == null) {
			jButtonAllLtoR = new JideButton();
			jButtonAllLtoR.setIcon(CMIcon.ARROW_ALL_RIGHT.getImageIcon());
			jButtonAllLtoR.setMaximumSize(new Dimension(25, 25));
			jButtonAllLtoR.setMinimumSize(new Dimension(25, 25));
			jButtonAllLtoR.setMnemonic(KeyEvent.VK_UNDEFINED);
			jButtonAllLtoR.setPreferredSize(new Dimension(25, 25));
			jButtonAllLtoR.setButtonStyle(JideButton.FLAT_STYLE);
			jButtonAllLtoR.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					for (int i = 0; i<((DefaultListModel)getJListAvailable().getModel()).getSize();i++)
						((DefaultListModel)getJListAssigned().getModel()).addElement((((DefaultListModel)getJListAvailable().getModel()).get(i)));

					((DefaultListModel)getJListAvailable().getModel()).clear();
				}
			});
		}
		return jButtonAllLtoR;
	}

	/**
	 * This method initializes jButtonAllRtoL
	 *
	 * @return javax.swing.JButton
	 */
	public JButton getJButtonAllRtoL() {
		if (jButtonAllRtoL == null) {
			jButtonAllRtoL = new JideButton();
			jButtonAllRtoL.setIcon(CMIcon.ARROW_ALL_LEFT.getImageIcon());
			jButtonAllRtoL.setMaximumSize(new Dimension(25, 25));
			jButtonAllRtoL.setMinimumSize(new Dimension(25, 25));
			jButtonAllRtoL.setMnemonic(KeyEvent.VK_UNDEFINED);
			jButtonAllRtoL.setPreferredSize(new Dimension(25, 25));
			jButtonAllRtoL.setButtonStyle(JideButton.FLAT_STYLE);
			jButtonAllRtoL.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					for (int i = 0; i<((DefaultListModel)getJListAssigned().getModel()).getSize();i++)
						((DefaultListModel)getJListAvailable().getModel()).addElement((((DefaultListModel)getJListAssigned().getModel()).get(i)));

					((DefaultListModel)getJListAssigned().getModel()).clear();
				}
			});
		}
		return jButtonAllRtoL;
	}

	/**
	 * This method initializes jPanelButtons
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelButtons() {
		if (jPanelButtons == null) {
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.gridx = 0;
			gridBagConstraints5.gridy = 5;
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridx = 0;
			gridBagConstraints4.gridy = 3;
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.gridx = 0;
			gridBagConstraints3.gridy = 1;
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridx = 0;
			gridBagConstraints2.gridy = 6;
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 0;
			gridBagConstraints1.gridy = 4;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.gridy = 2;
			jPanelButtons = new JPanel();
			jPanelButtons.setLayout(new GridBagLayout());
			jPanelButtons.setPreferredSize(new Dimension(50, 100));
			jPanelButtons.setMaximumSize(new Dimension(60, 200));
			jPanelButtons.add(getJButtonLtoR(), new GridBagConstraints());
			jPanelButtons.add(getJPanelsep1(), gridBagConstraints3);
			jPanelButtons.add(getJButtonRtoL(), gridBagConstraints);
			jPanelButtons.add(getJButtonAllLtoR(), gridBagConstraints1);
			jPanelButtons.add(getJButtonAllRtoL(), gridBagConstraints2);
			jPanelButtons.add(getJPanelSep(), gridBagConstraints4);
			jPanelButtons.add(getJPanelSep3(), gridBagConstraints5);
		}
		return jPanelButtons;
	}

	/**
	 * This method initializes jPanelsep1
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelsep1() {
		if (jPanelsep1 == null) {
			jPanelsep1 = new JPanel();
			jPanelsep1.setLayout(new GridBagLayout());
			jPanelsep1.setPreferredSize(new Dimension(0, 5));
			jPanelsep1.setMinimumSize(new Dimension(0, 5));
		}
		return jPanelsep1;
	}

	/**
	 * This method initializes jPanelSep
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelSep() {
		if (jPanelSep == null) {
			jPanelSep = new JPanel();
			jPanelSep.setLayout(new GridBagLayout());
			jPanelSep.setPreferredSize(new Dimension(0, 5));
			jPanelSep.setMinimumSize(new Dimension(0, 5));
		}
		return jPanelSep;
	}

	/**
	 * This method initializes jPanelSep3
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelSep3() {
		if (jPanelSep3 == null) {
			jPanelSep3 = new JPanel();
			jPanelSep3.setLayout(new GridBagLayout());
			jPanelSep3.setPreferredSize(new Dimension(0, 5));
			jPanelSep3.setMinimumSize(new Dimension(0, 5));
		}
		return jPanelSep3;
	}

	/**
	 * This method initializes jScrollPaneLeft
	 *
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPaneLeft() {
		if (jScrollPaneLeft == null) {
			jScrollPaneLeft = new JScrollPane();
			jScrollPaneLeft.setViewportView(getJListAssigned());
		}
		return jScrollPaneLeft;
	}

	/**
	 * This method initializes jScrollPaneRight
	 *
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPaneRight() {
		if (jScrollPaneRight == null) {
			jScrollPaneRight = new JScrollPane();
			jScrollPaneRight.setViewportView(getJListAvailable());
		}
		return jScrollPaneRight;
	}

	/**
	 * This method initializes jListLeft
	 *
	 * @return javax.swing.JList
	 */
	public JList getJListAssigned() {
		if (jListLeft == null) {
			jListLeft = new CMDnDJList();
			jListLeft.setModel(new DefaultListModel());
			((DefaultListModel)jListLeft.getModel()).addListDataListener(listChangeListener );
		//	jListLeft.setTransferHandler(arrayListHandler );

		}
		return jListLeft;
	}

	/**
	 * This method initializes jListRight
	 *
	 * @return javax.swing.JList
	 */
	public JList getJListAvailable() {
		if (jListRight == null) {
			jListRight = new CMDnDJList();
			jListRight.setModel(new DefaultListModel());
			((DefaultListModel)jListRight.getModel()).addListDataListener(listChangeListener );
			//jListRight.setTransferHandler(arrayListHandler );
		}
		return jListRight;
	}

	public void setAssignedList(List p_v) {
		clearAssignedList();
		try{
			Collections.sort(p_v);
		} catch (Exception e) {
			Logger.getLogger(this.getClass()).warn("The Assigned list is unsortable");
		}
		for (Object obj : p_v)
			getAssignedModel().addElement(obj);

	}

	public void setAvailableList(List p_v) {
		clearAvailableList();
		try{
			Collections.sort(p_v);
		} catch (Exception e) {
			Logger.getLogger(this.getClass()).warn("The Available list is unsortable");
		}
		for (Object obj : p_v)
			getAvailableModel().addElement(obj);
	}

	private DefaultListModel getAvailableModel() {
		return ((DefaultListModel)getJListAvailable().getModel());
	}

	private DefaultListModel getAssignedModel() {
		return ((DefaultListModel)getJListAssigned().getModel());
	}

	public List<Component> getTabOrder() {
		ArrayList<Component> list = new ArrayList<Component>();
		if (((DefaultListModel)getJListAssigned().getModel()).size()>0)
			list.add(getJListAssigned());
		list.add(getJButtonLtoR());
		list.add(getJButtonRtoL());
		list.add(getJButtonAllLtoR());
		list.add(getJButtonAllRtoL());
		if (((DefaultListModel)getJListAvailable().getModel()).size()>0)
			list.add(getJListAvailable());
		return list;
	}

	public List getAssignedList(){
		return Arrays.asList(((DefaultListModel)getJListAssigned().getModel()).toArray());
	}
	public List getAvailableList(){
		return Arrays.asList((getAvailableModel()).toArray());
	}
	public void clearAvailableList() {
			getAvailableModel().clear();

	}
	public void clearAssignedList() {
			getAssignedModel().clear();

	}
}  //  @jve:decl-index=0:visual-constraint="10,10"
