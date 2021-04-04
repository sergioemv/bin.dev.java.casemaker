/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.view.actions.effect;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.SystemColor;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.jidesoft.swing.JideButton;

import bi.controller.editcontrol.CMEffectEditController;
import bi.view.actions.CMAction;
import bi.view.icons.CMIcon;
import bi.view.lang.CMMessages;
import bi.view.utils.CMTwinBox;

/**
 * @author smoreno
 *
 */
public class CMEffectsEditPanel extends JPanel {
	private static Icon effectIcon = CMIcon.EFFECT.getImageIcon();  //  @jve:decl-index=0:
	private JSplitPane jSplitContent = null;
	private JPanel panelShuttle = null;
	private JPanel panelEffect = null;
	private CMEffectDialogPanel effectDialogPanel = null;
	private CMTwinBox shuttleEffects = null;
	private JToolBar jToolBarEffects = null;
	private JideButton jButtonCreateEffect = null;
	/**
	 *  This is the active effect being edited
	 */
	private CMEffectEditController effectEditController;
	/**
	 * @param p_layout
	 * @param p_isDoubleBuffered
	 */
	public CMEffectsEditPanel(LayoutManager p_layout, boolean p_isDoubleBuffered) {
		super(p_layout, p_isDoubleBuffered);
		// TODO Auto-generated constructor stub
		initialize();
	}

	/**
	 * @param p_layout
	 */
	public CMEffectsEditPanel(LayoutManager p_layout) {
		super(p_layout);
		// TODO Auto-generated constructor stub
		initialize();
	}

	/**
	 * @param p_isDoubleBuffered
	 */
	public CMEffectsEditPanel(boolean p_isDoubleBuffered) {
		super(p_isDoubleBuffered);
		// TODO Auto-generated constructor stub
		initialize();
	}

	/**
	 *
	 */
	public CMEffectsEditPanel() {
		super();
		// TODO Auto-generated constructor stub
		initialize();
	}
	private DefaultListCellRenderer listCellRenderer = new DefaultListCellRenderer()

	{


		/* (non-Javadoc)
		 * @see javax.swing.DefaultListCellRenderer#getListCellRendererComponent(javax.swing.JList, java.lang.Object, int, boolean, boolean)
		 */
		@Override
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		       setComponentOrientation(list.getComponentOrientation());

		   	if (isSelected )
		   	{

		   	    setBackground(list.getSelectionBackground());
		   	    setForeground(list.getSelectionForeground());
		   	}
		   	else {
		   	    setBackground(list.getBackground());
		   	    setForeground(list.getForeground());
		   	}

		   	if (value instanceof Icon) {
		   	    setIcon((Icon)value);
		   	    setText("");
		   	}
		   	else {
		   	    setIcon(null);
		   	    setText((value == null) ? "" : value.toString());
		   	}

		   	setEnabled(list.isEnabled());
		   	setFont(list.getFont());

		           Border border = null;
		           if (cellHasFocus) {
		               if (isSelected ) {
		                   border = UIManager.getBorder("List.focusSelectedCellHighlightBorder");

		               }
		               if (border == null) {
		                   border = UIManager.getBorder("List.focusCellHighlightBorder");
		               }
		           }
		           else {
		               border = noFocusBorder;
		           }
			           if (value == effectEditController )

					          border = BorderFactory.createLineBorder(Color.ORANGE);


			setIcon(effectIcon );
		   	setBorder(border);

		   	return this;
		       }


	};
public boolean childHasfocus(Component comp)
{
	  boolean childFocus = comp.hasFocus();
	  if (!childFocus)
	 if (((comp instanceof Container) )
 			  &&(((Container)comp).getComponentCount()>0))
 			  {
			for (int i = 0;i<((Container)comp).getComponentCount();i++)
 					if (childHasfocus(((Container)comp).getComponent(i)))
 					childFocus = true;
 			  }
	 return childFocus;
}

	/**
	 * This method initializes this
	 *
	 * @return void
	 */
	private void initialize() {
		this.setLayout(new BorderLayout());
		this.setSize(603, 444);
		this.setMinimumSize(new Dimension(312, 387));
		this.add(getJSplitContent(), java.awt.BorderLayout.CENTER);
		this.add(getJToolBarEffects(), BorderLayout.NORTH);
		//this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Selected Effect Properties", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.PLAIN, 11), SystemColor.activeCaption));
	}

	/**
	 * This method initializes jSplitContent
	 *
	 * @return javax.swing.JSplitPane
	 */
	public JSplitPane getJSplitContent() {
		if (jSplitContent == null) {
			jSplitContent = new JSplitPane();
			jSplitContent.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
			jSplitContent.setDividerSize(7);
			jSplitContent.setDividerLocation(150);
			jSplitContent.setOneTouchExpandable(true);
			jSplitContent.setContinuousLayout(true);
			jSplitContent.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			jSplitContent.setAutoscrolls(true);
			jSplitContent.setBottomComponent(getPanelEffect());
			jSplitContent.setTopComponent(getPanelShuttle());

		}
		return jSplitContent;
	}

	/**
	 * This method initializes panelShuttle
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getPanelShuttle() {
		if (panelShuttle == null) {
			panelShuttle = new JPanel();
			panelShuttle.setLayout(new BorderLayout());

			panelShuttle.add(getShuttleEffects(), java.awt.BorderLayout.CENTER);
		}
		return panelShuttle;
	}

	/**
	 * This method initializes panelEffect
	 *
	 * @return javax.swing.JPanel
	 */
	public JPanel getPanelEffect() {
		if (panelEffect == null) {
			panelEffect = new JPanel();
			panelEffect.setLayout(new BorderLayout());
			panelEffect.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), CMMessages.getString("EFFECTS_PANEL_PROPERTIES_TITLE"), TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.PLAIN, 11), SystemColor.activeCaption)); //$NON-NLS-1$ //$NON-NLS-2$
			//panelEffect.add(getEffectDialogPanel(), BorderLayout.NORTH);
			//panelEffect.add(getEffectDialogPanel(),BorderLayout.CENTER);
			//panelEffect.setTabPlacement(JTabbedPane.LEFT);
		}
		return panelEffect;
	}

	/**
	 * This method initializes effectDialogPanel
	 *
	 * @return bi.view.actions.effect.CMEffectDialogPanel
	 */
	public CMEffectDialogPanel getEffectDialogPanel() {
		if (effectDialogPanel == null) {
			effectDialogPanel = new CMEffectDialogPanel();

		}
		return effectDialogPanel;
	}

	/**
	 * This method initializes shuttleEffect
	 *
	 * @return bi.view.utils.CMShuttle
	 */
	public CMTwinBox getShuttleEffects() {
		if (shuttleEffects == null)
			shuttleEffects = new CMTwinBox();
			return shuttleEffects;
	}

	public void setAssignedList(List p_v) {
	this.getShuttleEffects().setAssignedList(p_v);
	}

	public void setAvailableList(Vector p_v) {
		this.getShuttleEffects().setAvailableList(p_v);
	}

	public List getAssignedList() {
		return  this.getShuttleEffects().getAssignedList();
	}

	public List getAvailableList() {
		return this.getShuttleEffects().getAvailableList();
	}

	public JList getJListAssigned() {
		return this.getShuttleEffects().getJListAssigned();
	}

	public JList getJListAvailable() {
		return this.getShuttleEffects().getJListAvailable();
	}
/**
 * @param p_effectDialogPanel The effectDialogPanel to set.
 */
public void setEffectDialogPanel(CMEffectDialogPanel p_effectDialogPanel) {
	this.effectDialogPanel = p_effectDialogPanel;
}

public void setPanelEffect(JPanel p_panelEffect) {
	this.panelEffect = p_panelEffect;
}

/**
 * @return
 */
public boolean childHasfocus() {
	// TODO Auto-generated method stub
	return childHasfocus(this);
}

/**
 * This method initializes jToolBarEffects
 *
 * @return javax.swing.JToolBar
 */
private JToolBar getJToolBarEffects() {
	if (jToolBarEffects == null) {
		jToolBarEffects = new JToolBar();
		jToolBarEffects.setPreferredSize(new Dimension(18, 30));
		jToolBarEffects.setFloatable(false);
		jToolBarEffects.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		jToolBarEffects.add(getJButtonCreateEffect());
	}
	return jToolBarEffects;
}

/**
 * This method initializes jButtonCreateEffect
 *
 * @return javax.swing.JButton
 */
public JButton getJButtonCreateEffect() {
	if (jButtonCreateEffect == null) {
		jButtonCreateEffect = new JideButton();
		jButtonCreateEffect.setButtonStyle(JideButton.FLAT_STYLE);
		jButtonCreateEffect.setHorizontalAlignment(SwingConstants.CENTER);
		jButtonCreateEffect.setSize(new Dimension(113, 20));
		jButtonCreateEffect.setAlignmentY(5.0F);
		jButtonCreateEffect.setIcon(CMAction.EFFECT_CREATE.getIcon());
		jButtonCreateEffect.setFocusCycleRoot(true);
		jButtonCreateEffect.setText((String) CMAction.EFFECT_CREATE.getAction().getValue(Action.NAME));

	}
	return jButtonCreateEffect;
}

/**
 *  adds the sincronization between the Shuttle
 */
public void initializePanelSync() {
	getJListAssigned().setCellRenderer(listCellRenderer );
	getJListAvailable().setCellRenderer(listCellRenderer);

	FocusListener shuttleFocusListener = new FocusAdapter(){
		/* (non-Javadoc)
		 * @see java.awt.event.FocusAdapter#focusGained(java.awt.event.FocusEvent)
		 */
		@Override
		public void focusGained(FocusEvent p_e) {
			// TODO Auto-generated method stub


			if (p_e.getSource().equals(getJListAssigned())&&getJListAssigned().getSelectedIndex()!=-1)
			{

				effectEditController = (CMEffectEditController) getShuttleEffects().getAssignedList().get(getJListAssigned().getSelectedIndex());
			}
			if (p_e.getSource().equals(getJListAvailable())&&getJListAvailable().getSelectedIndex()!=-1)
			{

				effectEditController =(CMEffectEditController) getShuttleEffects().getAvailableList().get(getJListAvailable().getSelectedIndex());
			}
			int selectedTab = getEffectDialogPanel().getJTabbedPaneEffect().getSelectedIndex();
			getJSplitContent().setBottomComponent(null);
			if (effectEditController==null) {
				getJSplitContent().setBottomComponent(getPanelEffect());
			    getJSplitContent().setDividerLocation(150);
				return;
			}
			setPanelEffect(null);
			getPanelEffect().add(effectEditController.getPanel(),BorderLayout.CENTER);
			getJSplitContent().setBottomComponent(getPanelEffect());
			getJSplitContent().setDividerLocation(150);
			getEffectDialogPanel().getJTabbedPaneEffect().setSelectedIndex(selectedTab);
	}
	};
	ListSelectionListener shuttleListener = new ListSelectionListener(){





		public void valueChanged(ListSelectionEvent p_e) {
			//only for single selection

			if (p_e.getValueIsAdjusting()) return;
			if (p_e.getSource().equals(getJListAssigned())&&getJListAssigned().getSelectedIndex()!=-1)
			{
				List leftList = getAssignedList();
				if (getJListAssigned().getSelectedIndex()<leftList.size())
					effectEditController = (CMEffectEditController) leftList.get(getJListAssigned().getSelectedIndex());
			}
			if (p_e.getSource().equals(getJListAvailable())&&getJListAvailable().getSelectedIndex()!=-1){
				List rigthList = getAvailableList();
				if (getJListAvailable().getSelectedIndex()<rigthList.size())
					effectEditController =(CMEffectEditController) rigthList.get(getJListAvailable().getSelectedIndex());
			}
			int selectedTab = getEffectDialogPanel().getJTabbedPaneEffect().getSelectedIndex();
			getJSplitContent().setBottomComponent(null);
			if (effectEditController==null) {
				getJSplitContent().setBottomComponent(getPanelEffect());
				getJSplitContent().setDividerLocation(150);
				return;
			}

			setPanelEffect(null);
			getPanelEffect().add(effectEditController.getPanel(),BorderLayout.CENTER);
			getJSplitContent().setBottomComponent(getPanelEffect());
			getJSplitContent().setDividerLocation(150);
			getEffectDialogPanel().getJTabbedPaneEffect().setSelectedIndex(selectedTab);

		}};
	this.getJListAssigned().addListSelectionListener(shuttleListener );
	this.getJListAssigned().addFocusListener(shuttleFocusListener );
	this.getJListAvailable().addListSelectionListener(shuttleListener);
	this.getJListAvailable().addFocusListener(shuttleFocusListener );
}
public List<Component> getTabOrder(){
	ArrayList< Component> list = new ArrayList<Component>();
	list.addAll((getShuttleEffects().getTabOrder()));
	list.addAll(getEffectDialogPanel().getTabOrder());
	list.add(getJButtonCreateEffect());
	return list;
}
}  //  @jve:decl-index=0:visual-constraint="10,10"
