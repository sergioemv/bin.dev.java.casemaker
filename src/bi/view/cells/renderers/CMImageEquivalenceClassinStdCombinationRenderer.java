package bi.view.cells.renderers;

import java.awt.Component;
import java.awt.SystemColor;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;

import model.EquivalenceClass;
import model.StdCombination;
import model.util.CMStateBean;
import bi.view.cells.CMCellEquivalenceClassInStdCombination;
import bi.view.cells.CMCellStdCombination;
import bi.view.elementviews.CMElementViews;
import bi.view.grids.CMBaseJSmartGrid;
import bi.view.icons.CMIcon;
import bi.view.stdcombinationviews.CMDescriptionStdCombinationViews;
import bi.view.stdcombinationviews.CMStdCombinationViews;

import com.eliad.model.GridContext;
import com.eliad.model.defaults.DefaultStyleModel.BooleanRenderer;


public class CMImageEquivalenceClassinStdCombinationRenderer extends BooleanRenderer{

	public Component getComponent(Object value, boolean isSelected, boolean hasFocus, boolean isEditable, int row, int column, GridContext context) {
		CMStdCombinationViews stdCombGrid=(CMStdCombinationViews)context.getComponent();
		Component c = super.getComponent(((CMCellEquivalenceClassInStdCombination)value).getSelected(),isSelected,hasFocus,isEditable,row,column,context);
		if (((stdCombGrid.getFirstSelectedColumn() == column)&&(((CMBaseJSmartGrid)context.getComponent()).isFocusOwner()))
			&&(stdCombGrid.getM_CMElementAndStdCombinationViews().getM_CMStdCombinationStructureView().getM_CMDescriptionStdCombinationViews().getFirstSelectedRow()==column))
			setBackground(SystemColor.inactiveCaptionText);
		else{
			CMDescriptionStdCombinationViews descriptionGridView = ((CMStdCombinationViews)context.getComponent()).getM_CMElementAndStdCombinationViews().getM_CMStdCombinationStructureView().getM_CMDescriptionStdCombinationViews();

			  if ((descriptionGridView.getFirstSelectedRow()==column) && (descriptionGridView.isFocusOwner()))
			  {
				  setBackground(SystemColor.inactiveCaptionText);
			  }else
				  setBackground(SystemColor.WHITE);

		}

		CMElementViews ElementsViewGrid = ((CMStdCombinationViews)context.getComponent()).getLnkCMElementViews();
		if (ElementsViewGrid.isFocusOwner())
		{
		  if (ElementsViewGrid.getFirstSelectedRow()==row)
		  {
			  setBackground(SystemColor.inactiveCaptionText);
		  }else
			  setBackground(SystemColor.WHITE);
		}


		if( value instanceof CMCellEquivalenceClassInStdCombination) {
			StdCombination combination = ((CMCellStdCombination)value).getStdCombination();
            EquivalenceClass equivalenceClass = ((CMCellEquivalenceClassInStdCombination)value).getEquivalenceClass();
            if (combination.getEquivalenceClasses().contains(equivalenceClass)) {
                if (equivalenceClass.getState() == CMStateBean.STATE_FAULTY) {
                	setIcon(CMIcon.EQUIVALENCECLASS_FAULTY_IN_TESTCASE.getImageIcon());
                }
                else {
                	setIcon(CMIcon.EQUIVALENCECLASS_IN_TESTCASE.getImageIcon());
                }
            }
            else {
            	setIcon(new ImageIcon(""));
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

	public CMImageEquivalenceClassinStdCombinationRenderer() {
		((JCheckBox)this).setBorder(BorderFactory.createLineBorder(SystemColor.ORANGE,2));

	}

}
