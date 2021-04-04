/* Developed by BUSINESS SOFTWARE INNOVATIONS.  .. Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved..*/

package  bi.view.cells;

import model.Dependency;

public class CMCellDependencyName extends CMCellDependency {
    public CMCellDependencyName(Dependency dependency) {
      super(dependency);
    }
    public String toString() {
    	return this.getDependency().getName();
    }

}
