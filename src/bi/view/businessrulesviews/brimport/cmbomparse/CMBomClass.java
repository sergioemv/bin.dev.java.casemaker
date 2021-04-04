package bi.view.businessrulesviews.brimport.cmbomparse;

import java.util.ArrayList;
//
/**
 *This represents a class that was read directly from the bom file 
 * @author portiz
 * @since  2007/10/23
 */
public class CMBomClass {

	//the name of the class
	private String _strClassName="";
	
	//the extension (if not extends, then this attribute is equal to "")
	private String _strExtends="";
	
	//the visibility
	private CMBomEVisibility _visibility;
	
	//a list of attributes the class has
	private CMBomVariableList _variableList;
	
	//a list of functions the class has
	private CMBomFunctionList _functionList;
	
	/**
	 * Constructor
	 */
	public CMBomClass(){
		_variableList=new CMBomVariableList();
		_functionList=new CMBomFunctionList();
	}
		
	/**
	 * Returns the name of the class
	 * @return A string that represents the name of the class
	 */
	public String get_ClassName() {
		return _strClassName;
	}
	
	/**
	 * Returns the name of the class without Package.  
	 * @return String. EG.- Carrental.Cargroup -> Cargroup
	 */
	public String get_SimpleClassName() {
		Integer i= _strClassName.lastIndexOf(".");
		if (i==-1)
			return _strClassName;
		else
			return _strClassName.substring(i+1);
	}
	
	/**
	 * Returns the extends of the class without Package
	 * @return String. EG.- Carrental.Cargroup -> Cargroup
	 */
	public String get_SimpleExtends(){
		Integer i=-1;
		i=_strExtends.lastIndexOf(".");
		if (i==-1)
			return this._strExtends;
		else
			return this._strExtends.substring(i+1);
	}

	/**
	 * Set the class name
	 * @param className The new name of the class.
	 */
	public void set_ClassName(String className) {
		_strClassName = className;
	}
	
	/**
	 * Get the visibility of the class.
	 * @return The visibility of the class as CMBomEVisibility object.
	 */
	public CMBomEVisibility get_visibility() {
		return _visibility;
	}
	
	/**
	 * Set the visibility of the class.
	 * @param _visibility The visibility of the class as CMBomEVisibility object.
	 */
	public void set_visibility(CMBomEVisibility _visibility) {
		this._visibility = _visibility;
	}
	
	/**
	 * Get the list of (variables) attributes
	 * @return A list of attributes represented by CMBomVariableList.
	 */
	public CMBomVariableList get_variableList() {
		return _variableList;
	}
	
	/**
	 * Get the list of functions
	 * @return A list of functions represented by CMBomFunctionList.
	 */
	public CMBomFunctionList get_functionList() {
		return _functionList;
	}

	/**
	 * Get the extends of the class.
	 * @return A string that represents the extends of the class. 
	 */
	public String get_Extends() {
		return _strExtends;
	}

	/**
	 * Set the extends of the class.
	 * @param __strExtends A string that represents the extends of the class.
	 */
	public void set_Extends(String __strExtends) {
		_strExtends = __strExtends;
	}

	/**
	 * Obtain all the combinations of Class.Attributes that can be form. 
	 * 		Eg.- Class1.att1=data_type1 
	 * 			 Class2.att2=data_type2
	 * 			 ... 
	 * @param __bUseDefaultValue Indicate that the attributes comes with values, according their data type.
	 * @param __ListOfClasses A reference for all the classes.
	 * @param __bConcatClassName Indicates if the list is given with the class name.(Class1.att1 | att1 ).
	 * 		  The high level users should call this method with "true".	
	 * @return An ArrayList of strings that contains all the Class.attributes references.
	 */
	public  ArrayList<String>  getCombination(boolean __bUseDefaultValue,ArrayList<CMBomClass> __ListOfClasses, boolean __bConcatClassName ){
		ArrayList<String> arrList=new  ArrayList<String>();
		
		//check for the inherited attributes
		if (this._strExtends.length()>0){
			CMBomClass myClass=null;
			//search if the class exists.
			String strSimpleExtend=this.get_SimpleExtends();
			for (CMBomClass auxClass : __ListOfClasses) {
				if (auxClass.get_ClassName().equals(strSimpleExtend)){
					myClass=auxClass;
					break;
				}
			}
			if (myClass!=null){
				//if the class is found, extract all the attribues (calling )
				ArrayList<String> _inheriteddList=myClass.getCombination(true,__ListOfClasses,false);			
				for (String str : _inheriteddList) {
					arrList.add( this._strClassName +"."+ str);
				}
			}
		}
		
		//check for the actual attributes
		for (CMBomVariable myVar : this._variableList.getVariableList()) {
			if ( myVar.isArray() )
				continue;
			if ( !myVar.isCustomClass() ){
				if (!myVar.isCollection()){
					if (__bConcatClassName)
						arrList.add( this._strClassName +"."+  myVar.getDefinitionValue());
					else
						arrList.add( myVar.getDefinitionValue());
				}
			}
			else{
				//check that it's not a composition of myself
				if (!this._strClassName.equals(myVar.get_DataType().getDataTypeSimple())){
					CMBomClass myClass=null;
					for (CMBomClass auxClass : __ListOfClasses) {
						if (auxClass.get_ClassName().equals(myVar.get_DataType().getDataTypeSimple())){
							myClass=auxClass;
							break;
						}
					}
					if (myClass!=null){
						//obtain the class-attributes of the composed attribute, now let's handle them like
						//if they are of the own class.
						ArrayList<String> _composedList=myClass.getCombination(true,__ListOfClasses,false);
						for (String str : _composedList) {
							if (__bConcatClassName)
								arrList.add( this._strClassName +"."+ myVar.get_VariableName() +"."+ str);
							else
								arrList.add( myVar.get_VariableName() +"."+ str);
						}
					}
				}
			}
		}
		return arrList;
	}
}	
