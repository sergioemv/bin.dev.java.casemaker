package bi.view.businessrulesviews.brimport.cmbomparse;

import java.util.ArrayList;

/**
 * Manage a list of datatypes
 * @author portiz
 * @since 2007/10/23
 */
public class CMBomDataTypeList {

	//Arraylist that contains the data types
	private ArrayList<CMBomDataType> _arrDataTypeList;
	
	/**
	 * Indicates if a datatype exists.(Search by name, not by reference.)
	 * @param __DataType Datatype to be search.
	 * @return True if the data type exists.
	 */
	public boolean existDataType (CMBomDataType __DataType){
		for (CMBomDataType dataType :_arrDataTypeList ) {
			if (dataType.getDataType().equals(__DataType.getDataType()))
				return true;
		}
		return false;
	}
	
	/**
	 * Indicates if a datatype exists.(Search by name, not by reference.)
	 * @param __strDataType Name of the data type.
	 * @return True if the data type exists.
	 */
	public boolean existDataType (String __strDataType){
		for (CMBomDataType dataType :_arrDataTypeList ) {
			if (dataType.getDataType().equals(__strDataType))
				return true;
		}
		return false;
	}
	
	/**
	 * Adds a data type to the list. If the data type exists, nothing happens (not added).
	 * @param __strNewDataType Name of the data type.
	 */
	public void addDataType (String __strNewDataType){
		if (! this.existDataType(__strNewDataType))
			_arrDataTypeList.add( new CMBomDataType( __strNewDataType));
	}
	
	/**
	 * Adds a data type to the list. If the data type exists, nothing happens (not added). 
	 * @param __DataType
	 */
	public void addDataType (CMBomDataType __DataType){
		if (! this.existDataType(__DataType))
			_arrDataTypeList.add( __DataType);
	}
	
	/**
	 * Constructor.
	 */
	public CMBomDataTypeList(){
		super();
		_arrDataTypeList = new ArrayList<CMBomDataType>();
	}
	
	/**
	 * Search a data type.
	 * @param __DataType Data type to search.
	 * @return If the data type exists, return a CMBomDataType; else returns null.
	 */
	public CMBomDataType getDataType(CMBomDataType __DataType){
		for (CMBomDataType dataType :_arrDataTypeList ) {
			if (dataType.getDataType().equals(__DataType.getDataType()))
				return dataType;
		}
		return null;
	} 

	/**
	 * Search a data type. If not exists, add the data type to the list.
	 * @param __DataType Datatype to search.
	 * @return A data type represented by CMBomDataType.
	 */
	public CMBomDataType SearchDataTypeAdd (CMBomDataType __DataType){
		CMBomDataType myDataType=this.getDataType(__DataType);	
		if (myDataType!=null)
			return myDataType;
		else{
			this.addDataType(__DataType);
			return __DataType;
		}
	}

	/**
	 * Search a data type. If not exists, add the data type to the list.
	 * @param __strDataType Datatype to search, represented by his name.
	 * @return A data type represented by CMBomDataType.
	 */
	public CMBomDataType SearchDataTypeAdd (String __strDataType){
		CMBomDataType tempDataType=new CMBomDataType(__strDataType);
		CMBomDataType myDataType=this.getDataType(tempDataType);	
		if (myDataType!=null)
			return myDataType;
		else{
			this.addDataType(tempDataType);
			return tempDataType;
		}
	}
	
}
