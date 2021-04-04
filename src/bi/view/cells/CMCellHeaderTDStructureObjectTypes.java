package bi.view.cells;

import com.eliad.swing.JSmartGrid;

public class CMCellHeaderTDStructureObjectTypes extends CMCellTDStructure {

	public CMCellHeaderTDStructureObjectTypes(JSmartGrid p_grid, Object p_model) {
		super(p_grid, p_model);
		//set the default renderer for this 
		this.setRenderer(defaultGridHeaderRenderer);
	}

	public String getName(){ return name; }
    
    public String toString() {
    	return name;
    }

    public void setName(String name){ this.name = name; }

    private String name = "Object Types";//CMMessages.getString("LABEL_DESCRIPTION"); //$NON-NLS-1$
    

	/*public CMCellHeaderTDStructureObjectTypes(TDStructure m_testData) {
		super(m_testData);
		// TODO Auto-generated constructor stub
	}
	public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    private String name="Object Types";*/

}
