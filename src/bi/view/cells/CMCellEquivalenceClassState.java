/* Developed by BUSINESS SOFTWARE INNOVATIONS.  .. Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved..*/

package  bi.view.cells;
import com.eliad.swing.JSmartGrid;

  
  public class CMCellEquivalenceClassState  extends CMCellEquivalenceClass{
    //String name = null;
	
    public CMCellEquivalenceClassState(JSmartGrid p_grid, Object p_model) {
		super(p_grid, p_model);
	
	}
    
	
    public String toString() {
    	
    		return this.getEquivalenceClass().getStateName();
    	  }
    public int intValue() 
    {
    	
    		return this.getEquivalenceClass().getState();
    	
    }
    
	
  }