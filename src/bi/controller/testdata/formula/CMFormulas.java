package bi.controller.testdata.formula;

import java.util.ArrayList;
import java.util.List;

import bi.view.lang.CMMessages;
import model.BusinessRules;
import model.ICMFormula;
import model.formula.CMACosHypFormula;
import model.formula.CMAbsFormula;
import model.formula.CMAcosFormula;
import model.formula.CMAddDayFormula;
import model.formula.CMAddMonthFormula;
import model.formula.CMAddYearFormula;
import model.formula.CMAsenFormula;
import model.formula.CMAsenHypFormula;
import model.formula.CMAtanFormula;
import model.formula.CMAtanHypFormula;
import model.formula.CMCFormula;
import model.formula.CMCeilFormula;
import model.formula.CMConcatFormula;
import model.formula.CMCosFormula;
import model.formula.CMCosHypFormula;
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
import model.formula.CMLStringFormula;
import model.formula.CMLnFormula;
import model.formula.CMLog10Formula;
import model.formula.CMLogFormula;
import model.formula.CMLowerCatFormula;
import model.formula.CMMaxFormula;
import model.formula.CMMinFormula;
import model.formula.CMMonthFormula;
import model.formula.CMNumberToStringFormula;
import model.formula.CMPIFormula;
import model.formula.CMPowFormula;
import model.formula.CMProductFormula;
import model.formula.CMRStringFormula;
import model.formula.CMRandomFormula;
import model.formula.CMRepeatStringFormula;
import model.formula.CMReplaceFormula;
import model.formula.CMRomanFormula;
import model.formula.CMRoundFormula;
import model.formula.CMSecondFormula;
import model.formula.CMSenFormula;
import model.formula.CMSenHypFormula;
import model.formula.CMSqrtFormula;
import model.formula.CMStringToNumFormula;
import model.formula.CMSubStringFormula;
import model.formula.CMSumFormula;
import model.formula.CMTanFormula;
import model.formula.CMTanHypFormula;
import model.formula.CMToDegreesFormula;
import model.formula.CMToRadianFormula;
import model.formula.CMTodayFormula;
import model.formula.CMTrimAllFormula;
import model.formula.CMTrimFormula;
import model.formula.CMUpperCaseFormula;
import model.formula.CMYearFormula;

public enum CMFormulas {

	//DATE AND TIME CATEGORY
	DATE(CMMessages.getString("FORMULA_DATE"),CMFormulaCategory.DATE_TIME, CMMessages.getString("FORMULAS_CATEGORY_DATETIME_DATE_DESCRIPTION")),
	DATENOW(CMMessages.getString("FORMULA_DATENOW"),CMFormulaCategory.DATE_TIME,CMMessages.getString("FORMULAS_CATEGORY_DATETIME_DATENOW_DESCRIPTION")),
	ADD_DAY(CMMessages.getString("FORMULA_ADDDAY"),CMFormulaCategory.DATE_TIME,CMMessages.getString("FORMULAS_CATEGORY_DATETIME_DATESUM_DESCRIPTION")),
	ADD_MONTH(CMMessages.getString("FORMULA_ADDMONTH"),CMFormulaCategory.DATE_TIME,CMMessages.getString("FORMULAS_CATEGORY_DATETIME_ADDMONTH_DESCRIPTION")),
	ADD_YEAR(CMMessages.getString("FORMULA_ADDYEAR"),CMFormulaCategory.DATE_TIME,CMMessages.getString("FORMULAS_CATEGORY_DATETIME_ADDYEAR_DESCRIPTION")),
	DAY_WEEK(CMMessages.getString("FORMULA_DAYWEEK"),CMFormulaCategory.DATE_TIME,CMMessages.getString("FORMULAS_CATEGORY_DATETIME_DAYWEEK_DESCRIPTION")),
	DIFF_DATE(CMMessages.getString("FORMULA_DIFFDATE"),CMFormulaCategory.DATE_TIME,CMMessages.getString("FORMULAS_CATEGORY_DATETIME_DIFFDATE_DESCRIPTION")),
	HOUR(CMMessages.getString("FORMULA_HOUR"),CMFormulaCategory.DATE_TIME,CMMessages.getString("FORMULAS_CATEGORY_DATETIME_HOUR_DESCRIPTION")),
	MONTH(CMMessages.getString("FORMULA_MONTH"),CMFormulaCategory.DATE_TIME,CMMessages.getString("FORMULAS_CATEGORY_DATETIME_MONTH_DESCRIPTION")),
	SECOND(CMMessages.getString("FORMULA_SECONDS"),CMFormulaCategory.DATE_TIME,CMMessages.getString("FORMULAS_CATEGORY_DATETIME_SECONDS_DESCRIPTION")),
	TODAY(CMMessages.getString("FORMULA_TODAY"),CMFormulaCategory.DATE_TIME,CMMessages.getString("FORMULAS_CATEGORY_DATETIME_TODAY_DESCRIPTION")),
	YEAR(CMMessages.getString("FORMULA_YEAR"),CMFormulaCategory.DATE_TIME,CMMessages.getString("FORMULAS_CATEGORY_DATETIME_YEAR_DESCRIPTION")),
	//MATHEMATICS
	ABS(CMMessages.getString("FORMULA_ABS"),CMFormulaCategory.MATHEMATICS,CMMessages.getString("FORMULAS_CATEGORY_MATH_ABS_DESCRIPTION")),
	CEIL(CMMessages.getString("FORMULA_CEIL"),CMFormulaCategory.MATHEMATICS,CMMessages.getString("FORMULAS_CATEGORY_MATH_CEIL_DESCRIPTION")),
	DIFFERENCE(CMMessages.getString("FORMULA_DIFFERENCE"),CMFormulaCategory.MATHEMATICS,CMMessages.getString("FORMULAS_CATEGORY_MATH_REST_DESCRIPTION")),
	DIV(CMMessages.getString("FORMULA_DIV"),CMFormulaCategory.MATHEMATICS,CMMessages.getString("FORMULAS_CATEGORY_MATH_DIV_DESCRIPTION")),
	EXP(CMMessages.getString("FORMULA_EXP"),CMFormulaCategory.MATHEMATICS,CMMessages.getString("FORMULAS_CATEGORY_MATH_EXP_DESCRIPTION")),
	FACT(CMMessages.getString("FORMULA_FACT"),CMFormulaCategory.MATHEMATICS,CMMessages.getString("FORMULAS_CATEGORY_MATH_FACT_DESCRIPTION")),
	FLOOR(CMMessages.getString("FORMULA_FLOOR"),CMFormulaCategory.MATHEMATICS,CMMessages.getString("FORMULAS_CATEGORY_MATH_FLOOR_DESCRIPTION")),
	LN(CMMessages.getString("FORMULA_LN"),CMFormulaCategory.MATHEMATICS,CMMessages.getString("FORMULAS_CATEGORY_MATH_LN_DESCRIPTION")),
	LOG(CMMessages.getString("FORMULA_LOG"),CMFormulaCategory.MATHEMATICS,CMMessages.getString("FORMULAS_CATEGORY_MATH_LOG_DESCRIPTION")),
	LOG10(CMMessages.getString("FORMULA_LOG10"),CMFormulaCategory.MATHEMATICS,CMMessages.getString("FORMULAS_CATEGORY_MATH_LOG10_DESCRIPTION")),
	MAX(CMMessages.getString("FORMULA_MAX"),CMFormulaCategory.MATHEMATICS,CMMessages.getString("FORMULAS_CATEGORY_MATH_MAX_DESCRIPTION")),
	MIN(CMMessages.getString("FORMULA_MIN"),CMFormulaCategory.MATHEMATICS,CMMessages.getString("FORMULAS_CATEGORY_MATH_MIN_DESCRIPTION")),
	POW(CMMessages.getString("FORMULA_POW"),CMFormulaCategory.MATHEMATICS,CMMessages.getString("FORMULAS_CATEGORY_MATH_POW_DESCRIPTION")),
	PRODUCT(CMMessages.getString("FORMULA_PRODUCT"),CMFormulaCategory.MATHEMATICS,CMMessages.getString("FORMULAS_CATEGORY_MATH_PRODUCT_DESCRIPTION")),
	RANDOM(CMMessages.getString("FORMULA_RANDOM"),CMFormulaCategory.MATHEMATICS,CMMessages.getString("FORMULAS_CATEGORY_MATH_RANDOM_DESCRIPTION")),
	ROMAN(CMMessages.getString("FORMULA_ROMAN"),CMFormulaCategory.MATHEMATICS,CMMessages.getString("FORMULAS_CATEGORY_MATH_ROMAN_DESCRIPTION")),
	ROUND(CMMessages.getString("FORMULA_ROUND"),CMFormulaCategory.MATHEMATICS,CMMessages.getString("FORMULAS_CATEGORY_MATH_ROUND_DESCRIPTION")),
	SQRT(CMMessages.getString("FORMULA_SQRT"),CMFormulaCategory.MATHEMATICS,CMMessages.getString("FORMULAS_CATEGORY_MATH_SQRT_DESCRIPTION")),
	SUM(CMMessages.getString("FORMULA_SUM"),CMFormulaCategory.MATHEMATICS,CMMessages.getString("FORMULAS_CATEGORY_MATH_SUM_DESCRIPTION")),
	//TEXT
	CONCAT(CMMessages.getString("FORMULA_CONCAT"),CMFormulaCategory.TEXT,CMMessages.getString("FORMULAS_CATEGORY_TEXT_CONCAT_DESCRIPTION")),
	LOWER_CAT(CMMessages.getString("FORMULA_LOWERCASE"),CMFormulaCategory.TEXT,CMMessages.getString("FORMULAS_CATEGORY_TEXT_LOWERCASE_DESCRIPTION")),
	REPLACE(CMMessages.getString("FORMULA_REPLACE"),CMFormulaCategory.TEXT,CMMessages.getString("FORMULAS_CATEGORY_TEXT_REMPLACE_DESCRIPTION")),
	REPEAT_STRING(CMMessages.getString("FORMULA_REPEATSTRING"),CMFormulaCategory.TEXT,CMMessages.getString("FORMULAS_CATEGORY_TEXT_REPEATSTRING_DESCRIPTION")),
	SUB_STRING(CMMessages.getString("FORMULA_SUBSTRING"),CMFormulaCategory.TEXT,CMMessages.getString("FORMULAS_CATEGORY_TEXT_SUBSTRING_DESCRIPTION")),
	//svonborries_7082007_begin
	STRINGTONUM(CMMessages.getString("FORMULA_STRINGTONUM"), CMFormulaCategory.TEXT, CMMessages.getString("FORMULAS_CATEGORY_TEXT_STRINGTONUM_DESCRIPTION")),
	NUMTOSTRING(CMMessages.getString("FORMULA_NUMTOSTRING"), CMFormulaCategory.TEXT, CMMessages.getString("FORMULAS_CATEGORY_TEXT_NUMTOSTRING_DESCRIPTION")),
	RSTRING(CMMessages.getString("FORMULA_RSTRING"), CMFormulaCategory.TEXT, CMMessages.getString("FORMULAS_CATEGORY_TEXT_RSTRING_DESCRIPTION")),
	LSTRING(CMMessages.getString("FORMULA_LSTRING"), CMFormulaCategory.TEXT, CMMessages.getString("FORMULAS_CATEGORY_TEXT_LSTRING_DESCRIPTION")),
	//svonborries_7082007_end
	TRIM(CMMessages.getString("FORMULA_TRIM"),CMFormulaCategory.TEXT,CMMessages.getString("FORMULAS_CATEGORY_TEXT_TRIM_DESCRIPTION")),
	TRIM_ALL(CMMessages.getString("FORMULA_TRIMALL"),CMFormulaCategory.TEXT,CMMessages.getString("FORMULAS_CATEGORY_TEXT_TRIMALL_DESCRIPTION")),
	UPPER_CASE(CMMessages.getString("FORMULA_UPPERCASE"),CMFormulaCategory.TEXT,CMMessages.getString("FORMULAS_CATEGORY_TEXT_UPPERCASE_DESCRIPTION")),
	//TRIGONOMETRY
	ACOS(CMMessages.getString("FORMULA_ACOS"),CMFormulaCategory.TRIGONOMETRY,CMMessages.getString("FORMULAS_CATEGORY_TRIGONOMETRY_ACOS_DESCRIPTION")),
	ACOSHYP(CMMessages.getString("FORMULA_ACOSHYP"),CMFormulaCategory.TRIGONOMETRY,CMMessages.getString("FORMULAS_CATEGORY_TRIGONOMETRY_ACOSHYP_DESCRIPTION")),
	ASEN(CMMessages.getString("FORMULA_ASEN"),CMFormulaCategory.TRIGONOMETRY,CMMessages.getString("FORMULAS_CATEGORY_TRIGONOMETRY_ASEN_DESCRIPTION")),
	ASENHYP(CMMessages.getString("FORMULA_ASENHYP"),CMFormulaCategory.TRIGONOMETRY,CMMessages.getString("FORMULAS_CATEGORY_TRIGONOMETRY_ASENHYP_DESCRIPTION")),
	ATAN(CMMessages.getString("FORMULA_ATAN"),CMFormulaCategory.TRIGONOMETRY,CMMessages.getString("FORMULAS_CATEGORY_TRIGONOMETRY_ATAN_DESCRIPTION")),
	ATANHYP(CMMessages.getString("FORMULA_ATANHYP"),CMFormulaCategory.TRIGONOMETRY,CMMessages.getString("FORMULAS_CATEGORY_TRIGONOMETRY_ATANHYP_DESCRIPTION")),
	COS(CMMessages.getString("FORMULA_COS"),CMFormulaCategory.TRIGONOMETRY,CMMessages.getString("FORMULAS_CATEGORY_TRIGONOMETRY_COS_DESCRIPTION")),
	COSHYP(CMMessages.getString("FORMULA_COSHYP"),CMFormulaCategory.TRIGONOMETRY,CMMessages.getString("FORMULAS_CATEGORY_TRIGONOMETRY_COSHYP_DESCRIPTION")),
	SEN(CMMessages.getString("FORMULA_SEN"),CMFormulaCategory.TRIGONOMETRY,CMMessages.getString("FORMULAS_CATEGORY_TRIGONOMETRY_SEN_DESCRIPTION")),
	SENHYP(CMMessages.getString("FORMULA_SENHYP"),CMFormulaCategory.TRIGONOMETRY,CMMessages.getString("FORMULAS_CATEGORY_TRIGONOMETRY_SENHYP_DESCRIPTION")),
	TAN(CMMessages.getString("FORMULA_TAN"),CMFormulaCategory.TRIGONOMETRY,CMMessages.getString("FORMULAS_CATEGORY_TRIGONOMETRY_TAN_DESCRIPTION")),
	TANHYP(CMMessages.getString("FORMULA_TANHYP"),CMFormulaCategory.TRIGONOMETRY,CMMessages.getString("FORMULAS_CATEGORY_TRIGONOMETRY_TANHYP_DESCRIPTION")),
	TO_DEGREES(CMMessages.getString("FORMULA_TODEGREES"),CMFormulaCategory.TRIGONOMETRY,CMMessages.getString("FORMULAS_CATEGORY_TRIGONOMETRY_TODEGREES_DESCRIPTION")),
	TO_RADIANS(CMMessages.getString("FORMULA_TORADIANS"),CMFormulaCategory.TRIGONOMETRY,CMMessages.getString("FORMULAS_CATEGORY_TRIGONOMETRY_TORADIANS_DESCRIPTION")),
	//CONSTANT
	C(CMMessages.getString("FORMULA_C"),CMFormulaCategory.CONSTANTS,CMMessages.getString("FORMULAS_CATEGORY_CONSTANTS_C_DESCRIPTION")),
	E(CMMessages.getString("FORMULA_E"),CMFormulaCategory.CONSTANTS,CMMessages.getString("FORMULAS_CATEGORY_CONSTANTS_E_DESCRIPTION")),
	G(CMMessages.getString("FORMULA_G"),CMFormulaCategory.CONSTANTS,CMMessages.getString("FORMULAS_CATEGORY_CONSTANTS_G_DESCRIPTION")),
	PI(CMMessages.getString("FORMULA_PI"),CMFormulaCategory.CONSTANTS,CMMessages.getString("FORMULAS_CATEGORY_CONSTANTS_PI_DESCRIPTION"))
	;

	private String name;
	private CMFormulaCategory category;
	private String description;
	private String canonicalFormula;
	private List<CMFormulaParameter> allowedParam = null;
	private String defaultPattern = null;
	private String formulaType = null;

	
	
	CMFormulas(String p_name, CMFormulaCategory p_category, String p_description){
		name = p_name;
		category = p_category;
		description = p_description;
	}
	
	public ICMFormula getFormula() {
		return createFormula();
	}

	private ICMFormula createFormula() {
		ICMFormula formula = null;
		try {
			switch (this) {
			case DATE:
				formula = new CMDateFormula();
				break;
			case DATENOW:
				formula = new CMDateNowFormula();
				break;
			case ADD_DAY:
				formula = new CMAddDayFormula();
				break;
			case ADD_MONTH:
				formula = new CMAddMonthFormula();
				break;
			case ADD_YEAR:
				formula = new CMAddYearFormula();
				break;
			case DAY_WEEK:
				formula = new CMDayWeekFormula();
				break;
			case DIFF_DATE:
				formula = new CMDiffDateFormula();
				break;
			case HOUR:
				formula = new CMHourFormula();
				break;
			case SECOND:
				formula = new CMSecondFormula();
				break;
			case TODAY:
				formula = new CMTodayFormula();
				break;
			case YEAR:
				formula = new CMYearFormula();
				break;
			case MONTH:
				formula = new CMMonthFormula();
				break;
			case ABS:
				formula = new CMAbsFormula();
				break;
			case CEIL: 
				formula = new CMCeilFormula();
				break;
			case DIFFERENCE:
				formula = new CMDifferenceFormula();
				break;
			case DIV:
				formula = new CMDivFormula();
				break;
			case EXP:
				formula = new CMExpFormula();
				break;
			case FACT:
				formula = new CMFactFormula();
				break;
			case FLOOR:
				formula = new CMFloorFormula();
				break;
			case LN:
				formula = new CMLnFormula();
				break;
			case LOG:
				formula = new CMLogFormula();
				break;
			case LOG10:
				formula = new CMLog10Formula();
				break;
			case MAX:
				formula = new CMMaxFormula();
				break;
			case MIN:
				formula = new CMMinFormula();
				break;
			case POW:
				formula = new CMPowFormula();
				break;
			case PRODUCT:
				formula = new CMProductFormula();
				break;
			case RANDOM:
				formula = new CMRandomFormula();
				break;
			case ROMAN:
				formula = new CMRomanFormula();
				break;
			case ROUND:
				formula = new CMRoundFormula();
				break;
			case SQRT:
				formula = new CMSqrtFormula();
				break;
			case SUM:
				formula = new CMSumFormula();
				break;
			case CONCAT:
				formula = new CMConcatFormula();
				break;
			case LOWER_CAT:
				formula = new CMLowerCatFormula();
				break;
			case REPLACE:
				formula = new CMReplaceFormula();
				break;
			case REPEAT_STRING:
				formula = new CMRepeatStringFormula();
				break;
			case SUB_STRING:
				formula = new CMSubStringFormula();
				break;
			case TRIM:
				formula = new CMTrimFormula();
				break;
			case TRIM_ALL:
				formula = new CMTrimAllFormula();
				break;
			case UPPER_CASE:
				formula = new CMUpperCaseFormula();
				break;
			case ACOS:
				formula = new CMAcosFormula();
				break;
			case ACOSHYP:
				formula = new CMACosHypFormula();
				break;
			case ASEN:
				formula = new CMAsenFormula();
				break;
			case ASENHYP:
				formula = new CMAsenHypFormula();
				break;
			case ATAN:
				formula = new CMAtanFormula();
				break;
			case ATANHYP:
				formula = new CMAtanHypFormula();
				break;
			case COS:
				formula = new CMCosFormula();
				break;
			case COSHYP:
				formula = new CMCosHypFormula();
				break;
			case SEN:
				formula = new CMSenFormula();
				break;
			case SENHYP:
				formula = new CMSenHypFormula();
				break;
			case TAN:
				formula = new CMTanFormula();
				break;
			case TANHYP:
				formula = new CMTanHypFormula();
				break;
			case TO_DEGREES:
				formula = new CMToDegreesFormula();
				break;
			case TO_RADIANS:
				formula = new CMToRadianFormula();
				break;
			case C:
				formula = new CMCFormula();
				break;
			case E:
				formula = new CMEFormula();
				break;
			case G:
				formula = new CMGFormula();
				break;
			case PI:
				formula = new CMPIFormula();
				break;
			//svonborries_07082007_begin
			case STRINGTONUM:
				formula = new CMStringToNumFormula();
				break;
			case NUMTOSTRING:
				formula = new CMNumberToStringFormula();
				break;
			case RSTRING:
				formula = new CMRStringFormula();
				break;
			case LSTRING:
				formula = new CMLStringFormula();
				break;
			//svonborries_07082007_end	
				
			default:
				break;
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return formula;
	}


	public String getName() {
		return name;
	}
	public CMFormulaCategory getCategory() {
		return category;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public String toString() {
		return getName();
	}

	public String getCanonicalFormula() {
		try {
			switch (this) {
			case DATE:
				canonicalFormula = (CMMessages.getString("PARAM_FORMULAS_DATE")+"("+CMMessages.getString("PARAM_FORMULAS_YEAR")+
						"; "+CMMessages.getString("PARAM_FORMULAS_MONTH")+"; "+CMMessages.getString("PARAM_FORMULAS_DAY1")+")");
				break;
			case DATENOW:
				canonicalFormula = (CMMessages.getString("FORMULA_DATENOW")+"()");
				break;
			case ADD_DAY:
				canonicalFormula = (CMMessages.getString("FORMULA_ADDDAY")+"("+CMMessages.getString("PARAM_FORMULAS_DATE")+"; "
						+CMMessages.getString("PARAM_FORMULAS_DIA")+")");
				break;
			case ADD_MONTH:
				canonicalFormula = (CMMessages.getString("FORMULA_ADDMONTH")+"("+CMMessages.getString("PARAM_FORMULAS_DATE")+"; "
						+CMMessages.getString("PARAM_FORMULAS_MONTH")+")");
				break;
			case ADD_YEAR:
				canonicalFormula = (CMMessages.getString("FORMULA_ADDYEAR")+"("+CMMessages.getString("PARAM_FORMULAS_DATE")+"; "
						+CMMessages.getString("PARAM_FORMULAS_YEAR")+")");
				break;
			case DAY_WEEK:
				canonicalFormula = (CMMessages.getString("FORMULA_DAYWEEK")+"("+CMMessages.getString("PARAM_FORMULAS_DATE")+")");
				break;
			case DIFF_DATE:
				canonicalFormula = (CMMessages.getString("FORMULA_DIFFDATE")+"("+CMMessages.getString("PARAM_FORMULAS_DATE1")+"; "
						+CMMessages.getString("PARAM_FORMULAS_DATE2")+")");
				break;
			case HOUR:
				canonicalFormula = (CMMessages.getString("FORMULA_HOUR")+"()");
				break;
			case MONTH:
				canonicalFormula = (CMMessages.getString("FORMULA_MONTH")+"()");

				break;
			case SECOND:
				canonicalFormula = (CMMessages.getString("FORMULA_SECONDS")+"("+CMMessages.getString("PARAM_FORMULAS_HOURS")+")");
				break;
			case TODAY:
				canonicalFormula = (CMMessages.getString("FORMULA_TODAY")+"()");
				break;
			case YEAR:
				canonicalFormula = (CMMessages.getString("FORMULA_YEAR")+"()");
				break;
			case ABS:
				canonicalFormula = (CMMessages.getString("FORMULA_ABS")+"("+CMMessages.getString("PARAM_FORMULAS_NUMBER")+")");
				break;
			case CEIL:
				canonicalFormula = (CMMessages.getString("FORMULA_CEIL")+"("+CMMessages.getString("PARAM_FORMULAS_NUMBER")+"; "
						+CMMessages.getString("PARAM_FORMULAS_NUM_DECIMAL")+")");
				break;
			case DIFFERENCE:
				canonicalFormula = (CMMessages.getString("FORMULA_DIFFERENCE")+"("+CMMessages.getString("PARAM_FORMULAS_NUMX")+"; "
						+CMMessages.getString("PARAM_FORMULAS_NUMY")+")");
				break;
			case DIV:
				canonicalFormula = (CMMessages.getString("FORMULA_DIV")+"("+CMMessages.getString("PARAM_FORMULAS_NUMY")+"; "
						+CMMessages.getString("PARAM_FORMULAS_NUMX")+")");
				break;
			case EXP:
				canonicalFormula = (CMMessages.getString("FORMULA_EXP")+"("+CMMessages.getString("PARAM_FORMULAS_NUMBER")+")");
				break;
			case FACT:
				canonicalFormula = (CMMessages.getString("FORMULA_FACT")+"("+CMMessages.getString("PARAM_FORMULAS_NUMBER")+")");
				break;
			case FLOOR:
				canonicalFormula = (CMMessages.getString("FORMULA_FLOOR")+"("+CMMessages.getString("PARAM_FORMULAS_NUMBER")+"; "
						+CMMessages.getString("PARAM_FORMULAS_NUM_DECIMAL")+")");
				break;
			case LN:
				canonicalFormula = (CMMessages.getString("FORMULA_LN")+"("+CMMessages.getString("PARAM_FORMULAS_NUMBER")+")");
				break;
			case LOG:
				canonicalFormula = (CMMessages.getString("FORMULA_LOG")+"("+CMMessages.getString("PARAM_FORMULAS_NUMBER")+"; "
						+CMMessages.getString("PARAM_FORMULAS_BASE")+")");
				break;
			case LOG10:
				canonicalFormula = (CMMessages.getString("FORMULA_LOG10")+"("+CMMessages.getString("PARAM_FORMULAS_NUMBER")+")");
				break;
			case MAX:
				canonicalFormula = (CMMessages.getString("FORMULA_MAX")+"("+CMMessages.getString("PARAM_FORMULAS_NUMX")+"; "
						+CMMessages.getString("PARAM_FORMULAS_NUMY")+")");
				break;
			case MIN:
				canonicalFormula = (CMMessages.getString("FORMULA_MIN")+"("+CMMessages.getString("PARAM_FORMULAS_NUMX")+"; "
						+CMMessages.getString("PARAM_FORMULAS_NUMY")+")");
				break;
			case POW:
				canonicalFormula = (CMMessages.getString("FORMULA_POW")+"("+CMMessages.getString("PARAM_FORMULAS_NUMBER")+"; "
						+CMMessages.getString("PARAM_FORMULAS_POWER")+")");
				break;
			case PRODUCT:
				canonicalFormula = (CMMessages.getString("FORMULA_PRODUCT")+"("+CMMessages.getString("PARAM_FORMULAS_NUMX")+"; "
						+CMMessages.getString("PARAM_FORMULAS_NUMY")+")");
				break;
			case RANDOM:
				canonicalFormula = (CMMessages.getString("FORMULA_RANDOM")+"()");
				break;
			case ROMAN:
				canonicalFormula = (CMMessages.getString("FORMULA_ROMAN")+"("+CMMessages.getString("PARAM_FORMULAS_NUMBER")+")");
				break;
			case ROUND:
				canonicalFormula = (CMMessages.getString("FORMULA_ROUND")+"("+CMMessages.getString("PARAM_FORMULAS_NUMBER")+"; "
						+CMMessages.getString("PARAM_FORMULAS_NUM_DECIMAL")+")");
				break;
			case SQRT:
				canonicalFormula = (CMMessages.getString("FORMULA_SQRT")+"("+CMMessages.getString("PARAM_FORMULAS_NUMBER")+")");
				break;
			case SUM:
				canonicalFormula = (CMMessages.getString("FORMULA_SUM")+"("+CMMessages.getString("PARAM_FORMULAS_NUMX")+"; "
						+CMMessages.getString("PARAM_FORMULAS_NUMY")+")");
				break;
			case CONCAT:
				canonicalFormula = (CMMessages.getString("FORMULA_CONCAT")+"("+CMMessages.getString("PARAM_FORMULAS_STRING1")+"; "
						+CMMessages.getString("PARAM_FORMULAS_STRING2")+")");
				break;
			case LOWER_CAT:
				canonicalFormula = (CMMessages.getString("FORMULA_LOWERCASE")+"("+CMMessages.getString("PARAM_FORMULAS_STRING")+")");
				break;
			case REPLACE:
				canonicalFormula = (CMMessages.getString("FORMULA_REPLACE")+"("+CMMessages.getString("PARAM_FORMULAS_STRING")+";"
						+CMMessages.getString("PARAM_FORMULAS_OLDSTRING")+";"+CMMessages.getString("PARAM_FORMULAS_NEWSTRING")+")");
				break;
			case REPEAT_STRING:
				canonicalFormula = (CMMessages.getString("FORMULA_REPEATSTRING")+"("+CMMessages.getString("PARAM_FORMULAS_STRING")
						+";"+CMMessages.getString("PARAM_FORMULAS_COMPARESTRING")+")");
				break;
			case SUB_STRING:
				canonicalFormula = (CMMessages.getString("FORMULA_SUBSTRING")+"("+CMMessages.getString("PARAM_FORMULAS_STRING")+";"
						+CMMessages.getString("PARAM_FORMULAS_LOCATION")+")");
				break;
				
			//svonborries_07082007_begin
			case STRINGTONUM:
				canonicalFormula = (CMMessages.getString("FORMULA_STRINGTONUM") + "(" + CMMessages.getString("PARAM_FORMULAS_STRING") + ")");
				break;
			case NUMTOSTRING:
				canonicalFormula = (CMMessages.getString("FORMULA_NUMTOSTRING") + "(" + CMMessages.getString("PARAM_FORMULAS_NUMBER") + ")");
				break;
			case RSTRING:
				canonicalFormula = (CMMessages.getString("FORMULA_RSTRING") + "(" + CMMessages.getString("PARAM_FORMULAS_STRING") + ";"
						+CMMessages.getString("PARAM_FORMULAS_POSITION") + ")");
				break;
			case LSTRING:
				canonicalFormula = (CMMessages.getString("FORMULA_LSTRING") + "(" + CMMessages.getString("PARAM_FORMULAS_STRING") + ";"
						+CMMessages.getString("PARAM_FORMULAS_POSITION") + ")");
				break;
			//svonborries_07082007_end	
			case TRIM:
				canonicalFormula = (CMMessages.getString("FORMULA_TRIM")+"("+CMMessages.getString("PARAM_FORMULAS_STRING")+")");
				break;
			case TRIM_ALL:
				canonicalFormula = (CMMessages.getString("FORMULA_TRIMALL")+"("+CMMessages.getString("PARAM_FORMULAS_STRING")+")");
				break;
			case UPPER_CASE:
				canonicalFormula = (CMMessages.getString("FORMULA_UPPERCASE")+"("+CMMessages.getString("PARAM_FORMULAS_STRING")+")");
				break;
			case ACOS:
				canonicalFormula = CMMessages.getString("FORMULA_ACOS")+"("+CMMessages.getString("PARAM_FORMULAS_NUMBER")+")";
				break;
			case ACOSHYP:
				canonicalFormula = (CMMessages.getString("FORMULA_ACOSHYP")+"("+CMMessages.getString("PARAM_FORMULAS_NUMBER")+")");
				break;
			case ASEN:
				canonicalFormula = (CMMessages.getString("FORMULA_ASEN")+"("+CMMessages.getString("PARAM_FORMULAS_NUMBER")+")");
				break;
			case ASENHYP:
				canonicalFormula = (CMMessages.getString("FORMULA_ASENHYP")+"("+CMMessages.getString("PARAM_FORMULAS_NUMBER")+")");
				break;
			case ATAN:
				canonicalFormula = (CMMessages.getString("FORMULA_ATAN")+"("+CMMessages.getString("PARAM_FORMULAS_NUMBER")+")");
				break;
			case ATANHYP:
				canonicalFormula = (CMMessages.getString("FORMULA_ATANHYP")+"("+CMMessages.getString("PARAM_FORMULAS_NUMBER")+")");
				break;
			case COS:
				canonicalFormula = (CMMessages.getString("FORMULA_COS")+"("+CMMessages.getString("PARAM_FORMULAS_NUMBER")+")");
				break;
			case COSHYP:
				canonicalFormula = (CMMessages.getString("FORMULA_COSHYP")+"("+CMMessages.getString("PARAM_FORMULAS_NUMBER")+")");
				break;
			case SEN:
				canonicalFormula = (CMMessages.getString("FORMULA_SEN")+"("+CMMessages.getString("PARAM_FORMULAS_NUMBER")+")");
				break;
			case SENHYP:
				canonicalFormula = (CMMessages.getString("FORMULA_SENHYP")+"("+CMMessages.getString("PARAM_FORMULAS_NUMBER")+")");
				break;
			case TAN:
				canonicalFormula = (CMMessages.getString("FORMULA_TAN")+"("+CMMessages.getString("PARAM_FORMULAS_NUMBER")+")");
				break;
			case TANHYP:
				canonicalFormula = (CMMessages.getString("FORMULA_TANHYP")+"("+CMMessages.getString("PARAM_FORMULAS_NUMBER")+")");
				break;
			case TO_DEGREES:
				canonicalFormula = (CMMessages.getString("FORMULA_TODEGREES")+"("+CMMessages.getString("PARAM_FORMULAS_ANGLE")+")");
				break;
			case TO_RADIANS:
				canonicalFormula = (CMMessages.getString("FORMULA_TORADIANS")+"("+CMMessages.getString("PARAM_FORMULAS_GRADE")+")");
				break;
			case C:
				canonicalFormula = BusinessRules.FORMULAS_CATEGORY_CONSTANTS_C();
				break;
			case E:
				canonicalFormula = BusinessRules.FORMULAS_CATEGORY_CONSTANTS_E();
				break;
			case G:
				canonicalFormula = BusinessRules.FORMULAS_CATEGORY_CONSTANTS_G();
				break;
			case PI:
				canonicalFormula = BusinessRules.FORMULAS_CATEGORY_CONSTANTS_PI();
				break;
			default:
				break;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		return canonicalFormula;
	}

	public List<CMFormulaParameter> getAllowedParam() {
		if(allowedParam == null)
			allowedParameters();
		return allowedParam;
	}

	private void allowedParameters() {
		try {
			switch (this) {
			case DATE:
				allowedParam = new ArrayList <CMFormulaParameter>();
				allowedParam.add(CMFormulaParameter.YEAR);
				allowedParam.add(CMFormulaParameter.MONTH);
				allowedParam.add(CMFormulaParameter.DAY);
				break;
			case DATENOW:
				allowedParam = new ArrayList <CMFormulaParameter>();
				allowedParam.add(CMFormulaParameter.NULLPARAM);
				break;
			case ADD_DAY:
				allowedParam = new ArrayList <CMFormulaParameter>();
				allowedParam.add(CMFormulaParameter.DATE);
				allowedParam.add(CMFormulaParameter.DAY);
				break;
			case ADD_MONTH:
				allowedParam = new ArrayList <CMFormulaParameter>();
				allowedParam.add(CMFormulaParameter.DATE);
				allowedParam.add(CMFormulaParameter.MONTH);
				break;
			case ADD_YEAR:
				allowedParam = new ArrayList <CMFormulaParameter>();
				allowedParam.add(CMFormulaParameter.DATE);
				allowedParam.add(CMFormulaParameter.YEAR);
				break;
			case DAY_WEEK:
				allowedParam = new ArrayList <CMFormulaParameter>();
				allowedParam.add(CMFormulaParameter.DATE);
				break;
			case DIFF_DATE:
				allowedParam = new ArrayList <CMFormulaParameter>();
				allowedParam.add(CMFormulaParameter.DATE1);
				allowedParam.add(CMFormulaParameter.DATE2);
				break;
			case HOUR:
				allowedParam = new ArrayList <CMFormulaParameter>();
				allowedParam.add(CMFormulaParameter.NULLPARAM);
				break;
			case MONTH:
				allowedParam = new ArrayList <CMFormulaParameter>();
				allowedParam.add(CMFormulaParameter.NULLPARAM);
				break;
			case SECOND:
				allowedParam = new ArrayList <CMFormulaParameter>();
				allowedParam.add(CMFormulaParameter.HOUR);
				break;
			case TODAY:
				allowedParam = new ArrayList <CMFormulaParameter>();
				allowedParam.add(CMFormulaParameter.NULLPARAM);
				break;
			case YEAR:
				allowedParam = new ArrayList <CMFormulaParameter>();
				allowedParam.add(CMFormulaParameter.NULLPARAM);
				break;
			case ABS:
				allowedParam = new ArrayList <CMFormulaParameter>();
				allowedParam.add(CMFormulaParameter.NUMBER);
				break;
			case CEIL:
				allowedParam = new ArrayList <CMFormulaParameter>();
				allowedParam.add(CMFormulaParameter.NUMBER);
				allowedParam.add(CMFormulaParameter.DECIMAL);
				break;
			case DIFFERENCE:
				allowedParam = new ArrayList <CMFormulaParameter>();
				allowedParam.add(CMFormulaParameter.NUMBERX);
				allowedParam.add(CMFormulaParameter.NUMBERY);
				break;
			case DIV:
				allowedParam = new ArrayList <CMFormulaParameter>();
				allowedParam.add(CMFormulaParameter.NUMBERY);
				allowedParam.add(CMFormulaParameter.NUMBERX);
				break;
			case EXP:
				allowedParam = new ArrayList <CMFormulaParameter>();
				allowedParam.add(CMFormulaParameter.NUMBER);
				break;
			case FACT:
				allowedParam = new ArrayList <CMFormulaParameter>();
				allowedParam.add(CMFormulaParameter.NUMBER);
				break;
			case FLOOR:
				allowedParam = new ArrayList <CMFormulaParameter>();
				allowedParam.add(CMFormulaParameter.NUMBER);
				allowedParam.add(CMFormulaParameter.DECIMAL);
				break;
			case LN:
				allowedParam = new ArrayList <CMFormulaParameter>();
				allowedParam.add(CMFormulaParameter.NUMBER);
				break;
			case LOG:
				allowedParam = new ArrayList <CMFormulaParameter>();
				allowedParam.add(CMFormulaParameter.NUMBER);
				allowedParam.add(CMFormulaParameter.BASE);
				break;
			case LOG10:
				allowedParam = new ArrayList <CMFormulaParameter>();
				allowedParam.add(CMFormulaParameter.NUMBER);
				break;
			case MAX:
				allowedParam = new ArrayList <CMFormulaParameter>();
				allowedParam.add(CMFormulaParameter.NUMBERX);
				allowedParam.add(CMFormulaParameter.NUMBERY);
				break;
			case MIN:
				allowedParam = new ArrayList <CMFormulaParameter>();
				allowedParam.add(CMFormulaParameter.NUMBERX);
				allowedParam.add(CMFormulaParameter.NUMBERY);
				break;
			case POW:
				allowedParam = new ArrayList <CMFormulaParameter>();
				allowedParam.add(CMFormulaParameter.NUMBER);
				allowedParam.add(CMFormulaParameter.POWER);
				break;
			case PRODUCT:
				allowedParam = new ArrayList <CMFormulaParameter>();
				allowedParam.add(CMFormulaParameter.NUMBERX);
				allowedParam.add(CMFormulaParameter.NUMBERY);
				break;
			case RANDOM:
				allowedParam = new ArrayList <CMFormulaParameter>();
				allowedParam.add(CMFormulaParameter.NULLPARAM);
				break;
			case ROMAN:
				allowedParam = new ArrayList <CMFormulaParameter>();
				allowedParam.add(CMFormulaParameter.NUMBER);
				break;
			case ROUND:
				allowedParam = new ArrayList <CMFormulaParameter>();
				allowedParam.add(CMFormulaParameter.NUMBER);
				allowedParam.add(CMFormulaParameter.DECIMAL);
				break;
			case SQRT:
				allowedParam = new ArrayList <CMFormulaParameter>();
				allowedParam.add(CMFormulaParameter.NUMBER);
				break;
			case SUM:
				allowedParam = new ArrayList <CMFormulaParameter>();
				allowedParam.add(CMFormulaParameter.NUMBERX);
				allowedParam.add(CMFormulaParameter.NUMBERY);
				break;
			case CONCAT:
				allowedParam = new ArrayList <CMFormulaParameter>();
				allowedParam.add(CMFormulaParameter.STRING1);
				allowedParam.add(CMFormulaParameter.STRING2);
				break;
			case LOWER_CAT:
				allowedParam = new ArrayList <CMFormulaParameter>();
				allowedParam.add(CMFormulaParameter.STRING);
				break;
			case REPLACE:
				allowedParam = new ArrayList <CMFormulaParameter>();
				allowedParam.add(CMFormulaParameter.STRING);
				allowedParam.add(CMFormulaParameter.OLDSTRING);
				allowedParam.add(CMFormulaParameter.NEWSTRING);
				break;
			case REPEAT_STRING:
				allowedParam = new ArrayList <CMFormulaParameter>();
				allowedParam.add(CMFormulaParameter.STRING);
				allowedParam.add(CMFormulaParameter.COMPARESTRING);
				break;
			case SUB_STRING:
				allowedParam = new ArrayList <CMFormulaParameter>();
				allowedParam.add(CMFormulaParameter.STRING);
				allowedParam.add(CMFormulaParameter.LOCATIONSTRING);
				break;
				
			//svonborries_07082007_begin
			case STRINGTONUM:
				allowedParam = new ArrayList<CMFormulaParameter>();
				allowedParam.add(CMFormulaParameter.STRING);
				break;
			case NUMTOSTRING:
				allowedParam = new ArrayList<CMFormulaParameter>();
				allowedParam.add(CMFormulaParameter.NUMBER);
				break;
			case RSTRING:
				allowedParam = new ArrayList<CMFormulaParameter>();
				allowedParam.add(CMFormulaParameter.STRING);
				allowedParam.add(CMFormulaParameter.POSITION);
				break;
			case LSTRING:
				allowedParam = new ArrayList<CMFormulaParameter>();
				allowedParam.add(CMFormulaParameter.STRING);
				allowedParam.add(CMFormulaParameter.POSITION);
				break;
			//svonborries_07082007_end	
				
			case TRIM:
				allowedParam = new ArrayList <CMFormulaParameter>();
				allowedParam.add(CMFormulaParameter.STRING);
				break;
			case TRIM_ALL:
				allowedParam = new ArrayList <CMFormulaParameter>();
				allowedParam.add(CMFormulaParameter.STRING);
				break;
			case UPPER_CASE:
				allowedParam = new ArrayList <CMFormulaParameter>();
				allowedParam.add(CMFormulaParameter.STRING);
				break;
			case ACOS:
				allowedParam = new ArrayList <CMFormulaParameter>();
				allowedParam.add(CMFormulaParameter.NUMBER);
				break;
			case ACOSHYP:
				allowedParam = new ArrayList <CMFormulaParameter>();
				allowedParam.add(CMFormulaParameter.NUMBER);
				break;
			case ASEN:
				allowedParam = new ArrayList <CMFormulaParameter>();
				allowedParam.add(CMFormulaParameter.NUMBER);
				break;
			case ASENHYP:
				allowedParam = new ArrayList <CMFormulaParameter>();
				allowedParam.add(CMFormulaParameter.NUMBER);
				break;
			case ATAN:
				allowedParam = new ArrayList <CMFormulaParameter>();
				allowedParam.add(CMFormulaParameter.NUMBER);
				break;
			case ATANHYP:
				allowedParam = new ArrayList <CMFormulaParameter>();
				allowedParam.add(CMFormulaParameter.NUMBER);
				break;
			case COS:
				allowedParam = new ArrayList <CMFormulaParameter>();
				allowedParam.add(CMFormulaParameter.NUMBER);
				break;
			case COSHYP:
				allowedParam = new ArrayList <CMFormulaParameter>();
				allowedParam.add(CMFormulaParameter.NUMBER);
				break;
			case SEN:
				allowedParam = new ArrayList <CMFormulaParameter>();
				allowedParam.add(CMFormulaParameter.NUMBER);
				break;
			case SENHYP:
				allowedParam = new ArrayList <CMFormulaParameter>();
				allowedParam.add(CMFormulaParameter.NUMBER);
				break;
			case TAN:
				allowedParam = new ArrayList <CMFormulaParameter>();
				allowedParam.add(CMFormulaParameter.NUMBER);
				break;
			case TANHYP:
				allowedParam = new ArrayList <CMFormulaParameter>();
				allowedParam.add(CMFormulaParameter.NUMBER);
				break;
			case TO_DEGREES:
				allowedParam = new ArrayList <CMFormulaParameter>();
				allowedParam.add(CMFormulaParameter.ANGLE);
				break;
			case TO_RADIANS:
				allowedParam = new ArrayList <CMFormulaParameter>();
				allowedParam.add(CMFormulaParameter.GRADE);
				break;
			case C:
				allowedParam = new ArrayList <CMFormulaParameter>();
				allowedParam.add(CMFormulaParameter.NULLPARAM);
				break;
			case E:
				allowedParam = new ArrayList <CMFormulaParameter>();
				allowedParam.add(CMFormulaParameter.NULLPARAM);
				break;
			case G:
				allowedParam = new ArrayList <CMFormulaParameter>();
				allowedParam.add(CMFormulaParameter.NULLPARAM);
				break;
			case PI:
				allowedParam = new ArrayList <CMFormulaParameter>();
				allowedParam.add(CMFormulaParameter.NULLPARAM);
				break;
				
			default:
				break;
			}
		} catch (Exception e) {
			
		}
		
	}

	public String getDefaultPattern() {
		if(this.getCategory() == CMFormulaCategory.TEXT)
		//svonborries_07082007_begin	
			defaultPattern = BusinessRules.FORMULAS_FORMAT_STRING;
			if(this == CMFormulas.STRINGTONUM)
				defaultPattern = BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY;
		//svonborries_07082007_end	
		else if((this.getCategory() == CMFormulaCategory.MATHEMATICS)||(this.getCategory() == CMFormulaCategory.TRIGONOMETRY)){
			defaultPattern = BusinessRules.FORMULAS_FORMAT_COMUN_MATH_TRIGONOMETRY;
			if(this == CMFormulas.CEIL)
				defaultPattern = BusinessRules.FORMULAS_FORMAT_MATH_CEIL_FLOOR_RINT_ROUND;
			else if(this == CMFormulas.FLOOR)
				defaultPattern = BusinessRules.FORMULAS_FORMAT_MATH_CEIL_FLOOR_RINT_ROUND;
			else if(this == CMFormulas.ROUND)
				defaultPattern = BusinessRules.FORMULAS_FORMAT_MATH_CEIL_FLOOR_RINT_ROUND;
			else if(this == CMFormulas.FACT)
				defaultPattern = BusinessRules.FORMULAS_FORMAT_MATH_FACT;
			else if(this == CMFormulas.RANDOM)
				defaultPattern = BusinessRules.FORMULAS_FORMAT_RANDOM;
			else if(this == CMFormulas.ROMAN)
				defaultPattern = BusinessRules.FORMULAS_FORMAT_STRING;
		}
		else if(this.getCategory() == CMFormulaCategory.DATE_TIME){
			defaultPattern = BusinessRules.FORMULAS_FORMAT_DATE_DATE;
			if(this == CMFormulas.DATENOW)
				defaultPattern = BusinessRules.FORMULAS_FORMAT_DATE_DATENOW;
			else if(this == CMFormulas.DAY_WEEK)
				defaultPattern = BusinessRules.FORMULAS_FORMAT_DATE_DAYWEEK;
			else if (this == CMFormulas.DIFF_DATE)
				defaultPattern = BusinessRules.FORMULAS_FORMAT_DIFFDATE;
			else if(this == CMFormulas.HOUR)
				defaultPattern = BusinessRules.FORMULAS_FORMAT_DATE_HOUR;
			else if(this == CMFormulas.MONTH)
				defaultPattern = BusinessRules.FORMULAS_FORMAT_DATE_MONTH;
			else if(this == CMFormulas.SECOND)
				defaultPattern = BusinessRules.FORMULAS_FORMAT_DATE_SECOND;
			else if(this == CMFormulas.TODAY)
				defaultPattern = BusinessRules.FORMULAS_FORMAT_DATE_TODAY;
			else if(this == CMFormulas.YEAR)
				defaultPattern = BusinessRules.FORMULAS_FORMAT_DATE_YEAR;
		}
		else if(this.getCategory() == CMFormulaCategory.CONSTANTS){
			if(this == CMFormulas.C)
				defaultPattern = "#,########E00";
			else if(this == CMFormulas.E)
				defaultPattern = "#.######";
			else if(this == CMFormulas.G)
				defaultPattern = BusinessRules.FORMULAS_FORMAT_G;
			else if(this == CMFormulas.PI)
				defaultPattern = BusinessRules.FORMULAS_FORMAT_PI;
		}

		return defaultPattern;
	}

	public String getFormulaType() {
		selectFormulaType();
		return formulaType;
	}

	private void selectFormulaType() {
		try {
			if(this.getCategory() == CMFormulaCategory.DATE_TIME)
				formulaType = BusinessRules.TESTDATA_STATE_DATETIME;
			else if(this.getCategory() == CMFormulaCategory.MATHEMATICS){
				formulaType = BusinessRules.TESTDATA_STATE_FLOAT;
				if((this == CMFormulas.FACT)||(this == CMFormulas.ROMAN))
					formulaType = BusinessRules.TESTDATA_STATE_INT;
			}
			else if(this.getCategory() == CMFormulaCategory.TEXT){
				formulaType = BusinessRules.TESTDATA_STATE_VARCHAR;
				if(this == CMFormulas.REPEAT_STRING)
					formulaType = BusinessRules.TESTDATA_STATE_INT;
				//svonborries_07082007_begin
				if(this == CMFormulas.STRINGTONUM)
					formulaType = BusinessRules.TESTDATA_STATE_FLOAT;
				//svonborries_07082007_end
			}
			else if(this.category == CMFormulaCategory.TRIGONOMETRY)
				formulaType = BusinessRules.TESTDATA_STATE_REAL;
			else if(this.getCategory() == CMFormulaCategory.CONSTANTS)
				formulaType = BusinessRules.TESTDATA_STATE_REAL;
			
		} catch (Exception e) {
			
		}
	}
	
	
}
