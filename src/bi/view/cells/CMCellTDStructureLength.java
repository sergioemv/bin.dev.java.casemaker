/* Generated by Together */

package  bi.view.cells;
import model.ITypeData;

import com.eliad.swing.JSmartGrid;

public class CMCellTDStructureLength extends CMBaseCell {
	public CMCellTDStructureLength(JSmartGrid p_grid, ITypeData typeData) {
		super(p_grid, typeData);		
	}

	public String getName()
    {
        return toString();
    }

    
    
	public String toString() {
		if (this.getModel()!=null)
		      return ((ITypeData)this.getModel()).getLength();
			else
				return "";
    }
	
	

}

