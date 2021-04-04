package model.formula;



import bi.controller.testdata.formula.CMFormulaCategory;
import bi.controller.testdata.formula.CMFormulaParameter;
import bi.controller.testdata.formula.CMFormulas;

import bi.view.lang.CMMessages;
import bi.view.utils.CMFormatFactory;



public class CMMaxFormula extends CMAbstractFormula {



	/* (non-Javadoc)
	 * @see model.ICMFormula#calculate()
	 * 27/10/2006
	 * svonborries
	 */
	public Object calculate()  throws Exception{
        	Number param1  = (Number) getCalculatedParameter(CMFormulaParameter.NUMBERX);
        	Number param2 = (Number) getCalculatedParameter(CMFormulaParameter.NUMBERY);
            return (Number) Math.max(param1.doubleValue(), param2.doubleValue());
	}

	/* (non-Javadoc)
	 * @see model.ICMFormula#getCategory()
	 * @return
	 * 27/10/2006
	 * svonborries
	 */
	public CMFormulaCategory getCategory() {
		return CMFormulaCategory.MATHEMATICS;
	}

	/* (non-Javadoc)
	 * @see model.ICMFormula#getFormula()
	 * @return
	 * 27/10/2006
	 * svonborries
	 */
	public CMFormulas getFormulaEnum() {
		super.setFormulaEnum(CMFormulas.MAX);
		return CMFormulas.MAX;
	}

	public String getFormattedValueResult(){
		try {
			return CMFormatFactory.formatNumber(getFormulaEnum().getDefaultPattern(), (Number)getValue());
		} catch (Exception e) {
			return CMMessages.getString("TESTDATA_MISSING_PARAMETER");
		}
	}

	@Override
	public String toString() {
		return getFormulaEnum().getName()+"("+getParameter(CMFormulaParameter.NUMBERX).toString()+"; "
		+getParameter(CMFormulaParameter.NUMBERY).toString()+")";
	}

}
