package bi.view.cells.renderers;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.SystemColor;

import javax.swing.JLabel;

import model.EquivalenceClass;
import model.Structure;
import bi.view.cells.CMCellEquivalenceClassName;
import bi.view.cells.CMCellEquivalenceClassState;
import bi.view.elementviews.CMGridElements;
import bi.view.grids.CMBaseJSmartGrid;
import bi.view.mainframeviews.CMApplication;

import com.eliad.model.GridContext;


public class CMEquivalenceClassCellRenderer extends CMBaseGridCellRenderer {	
    
	
	
    public CMEquivalenceClassCellRenderer() {
    
    }
    
    public Component getComponent(Object value, boolean isSelected, boolean hasFocus, boolean isEditable, int row, int column, GridContext context) {
		Component c = super.getComponent(value, isSelected, hasFocus, isEditable, row, column, context);
	
		if (((((CMBaseJSmartGrid)context.getComponent()).getFirstSelectedRow() == row) && (column>0) && (((CMBaseJSmartGrid)context.getComponent()).isFocusOwner()))
				&&(!((CMBaseJSmartGrid)context.getComponent() instanceof CMGridElements)))
		{
			setBackground(SystemColor.inactiveCaptionText);
		}
		else{	
			setBackground(SystemColor.WHITE);	
		}
		
		if (value instanceof CMCellEquivalenceClassName){
			setBackground(new Color(235, 235, 228));
			EquivalenceClass eq=((CMCellEquivalenceClassName)value).getEquivalenceClass();
		    Structure m_Structure = eq.getLnkElement().getLnkStructure();
			
			if(CMApplication.frame.getTreeWorkspaceView().getM_WorkspaceManager().isUsedEquivalenceClass(m_Structure,eq))
	        {
				 c.setForeground(new Color(0,0,0));
		   		 c.setFont(new Font("Dialog",Font.PLAIN,12));
	        }
			else
	        {
	        	c.setForeground(new Color(204,0,0));	            
		  		c.setFont(new Font("Dialog",Font.PLAIN,12));
	        }
		}	
		if (value instanceof CMCellEquivalenceClassState)
			this.setHorizontalAlignment(JLabel.CENTER);
		
		return c;
    }

	
   
}	
  