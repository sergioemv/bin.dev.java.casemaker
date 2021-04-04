package bi.view.utils;

import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Random;
import java.util.Vector;

import org.apache.commons.math.random.RandomDataImpl;

public class CMIntervalValue {

	private String writeFormat= new String();
	private Locale locale;
	private Number number;
	private String expresion=new String();
	private Number diff;
    private static Random random;
    private static long randomSeed;
    
    static{
    	randomSeed = System.currentTimeMillis();
    	random = new Random(randomSeed);
    }
	
	public CMIntervalValue() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @return Returns the expresion.
	 */
	public String getExpresion() {
		if(expresion == null)
			expresion= new String();
		return expresion;
	}
	/**
	 * @param expresion The expresion to set.
	 */
	public void setExpresion(String expresion) {
		this.expresion = expresion;
	}
	/**
	 * @return Returns the locale.
	 */
	public Locale getLocale() {
		if(locale== null){
			locale= Locale.US;
		}
		return locale;
	}
	/**
	 * @param locale The locale to set.
	 */
	public void setLocale(Locale locale) {
		this.locale = locale;
	}
	/**
	 * @return Returns the number.
	 */
	public Number getNumber() {
		if(number==null){
			number = new Integer(0);
		}
		return number;
	}
	/**
	 * @param number The number to set.
	 */
	public void setNumber(Number number) {
		this.number = number;
	}
	/**
	 * @return Returns the writeFormat.
	 */
	public String getWriteFormat() {
		if(writeFormat==null)
			writeFormat = new String("###,##");
		return writeFormat;
	}
	/**
	 * @param writeFormat The writeFormat to set.
	 */
	public void setWriteFormat(String writeFormat) {
		this.writeFormat = writeFormat;
	}

	/**
	 * @return Returns the diff.
	 */
	public Number getDiff() {
		if(diff== null)
			diff= new Integer(0);
		return diff;
	}
	/**
	 * @param diff The diff to set.
	 */
	public void setDiff(Number diff) {
		this.diff = diff;
	}
	private String convertFloatToString(float p_value){
		NumberFormat nf = NumberFormat.getNumberInstance(locale);
        DecimalFormat myFormatter = (DecimalFormat)nf;
        myFormatter.applyPattern(writeFormat);
        String output = myFormatter.format(p_value);
        return output;
	}
	
	private String convertIntToString(int p_value){
		NumberFormat nf = NumberFormat.getNumberInstance(locale);
        DecimalFormat myFormatter = (DecimalFormat)nf;
        myFormatter.applyPattern(writeFormat);
        String output = myFormatter.format(p_value);
        return output;
	}
	public String getNextValue(){
		String result=new String();
		if (number instanceof Float) {
			float tomodify=number.floatValue();
			float toadd=diff.floatValue();
			if(tomodify < 0)
				tomodify=tomodify - toadd;
			else
				tomodify=tomodify + toadd;
			result= convertFloatToString(tomodify)+" "+expresion;
		}
		else{
			if (number instanceof Integer) {
				int tomodify=number.intValue();
				int toadd=diff.intValue();
				if(tomodify < 0)
					tomodify=tomodify - toadd;
				else
					tomodify=tomodify + toadd;
				result= convertIntToString(tomodify)+" "+expresion;
			}
			else
				if (number instanceof Long) {
					Long tomodify = (Long) number;
					Date date= Calendar.getInstance().getTime();
	                date.setTime(tomodify.longValue());
	                GregorianCalendar calendar=new GregorianCalendar();
	                calendar.setTime(date);
	                if(writeFormat.equals("HH:mm"))
	                        calendar.add(Calendar.MINUTE, diff.intValue());
	                else
	                        calendar.add(Calendar.SECOND, diff.intValue());
	                Format formatter;
	                formatter = new SimpleDateFormat(writeFormat);
	                result = formatter.format(calendar.getTime());
				}
		}
		return result.trim();
	}
	
	public String getMiddleValue(CMIntervalValue p_SecondValue){
		String result= new String();
		if (number instanceof Float) {
			Float firstValue = (Float) number;
			if (p_SecondValue.getNumber() instanceof Float) {
				Float secondValue = (Float) p_SecondValue.getNumber();
				float fValue=firstValue.floatValue();
				float sValue=secondValue.floatValue();
				float rValue= (fValue+sValue)/2;
				result = CMFormatFactory.formatNumberWithLocale(new Double(rValue), getLocale(), this.getWriteFormat())+" "+expresion;
				//result=convertFloatToString(rValue)+" "+expresion;
			}
			else if (p_SecondValue.getNumber() instanceof Integer) {
				Integer secondValue = (Integer) p_SecondValue.getNumber();
				float fValue=firstValue.floatValue();
				float sValue=secondValue.floatValue();
				float rValue=(fValue+sValue)/2;
				result = CMFormatFactory.formatNumberWithLocale(new Double(rValue), getLocale(),this.getWriteFormat())+" "+expresion;
				//result=convertFloatToString(rValue)+" "+expresion;
			}
		}
		else if (number instanceof Integer) {
			Integer firstValue = (Integer) number;
			if (p_SecondValue.getNumber() instanceof Float) {
				Float secondValue = (Float) p_SecondValue.getNumber();
				float fValue=firstValue.floatValue();
				float sValue=secondValue.floatValue();
				float rValue= (fValue+sValue)/2;
				result = CMFormatFactory.formatNumberWithLocale(new Double(rValue), getLocale(),p_SecondValue.getWriteFormat())+" "+expresion;
				//result=convertFloatToString(rValue)+" "+expresion;
			}
			else if (p_SecondValue.getNumber() instanceof Integer) {
				Integer secondValue = (Integer) p_SecondValue.getNumber();
				int fValue=firstValue.intValue();
				int sValue=secondValue.intValue();
				int rValue=(fValue+sValue)/2;
				//result = CMFormatFactory.formatNumberWithLocale(new Double(rValue), getLocale(),this.getWriteFormat())+" "+expresion;
				//result=convertIntToString(rValue)+" "+expresion;
				result = (new Integer(rValue)).toString();
			}
		}
		else if (number instanceof Long) {
			Long firstValue = (Long) number;
			Long secondValue=(Long)p_SecondValue.getNumber();
			long fValue=firstValue.longValue();
			long sValue=secondValue.longValue();
			long rValue= (fValue+sValue)/2;
            
			Date date= Calendar.getInstance().getTime();
            date.setTime(rValue);
            GregorianCalendar calendar=new GregorianCalendar();
            calendar.setTime(date);
            Format formatter;
            formatter = new SimpleDateFormat(writeFormat);
            result = formatter.format(calendar.getTime());
		}
		return result;
	}
	
	/**
	 * @param p_SecondValue
	 * @param amountsOfValues
	 * @param parserValues2
	 * @return It Returns an irrelevant Vector<String>, that contains all the random dates (in String) expresed in the range of "this" and
	 * p_SecondValue(CMIntervalValue), this returned Vector<String> can be replaced by, sending the Vector<String> parserValues2 
	 * 01/09/2006
	 * svonborries
	 */
	public Vector<String> getRandomValues(CMIntervalValue p_SecondValue, int amountsOfValues, Vector<String> parserValues2){
		String result= new String();
		Vector<String> returnedVector = new Vector<String>();
		if(number instanceof Long){
			Long firstValue = (Long) number;
			Long secondValue = (Long) p_SecondValue.getNumber();
			String algo = p_SecondValue.getNumber().toString();
			try {//terminar este bloque
				secondValue = Long.parseLong(algo);
			} catch (Exception e) {
				secondValue = new Long(Integer.parseInt(algo));
			}
			long fValue = firstValue.longValue();
			long sValue = secondValue.longValue();
			
			long cantToGenerate = (sValue-fValue)+1;
			RandomDataImpl rand1 = new RandomDataImpl();
			Vector<Long> parserValues = new Vector<Long>();
			parserValues.add(firstValue);
			parserValues.add(secondValue);
			boolean cond1 = false;
			while(cond1==false){
				long valor = rand1.nextLong(fValue,sValue);
				Vector<Long> tempRand = new Vector<Long>();
				tempRand.add(valor);
				if(Collections.disjoint(parserValues,tempRand)){
					parserValues.add(valor);
					if(parserValues.size()==amountsOfValues||parserValues.size()==cantToGenerate){
						cond1=true;
					}
				}
			}
			
			for(int i = 0; i < parserValues.size();i++){
				long rValue = parserValues.elementAt(i);
				Date date= Calendar.getInstance().getTime();
	            date.setTime(rValue);
	            GregorianCalendar calendar=new GregorianCalendar();
	            calendar.setTime(date);
	            Format formatter;
	            formatter = new SimpleDateFormat(writeFormat);
	            result = formatter.format(calendar.getTime());
	            returnedVector.add(result);
			}
		}
		else if(number instanceof Float){
			Float fValue = (Float) number;
			Float sValue = null;
			if(p_SecondValue.getNumber() instanceof Float){
				sValue = (Float)p_SecondValue.getNumber();
			}
			else if(p_SecondValue.getNumber() instanceof Integer){
				sValue = new Float(((Integer)p_SecondValue.getNumber()).floatValue());
			}
			Float cantToGenerate = (sValue-fValue)+1;
			Vector<Float> parserValues = new Vector<Float>();
			parserValues.add(fValue);
			parserValues.add(sValue);
			
			
			boolean cond1 = false;
			while(cond1==false){
				Float rValue =  randomFloat(fValue,sValue);
				Vector<Float> tempRand = new Vector<Float>();
				tempRand.add(rValue);
				if(Collections.disjoint(parserValues,tempRand)){
					parserValues.add(rValue);
					if(parserValues.size()==amountsOfValues||parserValues.size()==cantToGenerate){
						cond1=true;
					}
				}
			}
			for(int i = 0; i < parserValues.size();i++){
				Float vResult = parserValues.elementAt(i);
				double previo = vResult.doubleValue();
				//result = convertFloatToString(previo);
				if(p_SecondValue.getNumber() instanceof Float){
					result = CMFormatFactory.formatNumberWithLocale(new Double(previo), getLocale(),this.getWriteFormat())+" "+expresion;
				}
				else if(p_SecondValue.getNumber() instanceof Integer){
					result = CMFormatFactory.formatNumberWithLocale(new Double(previo), getLocale(),this.getWriteFormat())+" "+expresion;
				}
				returnedVector.add(result);
			}
		}
		else if (number instanceof Integer){
			Float fValue = new Float((Integer) number);
			Float sValue = null;
			if(p_SecondValue.getNumber() instanceof Integer){
				sValue = new Float(((Integer)p_SecondValue.getNumber()).floatValue());
				int fValue1 = fValue.intValue();
				int sValue1 = sValue.intValue();
				int cantToGenerate = (sValue1-fValue1)+1;
				Vector<Integer> parserValues = new Vector<Integer>();
				parserValues.add(new Integer(fValue1));
				parserValues.add(new Integer(sValue1));
				
				boolean cond1 = false;
				while(cond1==false){
					Integer rValue =  randomInteger(fValue1,sValue1);
					Vector<Integer> tempRand = new Vector<Integer>();
					tempRand.add(rValue);
					if(Collections.disjoint(parserValues,tempRand)){
						parserValues.add(rValue);
						if(parserValues.size()==amountsOfValues||parserValues.size()==cantToGenerate){
							cond1=true;
						}
					}
				}		
				
				for(int i = 0; i < parserValues.size();i++){
					Integer vResult = parserValues.elementAt(i);
					result = vResult.toString();
					returnedVector.add(result);
				}				
				
			}
			else if(p_SecondValue.getNumber() instanceof Float){
				sValue = (Float) p_SecondValue.getNumber();
				Float cantToGenerate = (sValue-fValue)+1;
				Vector<Float> parserValues = new Vector<Float>();
				parserValues.add(fValue);
				parserValues.add(sValue);
				
				boolean cond1 = false;
				while(cond1==false){
					Float rValue =  randomFloat(fValue,sValue);
					Vector<Float> tempRand = new Vector<Float>();
					tempRand.add(rValue);
					if(Collections.disjoint(parserValues,tempRand)){
						parserValues.add(rValue);
						if(parserValues.size()==amountsOfValues||parserValues.size()==cantToGenerate){
							cond1=true;
						}
					}
				}
				for(int i = 0; i < parserValues.size();i++){
					Float vResult = parserValues.elementAt(i);
					double previo = vResult.doubleValue();
					result = CMFormatFactory.formatNumberWithLocale(new Double(previo), p_SecondValue.getLocale(), p_SecondValue.getWriteFormat())+" "+expresion;
					returnedVector.add(result);
				}
			}			
		}
		if(returnedVector.size()!=0){
			if(parserValues2!=null){
				parserValues2.removeAllElements();
				parserValues2.addAll(returnedVector);
			}
			else{
				parserValues2 = new Vector<String>();
				parserValues2.addAll(returnedVector);
			}
		}
		return returnedVector;
	}
	
	/**
	 * @param value
	 * @param value2
	 * @return an Float number between the two parameters, value = inferior param, value2 = superior param
	 * 13/09/2006
	 * svonborries
	 */
	public static Float randomFloat(Float value, Float value2) {
		return (value2 - value) * getRandom().nextFloat() + value;
	}
	
	public static Integer randomInteger(Integer value, Integer value2) {
		RandomDataImpl random = new RandomDataImpl();
		return random.nextInt(value, value2);
	}
	/**
	 * @param p_secondValue
	 * @param p_parseValues
	 * This method generate intervals for all the datas integer, interval times, real numbers
	 * @return 
	 * 01/09/2006
	 * svonborries
	 */
	public Vector<String> getAllIntervalNumber(CMIntervalValue p_secondValue, Vector<String> p_parseValues) {
		String result= new String();
		Vector<Long> returnedVector = new Vector<Long>();
		Vector<String> formatedValues = new Vector<String>();
		
		if(number instanceof Long){
			long ONE_SECOND = 1000;
			long ONE_MINUTE = 60*ONE_SECOND;
			String simpleFormat = "HH:mm";
			String complexFormat = simpleFormat+":ss";
			Long firstValue = (Long) number;
			Long secondValue = (Long)p_secondValue.getNumber();
			long fValue = firstValue.longValue();
			long sValue = secondValue.longValue();
			returnedVector.add(firstValue);
			returnedVector.add(secondValue);
			
			if(this.getWriteFormat().equalsIgnoreCase(complexFormat)){
				for(long inferior = fValue+ONE_SECOND;inferior<sValue;inferior=inferior+ONE_SECOND){
					returnedVector.add(inferior);
				}
			}
			else if(this.getWriteFormat().equalsIgnoreCase(simpleFormat)){
				for(long inferior = fValue + ONE_MINUTE ; inferior < sValue ; inferior = inferior + ONE_MINUTE){
					returnedVector.add(inferior);
				}
			}

			
			for(int i = 0; i < returnedVector.size();i++){
				long rValue = returnedVector.elementAt(i);
				Date date= Calendar.getInstance().getTime();
	            date.setTime(rValue);
	            GregorianCalendar calendar=new GregorianCalendar();
	            calendar.setTime(date);
	            Format formatter;
	            formatter = new SimpleDateFormat(writeFormat);
	            result = formatter.format(calendar.getTime());
	            formatedValues.add(result);
			}
		}
		//for real numbers
		else if(number instanceof Float){
			Float fValue = (Float) number;
			Float sValue = null;
			Vector<Float> tempResults = new Vector<Float>();
			boolean sw = true;
			if(p_secondValue.getNumber() instanceof Float){
				sValue = (Float)p_secondValue.getNumber();
			}
			else if(p_secondValue.getNumber() instanceof Integer){
				sValue = new Float(((Integer)p_secondValue.getNumber()).floatValue());
			}
			Float rValue = fValue;
			tempResults.add(fValue);
			tempResults.add(sValue);
			while(sw){
				rValue = rValue+1;
				if(rValue<sValue){
					tempResults.add(rValue);
				}
				else{
					sw = false;
				}
			}
			for(int i = 0; i < tempResults.size();i++){
				Float vResult = tempResults.elementAt(i);
				double previo = vResult.doubleValue();
				//result = convertFloatToString(previo);
				result = CMFormatFactory.formatNumberWithLocale(new Double(previo), getLocale())+" "+expresion;
				formatedValues.add(result);
			}
			
			
			
		}
		else if(number instanceof Integer){
			Float fValue = new Float((Integer) number);
			Float sValue = null;
			Vector<Float> tempResults = new Vector<Float>();
			boolean sw = true;
			if(p_secondValue.getNumber() instanceof Integer){
				Integer fValue1 = (Integer) number;
				Integer sValue1 = (Integer)p_secondValue.getNumber();
				Vector<Integer> tempResults1 = new Vector<Integer>();
				Integer rValue1 = fValue1;
				tempResults1.add(fValue1);
				tempResults1.add(sValue1);
				while(sw){
					rValue1 = rValue1+1;
					if(rValue1<sValue1){
						tempResults1.add(rValue1);
					}
					else{
						sw = false;
					}
				}
				for(int i = 0; i < tempResults1.size();i++){
					Integer vResult1 = tempResults1.elementAt(i);
					result = vResult1.toString();
					formatedValues.add(result);
				}	
			}
			else if(p_secondValue.getNumber() instanceof Float){
				sValue = (Float) p_secondValue.getNumber();
				Float rValue = fValue;
				tempResults.add(fValue);
				tempResults.add(sValue);
				while(sw){
					rValue = rValue+1;
					if(rValue<sValue){
						tempResults.add(rValue);
					}
					else{
						sw = false;
					}
				}
				for(int i = 0; i < tempResults.size();i++){
					Float vResult = tempResults.elementAt(i);
					double previo = vResult.doubleValue();
					//result = convertFloatToString(previo);
					result = CMFormatFactory.formatNumberWithLocale(new Double(previo), getLocale())+" "+expresion;
					formatedValues.add(result);
				}
			}
		}
		
		if(formatedValues.size()!=0){
			if(p_parseValues!=null){
				p_parseValues.removeAllElements();
				p_parseValues.addAll(formatedValues);
			}
			else{
				p_parseValues = new Vector<String>();
				p_parseValues.addAll(formatedValues);
			}
		}
		
		return formatedValues;
	}
	public static Random getRandom() {
		return random;
	}
	
}
