/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.controller.cmimport.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JViewport;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.apache.log4j.Logger;

import bi.controller.cmimport.CMCSVField;
import bi.controller.cmimport.CMCSVFileFormat;
import bi.controller.cmimport.CMCSVImportContext;
import bi.view.cells.headers.CMCellHeaderDefault;
import bi.view.cells.renderers.CMBaseGridCellRenderer;
import bi.view.grids.CMBaseJSmartGrid;
import bi.view.lang.CMMessages;

import com.eliad.model.GenericGridModel;
import com.eliad.model.defaults.DefaultStyleModel;
import com.eliad.swing.JSmartGrid;

/**
 * @author smoreno
 *
 */
public class CMCVSImportPreviewGrid extends CMBaseJSmartGrid implements ActionListener, DocumentListener  {
private CMCSVImportOptionsDialog dialog;
private GenericGridModel model ;
private Logger logger = Logger.getLogger(this.getClass());


/**
 *
 */
public CMCVSImportPreviewGrid() {
	// TODO Auto-generated constructor stub
	super();
	initialize();
}

/**
*@autor smoreno
 */
private void initialize() {
	this.setAutoResizeMode(JSmartGrid.AUTO_RESIZE_ALL);
	this.setShowGrid(true);
	this.setOpaque(false);
	this.setColumnResizable(true);
	this.setSelectionCellBorder(BorderFactory.createLineBorder(Color.orange,2));
	this.setFocusHighlightBorder(BorderFactory.createLineBorder(Color.orange,2));
    this.setSelectionBackgroundColor(SystemColor.activeCaptionText);
    this.setSelectionForegroundColor(Color.black);
    this.setSelectionUnit(com.eliad.swing.JSmartGrid.UNIT_CELL);
    this.setSelectionPolicy(com.eliad.swing.JSmartGrid.POLICY_SINGLE);
    this.setGridColor(new Color(196,194,196));
	this.setToolTipText(CMMessages.getString("IMPORT_CSV_OPTIONS_DIALOG_PREVIEW_TITLE")); //$NON-NLS-1$
	model = new GenericGridModel(0,0);
	this.setModel(model);
	this.setAutoscrolls(true);
	((DefaultStyleModel)this.getStyleModel()).setRenderer(String.class, new CMBaseGridCellRenderer());
	((DefaultStyleModel)this.getStyleModel()).setEditor(Object.class, null);

	}

/* (non-Javadoc)
 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
 */
public void actionPerformed(ActionEvent p_e) {
	//update the view based on the current selection

		refreshAll();

}

private void refreshContents(CMCSVFileFormat p_format)
{

	if (dialog.getSelectedFile()!=null)
	{

	CMCSVImportContext context = new CMCSVImportContext(p_format);
	String[][] contents;
	context.setDelimiter(dialog.getDelimiter());
	try {
		contents = context.parse(dialog.getSelectedFile());
	} catch (IOException e) {
		//if the preview is not posible exit
		logger.error(e.getMessage());
		return;
	}
	//get the current headers
	Vector<Vector> data = new Vector<Vector>();
	Vector<CMCellHeaderDefault> headers = new Vector<CMCellHeaderDefault>();
	for (int col = 0;col< model.getColumnCount();col++)
	{
		headers.add((CMCellHeaderDefault) model.getValueAt(0,col));
	}
	//set the current data
	data.add(headers);
	for (String[] row: contents)
	{
		//Arrays.asList(row)
		Vector<String> vec = new Vector<String>();
		vec.addAll(Arrays.asList(row));
		while (headers.size()>vec.size())
			vec.add("");
		data.add(vec);
	}

	model = new GenericGridModel(0,0);
	this.setModel(model);
	try
	{
	model.setSize(data.size()+1, headers.size()+1);
	model.setDataVector(data);
	}catch (ArrayIndexOutOfBoundsException e)
	{
		logger.error(CMMessages.getString("IMPORT_CSV_OPTIONS_DIALOG_PREVIEW_ERROR")+e.getMessage()); //$NON-NLS-1$
	}
	}
}
/**
*@autor smoreno
 * @param p_format
 */
private void refreshHeaders(CMCSVFileFormat p_format) {
	Vector<Vector> data = new Vector<Vector>();
	Vector<CMCellHeaderDefault> headers = new Vector<CMCellHeaderDefault>();
	for (CMCSVField field : p_format.getFields())
		headers.add(new CMCellHeaderDefault(this,field.toString()));
	//do not delete old data
	if (model.getRowCount()>1)
	{
		for (int row = 1;row< model.getRowCount();row++)
		{
			Vector rowVector = new Vector();
			for (int col = 0;col< model.getColumnCount();col++)
				rowVector.add(model.getValueAt(row, col));
			data.add(rowVector);
		}
	}

	data.insertElementAt(headers, 0);
	try
	{
	model.setDataVector(data);
	}catch (ArrayIndexOutOfBoundsException e)
	{
		logger.error(CMMessages.getString("IMPORT_CSV_OPTIONS_DIALOG_PREVIEW_ERROR")+e.getMessage()); //$NON-NLS-1$
	}




}

/* (non-Javadoc)
 * @see javax.swing.event.DocumentListener#insertUpdate(javax.swing.event.DocumentEvent)
 */
//event from the filename text field
public void insertUpdate(DocumentEvent p_e) {
		refreshAll();
}

/**
*@autor smoreno
 */
private void refreshAll() {

			CMCSVFileFormat format = (CMCSVFileFormat) dialog.getJComboImportAs().getSelectedItem();
			refreshHeaders(format);
			refreshContents(format);
}

/* (non-Javadoc)
 * @see javax.swing.event.DocumentListener#removeUpdate(javax.swing.event.DocumentEvent)
 */
public void removeUpdate(DocumentEvent p_e) {
}

/* (non-Javadoc)
 * @see javax.swing.event.DocumentListener#changedUpdate(javax.swing.event.DocumentEvent)
 */
public void changedUpdate(DocumentEvent p_e) {
}

public JViewport getViewport1() {
    return getViewport();    // getViewport is not public in JSmartGrid
  }

public CMCSVImportOptionsDialog getDialog() {
	return this.dialog;
}

public void setDialog(CMCSVImportOptionsDialog p_dialog) {
	this.dialog = p_dialog;
	//add the listeners to the radio buttons
	if (!Arrays.asList(dialog.getDelimiterPanel().getJRadioComma().getActionListeners()).contains(this))
		dialog.getDelimiterPanel().getJRadioComma().addActionListener(this);
	if (!Arrays.asList(dialog.getDelimiterPanel().getJRadioSemiColon().getActionListeners()).contains(this))
		dialog.getDelimiterPanel().getJRadioSemiColon().addActionListener(this);
	if (!Arrays.asList(dialog.getDelimiterPanel().getJRadioSpace().getActionListeners()).contains(this))
		dialog.getDelimiterPanel().getJRadioSpace().addActionListener(this);
	if (!Arrays.asList(dialog.getDelimiterPanel().getJRadioTab().getActionListeners()).contains(this))
		dialog.getDelimiterPanel().getJRadioTab().addActionListener(this);
	if (!Arrays.asList(dialog.getDelimiterPanel().getJRadioOther().getActionListeners()).contains(this))
		dialog.getDelimiterPanel().getJRadioOther().addActionListener(this);
	dialog.getDelimiterPanel().getJTextOther().getDocument().removeDocumentListener(this);
	dialog.getDelimiterPanel().getJTextOther().getDocument().addDocumentListener(this);
}

@Override
protected HashMap<Class, Component> getCellClasses() {
	// TODO Auto-generated method stub
	return null;
}
}
