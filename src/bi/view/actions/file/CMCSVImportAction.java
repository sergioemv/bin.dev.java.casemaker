/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved. 
 */
package bi.view.actions.file;

import java.io.File;
import java.io.IOException;

import javax.swing.Action;
import javax.swing.JOptionPane;

import model.BusinessRules;

import org.apache.log4j.Logger;

import bi.controller.SessionManager;
import bi.controller.cmimport.CMCSVFileFormat;
import bi.controller.cmimport.CMCSVImportContext;
import bi.controller.cmimport.ui.CMCSVImportOptionsDialog;
import bi.view.actions.CMAbstractAction;
import bi.view.actions.CMAction;
import bi.view.actions.CMEnabledAction;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.undomanagementviews.CMCompoundEdit;
import bi.view.undomanagementviews.undoviews.CMComponentAwareEdit;
import bi.view.undomanagementviews.undoviews.CMUndoMediator;
import bi.view.utils.CMModalResult;

/**
 * @author smoreno
 * Action that imports elements from a CVS file
 */
public class CMCSVImportAction extends CMAbstractAction implements CMEnabledAction {

	private Logger logger = Logger.getLogger(this.getClass()); 
	public CMCSVImportAction() {
		super(CMMessages.getString("IMPORT_CSV_ACTION_LABEL"));
	    // Set tool tip text
	    putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("IMPORT_CSV_ACTION_TOOLTIP"));
	    // Set an icon
	    putValue(Action.SMALL_ICON, CMAction.IMPORT_CSV.getIcon());
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("IMPORT_CSV_ACTION_MNEMONIC").charAt(0));
	}
	public void execute() {
		//show an open file dialog to open the cvs file
		
		//parse the file
		CMCSVImportOptionsDialog dialog = new CMCSVImportOptionsDialog();
		//put the default format selected based on the current mainframe tab
		dialog.getJComboImportAs().setSelectedItem(getDefaultFormatForTab());
		dialog.getJButtonBrowse().doClick();
		
		File csvFile = dialog.getSelectedFile();
		if (csvFile==null)
			return;
		dialog.setVisible(true);
		if (dialog.getModalResult() == CMModalResult.OK)
		{
		//get the selected format	
		CMCSVImportContext context = new CMCSVImportContext(dialog.getFormat());
		context.setDelimiter(dialog.getDelimiter());
		//set the correct tab in tne main frame
		CMApplication.frame.getTabbedPaneTestCasesElementsViews().setSelectedIndex(getDefaultTabForFormat(dialog.getFormat()));
		try {
			//CMApplication.frame.getM_CMUndoMediator().importFromCSV(context,csvFile);
			CMCompoundEdit ce = new CMCompoundEdit();
			ce.addEdit(new CMComponentAwareEdit());
			ce.addEdit(context.importFile(csvFile));
			this.getWarningMessages().addAll(context.getWarningMessages());
			CMUndoMediator.getInstance().doEdit(ce);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(CMApplication.frame,
					CMMessages.getString("IMPORT_CSV_ACTION_IO_ERROR"),
					CMMessages.getString("IMPORT_CSV_ACTION_MESSAGE_TITLE"),
					JOptionPane.ERROR_MESSAGE);
			logger.error(e.getMessage());	
			e.printStackTrace();
		}
		catch (NullPointerException e) {
			logger.error(e.getMessage());	
			e.printStackTrace();
		} 
		//check the objects for conflict with the current ones
		//obtain an insertion point in the curren test object
		//insert the new objects
		//on error show a error dialog	
		//return the focus to the grid of elements
		}
		CMApplication.frame.getElementsGrid().requestFocus();
	}
	
	/**
	*@autor smoreno
	 * @return
	 */
	private CMCSVFileFormat getDefaultFormatForTab() {
		if (CMApplication.frame.getTabbedPaneTestCasesElementsViews().getSelectedIndex() == 0)
			return CMCSVFileFormat.ELEMENTS_EQUIVALENCE_EFFECTS_1;
		if (CMApplication.frame.getTabbedPaneTestCasesElementsViews().getSelectedIndex() == 1)
			return CMCSVFileFormat.EFFECTS;
		return null;
	}
	/**
	*@autor smoreno
	 * @return
	 */
	private int getDefaultTabForFormat(CMCSVFileFormat format) {
		if (format == CMCSVFileFormat.ELEMENTS_EQUIVALENCE_EFFECTS_1)
			return 0;
		if (format == CMCSVFileFormat.EFFECTS)
			return 1;
		return 0;
	}
	

	/* (non-Javadoc)
	 * @see bi.view.actions.CMEnabledAction#calculateEnabled()
	 */
	public boolean calculateEnabled() {
		if (CMApplication.frame.getTreeWorkspaceView().getCurrentTestObject()!=null)
		{
		return !CMApplication.frame.getTreeWorkspaceView().getCurrentTestObject().getAccessState().equalsIgnoreCase(BusinessRules.ACCESS_STATE_CHECKED_IN)
		&& CMApplication.frame.getTreeWorkspaceView().getCurrentTestObject().getUser().equalsIgnoreCase(SessionManager.getCurrentSession().getM_User());
		}
		else
			return false;
	}


	
}
