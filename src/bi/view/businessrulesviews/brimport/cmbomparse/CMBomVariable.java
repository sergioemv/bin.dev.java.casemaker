package bi.view.businessrulesviews.brimport.cmbomparse;

/**
 * Representation of a variable read from the bom file.
 * @author portiz
 * @since 2007-10-23
 */
public class CMBomVariable {

	//variable name
	private String _strVariableName;
	
	//variable data type
	private CMBomDataType _DataType;
	
	//Visibility
	private CMBomEVisibility _visibility;
	
	//Indicates if the variable is a array.
	private boolean _bIsArray;

	//Indicates if the variable is a read only.	
	private boolean _bIsReadOnly;
	
	//Indicates if the variable is static.	
	private boolean _bIsStatic;
	
	//Indicates if the variable is a final	
	private boolean _bIsFinal;
	
	//Indicates if the variable is a custom class (Not one of java type.)	
	private boolean _bIsCustomClass;
	
	//Indicates if the variables domain.	
	private String _strDomain="";
	
	/**
	 * Indicate if the variable is static.
	 * @return True if is an static variable.
	 */
	public boolean is_isStatic() {
		return _bIsStatic;
	}
	
	/**
	 * Set the variable to static or not
	 * @param __strStatic True or false to indicate if is static.
	 */
	public void set_Static(boolean __strStatic) {
		_bIsStatic = __strStatic;
	}
	
	/**
	 * Indicate if the variable is final.
	 * @return true if is final.
	 */
	public boolean is_isFinal() {
		return _bIsFinal;
	}
	
	/**
	 * Set the variable to final or not
	 * @param final1 True or false to indicate if is final.
	 */
	public void set_Final(boolean final1) {
		_bIsFinal = final1;
	}
	
	/**
	 * Gets the variable name.
	 * @return The variable name.
	 */
	public String get_VariableName() {
		return _strVariableName;
	}
	
	/**
	 * Sets the variable name.
	 * @param variableName The variable name.
	 */
	public void set_VariableName(String variableName) {
		_strVariableName = variableName;
	}
	
	/**
	 * Gets the data type. 
	 * @return Returns the data type represented by CMBomDataType
	 */
	public CMBomDataType get_DataType() {
		return _DataType;
	}
	
	/**
	 * Sets the data type.
	 * @param dataType The data type represented by CMBomDataType
	 */
	public void set_DataType(CMBomDataType dataType) {
		_DataType = dataType;
	}
	
	/**
	 * Gets the visibility
	 * @return A CMBomEVisibility enumerate.
	 */
	public CMBomEVisibility get_visibility() {
		return _visibility;
	}
	
	/**
	 * Sets the visibility
	 * @param _visibility A CMBomEVisibility enumerate.
	 */
	public void set_visibility(CMBomEVisibility _visibility) {
		this._visibility = _visibility;
	}
	
	/**
	 * Indicate if the variable is an array.
	 * @return True if is an array.
	 */
	public boolean isArray() {
		return _bIsArray;
	}
	
	/**
	 * Set or unset the variable to an array.
	 * @param array true for indicate that is an array.
	 */
	public void set_Array(boolean array) {
		_bIsArray = array;
	}
	
	/**
	 * Get if the variable is read only.
	 * @return True if is a read only.
	 */
	public boolean isReadOnly() {
		return _bIsReadOnly;
	}
	
	/**
	 * Sets the variable to read only.
	 * @param readOnly True for indicates that is a readonly.
	 */
	public void set_ReadOnly(boolean readOnly) {
		_bIsReadOnly = readOnly;
	}
	
	/**
	 * Representation of the variable in a String
	 */
	public String toString(){
		StringBuilder builder=new StringBuilder();
		builder.append(CMBomEVisibility.toString(this._visibility) +" ");
		builder.append(this._strVariableName);
		if (this._bIsArray)
			builder.append("[]");
		builder.append(" (");
		builder.append(this._DataType.getDataType());
		builder.append(") ");
		if (this._bIsReadOnly)
			builder.append(" -readonly- ");
		if (this._bIsFinal)
			builder.append(" -final- ");
		if (this._bIsStatic )
			builder.append(" -static- ");
		if (this._bIsCustomClass)
			builder.append(" -Class- ");
		
		if (this._strDomain.length()>0)
			builder.append(" -domain "+ this._strDomain + " -" );
		
		
		return builder.toString();
	}
	
	/**
	 * Indicates if the variable is a custom class. (Class defined by the user.)
	 * @return True if is a custom class.
	 */
	public boolean isCustomClass() {
		return _bIsCustomClass;
	}
	
	/**
	 * Sets the variable to a custom class
	 * @param __bCustomClass True for indicate that is custom.
	 */
	public void set_CustomClass(boolean __bCustomClass) {
		_bIsCustomClass = __bCustomClass;
	}
	
	/**
	 * Get the domain of the variable.
	 * @return The domain.
	 */
	public String get_Domain() {
		return _strDomain;
	}
	
	/**
	 * Sets the domain of the value.
	 * @param __strDomain The domain.
	 */
	public void set_Domain(String __strDomain) {
		_strDomain = __strDomain;
	}
	
	/**
	 * Indicates if the data type is a collection.
	 * @return True if is a collection.
	 */
	public boolean isCollection() {
		return (_DataType.getDataType().toLowerCase().indexOf("collection")>-1) ;
	}

	/**
	 * Return a default value for float-double data types.
	 * @return A string form as a value.
	 */
	private String getValueFloatDomain(){
		String range[]= _strDomain.replace("[","").replace("]", "").split(",");
		float fvalue=Float.parseFloat(range[0]);
		if (_strDomain.charAt(0)=='[')
			return Float.toString(fvalue);     
		else
		{
			if ( Float.parseFloat(range[1]) <= Float.parseFloat(range[0]))
				return Float.toString(fvalue);   //the range is wrong!!!
			
			float val=1;
			while (fvalue+val > Float.parseFloat(range[1]) )
				val/=10;
			return Float.toString(fvalue+val);
		}	
	}
	
	/**
	 * A default value for a int data type
	 * @return A string that contains a Int value
	 */
	private String getValueIntDomain(){
		String range[]= _strDomain.replace("[","").replace("]", "").split(",");
		Integer iValue=Integer.parseInt(range[0]);
		if (_strDomain.charAt(0)=='[')
			return iValue.toString();     
		else
		{
			if ( Integer.parseInt(range[1]) <= Integer.parseInt(range[0]))
				return  iValue.toString();    //the range is wrong!!!
			iValue+=1;
			return iValue.toString() ;
		}	
	}

	/**
	 * Gets the default value for the variable.
	 * @return A String containing the value.
	 */
	public String getDefaultValue(){
		
		String strCad= this.get_DataType().getDataType().toLowerCase();
		String values[];
				
		if (strCad.indexOf("string")>-1 ) {
			if (_strDomain.length()==0)
				return "\" \"";
			else{
				values=_strDomain.split(",");
				return values[0].trim() ;
			}
		}

		if ( strCad.indexOf("double")>-1 ) {
			if (_strDomain.length()==0)
				return "0";
			else
				return getValueFloatDomain();
		}

		if ( strCad.indexOf("float")>-1 ) {
			if (_strDomain.length()==0)
				return "0";
			else
				return getValueFloatDomain();
		}
		
		if ( strCad.indexOf("int")>-1 ) {
			if (_strDomain.length()==0)
				return "0";
			else
				return getValueIntDomain();
		}
		
		if ( strCad.indexOf("boolean")>-1 ) {
			return "false";
		}

		if ( strCad.indexOf("date")>-1 ) {
			return "\"1900/01/01\"";
		}
		
		System.out.println("Tipo no soportado" + this.get_DataType().getDataType()  );
		return null;
	}
	
	/**
	 * Get the definition of the variable. 
	 * @return A String with variable_name = data_type_value
	 */
	public String getDefinitionValue(){
		return this._strVariableName +" = " + getDefaultValue();
	}
	
	
}
