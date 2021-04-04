package bi.view.cells;

import com.eliad.swing.JSmartGrid;

public class CMCellHeaderTDStructureNewColumn extends CMCellTDStructure {

	public CMCellHeaderTDStructureNewColumn(JSmartGrid p_grid, Object p_model) {
		super(p_grid, p_model);
		//set the default renderer for this 
		this.setRenderer(defaultGridHeaderRenderer);
	}

	public String getName(){ return name; }
    
    public String toString() {
    	return name;
    }

    public void setName(String name){ this.name = name; }

    private String name = "";//CMMessages.getString("LABEL_DESCRIPTION"); //$NON-NLS-1$
    
}

