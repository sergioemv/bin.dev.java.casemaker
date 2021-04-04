/*******************************************************************************
Developed by BUSINESS SOFTWARE INNOVATIONS. .
Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
*******************************************************************************/

package model;
import java.io.File;

import bi.view.lang.CMMessages;
import bi.view.utils.CMFormatFactory;

/**
 * @type Business Rule
 */
public class BusinessRules {

    public static final String APPLICATIONNAME = "CaseMaker";
    public static final String APPLICATIONVERSION = "2.7";
    public static final String BUILDDATE = "07072008";
    public static final String BUILDNUMBER = "197 ";
    public static final String JARTARGET = "casemaker.jar";
    public static String applicationPath() {
    File f = new File("");

    String applicationPath = f.getAbsolutePath();
    return applicationPath.replace('\\','/');
    }
    
    public static final String UILOOKANDFEEL_WINDOWS = "WindowsLookAndFeel";
    public static final String ACCESS_USER_GUEST = "Guest";
    public static final String ACCESS_STATE_CHECKED_IN = "Checked in";
    public static final String ACCESS_STATE_CHECKED_OUT = "Checked out";
    public static final int TEST_CASE_GROUP_POSITION_OF_POSITIVES = 0;
    public static final int TEST_CASE_GROUP_POSITION_OF_NEGATIVES = 1;
    public static final int TEST_CASE_GROUP_POSITION_OF_FAULTIES    = 2;
    public static final int TEST_CASE_GROUP_POSITION_OF_IRRELEVANTS = 3;

    //grueda_16072004_begin
    public static final int    MAX_NUMBER_OF_IDS = 10000;

    public static final int    MAX_NUMBER_OF_POSITIVE_TEST_CASES = 6000;
    public static final int    MIN_NUMBER_OF_POSITIVE_TEST_CASES = 200;

    public static final int    MAX_NUMBER_OF_NEGATIVE_TEST_CASES = 6000;
    public static final int    MIN_NUMBER_OF_NEGATIVE_TEST_CASES = 400;

    public static final int    MAX_NUMBER_OF_FAULTY_TEST_CASES   = 3000;
    public static final int    MIN_NUMBER_OF_FAULTY_TEST_CASES   = 400;

    public static final int    MAX_NUMBER_OF_COMBINATIONS = 15000;
    public static final int    MIN_NUMBER_OF_COMBINATIONS = 999;
    //grueda_19072004_begin
    public static final String LICENSE_TYPE_DEMO = "DEMO";
    public static final String LICENSE_TYPE_FULL = "FULL";
    //grueda_19072004_end

    //grueda_16072004_end

    public static final String DOCUMENT_HELP_CONTENTS = "help.pdf";
    public static final String NEW_LINE = "\n";
    public static final String COLON = ":";
    //grueda_05082004_begin
    public static final String SEMICOLON = ";";
    //grueda_05082004_end
    public static final String SPACE = " ";
    public static final String COMMA = ",";
    public static final String POINT = ".";
    //hmendez_26112005_begin
    public static final String AND = "AND";
    public static final String OR = "OR";
    public static final String OPEN_PARENTHESES = "(";
    public static final String CLOSED_PARENTHESES = ")";
    public static final String OPEN_SQUARE_BRACKET = "[";
    public static final String CLOSED_SQUARE_BRACKET = "]";
    public static final String OPEN_CURLY_BRACKET = "{";
    public static final String CLOSED_CURLY_BRACKET = "}";
    public static final String THEN = "THEN";
    public static final String DOUBLE_QUOTE = "\"";
    public static final String SIGN_ISGREATERTHAN = ">";
    public static final String DEPENDENT_ELEMENTS_LABEL = "Dependent Elements";
    //hmendez_26112005_end
    public static final int ACCESS_CHECK_OUT_ACCEPTED = 0;
    public static final int ACCESS_CHECK_OUT_REJECTED = 1;
    public static final int ACCESS_CHECK_IN_ACCEPTED =  2;
    public static final int ACCESS_CHECK_IN_REJECTED =  3;
    public static final int ACCESS_CHECK_IN_USER_REJECTED = 4;

    public static final String FILE_PROJECT_EXTENSION = ".cpr.xml";
    public static final String FILE_TESTOBJECT_EXTENSION = ".cto.xml";
    public static final String FILE_SESSION_FILE_NAME = "cm.ini.xml";

    public static final String TEST_OBJECTS_FOLDER = "testobjects";

    public static final int    ID_LENGTH = 3;
    public static final String ID_FILLER_CHARACTER = "0";
    public static final String CAUSE_EFFECT_SEPARATOR = ", ";
    public static final String DEPENDENCY_COMBINATION_SEPARATOR = ".";
    public static final String COMBINATION_SEPARATOR = "/";
    public static final int    MIN_NUMBER_OF_DEPENDENT_ELEMENTS = 2;
    public static final String EQUAL = "=";

    public static final String EQUIVALENCECLASS_IN_COMBINATION = "\u25CF";
    public static final String EQUIVALENCECLASS_NOT_IN_COMBINATION = "";


   //////////////////// STRUCTURE VIEWS INDEXES ///////////////////////////////
   public static final int STRUCTURE_VIEW_PROJECT                   = 0;
   public static final int STRUCTURE_VIEW_TEST_OBJECT               = 1;
   public static final int STRUCTURE_VIEW_ELEMENTS                  = 2;
   public static final int STRUCTURE_VIEW_CAUSE_EFFECTS             = 3;
   public static final int STRUCTURE_VIEW_DEPENDENCIES_COMBINATIONS = 4;
   public static final int STRUCTURE_VIEW_TEST_CASES                = 5;
   public static final int STRUCTURE_VIEW_STANDARD_COMBINATIONS 		= 6;


     ///////////////////// FORMATS ///////////////////////////////////////////////////////////
     public static final String FORMAT_XML = "xml";
     public static final String FORMAT_RTF = "rtf";
     public static final String FORMAT_HTML = "html";
     public static final String FORMAT_PLAIN_TEXT = "plain text";
     public static final String FORMAT_CSV = "csv";
     public static final String FORMAT_EXCEL = "xls";

     //////////////////// LABELS ///////////////////////////////////////////////////////////////
	 public static final String ERROR_STATE_OPEN = "open";
     public static final String ERROR_STATE_IN_WORK = "in work";
     public static final String ERROR_STATE_CLOSED = "closed";

     public static final String ERROR_PRIORITY_HIGH = "high";
     public static final String ERROR_PRIORITY_MIDDLE = "middle";
     public static final String ERROR_PRIORITY_LOW = "low";

     public static final String ERROR_CLASS_A = "A";
     public static final String ERROR_CLASS_B = "B";
     public static final String ERROR_CLASS_C = "C";
     public static final String ERROR_CLASS_D = "D";

     //////////////////// INTERNET EXPLORER PATH //////////////////////////////
     public static final String PATH_INTERNET_EXPLORER = "C:"+BusinessRules.URL_SEPARATOR+"programme"+BusinessRules.URL_SEPARATOR+
     "internet explorer"+BusinessRules.URL_SEPARATOR+"iexplore.exe ";
     //public static final String PATH_INTERNET_EXPLORER = "D:/Archivos de programa/Internet Explorer/iexplore.exe ";
     public static final String PATH_EXCEL_APPLICATION = "C:"+BusinessRules.URL_SEPARATOR+
     "Programme"+BusinessRules.URL_SEPARATOR+"Microsoft Office"+BusinessRules.URL_SEPARATOR+"Office"+BusinessRules.URL_SEPARATOR+"excel.exe";
     //public static final String PATH_EXCEL_APPLICATION = "E:/windowsXP/Microsoft Office/Office10/excel.exe";

	 public static final String REPORT_CSV_SEPARATOR = "|";//svonborries_01092006
      public static String REPORT_IMAGEFLD = "images";


    ////////////////////////// RISK MANAGEMENT //////////////////////////////////////////////////////////////////
    public static int    RISK_NUM_OF_LEVELS = 11;
    public static String RISK_LEVEL_LABEL_ZERO = "0";
    public static String RISK_LEVEL_LABEL_ONE = "1";
    public static String RISK_LEVEL_LABEL_TWO = "2";
    public static String RISK_LEVEL_LABEL_THREE = "3";
    public static String RISK_LEVEL_LABEL_FOUR = "4";
    public static String RISK_LEVEL_LABEL_FIVE = "5";
    public static String RISK_LEVEL_LABEL_SIX = "6";
    public static String RISK_LEVEL_LABEL_SEVEN = "7";
    public static String RISK_LEVEL_LABEL_EIGHT = "8";
    public static String RISK_LEVEL_LABEL_NEIN = "9";
    public static String RISK_LEVEL_LABEL_TEN = "10";
////////////////////////////////////TEST DATA COMBOBOX ITEMS///////////////////////////////////////
	public static final String TESTDATA_STATE_CHAR = "char";
    public static final String TESTDATA_STATE_INT = "int";
    public static final String TESTDATA_STATE_BOOLEAN = "boolean";
    public static final String TESTDATA_STATE_FLOAT = "float";
    public static final String TESTDATA_STATE_DATETIME = "dateTime";
    public static final String TESTDATA_STATE_BINARY = "binary";
    public static final String TESTDATA_STATE_BIT = "bit";
    public static final String TESTDATA_STATE_DECIMAL = "decimal";
    public static final String TESTDATA_STATE_MONEY ="money";
    public static final String TESTDATA_STATE_NCHAR ="nChar";
    public static final String TESTDATA_STATE_NTEXT = "nText";
    public static final String TESTDATA_STATE_NUMERIC = "numeric";
    public static final String TESTDATA_STATE_NVARCHAR = "nVarChar";
    public static final String TESTDATA_STATE_REAL ="real";
    public static final String TESTDATA_STATE_SMALLDATETIME = "smalldateTime";
    public static final String TESTDATA_STATE_SMALLINT = "smallInt";
    public static final String TESTDATA_STATE_SMALLMONEY ="smallMoney";
    public static final String TESTDATA_STATE_TEXT = "text";
    public static final String TESTDATA_STATE_TIMESTAMP = "timeStamp";
    public static final String TESTDATA_STATE_TINYINT = "tinyInt";
    public static final String TESTDATA_STATE_VARBINARY = "varBinary";
    public static final String TESTDATA_STATE_VARCHAR = "varChar";



	public static final String TESTDATA_STATE_CHAR_DEFAULT_VALUE = "10";
    public static final String TESTDATA_STATE_INT_DEFAULT_VALUE = "4";
    public static final String TESTDATA_STATE_BOOLEAN_DEFAULT_VALUE = "5";
    public static final String TESTDATA_STATE_FLOAT_DEFAULT_VALUE = "8";
    public static final String TESTDATA_STATE_DATETIME_DEFAULT_VALUE = "8";
    public static final String TESTDATA_STATE_BINARY_DEFAULT_VALUE = "10";
    public static final String TESTDATA_STATE_BIT_DEFAULT_VALUE = "1";
    public static final String TESTDATA_STATE_DECIMAL_DEFAULT_VALUE = "9";
    public static final String TESTDATA_STATE_MONEY_DEFAULT_VALUE ="8";
    public static final String TESTDATA_STATE_NCHAR_DEFAULT_VALUE ="10";
    public static final String TESTDATA_STATE_NTEXT_DEFAULT_VALUE = "16";
    public static final String TESTDATA_STATE_NUMERIC_DEFAULT_VALUE = "9";
    public static final String TESTDATA_STATE_NVARCHAR_DEFAULT_VALUE = "50";
    public static final String TESTDATA_STATE_REAL_DEFAULT_VALUE ="4";
    public static final String TESTDATA_STATE_SMALLDATETIME_DEFAULT_VALUE = "4";
    public static final String TESTDATA_STATE_SMALLINT_DEFAULT_VALUE = "2";
    public static final String TESTDATA_STATE_SMALLMONEY_DEFAULT_VALUE ="4";
    public static final String TESTDATA_STATE_TEXT_DEFAULT_VALUE = "16";
    public static final String TESTDATA_STATE_TIMESTAMP_DEFAULT_VALUE = "8";
    public static final String TESTDATA_STATE_TINYINT_DEFAULT_VALUE = "1";
    public static final String TESTDATA_STATE_VARBINARY_DEFAULT_VALUE = "50";
    public static final String TESTDATA_STATE_VARCHAR_DEFAULT_VALUE = "50";
    public static final String TESTDATA_NEW_LINE ="\n";
    public static final String TESTDATA_SEPARATOR = "|" ;//svonborries_01092006
    public static final String TESTDATA_EMPTY_AT_EQUIVALENCE_CLASS_IN_TEST_CASE = "TestDataEmptyUnknownValueInTestCaseByHCL24";
    //////////////////////////////////Formulas//////////////////////////////////////////////////
	public static final Object FORMULAS_CATEGORY[]={"Date and Time","Mathematics","Text","Trigonometry","Constants"};
	public static final Object FORMULAS_CATEGORY_DATETIME[]={"Date",/*"Date360",*/"DateNow",/*"DateSum"*/"AddDay","AddMonth","AddYear","DayWeek","DiffDate","Hour","Month",/*"NewDat",*/"Seconds","Today","Year"};
    public static final Object FORMULAS_CATEGORY_MATH[]={"Abs","Ceil","Difference","Div","Exp","Fact","Floor","Ln","Log","Log10","Max","Min","Pow","Product","Random",/*"Rest",/*"Rint",*/"Roman","Round","Sqrt","Sum"};//Linea Modificada reemplazar la linea
    public static final Object FORMULAS_CATEGORY_TEXT[]={"Concat","LowerCase","Replace","RepeatString","SubString","Trim","TrimAll","UpperCase"};

    public static final Object FORMULAS_CATEGORY_TRIGONOMETRY[]={"ACos","ACosHyp","ASen","ASenHyp","ATan","ATanHyp","Cos","CosHyp","Sen","SenHyp","Tan","TanHyp","toDegrees","toRadians" };
     public static final Object FORMULAS_CATEGORY_CONSTANTS[]={"C","E","G","PI"};
    //////////////////////////Formulas  DateTime////////////////////////////////////////
    public static final String FORMULAS_CATEGORY_DATETIME_DATENOW="DateNow()";
    public static final String FORMULAS_CATEGORY_DATETIME_DATE360="Date360(init_day,final_Day)";
    public static final String FORMULAS_CATEGORY_DATETIME_DAYWEEK="DayWeek("+CMMessages.getString("PARAM_FORMULAS_DATE")+")";
    public static final String FORMULAS_CATEGORY_DATETIME_DATE="Date("+CMMessages.getString("PARAM_FORMULAS_YEAR")+"; "+CMMessages.getString("PARAM_FORMULAS_MONTH")+"; "+CMMessages.getString("PARAM_FORMULAS_DAY1")+")";
    public static final String FORMULAS_CATEGORY_DATETIME_HOUR="Hour()";
    public static final String FORMULAS_CATEGORY_DATETIME_MONTH="Month()";
    public static final String FORMULAS_CATEGORY_DATETIME_NEWDAT="NewDat(Date1, Date2)";
    public static final String FORMULAS_CATEGORY_DATETIME_SECONDS="Seconds("+CMMessages.getString("PARAM_FORMULAS_HOURS")+")";
    public static final String FORMULAS_CATEGORY_DATETIME_TODAY="Today()";
    public static final String FORMULAS_CATEGORY_DATETIME_YEAR="Year()";
    public static final String FORMULAS_CATEGORY_DATETIME_DATESUM="AddDay("+CMMessages.getString("PARAM_FORMULAS_DATE")+"; "+CMMessages.getString("PARAM_FORMULAS_DIA")+")";
    public static final String FORMULAS_CATEGORY_DATETIME_ADDMONTH = "AddMonth("+CMMessages.getString("PARAM_FORMULAS_DATE")+"; "+CMMessages.getString("PARAM_FORMULAS_MONTH")+")";
    public static final String FORMULAS_CATEGORY_DATETIME_ADDYEAR = "AddYear("+CMMessages.getString("PARAM_FORMULAS_DATE")+"; "+CMMessages.getString("PARAM_FORMULAS_YEAR")+")";
	public static final String FORMULAS_CATEGORY_DATETIME_DIFFDATE="DiffDate("+CMMessages.getString("PARAM_FORMULAS_DATE1")+"; "+CMMessages.getString("PARAM_FORMULAS_DATE2")+")";
    ////////////////////////////formulas dAtatiME descriptions/////////////////////////////
	public static final String FORMULAS_CATEGORY_DATETIME_DATENOW_DESCRIPTION="Return  the date and hours current; this function do not recive params";
   // public static final String FORMULAS_CATEGORY_DATETIME_DATE360_DESCRIPTION="calculate the numbers of days between two dates,to base  in a year of 360 days";
    public static final String FORMULAS_CATEGORY_DATETIME_DAYWEEK_DESCRIPTION=" identificate a day of the week based in a given to date(yyyy/mm/dd)";
    public static final String FORMULAS_CATEGORY_DATETIME_DATE_DESCRIPTION="covert the param given in a format Date (first param = yyyy; second param = MM; thirt param = DD )";
    public static final String FORMULAS_CATEGORY_DATETIME_HOUR_DESCRIPTION="return the hour in curse";
    public static final String FORMULAS_CATEGORY_DATETIME_MONTH_DESCRIPTION="return de actual Month";
   // public static final String FORMULAS_CATEGORY_DATETIME_NEWDAT_DESCRIPTION="return a new Date after sum or subtraction two Dates";
    public static final String FORMULAS_CATEGORY_DATETIME_SECONDS_DESCRIPTION="returns the amount of seconds what have in an hour (Hour:Min:Sec 0<=hour<=24, 0<=Min<=60 and 0<=Sec<=60) ";
    public static final String FORMULAS_CATEGORY_DATETIME_TODAY_DESCRIPTION="return date of today";
    public static final String FORMULAS_CATEGORY_DATETIME_YEAR_DESCRIPTION="return the present Year";
     public static final String FORMULAS_CATEGORY_DATETIME_DATESUM_DESCRIPTION="return a new Date after sum or subtraction Days to given Date(yyyy/mm/dd)";
	public static final String FORMULAS_CATEGORY_DATETIME_DIFFDATE_DESCRIPTION="calculate the numbers of days between two dates ";

    /////////////////////////////Formula Math////////////////////////////////////////////
	public static final String FORMULAS_CATEGORY_MATH_ABS ="Abs("+CMMessages.getString("PARAM_FORMULAS_NUMBER")+")";
   public static final String FORMULAS_CATEGORY_MATH_CEIL="Ceil("+CMMessages.getString("PARAM_FORMULAS_NUMBER")+"; "+CMMessages.getString("PARAM_FORMULAS_NUM_DECIMAL")+")";
   public static final String FORMULAS_CATEGORY_MATH_DIV ="Div("+CMMessages.getString("PARAM_FORMULAS_NUMY")+"; "+CMMessages.getString("PARAM_FORMULAS_NUMX")+")";
   public static final String FORMULAS_CATEGORY_MATH_EXP= "Exp("+CMMessages.getString("PARAM_FORMULAS_NUMBER")+")";
   public static final String FORMULAS_CATEGORY_MATH_FACT= "Fact("+CMMessages.getString("PARAM_FORMULAS_NUMBER")+")";
   public static final String FORMULAS_CATEGORY_MATH_FLOOR="Floor("+CMMessages.getString("PARAM_FORMULAS_NUMBER")+"; "+CMMessages.getString("PARAM_FORMULAS_NUM_DECIMAL")+")";
   public static final String FORMULAS_CATEGORY_MATH_LN= "Ln("+CMMessages.getString("PARAM_FORMULAS_NUMBER")+")";
   public static final String FORMULAS_CATEGORY_MATH_LOG= "Log("+CMMessages.getString("PARAM_FORMULAS_NUMBER")+"; "+CMMessages.getString("PARAM_FORMULAS_BASE")+")";
   public static final String FORMULAS_CATEGORY_MATH_LOG10= "Log10("+CMMessages.getString("PARAM_FORMULAS_NUMBER")+")";
   public static final String FORMULAS_CATEGORY_MATH_MAX="Max("+CMMessages.getString("PARAM_FORMULAS_NUMX")+"; "+CMMessages.getString("PARAM_FORMULAS_NUMY")+")";
   public static final String FORMULAS_CATEGORY_MATH_MIN= "Min("+CMMessages.getString("PARAM_FORMULAS_NUMX")+"; "+CMMessages.getString("PARAM_FORMULAS_NUMY")+")";
   public static final String FORMULAS_CATEGORY_MATH_POW="Pow("+CMMessages.getString("PARAM_FORMULAS_NUMBER")+"; "+CMMessages.getString("PARAM_FORMULAS_POWER")+")";
   public static final String FORMULAS_CATEGORY_MATH_PRODUCT= "Product("+CMMessages.getString("PARAM_FORMULAS_NUMX")+"; "+CMMessages.getString("PARAM_FORMULAS_NUMY")+")";
   public static final String FORMULAS_CATEGORY_MATH_REST= "Difference("+CMMessages.getString("PARAM_FORMULAS_NUMX")+"; "+CMMessages.getString("PARAM_FORMULAS_NUMY")+")";
   public static final String FORMULAS_CATEGORY_MATH_SQRT="Sqrt("+CMMessages.getString("PARAM_FORMULAS_NUMBER")+")";
   public static final String FORMULAS_CATEGORY_MATH_SUM="Sum("+CMMessages.getString("PARAM_FORMULAS_NUMX")+"; "+CMMessages.getString("PARAM_FORMULAS_NUMY")+")";
   public static final String FORMULAS_CATEGORY_MATH_RANDOM="Random()";
   public static final String FORMULAS_CATEGORY_MATH_RINT="Rint(num)";
   public static final String FORMULAS_CATEGORY_MATH_ROMAN= "Roman("+CMMessages.getString("PARAM_FORMULAS_NUMBER")+")";
   public static final String FORMULAS_CATEGORY_MATH_ROUND="Round("+CMMessages.getString("PARAM_FORMULAS_NUMBER")+"; "+CMMessages.getString("PARAM_FORMULAS_NUM_DECIMAL")+")";
   ///////////////////////////FORMULAS MATH DESCRIPTION/////////////////////////////////
	public static final String FORMULAS_CATEGORY_MATH_ABS_DESCRIPTION ="Bravery absolute of the param num";
   public static final String FORMULAS_CATEGORY_MATH_CEIL_DESCRIPTION="round num at enterie more short, not least what num";
   public static final String FORMULAS_CATEGORY_MATH_DIV_DESCRIPTION="return a result of div numY beteween num Y";
   public static final String FORMULAS_CATEGORY_MATH_EXP_DESCRIPTION= "method exponential (e elevated at num)";
   public static final String FORMULAS_CATEGORY_MATH_FACT_DESCRIPTION= "Return a factorial of num ";
   public static final String FORMULAS_CATEGORY_MATH_FLOOR_DESCRIPTION="round num at enterie more large, not bigger what num";
   public static final String FORMULAS_CATEGORY_MATH_LN_DESCRIPTION= "return logarithm natural the num (base e)";
   public static final String FORMULAS_CATEGORY_MATH_LOG_DESCRIPTION= "logarithm in any base (especific in base) the num";
   public static final String FORMULAS_CATEGORY_MATH_LOG10_DESCRIPTION= "logarithm in  base 10 the num";
   public static final String FORMULAS_CATEGORY_MATH_MAX_DESCRIPTION="return maximum number between  numX and num Y ";
   public static final String FORMULAS_CATEGORY_MATH_MIN_DESCRIPTION= "return minimun number beteween numX and numY";
   public static final String FORMULAS_CATEGORY_MATH_PRODUCT_DESCRIPTION= "return Product beteween numX and numY";
   public static final String FORMULAS_CATEGORY_MATH_REST_DESCRIPTION= "return rest beteween numX and numY";
   public static final String FORMULAS_CATEGORY_MATH_ROMAN_DESCRIPTION= "Convert num into a Roman num";
   public static final String FORMULAS_CATEGORY_MATH_POW_DESCRIPTION=" return num elevated at exp";
   public static final String FORMULAS_CATEGORY_MATH_SQRT_DESCRIPTION="return the root Square to num";
   public static final String FORMULAS_CATEGORY_MATH_SUM_DESCRIPTION="return a result of add numX and num Y";
   public static final String FORMULAS_CATEGORY_MATH_RANDOM_DESCRIPTION="return a random num (between 0 and 1)";
   public static final String FORMULAS_CATEGORY_MATH_RINT_DESCRIPTION="return Round num";
   public static final String FORMULAS_CATEGORY_MATH_ROUND_DESCRIPTION="return Round num";

///////////////////////////////formulas Text////////////////////////////////////////////////
public static final String FORMULAS_CATEGORY_TEXT_CONCAT="Concat("+CMMessages.getString("PARAM_FORMULAS_STRING1")+"; "+CMMessages.getString("PARAM_FORMULAS_STRING2")+")";
public static final String FORMULAS_CATEGORY_TEXT_LOWERCASE="LowerCase("+CMMessages.getString("PARAM_FORMULAS_STRING")+")";
public static final String FORMULAS_CATEGORY_TEXT_SUBSTRING="SubString("+CMMessages.getString("PARAM_FORMULAS_STRING")+";"+CMMessages.getString("PARAM_FORMULAS_LOCATION")+")";
public static final String FORMULAS_CATEGORY_TEXT_REMPLACE="Replace("+CMMessages.getString("PARAM_FORMULAS_STRING")+";"+CMMessages.getString("PARAM_FORMULAS_OLDSTRING")+";"+CMMessages.getString("PARAM_FORMULAS_NEWSTRING")+")";
public static final String FORMULAS_CATEGORY_TEXT_REPEATSTRING="RepeatString("+CMMessages.getString("PARAM_FORMULAS_STRING")+";"+CMMessages.getString("PARAM_FORMULAS_COMPARESTRING")+")";
public static final String FORMULAS_CATEGORY_TEXT_TRIM="Trim("+CMMessages.getString("PARAM_FORMULAS_STRING")+")";
public static final String FORMULAS_CATEGORY_TEXT_TRIMALL="TrimAll("+CMMessages.getString("PARAM_FORMULAS_STRING")+")";
public static final String FORMULAS_CATEGORY_TEXT_UPPERCASE="UpperCase("+CMMessages.getString("PARAM_FORMULAS_STRING")+")";
public static final String FORMULAS_CATEGORY_TEXT_STRINGTONUM = "StringToNum(" + CMMessages.getString("PARAM_FORMULAS_STRING") + ")";
public static final String FORMULAS_CATEGORY_TEXT_NUMTOSTRING = "NumToString(" + CMMessages.getString("PARAM_FORMULAS_NUMBER") + ")";
public static final String FORMULAS_CATEGORY_TEXT_RSTRING = "RString(" + CMMessages.getString("PARAM_FORMULAS_STRING" + ";" + CMMessages.getString("PARAM_FORMULAS_POSITION") + ")");
public static final String FORMULAS_CATEGORY_TEXT_LSTRING = "LString(" + CMMessages.getString("PARAM_FORMULAS_STRING" + ";" + CMMessages.getString("PARAM_FORMULAS_POSITION") + ")");
/////////////////////////////////////FORMULAS TEXT DESCRIPTION/////////////////////////////
public static final String FORMULAS_CATEGORY_TEXT_CONCAT_DESCRIPTION="concatenation String1 and String2";
public static final String FORMULAS_CATEGORY_TEXT_LOWERCASE_DESCRIPTION="return LowerCase that String";
public static final String FORMULAS_CATEGORY_TEXT_SUBSTRING_DESCRIPTION="return SubString at String from the foothold Index)";
public static final String FORMULAS_CATEGORY_TEXT_REMPLACE_DESCRIPTION="Remplace in String the all apparition of OldString by NewString)";
public static final String FORMULAS_CATEGORY_TEXT_REPEATSTRING_DESCRIPTION="return a number place of repeat compareString into String";
public static final String FORMULAS_CATEGORY_TEXT_TRIM_DESCRIPTION="eliminate these space in white before and back of the String";
public static final String FORMULAS_CATEGORY_TEXT_TRIMALL_DESCRIPTION="eliminate all the space in white of the String";
public static final String FORMULAS_CATEGORY_TEXT_UPPERCASE_DESCRIPTION=" return UpperCase that String";

////////////////////////////////formulas Trigonometrics/////////////////////////////////
public static final String FORMULAS_CATEGORY_TRIGONOMETRY_SEN="Sen("+CMMessages.getString("PARAM_FORMULAS_NUMBER")+")";
public static final String FORMULAS_CATEGORY_TRIGONOMETRY_COS="Cos("+CMMessages.getString("PARAM_FORMULAS_NUMBER")+")";
public static final String FORMULAS_CATEGORY_TRIGONOMETRY_TAN="Tan("+CMMessages.getString("PARAM_FORMULAS_NUMBER")+")";
public static final String FORMULAS_CATEGORY_TRIGONOMETRY_ASEN="ASen("+CMMessages.getString("PARAM_FORMULAS_NUMBER")+")";
public static final String FORMULAS_CATEGORY_TRIGONOMETRY_ACOS="ACos("+CMMessages.getString("PARAM_FORMULAS_NUMBER")+")";
public static final String FORMULAS_CATEGORY_TRIGONOMETRY_ATAN="ATan("+CMMessages.getString("PARAM_FORMULAS_NUMBER")+")";
public static final String FORMULAS_CATEGORY_TRIGONOMETRY_SENHYP="SenHyp("+CMMessages.getString("PARAM_FORMULAS_NUMBER")+")";
public static final String FORMULAS_CATEGORY_TRIGONOMETRY_COSHYP="CosHyp("+CMMessages.getString("PARAM_FORMULAS_NUMBER")+")";
public static final String FORMULAS_CATEGORY_TRIGONOMETRY_TANHYP="TanHyp("+CMMessages.getString("PARAM_FORMULAS_NUMBER")+")";
public static final String FORMULAS_CATEGORY_TRIGONOMETRY_ASENHYP="ASenHyp("+CMMessages.getString("PARAM_FORMULAS_NUMBER")+")";
public static final String FORMULAS_CATEGORY_TRIGONOMETRY_ACOSHYP="ACosHyp("+CMMessages.getString("PARAM_FORMULAS_NUMBER")+")";
public static final String FORMULAS_CATEGORY_TRIGONOMETRY_ATANHYP="ATanHyp("+CMMessages.getString("PARAM_FORMULAS_NUMBER")+")";
public static final String FORMULAS_CATEGORY_TRIGONOMETRY_TODEGREES="toDegrees("+CMMessages.getString("PARAM_FORMULAS_ANGLE")+")";
public static final String FORMULAS_CATEGORY_TRIGONOMETRY_TORADIANS="toRadians("+CMMessages.getString("PARAM_FORMULAS_GRADE")+")";
//////////////////////////////FORMULA TRIGONOMETRY DESCRIPTIONS///////////////////////////////
public static final String FORMULAS_CATEGORY_TRIGONOMETRY_SEN_DESCRIPTION="return Sine trigonometric of num (num in radians)";
public static final String FORMULAS_CATEGORY_TRIGONOMETRY_COS_DESCRIPTION="return Cosine trigonometric of num (num in radians)";
public static final String FORMULAS_CATEGORY_TRIGONOMETRY_TAN_DESCRIPTION="return Tangent trigonometric of num (num in radians)";
public static final String FORMULAS_CATEGORY_TRIGONOMETRY_ASEN_DESCRIPTION="return AntiSine trigonometric of num (num in radians)";
public static final String FORMULAS_CATEGORY_TRIGONOMETRY_ACOS_DESCRIPTION="return AntiCosine trigonometric of num (num in radians)";
public static final String FORMULAS_CATEGORY_TRIGONOMETRY_ATAN_DESCRIPTION="AntiTangent trigonometric of num (num in radians)";
public static final String FORMULAS_CATEGORY_TRIGONOMETRY_SENHYP_DESCRIPTION="return Sine trigonometric hipervolic of num (num in radians and >1)";
public static final String FORMULAS_CATEGORY_TRIGONOMETRY_COSHYP_DESCRIPTION="return Cosine trigonometric hipervolic of num (num in radians and >1)";
public static final String FORMULAS_CATEGORY_TRIGONOMETRY_TANHYP_DESCRIPTION="return Tangent trigonometric hipervolic of num (num in radians and >1)";
public static final String FORMULAS_CATEGORY_TRIGONOMETRY_ASENHYP_DESCRIPTION="return AntiSine trigonometric hipervolic of num (num in radians and >1)";
public static final String FORMULAS_CATEGORY_TRIGONOMETRY_ACOSHYP_DESCRIPTION="return AntiCosine trigonometric hipervolic  of num (num in radians and >1 )";
public static final String FORMULAS_CATEGORY_TRIGONOMETRY_ATANHYP_DESCRIPTION="AntiTangent trigonometric hipervolic of num (num in radians and >1)";
public static final String FORMULAS_CATEGORY_TRIGONOMETRY_TODEGREES_DESCRIPTION="convert num (in radians) to degrees";
public static final String FORMULAS_CATEGORY_TRIGONOMETRY_TORADIANS_DESCRIPTION="convert num to Radians";
/////////////////////////////////FORMULAS CONSTATNTS//////////////////////////////////////////////////
public static  String FORMULAS_CATEGORY_CONSTANTS_E(){
	if(CMFormatFactory.getTestObejctLocale().getCountry().equalsIgnoreCase("DE"))
		return "2,718281";
	else
		return "2.718281";
	}
public static  String FORMULAS_CATEGORY_CONSTANTS_PI(){
	if(CMFormatFactory.getTestObejctLocale().getCountry().equalsIgnoreCase("DE"))
		return "3,14159265";
	else
		return "3.14159265";
}
public static  String FORMULAS_CATEGORY_CONSTANTS_G(){
	if(CMFormatFactory.getTestObejctLocale().getCountry().equalsIgnoreCase("DE"))
		return "6,67300E-11";
	else
	return"6.67300E-11";
}
public static  String FORMULAS_CATEGORY_CONSTANTS_C(){
	if(CMFormatFactory.getTestObejctLocale().getCountry().equalsIgnoreCase("DE"))
		return "2.99792458E-10";
	else
	return"2.99792458E-10";
}
///////////////////////////FORMULAS CONSTANT DESCRIPTION//////////////////////////////////////
public static final String FORMULAS_CATEGORY_CONSTANTS_E_DESCRIPTION="The number e, bases of the natural logarithms or neperianos, it's without a doubt the most important number of the field of the analysis.";
public static final String FORMULAS_CATEGORY_CONSTANTS_PI_DESCRIPTION="In mathematics and geometry, it's the relation between the length of the circumference and it's diameter.  It is a transcendental number, which means that:  - it is not been from any fraction (of the type p/q) - it is not been from any operation of radicación (like cubical root of which is)";
public static final String FORMULAS_CATEGORY_CONSTANTS_G_DESCRIPTION="G gravitational constant";
public static final String FORMULAS_CATEGORY_CONSTANTS_C_DESCRIPTION="speed of the light in vacuum according to the Einstein's Theory of the Relativity";
///////////////////////////////Formulas menssages////////////////////////////////////////////
public static final String FORMULAS_ERROR="#MA Error";
public static final String FORMULAS_ERROR_STRING="#Incorrect Index";
public static final String FORMULAS_ERROR_DATE="#MalFormed Date";
//////////////////////////////Structure Description///////////////////////////////////////////
public static final String DESCRIPTION_STRUCTURE ="Structure for the test data combination";
public static final String STRUCTURE_PREFIX="S";
public static final int    MAX_NUMBER_OF_STRUCTURE= 1000;

public static final int    MAX_NUMBER_OF_NEW_COLUMNS= 5;

////////////////////////////Test Data Description//////////////////////////////
public static final String TESTDATA_PREFIX="TD";
public static final int    MAX_NUMBER_OF_TESTDATA= 10000;
public static final String DESCRIPTION_TESTDATA ="Test data combination generate automatic";
///////////////////////////test data set Description///////////////////////////
public static final int    MAX_NUMBER_OF_TESTDATASET= 1000;
public static final String TESTDATASET_PREFIX="TDS";
///////////////////////////testcase in test data Description///////////////////////////
public static final int    MAX_NUMBER_OF_TESTCASEINTESTDATA= 1000;
public static final String TESTCASEINTESTDATA_PREFIX="TF";
//new Harold
////////////////////////////Formats for Formulas/////////////////////////////////////
//public static final String FORMULAS_FORMAT_DATE_DATE ="dd.MM.yy";
public static final String FORMULAS_FORMAT_DATE_DATE ="yyyy/MM/dd";
public static final String FORMULAS_FORMAT_DATE_DATENOW ="yyyy.MM.dd G 'at' hh:mm:ss z ";
public static final String FORMULAS_FORMAT_DATE_DAYWEEK= "EEE";
public static final String FORMULAS_FORMAT_DATE_HOUR="H";
public static final String FORMULAS_FORMAT_DATE_MONTH="MMM";
public static String FORMULAS_FORMAT_DATE_SECOND(){
	if(CMFormatFactory.getTestObejctLocale().getCountry().equalsIgnoreCase("DE"))
		return "###.###,###";
	else
		return "###,###.###";
}
public static final String FORMULAS_FORMAT_DATE_TODAY="MMM EE yyyy";
public static final String FORMULAS_FORMAT_DATE_YEAR="yyyy";
public static  String FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY(){
	if(CMFormatFactory.getTestObejctLocale().getCountry().equalsIgnoreCase("DE"))
		return "###.###,###";
	else
		return"###,###.###";

}
public static  String FORMULAS_FORMAT_MATH_CEIL_FLOOR_RINT_ROUND(){
	if(CMFormatFactory.getTestObejctLocale().getCountry().equalsIgnoreCase("DE"))
		return "###.###,0";
	else
		return"###,###.0";
}
public static String FORMULAS_FORMAT_MATH_FACT(){
	if(CMFormatFactory.getTestObejctLocale().getCountry().equalsIgnoreCase("DE"))
		return "###.###";
	else
		return"###,###";
}
public static String FORMULAS_FORMAT_RANDOM(){
	if(CMFormatFactory.getTestObejctLocale().getCountry().equalsIgnoreCase("DE"))
		return "0,###";
	else
		return "0.####";
}
public static  String FORMULAS_FORMAT_C(){
	if(CMFormatFactory.getTestObejctLocale().getCountry().equalsIgnoreCase("DE"))
		return "###.###.###";
	else
		return"###,###,###";
}
public static  String FORMULAS_FORMAT_E(){
	if(CMFormatFactory.getTestObejctLocale().getCountry().equalsIgnoreCase("DE"))
		return "#,#####";
	else
		return"#.######";
}
public static  String FORMULAS_FORMAT_G(){
	if(CMFormatFactory.getTestObejctLocale().getCountry().equalsIgnoreCase("DE"))
		return "#,###";
	else
		return"#.###";
}
public static  String FORMULAS_FORMAT_PI(){
	if(CMFormatFactory.getTestObejctLocale().getCountry().equalsIgnoreCase("DE"))
		return "#,########";
	else
		return"#.########";
}
public static final String FORMULAS_FORMAT_PI="#.########";
public static final String FORMULAS_FORMAT_G="#.###E00";
public static final String FORMULAS_FORMAT_C="###,###,###";
public static final String FORMULAS_FORMAT_RANDOM="0.####";
public static final String FORMULAS_FORMAT_MATH_FACT="###,###";
public static final String FORMULAS_FORMAT_MATH_CEIL_FLOOR_RINT_ROUND="###,##0.0";
public static final String FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY="###,##0.###";
public static final String FORMULAS_FORMAT_DIFFDATE = "####";
//public static final String FORMULAS_FORMAT_DEFAULT_MATH_TRIGONOMETRY="###,##0.###############";
public static final String FORMULAS_FORMAT_DEFAULT_MATH_TRIGONOMETRY="###,###.###############";
//public static final String FORMULAS_FORMAT_DATE_SECOND="###,##0.###";
public static final String FORMULAS_FORMAT_DATE_SECOND="ss";
public static final String FORMULAS_FORMAT_STRING="Text";
public static final String FORMULAS_FORMAT_EMPTY="";
/////////////////////////////////////Type Structures//////////////////////////////
public static final String TYPETDSTRUCTURE_PRIMARY ="Primary";
public static final String TYPETDSTRUCTURE_SECONDARY ="Secondary";
public static final String TYPETDSTRUCTURE_RESULT = "Result";
public static final String TESTDATA_REPORT_CSV_FILE ="CSV2.xsl";
public static final String TESTDATA_REPORT_CSV_DORMA_FILE = "CSVDorma.xsl";
public static final String TESTDATA_REPORT_RESULT_COMPARISON_FILE ="ResultComparison.xsl";
public static final String TESTDATA_REPORT_FORMATS_FILE ="Formats.xsl";


public static final String FILE_TESTDATA_EXTENSION = ".ctd.xml";
public static final String TEST_DATA_FOLDER = "testdata";
public static final String RESULT_COMPARATION_FOLDER = "Result Comparison";
public static final String FILE_REPORT_CSV_EXTENSION =".csv";
public static final String TESTDATA_IMPORT_SEPARATION_CHAR[]={String.valueOf('"'),"'","None"};
public static final String TESTDATA_IMPORT_SEPARATIONSIGN_TABSTOP="	";
public static final String TESTDATA_IMPORT_SEPARATIONSIGN_SEMICOLON=";";
public static final String TESTDATA_IMPORT_SEPARATIONSIGN_COMA=",";
public static final String TESTDATA_IMPORT_SEPARATIONSIGN_BLANK=" ";

public static final String TESTDATA_FORMAT_CATEGORY[]= {"Date and Time","Money","Numeric","Percentage","Text"};
//hcanedo_21_09_2004_begin
public static final String TESTDATA_FORMAT_MONEYSIGN[]={"€","$us","C$","£","$"};

public static final String TESTDATA_FORMAT_MONEYSIGNE[]={"###,###.## € ","###,###.##€", "###.###,## €","###.###,##€" ,"€ ###,###.##","€ ###.###,##"};
public static final String TESTDATA_FORMAT_MONEYSIGND[]={"###,###.## $us","###,###.##$us",  "###.###,## $us","###.###,##$us","$us ###,###.##","$us ###.###,##"};
public static final String TESTDATA_FORMAT_MONEYSIGNP[]={"###,###.## $","###,###.##$","###.###,## $","###.###,##$","$ ###,###.##","$ ###.###,##"};
public static final String TESTDATA_FORMAT_MONEYSIGNC[]={"###,###.## C$","###,###.##C$","###.###,## C$","###.###,##C$","C$ ###,###.##","C$ ###.###,##"};
public static final String TESTDATA_FORMAT_MONEYSIGNI[]={"###,###.## £","###,###.##£","###.###,## £","###.###,##£","£ ###,###.##","£ ###.###,##"};
public static final String TESTDATA_FORMAT_NUMERIC[]={"###,###.###","###,###.0","###,0","###.###,###","###.###,0","###.0", "#,##", "#.##", "0,##", "0.##", "####"};
public static final String TESTDATA_FORMAT_PERCENTAGE[]={"#,##%", "#.##%"};
public static final String TESTDATA_FORMAT_VISUAL_FORMATTERS[]={"###,0","###.###,## $","###.###,## $us","###.###,## C$","###.###,## £","###.###,## €",
    															"###.###,###","###.###,##$","###.###,##$us","###.###,##C$", "###.###,##£","###.###,##€",
                                                                "###.###,0","#,##","#,##%","$ ###.###,##","$us ###.###,##","0,##","C$ ###.###,##","£ ###.###,##","€ ###.###,##"};

public static final String TESTDATA_FORMAT_REAL_FORMATTERS[]={"###.0","###,###.## $","###,###.## $us","###,###.## C$","###,###.## £","###,###.## €",
    															"###,###.###","###,###.##$","###,###.##$us","###,###.##C$", "###,###.##£","###,###.##€",
                                                                "###,###.0","#.##", "#.##%","$ ###,###.##","$us ###,###.##","0.##","C$ ###,###.##","£ ###,###.##","€ ###,###.##"};

public static final String TESTDATA_FORMAT_DATE[]={"EEE",
		"EEE, MMM d, ''yy",
		"H",
		"H:mm",
		"HH:mm",
		"HH:mm:ss",
		"H:mm:ss:SSS",
		"K:mm a,z",
		"MM/dd/yy",
		"MMM",
		"MMM EE yyyy",
		"dd-MMM-yy",
		"dd.MM.yy",
		"h:mm a",
		"yyyy",
		"yyyy.MM.dd G 'at' hh:mm:ss z",
		"yyyy.MMMMM.dd GGG hh:mm aaa"
	};
//changed the examples of dates, to update the year from 2004 to 2006, by svonborries_03102006
public static final String FORMULAS_FORMAT_DATE_EXAMPLE[] ={"Tue",
		"Tue, Jun 8, '06",
		"9",
		"9:54",
		"19:54",
		"19:54:19",
		"9:54:19:749",
		"9:54 AM,GMT-04:00",
		"06/08/06",
		"Jun",
		"Jun Tue 2006",
		"08-Jun-06",
		"08.06.06",
		"9:54 AM",
		"2006",
		"2006.06.08 AD at 09:54:19 GMT-04:00",
		"2006.June.08 AD 09:54 AM"
	};

//fin


//Freddy's code
public static final String BUSINESSRULES_FOLDER_NAME ="brules";
public static final String BUSINESSRULES_FILE_NAME = "BRFile";
public static final String BUSINESSRULES_FILE_EXTENSION = ".txt";
public static final String OTHER_ENGLISH_WORD = "Other";
public static final String OTHER_GERMAN_WORD = "Elseiges";
//ends Freddy's code

//fcastro_28061004_begin
public static final String INTERNET_APPLICATION_DENOMINATIVE = "internet browser";
public static final String CHART_APPLICATION_DENOMINATIVE = "chart application";
//fcastro_28062004_end


//hcanedo 26_07_2004_Begin
public static final String REPORT_XSLT_DEFAULT = "CSV";

public static final String REPORT_XSLT_CARPET_NEW = "ExternalXSLT";

public static final String REPORT_CARPET_NAME = "/reports";
    public static final String SESSION_FILE_VERSION = "2.5";//smoreno changed 1.9 to 2.2 ccastedo changed form 2.2 to 2.3//svonborries change fron 2.4 to 2.5 to add Dorma Report
    public static final String PROJECT_FILE_VERSION = "1.5";
    public static final String TESTOBJECT_FILE_VERSION = "2.7"; // smoreno changed from 2.6 -- 2.7
    public static final String TESTDATA_FILE_VERSION = "1.9";//svonborries_30082006 "changed from 1.7 to 1.8"
//grueda22082004_end
    //grueda24082004_begin
    public static final String EXCEL_EXTENSION = ".xls";
    public static final String HTML_EXTENSION = ".html";
    public static final String TEXT_EXTENSION = ".txt";
    public static final String MSWORD_EXTENSION = ".doc";
    public static final String XML_EXTENSION = ".xml";

    public static final String REPORT_TESTDIRECTOR_ABREVIATION = "_td";
    public static final String REPORT_QADIRECTOR_ABREVIATION = "_qa";
    public static final String REPORT_COMPUWARE_ABREVIATION = "_cw";
    public static final String ACCESS_STATE_CHECKED_OUT_LOCAL = "Checked out local";
    public static final String LOCAL_DATA_FOLDER = "local";
    public static final String ZIP_EXTENSION = ".zip";
    public static final String REPORT_ERROR_ABREVIATION = "_err";
    //grueda24082004_end

//HCanedo_18_08_2004_Begin
public static final String FORMULAS_CATEGORY_CONSTANTS_E_NAME="E";
public static final String FORMULAS_CATEGORY_CONSTANTS_PI_NAME="PI";
public static final String FORMULAS_CATEGORY_CONSTANTS_G_NAME="G";
public static final String FORMULAS_CATEGORY_CONSTANTS_C_NAME="C";
//HCanedo_18_08_2004_End
//hcanedo_23082004_begin
public static final String XML_FILE_QADIRECTOR = "qadirector.xml";

public static String RESOURCE_FOLDER = applicationPath()+BusinessRules.URL_SEPARATOR+"res";

public static final String TESTDATA_REPORT_QADIRECTOR_FILERES ="QADirector.xsl";
//hcanedo_23082004_end
//Hcanedo_13_09_2004_Begin
public static final String ADD_EXTERNAL_REPORT= "externalReports";
public static final String TESTDATA_IMPICIT_VARIABLES[]={"PR","ST","TD","TO","WS"};
//hcanedo_20_10_2004_end

//hcanedo_09112004_begin
public static final String TESTDATA_REPORT_DEPENDENCY_FILE ="DependencyReport.xsl";
 public static final String REPORT_DEPENDENCY_REPORT_ABREVIATION = "_dc";
//hcanedo_09112004_end

//HCanedo_30112004_End
public static final Object[] NUMBER_CHARACTERS_SEPARATOR={",","."};
public static final String DEFAULT_DECIMAL_SEPARATOR=",";
public static final String DEFAULT_MILES_SEPARATOR=".";



//grueda03042005_begin
public static final String REPORT_TESTDIRECTOR_ENDSTEP = "END";
public static final String LABEL_SEARCH_PATTERN = "\\ Search Pattern:";
public static final String TESTDATA_REPORT_CARPET_NAME = "outputs";
//grueda03042005_end

//grueda05042005_begin
public static final String URL_SEPARATOR = "/";
//grueda05042005_end
public static final String TESTDATA_NAME_OBJECT_TESTDATA="TestData";
public static final String TESTDATA_REPORT_EXTENCION=".xml";
public static final String TESTDATA_REPORT_ARCHIVE_NAME="out1";

public static final String TESTCASE_XSLTREPORTS_CARPET=RESOURCE_FOLDER+BusinessRules.URL_SEPARATOR+"TestCaseReports";
public static final String TESTCASELIST1_XSLT_DEFAULT_FILEPATH=TESTCASE_XSLTREPORTS_CARPET+BusinessRules.URL_SEPARATOR+"TestCaseList1.xsl";
public static final String TESTCASELIST2_XSLT_DEFAULT_FILEPATH=TESTCASE_XSLTREPORTS_CARPET+BusinessRules.URL_SEPARATOR+"TestCaseList2.xsl";
public static final String TESTCASEEXPORTCSV_XSLT_DEFAULT_FILEPATH=TESTCASE_XSLTREPORTS_CARPET+BusinessRules.URL_SEPARATOR+"TestCaseCSV.xsl";
public static final String TESTCASEEXPORTQADIRECTORXML_XSLT_DEFAULT_FILEPATH=TESTCASE_XSLTREPORTS_CARPET+BusinessRules.URL_SEPARATOR+"TestCaseList2QADirector.xsl";
public static final String TESTCASELIST1EXCEL_XSLT_DEFAULT_FILEPATH=TESTCASE_XSLTREPORTS_CARPET+BusinessRules.URL_SEPARATOR+"TestCaseList1Excel.xsl";
public static final String TESTCASELIST2EXCEL_XSLT_DEFAULT_FILEPATH=TESTCASE_XSLTREPORTS_CARPET+BusinessRules.URL_SEPARATOR+"TestCaseList2Excel.xsl";
public static final String TESTCASELIST2MOD1EXCEL_XSLT_DEFAULT_FILEPATH=TESTCASE_XSLTREPORTS_CARPET+BusinessRules.URL_SEPARATOR+"TestCaseList2Excel_1.xsl";
public static final String TESTCASELISTWORKFLOW_XSLT_DEFAULT_FILEPATH = TESTCASE_XSLTREPORTS_CARPET+BusinessRules.URL_SEPARATOR+"TestCaseList2Workflow.xsl";
public static final String TESTCASELISTQA_XSLT_DEFAULT_FILEPATH=TESTCASE_XSLTREPORTS_CARPET+BusinessRules.URL_SEPARATOR+"TestCaseListQA.xsl";
public static final String DEPENDENCYCOMB_XSLT_DEFAULT_FILEPATH=TESTCASE_XSLTREPORTS_CARPET+BusinessRules.URL_SEPARATOR+"DepCombList.xsl";
public static final String TESTCASELISTCOMPUWARE_XSLT_DEFAULT_FILEPATH=TESTCASE_XSLTREPORTS_CARPET+BusinessRules.URL_SEPARATOR+"TestCaseListCompuware.xsl";
public static final String ERROR_LIST_DEFAULT_FILEPATH=TESTCASE_XSLTREPORTS_CARPET+BusinessRules.URL_SEPARATOR+"ErrorList1.xsl";
public static final String REPORT_TESTCASE_LIST_ORDERED_BY_NUMBER="Test Case List Ordered by Test Case ID";
public static final String REPORT_TESTCASE_LIST_ORDERED_BY_RISK_LEVELS="Test Case List Ordered by Risk Level";
public static final String REPORT_TESTCASE_LIST_XML_BEGIN ="<TestCaseListReport>";
public static final String REPORT_HEAD_TITLE_BEGIN = "<ReportHeadTitle>";
public static final String REPORT_HEAD_TITLE_END = "</ReportHeadTitle>";
public static final String REPORT_TITLE_BEGIN = "<ReportTitle>";
public static final String REPORT_TITLE_END = "</ReportTitle>";
public static final String REPORT_IMAGE_LOGO_BEGIN = "<HeaderImg>";
public static final String REPORT_IMAGE_END = "</HeaderImg>";
public static final String REPORT_PROJECT_LABEL_BEGIN = "<ReportProjectLabel>";
public static final String REPORT_PROJECT_LABEL_END = "</ReportProjectLabel>";
public static final String REPORT_PROJECT_VALUE_BEGIN = "<ProjectName>";
public static final String REPORT_PROJECT_VALUE_END = "</ProjectName>";
public static final String REPORT_OBJECT_LABEL_BEGIN = "<ReportTestObjectLabel>";
public static final String REPORT_OBJECT_LABEL_END = "</ReportTestObjectLabel>";
public static final String REPORT_OBJECT_VALUE_BEGIN = "<TestObjectName>";
public static final String REPORT_OBJECT_VALUE_END = "</TestObjectName>";
public static final String REPORT_OBJECT_DESCRIPTION_LABEL_BEGIN = "<ReportTODescriptionLabel>";
public static final String REPORT_OBJECT_DESCRIPTION_LABEL_END = "</ReportTODescriptionLabel>";
public static final String REPORT_OBJECT_DESCRIPTION_VALUE_BEGIN = "<TestObjectDescription>";
public static final String REPORT_OBJECT_DESCRIPTION_VALUE_END = "</TestObjectDescription>";
public static final String REPORT_OBJECT_PRECONDITIONS_LABEL_BEGIN = "<ReportTOPreconditionLabel>";
public static final String REPORT_OBJECT_PRECONDITIONS_LABEL_END = "</ReportTOPreconditionLabel>";
public static final String REPORT_OBJECT_PRECONDITIONS_VALUE_BEGIN = "<TestObjectPrecondition>";
public static final String REPORT_OBJECT_PRECONDITIONS_VALUE_END = "</TestObjectPrecondition>";
public static final String REPORT_DATE_LABEL_BEGIN = "<DateLabel>";
public static final String REPORT_DATE_LABEL_END = "</DateLabel>";
public static final String REPORT_DATE_VALUE_BEGIN = "<Date>";
public static final String REPORT_DATE_VALUE_END = "</Date>";
public static final String REPORT_USER_LABEL_BEGIN = "<ReportUserNameLabel>";
public static final String REPORT_USER_LABEL_END = "</ReportUserNameLabel>";
public static final String REPORT_USER_VALUE_BEGIN = "<UserName>";
public static final String REPORT_USER_VALUE_END = "</UserName>";
public static final String REPORT_RISKLEVEL_LABEL_BEGIN = "<RiskLevelName>";
public static final String REPORT_RISKLEVEL_LABEL_END = "</RiskLevelName>";
public static final String REPORT_RISKLEVEL_VALUE_BEGIN = "<RiskLevelValue ";
public static final String REPORT_RISKLEVEL_VALUE_ATTR1 = "Width=";
public static final String REPORT_RISKLEVEL_VALUE_ATTR2 = "Color=";
public static final String REPORT_RISKLEVEL_VALUE_ATTR3 = "Value=";
public static final String REPORT_RISKLEVEL_VALUE_END = "></RiskLevelValue>";
public static final String REPORT_TEST_CASE_LABEL_BEGIN = "<TestCaseLabel>";
public static final String REPORT_TEST_CASE_LABEL_END = "</TestCaseLabel>";
public static final String REPORT_TEST_CASE_BEGIN = "<TestCase ";
public static final String REPORT_TEST_CASE_ATTR1 = "TestCaseID= ";
public static final String REPORT_TEST_CASE_ATTR2 = "TestCaseRiskLevel=";
public static final String REPORT_TEST_CASE_ATTR3 = "TestCaseRiskLevelValue=";
public static final String REPORT_TEST_CASE_END = "</TestCase>";
public static final String REPORT_TEST_CASE_DESCRIPTION_LABEL_BEGIN = "<TestCaseDescriptionLabel>";
public static final String REPORT_TEST_CASE_DESCRIPTION_LABEL_END = "</TestCaseDescriptionLabel>";
public static final String REPORT_TEST_CASE_DESCRIPTION_VALUE_BEGIN = "<TestCaseDescription>";
public static final String REPORT_TEST_CASE_DESCRIPTION_VALUE_END = "</TestCaseDescription>";
public static final String REPORT_EQUIVALENCE_CLASS_LIST_LABEL_BEGIN = "<EquivalenceClassLabel>";
public static final String REPORT_EQUIVALENCE_CLASS_LIST_LABEL_END = "</EquivalenceClassLabel>";
public static final String REPORT_CAUSE_EFFECT_LIST_LABEL_BEGIN = "<CauseEffectLabel>";
public static final String REPORT_CAUSE_EFFECT_LIST_LABEL_END = "</CauseEffectLabel>";
public static final String REPORT_CAUSE_EFFECT_LABEL_BEGIN = "<CauseEffectNameLabel>";
public static final String REPORT_CAUSE_EFFECT_LABEL_END = "</CauseEffectNameLabel>";
public static final String REPORT_CAUSE_EFFECT_DESCRIPTION_LABEL_BEGIN = "<CauseEffectDescriptionLabel>";
public static final String REPORT_CAUSE_EFFECT_DESCRIPTION_LABEL_END = "</CauseEffectDescriptionLabel>";
public static final String REPORT_CAUSE_EFFECT_VALUE_BEGIN = "<CauseEffectName>";
public static final String REPORT_CAUSE_EFFECT_VALUE_END = "</CauseEffectName>";
public static final String REPORT_CAUSE_EFFECT_DESCRIPTION_VALUE_BEGIN = "<CauseEffectDescription>";
public static final String REPORT_CAUSE_EFFECT_DESCRIPTION_VALUE_END = "</CauseEffectDescription>";
public static final String REPORT_CAUSE_EFFECT_BEGIN = "<CauseEffect>";
public static final String REPORT_CAUSE_EFFECT_END = "</CauseEffect>";
public static final String REPORT_STD_COMBINATION_LIST_LABEL_BEGIN = "<StdCombinationLabel>";
public static final String REPORT_STD_COMBINATION_LIST_LABEL_END = "</StdCombinationLabel>";
public static final String REPORT_STD_COMBINATION_BEGIN = "<StdCombinations>";
public static final String REPORT_STD_COMBINATION_END = "</StdCombinations>";
public static final String REPORT_STD_COMBINATION_LABEL_BEGIN = "<StdCombinationNameLabel>";
public static final String REPORT_STD_COMBINATION_LABEL_END = "</StdCombinationNameLabel>";
public static final String REPORT_STD_COMBINATION_DESCRIPTION_LABEL_BEGIN = "<StdCombinationDescriptionLabel>";
public static final String REPORT_STD_COMBINATION_DESCRIPTION_LABEL_END = "</StdCombinationDescriptionLabel>";
public static final String REPORT_STD_COMBINATION_VALUE_BEGIN = "<StdCombinationId>";
public static final String REPORT_STD_COMBINATION_VALUE_END = "</StdCombinationId>";
public static final String REPORT_STD_COMBINATION_DESCRIPTION_VALUE_BEGIN = "<StdCombinationDescription>";
public static final String REPORT_STD_COMBINATION_DESCRIPTION_VALUE_END = "</StdCombinationDescription>";
public static final String REPORT_REFERENCES_OF_COMBINATIONS_LABEL_BEGIN = "<CombinationLabel>";
public static final String REPORT_REFERENCES_OF_COMBINATIONS_LABEL_END = "</CombinationLabel>";
public static final String REPORT_COMBINATION_BEGIN = "<Combination>";
public static final String REPORT_COMBINATION_END = "</Combination>";
public static final String REPORT_COMBINATION_LABEL_BEGIN = "<CombinationNameLabel>";
public static final String REPORT_COMBINATION_LABEL_END = "</CombinationNameLabel>";
public static final String REPORT_COMBINATION_DESCRIPTION_LABEL_BEGIN = "<CombinationDescriptionLabel>";
public static final String REPORT_COMBINATION_DESCRIPTION_LABEL_END = "</CombinationDescriptionLabel>";
public static final String REPORT_COMBINATION_CAUSE_EFFECTS_LABEL_BEGIN = "<CombinationEffectLabel>";
public static final String REPORT_COMBINATION_CAUSE_EFFECTS_LABEL_END = "</CombinationEffectLabel>";
public static final String REPORT_COMBINATION_VALUE_BEGIN = "<CombinationName>";
public static final String REPORT_COMBINATION_VALUE_END = "</CombinationName>";
public static final String REPORT_COMBINATION_DESCRIPTION_VALUE_BEGIN = "<CombinationDescription>";
public static final String REPORT_COMBINATION_DESCRIPTION_VALUE_END = "</CombinationDescription>";
public static final String REPORT_COMBINATION_CAUSE_EFFECTS_VALUE_BEGIN = "<CombinationEffect>";
public static final String REPORT_COMBINATION_CAUSE_EFFECTS_VALUE_END = "</CombinationEffect>";
public static final String REPORT_EQUIVALENCE_CLASS_BEGIN = "<EquivalenceClass>";
public static final String REPORT_EQUIVALENCE_CLASS_END = "</EquivalenceClass>";
public static final String REPORT_EQUIVALENCE_CLASS_LABEL_BEGIN = "<EquivalenceClassLabelName>";
public static final String REPORT_EQUIVALENCE_CLASS_LABEL_END = "</EquivalenceClassLabelName>";
public static final String REPORT_EQUIVALENCE_CLASS_VALUE_BEGIN = "<EquivalenceClassName>";
public static final String REPORT_EQUIVALENCE_CLASS_VALUE_END = "</EquivalenceClassName>";
public static final String REPORT_EQUIVALENCE_CLASS_VALUE_LABEL_BEGIN = "<EquivalenceClassValueLabel>";
public static final String REPORT_EQUIVALENCE_CLASS_VALUE_LABEL_END = "</EquivalenceClassValueLabel>";
public static final String REPORT_EQUIVALENCE_CLASS_VALUE_VALUE_BEGIN = "<EquivalenceClassValue>";
public static final String REPORT_EQUIVALENCE_CLASS_VALUE_VALUE_END = "</EquivalenceClassValue>";
public static final String REPORT_EQUIVALENCE_CLASS_DESCRIPTION_LABEL_BEGIN = "<EquivalenceClassDescriptionLabel>";
public static final String REPORT_EQUIVALENCE_CLASS_DESCRIPTION_LABEL_END = "</EquivalenceClassDescriptionLabel>";
public static final String REPORT_EQUIVALENCE_CLASS_DESCRIPTION_VALUE_BEGIN = "<EquivalenceClassDescription>";
public static final String REPORT_EQUIVALENCE_CLASS_DESCRIPTION_VALUE_END = "</EquivalenceClassDescription>";
public static final String REPORT_EQUIVALENCE_CLASS_CAUSE_EFFECTS_LABEL_BEGIN = "<EquivalenceClassEffectLabel>";
public static final String REPORT_EQUIVALENCE_CLASS_CAUSE_EFFECTS_LABEL_END = "</EquivalenceClassEffectLabel>";
public static final String REPORT_EQUIVALENCE_CLASS_CAUSE_EFFECTS_VALUE_BEGIN = "<EquivalenceClassEffect>";
public static final String REPORT_EQUIVALENCE_CLASS_CAUSE_EFFECTS_VALUE_END = "</EquivalenceClassEffect>";
public static final String REPORT_TESTCASE_LIST_XML_END = "</TestCaseListReport>";
public static final String REPORT_NUMBER_OF_TESTCASE_LABEL_BEGIN = "<NumberTestCaseLabel>";
public static final String REPORT_NUMBER_OF_TESTCASE_LABEL_END = "</NumberTestCaseLabel>";
public static final int UPDATE_COMBINATIONS = 0;
public static final int OVERWRITE_COMBINATIONS = 1;



public static final String FILE_TOOLVENDOR_FILE_EXTENSION = ".ctv.xml";
public static final String TOOLVENDORS_FOLDER = "ToolVendors";
public static final String NEW_COLUMNS_FOLDER = "NewColumns";
public static final int STATEOT_DNET = 0;
public static final String COMPUWARE = "compuware";
public static final String PATH_TOOLVENDORS = "ToolVendors"+BusinessRules.URL_SEPARATOR+BusinessRules.COMPUWARE+BusinessRules.FILE_TOOLVENDOR_FILE_EXTENSION;
public static final String DEFAULT_ToolVendor="compuware";
public static final String DEFAULT_ToolVendor_Technology="DOTNET";
public static final int DEFAULT_ToolVendorTech=0;

public static final String XSD_RES_DIRECTORY = "/res/jsx.xsd";
public static final String TOOLVENDOR = "ToolVendors";
public static final int    MAX_SIZE_OF_XML_FILE = 150;
public static final int    MIN_SIZE_OF_XML_FILE = 2;
public static final String TESTDATASET_XSLT_DEFAULT_FILEPATH=RESOURCE_FOLDER+BusinessRules.URL_SEPARATOR+TESTDATA_REPORT_CSV_FILE;
public static final String TESTDATASET_XSLT_DEFAULT_DORMA_FILEPATH = RESOURCE_FOLDER+BusinessRules.URL_SEPARATOR+TESTDATA_REPORT_CSV_DORMA_FILE;
public static final String TESTDATA_XSLTREPORTS_CARPET=RESOURCE_FOLDER+BusinessRules.URL_SEPARATOR+"ExternalXSLT";
}