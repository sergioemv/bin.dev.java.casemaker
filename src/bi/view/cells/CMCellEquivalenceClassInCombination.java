/* Developed by BUSINESS SOFTWARE INNOVATIONS.  .. Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved..*/

package  bi.view.cells;

import javax.swing.JCheckBox;

import bi.view.cells.editors.CMGridCellEditorEquivalenceClassinCombination;
import bi.view.cells.renderers.CMImageEquivalenceClassinCombinationRenderer;

import com.eliad.model.defaults.DefaultGridCellEditor;

import model.BusinessRules;
import model.Combination;
import model.EquivalenceClass;

public class CMCellEquivalenceClassInCombination extends CMCellCombination {
    public CMCellEquivalenceClassInCombination(EquivalenceClass equivalenceClass, Combination combination) {
		super(combination);
        this.equivalenceClass = equivalenceClass;
    }

    public static final DefaultGridCellEditor defaultEditor = new CMGridCellEditorEquivalenceClassinCombination(new JCheckBox());
    public static final CMImageEquivalenceClassinCombinationRenderer defaultRenderer = new CMImageEquivalenceClassinCombinationRenderer();

    public Boolean getSelected(){ return (new Boolean(combination.getEquivalenceClasses().contains(equivalenceClass)));}//selected; }

    //public void setSelected(Boolean selected){ this.selected = selected; }

    public EquivalenceClass getEquivalenceClass(){ return equivalenceClass; }

    public void setEquivalenceClass(EquivalenceClass equivalenceClass){ this.equivalenceClass = equivalenceClass; }

    public String toString() {
    	/*Combination combination = ((CMCellCombination)obj).getCombination();
        EquivalenceClass equivalenceClass = ((CMCellEquivalenceClassInCombination)obj).getEquivalenceClass();*/
        if( getSelected().booleanValue()) {
          return BusinessRules.EQUIVALENCECLASS_IN_COMBINATION;
        }
        else {
          return BusinessRules.EQUIVALENCECLASS_NOT_IN_COMBINATION;
        }
    }

    //private Boolean selected = new Boolean(false);
    private EquivalenceClass equivalenceClass;

}
