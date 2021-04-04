/**
 * 
 */
package model.formula;

import bi.controller.StructureManager;
import bi.controller.testdata.formula.CMFormulaCategory;
import bi.controller.testdata.formula.CMFormulaParameter;
import bi.controller.testdata.formula.CMFormulas;
import bi.view.lang.CMMessages;
import bi.view.utils.CMFormatFactory;

/**
 * @author svonborries
 *
 */
public class CMNumberToStringFormula extends CMAbstractFormula {

	/* (non-Javadoc)
	 * @see model.formula.CMAbstractFormula#calculate()
	 */
	@Override
	public Object calculate() throws Exception {
		Object param1 = getCalculatedParameter(CMFormulaParameter.NUMBER);
		
		if(param1 instanceof Number){
			String pattern;
			if(StructureManager.getSelectedStructure().getTestObject().isDefaultSeparator()){
				pattern = "###,###.##########";
			}
			else{
				pattern = "###.###,##########";
			}
			return CMFormatFactory.formatNumber(pattern, (Number) param1);
		}
		else
			throw new Exception();
	}

	/* (non-Javadoc)
	 * @see model.formula.CMAbstractFormula#toString()
	 */
	@Override
	public String toString() {
		return getFormulaEnum().getName() + "(" + getParameter(CMFormulaParameter.NUMBER).toString() + ")";
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
			return getValue().toString();
		} catch (Exception e) {
			return CMMessages.getString("TESTDATA_MISSING_PARAMETER");
		}
	}

	@Override
	public CMFormulas getFormulaEnum() {
		super.setFormulaEnum(CMFormulas.NUMTOSTRING);
		return CMFormulas.NUMTOSTRING;
	}
	
	

}
