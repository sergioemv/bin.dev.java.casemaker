package model.formula;

import model.BusinessRules;
import bi.controller.testdata.formula.CMFormulaCategory;
import bi.controller.testdata.formula.CMFormulas;
import bi.view.lang.CMMessages;
import bi.view.utils.CMFormatFactory;

public class CMEFormula extends CMAbstractFormula {


	
	@Override
	public String toString() {
		return BusinessRules.FORMULAS_CATEGORY_CONSTANTS_E();
	}

	public Object calculate()  throws Exception{
        return (Number) Math.E;
	}

	public CMFormulaCategory getCategory() {
		return CMFormulaCategory.CONSTANTS;
	}

	public String getFormattedValueResult(){
		try {
			return CMFormatFactory.formatNumber(getFormulaEnum().getDefaultPattern(), (Number) getValue());
		} catch (Exception e) {
			return CMMessages.getString("TESTDATA_MISSING_PARAMETER");
		}
	}
	

	/* (non-Javadoc)
	 * @see model.ICMFormula#getFormula()
	 * @return
	 * 27/10/2006
	 * svonborries
	 */
	public CMFormulas getFormulaEnum() {
		super.setFormulaEnum(CMFormulas.E);
		return CMFormulas.E;
	}

}
