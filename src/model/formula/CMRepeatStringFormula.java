package model.formula;



import bi.controller.testdata.formula.CMFormulaCategory;
import bi.controller.testdata.formula.CMFormulaParameter;
import bi.controller.testdata.formula.CMFormulas;
import bi.controller.utils.CMCharUtils;
import bi.view.lang.CMMessages;


public class CMRepeatStringFormula extends CMAbstractFormula {



	
	public Object calculate()  throws Exception{
        	String param1 = (String) getCalculatedParameter(CMFormulaParameter.STRING);
        	String param2 = (String) getCalculatedParameter(CMFormulaParameter.COMPARESTRING);
            return (Number) Integer.parseInt(CMCharUtils.repeatString(param1, param2));
	}

	public CMFormulaCategory getCategory() {
		return CMFormulaCategory.TEXT;
	}

	public CMFormulas getFormulaEnum() {
		super.setFormulaEnum(CMFormulas.REPEAT_STRING);
		return CMFormulas.REPEAT_STRING;
	}

	public String getFormattedValueResult(){
		try {
			return ((Number)getValue()).toString();
		} catch (Exception e) {
			return CMMessages.getString("TESTDATA_MISSING_PARAMETER");
		}
	}

	@Override
	public String toString() {
		return getFormulaEnum().getName()+"("+getParameter(CMFormulaParameter.STRING).toString()+"; "
		+getParameter(CMFormulaParameter.COMPARESTRING).toString()+")";
	}

}
