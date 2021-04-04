package bi.view.cells.renderers;

import java.awt.Component;
import java.awt.SystemColor;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;

import model.Combination;
import model.EquivalenceClass;
import bi.view.cells.CMCellCombination;
import bi.view.cells.CMCellEquivalenceClassInCombination;
import bi.view.dependencycombinationviews.CMCombinationViews;
import bi.view.dependencycombinationviews.CMDependencyElementViews;
import bi.view.grids.CMBaseJSmartGrid;
import bi.view.icons.CMIcon;

import com.eliad.model.GridContext;
import com.eliad.model.defaults.DefaultStyleModel.BooleanRenderer;


public class CMImageEquivalenceClassinCombinationRenderer extends BooleanRenderer{
	

	public Component getComponent(Object value, boolean isSelected, boolean hasFocus, boolean isEditable, int row, int column, GridContext context) {
		
		
		
		Component c = super.getComponent(((CMCellEquivalenceClassInCombination)value).getSelected(),isSelected,hasFocus,isEditable,row,column,context);
		CMCombinationViews combinationgrid =(CMCombinationViews) context.getComponent();
		//	if ((((CMCombinationViews)context.getComponent()).getFirstSelectedColumn() == column)&&(((CMBaseJSmartGrid)context.getComponent()).isFocusOwner()))
			if ((combinationgrid.getFirstSelectedColumn() == column)&&(((CMBaseJSmartGrid)context.getComponent()).isFocusOwner()))
				setBackground(SystemColor.inactiveCaptionText);
			else{
				setBackground(SystemColor.WHITE);	
				CMDependencyElementViews dependencyElementGrid = ((CMCombinationViews)context.getComponent()).getCmPanelDependencies().getM_CMDependencyElementView();
				if (dependencyElementGrid.getFirstSelectedRow()==row)
				{
					if (dependencyElementGrid.isFocusOwner())
					  {
						  setBackground(SystemColor.inactiveCaptionText);							  
					  }
					  else{						  
						if (dependencyElementGrid.getCurrentCellEditor()!=null)
							setBackground(SystemColor.inactiveCaptionText);
						else
							setBackground(SystemColor.WHITE);	
					  }				  
				}
				else{					
						setBackground(SystemColor.WHITE);	
				}
					
		}					
		if(value instanceof CMCellEquivalenceClassInCombination) {
			//TODO this is a task of the cell
	        Combination combination = ((CMCellCombination)value).getCombination();
	        EquivalenceClass equivalenceClass = ((CMCellEquivalenceClassInCombination)value).getEquivalenceClass();
	        if(combination != null && equivalenceClass != null) 
				if( combination.contains(equivalenceClass)) {
					if (combination.getOriginType()==Combination.Origin.MANUAL)
						setIcon(CMIcon.EQUIVALENCECLASS_IN_MANUAL_COMBINATION.getImageIcon());
					if (combination.getOriginType()==Combination.Origin.PERMUTATION)
						setIcon(CMIcon.EQUIVALENCECLASS_IN_COMBINATION.getImageIcon());
					if (combination.getOriginType()==Combination.Origin.ALLPAIRS)
						setIcon(CMIcon.EQUIVALENCECLASS_IN_ALLPAIRS_COMBINATION.getImageIcon());
				}
	            else
	        	  setIcon(new ImageIcon(""));  
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
	public CMImageEquivalenceClassinCombinationRenderer() {
		((JCheckBox)this).setBorder(BorderFactory.createLineBorder(SystemColor.ORANGE,2));
//		setSelectedIcon(new ImageIcon(CMFrameView.class.getResource(BusinessRules.IMAGE_EQUIVALENCECLASS_IN_COMBINATION)));
//		setIcon(new ImageIcon(CMFrameView.class.getResource(BusinessRules.EQUIVALENCECLASS_NOT_IN_COMBINATION)));
////		
	}
}