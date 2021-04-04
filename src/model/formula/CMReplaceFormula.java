package model.formula;



import bi.controller.testdata.formula.CMFormulaCategory;
import bi.controller.testdata.formula.CMFormulaParameter;
import bi.controller.testdata.formula.CMFormulas;
import bi.controller.utils.CMCharUtils;
import bi.view.lang.CMMessages;


public class CMReplaceFormula extends CMAbstractFormula {



	
	public Object calculate()  throws Exception{
        	String param1 = (String) getCalculatedParameter(CMFormulaParameter.STRING);
        	String param2 = (String) getCalculatedParameter(CMFormulaParameter.OLDSTRING);
        	String param3 = (String) getCalculatedParameter(CMFormulaParameter.NEWSTRING);
//        	if(param2 == null)
//        		param2 = "";
//        	if(param3 == null)
//        		param3 = "";
        	if(param1 != null && param2 !=null && param3 == null)
        		param3 = "";
            return (String) CMCharUtils.remplace(param1, param2, param3);
	}

	public CMFormulaCategory getCategory() {
		return CMFormulaCategory.TEXT;
	}

	public CMFormulas getFormulaEnum() {
		super.setFormulaEnum(CMFormulas.REPLACE);
		return CMFormulas.REPLACE;
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
		String param3 = getParameter(CMFormulaParameter.NEWSTRING).toString();
		if(!getParameter(CMFormulaParameter.STRING).toString().equalsIgnoreCase("")&&
				!getParameter(CMFormulaParameter.OLDSTRING).toString().equalsIgnoreCase("")&&
				getParameter(CMFormulaParameter.NEWSTRING).toString().equalsIgnoreCase("?"))
			param3 = "";
		return getFormulaEnum().getName()+"("+getParameter(CMFormulaParameter.STRING).toString()+"; "
		+getParameter(CMFormulaParameter.OLDSTRING).toString()+"; "+param3+")";
	}

}
