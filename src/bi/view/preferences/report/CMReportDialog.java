package bi.view.preferences.report;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import model.BusinessRules;
import model.TestCaseExternalReports;

import org.apache.log4j.Logger;

import bi.controller.ApplicationSettingManager;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.mainframeviews.CMFrameView;
import bi.view.report.data.EReportDataSource;
import bi.view.utils.CMBaseJComboBox;
import bi.view.utils.CMBaseJDialog;
import bi.view.utils.CMFileFilter;
import bi.view.utils.CMModalResult;




@SuppressWarnings("serial")
public class CMReportDialog extends CMBaseJDialog {




    public CMReportDialog(CMFrameView p_Frame) {
		super(p_Frame);
        m_CMFrameView = p_Frame;
        m_AppSettingManager=m_CMFrameView.getCmApplication().getSessionManager().getApplicationSettingManager();
        initGUI();
        this.addKeyListenertoAll(this,this.getDefaultKeyListener());
    }

    @SuppressWarnings("unchecked")
	public void initGUI() {
        this.setModal(true);
        this.getContentPane().setLayout(null);
        this.getContentPane().add(jLabeldefaultApp);
        this.getContentPane().add(jLabelFilePath);
        this.getContentPane().add(jButtonBrowse);
        this.getContentPane().add(jButtonOK);
        this.getContentPane().add(jButtonCancel);
        this.getContentPane().add(jTextFieldFormatName);
        this.getContentPane().add(jTextFieldFilePath);
        this.getContentPane().add(jTextFieldExtension);
        this.getContentPane().add(jLabelExtension);
        this.getContentPane().add(jLabelReportDS);
        this.getContentPane().add(jcomboReportDS);

        jcomboReportDS.setBounds(141, 34, 295, 21);
        Vector reportDS = new Vector<EReportDataSource>();
        reportDS.addAll(CMApplication.frame.getCmApplication().getSessionManager().getApplicationSettingManager().getApplicationSetting().getM_ReportDataSources());
        reportDS.removeElement(EReportDataSource.REPORTDS_OLD_TESTDATASET);
        ComboBoxModel JComboReportDSModel = new DefaultComboBoxModel(reportDS);
		jcomboReportDS.setModel(JComboReportDSModel);
        ComboBoxModel JComboDefaultAppModel = new DefaultComboBoxModel(
				CMApplication.frame.getCmApplication().getSessionManager().getApplicationSettingManager().getApplicationSetting().getM_ExternalApplications());
			JComboDefaultApp = new CMBaseJComboBox(this);

			this.getContentPane().add(JComboDefaultApp);
			JComboDefaultApp.setModel(JComboDefaultAppModel);
			JComboDefaultApp.setBounds(141, 96, 168, 21);


			jLabelName = new JLabel();
			this.getContentPane().add(jLabelName);
			jLabelName.setText(CMMessages
				.getString("TESTDATASET_OUTPUT_FORMAT_NAME"));
			jLabelName.setBounds(41, 10, 92, 15);

        jLabeldefaultApp.setText(CMMessages.getString("REPORT_DEFAULT_APP_LABEL"));
        jLabeldefaultApp.setBounds(55, 98, 84, 15);
        jLabelFilePath.setText(CMMessages.getString("TESTDATASET_FILEPATH_FORMAT_DIALOG"));
        jLabelFilePath.setBounds(62, 66, 60, 15);
        jButtonBrowse.setText(CMMessages.getString("TESTDATA_IMPORT_BROWSE"));
        jButtonBrowse.setBounds(343, 64, 95, 22);
        jButtonOK.setText(CMMessages.getString("BUTTON_OK"));
        jButtonOK.setBounds(141, 131, 99, 25);
        jButtonCancel.setText(CMMessages.getString("BUTTON_CANCEL"));
        jButtonCancel.setBounds(247, 131, 105, 25);
        jTextFieldFormatName.setBounds(141, 7, 298, 21);
        jTextFieldFilePath.setText("");
        jTextFieldFilePath.setEditable(false);
        jTextFieldFilePath.setBounds(142, 64, 196, 21);
        jLabelReportDS.setText(CMMessages.getString("TESTCASE_REPORT_TYPE_LABEL"));
        jLabelReportDS.setBounds(12, 37, 125, 15);

        setTitle(CMMessages.getString("TITLE_DIALOG_ADD_NEW_FORMAT"));
        jButtonBrowse.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) { jButtonBrowseActionPerformed(e); }
            });
        jButtonOK.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) { jButtonOKActionPerformed(e); }
            });
        jButtonCancel.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) { jButtonCancelActionPerformed(e); }
            });
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension dlgSize = this.getSize();
        setLocation((screenSize.width - dlgSize.width) / 2, (screenSize.height - dlgSize.height) / 2);
        jTextFieldExtension.setBounds(385, 95, 53, 21);
        jLabelExtension.setText(CMMessages.getString("LABEL_REPORT_FORMAT_EXT_COLUMN"));
        jLabelExtension.setBounds(318, 98, 73, 15);
        setModal(true);
        setResizable(false);
        this.setPreferredSize(new java.awt.Dimension(450, 170));
        this.setBounds(25, 25, 472, 194);
     //   this.setPreferredSize(new java.awt.Dimension(438, 206));
    }

    public void jButtonBrowseActionPerformed(ActionEvent e) {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(CMFileFilter.XSL.getFilter());
        int returnVal = chooser.showOpenDialog(m_CMFrameView);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            jTextFieldFilePath.setText(chooser.getSelectedFile().toString().replace('\\','/'));
            openedFile = chooser.getSelectedFile();
        }
    }

    public void jButtonOKActionPerformed(ActionEvent e) {
        if (jTextFieldFilePath.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(m_CMFrameView, CMMessages.getString("XSLT_REPORTS_FORMAT_MENSSAGE_EMPTY_FILEPATH"),
                CMMessages.getString("TESTDATASETREPORTS_TITLE_INFORMATION"), JOptionPane.INFORMATION_MESSAGE);
        }
        else if (jTextFieldFormatName.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(m_CMFrameView, CMMessages.getString("XSLT_REPORTS_FORMAT_MENSSAGE_EMPTY_NAMES"),
                CMMessages.getString("TESTDATASETREPORTS_TITLE_INFORMATION"), JOptionPane.INFORMATION_MESSAGE);
            jTextFieldFormatName.requestFocus();
        }
        else {
            if (m_AppSettingManager.existTestCaseNameReport(jTextFieldFormatName.getText().trim()) &&
                evalueEditing()) {
                    JOptionPane.showMessageDialog(m_CMFrameView, CMMessages.getString("XSLT_REPORTS_FORMAT_MENSSAGE_EQUALS_NAMES"),
                        CMMessages.getString("TESTDATASETREPORTS_TITLE_INFORMATION"), JOptionPane.INFORMATION_MESSAGE);
            }
            else {
            	if (jTextFieldExtension.getText().trim().equals("")) {
                    JOptionPane.showMessageDialog(m_CMFrameView, CMMessages.getString("XSLT_REPORTS_FORMAT_MENSSAGE_EMPTY_EXTENSION"),
                        CMMessages.getString("TESTDATASETREPORTS_TITLE_INFORMATION"), JOptionPane.INFORMATION_MESSAGE);
                    jTextFieldExtension.requestFocus();
                }
            	else if (validExtension()) {
                    if (!isEditing) {
                    	setModalResult(CMModalResult.OK);
                        String fuente = jTextFieldFilePath.getText();
                        File fuenteFile = new File(fuente);
                        StringBuffer destinoForm = new StringBuffer();
                        File obj = null;

                        if (jcomboReportDS.getSelectedItem().toString().equals(EReportDataSource.REPORTDS_TESTDATASET.toString()))
                        	obj = new File(BusinessRules.TESTDATA_XSLTREPORTS_CARPET);
                        else
                        	obj = new File(BusinessRules.TESTCASE_XSLTREPORTS_CARPET);
                        boolean exist = true;
                        if (!obj.exists()) {
                            obj.mkdirs();
                        }
                        if (exist) {
                            String path = obj.toString();
                            destinoForm.append(path);
                        }
                        else {
                            String path = obj.toString();
                            destinoForm.append(path);
                        }
                        destinoForm.append(BusinessRules.URL_SEPARATOR);
                        destinoForm.append(fuenteFile.getName());
                        String destino = destinoForm.toString().replace('\\','/');
                        File destinoFile = new File(destino);
                        int i = 0;
                        while (destinoFile.exists()) {
                            i++;
                            String newName = generateNewName(destinoFile.toString().replace('\\','/'), i);
                            destinoFile = new File(newName);
                        }
                        copyFileToExternalXSLT(fuenteFile, destinoFile);
                        EReportDataSource typeDS = (EReportDataSource)jcomboReportDS.getSelectedItem();

                        //TODO parametrize the type of report
                      editTestCaseReport =   m_AppSettingManager.registerNewTestCaseReport(jTextFieldFormatName.getText().trim(),
                            destinoFile.toString().replace('\\','/'), jTextFieldExtension.getText().trim(),typeDS.name(),
                            JComboDefaultApp.getSelectedItem().toString());//cccastedo changed the combobox
                        m_CMFrameView.getCmApplication().getSessionManager().writeSession2ToFile(m_CMFrameView.getTreeWorkspaceView().getM_Session2());
                    }
                    else {
                        m_AppSettingManager.changeExtensionTestCaseReport(editTestCaseReport,
                            jTextFieldExtension.getText().trim());
                        m_AppSettingManager.changeNameTestCaseReport(editTestCaseReport,
                            jTextFieldFormatName.getText().trim());
                        editTestCaseReport.setDefaultApp(JComboDefaultApp.getSelectedItem().toString());

                        String ds = ((EReportDataSource)jcomboReportDS.getSelectedItem()).name();
                        editTestCaseReport.setReportDataSourceName(ds);
                        m_CMFrameView.getCmApplication().getSessionManager().writeSession2ToFile(m_CMFrameView.getTreeWorkspaceView().getM_Session2());
                    }
                    dispose();
                }
            }
        }
    }

    public boolean evalueEditing() {
        if (!isEditing)
            return true;
        else {
            if (editTestCaseReport.getName().equals(jTextFieldFormatName.getText().trim()))
                return false;
            else
                return true;
        }
    }

    public boolean validExtension() {
        String ext = jTextFieldExtension.getText();
        if (ext.indexOf(".") > -1) {
            JOptionPane.showMessageDialog(m_CMFrameView, CMMessages.getString("XSLT_REPORTS_FORMAT_MENSSAGE_POINT_EXTENSION"),
                CMMessages.getString("TESTDATASETREPORTS_TITLE_INFORMATION"), JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        else {
            if (ext.length() > 4) {
                JOptionPane.showMessageDialog(m_CMFrameView, CMMessages.getString("XSLT_REPORTS_FORMAT_MENSSAGE_LENGTH_EEXTENSION"),
                    CMMessages.getString("TESTDATASETREPORTS_TITLE_INFORMATION"), JOptionPane.INFORMATION_MESSAGE);
                return false;
            }
            else {
                return true;
            }
        }
    }

    public String generateNewName(String p_FileName, int p_index) {
        StringBuffer fileNameBuffer = new StringBuffer(p_FileName);
        int index = p_FileName.lastIndexOf("."); //$NON-NLS-1$
        StringBuffer sBuffer = new StringBuffer();
        sBuffer.append("_");
        sBuffer.append(p_index);
        fileNameBuffer.insert(index, sBuffer.toString());
        return fileNameBuffer.toString();
    }

    public void copyFileToExternalXSLT(File fuente, File destino) {
        try {
            FileInputStream fis = new FileInputStream(fuente);
            FileOutputStream fos = new FileOutputStream(destino);
            FileChannel canalFuente = fis.getChannel();
            FileChannel canalDestino = fos.getChannel();
            canalFuente.transferTo(0, canalFuente.size(), canalDestino);
            fis.close();
            fos.close();
        }
        catch (Exception ex) {
        	Logger.getLogger(this.getClass()).error(ex.getMessage());
        }
    }

    public void jButtonCancelActionPerformed(ActionEvent e) {
        dispose();
    }

    public void setTestCaseReportEdit(TestCaseExternalReports testCaseReport) {
        jButtonBrowse.setVisible(false);
        editTestCaseReport = testCaseReport;
        String nameFormat = testCaseReport.getName();
        jTextFieldFormatName.setText(testCaseReport.getName());
        jTextFieldFilePath.setText(testCaseReport.getFilePath().replace('\\','/'));
        jTextFieldExtension.setText(testCaseReport.getExtension());
       /* JComboDefaultApp.setSelectedItem(CMApplication.frame.getCmApplication().getSessionManager().getApplicationSettingManager().
        		getApplicationSetting().getM_ExternalApplicationByName(testCaseReport.getDefaultApp()));
*/
        JComboDefaultApp.setSelectedItem(CMApplication.frame.getCmApplication().getSessionManager().getApplicationSettingManager().
        		getApplicationSetting().getM_ExternalApplicationByName(testCaseReport.getDefaultApp()));

      //ccastedo 11.12.06  jcomboReportDS.setSelectedIndex(testCaseReport.getReportDataSourceId());
        int index = getIndexFromSourceName(testCaseReport);

        jcomboReportDS.setSelectedIndex(index);
        isEditing = true;
    }
    private int getIndexFromSourceName(TestCaseExternalReports testCaseReport){
    	String bueno = EReportDataSource.valueOf(testCaseReport.getReportDataSourceName()).toString();
        int index = 0;
        for (int i = 0;i < jcomboReportDS.getItemCount();i++){
        	String sd = jcomboReportDS.getItemAt(i).toString();
        	if (bueno.equals(sd)){
        		index = i;
        		break;
        	}
        }
        return index;
    }
    public void setElementsDisabled(){
    	jcomboReportDS.setEnabled(false);
    }

    private JLabel jLabeldefaultApp = new JLabel();
    private JLabel jLabelFilePath = new JLabel();
    private JButton jButtonBrowse = new JButton();
    private JButton jButtonOK = new JButton();
    private JButton jButtonCancel = new JButton();
    private JTextField jTextFieldFormatName = new JTextField();
    private JTextField jTextFieldFilePath = new JTextField();
    private JLabel jLabelReportDS = new JLabel();
    private CMBaseJComboBox jcomboReportDS = new CMBaseJComboBox(this);
    private CMFrameView m_CMFrameView;
    private File openedFile = null;
    private boolean isEditing = false;
    private TestCaseExternalReports editTestCaseReport;  //  @jve:decl-index=0:
    private JTextField jTextFieldExtension = new JTextField();
    private JLabel jLabelExtension = new JLabel();
    private ApplicationSettingManager m_AppSettingManager;
    private JLabel jLabelName;
    private JComboBox JComboDefaultApp;
	public JButton getDefaultButton() {
		return jButtonOK;
	}

	protected List getOrder() {
		List<JComponent> order = new ArrayList<JComponent>();
		order.add(jTextFieldFormatName);
		order.add(jTextFieldExtension);
		order.add(jButtonBrowse);
		order.add(jcomboReportDS);
		order.add(jButtonOK);
		order.add(jButtonCancel);
		return order;
	}

	protected void fireButtonOk() {
		jButtonOK.doClick();

	}

	protected void fireButtonCancel() {
		jButtonCancel.doClick();

	}

	public CMBaseJComboBox getJcomboReportDS() {
		return jcomboReportDS;
	}

	public void setJcomboReportDS(CMBaseJComboBox jcomboReportDS) {
		this.jcomboReportDS = jcomboReportDS;
	}

	public TestCaseExternalReports getEditTestCaseReport() {
		return editTestCaseReport;
	}

}
