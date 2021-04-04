package bi.view.preferences.lang;


import java.awt.Dimension;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.JOptionPane;


import bi.view.lang.CMMessages;
import bi.view.lang.NamedPropertyResourceBundle;

public class CMLanguageEditController {

	protected static final Dimension DEFAULT_DIALOG_SIZE = new Dimension(700, 650);
	private CMLanguageDialogPanel panel;
	private ResourceBundle baseLanguage;
	private NamedPropertyResourceBundle editingLanguage;
	private boolean modified;

	public CMLanguageDialogPanel getPanel() {
		if (panel == null)
			panel = new CMLanguageDialogPanel();
		return panel;
	}
	public CMLanguageEditController(ResourceBundle base,NamedPropertyResourceBundle editLanguage) {
		baseLanguage = base;
		editingLanguage = editLanguage;
		loadPanelProperties();
	}

	public CMLanguageEditController(ResourceBundle base) {
		baseLanguage = base;
	}
	public void loadPanelProperties(){
		if (baseLanguage==null) return;
		getPanel().getLanguageTableModel().setRowCount(0);
		if (editingLanguage!=null)
			getPanel().setLangName(editingLanguage.getName());
		Enumeration keys = baseLanguage.getKeys();
		while (keys.hasMoreElements()){
			Vector<Object> row = new Vector<Object>(3);
			Object key = keys.nextElement();
			row.add(key);
			row.add(baseLanguage.getString((String) key));
			try{
			row.add(editingLanguage.getString((String) key));
			}catch(Exception e){
				row.add(""); //$NON-NLS-1$
			}

			//getPanel().getGridLanguage().getLanguageGridModel().addRow(row);
			getPanel().getLanguageTableModel().addRow(row);
		}
	}
	public void applyChanges(){
		if (editingLanguage == null) return;
		Properties props = new Properties();
		Vector dataVector = panel.getLanguageTableModel().getDataVector();
		Map<String,String> values = new  HashMap<String, String>();
		for (Object vec : dataVector){
			values.put(((Vector)vec).get(0).toString(), ((Vector)vec).get(2).toString());
		}

		Enumeration keys = baseLanguage.getKeys();
		while (keys.hasMoreElements()){
			Object key = keys.nextElement();
			props.put(key, values.get(key));
			}
		try {
			CMMessages.saveLangBundle(props, editingLanguage.getName());
		} catch (IOException e) {
			JOptionPane.showMessageDialog(panel,CMMessages.getString("ERROR_SAVE_LANGUAGE_BUNDLE")+e.getMessage()); //$NON-NLS-1$
		}

	}
	public void setEditingLanguage(NamedPropertyResourceBundle editingLanguage) {
		this.editingLanguage = editingLanguage;
	}


}
