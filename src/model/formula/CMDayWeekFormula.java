/**
 * 26/10/2006
 * svonborries
 */
package model.formula;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
public class CMDayWeekFormula extends CMAbstractFormula {


	/* (non-Javadoc)
	 * @see model.ICMFormula#calculate()
	 * 26/10/2006
	 * svonborries
	 */
	public Object calculate() throws Exception {
            Date param1 =(Date) getCalculatedParameter(CMFormulaParameter.DATE);
            String fecha = param1.toString();
            if(param1 != null)
            {
            	DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
            	fecha=format.format(param1);
            }
            DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
            return (Date)formatter.parse(fecha);
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
		super.setFormulaEnum(CMFormulas.DAY_WEEK);
		return CMFormulas.DAY_WEEK;
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
		return getFormulaEnum().getName()+"("+getParameter(CMFormulaParameter.DATE).toString()+")";
	}

}
