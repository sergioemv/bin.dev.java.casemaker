/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.view.actions.effect;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.SystemColor;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import com.jidesoft.swing.JideTabbedPane;

import bi.view.icons.CMIcon;
import bi.view.lang.CMMessages;
import bi.view.utils.CMJEditorPaneFocusChangeable;
import bi.view.utils.CMRiskLevelView;
import bi.view.utils.CMStateView;

/**
 * @author smoreno
 *
 */
public class CMEffectDialogPanel extends JPanel {

	private JideTabbedPane jTabbedPaneEffect = null;
	private JPanel jPanel = null;
	private JScrollPane jScrollPaneDescription = null;
	private CMJEditorPaneFocusChangeable jEditorPaneDescription = null;
	private CMRequirementDialogPanel requirementPanel = null;
	private CMExpectedResultsDialogPanel expectedResultsDialogPanel = null;
	private JPanel jPanelGeneral = null;
	private JPanel jPanelGeneralAbove = null;
	private CMRiskLevelView riskLevelView = null;
	private CMStateView stateView = null;
	/**
	 * @param p_layout
	 * @param p_isDoubleBuffered
	 */
	public CMEffectDialogPanel(LayoutManager p_layout,
			boolean p_isDoubleBuffered) {
		super(p_layout, p_isDoubleBuffered);
		// TODO Auto-generated constructor stub
		initialize();
	}

	/**
	 * @param p_layout
	 */
	public CMEffectDialogPanel(LayoutManager p_layout) {
		super(p_layout);
		// TODO Auto-generated constructor stub
		initialize();
	}

	/**
	 * @param p_isDoubleBuffered
	 */
	public CMEffectDialogPanel(boolean p_isDoubleBuffered) {
		super(p_isDoubleBuffered);
		// TODO Auto-generated constructor stub
		initialize();
	}

	/**
	 *
	 */
	public CMEffectDialogPanel() {
		super();
		// TODO Auto-generated constructor stub
		initialize();
	}

	/**
	 * This method initializes this
	 *
	 * @return void
	 */
	private void initialize() {
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new java.awt.Dimension(300,200));
        this.setSize(new java.awt.Dimension(400,300));
        this.setLocation(new java.awt.Point(0,0));
        this.add(getJTabbedPaneEffect(), java.awt.BorderLayout.CENTER);
	}

	/**
	 * This method initializes jTabbedPaneEffect
	 *
	 * @return javax.swing.JTabbedPane
	 */
	public JTabbedPane getJTabbedPaneEffect() {
		if (jTabbedPaneEffect == null) {
			jTabbedPaneEffect = new JideTabbedPane();
			jTabbedPaneEffect.setTabPlacement(javax.swing.JTabbedPane.TOP);
			jTabbedPaneEffect.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 11)); //$NON-NLS-1$
			jTabbedPaneEffect.addTab(CMMessages.getString("LABEL_CASEMAKER_SETTINGS_TAB"), CMIcon.EFFECT.getImageIcon(), getJPanelGeneral(), null);
			jTabbedPaneEffect.addTab(CMMessages.getString("EFFECT_DIALOG_REQUIREMENT_TAB"), null, getRequirementPanel(), null); //$NON-NLS-1$
			jTabbedPaneEffect.addTab(CMMessages.getString("EFFECT_DIALOG_EXPECTED_RESULT_TAB"), null, getExpectedResultsDialogPanel(), null); //$NON-NLS-1$

		}
		return jTabbedPaneEffect;
	}

	/**
	 * This method initializes jPanel
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
		}
		return jPanel;
	}

	/**
	 * This method initializes jScrollPaneDescription
	 *
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPaneDescription() {
		if (jScrollPaneDescription == null) {
			jScrollPaneDescription = new JScrollPane();
			jScrollPaneDescription.setViewportBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEmptyBorder(4,4,4,4), javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED)));
			jScrollPaneDescription.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), CMMessages.getString("EFFECT_DIALOG_DESCRIPTION_TAB"), TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.PLAIN, 11), SystemColor.activeCaption));
			jScrollPaneDescription.setViewportView(getJEditorPaneDescription());
		}
		return jScrollPaneDescription;
	}

	/**
	 * This method initializes jEditorPaneDescription
	 *
	 * @return javax.swing.JEditorPane
	 */
	public JEditorPane getJEditorPaneDescription() {
		if (jEditorPaneDescription == null) {
			jEditorPaneDescription = new CMJEditorPaneFocusChangeable();
		}
		return jEditorPaneDescription;
	}

	/**
	 * This method initializes requirementPanel
	 *
	 * @return bi.view.actions.effect.CMRequirementDialogPanel
	 */
	public CMRequirementDialogPanel getRequirementPanel() {
		if (requirementPanel == null) {
			requirementPanel = new CMRequirementDialogPanel();
			requirementPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), CMMessages.getString("EFFECT_DIALOG_REQUIREMENTS_TITLE"), TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.PLAIN, 11), SystemColor.activeCaption)); //$NON-NLS-1$ //$NON-NLS-2$
		}
		return requirementPanel;
	}

	/**
	 * This method initializes expectedResultsDialogPanel
	 *
	 * @return bi.view.actions.effect.CMExpectedResultsDialogPanel
	 */
	public CMExpectedResultsDialogPanel getExpectedResultsDialogPanel() {
		if (expectedResultsDialogPanel == null) {
			expectedResultsDialogPanel = new CMExpectedResultsDialogPanel();
			expectedResultsDialogPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), CMMessages.getString("EFFECT_DIALOG_EXPECTED_RESULTS_TITLE"), TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.PLAIN, 11), SystemColor.activeCaption)); //$NON-NLS-1$ //$NON-NLS-2$
			expectedResultsDialogPanel.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 11)); //$NON-NLS-1$
		}
		return expectedResultsDialogPanel;
	}

	public CMExpectedResultsGrid getGridExpectedResults() {
		return this.expectedResultsDialogPanel.getGridExpectedResults();
	}

	/**
	 * @return
	 */
	public List getTabOrder() {
		ArrayList list = new ArrayList();
		if (this.getJTabbedPaneEffect().getSelectedIndex()==0)
			list.add(getJEditorPaneDescription());
		if (this.getJTabbedPaneEffect().getSelectedIndex()==1){
			list.add(getRequirementPanel().getJComboSelectedRequirement());
			list.add(getRequirementPanel().getCMJListReqs());
			list.add(getRequirementPanel().getJButtonAdd());
			list.add(getRequirementPanel().getJButtonRemove());
		}
		if (this.getJTabbedPaneEffect().getSelectedIndex()==2){
			list.add(getGridExpectedResults());
			list.add(getExpectedResultsDialogPanel().getJButtonAdd());
			list.add(getExpectedResultsDialogPanel().getJButtonDelete());
		}
		list.add(getJTabbedPaneEffect());
		return list;
	}

	/**
	 * This method initializes jPanelGeneral
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelGeneral() {
		if (jPanelGeneral == null) {
			jPanelGeneral = new JPanel();
			jPanelGeneral.setLayout(new BorderLayout());
			jPanelGeneral.add(getJScrollPaneDescription(), BorderLayout.CENTER);
			jPanelGeneral.add(getJPanelGeneralAbove(), BorderLayout.NORTH);
		}
		return jPanelGeneral;
	}

	/**
	 * This method initializes jPanelGeneralAbove
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelGeneralAbove() {
		if (jPanelGeneralAbove == null) {
			FlowLayout flowLayout = new FlowLayout();
			flowLayout.setAlignment(java.awt.FlowLayout.LEFT);
			flowLayout.setVgap(0);
			flowLayout.setHgap(5);
			jPanelGeneralAbove = new JPanel();
			jPanelGeneralAbove.setLayout(flowLayout);
			jPanelGeneralAbove.add(getStateView(), null);
			jPanelGeneralAbove.add(getRiskLevelView(), null);
		}
		return jPanelGeneralAbove;
	}

	/**
	 * This method initializes riskLevelView
	 *
	 * @return bi.view.utils.CMRiskLevelView
	 */
	public CMRiskLevelView getRiskLevelView() {
		if (riskLevelView == null) {
			riskLevelView = new CMRiskLevelView();
		}
		return riskLevelView;
	}

	/**
	 * This method initializes stateView
	 *
	 * @return bi.view.utils.CMState
	 */
	public CMStateView getStateView() {
		if (stateView == null) {
			stateView = new CMStateView();
		}
		return stateView;
	}

	public void setRiskLevelView(CMRiskLevelView riskLevelView) {
		this.riskLevelView = riskLevelView;
	}

}
