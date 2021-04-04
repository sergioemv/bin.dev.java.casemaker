/**
 * 26/10/2006
 * svonborries
 */
package model.formula;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import bi.controller.testdata.formula.CMFormulaCategory;
import bi.controller.testdata.formula.CMFormulaParameter;
import bi.controller.testdata.formula.CMFormulas;
import bi.view.lang.CMMessages;



/**
 * @author svonborries
 *
 */
public class CMSecondFormula extends CMAbstractFormula {



	/* (non-Javadoc)
	 * @see model.ICMFormula#calculate()
	 * 26/10/2006
	 * svonborries
	 */
	public Object calculate()  throws Exception{
            Date result = (Date) getCalculatedParameter(CMFormulaParameter.HOUR);
            if(result == null)
            	throw new Exception();
            return result;
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
		super.setFormulaEnum(CMFormulas.SECOND);
		return CMFormulas.SECOND;
	}

	/* (non-Javadoc)
	 * @see model.ICMFormula#getValueResult()
	 * @return
	 * 26/10/2006
	 * svonborries
	 */
	public String getFormattedValueResult(){
        
        try {
        	Calendar calendar = new GregorianCalendar();
			calendar.setTime((Date) getValue());
	        Integer value = calendar.get(Calendar.SECOND);	        
			//return CMFormatFactory.formatNumber(getFormulaEnum().getDefaultPattern(), value);
	        return value.toString();
		} catch (Exception e) {
			return CMMessages.getString("TESTDATA_MISSING_PARAMETER");
		}
	}

	@Override
	public String toString() {
		return getFormulaEnum().getName()+"("+getParameter(CMFormulaParameter.HOUR).toString()+")";
	}

}
