package model.formula;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import bi.controller.testdata.formula.CMFormulaCategory;
import bi.controller.testdata.formula.CMFormulaParameter;
import bi.controller.testdata.formula.CMFormulas;
import bi.controller.utils.CMDateUtils;
import bi.view.lang.CMMessages;
import bi.view.utils.CMFormatFactory;


public class CMDateFormula extends CMAbstractFormula {
	


	
	public Object calculate() throws Exception {
			Number year = (Number) getCalculatedParameter(CMFormulaParameter.YEAR);
			Number month = (Number) getCalculatedParameter(CMFormulaParameter.MONTH);
			Number day = (Number) getCalculatedParameter(CMFormulaParameter.DAY);
		CMDateUtils.restrictedDateValues(year.toString(),month.toString(),day.toString());
        DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        String fecha=year.toString()+"/"+month.toString()+"/"+day.toString();
        return (Date)formatter.parse(fecha);
	}

	public final CMFormulaCategory getCategory() {
		return CMFormulaCategory.DATE_TIME;
	}

	public final CMFormulas getFormulaEnum() {
		super.setFormulaEnum(CMFormulas.DATE);
		return CMFormulas.DATE;
	}

	public String getFormattedValueResult(){
		try {
			return CMFormatFactory.formatDate(getFormulaEnum().getDefaultPattern(), (Date)getValue());
		} catch (Exception e) {
			return CMMessages.getString("TESTDATA_MISSING_PARAMETER");
		}
	}

	@Override
	public String toString() {
		return getFormulaEnum().getName()+"("+getParameter(CMFormulaParameter.YEAR).toString()+"; "
		+getParameter(CMFormulaParameter.MONTH).toString()+"; "
		+getParameter(CMFormulaParameter.DAY).toString()+")";
	}

}
