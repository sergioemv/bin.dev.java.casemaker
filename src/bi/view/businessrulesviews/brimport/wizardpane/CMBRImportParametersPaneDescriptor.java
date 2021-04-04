package bi.view.businessrulesviews.brimport.wizardpane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import bi.view.businessrulesviews.brimport.CMBusinessRulesImportWizard;

import com.nexes.wizard.WizardPanelDescriptor;


/**
* This code was generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* *************************************
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED
* for this machine, so Jigloo or this code cannot be used legally
* for any corporate or commercial purpose.
* *************************************
*/
public class CMBRImportParametersPaneDescriptor extends WizardPanelDescriptor implements ActionListener {

	public static final String IDENTIFIER = "DEFAULT_IMPORT_PANEL"; //$NON-NLS-1$  //  @jve:decl-index=0:
	protected CMBRImportParametersPane panel;

	/**
	 *
	 */
	public CMBRImportParametersPaneDescriptor() {
		setPanelComponent(getPanel());
	}

	@Override
	public Object getBackPanelDescriptor() {
		return ((CMBusinessRulesImportWizard)getWizard()).getSelectedImport().getPrevPanelDescriptor(IDENTIFIER);
	}

	@Override
	public Object getNextPanelDescriptor() {
		return ((CMBusinessRulesImportWizard)getWizard()).getSelectedImport().getNextPanelDescriptor(IDENTIFIER);
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(getPanel().getJBrowseButton())){
			File selectedFile= ((CMBusinessRulesImportWizard)getWizard()).selectImportBusinessRuleFile();
			if(selectedFile != null){
				getPanel().setSelectedFile(selectedFile);
			}
			else{
				getPanel().setSelectedFile(selectedFile);
			}
		}
		
		//portiz_25102007_begin
		if(e.getSource().equals(getPanel().getJBrowseButtonBom())){
			File selectedFile= ((CMBusinessRulesImportWizard)getWizard()).selectImportBomFile();
			if(selectedFile != null){
				getPanel().setSelectedFileBom(selectedFile);
			}
			else{
				getPanel().setSelectedFileBom(selectedFile);
			}
		}
		//portiz_25102007_end		
		
		
		
		if(e.getSource().equals(getPanel().getJSaveAsButton())){
			File selectedFile =((CMBusinessRulesImportWizard)getWizard()).selectSaveAsBusinessRulesFile();
			if(selectedFile  != null){
				getPanel().setSaveAsSelectedFile(selectedFile);
			}

		}
		if(e.getSource().equals(getPanel().getJEnglishGrammarRadioButton())){
			if(getPanel().getJEnglishGrammarRadioButton().isSelected()){
				((CMBusinessRulesImportWizard)getWizard()).setEnglishSyntax();
			}

		}
		if(e.getSource().equals(getPanel().getJGermanGrammarRadioButton())){
			if(getPanel().getJGermanGrammarRadioButton().isSelected()){
				((CMBusinessRulesImportWizard)getWizard()).setGermanSyntax();
			}
		}
		if(e.getSource().equals(getPanel().getJSaveCheckBox())){
			if(getPanel().getJSaveCheckBox().isSelected()){
				((CMBusinessRulesImportWizard)getWizard()).setOldFilePreserved(true);
				getPanel().setStateOfSaveAsComponents();
			}
			else{
				((CMBusinessRulesImportWizard)getWizard()).setOldFilePreserved(false);
				getPanel().setStateOfSaveAsComponents();
			}
		}
		validateNextFinishedButtonState();
	}
	private void validateNextFinishedButtonState(){
		try {
			//portiz_25102007_begin
			boolean bEnableNextButton=false;
			//portiz_25102007_end
			
			if(((CMBusinessRulesImportWizard)getWizard()).getImportFile()!=null){
				if(((CMBusinessRulesImportWizard)getWizard()).isOldFilePreserved()){
					if(((CMBusinessRulesImportWizard)getWizard()).getSaveAsFile()!=null){
						bEnableNextButton=true;
						//getWizard().setNextFinishButtonEnabled(true);
					}
					else{
						bEnableNextButton=false;
						//getWizard().setNextFinishButtonEnabled(false);
					}
				}
				else{
					//getWizard().setNextFinishButtonEnabled(true);
					bEnableNextButton=true;
				}
			}
			else{
				//getWizard().setNextFinishButtonEnabled(false);
				bEnableNextButton=false;
			}
						
			if (((CMBusinessRulesImportWizard)getWizard()).mustValidateBomFile()  ){
				if(((CMBusinessRulesImportWizard)getWizard()).getImportFileBom()!=null)
					bEnableNextButton= bEnableNextButton && true;
				else
					bEnableNextButton=false;
			}
							
			getWizard().setNextFinishButtonEnabled(bEnableNextButton);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			getWizard().setNextFinishButtonEnabled(false);
		}
	}

	@Override
	public void aboutToDisplayPanel() {
		getWizard().setPanelComponentsOrder(getPanel().getOrder());
		validateNextFinishedButtonState();
		  getWizard().addKeyListenertoAll(getPanel(),getWizard().getDefaultKeyListener());

	}


	@Override
	public void displayingPanel() {
		getWizard().setFocus();
	getWizard().setPanelComponentsOrder(getPanel().getOrder());

	}

	public CMBRImportParametersPane getPanel() {
		if (panel==null) {
			panel= new CMBRImportParametersPane();
			panel.getJBrowseButton().addActionListener(this);
			//portiz_20102007_begin
			panel.getJBrowseButtonBom().addActionListener(this);
			//portiz_20102007_end			
			panel.getJSaveCheckBox().addActionListener(this);
			panel.getJSaveAsButton().addActionListener(this);
			panel.getJEnglishGrammarRadioButton().addActionListener(this);
			panel.getJGermanGrammarRadioButton().addActionListener(this);
			setPanelDescriptorIdentifier(IDENTIFIER);
		}
		return panel;
	}


}