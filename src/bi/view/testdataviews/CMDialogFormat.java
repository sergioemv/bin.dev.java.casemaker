package bi.view.testdataviews;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.BusinessRules;
import model.TestDataFormat;
import model.TestObject;

import org.apache.log4j.Logger;

import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMFrameView;
import bi.view.utils.CMBaseJComboBox;
import bi.view.utils.CMFormatFactory;
import bi.view.utils.JCustomDialog;

/**
 * Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
public class CMDialogFormat extends JCustomDialog {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//hcanedo_21_09_2004_begin
    public CMDialogFormat(CMFrameView p_Frame, TestDataFormat p_OldFormatSelected) {
        super(p_Frame);
        m_Frame = p_Frame;
        formatResult = new String("");
        //int indexVisualPattern=Arrays.binarySearch(BusinessRules.TESTDATA_FORMAT_VISUAL_FORMATTERS,p_OldFormatSelected);
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////  
        //Parche for Percentage formatter
        if(p_OldFormatSelected.getOriginalFormatter().equals("#,##%") && !p_OldFormatSelected.isVisualFormat()){
        	p_OldFormatSelected.setVisualFormat(true);
        }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////        
        if(p_OldFormatSelected.isVisualFormat())
        {
            decimalSeparatorOldFormat=",";
        }
        else{
            decimalSeparatorOldFormat=".";
        }

        m_OldFormatSelected = p_OldFormatSelected;
        initGUI();
    }

    //hcanedo_21_09_2004_end
    public void initGUI() {
//hcanedo_21_09_2004_begin
      //  charge();
      jComboBoxSimbol = new CMBaseJComboBox(BusinessRules.TESTDATA_FORMAT_MONEYSIGN);
//hcanedo_21_09_2004_end
        this.setModal(true);

        /** nuevo codigo jSpinner */
        jSpinnerdecimal.setBounds(new java.awt.Rectangle(330, 73, 86, 25));
        jSpinnerdecimal.setEditor(new JSpinner.NumberEditor(jSpinnerdecimal));
        ftf = getTextField(jSpinnerdecimal);
        if (ftf != null) {
            ftf.setColumns(8); //specify more width than we need
            ftf.setHorizontalAlignment(JTextField.RIGHT);
            ftf.setEditable(false);
            ftf.setBackground(new java.awt.Color(255, 255, 255));
        }
        jTextFieldExample.setText(""); //$NON-NLS-1$
        jTextFieldExample.setEditable(false);
        jTextFieldExample.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        setBounds(new java.awt.Rectangle(0, 0, 435, 449));
        getContentPane().setLayout(null);
        getContentPane().add(jScrollPaneCategory);
        getContentPane().add(jPanelExample);
        //getContentPane().add(jTextFieldPosDecimal);
        getContentPane().add(jSpinnerdecimal);
        getContentPane().add(jLabelPosDecimal);
        getContentPane().add(jLabelSimbol);
        getContentPane().add(jComboBoxSimbol);
        getContentPane().add(jScrollPaneFormat);
        getContentPane().add(jLabelCategory);
        getContentPane().add(jLabelFormat);
        // getContentPane().add(jSpinnerdecimal);
        getContentPane().add(jScrollPaneDescription);
        getContentPane().add(jButtonOk);
        getContentPane().add(jButtonCancel);
        jScrollPaneCategory.setBounds(new java.awt.Rectangle(13, 28, 174, 247));
        jScrollPaneCategory.getViewport().add(jListCategory);
        jListCategory.setBounds(new java.awt.Rectangle(104, 83, 32, 32));
        jPanelExample.setBounds(new java.awt.Rectangle(204, 8, 215, 52));
        jPanelExample.setLayout(new java.awt.BorderLayout());
        jPanelExample.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(),
            CMMessages.getString("TESTDATA_FORMAT_EXAMPLE"), javax.swing.border.TitledBorder.LEADING, javax.swing.border.TitledBorder.TOP,
            new java.awt.Font("SansSerif", 0, 11), //$NON-NLS-1$ //$NON-NLS-2$
            new java.awt.Color(60, 60, 60)));
        jPanelExample.add(jTextFieldExample, java.awt.BorderLayout.CENTER);
        //   jTextFieldPosDecimal.setText(""); //$NON-NLS-1$
        //   jTextFieldPosDecimal.setBounds(new java.awt.Rectangle(330, 69, 48, 31));
        //   jTextFieldPosDecimal.setEditable(false);
        //   jTextFieldPosDecimal.setBackground(new java.awt.Color(255,255,255));
        jLabelPosDecimal.setText(CMMessages.getString("TESTDATA_FORMAT_POS_DECIMAL")); //$NON-NLS-1$
        jLabelPosDecimal.setBounds(new java.awt.Rectangle(206, 78, 119, 16));
        jLabelSimbol.setText(CMMessages.getString("TESTDATA_FORMAT_SIMBOL")); //$NON-NLS-1$
        jLabelSimbol.setBounds(new java.awt.Rectangle(206, 113, 201, 16));
        jComboBoxSimbol.setBounds(new java.awt.Rectangle(206, 133, 210, 25));
        jScrollPaneFormat.setBounds(new java.awt.Rectangle(206, 189, 210, 86));
        jScrollPaneFormat.getViewport().add(jListFormat);
        jListFormat.setBounds(new java.awt.Rectangle(153, 19, 32, 32));
        jLabelCategory.setText(CMMessages.getString("TESTDATA_FORMAT_CATEGORY")); //$NON-NLS-1$
        jLabelCategory.setBounds(new java.awt.Rectangle(13, 8, 170, 16));
        jLabelFormat.setText(CMMessages.getString("TESTDATA_FORMAT_FORMAT")); //$NON-NLS-1$
        jLabelFormat.setBounds(new java.awt.Rectangle(206, 170, 205, 16));
        //jSpinnerdecimal.setBounds(new java.awt.Rectangle(378, 69, 17, 32));
        //jSpinnerdecimal.setValue(2);
        jScrollPaneDescription.setBounds(new java.awt.Rectangle(13, 286, 405, 83));
        jScrollPaneDescription.setBorder(null);
        jTextAreaDescription.setText(""); //$NON-NLS-1$
        jTextAreaDescription.setBounds(new java.awt.Rectangle(13, 31, 53, 16));
        jTextAreaDescription.setLineWrap(true);
        jTextAreaDescription.setEditable(false);
        jTextAreaDescription.setWrapStyleWord(true);
        jTextAreaDescription.setBackground(this.getBackground());//new java.awt.Color(212, 208, 200));
        jTextAreaDescription.setForeground(this.getForeground());
        jTextAreaDescription.setDisabledTextColor(new java.awt.Color(51, 0, 51));
        jTextAreaDescription.setBorder(null);
        jScrollPaneDescription.getViewport().add(jTextAreaDescription);
        jButtonOk.setText(CMMessages.getString("TESTDATA_FORMAT_OK")); //$NON-NLS-1$
        jButtonOk.setBounds(new java.awt.Rectangle(111, 384, 89, 28));
        jButtonCancel.setText(CMMessages.getString("TESTDATA_FORMAT_CANCEL")); //$NON-NLS-1$
        jButtonCancel.setBounds(new java.awt.Rectangle(222, 383, 90, 29));
        jListCategory.addListSelectionListener(
            new ListSelectionListener() {
                public void valueChanged(ListSelectionEvent e) { jListCategoryValueChanged(e); }
            });
        jListFormat.addListSelectionListener(
            new ListSelectionListener() {
                public void valueChanged(ListSelectionEvent e) { jListFormatValueChanged(e); }
            });
        jComboBoxSimbol.addItemListener(
            new ItemListener() {
                public void itemStateChanged(ItemEvent e) { jComboBoxSimbolItemStateChanged(e); }
            });
        // jSpinnerdecimal.addAdjustmentListener(new AdjustmentListener(){public void adjustmentValueChanged(AdjustmentEvent
        // e){jSpinnerdecimalAdjustmentValueChanged(e);}});
        jButtonOk.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) { jButtonOkActionPerformed(e); }
            });
        jButtonCancel.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) { jButtonCancelActionPerformed(e); }
            });
        jSpinnerdecimal.addChangeListener(
            new ChangeListener() {
                public void stateChanged(ChangeEvent e) { jSpinnerDecimalChangeEvent(e); }
            });
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension dlgSize = this.getSize();
        setLocation((screenSize.width - dlgSize.width) / 2, (screenSize.height - dlgSize.height) / 2);
        //hcanedo_21_09_2004_begin
        setResizable(false);

		charge();
        jListCategory.revalidate();
        jListFormat.revalidate();
        //   initExample();
//hcanedo_21_09_2004_end
//hcanedo_07102005_begin
        getRootPane().setDefaultButton(jButtonOk);
//hcanedo_07102005_end        
    }

    public void initExample() {
        jListFormat.setSelectedIndex(0);
        jTextFieldExample.setText(BusinessRules.FORMULAS_FORMAT_DATE_EXAMPLE[jListFormat.getSelectedIndex()]);
        formatResult = jListFormat.getSelectedValue().toString();
    }

    //hcanedo_21_09_2004_begin
    public boolean isOldFormatDate() {
        boolean result = false;
        int indexOldFormat = Arrays.binarySearch(BusinessRules.TESTDATA_FORMAT_DATE, m_OldFormatSelected.getOriginalFormatter());
        if (indexOldFormat >= 0) {
            jListFormat.setListData(BusinessRules.TESTDATA_FORMAT_DATE);
            jListCategory.setSelectedIndex(0);
            jListCategory.revalidate();
            jListFormat.setSelectedIndex(indexOldFormat);
            jListFormat.revalidate();
            jLabelSimbol.setVisible(false);
            jComboBoxSimbol.setVisible(false);
            jLabelPosDecimal.setVisible(false);
            jLabelFormat.setVisible(true);
            jScrollPaneFormat.setVisible(true);
            jTextFieldExample.setVisible(true);
            jSpinnerdecimal.setVisible(false);
            jTextFieldExample.setText(BusinessRules.FORMULAS_FORMAT_DATE_EXAMPLE[jListFormat.getSelectedIndex()]);
            jTextAreaDescription.setText(CMMessages.getString("TESTDATA_DATE_AND_TIME_FORMAT_DESCRIPTION")); //$NON-NLS-1$
            formatResult=jListFormat.getSelectedValue().toString();
            result = true;
        }
        return result;
    }

    public boolean isOldFormatPercent() {
        boolean result = false;
        if (m_OldFormatSelected.getVisualFormatter().indexOf("%") >= 0) {
            jListCategory.setSelectedIndex(3);
            jListCategory.revalidate();
            jListFormat.setListData(BusinessRules.TESTDATA_FORMAT_PERCENTAGE);
            int indexOldFormat = Arrays.binarySearch(BusinessRules.TESTDATA_FORMAT_PERCENTAGE, m_OldFormatSelected.getOriginalFormatter());
            if(indexOldFormat>=0)
            	jListFormat.setSelectedIndex(indexOldFormat);
            else
            	jListFormat.setSelectedIndex(0);
            jListFormat.revalidate();
            jListFormat.setVisible(true);
            jLabelSimbol.setVisible(false);
            jComboBoxSimbol.setVisible(false);
            jLabelPosDecimal.setVisible(true);
            jSpinnerdecimal.setVisible(true);
            jLabelFormat.setVisible(true);
            jScrollPaneFormat.setVisible(true);
            jTextFieldExample.setVisible(true);
            if (m_OldFormatSelected.getVisualFormatter().lastIndexOf(decimalSeparatorOldFormat) >= 0) {
                int cantDec = (m_OldFormatSelected.getVisualFormatter().length() - 1) -( m_OldFormatSelected.getVisualFormatter().lastIndexOf(decimalSeparatorOldFormat)+1);
                jSpinnerdecimal.setValue(new Integer(cantDec));
            }
            else{
                jSpinnerdecimal.setValue(new Integer(0));
            }
            jTextAreaDescription.setText(CMMessages.getString("TESTDATA_PERCENTAGE_FORMAT_DESCRIPTION")); //$NON-NLS-1$
            createExamplePercent(jSpinnerdecimal.getValue().toString());
            formatResult=jTextFieldExample.getText();
            result = true;
        }
        return result;
    }

    public boolean isOldFormatNumeric() {
        int indexOldFormat = -1;
        boolean result = false;
       /* if (m_OldFormatSelected.getVisualFormatter().lastIndexOf(decimalSeparatorOldFormat) > 0) {
            for (int i = 0; i < (BusinessRules.TESTDATA_FORMAT_NUMERIC).length; i++) {
                String format = BusinessRules.TESTDATA_FORMAT_NUMERIC[i];
                char lastChar = m_OldFormatSelected.getVisualFormatter().charAt(0);
                StringBuffer reBuiltFormat = new StringBuffer();
                for (int j = 0; j < format.length(); j++) {
                    if (m_OldFormatSelected.getVisualFormatter().length() > j) {
                        lastChar = m_OldFormatSelected.getVisualFormatter().charAt(j);
                        reBuiltFormat.append(lastChar);
                    }
                    else {
                        reBuiltFormat.append(lastChar);
                    }
                }
                if (BusinessRules.TESTDATA_FORMAT_NUMERIC[i].equals(reBuiltFormat.toString())) {
                    indexOldFormat = i;
                    break;
                }
            }
        }
        else {
            for (int i = 0; i < (BusinessRules.TESTDATA_FORMAT_NUMERIC).length; i++) {
                String format = BusinessRules.TESTDATA_FORMAT_NUMERIC[i];
                StringBuffer reBuiltFormat = new StringBuffer();
                for (int j = 0; j < format.length(); j++) {
                    if (m_OldFormatSelected.length() > j) {
                        char lastChar = m_OldFormatSelected.charAt(j);
                        reBuiltFormat.append(lastChar);
                    }
                    else {
                        reBuiltFormat.append(format.charAt(j));
                    }
                }
                if (BusinessRules.TESTDATA_FORMAT_NUMERIC[i].equals(reBuiltFormat.toString())) {
                    indexOldFormat = i;
                    break;
                }
            }
        }*/
        indexOldFormat = Arrays.binarySearch(BusinessRules.TESTDATA_FORMAT_NUMERIC, m_OldFormatSelected.getOriginalFormatter());

        if (indexOldFormat >= 0) {
            jListFormat.setListData(BusinessRules.TESTDATA_FORMAT_NUMERIC);
            jListCategory.setSelectedIndex(2);
            jListCategory.revalidate();
            jListFormat.setSelectedIndex(indexOldFormat);
            jListFormat.revalidate();
            jLabelSimbol.setVisible(false);
            jComboBoxSimbol.setVisible(false);
            jLabelPosDecimal.setVisible(true);
            jSpinnerdecimal.setVisible(true);
            jLabelFormat.setVisible(true);
            jScrollPaneFormat.setVisible(true);
            jTextFieldExample.setVisible(true);
            if (m_OldFormatSelected.getVisualFormatter().lastIndexOf(decimalSeparatorOldFormat) >= 0) {
                int cantDec = (m_OldFormatSelected.getVisualFormatter().length() - 1) - m_OldFormatSelected.getVisualFormatter().lastIndexOf(decimalSeparatorOldFormat);
                jSpinnerdecimal.setValue(new Integer(cantDec));
            }
            else{
                jSpinnerdecimal.setValue(new Integer(0));
            }
            createExampleNum(jSpinnerdecimal.getValue().toString());
            formatResult =jTextFieldExample.getText();// jListFormat.getSelectedValue().toString();
            jTextAreaDescription.setText(CMMessages.getString("TESTDATA_NUMERIC_FORMAT_DESCRIPTION")); //$NON-NLS-1$
            result = true;
        }
        return result;
    }

    public boolean isOldFormatMoney() {
        boolean result = false;
        int indexJComboBox = -1;
        int indexOldFormat = -1;
        /*for (int i = 0; i < BusinessRules.TESTDATA_FORMAT_MONEYSIGN.length; i++) {
            moneySign = BusinessRules.TESTDATA_FORMAT_MONEYSIGN[i];
            if (m_OldFormatSelected.indexOf(moneySign) >= 0) {
                indexJComboBox = i;
                break;
            }
        }
        if (indexJComboBox >= 0) {
            if (m_OldFormatSelected.indexOf(moneySign) == 0)
                indexOldFormat = 2;
            else {
                if (m_OldFormatSelected.indexOf(" ") > 0)
                    indexOldFormat = 0;
                else
                    indexOldFormat = 1;
            }
        }*/
        indexOldFormat = Arrays.binarySearch(BusinessRules.TESTDATA_FORMAT_MONEYSIGNC, m_OldFormatSelected.getOriginalFormatter());
        if(indexOldFormat <0){
            indexOldFormat = Arrays.binarySearch(BusinessRules.TESTDATA_FORMAT_MONEYSIGND, m_OldFormatSelected.getOriginalFormatter());
            if(indexOldFormat <0){
            	indexOldFormat = Arrays.binarySearch(BusinessRules.TESTDATA_FORMAT_MONEYSIGNE, m_OldFormatSelected.getOriginalFormatter());
            
            	if(indexOldFormat <0){
            		indexOldFormat = Arrays.binarySearch(BusinessRules.TESTDATA_FORMAT_MONEYSIGNI, m_OldFormatSelected.getOriginalFormatter());
            	
            		if(indexOldFormat <0){
            			indexOldFormat = Arrays.binarySearch(BusinessRules.TESTDATA_FORMAT_MONEYSIGNP, m_OldFormatSelected.getOriginalFormatter());
            		}
            	}
            }
        }


        	

        if (indexOldFormat >= 0) {
        	for (int i = 0; i < BusinessRules.TESTDATA_FORMAT_MONEYSIGN.length; i++) {
                String moneySign = BusinessRules.TESTDATA_FORMAT_MONEYSIGN[i];
                if (m_OldFormatSelected.getOriginalFormatter().indexOf(moneySign) >= 0) {
                    indexJComboBox = i;
                    break;
                }
            }
            jListCategory.setSelectedIndex(1);
            jComboBoxSimbol.setSelectedIndex(indexJComboBox);
            jComboBoxSimbolItemStateChanged(null);
            jListCategory.revalidate();
            jListFormat.setSelectedIndex(indexOldFormat);
            jListFormat.revalidate();
            jLabelSimbol.setVisible(true);
            jComboBoxSimbol.setVisible(true);
            jLabelPosDecimal.setVisible(true);
            jSpinnerdecimal.setVisible(true);
            jLabelFormat.setVisible(true);
            jScrollPaneFormat.setVisible(true);
            jTextFieldExample.setVisible(true);
            if (m_OldFormatSelected.getVisualFormatter().lastIndexOf(decimalSeparatorOldFormat) >= 0) {
                int cantDec = ((m_OldFormatSelected.getVisualFormatter().length() - 1) - m_OldFormatSelected.getVisualFormatter().lastIndexOf(decimalSeparatorOldFormat))-((m_OldFormatSelected.getVisualFormatter().length() - 1) - m_OldFormatSelected.getVisualFormatter().lastIndexOf("#"));
                jSpinnerdecimal.setValue(new Integer(cantDec));
            }
            else{
                jSpinnerdecimal.setValue(new Integer(0));
            }
            createExampleMoney(jSpinnerdecimal.getValue().toString());
            formatResult =jTextFieldExample.getText(); //jListFormat.getSelectedValue().toString();
            jTextAreaDescription.setText(CMMessages.getString("TESTDATA_MONEY_FORMAT_DESCRIPTION")); //$NON-NLS-1$
            result = true;
        }
        return result;
    }

    public void setOldFormatSelected() {
        if (!isOldFormatDate())
            if (!isOldFormatPercent())
                if (!isOldFormatMoney())
                    if (!isOldFormatNumeric()) {
                        jListCategory.setSelectedIndex(4);
                        jListCategory.revalidate();
                        jListFormat.revalidate();
                        jListFormat.setVisible(false);
                        jLabelSimbol.setVisible(false);
                        jComboBoxSimbol.setVisible(false);
                        jLabelPosDecimal.setVisible(false);
                        jSpinnerdecimal.setVisible(false);
                        jLabelFormat.setVisible(false);
                        jScrollPaneFormat.setVisible(false);
                        jTextFieldExample.setVisible(false);
                        jTextAreaDescription.setText(CMMessages.getString("TESTDATA_TEXT_FORMAT_DESCRIPTION")); //$NON-NLS-1$
                        formatResult = BusinessRules.FORMULAS_FORMAT_STRING; //$NON-NLS-1$
                    }
    }

    //hcanedo_21_09_2004_end
    public void charge() {
        jListCategory.setListData(BusinessRules.TESTDATA_FORMAT_CATEGORY);
        jListFormat.setListData(BusinessRules.TESTDATA_FORMAT_DATE);
//hcanedo_20_10_2004_begin
        setTitle(CMMessages.getString("TESTDATA_TITLE_DIALOG_FORMATS")); //$NON-NLS-1$
//hcanedo_20_10_2004_end
        //hcanedo_21_09_2004_begin
        setOldFormatSelected();

  /*     jListCategory.setSelectedIndex(0);
		jListFormat.setSelectedIndex(0);

        jListCategory.revalidate();
        jListFormat.revalidate();
        jLabelSimbol.setVisible(false);
        jComboBoxSimbol.setVisible(false);
        jLabelPosDecimal.setVisible(false);
        jSpinnerdecimal.setVisible(false);
      //  jTextFieldPosDecimal.setVisible(false);
        jTextFieldExample.setVisible(true);
      //  jTextFieldPosDecimal.setText("2"); //$NON-NLS-1$
       // jSpinnerdecimal.setValue(2);
        //jTextFieldExample.setText(BusinessRules.FORMULAS_FORMAT_DATE_EXAMPLE[0]);
        jTextAreaDescription.setText(CMMessages.getString("TESTDATA_DATE_AND_TIME_FORMAT_DESCRIPTION")); //$NON-NLS-1$
        jTextFieldExample.setText(BusinessRules.FORMULAS_FORMAT_DATE_EXAMPLE[jListFormat.getSelectedIndex()]);*/

        //hcanedo_21_09_2004_end
        //       formatResult=jListFormat.getSelectedValue().toString();
    }

    public JFormattedTextField getTextField(JSpinner spinner) {
        JComponent editor = spinner.getEditor();
        if (editor instanceof JSpinner.NumberEditor) {
            return ((JSpinner.NumberEditor) editor).getTextField();
        } else {
            System.err.println("Unexpected editor type: " + spinner.getEditor().getClass() +
                " isn't a descendant of DefaultEditor");
            return null;
        }
    }

    public void jListCategoryValueChanged(ListSelectionEvent e) {
        int index = jListCategory.getSelectedIndex();
        if (index == 0) {
            jListFormat.setVisible(true);
            jListFormat.setListData(BusinessRules.TESTDATA_FORMAT_DATE);
            jLabelSimbol.setVisible(false);
            jComboBoxSimbol.setVisible(false);
            jLabelPosDecimal.setVisible(false);
            //	jSpinnerdecimal.setVisible(false);
            //	jTextFieldPosDecimal.setVisible(false);
            jLabelFormat.setVisible(true);
            jScrollPaneFormat.setVisible(true);
            jTextFieldExample.setVisible(true);
//hcanedo_21_09_2004_begin
            jSpinnerdecimal.setVisible(false);
//hcanedo_21_09_2004_end
            //  jTextFieldPosDecimal.setText("2"); //$NON-NLS-1$
            //jSpinnerdecimal.setValue(2);
            jListFormat.setSelectedIndex(0);
            jTextAreaDescription.setText(CMMessages.getString("TESTDATA_DATE_AND_TIME_FORMAT_DESCRIPTION")); //$NON-NLS-1$
        }
        else {
            if (index == 1) {
                jListFormat.setVisible(true);
                jListFormat.setListData(BusinessRules.TESTDATA_FORMAT_MONEYSIGNE);
                jLabelSimbol.setVisible(true);
                jComboBoxSimbol.setVisible(true);
                jLabelPosDecimal.setVisible(true);
                jSpinnerdecimal.setVisible(true);
                //	jTextFieldPosDecimal.setVisible(true);
                jLabelFormat.setVisible(true);
                jScrollPaneFormat.setVisible(true);
                jTextFieldExample.setVisible(true);
                //   jTextFieldPosDecimal.setText("2"); //$NON-NLS-1$
                //jSpinnerdecimal.setValue(2);
                jListFormat.setSelectedIndex(0);
                jTextAreaDescription.setText(CMMessages.getString("TESTDATA_MONEY_FORMAT_DESCRIPTION")); //$NON-NLS-1$
            }
            else {
                if (index == 2) {
                    jListFormat.setVisible(true);
                    jListFormat.setListData(BusinessRules.TESTDATA_FORMAT_NUMERIC);
                    jLabelSimbol.setVisible(false);
                    jComboBoxSimbol.setVisible(false);
                    jLabelPosDecimal.setVisible(true);
                    jSpinnerdecimal.setVisible(true);
                    //	jTextFieldPosDecimal.setVisible(true);
                    jLabelFormat.setVisible(true);
                    jScrollPaneFormat.setVisible(true);
                    jTextFieldExample.setVisible(true);
                    //	jTextFieldPosDecimal.setText("2"); //$NON-NLS-1$
                    //      jSpinnerdecimal.setValue(2);
                    jListFormat.setSelectedIndex(0);
                    jTextAreaDescription.setText(CMMessages.getString("TESTDATA_NUMERIC_FORMAT_DESCRIPTION")); //$NON-NLS-1$
                }
                else {
                    if (index == 3) {
                    	jListFormat.setVisible(true);
                    	jListFormat.setListData(BusinessRules.TESTDATA_FORMAT_PERCENTAGE);
                        jLabelSimbol.setVisible(false);
                        jComboBoxSimbol.setVisible(false);
                        jLabelPosDecimal.setVisible(true);
                        jSpinnerdecimal.setVisible(true);
                        //jTextFieldPosDecimal.setVisible(true);
                        jLabelFormat.setVisible(true);
                        jScrollPaneFormat.setVisible(true);
                        //                        jSpinnerdecimal.setValue(0);
                        jTextFieldExample.setVisible(true);
                        jListFormat.setSelectedIndex(0);
                        jTextAreaDescription.setText(CMMessages.getString("TESTDATA_PERCENTAGE_FORMAT_DESCRIPTION")); //$NON-NLS-1$
                        createExamplePercent(jSpinnerdecimal.getValue().toString());
                    }
                    else {
                        if (index == 4) {
                            jListFormat.setVisible(false);
                            jLabelSimbol.setVisible(false);
                            jComboBoxSimbol.setVisible(false);
                            jLabelPosDecimal.setVisible(false);
                            jSpinnerdecimal.setVisible(false);
                            //jTextFieldPosDecimal.setVisible(false);
                            jLabelFormat.setVisible(false);
                            jScrollPaneFormat.setVisible(false);
                            jTextFieldExample.setVisible(false);
                            jTextAreaDescription.setText(CMMessages.getString("TESTDATA_TEXT_FORMAT_DESCRIPTION")); //$NON-NLS-1$
                            formatResult = BusinessRules.FORMULAS_FORMAT_STRING; //$NON-NLS-1$
                        }
                    }
                }
            }
        }
    }

    public Object calculeDecimals() {
        String cantDecimals = jListFormat.getSelectedValue().toString().trim();
        cantDecimals = CMFormatFactory.clearMoneyPercentSign(cantDecimals).trim();
        if (cantDecimals == null)
            return new Integer(0);
        else {
            if (cantDecimals.indexOf(decimalSeparator) == -1)
                return new Integer(0);
            else {
                return new Integer(cantDecimals.length() - cantDecimals.indexOf(decimalSeparator) - 1);
            }
        }
    }

    public void jListFormatValueChanged(ListSelectionEvent e) {
        int index = jListCategory.getSelectedIndex();
        if (jListFormat.getSelectedIndex() > -1)
            if (index == 0) {
                jTextFieldExample.setText(BusinessRules.FORMULAS_FORMAT_DATE_EXAMPLE[jListFormat.getSelectedIndex()]);
                formatResult = jListFormat.getSelectedValue().toString();
            }
            else {
                if (index == 1) {
                   // int indexVisualPattern=Arrays.binarySearch(BusinessRules.TESTDATA_FORMAT_VISUAL_FORMATTERS,jListFormat.getSelectedValue().toString());
        			if(isVisualFormatter(jListFormat.getSelectedValue().toString()))
        			{
            			decimalSeparator=",";
        			}
        			else{
            			decimalSeparator=".";
        			}
                    jSpinnerdecimal.setValue(calculeDecimals());
                    ejem = reemplaceX(jListFormat.getSelectedValue().toString());
                    jTextFieldExample.setText(ejem);
                    formatResult = jListFormat.getSelectedValue().toString();
                }
                else {
                    if (index == 2) {
                       // int indexVisualPattern=Arrays.binarySearch(BusinessRules.TESTDATA_FORMAT_VISUAL_FORMATTERS,jListFormat.getSelectedValue().toString());
        				if(isVisualFormatter(jListFormat.getSelectedValue().toString()))
        				{
            				decimalSeparator=",";
        				}
        				else{
            				decimalSeparator=".";
        				}
                        jSpinnerdecimal.setValue(calculeDecimals());
                        ejem = reemplaceX(jListFormat.getSelectedValue().toString());
                        jTextFieldExample.setText(ejem);
                        formatResult = jListFormat.getSelectedValue().toString();
                    }
                    else {
                        if (index == 3) {
                           // int indexVisualPattern=Arrays.binarySearch(BusinessRules.TESTDATA_FORMAT_VISUAL_FORMATTERS,jListFormat.getSelectedValue().toString());
                        	if(isVisualFormatter(jListFormat.getSelectedValue().toString()))
            				{
                				decimalSeparator=",";
            				}
            				else{
                				decimalSeparator=".";
            				}
                            jSpinnerdecimal.setValue(calculeDecimals());
                            //ejem = reemplaceX(jListFormat.getSelectedValue().toString());
                            jTextFieldExample.setText(jListFormat.getSelectedValue().toString());
                            formatResult = jListFormat.getSelectedValue().toString();
                        }
                    }
                }
            }
    }

    public void jComboBoxSimbolItemStateChanged(ItemEvent e) {
        int index = jComboBoxSimbol.getSelectedIndex();
        if (index == 0) {
            jListFormat.setListData(BusinessRules.TESTDATA_FORMAT_MONEYSIGNE);
            jListFormat.setSelectedIndex(0);
        }
        else {
            if (index == 1) {
                jListFormat.setListData(BusinessRules.TESTDATA_FORMAT_MONEYSIGND);
                jListFormat.setSelectedIndex(0);
            }
            else {
                if (index == 2) {
                    jListFormat.setListData(BusinessRules.TESTDATA_FORMAT_MONEYSIGNC);
                    jListFormat.setSelectedIndex(0);
                }
                else {
                    if (index == 3) {
                        jListFormat.setListData(BusinessRules.TESTDATA_FORMAT_MONEYSIGNI);
                        jListFormat.setSelectedIndex(0);
                    }
                    else {
                        if (index == 4) {
                            jListFormat.setListData(BusinessRules.TESTDATA_FORMAT_MONEYSIGNP);
                            jListFormat.setSelectedIndex(0);
                        }
                    }
                }
            }
        }
    }

 

    public void jSpinnerDecimalChangeEvent(ChangeEvent e) {
        SpinnerModel numberModel = jSpinnerdecimal.getModel();
        if (numberModel instanceof SpinnerNumberModel) {
            if (jListCategory.getSelectedIndex() == 3)
                createExamplePercent(jSpinnerdecimal.getValue().toString());
            if (jListCategory.getSelectedIndex() == 2)
                createExampleNum(jSpinnerdecimal.getValue().toString());
            if (jListCategory.getSelectedIndex() == 1)
                createExampleMoney(jSpinnerdecimal.getValue().toString());
        }
    }

    public void jButtonOkActionPerformed(ActionEvent e) {
        dispose();
        buttonOkclicked = true;
        TestObject currentTestObject=m_Frame.getTreeWorkspaceView().getCurrentTestObject();
        if(currentTestObject.getDecimalSeparator().equals(",")){
        	CMFormatFactory.setTestObjectLocale(new Locale("de","DE"));
        }
        else{
        	CMFormatFactory.setTestObjectLocale(new Locale("en", "US"));
        }
        formatterResult= createFormatterResult();        
    }

    public void jButtonCancelActionPerformed(ActionEvent e) {
        buttonOkclicked = false;
        dispose();
    }

    public String getFormatResult() { return formatResult; }

    public void setFormatResult(String formatResult) { this.formatResult = formatResult; }

    public void createExamplePercent(String cant) {
    	String entero = ((String)jListFormat.getSelectedValue());
        if (entero.indexOf(decimalSeparator) != -1)
            entero = entero.substring(0, entero.indexOf(decimalSeparator));
        int cantidad = Integer.parseInt(cant);
        StringBuffer example = new StringBuffer(entero); //$NON-NLS-1$
        if (cantidad > 0)
            example.append(decimalSeparator);
        for (int i = 0; i < cantidad; i++)
            example.append("#"); //$NON-NLS-1$
        example.append("%"); //$NON-NLS-1$
        formatResult = example.toString();
        jTextFieldExample.setText(example.toString());
    }

    public void createExampleNum(String cant) {
        String entero = ((String)jListFormat.getSelectedValue());
        if (entero == null) {
            return;
        }
        if (entero.indexOf(decimalSeparator) != -1)
            entero = entero.substring(0, entero.indexOf(decimalSeparator));
        int cantidad = Integer.parseInt(cant);
        StringBuffer example = new StringBuffer(entero);
        if (cantidad > 0)
            example.append(decimalSeparator);
        if (jListFormat.getSelectedIndex() == 1 || jListFormat.getSelectedIndex() == 4) {
            for (int i = 0; i < cantidad; i++)
                example.append("0"); //$NON-NLS-1$
        }
        else if (jListFormat.getSelectedIndex() == 2 || jListFormat.getSelectedIndex() == 5) {
            for (int i = 0; i < cantidad; i++)
                example.append("0"); //$NON-NLS-1$
        }
        else {
            for (int i = 0; i < cantidad; i++)
                example.append("#"); //$NON-NLS-1$
        }
        formatResult = example.toString();
        jTextFieldExample.setText(example.toString());
    }

    public void createExampleMoney(String cant) {
        String entero = ((String)jListFormat.getSelectedValue());
        if (entero.indexOf(decimalSeparator) != -1)
            entero = entero.substring(0, entero.indexOf(decimalSeparator));
        int cantidad = Integer.parseInt(cant);
        StringBuffer example = new StringBuffer(entero); //$NON-NLS-1$
        if (cantidad > 0)
            example.append(decimalSeparator);
        for (int i = 0; i < cantidad; i++)
            example.append("#"); //$NON-NLS-1$
        if (jListFormat.getSelectedIndex() == 0 || jListFormat.getSelectedIndex() == 2) {
            example.append(" " + jComboBoxSimbol.getSelectedItem().toString()); //$NON-NLS-1$
        }
 /*       if (jListFormat.getSelectedIndex() == 1) {
            example.insert(0, jComboBoxSimbol.getSelectedItem().toString());
        }*/
        if (jListFormat.getSelectedIndex() == 1 || jListFormat.getSelectedIndex() == 3) {
            example.append(jComboBoxSimbol.getSelectedItem().toString());
        }
        formatResult = example.toString();
        jTextFieldExample.setText(example.toString());
    }

    public String reemplaceX(String p_ej) {
        String newstring = new String(p_ej);
        newstring = newstring.replace('#', '1');
        return newstring;
    }

     private  boolean isVisualFormatter(String pattern)
    {
     	if(pattern.indexOf("%")>0 && pattern.indexOf(",")>0){
    		return true;
    	}
    	else if(pattern.indexOf("%")>0&& pattern.indexOf(",") < 0){
    		return false;
    	}
    	
        int indexVisualPattern=Arrays.binarySearch(BusinessRules.TESTDATA_FORMAT_VISUAL_FORMATTERS,pattern);
        if(indexVisualPattern>=0)
        {
            return true;
        }
        else
        {
            Vector visualFormaters= new Vector(Arrays.asList(BusinessRules.TESTDATA_FORMAT_VISUAL_FORMATTERS));
			boolean result=false;
            for(int i=0; i< visualFormaters.size();i++)
            {
                String formatvisual=visualFormaters.elementAt(i).toString();

                if(!result &&(pattern.startsWith(formatvisual)|| formatvisual.startsWith(pattern))){
                    result=true;//return true;
                }
                else{
                    if(!result)
                    result= ismodificVisualFormula(formatvisual,pattern);
                }
            }

            return result;
        }
    }
    private  boolean ismodificVisualFormula(String visual, String pattern)
    {
        try{
        int lengthVisual= visual.length();
        Logger.getLogger(this.getClass()).debug(lengthVisual);
        int lengthPattern=pattern.length();
        Logger.getLogger(this.getClass()).debug(lengthPattern);
		int indexVisual=0;
        int indexPattern=0;
        char ccVisual= visual.charAt(0);
        char ccPattern=pattern.charAt(0);
        boolean resultFinal=false;
        while(!resultFinal){
        	Logger.getLogger(this.getClass()).debug(indexVisual);
        	Logger.getLogger(this.getClass()).debug(indexPattern);
			if(ccVisual== ccPattern){

                if(indexVisual<lengthVisual)
             			ccVisual= visual.charAt(indexVisual);
                if(indexPattern<lengthPattern)
             			ccPattern=pattern.charAt(indexPattern);
                indexVisual++;
        	    indexPattern++;
            }
            else{
                if(/*ccVisual=='.'||*/ ccVisual==','|| ccVisual=='#')
                {

                	if(indexVisual<lengthVisual)
             			ccVisual= visual.charAt(indexVisual);
                	indexVisual++;
                }
                else
                if(/*ccPattern=='.'|| ccPattern==','||*/ ccPattern=='#')
                {

          		      if(indexPattern<lengthPattern)
             			ccPattern=pattern.charAt(indexPattern);
          			indexPattern++;
                }
                else
                    return false;
            }
            if(indexVisual==lengthVisual && indexPattern==lengthPattern)
                return true;
            else
                if(indexVisual>lengthVisual || indexPattern>lengthPattern)
                	return false;
        }

        return ccPattern==ccVisual;
        }
        catch(Exception ex)
        {
            return false;
        }
    }

    private TestDataFormat createFormatterResult(){
    	TestDataFormat format= new TestDataFormat();
    	String visulaFormatter= getFormatResult();
    	Locale locale=new Locale("en", "US");
    	String realFormat= getFormatResult();
    	boolean isVisualFormatter=false;
    	String originalFormatter= (String)jListFormat.getSelectedValue();
    	int indexVisualPattern=Arrays.binarySearch(BusinessRules.TESTDATA_FORMAT_VISUAL_FORMATTERS,originalFormatter);
    	if(indexVisualPattern >=0){
    		locale = new Locale("de","DE");
    		isVisualFormatter=true;
    		StringBuffer newPatternForVisual= new StringBuffer();
            for(int i=0; i<realFormat.length();i++)
            {
               char cc=realFormat.charAt(i);
               if(cc==','){
                   newPatternForVisual.append(".");
               }
               else{
                   if(cc=='.'){
                       newPatternForVisual.append(",");
                   }
                   else{
                       newPatternForVisual.append(cc);
                   }
               }

            }
            realFormat=newPatternForVisual.toString();
    	}
    	format.setFormatLocale(locale);
    	format.setOriginalFormatter(originalFormatter);
    	format.setRealFormat(realFormat);
    	format.setVisualFormatter(visulaFormatter);
    	format.setVisualFormat(isVisualFormatter);
    	return format;
    }
    private JScrollPane jScrollPaneCategory = new JScrollPane();
    private JList jListCategory = new JList();
    private JPanel jPanelExample = new JPanel();
    private JTextField jTextFieldExample = new JTextField();
    //  private JTextField jTextFieldPosDecimal = new JTextField();
    private JLabel jLabelPosDecimal = new JLabel();
    private JLabel jLabelSimbol = new JLabel();
    private CMBaseJComboBox jComboBoxSimbol;
    private JScrollPane jScrollPaneFormat = new JScrollPane();
    private JList jListFormat = new JList();
    private JLabel jLabelCategory = new JLabel();
    private JLabel jLabelFormat = new JLabel();
    //private JScrollBar jSpinnerdecimal = new JScrollBar();
    private JScrollPane jScrollPaneDescription = new JScrollPane();
    private JTextArea jTextAreaDescription = new JTextArea();
    private JButton jButtonOk = new JButton();
    private JButton jButtonCancel = new JButton();
    private String formatResult;
    private CMFrameView m_Frame;
    public boolean buttonOkclicked;
    private String ejem;
    Integer value = new Integer(2);
    Integer min = new Integer(0);
    Integer max = new Integer(15);
    Integer step = new Integer(1);
    SpinnerNumberModel model = new SpinnerNumberModel(value, min, max, step);
    JSpinner jSpinnerdecimal = new JSpinner(model);
    JFormattedTextField ftf = null;
    //hcanedo_21_09_2004_begin
    TestDataFormat m_OldFormatSelected =new TestDataFormat(); //BusinessRules.TESTDATA_FORMAT_CATEGORY[BusinessRules.TESTDATA_FORMAT_CATEGORY.length - 1];
    String decimalSeparatorOldFormat=".";
    String decimalSeparator=".";
    private TestDataFormat formatterResult;
    //hcanedo_21_09_2004_end
	public TestDataFormat getFormatterResult() {
		return formatterResult;
	}

	protected List getOrder() {
		List focusOrder= new ArrayList();
		int index = jListCategory.getSelectedIndex();
		switch (index) {
		case 0:
			focusOrder.add(jListCategory);
			focusOrder.add(jListFormat);
			focusOrder.add(jButtonOk);
			focusOrder.add(jButtonCancel);
			break;
		case 1:
			focusOrder.add(jListCategory);
			focusOrder.add(jSpinnerdecimal);
			focusOrder.add(jComboBoxSimbol);
			focusOrder.add(jListFormat);
			focusOrder.add(jButtonOk);
			focusOrder.add(jButtonCancel);
			break;
		case 2:
			focusOrder.add(jListCategory);
			focusOrder.add(jSpinnerdecimal);
			focusOrder.add(jListFormat);
			focusOrder.add(jButtonOk);
			focusOrder.add(jButtonCancel);
			break;
		case 3:
			focusOrder.add(jListCategory);
			focusOrder.add(jSpinnerdecimal);
			focusOrder.add(jListFormat);
			focusOrder.add(jButtonOk);
			focusOrder.add(jButtonCancel);
			break;
		case 4:
			focusOrder.add(jListCategory);
			focusOrder.add(jButtonOk);
			focusOrder.add(jButtonCancel);
			break;
			
		default:
			focusOrder.add(jListCategory);
			focusOrder.add(jSpinnerdecimal);
			focusOrder.add(jComboBoxSimbol);
			focusOrder.add(jListFormat);
			focusOrder.add(jButtonOk);
			focusOrder.add(jButtonCancel);
			break;
		}
		return focusOrder;
	}

	protected void PressJButtonCancel(Object object) {
		jButtonCancelActionPerformed(null);
	}

	protected void PressJButtonOk(Object object) {
		jButtonOkActionPerformed(null);
	}
}
