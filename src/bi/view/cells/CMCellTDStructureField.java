
package  bi.view.cells;
import model.ITypeData;

import com.eliad.swing.JSmartGrid;


public class CMCellTDStructureField extends CMBaseCell {
	public CMCellTDStructureField(JSmartGrid p_grid, ITypeData typeData) {
		super(p_grid, typeData);		
	}

	public String getName()
    {
        return toString();
    }
    
	public String toString() {
		if (this.getModel()!=null)
		      return ((ITypeData)this.getModel()).getField();
			else
				return "";
    }
	
	
}
