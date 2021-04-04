/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.view.actions.file;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.SpinnerNumberModel;

import com.jidesoft.dialog.MultiplePageDialog;
import com.jidesoft.dialog.PageList;
import com.jidesoft.dialog.StandardDialog;
import com.jidesoft.swing.JideSwingUtilities;

import model.ApplicationSetting;
import model.TestObject;

import bi.controller.SessionManager;
import bi.controller.editcontrol.CMEquivalenceClassEditController;
import bi.view.icons.CMIcon;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.preferences.CMPrefDialog;
import bi.view.preferences.CMPreferencesDefaultPage;
import bi.view.preferences.externalapps.CMExternalApplicationController;
import bi.view.preferences.general.CMGeneralPreferencesController;
import bi.view.preferences.lang.CMLanguagePreferencesController;
import bi.view.preferences.report.CMReportsController;
import bi.view.preferences.toolvendors.CMToolVendorReportsController;
import bi.view.utils.CMDefaultDialog;
import bi.view.utils.CMModalResult;

/**
 * @author smoreno
 *
 */
public class CMCaseMakerSettingsAction extends AbstractAction implements Action {
	public CMCaseMakerSettingsAction() {
		super(CMMessages.getString("TESTDATA_CASEMAKER_SETTINGS"));
	    // Set tool tip text
	    putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("MENU_ITEM_CREATE_WORKSPACE"));
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("TESTDATA_CASEMAKER_SETTINGS_MNEMONIC").charAt(0));

	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent p_e) {

		ApplicationSetting setting = SessionManager.getCurrentSession().getM_ApplicationSetting();
	    
	    final CMExternalApplicationController externalApplicationPreferencesController = new CMExternalApplicationController();
	    externalApplicationPreferencesController.initData(setting);
	    final CMToolVendorReportsController toolVendorReportsController = new CMToolVendorReportsController();
	    toolVendorReportsController.initData(setting);
	    final CMLanguagePreferencesController controller = new CMLanguagePreferencesController();
	    final CMReportsController reportsController = new CMReportsController();
	    reportsController.initData(setting);
	    final CMGeneralPreferencesController preferencesController = new CMGeneralPreferencesController();
	    preferencesController.initData(setting);
	    CMPrefDialog dlg = new CMPrefDialog(CMApplication.frame,(String) this.getValue(Action.NAME));
	    dlg.setStyle(MultiplePageDialog.ICON_STYLE);
	   	PageList model = new PageList();
	   	model.append(new CMPreferencesDefaultPage(CMMessages.getString("LABEL_CASEMAKER_SETTINGS_TAB"),CMIcon.GENERAL_PREFERENCES.getImageIcon()){
	    	@Override
	    	protected void initComponents() {	    		
	    		super.initComponents();
	    		add(preferencesController.getPanel(),BorderLayout.CENTER);
	    	}
	    });
	   	model.append(new CMPreferencesDefaultPage(CMMessages.getString("LABEL_EXTERNAL_APPLICATIONS"),CMIcon.EXTERNAL_APP.getImageIcon()){
	    	    	@Override
	    	    	protected void initComponents() {	    		
	    	    		super.initComponents();
	    	    		add(externalApplicationPreferencesController.getPanel(),BorderLayout.CENTER);
	    	    	}
	    	    });
	   	model.append(new CMPreferencesDefaultPage(CMMessages.getString("LABEL_CASEMAKER_SETTINGS_ToolVendorTab"),CMIcon.TOOL_VENDOR_PREFERENCES.getImageIcon()){
	    	@Override
	    	protected void initComponents() {	    		
	    		super.initComponents();
	    		add(toolVendorReportsController.getPanel(),BorderLayout.CENTER);
	    	}
	    });
		model.append(new CMPreferencesDefaultPage(CMMessages.getString("LABEL_LANGUAGE"),CMIcon.LANGUAGE_PREFERENCES.getImageIcon()){
	    	@Override
	    	protected void initComponents() {	    		
	    		super.initComponents();
	    		add(controller.getPanel(),BorderLayout.CENTER);
	    	}
	    });
		model.append(new CMPreferencesDefaultPage(CMMessages.getString("LABEL_CASEMAKER_REPORTS_TAB"),CMIcon.REPORT_PREFERENCES.getImageIcon()){
	    	@Override
	    	protected void initComponents() {	    		
	    		super.initComponents();
	    		add(reportsController.getPanel(),BorderLayout.CENTER);
	    	}
	    });
	   	dlg.setPageList(model);
	    
	   	dlg.pack();
	   	JideSwingUtilities.globalCenterWindow(dlg);
       dlg.setVisible(true);
        if (dlg.getDialogResult() == StandardDialog.RESULT_AFFIRMED){
        	externalApplicationPreferencesController.applyChanges(setting);
        	toolVendorReportsController.applyChanges(setting);
        	applyChanges(setting);
        }

	}
	 private void applyChanges(ApplicationSetting setting) {
	        SessionManager.getCurrentSession().setM_ApplicationSetting(setting);
	        SessionManager.INSTANCE.writeSessionToFile();
	        CMApplication.frame.getPanelTestDataSetReportsView().updateApplications();
	        CMApplication.frame.getPanelTestObjectView().updatePanelTestObjecView();
	        CMApplication.frame.getPanelTestDataSetView().updateJcomboxReports();
	        CMApplication.frame.getTreeWorkspaceView().save2();

	    }
}
