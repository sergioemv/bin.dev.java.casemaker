package model.formula;





import bi.controller.testdata.formula.CMFormulaCategory;
import bi.controller.testdata.formula.CMFormulaParameter;
import bi.controller.testdata.formula.CMFormulas;
import bi.controller.utils.CMNumberUtils;
import bi.view.lang.CMMessages;
import bi.view.utils.CMFormatFactory;



public class CMDivFormula extends CMAbstractFormula {



	/* (non-Javadoc)
	 * @see model.ICMFormula#calculate()
	 * 27/10/2006
	 * svonborries
	 */
	public Object calculate() throws Exception {
        	Number param1 = (Number) getCalculatedParameter(CMFormulaParameter.NUMBERY);
        	Number param2 = (Number) getCalculatedParameter(CMFormulaParameter.NUMBERX);
            return (Number) CMNumberUtils.division(param1.doubleValue(), param2.doubleValue());

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
		super.setFormulaEnum(CMFormulas.DIV);
		return CMFormulas.DIV;
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
		return getFormulaEnum().getName()+"("+getParameter(CMFormulaParameter.NUMBERY).toString()+"; "
		+getParameter(CMFormulaParameter.NUMBERX).toString()+")";
	}

}
