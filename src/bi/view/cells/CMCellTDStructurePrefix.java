/* Generated by Together */

package  bi.view.cells;
import com.eliad.swing.JSmartGrid;
import model.ITypeData;


public class CMCellTDStructurePrefix extends CMBaseCell {
	public CMCellTDStructurePrefix(JSmartGrid p_grid, ITypeData typeData) {
		super(p_grid, typeData);		
	}

	public String getName()
    {
        return toString();
    }
    
	public String toString() {
		if (this.getModel()!=null)
		      return ((ITypeData)this.getModel()).getPrefix();
			else
				return "";
    }	
	
}
