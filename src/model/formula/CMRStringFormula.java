/**
 * 
 */
package model.formula;

import bi.controller.testdata.formula.CMFormulaCategory;
import bi.controller.testdata.formula.CMFormulaParameter;
import bi.controller.testdata.formula.CMFormulas;
import bi.view.lang.CMMessages;

/**
 * @author svonborries
 *
 */
public class CMRStringFormula extends CMAbstractFormula {

	/* (non-Javadoc)
	 * @see model.formula.CMAbstractFormula#calculate()
	 */
	@Override
	public Object calculate() throws Exception {
		String param1 = (String) getCalculatedParameter(CMFormulaParameter.STRING);
		Number param2 = (Number) getCalculatedParameter(CMFormulaParameter.POSITION);
		String result = param1.substring(0, param2.intValue());
		
		return result;
	}

	/* (non-Javadoc)
	 * @see model.formula.CMAbstractFormula#toString()
	 */
	@Override
	public String toString() {
		return getFormulaEnum().getName()+"("+getParameter(CMFormulaParameter.STRING).toString()+"; "
		+getParameter(CMFormulaParameter.POSITION).toString()+")";
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
			return (String)getValue();
		} catch (Exception e) {
			return CMMessages.getString("TESTDATA_MISSING_PARAMETER");
		}
	}

	@Override
	public CMFormulas getFormulaEnum() {
		super.setFormulaEnum(CMFormulas.RSTRING);
		return CMFormulas.RSTRING;
	}
	
	

}
