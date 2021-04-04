package bi.view.cells;

import model.ITypeData;

import com.eliad.swing.JSmartGrid;

public class CMCellTDStructureNewColumn extends CMBaseCell{
	private int column = 0;
	public CMCellTDStructureNewColumn(JSmartGrid p_grid, ITypeData typeData,int col) {
		super(p_grid, typeData);
		column = col;
	}

	public String getName()
    {
        return toString();
    }

    
    
	public String toString() {
		if (this.getModel()!=null)
		      return ((ITypeData)this.getModel()).getNewColumns().elementAt(column).toString();
			else
				return "";
    }
	
    
}
