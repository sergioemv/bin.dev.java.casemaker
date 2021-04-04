/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.view.actions.dependency;

import java.awt.event.ActionEvent;

import javax.swing.Action;
import javax.swing.JOptionPane;

import model.BusinessRules;
import model.Combination;
import model.Dependency;
import bi.controller.DependencyManager;
import bi.controller.combination.generator.AllPairsGenerator;
import bi.controller.combination.generator.AllPairsGeneratorUpdater;
import bi.view.actions.CMAction;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;

/**
 * @author smoreno
 *
 */
public class CMDependencyGenerateCombAllPairsAction extends CMGenerateCombinationAction
		implements Action {
	public CMDependencyGenerateCombAllPairsAction() {
		super(CMMessages.getString("MENU_ITEM_GENERATE_COMBINATIONS_BY_ALLPAIRS"));
	    // Set tool tip text
	    putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("BUTTON_TOOLTIP_GENERATE_COMBINATIONS_BY_ALLPAIRS"));
	    // Set an icon
	    putValue(Action.SMALL_ICON, CMAction.DEPENDENCY_GENERATE_COMB_ALLPAIRS.getIcon());
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("MENU_ITEM_GENERATE_COMBINATIONS_BY_ALLPAIRS_MNEMONIC").charAt(0));
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent p_e) {

		String[] possibilities1 = {CMMessages.getString("OPTION_UPDATE_EXISTING_COMBINATIONS"), CMMessages.getString("OPTION_OVERWRITE_EXISTING_COMBINATIONS")};
	    String[] possibilities2 = {CMMessages.getString("OPTION_OVERWRITE_EXISTING_COMBINATIONS")};
	    String stringOption = null;
	    int option = 0;
	    //check for at least one equivalence class selected per element



	    Dependency  selectedDependency = DependencyManager.getSelectedDependency();


	    if( selectedDependency.getLnkCombinations().size() > 0 ) {
	          if(( selectedDependency.getTypeofCombinations() == Combination.Origin.ALLPAIRS)
	              || (selectedDependency.getTypeofCombinations() == Combination.Origin.MANUAL) ){
	 		    stringOption = (String)JOptionPane.showInputDialog(CMApplication.frame,CMMessages.getString("INFO_MESSAGE_PLEASE_SELECT_COMBINATION_PROCESS"),CMMessages.getString("LABEL_COMBINATION_GENERATION"),JOptionPane.PLAIN_MESSAGE,null, possibilities1, null);
	 		   if (stringOption != null){
	 			  option = derivePossibilitiesIndex(possibilities1, stringOption);
			    }
			    else
			    	return;
	          }
	          else {
			    stringOption = (String)JOptionPane.showInputDialog(CMApplication.frame,CMMessages.getString("INFO_MESSAGE_PLEASE_SELECT_COMBINATION_PROCESS"),CMMessages.getString("LABEL_COMBINATION_GENERATION"),JOptionPane.PLAIN_MESSAGE,null, possibilities2, null);
			    if (stringOption != null){
			    	option = derivePossibilitiesIndex(possibilities2, stringOption);
		         	option++;
			    }
			    else
			    	return;

	          }
	          overwriteOrUpdateByAllPairs(option, selectedDependency);

	    }
	    else {
		  overwriteCombinationsByAllPairs(selectedDependency);
	    }
		CMApplication.frame.getPanelDependencyCombiView().getM_CMCombinationViews().requestFocus();
	}

	  private  void overwriteOrUpdateByAllPairs(int p_Option, Dependency selectedDependency){
	        int confirmation = 0;
			  if( p_Option == BusinessRules.UPDATE_COMBINATIONS ) {
	            confirmation = JOptionPane.showConfirmDialog(CMApplication.frame,CMMessages.getString("INFO_MESSAGE_CHANGES_IN_COMBINATIONS"), CMMessages.getString("INFO_MESSAGE_UPDATE_COMBINATIONS"),JOptionPane.YES_NO_OPTION); //$NON-NLS-1$ //$NON-NLS-2$
	            if( confirmation == JOptionPane.YES_OPTION){
			      updateCombinationsByAllPairs(selectedDependency);
	            }
			  }
			  else if( p_Option == BusinessRules.OVERWRITE_COMBINATIONS ) {
	            confirmation = JOptionPane.showConfirmDialog(CMApplication.frame,CMMessages.getString("INFO_MESSAGE_CHANGES_IN_COMBINATIONS"),CMMessages.getString("INFO_MESSAGE_OVERWRITE_COMBINATIONS"),JOptionPane.YES_NO_OPTION); //$NON-NLS-1$ //$NON-NLS-2$
	            if( confirmation == JOptionPane.YES_OPTION){
			      overwriteCombinationsByAllPairs(selectedDependency);
	            }

		      }

	  }
	  private void overwriteCombinationsByAllPairs(Dependency p_Dependency){
		  	AllPairsGenerator generator = new AllPairsGenerator(p_Dependency);
		  	 try{
		    		generator.generate();
				}
		    catch (Exception e) {
		    	 JOptionPane.showMessageDialog(CMApplication.frame,e.getMessage());
		    }
		  }

	  private void updateCombinationsByAllPairs(Dependency p_Dependency) {

		  AllPairsGeneratorUpdater generator = new AllPairsGeneratorUpdater(p_Dependency);
		  try{
	    		generator.generate();
			}
	    catch (Exception e) {
	    	 JOptionPane.showMessageDialog(CMApplication.frame,e.getMessage());
	    }
	  }

}
