package bi.view.cells;

import bi.view.mainframeviews.CMApplication;

import com.eliad.swing.JSmartGrid;
import model.ITypeData;

public class CMCellTDStructureObjectTypes extends CMBaseCell{

	public CMCellTDStructureObjectTypes(JSmartGrid grid,ITypeData typeData)
    {
		super(grid,typeData);		
    }
	
	public String toString() {
		if (this.getModel()!=null)
	      return ((ITypeData)this.getModel()).getToolVendorOT(CMApplication.frame.getTreeWorkspaceView().getCurrentTestObject());
		else
			return "";
	}
	
    public String getStateNameOT(){ return toString(); }
      
    public int getStateOT(){ return ((ITypeData)this.getModel()).getStateOT(); }    
    
}
