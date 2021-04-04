/* Developed by BUSINESS SOFTWARE INNOVATIONS.  .. Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved..*/

package  bi.view.cells.headers;
import bi.view.cells.CMCellStdCombination;
import model.StdCombination;

public class CMCellHeaderStdCombination extends CMCellStdCombination {
	private StdCombination m_Combination=null;
    public CMCellHeaderStdCombination(StdCombination combination) {
        super(combination);
        m_Combination = combination;        
    }
    public String toString() {    	
    	return this.m_Combination.getName();
    }
}
