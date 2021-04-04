/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package model;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Arrays;

import model.util.CMNameBean;
import model.util.CMValueBean;

import org.apache.log4j.Logger;

import bi.controller.StructureManager;

/**
 * @author smoreno
 *
 */
public class ExpectedResult implements CMNameBean, CMValueBean, Cloneable {

	private Effect parentEffect;
	private String name;
	private Object value;
	private String formatPattern;
	private transient DecimalFormat decimalFormat;
	private transient DecimalFormatSymbols testObjectSymbols;
	private transient static char[] validNumberChars = {'1','2','3','4','5','6','7','8','9','0',',','.','-',' '};
	static { Arrays.sort(validNumberChars);};
	/**
	 * @param p_key
	 * @param p_value
	 */
	public ExpectedResult() {


	}
	
	public ExpectedResult(String p_name, String p_value, Effect p_parent){
		setName(p_name);
		setValue(p_value);
		setParentEffect(p_parent);
	}
/**
 * @param p_parentEffect The parentEffect to set.
 */
public void setParentEffect(Effect p_parentEffect) {
	this.parentEffect = p_parentEffect;
}
/**
 * @return Returns the parentEffect.
 */
public Effect getParentEffect() {
	return this.parentEffect;
}
	/* (non-Javadoc)
	 * @see model.util.CMNameBean#getName()
	 */
	public String getName() {
		if (this.name == null)
			name = ""; //$NON-NLS-1$
		return name;
	}

	/* (non-Javadoc)
	 * @see model.util.CMNameBean#setName(java.lang.String)
	 */
	public void setName(String p_name) {
		this.name = p_name;

	}

	/* (non-Javadoc)
	 * @see model.util.CMValueBean#getValue()
	 */
	public String getValue() {
		if (value==null)
			value = ""; //$NON-NLS-1$
		if (value instanceof Number)
		{
		    //Logger.getLogger(this.getClass()).debug(getDecimalFormat().format(getNumberValue())+" desde "+value);
			return getDecimalFormat().format((Number)value);
		}
		else
			return (String)value;
	}

	/* (non-Javadoc)
	 * @see model.util.CMValueBean#setValue(java.lang.String)
	 */
	public void setValue(String p_value) {
		if (isNumber(p_value))
			try {

				//eliminate all white spaces
				String l_value = p_value.trim().replaceAll("\\s", "");
				
				char thoSep = getTestObjectSymbols().getGroupingSeparator();
				StringBuilder newValue = new StringBuilder();
				char decSep = getTestObjectSymbols().getDecimalSeparator();
				int decPos = l_value.indexOf(decSep);
				int thoCount = 0;
				int i = 0;
//				count all thousand separators and commas from the original string after the comma
				for (Character ch : l_value.toCharArray())
				{
					if (((ch.charValue() == thoSep) 
					 || (ch.charValue() == decSep) || 
					 (ch.charValue() == '-' && decPos !=-1)) &&(i>decPos))
					 {
						thoCount++;
					}
					else
						//create a new string without those extra symbols
						newValue.append(ch);
					i++;
				}
				//change the decimal format to match the ammount of digits in the string after the comma
				if (l_value.indexOf(decSep)>0)
				{
					int positions = p_value.length() - l_value.indexOf(decSep)-1;
					char[] zeros = new char[positions-thoCount];
					Arrays.fill(zeros,'0');
					setFormatPattern("#,##0."+String.copyValueOf(zeros)); //$NON-NLS-1$
				}
				else
					//use de default format
					setFormatPattern(null);
				
				//set the value to the string without extra symbols
				this.value = getDecimalFormat().parse(newValue.toString());
			} catch (ParseException e) {

				Logger.getLogger(this.getClass()).error(e);
			}
		else
			this.value = p_value;

	}

	public Number getNumberValue()
	{
		getValue();
		if (value instanceof Number)

				return (Number)value;
		else
			if (value == "") //$NON-NLS-1$
				return null;

		return null;


	}
    public Object clone() {
        Object b = null;
        try {
         b = super.clone();
        } catch(CloneNotSupportedException e) {
          e.printStackTrace();
        }
        return b;
      }
	public String getFormatPattern() {
		if (this.formatPattern == null) { //$NON-NLS-1$
			this.formatPattern = "#,##0.###########"; //$NON-NLS-1$
		}
		return this.formatPattern;
	}
	public void setFormatPattern(String p_formatlPattern) {
		this.formatPattern = p_formatlPattern;
	}
	private  boolean isNumber(String p_value){
		boolean isNumeric = false;
		String l_value = p_value.trim().replaceAll("\\s", "");
		try {
			if(l_value!= ""){ //$NON-NLS-1$
				getDecimalFormat().parse(l_value);
				isNumeric = true;
			}
			Arrays.sort(validNumberChars);
			for (int i = 0; i<l_value.length();i++)
				if (Arrays.binarySearch(validNumberChars,l_value.toString().charAt(i))<0)
				{
					isNumeric = false;
					//Logger.getLogger(this.getClass()).debug(value.toString().charAt(i)+" not a number symbol"); //$NON-NLS-1$
				}
		}
	catch (java.text.ParseException e) {
			isNumeric = false;
		}
		return isNumeric;
	}
	public DecimalFormat getDecimalFormat() {
		if (this.decimalFormat == null){
			 decimalFormat = new DecimalFormat(getFormatPattern(),getTestObjectSymbols());
			}
			decimalFormat.setDecimalFormatSymbols(getTestObjectSymbols());
			decimalFormat.applyPattern(getFormatPattern());
			return this.decimalFormat;
	}
	private DecimalFormatSymbols getTestObjectSymbols() {
		if (this.testObjectSymbols == null)
			this.testObjectSymbols = new DecimalFormatSymbols();
//		set the default separators
		try
		{
		if (getStructure()!=null)
		if (getStructure().getTestObject().isDefaultSeparator())
		{
			  testObjectSymbols.setDecimalSeparator('.');
			  testObjectSymbols.setGroupingSeparator(',');
		}else
		{
			  testObjectSymbols.setDecimalSeparator(getStructure().getTestObject().getDecimalSeparator().charAt(0));
			  testObjectSymbols.setGroupingSeparator(getStructure().getTestObject().getMilesSeparator().charAt(0));
		}} catch (Exception e)
		{
			Logger.getLogger(this.getClass()).error("Could not obtain the current decimal separators"); //$NON-NLS-1$
		}
		return this.testObjectSymbols;
	}
	/**
	 * @return
	 */
	private Structure getStructure() {
		return StructureManager.getSelectedStructure();
	}
	public void setTestObjectSymbols(DecimalFormatSymbols p_testObjectSymbols) {
		this.testObjectSymbols = p_testObjectSymbols;
	}

}
