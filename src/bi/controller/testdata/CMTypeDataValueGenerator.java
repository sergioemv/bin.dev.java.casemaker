package bi.controller.testdata;

import java.io.StringReader;
import java.util.Vector;
import model.BusinessRules;
import model.TestObject;
import bi.controller.tdparser.american.AmericanParser;
import bi.controller.tdparser.americantiny.AmericanTinyParser;
import bi.controller.tdparser.european.EuropeanParser;
import bi.controller.tdparser.europeantiny.EuropeanParserTiny;
import bi.controller.tdparser.intervaltimeparser.IntervalTimeParser;
import bi.controller.tdparser.intervaltimeparser.SetValuesParser;
import bi.controller.utils.CMCharUtils;
import bi.controller.utils.CMNumberUtils;
import bi.view.utils.CMFormatFactory;
import bi.view.utils.CMIntervalValue;

public class CMTypeDataValueGenerator {


	
	/**
	 * @param parserValues
	 * @param amountsOfValues
	 * @param intervalValues 
	 */
	/*@SuppressWarnings("unchecked")
	private static void calculateRandomIntervals(Vector parserValues, int amountsOfValues, Vector intervalValues) {

		String inferiorSTR1 = ((CMIntervalValue)intervalValues.elementAt(0)).getNumber().toString();
		String superiorSTR1 = ((CMIntervalValue)intervalValues.elementAt(1)).getNumber().toString();
		parserValues.removeAllElements();
		parserValues.add(inferiorSTR1);
		parserValues.add(superiorSTR1);
		int superior1 = Integer.parseInt(superiorSTR1);
		int inferior1 = Integer.parseInt(inferiorSTR1);
		int cantToGenerate = (superior1-inferior1)+1;
		Random rand1 = new Random();
		boolean cond1 = false;
		while(cond1==false){
			int valor = rand1.nextInt(superior1-inferior1)+inferior1;
			Vector tempRand = new Vector();
			tempRand.add(""+valor);
			if(Collections.disjoint(parserValues,tempRand)){
				parserValues.add(""+valor);
				if(parserValues.size()==amountsOfValues||parserValues.size()==cantToGenerate){
					cond1=true;
				}
			}
		}
	}*/
	
	@SuppressWarnings("unchecked")
	private static void calculateMoreThan2IntervalsValue(Vector intervalValues,Vector parserValues, int amountsOfValues) {
    	if((parserValues.size() !=1) && (amountsOfValues > 2) ){
    		CMIntervalValue firstValue= (CMIntervalValue) intervalValues.firstElement();
    		CMIntervalValue secondValue= (CMIntervalValue) intervalValues.elementAt(1);
    		switch (amountsOfValues) {
			case 3:
				String value=firstValue.getMiddleValue(secondValue);
				if(!value.trim().equals(""))
					//value = CMFormatFactory.formatNumberWithLocale(new Double(value), firstValue.getLocale());
					parserValues.addElement(value);
				break;
    		default: {
    			firstValue.getRandomValues(secondValue,amountsOfValues,parserValues);
/*    			if(firstValue.getNumber() instanceof Integer && secondValue.getNumber() instanceof Integer){
    				//if the numbers are integers
    				calculateRandomIntervals(parserValues,amountsOfValues,intervalValues);
    			}
    			else{
    				firstValue.getRandomValues(secondValue,amountsOfValues,parserValues);
    			}*/
    		}
			}
    	}

	}
	
	
    /**
	 * @param dataCombination2 
     * @param parseValues
	 * @param amountsOfValues
	 * @return
	 */
	/*@SuppressWarnings("unchecked")
	private static void calculeteAllIntervals(Vector parseValues, int amountsOfValues) {
		if(amountsOfValues == 0){
			String inferiorSTR = (String) parseValues.elementAt(0);
			String superiorSTR = (String)parseValues.elementAt(1);
			int superior = Integer.parseInt(superiorSTR);
			for(int inferior = Integer.parseInt(inferiorSTR)+1; inferior < superior; inferior++){
				String value = String.valueOf(inferior);
				parseValues.add(value);
			}
		}
	}*/
	
    @SuppressWarnings("unchecked")
	public static Vector generateTypeDataValue(String values, TestObject p_TestObject, int p_AmountsOfValues, Vector dataCombination) {
    	//Vector dataCombination = new Vector();
        values = CMCharUtils.trimComa(values);
        String value = CMCharUtils.firstElement(values);
        values = CMCharUtils.killFirstElement(values);
        try {
            String decimalSep = p_TestObject.getDecimalSeparator();
            String toEvaluateValue= value.trim();
            StringReader reader = new StringReader(toEvaluateValue);

            if (decimalSep.equals(BusinessRules.DEFAULT_DECIMAL_SEPARATOR)) {
                AmericanParser parse = new AmericanParser(reader);
                parse.start();
                Vector parseValues = parse.getIntervalValues();
                Vector toExtraValues= parse.getIntervalsToExtraValue();
                if(p_AmountsOfValues == 1){
                	CMIntervalValue firstValue= (CMIntervalValue) toExtraValues.firstElement();
                	if(toExtraValues.size() == 1){
                		String oneValue;
                		if(firstValue.getNumber() instanceof Integer){
                			oneValue = firstValue.getNumber().toString();
                		}
                		else{
                			oneValue = CMFormatFactory.formatNumberWithLocale(new Double(firstValue.getNumber().toString()), firstValue.getLocale(),firstValue.getWriteFormat());
                		}
                		dataCombination.insertElementAt(oneValue+",numeric", 0);
                		return dataCombination;
                	}
            		CMIntervalValue secondValue= (CMIntervalValue) toExtraValues.elementAt(1);
    				String value1=firstValue.getMiddleValue(secondValue);
    				//value1 = CMFormatFactory.formatNumberWithLocale(new Double(value1), firstValue.getLocale());
    				if(!value.trim().equals("")){
    					value1+=",numeric";
    					dataCombination.insertElementAt(value1,0);}
    				return dataCombination;
                }//svonborries_28062006_end generation of one testdata with intervals
                else if(p_AmountsOfValues == 0){
                	//calculeteAllIntervals(parseValues,p_AmountsOfValues);
                	CMIntervalValue firstValue= (CMIntervalValue) toExtraValues.firstElement();
                	if(toExtraValues.size() == 1){
                		String oneValue;
                		if(firstValue.getNumber() instanceof Integer){
                			oneValue = firstValue.getNumber().toString();
                		}
                		else{
                			oneValue = CMFormatFactory.formatNumberWithLocale(new Double(firstValue.getNumber().toString()), firstValue.getLocale(),firstValue.getWriteFormat());
                		}
                		dataCombination.insertElementAt(oneValue+",numeric", 0);
                		return dataCombination;
                	}
                	CMIntervalValue secondValue= (CMIntervalValue) toExtraValues.elementAt(1);
    				firstValue.getAllIntervalNumber(secondValue, parseValues);
                    for (int i = parseValues.size()-1;i>=0; i--) {
                        String result = parseValues.elementAt(i).toString() + ",numeric";
                        dataCombination.insertElementAt(result, 0);
                    }
                	return dataCombination;
                }
                calculateMoreThan2IntervalsValue(toExtraValues,parseValues,p_AmountsOfValues);
                //for (int i = 0; i < parseValues.size(); i++) {
                for (int i = parseValues.size()-1;i>=0; i--) {
                    String result = parseValues.elementAt(i).toString() + ",numeric";
                    dataCombination.insertElementAt(result, 0);
                }
            }
            else {
                EuropeanParser parse = new EuropeanParser(reader);
                parse.start();
                Vector parseValues = parse.getIntervalValues();
                Vector toExtraValues= parse.getIntervalsToExtraValue();
                if(p_AmountsOfValues == 1){
                	CMIntervalValue firstValue= (CMIntervalValue) toExtraValues.firstElement();
                	if(toExtraValues.size() == 1){
                		String oneValue = CMFormatFactory.formatNumberWithLocale(new Double(firstValue.getNumber().toString()), firstValue.getLocale(),firstValue.getWriteFormat());
                		dataCombination.insertElementAt(oneValue+",numeric", 0);
                		return dataCombination;
                	}
            		CMIntervalValue secondValue= (CMIntervalValue) toExtraValues.elementAt(1);
    				String value1=firstValue.getMiddleValue(secondValue);
    				//value1 = CMFormatFactory.formatNumberWithLocale(new Double(value1), firstValue.getLocale());
    				if(!value.trim().equals("")){
    					value1+=",numeric";
    					dataCombination.insertElementAt(value1,0);}
    				return dataCombination;
                }//svonborries_28062006_end generation of one testdata with intervals
                else if(p_AmountsOfValues == 0){
                	//calculeteAllIntervals(parseValues,p_AmountsOfValues);
                	CMIntervalValue firstValue= (CMIntervalValue) toExtraValues.firstElement();
                	if(toExtraValues.size() == 1){
                		String oneValue = CMFormatFactory.formatNumberWithLocale(new Double(firstValue.getNumber().toString()), firstValue.getLocale(),firstValue.getWriteFormat());
                		dataCombination.insertElementAt(oneValue+",numeric", 0);
                		return dataCombination;
                	}
                	CMIntervalValue secondValue= (CMIntervalValue) toExtraValues.elementAt(1);
    				firstValue.getAllIntervalNumber(secondValue, parseValues);
                    for (int i = parseValues.size()-1;i>=0; i--) {
                        String result = parseValues.elementAt(i).toString() + ",numeric";
                        dataCombination.insertElementAt(result, 0);
                    }
                	return dataCombination;
                }
                calculateMoreThan2IntervalsValue(toExtraValues,parseValues,p_AmountsOfValues);
                //for (int i = 0; i < parseValues.size(); i++) {
                for (int i = parseValues.size()-1;i>=0; i--) {
                    String result = parseValues.elementAt(i).toString() + ",numeric";
                    dataCombination.insertElementAt(result, 0);
                }
            }

            return dataCombination;
        }
        catch (Throwable t2/*Exception ex*/) {

            try {
                String decimalSep = p_TestObject.getDecimalSeparator();
                String toEvaluateValue= value.trim();
                StringReader reader = new StringReader(toEvaluateValue);

                if (decimalSep.equals(BusinessRules.DEFAULT_DECIMAL_SEPARATOR)) {
                    AmericanTinyParser parse = new AmericanTinyParser(reader);
                    parse.start();
                    Vector parseValues = parse.getIntervalValues();
                    Vector toExtraValues= parse.getIntervalsToExtraValue();
                    //svonborries_28062006_begin generation of one testdata with intervals
                    if(p_AmountsOfValues == 1){
                    	CMIntervalValue firstValue= (CMIntervalValue) toExtraValues.firstElement();
                    	if(toExtraValues.size() == 1){
                    		String oneValue = CMFormatFactory.formatNumberWithLocale(new Double(firstValue.getNumber().toString()), firstValue.getLocale(),firstValue.getWriteFormat());
                    		dataCombination.insertElementAt(oneValue+",numeric", 0);
                    		return dataCombination;
                    	}
                		CMIntervalValue secondValue= (CMIntervalValue) toExtraValues.elementAt(1);
        				String value1=firstValue.getMiddleValue(secondValue);
        				//value1 = CMFormatFactory.formatNumberWithLocale(new Double(value1), firstValue.getLocale());
        				if(!value.trim().equals("")){
        					value1+=",numeric";
        					dataCombination.insertElementAt(value1,0);}
        				return dataCombination;
                    }//svonborries_28062006_end generation of one testdata with intervals
                    else if(p_AmountsOfValues == 0){
                    	//calculeteAllIntervals(parseValues,p_AmountsOfValues);
                    	CMIntervalValue firstValue= (CMIntervalValue) toExtraValues.firstElement();
                    	if(toExtraValues.size() == 1){
                    		String oneValue = CMFormatFactory.formatNumberWithLocale(new Double(firstValue.getNumber().toString()), firstValue.getLocale(),firstValue.getWriteFormat());
                    		dataCombination.insertElementAt(oneValue+",numeric", 0);
                    		return dataCombination;
                    	}
                    	CMIntervalValue secondValue= (CMIntervalValue) toExtraValues.elementAt(1);
        				firstValue.getAllIntervalNumber(secondValue, parseValues);
                        for (int i = parseValues.size()-1;i>=0; i--) {
                            String result = parseValues.elementAt(i).toString() + ",numeric";
                            dataCombination.insertElementAt(result, 0);
                        }
                    	return dataCombination;
                    }
                    calculateMoreThan2IntervalsValue(toExtraValues,parseValues,p_AmountsOfValues);
                    //for (int i = 0; i < parseValues.size(); i++) {
                    for (int i = parseValues.size()-1;i>=0; i--) {
                        String result = parseValues.elementAt(i).toString() + ",numeric";
                        dataCombination.insertElementAt(result, 0);
                    }
                }
                else {
                    EuropeanParserTiny parse = new EuropeanParserTiny(reader);
                    parse.start();
                    Vector parseValues = parse.getIntervalValues();
                    Vector toExtraValues= parse.getIntervalsToExtraValue();
                    if(p_AmountsOfValues == 1){
                    	CMIntervalValue firstValue= (CMIntervalValue) toExtraValues.firstElement();
                    	if(toExtraValues.size() == 1){
                    		String oneValue = CMFormatFactory.formatNumberWithLocale(new Double(firstValue.getNumber().toString()), firstValue.getLocale(),firstValue.getWriteFormat());
                    		dataCombination.insertElementAt(oneValue+",numeric", 0);
                    		return dataCombination;
                    	}
                		CMIntervalValue secondValue= (CMIntervalValue) toExtraValues.elementAt(1);
        				String value1=firstValue.getMiddleValue(secondValue);
        				//value1 = CMFormatFactory.formatNumberWithLocale(new Double(value1), firstValue.getLocale());
        				if(!value.trim().equals("")){
        					value1+=",numeric";
        					dataCombination.insertElementAt(value1,0);}
        				return dataCombination;
                    }//svonborries_28062006_end generation of one testdata with intervals
                    else if(p_AmountsOfValues == 0){
                    	//calculeteAllIntervals(parseValues,p_AmountsOfValues);
                    	CMIntervalValue firstValue= (CMIntervalValue) toExtraValues.firstElement();
                    	if(toExtraValues.size() == 1){
                    		String oneValue = CMFormatFactory.formatNumberWithLocale(new Double(firstValue.getNumber().toString()), firstValue.getLocale(),firstValue.getWriteFormat());
                    		dataCombination.insertElementAt(oneValue+",numeric", 0);
                    		return dataCombination;
                    	}
                    	CMIntervalValue secondValue= (CMIntervalValue) toExtraValues.elementAt(1);
        				firstValue.getAllIntervalNumber(secondValue, parseValues);
                        for (int i = parseValues.size()-1;i>=0; i--) {
                            String result = parseValues.elementAt(i).toString() + ",numeric";
                            dataCombination.insertElementAt(result, 0);
                        }
                    	return dataCombination;
                    }
                    calculateMoreThan2IntervalsValue(toExtraValues,parseValues,p_AmountsOfValues);
                    //for (int i = 0; i < parseValues.size(); i++) {
                    for (int i = parseValues.size()-1;i>=0; i--) {
                        String result = parseValues.elementAt(i).toString() + ",numeric";
                        dataCombination.insertElementAt(result, 0);
                    }
                }
                return dataCombination;

            }
            catch (Throwable t1/*Exception ext*/) {
            	try{
               		String toEvaluateValue= value.trim();
            		StringReader reader = new StringReader(toEvaluateValue);

            		 SetValuesParser parser= new SetValuesParser(reader);
            		 parser.start();
                     Vector parseValues = parser.getIntervalValues();
                     //for (int i = 0; i < parseValues.size(); i++) {
                     for (int i = parseValues.size()-1;i>=0; i--) {
                         String result = parseValues.elementAt(i).toString() + ","+BusinessRules.TESTDATA_STATE_NVARCHAR;
                         dataCombination.insertElementAt(result, 0);
                     }

                     return dataCombination;
/*            		String toEvaluateValue= value.trim();
            		StringReader reader = new StringReader(toEvaluateValue);

            		 IntervalTimeParser parser= new IntervalTimeParser(reader);
            		 parser.start();
                     Vector parseValues = parser.getIntervalValues();
                     Vector toExtraValues= parser.getIntervalToExtraValue();
                     if(p_AmountsOfValues == 1){
                    	 String value1;
                    	 if(parseValues.size()==1||toExtraValues.size()==1){
                    		 value1 = parseValues.elementAt(0).toString();
                    	 }
                    	 else{
                      		CMIntervalValue firstValue= (CMIntervalValue) toExtraValues.firstElement();
                     		CMIntervalValue secondValue= (CMIntervalValue) toExtraValues.elementAt(1);
             				value1=firstValue.getMiddleValue(secondValue);
                    	 }
         				if(!value.trim().equals("")){
         					value1=value1+","+BusinessRules.TESTDATA_STATE_SMALLDATETIME;
         					dataCombination.insertElementAt(value1,0);}
         				return dataCombination;
                     }//svonborries_28062006_end generation of one testdata with intervals
                     else if(p_AmountsOfValues == 0){
                    	 String value1 = new String();
                    	 if(parseValues.size()==1||toExtraValues.size()==1){
                    		 value1 = (String) parseValues.elementAt(0);
                    		 value1 = value1+","+BusinessRules.TESTDATA_STATE_SMALLDATETIME;
                    		 dataCombination.insertElementAt(value1,0);
          					 return dataCombination;
                    	 }
                    	 else{
                    		 CMIntervalValue firstValue= (CMIntervalValue) toExtraValues.firstElement();
                    		 if(firstValue.getNumber() instanceof Long){
                    			 //hacer el codigo para generar all en intervalos de tiempo enviar el parsevalue y devolverlo para que
                    			 //el for de abajo se encarge de hacer su trabajo(linea 1511)
                          		 CMIntervalValue secondValue= (CMIntervalValue) toExtraValues.elementAt(1);
                          		 firstValue.getAllIntervalNumber(secondValue,parseValues);
                    		 }
                    		 else{
                    			 calculeteAllIntervals(parseValues,p_AmountsOfValues);
                    		 }
                    	 }
                     	
                         for (int i = parseValues.size()-1;i>=0; i--) {
                             String result = parseValues.elementAt(i).toString() + ","+BusinessRules.TESTDATA_STATE_SMALLDATETIME;
                             dataCombination.insertElementAt(result, 0);
                         }
                     	return dataCombination;
                     }
                     calculateMoreThan2IntervalsValue(toExtraValues,parseValues,p_AmountsOfValues);
                    // for (int i = 0; i < parseValues.size(); i++) {
                     for (int i = parseValues.size()-1;i>=0; i--) {
                         String result = parseValues.elementAt(i).toString() + ","+BusinessRules.TESTDATA_STATE_SMALLDATETIME;
                         dataCombination.insertElementAt(result, 0);
                     }
                     return dataCombination;*/
            	}
            	catch(Throwable t) {
            		try{
/*                		String toEvaluateValue= value.trim();
                		StringReader reader = new StringReader(toEvaluateValue);

                		 SetValuesParser parser= new SetValuesParser(reader);
                		 parser.start();
                         Vector parseValues = parser.getIntervalValues();
                         //for (int i = 0; i < parseValues.size(); i++) {
                         for (int i = parseValues.size()-1;i>=0; i--) {
                             String result = parseValues.elementAt(i).toString() + ","+BusinessRules.TESTDATA_STATE_NVARCHAR;
                             dataCombination.insertElementAt(result, 0);
                         }

                         return dataCombination;*/
            			
            			String toEvaluateValue= value.trim();
                		StringReader reader = new StringReader(toEvaluateValue);

                		 IntervalTimeParser parser= new IntervalTimeParser(reader);
                		 parser.start();
                         Vector parseValues = parser.getIntervalValues();
                         Vector toExtraValues= parser.getIntervalToExtraValue();
                         if(p_AmountsOfValues == 1){
                        	 String value1;
                        	 if(parseValues.size()==1||toExtraValues.size()==1){
                        		 value1 = parseValues.elementAt(0).toString();
                        	 }
                        	 else{
                          		CMIntervalValue firstValue= (CMIntervalValue) toExtraValues.firstElement();
                         		CMIntervalValue secondValue= (CMIntervalValue) toExtraValues.elementAt(1);
                 				value1=firstValue.getMiddleValue(secondValue);
                        	 }
             				if(!value.trim().equals("")){
             					value1=value1+","+BusinessRules.TESTDATA_STATE_SMALLDATETIME;
             					dataCombination.insertElementAt(value1,0);}
             				return dataCombination;
                         }//svonborries_28062006_end generation of one testdata with intervals
                         else if(p_AmountsOfValues == 0){
                        	 String value1 = new String();
                        	 if(parseValues.size()==1||toExtraValues.size()==1){
                        		 value1 = (String) parseValues.elementAt(0);
                        		 value1 = value1+","+BusinessRules.TESTDATA_STATE_SMALLDATETIME;
                        		 dataCombination.insertElementAt(value1,0);
              					 return dataCombination;
                        	 }
                        	 else{
                        		 CMIntervalValue firstValue= (CMIntervalValue) toExtraValues.firstElement();
                        		 if(firstValue.getNumber() instanceof Long){
                              		 CMIntervalValue secondValue= (CMIntervalValue) toExtraValues.elementAt(1);
                              		 firstValue.getAllIntervalNumber(secondValue,parseValues);
                        		 }
                        	 }
                         	
                             for (int i = parseValues.size()-1;i>=0; i--) {
                                 String result = parseValues.elementAt(i).toString() + ","+BusinessRules.TESTDATA_STATE_SMALLDATETIME;
                                 dataCombination.insertElementAt(result, 0);
                             }
                         	return dataCombination;
                         }
                         calculateMoreThan2IntervalsValue(toExtraValues,parseValues,p_AmountsOfValues);
                        // for (int i = 0; i < parseValues.size(); i++) {
                         for (int i = parseValues.size()-1;i>=0; i--) {
                             String result = parseValues.elementAt(i).toString() + ","+BusinessRules.TESTDATA_STATE_SMALLDATETIME;
                             dataCombination.insertElementAt(result, 0);
                         }
                         return dataCombination;
                	}
                	catch(Throwable t5) {

                		if (value.startsWith("=")) {
                			try {
                				//svonborries 15082008
                				//Fixed to give the new funtionality to TestData, if the value starts with an '=' the rest of the 
                				//text must be a String.
                				if (value.length()>1){
                					value = value.substring(value.indexOf("=") + 1);
                					String DataValues = value + ","+BusinessRules.TESTDATA_STATE_VARCHAR;
                            		dataCombination.insertElementAt(DataValues, 0);
                            		return dataCombination;
                				}
                				
                			}
                			catch (Exception ext1) {
                				value = "=";
                			}
                		}
                		if(CMNumberUtils.isValidNumber(value)){
                			String DataValues = value + ","+BusinessRules.TESTDATA_STATE_NUMERIC;
                			dataCombination.insertElementAt(DataValues, 0);
                			return dataCombination;
                		}
                		String DataValues = value + ","+BusinessRules.TESTDATA_STATE_VARCHAR;
                		dataCombination.insertElementAt(DataValues, 0);
                		return dataCombination;
                	}
            	}
            	/*catch(Exception exp) {
            		if (value.startsWith("=")) {
            			try {
            				value = value.substring(value.indexOf("=") + 1);
            			}
            			catch (Exception ext1) {
            				value = "=";
            			}
            		}
            		if(isValidNumber(value)){
            			String DataValues = value + ","+BusinessRules.TESTDATA_STATE_NUMERIC;
            			dataCombination.insertElementAt(DataValues, 0);
            			return dataCombination;
            		}
            		String DataValues = value + ","+BusinessRules.TESTDATA_STATE_VARCHAR;
            		dataCombination.insertElementAt(DataValues, 0);
            		return dataCombination;
            	}*/
            }

        }
    }
	
	
}
