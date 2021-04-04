package bi.view.cells;

import model.Element;

import com.eliad.swing.JSmartGrid;

public class CMCellElementGuiObject extends CMCellElement{
	public CMCellElementGuiObject(JSmartGrid grid, Element element) {
	      super(grid,element);
	    }
	
	
	public String toString() {
	      return this.getElement().getGUIObjectName();
	    }
	}