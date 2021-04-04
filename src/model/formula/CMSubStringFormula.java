package model.formula;



import bi.controller.testdata.formula.CMFormulaCategory;
import bi.controller.testdata.formula.CMFormulaParameter;
import bi.controller.testdata.formula.CMFormulas;
import bi.view.lang.CMMessages;



public class CMSubStringFormula extends CMAbstractFormula {



	
	public Object calculate()  throws Exception{
        	String param1 = (String) getCalculatedParameter(CMFormulaParameter.STRING);
        	Number param2 = (Number) getCalculatedParameter(CMFormulaParameter.LOCATIONSTRING);
            return (String) param1.substring(param2.intValue());
	}

	public CMFormulaCategory getCategory() {
		return CMFormulaCategory.TEXT;
	}

	public CMFormulas getFormulaEnum() {
		super.setFormulaEnum(CMFormulas.SUB_STRING);
		return CMFormulas.SUB_STRING;
	}

	public String getFormattedValueResult(){
		try {
			return (String)getValue();
		} catch (Exception e) {
			return CMMessages.getString("TESTDATA_MISSING_PARAMETER");
		}
	}

	@Override
	public String toString() {
		return getFormulaEnum().getName()+"("+getParameter(CMFormulaParameter.STRING).toString()+"; "
		+getParameter(CMFormulaParameter.LOCATIONSTRING).toString()+")";
	}

}
