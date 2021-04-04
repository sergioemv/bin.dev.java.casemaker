package bi.view.errorviews;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JFormattedTextField.AbstractFormatter;

import model.BusinessRules;
import model.CMError;
import model.Structure;
import model.TestCase;

import org.apache.log4j.Logger;

import bi.controller.ErrorManager;
import bi.view.lang.CMMessages;
import bi.view.utils.CMBaseJComboBox;
import bi.view.utils.CMBaseJDialog;
import bi.view.utils.CMBaseJFormattedTextField;
import bi.view.utils.CMJEditorPaneFocusChangeable;
import bi.view.utils.CMShuttle;

/**
 ****************************************************************************** Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
public class CMErrorDialogView extends CMBaseJDialog {
    /** Creates new form JDialog */
    public CMErrorDialogView(Structure p_Structure) {
        super();
        m_ErrorManager = ErrorManager.INSTANCE;
        m_SelectedStructure = p_Structure;

        initGUI();
        this.addKeyListenertoAll(this,this.getDefaultKeyListener());
    }

    /** This method is called from within the constructor to initialize the form. */
    private void initGUI() {
        jEditorPane1.setText("");
        jEditorPane1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        this.setTitle(CMMessages.getString("LABEL_ERROR")); //$NON-NLS-1$
        addWindowListener(
            new java.awt.event.WindowAdapter() {
                public void windowClosing(java.awt.event.WindowEvent evt) {
                    closeDialog(evt);
                }
            });
        pack();
        setBounds(new java.awt.Rectangle(0, 0, 620, 550));
        setResizable(false);
        getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);
        getContentPane().add(jPanel1, java.awt.BorderLayout.SOUTH);
        jButtonOK.setText(CMMessages.getString("BUTTON_OK")); //$NON-NLS-1$
		jButtonOK.setSize(new java.awt.Dimension(50,27));

        jPanel1.add(jButtonOK);
        jPanel1.add(jButtonCancel);
        jButtonCancel.setText(CMMessages.getString("BUTTON_CANCEL")); //$NON-NLS-1$
        jButtonCancel.setSize(new java.awt.Dimension(50,27));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(),
        CMMessages.getString("LABEL_ERROR"), javax.swing.border.TitledBorder.LEADING, javax.swing.border.TitledBorder.TOP, new java.awt.Font("SansSerif", 0, 11), //$NON-NLS-1$
        new java.awt.Color(60, 60, 60)));
        jPanel2.setLayout(new java.awt.GridLayout(3, 1));
        jPanel2.add(jPanel9);
        jPanel2.add(jPanel4);
        jPanel2.add(jPanelShuttle);
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(),
        CMMessages.getString("LABEL_DESCRIPTION"), javax.swing.border.TitledBorder.LEADING, javax.swing.border.TitledBorder.TOP, new java.awt.Font("SansSerif", 0, 11), //$NON-NLS-1$
        new java.awt.Color(60, 60, 60)));
        jPanel4.setLayout(new java.awt.BorderLayout());
        jPanel4.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jButtonOK.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){jButtonOKActionPerformed(e);}});
        jButtonCancel.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){jButtonCancelActionPerformed(e);}});
        jScrollPane1.getViewport().add(jEditorPane1);
        jPanel9.setBorder(null);
        jPanel9.setLayout(new java.awt.GridBagLayout());
//HCanedo_30112004_Begin
        jPanel9.add(jTextFieldClosingDate, new java.awt.GridBagConstraints(3, 4, 1, 1, 0.0, 0.0, java.awt.GridBagConstraints.WEST, java.awt.GridBagConstraints.NONE, new java.awt.Insets(0, 0, 10, 0), 0, 0));
        jPanel9.add(jLabelClosingDate, 	   new java.awt.GridBagConstraints(2, 4, 1, 1, 0.0, 0.0, java.awt.GridBagConstraints.EAST, java.awt.GridBagConstraints.NONE, new java.awt.Insets(0, 30, 10, 5), 0, 0));
        jPanel9.add(jTextFieldClosedBy,	   new java.awt.GridBagConstraints(1, 4, 1, 1, 0.0, 0.0, java.awt.GridBagConstraints.WEST, java.awt.GridBagConstraints.NONE, new java.awt.Insets(0, 0, 10, 0), 0, 0));
        jPanel9.add(jLabelClosedBy, 	   new java.awt.GridBagConstraints(0, 4, 1, 1, 0.0, 0.0, java.awt.GridBagConstraints.EAST, java.awt.GridBagConstraints.NONE,new java.awt.Insets(0, 0, 10, 5), 0, 0));

		jPanel9.add(jTextFieldAssignDate,  new java.awt.GridBagConstraints(3, 3, 1, 1, 0.0, 0.0, java.awt.GridBagConstraints.WEST, java.awt.GridBagConstraints.NONE, new java.awt.Insets(0, 0, 10, 0), 0, 0));
        jPanel9.add(jLabelAssignDate, 	   new java.awt.GridBagConstraints(2, 3, 1, 1, 0.0, 0.0, java.awt.GridBagConstraints.EAST, java.awt.GridBagConstraints.NONE, new java.awt.Insets(0, 30, 10, 5), 0, 0));
        jPanel9.add(jTextFieldAssignTo,	   new java.awt.GridBagConstraints(1, 3, 1, 1, 0.0, 0.0, java.awt.GridBagConstraints.WEST, java.awt.GridBagConstraints.NONE, new java.awt.Insets(0, 0, 10, 0), 0, 0));
        jPanel9.add(jLabelAssignTo, 	   new java.awt.GridBagConstraints(0, 3, 1, 1, 0.0, 0.0, java.awt.GridBagConstraints.EAST, java.awt.GridBagConstraints.NONE,new java.awt.Insets(0, 0, 10, 5), 0, 0));
//HCanedo_30112004_Begin
        jPanel9.add(jTextFieldIssueDate,   new java.awt.GridBagConstraints(3, 2, 1, 1, 0.0, 0.0, java.awt.GridBagConstraints.WEST, java.awt.GridBagConstraints.NONE, new java.awt.Insets(0, 0, 10, 0), 0, 0));
        jPanel9.add(jLabelIssueDate, 	   new java.awt.GridBagConstraints(2, 2, 1, 1, 0.0, 0.0, java.awt.GridBagConstraints.EAST, java.awt.GridBagConstraints.NONE, new java.awt.Insets(0, 30, 10, 5), 0, 0));
	    jPanel9.add(jTextFieldIssuedBy,    new java.awt.GridBagConstraints(1, 2, 1, 1, 0.0, 0.0, java.awt.GridBagConstraints.WEST, java.awt.GridBagConstraints.NONE, new java.awt.Insets(0, 0, 10, 0), 0, 0));
        jPanel9.add(jLabelIssuedBy, 	   new java.awt.GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, java.awt.GridBagConstraints.EAST, java.awt.GridBagConstraints.NONE, new java.awt.Insets(0, 0, 10, 5), 0, 0));
        jPanel9.add(jComboBoxPriority,	   new java.awt.GridBagConstraints(5, 1, 1, 1, 0.0, 0.0, java.awt.GridBagConstraints.WEST, java.awt.GridBagConstraints.NONE, new java.awt.Insets(0, 0, 10, 0), 0, 0));
        jPanel9.add(jLabelPriority, 	   new java.awt.GridBagConstraints(4, 1, 1, 1, 0.0, 0.0, java.awt.GridBagConstraints.EAST, java.awt.GridBagConstraints.NONE, new java.awt.Insets(0, 30, 10, 5), 0, 0));
        jPanel9.add(jComboBoxErrorClass,   new java.awt.GridBagConstraints(3, 1, 1, 1, 0.0, 0.0, java.awt.GridBagConstraints.WEST, java.awt.GridBagConstraints.NONE, new java.awt.Insets(0, 0, 10, 0), 0, 0));
        jPanel9.add(jLabelErrorClass,	   new java.awt.GridBagConstraints(2, 1, 1, 1, 0.0, 0.0, java.awt.GridBagConstraints.EAST, java.awt.GridBagConstraints.NONE, new java.awt.Insets(0, 30, 10, 5), 0, 0));
        jPanel9.add(jComboBoxState,		   new java.awt.GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, java.awt.GridBagConstraints.WEST, java.awt.GridBagConstraints.NONE, new java.awt.Insets(0, 0, 10, 0), 0, 0));
        jPanel9.add(jLabelState, 		   new java.awt.GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, java.awt.GridBagConstraints.EAST, java.awt.GridBagConstraints.NONE, new java.awt.Insets(0, 0, 10, 5), 0, 0));
        jPanel9.add(jTextFieldName,		   new java.awt.GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, java.awt.GridBagConstraints.WEST, java.awt.GridBagConstraints.NONE,new java.awt.Insets(0, 0, 10, 0), 0, 0));
        jPanel9.add(jLabelName, 		   new java.awt.GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, java.awt.GridBagConstraints.EAST, java.awt.GridBagConstraints.NONE,new java.awt.Insets(0, 0, 10, 5), 0, 0));


        jLabelName.setText(CMMessages.getString("LABEL_NAME")); //$NON-NLS-1$
        jLabelState.setText(CMMessages.getString("LABEL_STATE")); //$NON-NLS-1$
        jLabelErrorClass.setText(CMMessages.getString("LABEL_ERROR_CLASS")); //$NON-NLS-1$
        jLabelPriority.setText(CMMessages.getString("LABEL_PRIORITY")); //$NON-NLS-1$
        jLabelIssueDate.setText(CMMessages.getString("LABEL_ISSUE_DATE")); //$NON-NLS-1$
        jLabelIssuedBy.setText(CMMessages.getString("LABEL_ISSUED_BY")); //$NON-NLS-1$
        jLabelClosingDate.setText(CMMessages.getString("LABEL_CLOSING_DATE")); //$NON-NLS-1$
        jLabelClosedBy.setText(CMMessages.getString("LABEL_CLOSED_BY")); //$NON-NLS-1$
//HCanedo_30112004_Begin
		jLabelAssignDate.setText(CMMessages.getString("LABEL_ASSIGN_DATE_COLUMN")); //$NON-NLS-1$
        jLabelAssignTo.setText(CMMessages.getString("LABEL_ASSIGN_TO")); //$NON-NLS-1$
        jTextFieldAssignTo.setPreferredSize(new java.awt.Dimension(100,20));
        jTextFieldAssignTo.setSize(new java.awt.Dimension(100,20));

        jTextFieldAssignDate.setValue(new Date());
        jTextFieldAssignDate.setPreferredSize(new java.awt.Dimension(75,20));
        jTextFieldAssignDate.setSize(new java.awt.Dimension(75,20));
//HCanedo_30112004_End
        jTextFieldName.setText("    ");
        jTextFieldName.setPreferredSize(new java.awt.Dimension(70,20));
        jTextFieldName.setSize(new java.awt.Dimension(70,20));
        jTextFieldName.setEditable(false);
        //jTextFieldIssuedBy.setText("");
        jTextFieldIssuedBy.setPreferredSize(new java.awt.Dimension(100,20));
        jTextFieldIssuedBy.setSize(new java.awt.Dimension(100,20));
        jTextFieldIssueDate.setValue(new Date());

        jTextFieldIssueDate.setPreferredSize(new java.awt.Dimension(75,20));
        jTextFieldIssueDate.setSize(new java.awt.Dimension(75,20));
        //jTextFieldClosedBy.setText("");
        jTextFieldClosedBy.setPreferredSize(new java.awt.Dimension(100,20));
        jTextFieldClosedBy.setSize(new java.awt.Dimension(100,20));
        jTextFieldClosingDate.setValue(new Date());
        jTextFieldClosingDate.setPreferredSize(new java.awt.Dimension(75,20));
        jTextFieldClosingDate.setSize(new java.awt.Dimension(75,20));
        jComboBoxState.setEditable(false);
        jComboBoxState.setPreferredSize(new java.awt.Dimension(70, 20));
        jComboBoxState.setSize(new java.awt.Dimension(70, 20));
        jComboBoxErrorClass.setEditable(false);
        jComboBoxErrorClass.setPreferredSize(new java.awt.Dimension(35, 20));
        jComboBoxErrorClass.setSize(new java.awt.Dimension(35, 20));
        jComboBoxPriority.setEditable(false);
        jComboBoxPriority.setPreferredSize(new java.awt.Dimension(70, 20));
        jComboBoxPriority.setSize(new java.awt.Dimension(70, 20));

        jComboBoxState.addItem(BusinessRules.ERROR_STATE_OPEN);
        jComboBoxState.addItem(BusinessRules.ERROR_STATE_IN_WORK);
        jComboBoxState.addItem(BusinessRules.ERROR_STATE_CLOSED);

        jComboBoxErrorClass.addItem(BusinessRules.ERROR_CLASS_A);
        jComboBoxErrorClass.addItem(BusinessRules.ERROR_CLASS_B);
        jComboBoxErrorClass.addItem(BusinessRules.ERROR_CLASS_C);
        jComboBoxErrorClass.addItem(BusinessRules.ERROR_CLASS_D);

        jComboBoxPriority.addItem(BusinessRules.ERROR_PRIORITY_HIGH);
        jComboBoxPriority.addItem(BusinessRules.ERROR_PRIORITY_MIDDLE);
        jComboBoxPriority.addItem(BusinessRules.ERROR_PRIORITY_LOW);
        jComboBoxState.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){jComboBoxStateActionPerformed(e);}});
    }

    /** Closes the dialog */
    private void closeDialog(WindowEvent evt) {
        this.m_CancelButtonClicked = true;
        this.m_OKButtonClicked = false;
    	setVisible(false);
        dispose();
    }

    public boolean jButtonOKClicked(){
      return this.m_OKButtonClicked;
    }

    public boolean jButtonCancelClicked(){
      return this.m_CancelButtonClicked;
    }

    public CMError getM_CMError(){
      return m_CMError;
    }

    public void setM_CMError(CMError p_CMError) {
      if(p_CMError != null) {
		  m_CMError = p_CMError;
	
		  jTextFieldName.setText(m_CMError.getM_Name());
		  jComboBoxState.setSelectedItem(m_CMError.getM_State());
		  jComboBoxErrorClass.setSelectedItem(m_CMError.getM_ErrorClass());//fcastro_20082004
		  jComboBoxPriority.setSelectedItem(m_CMError.getM_Priority());
		  jTextFieldClosedBy.setText(m_CMError.getM_ClosedBy());
		  jTextFieldClosingDate.setValue(m_CMError.getM_ClosingDate());
		  jTextFieldIssueDate.setValue(m_CMError.getM_IssueDate());
		  jTextFieldIssuedBy.setText(m_CMError.getM_IssuedBy());
		  jEditorPane1.setText( m_CMError.getM_Description() );
//HCanedo_30112004_Begin
		 jTextFieldAssignDate.setValue(m_CMError.getM_AssignDate());
         jTextFieldAssignTo.setText(m_CMError.getM_AssignTo());
//HCanedo_30112004_End
      }
//HCanedo_30112004_Begin
	validFieldInSpecificSateError();
//HCanedo_30112004_End
      setLeftListView();
      setRightListView();
    }

//HCanedo_30112004_Begin
	public void jComboBoxStateActionPerformed(ActionEvent e)
    {
        validFieldInSpecificSateError();
    }
	public void validFieldInSpecificSateError()
    {
        String stateSelected=jComboBoxState.getSelectedItem().toString();
        if(stateSelected.equals(BusinessRules.ERROR_STATE_OPEN))
        {
            jTextFieldAssignDate.setEditable(true);
            jTextFieldAssignTo.setEditable(true);
            jTextFieldClosedBy.setText("");
            jTextFieldClosingDate.setValue(null);
            jTextFieldClosedBy.setEditable(false);
            jTextFieldClosingDate.setEditable(false);
            jTextFieldIssueDate.setEditable(true);
            jTextFieldIssuedBy.setEditable(true);
        }
        else
        {
            if(stateSelected.equals(BusinessRules.ERROR_STATE_IN_WORK))
            {
                    jTextFieldAssignDate.setEditable(false);
            		jTextFieldAssignTo.setEditable(false);
            		jTextFieldClosedBy.setText("");
            		jTextFieldClosingDate.setValue(null);
                    jTextFieldClosedBy.setEditable(false);
            		jTextFieldClosingDate.setEditable(false);
            		jTextFieldIssueDate.setEditable(false);
            		jTextFieldIssuedBy.setEditable(false);
            }
            else
            {
                if(stateSelected.equals(BusinessRules.ERROR_STATE_CLOSED))
                {
                    jTextFieldAssignDate.setEditable(false);
            		jTextFieldAssignTo.setEditable(false);
                    jTextFieldClosedBy.setEditable(true);
            		jTextFieldClosingDate.setEditable(true);
                    jTextFieldClosingDate.setValue(new Date(System.currentTimeMillis()));
            		jTextFieldIssueDate.setEditable(false);
            		jTextFieldIssuedBy.setEditable(false);
                }
            }
        }

    }
//HCanedo_30112004_End
//hmendez_09112005_begin
	private Calendar stringToDate(String p_Date) {
        StringBuffer buffer = new StringBuffer();
        buffer = new StringBuffer();
        int l_index = 0;
        //year
        while ((l_index<p_Date.length())&&(!(""+p_Date.charAt(l_index)).equalsIgnoreCase("-")))
        {
          buffer.append(p_Date.charAt(l_index));
          l_index++;
        }
        Integer temp = new Integer(buffer.toString());
        int yearOfDate = temp.intValue();
        //month
        buffer = new StringBuffer();
        l_index = p_Date.indexOf("-",3);   
        l_index++;
        while ((l_index<p_Date.length())&&(!(""+p_Date.charAt(l_index)).equalsIgnoreCase("-")))
        {
          buffer.append(p_Date.charAt(l_index));
          l_index++;
        }
        temp = new Integer(buffer.toString());
        int monthOfDate = temp.intValue();
        //day
        buffer = new StringBuffer();
        l_index = p_Date.indexOf("-",l_index);  
        l_index++;
        while (l_index<p_Date.length())
        {
          buffer.append(p_Date.charAt(l_index));
          l_index++;
        }
        temp = new Integer(buffer.toString());
        int dayOfDate = temp.intValue();

        Calendar calendarDate = Calendar.getInstance();
        calendarDate.set(Calendar.YEAR, yearOfDate);
        calendarDate.set(Calendar.MONTH, monthOfDate - 1);
        calendarDate.set(Calendar.DAY_OF_MONTH, dayOfDate);
        return calendarDate;
      }
	   private boolean isFirstOlderThanSecond(Calendar p_First, Calendar p_Second) {
	        int firstYear = p_First.get(Calendar.YEAR);
	        int firstMonth = p_First.get(Calendar.MONTH);
	        int firstDay = p_First.get(Calendar.DAY_OF_MONTH);
	        int secondYear = p_Second.get(Calendar.YEAR);
	        int secondMonth = p_Second.get(Calendar.MONTH);
	        int secondDay = p_Second.get(Calendar.DAY_OF_MONTH);
	        if (firstYear > secondYear) {
	            return true;
	        }
	        else if (firstYear == secondYear) {
	            if (firstMonth > secondMonth) {
	                return true;
	            }
	            else if (firstMonth == secondMonth) {
	                if (firstDay > secondDay) {
	                    return true;
	                }
	            }
	        }
	        return false;
	    }	
	   private boolean isFirstOlderOrEqualThanSecond(Calendar p_First, Calendar p_Second) {
	        int firstYear = p_First.get(Calendar.YEAR);
	        int firstMonth = p_First.get(Calendar.MONTH);
	        int firstDay = p_First.get(Calendar.DAY_OF_MONTH);
	        int secondYear = p_Second.get(Calendar.YEAR);
	        int secondMonth = p_Second.get(Calendar.MONTH);
	        int secondDay = p_Second.get(Calendar.DAY_OF_MONTH);
	        if (firstYear > secondYear) {
	            return true;
	        }
	        else if (firstYear == secondYear) {
	            if (firstMonth > secondMonth) {
	                return true;
	            }
	            else if (firstMonth == secondMonth) {
	                if (firstDay >= secondDay) {
	                    return true;
	                }
	            }
	        }
	        return false;
	    }		   
  private boolean validateDates()
  {
	  boolean l_AssignOlderThanIssue = true;
	  boolean l_ClosingOlderThanAsign = true;
	  boolean l_ClosingOlderThanIssue = true;
		if (jTextFieldAssignDate.getText().length()>0 && jTextFieldIssueDate.getText().length()>0)
		{
		  if (isFirstOlderOrEqualThanSecond(stringToDate(jTextFieldAssignDate.getText()),stringToDate(jTextFieldIssueDate.getText())))
			  l_AssignOlderThanIssue = true;  
		  else
		  {
        	  JOptionPane.showMessageDialog((java.awt.Component)
    		            null, CMMessages.getString("INFO_ERROR_DIALOG_ASSIGN_DATE_INVALID"), 
    		          CMMessages.getString("TITLE_MESSAGE_ERROR_NAME"), JOptionPane.ERROR_MESSAGE);			  
        	  l_AssignOlderThanIssue = false;
		  }
		}
		if (jTextFieldClosingDate.getText().length()>0 && jTextFieldAssignDate.getText().length()>0)
		{
			  if (isFirstOlderThanSecond(stringToDate(jTextFieldClosingDate.getText()),stringToDate(jTextFieldAssignDate.getText())))
				  l_ClosingOlderThanAsign = true;
			  else
			  {
		       	JOptionPane.showMessageDialog((java.awt.Component)
		    		          null, CMMessages.getString("INFO_ERROR_DIALOG_CLOSING_DATE_INVALID_1"), 
		    		        CMMessages.getString("TITLE_MESSAGE_ERROR_NAME"), JOptionPane.ERROR_MESSAGE);					  
		       	l_ClosingOlderThanAsign = false;
			  }
		}
  		if (jTextFieldClosingDate.getText().length()>0 && jTextFieldIssueDate.getText().length()>0)
  		{
		  if (isFirstOlderThanSecond(stringToDate(jTextFieldClosingDate.getText()),stringToDate(jTextFieldIssueDate.getText())))

			  l_ClosingOlderThanIssue = true;
		  else
		  {
	       	  JOptionPane.showMessageDialog((java.awt.Component)
	    	            null, CMMessages.getString("INFO_ERROR_DIALOG_CLOSING_DATE_INVALID_2"), 
	    	          CMMessages.getString("TITLE_MESSAGE_ERROR_NAME"), JOptionPane.ERROR_MESSAGE);				  
	       	l_ClosingOlderThanIssue = false;
		  }
  		}
  		
	  return (l_AssignOlderThanIssue && l_ClosingOlderThanAsign && l_ClosingOlderThanIssue);
  }
  private boolean parseDate(JFormattedTextField p_JFormattedTextField)
  {
	 if (p_JFormattedTextField.getText().length()>0)
	 {
	   AbstractFormatter l_AbstractFormatter = p_JFormattedTextField.getFormatter();
	   try 
	   {
	     //Manual Parsing of the text in the JFormattedTextField
	     l_AbstractFormatter.stringToValue(p_JFormattedTextField.getText());
 		 return true;
	   }
	   catch (ParseException pe)
	   {
		   Logger.getLogger(this.getClass()).error("Invalid Field "+pe);
  	      JOptionPane.showMessageDialog((java.awt.Component)
	            null, CMMessages.getString("INFO_ERROR_DIALOG_INVALID_DATE"), 
	          CMMessages.getString("TITLE_MESSAGE_ERROR_NAME"), JOptionPane.ERROR_MESSAGE);				  
		  return false;
	   }
	 }
	 else
		 return true;
  }
//hmendez_09112005_end	
//integration_fcastro_17082004_begin
   public void jButtonOKActionPerformed(ActionEvent e) {
/*   	  try
	  {
        Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler http://www.rgagnon.com/howto.ht%6D");
	  }
      catch
      (Exception se){System.out.println("Exception->"+se);}	  */
	  m_OKButtonClicked = true;
      m_CancelButtonClicked = false;
//hmendez_10112005_begin
      if ((!parseDate(jTextFieldIssueDate))||(!parseDate(jTextFieldAssignDate))||(!parseDate(jTextFieldClosingDate)))
    	return;
      else
      if (!this.validateDates())
    	return;
      else
      {
//hmendez_10112005_end    	  
	    if(m_CMError == null) {
			m_CMError = m_ErrorManager.createCMError(m_SelectedStructure);

            m_CMError.setM_State((String)jComboBoxState.getSelectedItem());
            m_CMError.setM_ErrorClass( (String) jComboBoxErrorClass.getSelectedItem());
            m_CMError.setM_Priority((String)jComboBoxPriority.getSelectedItem());
            m_CMError.setM_ClosedBy(jTextFieldClosedBy.getText());
            m_CMError.setM_ClosingDate((Date) jTextFieldClosingDate.getValue());
            m_CMError.setM_IssueDate((Date) jTextFieldIssueDate.getValue());
            m_CMError.setM_IssuedBy(jTextFieldIssuedBy.getText());
            m_CMError.setM_Description(this.jEditorPane1.getText());
//HCanedo_30112004_Begin
			m_CMError.setM_AssignDate((Date)jTextFieldAssignDate.getValue());
            m_CMError.setM_AssignTo(jTextFieldAssignTo.getText());            
//HCanedo_30112004_End
            //Ccastedo begins 15-02-06
            processTestCaseShuttleWhenOKActionPerformed();
            //Ccastedo ends 15-02-06
			 ;
	  }/*
	  else {
            m_CMError.setM_State((String)jComboBoxState.getSelectedItem());
            m_CMError.setM_ErrorClass( (String) jComboBoxErrorClass.getSelectedItem());
            m_CMError.setM_Priority((String)jComboBoxPriority.getSelectedItem());
            m_CMError.setM_ClosedBy(jTextFieldClosedBy.getText());
            m_CMError.setM_ClosingDate((Date) jTextFieldClosingDate.getValue());
            m_CMError.setM_IssueDate((Date) jTextFieldIssueDate.getValue());
            m_CMError.setM_IssuedBy(jTextFieldIssuedBy.getText());
            m_CMError.setM_Description(this.jEditorPane1.getText());

			processTestCaseShuttleWhenOKActionPerformed();
	  }*///fcastro_01072004
	    dispose();
    //hmendez_10112005_begin	    
      }
    //hmendez_10112005_end
    }

   ////////new functions/////
    public String getState(){
        return (String)jComboBoxState.getSelectedItem();
    }
    public String getErrorClass(){
        return (String) jComboBoxErrorClass.getSelectedItem();
    }
    public String getPriority(){
        return (String) jComboBoxPriority.getSelectedItem();
    }
	public String getClosedBy(){
        return  jTextFieldClosedBy.getText();
    }
    public Date getClosingDate(){
        return (Date)jTextFieldClosingDate.getValue();
    }
    public Date getIssueDate(){
        return (Date)jTextFieldIssueDate.getValue();
    }
    public String getIssuedBy(){
        return jTextFieldIssuedBy.getText();
    }
    public String getDescription(){
        return jEditorPane1.getText();
    }
    public Vector getRightList(){
        return jPanelShuttle.getRightList();
    }
    public Vector getLeftList(){
        return (Vector) jPanelShuttle.getLeftList();
    }
//HCanedo_30112004_Begin
	public String getAssigTo(){
        return jTextFieldAssignTo.getText();
    }
    public Date getAsssignDate(){
		return (Date)jTextFieldAssignDate.getValue();
    }
//HCanedo_30112004_End
   //integration_fcastro_17082004_end

    public void jButtonCancelActionPerformed(ActionEvent e) {
      m_CancelButtonClicked = true;
      m_OKButtonClicked = false;
      closeDialog(null);
    }

    private void processTestCaseShuttleWhenOKActionPerformed() {
    Vector testCasesRightList = jPanelShuttle.getRightList();
    if(testCasesRightList != null) {
      int numOfTestCases = testCasesRightList.size();
      for( int i = 0; i < numOfTestCases; i++) {
	    TestCase testCase = (TestCase) testCasesRightList.elementAt(i);
		if( !testCase.getM_CMErrors().contains(m_CMError)) {
          int index = m_SelectedStructure.getLnkTestCases().indexOf(testCase);
          TestCase realTestCase = (TestCase) m_SelectedStructure.getLnkTestCases().elementAt(index);
		  realTestCase.getM_CMErrors().addElement(m_CMError);
          //NEW 13.04.2004 by Gary.
          m_CMError.getM_TestCases().addElement(realTestCase);
		}
      }
    }

    Vector testCasesLeftList = (Vector) jPanelShuttle.getLeftList();
    if( testCasesLeftList != null) {
      int numOfTestCases = testCasesLeftList.size();
      for( int i = 0; i < numOfTestCases; i++) {
        TestCase testCase = (TestCase) testCasesLeftList.elementAt(i);
        int index = m_SelectedStructure.getLnkTestCases().indexOf(testCase);
        TestCase realTestCase = (TestCase) m_SelectedStructure.getLnkTestCases().elementAt(index);
        if( realTestCase.getM_CMErrors().contains(m_CMError)) {
	      realTestCase.getM_CMErrors().removeElement(m_CMError);
          //New 13.04.2004 by Gary.
          m_CMError.getM_TestCases().removeElement(realTestCase);
        }
      }
		}
    }

  private Vector createViews(Vector cloneElements) {
    Vector views = new Vector(0);
    StringBuffer sBuffer = null;
    int numElements = cloneElements.size();
    TestCase testCase = null;
    for( int i = 0; i < numElements; i++) {
      testCase = (TestCase) cloneElements.elementAt(i);
      sBuffer = new StringBuffer();
      sBuffer.append(testCase.getName());
      sBuffer.append(BusinessRules.SPACE);
      sBuffer.append(BusinessRules.SPACE);
      sBuffer.append(testCase.getDescriptionWithOutCheck());
      views.addElement(sBuffer.toString());
    }
    return views;
  }

  private void setLeftListView() {
    Vector allowedTestCases = new Vector(0);
    int numOfTestCases = m_SelectedStructure.getLnkTestCases().size();
    for( int i = 0; i < numOfTestCases; i ++) {
      TestCase testCase = (TestCase) m_SelectedStructure.getLnkTestCases().elementAt(i);
      if( testCase.getM_CMErrors() == null) {
        testCase.setM_CMErrors(new Vector(0));
      }
      if( !testCase.getM_CMErrors().contains(m_CMError) ) {
        allowedTestCases.add(testCase);
	  }
    }
    Vector clones = (Vector) allowedTestCases.clone();
    Vector views = createViews(clones);
    jPanelShuttle.setLeftList(clones, views);
  }

  private void setRightListView() {
    Vector allowedTestCases = new Vector(0);
    int numOfTestCases = m_SelectedStructure.getLnkTestCases().size();
    for( int i = 0; i < numOfTestCases; i ++) {
      TestCase testCase = (TestCase) m_SelectedStructure.getLnkTestCases().elementAt(i);
      if( testCase.getM_CMErrors() == null) {
        testCase.setM_CMErrors(new Vector(0));
      }
	  if( testCase.getM_CMErrors().contains(m_CMError) ) {
	    allowedTestCases.add(testCase);
	  }
    }
    Vector clones = (Vector) allowedTestCases.clone();
    Vector views = createViews(clones);
    jPanelShuttle.setRightList(clones, views);
  }

    private CMError m_CMError = null;
    private boolean m_OKButtonClicked = false;
    private boolean m_CancelButtonClicked = false;
    private ErrorManager m_ErrorManager;
    private Structure m_SelectedStructure;

    private JPanel jPanel1 = new JPanel();
    private JButton jButtonOK = new JButton();
    private JButton jButtonCancel = new JButton();
    private JPanel jPanel2 = new JPanel();
    private JPanel jPanel4 = new JPanel();
    private CMJEditorPaneFocusChangeable jEditorPane1 = new CMJEditorPaneFocusChangeable();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private CMBaseJFormattedTextField jTextFieldIssueDate = new CMBaseJFormattedTextField(new SimpleDateFormat("yyyy-M-d"),this);
    private CMBaseJFormattedTextField jTextFieldClosingDate = new CMBaseJFormattedTextField(new SimpleDateFormat("yyyy-M-d"),this);
//HCanedo_30112004_Begin
	private CMBaseJFormattedTextField jTextFieldAssignDate = new CMBaseJFormattedTextField(new SimpleDateFormat("yyyy-M-d"),this);
    private JLabel jLabelAssignTo = new JLabel();
    private JLabel jLabelAssignDate= new JLabel();
    private JTextField jTextFieldAssignTo = new JTextField();
//HCanedo_30112004_End
    private JPanel jPanel9 = new JPanel();
    CMShuttle jPanelShuttle = new CMShuttle(CMMessages.getString("LABEL_AVAILABLE_TEST_CASES"),CMMessages.getString("LABEL_CURRENT_ERROR_ASSIGNED_IN")); //$NON-NLS-1$ //$NON-NLS-2$
    
    private JLabel jLabelName = new JLabel();
    private JTextField jTextFieldName = new JTextField();
    private JLabel jLabelState = new JLabel();
    private CMBaseJComboBox jComboBoxState = new CMBaseJComboBox(this);
    private JLabel jLabelErrorClass = new JLabel();
    private CMBaseJComboBox jComboBoxErrorClass = new CMBaseJComboBox(this);
    private JLabel jLabelPriority = new JLabel();
    private CMBaseJComboBox jComboBoxPriority = new CMBaseJComboBox(this);
    private JLabel jLabelIssuedBy = new JLabel();
    private JTextField jTextFieldIssuedBy = new JTextField();
    private JLabel jLabelIssueDate = new JLabel();
    private JLabel jLabelClosedBy = new JLabel();
    private JTextField jTextFieldClosedBy = new JTextField();
    private JLabel jLabelClosingDate = new JLabel();
	protected List getOrder() {
		Vector componentList = new Vector(0); 
	    componentList.add(jComboBoxState);
		componentList.add(jComboBoxErrorClass);
	    componentList.add(jComboBoxPriority);
        if (jTextFieldIssuedBy.isEditable())
		  componentList.add(jTextFieldIssuedBy);
        if (jTextFieldIssueDate.isEditable())
		  componentList.add(jTextFieldIssueDate);
        if (jTextFieldAssignTo.isEditable())
		  componentList.add(jTextFieldAssignTo);
        if (jTextFieldAssignDate.isEditable())
		  componentList.add(jTextFieldAssignDate);
        if (this.jTextFieldClosedBy.isEditable())
          componentList.add(this.jTextFieldClosedBy);
        if (this.jTextFieldClosingDate.isEditable())
          componentList.add(this.jTextFieldClosingDate);
        if (jEditorPane1.isEditable())
		  componentList.add(jEditorPane1);
        if (jPanelShuttle.getLeftJList().getModel().getSize()>0)
		  componentList.add(jPanelShuttle.getLeftJList());
		componentList.add(jPanelShuttle.getAddButton());
        if (jPanelShuttle.getRightJList().getModel().getSize()>0)
		  componentList.add(jPanelShuttle.getRightJList());
		componentList.add(jPanelShuttle.getDeleteButton());
		componentList.add(jButtonOK);
		componentList.add(jButtonCancel);
		return componentList;
	}

	protected void fireButtonOk() {
			
		this.jButtonOKActionPerformed(null);
	}

	protected void fireButtonCancel() {
		this.jButtonCancelActionPerformed(null);
		
	}

	public JButton getDefaultButton() {
		return this.jButtonOK;
	}
}
