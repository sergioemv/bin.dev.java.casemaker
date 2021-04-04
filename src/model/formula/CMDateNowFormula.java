/**
 * 26/10/2006
 * svonborries
 */
package model.formula;

import java.util.Calendar;
import java.util.Date;


import bi.controller.testdata.formula.CMFormulaCategory;

import bi.controller.testdata.formula.CMFormulas;
import bi.view.lang.CMMessages;
import bi.view.utils.CMFormatFactory;


/**
 * @author svonborries
 *
 */
public class CMDateNowFormula extends CMAbstractFormula {

	/* (non-Javadoc)
	 * @see model.ICMFormula#calculate()
	 * 26/10/2006
	 * svonborries
	 */


	
	public Object calculate() {
        	Calendar calendar = Calendar.getInstance();
            return (Date) calendar.getTime();
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
		super.setFormulaEnum(CMFormulas.DATENOW);
		return CMFormulas.DATENOW;
	}

	/* (non-Javadoc)
	 * @see model.ICMFormula#getValueResult()
	 * @return
	 * 26/10/2006
	 * svonborries
	 */
	public String getFormattedValueResult() {
		try {
			return CMFormatFactory.formatDate(getFormulaEnum().getDefaultPattern(), (Date)getValue());
		} catch (Exception e) {
			return CMMessages.getString("TESTDATA_MISSING_PARAMETER");
		}
	}

	@Override
	public String toString() {
		return getFormulaEnum().getCanonicalFormula();
	}

}
