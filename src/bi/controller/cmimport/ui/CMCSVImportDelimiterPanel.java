/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved. 
 */
package bi.controller.cmimport.ui;

import java.awt.Component;
import java.awt.Font;
import java.awt.SystemColor;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import bi.view.lang.CMMessages;



/**
 * @author smoreno
 *
 */
public class CMCSVImportDelimiterPanel extends JPanel {
private ButtonGroup delimiterGroup;
private JRadioButton jRadioComma;
private JRadioButton jRadioSemiColon;
private JRadioButton jRadioTab;
private JRadioButton jRadioSpace;
private JRadioButton jRadioOther;
private JTextField jTextOther;

/**
 * 
 */
public CMCSVImportDelimiterPanel() {
		super();
		initialize();
}

/**
*@autor smoreno
 */
private void initialize() {
	// TODO Auto-generated method stub
	TitledBorder titledBorder = BorderFactory.createTitledBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),CMMessages.getString("IMPORT_CSV_OPTIONS_DIALOG_DELIMITER_PANEL_TITLE"), TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.PLAIN, 11), SystemColor.activeCaption)); //$NON-NLS-1$ //$NON-NLS-2$
	this.setPreferredSize(new java.awt.Dimension(388,55));
	this.setBorder(titledBorder);
	this.add(getJRadioComma(), null);
	this.add(getJRadioSemiColon(), null);
	this.add(getJRadioTab(), null);
	this.add(getJRadioSpace(), null);
	this.add(getJRadioOther(), null);
	this.add(getJTextOther(), null);
	delimiterGroup = new ButtonGroup();
		this.setToolTipText(CMMessages.getString("IMPORT_CSV_OPTIONS_DIALOG_DELIMITER_PANEL_TOOLTIP")); //$NON-NLS-1$
	delimiterGroup.add(getJRadioComma());
	delimiterGroup.add(getJRadioSemiColon());
	delimiterGroup.add(getJRadioTab());
	delimiterGroup.add(getJRadioSpace());
	delimiterGroup.add(getJRadioOther());
	//by default the comma separator is selected
	delimiterGroup.setSelected(getJRadioComma().getModel(), true);
	
}
 JRadioButton getJRadioComma() {
	if (jRadioComma == null) {
		jRadioComma = new JRadioButton();
		jRadioComma.setText(CMMessages.getString("IMPORT_CSV_OPTIONS_DIALOG_DELIMITER_COMMA")); //$NON-NLS-1$
		
	}
	return jRadioComma;
}

/**
 * This method initializes jRadioSemiColon	
 * 	
 * @return javax.swing.JRadioButton	
 */
JRadioButton getJRadioSemiColon() {
	if (jRadioSemiColon == null) {
		jRadioSemiColon = new JRadioButton();
		jRadioSemiColon.setText(CMMessages.getString("IMPORT_CSV_OPTIONS_DIALOG_DELIMITER_SEMICOLON")); //$NON-NLS-1$
	}
	return jRadioSemiColon;
}

/**
 * This method initializes jRadioTab	
 * 	
 * @return javax.swing.JRadioButton	
 */
 JRadioButton getJRadioTab() {
	if (jRadioTab == null) {
		jRadioTab = new JRadioButton();
		jRadioTab.setText(CMMessages.getString("IMPORT_CSV_OPTIONS_DIALOG_DELIMITER_TAB")); //$NON-NLS-1$
	}
	return jRadioTab;
}

/**
 * This method initializes jRadioSpace	
 * 	
 * @return javax.swing.JRadioButton	
 */
 JRadioButton getJRadioSpace() {
	if (jRadioSpace == null) {
		jRadioSpace = new JRadioButton();
		jRadioSpace.setText(CMMessages.getString("IMPORT_CSV_OPTIONS_DIALOG_DELIMITER_SPACE")); //$NON-NLS-1$
	}
	return jRadioSpace;
}

/**
 * This method initializes jRadioOther	
 * 	
 * @return javax.swing.JRadioButton	
 */
JRadioButton getJRadioOther() {
	if (jRadioOther == null) {
		jRadioOther = new JRadioButton();
		jRadioOther.setText(CMMessages.getString("IMPORT_CSV_OPTIONS_DIALOG_DELIMITER_OTHER")); //$NON-NLS-1$
		
	}
	return jRadioOther;
}

/**
 * This method initializes jTextOther	
 * 	
 * @return javax.swing.JTextField	
 */
 JTextField getJTextOther() {
	if (jTextOther == null) {
		jTextOther = new JTextField();
		jTextOther.setPreferredSize(new java.awt.Dimension(20,20));
		jTextOther.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyTyped(java.awt.event.KeyEvent e) {
				if (!jRadioOther.isSelected())
				jRadioOther.doClick();
				jTextOther.selectAll();
			}
		});
	}
	return jTextOther;
}
public char getDelimiter()
{
	if (jRadioComma.isSelected())
		return ',';
	if (jRadioSemiColon.isSelected())
		return ';';
	if (jRadioSpace.isSelected())
		return ' ';
	if (jRadioTab.isSelected())
		return '	';
	if (jRadioOther.isSelected()&&!jTextOther.getText().equalsIgnoreCase("")) //$NON-NLS-1$
		return jTextOther.getText().charAt(0);
	return ' ';
}


/**
*@autor smoreno
 * @return
 */
public Collection<? extends Component> getOrder() {
	List<Component> list = new Vector<Component>();
	list.add(jRadioComma);
	list.add(jRadioSemiColon);
	list.add(jRadioTab);
	list.add(jRadioSpace);
	list.add(jRadioOther);
	list.add(jTextOther);
	return list;
}
}
