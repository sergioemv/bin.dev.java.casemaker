package bi.view.cells.renderers;

import java.awt.Component;
import java.awt.SystemColor;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;

import bi.view.cells.CMCellSelectAllEquivalenceClassesOfElement;
import bi.view.cells.CMCellSelectEquivalenceClass;
import bi.view.grids.CMBaseJSmartGrid;

import com.eliad.model.GridContext;
import com.eliad.model.defaults.DefaultStyleModel.BooleanRenderer;


public class CMCheckBoxRenderer extends BooleanRenderer{

	public Component getComponent(Object value, boolean isSelected, boolean hasFocus, boolean isEditable, int row, int column, GridContext context) {
		Component c = null;
		if (value instanceof CMCellSelectAllEquivalenceClassesOfElement)
			c = super.getComponent(((CMCellSelectAllEquivalenceClassesOfElement)value).getSelected(),isSelected,hasFocus,isEditable,row,column,context);

		if (value instanceof CMCellSelectEquivalenceClass){
			c = super.getComponent(((CMCellSelectEquivalenceClass)value).getSelected(),isSelected,hasFocus,isEditable,row,column,context);
			if ((((CMBaseJSmartGrid)context.getComponent()).getFirstSelectedRow() == row) && (column>0) && (((CMBaseJSmartGrid)context.getComponent()).isFocusOwner())){
				setBackground(SystemColor.inactiveCaptionText);				
			}
			else{		
				setBackground(SystemColor.WHITE);
			}
				
		}
		
		if (hasFocus){			
			//By default the checkbox does not show his focused rectangle
			((JCheckBox)this).setBorderPaintedFlat(true);
			((JCheckBox)this).setBorderPainted(true);
		}
		else
		{
			((JCheckBox)this).setBorderPaintedFlat(false);
			((JCheckBox)this).setBorderPainted(false);
		}
		
		return c;
		
	}
	
	public CMCheckBoxRenderer() {
		((JCheckBox)this).setBorder(BorderFactory.createLineBorder(SystemColor.ORANGE,2));
	}
}
