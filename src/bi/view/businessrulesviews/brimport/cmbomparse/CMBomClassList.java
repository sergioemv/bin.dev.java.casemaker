package bi.view.businessrulesviews.brimport.cmbomparse;

import java.util.ArrayList;


/**
 * This class represents a list of classes, represented by the class CMBomClass
 * @author portiz
 * @since  2007/10/23
 */
public class CMBomClassList {

	//ArrayList containing the classes, represented by CMBomClass
	private ArrayList<CMBomClass> _arrClassList;
	
	/**
	 * Constructor.
	 */
	public CMBomClassList() {
		super();
		_arrClassList= new ArrayList<CMBomClass>();
	}
	
	/**
	 * Get the Arraylist of classes.
	 * @return Returns ArrayList<CMBomClass>
	 */ 
	public ArrayList<CMBomClass> getClassList(){
		return _arrClassList;
	}

	/**
	 * Indicates if a class exists in the current list (ArrayList)
	 * @param __strClassName Name of the class to search.
	 * @return True if the class exists.
	 */
	public boolean existsClass(String __strClassName){
		String strAux;
				
		//take out packages. Carrental.Car must be Car.
		Integer i=__strClassName.lastIndexOf(".");
		if (i==-1)
			strAux=__strClassName;
		else
			strAux=__strClassName.substring(i+1);
		
		for (CMBomClass myClass : _arrClassList) {
			if (myClass.get_SimpleClassName().equals(strAux))
				return true;
		}
		return false;
	}
	
	
	/**
	 * Obtain all the combinations of Class.Attributes that can be form. 
	 * 		Eg.- Class1.att1=data_type1 
	 * 			 Class2.att2=data_type2
	 * @return An Arraylist with the combinations.
	 */
	public  ArrayList<String> getCombination(){
		ArrayList<String> arrList=new  ArrayList<String>();
		for (CMBomClass myClass : this._arrClassList) {
			ArrayList<String> arrAux=myClass.getCombination(true,_arrClassList,true);
			for (String strLine : arrAux) {
				arrList.add(strLine);
			}
		}
		return arrList;
	}
	

}
