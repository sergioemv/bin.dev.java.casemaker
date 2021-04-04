package bi.view.businessrulesviews.brimport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.Icon;

import com.nexes.wizard.WizardPanelDescriptor;

import bi.view.businessrulesviews.brimport.wizardpane.CMBRImportParametersPaneDescriptor;
import bi.view.businessrulesviews.brimport.wizardpane.CMBRJRulesImportPaneDescriptor;
import bi.view.businessrulesviews.brimport.wizardpane.CMPrincipalPaneDescriptor;
import bi.view.businessrulesviews.brimport.wizardpane.CMProgressImportPaneDescriptor;
import bi.view.icons.CMIcon;
import bi.view.lang.CMMessages;
import bi.view.utils.CMFileFilter;

public enum ECMBusinessRulesImport {

	BUSINESS_RULES(
			CMMessages.getString("MENU_BUSINESS_RULES"),
			CMMessages.getString("BUSINESS_RULES_IMPORT_DESCRIPTION"),
			CMIcon.BUSINESS_RULES_TEXT_IMPORT.getImageIcon(),
			CMFileFilter.TXT,
			false
			),
	VISIO(
			CMMessages.getString("MENU_ITEM_BUSINESS_RULES_VISIO_IMPORT"),
			CMMessages.getString("BUSINESS_RULES_VISIO_IMPORT_DESCRIPTION"),
			CMIcon.BR_VISIO_IMPORT.getImageIcon(),
			CMFileFilter.VDX,
			true
			),
	JRULES(
			CMMessages.getString("LABEL_JRULES_IMPORT"),
			CMMessages.getString("LABEL_JRULES_IMPORT_DESCRIPTION"),
			CMIcon.BR_JRULES_IMPORT.getImageIcon(),
			CMFileFilter.XRL,
			false
			);
	
	private ICMBRImporter importer;
	private List<String> panelDescriptorIdentifiers;
	private Icon image;
	private String description;
	private String name;
	private CMFileFilter filter;
	private boolean workflowResult;
	
	
	
	ECMBusinessRulesImport (String p_name, String p_Description, Icon p_image,CMFileFilter p_filter, boolean p_workFlowResult){
		name = p_name;
		description = p_Description;
		image = p_image;
		filter = p_filter;
		workflowResult = p_workFlowResult;
	}
	public Icon getIconImage() {
		return image;
	}
	public String getDescription() {
		return description;
	}

	public String getName() {
		return name;
	}
	
	public CMFileFilter getFileFilter() {
		return filter;
	}
	public List<String> getPanelDescriptorIdentifiers(){
		if (panelDescriptorIdentifiers == null) {
			panelDescriptorIdentifiers = new ArrayList<String>();
			panelDescriptorIdentifiers.add(CMPrincipalPaneDescriptor.IDENTIFIER);
			switch (this) {
			case VISIO:
				panelDescriptorIdentifiers.addAll(Arrays.asList(CMBRImportParametersPaneDescriptor.IDENTIFIER, 
						CMProgressImportPaneDescriptor.IDENTIFIER));
				break;
			case JRULES:
				panelDescriptorIdentifiers.addAll(Arrays.asList(CMBRJRulesImportPaneDescriptor.IDENTIFIER, 
						CMProgressImportPaneDescriptor.IDENTIFIER));
				break;
			default:
				panelDescriptorIdentifiers.addAll(Arrays.asList(CMBRImportParametersPaneDescriptor.IDENTIFIER,
						   CMProgressImportPaneDescriptor.IDENTIFIER));
				break;
			}
		}
		return panelDescriptorIdentifiers;
	}
	
	public String getPrevPanelDescriptor(String identifier)
	{
		int position = getPanelDescriptorIdentifiers().indexOf(identifier);
		if ((position>0)&&(getPanelDescriptorIdentifiers().size()-1>=position))
			return getPanelDescriptorIdentifiers().get(position-1);
		return CMPrincipalPaneDescriptor.IDENTIFIER;
	}
	
	public String getNextPanelDescriptor(String identifier) {
		int position = getPanelDescriptorIdentifiers().indexOf(identifier);
		if ((position>=0)&&(position+1<getPanelDescriptorIdentifiers().size()))
			return getPanelDescriptorIdentifiers().get(position+1);
		
			
		return null;
	}
	
	public ICMBRImporter getImporter() {
		if (importer == null)
			createImporter();
		return importer;
	}
	private void createImporter() {
		switch (this) {
		case BUSINESS_RULES:
			importer = new CMBRTextImporter();
			break;
		case JRULES:
			importer = new CMBRJRulesImporter();
			break;
		case VISIO:
			importer = new CMBRVisioImporter();
			break;
		default:
			break;
		}
		
	}
	public boolean isWorkflowResult() {
		return workflowResult;
	}
	//ccastedo adds this from the patch stream 31.05.07
	public void setImporter(ICMBRImporter importer) {
		this.importer = importer;
	}

}