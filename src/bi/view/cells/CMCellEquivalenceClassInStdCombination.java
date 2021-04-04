/* Developed by BUSINESS SOFTWARE INNOVATIONS.  .. Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved..*/

package  bi.view.cells;
import javax.swing.JCheckBox;

import bi.view.cells.editors.CMGridCellEditorCheckBox;
import bi.view.cells.renderers.CMImageEquivalenceClassinStdCombinationRenderer;

import com.eliad.model.defaults.DefaultGridCellEditor;

import model.StdCombination;
import model.EquivalenceClass;

public class CMCellEquivalenceClassInStdCombination extends CMCellStdCombination {
    public CMCellEquivalenceClassInStdCombination(EquivalenceClass equivalenceClass, StdCombination combination) {
		super(combination);
        this.equivalenceClass = equivalenceClass;
    }

    public static final DefaultGridCellEditor defaultEditor = new CMGridCellEditorCheckBox(new JCheckBox());
    public static final CMImageEquivalenceClassinStdCombinationRenderer defaultRenderer = new CMImageEquivalenceClassinStdCombinationRenderer();

    public Boolean getSelected(){ return new Boolean(this.getStdCombination().getEquivalenceClasses().contains(this.equivalenceClass)); }

    public void setSelected(Boolean selected){ this.selected = selected; }

    public EquivalenceClass getEquivalenceClass(){ return equivalenceClass; }

    public void setEquivalenceClass(EquivalenceClass equivalenceClass){ this.equivalenceClass = equivalenceClass; }

    private Boolean selected = new Boolean(false);
    private EquivalenceClass equivalenceClass;
}
