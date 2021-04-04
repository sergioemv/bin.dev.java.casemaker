/* Developed by BUSINESS SOFTWARE INNOVATIONS.  .. Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved..*/

package  bi.view.cells;
import model.Combination;

public class CMCellCombination extends Object {
    public CMCellCombination(Combination combination) {
        this.combination = combination;
    }

    public Combination getCombination(){ return combination; }

    public void setCombination(Combination combination){ this.combination = combination; }
   
    protected Combination combination;
}
