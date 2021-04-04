/**
 * 10/11/2006
 * svonborries
 */
package model;

import bi.controller.testdata.formula.CMFormulaParameter;
import model.util.CMModelSource;

/**
 * @author svonborries
 *
 */
public interface ICMParameter extends CMModelSource, ICMFormula {
	
	/**
	 * @param formulaParameter, need this parameter because each parameter in any formula, need this atribute as identifier
	 * is unique in any ICMFormula parameter, because is used for methods that search in the list of parameters of any ICMFormula.
	 * Is highly recomended
	 * for the news class that implements ICMParameter, to define the Constructor of the class with this atribute(CMFormulaParameter).
	 * Because this is the way how CaseMaker find the specific parameter in the list of any ICMFormula.
	 * 15/11/2006
	 * svonborries
	 */
	public void setTypeParameter(CMFormulaParameter formulaParameter);
	
	/**
	 * @return an object of the type CMFormulaParameter, for any ICMParameter is necesary this atributte.
	 * Is highly recomended
	 * for the news class that implements ICMParameter, to define the Constructor of the class with this atribute(CMFormulaParameter).
	 * Because this is the way how CaseMaker find the specific parameter in the list of any ICMFormula.
	 * 15/11/2006
	 * svonborries
	 */
	public CMFormulaParameter getTypeParameter();
	
	/**
	 * @return The pure value of the Object(Number, Date, String); and can return an ICMFormula with NO call to the method Calculate()
	 * in other words it returns the Object with no precess at all. If you need the result of the method, should call the getResult()
	 * method of the ICMFormula, check the Interface ICMFormula, for more details.
	 * 15/11/2006
	 * svonborries
	 */
	public ICMValue getObject();
	
	public void setResult(ICMValue value);
	
}
