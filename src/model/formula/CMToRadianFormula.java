package model.formula;



import bi.controller.testdata.formula.CMFormulaCategory;
import bi.controller.testdata.formula.CMFormulaParameter;
import bi.controller.testdata.formula.CMFormulas;
import bi.view.lang.CMMessages;
import bi.view.utils.CMFormatFactory;



public class CMToRadianFormula extends CMAbstractFormula {
	

	
	public Object calculate()  throws Exception{
        	Number param1 = (Number) getCalculatedParameter(CMFormulaParameter.GRADE);
            Number result = Math.toRadians(param1.doubleValue());
            if(result.toString().equals("NaN")){
            	throw new Exception();
            }
            return result;
	}

	public CMFormulaCategory getCategory() {
		return CMFormulaCategory.TRIGONOMETRY;
	}

	public CMFormulas getFormulaEnum() {
		super.setFormulaEnum(CMFormulas.TO_RADIANS);
		return CMFormulas.TO_RADIANS;
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
		return getFormulaEnum().getName()+"("+getParameter(CMFormulaParameter.GRADE).toString()+")";
	}

}
