package bi.view.businessrulesviews.brimport.wizardpane;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import bi.view.businessrulesviews.brimport.CMBusinessRulesImportWizard;
import bi.view.lang.CMMessages;
import bi.view.utils.CMFileFilter;

public class CMBRJRulesImportPaneDescriptor extends
		CMBRImportParametersPaneDescriptor {
	public static final String IDENTIFIER = "JRULES_IMPORT_PANEL"; //$NON-NLS-1$

	@Override
	public CMBRImportParametersPane getPanel() {
		// TODO Auto-generated method stub
		if (panel == null) {
			panel= new CMBRJRulesImportPane();
			
			//portiz_25102007_begin
			panel.getJBrowseButtonBom().addActionListener(this);
			panel.showHideBomData(true);
			//portiz_25102007_end
			
			panel.getJBrowseButton().addActionListener(this);
			panel.getJSaveCheckBox().addActionListener(this);
			panel.getJSaveAsButton().addActionListener(this);
			panel.getJEnglishGrammarRadioButton().addActionListener(this);
			panel.getJGermanGrammarRadioButton().addActionListener(this);
			((CMBRJRulesImportPane)panel).getJCheckBoxIgnoreCasts().addActionListener(this);
			((CMBRJRulesImportPane)panel).getJCheckBoxNativeReplacements().addActionListener(this);
			((CMBRJRulesImportPane)panel).getJButtonSearchVoc().addActionListener(this);

			setPanelDescriptorIdentifier(IDENTIFIER);


		}
		return panel;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		super.actionPerformed(e);
		if (e.getSource().equals(((CMBRJRulesImportPane)getPanel()).getJCheckBoxIgnoreCasts()))
		{
			((CMBusinessRulesImportWizard)getWizard()).getAditionalOptions().put("IGNORE_CAST", ((CMBRJRulesImportPane)getPanel()).getJCheckBoxIgnoreCasts().isSelected());
		}
		if (e.getSource().equals(((CMBRJRulesImportPane)getPanel()).getJCheckBoxNativeReplacements()))
		{
			((CMBusinessRulesImportWizard)getWizard()).getAditionalOptions().put("NATIVE_REPLACEMENTS", ((CMBRJRulesImportPane)getPanel()).getJCheckBoxNativeReplacements().isSelected());
		}
		if (e.getSource().equals(((CMBRJRulesImportPane)getPanel()).getJButtonSearchVoc())) {
			File selectedFile= selectVocabularyFile();
			((CMBusinessRulesImportWizard)getWizard()).getAditionalOptions().put("VOCABULARY_FILE", selectedFile);
			if (selectedFile!=null) {

				((CMBRJRulesImportPane)getPanel()).getJTextFieldVoc().setText(selectedFile.getAbsolutePath());
			}
			else
				((CMBRJRulesImportPane)getPanel()).getJTextFieldVoc().setText("");
		}
	}
	private File selectVocabularyFile() {

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.addChoosableFileFilter(CMFileFilter.VOC.getFilter());
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int returnVal = fileChooser.showOpenDialog(getWizard().getDialog());
        File selectedFile = null;
        if (returnVal == 0) {
            selectedFile = fileChooser.getSelectedFile();

            if(!selectedFile.exists()){
				JOptionPane.showMessageDialog(getWizard().getDialog(),CMMessages.getString("BR_IMPORT_WIZARD_FILE_DOESNT_EXIST"),CMMessages.getString("ERROR_MESSAGE"),JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$ //$NON-NLS-2$
                return null;
			}
            return selectedFile;
		}
        else{
            return null;
        }
	}
	@Override
	public Object getBackPanelDescriptor() {
		return ((CMBusinessRulesImportWizard)getWizard()).getSelectedImport().getPrevPanelDescriptor(IDENTIFIER);
	}

	@Override
	public Object getNextPanelDescriptor() {
		if (((CMBusinessRulesImportWizard)getWizard()).getAditionalOptions().get("NATIVE_REPLACEMENTS")!=null)
		    if ((Boolean)((CMBusinessRulesImportWizard)getWizard()).getAditionalOptions().get("NATIVE_REPLACEMENTS"))
		    	return CMJRulesNativeReplacePanelDescriptor.IDENTIFIER;
		return ((CMBusinessRulesImportWizard)getWizard()).getSelectedImport().getNextPanelDescriptor(IDENTIFIER);
	}
	@Override
	public void aboutToDisplayPanel() {
		super.aboutToDisplayPanel();
		((CMBusinessRulesImportWizard)getWizard()).getAditionalOptions().put("IGNORE_CAST", ((CMBRJRulesImportPane)getPanel()).getJCheckBoxIgnoreCasts().isSelected());
		((CMBusinessRulesImportWizard)getWizard()).getAditionalOptions().put("NATIVE_REPLACEMENTS", ((CMBRJRulesImportPane)getPanel()).getJCheckBoxNativeReplacements().isSelected());

	}
}