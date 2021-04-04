package bi.controller.combination.generator;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.undo.UndoableEdit;

import model.ApplicationSetting;
import model.CMField;
import model.Combination;
import model.Dependency;
import model.Element;
import model.edit.CMModelEditFactory;
import model.util.CMModelEvent;
import model.util.CMModelEventHandler;

import org.apache.log4j.Logger;
import org.jdesktop.swingworker.SwingWorker;

import bi.controller.SessionManager;
import bi.view.icons.CMIcon;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.undomanagementviews.CMCompoundEdit;
import bi.view.undomanagementviews.undoviews.CMUndoMediator;

public abstract class AbstractCombinationGenerator implements CombinationGenerator {

	protected Dependency dependency;
	private int maxNumberofCombinations = -1;
	protected Logger logger =Logger.getLogger(this.getClass());
	public void generate() throws Exception {

		SwingWorker worker = new SwingWorker(){

			@Override
			protected Object doInBackground() throws Exception {
				CMCompoundEdit ce = new CMCompoundEdit();
//				ce.addUndoDelegateEdit(new CMDelegate(){
//					public void execute() {
//						CMModelEventHandler.setNotifyEnabled(true);
//						updateCombinationViews();
//					}});
				CMApplication.frame.setWaitCursor(true);
				CMApplication.frame.setEnabled(false);
//				ce.addRedoDelegateEdit(CMModelEventHandler.stopUpdatingDelegate());
				CMModelEventHandler.setNotifyEnabled(false);
				CMApplication.frame.getStatusLabel().setText("Generating Combinations ...");
				CMApplication.frame.getStatusLabel().setIcon(CMIcon.LOADING.getImageIcon());
				try{
					checkEquivalenceClassSelected();
					ce.addEdit(doGenerate());
				}catch (Exception e) {
					logger.error("Could not generate combinations");
			    	 JOptionPane.showMessageDialog(CMApplication.frame,e.getMessage());
				}finally{
					CMModelEventHandler.setNotifyEnabled(true);
					CMApplication.frame.setWaitCursor(false);
					CMApplication.frame.getStatusLabel().setIcon(null);
					CMApplication.frame.getStatusLabel().setText("Ready");
				}
//				ce.addUndoDelegateEdit(CMModelEventHandler.stopUpdatingDelegate());
//				ce.addRedoDelegateEdit(new CMDelegate(){
//					public void execute() {
//						CMModelEventHandler.setNotifyEnabled(true);
//						updateCombinationViews();
//					}});

				CMUndoMediator.getInstance().doMassiveEdit(ce);
				return null;
			}
			@Override
			protected void done() {
				CMApplication.frame.setEnabled(true);

				CMModelEventHandler.setNotifyEnabled(true);
				CMApplication.frame.setWaitCursor(false);
				CMApplication.frame.getStatusLabel().setText("Ready");
				updateCombinationViews();
				super.done();
			}
			};
			worker.execute();
	}

	private void checkEquivalenceClassSelected() throws Exception {
		for (Element element : dependency.getLnkElements())
			if (Collections.disjoint(element.getEquivalenceClasses(),dependency.getLnkEquivalenceClasses()))
				 throw new Exception(getErrorMessage(2));
	}

	private void updateCombinationViews() {
		CMModelEvent evt = new CMModelEvent(dependency,CMField.COMBINATIONS);
		CMApplication.frame.getPanelDependencyCombiView().getM_CMCombinationViews().handleCMModelChange(evt);
	}

	protected abstract UndoableEdit doGenerate() throws Exception;
	public AbstractCombinationGenerator(Dependency dependency) {
		this.dependency = dependency;
	}
	public void setDependency(Dependency dependency) {
		this.dependency = dependency;

	}

	protected int getMaxNumberofCombinations(){
		if (maxNumberofCombinations == -1){
			 ApplicationSetting appSetting = SessionManager.INSTANCE.getApplicationSettingManager().getApplicationSetting();
			 maxNumberofCombinations = appSetting.getM_MaxNumOfCombinations();
		}
		return maxNumberofCombinations;
	}
	protected String getErrorMessage(int val){

		switch (val) {
		case 1:
			return CMMessages.getString("INFO_GENERATION_OF_COMBINATIONS_NOT_POSIBLE_1");
		case 2:
			return CMMessages.getString("INFO_GENERATION_OF_COMBINATIONS_NOT_POSIBLE_2");
		case 3:
			return CMMessages.getString("INFO_GENERATION_OF_COMBINATIONS_NOT_POSIBLE_3");
		default:
			return "Unknow error generating combinations";
		}

	}
	public UndoableEdit reAssignOrderedIDsToCombinations(List p_Combinations){
		   CMCompoundEdit ce = new CMCompoundEdit();
	       Combination combination = null;
	       int i = 0;
	       for( Iterator iterator = p_Combinations.iterator(); iterator.hasNext(); ) {
	         combination = (Combination) iterator.next();
	         i++;
	         ce.addEdit(CMModelEditFactory.INSTANCE.createChangeIdModelEdit(combination, i));
			 combination.setId(i);
	        }
	       return ce;
	   }
}
