package bi.view.cells.renderers;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import model.Element;
import bi.view.cells.CMCellElementName;
import bi.view.mainframeviews.CMApplication;

import com.eliad.model.GridContext;

public class CMElementNameCellRenderer extends CMBaseGridCellRenderer {

	public CMElementNameCellRenderer() {
		super();
		
	}
	 public Component getComponent(Object value, boolean isSelected, boolean hasFocus, boolean isEditable, int row, int column, GridContext context) {
			Element el=((CMCellElementName)value).getElement();
			Component c = super.getComponent(el.getName(), isSelected, hasFocus, isEditable, row, column, context);



              if(!CMApplication.frame.getTreeWorkspaceView().getM_WorkspaceManager().isUsedElement(el.getLnkStructure(),el))
              {
					 c.setForeground(new Color(204,0,0));
			   		 c.setFont(new Font("Dialog",Font.PLAIN,12));
              }
              else
              {
            
                   c.setForeground(new Color(0,0,0));
			   		 c.setFont(new Font("Dialog",Font.PLAIN,12));
              }
			
			return c;
	 }
}
