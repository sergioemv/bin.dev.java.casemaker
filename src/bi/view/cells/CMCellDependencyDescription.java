/* Developed by BUSINESS SOFTWARE INNOVATIONS.  .. Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved..*/

package  bi.view.cells;

import model.Dependency;

public class CMCellDependencyDescription extends CMCellDependency {
    public CMCellDependencyDescription(Dependency dependency) {
      super(dependency);
    }
    @Override
    public String toString() {
    	// TODO Auto-generated method stub
    	return dependency.getDescription();
    }
}
