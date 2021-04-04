/* Developed by BUSINESS SOFTWARE INNOVATIONS.  .. Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved..*/

package  bi.view.cells.headers;
import bi.view.cells.CMCellCombination;
import bi.view.lang.CMMessages;
import model.Combination;
import model.util.CMStateBean;

public class CMCellHeaderCombination extends CMCellCombination {
    public CMCellHeaderCombination(Combination combination) {
        super(combination);
    }
    public String toString() {
    	//Combination combination = ((CMCellCombination)obj).getCombination();
		String parentCombinationName = this.combination.getName();//((CMCellCombination)obj).getCombination().getName();
	    String parentCombinationState = CMStateBean.STATE_POSITIVE_LABEL;
		int    state = combination.getState();//((CMCellCombination)obj).getCombination().getState();
		if( state == CMStateBean.STATE_FAULTY) {
		 parentCombinationState = CMMessages.getString("STATE_FAULTY_LABEL");
		} else if( state == CMStateBean.STATE_IRRELEVANT) {
		 parentCombinationState = CMMessages.getString("STATE_IRRELEVANT_LABEL");
		} else if( state == CMStateBean.STATE_NEGATIVE) {
		 parentCombinationState = CMStateBean.STATE_NEGATIVE_LABEL;
		} else if ( state == CMStateBean.STATE_POSITIVE) {
		 parentCombinationState = CMStateBean.STATE_POSITIVE_LABEL;
		}

		if( combination.getCombinations().size() > 0) { // Child Combinations
					StringBuffer sb = new StringBuffer(parentCombinationName);
					sb.append(parentCombinationState);
		  return sb.toString();
		}
		else {   // No children Combinations
				StringBuffer sb = new StringBuffer(parentCombinationName);
				sb.append(parentCombinationState);
				return sb.toString();
		}

    }
}
