/**
 * 22/11/2006
 * svonborries
 */
package model;
/**
 * @author svonborries
 *
 */
public interface ICMValue {
	
	/**
	 * USE toString() METHOD!!!
	 * It returns the value that has to be put in the Formula column, all Objects that implements this interface, should return
	 * something to put in Formula Column, in the case that User introduce an Naturan Object(String, Number, Date), this toString()
	 * must return an empy String ("");
	 * 23/11/2006
	 * svonborries
	 */
	
	
	/**
	 * @return The Object that is the result of the formula, if the formula returns a Date, this method will return that kind of Object
	 * This method calls the calculate() method of any ICMFormula, if it founds an ICMFormula in the parameter List, it will
	 * call the getResult() of this ICMFormula, and it will recursivelly calculate all the ICMFormulas, until it can return an Object
	 * (Numeric, Date, String).
	 * If it is another kind of data different than ICMFormula, it will take the Object value, the natural kind of data, it won't take
	 * a String, it will return the original Object
	 * 04/10/2006
	 * svonborries
	 * @throws Exception 
	 */
	
	public Object getValue() throws Exception;
	


	/**
	 * @return A perfect copy of the Object.
	 * 06/12/2006
	 * svonborries
	 */
	public Object clone();
}
