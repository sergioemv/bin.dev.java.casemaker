package bi.view.cells.renderers;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import model.Combination;
import model.Element;
import model.EquivalenceClass;
import model.StdCombination;
import model.TestCase;
import model.util.CMStateBean;
import bi.view.cells.CMCellEquivalenceClassInTestCase;
import bi.view.icons.CMIcon;
import bi.view.lang.CMMessages;

import com.eliad.model.GridContext;

public class CMImageEquivalenceClassinTestCaseRenderer extends CMTestCaseCellRenderer{
	
	public Component getComponent(Object value, boolean isSelected, boolean hasFocus, boolean isEditable, int row, int column, GridContext context) {
		Component c = super.getComponent(value, isSelected, hasFocus, isEditable, row, column, context);
		
		if( value instanceof CMCellEquivalenceClassInTestCase) {// Set the Icons
			EquivalenceClass equivalenceClass = ((CMCellEquivalenceClassInTestCase)value).getEquivalenceClass();
			TestCase testCase = (TestCase) ((CMCellEquivalenceClassInTestCase)value).getModel();
			if(getText().startsWith("EC")){
				setText("");
				if( equivalenceClass.getState() == CMStateBean.STATE_FAULTY) {
					setIcon(CMIcon.EQUIVALENCECLASS_FAULTY_IN_TESTCASE.getImageIcon());
				}
				else {
					setIcon(CMIcon.EQUIVALENCECLASS_IN_TESTCASE.getImageIcon());
	        	}		
			}else
				setIcon(null);
			
			if(getText().startsWith(CMMessages.getString("STD_COMBINATION_PREFIX"))) {
				  Element el = equivalenceClass.getLnkElement();
				  for (EquivalenceClass equivalenceClass2 : testCase.getEquivalenceClasses())
					  if (equivalenceClass!=equivalenceClass2 && 
							  el.getEquivalenceClasses().contains(equivalenceClass2)){
						  setText("");
						  return c;
					  }
				 
				  for (Combination combi : testCase.getCombinations())
					  if (!(combi instanceof StdCombination)){
						  for (EquivalenceClass eq : combi.getEquivalenceClassesRecursiv())
						  { 
							  if (eq.getLnkElement().equals(el)){
								  setText("");
								  return c;
							  }
						  }
					  }
			      c.setForeground(new Color(255, 112,43));
			      String currentFont = c.getFont().getFontName();
			      int currentFontSize = c.getFont().getSize();
			      c.setFont(new Font(currentFont,Font.BOLD,currentFontSize));
			    }else
			    	c.setForeground(Color.black);
		}
			
	
		return c;
		
	}
}
