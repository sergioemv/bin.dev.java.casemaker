package bi.view.businessrulesviews.brimport.wizardpane;

import java.util.Map;

import javax.swing.Action;
import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import bi.view.actions.CMAction;
import bi.view.businessrulesviews.brimport.CMBRJRulesImporter;
import bi.view.businessrulesviews.brimport.CMBusinessRulesImportWizard;
import bi.view.lang.CMMessages;

import com.nexes.wizard.WizardPanelDescriptor;

public class CMJRulesNativeReplacePanelDescriptor extends WizardPanelDescriptor {
	public static final String IDENTIFIER = "NATIVE_REPLACE_IMPORT_PANEL"; //$NON-NLS-1
	private CMJRulesNativeIdentifiersReplacePanel panel;

	public CMJRulesNativeReplacePanelDescriptor() {

		setPanelDescriptorIdentifier(IDENTIFIER);
		setPanelComponent(getPanel());
	}
	public CMJRulesNativeIdentifiersReplacePanel getPanel() {
		if (panel == null)
			panel = new CMJRulesNativeIdentifiersReplacePanel();
		return panel;
	}

	@Override
	public Object getBackPanelDescriptor() {
		return CMBRJRulesImportPaneDescriptor.IDENTIFIER;
	}

	@Override
	public Object getNextPanelDescriptor() {
		return CMProgressImportPaneDescriptor.IDENTIFIER;
	}
	@Override
	public void aboutToDisplayPanel() {
		CMBusinessRulesImportWizard wiz = (CMBusinessRulesImportWizard)getWizard();
		getWizard().setPanelComponentsOrder(getPanel().getTabOrder());
		try {
			Map<String,String> identifiers = ((CMBRJRulesImporter)wiz.getSelectedImport().getImporter()).findNativeIdentifiers(wiz.getImportFile(), wiz.getAditionalOptions());
			getPanel().getReplaceGrid().setGridContents(identifiers);
			
		} catch (Exception e) {
		  	 wiz.getHandler().fireProgressEventHappen(e, CMMessages.getString("BR_IMPORT_WIZARD_ERROR"));
         	 JOptionPane.showMessageDialog(wiz.getDialog(),"An error occur during the operation: \n"+e.getMessage(), CMAction.BI_BUSINESS_RULES_IMPORT.getAction().getValue(Action.NAME).toString(),JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$ //$NON-NLS-2$
        	 Logger.getLogger(this.getClass()).error(e);
        	 e.printStackTrace();
        	 wiz.setBackButtonEnabled(true);
        	 wiz.setNextFinishButtonEnabled(false);
        	 
        	 return;
		}
	}
	@Override
	public void aboutToHidePanel() {
		CMBusinessRulesImportWizard wiz = (CMBusinessRulesImportWizard)getWizard();
		Map map = panel.getGridContents();
		for (Object key : map.keySet()) {
			((CMBRJRulesImporter)wiz.getSelectedImport().getImporter()).getNativeIdentifiers().put((String)key, (String)map.get(key));
		}
		super.aboutToHidePanel();
	}
}
