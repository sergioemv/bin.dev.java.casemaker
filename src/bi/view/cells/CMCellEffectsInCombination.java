/* Developed by BUSINESS SOFTWARE INNOVATIONS.  .. Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved..*/

package  bi.view.cells;
import java.util.List;

import model.BusinessRules;
import model.Combination;
import model.Effect;

public class CMCellEffectsInCombination extends CMCellCombination {
    public CMCellEffectsInCombination(Combination combination) {
        super(combination);
    }
    public String toString() {    	
        List effects = combination.getEffects();
        StringBuffer sBuffer = new StringBuffer();
        int numEffects = effects.size();
        for(int i = 0; i < numEffects; i++) {
          Effect effect = (Effect) effects.get(i);
          sBuffer.append(effect.getName());
          if( i < numEffects - 1)
        	  sBuffer.append(BusinessRules.CAUSE_EFFECT_SEPARATOR);
        	
        }
        if (sBuffer.length()==0)
        	return "";
        else
        	return sBuffer.toString();    	
    }
}
