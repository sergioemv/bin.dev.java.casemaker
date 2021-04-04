package bi.view.preferences.toolvendors;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.BusinessRules;
import model.ToolVendor;
import bi.controller.ToolVendorManager;
import bi.controller.utils.CMFileValidator;
import bi.view.lang.CMMessages;
import bi.view.utils.CMFileFilter;
import bi.view.utils.JCustomDialog;

public class CMDialogToolVendor extends JCustomDialog {
    public CMDialogToolVendor(Frame frame) {
        super(frame);
        try {
            initGUI();
            pack();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    void initGUI() throws Exception {
        this.setModal(true);
        this.setResizable(false);
        getContentPane().setLayout(new java.awt.BorderLayout());
        this.setTitle(CMMessages.getString("LABEL_CASEMAKER_EXTERNAL_APPLICATION")); //$NON-NLS-1$
        setSize(new java.awt.Dimension(500, 200));

        jPanelLabelFields.setLayout(new GridLayout(2, 1));//linea modificada

        jLabelName.setText("   " + CMMessages.getString("LABEL_APPLICATION_NAME_COLUMN"));
        jLabelName.setPreferredSize(new java.awt.Dimension(95, 20));
        jLabelName.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelName.setMinimumSize(new java.awt.Dimension(50, 20));
        jLabelName.setSize(new java.awt.Dimension(150, 0));
        jLabelName.setMaximumSize(new java.awt.Dimension(100, 20));
        jLabelName.setAlignmentX(0.5f);
        jLabelName.setAlignmentY(0.5f);
        jPanelLabelName.setAlignmentY(0.5f);
        jPanelLabelName.setLayout(new javax.swing.BoxLayout(jPanelLabelName, javax.swing.BoxLayout.X_AXIS));
        jPanelLabelName.setPreferredSize(new java.awt.Dimension(95, 30));
        jPanelLabelName.add(jLabelName);
        jLabelPath.setText("   " + CMMessages.getString("LABEL_APPLICATION_PATH_COLUMN"));
        jLabelPath.setPreferredSize(new java.awt.Dimension(95, 20));
        jLabelPath.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelPath.setMinimumSize(new java.awt.Dimension(50, 20));
        jLabelPath.setMaximumSize(new java.awt.Dimension(100, 20));
        jLabelPath.setSize(new java.awt.Dimension(150, 20));
        jLabelPath.setAlignmentY(0.5f);
        jPanelLabelPath.setLayout(new javax.swing.BoxLayout(jPanelLabelPath, javax.swing.BoxLayout.X_AXIS));
        jPanelLabelPath.setPreferredSize(new java.awt.Dimension(95, 30));
        jPanelLabelPath.add(jLabelPath);


        jPanelLabelFields.setMaximumSize(new java.awt.Dimension(200, 32767));
        jPanelLabelFields.setPreferredSize(new java.awt.Dimension(110, 100));
        jPanelLabelFields.setSize(new java.awt.Dimension(50, 256));
        jPanelLabelFields.setMinimumSize(new java.awt.Dimension(50, 30));
        jPanelLabelFields.add(jPanelLabelName);
        jPanelLabelFields.add(jPanelLabelPath);


		jPanelTextFields.setLayout(new GridLayout(2, 1));


        jTextFieldName.setPreferredSize(new java.awt.Dimension(100, 20));
        jTextFieldName.setMaximumSize(new java.awt.Dimension(100, 20));
        jTextFieldName.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextFieldName.setAlignmentX(0.5f);

        jTextFieldName.setEditable(false);//false);

        jPanelTextName.setLayout(new javax.swing.BoxLayout(jPanelTextName, javax.swing.BoxLayout.X_AXIS));
        jPanelTextName.setPreferredSize(new java.awt.Dimension(100, 30));
        jPanelTextName.add(jTextFieldName);
        jTextFieldPath.setAlignmentX(0.5f);
        jTextFieldPath.setMaximumSize(new java.awt.Dimension(280, 23));
        jTextFieldPath.setPreferredSize(new java.awt.Dimension(190, 23));
        jTextFieldPath.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextFieldPath.setAlignmentY(0.04f);

        jTextFieldPath.setEditable(false);

        jPanelTextPath.setLayout(new javax.swing.BoxLayout(jPanelTextPath, javax.swing.BoxLayout.X_AXIS));
        jPanelTextPath.setAlignmentY(0.5f);
        jPanelTextPath.setPreferredSize(new java.awt.Dimension(245, 30));
        jPanelTextPath.add(jTextFieldPath);



        jPanelTextFields.setPreferredSize(new java.awt.Dimension(250, 100));
        jPanelTextFields.add(jPanelTextName);
        jPanelTextFields.add(jPanelTextPath);


		jPanelBrowseFields.setLayout(new GridLayout(2, 1));//linea modifica solo modificar

        jPanelBrowseName.setPreferredSize(new java.awt.Dimension(10, 30));
        jPanelBrowseName.setLayout(new javax.swing.BoxLayout(jPanelBrowseName, javax.swing.BoxLayout.X_AXIS));
        jPanelBrowseName.add(jLabelBrowseName);
        jPanelBrowsePath.setPreferredSize(new java.awt.Dimension(45, 30));
        jButtonBrowsePath.setText(CMMessages.getString("LABEL_BROWSE_APPLICATION"));
        jPanelBrowsePath.setLayout(new javax.swing.BoxLayout(jPanelBrowsePath, javax.swing.BoxLayout.X_AXIS));
        jPanelBrowsePath.add(jButtonBrowsePath);
        jPanelBrowseFields.setPreferredSize(new java.awt.Dimension(45, 100));
        jPanelBrowseFields.add(jPanelBrowseName);
        jPanelBrowseFields.add(jPanelBrowsePath);
        jPanelCenter.setLayout(new BoxLayout(jPanelCenter, BoxLayout.X_AXIS));
        jPanelCenter.setPreferredSize(new java.awt.Dimension(500, 100));
        jPanelCenter.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanelCenter.add(jPanelLabelFields);
        jPanelCenter.add(jPanelTextFields);
        jPanelCenter.add(jPanelBrowseFields);
        getContentPane().add(jPanelCenter, BorderLayout.CENTER);
        jPanelOKCancelButtons.setLayout(new FlowLayout());
        jButtonOK.setText(CMMessages.getString("BUTTON_OK"));
        jButtonCancel.setText(CMMessages.getString("BUTTON_CANCEL"));
        jPanelOKCancelButtons.add(jButtonOK);
        jPanelOKCancelButtons.add(jButtonCancel);
        getContentPane().add(jPanelOKCancelButtons, BorderLayout.SOUTH);
        jButtonOK.addActionListener(
            new java.awt.event.ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    jButtonOK_actionPerformed(e);
                }
            });
        jButtonCancel.addActionListener(
            new java.awt.event.ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    jButtonCancel_actionPerformed(e);
                }
            });
        jButtonCancelClicked = false;
        jButtonOKClicked = false;
        jButtonBrowsePath.setPreferredSize(new java.awt.Dimension(70, 20));
        jButtonBrowsePath.setAlignmentY(0.0f);
        jButtonBrowsePath.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
        jButtonBrowsePath.setMaximumSize(new java.awt.Dimension(70, 23));
        jButtonBrowsePath.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jButtonBrowsePath.setMargin(new java.awt.Insets(2, 10, 2, 10));
        jButtonBrowsePath.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) { jButtonBrowsePathActionPerformed(e); }
            });
//hcanedo_07102005_begin
        getRootPane().setDefaultButton(jButtonOK);
//hcanedo_07102005_end
        jButtonOK.setEnabled(false);

    }

    /** Overridden so we can exit when window is closed */
    protected void processWindowEvent(WindowEvent e) {
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            jButtonCancelClicked = true;
            cancel();
        }
        super.processWindowEvent(e);
    }

    /** Close the dialog */
    void cancel() {
        dispose();
    }

    public boolean jButtonCancelClicked() {
        return jButtonCancelClicked;
    }

    public boolean jButtonOKClicked() {
        return jButtonOKClicked;
    }

    void jButtonOK_actionPerformed(ActionEvent e) {
        jButtonOKClicked = true;

        if (m_ToolVendor == null) {
           /* m_ToolVendor = new ToolVendor();


                m_ToolVendor.setM_FilePath(jTextFieldPath.getText());
                m_ToolVendor.setM_Name(jTextFieldName.getText());

                      **/


        }
        else {

           // m_ToolVendor.setM_FilePath(jTextFieldPath.getText());//svonborries_11012006
            /*ToolVendorManager toolvendormanager = new ToolVendorManager();
            ToolVendor toolVendor = new ToolVendor();
            toolVendor = toolvendormanager.readToolVendorFromFile(m_ToolVendor.getM_FilePath());
            String tv = toolVendor.getM_Name();*/
           // m_ToolVendor.setM_Name(jTextFieldName.getText());  //svonborries_11012006


        }
        dispose();
    }

    void jButtonCancel_actionPerformed(ActionEvent e) {
        jButtonCancelClicked = true;
        cancel();
    }

    public ToolVendor getToolVendor() { return m_ToolVendor; }

    public void setToolVendor(ToolVendor p_ToolVendor,boolean defaultApplication) {
        m_ToolVendor = p_ToolVendor;
        this.jTextFieldName.setText(m_ToolVendor.getM_Name());
        this.jTextFieldPath.setText(BusinessRules.RESOURCE_FOLDER+BusinessRules.URL_SEPARATOR+m_ToolVendor.getM_FilePath());//svonborries_11012006

        if(defaultApplication){
            this.jTextFieldName.setEditable(false);
        }
    }

    public void jButtonBrowsePathActionPerformed(ActionEvent e) {
    	JFileChooser toolVendorFileChooser = new JFileChooser();
        toolVendorFileChooser.addChoosableFileFilter(CMFileFilter.CTV.getFilter());
        toolVendorFileChooser.setAcceptAllFileFilterUsed(false);
        toolVendorFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int returnVal = toolVendorFileChooser.showOpenDialog(this);
        java.io.File file;
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            file = toolVendorFileChooser.getSelectedFile();
            if(file.canRead()){
//svonborries_11012006_begin
				ToolVendorManager toolvendormanager = ToolVendorManager.INSTANCE;
	            ToolVendor toolVendor = new ToolVendor();
	            File outPathFile = CMFileValidator.copyToolVentorToResDir(file);//svonborries_11012006
	            this.jTextFieldPath.setText(outPathFile.getPath().replace('\\','/'));
	            toolVendor = toolvendormanager.readToolVendorFromFile(jTextFieldPath.getText());
//svonborries_11012006_end
	            if (toolVendor!=null){
	            	String tv = toolVendor.getM_Name();
	            	this.jTextFieldName.setText(tv);
	            	m_ToolVendor= toolVendor;
	                m_ToolVendor.setM_FilePath(CMFileValidator.relativePathToolVendor(outPathFile));//svonborries_11012006
	                m_ToolVendor.setM_Name(tv);
	                jButtonOK.setEnabled(true);
	            }
	            else{
	            	JOptionPane.showMessageDialog(this,CMMessages.getString("LABEL_TOOLVENDORS_FILE_CORRUPTED"),CMMessages.getString("TITLE_DEFAULT_APPLICATION_DELETE"),JOptionPane.ERROR_MESSAGE);
	            	jButtonOK.setEnabled(false);
	            }

            }
        }
      /*  else {
            return null;




        JFileChooser fileChooser = new JFileChooser();
        int returnVal = fileChooser.showOpenDialog(this);
        File selectedFile = null;
        if (returnVal == 0) {
            selectedFile = fileChooser.getSelectedFile();
            if(selectedFile.canRead()){
				this.jTextFieldPath.setText(selectedFile.getPath().replace('\\','/'));
            }
		}*/
    }

    JPanel jPanelOKCancelButtons = new JPanel();
    JPanel jPanelLabelFields = new JPanel();
    JPanel jPanelTextFields = new JPanel();
    JPanel jPanelBrowseFields = new JPanel();
    JPanel jPanelCenter = new JPanel();
    JPanel jPanelLabelName = new JPanel();
    JPanel jPanelLabelPath = new JPanel();
    JPanel jPanelTextName = new JPanel();
    JPanel jPanelTextPath = new JPanel();
    JPanel jPanelBrowseName = new JPanel();
    JPanel jPanelBrowsePath = new JPanel();
    JButton jButtonOK = new JButton();
    JButton jButtonCancel = new JButton();
    boolean jButtonCancelClicked = false;
    boolean jButtonOKClicked = false;
    JLabel jLabelName = new JLabel();
    JLabel jLabelPath = new JLabel();
    JLabel jLabelBrowseName = new JLabel();
    JButton jButtonBrowsePath = new JButton();
    JTextField jTextFieldName = new JTextField();
    JTextField jTextFieldPath = new JTextField();
    private ToolVendor m_ToolVendor = null;
//hcanedo_07102005_begin
	protected List getOrder() {
		List focusOrder=new ArrayList();
		focusOrder.add(jButtonBrowsePath);
		if(jButtonOK.isEnabled())
			focusOrder.add(jButtonOK);
		focusOrder.add(jButtonCancel);
		return focusOrder;
	}

	protected void PressJButtonCancel(Object object) {
		jButtonCancel_actionPerformed(null);
	}

	protected void PressJButtonOk(Object object) {
		if(jButtonOK.isEnabled())
			jButtonOK_actionPerformed(null);
	}
//hcanedo_07102005_end

}
