package bi.view.businessrulesviews.brimport.cmbomparse;

import java.util.ArrayList;
/**
 * Represents a list of variables.
 * @author portiz
 * @since 2007-10-23
 */
public class CMBomVariableList {

	//list of variables.
	private ArrayList<CMBomVariable> _arrVariableList;

	
	/**
	 * Constructor.
	 */
	public CMBomVariableList() {
		super();
		_arrVariableList= new ArrayList<CMBomVariable>();
	}
	
	/**
	 * Get the arrayList of variables.
	 * @return Returns the arraylist.
	 */
	public ArrayList<CMBomVariable> getVariableList(){
		return _arrVariableList;
	}
	
}
