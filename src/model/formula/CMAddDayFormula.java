/**
 * 26/10/2006
 * svonborries
 */
package model.formula;

import java.util.Calendar;
import java.util.Date;
import bi.controller.testdata.formula.CMFormulaCategory;
import bi.controller.testdata.formula.CMFormulaParameter;
import bi.controller.testdata.formula.CMFormulas;
import bi.view.lang.CMMessages;
import bi.view.utils.CMFormatFactory;

/**
 * @author svonborries
 *
 */
public class CMAddDayFormula extends CMAbstractFormula {


	/* (non-Javadoc)
	 * @see model.ICMFormula#calculate()
	 * 26/10/2006
	 * svonborries
	 */
	
	public Object calculate() throws Exception {
            Date param1 = (Date) getCalculatedParameter(CMFormulaParameter.DATE);
            Number param2 = (Number) getCalculatedParameter(CMFormulaParameter.DAY);
            //Date date =CMFormatFactory.getInstance().stringToDate(param1.trim());
            long number = (24*60*60*1000)*Long.parseLong(param2.toString());
            Calendar miCalen=Calendar.getInstance();//.setTime(date);
            miCalen.setTime(param1);
            long timeTomillis=miCalen.getTimeInMillis();
			long datesum= timeTomillis + number;
            miCalen.setTimeInMillis(datesum);
            return (Date) miCalen.getTime();
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
		super.setFormulaEnum(CMFormulas.ADD_DAY);
		return CMFormulas.ADD_DAY;
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
			+getParameter(CMFormulaParameter.DAY).toString()+")";
	}


}
