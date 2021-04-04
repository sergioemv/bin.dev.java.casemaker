/**
 * 07/11/2006
 * svonborries
 */
package model.formula;

import model.BusinessRules;
import bi.controller.testdata.formula.CMFormulaCategory;
import bi.controller.testdata.formula.CMFormulas;
import bi.view.lang.CMMessages;
import bi.view.utils.CMFormatFactory;

/**
 * @author svonborries
 *
 */
public class CMPIFormula extends CMAbstractFormula {

	

	/* (non-Javadoc)
	 * @see model.formula.CMAbstractFormula#toString()
	 * @return
	 * 07/11/2006
	 * svonborries
	 */
	@Override
	public String toString() {
		return BusinessRules.FORMULAS_CATEGORY_CONSTANTS_PI();
	}

	/* (non-Javadoc)
	 * @see model.ICMFormula#calculate()
	 * 07/11/2006
	 * svonborries
	 */
	public Object calculate()  throws Exception{
        return (Number) Math.PI;
	}

	/* (non-Javadoc)
	 * @see model.ICMFormula#getCategory()
	 * @return
	 * 07/11/2006
	 * svonborries
	 */
	public CMFormulaCategory getCategory() {
		return CMFormulaCategory.CONSTANTS;
	}

	/* (non-Javadoc)
	 * @see model.ICMFormula#getValueResult()
	 * @return
	 * 07/11/2006
	 * svonborries
	 */
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
		super.setFormulaEnum(CMFormulas.PI);
		return CMFormulas.PI;
	}

}
