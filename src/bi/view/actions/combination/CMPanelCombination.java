package bi.view.actions.combination;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.SystemColor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import com.jidesoft.swing.JideTabbedPane;

import bi.view.actions.effect.CMEffectsEditPanel;
import bi.view.icons.CMIcon;
import bi.view.lang.CMMessages;
import bi.view.utils.CMJEditorPaneFocusChangeable;
import bi.view.utils.CMRiskLevelView;
import bi.view.utils.CMStateView;

public class CMPanelCombination extends JPanel {
	private JPanel jPanelUpperPanel = null;
	private JPanel jPanelDescription = null;
	private JPanel jPanelGeneratedDescription = null;
	private JPanel jPanelStateAndRisk = null;
	private JScrollPane jScrollPaneDescription = null;
	private JScrollPane jScrollPaneGeneratedDescription = null;
	private CMStateView jCMState = null;
	private CMRiskLevelView jCMRiskLevelView = null;
	private CMJEditorPaneFocusChangeable jTextAreaDescription = null;	
	private CMJEditorPaneFocusChangeable jTextAreaGeneratedDescription = null;	
	private JideTabbedPane jTabbedPaneCombination = null;
	private JPanel jPanelGeneral = null;
	private JPanel jPanelEffects = null;
	private JPanel jPanelTestCases = null;
	private CMEffectsEditPanel effectsEditPanel = null;
	Vector m_AllowedTestCases = null;
	Vector m_TestCasesWithCombination = null;  //  @jve:decl-index=0:
	private CMPanelTestCasesAssigment panelTestCasesAssigment = null;
	private JPanel jPanelOrigin = null;
	private JLabel jLabelOrigin = null;

	
	public CMPanelCombination() {
		super();
		initialize();
	}
		
	
	private void initialize() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setMinimumSize(new Dimension(670, 296));
        this.setSize(new Dimension(670, 526));
        this.add(getJTabbedPaneCombination(), null);     
	}
	/**
	 * This method initializes jPanelUpperPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	public JPanel getJPanelUpperPanel() {
		if (jPanelUpperPanel == null) {
			jPanelUpperPanel = new JPanel();
			jPanelUpperPanel.setLayout(new BoxLayout(getJPanelUpperPanel(), BoxLayout.X_AXIS));
			jPanelUpperPanel.add(getJPanelStateAndRisk(), null);
			jPanelUpperPanel.add(getJPanelOrigin(), null);
			
		}
		return jPanelUpperPanel;
	}
	/**
	 * This method initializes jPanelDescription	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	public JPanel getJPanelDescription() {
		if (jPanelDescription == null) {
			jPanelDescription = new JPanel();
			jPanelDescription.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), CMMessages.getString("LABEL_DESCRIPTION"), TitledBorder.LEADING, TitledBorder.TOP, new Font("Dialog", Font.PLAIN, 11), SystemColor.activeCaption)); //$NON-NLS-1$);   
			jPanelDescription.setLayout(new BorderLayout());
			jPanelDescription.setPreferredSize(new Dimension(0, 10));
			jPanelDescription.add(getJScrollPaneDescription(), BorderLayout.CENTER);
		}
		return jPanelDescription;
	}
	public JPanel getJPanelGeneratedDescription() {
		if (jPanelGeneratedDescription == null) {
			jPanelGeneratedDescription = new JPanel();
			jPanelGeneratedDescription.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), CMMessages.getString("LABEL_DESCRIPTION1"), TitledBorder.LEADING, TitledBorder.TOP, new Font("Dialog", Font.PLAIN, 11), SystemColor.activeCaption)); //$NON-NLS-1$);   
			jPanelGeneratedDescription.setLayout(new BorderLayout());
			jPanelGeneratedDescription.setPreferredSize(new Dimension(0, 10));
			jPanelGeneratedDescription.add(getJScrollPaneGeneratedDescription(), BorderLayout.CENTER);
		}
		return jPanelGeneratedDescription;
	}
	/**
	 * This method initializes jPanelStateAndRisk	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelStateAndRisk() {
		if (jPanelStateAndRisk == null) {
			FlowLayout flowLayout2 = new FlowLayout();
			flowLayout2.setAlignment(FlowLayout.LEFT);
			jPanelStateAndRisk = new JPanel();
			jPanelStateAndRisk.setLayout(flowLayout2);
			jPanelStateAndRisk.add(getJCMState(), null);
			jPanelStateAndRisk.add(getJCMRiskLevelView(), null);
		}
		return jPanelStateAndRisk;
	}

	/**
	 * This method initializes jScrollPaneDescription	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPaneDescription() {
		if (jScrollPaneDescription == null) {
			jScrollPaneDescription = new JScrollPane();
			jScrollPaneDescription.setViewportView(getJTextAreaDescription());
		}
		return jScrollPaneDescription;
	}
	private JScrollPane getJScrollPaneGeneratedDescription() {
		if (jScrollPaneGeneratedDescription == null) {
			jScrollPaneGeneratedDescription = new JScrollPane();
			jScrollPaneGeneratedDescription.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
			jScrollPaneGeneratedDescription.setViewportView(getJTextAreaGeneratedDescription());
		}
		return jScrollPaneGeneratedDescription;
	}
	public Collection<? extends Component> getTabOrder() {
		ArrayList<Component> list = new ArrayList<Component>();
		if(getJCMState().isVisible())
			list.add(getJCMState().getJComboBoxState());
		if (getJCMRiskLevelView().isVisible())
			list.add(getJCMRiskLevelView().getComboBox());
		list.add(getJTextAreaDescription());
		
		return list;
	}
	/**
	 * This method initializes jCMState	
	 * 	
	 * @return bi.view.utils.CMState	
	 */
	public CMStateView getJCMState() {
		if (jCMState == null) {
			FlowLayout flowLayout = new FlowLayout();
			flowLayout.setAlignment(FlowLayout.RIGHT);
			flowLayout.setVgap(3);
			flowLayout.setHgap(15);
			jCMState = new CMStateView();
			jCMState.setLayout(flowLayout);
		}
		return jCMState;
	}
	/**
	 * This method initializes jCMRiskLevelView	
	 * 	
	 * @return bi.view.utils.CMRiskLevelView	
	 */
	public CMRiskLevelView getJCMRiskLevelView() {
		if (jCMRiskLevelView == null) {
			FlowLayout flowLayout1 = new FlowLayout();
			flowLayout1.setAlignment(FlowLayout.RIGHT);
			flowLayout1.setVgap(3);
			flowLayout1.setHgap(15);
			jCMRiskLevelView = new CMRiskLevelView();
		
			jCMRiskLevelView.setLayout(flowLayout1);
		}
		return jCMRiskLevelView;
	}

	  /**
	   * This method initializes jTextAreaDescription	
	   * 	
	   * @return bi.view.utils.CMJEditorPaneFocusChangeable	
	   */
	  public CMJEditorPaneFocusChangeable getJTextAreaDescription() {
	  	if (jTextAreaDescription == null) {
	  		jTextAreaDescription = new CMJEditorPaneFocusChangeable();
	  		jTextAreaDescription.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, null, null, null, null));
	  		jTextAreaDescription.setText("");
	  	}
	  	return jTextAreaDescription;
	  }
	  public CMJEditorPaneFocusChangeable getJTextAreaGeneratedDescription() {
		  	if (jTextAreaGeneratedDescription == null) {
		  		jTextAreaGeneratedDescription = new CMJEditorPaneFocusChangeable();
		  		jTextAreaGeneratedDescription.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		  		jTextAreaGeneratedDescription.setEditable(false);
		  		jTextAreaGeneratedDescription.setBackground(SystemColor.inactiveCaptionBorder);
		  		jTextAreaGeneratedDescription.setText("");
		  	}
		  	return jTextAreaGeneratedDescription;
		  }

	/**	 * This method initializes jTabbedPaneEquivalence	
	 * 	
	 * @return javax.swing.JTabbedPane	
	 */
	public JTabbedPane getJTabbedPaneCombination() {
		if (jTabbedPaneCombination == null) {
			jTabbedPaneCombination = new JideTabbedPane();
			/*jTabbedPaneCombination.addTab(CMMessages.getString("LABEL_CASEMAKER_SETTINGS_TAB"), null, getJPanelGeneral(), null);
			jTabbedPaneCombination.addTab(CMMessages.getString("IMPORT_CSV_EFFECTS_NAME"), null, getJPanelEffects(), null);
*/			////
			jTabbedPaneCombination.addTab(CMMessages.getString("LABEL_CASEMAKER_SETTINGS_TAB"),CMIcon.COMBINATION_EDIT.getImageIcon(),getJPanelGeneral(),null);
			jTabbedPaneCombination.addTab(CMMessages.getString("LABEL_CAUSE_EFFECTS"),CMIcon.EFFECT.getImageIcon(),getJPanelEffects(), null);
			jTabbedPaneCombination.addTab(CMMessages.getString("LABEL_TEST_CASES"),CMIcon.TESTCASE.getImageIcon(),getJPanelTestCases(), null);
		}
		return jTabbedPaneCombination;
	}

	/**
	 * This method initializes jPanelGeneral	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	public JPanel getJPanelGeneral() {
		if (jPanelGeneral == null) {
			jPanelGeneral = new JPanel();
			jPanelGeneral.setLayout(new BoxLayout(getJPanelGeneral(), BoxLayout.Y_AXIS));
			jPanelGeneral.add(getJPanelUpperPanel(), null);
			jPanelGeneral.add(getJPanelGeneratedDescription(), null);
			jPanelGeneral.add(getJPanelDescription(), null);
			jPanelGeneral.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), CMMessages.getString("LABEL_COMBINATION"), TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.PLAIN, 11), SystemColor.activeCaption));
		
		}
		return jPanelGeneral;
	}

	/**
	 * This method initializes jPanelEffects	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	public JPanel getJPanelEffects() {
		if (jPanelEffects == null) {
			jPanelEffects = new JPanel();
			jPanelEffects.setLayout(new BorderLayout());
			//jPanelEffects.add(getEffectsEditPanel(), BorderLayout.CENTER);
		}
		return jPanelEffects;
	}

	
	/**
	 * This method initializes effectsEditPanel	
	 * 	
	 * @return bi.view.actions.effect.CMEffectsEditPanel	
	 */
	public CMEffectsEditPanel getEffectsEditPanel() {
		if (effectsEditPanel == null) {
			effectsEditPanel = new CMEffectsEditPanel();
		}
		return effectsEditPanel;
	}



	public void setEffectsEditPanel(CMEffectsEditPanel effectsEditPanel) {
		this.effectsEditPanel = effectsEditPanel;
		jPanelEffects.add(this.effectsEditPanel , BorderLayout.CENTER);
	}

	private JPanel getJPanelTestCases() {
		
		if (jPanelTestCases == null) {
			jPanelTestCases = new JPanel();
			jPanelTestCases.setLayout(new BorderLayout());					
			jPanelTestCases.add(getPanelTestCasesAssigment(), BorderLayout.CENTER);
					
		}
		return jPanelTestCases;
	}
	
	public Vector getM_TestCasesWithCombination() {
		return m_TestCasesWithCombination;
	}


	public Vector getM_AllowedTestCases() {
		return m_AllowedTestCases;
	}
	/**
	 * This method initializes panelTestCasesAssigment	
	 * 	
	 * @return bi.view.actions.combination.CMPanelTestCasesAssigment	
	 */
	public CMPanelTestCasesAssigment getPanelTestCasesAssigment() {
		if (panelTestCasesAssigment == null) {
			panelTestCasesAssigment = new CMPanelTestCasesAssigment();
		}
		return panelTestCasesAssigment;
	}


	/**
	 * This method initializes jPanelOrigin	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelOrigin() {
		if (jPanelOrigin == null) {
			
				FlowLayout flowLayout3 = new FlowLayout();
				flowLayout3.setAlignment(FlowLayout.RIGHT);
				flowLayout3.setVgap(10);
				flowLayout3.setHgap(10);
				jPanelOrigin = new JPanel();
					jPanelOrigin.setLayout(flowLayout3);
			jPanelOrigin.add(getjLabelOrigin(), null);
		}
		return jPanelOrigin;
	}


	public JLabel getjLabelOrigin() {
		if (jLabelOrigin==null) {
		jLabelOrigin = new JLabel();
		jLabelOrigin.setText("text");
		jLabelOrigin.setHorizontalAlignment(SwingConstants.LEADING);
		jLabelOrigin.setHorizontalTextPosition(SwingConstants.LEFT);
		jLabelOrigin.setIcon(CMIcon.EQUIVALENCECLASS_IN_COMBINATION.getImageIcon());
		}
		return jLabelOrigin;
	}
}  
