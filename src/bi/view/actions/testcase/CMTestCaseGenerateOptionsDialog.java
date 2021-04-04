/*******************************************************************************
Developed by BUSINESS SOFTWARE INNOVATIONS. .
Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
*******************************************************************************/


package  bi.view.actions.testcase;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.utils.CMBaseJDialog;
import bi.view.utils.CMCheckBoxJList;
import bi.view.utils.CMOkCancelPanel;

public class CMTestCaseGenerateOptionsDialog extends CMBaseJDialog {
    private JPanel containerPanel;
	private JPanel jPanelTestCasesOptions = null;
	private CMOkCancelPanel okCancelPanel = null;
	private JPanel jPanelDependencyList = null;
	private JScrollPane jScrollPaneDependencies = null;
	private CMCheckBoxJList jListDependencies = null;
	private JComboBox jComboBoxTestCaseOptions = null;
	private DefaultComboBoxModel comboBoxModelTestCaseOptions = null;  //  @jve:decl-index=0:visual-constraint=""
	private JCheckBox jCheckBoxOneWiseGen = null;
	private JPanel jPanelComboTestCases = null;
	private DefaultListModel listModelDependencies = null;  //  @jve:decl-index=0:visual-constraint=""
	private JPanel jPanelSelectAll = null;
	private JCheckBox jCheckBoxSelectAll = null;
	private JPanel jPanelCheckBoxTestCaseOptions = null;
	private DefaultListSelectionModel listSelectionModelDependency = null;  //  @jve:decl-index=0:visual-constraint=""
	/** Creates new form JDialog */
    public CMTestCaseGenerateOptionsDialog() {
        super(CMApplication.frame);
        initGUI();
        this.addKeyListenertoAll(this,this.getDefaultKeyListener());

    }
/** This method is called from within the constructor to initialize the form. */
    private void initGUI() {
        this.setSize(new java.awt.Dimension(366,313));

        this.setModal(true);
        getContainerPanel();
        this.setContentPane(containerPanel);
    }
	protected List getOrder() {

		List<Component> componentList = new ArrayList<Component>(0);
		componentList.add(this.getJComboBoxTestCaseOptions());
		componentList.add(this.getJCheckBoxOneWiseGen());
		componentList.add(this.getJCheckBoxSelectAll());
		if (getJListDependencies().getModel().getSize()>0)
		  componentList.add(getJListDependencies());
		componentList.add(this.getOkCancelPanel().getJButtonOk());
		componentList.add(this.getOkCancelPanel().getJButtonCancel());

		return componentList;
	}

	protected void fireButtonOk() {

		this.getOkCancelPanel().getJButtonOk().doClick();

	}

	protected void fireButtonCancel() {

		this.getOkCancelPanel().getJButtonCancel().doClick();

	}

	public JButton getDefaultButton() {
		return this.getOkCancelPanel().getJButtonOk();
	}








	private JPanel getContainerPanel() {
		if (this.containerPanel== null)
			containerPanel = new JPanel();
			containerPanel.setLayout(new BorderLayout());
			containerPanel.add(getJPanelTestCasesOptions(), java.awt.BorderLayout.NORTH);
			containerPanel.add(getOkCancelPanel(), java.awt.BorderLayout.SOUTH);
			containerPanel.add(getJPanelDependencyList(), java.awt.BorderLayout.CENTER);
		return this.containerPanel;
	}
	/**
	 * This method initializes jPanelTestCasesOptions
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelTestCasesOptions() {
		if (jPanelTestCasesOptions == null) {
			jPanelTestCasesOptions = new JPanel();
			jPanelTestCasesOptions.setLayout(new BorderLayout());
			jPanelTestCasesOptions.setPreferredSize(new java.awt.Dimension(10,90));
			jPanelTestCasesOptions.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED), CMMessages.getString("QUESTION_WHICH_TEST_CASES"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11), new java.awt.Color(0,70,213))); //$NON-NLS-1$ //$NON-NLS-2$
			jPanelTestCasesOptions.add(getJPanelComboTestCases(), java.awt.BorderLayout.CENTER);
			jPanelTestCasesOptions.add(getJPanelCheckBoxTestCaseOptions(), java.awt.BorderLayout.SOUTH);
		}
		return jPanelTestCasesOptions;
	}




	/**
	 * This method initializes okCancelPanel
	 *
	 * @return bi.view.utils.CMOkCancelPanel
	 */
	private CMOkCancelPanel getOkCancelPanel() {
		if (okCancelPanel == null) {
			okCancelPanel = new CMOkCancelPanel(this);
		}
		return okCancelPanel;
	}




	/**
	 * This method initializes jPanel
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelDependencyList() {
		if (jPanelDependencyList == null) {
			jPanelDependencyList = new JPanel();
			jPanelDependencyList.setLayout(new BorderLayout());
			jPanelDependencyList.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED), CMMessages.getString("QUESTION_FROM_WHICH_DEPENDENCIES"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null)); //$NON-NLS-1$
			jPanelDependencyList.add(getJScrollPaneDependencies(), java.awt.BorderLayout.CENTER);
			jPanelDependencyList.add(getJPanelSelectAll(), java.awt.BorderLayout.NORTH);
		}
		return jPanelDependencyList;
	}




	/**
	 * This method initializes jScrollPaneDependencies
	 *
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPaneDependencies() {
		if (jScrollPaneDependencies == null) {
			jScrollPaneDependencies = new JScrollPane();
			jScrollPaneDependencies.setPreferredSize(new java.awt.Dimension(258,120));
			jScrollPaneDependencies.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.black,1));
			jScrollPaneDependencies.setViewportView(getJListDependencies());
		}
		return jScrollPaneDependencies;
	}




	/**
	 * This method initializes jListDependencies
	 *
	 * @return javax.swing.JList
	 */
	public CMCheckBoxJList getJListDependencies() {
		if (jListDependencies == null) {
			jListDependencies = new CMCheckBoxJList();
			jListDependencies.setModel(getListModelDependencies());
			jListDependencies.setSelectionModel(getListSelectionModelDependency());
			jListDependencies
					.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
						public void valueChanged(javax.swing.event.ListSelectionEvent e) {
							//check if not all elements are selected to unset the "select all" checkbox
							jCheckBoxSelectAll.setSelected( ((jListDependencies.getSelectionModel().getMinSelectionIndex() == 0)
								&&(jListDependencies.getSelectionModel().getMaxSelectionIndex() == jListDependencies.getModel().getSize())));

						}
					});
		}
		return jListDependencies;
	}




	/**
	 * This method initializes jComboBoxTestCaseOptions
	 *
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBoxTestCaseOptions() {
		if (jComboBoxTestCaseOptions == null) {
			jComboBoxTestCaseOptions = new JComboBox();
			jComboBoxTestCaseOptions.setPreferredSize(new java.awt.Dimension(230,22));
			jComboBoxTestCaseOptions.setModel(getComboBoxModelTestCaseOptions());
		}
		return jComboBoxTestCaseOptions;
	}




	/**
	 * This method initializes comboBoxModelTestCaseOptions
	 *
	 * @return javax.swing.DefaultComboBoxModel
	 */
	public DefaultComboBoxModel getComboBoxModelTestCaseOptions() {
		if (comboBoxModelTestCaseOptions == null) {
			comboBoxModelTestCaseOptions = new DefaultComboBoxModel();
		}
		return comboBoxModelTestCaseOptions;
	}




	/**
	 * This method initializes jCheckBoxOneWiseGen
	 *
	 * @return javax.swing.JCheckBox
	 */
	public JCheckBox getJCheckBoxOneWiseGen() {
		if (jCheckBoxOneWiseGen == null) {
			jCheckBoxOneWiseGen = new JCheckBox();
			jCheckBoxOneWiseGen.setText(CMMessages.getString("TESTCASE_GENERATION_CHECKBOX_LABEL")); //$NON-NLS-1$
			jCheckBoxOneWiseGen.setSelected(true);
		}
		return jCheckBoxOneWiseGen;
	}




	/**
	 * This method initializes jPanelComboTestCases
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelComboTestCases() {
		if (jPanelComboTestCases == null) {
			jPanelComboTestCases = new JPanel();
			jPanelComboTestCases.setPreferredSize(new java.awt.Dimension(230,20));
			jPanelComboTestCases.setLayout(new BorderLayout());
			jPanelComboTestCases.add(getJComboBoxTestCaseOptions(), java.awt.BorderLayout.CENTER);
		}
		return jPanelComboTestCases;
	}




	/**
	 * This method initializes listModelDependencies
	 *
	 * @return javax.swing.DefaultListModel
	 */
	public DefaultListModel getListModelDependencies() {
		if (listModelDependencies == null) {
			listModelDependencies = new DefaultListModel();
			//only for testing purposes
			//listModelDependencies.addElement("Unico");
			//listModelDependencies.addElement("Donico");
		}
		return listModelDependencies;
	}





	/**
	 * This method initializes jPanelSelectAll
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelSelectAll() {
		if (jPanelSelectAll == null) {
			jPanelSelectAll = new JPanel();
			jPanelSelectAll.setMinimumSize(new java.awt.Dimension(79,30));
			jPanelSelectAll.setBackground(java.awt.SystemColor.text);
			jPanelSelectAll.setBorder(javax.swing.BorderFactory.createMatteBorder(1,1,0,1,java.awt.Color.black));
			jPanelSelectAll.setLayout(new BorderLayout());
			jPanelSelectAll.setPreferredSize(new java.awt.Dimension(10,25));
			jPanelSelectAll.add(getJCheckBoxSelectAll(), java.awt.BorderLayout.NORTH);
		}
		return jPanelSelectAll;
	}




	/**
	 * This method initializes jCheckBoxSelectAll
	 *
	 * @return javax.swing.JCheckBox
	 */
	public JCheckBox getJCheckBoxSelectAll() {
		if (jCheckBoxSelectAll == null) {
			jCheckBoxSelectAll = new JCheckBox();
			jCheckBoxSelectAll.setText(CMMessages.getString("BUTTON_TOOLTIP_SELECT_ALL")); //$NON-NLS-1$

			jCheckBoxSelectAll.setPreferredSize(new java.awt.Dimension(69,20));
			jCheckBoxSelectAll.setFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11));
			jCheckBoxSelectAll.setForeground(java.awt.SystemColor.activeCaption);
			jCheckBoxSelectAll.setBackground(java.awt.SystemColor.text);
			jCheckBoxSelectAll.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getJListDependencies().getSelectionModel().clearSelection();
					((CMCheckBoxJList)getJListDependencies()).setSelectionCache(new HashSet());
					if (jCheckBoxSelectAll.isSelected())
						getJListDependencies().getSelectionModel().setSelectionInterval(0,getJListDependencies().getModel().getSize());
					else
					{
						//remove and add all the items
						Object[] objects = getListModelDependencies().toArray();
						getListModelDependencies().clear();
						for (Object ob : Arrays.asList(objects))
							getListModelDependencies().addElement(ob);
					}

				getJListDependencies().repaint();
				}
			});

		}
		return jCheckBoxSelectAll;
	}




	/**
	 * This method initializes jPanelCheckBoxTestCaseOptions
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelCheckBoxTestCaseOptions() {
		if (jPanelCheckBoxTestCaseOptions == null) {
			FlowLayout flowLayout1 = new FlowLayout();
			flowLayout1.setAlignment(java.awt.FlowLayout.LEFT);
			flowLayout1.setVgap(8);
			flowLayout1.setHgap(1);
			jPanelCheckBoxTestCaseOptions = new JPanel();
			jPanelCheckBoxTestCaseOptions.setLayout(flowLayout1);
			jPanelCheckBoxTestCaseOptions.setPreferredSize(new java.awt.Dimension(265,42));
			jPanelCheckBoxTestCaseOptions.add(getJCheckBoxOneWiseGen(), null);
		}
		return jPanelCheckBoxTestCaseOptions;
	}




	/**
	 * This method initializes listSelectionModel
	 *
	 * @return javax.swing.DefaultListSelectionModel
	 */
	private DefaultListSelectionModel getListSelectionModelDependency() {
		if (listSelectionModelDependency == null) {
			listSelectionModelDependency = new DefaultListSelectionModel();
		}
		return listSelectionModelDependency;
	}






}  //  @jve:decl-index=0:visual-constraint="26,12"
