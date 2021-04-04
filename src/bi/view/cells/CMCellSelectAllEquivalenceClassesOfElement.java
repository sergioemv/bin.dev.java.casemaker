/* Developed by BUSINESS SOFTWARE INNOVATIONS.  .. Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved..*/

package  bi.view.cells;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import model.Dependency;
import model.Element;
import model.EquivalenceClass;

import com.eliad.swing.JSmartGrid;
public class CMCellSelectAllEquivalenceClassesOfElement extends CMCellElement {
    public CMCellSelectAllEquivalenceClassesOfElement( Element element, Dependency dependency,JSmartGrid p_grid) {
      super(p_grid,element);
      this.dependency = dependency;

    }

    //public static final CMSelectAllEquivalenceClassesCellEditor defaultEditor = new CMSelectAllEquivalenceClassesCellEditor();  
    //public static final CMCheckBoxRenderer defaultRenderer = new CMCheckBoxRenderer(); 
    
    public Dependency getDependency(){ return dependency; }

    public void setDependency(Dependency dependency){ this.dependency = dependency; }

    public Boolean getSelected(){ 
    	Collection<EquivalenceClass> equivalenceClassPositiveList = new Vector<EquivalenceClass>(0);
    	//If all the positive equivalence classes of the element are in the dependency
    	for (Iterator i =this.getElement().getEquivalenceClasses().iterator();i.hasNext();)
    	{
    		EquivalenceClass equivalenceClass = (EquivalenceClass) i.next();
    		if (equivalenceClass.getStateName().equalsIgnoreCase("+")) //$NON-NLS-1$
    			equivalenceClassPositiveList.add(equivalenceClass);
    	}
	return new Boolean(this.dependency.getLnkEquivalenceClasses().containsAll(equivalenceClassPositiveList)); }

    private Dependency dependency;
   
}
