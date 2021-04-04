package bi.view.testdataviews;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextField;

import model.ApplicationSetting;

import org.apache.log4j.Logger;

import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMFrameView;
import bi.view.utils.CMFileFilter;
import bi.view.utils.JCustomDialog;

/**
 ****************************************************************************** Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
public class CMDialogXSLTManagement extends JCustomDialog {
    public CMDialogXSLTManagement(CMFrameView p_Frame) {
        m_CMFrameView=p_Frame;
        m_ApplicationSetting= m_CMFrameView.getTreeWorkspaceView().getM_Session2().getM_ApplicationSetting();
        initGUI();
    }

    public void initGUI() {
        this.setModal(true);
        getContentPane().setLayout(null);
        getContentPane().add(jLabelFormatName);
        getContentPane().add(jLabelFilePath);
        getContentPane().add(jButtonBrowse);
        getContentPane().add(jButtonOK);
        getContentPane().add(jButtonCancel);
        getContentPane().add(jTextFieldFormatName);
        getContentPane().add(jTextFieldFilePath);
        jLabelFormatName.setText(CMMessages.getString("TESTDATASET_OUTPUT_FORMAT_NAME"));
        jLabelFormatName.setBounds(new java.awt.Rectangle(22, 21, 96, 16));
        jLabelFilePath.setText(CMMessages.getString("TESTDATASET_FILEPATH_FORMAT_DIALOG"));
        jLabelFilePath.setBounds(new java.awt.Rectangle(22, 60, 96, 16));
        jButtonBrowse.setText(CMMessages.getString("TESTDATA_IMPORT_BROWSE"));
        jButtonBrowse.setBounds(new java.awt.Rectangle(373, 58, 80, 25));
        jButtonOK.setText(CMMessages.getString("BUTTON_OK"));
        jButtonOK.setBounds(new java.awt.Rectangle(136, 102, 86, 27));
        jButtonCancel.setText(CMMessages.getString("BUTTON_CANCEL"));
        jButtonCancel.setBounds(new java.awt.Rectangle(230, 103, 87, 27));
        jTextFieldFormatName.setText("");
        jTextFieldFormatName.setBounds(new java.awt.Rectangle(100, 20, 267, 24));
        jTextFieldFilePath.setText("");
        jTextFieldFilePath.setBounds(new java.awt.Rectangle(100, 58, 267, 24));
        jTextFieldFilePath.setEditable(false);
        setBounds(new java.awt.Rectangle(0, 0, 472, 172));
        setTitle(CMMessages.getString("TITLE_DIALOG_ADD_NEW_FORMAT"));
        jButtonBrowse.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){jButtonBrowseActionPerformed(e);}});
        jButtonOK.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){jButtonOKActionPerformed(e);}});
        jButtonCancel.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){jButtonCancelActionPerformed(e);}});
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension dlgSize = this.getSize();
		Point loc = getLocation();
		setLocation((screenSize.width - dlgSize.width) / 2 , (screenSize.height - dlgSize.height) / 2 );
//hcanedo_21_09_2004_begin
		setResizable(false);
//hcanedo_21_09_2004_end
//hcanedo_07102005_begin
		getRootPane().setDefaultButton(jButtonOK);
//hcanedo_07102005_end
    }

    public void jButtonBrowseActionPerformed(ActionEvent e) {
        JFileChooser chooser = new JFileChooser();
	    chooser.setFileFilter(CMFileFilter.XSL.getFilter());
    	int returnVal = chooser.showOpenDialog(m_CMFrameView);
    	if(returnVal == JFileChooser.APPROVE_OPTION)
    	{
        	jTextFieldFilePath.setText(chooser.getSelectedFile().toString().replace('\\','/'));
            openedFile = chooser.getSelectedFile();
    	}
    }

    public void jButtonOKActionPerformed(ActionEvent e) {/*
        if(jTextFieldFilePath.getText().trim().equals(""))
        {
			JOptionPane.showMessageDialog(m_CMFrameView,CMMessages.getString("XSLT_REPORTS_FORMAT_MENSSAGE_EMPTY_FILEPATH"),CMMessages.getString("TESTDATASETREPORTS_TITLE_INFORMATION"),JOptionPane.INFORMATION_MESSAGE);
        }
        else if(jTextFieldFormatName.getText().trim().equals(""))
        {
            	JOptionPane.showMessageDialog(m_CMFrameView,CMMessages.getString("XSLT_REPORTS_FORMAT_MENSSAGE_EMPTY_NAMES"),CMMessages.getString("TESTDATASETREPORTS_TITLE_INFORMATION"),JOptionPane.INFORMATION_MESSAGE);
        }
        else
        {
            //ccastedo 07.11.06  if(m_ApplicationSetting.getM_ExternalXSLTReportFormat().existsNameReport(jTextFieldFormatName.getText().trim()))
        	if(m_CMFrameView.getCmApplication().getSessionManager().getApplicationSettingManager().existTestDataSetNameReport(jTextFieldFormatName.getText().trim()))        	
        	{
                JOptionPane.showMessageDialog(m_CMFrameView,CMMessages.getString("XSLT_REPORTS_FORMAT_MENSSAGE_EQUALS_NAMES"),CMMessages.getString("TESTDATASETREPORTS_TITLE_INFORMATION"),JOptionPane.INFORMATION_MESSAGE);
            }
            else
            {
                String fuente=jTextFieldFilePath.getText();
                File fuenteFile= new File(fuente);
                StringBuffer destinoForm = new StringBuffer();
                File obj= new File (BusinessRules.REPORT_XSLT_CARPET);
                if(!obj.exists()) 
                {
                    obj.mkdirs();
  		   obj=CMFrameView.class.getResource(BusinessRules.REPORT_CARPET_NAME);
                    StringBuffer  createXSLTCarpet= new StringBuffer();
                    createXSLTCarpet.append(obj.toString());
                    createXSLTCarpet.append(BusinessRules.URL_SEPARATOR);
                    createXSLTCarpet.append(BusinessRules.REPORT_XSLT_CARPET_NEW);
                    obj= createXSLTCarpet.toString();
                }
                String path=obj.toString();
               int index= path.indexOf(":");
                destinoForm.append(path.substring(index+2,path.length()));
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
				copyFileToExternalXSLT(fuenteFile,destinoFile);
                // ccastedo 07.11.06  m_ApplicationSetting.getM_ExternalXSLTReportFormat().registerNewXSLTReport(jTextFieldFormatName.getText(),destinoFile.toString(), "");
				m_CMFrameView.getCmApplication().getSessionManager().getApplicationSettingManager().registerNewTestDataSetReport(jTextFieldFormatName.getText(), destinoFile.toString(), "", "");
                m_CMFrameView.getCmApplication().getSessionManager().writeSession2ToFile(m_CMFrameView.getTreeWorkspaceView().getM_Session2());
                dispose();
            }
        }

    */}
	public String generateNewName(String p_FileName, int p_index) {
        StringBuffer fileNameBuffer = new StringBuffer(p_FileName);
        int index = p_FileName.lastIndexOf("."); //$NON-NLS-1$
        StringBuffer sBuffer = new StringBuffer();
        sBuffer.append("_");
        sBuffer.append(p_index);
        fileNameBuffer.insert(index, sBuffer.toString());
        return fileNameBuffer.toString();
    }
    public void copyFileToExternalXSLT(File fuente, File destino){
        try{
    		FileInputStream fis  = new FileInputStream(fuente);
		    FileOutputStream fos = new FileOutputStream(destino);
    		FileChannel canalFuente  = fis.getChannel();
    		FileChannel canalDestino = fos.getChannel();
		    canalFuente.transferTo(0, canalFuente.size(), canalDestino);
		    fis.close();
		    fos.close();
        }
        catch(Exception ex)
        {
        	Logger.getLogger(this.getClass()).error(ex.getMessage());
        }
	}
    public void jButtonCancelActionPerformed(ActionEvent e) {
        dispose();
    }

    
    private JLabel jLabelFormatName = new JLabel();
    private JLabel jLabelFilePath = new JLabel();
    private JButton jButtonBrowse = new JButton();
    private JButton jButtonOK = new JButton();
    private JButton jButtonCancel = new JButton();
    private JTextField jTextFieldFormatName = new JTextField();
    private JTextField jTextFieldFilePath = new JTextField();
    private CMFrameView m_CMFrameView;
    private File openedFile= null;
    private ApplicationSetting m_ApplicationSetting;
	protected List getOrder() {
		List focusOrder= new ArrayList();
		return focusOrder;
	}

	protected void PressJButtonCancel(Object object) {
		jButtonCancelActionPerformed(null);
	}

	protected void PressJButtonOk(Object object) {
		jButtonOKActionPerformed(null);
	}
}
