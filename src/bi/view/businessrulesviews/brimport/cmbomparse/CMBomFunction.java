package bi.view.businessrulesviews.brimport.cmbomparse;

/**
 * Represents a function.
 * @author portiz
 * @since 2007-10-23
 */
public class CMBomFunction {

	//name of the function
	private String _strFunctionName;
	
	//visibility of the function
	private CMBomEVisibility _visibility;

	//list of arguments
	private CMBomVariableList _arguments;
	
	//return type.
	private CMBomVariable  _functionReturns;
	
	//indicates if the return type is an array
	private boolean isArrayTheReturn;
	
	/**
	 * Get the function name.
	 * @return The function name.
	 */
	public String get_strFunctionName() {
		return _strFunctionName;
	}
	
	/**
	 * Sets the function name
	 * @param functionName Function name.
	 */
	public void set_strFunctionName(String functionName) {
		_strFunctionName = functionName;
	}
	
	/**
	 * Get the visibility.
	 * @return visibility as CMBomEVisibility enumerate.
	 */
	public CMBomEVisibility get_visibility() {
		return _visibility;
	}
	
	/**
	 * Sets the visibility.
	 * @param _visibility visibility as CMBomEVisibility enumerate.
	 */
	public void set_visibility(CMBomEVisibility _visibility) {
		this._visibility = _visibility;
	}
	
	/**
	 * Get the list of arguments.
	 * @return List of arguments represented by CMBomVariableList class.
	 */
	public CMBomVariableList get_arguments() {
		return _arguments;
	}
	
	/**
	 * Get the function returns.
	 * @return The return as a CMBomVariable class.
	 */
	public CMBomVariable get_functionReturns() {
		return _functionReturns;
	}

	/**
	 * Sets the returns of the function.
	 * @param returns The return as a CMBomVariable class.
	 */
	public void set_functionReturns(CMBomVariable returns) {
		_functionReturns = returns;
	}
	
	/**
	 * Indicates if the return is an array.
	 * @return
	 */
	public boolean isArrayTheReturn() {
		return isArrayTheReturn;
	}
	
	/**
	 * Set if the return is an array.
	 * @param isArrayTheReturn
	 */
	public void set_ArrayTheReturn(boolean isArrayTheReturn) {
		this.isArrayTheReturn = isArrayTheReturn;
	}
	
}
