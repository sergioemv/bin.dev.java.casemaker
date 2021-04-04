package model.formula;



import bi.controller.testdata.formula.CMFormulaCategory;
import bi.controller.testdata.formula.CMFormulaParameter;
import bi.controller.testdata.formula.CMFormulas;
import bi.controller.utils.CMNumberUtils;
import bi.view.lang.CMMessages;
import bi.view.utils.CMFormatFactory;

public class CMAsenHypFormula extends CMAbstractFormula {


	
	public Object calculate() throws Exception {
        	Number param1 = (Number) getCalculatedParameter(CMFormulaParameter.NUMBER);
            Number result = CMNumberUtils.aSenHyp(param1.doubleValue());
            if(result.toString().equals("NaN")){
            	throw new Exception();
            }
            return result;
	}

	public CMFormulaCategory getCategory() {
		return CMFormulaCategory.TRIGONOMETRY;
	}

	public CMFormulas getFormulaEnum() {
		super.setFormulaEnum(CMFormulas.ASENHYP);
		return CMFormulas.ASENHYP;
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
		return getFormulaEnum().getName()+"("+getParameter(CMFormulaParameter.NUMBER).toString()+")";
	}

}
