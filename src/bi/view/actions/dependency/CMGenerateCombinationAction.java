/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.view.actions.dependency;

import java.awt.event.ActionEvent;
import java.util.Collections;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import model.Dependency;
import model.Element;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;

/**
 * @author smoreno
 *
 */
public abstract class CMGenerateCombinationAction extends AbstractAction {

	/**
	 * @param p_string
	 */
	public CMGenerateCombinationAction(String p_string) {
		super(p_string);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent p_e) {
		// TODO Auto-generated method stub

	}
	  /**
	*@autor smoreno
	 * @param p_selectedDependency
	 * @return
	 */
	protected boolean hasAtLeastOneEqClassSelected(Dependency p_selectedDependency) {
		for (Element element : p_selectedDependency.getLnkElements())
			if (Collections.disjoint(element.getEquivalenceClasses(),p_selectedDependency.getLnkEquivalenceClasses()))
				return false;

		return true;
	}
	protected int derivePossibilitiesIndex(String[] p_Possibilities, String p_Selection){
		  	String option = null;
		    for( int i = 0; i < p_Possibilities.length; i++){
		      option = p_Possibilities[i];
		      if( option.equals(p_Selection)){
		        return i;
		      }
		    }
		    return 0;
		  }

	protected void showErrorMessage(String message) {

	}
	  protected  void showMessage(int p_Index) {
		    if( p_Index == 1) {
			  JOptionPane.showMessageDialog(CMApplication.frame,CMMessages.getString("INFO_GENERATION_OF_COMBINATIONS_NOT_POSIBLE_1"));
		    }
		    else if( p_Index == 2) {
			  JOptionPane.showMessageDialog(CMApplication.frame,CMMessages.getString("INFO_GENERATION_OF_COMBINATIONS_NOT_POSIBLE_2"));
		    }
		    else if( p_Index == 3) {
		      JOptionPane.showMessageDialog(CMApplication.frame,CMMessages.getString("INFO_GENERATION_OF_COMBINATIONS_NOT_POSIBLE_3"));
		    }
		  }
}
