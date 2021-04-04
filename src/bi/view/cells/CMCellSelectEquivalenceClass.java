/* Developed by BUSINESS SOFTWARE INNOVATIONS.  .. Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved..*/

package  bi.view.cells;

import model.Dependency;
import model.EquivalenceClass;
import bi.view.cells.editors.CMGridCellEditorSelectEquivalenceClass;
import bi.view.cells.renderers.CMCheckBoxRenderer;

import com.eliad.swing.JSmartGrid;
public class CMCellSelectEquivalenceClass extends CMCellEquivalenceClass {
    public CMCellSelectEquivalenceClass(JSmartGrid p_grid, EquivalenceClass equivalenceClass, Dependency dependency) {
      super(p_grid,equivalenceClass);
      this.dependency = dependency;
      this.setEditor(defaultEditor);
    }
   
    /*public String toString() {
    	return this.getSelected();
    }*/

    public static final  CMGridCellEditorSelectEquivalenceClass defaultEditor = new CMGridCellEditorSelectEquivalenceClass();  
    public static final CMCheckBoxRenderer defaultRenderer = new CMCheckBoxRenderer(); 
    
    public Dependency getDependency(){ return dependency; }

    public void setDependency(Dependency dependency){ this.dependency = dependency; }

    public Boolean getSelected(){ return new Boolean(this.dependency.getLnkEquivalenceClasses().contains(getEquivalenceClass())); }

    private Dependency dependency;



}
