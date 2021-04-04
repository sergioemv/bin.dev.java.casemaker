/**
 * 10/11/2006
 * svonborries
 */
package model;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;

import model.util.CMModelEventHandler;
import model.util.CMModelListener;
import bi.controller.StructureManager;
import bi.controller.testdata.formula.CMFormulaCategory;
import bi.controller.testdata.formula.CMFormulaParameter;
import bi.controller.testdata.formula.CMFormulas;

import bi.view.lang.CMMessages;

/**
 * @author svonborries
 *
 */
public class CMDefaultParameter implements ICMParameter,Cloneable {
	
	private ICMValue value = null;
	private CMFormulaParameter type = null;
	private transient CMModelEventHandler handler;

	/* (non-Javadoc)
	 * @see model.ICMParameter#getTypeParameter()
	 * @return
	 * 10/11/2006
	 * svonborries
	 */
	
	public CMDefaultParameter(CMFormulaParameter p_type, ICMValue p_value){
		type = p_type;
		value = p_value;
	}
	
	public CMDefaultParameter(CMFormulaParameter p_type){
		type = p_type;
	}
	
	public CMFormulaParameter getTypeParameter() {
		return type;
	}

	/* (non-Javadoc)
	 * @see model.ICMParameter#setTypeParameter(bi.controller.testdata.formula.CMFormulaParameter)
	 * @param formulaParameter
	 * 10/11/2006
	 * svonborries
	 */
	public void setTypeParameter(CMFormulaParameter formulaParameter) {
		type = formulaParameter;
	}

	/* (non-Javadoc)
	 * @see model.util.CMModelSource#addModelListener(model.util.CMModelListener)
	 * @param listener
	 * 10/11/2006
	 * svonborries
	 */
	public void addModelListener(CMModelListener listener) {
		getHandler().addModelListener(listener);
	}

	/* (non-Javadoc)
	 * @see model.util.CMModelSource#removeModelListener(model.util.CMModelListener)
	 * @param listener
	 * 10/11/2006
	 * svonborries
	 */
	public void removeModelListener(CMModelListener listener) {
		getHandler().removeModelListener(listener);
	}

	/* (non-Javadoc)
	 * @see model.ICMFormula#addParameter(bi.controller.testdata.formula.CMFormulaParameter, java.lang.Object)
	 * @param key
	 * @param value
	 * 10/11/2006
	 * svonborries
	 */
	public void addParameter(Object value) {
		if(getObject() instanceof ICMFormula){
			((ICMFormula)getObject()).addParameter(value);
		}
	}

	/* (non-Javadoc)
	 * @see model.ICMFormula#getCategory()
	 * @return
	 * 10/11/2006
	 * svonborries
	 */
	public CMFormulaCategory getCategory() {
		if(getObject() instanceof ICMFormula)
			return ((ICMFormula)getObject()).getCategory();
		return null;
	}

	/* (non-Javadoc)
	 * @see model.ICMFormula#getFormulaEnum()
	 * @return
	 * 10/11/2006
	 * svonborries
	 */
	public CMFormulas getFormulaEnum() {
		if(getObject() instanceof ICMFormula)
			return ((ICMFormula)getObject()).getFormulaEnum();
		return null;
	}

	/* (non-Javadoc)
	 * @see model.ICMFormula#getParameter(bi.controller.testdata.formula.CMFormulaParameter)
	 * @param parameterType
	 * @return
	 * 10/11/2006
	 * svonborries
	 */
	public Object getParameter(CMFormulaParameter parameterType) {
		if(getObject() instanceof ICMFormula)
			return ((ICMFormula)getObject()).getParameter(parameterType);
		return null;
	}

	/* (non-Javadoc)
	 * @see model.ICMValue#getValue()
	 * @return
	 * 10/11/2006
	 * svonborries
	 */
	public Object getValue() throws Exception {
//		if(value == null){
//			value = "";
//		}
		if(value instanceof ICMFormula){
			return ((ICMFormula)value).getValue();
		}
		if(value instanceof CMDefaultValue){
			return ((CMDefaultValue)value).getValue();
		}
		if(value instanceof CMLinkElement){
			return ((CMLinkElement)value).getValue();
		}
		return value;
	}

	/* (non-Javadoc)
	 * @see model.ICMFormula#getType()
	 * @return
	 * 10/11/2006
	 * svonborries
	 */
	public String getTypeFormula() {
		if(getObject() instanceof ICMFormula){
			return ((ICMFormula)getObject()).getTypeFormula();
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see model.ICMFormula#getValueResult()
	 * @return
	 * 10/11/2006
	 * svonborries
	 */
	public String getFormattedValueResult() {
		if(getObject() instanceof ICMFormula){
			return ((ICMFormula)getObject()).getFormattedValueResult();
		}
		return null;
	}

	public void setResult(ICMValue value) {
		this.value = value;
		getHandler().fireModelEventHappen(this, CMField.VALUE);
	}
	
	private CMModelEventHandler getHandler() {
		if (this.handler == null)
			this.handler = new CMModelEventHandler();
		return this.handler;
	}
	
	public String toString() {
		try {
			if(value == null)
				throw new Exception();
		if(value instanceof ICMFormula)
			return ((ICMFormula)value).toString();
		else if(value instanceof CMDefaultValue){
			Object valueTemp = null;
			
				valueTemp = ((CMDefaultValue)value).getValue();
			if(valueTemp instanceof Number){
		    	DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		    	if(StructureManager.getSelectedStructure().getTestObject().isDefaultSeparator()){
		    		symbols.setDecimalSeparator('.');
		    		symbols.setGroupingSeparator(',');
		    	}
		    	else{
		    		symbols.setDecimalSeparator(StructureManager.getSelectedStructure().getTestObject().getDecimalSeparator().charAt(0));
		    		symbols.setGroupingSeparator(StructureManager.getSelectedStructure().getTestObject().getMilesSeparator().charAt(0));
		    	}
		    	DecimalFormat formatter = new DecimalFormat(BusinessRules.FORMULAS_FORMAT_DEFAULT_MATH_TRIGONOMETRY,symbols);
		    	if(type.equals(CMFormulaParameter.YEAR))
		    		formatter = new DecimalFormat("####");
		    	return formatter.format(valueTemp);
			}
			if(valueTemp instanceof String){
				return (String) valueTemp;
			}
			if(valueTemp instanceof Date){
				SimpleDateFormat formatter;
				if(type.equals(CMFormulaParameter.HOUR)){
					formatter = new SimpleDateFormat("HH:mm:ss");
				}
				else{
					formatter = new SimpleDateFormat("yyyy/MM/dd");
				}
	        	formatter.setLenient(false);
	        	String formattedDate;
	        	formattedDate = formatter.format(valueTemp);
				return formattedDate;
			}

		}
		}
		 catch (Exception e) {
			 return CMMessages.getString("TESTDATA_MISSING_PARAMETER");
			}
		return value.toString();	
	}

	public ICMValue getObject() {
//		if(value == null){
//			value = "";
//		}
		if(value instanceof ICMFormula){
			return ((ICMFormula)value);
		}
		return value;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 * @return
	 * @throws CloneNotSupportedException
	 * 08/12/2006
	 * svonborries
	 */
	@Override
	public Object clone(){
		Object b = null;
		//CMDefaultParameter clon = null;
		try {
			b =  super.clone();
			//CMDefaultParameter aux;
			if(((CMDefaultParameter)b).getObject() instanceof ICMFormula){
				//aux = (ICMFormula) (((CMDefaultParameter)b).getObject()).clone();
				//aux = (CMDefaultParameter) ((ICMFormula)b).clone();
				//clon = new CMDefaultParameter(((CMDefaultParameter)b).getTypeParameter());
				((CMDefaultParameter)b).setResult((ICMFormula) (((CMDefaultParameter)b).getObject()).clone());
			}
			if(((CMDefaultParameter)b).getObject() instanceof CMLinkElement){
				//clon = new CMDefaultParameter(((CMDefaultParameter)b).getTypeParameter());
				((CMDefaultParameter)b).setResult((CMLinkElement) (((CMDefaultParameter)b).getObject()).clone());
			}
			if(((CMDefaultParameter)b).getObject() instanceof CMDefaultValue){
				((CMDefaultParameter)b).setResult((CMDefaultValue) (((CMDefaultParameter)b).getObject()).clone());
			}
//			else{
//				aux = (CMDefaultParameter) b;
//				clon = new CMDefaultParameter(aux.getTypeParameter());
//				clon.setResult(aux.getObject());
//			}
		} catch (CloneNotSupportedException e) {
		}
		return b;
	}

	public void fireModelEventHappen(CMField field) {
		getHandler().fireModelEventHappen(this, field);

	}

}
