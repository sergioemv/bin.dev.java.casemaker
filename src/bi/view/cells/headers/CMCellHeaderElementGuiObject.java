package bi.view.cells.headers;

import bi.view.cells.CMCellElement;
import bi.view.lang.CMMessages;

import com.eliad.swing.JSmartGrid;

public class CMCellHeaderElementGuiObject extends CMCellElement{

	
	
	    public CMCellHeaderElementGuiObject(JSmartGrid p_grid, Object p_model) {
		super(p_grid, p_model);
		this.setRenderer(defaultGridHeaderRenderer);
	}

		public String getGuiObject(){ return GuiObject; }

	    public void setGuiObject(String GuiObject){ this.GuiObject = GuiObject; }

	    private String GuiObject =CMMessages.getString("LABEL_GuiObject"); //$NON-NLS-1$


	    public String toString() {
	    	return GuiObject;
	    }
}
