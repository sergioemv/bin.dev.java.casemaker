package bi.view.businessrulesviews.brimport.cmbomparse;

import java.io.File;
import java.util.ArrayList;

import bi.view.businessrulesviews.brimport.filereader.FileReaderCustom;
import bi.view.businessrulesviews.brimport.filereader.LineParser;

/**
 * 
 * @author portiz
 * @since 2007/10/23
 */
public class CMBomDocument {

	//List of classes
	private CMBomClassList _classList;

	//list of datatype (like a global reference) 
	private CMBomDataTypeList _dataTypeList;
	
	//List of uses attributes
	private ArrayList<String> _listOfUserAttributes;
	
//	List of uses attributes
	private ArrayList<String> _listOfParameters;
		
	//Indicates if the document was binded.
	private boolean _bWasBinded=false;
	
	
	/**
	 * Constructor.
	 */
	public CMBomDocument(){
		_classList=new CMBomClassList();
		_dataTypeList=new CMBomDataTypeList();
		_listOfUserAttributes=new ArrayList<String>();
		_listOfParameters=new ArrayList<String>();
	}
	
	private String depureClassReference(String __strParam){
		Integer i=__strParam.lastIndexOf(".");
		if ( i==-1)
			return __strParam;
		else
			return __strParam.substring(i+1);
	}
	
	/**
	 * Add a parameter to the list of parameters
	 * @param __strParam Parameters (In class form).
	 */
	public void addToListOfParameters(String __strParam){
		String strDepure= this.depureClassReference(__strParam);
		if (_bWasBinded && !_listOfParameters.contains(strDepure))
			_listOfParameters.add(strDepure);
	}
	
	
	/**
	 * Add a attributes to a list.  
	 * @param __strString A string that represents a Class.Attributes.
	 */
	public void addToListOfUsedAttribures(String __strString){
		if (_bWasBinded && !_listOfUserAttributes.contains(__strString))
			_listOfUserAttributes.add(__strString);
	}
		
	/**
	 * Get the combinations of the [Class.Attributes] that where read from the bom file and
	 * rest the [Class.Attributes] registered in the list of used.
	 * @see addToListOfUsedAttribures
	 * @return a String formated with \n
	 */
	public String parseNonUsedValues(){
		StringBuilder builder=new StringBuilder();
		//Get the combination of class.attributes. 
		ArrayList<String> _listOfCombination=this._classList.getCombination();
		ArrayList<String> _listOfUsedClasses=get_UsedClasses();
				
		for (String strAssignment : _listOfCombination) {
			String keyvalue[]= strAssignment.split("=");
			//Search for the ussed class.attributes. If a class.attribute was no used,
			//include it in the spected result.
			String strDepure= this.depurateClassAttribute(keyvalue[0].trim());
			if ( !_listOfUserAttributes.contains(keyvalue[0].trim()) && (_listOfUsedClasses.contains(strDepure) || _listOfParameters.contains(strDepure) )   )
				builder.append (strAssignment  + "\n");
		}
		
		//TODO: PAULO_PATCH 
		//Patch: Change '.' for '_' 
		String strAux=builder.toString().replace(".", "_");
		return strAux;
	}
		
	/**
	 * Obtain the list of classes.
	 * @return CMBomClassList, that represents a list of classes.
	 */
	public CMBomClassList get_listOfClasses() {
		if(_classList==null) 
			_classList=new CMBomClassList();
		return _classList;
	}
	
	
	private String depurateClassAttribute(String __str){
		Integer i=__str.indexOf(".");
		if (i==-1)
			return __str;
		else
			return __str.substring(0,i);
	}
	
	private ArrayList<String> get_UsedClasses(){
		ArrayList<String> arrRes=new ArrayList<String>();
		for (String strClassAtt : _listOfUserAttributes) {
			String strClass= depurateClassAttribute(strClassAtt);
			if(!arrRes.contains(strClass))
				arrRes.add(strClass);
		}
		return arrRes;
	}
	
	
	/**
	 * Fill a class with attributes and definitions.
	 * The recognition of functions are pendent. 
	 */
	private void buildClass_Definition (LineParser __myLines,CMBomClass __myClass) throws Exception{
		try {
	
			String values[]=__myLines.readLine().split(" ");
			
			//set the visibility
			__myClass.set_visibility(CMBomEVisibility.get_Visibility(values[0]));

			//set the name
			__myClass.set_ClassName(values[2]);
			
			//search for the begin of the body
			while (!buildClass_BeginClass(__myLines.readLine()) ){
				Integer iExt=__myLines.readLine().indexOf("extends");
				if ( iExt>-1 ){
					values=__myLines.readLine().split(" ");
					//String strExtendsName=values[1].substring( values[1].lastIndexOf(".")+1  );
					__myClass.set_Extends(values[1]);
				}
				__myLines.advance();
			}
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 *Build a attribute of a class. 
	 */
	private void buildClass_Attribute (String __strDeclartation, CMBomVariable __var)throws Exception{
		try {
			String values[]=__strDeclartation.split(" ");
			if ( (values.length==0) || (values[0].equals("domain")) ){
				__var=null;
				return;
			}
			//obtain the visibility
			__var.set_visibility( CMBomEVisibility.get_Visibility(values[0]));
			
			//check if is an array
			__var.set_Array(this.buildClass__Variable_isArray(__strDeclartation));
			
			//check if is readonly
			__var.set_ReadOnly(this.buildClass__Variable_hasReadOnly(values));
			
			//check if is static
			__var.set_Static( this.buildClass__Variable_hasStatic(values) );
			
			//check if is final
			__var.set_Final( this.buildClass__Variable_hasFinal(values) );
			
			Integer iPosDataType=1;
			if (__var.is_isStatic())
				iPosDataType++;
			if (__var.is_isFinal())
				iPosDataType++;
			if (__var.isReadOnly())
				iPosDataType++;
			
			//Search for the data type (remember that if the data type is not registered in the list,
			//SearchDataTypeAdd adds it automatically.)
			CMBomDataType type= _dataTypeList.SearchDataTypeAdd(values[iPosDataType]);
			
			__var.set_DataType(type);
		
			__var.set_VariableName(values[iPosDataType+1].replace(";",""));
			
			//Advance two positions			
			iPosDataType +=2;
			if  ( (iPosDataType<values.length)  &&  (values[iPosDataType].equals("domain")) ){
				
				//search for the form  like "domain [0,10000000["
				if (iPosDataType+2==values.length)
					__var.set_Domain(values[iPosDataType+1].replace(";", ""));
				else{
					String strAux="";
					//Concatenate all the values and examine later
					for (Integer h=iPosDataType+1; h <values.length; h++)
						strAux+=" "+values[h];
					if (strAux.indexOf("{")==-1)
						__var.set_Domain(values[iPosDataType+1].replace(";", ""));
					else{
						/* spected:
					 	public read only java.lang.String state domain {"New York", "Rhode Island", "New Hampshire", "Massachusetts"};
					 	public static final read only carrental.CarGroup Compact domain {static Economy, static Compact, static MidSize, static FullSize, static Luxury, static SportUtility, static Minivan};
						 */
						strAux=strAux.replace("{", "");
						strAux=strAux.replace("}", "");
						strAux=strAux.trim();
						values=strAux.split(",");
						strAux="";
						if(values.length>0)
							strAux += values[0];
						for (int h = 1; h < values.length; h++) {
							strAux +="," + values[h];
						__var.set_Domain(strAux);	
						}
					}
				}
			}
			else
				__var.set_Domain("");
		} catch (Exception e) {
			throw e; 
		}
	}
	
	/**
	 * Build the body body of the class (the name and extends of the class where read, now read the attributes and functions.) 
	 */
	private void buildClass_Body (LineParser __myLines,CMBomClass __myClass) throws Exception{
		try {
			//advance
			while (!buildClass_EndClass(__myLines.readLine()) ){
				String strLine=__myLines.readLine();

				//if is a "domain" value, skip it
				if (buildClass_hasBeginDomain(strLine)) {
					__myLines.advance();
					continue;
				}
				
				//search for the complete instruction (when a ";" is found)
				while (!buildClass_hasFinalInstruction(__myLines.readLine()) ){
					strLine=strLine + " " +	__myLines.readLineAdvance();
				}

				if (buildClass_isFunction(strLine)){
					//TODO: portiz, finish this, only for fun
					//Doesn't matter in this version 
				}				
				else{
					CMBomVariable myVariable=new CMBomVariable();
					buildClass_Attribute(strLine,myVariable);
					if (myVariable!=null)
						__myClass.get_variableList().getVariableList().add(myVariable);
				}
				__myLines.advance();
			}

		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Determines if exists an array declaration.
	 * @param __arrLine Line of "code" to analize.
	 * @return True if the line contains and array.
	 */
	private boolean buildClass__Variable_isArray (String __arrLine){
		//return ( (__arrLine.indexOf("[")>0) && (__arrLine.indexOf("]")>0) );
		//return  (__arrLine.indexOf("[]")>0);
		Integer i=__arrLine.indexOf("[");
		while (i!=-1 &&  i<__arrLine.length()){
			switch (__arrLine.charAt(i)) {
			case ' ':
			case '[':
				i++;
				break;
			case ']':
				return true;
			default:
				return false;
			}
		}
		return false;
	}
	
	/**
	 * Determines if exists an read only declaration.
	 * @param __arrVector Vector with line code, separated by " " 
	 * @return True if a read only exists.
	 */
	private boolean buildClass__Variable_hasReadOnly (String __arrVector[]){
		for (int i = 1; i < __arrVector.length; i++) {
			if ( __arrVector[i].trim().toLowerCase().equals("readonly") )
				return true;
		}
		return false;
	}
	
	/**
	 * Determines if exists an final declaration.
	 * @param __arrVector Vector with line code, separated by " "
	 * @return True if final exists.
	 */
	private boolean buildClass__Variable_hasFinal (String __arrVector[]){
		for (int i = 1; i < __arrVector.length; i++) {
			if ( __arrVector[i].trim().toLowerCase().equals("final") )
				return true;
		}
		return false;
	}
	
	/**
	 * Determines if exists an static declaration.
	 * @param __arrVector Vector with line code, separated by " "
	 * @return True if static exists.
	 */
	private boolean buildClass__Variable_hasStatic (String __arrVector[]){
		for (int i = 1; i < __arrVector.length; i++) {
			if ( __arrVector[i].trim().toLowerCase().equals("static") )
				return true;
		}
		return false;
	}

	/**
	 * Indicate if an '{' exists in the line code.
	 * @param __srtLine Line that represents code.
	 * @return True if the char '{' exists
	 */
	private boolean buildClass_BeginClass (String __srtLine){
		return ( __srtLine.equals("{")  );
	}
	
	/**
	 * Indicate if an '}' exists in the line code.
	 * @param __srtLine Line that represents code.
	 * @return True if the char '}' exists.
	 */
	private boolean buildClass_EndClass (String __srtLine){
		return ( __srtLine.equals("}")  );
	}

	/**
	 * Indicate if an ';' exists in the line code.
	 * @param __srtLine Line that represents code.
	 * @return True if the char ';' exists.
	 */
	private boolean buildClass_hasFinalInstruction (String __srtLine){
		return ( __srtLine.indexOf(";")>-1  );
	}

	/**
	 * Indicate if the word 'domain' exists in the line code.
	 * @param __srtLine Line that represents code.
	 * @return True if the word 'domain' exists.
	 */
	private boolean buildClass_hasBeginDomain (String __srtLine){
		return ( __srtLine.trim().toLowerCase().indexOf("domain")==0)  ;
	}
	
	/**
	 * Indicate if the line code refers to a function.
	 * @param __srtLine Line that represents code.
	 * @return True if exists an '()'
	 */
	private boolean buildClass_isFunction (String __srtLine){
		return ( (__srtLine.indexOf("(")>-1) && (__srtLine.indexOf(")")>-1) ); 
	}
	
	/**
	 * Indicates if the line of code is an attribute.
	 * @param __srtLine Line that represents code.
	 * @return True if the line represents an attribute.
	 */
	private boolean buildClass_Variable (String __srtLine){
		return ( !buildClass_isFunction(__srtLine) ); 
	}
	
	/**
	 * Parse the definitions of a class.
	 * @param __myLines Line that represents code.
	 * @throws Exception
	 */
	private void buildClass(LineParser __myLines)throws Exception {
		try {
			
			CMBomClass myClass= new CMBomClass();
			//extract the class name
			buildClass_Definition(__myLines, myClass);

			//Advance one more line to "eat" the begin of the body, "{"
			__myLines.advance();
			
			//Parses the body
			buildClass_Body(__myLines, myClass);

			//Advance one more line to "eat" the end of the body, "}"
			__myLines.advance();
			
			this._classList.getClassList().add(myClass);
			
		} catch (Exception e) {
			throw e;
		}
	}
		
	/**
	 * Bind the bom file. 
	 * @param __myLines Parser, that contains the parser.
	 * @throws Exception
	 */
	private void bindBomFile_aux(LineParser __myLines)throws Exception{
		try {
			String strLine;
			while (! __myLines.isEndOfFile()){
				strLine=__myLines.readLine();
				if (isClassReference(strLine)) {
					buildClass(__myLines);
				}else
					__myLines.advance();
			}
		} catch (Exception e) {
			throw e;
		}
	}
 
	/**
	 * Receives a path of a bom file and parse it.
	 * @param __strFile Path of BOM file.
	 * @throws Exception
	 */
	public void bindBomFile(String __strFile)throws Exception{
		try {
			
			File myFile=new File (__strFile);	
			if (!myFile.exists())
				throw new Exception("The file " +__strFile +" doen`t exists.");

			FileReaderCustom f=new FileReaderCustom(__strFile);
			f.set_bNoLineSpaces(true);
			ArrayList<String> listOfLines=f.readFile();
			LineParser myLines=new LineParser(listOfLines);
			bindBomFile_aux(myLines);
			checkForCustomClasses();
			_bWasBinded=true;
		} catch (Exception e) {
			_bWasBinded=false;
			throw e;
		}
	}

	/**
	 * Indicates if the word "class" exists.
	 * @param __strLine  Line that represents code.
	 * @return True if the word "class" exists.
	 */
	private boolean isClassReference(String __strLine){
		return __strLine.indexOf("class ")>-1;
	}
		
	/**
	 * Once parsed the bom file, check for the attributes type and identify classes.
	 * @throws Exception
	 */
	private void checkForCustomClasses( )throws Exception{
		try {
			
			for (CMBomClass myClass : this.get_listOfClasses().getClassList()) {
				for (CMBomVariable myVar : myClass.get_variableList().getVariableList()) {
					if (this.get_listOfClasses().existsClass(myVar.get_DataType().getDataType()))
						myVar.set_CustomClass(true);
					else	
						myVar.set_CustomClass(false);
				}
			}
			
		} catch (Exception e) {
			throw e;
		}
	}
	

}
