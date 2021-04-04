package bi.view.businessrulesviews.brimport.wizardpane;

import bi.view.businessrulesviews.brimport.CMBusinessRulesImportWizard;

import com.nexes.wizard.WizardPanelDescriptor;

public class CMimportWarningPaneDescriptor extends WizardPanelDescriptor {

	public static final String IDENTIFIER = "WARNING_IMPORT_PANEL"; //$NON-NLS-1
	private CMImportWarningPane importWarningPane;
	/**
	 * 
	 */
	public CMimportWarningPaneDescriptor() {
		importWarningPane= new  CMImportWarningPane();
		setPanelDescriptorIdentifier(IDENTIFIER);
		setPanelComponent(importWarningPane);
	}
	@Override
	public void aboutToDisplayPanel() {
		getWizard().setPanelComponentsOrder(importWarningPane.getOrder());
		importWarningPane.setLog(((CMBusinessRulesImportWizard)getWizard()).getSelectedImport().getImporter().getWarningMessages());
		getWizard().setBackButtonEnabled(false);
		getWizard().setCancelButtonEnabled(false);
		  getWizard().addKeyListenertoAll(importWarningPane,getWizard().getDefaultKeyListener());

	}
	@Override
	public void aboutToHidePanel() {
		// TODO Auto-generated method stub
		super.aboutToHidePanel();
	}
	@Override
	public void displayingPanel() {
		getWizard().setFocus();
	}

	@Override
	public Object getNextPanelDescriptor() {
		return FINISH;
	}
	
	

}
