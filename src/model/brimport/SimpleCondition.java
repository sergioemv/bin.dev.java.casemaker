package model.brimport;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SimpleCondition extends Condition {
	
	//portiz_15102007_begin
	public Boolean bIsCompareTo=false;
	//portiz_15102007_end
	//portiz_18102007_begin
	public Boolean bIsManagecondition=false;
	//portiz_18102007_end
	

	
	private String identifier = "";
	private BRKeyword operator = BRKeyword.EQUAL;
	private String value = "";
	private List<String> arrayValues;
	public SimpleCondition(String string, String string2) {
		identifier = string;
		value = string2;
	}
	public SimpleCondition() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		if (validIdentifier(getIdentifier()))
			builder.append(getIdentifier());
		else{
			//portiz_15102007_begin (quito los NATIVE)
			//builder.append(BRKeyword.NATIVE.get(getLanguaje()));
			//builder.append("[");
			builder.append(getIdentifier());
			//builder.append("]");
			//portiz_15102007_end
		}
		//portiz_15102007_begin
		if ( (! this.bIsCompareTo ) && (!this.bIsManagecondition) ) 
			builder.append(" "+getOperator().get(getLanguaje())+" ");
		//portiz_15102007_end
		
		if (getArrayValues().size()==0)  {
			//portiz_15102007_begin
			if (getValue()!=null){
			//portiz_15102007_end
				if ( validIdentifier(getValue())  )
					builder.append(getValue());
				else{
					//builder.append(BRKeyword.NATIVE.get(getLanguaje()));
					builder.append("\"");
					builder.append(getValue());
					builder.append("\"");
				}
			}

			
		}
		else
		{
			builder.append("(");
			for (String value : getArrayValues()){
				if (validIdentifier(value))
					builder.append(value);
				else{
					//builder.append(BRKeyword.NATIVE.get(getLanguaje()));
					builder.append("\"");
					builder.append(value);
					builder.append("\"");
				}
				builder.append(",");
			}
			builder.setCharAt(builder.length()-1,')');
		}
		return builder.toString();
	}
	
	//portiz_30102007_begin

	
	public String toString(BRLanguaje __Languaje) {
		StringBuilder builder = new StringBuilder();
		
		if (validIdentifier(getIdentifier()))
			builder.append(getIdentifier());
		else{
			//portiz_15102007_begin (quito los NATIVE)
			//builder.append(BRKeyword.NATIVE.get(getLanguaje()));
			//builder.append("[");
			builder.append(getIdentifier());
			//builder.append("]");
			//portiz_15102007_end
		}
		//portiz_15102007_begin
		if ( (! this.bIsCompareTo ) && (!this.bIsManagecondition) ) 
			builder.append(" "+getOperator().get(__Languaje)+" ");
		//portiz_15102007_end
		
		if (getArrayValues().size()==0)  {
			//portiz_15102007_begin
			if (getValue()!=null){
			//portiz_15102007_end
				if ( validIdentifier(getValue())  )
					builder.append(getValue());
				else{
					//builder.append(BRKeyword.NATIVE.get(getLanguaje()));
					builder.append("\"");
					builder.append(getValue());
					builder.append("\"");
				}
			}

			
		}
		else
		{
			builder.append("(");
			for (String value : getArrayValues()){
				if (validIdentifier(value))
					builder.append(value);
				else{
					//builder.append(BRKeyword.NATIVE.get(getLanguaje()));
					builder.append("\"");
					builder.append(value);
					builder.append("\"");
				}
				builder.append(",");
			}
			builder.setCharAt(builder.length()-1,')');
		}
		return builder.toString();
	}
	
	
	//portiz_30102007_end	
	
	public boolean validIdentifier(String identifier2) {
		boolean isValid = true;
		//todo: paulo
		if (identifier2==null)
			return true;
		
		for (Character ch : identifier2.toCharArray())
			for (Character ch2 : forbiddenChars)
				if (ch.charValue() == ch2.charValue())
					isValid = false;
		if (identifier2.startsWith("\"")  && identifier2.endsWith("\""))
					isValid = true;
		for (Character ch2 : forbiddenInitialChars)
			if (identifier2.startsWith(ch2.toString()))
					isValid = false;
		//check if is not a number
		try {
			DecimalFormat dc = new DecimalFormat();
			Number n = dc.parse(identifier2);
			if (n.toString().equalsIgnoreCase(identifier2))
					isValid = true;
			else
				    isValid = false;
		}catch (Exception e){};
		
		return isValid;
	}
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	public BRKeyword getOperator() {

		return operator;
	}
	public void setOperator(BRKeyword operator) throws Exception {
		if (!operator.isComparationOperator())
			throw new Exception("The Operator is not a valid Comparation operator");
		this.operator = operator;
	}
	public String getValue() {

		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public List<String> getArrayValues() {
		if (arrayValues == null)
			arrayValues = new ArrayList<String>();
		return arrayValues;
	}
	public Set<String> getInvalidIdentifiers() {
		Set<String> ids = new HashSet<String>();
		if (!validIdentifier(getIdentifier()))
			ids.add(getIdentifier());
		if (getArrayValues().size()==0){
			if (!validIdentifier(getValue()))
				ids.add(getValue());
		}
		else
			for (String value : getArrayValues()){
				if(!validIdentifier(value))
					ids.add(value);
			}
		return ids;
	}

}