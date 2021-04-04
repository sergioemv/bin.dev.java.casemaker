/*
 * This form sucks.
 */
package bi.view.testdataviews;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.BusinessRules;

import org.apache.log4j.Logger;

import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMFrameView;
import bi.view.utils.CMBaseJComboBox;
import bi.view.utils.JCustomDialog;

/**
 * Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
public class CMDialogRenameReport extends JCustomDialog {
    
    private String date;
    private int selected;
    
    private JPanel panel6 = new JPanel();
    private JLabel labelOpen = new JLabel();
    private JPanel panel7 = new JPanel();
    private CMBaseJComboBox comboBox = new CMBaseJComboBox(this);
    
    private JPanel panel8 = new JPanel();
    private JLabel labelPath = new JLabel();
    private JPanel panel9 = new JPanel();
    private JTextField fieldPath = new JTextField();    
    
    private JPanel panel10 = new JPanel();
    private JLabel labelDate = new JLabel();
    private JPanel panel11 = new JPanel();
    private JTextField fieldDate = new JTextField();    
    
    
//hcanedo_21_09_2004_begin
    public CMDialogRenameReport(CMFrameView p_CMFrameView, String p_FilePath, String p_Param, String date, ComboBoxModel comboBoxModel, boolean is_External) {
        super(p_CMFrameView);
        this.date = date;
        m_CMFrameView = p_CMFrameView;
        m_FilePath = p_FilePath;
        m_Parameter=p_Param;
        m_isExternal=is_External;
        if (comboBoxModel != null) {
            comboBox.setModel(comboBoxModel);
        }
        initGUI();
    }
//hcanedo_21_09_2004_end
//HCanedo_22112004_Begin
    public void initGUI() {
//hcanedo_21_09_2004_begin

        jLabelName.setText(CMMessages.getString("TESTDATASET_REPORT_NEW_NAME"));
        jPanel2.setLayout(new java.awt.BorderLayout());
        jPanel2.setPreferredSize(new java.awt.Dimension(60, 20));
        jPanel2.add(jLabelName, java.awt.BorderLayout.CENTER);
        jLabelParameter.setText(CMMessages.getString("LABEL_OPEN_PARAMETER_REPORTS"));
        jPanel4.setLayout(new java.awt.BorderLayout());
        jPanel4.setPreferredSize(new java.awt.Dimension(60, 20));
        jPanel4.add(jLabelParameter, java.awt.BorderLayout.CENTER);
        
        labelOpen.setText(CMMessages.getString("LABEL_OPEN_WITH_REPORTS"));
        panel6.setLayout(new java.awt.BorderLayout());
        panel6.setPreferredSize(new java.awt.Dimension(60, 20));
        panel6.add(labelOpen, java.awt.BorderLayout.CENTER);
        
        labelDate.setText(CMMessages.getString("LABEL_DATE_REPORTS"));
        panel10.setLayout(new java.awt.BorderLayout());
        panel10.setPreferredSize(new java.awt.Dimension(60, 20));
        panel10.add(labelDate, java.awt.BorderLayout.CENTER);        
        
        labelPath.setText(CMMessages.getString("LABEL_FILE_PATH_REPORTS"));
        panel8.setLayout(new java.awt.BorderLayout());
        panel8.setPreferredSize(new java.awt.Dimension(60, 20));
        panel8.add(labelPath, java.awt.BorderLayout.CENTER);        
        
        comboBox.setPreferredSize(new java.awt.Dimension(200, 20));
        comboBox.setMinimumSize(new java.awt.Dimension(200, 20));
        comboBox.setSize(new java.awt.Dimension(200,20));
        
        
        File currentFile= new File(m_FilePath);
        if ( currentFile != null ) {
            fieldPath.setText(currentFile.getParent());
        }
        fieldPath.setEditable(false);
        fieldPath.setPreferredSize(new java.awt.Dimension(200, 20));
        fieldPath.setMinimumSize(new java.awt.Dimension(200, 20));
        fieldPath.setSize(new java.awt.Dimension(200,20));
        
        fieldDate.setText(date);
        fieldDate.setEditable(false);
        fieldDate.setPreferredSize(new java.awt.Dimension(200, 20));
        fieldDate.setMinimumSize(new java.awt.Dimension(200, 20));
        fieldDate.setSize(new java.awt.Dimension(200,20));        
        
        panel7.setLayout(new java.awt.CardLayout());
        panel7.add(comboBox, "Component_0");
        
        panel9.setLayout(new java.awt.CardLayout());
        panel9.add(fieldPath, "Component_0");        
        
        panel11.setLayout(new java.awt.CardLayout());
        panel11.add(fieldDate, "Component_0");        
        
        jTextFieldParameter.setText("");
        jTextFieldParameter.setPreferredSize(new java.awt.Dimension(200, 20));
        jTextFieldParameter.setMinimumSize(new java.awt.Dimension(200, 20));
        jTextFieldParameter.setSize(new java.awt.Dimension(200,20));
        jTextFieldName.setText("");
        jTextFieldName.setPreferredSize(new java.awt.Dimension(200, 20));
        jTextFieldName.setMinimumSize(new java.awt.Dimension(200, 20));
        jTextFieldName.setSize(new java.awt.Dimension(200,20));
        jPanelChangeName.setLayout(new java.awt.FlowLayout());
        jPanelChangeName.add(jPanel2);
        jPanelChangeName.add(jPanel3);
        jPanelChangeName.add(jPanel4);
        jPanelChangeName.add(jPanel5);
        
        jPanelChangeName.add(panel6);
        jPanelChangeName.add(panel7);
        jPanelChangeName.add(panel8);
        jPanelChangeName.add(panel9);
        jPanelChangeName.add(panel10);
        jPanelChangeName.add(panel11);        
        
        jPanel3.setLayout(new java.awt.BorderLayout());
        jPanel3.setSize(new java.awt.Dimension(281,20));
        jPanel3.add(jTextFieldName, java.awt.BorderLayout.CENTER);
        jPanel5.setLayout(new java.awt.CardLayout());
        jPanel5.setSize(new java.awt.Dimension(281,20));
        jPanel5.add(jTextFieldParameter, "Component_0");
        jTextFieldParameter.setText(m_Parameter);
        setTitle(CMMessages.getString("TESTDATASET_REPORTS_EDIT"));//"CaseMaker - Rename Report");
        if(m_isExternal)
            jTextFieldName.setEditable(false);
//hcanedo_21_09_2004_end
        //setBounds(new java.awt.Rectangle(0, 0, 320, 143));
        setBounds(new java.awt.Rectangle(0, 0, 320, 205));
        setModal(true);
        setResizable(false);
        getContentPane().setLayout(new java.awt.BorderLayout());
        getContentPane().add(jPanelChangeName, java.awt.BorderLayout.CENTER);
        getContentPane().add(jPanel1, java.awt.BorderLayout.NORTH);
        getContentPane().add(jPanelButtons, java.awt.BorderLayout.SOUTH);
        jButtonOK.setText(CMMessages.getString("BUTTON_OK"));
        jButtonOK.setToolTipText("");
 /*       jButtonOK.setMaximumSize(new java.awt.Dimension(150, 27));
        jButtonOK.setMinimumSize(new java.awt.Dimension(73, 27));
        jButtonOK.setSize(new java.awt.Dimension(73, 27));*/
        jButtonOK.setDefaultCapable(true);
    //    jButtonOK.setPreferredSize(new java.awt.Dimension(73, 27));
        jPanelButtons.add(jButtonOK);
        jPanelButtons.add(jButtonCancel);
        jButtonCancel.setText(CMMessages.getString("BUTTON_CANCEL"));
  /*     jButtonCancel.setMaximumSize(new java.awt.Dimension(150, 27));
        jButtonCancel.setMinimumSize(new java.awt.Dimension(73, 27));*/
     //   jButtonCancel.setPreferredSize(new java.awt.Dimension(73, 27));
        jPanel1.setPreferredSize(new java.awt.Dimension(10, 15));
        jPanel1.setMinimumSize(new java.awt.Dimension(10, 15));
        jButtonOK.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) { jButtonOKActionPerformed(e); }
            });
        jButtonCancel.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) { jButtonCancelActionPerformed(e); }
            });
        currentFile= new File(m_FilePath);
        if(currentFile!=null)
        	jTextFieldName.setText(getNameAndExtension(currentFile));
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    	Dimension dlgSize = this.getPreferredSize();
    	Point loc = getLocation();
    	this.setLocation((screenSize.width - dlgSize.width) / 2 + 500, (screenSize.height - dlgSize.height) / 2);
//Hcanedo_07102005_begin
    	getRootPane().setDefaultButton(jButtonOK);
//hcanedo_07102005_end    	
    }
//HCanedo_22112004_End
	 public String getNameReport(File p_file)
    {
		StringBuffer name= new StringBuffer();
        File parent= p_file.getParentFile();
        if(parent !=null)
        {
        	name.append(parent.getName());
            name.append(".");
        }
        String filename=p_file.getName();
        int index= filename.lastIndexOf(".");
        if(index >= 0)
        	filename=filename.substring(0,index);
        name.append(filename);
        return name.toString();
    }
    public void jButtonOKActionPerformed(ActionEvent e) {
        File oldFile = new File(m_FilePath);
        selected = comboBox.getSelectedIndex();

        if (jTextFieldName.getText().trim() != "")
        {
            if (oldFile == null)
            {
                JOptionPane.showMessageDialog(m_CMFrameView,CMMessages.getString("TESTDATASETREPORTS_MENSSAGE_FILE_NOT_FOUND"),CMMessages.getString("TESTDATASETREPORTS_TITLE_INFORMATION"),JOptionPane.ERROR_MESSAGE);
            }
            else {
                String nameAndExtension = getNameAndExtension(oldFile);
                String auxp=jTextFieldName.getText().trim();
                if (!jTextFieldName.getText().trim().equals(nameAndExtension))
                {
                    String path = oldFile.getParent();
                   // String extension = getExtension(oldFile);
                    File newFile = new File(path +BusinessRules.URL_SEPARATOR +jTextFieldName.getText().trim() /*+ extension*/);
                    if (newFile.exists())
                    {
                /*el nombre ya existe*/
                        JOptionPane.showMessageDialog(m_CMFrameView,CMMessages.getString("TESTDATASETREPORTS_MENSSAGE_NAME_ALREADY_EXIST"),CMMessages.getString("TESTDATASETREPORTS_TITLE_INFORMATION"),JOptionPane.ERROR_MESSAGE);
                    }
                    else
                    {
                        try
                        {
                            boolean success = oldFile.renameTo(newFile);
                            if (!success) {
					/*no se puede cambiar el nombre*/
                                JOptionPane.showMessageDialog(m_CMFrameView,CMMessages.getString("TESTDATASETREPORTS_MENSSAGE_CANNOT_CHANGE_NAME"),CMMessages.getString("TESTDATASETREPORTS_TITLE_INFORMATION"),JOptionPane.ERROR_MESSAGE);
                                dispose();
                            }
                            else
                            {
                                nameReportModified=getNameReport(newFile);
                                isOKSelected=true;
                            	dispose();
                            }
                        }
                        catch (Exception ex)
                        {
                    /*archivo en uso*/
                            JOptionPane.showMessageDialog(m_CMFrameView,CMMessages.getString("TESTDATASETREPORTS_MENSSAGE_FILE_IN_USE"),CMMessages.getString("TESTDATASETREPORTS_TITLE_INFORMATION"),JOptionPane.ERROR_MESSAGE);
                            dispose();
                        }
                    }
                }
                else
                {
//hcanedo_21_09_2004_begin
                    if(!jTextFieldParameter.getText().trim().equals(m_Parameter))
                    {

        					m_Parameter=jTextFieldParameter.getText();
            				isOKSelected=true;
            				nameReportModified=getNameReport(oldFile);
            				Logger.getLogger(this.getClass()).info(m_Parameter.toString());
                            dispose();
                    }
                    else
                    {
//hcanedo_21_09_2004_end
                    	dispose();
//hcanedo_21_09_2004_begin
                    }
//hcanedo_21_09_2004_end
                }
            }
        }
        else
        {
            JOptionPane.showMessageDialog(m_CMFrameView,CMMessages.getString("TESTDATASETREPORTS_MENSSAGE_EMPTY_NAME"),CMMessages.getString("TESTDATASETREPORTS_TITLE_INFORMATION"),JOptionPane.INFORMATION_MESSAGE);
            /*nombre vacio*/
        }
    }

    public String getExtension(File f) {
        StringBuffer ext = new StringBuffer();
        String s = f.getName();
        int i = s.indexOf('.');
        if (i > 0 && i < s.length() - 1) {
            ext.append(s.substring(i + 1).toLowerCase());
        }
        ext.insert(0, "."); //$NON-NLS-1$
        return ext.toString();
    }

    public String getNameAndExtension(File f) {
      //  StringBuffer ext = new StringBuffer();
        String s = f.getName();
     /*   int i = s.indexOf('.');
        if (i > 0 && i < s.length() - 1) {
            ext.append(s.substring(0, i));
        }*/
        return s;
    }

    public void jButtonCancelActionPerformed(ActionEvent e) {
        dispose();
    }

    public String getM_FilePath() { return m_FilePath; }

    public void setM_FilePath(String m_FilePath) { this.m_FilePath = m_FilePath; }

    public CMFrameView getM_CMFrameView() { return m_CMFrameView; }

    public void setM_CMFrameView(CMFrameView m_CMFrameView) { this.m_CMFrameView = m_CMFrameView; }

    public boolean isIsOKSelected(){ return isOKSelected; }

    public void setIsOKSelected(boolean isOKSelected){ this.isOKSelected = isOKSelected; }

    public String getNameReportModified(){ return nameReportModified; }

    public void setNameReportModified(String nameReportModified){ this.nameReportModified = nameReportModified; }
//hcanedo_21_09_2004_begin
    public String getM_Parameter(){ return m_Parameter; }

    public void setM_Parameter(String m_Parameter){ this.m_Parameter = m_Parameter; }
//hcanedo_21_09_2004_end
    private JPanel jPanelChangeName = new JPanel();
    private JPanel jPanelButtons = new JPanel();
    private JButton jButtonOK = new JButton();
    private JButton jButtonCancel = new JButton();    
    private JLabel jLabelName = new JLabel();
    private JTextField jTextFieldName = new JTextField();
    private JLabel jLabelParameter = new JLabel();
    private JTextField jTextFieldParameter = new JTextField();
    private JPanel jPanel1 = new JPanel();
    private String m_FilePath;
    private CMFrameView m_CMFrameView;
    private boolean isOKSelected=false;
    private String nameReportModified;
//hcanedo_21_09_2004_begin
    private String m_Parameter;
    private boolean m_isExternal;
    private JPanel jPanel2 = new JPanel();
    private JPanel jPanel3 = new JPanel();
    private JPanel jPanel4 = new JPanel();
    private JPanel jPanel5 = new JPanel();
//hcanedo_21_09_2004_end
    /**
     * @return Returns the selected.
     */
    public int getSelected() {
        return selected;
    }
    /**
     * @param selected The selected to set.
     */
    public void setSelected(int selected) {
        this.selected = selected;
        comboBox.setSelectedIndex(selected);
    }
	protected List getOrder() {
		List focusOrder= new ArrayList();
		if(jTextFieldName.isEditable() && jTextFieldName.isEnabled())
			focusOrder.add(jTextFieldName);
		focusOrder.add(jTextFieldParameter);
		focusOrder.add(comboBox);
		focusOrder.add(jButtonOK);
		focusOrder.add(jButtonCancel);
		return focusOrder;
	}
	protected void PressJButtonCancel(Object object) {
		jButtonCancelActionPerformed(null);
		
	}
	protected void PressJButtonOk(Object object) {
		jButtonOKActionPerformed(null);
	}
}
