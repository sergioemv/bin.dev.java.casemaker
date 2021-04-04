package bi.view.testdataviews;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import model.BusinessRules;
import model.CMDefaultValue;
import model.ObjectTypes;
import model.StructureTestData;
import model.TDStructure;
import model.Technology;
import model.TestData;
import model.TestDataFormat;
import model.ITypeData;

import bi.controller.ToolVendorManager;
import bi.controller.testdata.formula.CMFormulasEditController;

import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.mainframeviews.CMFrameView;
import bi.view.undomanagementviews.CMCompoundEdit;
import bi.view.utils.CMBaseJComboBox;
import bi.view.utils.CMDefaultDialog;
import bi.view.utils.CMFormatFactory;
import bi.view.utils.CMModalResult;
import bi.view.utils.JCustomDialog;

@SuppressWarnings("serial")
public class CMDialogInsertFields extends JCustomDialog {
    public CMDialogInsertFields(ITypeData p_TypeData, CMFrameView p_FrameView,Technology p_Technology) {
        super(p_FrameView);
        m_FrameView = p_FrameView;
        m_TypeData = p_TypeData;
        m_Formatter=p_TypeData.getFormatter();
        this.m_Technology = p_Technology;
        initGUI();
    }

    public void initGUI() {

        jPanel1.setLayout(null);

        jPanel1.setPreferredSize(new java.awt.Dimension(784, 144));//I changed 712 to 784
        
        initializeObjectTypeComboBox();
        
        jPanel1.add(jTextFieldKey);
        jPanel1.add(jTextFieldField);
        jPanel1.add(jTextFieldName);
        jPanel1.add(jCBObjectType);
        jPanel1.add(jComboBoxType);
        jPanel1.add(jTextFieldLength);
        jPanel1.add(jTextFieldPrefix);
        jPanel1.add(jTextFieldSuffix);
        jPanel1.add(jTextFieldFormat);
        jPanel1.add(jTextFieldFormula);
        jPanel1.add(jTextFieldValue);
        jPanel1.add(jLabelKey);
        jPanel1.add(jLabelField);
        jPanel1.add(jLabelName);
        jPanel1.add(jLabelObjectType);
     //   jPanel1.add(jLabelNewColumn);

        jPanel1.add(jLabelType);
        jPanel1.add(jLabelLength);
        jPanel1.add(jLabelPrefix);
        jPanel1.add(jLabelSuffix);
        jPanel1.add(jLabelFormat);
        jPanel1.add(jLabelFormula);
        jPanel1.add(jLabelvalue);
        jPanel1.add(jButtonOK);
        jPanel1.add(jButtonCancel);
        jPanel1.add(jButtonAssignFormat);
        jPanel1.add(jButtonAssignFormula);
        jTextFieldKey.setBounds(new java.awt.Rectangle(13, 49, 35, 29));
        jTextFieldKey.setEditable(false);
        jTextFieldKey.setDisabledTextColor(new java.awt.Color(0, 51, 51));
        jTextFieldKey.setBackground(new java.awt.Color(255, 255, 255));
        jTextFieldField.setBounds(new java.awt.Rectangle(50, 49, 71, 30));
         //////////////////////Nuevo codigo fields y name por defecto////////////////////
         jTextFieldName.setText(m_TypeData.getName());
         jTextFieldField.setText(m_TypeData.getField());
         ////////////////////////////////////////////////////////////////////////////////

        jTextFieldName.setBounds(new java.awt.Rectangle(123, 49, 71, 30));
        //My add
        jCBObjectType.setBounds(new java.awt.Rectangle(196, 49, 91, 30));
      //  jTextFieldNewColumn.setBounds(new java.awt.Rectangle(289, 49, 71, 30));

        jComboBoxType.setBounds(new java.awt.Rectangle(289, 49, 71, 30));
        jTextFieldLength.setBounds(new java.awt.Rectangle(362, 49, 71, 30));
        jTextFieldLength.setEditable(false);
        jTextFieldLength.setBackground(new java.awt.Color(255, 255, 255));
        jTextFieldPrefix.setBounds(new java.awt.Rectangle(435, 49, 71, 30));
        jTextFieldSuffix.setBounds(new java.awt.Rectangle(508, 49, 71, 30));
        jTextFieldFormat.setBounds(new java.awt.Rectangle(581, 49, 58, 30));
        jTextFieldFormat.setEditable(false);
        jTextFieldFormat.setText(BusinessRules.FORMULAS_FORMAT_STRING);
        jTextFieldFormat.setBackground(new java.awt.Color(255, 255, 255));
        jTextFieldFormula.setBounds(new java.awt.Rectangle(654, 49, 59, 30));
        jTextFieldFormula.setEditable(false);
        jTextFieldFormula.setBackground(new java.awt.Color(255, 255, 255));
        jTextFieldValue.setBounds(new java.awt.Rectangle(727, 49, 71, 30));
        jLabelKey.setText(CMMessages.getString("TDSTRUCTURE_FIELD_KEY")); //$NON-NLS-1$
        jLabelKey.setBounds(new java.awt.Rectangle(13, 24, 35, 22));
        jLabelKey.setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 11)); //$NON-NLS-1$
        jLabelField.setText(CMMessages.getString("TDSTRUCTURE_FIELD_FIELD")); //$NON-NLS-1$
        jLabelField.setBounds(new java.awt.Rectangle(50, 24, 70, 22));
        jLabelField.setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 11)); //$NON-NLS-1$
        jLabelName.setText(CMMessages.getString("TDSTRUCTURE_FIELD_NAME")); //$NON-NLS-1$
        jLabelName.setBounds(new java.awt.Rectangle(123, 24, 70, 22));
        jLabelName.setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 11)); //$NON-NLS-1$
        //My adds
        jLabelObjectType.setBounds(new java.awt.Rectangle(196, 24, 70, 22));
        jLabelObjectType.setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 11)); //$NON-NLS-1$
        jLabelObjectType.setText(CMMessages.getString("TDSTRUCTURE_FIELD_OBJECT_TYPE")); //$NON-NLS-1$

      /*  jLabelNewColumn.setBounds(new java.awt.Rectangle(289, 24, 70, 22));
        jLabelNewColumn.setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 11)); //$NON-NLS-1$
        jLabelNewColumn.setText(CMMessages.getString("TDSTRUCTURE_FIELD_NEW_COLUMN"));
        */
        jLabelType.setText(CMMessages.getString("TDSTRUCTURE_FIELD_TYPE")); //$NON-NLS-1$
        jLabelType.setBounds(new java.awt.Rectangle(289, 24, 70, 22));
        jLabelType.setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 11)); //$NON-NLS-1$
        jLabelLength.setText(CMMessages.getString("TDSTRUCTURE_FIELD_LENGTH")); //$NON-NLS-1$
        jLabelLength.setBounds(new java.awt.Rectangle(362, 24, 70, 22));
        jLabelLength.setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 11)); //$NON-NLS-1$
        jLabelPrefix.setText(CMMessages.getString("TDSTRUCTURE_FIELD_PREFIX")); //$NON-NLS-1$
        jLabelPrefix.setBounds(new java.awt.Rectangle(435, 24, 70, 22));
        jLabelPrefix.setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 11)); //$NON-NLS-1$
        jLabelSuffix.setText(CMMessages.getString("TDSTRUCTURE_FIELD_SUFFIX")); //$NON-NLS-1$
        jLabelSuffix.setBounds(new java.awt.Rectangle(508, 24, 70, 22));
        jLabelSuffix.setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 11)); //$NON-NLS-1$
        jLabelFormat.setText(CMMessages.getString("TDSTRUCTURE_FIELD_FORMAT")); //$NON-NLS-1$
        jLabelFormat.setBounds(new java.awt.Rectangle(581, 24, 70, 22));
        jLabelFormat.setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 11)); //$NON-NLS-1$
        jLabelFormula.setText(CMMessages.getString("TDSTRUCTURE_FIELD_FORMULA")); //$NON-NLS-1$
        jLabelFormula.setBounds(new java.awt.Rectangle(654, 24, 70, 22));
        jLabelFormula.setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 11)); //$NON-NLS-1$
        jLabelvalue.setText(CMMessages.getString("TDSTRUCTURE_FIELD_VALUE")); //$NON-NLS-1$
        jLabelvalue.setBounds(new java.awt.Rectangle(727, 24, 70, 22));
        jLabelvalue.setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 11)); //$NON-NLS-1$
        jButtonOK.setText(CMMessages.getString("BUTTON_OK")); //$NON-NLS-1$
        jButtonOK.setBounds(new java.awt.Rectangle(320, 98, 73, 27));
        jButtonCancel.setText(CMMessages.getString("BUTTON_CANCEL")); //$NON-NLS-1$
        jButtonCancel.setBounds(new java.awt.Rectangle(407, 98, 73, 27));
        jComboBoxType.addItem(BusinessRules.TESTDATA_STATE_BINARY);
        jComboBoxType.addItem(BusinessRules.TESTDATA_STATE_BIT);
        jComboBoxType.addItem(BusinessRules.TESTDATA_STATE_BOOLEAN);
        jComboBoxType.addItem(BusinessRules.TESTDATA_STATE_CHAR);
        jComboBoxType.addItem(BusinessRules.TESTDATA_STATE_DATETIME);
        jComboBoxType.addItem(BusinessRules.TESTDATA_STATE_DECIMAL);
        jComboBoxType.addItem(BusinessRules.TESTDATA_STATE_FLOAT);
        jComboBoxType.addItem(BusinessRules.TESTDATA_STATE_INT);
        jComboBoxType.addItem(BusinessRules.TESTDATA_STATE_MONEY);
        jComboBoxType.addItem(BusinessRules.TESTDATA_STATE_NCHAR);
        jComboBoxType.addItem(BusinessRules.TESTDATA_STATE_NTEXT);
        jComboBoxType.addItem(BusinessRules.TESTDATA_STATE_NUMERIC);
        jComboBoxType.addItem(BusinessRules.TESTDATA_STATE_NVARCHAR);
        jComboBoxType.addItem(BusinessRules.TESTDATA_STATE_REAL);
        jComboBoxType.addItem(BusinessRules.TESTDATA_STATE_SMALLDATETIME);
        jComboBoxType.addItem(BusinessRules.TESTDATA_STATE_SMALLINT);
        jComboBoxType.addItem(BusinessRules.TESTDATA_STATE_SMALLMONEY);
        jComboBoxType.addItem(BusinessRules.TESTDATA_STATE_TEXT);
        jComboBoxType.addItem(BusinessRules.TESTDATA_STATE_TIMESTAMP);
        jComboBoxType.addItem(BusinessRules.TESTDATA_STATE_TINYINT);
        jComboBoxType.addItem(BusinessRules.TESTDATA_STATE_VARBINARY);
        jComboBoxType.addItem(BusinessRules.TESTDATA_STATE_VARCHAR);
        jComboBoxType.setSelectedItem(BusinessRules.TESTDATA_STATE_TEXT);
        jTextFieldKey.addMouseListener(
            new MouseAdapter() {
                public void mouseClicked(MouseEvent e) { jTextFieldKeyMouseClicked(e); }
            });
        jButtonOK.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) { jButtonOKActionPerformed(e); }
            });
        jButtonCancel.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) { jButtonCancelActionPerformed(e); }
            });
        setBounds(new java.awt.Rectangle(0, 0, 819, 167)); //I change 716 to 819
        setTitle(CMMessages.getString("TESTDATA_DIALOG_INSERT_FIELDS")); //$NON-NLS-1$
        setModal(true);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension dlgSize = this.getSize(); // this.getPreferredSize();
        this.setLocation((screenSize.width - dlgSize.width) / 2, (screenSize.height - dlgSize.height) / 2);
//hcanedo_21_09_2004_begin
        setResizable(false);
//hcanedo_21_09_2004_end
        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);
        jComboBoxType.addItemListener(
            new ItemListener() {
                public void itemStateChanged(ItemEvent e) { jComboBoxTypeItemStateChanged(e); }
            });
        jTextFieldLength.setText(getDefaultValue(jComboBoxType.getSelectedItem().toString()));
        jButtonAssignFormat.setText("........");
        jButtonAssignFormat.setBounds(new java.awt.Rectangle(638, 49, 16, 31));//I change 546 to 586
        jButtonAssignFormula.setText(".......");
        jButtonAssignFormula.setBounds(new java.awt.Rectangle(712, 49, 15, 31));//I change 619 to 659
        jButtonAssignFormat.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) { jButtonAssignFormatActionPerformed(e); }
            });
        jButtonAssignFormula.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) { jButtonAssignFormulaActionPerformed(e); }
            });
        jTextFieldValue.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){jTextFieldValueActionPerformed(e);}});
        jTextFieldValue.addFocusListener(new FocusAdapter(){public void focusLost(FocusEvent e){jTextFieldValueFocusLost(e);}});
//hcanedo_07102005_begin
        getRootPane().setDefaultButton(jButtonOK);
        jTextFieldKey.addKeyListener(new KeyAdapter(){
        	 public void keyPressed(KeyEvent evt) {
                 // Check for key characters.
                 if (evt.getKeyChar() == 'k' || evt.getKeyChar() == 'K') {
                	 if (m_TypeData.getKey().equals("K")) //$NON-NLS-1$
                     {
                         m_TypeData.setKey(""); //$NON-NLS-1$
                         jTextFieldKey.setText(""); //$NON-NLS-1$
                     }
                     else {
                         m_TypeData.setKey("K"); //$NON-NLS-1$
                         jTextFieldKey.setText("K"); //$NON-NLS-1$
                     }
                 }

             }

        });
//hcanedo_07102005_end

    }

    private void initializeObjectTypeComboBox() {
    	
    	ToolVendorManager toolvendormanager = ToolVendorManager.INSTANCE;
   		
		String toolVendor = CMApplication.frame.getTreeWorkspaceView().getCurrentTestObject().getToolVendor();
	 		   
		String technology = CMApplication.frame.getTreeWorkspaceView().getCurrentTestObject()
			.getToolVendorTechnology();

		Technology tech = toolvendormanager.findTechnologyByName(toolVendor,technology);
	
	   int sizeObjectTypes = tech.getM_ObjectTypesValue().size();
	   for (int i=0; i < sizeObjectTypes; i++){    		
		    ObjectTypes objectTypes = (ObjectTypes)(tech.getM_ObjectTypesValue().elementAt(i));
		   	String cbItem =  objectTypes.getM_Name();		 			   	
		   	jCBObjectType.addItem(cbItem);		 
	   }
	}

	public void jTextFieldKeyMouseClicked(MouseEvent e) {
        if (m_TypeData.getKey().equals("K")) //$NON-NLS-1$
        {
            m_TypeData.setKey(""); //$NON-NLS-1$
            jTextFieldKey.setText(""); //$NON-NLS-1$
        }
        else {
            m_TypeData.setKey("K"); //$NON-NLS-1$
            jTextFieldKey.setText("K"); //$NON-NLS-1$
        }
    }

    public void jButtonOKActionPerformed(ActionEvent e) {
        String messageControl;
        	 messageControl = controlNameAndFieldRepeat();
        if (messageControl.equals("")) {
            isOkSelected = true;
        	String value=CMFormatFactory.applyAnyFormat(m_Formatter, jTextFieldValue.getText(),m_Formatter);
		 if(CMFormatFactory.isSuccessFormated())
         {
        	jTextFieldValue.setText(value);
        	//m_ce.addEdit(CMModelEditFactory.INSTANCE.createChangeKeyInTypeDataModelEdit(m_TypeData,jTextFieldKey.getText()));
            m_TypeData.setKey(jTextFieldKey.getText());
            //m_ce.addEdit(CMModelEditFactory.INSTANCE.createChangeFieldInTypeDataModelEdit(m_TypeData,jTextFieldField.getText()));
            m_TypeData.setField(jTextFieldField.getText());
            //m_ce.addEdit(CMModelEditFactory.INSTANCE.createChangeNameInTypeDataModelEdit(m_TypeData,jTextFieldName.getText()));
            m_TypeData.setName(jTextFieldName.getText());
            //My add
            //m_ce.addEdit(CMModelEditFactory.INSTANCE.createChangeToolVendorOTInTypeDataModelEdit(m_TypeData,jCBObjectType.getSelectedItem().toString()));
          //ccastedo 27.09.06  m_TypeData.setToolVendorOT(jCBObjectType.getSelectedItem().toString());
            m_TypeData.setStateOT(jCBObjectType.getSelectedIndex());
            
            //m_ce.addEdit(CMModelEditFactory.INSTANCE.createChangeTypeInTypeDataModelEdit(m_TypeData,jComboBoxType.getSelectedItem().toString()));
            m_TypeData.setType(jComboBoxType.getSelectedItem().toString());
            String length = this.m_FrameView.getGridTDStructure().length(jComboBoxType.getSelectedItem().toString());
            //m_ce.addEdit(CMModelEditFactory.INSTANCE.createChangeLengthInTypeDataModelEdit(m_TypeData,length));
            m_TypeData.setLength(length);
            //m_ce.addEdit(CMModelEditFactory.INSTANCE.createChangePrefixInTypeDataModelEdit(m_TypeData,jTextFieldPrefix.getText()));
            m_TypeData.setPrefix(jTextFieldPrefix.getText());
            //m_ce.addEdit(CMModelEditFactory.INSTANCE.createChangeSuffixInTypeDataModelEdit(m_TypeData,jTextFieldSuffix.getText()));
            m_TypeData.setSuffix(jTextFieldSuffix.getText());
            //m_ce.addEdit(CMModelEditFactory.INSTANCE.createChangeFormatInTypeDataModelEdit(m_TypeData,jTextFieldFormat.getText()));
            m_TypeData.setFormat(jTextFieldFormat.getText());
            if(m_Formatter == null){
            	TestDataFormat format = new TestDataFormat();
            	//m_ce.addEdit(CMModelEditFactory.INSTANCE.createChangeFormatterInTypeDataModelEdit(m_TypeData,format));
            	m_TypeData.setFormatter(format);}
            else{
            	m_TypeData.setFormatter(m_Formatter);
            	//m_ce.addEdit(CMModelEditFactory.INSTANCE.createChangeFormatterInTypeDataModelEdit(m_TypeData,m_Formatter));
            }
            //m_ce.addEdit(CMModelEditFactory.INSTANCE.createChangeFormulaInTypeDataModelEdit(m_TypeData,jTextFieldFormula.getText()));
            //m_TypeData.setFormula(jTextFieldFormula.getText());
            if(!jTextFieldFormula.getText().equals(""));
            	//m_ce.addEdit(CMModelEditFactory.INSTANCE.createChangeisFormulaInTypeDataModelEdit(m_TypeData,true));
            	//m_TypeData.setisFormula(true);
            //m_ce.addEdit(CMModelEditFactory.INSTANCE.createChangeValueInTypeDataModelEdit(m_TypeData,jTextFieldValue.getText()));
            	CMDefaultValue defaultValue = new CMDefaultValue(jTextFieldValue.getText());
                m_TypeData.setValue(defaultValue);
                //TODO ADD TO THE COMPOUND
            	//m_TypeData.setStringValue(jTextFieldValue.getText());
            dispose();
         }
          else
            {
                JOptionPane.showMessageDialog(this, CMMessages.getString("TESTDATA_ASSIGN_FORMAT_MENSSAGE_ERROR"), CMMessages.getString("TESTDATA_ASSIGN_FORMAT_TITLE_ERROR"), JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$ //$NON-NLS-2$
            }
        }
        else {
            JOptionPane.showMessageDialog(m_FrameView,
                CMMessages.getString("TESTDATA_NAME_FIELD_EQUALS_MENSSAGE_ERROR") + "\n" + messageControl,
                CMMessages.getString("TESTDATA_IMPORT_TITLE_ERROR"), JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$ //$NON-NLS-2$
        }
    }

    public void jButtonCancelActionPerformed(ActionEvent e) {
        isOkSelected = false;
        dispose();
    }

    public String getDefaultValue(String type) {
        if (type.equals(BusinessRules.TESTDATA_STATE_BINARY)) { return BusinessRules.TESTDATA_STATE_BINARY_DEFAULT_VALUE; }
        else if (type.equals(BusinessRules.TESTDATA_STATE_BIT)) { return BusinessRules.TESTDATA_STATE_BIT_DEFAULT_VALUE; }
        else if (type.equals(BusinessRules.TESTDATA_STATE_BOOLEAN))
            { return BusinessRules.TESTDATA_STATE_BOOLEAN_DEFAULT_VALUE; }
        else if (type.equals(BusinessRules.TESTDATA_STATE_CHAR)) { return BusinessRules.TESTDATA_STATE_CHAR_DEFAULT_VALUE; }
        else if (type.equals(BusinessRules.TESTDATA_STATE_DATETIME)) {
            return BusinessRules.TESTDATA_STATE_DATETIME_DEFAULT_VALUE;
        }
        else if (type.equals(BusinessRules.TESTDATA_STATE_DECIMAL))
            { return BusinessRules.TESTDATA_STATE_DECIMAL_DEFAULT_VALUE; }
        else if (type.equals(BusinessRules.TESTDATA_STATE_FLOAT)) { return BusinessRules.TESTDATA_STATE_FLOAT_DEFAULT_VALUE; }
        else if (type.equals(BusinessRules.TESTDATA_STATE_INT)) { return BusinessRules.TESTDATA_STATE_INT_DEFAULT_VALUE; }
        else if (type.equals(BusinessRules.TESTDATA_STATE_MONEY)) { return BusinessRules.TESTDATA_STATE_MONEY_DEFAULT_VALUE; }
        else if (type.equals(BusinessRules.TESTDATA_STATE_NCHAR)) { return BusinessRules.TESTDATA_STATE_NCHAR_DEFAULT_VALUE; }
        else if (type.equals(BusinessRules.TESTDATA_STATE_NTEXT)) { return BusinessRules.TESTDATA_STATE_NTEXT_DEFAULT_VALUE; }
        else if (type.equals(BusinessRules.TESTDATA_STATE_NUMERIC))
            { return BusinessRules.TESTDATA_STATE_NUMERIC_DEFAULT_VALUE; }
        else if (type.equals(BusinessRules.TESTDATA_STATE_NVARCHAR)) {
            return BusinessRules.TESTDATA_STATE_NVARCHAR_DEFAULT_VALUE;
        }
        else if (type.equals(BusinessRules.TESTDATA_STATE_REAL)) { return BusinessRules.TESTDATA_STATE_REAL_DEFAULT_VALUE; }
        else if (type.equals(BusinessRules.TESTDATA_STATE_SMALLDATETIME)) {
            return BusinessRules.TESTDATA_STATE_SMALLDATETIME_DEFAULT_VALUE;
        }
        else if (type.equals(BusinessRules.TESTDATA_STATE_SMALLINT)) {
            return BusinessRules.TESTDATA_STATE_SMALLINT_DEFAULT_VALUE;
        }
        else if (type.equals(BusinessRules.TESTDATA_STATE_SMALLMONEY)) {
            return BusinessRules.TESTDATA_STATE_SMALLMONEY_DEFAULT_VALUE;
        }
        else if (type.equals(BusinessRules.TESTDATA_STATE_TEXT)) { return BusinessRules.TESTDATA_STATE_TEXT_DEFAULT_VALUE; }
        else if (type.equals(BusinessRules.TESTDATA_STATE_TIMESTAMP)) {
            return BusinessRules.TESTDATA_STATE_TIMESTAMP_DEFAULT_VALUE;
        }
        else if (type.equals(BusinessRules.TESTDATA_STATE_TINYINT))
            { return BusinessRules.TESTDATA_STATE_TINYINT_DEFAULT_VALUE; }
        else if (type.equals(BusinessRules.TESTDATA_STATE_VARBINARY)) {
            return BusinessRules.TESTDATA_STATE_VARBINARY_DEFAULT_VALUE;
        }
        else if (type.equals(BusinessRules.TESTDATA_STATE_VARCHAR))
            { return BusinessRules.TESTDATA_STATE_VARCHAR_DEFAULT_VALUE; }
        else { return ""; }
    }


    public void jComboBoxTypeItemStateChanged(ItemEvent e) {
        jTextFieldLength.setText(getDefaultValue(jComboBoxType.getSelectedItem().toString()));
    }

    //My adds
    public void jCBObjectTypeActionPerformed(ActionEvent e) {
        	jCBObjectType.setActionCommand(jCBObjectType.getSelectedItem().toString());

        }
    public void jCBObjectTypeItemStateChanged(ItemEvent e) {
    //	jCBObjectType.set.setText(getDefaultValue(jCBObjectType.getSelectedItem().toString()));
    }

    public String controlNameAndFieldRepeat() {
        TDStructure tdStructure = m_FrameView.getGridTDStructure().getTDStructure();
        int index = CMIndexTDStructureUpdate.getInstance().getIndex();
        int globalindex = ((StructureTestData)tdStructure.getM_StructureTestData().elementAt(index)).getGlobalIndex();
        StructureTestData structureTD = (StructureTestData)tdStructure.getM_StructureTestData().elementAt(index);
        boolean swExist = uniqueValueFieldName(structureTD, jTextFieldName.getText(), jTextFieldField.getText());
        if (!swExist) {
            return structureTD.getName();
        }
        else {
            for (int i = 0; i < tdStructure.getTestDataCombination().getM_TestDatas().size(); i++) {
                TestData td = (TestData)tdStructure.getTestDataCombination().getM_TestDatas().elementAt(i);
                for (int j = 0; j < td.getM_TDStructure().getM_StructureTestData().size(); j++) {
                    int globalindex2 = ((StructureTestData)td.getM_TDStructure().getM_StructureTestData().elementAt(j)).getGlobalIndex();
                    if (globalindex == globalindex2) {
                        StructureTestData tds = ((StructureTestData)td.getM_TDStructure().getM_StructureTestData().elementAt(j));
                        swExist = uniqueValueFieldName(tds, jTextFieldName.getText(), jTextFieldField.getText());
                        if (!swExist) {
                            return td.getName() + "  " + tds.getName();
                        }
                    }
                }
            }
            return "";
        }
    }

    public boolean uniqueValueFieldName(StructureTestData p_std, String p_valueName, String p_valueField) {
        boolean sw = true;
        for (int i = 0; i < p_std.getTypeData().size(); i++) {
            ITypeData typ = (ITypeData)p_std.getTypeData().elementAt(i);
            if (typ.getField().equalsIgnoreCase(p_valueField))
                sw = false;
        }
        for (int i = 0; i < p_std.getTypeData().size(); i++) {
            ITypeData typ = (ITypeData)p_std.getTypeData().elementAt(i);
            if (typ.getName().equalsIgnoreCase(p_valueName))
                sw = false;
        }
        return sw;
    }

    @SuppressWarnings("static-access")
	public void jButtonAssignFormatActionPerformed(ActionEvent e) {
//hcanedo_21_09_2004_begin
        CMDialogFormat cmd = new CMDialogFormat(m_FrameView,m_Formatter);
//hcanedo_21_09_2004_end
        cmd.setVisible(true);
        if (cmd.buttonOkclicked) {
            String value=CMFormatFactory.applyAnyFormat(cmd.getFormatterResult(), jTextFieldValue.getText(),m_Formatter);
            if(CMFormatFactory.isSuccessFormated())
            {
            	jTextFieldFormat.setText(cmd.getFormatResult());
            	m_Formatter=cmd.getFormatterResult();
        		jTextFieldValue.setText(value);
            }
            else
            {
                JOptionPane.showMessageDialog(this, CMMessages.getString("TESTDATA_ASSIGN_FORMAT_MENSSAGE_ERROR"), CMMessages.getString("TESTDATA_ASSIGN_FORMAT_TITLE_ERROR"), JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$ //$NON-NLS-2$
            }
        }
    }

    public void jButtonAssignFormulaActionPerformed(ActionEvent e) {
    	CMDefaultDialog cm = new CMDefaultDialog();
    	CMFormulasEditController formulasEditController = new CMFormulasEditController();

    	cm.setJPanelContained(formulasEditController.getPanelFormulas());
        cm.setSize(new java.awt.Dimension(655,560));
        cm.setVisible(true);
        if(cm.getModalResult() == CMModalResult.OK){
        	if(CMApplication.frame.isIsPanelTestDataSelected()){
        		//TODO add Undo/Redo
        		//set the model with the ICMValue
        		m_TypeData.setValue(formulasEditController.getChild().getFormula());
        	}
        	else{
        		//TODO add Undo/Redo
//        		set the model with the ICMValue
        		m_TypeData.setValue(formulasEditController.getChild().getFormula());
        	}
        }
//        CMDialogFormulas cmd = new CMDialogFormulas();
//        cmd.setSize(new java.awt.Dimension(486, 394));
////		HCanedo_17112005_begin
//        cmd.setTypeDataInsertField(m_TypeData);
////		HCanedo_17112005_end
//        cmd.setVisible(true);
//        if (!(cmd.insertFieldFormula.equals("") && cmd.insertFieldValue.equals("")) && !(cmd.insertFieldFormat.equals("") &&
//            cmd.insertFieldType.equals("")));
//        {
//            jTextFieldFormat.setText(cmd.insertFieldFormat);
//            m_Formatter=cmd.getFormatterFormula();
//            jTextFieldFormula.setText(cmd.insertFieldFormula);
//            jTextFieldValue.setText(cmd.insertFieldValue);
//            jComboBoxType.setSelectedItem(cmd.insertFieldType);
//
//        }
    }

    public void jTextFieldValueActionPerformed(ActionEvent e) {
        String value=CMFormatFactory.applyAnyFormat(m_Formatter, jTextFieldValue.getText(),m_Formatter);
		 if(CMFormatFactory.isSuccessFormated())
         {
        	jTextFieldValue.setText(value);
         }
          else
            {
                isShowDialogFormatError=true;
                JOptionPane.showMessageDialog(this, CMMessages.getString("TESTDATA_ASSIGN_FORMAT_MENSSAGE_ERROR"), CMMessages.getString("TESTDATA_ASSIGN_FORMAT_TITLE_ERROR"), JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$ //$NON-NLS-2$
                isShowDialogFormatError=false;
            }
    }

    public void jTextFieldValueFocusLost(FocusEvent e) {
        if(!isShowDialogFormatError){
        String value=CMFormatFactory.applyAnyFormat(m_Formatter, jTextFieldValue.getText(),m_Formatter);
		 if(CMFormatFactory.isSuccessFormated())
         {
        	jTextFieldValue.setText(value);
         }
          else
            {
                JOptionPane.showMessageDialog(this, CMMessages.getString("TESTDATA_ASSIGN_FORMAT_MENSSAGE_ERROR"), CMMessages.getString("TESTDATA_ASSIGN_FORMAT_TITLE_ERROR"), JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$ //$NON-NLS-2$
            }
        }
   }
    public TestDataFormat getM_Formatter() {
		return m_Formatter;
	}

	public void setM_Formatter(TestDataFormat formatter) {
		m_Formatter = formatter;
	}
	private CMCompoundEdit m_ce;
    private JPanel jPanel1 = new JPanel();
    private JTextField jTextFieldKey = new JTextField();
    private JTextField jTextFieldField = new JTextField();
    private JTextField jTextFieldName = new JTextField();
    private CMBaseJComboBox jComboBoxType = new CMBaseJComboBox(this);
    //My add
    private CMBaseJComboBox jCBObjectType = new CMBaseJComboBox(this);
   
    private JTextField jTextFieldLength = new JTextField();
    private JTextField jTextFieldPrefix = new JTextField();
    private JTextField jTextFieldSuffix = new JTextField();
    private JTextField jTextFieldFormat = new JTextField();
    private JTextField jTextFieldFormula = new JTextField();
    private JTextField jTextFieldValue = new JTextField();
    private JLabel jLabelKey = new JLabel();
    private JLabel jLabelField = new JLabel();
    private JLabel jLabelName = new JLabel();
    //My add
    private JLabel jLabelObjectType = new JLabel();
 //   private JLabel jLabelNewColumn = new JLabel();
    private JLabel jLabelType = new JLabel();
    private JLabel jLabelLength = new JLabel();
    private JLabel jLabelPrefix = new JLabel();
    private JLabel jLabelSuffix = new JLabel();
    private JLabel jLabelFormat = new JLabel();
    private JLabel jLabelFormula = new JLabel();
    private JLabel jLabelvalue = new JLabel();
    private JButton jButtonOK = new JButton();
    private JButton jButtonCancel = new JButton();
    private ITypeData m_TypeData;
    public boolean isOkSelected = false;
    private CMFrameView m_FrameView;
    private JButton jButtonAssignFormat = new JButton();
    private JButton jButtonAssignFormula = new JButton();
    private boolean isShowDialogFormatError=false;
    private TestDataFormat m_Formatter=null;
    private Technology m_Technology = null;
	@SuppressWarnings("unchecked")
	protected List getOrder() {
		List focusOrder= new ArrayList();
		focusOrder.add(jTextFieldKey);
		focusOrder.add(jTextFieldField);
		focusOrder.add(jTextFieldName);
		focusOrder.add(jCBObjectType);
		focusOrder.add(jComboBoxType);
		focusOrder.add(jTextFieldPrefix);
		focusOrder.add(jTextFieldSuffix);
		focusOrder.add(jButtonAssignFormat);
		focusOrder.add(jButtonAssignFormula);
		focusOrder.add(jTextFieldValue);
		focusOrder.add( jButtonOK);
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
