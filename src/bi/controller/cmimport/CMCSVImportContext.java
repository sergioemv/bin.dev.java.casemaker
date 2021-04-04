/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved. 
 */
package bi.controller.cmimport;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.undo.UndoableEdit;

import org.apache.log4j.Logger;

import bi.view.lang.CMMessages;

import com.Ostermiller.util.CSVParser;

/**
 * @author smoreno
 *
 */
public class CMCSVImportContext {
	
private int importPosition;
private CMCSVFileFormat format;
private Logger logger = Logger.getLogger("bi.controller.cmimport");
private char delimiter; 
private List<String> warningMessages;
 /**
 * 
 */
public CMCSVImportContext( CMCSVFileFormat p_format) {
   this.format = p_format;
}
/**
 * @param p_importPosition The importPosition to set.
 */
public void setImportPosition(int p_importPosition) {
	this.importPosition = p_importPosition;
}
  public UndoableEdit importFile(File p_file) throws IOException
  {
	  //parse the file
	  String[][] values = parse(p_file);
	  //validate the imported values
	  //transform to list and do the import
	 getWarningMessages().clear();
	 UndoableEdit edit = this.format.getImporter().doImport(validateFormat(values));
	 getWarningMessages().addAll(this.format.getImporter().getWarningMessages());
	 
	return edit;
  }

/**
*@autor smoreno
 * @param p_values
 * @return
 */
private List<List> validateFormat(String[][] p_values) {
	List<List> validatedRows = new Vector<List>();
	for (String[] row: p_values)
	{
		validatedRows.add(validateRow(row));
	}
	return validatedRows;
}
/**
*@autor smoreno
 * @param p_row
 * @return
 */
private List<String> validateRow(String[] p_row) {
	List<String> validatedFields = new Vector<String>();
	int fieldIndex = 0 ; 
	for (String field : p_row)
	{
		if (fieldIndex>=format.getFields().size())
		{
			getWarningMessages().add(CMMessages.getString("IMPORT_CSV_WARNING_IGNORED_FIELD")+" - "+field);
			logger.warn(CMMessages.getString("IMPORT_CSV_WARNING_IGNORED_FIELD")+" - "+field);
			fieldIndex++;
			continue;
		}
		//search for allowed values
		if (( format.getFields()).get(fieldIndex).getFormat().getAllowedValues()!=null)
		{
			//logger.debug("Validating values "+ format.getFields().get(fieldIndex)+" for field "+field);
			if (!format.getFields().get(fieldIndex).getFormat().getAllowedValues().contains(field))
			{
				getWarningMessages().add("Found invalid value"+" \""+field+"\" expecting one of: "+format.getFields().get(fieldIndex).getFormat().getAllowedValues()+"; default value used \""+format.getFields().get(fieldIndex).getFormat().getDefault()+"\"");
				logger.warn(CMMessages.getString("IMPORT_CSV_WARNIGN_PUTTING_DEFAULT_VALUE")+" - "+field);
				
				validatedFields.add(format.getFields().get(fieldIndex).getFormat().getDefault());
				fieldIndex++;
				continue;
			}
			
		}
		//TODO: look for match patterns
		if (!( format.getFields()).get(fieldIndex).getFormat().getValidPattern().equalsIgnoreCase(""))
		{
			if (!field.matches( format.getFields().get(fieldIndex).getFormat().getValidPattern()))
			{
				getWarningMessages().add(CMMessages.getString("IMPORT_CSV_WARNIGN_PUTTING_DEFAULT_VALUE")+" - "+field);
				logger.warn(CMMessages.getString("IMPORT_CSV_WARNIGN_PUTTING_DEFAULT_VALUE")+" - "+field);
				validatedFields.add(format.getFields().get(fieldIndex).getFormat().getDefault());
				fieldIndex++;
				continue;
			}
		}
			validatedFields.add(field);
			fieldIndex++;
	}
	return validatedFields;
}
/**
*@autor smoreno
 * @param p_file
 * @return
 * @throws IOException 
 * @throws CMNoImportDataException 
 */
public String[][] parse(File p_file) throws IOException {
	FileReader fr = new FileReader(p_file);
	//TODO warn if no data has been found
	String[][] data = CSVParser.parse(fr,delimiter);
	return data;
}
public char getDelimiter() {
	return this.delimiter;
}
public void setDelimiter(char p_delimiter) {
	this.delimiter = p_delimiter;
}
public CMCSVFileFormat getFormat() {
	return this.format;
}
public List<String> getWarningMessages() {
	if (warningMessages == null)
		warningMessages = new ArrayList<String>();
	return warningMessages;
}
	
}
