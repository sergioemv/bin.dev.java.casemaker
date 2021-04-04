package bi.view.businessrulesviews.brimport.utilsimporter;

import java.rmi.activation.ActivationGroupDesc.CommandEnvironment;

/**
 * Manage Routines for "string-work" for the business innovations's rules.
 * 
 * @author portiz
 * 
 */
public class CMBRStringUtils {

	//-----------------------------------------------------------------------------------	

	
	/**
	 * Obtains the object referenced by an complete reference. eg.-"Part1.Part2" ->
	 * "Part2"
	 * 
	 * @param __strCompleteClassReference the complete string. 
	 * @return a String containing only the second part.
	 */
	public static String getSecondPartName(String __strCompleteString) {

		String strAux = __strCompleteString.trim();
		if (strAux.equals(""))
			return "";
		else {
			if (__strCompleteString.indexOf(".") == -1)
				return strAux;
			else
				return __strCompleteString.substring(__strCompleteString
						.indexOf(".") + 1);
		}
	}

	
	//-----------------------------------------------------------------------------------
	//Auxiliar function from DepureCompareToNumber
	private static boolean isCompareJavaInteger(String __strCad) {
		return ( __strCad.indexOf("java.math.BigInteger(java.lang.Integer.toString(")>-1 );
	}
	//Auxiliar function from DepureCompareToNumber
	private static String DepureJavaInteger(String __strCad,String __operator) {
		if( __operator==null)
			return __strCad;
		Integer i =__strCad.indexOf(".compareTo");
		if( i==-1)
			return __strCad;
		String part1=__strCad.substring(0,i);
		String part2="";
		i=__strCad.lastIndexOf("(")+1;
		while (__strCad.charAt(i)!=')')
		{
			part2 +=__strCad.charAt(i);
			i++;	
		}
		return 	part1 + " " +__operator+" " +part2;
	}
	//Auxiliar function from DepureCompareToNumber
	private static boolean isCompareJavaDateTime(String __strCad) {
		return ( __strCad.indexOf("java.util.Date(ilog.rules.brl.IlrDateUtil.getLocalTime(")>-1 );
	}
	//Auxiliar function from DepureCompareToNumber
	private static String DepureJavaDateTime(String __strCad,String __operator) {
		if( __operator==null)
			return __strCad;
		Integer i =__strCad.indexOf(".compareTo");
		if( i==-1)
			return __strCad;
					
		String strFunctionName=__strCad.substring(0,i);
		String strDay ,strMonth, strYear ;
		String strHour ,strMinutes, strSeconds;

		i =__strCad.indexOf("getLocalTime(") + new String("getLocalTime(") .length() ;
		strYear="";
		while (__strCad.charAt(i) != ','  )
			strYear += __strCad.charAt(i++);
		
		i++;
		strMonth="";
		while (__strCad.charAt(i) != ','  )
			strMonth += __strCad.charAt(i++);

		i++;
		strDay="";
		while (__strCad.charAt(i) != ','  )
			strDay += __strCad.charAt(i++);
		
		i++;
		strHour="";
		while (__strCad.charAt(i) != ','  )
			strHour += __strCad.charAt(i++);

		i++;
		strMinutes="";
		while (__strCad.charAt(i) != ','  )
			strMinutes += __strCad.charAt(i++);

		
		i++;
		strSeconds="";
		while (__strCad.charAt(i) != ','  )
			strSeconds += __strCad.charAt(i++);

		return 	strFunctionName +
				" " +__operator+" " +
		        "\"" + 
		        		strYear +"/"+ strMonth +"/" + strDay + " "  +
		        		strHour +":" +strMinutes +":" + strSeconds  +
		        "\""; 
	}

	
	private static boolean hasEquals(String __strCad){
		return (__strCad.indexOf(".equals") >-1);
	}
	
	
	
	//Auxiliar function from DepureCompareToNumber
	private static String DepureJavaEqual(String __strCad) {
		Integer i =__strCad.indexOf(".equals");
		Integer iSimpleQuote =__strCad.indexOf("'");
		if( ( i==-1)||(i==0) ||(iSimpleQuote==0) )
			return __strCad;
					
		String strFunctionName=__strCad.substring(0,i);
		String strCondition="";
		
		iSimpleQuote++;
		while (( iSimpleQuote< __strCad.length() ) && (__strCad.charAt(iSimpleQuote)!='\'' )){
			strCondition+=__strCad.charAt(iSimpleQuote);
			iSimpleQuote++;
		}	
			
		
		return 	strFunctionName +
				" = " +
				"\"" +
		        strCondition +
		        "\"" ;
		         
	}
	
	//Auxiliar function from DepureCompareToNumber
	private static boolean isTranslation(String __strCad) {
		return ( __strCad.indexOf("translation.")>-1 );
	}

	/**
	 * Returns the amount of arguments that the function has.
	 * @param __strCad the expected function.
	 * @return the number or arguments.
	 */
	public static int cantArguments(String __strCad) {
		
		Integer iCantArg=0;
		Integer iSearch=0;
		
		//if has an Parenthesis, there is almost one argument.
		if (__strCad.indexOf('(',0)>-1)
			iCantArg=1;
		//boolean bHasEnter=false;
		
		do{
			iSearch=__strCad.indexOf(',',iSearch);
			if ((iSearch !=-1) && (iSearch+1 <__strCad.length()  ))	{
				iCantArg++;
				iSearch++;
			}
				
		}while (iSearch!=-1);
		
		
		return iCantArg;
	}

	private static String deleteTranslation_aux(String __strCad) {
		Integer iPos=-1;
		String strRes;
		iPos=__strCad.indexOf(".");
		iPos=__strCad.indexOf(".",iPos+1)+1;
		
			if (iPos!=-1)
			strRes=__strCad.trim().substring(iPos);
		else
			strRes=__strCad;
		return strRes;
		
	}
	
	public static boolean isNumeric(String __strString){
		try {
			Integer.parseInt(__strString);
			return true;
		} catch (NumberFormatException nfe){
			return false;
		}
	}
	

	public static boolean isNumeric(char __char){
		try {
			String x=""+__char;
			return isNumeric(x);
		} catch (NumberFormatException nfe){
			return false;
		}
	}

	
	//Auxiliar function from DepureCompareToNumber
	private static String deleteTranslation(String __strCad,Integer __iCantArg) {
		
		String strRes;
		Integer iPos;

		switch (__iCantArg) {
		case 0:
			strRes=deleteTranslation_aux(__strCad);
			break;
		case 1:
			strRes=deleteTranslation_aux(__strCad);
			iPos=strRes.indexOf('(');
						
			if (strRes.length()>iPos ){
				if (strRes.charAt(iPos+1)!='"' && strRes.charAt(iPos+1)!='\'' &&  strRes.charAt(iPos+1)!='\"' && ! isNumeric(strRes.charAt(iPos+1)))
					strRes=strRes.substring(0, iPos);
			}else
				strRes=strRes.substring(0, iPos);
			break;	
		case 2:
			String AuxiliarChar="";
			strRes=deleteTranslation_aux(__strCad);
			Integer iPosParenthesis= strRes.indexOf('(')+1;
			iPos=strRes.indexOf(',')-1;
			if (strRes.charAt(iPosParenthesis)=='\'')
				AuxiliarChar="\'";
			if (strRes.charAt(iPosParenthesis)=='\"')
				AuxiliarChar="\"";
			if (AuxiliarChar.length()==0)
				iPos++;
			strRes=strRes.substring(0,iPos) +AuxiliarChar+ ")";
			break;	
		default:
			strRes=__strCad;
			break;
		}
		
		return strRes;
	}

	
	
	//Auxiliar function from DepureCompareToNumber
	private static String DepureTranslation (String __strCad) {
		String strRes;
		Integer i=cantArguments(__strCad);
		strRes=deleteTranslation(__strCad,i);
		return strRes;
	}

	
	private static boolean hasB2X(String __strCad){
		return (__strCad.indexOf("_B2X_")!=-1);
	}
	
	
	
	/**
	 * Formats a binary operation.
	 * @param __strString Debugs the comparer.
	 * @param __operator  Operator
	 * @return
	 */	 
	public static String DepureCompareToNumber (String __strString,String __operator ){
		if (__strString.indexOf(".")==-1)
			return __strString;
		else
		{
			String strAux =__strString; 
			
			//Indetify is is a comparison of numbers
			if (isCompareJavaInteger(strAux))
				strAux=DepureJavaInteger(strAux,__operator);
			else{
				//Indetify is is a comparison of datetime 
				if (isCompareJavaDateTime(strAux))
					strAux=DepureJavaDateTime(strAux,__operator);
				else{
					if (isTranslation(strAux))
						strAux= DepureTranslation(strAux);
				}
			}

			if ( hasEquals(strAux) )
				strAux=DepureJavaEqual(strAux);
			
			if ( hasB2X(strAux) )
				strAux=DepureB2X(strAux);
			
			return strAux;
		}	
	}

	//Depure the B2X concatenated from jrules project	
	private static String DepureB2X(String __strAux) {
		Integer i= __strAux.indexOf("_B2X_");
		String strRes;
		if (i==-1)
			return __strAux;
		strRes=__strAux.substring(0,i);
		i= __strAux.indexOf("(",i);
		strRes= strRes + __strAux.substring(i);
		return strRes;
	}


	//-----------------------------------------------------------------------------------
	
	//Auxiliar function of hasKnowedFunctions
	private static boolean hasKnowedFunctions_compareTo (String __strString ){
		return (__strString.trim().toLowerCase().indexOf("compareto")>-1 );
	}
	
//	Auxiliar function of hasKnowedFunctions
	public static boolean hasKnowedFunctions_equal_primitive (String __strString ){
		return (__strString.trim().toLowerCase().indexOf("=")>-1 );
	}
	
	/**
	 * Determines if the method passed is well knowed.  
	 * @param __strString Tje string that contains a method
	 * @return True if the string contains knowed functions, otherwise false.
	 */
	public static boolean hasKnowedFunctions (String __strString ){
		if (__strString.trim().length()==0)
			return false;
		else
			return hasKnowedFunctions_compareTo (__strString) || 
				   hasKnowedFunctions_equal_primitive (__strString);
	}
	
	/**
	 * Separate strings by the first white space
	 * @param __strCondition
	 * @return a String that separates the condition
	 */
	public static String depureConditionals(String __strCondition){
		String strAux=__strCondition.trim();
		Integer i= strAux.indexOf(" ");
		if (i==-1)
			return strAux;
		else
			return strAux.substring(0,i);
		
	}
	
	/**
	 * Get a Line of asterics. Then length of the line depends from the parameter's length.
	 * @param a String
	 * @return Line of asterics(*).
	 */
	public static String getOrnamentsAsterics(String __strString){
		String strAux="";
		for (int j = 0; j < __strString.length()*(2) ; j++) {
			strAux +="*";			
		}
		return strAux;
	}
	
	/**
	 * Get a Line of lines(-). Then length of the line depends from the parameter's length.
	 * @param a String
	 * @return Line of lines(-)
	 */
	public static String getOrnamentsLines(String __strString){
		String strAux="";
		for (int j = 0; j < __strString.length()*(2) ; j++) {
			strAux +="-";			
		}
		return strAux;
	}
	
	//Signs if exists "java_math_BigInteger"
	private static boolean hasBigInteger(String __strString){
		return __strString.indexOf("new java.math.BigInteger(java.lang.Integer.toString(")!=-1;
	}
	
	/**
	 * Delete a portion of a string specifying an initial position and a final position.
	 * Eg.- deletePos ('0123456789',4,6) -> '0123789'
	 * @param __strString The target string
	 * @param __iIni The initial position to start deleting
	 * @param __iEnd The position until deleting 
	 * @return A string with the positions deleted 
	 */	
	public static String deletePos(String __strString,int __iIni,int __iEnd){
		if (__strString==null || __strString.length()==0)
			return __strString;
		if (__iIni < 0)
			__iIni=0;
		if (__iEnd>= __strString.length())
			__iEnd=__strString.length()-1;
		String strRes;
		strRes = __strString.substring(0,__iIni);
		if (__iEnd+1<__strString.length())
			strRes +=  __strString.substring(__iEnd+1);
		return strRes;
	}
	
	/**
	 * Delete a portion of a string specifying an initial position and an amount to delete
	 * Eg.- deletePos ('0123456789',0,5) -> '56789'
	 * @param __strString The target string
	 * @param __iIni The initial position to start deleting
	 * @param __iAmount The amount to delete
	 * @return A string with the positions deleted 
	 */
	public static String deleteCant(String __strString,int __iIni,int __iAmount){
		int j=__iIni+__iAmount -1;
		return deletePos (__strString,__iIni,j);
	}
	
	//Depure "java_math_BigInteger" 
	private static String DepureBigInteger(String __strString){
		String pivot="new java.math.BigInteger(java.lang.Integer.toString(";
		String strRes=__strString;
		Integer i=strRes.indexOf(pivot);
		while (i!=-1){
			Integer j=strRes.indexOf("))",i);
			strRes=deleteCant(strRes,j,2);
			strRes=deleteCant(strRes,i,pivot.length());
			i=strRes.indexOf(pivot);
		}
		return strRes;
	}
	
	/**
	 * Take out the "translation"
	 * @param __strString
	 * @return
	 */
	public static String depureBusinessAction(String __strString){
		String strRes=__strString;
		Integer i=strRes.indexOf("translation.");
		while (i!=-1){
			String strAux="";
			String strAux2="";
			Integer j=i;
			while (strRes.charAt(j)!=')')
				strAux+=strRes.charAt(j++);
			strAux+=")";
			strAux2=DepureTranslation(strAux);
			strRes =strRes.substring(0,i)+ " "+ strAux2 + strRes.substring(i+strAux.length());
			strRes=strRes.trim();
			i=strRes.indexOf("translation.");
		}
		
		if ( hasB2X(strRes) )
			strRes=DepureB2X(strRes);

		if(hasBigInteger(strRes))		
			strRes=DepureBigInteger(strRes);
		return strRes;
	}

	/**
	 * Returns the conditional. EG.-"Person.yearsOfResidence >= 5" return ">="
	 * @param __strTest A single and complete conditional (eg.-"Person.yearsOfResidence >= 5")
	 * @return the conditional if exists, else null
	 */
	public static String getConditionPart(String __strTest) {
		Integer i=__strTest.indexOf("<");
		if (i==-1)
			i=__strTest.indexOf(">");
		if (i==-1)
			return null;
		String res=""+__strTest.charAt(i);
		if (i+1< __strTest.length() && __strTest.charAt(i+1)=='=')
			res+= __strTest.charAt(i+1);
		return res;
	}

	/**
	 * Returns the first part of the conditional. EG.-"Person.yearsOfResidence >= 5" return "Person.yearsOfResidence"
	 * @param __strTest
	 * @return the first part, else return null (if doesn't exist)
	 */
	public static String getConditionFirstPart(String __strTest) {
		Integer i=__strTest.indexOf("<");
		if (i==-1)
			i=__strTest.indexOf(">");
		if (i==-1  || i==0)
			return null;
		return __strTest.substring(0,i).trim();
	}

	/**
	 * Returns the second part of the conditional. EG.-"Person.yearsOfResidence >= 5" return "5"
	 * @param __strTest
	 * @return the second part, else return null (if doesn't exist)
	 */
	public static String getConditionSecondPart(String __strTest) {
		Integer i=__strTest.indexOf("<");
		if (i==-1)
			i=__strTest.indexOf(">");
		if (i==-1  || i==0)
			return null;
		if (i+1< __strTest.length() && __strTest.charAt(i+1)=='=')
			i=i+2;
		else
			i++;
		if (i>=__strTest.length())
			return null;
		return  __strTest.substring(i).trim();
	}
	
}