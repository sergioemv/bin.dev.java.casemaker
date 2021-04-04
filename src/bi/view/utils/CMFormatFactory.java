package bi.view.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.text.SimpleDateFormat;
import java.util.Date;
import model.BusinessRules;
import model.ICMFormula;
import model.ICMValue;
import model.TestDataFormat;
import java.util.Arrays;
import java.lang.reflect.Array;
import java.text.ParsePosition;

import org.apache.log4j.Logger;

import bi.controller.StructureManager;

/**
 * Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
public class CMFormatFactory {
    protected CMFormatFactory() {
    }

    public static CMFormatFactory getInstance() {
        if (instance == null) {
            instance = new bi.view.utils.CMFormatFactory();
        }
        return instance;
    }

    //svonborries_20092006_begin
    static public String formatNumberWithLocale(Number p_number, Locale p_locale, String pattern){
    	NumberFormat numberFormatter;
    	String amountOut;
    	DecimalFormat df;
    	
    	numberFormatter = NumberFormat.getNumberInstance(p_locale);
    	df = (DecimalFormat) numberFormatter;
    	df.applyPattern(pattern);
    	amountOut = df.format(p_number);
    	return amountOut;
    }
    //svonborries_20092006_end
    
    //svonborries_20092006_begin
    static public String formatNumberWithLocale(Number p_number, Locale p_locale){
    	NumberFormat numberFormatter;
    	String amountOut;
    	
    	numberFormatter = NumberFormat.getNumberInstance(p_locale);
    	amountOut = numberFormatter.format(p_number);
    	return amountOut;
    }
    //svonborries_20092006_end
    
    ////svonborries_09112006_begin
    public static String formatNumber(String pattern, Number value) throws Exception{
    	
    	String realFormat = pattern;
    	int point = realFormat.indexOf(".");
    	int coma = realFormat.indexOf(",");
    	DecimalFormatSymbols symbols = new DecimalFormatSymbols();
    	String formatedNumber = new String();

    	if(!(pattern.equalsIgnoreCase(BusinessRules.FORMULAS_FORMAT_STRING))){
    		if(pattern.indexOf("%") != -1){
    			value = value.doubleValue()/100;
    		}
    		if(coma > 0 && point > 0){
        		if(coma < point){
            		//we know that the decimal separator is '.'
        			symbols.setDecimalSeparator('.');
            		symbols.setGroupingSeparator(',');
            		
            		if(pattern.indexOf("#")== -1)
        				throw new Exception();
            		
            		NumberFormat formatter = new DecimalFormat(realFormat,symbols);
            		formatedNumber = formatter.format(value);
            	}
            	else{
            		//we know that the decimal separator is ','
            		String groupingPart = realFormat.substring(0, coma);
            		String decimalPart = realFormat.substring(coma);
            		
            		groupingPart = groupingPart.replace('.', ',');
            		decimalPart = decimalPart.replace(',', '.');
            		realFormat = groupingPart + decimalPart;
            		
            		symbols.setDecimalSeparator(',');
            		symbols.setGroupingSeparator('.');
            		
            		if(pattern.indexOf("#")== -1)
        				throw new Exception();
            		
            		NumberFormat formatter = new DecimalFormat(realFormat,symbols);
            		formatedNumber = formatter.format(value);
            	}
        	}
        	else if(coma == -1 && point >0){
        		symbols.setDecimalSeparator('.');
        		symbols.setGroupingSeparator(',');
        		
        		if(pattern.indexOf("#")== -1)
    				throw new Exception();
        		
        		NumberFormat formatter = new DecimalFormat(realFormat,symbols);
        		formatedNumber = formatter.format(value);
        	}
        	else if(point == -1 && coma >0){
//        		we know that the decimal separator is '.'
        		realFormat = realFormat.replace(',', '.');
        		
        		symbols.setDecimalSeparator(',');
        		symbols.setGroupingSeparator('.');
        		
        		if(pattern.indexOf("#")== -1)
    				throw new Exception();
        		
        		NumberFormat formatter = new DecimalFormat(realFormat,symbols);
        		formatedNumber = formatter.format(value);
        	}
        	else if(pattern.equalsIgnoreCase("####")){
        		NumberFormat formatter = new DecimalFormat(pattern);
            	formatedNumber = formatter.format(value);
        	}
        	else{
        		if(StructureManager.getSelectedStructure().getTestObject().isDefaultSeparator()){
            		symbols.setDecimalSeparator('.');
            		symbols.setGroupingSeparator(',');
            	}
            	else{
            		symbols.setDecimalSeparator(StructureManager.getSelectedStructure().getTestObject().getDecimalSeparator().charAt(0));
            		symbols.setGroupingSeparator(StructureManager.getSelectedStructure().getTestObject().getMilesSeparator().charAt(0));
            	}
        		NumberFormat formatter;
        		if(pattern.equalsIgnoreCase(""))
        			
        			//formatter = new DecimalFormat("###,###.###",symbols);
        			formatter = new DecimalFormat(BusinessRules.FORMULAS_FORMAT_DEFAULT_MATH_TRIGONOMETRY,symbols);
        		else{
        			if(pattern.indexOf("#")== -1)
        				throw new Exception();
        			formatter = new DecimalFormat(pattern,symbols);
        		}
            	formatedNumber = formatter.format(value);
        	}
    	}
    	else{
    		if(StructureManager.getSelectedStructure().getTestObject().isDefaultSeparator()){
        		symbols.setDecimalSeparator('.');
        		symbols.setGroupingSeparator(',');
        	}
        	else{
        		symbols.setDecimalSeparator(StructureManager.getSelectedStructure().getTestObject().getDecimalSeparator().charAt(0));
        		symbols.setGroupingSeparator(StructureManager.getSelectedStructure().getTestObject().getMilesSeparator().charAt(0));
        	}
        	//NumberFormat formatter = new DecimalFormat("###,###.###",symbols);
    		NumberFormat formatter;
    		if(pattern.equalsIgnoreCase(""))
    			//formatter = new DecimalFormat("###,###.###",symbols);
    			formatter = new DecimalFormat(BusinessRules.FORMULAS_FORMAT_DEFAULT_MATH_TRIGONOMETRY,symbols);
    		else{
    			if((pattern.indexOf("#")== -1)&& !(pattern.equalsIgnoreCase(BusinessRules.FORMULAS_FORMAT_STRING)))
    				throw new Exception();
    			if((pattern.equalsIgnoreCase(BusinessRules.FORMULAS_FORMAT_STRING))){
    				//formatter = new DecimalFormat("###,###.###",symbols);
    				formatter = new DecimalFormat(BusinessRules.FORMULAS_FORMAT_DEFAULT_MATH_TRIGONOMETRY,symbols);
    			}else
    				formatter = new DecimalFormat(pattern,symbols);
    		}
    			
        	formatedNumber = formatter.format(value);
    	}
    	
    	
    	return formatedNumber;
    	
    	
//    	if(realFormat.indexOf(str))
//    	NumberFormat myFormatter = new DecimalFormat(pattern);
//        String output = myFormatter.format(value);
//        return output;
//    	DecimalFormatSymbols symbols = new DecimalFormatSymbols();
//    	if(StructureManager.getSelectedStructure().getTestObject().isDefaultSeparator()){
//    		symbols.setDecimalSeparator('.');
//    		symbols.setGroupingSeparator(',');
//    	}
//    	else{
//    		symbols.setDecimalSeparator(StructureManager.getSelectedStructure().getTestObject().getDecimalSeparator().charAt(0));
//    		symbols.setGroupingSeparator(StructureManager.getSelectedStructure().getTestObject().getMilesSeparator().charAt(0));
//    	}
//    	NumberFormat formatter = new DecimalFormat(pattern,symbols);
//    	String formatedNumber = formatter.format(value);
//    	
//		return formatedNumber;
    }
//  svonborries_09112006_end
    static public String formatNumber(TestDataFormat pattern, String value) {
        try {
            value = clearSign(value);

            double number=0;

            
            
        	/*if(isVisualFormatter(m_OldpatternValue))
        	{
            	 number = NumberFormat.getInstance(secondLocale).parse(value).doubleValue();
        	}
        	else{*/
        		/*if(m_OldpatternValue.getRealFormat().equals(BusinessRules.FORMULAS_FORMAT_STRING))
        			number = NumberFormat.getInstance(testObejctLocale).parse(value).doubleValue();
        		else*/
            	 number = NumberFormat.getInstance(m_OldpatternValue.getFormatLocale()).parse(value).doubleValue();
        	//}

            NumberFormat nf = NumberFormat.getNumberInstance(pattern.getFormatLocale());
            DecimalFormat myFormatter = (DecimalFormat)nf;
            myFormatter.applyPattern(pattern.getRealFormat());
            String output = myFormatter.format(number);

            setSuccessFormated(true);
            return output;
        }
        catch (Exception ex) {

        	Logger.getLogger(CMFormatFactory.class).error(ex.getMessage());
            setSuccessFormated(false);
            return value;

        }
    }

     static public String formatPercent(TestDataFormat pattern, String value) {
        try {
        	boolean toconvert=false;
            if(value.indexOf("%")!=-1)
                toconvert=true;// value=convertToNumberFromPercent(value);
            //else{
            	value = clearSign(value);
            	double number=0;
                /*if(isVisualFormatter(m_OldpatternValue))
            	{
                	 number = NumberFormat.getInstance(secondLocale).parse(value).doubleValue();
            	}
            	else{*/
            		/*if(m_OldpatternValue.getRealFormat().equals(BusinessRules.FORMULAS_FORMAT_STRING))
            			number = NumberFormat.getInstance(testObejctLocale).parse(value).doubleValue();
            		else*/
            			number = NumberFormat.getInstance(m_OldpatternValue.getFormatLocale()).parse(value).doubleValue();
            	//}
                /*NumberFormat nf = NumberFormat.getNumberInstance(pattern.getFormatLocale());
                DecimalFormat myFormatter = (DecimalFormat)nf;
                myFormatter.applyPattern("###.###################");
                value = myFormatter.format(number);*/
            //}
            //double percent = NumberFormat.getInstance(firstLocale).parse(value).doubleValue();
            if(toconvert){
            	number= number/100;
            }
            //NumberFormat percentFormatter;
            String percentOut;
            NumberFormat nf = NumberFormat.getNumberInstance(pattern.getFormatLocale());
            DecimalFormat myFormatter = (DecimalFormat)nf;
            myFormatter.applyPattern(pattern.getRealFormat());
            percentOut = myFormatter.format(number);
            setSuccessFormated(true);
            return percentOut;
        }
        catch (Exception ex) {
        	Logger.getLogger(CMFormatFactory.class).error(ex.getMessage());
            setSuccessFormated(false);
            return value;
        }
    }

    static public String formatDate(TestDataFormat pattern, String value) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(m_OldpatternValue.getRealFormat(),m_OldpatternValue.getFormatLocale());//new SimpleDateFormat(BusinessRules.FORMULAS_FORMAT_DATE_DATENOW,currentLocale);
            formatter.setLenient(false);
            Logger.getLogger(CMFormatFactory.class).debug(formatter.get2DigitYearStart());
           ParsePosition pp= new ParsePosition(0);
            Date date = formatter.parse(value,pp);
            Logger.getLogger(CMFormatFactory.class).debug(pp.getIndex());
            Logger.getLogger(CMFormatFactory.class).debug(value.trim().length());
 	        if(pp.getIndex()< value.trim().length())
                date=null;

            //  SimpleDateFormat formatter;
            String output;
            formatter = new SimpleDateFormat(pattern.getRealFormat(), pattern.getFormatLocale());
            output = formatter.format(date);
            index = 0;
            setSuccessFormated(true);
            return output;
        }
        catch (Exception ex) {
        	Logger.getLogger(CMFormatFactory.class).error(ex.getMessage());
            setSuccessFormated(false);
            TestDataFormat tryFormat= new TestDataFormat();
            tryFormat.setRealFormat(BusinessRules.TESTDATA_FORMAT_DATE[index]);
            return formatDate(pattern, value, tryFormat);//BusinessRules.TESTDATA_FORMAT_DATE[index]);
            //value;
        }
    }

    static public String formatDate(TestDataFormat pattern, String value, TestDataFormat oldPattern) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(oldPattern.getRealFormat(),oldPattern.getFormatLocale());
           formatter.setLenient(false);
           ParsePosition pp= new ParsePosition(0);
            Date date = formatter.parse(value,pp);
            Logger.getLogger(CMFormatFactory.class).debug(pp.getIndex());
            Logger.getLogger(CMFormatFactory.class).debug(value.trim().length());
            Logger.getLogger(CMFormatFactory.class).debug(oldPattern);
            if(pp.getIndex()< value.trim().length())
                date=null;
            //  SimpleDateFormat formatter;
            String output;
            formatter = new SimpleDateFormat(pattern.getRealFormat(),pattern.getFormatLocale());
            output = formatter.format(date);
            index = 0;
            setSuccessFormated(true);
            return output;
        }
        catch (Exception ex) {
        	Logger.getLogger(CMFormatFactory.class).error(ex.getMessage());
            index++;
            if (index < Array.getLength(BusinessRules.TESTDATA_FORMAT_DATE)) {
                setSuccessFormated(false);
                TestDataFormat tryFormat= new TestDataFormat();
                tryFormat.setRealFormat(BusinessRules.TESTDATA_FORMAT_DATE[index]);
                return formatDate(pattern, value, tryFormat);
            }
            else
            {
				index=0;
                return value;
            }
        }
    }

    static public String formatDate(TestDataFormat pattern, Date date, String value) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(pattern.getRealFormat(),pattern.getFormatLocale());
            formatter.setLenient(false);
            String output;
            output = formatter.format(date);
            setSuccessFormated(true);
            return output;
        }
        catch (Exception ex) {
        	Logger.getLogger(CMFormatFactory.class).debug(ex.getMessage());
            setSuccessFormated(false);
            return value;
        }
    }
    
    static public String formatDate(String pattern, Date value){
    	try {
    		if(pattern.indexOf("#")!= -1)
				return null;
        	SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        	formatter.setLenient(false);
        	String formattedDate;
        	formattedDate = formatter.format(value);
        	return formattedDate;
		} catch (Exception e) {
			try{
				SimpleDateFormat formatter;
				GregorianCalendar calendar = new GregorianCalendar();
				calendar.setTime(value);
				if((calendar.get(Calendar.YEAR)==1970)){
					formatter = new SimpleDateFormat("HH:mm:ss");
				}
				else{
					formatter = new SimpleDateFormat("yyyy/MM/dd");
				}
	        	formatter.setLenient(false);
	        	String formattedDate;
	        	formattedDate = formatter.format(value);
	        	return formattedDate;
			}
			catch (Exception f){
				Logger.getLogger(CMFormatFactory.class).debug(e.getMessage());
				return null;
			}
		}

    }

    /*  private static boolean ismodificVisualFormula(String visual, String pattern)
    {
        try{
        int lengthVisual= visual.length();
        System.out.println(lengthVisual);
        int lengthPattern=pattern.length();
        System.out.println(lengthPattern);
		int indexVisual=0;
        int indexPattern=0;
        char ccVisual= visual.charAt(0);
        char ccPattern=pattern.charAt(0);
        boolean resultFinal=false;
        while(!resultFinal){
			System.out.println(indexVisual);
            System.out.println(indexPattern);
			if(ccVisual== ccPattern){

                if(indexVisual<lengthVisual)
             			ccVisual= visual.charAt(indexVisual);
                if(indexPattern<lengthPattern)
             			ccPattern=pattern.charAt(indexPattern);
                indexVisual++;
        	    indexPattern++;
            }
            else{*
               if(/ccVisual=='.'||/ ccVisual==','|| ccVisual=='#')
                {

                	if(indexVisual<lengthVisual)
             			ccVisual= visual.charAt(indexVisual);
                	indexVisual++;
                }
                else
                if(/*ccPattern=='.'|| ccPattern==','||/ ccPattern=='#')
                {

          		      if(indexPattern<lengthPattern)
             			ccPattern=pattern.charAt(indexPattern);
          			indexPattern++;
                }
                else
                    return false;
            }
            if(indexVisual==lengthVisual && indexPattern==lengthPattern)
                return true;
            else
                if(indexVisual>lengthVisual || indexPattern>lengthPattern)
                	return false;
        }

        return ccPattern==ccVisual;
        }
        catch(Exception ex)
        {
            return false;
        }
    }*/
  /*  private static int indexVisulaFormatter(TestDataFormat pattern){
        Vector visualFormaters= new Vector(Arrays.asList(BusinessRules.TESTDATA_FORMAT_VISUAL_FORMATTERS));
		for(int i=0; i< visualFormaters.size();i++)
            {
                String formatvisual=visualFormaters.elementAt(i).toString();
                if(pattern.startsWith(formatvisual) || formatvisual.startsWith(pattern))
                    return i;
            }
            return-1;
    }*/
    public static String applyAnyFormat( TestDataFormat newpattern, String value, TestDataFormat oldpattern) {
        m_OldpatternValue=oldpattern;
        
        /*if(oldpattern.equals(BusinessRules.FORMULAS_FORMAT_STRING)){
        	setLocale(testObejctLocale);
        }*/
        
        /*if(oldpattern.equals(BusinessRules.FORMULAS_FORMAT_STRING)){
        	setLocale(testObejctLocale);
        }*/
        
     /*   if(isVisualFormatter(newpattern))
        {
             int indexVisualPattern=indexVisulaFormatter(newpattern);
             StringBuffer newPatternForVisual= new StringBuffer();
             for(int i=0; i<newpattern.length();i++)
             {
                char cc=newpattern.charAt(i);
                if(cc==','){
                    newPatternForVisual.append(".");
                }
                else{
                    if(cc=='.'){
                        newPatternForVisual.append(",");
                    }
                    else{
                        newPatternForVisual.append(cc);
                    }
                }

             }
             newpattern = newPatternForVisual.toString();
            //newpattern=BusinessRules.TESTDATA_FORMAT_REAL_FORMATTERS[indexVisualPattern];
            setLocale(secondLocale);
        }*/
        
        if(newpattern.getRealFormat().equals(BusinessRules.FORMULAS_FORMAT_STRING))
			setSuccessFormated(true);
        else
        	setSuccessFormated(false);
        
        if (!newpattern.equals("") && !newpattern.getRealFormat().equals(BusinessRules.FORMULAS_FORMAT_STRING)) {
            if (Arrays.binarySearch(BusinessRules.TESTDATA_FORMAT_DATE, newpattern.getRealFormat()) >= 0) {
                return formatDate(newpattern, value);
            }
            else {
                if (newpattern.getRealFormat().indexOf("%") >= 0 && validNumber(value.trim())) {
                    String result= formatPercent(newpattern, value);
                    //setLocale(firstLocale);
                    return result;
                }
                else {
                    if(validNumber(value)){
      /*  if(isVisualFormatter(newpattern))
        {
             int indexVisualPattern=indexVisulaFormatter(newpattern);
             StringBuffer newPatternForVisual= new StringBuffer();
             for(int i=0; i<newpattern.length();i++)
             {
                char cc=newpattern.charAt(i);
                if(cc==','){
                    newPatternForVisual.append(".");
                }
                else{
                    if(cc=='.'){
                        newPatternForVisual.append(",");
                    }
                    else{
                        newPatternForVisual.append(cc);
                    }
                }

             }
             newpattern = newPatternForVisual.toString();
            //newpattern=BusinessRules.TESTDATA_FORMAT_REAL_FORMATTERS[indexVisualPattern];
            setLocale(secondLocale);
        }*/

                    	 String result= formatNumber(newpattern, value.trim());
                         //etLocale(firstLocale);
                    	 return result;
                    }
                    else{
                        //setLocale(firstLocale);
                        return value;
                    }
                }
            }
        }
        else{
            //setLocale(firstLocale);
            return value;
        }
    }

  /*  public static void setLocale(Locale m_Locale) {
        currentLocale = m_Locale;
    }

    public static Locale getLocale() {
        return currentLocale;
    }*/

    public static String clearMoneyPercentSign(String value) {
        String auxValue = new String(value.trim());
        for (int i = 0; i < Array.getLength(BusinessRules.TESTDATA_FORMAT_MONEYSIGN); i++) {
            String auxSign = BusinessRules.TESTDATA_FORMAT_MONEYSIGN[i];
            if (auxValue.endsWith(auxSign)) {
                try {
                    auxValue = auxValue.substring(0, auxValue.lastIndexOf(auxSign));
                }
                catch (Exception ex) {
                    auxValue = "";
                }
            }
            if (auxValue.startsWith(auxSign)) {
                try {
                    auxValue = auxValue.substring(auxSign.length());
                }
                catch (Exception ex) {
                    auxValue = "";
                }
            }
        }
        if (auxValue.endsWith("%")) {
            try {
                auxValue = auxValue.substring(0, auxValue.lastIndexOf("%"));
            }
            catch (Exception ex) {
                auxValue = "";
            }
        }
        return auxValue;
    }
	public static String convertToNumberFromPercent(String value, TestDataFormat p_Formatter)
    {
         try {
            value = clearSign(value);
            double number=0;
        		if(p_Formatter.getRealFormat().equals(BusinessRules.FORMULAS_FORMAT_STRING))
        			number = NumberFormat.getInstance(testObejctLocale).parse(value).doubleValue();
        		else
        			number = NumberFormat.getInstance(p_Formatter.getFormatLocale()).parse(value).doubleValue();
            
        	//number = NumberFormat.getInstance().parse(value).doubleValue();
            number= number/100;
            NumberFormat nf = NumberFormat.getNumberInstance(p_Formatter.getFormatLocale());
            DecimalFormat myFormatter = (DecimalFormat)nf;
            myFormatter.applyPattern("###.###################");
            String output = Double.toString(number);//myFormatter.format(number);
            return output;
        }
        catch (Exception ex) {
        	Logger.getLogger(CMFormatFactory.class).error(ex.getMessage());
            return value;
        }

    }
    private static boolean validNumber(String value) {
		boolean swvalidnumber=true;
        String auxValue = clearMoneyPercentSign(value);
        auxValue=auxValue.trim();
        char point='.';
        char coma=',';
        for(int i=0; i<auxValue.length();i++)
        {
            char cc= auxValue.charAt(i);
            if((!Character.isDigit(cc)) && (cc!=point ) && (cc!=coma)&& (cc!='-')&& (cc!='E'))
            {
                swvalidnumber=false;
            }
        }
        return swvalidnumber;
    }

    private static String clearSign(String value) {
        boolean sw = false;
        for (int i = 0; i < Array.getLength(BusinessRules.TESTDATA_FORMAT_MONEYSIGN); i++) {
            String prefix = BusinessRules.TESTDATA_FORMAT_MONEYSIGN[i];
            if (value.startsWith(prefix))
                sw = true;
        }
        if (value.length() > 0 && sw) {
            int i = 0;
            char cc = value.charAt(i);
            while (!Character.isDigit(cc) && !value.startsWith(".")) {
                value = value.substring(1);
                cc = value.charAt(i);
            }
        }
        return value;
    }

    public static boolean isSuccessFormated(){
            return successFormated;
        }

    public static void setSuccessFormated(boolean p_successFormated){
            successFormated = p_successFormated;
        }

    /**
     * @link
     * @shapeType PatternLink
     * @pattern Singleton
     * @supplierRole Singleton factory
     */
static public Date stringToDate(String value) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(BusinessRules.FORMULAS_FORMAT_DATE_DATENOW,testObejctLocale);
            Logger.getLogger(CMFormatFactory.class).debug(formatter.get2DigitYearStart());
           ParsePosition pp= new ParsePosition(0);
            Date date = formatter.parse(value,pp);
 	        if(pp.getIndex()< value.trim().length())
                date=null;
			if(date ==null)
            {
                throw new Exception();
            }
            //  SimpleDateFormat formatter;

            index = 0;

            return date;
        }
        catch (Exception ex) {
        	Logger.getLogger(CMFormatFactory.class).error(ex.getMessage());
            setSuccessFormated(false);
            return stringToDate( value, BusinessRules.TESTDATA_FORMAT_DATE[index]);
            //value;
        }
    }

    static public Date stringToDate(String value, String oldPattern) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(oldPattern, new Locale("en", "US"));
           formatter.setLenient(false);
           ParsePosition pp= new ParsePosition(0);
            Date date = formatter.parse(value,pp);
            if(pp.getIndex()< value.trim().length())
                date=null;
			if(date ==null)
            {
                throw new Exception();
            }
            index=0;
            return date;
        }
        catch (Exception ex) {
        	Logger.getLogger(CMFormatFactory.class).error(ex.getMessage());
            index++;
            if (index < Array.getLength(BusinessRules.TESTDATA_FORMAT_DATE)) {
                setSuccessFormated(false);
                return stringToDate( value, BusinessRules.TESTDATA_FORMAT_DATE[index]);
            }
            else
            {
				index=0;
                return null;
            }
        }
    }

    /*# private CMFormatFactory _cmFormatFactory; */

    static public void setTestObjectLocale(Locale p_Locale){
    	testObejctLocale=p_Locale;
    }
    private static Locale testObejctLocale=new Locale("de","DE");
    private static CMFormatFactory instance = null;
   // private static Locale currentLocale = new Locale("en", "US");
   // private static Locale secondLocale = new Locale("de","DE");
   // private static Locale firstLocale = new Locale("en", "US");
    private static int index = 0;
    private static boolean successFormated=false;
    private static TestDataFormat m_OldpatternValue=null;
	public static Locale getTestObejctLocale() {
		return testObejctLocale;
	}

	public static void setTestObejctLocale(Locale testObejctLocale) {
		CMFormatFactory.testObejctLocale = testObejctLocale;
	}
	
	public static String applyFormatToICMValue(ICMValue p_value, TestDataFormat p_formatter){
		String value = "";
		try {
			if(p_value instanceof ICMValue){
				//if(!(p_formatter.getRealFormat().equalsIgnoreCase(BusinessRules.FORMULAS_FORMAT_STRING))){
					if(p_value.getValue() instanceof Number){
						value = formatNumber(p_formatter.getVisualFormatter(), (Number)p_value.getValue());
						successFormated = true;

						return value;
					}
					else if(p_value.getValue() instanceof Date){
						if(p_value instanceof ICMFormula){
							if(p_formatter.getVisualFormatter().equalsIgnoreCase(BusinessRules.FORMULAS_FORMAT_STRING)){
								value = formatDate(((ICMFormula)p_value).getFormulaEnum().getDefaultPattern()
										, (Date)p_value.getValue());
								if(value == null)
									successFormated = false;
								else
									successFormated = true;

								return value;
							}
							else{
								value = formatDate(p_formatter.getVisualFormatter(), (Date)p_value.getValue());
								if(value == null)
									successFormated = false;
								else
									successFormated = true;

								return value;
							}
						}
						value = formatDate(p_formatter.getVisualFormatter(), (Date)p_value.getValue());
						if(value == null)
							successFormated = false;
						else
							successFormated = true;

						return value;
					}
					if(p_formatter.getVisualFormatter().equalsIgnoreCase("Text")){
						value = (String) p_value.getValue().toString();//probar si funciona
						successFormated = true;
					}
					else{
						value = (String) p_value.getValue().toString();//probar si funciona
						successFormated = false;
					}


					return value;
				//}
				//else{
				//	value = (String) p_value.getValue().toString();//probar si funciona
				//	successFormated = true;
				//	return value;
				//}
			}
		} catch (Exception e) {
			successFormated = false;
		}
		return value;
		
	}

	public static String updatePatternToCurrentTestObject(String pattern) {
		if(StructureManager.getSelectedStructure().getTestObject().getDecimalSeparator().equalsIgnoreCase(",")){
			String realFormat = pattern;
	    	int point = realFormat.indexOf(".");
	    	int coma = realFormat.indexOf(",");
	    	
	    	if(point > coma){
	    		String groupingPart = realFormat.substring(0, coma+1);
	    		String decimalPart = realFormat.substring(coma+1);
	    		
	    		groupingPart = groupingPart.replace(',', '.');
	    		decimalPart = decimalPart.replace('.', ',');
	    		realFormat = groupingPart + decimalPart;
	    		return realFormat;
	    	}
	    	else
	    		return realFormat;
		}
		return pattern;
	}
	
}
