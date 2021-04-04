/* Developed by BUSINESS SOFTWARE INNOVATIONS.  .. Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved..*/

package  bi.view.cells;

import javax.swing.JTextField;

import bi.view.cells.headers.CMCellHeaderDependencyRenderer;

import com.eliad.model.defaults.DefaultGridCellEditor;
import com.eliad.model.defaults.DefaultGridCellRenderer;

import model.Dependency;

public class CMCellDependency extends Object {
    public CMCellDependency(Dependency dependency) {
      this.dependency = dependency;
    }

    public static final DefaultGridCellEditor defaultEditor = new DefaultGridCellEditor(new JTextField());
    public static final DefaultGridCellRenderer defaultGridHeaderRenderer = new CMCellHeaderDependencyRenderer();

    public Dependency getDependency(){ return dependency; }

    public void setDependency(Dependency dependency){ this.dependency = dependency; }

    protected Dependency dependency;
}
