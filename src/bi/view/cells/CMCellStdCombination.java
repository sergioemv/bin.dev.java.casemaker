/* Developed by BUSINESS SOFTWARE INNOVATIONS.  .. Copyright (c)2003 D�az und Hilterscheid Unternehmensberatung. All rights reserved..*/

package  bi.view.cells;
import model.StdCombination;

public class CMCellStdCombination {
    public CMCellStdCombination(StdCombination stdCombination) {
      this.stdCombination = stdCombination;
    }

    public StdCombination getStdCombination(){ return stdCombination; }

    public void setStdCombination(StdCombination stdCombination){ this.stdCombination = stdCombination; }

    private StdCombination stdCombination;
}
