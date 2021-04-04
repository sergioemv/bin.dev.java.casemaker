package model.formula;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


import bi.controller.testdata.formula.CMFormulaCategory;
import bi.controller.testdata.formula.CMFormulaParameter;
import bi.controller.testdata.formula.CMFormulas;
import bi.view.lang.CMMessages;
import bi.view.utils.CMFormatFactory;

public class CMDiffDateFormula extends CMAbstractFormula {



	
	public Object calculate() throws Exception {
            Date date1 =(Date) getCalculatedParameter(CMFormulaParameter.DATE1);
            if(date1 != null)
            {
            	DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
            	String fecha=format.format(date1);
                DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
                date1 = (Date)formatter.parse(fecha);
            }

			Date date2 =(Date) getCalculatedParameter(CMFormulaParameter.DATE2);
			if(date2 != null)
            {
            	DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
            	String fecha=format.format(date2);
                DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
                date2 = (Date)formatter.parse(fecha);
            }
            Calendar miCalenDate1=Calendar.getInstance();
            Calendar miCalenDate2=Calendar.getInstance();
            miCalenDate1.setTime(date1);
            miCalenDate2.setTime(date2);
            long result2 =0;
             if(miCalenDate1.after(miCalenDate2)){
                result2= (miCalenDate1.getTimeInMillis()-miCalenDate2.getTimeInMillis());
             }
             else{
                if(miCalenDate2.after(miCalenDate1))
                	result2=(miCalenDate2.getTimeInMillis()-miCalenDate1.getTimeInMillis());
             }
             if(result2 !=0)
             	result2=(result2/(24*60*60*1000));

             return (Number)result2;
	}

	public CMFormulaCategory getCategory() {
		return CMFormulaCategory.DATE_TIME;
	}

	public CMFormulas getFormulaEnum() {
		super.setFormulaEnum(CMFormulas.DIFF_DATE);
		return CMFormulas.DIFF_DATE;
	}

	public String getFormattedValueResult(){
		try {
			return CMFormatFactory.formatNumber(getFormulaEnum().getDefaultPattern(), (Number) getValue());
		} catch (Exception e) {
			return CMMessages.getString("TESTDATA_MISSING_PARAMETER");
		}
	}

	@Override
	public String toString() {
		return getFormulaEnum().getName()+"("+getParameter(CMFormulaParameter.DATE1).toString()+"; "
		+getParameter(CMFormulaParameter.DATE2).toString()+")";
	}

}
