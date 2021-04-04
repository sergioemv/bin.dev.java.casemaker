/* Developed by BUSINESS SOFTWARE INNOVATIONS.  .. Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved..*/

package  bi.view.cells.headers;
import model.Dependency;
import bi.view.cells.CMCellDependency;
import bi.view.lang.CMMessages;

public class CMCellHeaderDependencyName extends CMCellDependency {
    public CMCellHeaderDependencyName(Dependency dependency) {
        super(dependency);
    }
      
    public String getName(){ return name; }

    public void setName(String name){ this.name = name; }

    public String toString() {
    	return name;
    }

    private String name = CMMessages.getString("LABEL_DEPENDENCY"); //$NON-NLS-1$
}
