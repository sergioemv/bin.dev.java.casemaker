/*nuevo mofificacion Itr_tdc_1*/
package  bi.view.cells;
import model.ITypeData;
import com.eliad.swing.JSmartGrid;

public class CMCellTDStructureFormat extends CMBaseCell {
	public CMCellTDStructureFormat(JSmartGrid p_grid, ITypeData typeData) {
		super(p_grid, typeData);		
	}

	public String getName()
    {
        return toString();
    }
    
	public String toString() {
		if (this.getModel()!=null)
		      return ((ITypeData)this.getModel()).getFormat();
			else
				return "";
    }
	
}
