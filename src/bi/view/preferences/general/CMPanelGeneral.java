package bi.view.preferences.general;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.SystemColor;

import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import model.BusinessRules;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;

import bi.view.lang.CMMessages;
import javax.swing.BorderFactory;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import com.jidesoft.swing.PartialLineBorder;

public class CMPanelGeneral extends JPanel {

	private static final long serialVersionUID = 1L;
	private JSpinner jSpinnerCombinations = null;
	private JSpinner jSpinnerXMLSize = null;
	private JLabel jLabelSizeXML = null;
	private JLabel jLabelMb = null;
	private JLabel jLabelNumOfCombinations = null;
	private SpinnerNumberModel modelPositiveTestCases = null;  //  @jve:decl-index=0:
	private JSpinner jSpinnerPositiveTestCases = null;
	private JLabel jLabelPositiveTestCases = null;
	private JSpinner jSpinnerNegativeTestCases = null;
	private JLabel jLabelNegativeTestCases = null;
	private JSpinner jSpinnerFaultyTestCases = null;
	private JLabel jLabelFaultyTestCases = null;
	/**
	 * This is the default constructor
	 */
	public CMPanelGeneral() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 *
	 * @return void
	 */
	private void initialize() {
		GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
		gridBagConstraints12.gridx = 0;
		gridBagConstraints12.anchor = GridBagConstraints.EAST;
		gridBagConstraints12.gridy = 4;
		jLabelFaultyTestCases = new JLabel();
		jLabelFaultyTestCases.setText(CMMessages.getString("LABEL_CASEMAKER_SETTINGS_MAX_NUM_OF_FAULTY_TEST_CASES"));
		GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
		gridBagConstraints11.gridx = 1;
		gridBagConstraints11.insets = new Insets(5, 5, 5, 5);
		gridBagConstraints11.gridy = 4;
		GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
		gridBagConstraints10.gridx = 0;
		gridBagConstraints10.anchor = GridBagConstraints.EAST;
		gridBagConstraints10.gridy = 3;
		jLabelNegativeTestCases = new JLabel();
		jLabelNegativeTestCases.setText(CMMessages.getString("LABEL_CASEMAKER_SETTINGS_MAX_NUM_OF_NEGATIVE_TEST_CASES"));
		GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
		gridBagConstraints9.gridx = 1;
		gridBagConstraints9.insets = new Insets(5, 5, 5, 5);
		gridBagConstraints9.gridy = 3;
		GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
		gridBagConstraints8.gridx = 0;
		gridBagConstraints8.anchor = GridBagConstraints.EAST;
		gridBagConstraints8.gridy = 2;
		jLabelPositiveTestCases = new JLabel();
		jLabelPositiveTestCases.setText(CMMessages.getString("LABEL_CASEMAKER_SETTINGS_MAX_NUM_OF_POSITIVE_TEST_CASES"));
		GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
		gridBagConstraints7.gridx = 1;
		gridBagConstraints7.gridy = 2;
		GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
		gridBagConstraints6.gridx = 1;
		gridBagConstraints6.insets = new Insets(5, 5, 5, 5);
		gridBagConstraints6.gridy = 2;
		GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
		gridBagConstraints5.gridx = 0;
		gridBagConstraints5.fill = GridBagConstraints.NONE;
		gridBagConstraints5.anchor = GridBagConstraints.EAST;
		gridBagConstraints5.gridy = 1;
		jLabelNumOfCombinations = new JLabel();
		jLabelNumOfCombinations.setText(CMMessages.getString("LABEL_CASEMAKER_SETTINGS_MAX_NUM_OF_COMBINATIONS"));
		GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
		gridBagConstraints4.gridx = 3;
		gridBagConstraints4.anchor = GridBagConstraints.WEST;
		gridBagConstraints4.gridy = 0;
		jLabelMb = new JLabel();
		jLabelMb.setText("Mb");
		GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
		gridBagConstraints3.gridx = 0;
		gridBagConstraints3.fill = GridBagConstraints.NONE;
		gridBagConstraints3.anchor = GridBagConstraints.EAST;
		gridBagConstraints3.gridy = 0;
		jLabelSizeXML = new JLabel();
		jLabelSizeXML.setText(CMMessages.getString("LABEL_MAX_XML_SIZE"));
		GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
		gridBagConstraints2.gridx = 1;
		gridBagConstraints2.insets = new Insets(5, 5, 5, 5);
		gridBagConstraints2.gridy = 0;
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.insets = new Insets(5, 5, 5, 5);
		gridBagConstraints.gridy = 1;
		this.setSize(497, 200);
		this.setLayout(new GridBagLayout());
		this.setBorder(BorderFactory.createTitledBorder(new PartialLineBorder(Color.gray, 1, true), "", TitledBorder.LEADING, TitledBorder.ABOVE_TOP, new Font("Dialog", Font.PLAIN, 11), SystemColor.activeCaption));
		this.add(getJSpinnerCombinations(), gridBagConstraints);
		this.add(getJSpinnerXMLSize(), gridBagConstraints2);
		this.add(jLabelSizeXML, gridBagConstraints3);
		this.add(jLabelMb, gridBagConstraints4);
		this.add(jLabelNumOfCombinations, gridBagConstraints5);

		this.add(getJSpinnerPositiveTestCases(), gridBagConstraints6);
		this.add(jLabelPositiveTestCases, gridBagConstraints8);
		this.add(getJSpinnerNegativeTestCases(), gridBagConstraints9);
		this.add(jLabelNegativeTestCases, gridBagConstraints10);
		this.add(getJSpinnerFaultyTestCases(), gridBagConstraints11);
		this.add(jLabelFaultyTestCases, gridBagConstraints12);
	}

	/**
	 * This method initializes jSpinnerCombinations
	 *
	 * @return javax.swing.JSpinner
	 */
	public JSpinner getJSpinnerCombinations() {
		if (jSpinnerCombinations == null) {
			SpinnerModel modelCombinations = new SpinnerNumberModel(BusinessRules.MIN_NUMBER_OF_COMBINATIONS, BusinessRules.MIN_NUMBER_OF_COMBINATIONS, BusinessRules.MAX_NUMBER_OF_COMBINATIONS, 1);
			jSpinnerCombinations = new JSpinner(modelCombinations);
			 jSpinnerCombinations.setPreferredSize(new Dimension(55,18));


		}
		return jSpinnerCombinations;
	}

	/**
	 * This method initializes jSpinnerXMLSize
	 *
	 * @return javax.swing.JSpinner
	 */
	public JSpinner getJSpinnerXMLSize() {
		if (jSpinnerXMLSize == null) {
	     	SpinnerModel modelMaxSizeXML = new SpinnerNumberModel(BusinessRules.MIN_SIZE_OF_XML_FILE, BusinessRules.MIN_SIZE_OF_XML_FILE, BusinessRules.MAX_SIZE_OF_XML_FILE, 1);
			jSpinnerXMLSize = new JSpinner(modelMaxSizeXML);
			jSpinnerXMLSize.setPreferredSize(new Dimension(55,18));

		}
		return jSpinnerXMLSize;
	}

	/**
	 * This method initializes modelPositiveTestCases
	 *
	 * @return javax.swing.SpinnerNumberModel
	 */
	private SpinnerNumberModel getModelPositiveTestCases() {
		if (modelPositiveTestCases == null) {
			modelPositiveTestCases = new SpinnerNumberModel(BusinessRules.MIN_NUMBER_OF_POSITIVE_TEST_CASES, BusinessRules.MIN_NUMBER_OF_POSITIVE_TEST_CASES, BusinessRules.MAX_NUMBER_OF_POSITIVE_TEST_CASES, 1);
		}
		return modelPositiveTestCases;
	}

	/**
	 * This method initializes jSpinnerPositiveTestCases
	 *
	 * @return javax.swing.JSpinner
	 */
	JSpinner getJSpinnerPositiveTestCases() {
		if (jSpinnerPositiveTestCases == null) {
			jSpinnerPositiveTestCases = new JSpinner(getModelPositiveTestCases());
			jSpinnerPositiveTestCases.setPreferredSize(new Dimension(55, 18));
		}
		return jSpinnerPositiveTestCases;
	}

	/**
	 * This method initializes jSpinnerPositiveTestCases
	 *
	 * @return javax.swing.JSpinner
	 */
	

	/**
	 * This method initializes jSpinnerNegativeTestCases
	 *
	 * @return javax.swing.JSpinner
	 */
	JSpinner getJSpinnerNegativeTestCases() {
		if (jSpinnerNegativeTestCases == null) {
			 SpinnerModel modelNegativeTestCases = new SpinnerNumberModel(BusinessRules.MIN_NUMBER_OF_NEGATIVE_TEST_CASES, BusinessRules.MIN_NUMBER_OF_NEGATIVE_TEST_CASES, BusinessRules.MAX_NUMBER_OF_NEGATIVE_TEST_CASES, 1);
			jSpinnerNegativeTestCases = new JSpinner(modelNegativeTestCases);


		}
		return jSpinnerNegativeTestCases;
	}

	/**
	 * This method initializes jSpinnerFaultyTestCases
	 *
	 * @return javax.swing.JSpinner
	 */
	JSpinner getJSpinnerFaultyTestCases() {
		if (jSpinnerFaultyTestCases == null) {
			  SpinnerModel modelFaultyTestCases = new SpinnerNumberModel(BusinessRules.MIN_NUMBER_OF_FAULTY_TEST_CASES, BusinessRules.MIN_NUMBER_OF_FAULTY_TEST_CASES, BusinessRules.MAX_NUMBER_OF_FAULTY_TEST_CASES, 1);
			jSpinnerFaultyTestCases = new JSpinner(modelFaultyTestCases);
		}
		return jSpinnerFaultyTestCases;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
