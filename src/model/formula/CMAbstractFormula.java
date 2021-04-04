/**
 * 03/11/2006
 * svonborries
 */
package model.formula;

import java.util.ArrayList;
import java.util.List;
import bi.controller.testdata.formula.CMFormulaParameter;
import bi.controller.testdata.formula.CMFormulas;

import model.CMDefaultParameter;
import model.ICMFormula;
import model.ICMParameter;

/**
 * @author svonborries
 *
 */
public abstract class CMAbstractFormula implements ICMFormula, Cloneable {

	/* (non-Javadoc)
	 * @see model.ICMFormula#calculate()
	 * 03/11/2006
	 * svonborries
	 */
	
	private List<Object> parameterList = null;
	private static CMFormulas formulaEnum = null;
	
	
	public CMAbstractFormula(){
		parameterList = new ArrayList<Object>();
	}

	/* (non-Javadoc)
	 * @see model.ICMFormula#getParameter(bi.controller.testdata.formula.CMFormulaParameter)
	 * @param parameterType
	 * @return
	 * 03/11/2006
	 * svonborries
	 */
	public Object getParameter(CMFormulaParameter parameterType) {
		if (parameterList == null){
			parameterList = new ArrayList<Object>();
		}
		for(Object parameter: parameterList){
			if(((ICMParameter)parameter).getTypeParameter() == parameterType){
				return ((ICMParameter)parameter);
			}
		}
		CMDefaultParameter defaultParam = new CMDefaultParameter(parameterType);
		parameterList.add(defaultParam);
		return defaultParam;
	}

	/* (non-Javadoc)
	 * @see model.ICMFormula#getType()
	 * @return
	 * 03/11/2006
	 * svonborries
	 */
	public String getTypeFormula() {
		return getFormulaEnum().getFormulaType();
	}

	@Override
	public abstract String toString(); 

	public CMFormulas getFormulaEnum() {
		return formulaEnum;
	}

	public void setFormulaEnum(CMFormulas formulaEnum) {
		CMAbstractFormula.formulaEnum = formulaEnum;
	}
	
	public void addParameter (Object value){
		List<CMFormulaParameter> list = new ArrayList<CMFormulaParameter>();
		for(Object parameter: parameterList){
			list.add(((CMDefaultParameter)parameter).getTypeParameter());
		}
		if(!list.contains((((CMDefaultParameter)value).getTypeParameter()))){
			parameterList.add(value);
		}
	}
	
	
	/**
	 * @param formulaParameter, needed to begin a serch in the List of parameters that posses any ICMFormula object.
	 * this method if find a ICMFormula as parameters, it calls the method getResult, always returns an Object(Numeric, Date, String).
	 * ready to be used in the ICMFormula where it where call.
	 * 
	 * @return always return an Object (Numeric, Date, String).
	 * 15/11/2006
	 * svonborries
	 * @throws Exception 
	 */
	public Object getCalculatedParameter(CMFormulaParameter formulaParameter) throws Exception{
		for(Object parameter: parameterList){
			if(((ICMParameter)parameter).getTypeParameter() == formulaParameter){
				return ((ICMParameter)parameter).getValue();
			}
		}
		return null;
	}
	
	/**
	 * 
	 * 28/09/2006
	 * svonborries
	 * Method used for the calculate of the formula
	 * first than all is necesary to set the respective parameters
	 * in case the formula don't has any parameter, this step is jumped
	 * @throws Exception 
	 */
	public abstract Object calculate() throws Exception;
	
	public Object getValue() throws Exception {
		return calculate();
	}
	
	public Object clone(){
		ICMFormula b = null;
		try {
			b= (ICMFormula) super.clone();
			List<Object> paramAlternative = new ArrayList<Object>();
			for(Object param: getParameterList()){
				paramAlternative.add(((CMDefaultParameter)param).clone());
			}
			if(((CMAbstractFormula)b).getParameterList() != null){
				((CMAbstractFormula)b).setParameterList(paramAlternative);
			}
			
		} catch (CloneNotSupportedException e) {
		}
		return b;
		
	}

	/**
	 * @return the parameterList
	 * 06/03/2007
	 * svonborries
	 */
	private List<Object> getParameterList() {
		return parameterList;
	}

	/**
	 * @param parameterList the parameterList to set
	 * 06/03/2007
	 * svonborries
	 */
	private void setParameterList(List<Object> parameterList) {
		this.parameterList = parameterList;
	}
	
}
