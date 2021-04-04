package bi.view.testdataviews;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMFrameView;
import bi.view.utils.JCustomDialog;

/**
 ****************************************************************************** Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */ 
public class CMDialogAddReportTestDataSetReports extends JCustomDialog {
    public CMDialogAddReportTestDataSetReports(CMFrameView p_Frame, String p_ReportsFilePath) {
       m_CMFrameView=p_Frame;
	
       initGUI();
    }

    public void initGUI() {
        getContentPane().add(jPanelNorth, java.awt.BorderLayout.NORTH);
        getContentPane().add(jPanelCenter, java.awt.BorderLayout.CENTER);
        getContentPane().add(jPanelSourth, java.awt.BorderLayout.SOUTH);
        jButtonOk.setText(CMMessages.getString("BUTTON_OK"));
//hcanedo_21_09_2004_begin
        jButtonOk.setPreferredSize(new java.awt.Dimension(79, 25));
        jButtonOk.setMinimumSize(new java.awt.Dimension(79, 25));
        jButtonOk.setMaximumSize(new java.awt.Dimension(79, 25));
        jButtonOk.setSize(new java.awt.Dimension(79,25));
//hcanedo_21_09_2004_end
        jPanelSourth.add(jButtonOk);
        jPanelSourth.add(jButtonCancel);
        jButtonCancel.setText(CMMessages.getString("BUTTON_CANCEL"));
        jButtonCancel.setMaximumSize(new java.awt.Dimension(79, 25));
        jButtonCancel.setMinimumSize(new java.awt.Dimension(79, 25));
        jButtonCancel.setPreferredSize(new java.awt.Dimension(79, 25));
        setBounds(new java.awt.Rectangle(0, 0, 520, 120));
        setResizable(false);
        setTitle(CMMessages.getString("TESTDATASET_LABEL_ADD_EXTERNAL_REPORTS"));
        setModal(true);
        jLabelPath.setText(CMMessages.getString("TESTDATA_IMPORT_FILE"));
        jPanelCenter.add(jLabelPath);
        jPanelCenter.add(jTextFieldPath);
        jPanelCenter.add(jButtonSearch);
        jTextFieldPath.setText("");
//hcanedo_21_09_2004_begin
        jTextFieldPath.setMinimumSize(new java.awt.Dimension(250, 23));
        jTextFieldPath.setPreferredSize(new java.awt.Dimension(250, 23));
        jTextFieldPath.setSize(new java.awt.Dimension(250, 23));
        jTextFieldPath.setEditable(false);
//hcanedo_21_09_2004_end
        jButtonSearch.setText(CMMessages.getString("TESTDATA_IMPORT_BROWSE"));
//hcanedo_21_09_2004_begin
        jButtonSearch.setPreferredSize(new java.awt.Dimension(79,25));
        jButtonSearch.setMinimumSize(new java.awt.Dimension(79,25));
        jButtonSearch.setMaximumSize(new java.awt.Dimension(79,25));
//hcanedo_21_09_2004_end
        jPanelNorth.setSize(new java.awt.Dimension(516,25));
        jPanelNorth.setPreferredSize(new java.awt.Dimension(10, 20));
        jButtonOk.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){jButtonOkActionPerformed(e);}});
        jButtonCancel.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){jButtonCancelActionPerformed(e);}});
        jButtonSearch.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){jButtonSearchActionPerformed(e);}});
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    	Dimension dlgSize = this.getPreferredSize();
    	Point loc = getLocation();
    	this.setLocation((screenSize.width - dlgSize.width) / 2, (screenSize.height - dlgSize.height) / 2);
//Hcanedo_06102005_begin    	
    	getRootPane().setDefaultButton(jButtonOk);
//Hcanedo_06102005_end
    }

   
    public void jButtonOkActionPerformed(ActionEvent e) {
		if(jTextFieldPath.getText().trim().equals(""))
        {
			JOptionPane.showMessageDialog(m_CMFrameView,CMMessages.getString("XSLT_REPORTS_FORMAT_MENSSAGE_EMPTY_FILEPATH"),CMMessages.getString("TESTDATASETREPORTS_TITLE_INFORMATION"),JOptionPane.INFORMATION_MESSAGE);
        }
         else
            {
                String fuente=jTextFieldPath.getText();
                File fuenteFile= new File(fuente);
                if(!fuenteFile.isFile()){
                	JOptionPane.showMessageDialog(m_CMFrameView,CMMessages.getString("EXTERNAL_REPORTS_MENSSAGE_FILE_DOESNT_EXIST"),CMMessages.getString("TESTDATASETREPORTS_TITLE_INFORMATION"),JOptionPane.INFORMATION_MESSAGE);
                	return;
                }
//hcanedo_21_09_2004_begin
 /*               StringBuffer destinoForm = new StringBuffer();
				destinoForm.append(m_reportsFilePath);
                destinoForm.append(BusinessRules.URL_SEPARATOR);
				destinoForm.append(BusinessRules.ADD_EXTERNAL_REPORT);
                File destinoCarpet= new File(destinoForm.toString());
                destinoCarpet.mkdirs();
                destinoForm.append(BusinessRules.URL_SEPARATOR);
                destinoForm.append(fuenteFile.getName());
                String destino= destinoForm.toString();
                File destinoFile= new File(destino);
                int i=0;
                while (destinoFile.exists()) {
                       i++;
                       String newName = generateNewName(destinoFile.toString(), i);
                       destinoFile = new File(newName);
                }
				copyFileToExternalReports(fuenteFile,destinoFile);*/

                nameReportImport=fuenteFile.getName();
                pathReportImport=jTextFieldPath.getText();
                isOKSelected=true;
//hcanedo_21_09_2004_end
                dispose();
    	}
    }

    public void jButtonCancelActionPerformed(ActionEvent e) {
		 dispose();
    }

    public void jButtonSearchActionPerformed(ActionEvent e) {
		JFileChooser chooser = new JFileChooser();
    	int returnVal = chooser.showOpenDialog(m_CMFrameView);
    	if(returnVal == JFileChooser.APPROVE_OPTION)
    	{
        	jTextFieldPath.setText(chooser.getSelectedFile().toString().replace('\\','/'));
            //openedFile = chooser.getSelectedFile();
    	}
    }

//hcanedo_21_09_2004_begin
   public void copyFileToExternalReports(File fuente, File destino){
        try{/*
    		FileInputStream fis  = new FileInputStream(fuente);
		    FileOutputStream fos = new FileOutputStream(destino);
    		FileChannel canalFuente  = fis.getChannel();
    		FileChannel canalDestino = fos.getChannel();
		    canalFuente.transferTo(0, canalFuente.size(), canalDestino);
		    fis.close();
		    fos.close();
            isOKSelected=true;*/
            PrintWriter output= new PrintWriter(new FileWriter(destino));
			output.write("this file is only a reference for orignal file go to "+fuente.toString());
			output.close();
            isOKSelected=true;
        }
        catch(Exception ex)
        {
        	Logger.getLogger(this.getClass()).debug(ex.getMessage());
            isOKSelected=false;
        }
	}
//hcanedo_21_09_2004_end
   	public String generateNewName(String p_FileName, int p_index) {
        StringBuffer fileNameBuffer = new StringBuffer(p_FileName);
        int index = p_FileName.lastIndexOf("."); //$NON-NLS-1$
        StringBuffer sBuffer = new StringBuffer();
        sBuffer.append("_");
        sBuffer.append(p_index);
        fileNameBuffer.insert(index, sBuffer.toString());
        return fileNameBuffer.toString();
    }

    private JPanel jPanelSourth = new JPanel();
    private JButton jButtonOk = new JButton();
    private JButton jButtonCancel = new JButton();
    private JPanel jPanelCenter = new JPanel();
    private JLabel jLabelPath = new JLabel();
    private JTextField jTextFieldPath = new JTextField();
    private JButton jButtonSearch = new JButton();
    private JPanel jPanelNorth = new JPanel();
    private CMFrameView m_CMFrameView;

    public boolean isOKSelected=false;
    public String nameReportImport="";
    public String pathReportImport="";
    
	protected void PressJButtonCancel(Object object) {
		jButtonCancelActionPerformed(null);
	}

	protected void PressJButtonOk(Object object) {
		jButtonOkActionPerformed(null);
	}

	protected List getOrder() {
		List focusOrder=new ArrayList();
		//focusOrder.add(jTextFieldPath);
		focusOrder.add(jButtonSearch);
		focusOrder.add(jButtonOk);
		focusOrder.add(jButtonCancel);
		return focusOrder;
	}
}
