/*******************************************************************************
Developed by BUSINESS SOFTWARE INNOVATIONS. .
Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
*******************************************************************************/

package  bi.view.utils;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import model.BusinessRules;
import model.Type;
import model.util.CMTypeBean;

/** 
 * author ccastedo
*/

@SuppressWarnings("serial")
public class CMTypeView extends JPanel {
  private CMBaseJComboBox jComboBoxType;
 

  public CMTypeView() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  public CMTypeView(CMBaseJComboBox box) {
	  try {
		  jComboBoxType = box;
	      jbInit();
	    }
	    catch(Exception e) {
	      e.printStackTrace();
	    }
}

private void jbInit() throws Exception {
   
    getJComboBoxType().addItem(BusinessRules.TESTDATA_STATE_BINARY);
    getJComboBoxType().addItem(BusinessRules.TESTDATA_STATE_BIT);
    getJComboBoxType().addItem(BusinessRules.TESTDATA_STATE_BOOLEAN);
    getJComboBoxType().addItem(BusinessRules.TESTDATA_STATE_CHAR);
    getJComboBoxType().addItem(BusinessRules.TESTDATA_STATE_DATETIME);
    getJComboBoxType().addItem(BusinessRules.TESTDATA_STATE_DECIMAL);
    getJComboBoxType().addItem(BusinessRules.TESTDATA_STATE_FLOAT);
    getJComboBoxType().addItem(BusinessRules.TESTDATA_STATE_INT);
    getJComboBoxType().addItem(BusinessRules.TESTDATA_STATE_MONEY);
    getJComboBoxType().addItem(BusinessRules.TESTDATA_STATE_NCHAR);
    getJComboBoxType().addItem(BusinessRules.TESTDATA_STATE_NTEXT);
    getJComboBoxType().addItem(BusinessRules.TESTDATA_STATE_NUMERIC);
    getJComboBoxType().addItem(BusinessRules.TESTDATA_STATE_NVARCHAR);
    getJComboBoxType().addItem(BusinessRules.TESTDATA_STATE_REAL);
    getJComboBoxType().addItem(BusinessRules.TESTDATA_STATE_SMALLDATETIME);
    getJComboBoxType().addItem(BusinessRules.TESTDATA_STATE_SMALLINT);
    getJComboBoxType().addItem(BusinessRules.TESTDATA_STATE_SMALLMONEY);
    getJComboBoxType().addItem(BusinessRules.TESTDATA_STATE_TEXT);
    getJComboBoxType().addItem(BusinessRules.TESTDATA_STATE_TIMESTAMP);
    getJComboBoxType().addItem(BusinessRules.TESTDATA_STATE_TINYINT);
    getJComboBoxType().addItem(BusinessRules.TESTDATA_STATE_VARBINARY);
    getJComboBoxType().addItem(BusinessRules.TESTDATA_STATE_VARCHAR);
    
    getJComboBoxType().setSelectedIndex(0);
    getJComboBoxType().setPreferredSize(new java.awt.Dimension(40,20));
    setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEADING));
    setBounds(new java.awt.Rectangle(0, 0, 78, 26));
    
    add(getJComboBoxType());
  }


  public Type getSelectedTypeModel(){
	  
	  Object selectedItem = (String) getJComboBoxType().getSelectedItem();
	  if( selectedItem.equals(BusinessRules.TESTDATA_STATE_BINARY))    {  return Type.binary; }
	  else if( selectedItem.equals(BusinessRules.TESTDATA_STATE_BIT)) { return Type.bit; }
	  else if( selectedItem.equals(BusinessRules.TESTDATA_STATE_BOOLEAN)) { return Type.bolean; }
	  else if( selectedItem.equals(BusinessRules.TESTDATA_STATE_CHAR)) { return Type.character; }
	  else if( selectedItem.equals(BusinessRules.TESTDATA_STATE_DATETIME)) { return Type.dateTime; }
	  else if( selectedItem.equals(BusinessRules.TESTDATA_STATE_DECIMAL)) { return Type.decimal; }
	  else if( selectedItem.equals(BusinessRules.TESTDATA_STATE_FLOAT)) { return Type.floatPoint; }
	  else if( selectedItem.equals(BusinessRules.TESTDATA_STATE_INT)) { return Type.integer; }
	  else if( selectedItem.equals(BusinessRules.TESTDATA_STATE_MONEY)) { return Type.money; }
	  else if( selectedItem.equals(BusinessRules.TESTDATA_STATE_NCHAR)) { return Type.nchar; }
	  else if( selectedItem.equals(BusinessRules.TESTDATA_STATE_NTEXT)) { return Type.ntext; }
	  else if( selectedItem.equals(BusinessRules.TESTDATA_STATE_NUMERIC)) { return Type.numeric; }
	  else if( selectedItem.equals(BusinessRules.TESTDATA_STATE_NVARCHAR)) { return Type.nVarChar; }
	  else if( selectedItem.equals(BusinessRules.TESTDATA_STATE_REAL)) { return Type.real; }
	  else if( selectedItem.equals(BusinessRules.TESTDATA_STATE_SMALLDATETIME)) { return Type.smallDateTime; }
	  else if( selectedItem.equals(BusinessRules.TESTDATA_STATE_SMALLINT)) { return Type.smallInt; }
	  else if( selectedItem.equals(BusinessRules.TESTDATA_STATE_SMALLMONEY)) { return Type.smallMoney; }
	  else if( selectedItem.equals(BusinessRules.TESTDATA_STATE_TEXT)) { return Type.text; }
	  else if( selectedItem.equals(BusinessRules.TESTDATA_STATE_TIMESTAMP)) { return Type.timeStamp; }
	  else if( selectedItem.equals(BusinessRules.TESTDATA_STATE_TINYINT)) { return Type.tinyInt; }
	  else if( selectedItem.equals(BusinessRules.TESTDATA_STATE_VARBINARY)) { return Type.varBinary; }	
	  else { return Type.varChar; }
	  
  }
  
  public int getSelectedType() {
    Object selectedItem = (String) getJComboBoxType().getSelectedItem();
    if( selectedItem.equals(BusinessRules.TESTDATA_STATE_BINARY))    {  return CMTypeBean.TYPE_BINARY; }
	  else if( selectedItem.equals(BusinessRules.TESTDATA_STATE_BIT)) { return CMTypeBean.TYPE_BIT; }
	  else if( selectedItem.equals(BusinessRules.TESTDATA_STATE_BOOLEAN)) { return CMTypeBean.TYPE_BOLEAN; }
	  else if( selectedItem.equals(BusinessRules.TESTDATA_STATE_CHAR)) { return CMTypeBean.TYPE_CHARACTER; }
	  else if( selectedItem.equals(BusinessRules.TESTDATA_STATE_DATETIME)) { return CMTypeBean.TYPE_DATETIME; }
	  else if( selectedItem.equals(BusinessRules.TESTDATA_STATE_DECIMAL)) { return CMTypeBean.TYPE_DECIMAL; }
	  else if( selectedItem.equals(BusinessRules.TESTDATA_STATE_FLOAT)) { return CMTypeBean.TYPE_FLOATPOINT; }
	  else if( selectedItem.equals(BusinessRules.TESTDATA_STATE_INT)) { return CMTypeBean.TYPE_INTEGER; }
	  else if( selectedItem.equals(BusinessRules.TESTDATA_STATE_MONEY)) { return CMTypeBean.TYPE_MONEY; }
	  else if( selectedItem.equals(BusinessRules.TESTDATA_STATE_NCHAR)) { return CMTypeBean.TYPE_NCHAR; }
	  else if( selectedItem.equals(BusinessRules.TESTDATA_STATE_NTEXT)) { return CMTypeBean.TYPE_NTEXT; }
	  else if( selectedItem.equals(BusinessRules.TESTDATA_STATE_NUMERIC)) { return CMTypeBean.TYPE_NUMERIC; }
	  else if( selectedItem.equals(BusinessRules.TESTDATA_STATE_NVARCHAR)) { return CMTypeBean.TYPE_NVARCHAR; }
	  else if( selectedItem.equals(BusinessRules.TESTDATA_STATE_REAL)) { return CMTypeBean.TYPE_REAL; }
	  else if( selectedItem.equals(BusinessRules.TESTDATA_STATE_SMALLDATETIME)) { return CMTypeBean.TYPE_SMALLDATETIME; }
	  else if( selectedItem.equals(BusinessRules.TESTDATA_STATE_SMALLINT)) { return CMTypeBean.TYPE_SMALLINT; }
	  else if( selectedItem.equals(BusinessRules.TESTDATA_STATE_SMALLMONEY)) { return CMTypeBean.TYPE_SMALLMONEY; }
	  else if( selectedItem.equals(BusinessRules.TESTDATA_STATE_TEXT)) { return CMTypeBean.TYPE_TEXT; }
	  else if( selectedItem.equals(BusinessRules.TESTDATA_STATE_TIMESTAMP)) { return CMTypeBean.TYPE_TIMESTAMP; }
	  else if( selectedItem.equals(BusinessRules.TESTDATA_STATE_TINYINT)) { return CMTypeBean.TYPE_TINYINT; }
	  else if( selectedItem.equals(BusinessRules.TESTDATA_STATE_VARBINARY)) { return CMTypeBean.TYPE_VARBINARY; }	
	  else { return CMTypeBean.TYPE_VARCHAR; }
   
  }
  public void setType(int type) {
	  if( type == CMTypeBean.TYPE_BINARY)
		  getJComboBoxType().setSelectedIndex(0);
	  else if( type == CMTypeBean.TYPE_BIT)
		  getJComboBoxType().setSelectedIndex(1);
	  else if( type == CMTypeBean.TYPE_BOLEAN)
		  getJComboBoxType().setSelectedIndex(2);
	  else if( type == CMTypeBean.TYPE_CHARACTER)
		  getJComboBoxType().setSelectedIndex(3);
	  else if( type == CMTypeBean.TYPE_DATETIME)
		  getJComboBoxType().setSelectedIndex(4);
	  else if( type == CMTypeBean.TYPE_DECIMAL)
		  getJComboBoxType().setSelectedIndex(5);
	  else if( type == CMTypeBean.TYPE_FLOATPOINT)
		  getJComboBoxType().setSelectedIndex(6);
	  else if( type == CMTypeBean.TYPE_INTEGER)
		  getJComboBoxType().setSelectedIndex(7);
	  else if( type == CMTypeBean.TYPE_MONEY)
		  getJComboBoxType().setSelectedIndex(8);
	  else if( type == CMTypeBean.TYPE_NCHAR)
		  getJComboBoxType().setSelectedIndex(9);
	  else if( type == CMTypeBean.TYPE_NTEXT)
		  getJComboBoxType().setSelectedIndex(10);
	  else if( type == CMTypeBean.TYPE_NUMERIC)
		  getJComboBoxType().setSelectedIndex(11);
	  else if( type == CMTypeBean.TYPE_NVARCHAR)
		  getJComboBoxType().setSelectedIndex(12);
	  else if( type == CMTypeBean.TYPE_REAL)
		  getJComboBoxType().setSelectedIndex(13);
	  else if( type == CMTypeBean.TYPE_SMALLDATETIME)
		  getJComboBoxType().setSelectedIndex(14);
	  else if( type == CMTypeBean.TYPE_SMALLINT)
		  getJComboBoxType().setSelectedIndex(15);
	  else if( type == CMTypeBean.TYPE_SMALLMONEY)
		  getJComboBoxType().setSelectedIndex(16);
	  else if( type == CMTypeBean.TYPE_TEXT)
		  getJComboBoxType().setSelectedIndex(17);
	  else if( type == CMTypeBean.TYPE_TIMESTAMP)
		  getJComboBoxType().setSelectedIndex(18);
	  else if( type == CMTypeBean.TYPE_TINYINT)
		  getJComboBoxType().setSelectedIndex(19);
	  else if( type == CMTypeBean.TYPE_VARBINARY)
		  getJComboBoxType().setSelectedIndex(20);
	  else if( type == CMTypeBean.TYPE_VARCHAR)	
		  getJComboBoxType().setSelectedIndex(21);
   
  }
  public JComboBox getJComboBoxType()
  {
	  if (jComboBoxType == null)
		  jComboBoxType = new CMBaseJComboBox(this);
    return jComboBoxType;
  }
}