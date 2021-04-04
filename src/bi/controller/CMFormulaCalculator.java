package bi.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.JOptionPane;
import bi.controller.testdata.formula.CMFormulaParameter;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.testdataviews.CMGridTDStructure;
import bi.view.testdataviews.CMIndexTDStructureUpdate;
import bi.view.utils.CMFormatFactory;

import model.BusinessRules;
import model.CMDefaultParameter;
import model.CMDefaultValue;
import model.CMLinkElement;
import model.ICMValue;
import model.ITypeData;
import model.Project2;
import model.StructureTestData;
import model.TDStructure;
import model.TestData;
import model.TestDataFormat;
import model.Variable;
import model.Variables;
import model.formula.CMAbsFormula;
import model.formula.CMAcosFormula;
import model.formula.CMAddDayFormula;
import model.formula.CMAddMonthFormula;
import model.formula.CMAddYearFormula;
import model.formula.CMCFormula;
import model.formula.CMCeilFormula;
import model.formula.CMConcatFormula;
import model.formula.CMDateFormula;
import model.formula.CMDateNowFormula;
import model.formula.CMDayWeekFormula;
import model.formula.CMDiffDateFormula;
import model.formula.CMDifferenceFormula;
import model.formula.CMDivFormula;
import model.formula.CMEFormula;
import model.formula.CMExpFormula;
import model.formula.CMFactFormula;
import model.formula.CMFloorFormula;
import model.formula.CMGFormula;
import model.formula.CMHourFormula;
import model.formula.CMLnFormula;
import model.formula.CMLog10Formula;
import model.formula.CMLogFormula;
import model.formula.CMLowerCatFormula;
import model.formula.CMMaxFormula;
import model.formula.CMMinFormula;
import model.formula.CMMonthFormula;
import model.formula.CMPIFormula;
import model.formula.CMPowFormula;
import model.formula.CMProductFormula;
import model.formula.CMRandomFormula;
import model.formula.CMRepeatStringFormula;
import model.formula.CMReplaceFormula;
import model.formula.CMRomanFormula;
import model.formula.CMRoundFormula;
import model.formula.CMSqrtFormula;
import model.formula.CMStringToNumFormula;
import model.formula.CMSubStringFormula;
import model.formula.CMSumFormula;
import model.formula.CMTodayFormula;
import model.formula.CMTrimAllFormula;
import model.formula.CMTrimFormula;
import model.formula.CMUpperCaseFormula;
import model.formula.CMYearFormula;
import model.formula.CMACosHypFormula;
import model.formula.CMAsenFormula;
import model.formula.CMAsenHypFormula;
import model.formula.CMAtanFormula;
import model.formula.CMAtanHypFormula;
import model.formula.CMCosFormula;
import model.formula.CMCosHypFormula;
import model.formula.CMSenFormula;
import model.formula.CMSenHypFormula;
import model.formula.CMTanFormula;
import model.formula.CMTanHypFormula;
import model.formula.CMToDegreesFormula;
import model.formula.CMToRadianFormula;
import model.formula.CMNumberToStringFormula;
import model.formula.CMRStringFormula;
import model.formula.CMLStringFormula;

public class CMFormulaCalculator {
    private boolean isNotValidNestedFormula;
	private String type;
	private String formatFormula;
	private TestDataFormat m_formatterSelected;
	private ICMValue result;
	private boolean useStringValue;
	private boolean recalculateFormula;
	private HashMap typeDataReferences;
	private CMGridTDStructure cmgrid;
	private boolean isAllowedVariable = true;
	private String formulaEdit;
	private String formulaSelected;
	
	public CMFormulaCalculator(CMGridTDStructure cmgrid2, String formula) {
		cmgrid = cmgrid2;
		setFormulaEdit(formula);
		setFormulaSelected(reFactoryFormula(formula));
	}
	public CMFormulaCalculator(CMGridTDStructure cmgrid2) {
		cmgrid = cmgrid2;
	}
	/**  */
    public void setType(String p_Type) {
        this.type = p_Type;
    }
    public void setFormatFormula(String p_FormatFormula) {
        formatFormula = p_FormatFormula;
    }
    public ICMValue getResult() {
        return result;
    }
    public void setResult(ICMValue result) {
        this.result = result;
    }
	public void setM_formatterSelected(TestDataFormat selected) {
		m_formatterSelected = selected;
	}

    @SuppressWarnings("unchecked")
	public String reFactoryFormula(String p_formulaEdit) {
        String nameFormula;
        Vector dateAndTime = new Vector();
        Vector mathematics = new Vector();
        Vector text = new Vector();
        Vector trigonometrics = new Vector();
        Vector constants = new Vector();
        dateAndTime.addElement(BusinessRules.FORMULAS_CATEGORY_DATETIME_DATE);
        dateAndTime.addElement(BusinessRules.FORMULAS_CATEGORY_DATETIME_DATENOW);
        dateAndTime.addElement(BusinessRules.FORMULAS_CATEGORY_DATETIME_DATESUM);
        dateAndTime.addElement(BusinessRules.FORMULAS_CATEGORY_DATETIME_ADDMONTH);
        dateAndTime.addElement(BusinessRules.FORMULAS_CATEGORY_DATETIME_ADDYEAR);
        dateAndTime.addElement(BusinessRules.FORMULAS_CATEGORY_DATETIME_DAYWEEK);
        dateAndTime.addElement(BusinessRules.FORMULAS_CATEGORY_DATETIME_DIFFDATE);
        dateAndTime.addElement(BusinessRules.FORMULAS_CATEGORY_DATETIME_HOUR);
        dateAndTime.addElement(BusinessRules.FORMULAS_CATEGORY_DATETIME_MONTH);
        dateAndTime.addElement(BusinessRules.FORMULAS_CATEGORY_DATETIME_SECONDS);
        dateAndTime.addElement(BusinessRules.FORMULAS_CATEGORY_DATETIME_TODAY);
        dateAndTime.addElement(BusinessRules.FORMULAS_CATEGORY_DATETIME_YEAR);
        mathematics.addElement(BusinessRules.FORMULAS_CATEGORY_MATH_ABS);
        mathematics.addElement(BusinessRules.FORMULAS_CATEGORY_MATH_CEIL);
        mathematics.addElement(BusinessRules.FORMULAS_CATEGORY_MATH_DIV);
        mathematics.addElement(BusinessRules.FORMULAS_CATEGORY_MATH_EXP);
        mathematics.addElement(BusinessRules.FORMULAS_CATEGORY_MATH_FACT);
        mathematics.addElement(BusinessRules.FORMULAS_CATEGORY_MATH_FLOOR);
        mathematics.addElement(BusinessRules.FORMULAS_CATEGORY_MATH_LN);
        mathematics.addElement(BusinessRules.FORMULAS_CATEGORY_MATH_LOG);
        mathematics.addElement(BusinessRules.FORMULAS_CATEGORY_MATH_LOG10);
        mathematics.addElement(BusinessRules.FORMULAS_CATEGORY_MATH_MAX);
        mathematics.addElement(BusinessRules.FORMULAS_CATEGORY_MATH_MIN);
        mathematics.addElement(BusinessRules.FORMULAS_CATEGORY_MATH_POW);
        mathematics.addElement(BusinessRules.FORMULAS_CATEGORY_MATH_PRODUCT);
        mathematics.addElement(BusinessRules.FORMULAS_CATEGORY_MATH_REST);
        mathematics.addElement(BusinessRules.FORMULAS_CATEGORY_MATH_SQRT);
        mathematics.addElement(BusinessRules.FORMULAS_CATEGORY_MATH_SUM);
        mathematics.addElement(BusinessRules.FORMULAS_CATEGORY_MATH_RANDOM);
      //  mathematics.addElement(BusinessRules.FORMULAS_CATEGORY_MATH_RINT);
        mathematics.addElement(BusinessRules.FORMULAS_CATEGORY_MATH_ROMAN);
        mathematics.addElement(BusinessRules.FORMULAS_CATEGORY_MATH_ROUND);
        text.addElement(BusinessRules.FORMULAS_CATEGORY_TEXT_CONCAT);
        text.addElement(BusinessRules.FORMULAS_CATEGORY_TEXT_LOWERCASE);
        text.addElement(BusinessRules.FORMULAS_CATEGORY_TEXT_SUBSTRING);
        text.addElement(BusinessRules.FORMULAS_CATEGORY_TEXT_REMPLACE);
        text.addElement(BusinessRules.FORMULAS_CATEGORY_TEXT_REPEATSTRING);
        text.addElement(BusinessRules.FORMULAS_CATEGORY_TEXT_TRIM);
        text.addElement(BusinessRules.FORMULAS_CATEGORY_TEXT_TRIMALL);
        text.addElement(BusinessRules.FORMULAS_CATEGORY_TEXT_UPPERCASE);
        trigonometrics.addElement(BusinessRules.FORMULAS_CATEGORY_TRIGONOMETRY_SEN);
        trigonometrics.addElement(BusinessRules.FORMULAS_CATEGORY_TRIGONOMETRY_COS);
        trigonometrics.addElement(BusinessRules.FORMULAS_CATEGORY_TRIGONOMETRY_TAN);
        trigonometrics.addElement(BusinessRules.FORMULAS_CATEGORY_TRIGONOMETRY_ASEN);
        trigonometrics.addElement(BusinessRules.FORMULAS_CATEGORY_TRIGONOMETRY_ACOS);
        trigonometrics.addElement(BusinessRules.FORMULAS_CATEGORY_TRIGONOMETRY_ATAN);
        trigonometrics.addElement(BusinessRules.FORMULAS_CATEGORY_TRIGONOMETRY_SENHYP);
        trigonometrics.addElement(BusinessRules.FORMULAS_CATEGORY_TRIGONOMETRY_COSHYP);
        trigonometrics.addElement(BusinessRules.FORMULAS_CATEGORY_TRIGONOMETRY_TANHYP);
        trigonometrics.addElement(BusinessRules.FORMULAS_CATEGORY_TRIGONOMETRY_ASENHYP);
        trigonometrics.addElement(BusinessRules.FORMULAS_CATEGORY_TRIGONOMETRY_ACOSHYP);
        trigonometrics.addElement(BusinessRules.FORMULAS_CATEGORY_TRIGONOMETRY_ATANHYP);
        trigonometrics.addElement(BusinessRules.FORMULAS_CATEGORY_TRIGONOMETRY_TODEGREES);
        trigonometrics.addElement(BusinessRules.FORMULAS_CATEGORY_TRIGONOMETRY_TORADIANS);
        constants.addElement(BusinessRules.FORMULAS_CATEGORY_CONSTANTS_E_NAME);
        constants.addElement(BusinessRules.FORMULAS_CATEGORY_CONSTANTS_C_NAME);
        constants.addElement(BusinessRules.FORMULAS_CATEGORY_CONSTANTS_G_NAME);
        constants.addElement(BusinessRules.FORMULAS_CATEGORY_CONSTANTS_PI_NAME);
        if (p_formulaEdit.indexOf("(") == -1) {
            for (int i = 0; i < constants.size(); i++) {
                String obj = constants.elementAt(i).toString();
                if (obj.startsWith(p_formulaEdit))
                    return obj;
            }
            if (p_formulaEdit.startsWith("$"))
                return p_formulaEdit;
            return "";
        }
        else
            nameFormula = p_formulaEdit.substring(0, p_formulaEdit.indexOf("("));
        if (nameFormula.equals(""))
            return "";

        for (int i = 0; i < dateAndTime.size(); i++) {
            String obj = dateAndTime.elementAt(i).toString();
            if (obj.startsWith(nameFormula)){
                if(!p_formulaEdit.trim().endsWith(")"))
        		{
                    isNotValidNestedFormula= true;
        		}
                return obj;
            }
        }
        for (int i = 0; i < mathematics.size(); i++) {
            String obj = mathematics.elementAt(i).toString();
            if (obj.startsWith(nameFormula)){
                if(!p_formulaEdit.trim().endsWith(")"))
        		{
                    isNotValidNestedFormula= true;
        		}
                return obj;
            }
        }
        for (int i = 0; i < text.size(); i++) {
            String obj = text.elementAt(i).toString();
            if (obj.startsWith(nameFormula)){
                if(!p_formulaEdit.trim().endsWith(")"))
        		{
                    isNotValidNestedFormula= true;
        		}
                return obj;
            }
        }
        for (int i = 0; i < trigonometrics.size(); i++) {
            String obj = trigonometrics.elementAt(i).toString();
            if (obj.startsWith(nameFormula)){
                if(!p_formulaEdit.trim().endsWith(")"))
        		{
                    isNotValidNestedFormula= true;
        		}
                return obj;
            }
        }
        return "";
    }
    public int cantParam(String formula) {
        int x, y;
        x = formula.indexOf("("); //$NON-NLS-1$
        y = formula.lastIndexOf(")"); //$NON-NLS-1$
        if (x + 1 == y) {
            return 0;
        }
        else if (x == y) {
            return 0;
        }
        else {
            x = formula.indexOf(";"); //$NON-NLS-1$
            y = formula.lastIndexOf(";"); //$NON-NLS-1$
            if (x == -1) {
                return 1;
            }
            else if (x == y) {
                return 2;
            }
            else {
                return 3;
            }
        }
    }
    public ICMValue calculate(String formula, ICMValue result1, ICMValue result2, ICMValue result3) {
         // String result;
        if (formula.equals(BusinessRules.FORMULAS_CATEGORY_DATETIME_DATE)) {
            try {
//                setType(BusinessRules.TESTDATA_STATE_DATETIME);
//                setFormatFormula(BusinessRules.FORMULAS_FORMAT_DATE_DATE);
//                TestDataFormat newFormatter= new TestDataFormat();
//                newFormatter.setVisualFormatter(BusinessRules.FORMULAS_FORMAT_DATE_DATE);
//                newFormatter.setOriginalFormatter(BusinessRules.FORMULAS_FORMAT_DATE_DATE);
//                newFormatter.setRealFormat(BusinessRules.FORMULAS_FORMAT_DATE_DATE);
//                setM_formatterSelected(newFormatter);
//                CMDateUtils.restrictedDateValues(param1,param2,param3);
//                DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
//                String fecha=param1+"/"+param2+"/"+param3;
//                Date d = (Date)formatter.parse(fecha);
//
//                setResult(CMFormatFactory.getInstance().formatDate(newFormatter, d, d.toString()));
                CMDefaultParameter parameter1 = new CMDefaultParameter(CMFormulaParameter.YEAR, result1);
                CMDefaultParameter parameter2 = new CMDefaultParameter(CMFormulaParameter.MONTH, result2);
                CMDefaultParameter parameter3 = new CMDefaultParameter(CMFormulaParameter.DAY, result3);
                CMDateFormula formulaDate = new CMDateFormula();
                formulaDate.addParameter(parameter1);
                formulaDate.addParameter(parameter2);
                formulaDate.addParameter(parameter3);
                setResult(formulaDate);
            }
            catch (Exception e) {
                //setResult(BusinessRules.FORMULAS_ERROR_DATE);
            	setResult(new CMDefaultValue(BusinessRules.FORMULAS_ERROR_DATE));
                //setFormatFormula(BusinessRules.FORMULAS_FORMAT_EMPTY);
            }
            return getResult();
       }
        else if(formula.equals(BusinessRules.FORMULAS_CATEGORY_DATETIME_DATESUM)){
           try {
//                setType(BusinessRules.TESTDATA_STATE_DATETIME);
//                setFormatFormula(BusinessRules.FORMULAS_FORMAT_DATE_DATE);
//                TestDataFormat newFormatter= new TestDataFormat();
//                newFormatter.setVisualFormatter(BusinessRules.FORMULAS_FORMAT_DATE_DATE);
//                newFormatter.setOriginalFormatter(BusinessRules.FORMULAS_FORMAT_DATE_DATE);
//                newFormatter.setRealFormat(BusinessRules.FORMULAS_FORMAT_DATE_DATE);
//                setM_formatterSelected(newFormatter);
//                Logger.getLogger(this.getClass()).info(param1.trim());
//                Date date =CMFormatFactory.getInstance().stringToDate(param1.trim());
//                if(date == null){
//                	CMDateUtils.restrictedDateValues(param1.trim());
//                    DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
//                    date = (Date)formatter.parse(param1.trim());
//                }
//                long number = (24*60*60*1000)*Long.parseLong(param2.trim());
//                Logger.getLogger(this.getClass()).info(number);
//                Calendar miCalen=Calendar.getInstance();//.setTime(date);
//                miCalen.setTime(date);
//                long timeTomillis=miCalen.getTimeInMillis();
//				long datesum= timeTomillis + number;
//				Logger.getLogger(this.getClass()).info(datesum);
//                miCalen.setTimeInMillis(datesum);
//                Date d=miCalen.getTime();
//                Logger.getLogger(this.getClass()).info(d);
//
//                setResult(CMFormatFactory.getInstance().formatDate(newFormatter, d, d.toString()));
                
                CMDefaultParameter parameter1 = new CMDefaultParameter(CMFormulaParameter.DATE, result1);
                CMDefaultParameter parameter2 = new CMDefaultParameter(CMFormulaParameter.DAY, result2);
                //CMDefaultParameter parameter3 = new CMDefaultParameter(CMFormulaParameter.DAY, new CMDefaultValue(param3));
                CMAddDayFormula formulaDate = new CMAddDayFormula();
                formulaDate.addParameter(parameter1);
                formulaDate.addParameter(parameter2);
                //formulaDate.addParameter(parameter3);
                setResult(formulaDate);
            }
            catch (Exception e) {
//                setResult(BusinessRules.FORMULAS_ERROR_DATE);
//                setFormatFormula(BusinessRules.FORMULAS_FORMAT_EMPTY);
            	setResult(new CMDefaultValue(BusinessRules.FORMULAS_ERROR_DATE));
            }
            return getResult();
      }
        //svonborries_21072006_begin
        else if(formula.equals(BusinessRules.FORMULAS_CATEGORY_DATETIME_ADDMONTH)){
        	try {
//				setType(BusinessRules.TESTDATA_STATE_DATETIME);
//				setFormatFormula(BusinessRules.FORMULAS_FORMAT_DATE_DATE);
//				TestDataFormat testDataFormatter = new TestDataFormat();
//				testDataFormatter.setVisualFormatter(BusinessRules.FORMULAS_FORMAT_DATE_DATE);
//				testDataFormatter.setOriginalFormatter(BusinessRules.FORMULAS_FORMAT_DATE_DATE);
//				testDataFormatter.setRealFormat(BusinessRules.FORMULAS_FORMAT_DATE_DATE);
//				setM_formatterSelected(testDataFormatter);
//                Date date =CMFormatFactory.getInstance().stringToDate(param1.trim());
//                if(date == null){
//                	CMDateUtils.restrictedDateValues(param1.trim());
//                    DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
//                    date = (Date)formatter.parse(param1.trim());
//                }
//                //String strDateParam1 = param1.trim();
//                SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
//                String str = df.format(date);
//                
//            	String year = str.substring(0, str.indexOf("/"));
//            	String month = str.substring(str.indexOf("/") + 1, str.lastIndexOf("/"));
//            	String day = str.substring(str.lastIndexOf("/") + 1);
//            	
//            	GregorianCalendar greCalendar = new GregorianCalendar(Integer.parseInt(year),Integer.parseInt(month)-1,Integer.parseInt(day));
//				greCalendar.add(Calendar.MONTH, Integer.parseInt(param2.trim()));
//				Date d = greCalendar.getTime();
//				setResult(CMFormatFactory.getInstance().formatDate(testDataFormatter, d, d.toString()));
				
				CMDefaultParameter parameter1 = new CMDefaultParameter(CMFormulaParameter.DATE, result1);
                CMDefaultParameter parameter2 = new CMDefaultParameter(CMFormulaParameter.MONTH, result2);
                //CMDefaultParameter parameter3 = new CMDefaultParameter(CMFormulaParameter.DAY, new CMDefaultValue(param3));
                CMAddMonthFormula formulaDate = new CMAddMonthFormula();
                formulaDate.addParameter(parameter1);
                formulaDate.addParameter(parameter2);
                //formulaDate.addParameter(parameter3);
				setResult(formulaDate);
				
			} catch (Exception e) {
				//setResult(BusinessRules.FORMULAS_ERROR_DATE);
				setResult(new CMDefaultValue(BusinessRules.FORMULAS_ERROR_DATE));
			}
        	
        	return getResult();
        }
      
        else if(formula.equals(BusinessRules.FORMULAS_CATEGORY_DATETIME_ADDYEAR)){
        	try {
        		
//				setType(BusinessRules.TESTDATA_STATE_DATETIME);
//				setFormatFormula(BusinessRules.FORMULAS_FORMAT_DATE_DATE);
//				TestDataFormat testDataFormatter = new TestDataFormat();
//				testDataFormatter.setVisualFormatter(BusinessRules.FORMULAS_FORMAT_DATE_DATE);
//				testDataFormatter.setOriginalFormatter(BusinessRules.FORMULAS_FORMAT_DATE_DATE);
//				testDataFormatter.setRealFormat(BusinessRules.FORMULAS_FORMAT_DATE_DATE);
//				setM_formatterSelected(testDataFormatter);
//                Date date =CMFormatFactory.getInstance().stringToDate(param1.trim());
//                if(date == null){
//                	CMDateUtils.restrictedDateValues(param1.trim());
//                    DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
//                    date = (Date)formatter.parse(param1.trim());
//                }
//              String strDateParam1 = param1.trim();
//                SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
//                String str = df.format(date);
//                
//            	String year = str.substring(0, str.indexOf("/"));
//            	String month = str.substring(str.indexOf("/") + 1, str.lastIndexOf("/"));
//            	String day = str.substring(str.lastIndexOf("/") + 1);
//            	
//            	GregorianCalendar greCalendar = new GregorianCalendar(Integer.parseInt(year),Integer.parseInt(month)-1,Integer.parseInt(day));
//				greCalendar.add(Calendar.YEAR, Integer.parseInt(param2.trim()));
//				Date d = greCalendar.getTime();
//				setResult(CMFormatFactory.getInstance().formatDate(testDataFormatter, d, d.toString()));
				
				CMDefaultParameter parameter1 = new CMDefaultParameter(CMFormulaParameter.DATE, result1);
                CMDefaultParameter parameter2 = new CMDefaultParameter(CMFormulaParameter.YEAR, result2);
                //CMDefaultParameter parameter3 = new CMDefaultParameter(CMFormulaParameter.DAY, new CMDefaultValue(param3));
                CMAddYearFormula formulaDate = new CMAddYearFormula();
                formulaDate.addParameter(parameter1);
                formulaDate.addParameter(parameter2);
                //formulaDate.addParameter(parameter3);
				setResult(formulaDate);
        		
				
			} catch (Exception e) {
				//setResult(BusinessRules.FORMULAS_ERROR_DATE);
				setResult(new CMDefaultValue(BusinessRules.FORMULAS_ERROR_DATE));
			}
        	
        	return getResult();
        }
      //svonborries_21072006_end
        else
        if (formula.equals(BusinessRules.FORMULAS_CATEGORY_DATETIME_DAYWEEK)) {
        	try {
//                setType(BusinessRules.TESTDATA_STATE_DATETIME);
//                setFormatFormula(BusinessRules.FORMULAS_FORMAT_DATE_DAYWEEK);
//                TestDataFormat newFormatter= new TestDataFormat();
//                newFormatter.setVisualFormatter(BusinessRules.FORMULAS_FORMAT_DATE_DAYWEEK);
//                newFormatter.setOriginalFormatter(BusinessRules.FORMULAS_FORMAT_DATE_DAYWEEK);
//                newFormatter.setRealFormat(BusinessRules.FORMULAS_FORMAT_DATE_DAYWEEK);
//                setM_formatterSelected(newFormatter);
//                String fecha = param1.trim();
//                Date d =CMFormatFactory.getInstance().stringToDate(param1.trim());
//                if(d != null)
//                {
//                	DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
//                	fecha=format.format(d);
//                }
//                CMDateUtils.restrictedDateValues(fecha);
//                DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
//                d = (Date)formatter.parse(fecha);
//
//                setResult(CMFormatFactory.getInstance().formatDate(newFormatter, d,
//                    d.toString()));
                
                CMDefaultParameter parameter1 = new CMDefaultParameter(CMFormulaParameter.DATE, result1);
                CMDayWeekFormula formulaDate = new CMDayWeekFormula();
                formulaDate.addParameter(parameter1);
                setResult(formulaDate);
            }
            catch (Exception e) {
                //setResult(BusinessRules.FORMULAS_ERROR_DATE);
            	setResult(new CMDefaultValue(BusinessRules.FORMULAS_ERROR_DATE));
                //setFormatFormula(BusinessRules.FORMULAS_FORMAT_EMPTY);
            }
           return getResult();
        }
        else if (formula.equals(BusinessRules.FORMULAS_CATEGORY_DATETIME_DATENOW)) {
            try {
//            	setType(BusinessRules.TESTDATA_STATE_DATETIME);
//                setFormatFormula(BusinessRules.FORMULAS_FORMAT_DATE_DATENOW);
//                TestDataFormat newFormatter= new TestDataFormat();
//                newFormatter.setVisualFormatter(BusinessRules.FORMULAS_FORMAT_DATE_DATENOW);
//                newFormatter.setOriginalFormatter(BusinessRules.FORMULAS_FORMAT_DATE_DATENOW);
//                newFormatter.setRealFormat(BusinessRules.FORMULAS_FORMAT_DATE_DATENOW);
//                setM_formatterSelected(newFormatter);
//            	Calendar calendar = Calendar.getInstance();
//                Date d = calendar.getTime();
//
//                setResult(CMFormatFactory.getInstance().formatDate(newFormatter, d,
//                    d.toString()));
            	CMDateNowFormula formulaDate = new CMDateNowFormula();
            	setResult(formulaDate);
            }
            catch (Exception e) {
                //setResult(BusinessRules.FORMULAS_ERROR_DATE);.
            	setResult(new CMDefaultValue(BusinessRules.FORMULAS_ERROR_DATE));
                //setFormatFormula(BusinessRules.FORMULAS_FORMAT_EMPTY);
            }
            return getResult();
        }
        else if (formula.equals(BusinessRules.FORMULAS_CATEGORY_DATETIME_TODAY)) {
            try {
//            	setType(BusinessRules.TESTDATA_STATE_DATETIME);
//                setFormatFormula(BusinessRules.FORMULAS_FORMAT_DATE_TODAY);
//                TestDataFormat newFormatter= new TestDataFormat();
//                newFormatter.setVisualFormatter(BusinessRules.FORMULAS_FORMAT_DATE_TODAY);
//                newFormatter.setOriginalFormatter(BusinessRules.FORMULAS_FORMAT_DATE_TODAY);
//                newFormatter.setRealFormat(BusinessRules.FORMULAS_FORMAT_DATE_TODAY);
//                setM_formatterSelected(newFormatter);
//            	Calendar calendar = Calendar.getInstance();
//                Date d = calendar.getTime();
//
//                setResult(CMFormatFactory.getInstance().formatDate(newFormatter, d, d.toString()));
            	CMTodayFormula formulaDate = new CMTodayFormula();
            	setResult(formulaDate);
            }
            catch (Exception e) {
                //setResult(BusinessRules.FORMULAS_ERROR_DATE);
            	setResult(new CMDefaultValue(BusinessRules.FORMULAS_ERROR_DATE));
                //setFormatFormula(BusinessRules.FORMULAS_FORMAT_EMPTY);
            }
            return getResult();
        }
        else if (formula.equals(BusinessRules.FORMULAS_CATEGORY_DATETIME_HOUR)) {
            try {
//                //setResult(Integer.toString(Calendar.getInstance().get(Calendar.HOUR_OF_DAY))); //d.getHours());
//                setType(BusinessRules.TESTDATA_STATE_DATETIME);
//                setFormatFormula(BusinessRules.FORMULAS_FORMAT_DATE_HOUR);
//                TestDataFormat newFormatter= new TestDataFormat();
//                newFormatter.setVisualFormatter(BusinessRules.FORMULAS_FORMAT_DATE_HOUR);
//                newFormatter.setOriginalFormatter(BusinessRules.FORMULAS_FORMAT_DATE_HOUR);
//                newFormatter.setRealFormat(BusinessRules.FORMULAS_FORMAT_DATE_HOUR);
//                setM_formatterSelected(newFormatter);
//                Calendar calendar = Calendar.getInstance();
//                Date d = calendar.getTime();
//
//                setResult(CMFormatFactory.getInstance().formatDate(newFormatter, d, d.toString()));
            	CMHourFormula formulaDate = new CMHourFormula();
            	setResult(formulaDate);
            }
            catch (Exception e) {
                //setResult(BusinessRules.FORMULAS_ERROR_DATE);
            	setResult(new CMDefaultValue(BusinessRules.FORMULAS_ERROR_DATE));
                //setFormatFormula(BusinessRules.FORMULAS_FORMAT_EMPTY);
            }
            return getResult();
        }
        else if (formula.equals(BusinessRules.FORMULAS_CATEGORY_DATETIME_MONTH)) {
            try {
//                //setResult(Integer.toString(Calendar.getInstance().get(Calendar.MONTH) + 1)); //d.getMonth());
//                setType(BusinessRules.TESTDATA_STATE_DATETIME);
//                setFormatFormula(BusinessRules.FORMULAS_FORMAT_DATE_MONTH);
//                TestDataFormat newFormatter= new TestDataFormat();
//                newFormatter.setVisualFormatter(BusinessRules.FORMULAS_FORMAT_DATE_MONTH);
//                newFormatter.setOriginalFormatter(BusinessRules.FORMULAS_FORMAT_DATE_MONTH);
//                newFormatter.setRealFormat(BusinessRules.FORMULAS_FORMAT_DATE_MONTH);
//                setM_formatterSelected(newFormatter);
//                Calendar calendar = Calendar.getInstance();
//                Date d = calendar.getTime();
//
//                setResult(CMFormatFactory.getInstance().formatDate(newFormatter, d, d.toString()));
            	CMMonthFormula formulaDate = new CMMonthFormula();
            	setResult(formulaDate);
            }
            catch (Exception e) {
                //setResult(BusinessRules.FORMULAS_ERROR_DATE);
            	setResult(new CMDefaultValue(BusinessRules.FORMULAS_ERROR_DATE));
                //setFormatFormula(BusinessRules.FORMULAS_FORMAT_EMPTY);
            }
            return getResult();
        }
        else if(formula.equals(BusinessRules.FORMULAS_CATEGORY_DATETIME_DIFFDATE)){
           try {
//                setType(BusinessRules.TESTDATA_STATE_DATETIME);
//                setFormatFormula(BusinessRules.FORMULAS_FORMAT_DATE_SECOND());
//                TestDataFormat newFormatter= new TestDataFormat();
//                newFormatter.setVisualFormatter(BusinessRules.FORMULAS_FORMAT_DATE_SECOND());
//                newFormatter.setOriginalFormatter(BusinessRules.FORMULAS_FORMAT_DATE_SECOND());
//                newFormatter.setRealFormat(BusinessRules.FORMULAS_FORMAT_DATE_SECOND);
//                setM_formatterSelected(newFormatter);
//                Date date1 =CMFormatFactory.stringToDate(param1.trim(),"yyyy/MM/dd");
//                if(date1 != null)
//                {
//                	DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
//                	String fecha=format.format(date1);
//                	CMDateUtils.restrictedDateValues(fecha);
//                    DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
//                    date1 = (Date)formatter.parse(fecha);
//                }
//
//				Date date2 =CMFormatFactory.stringToDate(param2.trim(),"yyyy/MM/dd");
//				if(date2 != null)
//                {
//                	DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
//                	String fecha=format.format(date2);
//                	CMDateUtils.restrictedDateValues(fecha);
//                    DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
//                    date2 = (Date)formatter.parse(fecha);
//                }
//                Calendar miCalenDate1=Calendar.getInstance();//.setTime(date);
//                Calendar miCalenDate2=Calendar.getInstance();
//                miCalenDate1.setTime(date1);
//                miCalenDate2.setTime(date2);
//                long result2 =0;
//                 if(miCalenDate1.after(miCalenDate2)){
//                    result2= (miCalenDate1.getTimeInMillis()-miCalenDate2.getTimeInMillis());
//                 }
//                 else{
//                    if(miCalenDate2.after(miCalenDate1))
//                    	result2=(miCalenDate2.getTimeInMillis()-miCalenDate1.getTimeInMillis());
//                 }
//                 if(result2 !=0)
//                 	result2=(result2/(24*60*60*1000));
//
//                setResult(CMFormatFactory.getInstance().applyAnyFormat(newFormatter,
//                   Long.toString(result2),new TestDataFormat()));
                
                
                CMDefaultParameter parameter1 = new CMDefaultParameter(CMFormulaParameter.DATE1, result1);
                CMDefaultParameter parameter2 = new CMDefaultParameter(CMFormulaParameter.DATE2, result2);
                //CMDefaultParameter parameter3 = new CMDefaultParameter(CMFormulaParameter.DAY, new CMDefaultValue(param3));
                CMDiffDateFormula formulaDate = new CMDiffDateFormula();
                formulaDate.addParameter(parameter1);
                formulaDate.addParameter(parameter2);
                //formulaDate.addParameter(parameter3);
				setResult(formulaDate);
            }
            catch (Exception e) {
                //setResult(BusinessRules.FORMULAS_ERROR_DATE);
            	setResult(new CMDefaultValue(BusinessRules.FORMULAS_ERROR_DATE));
               // setFormatFormula(BusinessRules.FORMULAS_FORMAT_EMPTY);
            }
            return getResult();

      }else
        if (formula.equals(BusinessRules.FORMULAS_CATEGORY_DATETIME_SECONDS)) {
            try {
//                //setResult(timeToSeconds(param1.getText()));
//                setType(BusinessRules.TESTDATA_STATE_DATETIME);
//                setFormatFormula(BusinessRules.FORMULAS_FORMAT_DATE_SECOND());
//                TestDataFormat newFormatter= new TestDataFormat();
//                newFormatter.setVisualFormatter(BusinessRules.FORMULAS_FORMAT_DATE_SECOND());
//                newFormatter.setOriginalFormatter(BusinessRules.FORMULAS_FORMAT_DATE_SECOND());
//                newFormatter.setRealFormat(BusinessRules.FORMULAS_FORMAT_DATE_SECOND);
//                setM_formatterSelected(newFormatter);
//                setResult(CMFormatFactory.getInstance().applyAnyFormat(newFormatter,
//                    timeToSeconds(param1),new TestDataFormat()));
                
                
                CMDefaultParameter parameter1 = new CMDefaultParameter(CMFormulaParameter.HOUR, result1);
                CMHourFormula formulaDate = new CMHourFormula();
                formulaDate.addParameter(parameter1);
                setResult(formulaDate);
            }
            catch (Exception e) {
                //setResult(BusinessRules.FORMULAS_ERROR_DATE);
            	setResult(new CMDefaultValue(BusinessRules.FORMULAS_ERROR_DATE));
                //setFormatFormula(BusinessRules.FORMULAS_FORMAT_EMPTY);
            }
            return getResult();
        }
        else if (formula.equals(BusinessRules.FORMULAS_CATEGORY_DATETIME_YEAR)) {
            try {
//                //setResult(Integer.toString(Calendar.getInstance().get(1)));
//                setType(BusinessRules.TESTDATA_STATE_DATETIME);
//                setFormatFormula(BusinessRules.FORMULAS_FORMAT_DATE_YEAR);
//                TestDataFormat newFormatter= new TestDataFormat();
//                newFormatter.setVisualFormatter(BusinessRules.FORMULAS_FORMAT_DATE_YEAR);
//                newFormatter.setOriginalFormatter(BusinessRules.FORMULAS_FORMAT_DATE_YEAR);
//                newFormatter.setRealFormat(BusinessRules.FORMULAS_FORMAT_DATE_YEAR);
//                setM_formatterSelected(newFormatter);
//                Calendar calendar = Calendar.getInstance();
//                Date d = calendar.getTime();
//
//                setResult(CMFormatFactory.getInstance().formatDate(newFormatter, d, d.toString()));
            	
            	CMYearFormula formulaDate = new CMYearFormula();
            	setResult(formulaDate);
            }
            catch (Exception e) {
                //setResult(BusinessRules.FORMULAS_ERROR_DATE);
            	setResult(new CMDefaultValue(BusinessRules.FORMULAS_ERROR_DATE));
               // setFormatFormula(BusinessRules.FORMULAS_FORMAT_EMPTY);
            }
            return getResult();
        }
        else if (formula.equals(BusinessRules.FORMULAS_CATEGORY_MATH_ABS)) {
            try {
//                setType(BusinessRules.TESTDATA_STATE_FLOAT);
//                setFormatFormula(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY());
//                TestDataFormat newFormatter= new TestDataFormat();
//                newFormatter.setVisualFormatter(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY());
//                newFormatter.setOriginalFormatter(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY());
//                newFormatter.setRealFormat(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY);
//                //newFormatter.setFormatLocale(new Locale("en","US"));
//                setM_formatterSelected(newFormatter);
//                double res = Math.abs(Double.parseDouble(CMNumberUtils.defaultFormatParam(param1.trim())));
//
//                TestDataFormat old=new TestDataFormat();
//                old.setFormatLocale(new Locale("en","US"));
//                old.setVisualFormatter("###.####################");
//                old.setOriginalFormatter("###.####################");
//                old.setRealFormat("###.####################");
//                setResult(CMFormatFactory.getInstance().applyAnyFormat(newFormatter,
//                    Double.toString(res),old));
            	
            	
            	CMDefaultParameter parameter1 = new CMDefaultParameter(CMFormulaParameter.NUMBER, result1);
                CMAbsFormula formulaAbs = new CMAbsFormula();
                formulaAbs.addParameter(parameter1);
                setResult(formulaAbs);
            }
            catch (Exception e) {
                //setResult(BusinessRules.FORMULAS_ERROR);
            	setResult(new CMDefaultValue(BusinessRules.FORMULAS_ERROR));
                //setFormatFormula(BusinessRules.FORMULAS_FORMAT_EMPTY);
            }
            return getResult();
        }
        else if (formula.equals(BusinessRules.FORMULAS_CATEGORY_MATH_CEIL)) {
            try {
//                setType(BusinessRules.TESTDATA_STATE_FLOAT);
//                setFormatFormula(BusinessRules.FORMULAS_FORMAT_MATH_CEIL_FLOOR_RINT_ROUND());
//                TestDataFormat newFormatter= new TestDataFormat();
//                newFormatter.setVisualFormatter(BusinessRules.FORMULAS_FORMAT_MATH_CEIL_FLOOR_RINT_ROUND());
//                newFormatter.setOriginalFormatter(BusinessRules.FORMULAS_FORMAT_MATH_CEIL_FLOOR_RINT_ROUND());
//                newFormatter.setRealFormat(CMNumberUtils.getFormatRoundTo(Integer.parseInt(param2.trim()),BusinessRules.FORMULAS_FORMAT_MATH_CEIL_FLOOR_RINT_ROUND));                //newFormatter.setFormatLocale(new Locale("en","US"));
//                setM_formatterSelected(newFormatter);
//                double toCeilNumber=Double.parseDouble(CMNumberUtils.defaultFormatParam(param1.trim()));
//                double res =0.0;
//                if(toCeilNumber >=0)
//                	 res = CMNumberUtils.ceilTo(Integer.parseInt(param2.trim()),toCeilNumber);
//                else{
//                	 res = CMNumberUtils.ceilTo(Integer.parseInt(param2.trim()),Math.abs(toCeilNumber));
//                	 res = res * -1;
//                }
//
//                TestDataFormat old=new TestDataFormat();
//                old.setFormatLocale(new Locale("en","US"));
//                setResult(CMFormatFactory.getInstance().applyAnyFormat(newFormatter,
//                    Double.toString(res),old));
//                //setResult(Double.toString(res));
                
                
                CMDefaultParameter parameter1 = new CMDefaultParameter(CMFormulaParameter.NUMBER, result1);
                CMDefaultParameter parameter2 = new CMDefaultParameter(CMFormulaParameter.DECIMAL, result2);
                //CMDefaultParameter parameter3 = new CMDefaultParameter(CMFormulaParameter.DAY, new CMDefaultValue(param3));
                CMCeilFormula formulaCeil = new CMCeilFormula();
                formulaCeil.addParameter(parameter1);
                formulaCeil.addParameter(parameter2);
                //formulaDate.addParameter(parameter3);
				setResult(formulaCeil);
            }
            catch (Exception e) {
                //setResult(BusinessRules.FORMULAS_ERROR);
            	setResult(new CMDefaultValue(BusinessRules.FORMULAS_ERROR));
               // setFormatFormula(BusinessRules.FORMULAS_FORMAT_EMPTY);
            }
            return getResult();
        }
        else /**  */
/* Nuevo codigo nuevas funciones de formulas 18/may/04							  */
        /**  */
        if (formula.equals(BusinessRules.FORMULAS_CATEGORY_MATH_DIV)) {
            try {
//                setType(BusinessRules.TESTDATA_STATE_FLOAT);
//                setFormatFormula(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY());
//                TestDataFormat newFormatter= new TestDataFormat();
//                newFormatter.setVisualFormatter(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY());
//                newFormatter.setOriginalFormatter(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY());
//                newFormatter.setRealFormat(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY);
//                //newFormatter.setFormatLocale(new Locale("en","US"));
//                setM_formatterSelected(newFormatter);
//                double res = CMNumberUtils.division(Double.parseDouble(CMNumberUtils.defaultFormatParam(param1.trim())), Double.parseDouble(CMNumberUtils.defaultFormatParam(param2.trim())));
//
//                TestDataFormat old=new TestDataFormat();
//                old.setFormatLocale(new Locale("en","US"));
//                setResult(CMFormatFactory.getInstance().applyAnyFormat(newFormatter,
//                    Double.toString(res),old));
//                //                setResult(Double.toString(res));
                
                
                CMDefaultParameter parameter1 = new CMDefaultParameter(CMFormulaParameter.NUMBERY, result1);
                CMDefaultParameter parameter2 = new CMDefaultParameter(CMFormulaParameter.NUMBERX, result2);
                //CMDefaultParameter parameter3 = new CMDefaultParameter(CMFormulaParameter.DAY, new CMDefaultValue(param3));
                CMDivFormula formulaDiv = new CMDivFormula();
                formulaDiv.addParameter(parameter1);
                formulaDiv.addParameter(parameter2);
                //formulaDate.addParameter(parameter3);
				setResult(formulaDiv);
            }
            catch (Exception e) {
                //setResult(BusinessRules.FORMULAS_ERROR);
            	setResult(new CMDefaultValue(BusinessRules.FORMULAS_ERROR));
                //setFormatFormula(BusinessRules.FORMULAS_FORMAT_EMPTY);
            }
            return getResult();
        }

        /**  */
        else if (formula.equals(BusinessRules.FORMULAS_CATEGORY_MATH_EXP)) {
            try {
//                setType(BusinessRules.TESTDATA_STATE_FLOAT);
//                setFormatFormula(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY());
//                TestDataFormat newFormatter= new TestDataFormat();
//                newFormatter.setVisualFormatter(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY());
//                newFormatter.setOriginalFormatter(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY());
//                newFormatter.setRealFormat(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY);
//                //newFormatter.setFormatLocale(new Locale("en","US"));
//                setM_formatterSelected(newFormatter);
//                double res = Math.exp(Double.parseDouble(CMNumberUtils.defaultFormatParam(param1.trim())));
//
//                TestDataFormat old=new TestDataFormat();
//                old.setFormatLocale(new Locale("en","US"));
//                setResult(CMFormatFactory.applyAnyFormat(newFormatter,
//                    Double.toString(res), old));
//                //                setResult(Double.toString(res));
                
                
                CMDefaultParameter parameter1 = new CMDefaultParameter(CMFormulaParameter.NUMBER, result1);
                CMExpFormula formulaExp = new CMExpFormula();
                formulaExp.addParameter(parameter1);
                setResult(formulaExp);
            }
            catch (Exception e) {
                //setResult(BusinessRules.FORMULAS_ERROR);
            	setResult(new CMDefaultValue(BusinessRules.FORMULAS_ERROR));
                //setFormatFormula(BusinessRules.FORMULAS_FORMAT_EMPTY);
            }
            return getResult();
        }
        else if (formula.equals(BusinessRules.FORMULAS_CATEGORY_MATH_FLOOR)) {
            try {
//                setType(BusinessRules.TESTDATA_STATE_FLOAT);
//                setFormatFormula(BusinessRules.FORMULAS_FORMAT_MATH_CEIL_FLOOR_RINT_ROUND());
//                TestDataFormat newFormatter= new TestDataFormat();
//                newFormatter.setVisualFormatter(BusinessRules.FORMULAS_FORMAT_MATH_CEIL_FLOOR_RINT_ROUND());
//                newFormatter.setOriginalFormatter(BusinessRules.FORMULAS_FORMAT_MATH_CEIL_FLOOR_RINT_ROUND());
//                newFormatter.setRealFormat(CMNumberUtils.getFormatRoundTo(Integer.parseInt(param2.trim()),BusinessRules.FORMULAS_FORMAT_MATH_CEIL_FLOOR_RINT_ROUND));                //newFormatter.setFormatLocale(new Locale("en","US"));
//                setM_formatterSelected(newFormatter);
//                double toCeilNumber=Double.parseDouble(CMNumberUtils.defaultFormatParam(param1.trim()));
//                double res =0.0;
//                if(toCeilNumber < 0){
//                	 res = CMNumberUtils.floorTo(Integer.parseInt(param2.trim()),Math.abs(toCeilNumber));
//                	 res = res * -1;
//                }
//                else
//                	 res = CMNumberUtils.floorTo(Integer.parseInt(param2.trim()),toCeilNumber);
//               //double res = /*floorTo(Integer.parseInt(param2.trim()),*/Double.parseDouble(CMNumberUtils.defaultFormatParam(param1.trim()));//));
//
//                TestDataFormat old=new TestDataFormat();
//                old.setFormatLocale(new Locale("en","US"));
//                setResult(CMFormatFactory.applyAnyFormat(newFormatter,
//                    Double.toString(res),old));
//                //                setResult(Double.toString(res));
                
                
                CMDefaultParameter parameter1 = new CMDefaultParameter(CMFormulaParameter.NUMBER, result1);
                CMDefaultParameter parameter2 = new CMDefaultParameter(CMFormulaParameter.DECIMAL, result2);
                //CMDefaultParameter parameter3 = new CMDefaultParameter(CMFormulaParameter.DAY, new CMDefaultValue(param3));
                CMFloorFormula formulaFloor = new CMFloorFormula();
                formulaFloor.addParameter(parameter1);
                formulaFloor.addParameter(parameter2);
                //formulaDate.addParameter(parameter3);
				setResult(formulaFloor);
            }
            catch (Exception e) {
                //setResult(BusinessRules.FORMULAS_ERROR);
            	setResult(new CMDefaultValue(BusinessRules.FORMULAS_ERROR));
                //setFormatFormula(BusinessRules.FORMULAS_FORMAT_EMPTY);
            }
            return getResult();
        }
        else if (formula.equals(BusinessRules.FORMULAS_CATEGORY_MATH_LN)) {
            try {
//                setType(BusinessRules.TESTDATA_STATE_FLOAT);
//                setFormatFormula(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY());
//                TestDataFormat newFormatter= new TestDataFormat();
//                newFormatter.setVisualFormatter(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY());
//                newFormatter.setOriginalFormatter(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY());
//                newFormatter.setRealFormat(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY);
//                //newFormatter.setFormatLocale(new Locale("en","US"));
//                setM_formatterSelected(newFormatter);
//                double res = Math.log(Double.parseDouble(CMNumberUtils.defaultFormatParam(param1.trim())));
//                if(Double.toString(res).equals("NaN")){
//                	throw new Exception();
//                }
//
//                TestDataFormat old=new TestDataFormat();
//                old.setFormatLocale(new Locale("en","US"));
//                setResult(CMFormatFactory.getInstance().applyAnyFormat(newFormatter,
//                    Double.toString(res),old));
//                //                setResult(Double.toString(res));
                
                CMDefaultParameter parameter1 = new CMDefaultParameter(CMFormulaParameter.NUMBER, result1);
                CMLnFormula formulaLn = new CMLnFormula();
                formulaLn.addParameter(parameter1);
                setResult(formulaLn);
            }
            catch (Exception e) {
                //setResult(BusinessRules.FORMULAS_ERROR);
            	setResult(new CMDefaultValue(BusinessRules.FORMULAS_ERROR));
                //setFormatFormula(BusinessRules.FORMULAS_FORMAT_EMPTY);
            }
            return getResult();
        }
        else if (formula.equals(BusinessRules.FORMULAS_CATEGORY_MATH_MAX)) {
            try {
//                setType(BusinessRules.TESTDATA_STATE_FLOAT);
//                setFormatFormula(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY());
//                TestDataFormat newFormatter= new TestDataFormat();
//                newFormatter.setVisualFormatter(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY());
//                newFormatter.setOriginalFormatter(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY());
//                newFormatter.setRealFormat(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY);
//                //newFormatter.setFormatLocale(new Locale("en","US"));
//                setM_formatterSelected(newFormatter);
//                double res = Math.max(Double.parseDouble(CMNumberUtils.defaultFormatParam(param1.trim())), Double.parseDouble(CMNumberUtils.defaultFormatParam(param2.trim())));
//
//                TestDataFormat old=new TestDataFormat();
//                old.setFormatLocale(new Locale("en","US"));
//                setResult(CMFormatFactory.getInstance().applyAnyFormat(newFormatter,
//                    Double.toString(res),old));
//                //                setResult(Double.toString(res));
                
                
                CMDefaultParameter parameter1 = new CMDefaultParameter(CMFormulaParameter.NUMBERX, result1);
                CMDefaultParameter parameter2 = new CMDefaultParameter(CMFormulaParameter.NUMBERY, result2);
                //CMDefaultParameter parameter3 = new CMDefaultParameter(CMFormulaParameter.DAY, new CMDefaultValue(param3));
                CMMaxFormula formulaMax = new CMMaxFormula();
                formulaMax.addParameter(parameter1);
                formulaMax.addParameter(parameter2);
                //formulaDate.addParameter(parameter3);
				setResult(formulaMax);
            }
            catch (Exception e) {
                //setResult(BusinessRules.FORMULAS_ERROR);
            	setResult(new CMDefaultValue(BusinessRules.FORMULAS_ERROR));
                //setFormatFormula(BusinessRules.FORMULAS_FORMAT_EMPTY);
            }
            return getResult();
        }
        else if (formula.equals(BusinessRules.FORMULAS_CATEGORY_MATH_MIN)) {
            try {
//                setType(BusinessRules.TESTDATA_STATE_FLOAT);
//                setFormatFormula(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY());
//                TestDataFormat newFormatter= new TestDataFormat();
//                newFormatter.setVisualFormatter(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY());
//                newFormatter.setOriginalFormatter(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY());
//                newFormatter.setRealFormat(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY);
//                //newFormatter.setFormatLocale(new Locale("en","US"));
//                setM_formatterSelected(newFormatter);
//                double res = Math.min(Double.parseDouble(CMNumberUtils.defaultFormatParam(param1.trim())), Double.parseDouble(CMNumberUtils.defaultFormatParam(param2.trim())));
//
//                TestDataFormat old=new TestDataFormat();
//                old.setFormatLocale(new Locale("en","US"));
//                setResult(CMFormatFactory.getInstance().applyAnyFormat(newFormatter,
//                    Double.toString(res),old));
//                //                setResult(Double.toString(res));
                
                CMDefaultParameter parameter1 = new CMDefaultParameter(CMFormulaParameter.NUMBERX,result1);
                CMDefaultParameter parameter2 = new CMDefaultParameter(CMFormulaParameter.NUMBERY, result2);
                //CMDefaultParameter parameter3 = new CMDefaultParameter(CMFormulaParameter.DAY, new CMDefaultValue(param3));
                CMMinFormula formulaMin = new CMMinFormula();
                formulaMin.addParameter(parameter1);
                formulaMin.addParameter(parameter2);
                //formulaDate.addParameter(parameter3);
				setResult(formulaMin);
            }
            catch (Exception e) {
                //setResult(BusinessRules.FORMULAS_ERROR);
            	setResult(new CMDefaultValue(BusinessRules.FORMULAS_ERROR));
               // setFormatFormula(BusinessRules.FORMULAS_FORMAT_EMPTY);
            }
            return getResult();
        }
        else if (formula.equals(BusinessRules.FORMULAS_CATEGORY_MATH_POW)) {
            try {
//                setType(BusinessRules.TESTDATA_STATE_FLOAT);
//                setFormatFormula(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY());
//                TestDataFormat newFormatter= new TestDataFormat();
//                newFormatter.setVisualFormatter(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY());
//                newFormatter.setOriginalFormatter(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY());
//                newFormatter.setRealFormat(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY);
//                //newFormatter.setFormatLocale(new Locale("en","US"));
//                setM_formatterSelected(newFormatter);
//                double res = Math.pow(Double.parseDouble(CMNumberUtils.defaultFormatParam(param1.trim())), Double.parseDouble(CMNumberUtils.defaultFormatParam(param2.trim())));
//
//                TestDataFormat old=new TestDataFormat();
//                old.setFormatLocale(new Locale("en","US"));
//                setResult(CMFormatFactory.getInstance().applyAnyFormat(newFormatter,
//                    Double.toString(res),old));
//                //                setResult(Double.toString(res));
                
                
                CMDefaultParameter parameter1 = new CMDefaultParameter(CMFormulaParameter.NUMBER, result1);
                CMDefaultParameter parameter2 = new CMDefaultParameter(CMFormulaParameter.POWER, result2);
                //CMDefaultParameter parameter3 = new CMDefaultParameter(CMFormulaParameter.DAY, new CMDefaultValue(param3));
                CMPowFormula formulaPow = new CMPowFormula();
                formulaPow.addParameter(parameter1);
                formulaPow.addParameter(parameter2);
                //formulaDate.addParameter(parameter3);
				setResult(formulaPow);
            }
            catch (Exception e) {
                //setResult(BusinessRules.FORMULAS_ERROR);
            	setResult(new CMDefaultValue(BusinessRules.FORMULAS_ERROR));
               // setFormatFormula(BusinessRules.FORMULAS_FORMAT_EMPTY);
            }
            return getResult();
        }
        else if (formula.equals(BusinessRules.FORMULAS_CATEGORY_MATH_RANDOM)) {
            try {
//                setType(BusinessRules.TESTDATA_STATE_FLOAT);
//                setFormatFormula(BusinessRules.FORMULAS_FORMAT_RANDOM());
//                TestDataFormat newFormatter= new TestDataFormat();
//                newFormatter.setVisualFormatter(BusinessRules.FORMULAS_FORMAT_RANDOM());
//                newFormatter.setOriginalFormatter(BusinessRules.FORMULAS_FORMAT_RANDOM());
//                newFormatter.setRealFormat(BusinessRules.FORMULAS_FORMAT_RANDOM);
//                //newFormatter.setFormatLocale(new Locale("en","US"));
//                setM_formatterSelected(newFormatter);
//                double res = Math.random();
//
//                TestDataFormat old=new TestDataFormat();
//                old.setFormatLocale(new Locale("en","US"));
//                setResult(CMFormatFactory.getInstance().applyAnyFormat(newFormatter,
//                    Double.toString(res),old));
//                // setResult(Double.toString(res));
            	
            	CMRandomFormula formulaRandom = new CMRandomFormula();
            	setResult(formulaRandom);
            }
            catch (Exception e) {
                //setResult(BusinessRules.FORMULAS_ERROR);
            	setResult(new CMDefaultValue(BusinessRules.FORMULAS_ERROR));
               // setFormatFormula(BusinessRules.FORMULAS_FORMAT_EMPTY);
           }
            return getResult();
        }
//      else if (formula.equals(BusinessRules.FORMULAS_CATEGORY_MATH_RINT)) {
//          try {
//              setType(BusinessRules.TESTDATA_STATE_FLOAT);
//                setFormatFormula(BusinessRules.FORMULAS_FORMAT_MATH_CEIL_FLOOR_RINT_ROUND());
//                double res = Math.rint(Double.parseDouble(CMNumberUtils.defaultFormatParam(param1.trim())));
//                TestDataFormat newFormatter= new TestDataFormat();
//                newFormatter.setVisualFormatter(BusinessRules.FORMULAS_FORMAT_MATH_CEIL_FLOOR_RINT_ROUND());
//                newFormatter.setOriginalFormatter(BusinessRules.FORMULAS_FORMAT_MATH_CEIL_FLOOR_RINT_ROUND());
//                newFormatter.setRealFormat(BusinessRules.FORMULAS_FORMAT_MATH_CEIL_FLOOR_RINT_ROUND);
//                //newFormatter.setFormatLocale(new Locale("en","US"));
//                setM_formatterSelected(newFormatter);
//                TestDataFormat old=new TestDataFormat();
//                old.setFormatLocale(new Locale("en","US"));
//                setResult(CMFormatFactory.getInstance().applyAnyFormat(newFormatter,
//                    Double.toString(res),old));
//                //setResult(Double.toString(res));
//            }
//            catch (Exception e) {
//                setResult(BusinessRules.FORMULAS_ERROR);
//                setFormatFormula(BusinessRules.FORMULAS_FORMAT_EMPTY);
//            }
//            return getResult();
//        }
        else if (formula.equals(BusinessRules.FORMULAS_CATEGORY_MATH_ROUND)) {
            try {
//                setType(BusinessRules.TESTDATA_STATE_FLOAT);
//                setFormatFormula(BusinessRules.FORMULAS_FORMAT_MATH_CEIL_FLOOR_RINT_ROUND());
//                TestDataFormat newFormatter= new TestDataFormat();
//                newFormatter.setVisualFormatter(BusinessRules.FORMULAS_FORMAT_MATH_CEIL_FLOOR_RINT_ROUND());
//                newFormatter.setOriginalFormatter(BusinessRules.FORMULAS_FORMAT_MATH_CEIL_FLOOR_RINT_ROUND());
//                newFormatter.setRealFormat(CMNumberUtils.getFormatRoundTo(Integer.parseInt(param2.trim()),BusinessRules.FORMULAS_FORMAT_MATH_CEIL_FLOOR_RINT_ROUND));
//                //newFormatter.setFormatLocale(new Locale("en","US"));
//                setM_formatterSelected(newFormatter);
//                double res = CMNumberUtils.roundTo(Integer.parseInt(param2.trim()),Double.parseDouble(CMNumberUtils.defaultFormatParam(param1.trim())));
//
//                TestDataFormat old=new TestDataFormat();
//                old.setFormatLocale(new Locale("en","US"));
//                setResult(CMFormatFactory.getInstance().applyAnyFormat(newFormatter,
//                    Double.toString(res),old));
//                //                setResult(Double.toString(res));
                
                
                CMDefaultParameter parameter1 = new CMDefaultParameter(CMFormulaParameter.NUMBER, result1);
                CMDefaultParameter parameter2 = new CMDefaultParameter(CMFormulaParameter.DECIMAL, result2);
                //CMDefaultParameter parameter3 = new CMDefaultParameter(CMFormulaParameter.DAY, new CMDefaultValue(param3));
                CMRoundFormula formulaRound = new CMRoundFormula();
                formulaRound.addParameter(parameter1);
                formulaRound.addParameter(parameter2);
                //formulaDate.addParameter(parameter3);
				setResult(formulaRound);
            }
            catch (Exception e) {
                //setResult(BusinessRules.FORMULAS_ERROR);
            	setResult(new CMDefaultValue(BusinessRules.FORMULAS_ERROR));
                //setFormatFormula(BusinessRules.FORMULAS_FORMAT_EMPTY);
            }
            return getResult();
        }
        else if (formula.equals(BusinessRules.FORMULAS_CATEGORY_MATH_SQRT)) {
            try {
//                setType(BusinessRules.TESTDATA_STATE_FLOAT);
//                setFormatFormula(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY());
//                TestDataFormat newFormatter= new TestDataFormat();
//                newFormatter.setVisualFormatter(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY());
//                newFormatter.setOriginalFormatter(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY());
//                newFormatter.setRealFormat(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY);
//                //newFormatter.setFormatLocale(new Locale("en","US"));
//                setM_formatterSelected(newFormatter);
//                double res = Math.sqrt(Double.parseDouble(CMNumberUtils.defaultFormatParam(param1.trim())));
//                if(Double.toString(res).equals("NaN")){
//                	throw new Exception();
//                }
//
//                TestDataFormat old=new TestDataFormat();
//                old.setFormatLocale(new Locale("en","US"));
//                setResult(CMFormatFactory.getInstance().applyAnyFormat(newFormatter,
//                    Double.toString(res),old));
//                //                setResult(Double.toString(res));
                
                
                CMDefaultParameter parameter1 = new CMDefaultParameter(CMFormulaParameter.NUMBER, result1);
                CMSqrtFormula formulaSqrt = new CMSqrtFormula();
                formulaSqrt.addParameter(parameter1);
                setResult(formulaSqrt);
            }
            catch (Exception e) {
                //setResult(BusinessRules.FORMULAS_ERROR);
            	setResult(new CMDefaultValue(BusinessRules.FORMULAS_ERROR));
                //setFormatFormula(BusinessRules.FORMULAS_FORMAT_EMPTY);
            }
            return getResult();
        }
        else if (formula.equals(BusinessRules.FORMULAS_CATEGORY_MATH_LOG10)) {
            try {
//                setType(BusinessRules.TESTDATA_STATE_FLOAT);
//                setFormatFormula(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY());
//                TestDataFormat newFormatter= new TestDataFormat();
//                newFormatter.setVisualFormatter(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY());
//                newFormatter.setOriginalFormatter(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY());
//                newFormatter.setRealFormat(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY);
//                //newFormatter.setFormatLocale(new Locale("en","US"));
//                setM_formatterSelected(newFormatter);
//                double res = 0.434294 * Math.log(Double.parseDouble(CMNumberUtils.defaultFormatParam(param1.trim())));
//                if(Double.toString(res).equals("NaN")){
//                	throw new Exception();
//                }
//
//                TestDataFormat old=new TestDataFormat();
//                old.setFormatLocale(new Locale("en","US"));
//                setResult(CMFormatFactory.getInstance().applyAnyFormat(newFormatter,
//                    Double.toString(res),old));
//                //                setResult(Double.toString(res));
                
                
                CMDefaultParameter parameter1 = new CMDefaultParameter(CMFormulaParameter.NUMBER, result1);
                CMLog10Formula formulaLog10 = new CMLog10Formula();
                formulaLog10.addParameter(parameter1);
                setResult(formulaLog10);
            }
            catch (Exception e) {
                //setResult(BusinessRules.FORMULAS_ERROR);
            	setResult(new CMDefaultValue(BusinessRules.FORMULAS_ERROR));
                //setFormatFormula(BusinessRules.FORMULAS_FORMAT_EMPTY);
            }
            return getResult();
        }
        else if (formula.equals(BusinessRules.FORMULAS_CATEGORY_MATH_FACT)) {
            try {
//                setType(BusinessRules.TESTDATA_STATE_INT);
//                setFormatFormula(BusinessRules.FORMULAS_FORMAT_MATH_FACT());
//                TestDataFormat newFormatter= new TestDataFormat();
//                newFormatter.setVisualFormatter(BusinessRules.FORMULAS_FORMAT_MATH_FACT());
//                newFormatter.setOriginalFormatter(BusinessRules.FORMULAS_FORMAT_MATH_FACT());
//                newFormatter.setRealFormat(BusinessRules.FORMULAS_FORMAT_MATH_FACT);
//                //newFormatter.setFormatLocale(new Locale("en","US"));
//                setM_formatterSelected(newFormatter);
//                double res = CMNumberUtils.factorial(Integer.parseInt(CMNumberUtils.defaultFormatParam(param1.trim())));
//
//                TestDataFormat old=new TestDataFormat();
//                old.setFormatLocale(new Locale("en","US"));
//                setResult(CMFormatFactory.getInstance().applyAnyFormat(newFormatter,
//                    Double.toString(res),old));
//                //setResult(Integer.toString(res));
                
                
                CMDefaultParameter parameter1 = new CMDefaultParameter(CMFormulaParameter.NUMBER, result1);
                CMFactFormula formulaFact = new CMFactFormula();
                formulaFact.addParameter(parameter1);
                setResult(formulaFact);
            }
            catch (Exception e) {
                //setResult(BusinessRules.FORMULAS_ERROR);
            	setResult(new CMDefaultValue(BusinessRules.FORMULAS_ERROR));
                //setFormatFormula(BusinessRules.FORMULAS_FORMAT_EMPTY);
            }
            return getResult();
        }
        else if (formula.equals(BusinessRules.FORMULAS_CATEGORY_MATH_LOG)) {
            try {
//                setType(BusinessRules.TESTDATA_STATE_FLOAT);
//                setFormatFormula(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY());
//                TestDataFormat newFormatter= new TestDataFormat();
//                newFormatter.setVisualFormatter(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY());
//                newFormatter.setOriginalFormatter(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY());
//                newFormatter.setRealFormat(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY);
//                //newFormatter.setFormatLocale(new Locale("en","US"));
//                setM_formatterSelected(newFormatter);
//                double res = (Math.log(Double.parseDouble(CMNumberUtils.defaultFormatParam(param1.trim())))) / (Math.log(Double.parseDouble(CMNumberUtils.defaultFormatParam(param2.trim()))));
//                if(Double.toString(res).equals("NaN")){
//                	throw new Exception();
//                }
//
//                TestDataFormat old=new TestDataFormat();
//                old.setFormatLocale(new Locale("en","US"));
//                setResult(CMFormatFactory.getInstance().applyAnyFormat(newFormatter,
//                    Double.toString(res),old));
//                //                setResult(Double.toString(res));
                
                
                CMDefaultParameter parameter1 = new CMDefaultParameter(CMFormulaParameter.NUMBER, result1);
                CMDefaultParameter parameter2 = new CMDefaultParameter(CMFormulaParameter.BASE, result2);
                //CMDefaultParameter parameter3 = new CMDefaultParameter(CMFormulaParameter.DAY, new CMDefaultValue(param3));
                CMLogFormula formulaLog = new CMLogFormula();
                formulaLog.addParameter(parameter1);
                formulaLog.addParameter(parameter2);
                //formulaDate.addParameter(parameter3);
				setResult(formulaLog);
            }
            catch (Exception e) {
                //setResult(BusinessRules.FORMULAS_ERROR);
            	setResult(new CMDefaultValue(BusinessRules.FORMULAS_ERROR));
               // setFormatFormula(BusinessRules.FORMULAS_FORMAT_EMPTY);
            }
            return getResult();
        }
        else if (formula.equals(BusinessRules.FORMULAS_CATEGORY_MATH_PRODUCT)) {
            try {
//                setType(BusinessRules.TESTDATA_STATE_FLOAT);
//                setFormatFormula(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY());
//                TestDataFormat newFormatter= new TestDataFormat();
//                newFormatter.setVisualFormatter(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY());
//                newFormatter.setOriginalFormatter(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY());
//                newFormatter.setRealFormat(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY);
//                //newFormatter.setFormatLocale(new Locale("en","US"));
//                setM_formatterSelected(newFormatter);
//                double res = Double.parseDouble(CMNumberUtils.defaultFormatParam(param1.trim())) * Double.parseDouble(CMNumberUtils.defaultFormatParam(param2.trim()));
//
//
//                TestDataFormat old=new TestDataFormat();
//                old.setFormatLocale(new Locale("en","US"));
//                setResult(CMFormatFactory.getInstance().applyAnyFormat(newFormatter,
//                    Double.toString(res),old));
//                //                setResult(Double.toString(res));
                
                
                CMDefaultParameter parameter1 = new CMDefaultParameter(CMFormulaParameter.NUMBERX, result1);
                CMDefaultParameter parameter2 = new CMDefaultParameter(CMFormulaParameter.NUMBERY, result2);
                //CMDefaultParameter parameter3 = new CMDefaultParameter(CMFormulaParameter.DAY, new CMDefaultValue(param3));
                CMProductFormula formulaProduct = new CMProductFormula();
                formulaProduct.addParameter(parameter1);
                formulaProduct.addParameter(parameter2);
                //formulaDate.addParameter(parameter3);
				setResult(formulaProduct);
            }
            catch (Exception e) {
                //setResult(BusinessRules.FORMULAS_ERROR);
            	setResult(new CMDefaultValue(BusinessRules.FORMULAS_ERROR));
               // setFormatFormula(BusinessRules.FORMULAS_FORMAT_EMPTY);
            }
            return getResult();
        }
        else if (formula.equals(BusinessRules.FORMULAS_CATEGORY_MATH_REST)) {
            try {
//                setType(BusinessRules.TESTDATA_STATE_FLOAT);
//                setFormatFormula(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY());
//                TestDataFormat newFormatter= new TestDataFormat();
//                newFormatter.setVisualFormatter(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY());
//                newFormatter.setOriginalFormatter(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY());
//                newFormatter.setRealFormat(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY);
//                //newFormatter.setFormatLocale(new Locale("en","US"));
//                setM_formatterSelected(newFormatter);
//                double res = Double.parseDouble(CMNumberUtils.defaultFormatParam(param1.trim())) - Double.parseDouble(CMNumberUtils.defaultFormatParam(param2.trim()));
//
//                TestDataFormat old=new TestDataFormat();
//                old.setFormatLocale(new Locale("en","US"));
//                setResult(CMFormatFactory.getInstance().applyAnyFormat(newFormatter,
//                    Double.toString(res),old));
//                //                setResult(Double.toString(res));
                
                
                CMDefaultParameter parameter1 = new CMDefaultParameter(CMFormulaParameter.NUMBERX, result1);
                CMDefaultParameter parameter2 = new CMDefaultParameter(CMFormulaParameter.NUMBERY, result2);
                //CMDefaultParameter parameter3 = new CMDefaultParameter(CMFormulaParameter.DAY, new CMDefaultValue(param3));
                CMDifferenceFormula formulaDifference = new CMDifferenceFormula();
                formulaDifference.addParameter(parameter1);
                formulaDifference.addParameter(parameter2);
                //formulaDate.addParameter(parameter3);
				setResult(formulaDifference);
            }
            catch (Exception e) {
                //setResult(BusinessRules.FORMULAS_ERROR);
            	setResult(new CMDefaultValue(BusinessRules.FORMULAS_ERROR));
                //setFormatFormula(BusinessRules.FORMULAS_FORMAT_EMPTY);
            }
            return getResult();
        }
        else /**  */
/* Nuevo codigo nuevas funciones de formulas 18/may/04							  */
        /**  */
        if (formula.equals(BusinessRules.FORMULAS_CATEGORY_MATH_SUM)) {
            try {
//                setType(BusinessRules.TESTDATA_STATE_FLOAT);
//                setFormatFormula(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY());
//                TestDataFormat newFormatter= new TestDataFormat();
//                newFormatter.setVisualFormatter(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY());
//                newFormatter.setOriginalFormatter(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY());
//                newFormatter.setRealFormat(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY);
//                //newFormatter.setFormatLocale(new Locale("en","US"));
//                setM_formatterSelected(newFormatter);
//                double res = Double.parseDouble(CMNumberUtils.defaultFormatParam(param1.trim())) + Double.parseDouble(CMNumberUtils.defaultFormatParam(param2.trim()));
//
//                TestDataFormat old=new TestDataFormat();
//                old.setFormatLocale(new Locale("en","US"));
//                setResult(CMFormatFactory.getInstance().applyAnyFormat(newFormatter,
//                    Double.toString(res),old));
//                //                setResult(Double.toString(res));
                
                
                CMDefaultParameter parameter1 = new CMDefaultParameter(CMFormulaParameter.NUMBERX, result1);
                CMDefaultParameter parameter2 = new CMDefaultParameter(CMFormulaParameter.NUMBERY, result2);
                //CMDefaultParameter parameter3 = new CMDefaultParameter(CMFormulaParameter.DAY, new CMDefaultValue(param3));
                CMSumFormula formulaSum = new CMSumFormula();
                formulaSum.addParameter(parameter1);
                formulaSum.addParameter(parameter2);
                //formulaDate.addParameter(parameter3);
				setResult(formulaSum);
            }
            catch (Exception e) {
                //setResult(BusinessRules.FORMULAS_ERROR);
            	setResult(new CMDefaultValue(BusinessRules.FORMULAS_ERROR));
              //  setFormatFormula(BusinessRules.FORMULAS_FORMAT_EMPTY);
            }
            return getResult();
        }
       /**  */
        else if (formula.equals(BusinessRules.FORMULAS_CATEGORY_MATH_ROMAN)) {
            try {
//                setType(BusinessRules.TESTDATA_STATE_INT);
//                setFormatFormula(BusinessRules.FORMULAS_FORMAT_STRING);
//                TestDataFormat newFormatter= new TestDataFormat();
//                newFormatter.setVisualFormatter(BusinessRules.FORMULAS_FORMAT_STRING);
//                newFormatter.setOriginalFormatter(BusinessRules.FORMULAS_FORMAT_STRING);
//                newFormatter.setRealFormat(BusinessRules.FORMULAS_FORMAT_STRING);
//
//                setM_formatterSelected(newFormatter);
//                setResult(CMNumberUtils.getRomanString(Integer.parseInt(CMNumberUtils.defaultFormatParam(param1.trim()))));
                
                
                CMDefaultParameter parameter1 = new CMDefaultParameter(CMFormulaParameter.NUMBER, result1);
                CMRomanFormula formulaRoman = new CMRomanFormula();
                formulaRoman.addParameter(parameter1);
                setResult(formulaRoman);
            }
            catch (Exception e) {
                //setResult(BusinessRules.FORMULAS_ERROR);
            	setResult(new CMDefaultValue(BusinessRules.FORMULAS_ERROR));
                //setFormatFormula(BusinessRules.FORMULAS_FORMAT_EMPTY);
            }
            return getResult();
        }
        else if (formula.equals(BusinessRules.FORMULAS_CATEGORY_TEXT_CONCAT)) {
            try {
//                setType(BusinessRules.TESTDATA_STATE_VARCHAR);
//                setFormatFormula(BusinessRules.FORMULAS_FORMAT_STRING);
//                TestDataFormat newFormatter= new TestDataFormat();
//                newFormatter.setVisualFormatter(BusinessRules.FORMULAS_FORMAT_STRING);
//                newFormatter.setOriginalFormatter(BusinessRules.FORMULAS_FORMAT_STRING);
//                newFormatter.setRealFormat(BusinessRules.FORMULAS_FORMAT_STRING);
//
//                setM_formatterSelected(newFormatter);
//                setResult(param1 + param2);
                
                
                CMDefaultParameter parameter1 = new CMDefaultParameter(CMFormulaParameter.STRING1, result1);
                CMDefaultParameter parameter2 = new CMDefaultParameter(CMFormulaParameter.STRING2, result2);
                //CMDefaultParameter parameter3 = new CMDefaultParameter(CMFormulaParameter.DAY, new CMDefaultValue(param3));
                CMConcatFormula formulaConcat = new CMConcatFormula();
                formulaConcat.addParameter(parameter1);
                formulaConcat.addParameter(parameter2);
                //formulaDate.addParameter(parameter3);
				setResult(formulaConcat);
            }
            catch (Exception e) {
                //setResult(BusinessRules.FORMULAS_ERROR_STRING);
            	setResult(new CMDefaultValue(BusinessRules.FORMULAS_ERROR_STRING));
               // setFormatFormula(BusinessRules.FORMULAS_FORMAT_EMPTY);
            }
            return getResult();
        }
        else if (formula.equals(BusinessRules.FORMULAS_CATEGORY_TEXT_LOWERCASE)) {
            try {
//                setType(BusinessRules.TESTDATA_STATE_VARCHAR);
//                setFormatFormula(BusinessRules.FORMULAS_FORMAT_STRING);
//                TestDataFormat newFormatter= new TestDataFormat();
//                newFormatter.setVisualFormatter(BusinessRules.FORMULAS_FORMAT_STRING);
//                newFormatter.setOriginalFormatter(BusinessRules.FORMULAS_FORMAT_STRING);
//                newFormatter.setRealFormat(BusinessRules.FORMULAS_FORMAT_STRING);
//
//                setM_formatterSelected(newFormatter);
//                setResult(param1.toLowerCase());
                
                
                CMDefaultParameter parameter1 = new CMDefaultParameter(CMFormulaParameter.STRING, result1);
                CMLowerCatFormula formulaLowerCat = new CMLowerCatFormula();
                formulaLowerCat.addParameter(parameter1);
                setResult(formulaLowerCat);
            }
            catch (Exception e) {
                //setResult(BusinessRules.FORMULAS_ERROR_STRING);
            	setResult(new CMDefaultValue(BusinessRules.FORMULAS_ERROR_STRING));
              // setFormatFormula(BusinessRules.FORMULAS_FORMAT_EMPTY);
            }
            return getResult();
        }
        else if (formula.equals(BusinessRules.FORMULAS_CATEGORY_TEXT_REMPLACE)) {
            try {
//                setType(BusinessRules.TESTDATA_STATE_VARCHAR);
//                setFormatFormula(BusinessRules.FORMULAS_FORMAT_STRING);
//                TestDataFormat newFormatter= new TestDataFormat();
//                newFormatter.setVisualFormatter(BusinessRules.FORMULAS_FORMAT_STRING);
//                newFormatter.setOriginalFormatter(BusinessRules.FORMULAS_FORMAT_STRING);
//                newFormatter.setRealFormat(BusinessRules.FORMULAS_FORMAT_STRING);
//
//                setM_formatterSelected(newFormatter);
//                setResult(CMCharUtils.remplace(param1, param2, param3));
                
                
                CMDefaultParameter parameter1 = new CMDefaultParameter(CMFormulaParameter.STRING, result1);
                CMDefaultParameter parameter2 = new CMDefaultParameter(CMFormulaParameter.OLDSTRING, result2);
                CMDefaultParameter parameter3 = new CMDefaultParameter(CMFormulaParameter.NEWSTRING, result3);
                CMReplaceFormula formulaReplace = new CMReplaceFormula();
                formulaReplace.addParameter(parameter1);
                formulaReplace.addParameter(parameter2);
                formulaReplace.addParameter(parameter3);
				setResult(formulaReplace);
            }
            catch (Exception e) {
                //setResult(BusinessRules.FORMULAS_ERROR_STRING);
            	setResult(new CMDefaultValue(BusinessRules.FORMULAS_ERROR_STRING));
                //setFormatFormula(BusinessRules.FORMULAS_FORMAT_EMPTY);
          }
            return getResult();
        }
        else if (formula.equals(BusinessRules.FORMULAS_CATEGORY_TEXT_REPEATSTRING)) {
            try {
//                setType(BusinessRules.TESTDATA_STATE_INT);
//                setFormatFormula(BusinessRules.FORMULAS_FORMAT_STRING);
//                TestDataFormat newFormatter= new TestDataFormat();
//                newFormatter.setVisualFormatter(BusinessRules.FORMULAS_FORMAT_STRING);
//                newFormatter.setOriginalFormatter(BusinessRules.FORMULAS_FORMAT_STRING);
//                newFormatter.setRealFormat(BusinessRules.FORMULAS_FORMAT_STRING);
//
//                setM_formatterSelected(newFormatter);
//                setResult(CMCharUtils.repeatString(param1, param2));
                
                
                CMDefaultParameter parameter1 = new CMDefaultParameter(CMFormulaParameter.STRING, result1);
                CMDefaultParameter parameter2 = new CMDefaultParameter(CMFormulaParameter.COMPARESTRING, result2);
                //CMDefaultParameter parameter3 = new CMDefaultParameter(CMFormulaParameter.DAY, new CMDefaultValue(param3));
                CMRepeatStringFormula formulaRepeat = new CMRepeatStringFormula();
                formulaRepeat.addParameter(parameter1);
                formulaRepeat.addParameter(parameter2);
                //formulaDate.addParameter(parameter3);
				setResult(formulaRepeat);
            }
            catch (Exception e) {
                //setResult(BusinessRules.FORMULAS_ERROR_STRING);
            	setResult(new CMDefaultValue(BusinessRules.FORMULAS_ERROR_STRING));
               // setFormatFormula(BusinessRules.FORMULAS_FORMAT_EMPTY);
            }
            return getResult();
        }
        else if (formula.equals(BusinessRules.FORMULAS_CATEGORY_TEXT_SUBSTRING)) {
            try {
//                setType(BusinessRules.TESTDATA_STATE_VARCHAR);
//                setFormatFormula(BusinessRules.FORMULAS_FORMAT_STRING);
//                TestDataFormat newFormatter= new TestDataFormat();
//                newFormatter.setVisualFormatter(BusinessRules.FORMULAS_FORMAT_STRING);
//                newFormatter.setOriginalFormatter(BusinessRules.FORMULAS_FORMAT_STRING);
//                newFormatter.setRealFormat(BusinessRules.FORMULAS_FORMAT_STRING);
//
//                setM_formatterSelected(newFormatter);
//                setResult(param1.substring(Integer.parseInt(param2.trim())));
                
                
                CMDefaultParameter parameter1 = new CMDefaultParameter(CMFormulaParameter.STRING, result1);
                CMDefaultParameter parameter2 = new CMDefaultParameter(CMFormulaParameter.LOCATIONSTRING, result2);
                //CMDefaultParameter parameter3 = new CMDefaultParameter(CMFormulaParameter.DAY, new CMDefaultValue(param3));
                CMSubStringFormula formulaSubString = new CMSubStringFormula();
                formulaSubString.addParameter(parameter1);
                formulaSubString.addParameter(parameter2);
                //formulaDate.addParameter(parameter3);
				setResult(formulaSubString);
            }
            catch (Exception e) {
                //setResult(BusinessRules.FORMULAS_ERROR_STRING);
            	setResult(new CMDefaultValue(BusinessRules.FORMULAS_ERROR_STRING));
              //  setFormatFormula(BusinessRules.FORMULAS_FORMAT_EMPTY);
            }
            return getResult();
        }
        else if (formula.equals(BusinessRules.FORMULAS_CATEGORY_TEXT_TRIM)) {
            try {
//                setType(BusinessRules.TESTDATA_STATE_VARCHAR);
//                setFormatFormula(BusinessRules.FORMULAS_FORMAT_STRING);
//                TestDataFormat newFormatter= new TestDataFormat();
//                newFormatter.setVisualFormatter(BusinessRules.FORMULAS_FORMAT_STRING);
//                newFormatter.setOriginalFormatter(BusinessRules.FORMULAS_FORMAT_STRING);
//                newFormatter.setRealFormat(BusinessRules.FORMULAS_FORMAT_STRING);
//
//                setM_formatterSelected(newFormatter);
//                setResult(param1.trim());
                
                
                CMDefaultParameter parameter1 = new CMDefaultParameter(CMFormulaParameter.STRING, result1);
                CMTrimFormula formulaTrim = new CMTrimFormula();
                formulaTrim.addParameter(parameter1);
                setResult(formulaTrim);
            }
            catch (Exception e) {
                //setResult(BusinessRules.FORMULAS_ERROR_STRING);
            	setResult(new CMDefaultValue(BusinessRules.FORMULAS_ERROR_STRING));
              //  setFormatFormula(BusinessRules.FORMULAS_FORMAT_EMPTY);
            }
            return getResult();
        }
        else if (formula.equals(BusinessRules.FORMULAS_CATEGORY_TEXT_TRIMALL)) {
            try {
//                setType(BusinessRules.TESTDATA_STATE_VARCHAR);
//                setFormatFormula(BusinessRules.FORMULAS_FORMAT_STRING);
//                TestDataFormat newFormatter= new TestDataFormat();
//                newFormatter.setVisualFormatter(BusinessRules.FORMULAS_FORMAT_STRING);
//                newFormatter.setOriginalFormatter(BusinessRules.FORMULAS_FORMAT_STRING);
//                newFormatter.setRealFormat(BusinessRules.FORMULAS_FORMAT_STRING);
//
//                setM_formatterSelected(newFormatter);
//                setResult(CMCharUtils.trimAll(param1));
                
                
                CMDefaultParameter parameter1 = new CMDefaultParameter(CMFormulaParameter.STRING, result1);
                CMTrimAllFormula formulaTrimAll = new CMTrimAllFormula();
                formulaTrimAll.addParameter(parameter1);
                setResult(formulaTrimAll);
            }
            catch (Exception e) {
                //setResult(BusinessRules.FORMULAS_ERROR_STRING);
            	setResult(new CMDefaultValue(BusinessRules.FORMULAS_ERROR_STRING));
              //  setFormatFormula(BusinessRules.FORMULAS_FORMAT_EMPTY);
            }
            return getResult();
        }
        else if (formula.equals(BusinessRules.FORMULAS_CATEGORY_TEXT_UPPERCASE)) {
            try {
//                setType(BusinessRules.TESTDATA_STATE_VARCHAR);
//                setFormatFormula(BusinessRules.FORMULAS_FORMAT_STRING);
//                TestDataFormat newFormatter= new TestDataFormat();
//                newFormatter.setVisualFormatter(BusinessRules.FORMULAS_FORMAT_STRING);
//                newFormatter.setOriginalFormatter(BusinessRules.FORMULAS_FORMAT_STRING);
//                newFormatter.setRealFormat(BusinessRules.FORMULAS_FORMAT_STRING);
//
//                setM_formatterSelected(newFormatter);
//                setResult(param1.toUpperCase());
                
                
                CMDefaultParameter parameter1 = new CMDefaultParameter(CMFormulaParameter.STRING, result1);
                CMUpperCaseFormula formulaUpper = new CMUpperCaseFormula();
                formulaUpper.addParameter(parameter1);
                setResult(formulaUpper);
            }
            catch (Exception e) {
                //setResult(BusinessRules.FORMULAS_ERROR_STRING);
            	setResult(new CMDefaultValue(BusinessRules.FORMULAS_ERROR_STRING));
               // setFormatFormula(BusinessRules.FORMULAS_FORMAT_EMPTY);
            }
            return getResult();
        }
        
        //svonborries_08082007_begin
        else if(formula.equals(BusinessRules.FORMULAS_CATEGORY_TEXT_STRINGTONUM)){
        	try {
				CMDefaultParameter parameter1 = new CMDefaultParameter(CMFormulaParameter.STRING, result1);
				CMStringToNumFormula formulaStringToNum = new CMStringToNumFormula();
				formulaStringToNum.addParameter(parameter1);
				setResult(formulaStringToNum);
			} catch (Exception e) {
				setResult(new CMDefaultValue(BusinessRules.FORMULAS_ERROR_STRING));
			}
			return getResult();
        }
        
        else if (formula.equals(BusinessRules.FORMULAS_CATEGORY_TEXT_NUMTOSTRING)){
        	try {
				CMDefaultParameter parameter1 = new CMDefaultParameter(CMFormulaParameter.NUMBER, result1);
				CMNumberToStringFormula formulaNumToString = new CMNumberToStringFormula();
				formulaNumToString.addParameter(parameter1);
				setResult(formulaNumToString);
				
			} catch (Exception e) {
				setResult(new CMDefaultValue(BusinessRules.FORMULAS_ERROR_STRING));
			}
        	return getResult();
        }
        
        else if (formula.equals(BusinessRules.FORMULAS_CATEGORY_TEXT_RSTRING)){
        	try {
        		CMDefaultParameter parameter1 = new CMDefaultParameter(CMFormulaParameter.STRING, result1);
        		CMDefaultParameter parameter2 = new CMDefaultParameter(CMFormulaParameter.POSITION, result2);
        		CMRStringFormula formulaRString = new CMRStringFormula();
        		formulaRString.addParameter(parameter1);
        		formulaRString.addParameter(parameter2);
				setResult(formulaRString);
			} catch (Exception e) {
				setResult(new CMDefaultValue(BusinessRules.FORMULAS_ERROR_STRING));
			}
        	
        	return getResult();
        }
        
        else if (formula.equals(BusinessRules.FORMULAS_CATEGORY_TEXT_LSTRING)){
        	try {
        		CMDefaultParameter parameter1 = new CMDefaultParameter(CMFormulaParameter.STRING, result1);
        		CMDefaultParameter parameter2 = new CMDefaultParameter(CMFormulaParameter.POSITION, result2);
        		CMLStringFormula formulaRString = new CMLStringFormula();
        		formulaRString.addParameter(parameter1);
        		formulaRString.addParameter(parameter2);
				setResult(formulaRString);
			} catch (Exception e) {
				setResult(new CMDefaultValue(BusinessRules.FORMULAS_ERROR_STRING));
			}
        	
        	return getResult();
        }
        //svonborries_08082007_end
        
        else if (formula.equals(BusinessRules.FORMULAS_CATEGORY_TRIGONOMETRY_ACOS)) {
            try {
//                setType(BusinessRules.TESTDATA_STATE_REAL);
//                setFormatFormula(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY());
//                TestDataFormat newFormatter= new TestDataFormat();
//                newFormatter.setVisualFormatter(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY());
//                newFormatter.setOriginalFormatter(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY());
//                newFormatter.setRealFormat(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY);
//                //newFormatter.setFormatLocale(new Locale("en","US"));
//                setM_formatterSelected(newFormatter);
//                double res = Math.acos(Double.parseDouble(CMNumberUtils.defaultFormatParam(param1.trim())));
//                if(Double.toString(res).equals("NaN")){
//                	throw new Exception();
//                }
//
//                TestDataFormat old=new TestDataFormat();
//                old.setFormatLocale(new Locale("en","US"));
//                setResult(CMFormatFactory.getInstance().applyAnyFormat(newFormatter,
//                    Double.toString(res),old));
//                //                setResult(Double.toString(res));
                
                
                CMDefaultParameter parameter1 = new CMDefaultParameter(CMFormulaParameter.NUMBER, result1);
                CMAcosFormula formulaAcos = new CMAcosFormula();
                formulaAcos.addParameter(parameter1);
                setResult(formulaAcos);
            }
            catch (Exception e) {
                //setResult(BusinessRules.FORMULAS_ERROR);
            	setResult(new CMDefaultValue(BusinessRules.FORMULAS_ERROR));
                //setFormatFormula(BusinessRules.FORMULAS_FORMAT_EMPTY);
            }
            return getResult();
        }
        else if (formula.equals(BusinessRules.FORMULAS_CATEGORY_TRIGONOMETRY_ACOSHYP)) {
            try {
//                setType(BusinessRules.TESTDATA_STATE_REAL);
//                setFormatFormula(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY());
//                TestDataFormat newFormatter= new TestDataFormat();
//                newFormatter.setVisualFormatter(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY());
//                newFormatter.setOriginalFormatter(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY());
//                newFormatter.setRealFormat(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY);
//                //newFormatter.setFormatLocale(new Locale("en","US"));
//                setM_formatterSelected(newFormatter);
//                double res =CMNumberUtils.aCosHyp(Double.parseDouble(CMNumberUtils.defaultFormatParam(param1.trim())));
//                if(Double.toString(res).equals("NaN")){
//                	throw new Exception();
//                }
//
//                TestDataFormat old=new TestDataFormat();
//                old.setFormatLocale(new Locale("en","US"));
//                setResult(CMFormatFactory.getInstance().applyAnyFormat(newFormatter,
//                    Double.toString(res),old));
//                //                setResult(Double.toString(res));
                
                
                CMDefaultParameter parameter1 = new CMDefaultParameter(CMFormulaParameter.NUMBER, result1);
                CMACosHypFormula formulaAcosHyp = new CMACosHypFormula();
                formulaAcosHyp.addParameter(parameter1);
                setResult(formulaAcosHyp);
            }
            catch (Exception e) {
                //setResult(BusinessRules.FORMULAS_ERROR);
            	setResult(new CMDefaultValue(BusinessRules.FORMULAS_ERROR));
                //setFormatFormula(BusinessRules.FORMULAS_FORMAT_EMPTY);
            }
            return getResult();
        }
        else if (formula.equals(BusinessRules.FORMULAS_CATEGORY_TRIGONOMETRY_ASEN)) {
            try {
//                setType(BusinessRules.TESTDATA_STATE_REAL);
//                setFormatFormula(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY());
//                TestDataFormat newFormatter= new TestDataFormat();
//                newFormatter.setVisualFormatter(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY());
//                newFormatter.setOriginalFormatter(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY());
//                newFormatter.setRealFormat(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY);
//                //newFormatter.setFormatLocale(new Locale("en","US"));
//                setM_formatterSelected(newFormatter);
//                double res = Math.asin(Double.parseDouble(CMNumberUtils.defaultFormatParam(param1.trim())));
//                if(Double.toString(res).equals("NaN")){
//                	throw new Exception();
//                }
//
//                TestDataFormat old=new TestDataFormat();
//                old.setFormatLocale(new Locale("en","US"));
//                setResult(CMFormatFactory.getInstance().applyAnyFormat(newFormatter,
//                    Double.toString(res),old));
//                //                setResult(Double.toString(res));
                
                
                CMDefaultParameter parameter1 = new CMDefaultParameter(CMFormulaParameter.NUMBER, result1);
                CMAsenFormula formulaASen = new CMAsenFormula();
                formulaASen.addParameter(parameter1);
                setResult(formulaASen);
            }
            catch (Exception e) {
                //setResult(BusinessRules.FORMULAS_ERROR);
            	setResult(new CMDefaultValue(BusinessRules.FORMULAS_ERROR));
               // setFormatFormula(BusinessRules.FORMULAS_FORMAT_EMPTY);
            }
            return getResult();
        }
        else if (formula.equals(BusinessRules.FORMULAS_CATEGORY_TRIGONOMETRY_ASENHYP)) {
            try {
//                setType(BusinessRules.TESTDATA_STATE_REAL);
//                setFormatFormula(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY());
//                TestDataFormat newFormatter= new TestDataFormat();
//                newFormatter.setVisualFormatter(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY());
//                newFormatter.setOriginalFormatter(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY());
//                newFormatter.setRealFormat(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY);
//                //newFormatter.setFormatLocale(new Locale("en","US"));
//                setM_formatterSelected(newFormatter);
//                double res = CMNumberUtils.aSenHyp(Double.parseDouble(CMNumberUtils.defaultFormatParam(param1.trim())));
//                if(Double.toString(res).equals("NaN")){
//                	throw new Exception();
//                }
//
//                TestDataFormat old=new TestDataFormat();
//                old.setFormatLocale(new Locale("en","US"));
//                setResult(CMFormatFactory.getInstance().applyAnyFormat(newFormatter,
//                    Double.toString(res),old));
//                //                setResult(Double.toString(res));
                
                
                CMDefaultParameter parameter1 = new CMDefaultParameter(CMFormulaParameter.NUMBER, result1);
                CMAsenHypFormula formulaASenHyp = new CMAsenHypFormula();
                formulaASenHyp.addParameter(parameter1);
                setResult(formulaASenHyp);
            }
            catch (Exception e) {
                //setResult(BusinessRules.FORMULAS_ERROR);
            	setResult(new CMDefaultValue(BusinessRules.FORMULAS_ERROR));
               // setFormatFormula(BusinessRules.FORMULAS_FORMAT_EMPTY);
            }
            return getResult();
        }
        else if (formula.equals(BusinessRules.FORMULAS_CATEGORY_TRIGONOMETRY_ATAN)) {
            try {
//                setType(BusinessRules.TESTDATA_STATE_REAL);
//                setFormatFormula(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY());
//                TestDataFormat newFormatter= new TestDataFormat();
//                newFormatter.setVisualFormatter(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY());
//                newFormatter.setOriginalFormatter(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY());
//                newFormatter.setRealFormat(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY);
//                //newFormatter.setFormatLocale(new Locale("en","US"));
//                setM_formatterSelected(newFormatter);
//                double res = Math.atan(Double.parseDouble(CMNumberUtils.defaultFormatParam(param1.trim())));
//                if(Double.toString(res).equals("NaN")){
//                	throw new Exception();
//                }
//
//                TestDataFormat old=new TestDataFormat();
//                old.setFormatLocale(new Locale("en","US"));
//                setResult(CMFormatFactory.getInstance().applyAnyFormat(newFormatter,
//                    Double.toString(res),old));
//                //                setResult(Double.toString(res));
                
                
                CMDefaultParameter parameter1 = new CMDefaultParameter(CMFormulaParameter.NUMBER, result1);
                CMAtanFormula formulaAtan = new CMAtanFormula();
                formulaAtan.addParameter(parameter1);
                setResult(formulaAtan);
            }
            catch (Exception e) {
                //setResult(BusinessRules.FORMULAS_ERROR);
            	setResult(new CMDefaultValue(BusinessRules.FORMULAS_ERROR));
              //  setFormatFormula(BusinessRules.FORMULAS_FORMAT_EMPTY);
            }
            return getResult();
        }
        else if (formula.equals(BusinessRules.FORMULAS_CATEGORY_TRIGONOMETRY_ATANHYP)) {
            try {
//                setType(BusinessRules.TESTDATA_STATE_REAL);
//                setFormatFormula(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY());
//                TestDataFormat newFormatter= new TestDataFormat();
//                newFormatter.setVisualFormatter(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY());
//                newFormatter.setOriginalFormatter(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY());
//                newFormatter.setRealFormat(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY);
//                //newFormatter.setFormatLocale(new Locale("en","US"));
//                setM_formatterSelected(newFormatter);
//                double res = CMNumberUtils.aTanHyp(Double.parseDouble(CMNumberUtils.defaultFormatParam(param1.trim())));
//                if(Double.toString(res).equals("NaN")){
//                	throw new Exception();
//                }
//
//                TestDataFormat old=new TestDataFormat();
//                old.setFormatLocale(new Locale("en","US"));
//                setResult(CMFormatFactory.getInstance().applyAnyFormat(newFormatter,
//                    Double.toString(res),old));
//                //                setResult(Double.toString(res));
                
                
                CMDefaultParameter parameter1 = new CMDefaultParameter(CMFormulaParameter.NUMBER, result1);
                CMAtanHypFormula formulaAtanHyp = new CMAtanHypFormula();
                formulaAtanHyp.addParameter(parameter1);
                setResult(formulaAtanHyp);
            }
            catch (Exception e) {
                //setResult(BusinessRules.FORMULAS_ERROR);
            	setResult(new CMDefaultValue(BusinessRules.FORMULAS_ERROR));
                //setFormatFormula(BusinessRules.FORMULAS_FORMAT_EMPTY);
            }
            return getResult();
        }
        else if (formula.equals(BusinessRules.FORMULAS_CATEGORY_TRIGONOMETRY_COS)) {
            try {
//                setType(BusinessRules.TESTDATA_STATE_REAL);
//                setFormatFormula(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY());
//                TestDataFormat newFormatter= new TestDataFormat();
//                newFormatter.setVisualFormatter(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY());
//                newFormatter.setOriginalFormatter(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY());
//                newFormatter.setRealFormat(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY);
//                //newFormatter.setFormatLocale(new Locale("en","US"));
//                setM_formatterSelected(newFormatter);
//                double res = Math.cos(Double.parseDouble(CMNumberUtils.defaultFormatParam(param1.trim())));
//                if(Double.toString(res).equals("NaN")){
//                	throw new Exception();
//                }
//
//                TestDataFormat old=new TestDataFormat();
//                old.setFormatLocale(new Locale("en","US"));
//                setResult(CMFormatFactory.getInstance().applyAnyFormat(newFormatter,
//                    Double.toString(res),old));
//                //                setResult(Double.toString(res));
                
                
                CMDefaultParameter parameter1 = new CMDefaultParameter(CMFormulaParameter.NUMBER, result1);
                CMCosFormula formulaCos = new CMCosFormula();
                formulaCos.addParameter(parameter1);
                setResult(formulaCos);
            }
            catch (Exception e) {
                //setResult(BusinessRules.FORMULAS_ERROR);
            	setResult(new CMDefaultValue(BusinessRules.FORMULAS_ERROR));
                //setFormatFormula(BusinessRules.FORMULAS_FORMAT_EMPTY);
            }
            return getResult();
        }
        else if (formula.equals(BusinessRules.FORMULAS_CATEGORY_TRIGONOMETRY_COSHYP)) {
            try {
//                setType(BusinessRules.TESTDATA_STATE_REAL);
//                setFormatFormula(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY());
//                TestDataFormat newFormatter= new TestDataFormat();
//                newFormatter.setVisualFormatter(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY());
//                newFormatter.setOriginalFormatter(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY());
//                newFormatter.setRealFormat(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY);
//                //newFormatter.setFormatLocale(new Locale("en","US"));
//                setM_formatterSelected(newFormatter);
//                double res = CMNumberUtils.cosHyp(Double.parseDouble(CMNumberUtils.defaultFormatParam(param1.trim())));
//                if(Double.toString(res).equals("NaN")){
//                	throw new Exception();
//                }
//
//                TestDataFormat old=new TestDataFormat();
//                old.setFormatLocale(new Locale("en","US"));
//                setResult(CMFormatFactory.getInstance().applyAnyFormat(newFormatter,
//                    Double.toString(res),old));
//                //                setResult(Double.toString(res));
                
                
                CMDefaultParameter parameter1 = new CMDefaultParameter(CMFormulaParameter.NUMBER, result1);
                CMCosHypFormula formulaCosHyp = new CMCosHypFormula();
                formulaCosHyp.addParameter(parameter1);
                setResult(formulaCosHyp);
            }
            catch (Exception e) {
                //setResult(BusinessRules.FORMULAS_ERROR);
            	setResult(new CMDefaultValue(BusinessRules.FORMULAS_ERROR));
               // setFormatFormula(BusinessRules.FORMULAS_FORMAT_EMPTY);
            }
            return getResult();
        }
        else if (formula.equals(BusinessRules.FORMULAS_CATEGORY_TRIGONOMETRY_SEN)) {
            try {
//                setType(BusinessRules.TESTDATA_STATE_REAL);
//                setFormatFormula(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY());
//                TestDataFormat newFormatter= new TestDataFormat();
//                newFormatter.setVisualFormatter(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY());
//                newFormatter.setOriginalFormatter(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY());
//                newFormatter.setRealFormat(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY);
//                //newFormatter.setFormatLocale(new Locale("en","US"));
//                setM_formatterSelected(newFormatter);
//                double res = Math.sin(Double.parseDouble(CMNumberUtils.defaultFormatParam(param1.trim())));
//                if(Double.toString(res).equals("NaN")){
//                	throw new Exception();
//                }
//
//                TestDataFormat old=new TestDataFormat();
//                old.setFormatLocale(new Locale("en","US"));
//                setResult(CMFormatFactory.getInstance().applyAnyFormat(newFormatter,
//                    Double.toString(res),old));
//                //                setResult(Double.toString(res));
                
                
                CMDefaultParameter parameter1 = new CMDefaultParameter(CMFormulaParameter.NUMBER, result1);
                CMSenFormula formulaSen = new CMSenFormula();
                formulaSen.addParameter(parameter1);
                setResult(formulaSen);
            }
            catch (Exception e) {
                //setResult(BusinessRules.FORMULAS_ERROR);
            	setResult(new CMDefaultValue(BusinessRules.FORMULAS_ERROR));
               // setFormatFormula(BusinessRules.FORMULAS_FORMAT_EMPTY);
            }
            return getResult();
        }
        else if (formula.equals(BusinessRules.FORMULAS_CATEGORY_TRIGONOMETRY_SENHYP)) {
            try {
//                setType(BusinessRules.TESTDATA_STATE_REAL);
//                setFormatFormula(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY());
//                TestDataFormat newFormatter= new TestDataFormat();
//                newFormatter.setVisualFormatter(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY());
//                newFormatter.setOriginalFormatter(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY());
//                newFormatter.setRealFormat(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY);
//                //newFormatter.setFormatLocale(new Locale("en","US"));
//                setM_formatterSelected(newFormatter);
//                double res = CMNumberUtils.senHyp(Double.parseDouble(CMNumberUtils.defaultFormatParam(param1.trim())));
//                if(Double.toString(res).equals("NaN")){
//                	throw new Exception();
//                }
//
//                TestDataFormat old=new TestDataFormat();
//                old.setFormatLocale(new Locale("en","US"));
//                setResult(CMFormatFactory.getInstance().applyAnyFormat(newFormatter,
//                    Double.toString(res), old));
//                //                setResult(Double.toString(res));
                
                
                CMDefaultParameter parameter1 = new CMDefaultParameter(CMFormulaParameter.NUMBER, result1);
                CMSenHypFormula formulaSenHyp = new CMSenHypFormula();
                formulaSenHyp.addParameter(parameter1);
                setResult(formulaSenHyp);
            }
            catch (Exception e) {
                //setResult(BusinessRules.FORMULAS_ERROR);
            	setResult(new CMDefaultValue(BusinessRules.FORMULAS_ERROR));
                //setFormatFormula(BusinessRules.FORMULAS_FORMAT_EMPTY);
            }
            return getResult();
        }
        else if (formula.equals(BusinessRules.FORMULAS_CATEGORY_TRIGONOMETRY_TAN)) {
            try {
//                setType(BusinessRules.TESTDATA_STATE_REAL);
//                setFormatFormula(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY());
//                TestDataFormat newFormatter= new TestDataFormat();
//                newFormatter.setVisualFormatter(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY());
//                newFormatter.setOriginalFormatter(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY());
//                newFormatter.setRealFormat(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY);
//                //newFormatter.setFormatLocale(new Locale("en","US"));
//                setM_formatterSelected(newFormatter);
//                double res = Math.tan(Double.parseDouble(CMNumberUtils.defaultFormatParam(param1.trim())));
//                if(Double.toString(res).equals("NaN")){
//                	throw new Exception();
//                }
//
//                TestDataFormat old=new TestDataFormat();
//                old.setFormatLocale(new Locale("en","US"));
//                setResult(CMFormatFactory.getInstance().applyAnyFormat(newFormatter,
//                    Double.toString(res),old));
//                //                setResult(Double.toString(res));
                
                
                CMDefaultParameter parameter1 = new CMDefaultParameter(CMFormulaParameter.NUMBER, result1);
                CMTanFormula formulaTan = new CMTanFormula();
                formulaTan.addParameter(parameter1);
                setResult(formulaTan);
            }
            catch (Exception e) {
                //setResult(BusinessRules.FORMULAS_ERROR);
            	setResult(new CMDefaultValue(BusinessRules.FORMULAS_ERROR));
              //  setFormatFormula(BusinessRules.FORMULAS_FORMAT_EMPTY);
            }
            return getResult();
        }
        else if (formula.equals(BusinessRules.FORMULAS_CATEGORY_TRIGONOMETRY_TANHYP)) {
            try {
//                setType(BusinessRules.TESTDATA_STATE_REAL);
//                setFormatFormula(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY());
//                TestDataFormat newFormatter= new TestDataFormat();
//                newFormatter.setVisualFormatter(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY());
//                newFormatter.setOriginalFormatter(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY());
//                newFormatter.setRealFormat(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY);
//                //newFormatter.setFormatLocale(new Locale("en","US"));
//                setM_formatterSelected(newFormatter);
//                double res = CMNumberUtils.tanHyp(Double.parseDouble(CMNumberUtils.defaultFormatParam(param1.trim())));
//                if(Double.toString(res).equals("NaN")){
//                	throw new Exception();
//                }
//
//                TestDataFormat old=new TestDataFormat();
//                old.setFormatLocale(new Locale("en","US"));
//                setResult(CMFormatFactory.getInstance().applyAnyFormat(newFormatter,
//                    Double.toString(res),old));
//                //                setResult(Double.toString(res));
                
                
                CMDefaultParameter parameter1 = new CMDefaultParameter(CMFormulaParameter.NUMBER, result1);
                CMTanHypFormula formulaTanHyp = new CMTanHypFormula();
                formulaTanHyp.addParameter(parameter1);
                setResult(formulaTanHyp);
            }
            catch (Exception e) {
                //setResult(BusinessRules.FORMULAS_ERROR);
            	setResult(new CMDefaultValue(BusinessRules.FORMULAS_ERROR));
               // setFormatFormula(BusinessRules.FORMULAS_FORMAT_EMPTY);
            }
            return getResult();
        }
        else if (formula.equals(BusinessRules.FORMULAS_CATEGORY_TRIGONOMETRY_TODEGREES)) {
            try {
//                setType(BusinessRules.TESTDATA_STATE_REAL);
//                setFormatFormula(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY());
//                TestDataFormat newFormatter= new TestDataFormat();
//                newFormatter.setVisualFormatter(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY());
//                newFormatter.setOriginalFormatter(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY());
//                newFormatter.setRealFormat(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY);
//                //newFormatter.setFormatLocale(new Locale("en","US"));
//                setM_formatterSelected(newFormatter);
//                double res = Math.toDegrees(Double.parseDouble(CMNumberUtils.defaultFormatParam(param1.trim())));
//                if(Double.toString(res).equals("NaN")){
//                	throw new Exception();
//                }
//
//                TestDataFormat old=new TestDataFormat();
//                old.setFormatLocale(new Locale("en","US"));
//                setResult(CMFormatFactory.getInstance().applyAnyFormat(newFormatter,
//                    Double.toString(res),old));
//                ///                setResult(Double.toString(res));
                
                
                CMDefaultParameter parameter1 = new CMDefaultParameter(CMFormulaParameter.ANGLE, result1);
                CMToDegreesFormula formulaToDegrees = new CMToDegreesFormula();
                formulaToDegrees.addParameter(parameter1);
                setResult(formulaToDegrees);
            }
            catch (Exception e) {
                //setResult(BusinessRules.FORMULAS_ERROR);
            	setResult(new CMDefaultValue(BusinessRules.FORMULAS_ERROR));
                //setFormatFormula(BusinessRules.FORMULAS_FORMAT_EMPTY);
            }
            return getResult();
        }
        else if (formula.equals(BusinessRules.FORMULAS_CATEGORY_TRIGONOMETRY_TORADIANS)) {
            try {
//                setType(BusinessRules.TESTDATA_STATE_REAL);
//                setFormatFormula(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY());
//                TestDataFormat newFormatter= new TestDataFormat();
//                newFormatter.setVisualFormatter(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY());
//                newFormatter.setOriginalFormatter(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY());
//                newFormatter.setRealFormat(BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY);
//                //newFormatter.setFormatLocale(new Locale("en","US"));
//                setM_formatterSelected(newFormatter);
//                double res = Math.toRadians(Double.parseDouble(CMNumberUtils.defaultFormatParam(param1.trim())));
//                if(Double.toString(res).equals("NaN")){
//                	throw new Exception();
//                }
//
//                TestDataFormat old=new TestDataFormat();
//                old.setFormatLocale(new Locale("en","US"));
//                setResult(CMFormatFactory.getInstance().applyAnyFormat(newFormatter,
//                    Double.toString(res),old));
//                //                setResult(Double.toString(res));
                
                
                CMDefaultParameter parameter1 = new CMDefaultParameter(CMFormulaParameter.GRADE, result1);
                CMToRadianFormula formulaToRadian = new CMToRadianFormula();
                formulaToRadian.addParameter(parameter1);
                setResult(formulaToRadian);
            }
            catch (Exception e) {
                //setResult(BusinessRules.FORMULAS_ERROR);
            	setResult(new CMDefaultValue(BusinessRules.FORMULAS_ERROR));
                //setFormatFormula(BusinessRules.FORMULAS_FORMAT_EMPTY);
            }
            return getResult();
        }
        else if (formula.equals(BusinessRules.FORMULAS_CATEGORY_CONSTANTS_C()) ||
            formula.equals(BusinessRules.FORMULAS_CATEGORY_CONSTANTS_C_NAME)) {
//                setType(BusinessRules.TESTDATA_STATE_REAL);
//                setFormatFormula(BusinessRules.FORMULAS_FORMAT_C());
//                TestDataFormat newFormatter= new TestDataFormat();
//                newFormatter.setVisualFormatter(BusinessRules.FORMULAS_FORMAT_C());
//                newFormatter.setOriginalFormatter(BusinessRules.FORMULAS_FORMAT_C());
//                newFormatter.setRealFormat(BusinessRules.FORMULAS_FORMAT_C);
//                setM_formatterSelected(newFormatter);
//                return BusinessRules.FORMULAS_CATEGORY_CONSTANTS_C();
        	return new CMCFormula();
        }
        else if (formula.equals(BusinessRules.FORMULAS_CATEGORY_CONSTANTS_E()) ||
            formula.equals(BusinessRules.FORMULAS_CATEGORY_CONSTANTS_E_NAME)) {
//                setType(BusinessRules.TESTDATA_STATE_REAL);
//                setFormatFormula(BusinessRules.FORMULAS_FORMAT_E());
//                TestDataFormat newFormatter= new TestDataFormat();
//                newFormatter.setVisualFormatter(BusinessRules.FORMULAS_FORMAT_E());
//                newFormatter.setOriginalFormatter(BusinessRules.FORMULAS_FORMAT_E());
//                //newFormatter.setRealFormat(BusinessRules.FORMULAS_FORMAT_E);
//                setM_formatterSelected(newFormatter);
//                return BusinessRules.FORMULAS_CATEGORY_CONSTANTS_E();
        		return new CMEFormula();
        }
        else if (formula.equals(BusinessRules.FORMULAS_CATEGORY_CONSTANTS_G()) ||
            formula.equals(BusinessRules.FORMULAS_CATEGORY_CONSTANTS_G_NAME)) {
//                setType(BusinessRules.TESTDATA_STATE_REAL);
//                setFormatFormula(BusinessRules.FORMULAS_FORMAT_G());
//                TestDataFormat newFormatter= new TestDataFormat();
//                newFormatter.setVisualFormatter(BusinessRules.FORMULAS_FORMAT_G());
//                newFormatter.setOriginalFormatter(BusinessRules.FORMULAS_FORMAT_G());
//                newFormatter.setRealFormat(BusinessRules.FORMULAS_FORMAT_G);
//                setM_formatterSelected(newFormatter);
//                return BusinessRules.FORMULAS_CATEGORY_CONSTANTS_G();
        	return new CMGFormula();
        }
        else if (formula.equals(BusinessRules.FORMULAS_CATEGORY_CONSTANTS_PI()) ||
            formula.equals(BusinessRules.FORMULAS_CATEGORY_CONSTANTS_PI_NAME)) {
//                setType(BusinessRules.TESTDATA_STATE_REAL);
//                setFormatFormula(BusinessRules.FORMULAS_FORMAT_PI());
//                TestDataFormat newFormatter= new TestDataFormat();
//                newFormatter.setVisualFormatter(BusinessRules.FORMULAS_FORMAT_PI());
//                newFormatter.setOriginalFormatter(BusinessRules.FORMULAS_FORMAT_PI());
//                newFormatter.setRealFormat(BusinessRules.FORMULAS_FORMAT_PI);
//                setM_formatterSelected(newFormatter);
//                return BusinessRules.FORMULAS_CATEGORY_CONSTANTS_PI();
        	return new CMPIFormula();
        }
        else {
            return new CMDefaultValue(""); //$NON-NLS-1$
        }
    }

    /**
     * Metodo que devuelve el parametro indicado por index de la formula p_formula
     * @param index indice del que se desea recuperar el parametro
     * @param p_formula formula que contiene parametros de donde se extraera el parametro indicado por index
     * @return retorna un String que contiene el parametro indicado por Index
     */
    @SuppressWarnings("unchecked")
	public String getParamForIndex(int index, String p_formula) {
        boolean swDT = true;
        StringBuffer ac = new StringBuffer();
        char cc;
        int p = 0, i = 0;
        int estado = 0;
        String arguments="";
        Vector theArguments = new Vector();
        try{
           arguments = p_formula.substring(p_formula.indexOf("(") + 1, p_formula.lastIndexOf(")"));
        }
        catch(Exception ex)
        {
        	isNotValidNestedFormula=true;
            if (theArguments.size() < 4) {
               for (int k = theArguments.size(); k <= 3; k++)
                  theArguments.addElement(Character.toString('\u0015'));
                        }
            return theArguments.elementAt(index).toString();
        }
        while (swDT) { //loop for ever
            if (arguments.length() > i) {
                cc = arguments.charAt(i);
            }
            else {
                cc = '\u0003';
            }
            switch (estado) {
                case 0: {
                        if (Character.isSpaceChar(cc)) {
                            i++;
                            ac.append(cc);
                            estado = 0;
                        }
                        else if (isComa(cc)) {
                            i++;
                            estado = 1;
                        }
                        else if (isOpenBracket(cc)) {
                            i++;
                            ac.append(cc);
                            p++;
                            estado = 2;
                        }
                        else if (isEOF(cc)) {
                            estado = 4;
                        }
                        /*else if (isParamStringSeparator(cc)) {
                            i++;
                            estado = 5;
                        }*/
                        else if (isSlash(cc)) {
                            estado = 6;
                        }
                        else {
                            i++;
                            ac.append(cc);
                            estado = 0;
                        }
                        break;
                    }
                case 1: { //cuando llega una coma
                        theArguments.addElement(ac.toString());
                        ac = new StringBuffer();
                        estado = 0;
                        break;
                    }
                case 2: { //cuando llega un (
                        if (isOpenBracket(cc)) {
                            i++;
                            ac.append(cc);
                            p++;
                            estado = 2;
                        }
                        else if (isCloseBracket(cc)) {
                            i++;
                            ac.append(cc);
                            p--;
                            if (p == 0)
                                estado = 0;
                            else
                                estado = 2;
                        }
                        else if (isEOF(cc)) {
                            //mostrar error
                            estado = 4;
                        }
                        else {
                            i++;
                            ac.append(cc);
                            estado = 2;
                        }
                        break;
                    }
                case 3: { //cuando llega un )
                        break;
                    }
                case 4: { //EOF
                        swDT = false;
                        theArguments.addElement(ac.toString());
                        if (theArguments.size() < 4) {
                            for (int k = theArguments.size(); k <= 3; k++)
                                theArguments.addElement(Character.toString('\u0015'));
                        }
                        ac = new StringBuffer();
                        break;
                    }
                case 5: {
                        if (isParamStringSeparator(cc)) {
                            i++;
                            estado = 0;
                        }
                        else if (isSlash(cc)) {
                            estado = 7;
                        }
                        else {
                            i++;
                            ac.append(cc);
                            estado = 5;
                        }
                        break;
                    }
            	case 6:{
                	if(i+1 < arguments.length() && isComa(arguments.charAt(i+1))){
                    	i++;
                        ac.append(arguments.charAt(i));
                        i++;
                        estado=0;
                	}
                	else{
                    	ac.append(cc);
                        i++;
                        estado=0;
                	}
                	break;
            	}
            	case 7:{
                	if(i+1<arguments.length() && isComa(arguments.charAt(i+1))){
                    	i++;
                        ac.append(arguments.charAt(i));
                        i++;
                        estado=5;
                	}
                	else{
                    	ac.append(cc);
                        i++;
                        estado=5;
                	}
                	break;
            	}
            }
        }
        return theArguments.elementAt(index).toString();
    }
	public ICMValue calculeCompexFormula(String complexFormula) {
        String refactoryFormula = reFactoryFormula(complexFormula);
        if (refactoryFormula.equals("")) {
            return new CMDefaultValue(complexFormula);
        }
        else {
            if (cantParam(refactoryFormula) == 0) {
                return calculate(complexFormula);
            }
            else {
                if (cantParam(refactoryFormula) == 1) {
                    String param1 = getParamForIndex(0, complexFormula);
//                    if(param1.equals(Character.toString('\u0015')) || !getParamForIndex(1, complexFormula).equals(Character.toString('\u0015')) )
//           			 	isNotValidNestedFormula=true;
                    ICMValue result1 = calculeCompexFormula(param1);
                    /*if (!isTextFormula) {
                        result1 = CMNumberUtils.defaultFormatParam(result1);
                    }*/
//                     if (result1.equals(BusinessRules.FORMULAS_ERROR) || result1.equals(BusinessRules.FORMULAS_ERROR_DATE)|| result1.equals(BusinessRules.FORMULAS_ERROR_STRING))
//                        isNotValidNestedFormula=true;
                    return calculate(refactoryFormula, result1, null, null);
                }
                else {
                    if (cantParam(refactoryFormula) == 2) {
                        String param1 = getParamForIndex(0, complexFormula);
                        String param2 = getParamForIndex(1, complexFormula);

//                        if(param1.equals(Character.toString('\u0015')) || param2.equals(Character.toString('\u0015'))|| !getParamForIndex(2, complexFormula).equals(Character.toString('\u0015')) )
//           			 		isNotValidNestedFormula=true;

                        ICMValue result1 = calculeCompexFormula(param1);
                        ICMValue result2 = calculeCompexFormula(param2);
                       /* if (!isTextFormula) {
                            result1 = CMNumberUtils.defaultFormatParam(result1);
                            result2 = CMNumberUtils.defaultFormatParam(result2);
                        }*/

//                        if (result1.equals(BusinessRules.FORMULAS_ERROR) || result1.equals(BusinessRules.FORMULAS_ERROR_DATE) || result1.equals(BusinessRules.FORMULAS_ERROR_STRING))
//                        	isNotValidNestedFormula=true;
//
//                        if (result2.equals(BusinessRules.FORMULAS_ERROR) || result2.equals(BusinessRules.FORMULAS_ERROR_DATE) || result2.equals(BusinessRules.FORMULAS_ERROR_STRING))
//                        	isNotValidNestedFormula=true;

                        return calculate(refactoryFormula, result1, result2, null);
                    }
                    else {
                        if (cantParam(refactoryFormula) == 3) {
                            String param1 = getParamForIndex(0, complexFormula);
                            String param2 = getParamForIndex(1, complexFormula);
                            String param3 = getParamForIndex(2, complexFormula);
//                            if(param1.equals(Character.toString('\u0015')) || param2.equals(Character.toString('\u0015'))|| param3.equals(Character.toString('\u0015'))|| !getParamForIndex(3, complexFormula).equals(Character.toString('\u0015')))
//           			 			isNotValidNestedFormula=true;
                            ICMValue result1 = calculeCompexFormula(param1);
                            ICMValue result2 = calculeCompexFormula(param2);
                            ICMValue result3 = calculeCompexFormula(param3);
                          /*  if (!isTextFormula) {
                                result1 = CMNumberUtils.defaultFormatParam(result1);
                                result2 = CMNumberUtils.defaultFormatParam(result2);
                                result3 = CMNumberUtils.defaultFormatParam(result3);
                            }*/
//                            if (result1.equals(BusinessRules.FORMULAS_ERROR) || result1.equals(BusinessRules.FORMULAS_ERROR_DATE) || result1.equals(BusinessRules.FORMULAS_ERROR_STRING))
//                        		isNotValidNestedFormula=true;
//
//                        	if (result2.equals(BusinessRules.FORMULAS_ERROR) || result2.equals(BusinessRules.FORMULAS_ERROR_DATE) || result2.equals(BusinessRules.FORMULAS_ERROR_STRING))
//                        		isNotValidNestedFormula=true;
//
//                        	if (result3.equals(BusinessRules.FORMULAS_ERROR) || result3.equals(BusinessRules.FORMULAS_ERROR_DATE) || result3.equals(BusinessRules.FORMULAS_ERROR_STRING))
//                        		isNotValidNestedFormula=true;

                            return calculate(refactoryFormula, result1, result2, result3);
                        }
                        else {
                            return new CMDefaultValue(complexFormula);
                        }
                    }
                }
            }
        }
    }
	  /**  */
    public ICMValue calculate(String formula) {
    	if(useStringValue){
    		return new CMDefaultValue(formula);
    	}
        //  String result;
//        if (formula.equals(BusinessRules.FORMULAS_CATEGORY_DATETIME_DATENOW)) {
//            try {
//            	setType(BusinessRules.TESTDATA_STATE_DATETIME);
//                setFormatFormula(BusinessRules.FORMULAS_FORMAT_DATE_DATENOW);
//                TestDataFormat newFormatter= new TestDataFormat();
//                newFormatter.setVisualFormatter(BusinessRules.FORMULAS_FORMAT_DATE_DATENOW);
//                newFormatter.setOriginalFormatter(BusinessRules.FORMULAS_FORMAT_DATE_DATENOW);
//                newFormatter.setRealFormat(BusinessRules.FORMULAS_FORMAT_DATE_DATENOW);
//                setM_formatterSelected(newFormatter);
//            	Calendar calendar = Calendar.getInstance();
//                Date d = calendar.getTime();
//
//                setResult(CMFormatFactory.formatDate(newFormatter, d,
//                    d.toString()));
//            }
//            catch (Exception e) {
//                setResult(BusinessRules.FORMULAS_ERROR_DATE);
//                //setFormatFormula(BusinessRules.FORMULAS_FORMAT_EMPTY);
//            }
//            return result;
//        }
//        else if (formula.equals(BusinessRules.FORMULAS_CATEGORY_DATETIME_TODAY)) {
//            try {
//            	setType(BusinessRules.TESTDATA_STATE_DATETIME);
//                setFormatFormula(BusinessRules.FORMULAS_FORMAT_DATE_TODAY);
//                TestDataFormat newFormatter= new TestDataFormat();
//                newFormatter.setVisualFormatter(BusinessRules.FORMULAS_FORMAT_DATE_TODAY);
//                newFormatter.setOriginalFormatter(BusinessRules.FORMULAS_FORMAT_DATE_TODAY);
//                newFormatter.setRealFormat(BusinessRules.FORMULAS_FORMAT_DATE_TODAY);
//                setM_formatterSelected(newFormatter);
//            	Calendar calendar = Calendar.getInstance();
//                Date d = calendar.getTime();
//
//                setResult(CMFormatFactory.formatDate(newFormatter, d, d.toString()));
//            }
//            catch (Exception e) {
//                setResult(BusinessRules.FORMULAS_ERROR_DATE);
//                //setFormatFormula(BusinessRules.FORMULAS_FORMAT_EMPTY);
//            }
//            return result;
//        }
//        else if (formula.equals(BusinessRules.FORMULAS_CATEGORY_DATETIME_HOUR)) {
//            try {
//                //setResult(Integer.toString(Calendar.getInstance().get(Calendar.HOUR_OF_DAY))); //d.getHours());
//                setType(BusinessRules.TESTDATA_STATE_DATETIME);
//                setFormatFormula(BusinessRules.FORMULAS_FORMAT_DATE_HOUR);
//                TestDataFormat newFormatter= new TestDataFormat();
//                newFormatter.setVisualFormatter(BusinessRules.FORMULAS_FORMAT_DATE_HOUR);
//                newFormatter.setOriginalFormatter(BusinessRules.FORMULAS_FORMAT_DATE_HOUR);
//                newFormatter.setRealFormat(BusinessRules.FORMULAS_FORMAT_DATE_HOUR);
//                setM_formatterSelected(newFormatter);
//                Calendar calendar = Calendar.getInstance();
//                Date d = calendar.getTime();
//
//                setResult(CMFormatFactory.formatDate(newFormatter, d, d.toString()));
//            }
//            catch (Exception e) {
//                setResult(BusinessRules.FORMULAS_ERROR_DATE);
//               // setFormatFormula(BusinessRules.FORMULAS_FORMAT_EMPTY);
//            }
//            return result;
//        }
//        else if (formula.equals(BusinessRules.FORMULAS_CATEGORY_DATETIME_MONTH)) {
//            try {
//                //setResult(Integer.toString(Calendar.getInstance().get(Calendar.MONTH) + 1)); //d.getMonth());
//                setType(BusinessRules.TESTDATA_STATE_DATETIME);
//                setFormatFormula(BusinessRules.FORMULAS_FORMAT_DATE_MONTH);
//                TestDataFormat newFormatter= new TestDataFormat();
//                newFormatter.setVisualFormatter(BusinessRules.FORMULAS_FORMAT_DATE_MONTH);
//                newFormatter.setOriginalFormatter(BusinessRules.FORMULAS_FORMAT_DATE_MONTH);
//                newFormatter.setRealFormat(BusinessRules.FORMULAS_FORMAT_DATE_MONTH);
//                setM_formatterSelected(newFormatter);
//                Calendar calendar = Calendar.getInstance();
//                Date d = calendar.getTime();
//
//                setResult(CMFormatFactory.formatDate(newFormatter, d, d.toString()));
//            }
//            catch (Exception e) {
//                setResult(BusinessRules.FORMULAS_ERROR_DATE);
//               // setFormatFormula(BusinessRules.FORMULAS_FORMAT_EMPTY);
//            }
//            return result;
//        }
//        else if (formula.equals(BusinessRules.FORMULAS_CATEGORY_DATETIME_YEAR)) {
//            try {
//                //setResult(Integer.toString(Calendar.getInstance().get(1)));
//                setType(BusinessRules.TESTDATA_STATE_DATETIME);
//                setFormatFormula(BusinessRules.FORMULAS_FORMAT_DATE_YEAR);
//                TestDataFormat newFormatter= new TestDataFormat();
//                newFormatter.setVisualFormatter(BusinessRules.FORMULAS_FORMAT_DATE_YEAR);
//                newFormatter.setOriginalFormatter(BusinessRules.FORMULAS_FORMAT_DATE_YEAR);
//                newFormatter.setRealFormat(BusinessRules.FORMULAS_FORMAT_DATE_YEAR);
//                setM_formatterSelected(newFormatter);
//                Calendar calendar = Calendar.getInstance();
//                Date d = calendar.getTime();
//
//                setResult(CMFormatFactory.formatDate(newFormatter, d, d.toString()));
//            }
//            catch (Exception e) {
//                setResult(BusinessRules.FORMULAS_ERROR_DATE);
//                //setFormatFormula(BusinessRules.FORMULAS_FORMAT_EMPTY);
//            }
//            return result;
//        }
//        else if (formula.equals(BusinessRules.FORMULAS_CATEGORY_MATH_RANDOM)) {
//            try {
//                setType(BusinessRules.TESTDATA_STATE_FLOAT);
//                setFormatFormula(BusinessRules.FORMULAS_FORMAT_RANDOM());
//                TestDataFormat newFormatter= new TestDataFormat();
//                newFormatter.setVisualFormatter(BusinessRules.FORMULAS_FORMAT_RANDOM());
//                newFormatter.setOriginalFormatter(BusinessRules.FORMULAS_FORMAT_RANDOM());
//                newFormatter.setRealFormat(BusinessRules.FORMULAS_FORMAT_RANDOM());
//                setM_formatterSelected(newFormatter);
//                double res = Math.random();
//
//                setResult(CMFormatFactory.applyAnyFormat(newFormatter,
//                    Double.toString(res),new TestDataFormat()));
//                // setResult(Double.toString(res));
//            }
//            catch (Exception e) {
//                setResult(BusinessRules.FORMULAS_ERROR);
//               // setFormatFormula(BusinessRules.FORMULAS_FORMAT_EMPTY);
//            }
//            return result;
//        }
          else if (formula.equals(BusinessRules.FORMULAS_CATEGORY_CONSTANTS_C()) ||
            formula.equals(BusinessRules.FORMULAS_CATEGORY_CONSTANTS_C_NAME)) {
                setType(BusinessRules.TESTDATA_STATE_REAL);
                setFormatFormula(BusinessRules.FORMULAS_FORMAT_C());
                return new CMCFormula();
        }
        else if (formula.equals(BusinessRules.FORMULAS_CATEGORY_CONSTANTS_E()) ||
            formula.equals(BusinessRules.FORMULAS_CATEGORY_CONSTANTS_E_NAME)) {
                setType(BusinessRules.TESTDATA_STATE_REAL);
                setFormatFormula(BusinessRules.FORMULAS_FORMAT_E());
                return new CMEFormula();
        }
        else if (formula.equals(BusinessRules.FORMULAS_CATEGORY_CONSTANTS_G()) ||
            formula.equals(BusinessRules.FORMULAS_CATEGORY_CONSTANTS_G_NAME)) {
                setType(BusinessRules.TESTDATA_STATE_REAL);
                setFormatFormula(BusinessRules.FORMULAS_FORMAT_G());
                return new CMGFormula();
        }
        else if (formula.equals(BusinessRules.FORMULAS_CATEGORY_CONSTANTS_PI()) ||
            formula.equals(BusinessRules.FORMULAS_CATEGORY_CONSTANTS_PI_NAME)) {
                setType(BusinessRules.TESTDATA_STATE_REAL);
                setFormatFormula(BusinessRules.FORMULAS_FORMAT_PI());
                return new CMPIFormula();
        }
        else if (formula.startsWith("$")) {
        	String valueString = VariablesManager.returnImplicitExplicitVariable(formula,cmgrid.getTDStructure());//svonborries_06042006
        	if (valueString.equals(""))
        		return new CMDefaultValue(valueString);
        	else
        		return new CMLinkElement(formula);
        }
        else {
            return new CMDefaultValue(""); //$NON-NLS-1$
        }
    }
    public boolean isOpenBracket(char x) {
        char j = '(';
        return x == j;
    }

    public boolean isCloseBracket(char x) {
        char j = ')';
        return x == j;
    }

    public boolean isComa(char x) {
        char j = ';';
        return x == j;
    }

    private boolean isParamStringSeparator(char x) {
        char j = '\u001E';
        return x == j;
    }

    public boolean isEOF(char x) {
        char j = '\u0003';
        return x == j;
    }

    private boolean isSlash(char x){
        char j='\\';
        return j==x;
    }
//    private String timeToSeconds(String p_Hour) {
//        if (validateHour(p_Hour)) {
//            int value = getHour(p_Hour) * 3600 + getMin(p_Hour) * 60 + getSec(p_Hour);
//            return Integer.toString(value);
//        }
//        else {
//            return BusinessRules.FORMULAS_ERROR_DATE;
//        }
//    }
//    private boolean validateHour(String p_Hour) {
//        if (getHour(p_Hour) < 0 || getHour(p_Hour) > 24) {
//            return false;
//        }
//        else {
//            if (getMin(p_Hour) < 0 || getMin(p_Hour) > 60) {
//                return false;
//            }
//            else {
//                if (getSec(p_Hour) < 0 || getSec(p_Hour) > 60) {
//                    return false;
//                }
//                else {
//                    return true;
//                }
//            }
//        }
//    }
//    private int getHour(String p_Hour) {
//        try {
//            int index = p_Hour.indexOf(":"); //$NON-NLS-1$
//            if (index < 0) {
//                return -1;
//            }
//            else {
//                return Integer.parseInt(p_Hour.substring(0, index));
//            }
//        }
//        catch (Exception e) {
//            return -1;
//        }
//    }
//    private int getMin(String p_Hour) {
//        try {
//            int index = p_Hour.indexOf(":"); //$NON-NLS-1$
//            int index2 = p_Hour.lastIndexOf(":"); //$NON-NLS-1$
//            if (index < 0 || index2 < 0 || index == index2) {
//                return -1;
//            }
//            else {
//                return Integer.parseInt(p_Hour.substring(index + 1, index2));
//            }
//        }
//        catch (Exception e) {
//            return -1;
//        }
//    }
//
//    private int getSec(String p_Hour) {
//        try {
//            int index = p_Hour.indexOf(":"); //$NON-NLS-1$
//            int index2 = p_Hour.lastIndexOf(":"); //$NON-NLS-1$
//            if (index < 0 || index2 < 0 || index == index2) {
//                return -1;
//            }
//            else {
//                return Integer.parseInt(p_Hour.substring(index2 + 1));
//            }
//        }
//        catch (Exception e) {
//            return -1;
//        }
//    }

    @SuppressWarnings("unchecked")
	private Vector getVariablesNames(Variables p_Variables){
    	Vector result= new Vector();
    	for(int i =0; i< p_Variables.getVariables().size();i++){
    		result.addElement(((Variable)p_Variables.getVariables().elementAt(i)).getM_Name());
    	}
    	return result;
    }
    public String convertVariableToStandarFormat(String p_VariableValue, TestDataFormat p_FormatVariable){
    	TestDataFormat newDefaultFormat=new TestDataFormat();
    	newDefaultFormat.setRealFormat(p_FormatVariable.getRealFormat());
    	newDefaultFormat.setOriginalFormatter(p_FormatVariable.getOriginalFormatter());
    	return CMFormatFactory.applyAnyFormat(newDefaultFormat,p_VariableValue,p_FormatVariable);
    }
    public boolean isRecalculateFormula() {
		return recalculateFormula;
	}

    /**
     * Metodo que devuelve el Valor de una Variable si existe la variable devuelve el valor de la variable
     * caso cantrario devuelve una cadena vacia y muestra un mesaje
     * "No existe la Variable su valor sera remplazado por una Cadena Vacia"
     * @param String nom_Variable cadena que contiene el nombre de la variable
     * @return String result contiene el valor de la variable si existe caso contrario devuelve "";
     */
    @SuppressWarnings("deprecation")
	public String getValueVariable(String nom_Variable) {
        try {
            String result = nom_Variable;
            nom_Variable = nom_Variable.substring(nom_Variable.indexOf("$") + 1, nom_Variable.length());
            Project2 actualProject = ProjectManager.getSelectedProject();
            //svonborries_06012006_begin
            TDStructure l_Structure = CMApplication.frame.getGridTDStructure().getTDStructure();
            if (getVariablesNames(l_Structure.getM_Variables()).contains(nom_Variable)){
                int index = getVariablesNames(l_Structure.getM_Variables()).indexOf(nom_Variable);
                String value=((Variable)l_Structure.getM_Variables().getVariables().elementAt(index)).getM_Value().getValue().toString();
                TestDataFormat formatter=((Variable)l_Structure.getM_Variables().getVariables().elementAt(index)).getFormatter();
                result = convertVariableToStandarFormat(value,formatter);
                if(!isRecalculateFormula()){
                Variable variable=((Variable)l_Structure.getM_Variables().getVariables().elementAt(index));
                variable.getM_Observers().addObserver(cmgrid.getSelectedTypeData());
                if(!CMApplication.frame.isIsPanelTestDataSelected()){
                	cmgrid.setAllTypeDataInTestDataToObserverVariable(variable);
                }
//              svonborries_06012006_end
                }
            }
            else {
            	/*
            	 * Hcanedo_14_11_2005_Begin
            	 * aqui el codigo para las referencias entre valores de estructuras
            	 */
            	if(typeDataReferences!= null && typeDataReferences.containsKey(result)){
            		Object value= typeDataReferences.get(result);
            		return ((ITypeData)value).getFormattedValue();
            		//return ((ITypeData)value).getStringValue();//oString();
            	}
            	//hcaneedo_14_11_2005_end
                //hcanedo_20_10_2004_begin
                String nom_Variables_Case = nom_Variable.toUpperCase();
                if (Arrays.binarySearch(BusinessRules.TESTDATA_IMPICIT_VARIABLES, nom_Variables_Case) >= 0) {
                    int indexImplicitVariables = Arrays.binarySearch(BusinessRules.TESTDATA_IMPICIT_VARIABLES,
                        nom_Variables_Case);
                    if (indexImplicitVariables == 0) {
                        result = actualProject.getName();
                    }
                    else {
                        if (indexImplicitVariables == 1) {
                            result = ((StructureTestData)cmgrid.getTDStructure().getM_StructureTestData().elementAt(CMIndexTDStructureUpdate.getInstance().getIndex())).getName();
                        }
                        else {
                            if (indexImplicitVariables == 2) {
                                if (CMApplication.frame.isIsPanelTestDataSelected()) {
                                    result = getNameTestData();
                                }
                                else {
                                    //hcanedo_21102004_begin
                                    isAllowedVariable = false;
                                    JOptionPane.showMessageDialog(CMApplication.frame, CMMessages.getString("FORMULAS_ERROR_NO_ALLOWED_VARIABLE"),
                                        CMMessages.getString("TESTDATA_TITLE_MENSSAGE_ERROR"), JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$
                                    //$NON-NLS-2$
                                    //hcanedo_21102004_end
                                }
                            }
                            else {
                                if (indexImplicitVariables == 3) {
                                    result = getNameTestObject();
                                }
                                else {
                                    result = getNameWorskspace();
                                }
                            }
                        }
                    }
                }
                else {
                   // JOptionPane.showMessageDialog(CMApplication.frame, CMMessages.getString("FORMULAS_ERROR_NON_EXIST_VARIABLE"),
                    //    CMMessages.getString("TESTDATA_TITLE_MENSSAGE_ERROR"), JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$ //$NON-NLS-2$
                }
                //hcanedo_20_10_2004_end
            }
            return result;
        }
        catch (Exception ex) {
     //       JOptionPane.showMessageDialog(CMApplication.frame, CMMessages.getString("FORMULAS_ERROR_NON_EXIST_VARIABLE"),
    //            CMMessages.getString("TESTDATA_TITLE_MENSSAGE_ERROR"), JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$ //$NON-NLS-2$
            return result.toString();
        }
    }
    public String getNameTestObject() {
        return CMApplication.frame.getGridTDStructure().getTDStructure().getM_TestObject().getName();
    }

    public String getNameWorskspace() {
        return CMApplication.frame.getTreeWorkspaceView().getCurrentProjectReference().getM_Workspace().getName();
    }

    
    public void setTypeDataReferences(HashMap subjects) {
		typeDataReferences= subjects;

	}
	public HashMap getTypeDataReferences() {
		return typeDataReferences;
	}
	public String getNameTestData() {
        return ((TestData)cmgrid.getTDStructure().getTestDataCombination().getM_TestDatas().elementAt(CMIndexTDStructureUpdate.getInstance().getindexTestData())).getName();
    }
	public boolean isNotValidNestedFormula() {
		return isNotValidNestedFormula;
	}
	public void setNotValidNestedFormula(boolean isNotValidNestedFormula) {
		this.isNotValidNestedFormula = isNotValidNestedFormula;
	}
	public boolean isAllowedVariable() {
		return isAllowedVariable;
	}
	public void setAllowedVariable(boolean isAllowedVariable) {
		this.isAllowedVariable = isAllowedVariable;
	}
	public boolean isUseStringValue() {
		return useStringValue;
	}
	public void setUseStringValue(boolean useStringValue) {
		this.useStringValue = useStringValue;
	}
	public String getFormatFormula() {
		return formatFormula;
	}
	public TestDataFormat getM_formatterSelected() {
		return m_formatterSelected;
	}
	public String getType() {
		return type;
	}
	public void setRecalculateFormula(boolean recalculateFormula) {
		this.recalculateFormula = recalculateFormula;
	}
	public String getFormulaEdit() {
		return formulaEdit;
	}
	public void setFormulaEdit(String formulaEdit) {
		this.formulaEdit = formulaEdit;
	}
	public String getFormulaSelected() {
		return formulaSelected;
	}
	public void setFormulaSelected(String formulaSelected) {
		this.formulaSelected = formulaSelected;
	}
}
