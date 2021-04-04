/**
 * 07/11/2006
 * svonborries
 */
package model.formula;

import bi.controller.testdata.formula.CMFormulaCategory;
import bi.controller.testdata.formula.CMFormulas;
import bi.view.lang.CMMessages;
import bi.view.utils.CMFormatFactory;

/**
 * @author svonborries
 *
 */
public class CMCFormula extends CMAbstractFormula {


	/* (non-Javadoc)
	 * @see model.formula.CMAbstractFormula#toString()
	 * @return
	 * 07/11/2006
	 * svonborries
	 */
	@Override
	public String toString() {
		return "2.99792458E-10";
	}

	public Object calculate()  throws Exception{
		return (Number) Double.parseDouble("2.99792458E-10");
	}

	public CMFormulaCategory getCategory() {
		return CMFormulaCategory.CONSTANTS;
	}

	public String getFormattedValueResult() {
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
		super.setFormulaEnum(CMFormulas.C);
		return CMFormulas.C;
	}

}
