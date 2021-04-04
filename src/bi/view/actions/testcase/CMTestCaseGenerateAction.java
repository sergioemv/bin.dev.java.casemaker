/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.view.actions.testcase;

import java.awt.Event;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import model.Dependency;
import model.Structure;
import model.util.CMDelegate;
import model.util.CMModelEventHandler;

import org.jdesktop.swingworker.SwingWorker;

import bi.controller.StructureManager;
import bi.controller.TestCaseManager;
import bi.controller.testcase.generator.CMTestCaseGenerator;
import bi.controller.testcase.generator.CMTestCaseGeneratorFactory;
import bi.view.actions.CMAction;
import bi.view.elementviews.CMElementViews;
import bi.view.icons.CMIcon;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.testcaseviews.CMTestCaseViews;
import bi.view.undomanagementviews.CMCompoundEdit;
import bi.view.undomanagementviews.undoviews.CMComponentAwareEdit;
import bi.view.undomanagementviews.undoviews.CMUndoMediator;
import bi.view.utils.CMModalResult;

/**
 * @author smoreno
 *
 */
public class CMTestCaseGenerateAction extends AbstractAction implements
		Action {
	public CMTestCaseGenerateAction() {
		super(CMMessages.getString("POPUPMENU_ITEM_GENERATE_TEST_CASES")); //$NON-NLS-1$
	    // Set tool tip text
	    putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("BUTTON_TOOLTIP_GENERATE_TEST_CASES")); //$NON-NLS-1$
	    // Set an icon
	    putValue(Action.SMALL_ICON, CMAction.TESTCASE_GENERATE.getIcon());
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("MENU_ITEM_GENERATE_TEST_CASES_MNEMONIC").charAt(0)); //$NON-NLS-1$
	    putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_G, Event.CTRL_MASK));
	 	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent p_e) {
		final CMElementViews elementViews = CMApplication.frame.getJSPlitPaneTestCaseStructureView().getM_CMElementAndTestCaseViews().getLnkCMElementViews();
		final CMTestCaseViews views = CMApplication.frame.getJSPlitPaneTestCaseStructureView().getM_CMElementAndTestCaseViews().getLnkCMTestCaseViews();

		final Structure structure = StructureManager.getSelectedStructure();
		//ask for the overwriting of the curent test cases
		if( structure.getLnkTestCases().size() > 0)
			  if (JOptionPane.showConfirmDialog(CMApplication.frame,CMMessages.getString("QUESTION_GENERATE_TEST_CASES"),CMMessages.getString("LABEL_GENERATE_TEST_CASES"),JOptionPane.YES_NO_OPTION)!=//$NON-NLS-1$ //$NON-NLS-2$
				  JOptionPane.YES_OPTION){
				return;
			}
		//create the dialog
		final CMTestCaseGenerateOptionsDialog dlg = new CMTestCaseGenerateOptionsDialog();
		dlg.setTitle(structure.getTestObject().getName()+" - "+this.getValue(Action.NAME)); //$NON-NLS-1$
		//fill the dialog with the current dependencies
		dlg.getListModelDependencies().clear();
		for (Dependency dependency : structure.getLnkDependencies())
			dlg.getListModelDependencies().addElement(dependency);
		   //fill the available generators
		if (structure.getLnkDependencies().size()>0)
			dlg.getJCheckBoxSelectAll().doClick();
		List<CMTestCaseGenerator> generators = null;
		if (structure.getTestObject().getOrigin()==null)
			generators = CMTestCaseGeneratorFactory.INSTANCE.createVisibleGenerators();
		else{
			generators = CMTestCaseGeneratorFactory.INSTANCE.createFlowGenerators();
			dlg.getJCheckBoxOneWiseGen().setSelected(false);
			dlg.getJCheckBoxOneWiseGen().setEnabled(false);
		}
		for (CMTestCaseGenerator testCaseGenerator : generators)
			dlg.getComboBoxModelTestCaseOptions().addElement(testCaseGenerator);

		dlg.show();

		if (dlg.getModalResult() == CMModalResult.OK)
		{

			//get the selected Dependencies
			final List deps = new ArrayList();
			for (Object o : Arrays.asList(dlg.getJListDependencies().getSelectionCache().toArray()))
				deps.add(dlg.getListModelDependencies().get((Integer)o));
			Collections.sort(deps);
			final CMCompoundEdit ce  = new CMCompoundEdit();
			final CMTestCaseGenerator gen = (CMTestCaseGenerator)dlg.getComboBoxModelTestCaseOptions().getSelectedItem();
			//has to choose at least one generator
			if (gen == null ) return;
			gen.setStructure(structure);
			gen.setDependencies(deps);
			// TODO Remove the grid notifications when grid listens to model
			SwingWorker worker = new SwingWorker(){
				@Override
				protected Object doInBackground() throws Exception {
					try {
						CMApplication.frame.getStatusLabel().setText("Generating Test Cases");
						CMApplication.frame.getStatusLabel().setIcon(CMIcon.LOADING.getImageIcon());
						CMApplication.frame.setWaitCursor(true);
						CMModelEventHandler.setNotifyEnabled(false);
						ce.addEdit(new CMComponentAwareEdit());
						ce.addRedoDelegateEdit(CMModelEventHandler.stopUpdatingDelegate());
						ce.addUndoDelegateEdit(new CMDelegate(){
							public void execute() {
								CMModelEventHandler.setNotifyEnabled(true);
			   				    Collections.sort(structure.getLnkTestCases());
			   				    TestCaseManager.INSTANCE.reloadTestCaseViews();
								CMApplication.frame.getCMTestCaseViews().requestFocus();
							}});

							ce.addEdit(gen.generate());
//							if the One Wise Generation is selected add the generation
								if (dlg.getJCheckBoxOneWiseGen().isSelected())
								  ce.addEdit(CMTestCaseGeneratorFactory.INSTANCE.createOneWiseGenerator(structure,deps).generate());

						ce.addEdit(new CMComponentAwareEdit());
						ce.addRedoDelegateEdit(new CMDelegate(){
							public void execute() {
								Collections.sort(structure.getLnkTestCases());
								TestCaseManager.INSTANCE.reloadTestCaseViews();
								CMModelEventHandler.setNotifyEnabled(true);
								CMApplication.frame.getCMTestCaseViews().requestFocus();
							}});

						ce.addUndoDelegateEdit(CMModelEventHandler.stopUpdatingDelegate());
						CMUndoMediator.getInstance().doEdit(ce);
//						sort the test cases
						Collections.sort(structure.getLnkTestCases());
						} catch (Exception e) {
							e.printStackTrace();
							   JOptionPane.showMessageDialog(CMApplication.frame,e.getMessage(),dlg.getTitle(),JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$
						} finally{
							CMModelEventHandler.setNotifyEnabled(true);
							CMApplication.frame.setWaitCursor(false);
						}

					return null;
				}
				@Override
				protected void done() {
					CMModelEventHandler.setNotifyEnabled(true);
					TestCaseManager.INSTANCE.reloadTestCaseViews();
					CMApplication.frame.getStatusLabel().setIcon(null);
					CMApplication.frame.getStatusLabel().setText("Ready");
					CMApplication.frame.setWaitCursor(false);
					 CMApplication.frame.getCMTestCaseViews().requestFocus();
				}
				};

			worker.execute();
		}
		 // TestCaseManager.INSTANCE.generateTestCases(StructureManager.getSelectedStructure());


	}


}
