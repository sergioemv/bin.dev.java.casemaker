/* Generated by Together */

package bi.view.testdataviews;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import bi.controller.testdata.formula.CMFormulaCategory;
import bi.controller.testdata.formula.CMFormulas;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.mainframeviews.CMFrameView;
import bi.view.utils.CMFormatFactory;
import bi.view.utils.JCustomDialog;
import bi.view.utils.testdata.CMPanelFormulas;

public class CMDialogFormulas extends JCustomDialog {
    /**
	 * 03/10/2006
	 * svonborries
	 */
	private static final long serialVersionUID = 1L;
	public CMDialogFormulas() {
        super(CMApplication.frame);
        cmFrame = CMApplication.frame;
//        if(cmFrame.isIsPanelTestDataSelected())
//        	cmGrid = cmFrame.getPanelTestDataView().getM_CMGridTDStructure();
//        else
//        	cmGrid = cmFrame.getGridTDStructure();
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
        getContentPane().add(panelDialog, java.awt.BorderLayout.CENTER);
        //getContentPane().setLayout(null);
        panelDialog.setLayout(null);
        panelDialog.add(panelFormulas);
        panelDialog.setPreferredSize(new java.awt.Dimension(487, 357));
       // getContentPane().add(panelFormulas);
        //getContentPane().add(jPanelFunction);
        //getContentPane().add(jPanelFormulas);
        //getContentPane().add(jButtonOK);
        //getContentPane().add(jButtonCancel);
        //getContentPane().add(jLabelDescription);
        //getContentPane().add(jTextAreaDescription);
        getContentPane().setSize(new java.awt.Dimension(475, 323));
        setTitle(CMMessages.getString("TESTDATA_TITLE_DIALOG_INSERT_FORMULA")); //$NON-NLS-1$
        setBounds(new java.awt.Rectangle(0, 0, 486, 394));
        jPanelFunction.setBounds(new java.awt.Rectangle(26, 15, 191, 181));
        jPanelFunction.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(),
            CMMessages.getString("TESTDATA_CATEGORY_FUNCTION"), javax.swing.border.TitledBorder.LEADING, javax.swing.border.TitledBorder.TOP, //$NON-NLS-1$
            new java.awt.Font("SansSerif", 0, 11), new java.awt.Color(60, 60, 60))); //$NON-NLS-1$
        jPanelFunction.setLayout(new java.awt.BorderLayout());
        jPanelFunction.add(jScrollPaneFunction, java.awt.BorderLayout.CENTER);
        jPanelFormulas.setBounds(new java.awt.Rectangle(248, 14, 198, 181));
        jPanelFormulas.setLayout(new java.awt.BorderLayout());
        jPanelFormulas.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(),
            CMMessages.getString("TESTDATA_NAME_FORMULA"), javax.swing.border.TitledBorder.LEADING, javax.swing.border.TitledBorder.TOP,
            new java.awt.Font("SansSerif", 0, 11), //$NON-NLS-1$ //$NON-NLS-2$
            new java.awt.Color(60, 60, 60)));
        jPanelFormulas.add(jScrollPaneFormulas, java.awt.BorderLayout.CENTER);
        jScrollPaneFunction.getViewport().add(jListFunction);
        jListFunction.setBounds(new java.awt.Rectangle(74, 50, 32, 32));
        jListFunction.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPaneFormulas.getViewport().add(getJListFormulas());
        getJListFormulas().setBounds(new java.awt.Rectangle(62, 55, 32, 32));
        getJListFormulas().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jButtonOK.setText(CMMessages.getString("BUTTON_OK")); //$NON-NLS-1$
        jButtonOK.setBounds(new java.awt.Rectangle(282, 321, 78, 27));
        // jButtonOK.setLabel("OK");
        jButtonCancel.setText(CMMessages.getString("BUTTON_CANCEL")); //$NON-NLS-1$
        jButtonCancel.setBounds(new java.awt.Rectangle(374, 321, 77, 27));
        //jButtonCancel.setLabel("Cancel");
        //jLabelDescription.setText(CMMessages.getString("TESTDATA_HERE_DESCRIPTION_FORMULAS")); //$NON-NLS-1$
        jLabelDescription.setBounds(new java.awt.Rectangle(28, 195, 414, 24));
        jLabelDescription.setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 14)); //$NON-NLS-1$
        jLabelDescription.setToolTipText(CMMessages.getString("TESTDATA_DESCRPTION_FORMULA")); //$NON-NLS-1$
        //jTextAreaDescription.setText(CMMessages.getString("TESTDATA_USES_DESCRIPTION_FORMULA")); //$NON-NLS-1$
        jTextAreaDescription.setBounds(new java.awt.Rectangle(28, 224, 418, 83));
        jTextAreaDescription.setCaretColor(new java.awt.Color(212, 208, 200));
        jTextAreaDescription.setBackground(this.getBackground());//new java.awt.Color(212, 208, 200));
        jTextAreaDescription.setForeground(this.getForeground());
        jTextAreaDescription.setDisabledTextColor(new java.awt.Color(212, 208, 200));
        //jTextAreaDescription.setBackground(new java.awt.Color(212, 208, 200));
        jTextAreaDescription.setLineWrap(true);
        jTextAreaDescription.setWrapStyleWord(true);
        jTextAreaDescription.setEditable(false);
        jTextAreaDescription.setFont(new java.awt.Font("SansSerif", java.awt.Font.PLAIN, 12)); //$NON-NLS-1$
        jButtonOK.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) { jButtonOKActionPerformed(e); }
            });
        jButtonCancel.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) { jButtonCancelActionPerformed(e); }
            });
        jListFunction.addListSelectionListener(
            new ListSelectionListener() {
                public void valueChanged(ListSelectionEvent e) { jListFunctionValueChanged(e); }
            });
        getJListFormulas().addListSelectionListener(
            new ListSelectionListener() {
                public void valueChanged(ListSelectionEvent e) { jListFormulasValueChanged(e); }
            });
        jListFunction.setListData(loadCategories());//(BusinessRules.FORMULAS_CATEGORY);
        jListFunction.setSelectedIndex(0);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension dlgSize = this.getPreferredSize();
        this.setLocation((screenSize.width - dlgSize.width) / 2 /*+ loc.x*/,
            (screenSize.height - dlgSize.height) / 2 /*+ loc.y*/);
        setResizable(false);
        getRootPane().setDefaultButton(jButtonOK);      
        try {
			if(cmFrame.getTreeWorkspaceView().getCurrentTestObject().getDecimalSeparator().equals(",")){
				CMFormatFactory.setTestObjectLocale(new Locale("de","DE"));
			}
			else{
				CMFormatFactory.setTestObjectLocale(Locale.US);
			}
		} catch (Exception e1) {
			
		}
    }

    private Object[] loadCategories() {
		return CMFormulaCategory.values();
	}

	protected void processWindowEvent(WindowEvent e) {
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            cancel();
        }
        super.processWindowEvent(e);
    }

    void cancel() {
        dispose();
    }

    public void jButtonOKActionPerformed(ActionEvent e) {
    	//TestDataFormat newformatter= new TestDataFormat();
        //CMDialogFormulasValues cmd = new CMDialogFormulasValues(cmFrame, getFormulaSelected(), getDescriptionSelected(), cmGrid, newformatter, false);
        CMDialogFormulasValues cmd1 = new CMDialogFormulasValues();
        cmd1.setFormulaEnum((CMFormulas) getJListFormulas().getSelectedValue());
        this.setVisible(false);
        cmd1.setVisible(true);
//		HCanedo_17112005_begin
        //cmd.setTypeDataForInsertField(m_TypeDataInsertField);
//		HCanedo_17112005_end
//        if (cmd.cantParam(getFormulaSelected()) == 0) {
//            cmd.inCaseNotParam();
//            insertFieldFormula = cmd.getInsertFieldFormula();
//            insertFieldValue = cmd.getInsertFieldValue();
//            insertFieldFormat = cmd.getInsertFieldFormat();
//            insertFieldType = cmd.getInsertFieldType();
//            m_Formatter=cmd.getM_formatterSelected();
//            cancel();
//        }
//        else {
//            cancel();
//            cmd.setVisible(true);
//            insertFieldFormula = cmd.getInsertFieldFormula();
//            insertFieldValue = cmd.getInsertFieldValue();
//            insertFieldFormat = cmd.getInsertFieldFormat();
//            insertFieldType = cmd.getInsertFieldType();
//            m_Formatter=cmd.getM_formatterSelected();
//        }

    } 
    public void jButtonCancelActionPerformed(ActionEvent e) {
        cancel();
    }

    public void jListFunctionValueChanged(ListSelectionEvent e) {
    	CMFormulaCategory category = (CMFormulaCategory) jListFunction.getSelectedValue();
    	((DefaultListModel)getJListFormulas().getModel()).clear();
    	for (CMFormulas formula: CMFormulas.values()){
    		if(formula.getCategory() == category){
    			((DefaultListModel)getJListFormulas().getModel()).addElement(formula);
    		}
    	}
    	getJListFormulas().setSelectedIndex(0);
    }

    public void jListFormulasValueChanged(ListSelectionEvent e) {
    	
    	CMFormulas formula = (CMFormulas) getJListFormulas().getSelectedValue();
    	if(formula != null)
    	{
    		jLabelDescription.setText(formula.getCanonicalFormula());
    		jTextAreaDescription.setText(formula.getDescription());
    	}
    }

    private JPanel jPanelFunction = new JPanel();
    private JPanel jPanelFormulas = new JPanel();
    private JScrollPane jScrollPaneFunction = new JScrollPane();
    private JScrollPane jScrollPaneFormulas = new JScrollPane();
    private JList jListFunction = new JList();
    private JList jListFormulas;
    private JButton jButtonOK = new JButton();
    private JButton jButtonCancel = new JButton();
    private JLabel jLabelDescription = new JLabel();
    private JTextArea jTextAreaDescription = new JTextArea();
    private CMFrameView cmFrame;
    private CMPanelFormulas panelFormulas = new CMPanelFormulas(); 
    private JPanel panelDialog = new JPanel();
    //private CMGridTDStructure cmGrid;
    //private String descriptionSelected;
    //private String formulaSelected;
    //public String insertFieldFormula;
    //public String insertFieldValue;
    //public String insertFieldFormat;
    //public String insertFieldType;

	protected List getOrder() {
		List<JComponent> focusOrder = new ArrayList<JComponent>();
		focusOrder.add(jListFunction);
		focusOrder.add(getJListFormulas());
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

	public JList getJListFormulas() {
		if(jListFormulas == null){
			jListFormulas = new JList();
			jListFormulas.setModel(new DefaultListModel());
		}
		return jListFormulas;
	}
}
