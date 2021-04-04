package model.formula;



import java.util.Date;

import bi.controller.testdata.formula.CMFormulaCategory;
import bi.controller.testdata.formula.CMFormulaParameter;
import bi.controller.testdata.formula.CMFormulas;
import bi.view.lang.CMMessages;
import bi.view.utils.CMFormatFactory;


public class CMConcatFormula extends CMAbstractFormula {



	
	public Object calculate() throws Exception {
			String param1 = null;
			String param2 = null;
        	Object param1var = getCalculatedParameter(CMFormulaParameter.STRING1);
        	Object param2var = getCalculatedParameter(CMFormulaParameter.STRING2);
        	
        	if(param1var instanceof Number)
        		param1 = param1var.toString();
        	else if(param1var instanceof Date)
        		param1 = CMFormatFactory.formatDate("yyyy/MM/dd",(Date)param1var);
        	else
        		param1 = (String) param1var;
        	
        	
        	if(param2var instanceof Number)
        		param2 = param2var.toString();
        	else if(param2var instanceof Date)
        		param2 = CMFormatFactory.formatDate("yyyy/MM/dd", (Date)param2var);
        	else
        		param2 = (String) param2var;
        	
        	if(param1.equalsIgnoreCase("null") || param2.equalsIgnoreCase("null"))
        		throw new Exception();
            return (String) param1 + param2;

	}

	public CMFormulaCategory getCategory() {
		return CMFormulaCategory.TEXT;
	}

	public CMFormulas getFormulaEnum() {
		super.setFormulaEnum(CMFormulas.CONCAT);
		return CMFormulas.CONCAT;
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
		return getFormulaEnum().getName()+"("+getParameter(CMFormulaParameter.STRING1).toString()+"; "
		+getParameter(CMFormulaParameter.STRING2).toString()+")";
	}

}
