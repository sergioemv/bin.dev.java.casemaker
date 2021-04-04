package bi.controller.cmimport.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import bi.controller.cmimport.CMCSVFileFormat;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.utils.CMBaseJDialog;
import bi.view.utils.CMFileFilter;
import bi.view.utils.CMOkCancelPanel;

/**
 * <p>Title: Casemaker</p>
 *
 * <p>Description: Tool for test case generation</p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: Business Innovations</p>
 *
 * @author Sergio Moreno
 * @version 2.5
 */
public class CMCSVImportOptionsDialog extends CMBaseJDialog {
   
	JPanel content = new JPanel();
    BorderLayout borderLayout1 = new BorderLayout();
    FlowLayout flowLayout1 = new FlowLayout(FlowLayout.LEFT);
    JPanel centerPanel = new JPanel();
	private CMCSVImportDelimiterPanel delimiterPanel = null;
	private JPanel wherePanel = null;
	private JPanel previewPanel = null;
	private JComboBox jComboImportAs = null;
	private JPanel jPanelImportOptions = null;
	private JPanel jPanelFilename = null;
	private JLabel jLabelFilename = null;
	private JLabel jLabelImportAs = null;
	private JTextField jTextFieldFilename = null;
	private JButton jButtonBrowse = null;
	private JLabel jLabelPosition = null;
	private JComboBox jComboBoxInsertionPoint = null;
	private CMCSVImportPreviewScrollPane jScrollPanePreview = new CMCSVImportPreviewScrollPane(this);
	private File selectedFile;
	private ComboBoxModel comboBoxModelImportIn;
	private CMOkCancelPanel okCancelPanel = null;
	public CMCSVImportOptionsDialog() {
        super(CMApplication.frame);
        try {
			jbInit();
			addKeyListenertoAll(this, this.getDefaultKeyListener());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    private void jbInit() throws Exception {
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setPreferredSize(new java.awt.Dimension(10,180));
      
        centerPanel.add(getWherePanel(), null);
        centerPanel.add(getDelimiterPanel(), null);
        content.setLayout(borderLayout1);
        this.setContentPane(content);
        this.setTitle(CMMessages.getString("IMPORT_CSV_OPTIONS_DIALOG_TITLE")); //$NON-NLS-1$
        this.setSize(new java.awt.Dimension(441,475));
       
        flowLayout1.setAlignment(FlowLayout.RIGHT);
        content.add(centerPanel, java.awt.BorderLayout.NORTH);
        content.add(getPreviewPanel(), java.awt.BorderLayout.CENTER);
        content.add(getOkCancelPanel(), BorderLayout.SOUTH);
    }

    public JButton getDefaultButton() {
        return null;
    }

    protected List getOrder() {
    	List<Component> list = new Vector<Component>();
    	list.add(getJButtonBrowse());
    	list.add(getJComboImportAs());
    	list.add(getJComboBoxInsertionPoint());
    	list.addAll(delimiterPanel.getOrder());
    	list.addAll(getOkCancelPanel().getTabOrder());
    	//list.add(getJButtonCancel());
        return list;
    }

    protected void fireButtonOk() {
    	getOkCancelPanel().getJButtonOk().doClick();
    }

    protected void fireButtonCancel() {
    	getOkCancelPanel().getJButtonCancel().doClick();
    }

	/**
	 * This method initializes delimiterPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	 CMCSVImportDelimiterPanel getDelimiterPanel() {
		if (delimiterPanel == null) {
			delimiterPanel = new CMCSVImportDelimiterPanel();
			//delimiterPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED), "Please Choose Delimiter that separates the fields", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
			
		}
		return delimiterPanel;
	}

	/**
	 * This method initializes wherePanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getWherePanel() {
		if (wherePanel == null) {
			
			wherePanel = new JPanel();
			wherePanel.setLayout(new BoxLayout(getWherePanel(), BoxLayout.Y_AXIS));
			wherePanel.setPreferredSize(new java.awt.Dimension(108,65));
			wherePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), CMMessages.getString("IMPORT_CSV_OPTIONS_DIALOG_IMPORT_AS_PANEL"), TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.PLAIN, 11), SystemColor.activeCaption));
			wherePanel.add(getJPanelFilename(), null);
			wherePanel.add(getJPanelImportOptions(), null);
		}
		return wherePanel;
	}

	/**
	 * This method initializes previewPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPreviewPanel() {
		if (previewPanel == null) {
			previewPanel = new JPanel();
			previewPanel.setLayout(new BorderLayout());
			previewPanel.setPreferredSize(new java.awt.Dimension(10,70));
			previewPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED), CMMessages.getString("IMPORT_CSV_OPTIONS_DIALOG_PREVIEW_PANEL_TITLE"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.PLAIN, 11), SystemColor.activeCaption)); //$NON-NLS-1$
			previewPanel.add(getJScrollPanePreview(), java.awt.BorderLayout.NORTH);
		}
		return previewPanel;
	}

	/**
	 * This method initializes jComboImportIn	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	public JComboBox getJComboImportAs() {
		if (jComboImportAs == null) {
			 comboBoxModelImportIn = new DefaultComboBoxModel(CMCSVFileFormat.values());
			jComboImportAs = new JComboBox(comboBoxModelImportIn);
			jComboImportAs.setPreferredSize(new java.awt.Dimension(150,22));
			jComboImportAs.setToolTipText(CMMessages.getString("IMPORT_CSV_OPTIONS_DIALOG_IMPORT_AS_TOOLTIP")); //$NON-NLS-1$
			jComboImportAs.addActionListener(getJScrollPanePreview().getJTablePreview());
			jComboImportAs.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent p_e) {
					// Update the import position combo
					getJComboBoxInsertionPoint().setModel(new DefaultComboBoxModel(((CMCSVFileFormat)getJComboImportAs().getSelectedItem()).getValidPositions().toArray()));
				}
				
			});
		}
		return jComboImportAs;
	}

	/**
	 * This method initializes jPanelFileName	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelImportOptions() {
		if (jPanelImportOptions == null) {
			jLabelPosition = new JLabel();
			jLabelPosition.setText(CMMessages.getString("IMPORT_CSV_OPTIONS_DIALOG_INSERTION_POINT")); //$NON-NLS-1$
			jLabelImportAs = new JLabel();
			jLabelImportAs.setText(CMMessages.getString("IMPORT_CSV_OPTIONS_DIALOG_IMPORT_AS_LABEL")); //$NON-NLS-1$
			FlowLayout flowLayout2 = new FlowLayout();
			flowLayout2.setAlignment(java.awt.FlowLayout.LEFT);
			jPanelImportOptions = new JPanel();
			jPanelImportOptions.setLayout(flowLayout2);
			jPanelImportOptions.add(jLabelImportAs, null);
			jPanelImportOptions.add(getJComboImportAs(), null);
			jPanelImportOptions.add(jLabelPosition, null);
			jPanelImportOptions.add(getJComboBoxInsertionPoint(), null);
		}
		return jPanelImportOptions;
	}

	/**
	 * This method initializes jPanelFilename	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelFilename() {
		if (jPanelFilename == null) {
			FlowLayout flowLayout = new FlowLayout();
			flowLayout.setAlignment(java.awt.FlowLayout.LEFT);
			jLabelFilename = new JLabel();
			jLabelFilename.setText(CMMessages.getString("IMPORT_CSV_OPTIONS_DIALOG_FILENAME_LABEL")); //$NON-NLS-1$
			jPanelFilename = new JPanel();
			jPanelFilename.setLayout(flowLayout);
			jPanelFilename.add(jLabelFilename, null);
			jPanelFilename.add(getJTextFieldFilename(), null);
			jPanelFilename.add(getJButtonBrowse(), null);
		}
		return jPanelFilename;
	}

	/**
	 * This method initializes jTextFieldFilename	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextFieldFilename() {
		if (jTextFieldFilename == null) {
			jTextFieldFilename = new JTextField();
			jTextFieldFilename.setPreferredSize(new java.awt.Dimension(320,20));
			jTextFieldFilename.setToolTipText(CMMessages.getString("IMPORT_CSV_OPTIONS_DIALOG_FILENAME_LABEL_TOOLTIP")); //$NON-NLS-1$
			jTextFieldFilename.setEditable(false);
			jTextFieldFilename.getDocument().addDocumentListener(getJScrollPanePreview().getJTablePreview());
		}
		return jTextFieldFilename;
	}

	/**
	 * This method initializes jButtonBrowse	
	 * 	
	 * @return javax.swing.JButton	
	 */
	public JButton getJButtonBrowse() {
		if (jButtonBrowse == null) {
			jButtonBrowse = new JButton();
			jButtonBrowse.setPreferredSize(new java.awt.Dimension(30,20));
			jButtonBrowse.setToolTipText(CMMessages.getString("IMPORT_CSV_OPTIONS_DIALOG_BROWSE_TOOLTIP")); //$NON-NLS-1$
			jButtonBrowse.setText("...");
			jButtonBrowse.addActionListener(new java.awt.event.ActionListener() {
			

				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFileChooser fc = new JFileChooser();
					fc.setFileFilter(CMFileFilter.CSV.getFilter());
					fc.setDialogTitle(CMMessages.getString("IMPORT_CSV_ACTION_OPEN_DIALOG_TITLE"));
					int response = fc.showOpenDialog(CMApplication.frame);
					if  (fc.getSelectedFile()!=null && response == JFileChooser.APPROVE_OPTION)
					{
						selectedFile = fc.getSelectedFile();
						jTextFieldFilename.setText(selectedFile.getAbsolutePath());
					} 
						
				}
			});
		}
		return jButtonBrowse;
	}

	/**
	 * This method initializes jComboBoxInsertionPoint	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJComboBoxInsertionPoint() {
		if (jComboBoxInsertionPoint == null) {
			DefaultComboBoxModel comboBoxModel = new DefaultComboBoxModel(((CMCSVFileFormat)getJComboImportAs().getSelectedItem()).getValidPositions().toArray());
			jComboBoxInsertionPoint = new JComboBox(comboBoxModel);
			jComboBoxInsertionPoint.setPreferredSize(new java.awt.Dimension(120,22));
			jComboBoxInsertionPoint.setToolTipText(CMMessages.getString("IMPORT_CSV_OPTIONS_DIALOG_INSERTION_POINT_TOOLTIP")); //$NON-NLS-1$
			
		}
		return jComboBoxInsertionPoint;
	}

	/**
	 * This method initializes jScrollPanePreview	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private CMCSVImportPreviewScrollPane getJScrollPanePreview() {
		if (jScrollPanePreview == null) {
			try {
				jScrollPanePreview = new CMCSVImportPreviewScrollPane(this);
			} catch (java.lang.Throwable e) {
				// TODO: Something
			}
		}
		return jScrollPanePreview;
	}

	

	public File getSelectedFile() {
		return this.selectedFile;
	}

	ComboBoxModel getComboBoxModelImportIn() {
		return this.comboBoxModelImportIn;
	}

	public char getDelimiter() {
		return this.delimiterPanel.getDelimiter();
	}
	public CMCSVFileFormat getFormat()
	{
		return (CMCSVFileFormat) this.getJComboImportAs().getSelectedItem();
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
}  
