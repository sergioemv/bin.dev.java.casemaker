/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.view.actions.dependency;

import java.awt.event.ActionEvent;

import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.undo.UndoableEdit;

import model.BusinessRules;
import model.Combination;
import model.Dependency;

import org.apache.log4j.Logger;

import bi.controller.combination.generator.PermutationGenerator;
import bi.controller.combination.generator.PermutationGeneratorUpdater;
import bi.view.actions.CMAction;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.undomanagementviews.CMCompoundEdit;

/**
 * @author smoreno
 *
 */
public class CMDependencyGenerateCombPermutationAction extends CMGenerateCombinationAction
		implements Action {

	 protected Logger logger =Logger.getLogger(this.getClass());
	/**
	 *
	 */
	public CMDependencyGenerateCombPermutationAction() {
		super(CMMessages.getString("MENU_ITEM_GENERATE_COMBINATIONS_BY_PERMUTATION"));
	    // Set tool tip text
	    putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("BUTTON_TOOLTIP_GENERATE_COMBINATIONS"));
	    // Set an icon
	    putValue(Action.SMALL_ICON, CMAction.DEPENDENCY_GENERATE_COMB_PERMUTATION.getIcon());
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("MENU_ITEM_GENERATE_COMBINATIONS_BY_PERMUTATION_MNEMONIC").charAt(0));
	}

	public void actionPerformed(ActionEvent p_e) {
		int option = 0;
		String[] possibilities1 = {CMMessages.getString("OPTION_UPDATE_EXISTING_COMBINATIONS"), CMMessages.getString("OPTION_OVERWRITE_EXISTING_COMBINATIONS")};
	    String[] possibilities2 = {CMMessages.getString("OPTION_OVERWRITE_EXISTING_COMBINATIONS")};
	    String stringOption = null;

	    Dependency  selectedDependency = CMApplication.frame.getPanelDependencyCombiView().getM_CMCombinationViews().getSelectedDependency();

	    if( selectedDependency.getLnkCombinations().size() > 0 ) {
	        if( selectedDependency.getTypeofCombinations() == Combination.Origin.PERMUTATION) {
	 	      stringOption = (String) JOptionPane.showInputDialog(CMApplication.frame,CMMessages.getString("INFO_MESSAGE_PLEASE_SELECT_COMBINATION_PROCESS"),CMMessages.getString("LABEL_COMBINATION_GENERATION"),JOptionPane.PLAIN_MESSAGE,null, possibilities1, null);
	 	      if(stringOption != null){
	 	    	 option = derivePossibilitiesIndex(possibilities1, stringOption);
	 	      }
	 	      else
	 	    	 return;
	         }
	        else {
	 		  stringOption =  (String) JOptionPane.showInputDialog(CMApplication.frame,CMMessages.getString("INFO_MESSAGE_PLEASE_SELECT_COMBINATION_PROCESS"),CMMessages.getString("LABEL_COMBINATION_GENERATION"),JOptionPane.PLAIN_MESSAGE,null, possibilities2, null);
	 		  if (stringOption != null){
	 			 option = derivePossibilitiesIndex(possibilities2, stringOption);
	 	  		  option++;
	 		  }
	 		  else
	 			  return;
	        }
	        	overwriteOrUpdateByPermutation(option, selectedDependency);

	    }
	    else {
		  overwriteCombinationsByPermutation(selectedDependency);
	    }
	    CMApplication.frame.getPanelDependencyCombiView().getM_CMCombinationViews().requestFocus();
	}

	public UndoableEdit overwriteOrUpdateByPermutation(int p_Option,  Dependency selectedDependency){
        CMCompoundEdit ce = new CMCompoundEdit();
		int confirmation = 0;
		  if( p_Option == BusinessRules.UPDATE_COMBINATIONS)  {
            confirmation = JOptionPane.showConfirmDialog(CMApplication.frame,CMMessages.getString("INFO_MESSAGE_CHANGES_IN_COMBINATIONS"), CMMessages.getString("INFO_MESSAGE_UPDATE_COMBINATIONS"),JOptionPane.YES_NO_OPTION); //$NON-NLS-1$ //$NON-NLS-2$
            if( confirmation == JOptionPane.YES_OPTION){
		      ce.addEdit(updateCombinationsByPermutation(selectedDependency));
            }
		  }
		  else if( p_Option == BusinessRules.OVERWRITE_COMBINATIONS)  {
            confirmation = JOptionPane.showConfirmDialog(CMApplication.frame,CMMessages.getString("INFO_MESSAGE_CHANGES_IN_COMBINATIONS"), CMMessages.getString("INFO_MESSAGE_OVERWRITE_COMBINATIONS"),JOptionPane.YES_NO_OPTION); //$NON-NLS-1$ //$NON-NLS-2$
            if( confirmation == JOptionPane.YES_OPTION){
		      ce.addEdit(overwriteCombinationsByPermutation(selectedDependency));
            }
            else{

            }
	      }
		  return ce;
  }
  private UndoableEdit updateCombinationsByPermutation(Dependency p_Dependency) {
	  CMCompoundEdit ce = new CMCompoundEdit();
	  PermutationGeneratorUpdater pgu = new PermutationGeneratorUpdater(p_Dependency);
	  try{
  			pgu.generate();
		}
  catch (Exception e) {
  	logger.error("Could not generate combinations");
  	 JOptionPane.showMessageDialog(CMApplication.frame,e.getMessage());
  }


      CMApplication.frame.setWaitCursor(false);

      return ce;
  }
  private UndoableEdit overwriteCombinationsByPermutation(Dependency p_Dependency){
	CMCompoundEdit ce = new CMCompoundEdit();
    PermutationGenerator generator = new PermutationGenerator(p_Dependency);
    try{
    		generator.generate();
		}
    catch (Exception e) {
    	logger.error("Could not generate combinations");
    	 JOptionPane.showMessageDialog(CMApplication.frame,e.getMessage());
    }


    return ce;
  }


}
