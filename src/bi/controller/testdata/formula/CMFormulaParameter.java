package bi.controller.testdata.formula;

import bi.view.lang.CMMessages;

public enum CMFormulaParameter {
	YEAR(CMMessages.getString("PARAM_FORMULAS_YEAR")), 
	MONTH(CMMessages.getString("PARAM_FORMULAS_MONTH")), 
	DAY(CMMessages.getString("PARAM_FORMULAS_DIA")), 
	NULLPARAM(CMMessages.getString("")), 
	DATE(CMMessages.getString("PARAM_FORMULAS_DATE")), 
	HOUR(CMMessages.getString("FORMULA_HOUR")), 
	NUMBER(CMMessages.getString("PARAM_FORMULAS_NUMBER")), 
	DECIMAL(CMMessages.getString("PARAM_FORMULAS_NUM_DECIMAL")), 
	BASE(CMMessages.getString("PARAM_FORMULAS_BASE")), 
	POWER(CMMessages.getString("PARAM_FORMULAS_POWER")), 
	STRING(CMMessages.getString("PARAM_FORMULAS_STRING")), 
	OLDSTRING(CMMessages.getString("PARAM_FORMULAS_OLDSTRING")), 
	NEWSTRING(CMMessages.getString("PARAM_FORMULAS_NEWSTRING")), 
	COMPARESTRING(CMMessages.getString("PARAM_FORMULAS_COMPARESTRING")), 
	LOCATIONSTRING(CMMessages.getString("PARAM_FORMULAS_LOCATION")), 
	ANGLE(CMMessages.getString("PARAM_FORMULAS_ANGLE")), 
	GRADE(CMMessages.getString("PARAM_FORMULAS_GRADE")), 
	DATE1(CMMessages.getString("PARAM_FORMULAS_DATE1")), 
	DATE2(CMMessages.getString("PARAM_FORMULAS_DATE2")), 
	NUMBERX(CMMessages.getString("PARAM_FORMULAS_NUMX")), 
	NUMBERY(CMMessages.getString("PARAM_FORMULAS_NUMY")), 
	STRING1(CMMessages.getString("PARAM_FORMULAS_STRING1")), 
	STRING2(CMMessages.getString("PARAM_FORMULAS_STRING2")), 
	POSITION(CMMessages.getString("PARAM_FORMULAS_POSITION"));
	
	private String m_text;

	CMFormulaParameter(String text){
		m_text = text;
	}

	public String getM_text() {
		return m_text;
	}
	
	
	
}
