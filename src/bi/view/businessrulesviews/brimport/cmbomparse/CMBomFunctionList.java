package bi.view.businessrulesviews.brimport.cmbomparse;

import java.util.ArrayList;

/**
 * Manage a list of functions.
 * @author portiz
 * @since 2007-10-23
 */
public class CMBomFunctionList {

	//Arraylist of functions.
	private ArrayList<CMBomFunction> _arrFunctionList;

	/**
	 * Constructor
	 */
	public CMBomFunctionList() {
		super();
		_arrFunctionList= new ArrayList<CMBomFunction>();
	}
	
	/**
	 * Get the list of functions.
	 * @return A ArrayList of CMBomFunction.
	 */
	public ArrayList<CMBomFunction> getFunctionList(){
		return _arrFunctionList;
	}
	
}
