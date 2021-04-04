package bi.view.testdataviews;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.mainframeviews.CMFrameView;
import bi.view.utils.JCustomDialog;




public class CMDialogIntervalValuesChooser extends JCustomDialog {

	protected static final int AMOUNT_TIPICAL = 2;
	protected static final int AMOUNT_PARTITION = 3;
	protected static final int AMOUNT_BOUNDARY = 5;
	protected static final int AMOUNT_ONE = 1;
	protected static final int AMOUNT_ALL = 0;
	protected static int AMOUNT_RANDOM = 0;
	private JPanel jPanelContent = null;
	private JPanel jPanelButtons = null;
	private JButton jButtonOk = null;
	private JButton jButtonCancel = null;
	private JPanel jPanelDialogContent = null;
	private JPanel jPanelRadioButtons = null;
	private JRadioButton jRadioButtonTipical = null;
	private JRadioButton jRadioButtonPartition = null;
	private JRadioButton jRadioButtonBoundary = null;
	private JRadioButton jRadioButtonOne = null;
	private JRadioButton jRadioButtonAll = null;
	private JRadioButton jRadioButtonRandom = null;
	private JPanel jPanelDescription = null;
	private JTextPane jTextPaneDescription = null;
	private ButtonGroup groupRadioButtons = null;
	private int amountOfIntervalValues=AMOUNT_TIPICAL;
	private String selectDescription=new String();
	private boolean okSelected=false;
	private JTextField jTextAreaRandomNumber = null;
	/**
	 * @return Returns the groupRadioButtons.
	 */
	private ButtonGroup getGroupRadioButtons() {
		if(groupRadioButtons == null)
			groupRadioButtons= new ButtonGroup();
		return groupRadioButtons;
	}

	/**
	 * This method initializes
	 *
	 */
	public CMDialogIntervalValuesChooser(CMFrameView frame) {
		super(frame);
		initialize();
	}

	/**
	 * This method initializes this
	 *
	 */
	private void initialize() {
        this.setSize(new java.awt.Dimension(309,320));//244
        this.setTitle(CMApplication.frame.getTreeWorkspaceView().getCurrentTestObject().getName() + " - " +
        		CMMessages.getString("INTERVAL_VALUES_TITLE"));
        this.setResizable(false);
        this.setModal(true);
        this.setContentPane(getJPanelContent());
        selectDescription=CMMessages.getString("INTERVAL_VALUES_TIPICAL_DESCRIPTION");
		getJTextPaneDescription().setText(selectDescription);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension dlgSize = this.getPreferredSize();
        this.setLocation((screenSize.width - dlgSize.width) / 2 ,
            (screenSize.height - dlgSize.height) / 2 );
        jTextAreaRandomNumber.setVisible(false);


	}

	@SuppressWarnings("unchecked")
	protected List getOrder() {
		List focusOrder= new ArrayList();
		focusOrder.add(getJRadioButtonTipical());
		focusOrder.add(getJRadioButtonPartition());
		//focusOrder.add(getJRadioButtonBoundary());
		focusOrder.add(getJradioButtonAll());
		focusOrder.add(getJradioButtonRandom());
		focusOrder.add(jTextAreaRandomNumber);
		focusOrder.add(getJButtonOk());
		focusOrder.add(getJButtonCancel());
		focusOrder.add(getJradioButtonOne());
		return focusOrder;
	}

	protected void PressJButtonCancel(Object object) {
		jButtonCancelActionPerformed(null);
	}

	protected void PressJButtonOk(Object object) {
		jButtonOkActionPerformed(null);
	}

	/**
	 * This method initializes jPanelContent
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelContent() {
		if (jPanelContent == null) {
			jPanelContent = new JPanel();
			jPanelContent.setLayout(new BorderLayout());
			jPanelContent.add(getJPanelButtons(), java.awt.BorderLayout.SOUTH);
			jPanelContent.add(getJPanelRadioButtons(), java.awt.BorderLayout.CENTER);
		}
		return jPanelContent;
	}

	/**
	 * This method initializes jPanelButtons
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelButtons() {
		if (jPanelButtons == null) {
			jPanelButtons = new JPanel();
			jPanelButtons.add(getJButtonOk(), null);
			jPanelButtons.add(getJButtonCancel(), null);
		}
		return jPanelButtons;
	}

	/**
	 * This method initializes jButtonOk
	 *
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonOk() {
		if (jButtonOk == null) {
			jButtonOk = new JButton();
			jButtonOk.setText(CMMessages.getString("BUTTON_OK")); //$NON-NLS-1$
			jButtonOk.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					jButtonOkActionPerformed(e);
				}


			});
		}
		return jButtonOk;
	}

	protected void jButtonOkActionPerformed(ActionEvent e) {
		if(jRadioButtonRandom.isSelected()){
			try {
				if(!jTextAreaRandomNumber.getText().equalsIgnoreCase("")){
					int i = Integer.parseInt(jTextAreaRandomNumber.getText().trim());
					AMOUNT_RANDOM =i;
					setAmountOfIntervalValues(AMOUNT_RANDOM);
					setOkSelected(true);
					dispose();}
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(CMApplication.frame,CMMessages.getString("INTERVAL_MESSAGE_ERROR_NOTNUMERIC"),
						CMMessages.getString("INTERVAL_MESSAGE_ERROR_TITLEDIALOG"),JOptionPane.OK_OPTION);
			}
		}
		else{
		setOkSelected(true);
		dispose();}

	}

	/**
	 * This method initializes jButtonCancel
	 *
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonCancel() {
		if (jButtonCancel == null) {
			jButtonCancel = new JButton();
			jButtonCancel.setText(CMMessages.getString("BUTTON_CANCEL")); //$NON-NLS-1$
			jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					jButtonCancelActionPerformed(e);
				}
			});
		}
		return jButtonCancel;
	}

	protected void jButtonCancelActionPerformed(ActionEvent e) {
		dispose();
		setOkSelected(false);
	}

	/**
	 * This method initializes jPanelRadioButtons
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelRadioButtons() {
		if (jPanelDialogContent == null) {
			jPanelDialogContent = new JPanel();
			jPanelDialogContent.setLayout(new BorderLayout());
			jPanelDialogContent.add(getJPanelRadioButtons2(), java.awt.BorderLayout.CENTER);
		}
		return jPanelDialogContent;
	}

	/**
	 * This method initializes jPanelRadioButtons
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelRadioButtons2() {
		if (jPanelRadioButtons == null) {
			TitledBorder titledBorder = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), CMMessages.getString("INTERVAL_VALUES_SUBTITLE"), TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, null, null);
			titledBorder.setTitleColor(java.awt.Color.black);
			titledBorder.setTitleFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
			gridBagConstraints.gridheight = 1;
			gridBagConstraints.gridwidth = 2;
			gridBagConstraints.gridy = 6;
			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			gridBagConstraints7.gridx = 1;
			gridBagConstraints7.anchor = java.awt.GridBagConstraints.EAST;
			gridBagConstraints7.ipadx = 2;
			gridBagConstraints7.ipady = 1;
			gridBagConstraints7.gridy = 4;
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.gridx = 1;
			gridBagConstraints6.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints6.ipadx = 1;
			gridBagConstraints6.ipady = 1;
			gridBagConstraints6.gridy = 5;
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.gridx = 1;
			gridBagConstraints5.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints5.ipadx = 1;
			gridBagConstraints5.ipady = 1;
			gridBagConstraints5.gridy = 4;
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridx = 1;
			gridBagConstraints4.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints4.ipadx = 1;
			gridBagConstraints4.ipady = 1;
			gridBagConstraints4.gridy = 3;
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.gridx = 1;
			gridBagConstraints3.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints3.ipadx = 1;
			gridBagConstraints3.ipady = 1;
			gridBagConstraints3.gridy = 2;
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridx = 1;
			gridBagConstraints2.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints2.ipadx = 1;
			gridBagConstraints2.ipady = 1;
			gridBagConstraints2.gridy = 1;
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 1;
			gridBagConstraints1.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints1.weightx = 0.0D;
			gridBagConstraints1.ipadx = 1;
			gridBagConstraints1.ipady = 1;
			gridBagConstraints1.gridy = 0;
			jPanelRadioButtons = new JPanel();
			jPanelRadioButtons.setLayout(new GridBagLayout());
			jPanelRadioButtons.setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 12));
			jPanelRadioButtons.setBorder(titledBorder);
			jPanelRadioButtons.add(getJradioButtonOne(), gridBagConstraints1);
			jPanelRadioButtons.add(getJRadioButtonTipical(), gridBagConstraints2);
			jPanelRadioButtons.add(getJRadioButtonPartition(), gridBagConstraints3);
			jPanelRadioButtons.add(getJradioButtonAll(),gridBagConstraints4);
			jPanelRadioButtons.add(getJradioButtonRandom(),gridBagConstraints5);
			jPanelRadioButtons.add(jTextAreaRandomNumber,gridBagConstraints7);
			jPanelRadioButtons.add(getJPanelDescription(), gridBagConstraints);
			getGroupRadioButtons().add(getJradioButtonOne());
			getGroupRadioButtons().add(getJRadioButtonTipical());
			getGroupRadioButtons().add(getJRadioButtonPartition());
			getGroupRadioButtons().add(getJradioButtonAll());
			getGroupRadioButtons().add(getJradioButtonRandom());
		}
		return jPanelRadioButtons;
	}

	private JRadioButton getJradioButtonRandom(){
		if(jRadioButtonRandom == null){
			jTextAreaRandomNumber = new JTextField();
			jTextAreaRandomNumber.setPreferredSize(new Dimension(60,20));
			jRadioButtonRandom = new JRadioButton();
			selectDescription = CMMessages.getString("INTERVAL_VALUES_RANDOM_DESCRIPTION");
			getJTextPaneDescription().setText(selectDescription);
			jRadioButtonRandom.setText(CMMessages.getString("INTERVAL_VALUES_RADIO_RANDOM_LABEL"));
			jRadioButtonRandom.setFont(new java.awt.Font("SansSerif", java.awt.Font.PLAIN, 12));
			jRadioButtonRandom.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setAmountOfIntervalValues(AMOUNT_RANDOM);
					selectDescription=CMMessages.getString("INTERVAL_VALUES_RANDOM_DESCRIPTION");
				}
			});

			jRadioButtonRandom.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseEntered(java.awt.event.MouseEvent e) {
					getJTextPaneDescription().setText(CMMessages.getString("INTERVAL_VALUES_RANDOM_DESCRIPTION")); //$NON-NLS-1$
				}
				public void mouseExited(java.awt.event.MouseEvent e) {
					getJTextPaneDescription().setText(selectDescription);
				}
			});

			jRadioButtonRandom.addFocusListener(new java.awt.event.FocusAdapter() {
				public void focusLost(java.awt.event.FocusEvent e) {
					getJTextPaneDescription().setText(selectDescription);
				}
				public void focusGained(java.awt.event.FocusEvent e) {
					getJTextPaneDescription().setText(CMMessages.getString("INTERVAL_VALUES_RANDOM_DESCRIPTION")); //$NON-NLS-1$
					//jTextAreaRandomNumber.setPreferredSize(new Dimension(60,20));
					jTextAreaRandomNumber.setVisible(true);
				}

			});

		}
		return jRadioButtonRandom;
	}


	private JRadioButton getJradioButtonAll(){
		if(jRadioButtonAll == null){
			jRadioButtonAll = new JRadioButton();
			selectDescription = CMMessages.getString("INTERVAL_VALUES_RADIO_ALL_LABEL");
			getJTextPaneDescription().setText(selectDescription);
			jRadioButtonAll.setText(CMMessages.getString("INTERVAL_VALUES_ALL_DESCRIPTION"));
			jRadioButtonAll.setFont(new java.awt.Font("SansSerif", java.awt.Font.PLAIN, 12));
			jRadioButtonAll.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setAmountOfIntervalValues(AMOUNT_ALL);
					selectDescription=CMMessages.getString("INTERVAL_VALUES_RADIO_ALL_LABEL");
				}
			});

			jRadioButtonAll.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseEntered(java.awt.event.MouseEvent e) {
					getJTextPaneDescription().setText(CMMessages.getString("INTERVAL_VALUES_RADIO_ALL_LABEL")); //$NON-NLS-1$
				}
				public void mouseExited(java.awt.event.MouseEvent e) {
					getJTextPaneDescription().setText(selectDescription);
				}
			});

			jRadioButtonAll.addFocusListener(new java.awt.event.FocusAdapter() {
				public void focusLost(java.awt.event.FocusEvent e) {
					getJTextPaneDescription().setText(selectDescription);
				}
				public void focusGained(java.awt.event.FocusEvent e) {
					getJTextPaneDescription().setText(CMMessages.getString("INTERVAL_VALUES_RADIO_ALL_LABEL")); //$NON-NLS-1$
					jTextAreaRandomNumber.setVisible(false);
				}
			});

		}
		return jRadioButtonAll;
	}

	private JRadioButton getJradioButtonOne(){
		if(jRadioButtonOne == null){
			jRadioButtonOne = new JRadioButton();
			selectDescription = CMMessages.getString("INTERVAL_VALUES_ONE_DESCRIPTION");
			getJTextPaneDescription().setText(selectDescription);
			jRadioButtonOne.setText(CMMessages.getString("INTERVAL_VALUES_RADIO_ONE_LABEL"));
			jRadioButtonOne.setFont(new java.awt.Font("SansSerif", java.awt.Font.PLAIN, 12));
			jRadioButtonOne.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setAmountOfIntervalValues(AMOUNT_ONE);
					selectDescription=CMMessages.getString("INTERVAL_VALUES_ONE_DESCRIPTION");
				}
			});

			jRadioButtonOne.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseEntered(java.awt.event.MouseEvent e) {
					getJTextPaneDescription().setText(CMMessages.getString("INTERVAL_VALUES_ONE_DESCRIPTION")); //$NON-NLS-1$
				}
				public void mouseExited(java.awt.event.MouseEvent e) {
					getJTextPaneDescription().setText(selectDescription);
				}
			});

			jRadioButtonOne.addFocusListener(new java.awt.event.FocusAdapter() {
				public void focusLost(java.awt.event.FocusEvent e) {
					getJTextPaneDescription().setText(selectDescription);
				}
				public void focusGained(java.awt.event.FocusEvent e) {
					getJTextPaneDescription().setText(CMMessages.getString("INTERVAL_VALUES_ONE_DESCRIPTION")); //$NON-NLS-1$
					jTextAreaRandomNumber.setVisible(false);
				}
			});

		}
		return jRadioButtonOne;
	}
	/**
	 * This method initializes jRadioButton1
	 *
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButtonTipical() {
		if (jRadioButtonTipical == null) {
			jRadioButtonTipical = new JRadioButton();
			selectDescription=CMMessages.getString("INTERVAL_VALUES_TIPICAL_DESCRIPTION");
			getJTextPaneDescription().setText(selectDescription);
			jRadioButtonTipical.setSelected(true);
			jRadioButtonTipical.setText(CMMessages.getString("INTERVAL_VALUES_RADIO_TIPICAL_LABEL")); //$NON-NLS-1$
			jRadioButtonTipical.setFont(new java.awt.Font("SansSerif", java.awt.Font.PLAIN, 12));
			jRadioButtonTipical.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setAmountOfIntervalValues(AMOUNT_TIPICAL);
					selectDescription=CMMessages.getString("INTERVAL_VALUES_TIPICAL_DESCRIPTION");
				}
			});
			jRadioButtonTipical.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseEntered(java.awt.event.MouseEvent e) {
					getJTextPaneDescription().setText(CMMessages.getString("INTERVAL_VALUES_TIPICAL_DESCRIPTION")); //$NON-NLS-1$
				}
				public void mouseExited(java.awt.event.MouseEvent e) {
					getJTextPaneDescription().setText(selectDescription);
				}
			});
			jRadioButtonTipical.addFocusListener(new java.awt.event.FocusAdapter() {
				public void focusLost(java.awt.event.FocusEvent e) {
					getJTextPaneDescription().setText(selectDescription);
				}
				public void focusGained(java.awt.event.FocusEvent e) {
					getJTextPaneDescription().setText(CMMessages.getString("INTERVAL_VALUES_TIPICAL_DESCRIPTION")); //$NON-NLS-1$
					jTextAreaRandomNumber.setVisible(false);
				}
			});
		}
		return jRadioButtonTipical;
	}

	/**
	 * This method initializes jRadioButton2
	 *
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButtonPartition() {
		if (jRadioButtonPartition == null) {
			jRadioButtonPartition = new JRadioButton();
			jRadioButtonPartition.setText(CMMessages.getString("INTERVAL_VALUES_RADIO_PARTITION_LABEL")); //$NON-NLS-1$
			jRadioButtonPartition.setFont(new java.awt.Font("SansSerif", java.awt.Font.PLAIN, 12));
			jRadioButtonPartition.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setAmountOfIntervalValues(AMOUNT_PARTITION);
					selectDescription=CMMessages.getString("INTERVAL_VALUES_PARITION_DESCRIPTION"); //$NON-NLS-1$
				}
			});
			jRadioButtonPartition.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseEntered(java.awt.event.MouseEvent e) {
					getJTextPaneDescription().setText(CMMessages.getString("INTERVAL_VALUES_PARITION_DESCRIPTION")); //$NON-NLS-1$
				}
				public void mouseExited(java.awt.event.MouseEvent e) {
					getJTextPaneDescription().setText(selectDescription);
				}
			});
			jRadioButtonPartition.addFocusListener(new java.awt.event.FocusAdapter() {
				public void focusLost(java.awt.event.FocusEvent e) {
					getJTextPaneDescription().setText(selectDescription);
				}
				public void focusGained(java.awt.event.FocusEvent e) {
					getJTextPaneDescription().setText(CMMessages.getString("INTERVAL_VALUES_PARITION_DESCRIPTION")); //$NON-NLS-1$
					jTextAreaRandomNumber.setVisible(false);
				}
			});
		}
		return jRadioButtonPartition;
	}

	/**
	 * This method initializes jRadioButton3
	 *
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButtonBoundary
	() {
		if (jRadioButtonBoundary == null) {
			jRadioButtonBoundary = new JRadioButton();
			jRadioButtonBoundary.setText(CMMessages.getString("INTERVAL_VALUES_RADIO_BONDARY_CHECK_LABEL")); //$NON-NLS-1$
			jRadioButtonBoundary.setFont(new java.awt.Font("SansSerif", java.awt.Font.PLAIN, 12));
			jRadioButtonBoundary.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setAmountOfIntervalValues(AMOUNT_BOUNDARY);
					selectDescription=CMMessages.getString("INTERVAL_VALUES_BOUNDARY_CHECK_DESCRIPTION"); //$NON-NLS-1$
				}
			});
			jRadioButtonBoundary.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseExited(java.awt.event.MouseEvent e) {
					getJTextPaneDescription().setText(selectDescription);
				}
				public void mouseEntered(java.awt.event.MouseEvent e) {
					getJTextPaneDescription().setText(CMMessages.getString("INTERVAL_VALUES_BOUNDARY_CHECK_DESCRIPTION")); //$NON-NLS-1$
				}
			});
			jRadioButtonBoundary.addFocusListener(new java.awt.event.FocusAdapter() {
				public void focusLost(java.awt.event.FocusEvent e) {
					getJTextPaneDescription().setText(selectDescription);
				}
				public void focusGained(java.awt.event.FocusEvent e) {
					getJTextPaneDescription().setText(CMMessages.getString("INTERVAL_VALUES_BOUNDARY_CHECK_DESCRIPTION")); //$NON-NLS-1$
					jTextAreaRandomNumber.setVisible(false);
				}
			});
		}
		return jRadioButtonBoundary;
	}

	/**
	 * This method initializes jPanelDescription
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelDescription() {
		if (jPanelDescription == null) {
			jPanelDescription = new JPanel();
			jPanelDescription.setLayout(new BorderLayout());
			jPanelDescription.setBorder(javax.swing.BorderFactory.createCompoundBorder(null, null));
			jPanelDescription.setPreferredSize(new java.awt.Dimension(285,65));
			jPanelDescription.add(getJTextPaneDescription(), java.awt.BorderLayout.CENTER);
		}
		return jPanelDescription;
	}

	/**
	 * This method initializes jTextPaneDescription
	 *
	 * @return javax.swing.JTextPane
	 */
	private JTextPane getJTextPaneDescription() {
		if (jTextPaneDescription == null) {
			jTextPaneDescription = new JTextPane();
			jTextPaneDescription.setBackground(this.getBackground());
			jTextPaneDescription.setFont(new java.awt.Font("SansSerif", java.awt.Font.PLAIN, 10));
			jTextPaneDescription.setEditable(false);
			jTextPaneDescription.setPreferredSize(new java.awt.Dimension(0,0));
			jTextPaneDescription.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		}
		return jTextPaneDescription;
	}

	/**
	 * @return Returns the amountOfIntervalValues.
	 */
	public int getAmountOfIntervalValues() {
		return amountOfIntervalValues;
	}

	/**
	 * @param amountOfIntervalValues The amountOfIntervalValues to set.
	 */
	private void setAmountOfIntervalValues(int amountOfIntervalValues) {
		this.amountOfIntervalValues = amountOfIntervalValues;
	}

	/**
	 * @return Returns the okSelected.
	 */
	public boolean isOkSelected() {
		return okSelected;
	}

	/**
	 * @param okSelected The okSelected to set.
	 */
	public void setOkSelected(boolean okSelected) {
		this.okSelected = okSelected;
	}

}  //  @jve:decl-index=0:visual-constraint="90,20"
