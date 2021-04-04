package bi.view.businessrulesviews.brimport.wizardpane;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import bi.view.businessrulesviews.brimport.CMBusinessRulesImportWizard;
import bi.view.businessrulesviews.brimport.ECMBusinessRulesImport;

import com.nexes.wizard.WizardPanelDescriptor;

public class CMPrincipalPaneDescriptor extends WizardPanelDescriptor implements ListSelectionListener {

	public static final String IDENTIFIER = "PRINCIPAL_PANEL"; //$NON-NLS-1$
	CMPrincipalPane principalPane;
	/**
	 * 
	 */
	public CMPrincipalPaneDescriptor() {
		super();
		principalPane= new CMPrincipalPane();
		principalPane.addListSelectionListener(this);
		setPanelDescriptorIdentifier(IDENTIFIER);
        setPanelComponent(principalPane);
        
	}

	@Override
	public Object getBackPanelDescriptor() {
		return null;
	}

	@Override
	public Object getNextPanelDescriptor() {
		
		return ((ECMBusinessRulesImport)principalPane.getJOptionList().getSelectedValue()).getNextPanelDescriptor(IDENTIFIER);
	}

	

	public void valueChanged(ListSelectionEvent e) {
		
			ECMBusinessRulesImport importer = (ECMBusinessRulesImport) principalPane.getJOptionList().getSelectedValue();
	        if(importer != null)
	    	{
	        	
	        	principalPane.getJDescriptionLabel().setText(importer.getDescription());
	        	((CMBusinessRulesImportWizard)getWizard()).setSelectedImport(importer);
	        	//portiz_26102007_begin
	        	/*if (importer.equals(ECMBusinessRulesImport.JRULES))
	        		((CMBusinessRulesImportWizard)getWizard()).getImportParametersPaneDescriptor().getPanel().showHideBomData(true);
	        	else
	        		((CMBusinessRulesImportWizard)getWizard()).getImportParametersPaneDescriptor().getPanel().showHideBomData(false);
	        		
	        		*/
	        	//portiz_26102007_end	        	
	    	}
	}

	@Override
	public void aboutToDisplayPanel() {
		getWizard().setPanelComponentsOrder(principalPane.getOrder());
		  getWizard().addKeyListenertoAll(principalPane,getWizard().getDefaultKeyListener());

	}

	@Override
	public void aboutToHidePanel() {
		// TODO Auto-generated method stub
		super.aboutToHidePanel();
	
	}

	@Override
	public void displayingPanel() {
		getWizard().setFocus();
		valueChanged(null);
	}

}