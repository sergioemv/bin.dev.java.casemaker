package bi.view.testdataviews;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.BusinessRules;
import model.CMDefaultValue;
import model.ITypeData;
import model.StructureTestData;
import model.TDStructure;
import model.TestData;
import model.TestDataSet;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMFrameView;
import bi.view.utils.CMBaseJComboBox;
import bi.view.utils.CMFileFilter;
import bi.view.utils.JCustomDialog;
/**
 ****************************************************************************** Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
public class CMDialogImportTestData extends JCustomDialog {

    public CMDialogImportTestData(CMFrameView p_frame, CMGridTDStructure p_gridTDStructure)
    {
        super(p_frame);
        m_Frame=p_frame;
        m_gridTDStructure=p_gridTDStructure;
        newTestDataSetForImport = new TestDataSet(p_gridTDStructure.getTDStructure());
        initGUI();
    }
//hcanedo_21_09_2004_begin
    public void initGUI() {
        charge();
        getContentPane().add(jPanelDialogImport, java.awt.BorderLayout.CENTER);
        jPanelDialogImport.setLayout(null);
        jPanelDialogImport.add(jLabelFile);
        jPanelDialogImport.add(jTextFieldFile);
        jPanelDialogImport.add(jButtonBrowse);
        jPanelDialogImport.add(jPanelSeparateSign);
        jPanelDialogImport.add(jLabelSeparationChar);
        jPanelDialogImport.add(jCheckBoxHeader);
        jPanelDialogImport.add(jComboBoxSeparationChar);
        jPanelDialogImport.add(jPanelTestDataimport);
        jPanelDialogImport.add(jButtonOk);
        jPanelDialogImport.add(jButtonCancel);

        jLabelFile.setText(CMMessages.getString("TESTDATA_IMPORT_FILE")); //$NON-NLS-1$
        jLabelFile.setBounds(new java.awt.Rectangle(21, 32, 44, 16));
        jTextFieldFile.setText(""); //$NON-NLS-1$
        jTextFieldFile.setBounds(new java.awt.Rectangle(79, 29, 355, 22));//svonborries_13012006
        jTextFieldFile.setEditable(false);
//hcanedo_21_09_2004_begin
        jTextFieldFile.setBackground(new java.awt.Color(255,255,255));
        jTextFieldFile.setSize(new java.awt.Dimension(312,22));
//hcanedo_21_09_2004_end
        jButtonBrowse.setText(CMMessages.getString("TESTDATA_IMPORT_BROWSE")); //$NON-NLS-1$
        jButtonBrowse.setBounds(new java.awt.Rectangle(418, 27, 98, 27));//svonborries_13012006
        setBounds(new java.awt.Rectangle(0, 0, 540, 420));//svonborries_13012006
        setTitle(CMMessages.getString("TESTDATA_IMPORT_TITLE_DIALOG")); //$NON-NLS-1$
        jPanelSeparateSign.setBounds(new java.awt.Rectangle(18, 69, 316, 93));//svonborries_13012006
        jPanelSeparateSign.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(),
        CMMessages.getString("TESTDATA_IMPORT_SEPARATION_SIGN"), javax.swing.border.TitledBorder.LEADING, javax.swing.border.TitledBorder.TOP, new java.awt.Font("SansSerif", 0, 11), //$NON-NLS-1$ //$NON-NLS-2$
        new java.awt.Color(60, 60, 60)));
        jPanelSeparateSign.setLayout(null);
        jPanelSeparateSign.add(jRadioButtonTabStop);
        jPanelSeparateSign.add(jRadioButtonSemicolon);
        jPanelSeparateSign.add(jRadioButtonComa);
        jPanelSeparateSign.add(jRadioButtonBlank);
        jPanelSeparateSign.add(jRadioButtonOther);
        jPanelSeparateSign.add(jTextFieldOther);
        jRadioButtonTabStop.setText(CMMessages.getString("TESTDATA_IMPORT_TABSTOP")); //$NON-NLS-1$
        jRadioButtonTabStop.setBounds(new java.awt.Rectangle(14, 26, 75, 24));//svonborries_13012006
        jRadioButtonSemicolon.setText(CMMessages.getString("TESTDATA_IMPORT_SEMICOLON")); //$NON-NLS-1$
		jRadioButtonSemicolon.setSelected(true);
        jRadioButtonSemicolon.setBounds(new java.awt.Rectangle(117, 26, 90, 24));//svonborries_13012006
        jRadioButtonComa.setText(CMMessages.getString("TESTDATA_IMPORT_COMA")); //$NON-NLS-1$
        jRadioButtonComa.setBounds(new java.awt.Rectangle(220, 26, 83, 24));//svonborries_13012006
        jRadioButtonBlank.setText(CMMessages.getString("TESTDATA_IMPORT_BLANK")); //$NON-NLS-1$
        jRadioButtonBlank.setBounds(new java.awt.Rectangle(14, 55, 75, 24));//svonborries_13012006
        jRadioButtonOther.setText(CMMessages.getString("TESTDATA_IMPORT_OTHER")); //$NON-NLS-1$
        jRadioButtonOther.setBounds(new java.awt.Rectangle(117, 55, 90, 24));//svonborries_13012006
        jTextFieldOther.setText(""); //$NON-NLS-1$
        jTextFieldOther.setEditable(false);
//hcanedo_21_09_2004_begin
        jTextFieldOther.setBounds(new java.awt.Rectangle(225, 55, 52, 20));//svonborries_13012006
        jTextFieldOther.setSize(new java.awt.Dimension(52,20));
//hcanedo_21_09_2004_end
    	buttonGroupSign.add(jRadioButtonTabStop);
    	buttonGroupSign.add(jRadioButtonSemicolon);
    	buttonGroupSign.add(jRadioButtonComa);
    	buttonGroupSign.add(jRadioButtonBlank);
    	buttonGroupSign.add(jRadioButtonOther);

    	jLabelSeparationChar.setText(CMMessages.getString("TESTDATA_IMPORT_SEPARATION_CHARACTER")); //$NON-NLS-1$
    	jLabelSeparationChar.setBounds(new java.awt.Rectangle(345, 124, 111, 16));//svonborries_13012006
    	jCheckBoxHeader.setText(CMMessages.getString("TESTDATA_IMPORT_HEADER")); //$NON-NLS-1$
        jCheckBoxHeader.setSelected(true);
    	jCheckBoxHeader.setBounds(new java.awt.Rectangle(341, 95, 151, 24));//svonborries_13012006
    	jCheckBoxHeader.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
    	jComboBoxSeparationChar.setBounds(new java.awt.Rectangle(461, 125, 51, 22));//svonborries_13012006
    	jPanelTestDataimport.setBounds(new java.awt.Rectangle(17, 175, 497, 161));//svonborries_13012006
    	jPanelTestDataimport.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(),
        CMMessages.getString("TESTDATA_IMPORT_TEST_DATA_IMPORT"), javax.swing.border.TitledBorder.LEADING, javax.swing.border.TitledBorder.TOP, new java.awt.Font("SansSerif", 0, 11), //$NON-NLS-1$ //$NON-NLS-2$
        new java.awt.Color(60, 60, 60)));
    	jPanelTestDataimport.setLayout(null);
    	jPanelTestDataimport.add(jLabelStructure);
    	jPanelTestDataimport.add(jLabelTestData);
    	jPanelTestDataimport.add(jLabelTestDataSet);
    	jPanelTestDataimport.add(jComboBoxStructure);
    	jPanelTestDataimport.add(jRadioButtonTestDataAll);
    	jPanelTestDataimport.add(jRadioButtonTestDataBeginen);
        jPanelTestDataimport.add(jSpinnerRows);/*
    	jPanelTestDataimport.add(jTextFieldTestDataNumber);
    	jPanelTestDataimport.add(jScrollBarTestDataNumber);*/
    	jPanelTestDataimport.add(jRadioButtonTestDataSetInclude);
    	jPanelTestDataimport.add(jRadioButtonTestDataSetNew);
    	jPanelTestDataimport.add(jRadioButtonTestDataSetNone);
    	jPanelTestDataimport.add(jComboBoxTestDataSetInclude);
    	jPanelTestDataimport.add(jTextFieldTestDataSetNew);
    	jLabelStructure.setText(CMMessages.getString("TESTDATA_IMPORT_STRUCTURE")); //$NON-NLS-1$
    	jLabelStructure.setBounds(new java.awt.Rectangle(19, 32, 62, 16));
    	jLabelTestData.setText(CMMessages.getString("TESTDATA_IMPORT_TEST_DATA_NUMBER")); //$NON-NLS-1$
    	jLabelTestData.setBounds(new java.awt.Rectangle(19, 64, 106, 16));
    	jLabelTestDataSet.setText(CMMessages.getString("TESTDATA_IMPORT_TEST_DATA_SET")); //$NON-NLS-1$
    	jLabelTestDataSet.setBounds(new java.awt.Rectangle(19, 98, 93, 16));
    	jComboBoxStructure.setBounds(new java.awt.Rectangle(149, 28, 303, 21));//svonborries_13012006
    	jRadioButtonTestDataAll.setText(CMMessages.getString("TESTDATA_IMPORT_ALL_IN")); //$NON-NLS-1$
        jRadioButtonTestDataAll.setSelected(true);
    	jRadioButtonTestDataAll.setBounds(new java.awt.Rectangle(145, 62, 80, 24));//svonborries_13012006
    	jRadioButtonTestDataBeginen.setText(CMMessages.getString("TESTDATA_IMPORT_BEGINEN_AT")); //$NON-NLS-1$
    	jRadioButtonTestDataBeginen.setBounds(new java.awt.Rectangle(258, 63, 105, 24));//svonborries_13012006
//hcanedo_21_09_2004_begin
         jSpinnerRows.setBounds(new java.awt.Rectangle(396, 64, 56,22));//svonborries_13012006
//hcanedo_21_09_2004_end
        jSpinnerRows.setEditor(new JSpinner.NumberEditor(jSpinnerRows,"#"));
        ftf = getTextField(jSpinnerRows);
        if (ftf != null ) {
            ftf.setColumns(8); //specify more width than we need
            ftf.setHorizontalAlignment(JTextField.RIGHT);
            ftf.setEditable(false);
            ftf.setBackground(new java.awt.Color(255,255,255));
        }
/*
    	jTextFieldTestDataNumber.setText(""); //$NON-NLS-1$
    	jTextFieldTestDataNumber.setBounds(new java.awt.Rectangle(313, 57, 61, 27));
    	jTextFieldTestDataNumber.setEditable(false);
    	jTextFieldTestDataNumber.setBackground(new java.awt.Color(255,255,255));
    	jScrollBarTestDataNumber.setBounds(new java.awt.Rectangle(374, 57, 17, 28));
        jTextFieldTestDataNumber.setText(Integer.toString(numofTestData));
        jScrollBarTestDataNumber.setMaximum(10000);
        jScrollBarTestDataNumber.setBlockIncrement(1);
        jScrollBarTestDataNumber.setValue(numofTestData);*/
    	jRadioButtonTestDataSetInclude.setText(CMMessages.getString("TESTDATA_IMPORT_INCLUDE_IN")); //$NON-NLS-1$
    	jRadioButtonTestDataSetInclude.setBounds(new java.awt.Rectangle(145, 96, 101, 24));//svonborries_13012006
    	jRadioButtonTestDataSetNew.setText(CMMessages.getString("TESTDATA_IMPORT_IN_A_NEW")); //$NON-NLS-1$
    	jRadioButtonTestDataSetNew.setBounds(new java.awt.Rectangle(258, 96, 91, 24));//svonborries_13012006
    	jRadioButtonTestDataSetNone.setText(CMMessages.getString("TESTDATA_IMPORT_NONE")); //$NON-NLS-1$
        jRadioButtonTestDataSetNone.setSelected(true);
    	jRadioButtonTestDataSetNone.setBounds(new java.awt.Rectangle(396, 96, 75, 24));//svonborries_13012006
    	jComboBoxTestDataSetInclude.setBounds(new java.awt.Rectangle(149, 124, 70, 22));
        jComboBoxTestDataSetInclude.setEnabled(false);
    	jTextFieldTestDataSetNew.setText(""); //$NON-NLS-1$
        jTextFieldTestDataSetNew.setEnabled(false);
    	jTextFieldTestDataSetNew.setBounds(new java.awt.Rectangle(261, 124, 80, 22));//svonborries_13012006
    	jTextFieldTestDataSetNew.setEditable(false);
    	jTextFieldTestDataSetNew.setDisabledTextColor(new java.awt.Color(0,51,51));
    	jButtonOk.setText(CMMessages.getString("TESTDATA_IMPORT_OK")); //$NON-NLS-1$
    	jButtonOk.setBounds(new java.awt.Rectangle(335, 348, 85, 27));//svonborries_13012006
    	jButtonCancel.setText(CMMessages.getString("TESTDATA_IMPORT_CANCEL")); //$NON-NLS-1$
    	jButtonCancel.setBounds(new java.awt.Rectangle(427, 348, 85, 27));//svonborries_13012006

        buttonGroupTestData.add(jRadioButtonTestDataAll);
        buttonGroupTestData.add(jRadioButtonTestDataBeginen);

		buttonGroupTestDataSet.add(jRadioButtonTestDataSetInclude);
		buttonGroupTestDataSet.add(jRadioButtonTestDataSetNew);
        buttonGroupTestDataSet.add(jRadioButtonTestDataSetNone);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension dlgSize = this.getSize();
		Point loc = getLocation();
		setLocation((screenSize.width - dlgSize.width) / 2 , (screenSize.height - dlgSize.height) / 2 );
		setModal(true);
//hcanedo_21_09_2004_begin
		setResizable(false);
//hcanedo_21_09_2004_end
        jButtonOk.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){jButtonOkActionPerformed(e);}});
        jButtonCancel.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){jButtonCancelActionPerformed(e);}});
        jButtonBrowse.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){jButtonBrowseActionPerformed(e);}});
        jRadioButtonOther.addChangeListener(new ChangeListener(){public void stateChanged(ChangeEvent e){jRadioButtonOtherStateChanged(e);}});
        jRadioButtonTestDataSetNew.addChangeListener(new ChangeListener(){public void stateChanged(ChangeEvent e){jRadioButtonTestDataSetNewStateChanged(e);}});
        jRadioButtonTestDataSetInclude.addChangeListener(new ChangeListener(){public void stateChanged(ChangeEvent e){jRadioButtonTestDataSetIncludeStateChanged(e);}});
   //     jScrollBarTestDataNumber.addAdjustmentListener(new AdjustmentListener(){public void adjustmentValueChanged(AdjustmentEvent e){jScrollBarTestDataNumberAdjustmentValueChanged(e);}});
        jRadioButtonTabStop.addChangeListener(new ChangeListener(){public void stateChanged(ChangeEvent e){jRadioButtonTabStopStateChanged(e);}});
        jRadioButtonBlank.addChangeListener(new ChangeListener(){public void stateChanged(ChangeEvent e){jRadioButtonBlankStateChanged(e);}});
        jRadioButtonSemicolon.addChangeListener(new ChangeListener(){public void stateChanged(ChangeEvent e){jRadioButtonSemicolonStateChanged(e);}});
        jRadioButtonComa.addChangeListener(new ChangeListener(){public void stateChanged(ChangeEvent e){jRadioButtonComaStateChanged(e);}});
        jRadioButtonTestDataBeginen.addChangeListener(new ChangeListener(){public void stateChanged(ChangeEvent e){jRadioButtonTestDataBeginenStateChanged(e);}});
        jRadioButtonTestDataSetNone.addChangeListener(new ChangeListener(){public void stateChanged(ChangeEvent e){jRadioButtonTestDataSetNoneStateChanged(e);}});
        jTextFieldOther.addCaretListener(new CaretListener(){public void caretUpdate(CaretEvent e){jTextFieldOtherCaretUpdate(e);}});
        jComboBoxStructure.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent e){jComboBoxStructureItemStateChanged(e);}});
        jComboBoxTestDataSetInclude.addItemListener(new ItemListener(){public void itemStateChanged(ItemEvent e){jComboBoxTestDataSetIncludeItemStateChanged(e);}});
        jSpinnerRows.addChangeListener(new ChangeListener(){public void stateChanged(ChangeEvent e){jSpinnerRowsChangeEvent(e);}} );
//hcanedo_07102005_begin
        getRootPane().setDefaultButton(jButtonOk);
//hcanedo_07102005_end
    }
//hcanedo_21_09_2004_end
    	 public JFormattedTextField getTextField(JSpinner spinner) {
        JComponent editor = spinner.getEditor();
        if (editor instanceof JSpinner.NumberEditor) {
            return ((JSpinner.NumberEditor)editor).getTextField();
        } else {
            System.err.println("Unexpected editor type: "
                               + spinner.getEditor().getClass()
                               + " isn't a descendant of DefaultEditor");
            return null;
        }
    }

      public void jSpinnerRowsChangeEvent(ChangeEvent e) {
        SpinnerModel numberModel = jSpinnerRows.getModel();
        if (numberModel instanceof SpinnerNumberModel) {
			numofTestData = Integer.parseInt(jSpinnerRows.getValue().toString());
        }
    }

    public void jButtonOkActionPerformed(ActionEvent e)
    {
        try
        {
			if(openedFile==null || !this.openedFile.isFile())
            {
                JOptionPane.showMessageDialog(m_Frame, CMMessages.getString("TESTDATA_IMPORT_MENSSAGE_OPEN_FILE"), CMMessages.getString("TESTDATA_IMPORT_TITLE_INFORMATION"), JOptionPane.INFORMATION_MESSAGE); //$NON-NLS-1$ //$NON-NLS-2$
            }
            else
            {
				BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(openedFile)));
                String line= new String();
                if(jCheckBoxHeader.isSelected()){
                        line=input.readLine();
                        line=line.trim();
                	while(line.equals("")) //$NON-NLS-1$
                	{
                    	line=input.readLine();
                    	line=line.trim();
                	}
                }
                if(jRadioButtonTestDataAll.isSelected())
                {
                    		allinTestData = new TestData(new TDStructure());
                    		if(CMIndexTDStructureUpdate.getInstance().getTDStructureManager().idTestDataVector.idExist(numofTestData))
                    		{
								int idexistent= numofTestData;
                        		numofTestData =CMIndexTDStructureUpdate.getInstance().getTDStructureManager().idTestDataVector.nextValidId(numofTestData-1);
                        		JOptionPane.showMessageDialog(m_Frame, CMMessages.getString("TESTDATA_IMPORT_MENSSAGE_INFORMATION_EXIST_PART1")+idexistent+CMMessages.getString("TESTDATA_IMPORT_MENSSAGE_INFORMATION_EXIST_PART2")+numofTestData, CMMessages.getString("TESTDATA_IMPORT_TITLE_INFORMATION"), JOptionPane.INFORMATION_MESSAGE); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                                allinTestData.setName(allinTestData.generateNameSpecificID(numofTestData));
                    		}
                    allinTestData.setName(allinTestData.generateNameSpecificID(numofTestData));
                }
                while(line!=null)
                {
                	line =input.readLine();
                    if(line !=null)
                    {
                		if(jRadioButtonTestDataAll.isSelected())
                		{
							StructureTestData cloneStructureSelected = selectedStructure.clones();
                            if(chargeDataInStructure(cloneStructureSelected, line))
                            {
                            	allinTestData.getM_TDStructure().getM_StructureTestData().addElement(cloneStructureSelected);
                    			CMIndexTDStructureUpdate.getInstance().getTDStructureManager().idTestDataVector.registerId(numofTestData);
                            }
                		}
                		else
                		{
                    		TestData newTestData = new TestData(new TDStructure());
                    		if(CMIndexTDStructureUpdate.getInstance().getTDStructureManager().idTestDataVector.idExist(numofTestData))
                    		{
								int idexistent= numofTestData;
                        		numofTestData =CMIndexTDStructureUpdate.getInstance().getTDStructureManager().idTestDataVector.nextValidId(numofTestData-1);
                        		JOptionPane.showMessageDialog(m_Frame, CMMessages.getString("TESTDATA_IMPORT_MENSSAGE_INFORMATION_EXIST_PART1")+idexistent+CMMessages.getString("TESTDATA_IMPORT_MENSSAGE_INFORMATION_EXIST_PART2")+numofTestData, CMMessages.getString("TESTDATA_IMPORT_TITLE_INFORMATION"), JOptionPane.INFORMATION_MESSAGE); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                                newTestData.setName(newTestData.generateNameSpecificID(numofTestData));
                    		}

                            StructureTestData cloneStructureSelected = selectedStructure.clones();
                            if(chargeDataInStructure(cloneStructureSelected, line))
                            {
                                 newTestData.setName(newTestData.generateNameSpecificID(numofTestData));
                    			CMIndexTDStructureUpdate.getInstance().getTDStructureManager().idTestDataVector.registerId(numofTestData);
                            	newTestData.getM_TDStructure().getM_StructureTestData().addElement(cloneStructureSelected);
                            	m_gridTDStructure.getTDStructure().getTestDataCombination().getM_TestDatas().addElement(newTestData);
                                numofTestData++;
                            }
                            if(selectedTestDataSet !=null)
                            {
								newTestData.setSet(true);
                                selectedTestDataSet.getM_TestDataCombinations().getM_TestDatas().addElement(newTestData);
                            }
               			}
                    }
                }
 				if(jRadioButtonTestDataAll.isSelected())
                {
					m_gridTDStructure.getTDStructure().getTestDataCombination().getM_TestDatas().addElement(allinTestData);
                    if(selectedTestDataSet !=null)
                    {
                    	allinTestData.setSet(true);
               			selectedTestDataSet.getM_TestDataCombinations().getM_TestDatas().addElement(allinTestData);
                        if(!jRadioButtonTestDataSetInclude.isSelected())
                        	m_gridTDStructure.getTDStructure().getM_TestDataSet().addElement(selectedTestDataSet);
                    }
 				}
 				if(jRadioButtonTestDataBeginen.isSelected())
 				{
    				if(selectedTestDataSet!=null && !jRadioButtonTestDataSetInclude.isSelected())
    				{
						m_gridTDStructure.getTDStructure().getM_TestDataSet().addElement(selectedTestDataSet);
    				}
 				}
 				if(jRadioButtonTestDataSetNone.isSelected())
 				{
    				m_gridTDStructure.getTDStructure().degenerateIDTestDataSet();
 				}
 				input.close();
                isOkSelected=true;
                if(!jRadioButtonTestDataSetNew.isSelected())
                	m_gridTDStructure.getTDStructure().degenerateIDTestDataSet();
                dispose();
            }

        }
        catch(Exception ex)
        {
          //  m_gridTDStructure.getTDStructure().degenerateIDTestDataSet();
            JOptionPane.showMessageDialog(m_Frame, CMMessages.getString("TESTDATA_IMPORT_MENSSAGE_ERROR"), CMMessages.getString("TESTDATA_IMPORT_TITLE_ERROR"), JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$ //$NON-NLS-2$
            dispose();
        }

    }

    public boolean chargeDataInStructure(StructureTestData p_cloneStructureSelected,String p_line)
    {
		p_line=p_line.trim();
        if(p_line.equals("")) //$NON-NLS-1$
        	return false;
        else
        {
            p_line=p_line.substring(p_line.indexOf(separationSign)+1);//eliminamos datos TD
            p_line=p_line.substring(p_line.indexOf(separationSign)+1);//Eliminamos datos TC
            for(int i =0; i< p_cloneStructureSelected.getTypeData().size();i++)
            {
                /**
                 * Nuevo codigo para separation char
                **/
                int index;
				if(!jComboBoxSeparationChar.getSelectedItem().toString().equals(BusinessRules.TESTDATA_IMPORT_SEPARATION_CHAR[2]))
                {
                    index= validIndexSeparated(new String(p_line),jComboBoxSeparationChar.getSelectedItem().toString(), 0);
                }
                else{
                /**/
                 index =p_line.indexOf(separationSign);
                }
                String value;
                if(index !=-1)
                {
                	value=p_line.substring(0,index);
					p_line=p_line.substring(index+1);//Eliminamos datos usados
                }
                else
                {
					value = new String(p_line);
                    p_line =""; //$NON-NLS-1$
                }
                value=clearSeparationChar(value);
                ITypeData typ= (ITypeData)p_cloneStructureSelected.getTypeData().elementAt(i);
            	CMDefaultValue defaultValue = new CMDefaultValue(value);
                typ.setValue(defaultValue);
                //TODO ADD TO THE COMPOUND
                //typ.setStringValue(value);
            }
            if(!p_line.equals("")) //$NON-NLS-1$
            {
                JOptionPane.showMessageDialog(m_Frame, CMMessages.getString("TESTDATA_IMPORT_MENSSAGE_INFORMATION_INCLUSION")+p_line, CMMessages.getString("TESTDATA_IMPORT_TITLE_INFORMATION"), JOptionPane.INFORMATION_MESSAGE); //$NON-NLS-1$ //$NON-NLS-2$
            }
            return true;
        }
    }
    public String clearSeparationChar(String p_Value)
    {
        StringBuffer newValue= new StringBuffer();
        if(!jComboBoxSeparationChar.getSelectedItem().toString().equals(BusinessRules.TESTDATA_IMPORT_SEPARATION_CHAR[2]))
        {
            for(int i=0;i< p_Value.length(); i++)
            {
                if(!jComboBoxSeparationChar.getSelectedItem().toString().equals(String.valueOf(p_Value.charAt(i))))
            	newValue.append(p_Value.charAt(i));
            }
            return newValue.toString();
        }
        else
        {
            return p_Value;
        }
    }
	public int validIndexSeparated(String p_line, String p_SeparationChar, int desface)
    {
		int indexSign =p_line.indexOf(separationSign);
        int indexChar=p_line.indexOf(p_SeparationChar);
        if(indexSign<=indexChar || indexChar==-1)
            return indexSign+desface;
        else{
            int i =indexChar+1;
            String aux=String.valueOf(p_line.charAt(i));
			while(!aux.equals(p_SeparationChar))
            {
                try{
                i++;
				aux=String.valueOf(p_line.charAt(i));
                }
                catch(Exception ex)
                {
                    return -1;
                }
            }
            try{
                p_line=p_line.substring(i+1);
            }
            catch(Exception ex)
            {
               return -1;
            }
            return validIndexSeparated(new String(p_line), p_SeparationChar, i+1);
        }
    }
    public void jButtonCancelActionPerformed(ActionEvent e)
    {
		isOkSelected=false;
	    m_gridTDStructure.getTDStructure().degenerateIDTestDataSet();
        dispose();
    }

    public void jButtonBrowseActionPerformed(ActionEvent e)
    {
        JFileChooser chooser = new JFileChooser();
	    chooser.setFileFilter(CMFileFilter.CSV.getFilter());
    	int returnVal = chooser.showOpenDialog(m_Frame);
    	if(returnVal == JFileChooser.APPROVE_OPTION)
    	{
        	jTextFieldFile.setText(chooser.getSelectedFile().toString().replace('\\','/'));
            openedFile = chooser.getSelectedFile();
    	}
    }

    public void jRadioButtonOtherStateChanged(ChangeEvent e)
    {
        jTextFieldOther.setEditable(jRadioButtonOther.isSelected());
        if(jRadioButtonOther.isSelected())
        	separationSign=jTextFieldOther.getText();
    }

    public void jRadioButtonTestDataSetNewStateChanged(ChangeEvent e)
    {
       if(jRadioButtonTestDataSetNew.isSelected()){
			jTextFieldTestDataSetNew.setBackground(new java.awt.Color(255,255,255));
			jTextFieldTestDataSetNew.setText(newTestDataSetForImport.getName());
            selectedTestDataSet= newTestDataSetForImport;
       }
       else{
      		jTextFieldTestDataSetNew.setBackground(new java.awt.Color(212,208,200));
			jTextFieldTestDataSetNew.setText(""); //$NON-NLS-1$
       }
    }

    public void jRadioButtonTestDataSetIncludeStateChanged(ChangeEvent e)
    {
        jComboBoxTestDataSetInclude.setEnabled(jRadioButtonTestDataSetInclude.isSelected());
        if(m_gridTDStructure.getTDStructure().getM_TestDataSet().size()!=0 && jRadioButtonTestDataSetInclude.isSelected())
        	selectedTestDataSet=(TestDataSet) m_gridTDStructure.getTDStructure().getM_TestDataSet().elementAt(jComboBoxTestDataSetInclude.getSelectedIndex());
        else{
            if(jRadioButtonTestDataSetInclude.isSelected())
            {
				selectedTestDataSet=null;
            }
        }
    }

	private void charge()
    {
		for(int i =0; i < m_gridTDStructure.getTDStructure().getM_StructureTestData().size();i++)
        {
            StructureTestData std = (StructureTestData)m_gridTDStructure.getTDStructure().getM_StructureTestData().elementAt(i);
            globalstructures.addElement(std.getName());
        }
        jComboBoxStructure = new CMBaseJComboBox(globalstructures);
        for(int j=0; j < m_gridTDStructure.getTDStructure().getM_TestDataSet().size();j++)
        {
            TestDataSet tds=(TestDataSet)m_gridTDStructure.getTDStructure().getM_TestDataSet().elementAt(j);
			testdataset.addElement(tds.getName());
        }
        jComboBoxTestDataSetInclude = new CMBaseJComboBox(testdataset);
        jComboBoxSeparationChar = new CMBaseJComboBox(BusinessRules.TESTDATA_IMPORT_SEPARATION_CHAR);
        try{
        selectedStructure=(StructureTestData)m_gridTDStructure.getTDStructure().getM_StructureTestData().firstElement();
        }
        catch(Exception ex)
        {
			JOptionPane.showMessageDialog(m_Frame, CMMessages.getString("TESTDATA_IMPORT_MENSSAGE_ERROR_ESTRUCTURE"), CMMessages.getString("TESTDATA_IMPORT_TITLE_ERROR"), JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$ //$NON-NLS-2$
            jButtonOk.setEnabled(false);
        }

    }

  /*  public void jScrollBarTestDataNumberAdjustmentValueChanged(AdjustmentEvent e)
    {
		jTextFieldTestDataNumber.setText(Integer.toString(e.getValue()));
        numofTestData = e.getValue();
    }*/

    public void jRadioButtonTabStopStateChanged(ChangeEvent e)
    {
        if(jRadioButtonTabStop.isSelected())
		separationSign=BusinessRules.TESTDATA_IMPORT_SEPARATIONSIGN_TABSTOP;
    }

    public void jRadioButtonBlankStateChanged(ChangeEvent e)
    {
        if(jRadioButtonBlank.isSelected())
        separationSign=BusinessRules.TESTDATA_IMPORT_SEPARATIONSIGN_BLANK;
    }

    public void jRadioButtonSemicolonStateChanged(ChangeEvent e)
    {
        if(jRadioButtonSemicolon.isSelected())
        separationSign=BusinessRules.TESTDATA_IMPORT_SEPARATIONSIGN_SEMICOLON;
    }

    public void jRadioButtonComaStateChanged(ChangeEvent e)
    {
        if(jRadioButtonComa.isSelected())
        separationSign=BusinessRules.TESTDATA_IMPORT_SEPARATIONSIGN_COMA;
    }

    public void jRadioButtonTestDataBeginenStateChanged(ChangeEvent e)
    {
    }

    public void jRadioButtonTestDataSetNoneStateChanged(ChangeEvent e)
    {
        if(jRadioButtonTestDataSetNone.isSelected())
        selectedTestDataSet=null;
    }

    public void jTextFieldOtherCaretUpdate(CaretEvent e)
    {
        separationSign=jTextFieldOther.getText();
    }

    public void jComboBoxStructureItemStateChanged(ItemEvent e)
    {
        int index = globalstructures.indexOf(jComboBoxStructure.getSelectedItem());
        selectedStructure =(StructureTestData)m_gridTDStructure.getTDStructure().getM_StructureTestData().elementAt(index);
    }

    public void jComboBoxTestDataSetIncludeItemStateChanged(ItemEvent e)
    {
        selectedTestDataSet= (TestDataSet)m_gridTDStructure.getTDStructure().getM_TestDataSet().elementAt(jComboBoxTestDataSetInclude.getSelectedIndex());
    }

    private Vector globalstructures= new Vector();
    private Vector testdataset= new Vector();
    private JPanel jPanelDialogImport = new JPanel();
    private JLabel jLabelFile = new JLabel();
    private JTextField jTextFieldFile = new JTextField();
    private JButton jButtonBrowse = new JButton();
    private JPanel jPanelSeparateSign = new JPanel();
    private JRadioButton jRadioButtonTabStop = new JRadioButton();
    private JRadioButton jRadioButtonSemicolon = new JRadioButton();
    private JRadioButton jRadioButtonComa = new JRadioButton();
    private JRadioButton jRadioButtonBlank = new JRadioButton();
    private JRadioButton jRadioButtonOther = new JRadioButton();
    private JTextField jTextFieldOther = new JTextField();
    private JLabel jLabelSeparationChar = new JLabel();
    private JCheckBox jCheckBoxHeader = new JCheckBox();
    private CMBaseJComboBox jComboBoxSeparationChar;// = new JComboBox();
    private JPanel jPanelTestDataimport = new JPanel();
    private JLabel jLabelStructure = new JLabel();
    private JLabel jLabelTestData = new JLabel();
    private JLabel jLabelTestDataSet = new JLabel();
    private CMBaseJComboBox jComboBoxStructure;// = new JComboBox();
    private JRadioButton jRadioButtonTestDataAll = new JRadioButton();
    private JRadioButton jRadioButtonTestDataBeginen = new JRadioButton();
  //  private JTextField jTextFieldTestDataNumber = new JTextField();
  //  private JScrollBar jScrollBarTestDataNumber = new JScrollBar();
    private JRadioButton jRadioButtonTestDataSetInclude = new JRadioButton();
    private JRadioButton jRadioButtonTestDataSetNew = new JRadioButton();
    private JRadioButton jRadioButtonTestDataSetNone = new JRadioButton();
    private CMBaseJComboBox jComboBoxTestDataSetInclude;// = new JComboBox();
    private JTextField jTextFieldTestDataSetNew = new JTextField();
    private ButtonGroup buttonGroupSign = new ButtonGroup();
    private ButtonGroup buttonGroupTestData = new ButtonGroup();
    private ButtonGroup buttonGroupTestDataSet = new ButtonGroup();
    private JButton jButtonOk = new JButton();
    private JButton jButtonCancel = new JButton();
    private CMFrameView m_Frame;
    private CMGridTDStructure m_gridTDStructure;
    private File openedFile;
    private String separationSign= BusinessRules.TESTDATA_IMPORT_SEPARATIONSIGN_SEMICOLON;
    private int numofTestData =CMIndexTDStructureUpdate.getInstance().getTDStructureManager().idTestDataVector.nextValidId(5000);
    private TestDataSet newTestDataSetForImport;
    private TestDataSet selectedTestDataSet;
    private TestData allinTestData;
    private StructureTestData selectedStructure;
    Integer value = new Integer(numofTestData);
 	Integer min = new Integer(1);
	Integer max = new Integer(9999);
	Integer step = new Integer(1);
    SpinnerNumberModel model = new SpinnerNumberModel(value, min, max, step);
    JSpinner jSpinnerRows= new JSpinner(model);
    JFormattedTextField ftf = null;
    public boolean isOkSelected=false;
	protected List getOrder() {
		List focusOrder= new ArrayList();
		focusOrder.add(jButtonBrowse);
		focusOrder.add(jRadioButtonTabStop);
		focusOrder.add(jRadioButtonSemicolon);
		focusOrder.add(jRadioButtonComa);
		focusOrder.add(jRadioButtonBlank);
		focusOrder.add(jRadioButtonOther);
		if(jTextFieldOther.isEditable() && jTextFieldOther.isEnabled())
			focusOrder.add(jTextFieldOther);
		focusOrder.add(jCheckBoxHeader);
		focusOrder.add(jComboBoxSeparationChar);
		focusOrder.add(jComboBoxStructure);
		focusOrder.add(jRadioButtonTestDataAll);
		focusOrder.add(jRadioButtonTestDataBeginen);
		focusOrder.add(jSpinnerRows);
		focusOrder.add(jRadioButtonTestDataSetInclude);
		focusOrder.add(jRadioButtonTestDataSetNew);
		focusOrder.add(jRadioButtonTestDataSetNone);
		if(jComboBoxTestDataSetInclude.isEnabled())
			focusOrder.add(jComboBoxTestDataSetInclude);
		focusOrder.add(jButtonOk);
		focusOrder.add(jButtonCancel);
		return focusOrder;
	}
	protected void PressJButtonCancel(Object object) {
		jButtonCancelActionPerformed(null);

	}
	protected void PressJButtonOk(Object object) {
		jButtonOkActionPerformed(null);
	}

}
