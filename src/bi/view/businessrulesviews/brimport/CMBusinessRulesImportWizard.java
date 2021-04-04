package bi.view.businessrulesviews.brimport;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import model.TestObject;
import model.TestObjectOrigin;
import model.brimport.BRLanguaje;

import org.apache.log4j.Logger;

import bi.controller.StructureManager;
import bi.view.actions.CMAction;
import bi.view.businessrulesviews.brimport.wizardpane.CMBRImportParametersPaneDescriptor;
import bi.view.businessrulesviews.brimport.wizardpane.CMBRJRulesImportPaneDescriptor;
import bi.view.businessrulesviews.brimport.wizardpane.CMJRulesNativeReplacePanelDescriptor;
import bi.view.businessrulesviews.brimport.wizardpane.CMPrincipalPaneDescriptor;
import bi.view.businessrulesviews.brimport.wizardpane.CMProgressImportPaneDescriptor;
import bi.view.businessrulesviews.brimport.wizardpane.CMimportWarningPaneDescriptor;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.utils.CMFileFilter;
import bi.view.utils.event.CMProgressEventHandler;
import bi.view.utils.event.CMProgressListener;
import bi.view.utils.event.CMProgressSource;

import com.nexes.wizard.Wizard;

public class CMBusinessRulesImportWizard extends Wizard implements CMProgressSource{

	private File importFile;
	//paulo_25102007_begin
	private File importFileBom;
	//paulo_25102007_end	
	
	private File saveAsFile;
	private boolean oldFilePreserved;
	private BRLanguaje selectedLanguaje;
	private ECMBusinessRulesImport selectedImport;
	private CMProgressEventHandler handler;
	private CMProgressImportPaneDescriptor progressImportPaneDescriptor;
	private Map<String,Object> aditionalOptions;  //  @jve:decl-index=0:

	//paulo_26102007_begin
	private CMBRImportParametersPaneDescriptor importParametersPaneDescriptor;
	//paulo_26102007_end
	
	
	public CMBusinessRulesImportWizard() {

		super(CMApplication.frame);
		//internationalization
		setBACK_TEXT(CMMessages.getString("BR_IMPORT_WIZARD_BACK_BUTTON"));
		setCANCEL_TEXT(CMMessages.getString("BUTTON_CANCEL"));
		setFINISH_TEXT(CMMessages.getString("BUTTON_FINISH"));
		setNEXT_TEXT(CMMessages.getString("BR_IMPORT_WIZARD_NEXT_BUTTON"));
		//register all descriptors
		registerWizardPanel(CMPrincipalPaneDescriptor.IDENTIFIER, new CMPrincipalPaneDescriptor());
		registerWizardPanel(CMBRImportParametersPaneDescriptor.IDENTIFIER, new CMBRImportParametersPaneDescriptor());

		progressImportPaneDescriptor = new CMProgressImportPaneDescriptor();
		getHandler().addProgressListener(progressImportPaneDescriptor);
		//portiz_26102007_begin
		registerWizardPanel(CMProgressImportPaneDescriptor.IDENTIFIER, progressImportPaneDescriptor );
		registerWizardPanel(CMimportWarningPaneDescriptor.IDENTIFIER, new CMimportWarningPaneDescriptor());
		importParametersPaneDescriptor=new CMBRJRulesImportPaneDescriptor();
		registerWizardPanel(CMBRJRulesImportPaneDescriptor.IDENTIFIER,importParametersPaneDescriptor );
		registerWizardPanel(CMJRulesNativeReplacePanelDescriptor.IDENTIFIER , new CMJRulesNativeReplacePanelDescriptor());
		//portiz_26102007_end
		
		/*original code commented
		registerWizardPanel(CMProgressImportPaneDescriptor.IDENTIFIER, progressImportPaneDescriptor );
		registerWizardPanel(CMimportWarningPaneDescriptor.IDENTIFIER, new CMimportWarningPaneDescriptor());
		registerWizardPanel(CMBRJRulesImportPaneDescriptor.IDENTIFIER, new CMBRJRulesImportPaneDescriptor());
		registerWizardPanel(CMJRulesNativeReplacePanelDescriptor.IDENTIFIER , new CMJRulesNativeReplacePanelDescriptor());*/	
		
	}

	
	public CMBRImportParametersPaneDescriptor getImportParametersPaneDescriptor(){
		return importParametersPaneDescriptor;
	}
	
	@Override
	public int showModalDialog() {

        setModal(true);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        getDialog().setPreferredSize(new Dimension(550, 450));
        Dimension dlgSize = getDialog().getPreferredSize();
        getDialog().setLocation((screenSize.width - dlgSize.width) / 2 /*+ loc.x*/,
                (screenSize.height - dlgSize.height) / 2 /*+ loc.y*/);

//      set the initial panel
		setCurrentPanel(CMPrincipalPaneDescriptor.IDENTIFIER);
		 getWizardController().resetButtonsToPanelRules();
        getDialog().setVisible(true);
        return 1;
	}

	public File getImportFile() throws Exception {
		if (importFile!=null){
		String filePath= importFile.toString();
		if (!filePath.endsWith(getSelectedImport().getFileFilter().getExtension()))
				throw new Exception(CMMessages.getString("INCORRECT_FILE_EXTENSION"));
		 if(!importFile.exists() || !importFile.canRead()) //$NON-NLS-1$
   			throw new IOException(CMMessages.getString("BR_IMPORT_WIZARD_FILE_CORRUPTED"));
		}
		return importFile;
	}

	public void setImportFile(File importFile) {
		this.importFile = importFile;
	}

	//paulo_25102007_begin
	
	public File getImportFileBom() throws Exception {
		if (importFileBom!=null){
		String filePath= importFileBom.toString();
		if (!filePath.endsWith("bom"))
				throw new Exception(CMMessages.getString("INCORRECT_FILE_EXTENSION"));
		 if(!importFileBom.exists() || !importFileBom.canRead()) //$NON-NLS-1$
   			throw new IOException(CMMessages.getString("BR_IMPORT_WIZARD_FILE_CORRUPTED"));
		}
		return importFileBom;
	}
	
	public void setImportFileBom(File importFile) {
		this.importFileBom = importFile;
	}
	//paulo_25102007_end
	
	public File getSaveAsFile() {

		return saveAsFile;
	}

	public void setSaveAsFile(File saveAsFile) {
		this.saveAsFile = saveAsFile;
	}

	public void setEnglishSyntax() {
		selectedLanguaje = BRLanguaje.ENGLISH;
	}

	public void setGermanSyntax() {
		selectedLanguaje = BRLanguaje.GERMAN;
	}

	BRLanguaje getSelectedLanguaje() {
		if (selectedLanguaje == null)
			selectedLanguaje = BRLanguaje.ENGLISH;
		return selectedLanguaje;
	}

	public boolean isOldFilePreserved() {
		return oldFilePreserved;
	}

	public void setOldFilePreserved(boolean oldFilePreserved) {
		this.oldFilePreserved = oldFilePreserved;
	}

	public ECMBusinessRulesImport getSelectedImport() {
		return selectedImport;
	}

	//portiz_25102007_begin
	public boolean mustValidateBomFile(){
		return (getSelectedImport()==ECMBusinessRulesImport.JRULES);
	}
	//portiz_25102007_end	
	
	public void setSelectedImport(ECMBusinessRulesImport selectedImport) {
		this.selectedImport = selectedImport;
		
		
	}
	public File selectSaveAsBusinessRulesFile() {
		   JFileChooser fileChooser;
	        File selectedFile= null;
	        fileChooser = new JFileChooser();
	        fileChooser.addChoosableFileFilter(CMFileFilter.TXT.getFilter());
	        fileChooser.setAcceptAllFileFilterUsed(false);
	        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
	        int returnVal=fileChooser.showSaveDialog(null);
			if (returnVal == 0) {
	            selectedFile = fileChooser.getSelectedFile();
	            if(!selectedFile.getName().endsWith(CMFileFilter.TXT.getExtension())){
	            	selectedFile=new File(selectedFile.getAbsolutePath()+CMFileFilter.TXT.getExtension());
	            }
			}
			setSaveAsFile(selectedFile);
			return selectedFile;
	}
	
	//portiz_25102007_begin
	public File selectImportBomFile() {
		if (getSelectedImport() == null) return null;

		CMFileFilter filter = CMFileFilter.BOM;
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.addChoosableFileFilter(filter.getFilter());
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int returnVal = fileChooser.showOpenDialog(getDialog());
        File selectedFile = null;
        if (returnVal == 0) {
            selectedFile = fileChooser.getSelectedFile();

            if(!selectedFile.exists()){
				JOptionPane.showMessageDialog(getDialog(),CMMessages.getString("BR_IMPORT_WIZARD_FILE_DOESNT_EXIST"),CMMessages.getString("ERROR_MESSAGE"),JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$ //$NON-NLS-2$
                return null;
			}
            setImportFileBom(selectedFile);
            return selectedFile;
		}
        else{
            return null;
        }
    }
	//portiz_25102007_end
	
	
	public File selectImportBusinessRuleFile() {
		if (getSelectedImport() == null) return null;

		CMFileFilter filter = getSelectedImport().getFileFilter();
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.addChoosableFileFilter(filter.getFilter());
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int returnVal = fileChooser.showOpenDialog(getDialog());
        File selectedFile = null;
        if (returnVal == 0) {
            selectedFile = fileChooser.getSelectedFile();

            if(!selectedFile.exists()){
				JOptionPane.showMessageDialog(getDialog(),CMMessages.getString("BR_IMPORT_WIZARD_FILE_DOESNT_EXIST"),CMMessages.getString("ERROR_MESSAGE"),JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$ //$NON-NLS-2$
                return null;
			}
            setImportFile(selectedFile);
            return selectedFile;
		}
        else{
            return null;
        }
    }


	public  void initiateImport() {

		//define a separate thread for importing
		getSelectedImport().setImporter(null);//cc

		if (getSelectedImport().getImporter() instanceof CMProgressSource)
			((CMProgressSource)getSelectedImport().getImporter()).addProgressListener(progressImportPaneDescriptor);
		final Thread task = new Thread() {
			@Override
			public synchronized void run() {
				try {
				//if the save is selected save the old br file
				getHandler().fireProgressEventHappen(0, CMMessages.getString("BR_IMPORT_WIZARD_COLLECTING_DATA"));
				Thread.sleep(200);
	        	if(isOldFilePreserved()){
	        		getHandler().fireProgressEventHappen(25, CMMessages.getString("BR_IMPORT_WIZARD_SAVING_FILE"));
					String oldBRContent = CMApplication.frame.getBusinessRulesPanelView().getCMBusinessRuleEditor().getJTextPaneEditor().getText();
					saveStringToFile(oldBRContent, getSaveAsFile() );
					Thread.sleep(100);
	            }
	         	//import the file


	         	String result = getSelectedImport().getImporter().doImport(getImportFile(),getImportFileBom(),getAditionalOptions());

				String absoluteFilePath = StructureManager.getSelectedTestObjectPath()+StructureManager.getSelectedStructure().getTestObject().getBRulesReference().getFilePath();
				saveStringToFile(result,new File(absoluteFilePath));
				StructureManager.getSelectedStructure().getTestObject().getBRulesReference().setFileSyntax(getSelectedLanguaje().ordinal());
				CMApplication.frame.getBusinessRulesPanelView().selectFileSyntaxCombo(getSelectedLanguaje().ordinal());
				if (!getSelectedImport().isWorkflowResult())
					StructureManager.getSelectedStructure().getTestObject().setOrigin(null);
				else
					StructureManager.getSelectedStructure().getTestObject().setOrigin(new TestObjectOrigin(getImportFile().toString()));

				Thread.sleep(100);
	              if (getSelectedImport().getImporter().getWarningMessages().equalsIgnoreCase(""))
	            	  getHandler().fireProgressEventHappen(100, CMMessages.getString("BR_IMPORT_WIZARD_SUCCESS"));
	              else
	            	  getHandler().fireProgressEventHappen(100,CMMessages.getString("BR_IMPORT_WIZARD_WARNINGS_VIEW"));
	              setNextFinishButtonEnabled(true);
	             // setBackButtonEnabled(false);
	              setBusinessRulesInToEditor(result);
	              getWizardController().resetButtonsToPanelRules();
	              setBackButtonEnabled(false);
	         	 CMApplication.frame.initializeTheUndoManager();
	         	 notifyAll();
			 }//try
	         catch (Exception e) {TestObject selectedTestObject= CMApplication.frame.getTreeWorkspaceView().getCurrentTestObject();
	        	 getHandler().fireProgressEventHappen(e, CMMessages.getString("BR_IMPORT_WIZARD_ERROR"));
	         	 JOptionPane.showMessageDialog(getDialog(),"An error occur during the operation: \n"+e.getMessage(),selectedTestObject.getName()+" - "+ CMAction.BI_BUSINESS_RULES_IMPORT.getAction().getValue(Action.NAME).toString(),JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$ //$NON-NLS-2$
	        	 Logger.getLogger(this.getClass()).error(e);
	        	 e.printStackTrace();
	        	 setBackButtonEnabled(true);
	        	 return;

			}finally {
				 getWizardController().resetButtonsToPanelRules();
	        	 setBackButtonEnabled(false);
			}

			}
		};
	task.start();
}
private void saveStringToFile(String text,File selectedFile) throws IOException{
  	        FileWriter writer = new FileWriter(selectedFile);
				for(int i=0;i<text.length();i++){
                      char c=text.charAt(i);
                      if(c==13||c==10){
							writer.write(System.getProperty("line.separator"));
                      }
                      else{
                      	writer.write(c);
                      }
                  }

          		writer.close();
      }
private void setBusinessRulesInToEditor(String result) {
	CMApplication.frame.getBusinessRulesPanelView().getCMBusinessRuleEditor().setText(result);
	CMApplication.frame.getBusinessRulesPanelView().getCMBusinessRuleEditor().revalidate();

}
public void addProgressListener(CMProgressListener rl) {
	getHandler().addProgressListener(rl);

}
public void removeProgressListener(CMProgressListener rl) {
	getHandler().removeProgressListener(rl);

}
public CMProgressEventHandler getHandler() {
	if (handler == null)
		handler = new CMProgressEventHandler();
	return handler;
}
public Map<String, Object> getAditionalOptions() {
	if (aditionalOptions== null)
		aditionalOptions = new HashMap<String, Object>();
	return aditionalOptions;
}

}