package bi.controller.testdata.formula;

import bi.view.lang.CMMessages;

public enum CMFormulaCategory {

	DATE_TIME(CMMessages.getString("FORMULA_CATEGORY_DATETIME")),
	MATHEMATICS(CMMessages.getString("FORMULA_CATEGORY_MATHEMATICS")),
	TEXT(CMMessages.getString("FORMULA_CATEGORY_TEXT")),
	TRIGONOMETRY(CMMessages.getString("FORMULA_CATEGORY_TRIGONOMETRY")),
	CONSTANTS(CMMessages.getString("FORMULA_CATEGORY_CONTANTS"));
	
	private String name;
	
	CMFormulaCategory(String p_name){
		name = p_name;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {	
		return getName();
	}
	
	
}
