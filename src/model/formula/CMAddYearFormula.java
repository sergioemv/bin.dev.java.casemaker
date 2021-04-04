/**
 * 26/10/2006
 * svonborries
 */
package model.formula;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import bi.controller.testdata.formula.CMFormulaCategory;
import bi.controller.testdata.formula.CMFormulaParameter;
import bi.controller.testdata.formula.CMFormulas;
import bi.view.lang.CMMessages;
import bi.view.utils.CMFormatFactory;


/**
 * @author svonborries
 *
 */
public class CMAddYearFormula extends CMAbstractFormula {


	/* (non-Javadoc)
	 * @see model.ICMFormula#calculate()
	 * 26/10/2006
	 * svonborries
	 */
	public Object calculate() throws Exception {
            Date param1 =(Date) getCalculatedParameter(CMFormulaParameter.DATE);
            Number param2 = (Number) getCalculatedParameter(CMFormulaParameter.YEAR);
            SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
            String str = df.format(param1);
        	String year = str.substring(0, str.indexOf("/"));
        	String month = str.substring(str.indexOf("/") + 1, str.lastIndexOf("/"));
        	String day = str.substring(str.lastIndexOf("/") + 1);
        	
        	GregorianCalendar greCalendar = new GregorianCalendar(Integer.parseInt(year),Integer.parseInt(month)-1
        			,Integer.parseInt(day));
			greCalendar.add(Calendar.YEAR, param2.intValue());
			return (Date) greCalendar.getTime();
	}

	/* (non-Javadoc)
	 * @see model.ICMFormula#getCategory()
	 * @return
	 * 26/10/2006
	 * svonborries
	 */
	public CMFormulaCategory getCategory() {
		return CMFormulaCategory.DATE_TIME;
	}

	/* (non-Javadoc)
	 * @see model.ICMFormula#getFormula()
	 * @return
	 * 26/10/2006
	 * svonborries
	 */
	public CMFormulas getFormulaEnum() {
		super.setFormulaEnum(CMFormulas.ADD_YEAR);
		return CMFormulas.ADD_YEAR;
	}

	/* (non-Javadoc)
	 * @see model.ICMFormula#getValueResult()
	 * @return
	 * 26/10/2006
	 * svonborries
	 */
	public String getFormattedValueResult(){
		try {
			return CMFormatFactory.formatDate(getFormulaEnum().getDefaultPattern(), (Date)getValue());
		} catch (Exception e) {
			return CMMessages.getString("TESTDATA_MISSING_PARAMETER");
		}
	}

	@Override
	public String toString() {
		return getFormulaEnum().getName()+"("+getParameter(CMFormulaParameter.DATE).toString()+"; "
		+getParameter(CMFormulaParameter.YEAR).toString()+")";
	}

}
