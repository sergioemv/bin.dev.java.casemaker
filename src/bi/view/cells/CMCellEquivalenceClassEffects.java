/* Developed by BUSINESS SOFTWARE INNOVATIONS.  .. Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved..*/

package  bi.view.cells;
import java.util.List;

import model.BusinessRules;
import model.Effect;
import bi.view.cells.renderers.CMEquivalenceClassEffectCellRenderer;

import com.eliad.swing.JSmartGrid;

public class CMCellEquivalenceClassEffects extends CMCellEquivalenceClass {
 
	public static final CMEquivalenceClassEffectCellRenderer defaultRenderer = new CMEquivalenceClassEffectCellRenderer(); 
	public CMCellEquivalenceClassEffects(JSmartGrid p_grid, Object p_model) {
		super(p_grid, p_model);
		this.setRenderer(defaultRenderer);
	}
   public String toString() {
	   List effects = getEquivalenceClass().getEffects();
		StringBuffer sBuffer = new StringBuffer();
		int numEffects = effects.size();
		for(int i = 0; i < numEffects; i++) {
		  Effect effect = (Effect) effects.get(i);
		  sBuffer.append(effect.getName());
		  if( i < numEffects - 1) { sBuffer.append(BusinessRules.CAUSE_EFFECT_SEPARATOR);}
		}
		return sBuffer.toString();

}
}
