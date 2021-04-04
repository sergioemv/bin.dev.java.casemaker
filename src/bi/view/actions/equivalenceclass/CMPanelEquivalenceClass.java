package bi.view.actions.equivalenceclass;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.SystemColor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import com.jidesoft.swing.JideTabbedPane;

import bi.view.actions.effect.CMEffectsEditPanel;
import bi.view.icons.CMIcon;
import bi.view.lang.CMMessages;
import bi.view.utils.CMBaseJTabbedPane;
import bi.view.utils.CMJEditorPaneFocusChangeable;
import bi.view.utils.CMRiskLevelView;
import bi.view.utils.CMStateView;

public class CMPanelEquivalenceClass extends JPanel {
	private JPanel jPanelUpperPanel = null;
	private JPanel jPanelDescription = null;
	private JPanel jPanelValue = null;
	private JLabel jLabelValue = null;
	private JTextField jTextFieldValue = null;
	private JPanel jPanelStateAndRisk = null;
	private JScrollPane jScrollPaneDescription = null;
	private CMStateView jCMState = null;
	private CMRiskLevelView jCMRiskLevelView = null;
	private CMJEditorPaneFocusChangeable jTextAreaDescription = null;
	private boolean m_Showed = false;
	private int m_OldRiskLevel = -1;
	private JideTabbedPane jTabbedPaneEquivalence = null;
	private JPanel jPanelGeneral = null;
	private JPanel jPanelEffects = null;
	private CMEffectsEditPanel effectsEditPanel = null;
	private JPanel jPanelTextFieldValue = null;
	private JPanel jPanelText = null;
	private JPanel jPanelLabelValue = null;

	public CMPanelEquivalenceClass() {
		super();
		initialize();
	}

	/**
	 * This method initi			this.add(getJPanelDescription(), null);
			this.add(getJPanelEffects(), null);
			this.setSize(new Dimension(361, 223));
alizes this
	 *
	 */
	private void initialize() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setMinimumSize(new Dimension(515, 277));
        this.setSize(new Dimension(515, 407));
        this.add(getJTabbedPaneEquivalence(), null);

	}
	/**
	 * This method initializes jPanelUpperPanel
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelUpperPanel() {
		if (jPanelUpperPanel == null) {
			jPanelUpperPanel = new JPanel();
			jPanelUpperPanel.setLayout(new BoxLayout(getJPanelUpperPanel(), BoxLayout.X_AXIS));
			jPanelUpperPanel.setPreferredSize(new Dimension(0, 55));
			jPanelUpperPanel.add(getJPanelValue(), null);
			jPanelUpperPanel.add(getJPanelStateAndRisk(), null);

		}
		return jPanelUpperPanel;
	}
	/**
	 * This method initializes jPanelDescription
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelDescription() {
		if (jPanelDescription == null) {
			jPanelDescription = new JPanel();
			jPanelDescription.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), CMMessages.getString("EFFECT_DIALOG_DESCRIPTION_TAB"), TitledBorder.LEADING, TitledBorder.TOP, new Font("Dialog", Font.PLAIN, 11), SystemColor.activeCaption)); //$NON-NLS-1$);
			jPanelDescription.setLayout(new BorderLayout());
			jPanelDescription.setPreferredSize(new Dimension(0, 30));
			jPanelDescription.add(getJScrollPaneDescription(), BorderLayout.CENTER);
		}
		return jPanelDescription;
	}
	/**
	 * This method initializes jPanelValue
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelValue() {
		if (jPanelValue == null) {
			jLabelValue = new JLabel();
			jLabelValue.setText(CMMessages.getString("LABEL_VALUE"));
			jPanelValue = new JPanel();
			jPanelValue.setLayout(new BoxLayout(getJPanelValue(), BoxLayout.X_AXIS));
			jPanelValue.setPreferredSize(new Dimension(352, 50));
			jPanelValue.add(getJPanelLabelValue(), null);
			jPanelValue.add(getJPanelText(), null);
		}
		return jPanelValue;
	}
	/**
	 * This method initializes jTextFieldValue
	 *
	 * @return javax.swing.JTextField
	 */
	public JTextField getJTextFieldValue() {
		if (jTextFieldValue == null) {
			jTextFieldValue = new JTextField("");
			jTextFieldValue.setText("");
			jTextFieldValue.setPreferredSize(new Dimension(270, 20));
		}
		return jTextFieldValue;
	}

	/**
	 * This method initializes jPanelStateAndRisk
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelStateAndRisk() {
		if (jPanelStateAndRisk == null) {
			jPanelStateAndRisk = new JPanel();
			jPanelStateAndRisk.setLayout(new BoxLayout(getJPanelStateAndRisk(), BoxLayout.Y_AXIS));
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
	public Collection<? extends Component> getTabOrder() {
		ArrayList<Component> list = new ArrayList<Component>();
		list.add(getJTextFieldValue());
		list.add(getJCMState().getJComboBoxState());
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
			jCMState.setPreferredSize(new Dimension(150, 26));
			jCMState.setMinimumSize(new Dimension(150, 26));
			jCMState.setMaximumSize(new Dimension(150, 26));
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
			jCMRiskLevelView.setPreferredSize(new Dimension(150, 26));
			jCMRiskLevelView.setMinimumSize(new Dimension(150, 26));
			jCMRiskLevelView.setMaximumSize(new Dimension(150, 26));
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

	/**	 * This method initializes jTabbedPaneEquivalence
	 *
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getJTabbedPaneEquivalence() {
		if (jTabbedPaneEquivalence == null) {
			jTabbedPaneEquivalence = new JideTabbedPane();
			jTabbedPaneEquivalence.addTab(CMMessages.getString("LABEL_CASEMAKER_SETTINGS_TAB"), CMIcon.EQUIVALENCECLASS.getImageIcon(), getJPanelGeneral(), null);
			jTabbedPaneEquivalence.addTab(CMMessages.getString("IMPORT_CSV_EFFECTS_NAME"), CMIcon.EFFECT.getImageIcon(), getJPanelEffects(), null);
		}
		return jTabbedPaneEquivalence;
	}

	/**
	 * This method initializes jPanelGeneral
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelGeneral() {
		if (jPanelGeneral == null) {
			jPanelGeneral = new JPanel();
			jPanelGeneral.setLayout(new BoxLayout(getJPanelGeneral(), BoxLayout.Y_AXIS));
			jPanelGeneral.add(getJPanelUpperPanel(), null);
			jPanelGeneral.add(getJPanelDescription(), null);
			jPanelGeneral.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), CMMessages.getString("LABEL_EQUIVALENCE_CLASS"), TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.PLAIN, 11), SystemColor.activeCaption));

		}
		return jPanelGeneral;
	}

	/**
	 * This method initializes jPanelEffects
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelEffects() {
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

	/**
	 * This method initializes jPanelTextFieldValue
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelTextFieldValue() {
		if (jPanelTextFieldValue == null) {
			jPanelTextFieldValue = new JPanel();
			jPanelTextFieldValue.setLayout(new BorderLayout());
			jPanelTextFieldValue.setPreferredSize(new Dimension(400, 20));
			jPanelTextFieldValue.add(getJTextFieldValue(), BorderLayout.CENTER);
		}
		return jPanelTextFieldValue;
	}

	/**
	 * This method initializes jPanelText
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelText() {
		if (jPanelText == null) {
			FlowLayout flowLayout2 = new FlowLayout();
			flowLayout2.setVgap(15);
			flowLayout2.setAlignment(FlowLayout.LEFT);
			jPanelText = new JPanel();
			jPanelText.setLayout(flowLayout2);
			jPanelText.add(getJPanelTextFieldValue(), null);
		}
		return jPanelText;
	}

	/**
	 * This method initializes jPanelLabelValue
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelLabelValue() {
		if (jPanelLabelValue == null) {
			FlowLayout flowLayout3 = new FlowLayout();
			flowLayout3.setVgap(15);
			flowLayout3.setAlignment(FlowLayout.RIGHT);
			jPanelLabelValue = new JPanel();
			jPanelLabelValue.setLayout(flowLayout3);
			jPanelLabelValue.add(jLabelValue, null);
		}
		return jPanelLabelValue;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
