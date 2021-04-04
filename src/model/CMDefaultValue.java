/**
 * 30/11/2006
 * svonborries
 */
package model;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import bi.controller.StructureManager;
import bi.controller.utils.CMNumberUtils;
import bi.view.utils.CMFormatFactory;

/**
 * @author svonborries
 *
 */
public class CMDefaultValue implements ICMValue, Cloneable {
	
	private Object value = null;
	
	
	public CMDefaultValue(Object p_value){
		if(p_value instanceof Number){
			setValue((Number)p_value);
			return;
		}
		else if(p_value instanceof Date){
			setValue((Date)p_value);
			return;
		}
		else if(p_value instanceof String){
			setValue((String)p_value);
			return;
		}
		setValue(p_value);
	}
	

	public CMDefaultValue(String p_value){
		validateValue(p_value);
	}

	/* (non-Javadoc)
	 * @see model.ICMValue#getValue()
	 * @return
	 * @throws Exception
	 * 30/11/2006
	 * svonborries
	 */
	public Object getValue() throws Exception {
		return value;
	}
	
	private void validateValue(String p_value){
		if(!validateNumber(p_value))
			if(!validateDate(p_value))
				setValue(new String(p_value));
	}
	
	private boolean validateDate(String p_value){
//		try {
//			   DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
//			   df.setLenient(false);  // this is important!
//			   Date dt2;
//			   dt2 = df.parse(p_value);
//			   setValue(dt2);
//			   return true;
//		} catch (ParseException e) {
//			return false;
//		}
		
		try {
		     SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		     sdf.setLenient(false);
			 Date dt2 = sdf.parse(p_value);
			 setValue(dt2);
			 return true;
			 
		} catch (ParseException e) {
			 
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			    sdf.setLenient(false);
				Date dt2;
				dt2 = sdf.parse(p_value);
				setValue(dt2);
				return true;
			} catch (ParseException e1) {
				try {
					SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
				    sdf.setLenient(false);
					Date dt2;
					dt2 = sdf.parse(p_value);
					setValue(dt2);
					return true;
				} catch (Exception e2) {
					return false;
				}
			}
			 
			
		}
		   
	}
	
	private boolean validateNumber(String p_value){

    	try {
    		if(p_value.endsWith(",") || p_value.endsWith(".")){
		    	//setValue(p_value);
		    	return false;
			}
    		if(p_value.endsWith("0") && (p_value.indexOf(".") != -1) && (p_value.indexOf(",") != -1))
    			return false;
    		
    		if(!CMNumberUtils.isValidNumber(p_value)){
    			return false;
    		}
    		
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
			Number value = formatter.parse(p_value);
			setValue(value);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}

	/**
	 * @param value the value to set
	 * 30/11/2006
	 * svonborries
	 */
	public void setValue(Object value) {
		this.value = value;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 * @return
	 * 30/11/2006
	 * svonborries
	 */
	@Override
	public String toString() {
		return "";
//		try {
//			return getValue().toString();
//		} catch (Exception e) {
//			e.printStackTrace();
//			return "";
//			
//		}
	}
	
	public String getValueString(){
		try {
			if(getValue() instanceof Number){
				DecimalFormatSymbols symbols = new DecimalFormatSymbols();
				if(StructureManager.getSelectedStructure().getTestObject().isDefaultSeparator()){
            		symbols.setDecimalSeparator('.');
            		symbols.setGroupingSeparator(',');
            	}
            	else{
            		symbols.setDecimalSeparator(StructureManager.getSelectedStructure().getTestObject().getDecimalSeparator().charAt(0));
            		symbols.setGroupingSeparator(StructureManager.getSelectedStructure().getTestObject().getMilesSeparator().charAt(0));
            	}
            	NumberFormat formatter = new DecimalFormat(BusinessRules.FORMULAS_FORMAT_DEFAULT_MATH_TRIGONOMETRY,symbols);
            	String formatedNumber = formatter.format((Number) getValue());
            	return formatedNumber;
				//return CMFormatFactory.formatNumber(BusinessRules.FORMULAS_FORMAT_DEFAULT_MATH_TRIGONOMETRY, 
						//(Number) getValue());
			}
			if(getValue() instanceof Date){
				return CMFormatFactory.formatDate("yyyy/MM/dd", (Date)getValue());
			}
			return getValue().toString();
		} catch (Exception e) {
			return "";
		}
	}
	
	public String getValueString(String pattern){
		try {
			if(getValue() instanceof Number){
				return CMFormatFactory.formatNumber(pattern, 
						(Number) getValue());
			}
			if(getValue() instanceof Date){
				return CMFormatFactory.formatDate(pattern, (Date)getValue());
			}
			return getValue().toString();
		} catch (Exception e) {
			return "";
		}
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
		Object b= null;
		//CMDefaultValue clon = null;
		try {
			b = super.clone();
			if(((CMDefaultValue)b).getValue() instanceof Number){
				if(((CMDefaultValue)b).getValue() instanceof Long){
					Long value = new Long(((Number)((CMDefaultValue)b).getValue()).longValue());
					((CMDefaultValue)b).setValue(value);
				}
				else if(((CMDefaultValue)b).getValue() instanceof Double){
					Double value = new Double(((Number)((CMDefaultValue)b).getValue()).doubleValue());
					((CMDefaultValue)b).setValue(value);
				}
				else if(((CMDefaultValue)b).getValue() instanceof Float){
					Float value = new Float(((Number)((CMDefaultValue)b).getValue()).floatValue());
					((CMDefaultValue)b).setValue(value);
				}
				else if(((CMDefaultValue)b).getValue() instanceof Integer){
					Integer value = new Integer(((Number)((CMDefaultValue)b).getValue()).intValue());
					((CMDefaultValue)b).setValue(value);
				}
			}
			else if(((CMDefaultValue)b).getValue() instanceof Date){
				Date value = new Date(((Date)((CMDefaultValue)b).getValue()).getTime());
				((CMDefaultValue)b).setValue(value);
			}
			else if(((CMDefaultValue)b).getValue() instanceof String){
				((CMDefaultValue)b).setValue(new String(((String)((CMDefaultValue)b).getValue())));
			}
		} catch (Exception e) {
		}
		//return clon;
		return b;
	}

}
