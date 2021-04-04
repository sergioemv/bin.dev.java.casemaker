/**
 * 
 */
package model.formula;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import bi.controller.StructureManager;
import bi.controller.testdata.formula.CMFormulaCategory;
import bi.controller.testdata.formula.CMFormulaParameter;
import bi.controller.testdata.formula.CMFormulas;
import bi.view.lang.CMMessages;
import bi.view.utils.CMFormatFactory;


/**
 * @author svonborries
 * @since 07/08/2007
 *
 */
public class CMStringToNumFormula extends CMAbstractFormula {

	/* (non-Javadoc)
	 * @see model.formula.CMAbstractFormula#calculate()
	 */
	@Override
	public Object calculate() throws Exception {
		Object param1 = getCalculatedParameter(CMFormulaParameter.STRING);
		
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
    	if(StructureManager.getSelectedStructure().getTestObject().isDefaultSeparator()){
    		symbols.setDecimalSeparator('.');
    		symbols.setGroupingSeparator(',');
    	}
    	else{
    		symbols.setDecimalSeparator(StructureManager.getSelectedStructure().getTestObject().getDecimalSeparator().charAt(0));
    		symbols.setGroupingSeparator(StructureManager.getSelectedStructure().getTestObject().getMilesSeparator().charAt(0));
    	}
    	
    	DecimalFormat format = new DecimalFormat(getFormulaEnum().getDefaultPattern(),symbols);
    	Number value;
    	if(param1 instanceof String)	
    		value = format.parse((String)param1);
    	else
    		value = format.parse(param1.toString());
    		
		
		return value;
	}

	/* (non-Javadoc)
	 * @see model.formula.CMAbstractFormula#toString()
	 */
	@Override
	public String toString() {
		return getFormulaEnum().getName() + "(" + getParameter(CMFormulaParameter.STRING).toString() + ")"; 
	}

	/* (non-Javadoc)
	 * @see model.ICMFormula#getCategory()
	 */
	public CMFormulaCategory getCategory() {
		return getFormulaEnum().getCategory();
	}

	/* (non-Javadoc)
	 * @see model.ICMFormula#getFormattedValueResult()
	 */
	public String getFormattedValueResult() {
		try {
			return CMFormatFactory.formatNumber(getFormulaEnum().getDefaultPattern(), (Number)getValue());
		} catch (Exception e) {
			return CMMessages.getString("TESTDATA_MISSING_PARAMETER");
		}
	}

	@Override
	public CMFormulas getFormulaEnum() {
		super.setFormulaEnum(CMFormulas.STRINGTONUM);
		return CMFormulas.STRINGTONUM;
	}

}
