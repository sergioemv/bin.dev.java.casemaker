package model;


import bi.controller.testdata.formula.CMFormulaCategory;
import bi.controller.testdata.formula.CMFormulaParameter;
import bi.controller.testdata.formula.CMFormulas;

public interface ICMFormula extends ICMValue{

	
	/**
	 * @param parameter
	 * @param paramValue
	 * 04/10/2006
	 * svonborries
	 * This method returns an ICMParameter, with no calculation values, this ICMParameter is stored in a List, that has
	 * any ICMFormula and Object(Numeric, Date, String).
	 * This method recive as parameter the type (CMFormulaParameter) of parameter needed to begin a search for the especific parameter
	 * stored in the List.
	 * This method returns an Object, that can be an ICMParameter, or any other Object(Numeric, Date, String).
	 */
	
	Object getParameter(CMFormulaParameter parameterType);
	
	
	/**
	 * @return
	 * 04/10/2006
	 * svonborries
	 * It return an enum of type CMFormulaCategory, this enum posses the respective information of the Category.
	 */
	CMFormulaCategory getCategory();
	
	
	
	/**
	 * @return
	 * 04/10/2006
	 * svonborries
	 * This method return an enum of type CMFormula, this enum contents the info. of the formula, for example The Name,
	 * the Description, and the CanonicalName of the formula, it is usefull in edition of an formula.
	 */
	CMFormulas getFormulaEnum();

	/**
	 * @return The result of the formula expressed in String, this value returned is the result of the calculation of the respective
	 * formula, the result of this method must be the same than the value attribute of the typedata, that is shown in the Screen.
	 * This result is always formatted with the respective pattern.
	 * 04/10/2006
	 * svonborries
	 * @throws Exception 
	 */
	String getFormattedValueResult();
	
	/**
	 * @param value, this value is inserted in the value(Object) field of the ICMParameter, and is added to the List of parameters
	 * that has any ICMFormula. 
	 * 15/11/2006
	 * svonborries
	 */
	void addParameter (Object value);
	
	/**
	 * @return an String, that is the type of data that returns the formula, this data will be stored in the Type fild of any
	 * ITypeData.
	 * This values are in the Enum CMFormulas.
	 * 15/11/2006
	 * svonborries
	 */
	String getTypeFormula();

}
