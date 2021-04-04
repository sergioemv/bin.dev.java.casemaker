package bi.view.businessrulesviews.brimport.wizardpane;

import javax.swing.WindowConstants;

import bi.view.businessrulesviews.brimport.CMBusinessRulesImportWizard;
import bi.view.utils.event.CMProgressEvent;
import bi.view.utils.event.CMProgressListener;

import com.nexes.wizard.WizardPanelDescriptor;

public class CMProgressImportPaneDescriptor extends WizardPanelDescriptor implements CMProgressListener {

	public static final String IDENTIFIER = "PROGRESS_IMPORT_PANEL"; //$NON-NLS-1$
	
	private CMProgressImportPane progressImportPane;
	/**
	 * 
	 */
	public CMProgressImportPaneDescriptor() {
		progressImportPane= new CMProgressImportPane();
		setPanelDescriptorIdentifier(IDENTIFIER);
		setPanelComponent(progressImportPane);
	}

	@Override
	public void aboutToDisplayPanel() {
		getWizard().setPanelComponentsOrder(progressImportPane.getOrder());
		 getWizard().setNextFinishButtonEnabled(false);
	     getWizard().setBackButtonEnabled(false);
	     getWizard().setCancelButtonEnabled(false);
	     getWizard().getDialog().setDefaultCloseOperation(
	    		 WindowConstants.DO_NOTHING_ON_CLOSE);
	     ((CMBusinessRulesImportWizard)getWizard()).initiateImport();
	   
	}

	@Override
	public void displayingPanel() {
		
		getWizard().setFocus();
	    getWizard().addKeyListenertoAll(progressImportPane,getWizard().getDefaultKeyListener());
	    
	}

	@Override
	public Object getBackPanelDescriptor() {
		return ((CMBusinessRulesImportWizard)getWizard()).getSelectedImport().getPrevPanelDescriptor(IDENTIFIER);
	}

	@Override
	public Object getNextPanelDescriptor() {
		/*getWizard().getDialog().setDefaultCloseOperation(
	    		 WindowConstants.DISPOSE_ON_CLOSE);
		  if (((CMBusinessRulesImportWizard)getWizard()).getSelectedImport().getImporter().getWarningMessages().equalsIgnoreCase(""))
			return FINISH;
		else
			return CMimportWarningPaneDescriptor.IDENTIFIER;*/
		 if (((CMBusinessRulesImportWizard)getWizard()).getSelectedImport().getNextPanelDescriptor(IDENTIFIER)== null)
			 return FINISH;
		 
		return ((CMBusinessRulesImportWizard)getWizard()).getSelectedImport().getNextPanelDescriptor(IDENTIFIER);
	}

	public void progressEventHappen(CMProgressEvent rev) {
		if (rev.getSource() instanceof Integer)
		{
			progressImportPane.setProgressValue((Integer) rev.getSource());
			progressImportPane.setProgressText(rev.getProgress()); //$NON-NLS-1$
		}
		if (rev.getSource() instanceof Exception)
		{
			progressImportPane.setProgressValue(0);
			progressImportPane.setProgressText(rev.getProgress());
		}
		
	}

	
	
	
}
