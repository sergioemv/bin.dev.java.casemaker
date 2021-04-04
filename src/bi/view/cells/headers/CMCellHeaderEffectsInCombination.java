/* Developed by BUSINESS SOFTWARE INNOVATIONS.  .. Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved..*/

package  bi.view.cells.headers;
import model.Combination;
import bi.view.cells.CMCellCombination;
import bi.view.lang.CMMessages;

public class CMCellHeaderEffectsInCombination extends CMCellCombination {
    public CMCellHeaderEffectsInCombination(Combination combination) {
        super(combination);
    }

    public String getName(){ return name; }

    public void setName(String name){ this.name = name; }

    public String toString() {    	
    	return name;
    }
    private String name = CMMessages.getString("LABEL_CAUSE_EFFECT_PREFIX"); //$NON-NLS-1$
}
