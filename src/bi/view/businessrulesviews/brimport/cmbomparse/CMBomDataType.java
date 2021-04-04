package bi.view.businessrulesviews.brimport.cmbomparse;
/**
 * This class represents a data type, readed from the bom file.
 * @author portiz
 * @since  2007/10/23
 */
public class CMBomDataType {

	//Name of the data type
	private String _strDataTypeName;
	
	/**
	 * Return the name of the data type
	 * @return A string that represents the name of the dataType.
	 */
	public String getDataType(){
		return _strDataTypeName;
	} 
	
	/**
	 * Return the data type without Namespace.
	 * Eg.- if System.math.BigInteger -> BigInteger 
	 * @return A string that represents the name of the dataType without namespaces.
	 */
	public String getDataTypeSimple(){
		Integer i=-1;	
		i=_strDataTypeName.lastIndexOf(".");
		if (i==-1)
			return _strDataTypeName;
		else
			return _strDataTypeName.substring(i+1);
	} 
	
	/**
	 * Set a name for the data type
	 * @param __strDataTypeName Name of the data type.
	 */
	public CMBomDataType(String __strDataTypeName){
		super();
		this._strDataTypeName=__strDataTypeName;
	}
}
