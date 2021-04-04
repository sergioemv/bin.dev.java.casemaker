package bi.view.actions.testcase;

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
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ListModel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import bi.view.actions.effect.CMEffectsEditPanel;
import bi.view.icons.CMIcon;
import bi.view.lang.CMMessages;
import bi.view.utils.CMDnDJList;
import bi.view.utils.CMJEditorPaneFocusChangeable;
import bi.view.utils.CMRiskLevelView;
import bi.view.utils.CMStateView;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Color;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.JSplitPane;
import javax.swing.JToggleButton;

import com.jidesoft.swing.JideTabbedPane;

public class CMPanelTestCase extends JPanel {
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
	Vector m_AllowedTestCases = null;
	Vector m_TestCasesWithCombination = null;  //  @jve:decl-index=0:
	private JPanel jPanelUsedEffects = null;
	private JScrollPane jScrollPaneUsedEffects = null;
	private CMDnDJList jListEffects = null;
	private JSplitPane jSplitPaneGeneralAndEffects = null;
	private JPanel jPanelSubContainer = null;
	private JPanel jPanelErrors = null;
	private CMPanelErrorsAssigment CMPanelErrorsAssigment = null;
	private JPanel jPanelStdCombinations = null;
	private CMPanelStdCombinationsAssigment CMPanelStdCombinationsAssigment = null;
	private CMPanelCombinationsAssigment panelCombinationsAssigment = null;
	private JPanel jPanelCombinations = null;
	public CMPanelTestCase() {
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
			jPanelDescription.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Description", TitledBorder.LEADING, TitledBorder.TOP, new Font("Dialog", Font.PLAIN, 11), SystemColor.activeCaption)); //$NON-NLS-1$);   
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
			jTabbedPaneCombination.addTab(CMMessages.getString("LABEL_CASEMAKER_SETTINGS_TAB"),CMIcon.TESTCASE.getImageIcon(),getJPanelGeneral(),null);
			jTabbedPaneCombination.addTab(CMMessages.getString("LABEL_ERRORS"), CMIcon.ERROR_MANAGMENT.getImageIcon(), getJPanelErrors(), null);
			jTabbedPaneCombination.addTab(CMMessages.getString("LABEL_STD_COMBINATIONS"), CMIcon.STDCOMBINATION.getImageIcon(), getJPanelStdCombinations(), null);
			jTabbedPaneCombination.addTab(CMMessages.getString("LABEL_COMBINATIONS"), CMIcon.COMBINATION_ASSIGN_TO_TESTCASE.getImageIcon(), getJPanelCombinations(), null);
		
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
			jPanelGeneral.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), CMMessages.getString("LABEL_TEST_CASE"), TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.PLAIN, 11), SystemColor.activeCaption));
			jPanelGeneral.add(getJSplitPaneGeneralAndEffects(), null);
		
		}
		return jPanelGeneral;
	}






	/**
	 * This method initializes jPanelUsedEffects	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelUsedEffects() {
		if (jPanelUsedEffects == null) {
			jPanelUsedEffects = new JPanel();
			jPanelUsedEffects.setLayout(new BorderLayout());
			jPanelUsedEffects.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), CMMessages.getString("IMPORT_CSV_EFFECTS_NAME"), TitledBorder.LEADING, TitledBorder.TOP, new Font("Dialog", Font.PLAIN, 11), SystemColor.activeCaption)); //$NON-NLS-1$);
			jPanelUsedEffects.add(getJScrollPaneUsedEffects(), BorderLayout.CENTER);
			jPanelUsedEffects.setPreferredSize(new Dimension(0,10));
		}
		return jPanelUsedEffects;
	}


	/**
	 * This method initializes jScrollPaneUsedEffects	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPaneUsedEffects() {
		if (jScrollPaneUsedEffects == null) {
			jScrollPaneUsedEffects = new JScrollPane();
			jScrollPaneUsedEffects.setViewportView(getJListEffects());
		}
		return jScrollPaneUsedEffects;
	}


	/**
	 * This method initializes jListEffects	
	 * 	
	 * @return javax.swing.JList	
	 */
	private JList getJListEffects() {
		if (jListEffects == null) {
			jListEffects = new CMDnDJList();
			jListEffects.setModel(new DefaultListModel());
			jListEffects.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			jListEffects.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
			jListEffects.setLeftIcon(CMIcon.EFFECT.getImageIcon());
		}
		return jListEffects;
	}
	public DefaultListModel getEffectsListModel(){
		return (DefaultListModel) getJListEffects().getModel();
	}


	/**
	 * This method initializes jSplitPaneGeneralAndEffects	
	 * 	
	 * @return javax.swing.JSplitPane	
	 */
	private JSplitPane getJSplitPaneGeneralAndEffects() {
		if (jSplitPaneGeneralAndEffects == null) {
			jSplitPaneGeneralAndEffects = new JSplitPane();
			jSplitPaneGeneralAndEffects.setOrientation(JSplitPane.VERTICAL_SPLIT);
			jSplitPaneGeneralAndEffects.setDividerLocation(275);
			jSplitPaneGeneralAndEffects.setDividerSize(10);
			jSplitPaneGeneralAndEffects.setOneTouchExpandable(true);
			jSplitPaneGeneralAndEffects.setBorder(null);
			jSplitPaneGeneralAndEffects.setTopComponent(getJPanelSubContainer());
			jSplitPaneGeneralAndEffects.setBottomComponent(getJPanelUsedEffects());
		}
		return jSplitPaneGeneralAndEffects;
	}


	/**
	 * This method initializes jPanelSubContainer	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelSubContainer() {
		if (jPanelSubContainer == null) {
			jPanelSubContainer = new JPanel();
			jPanelSubContainer.setLayout(new BoxLayout(getJPanelSubContainer(), BoxLayout.Y_AXIS));
			jPanelSubContainer.add(getJPanelUpperPanel(), null);
			jPanelSubContainer.add(getJPanelGeneratedDescription(), null);
			jPanelSubContainer.add(getJPanelDescription(), null);
		}
		return jPanelSubContainer;
	}


	/**
	 * This method initializes jPanelErrors	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelErrors() {
		if (jPanelErrors == null) {
			jPanelErrors = new JPanel();
			jPanelErrors.setLayout(new BorderLayout());
			jPanelErrors.add(getCMPanelErrorsAssigment(), BorderLayout.CENTER);
		}
		return jPanelErrors;
	}


	/**
	 * This method initializes CMPanelErrorsAssigment	
	 * 	
	 * @return bi.view.actions.testcase.CMPanelErrorsAssigment	
	 */
	public CMPanelErrorsAssigment getCMPanelErrorsAssigment() {
		if (CMPanelErrorsAssigment == null) {
			CMPanelErrorsAssigment = new CMPanelErrorsAssigment();
		}
		return CMPanelErrorsAssigment;
	}


	/**
	 * This method initializes jPanelStdCombinations	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelStdCombinations() {
		if (jPanelStdCombinations == null) {
			jPanelStdCombinations = new JPanel();
			jPanelStdCombinations.setLayout(new BorderLayout());
			jPanelStdCombinations.add(getCMPanelStdCombinationsAssigment(), BorderLayout.CENTER);
		}
		return jPanelStdCombinations;
	}


	/**
	 * This method initializes CMPanelStdCombinationsAssigment	
	 * 	
	 * @return bi.view.actions.testcase.CMPanelStdCombinationsAssigment	
	 */
	public CMPanelStdCombinationsAssigment getCMPanelStdCombinationsAssigment() {
		if (CMPanelStdCombinationsAssigment == null) {
			CMPanelStdCombinationsAssigment = new CMPanelStdCombinationsAssigment();
		}
		return CMPanelStdCombinationsAssigment;
	}


	/**
	 * This method initializes jPanelCombinations	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	public JPanel getJPanelCombinations() {
		if (jPanelCombinations == null) {
			jPanelCombinations = new JPanel();
			jPanelCombinations.setLayout(new BorderLayout());
			jPanelCombinations.add(getPanelCombinationAssignnment(), BorderLayout.CENTER);
		}
		return jPanelCombinations;
	}


	public CMPanelCombinationsAssigment getPanelCombinationAssignnment() {
		if (panelCombinationsAssigment==null){
			panelCombinationsAssigment = new CMPanelCombinationsAssigment();
		}
		return panelCombinationsAssigment;
	}
}  
