package bi.view.preferences.lang;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.util.MissingResourceException;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import bi.view.icons.CMIcon;
import bi.view.lang.CMMessages;
import bi.view.lang.NamedPropertyResourceBundle;
import bi.view.mainframeviews.CMApplication;
import bi.view.utils.CMModalResult;

import com.jidesoft.dialog.ButtonEvent;
import com.jidesoft.dialog.ButtonListener;
import com.jidesoft.dialog.ButtonNames;
import com.jidesoft.dialog.PageEvent;
import com.jidesoft.dialog.PageList;
import com.jidesoft.dialog.PageListener;
import com.jidesoft.swing.JideSwingUtilities;
import com.jidesoft.wizard.AbstractWizardPage;
import com.jidesoft.wizard.WizardDialog;
import com.jidesoft.wizard.WizardStyle;

public class CMLanguageImportCreateController {

	private WizardDialog wizard;
	private PageList pageModel;
	private String newLanguageName=""; //$NON-NLS-1$
	private String importedFileName=""; //$NON-NLS-1$
	protected CMLanguageEditController editcontroller;
    public WizardDialog getWizard(){
    	if (wizard == null){
    		wizard = new WizardDialog(CMApplication.frame, CMMessages.getString("CAPTION_LANGUAGE_DEFINITION")); //$NON-NLS-1$
    		wizard.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    		wizard.setDefaultGraphic(CMIcon.WIZARD_ICON.getImageIcon().getImage());
    		wizard.setPageList(getPageModel());
    		wizard.pack();
    	    wizard.setResizable(true);
    	    JideSwingUtilities.globalCenterWindow(wizard);
    	    wizard.setFinishAction(new AbstractAction(CMMessages.getString("BUTTON_FINISH")){
				public void actionPerformed(ActionEvent e) {
					 if (wizard.closeCurrentPage()) {
		                if (newLanguageName=="") return;
		                try {
							CMMessages.registerNewBundle(newLanguageName);
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(wizard, "An error occurred registering the new language\n"+e1.getLocalizedMessage(), "Create", JOptionPane.ERROR_MESSAGE);
							return;
						}
						 editcontroller.applyChanges();
						 wizard.dispose();
				}}});
    	  JButton but = (JButton) wizard.getButtonPanel().getButtonByName(ButtonNames.NEXT );
    	  but.setText(CMMessages.getString("BR_IMPORT_WIZARD_NEXT_BUTTON"));
    	  but = (JButton) wizard.getButtonPanel().getButtonByName(ButtonNames.BACK  );
    	  but.setText(CMMessages.getString("BR_IMPORT_WIZARD_BACK_BUTTON"));
    	  
    	}
    	return wizard;
    }


	private PageList getPageModel() {
		if (pageModel==null){
			final AbstractWizardPage pageNameAndFile = new AbstractWizardPage(CMMessages.getString("TITLE_IMPORT_LANGUAGE_DEFINITION"), //$NON-NLS-1$
					CMMessages.getString("DESCRIPTION_CREATE_IMPORT_LANGUAGE"),CMIcon.WIZARD_ICON.getImageIcon()){ //$NON-NLS-1$
				final CMPageNameAndFilePanel panel = new CMPageNameAndFilePanel();
				@Override
				public JComponent createWizardContent() {

		            panel.setBorder(getContentThinBorder());
		            panel.getJTextFieldName().addCaretListener(new CaretListener(){


						public void caretUpdate(CaretEvent e) {
							updateNextButton();
						}});
		            panel.getJTextFieldFileName().addCaretListener(new CaretListener(){

						public void caretUpdate(CaretEvent e) {
							importedFileName = panel.getJTextFieldFileName().getText();
							updateNextButton();
						}});

		            return panel;
				}

				public void updateNextButton(){
					if (!panel.getJTextFieldName().getText().equalsIgnoreCase("")) //$NON-NLS-1$
						fireButtonEvent(ButtonEvent.ENABLE_BUTTON, ButtonNames.NEXT);
					else
						fireButtonEvent(ButtonEvent.DISABLE_BUTTON, ButtonNames.NEXT);
					newLanguageName = panel.getJTextFieldName().getText();
				}
				@Override
				public void setupWizardButtons() {
					fireButtonEvent(ButtonEvent.DISABLE_BUTTON, ButtonNames.BACK);
					fireButtonEvent(ButtonEvent.HIDE_BUTTON, ButtonNames.FINISH);
					fireButtonEvent(ButtonEvent.DISABLE_BUTTON, ButtonNames.NEXT);
					updateNextButton();

				}

			};
			final AbstractWizardPage pagePreviewAndEdit = new AbstractWizardPage(CMMessages.getString("CAPTION_LANGUAGE_DEFINITION"),CMMessages.getString("TITLE_EDIT_LANGUAGE_DEFINITION"), CMIcon.WIZARD_ICON.getImageIcon()){ //$NON-NLS-1$ //$NON-NLS-2$

				@Override
				public JComponent createWizardContent() {
					editcontroller = new CMLanguageEditController(CMMessages.getLangBaseBundle());
					JPanel panel = editcontroller.getPanel();
					panel.setBorder(getContentThinBorder());
					return panel;
				}

				@Override
				public void setupWizardButtons() {
					// TODO Auto-generated method stub
					fireButtonEvent(ButtonEvent.HIDE_BUTTON, ButtonNames.NEXT);
					fireButtonEvent(ButtonEvent.SHOW_BUTTON, ButtonNames.FINISH);
					fireButtonEvent(ButtonEvent.ENABLE_BUTTON, ButtonNames.BACK);
				}

			};
			pageNameAndFile.addPageListener(new PageListener(){

				public void pageEventFired(PageEvent arg0) {
					if (arg0.getID()==PageEvent.PAGE_OPENED){
						pageNameAndFile.setupWizardButtons();
					}

				}});
			pagePreviewAndEdit.addPageListener(new PageListener(){

				public void pageEventFired(PageEvent arg0) {
					if (arg0.getID()==PageEvent.PAGE_OPENED){
						loadImportedFile();
						editcontroller.loadPanelProperties();
					}

				}});
		
			pageModel = new PageList();
			pageModel.addElement(pageNameAndFile);
			pageModel.addElement(pagePreviewAndEdit);

		}
		return pageModel;
	}


	protected void loadImportedFile() {
		if (!importedFileName.equalsIgnoreCase("")){ //$NON-NLS-1$
			try{
				File file = new File(importedFileName);
				FileInputStream fip = new FileInputStream(file);
				NamedPropertyResourceBundle rb = new NamedPropertyResourceBundle(fip,newLanguageName);
				editcontroller.setEditingLanguage(rb);

			}catch (Exception e){
				JOptionPane.showMessageDialog(wizard, CMMessages.getString("ERROR_READING_THE_IMPORTED_FILE")+e.getLocalizedMessage()); //$NON-NLS-1$
			}
		}


	}




}
