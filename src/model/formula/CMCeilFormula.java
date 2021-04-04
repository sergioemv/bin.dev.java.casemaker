/**
 * 27/10/2006
 * svonborries
 */
package model.formula;




import bi.controller.testdata.formula.CMFormulaCategory;
import bi.controller.testdata.formula.CMFormulaParameter;
import bi.controller.testdata.formula.CMFormulas;
import bi.controller.utils.CMNumberUtils;
import bi.view.lang.CMMessages;
import bi.view.utils.CMFormatFactory;



/**
 * @author svonborries
 *
 */
public class CMCeilFormula extends CMAbstractFormula {



	/* (non-Javadoc)
	 * @see model.ICMFormula#calculate()
	 * 27/10/2006
	 * svonborries
	 */
	public Object calculate() throws Exception {
        	Number param1 = (Number) getCalculatedParameter(CMFormulaParameter.NUMBER);
        	Number param2 = (Number) getCalculatedParameter(CMFormulaParameter.DECIMAL);
            double toCeilNumber=Double.parseDouble(param1.toString());
            Number result;
			if(toCeilNumber >=0)
            	 result = CMNumberUtils.ceilTo(param2.intValue(),toCeilNumber);
            else{
            	 result = CMNumberUtils.ceilTo(param2.intValue(),Math.abs(toCeilNumber));
            	 result = result.doubleValue() * -1;
            }
			return result;
	}

	/* (non-Javadoc)
	 * @see model.ICMFormula#getCategory()
	 * @return
	 * 27/10/2006
	 * svonborries
	 */
	public CMFormulaCategory getCategory() {
		return CMFormulaCategory.MATHEMATICS;
	}

	/* (non-Javadoc)
	 * @see model.ICMFormula#getFormula()
	 * @return
	 * 27/10/2006
	 * svonborries
	 */
	public CMFormulas getFormulaEnum() {
		super.setFormulaEnum(CMFormulas.CEIL);
		return CMFormulas.CEIL;
	}

	/* (non-Javadoc)
	 * @see model.ICMFormula#getValueResult()
	 * @return
	 * 27/10/2006
	 * svonborries
	 */
	public String getFormattedValueResult(){

		try {
			String pattern = CMNumberUtils.getFormatRoundTo(((Number) getCalculatedParameter(CMFormulaParameter.DECIMAL)).intValue(),
					getFormulaEnum().getDefaultPattern());
			return CMFormatFactory.formatNumber(pattern, (Number)getValue());
		} catch (Exception e) {
			return CMMessages.getString("TESTDATA_MISSING_PARAMETER");
		}
	}

	@Override
	public String toString() {
		return getFormulaEnum().getName()+"("+getParameter(CMFormulaParameter.NUMBER).toString()+"; "
			+getParameter(CMFormulaParameter.DECIMAL).toString()+")";
	}

}
