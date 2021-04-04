/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved. 
 */
package model;
/**
 * @author ccastedo
 *
 */
public enum Type{

	binary,
	bit,
	bolean,
	character,
    dateTime, 
    decimal,
    floatPoint,
    integer,
    money,
    nchar,
    ntext,
    numeric,
    nVarChar,
    real,
    smallDateTime,
    smallInt,
    smallMoney,
    text,
    timeStamp,
    tinyInt,
    varBinary,
    varChar;
    
	public String toString() {
		switch (this) {
		case binary:
			return BusinessRules.TESTDATA_STATE_BINARY;
		case bit:
			return BusinessRules.TESTDATA_STATE_BIT;
		case bolean:
			return BusinessRules.TESTDATA_STATE_BOOLEAN;
		case character:
			return BusinessRules.TESTDATA_STATE_CHAR;
		case dateTime: 
			return BusinessRules.TESTDATA_STATE_DATETIME;
		case decimal:
			return BusinessRules.TESTDATA_STATE_DECIMAL;
		case floatPoint:
			return BusinessRules.TESTDATA_STATE_FLOAT;
		case integer:
			return BusinessRules.TESTDATA_STATE_INT;
		case money:
			return BusinessRules.TESTDATA_STATE_MONEY;
		case nchar:
			return BusinessRules.TESTDATA_STATE_NCHAR;
		case ntext:
			return BusinessRules.TESTDATA_STATE_NTEXT;
		case numeric:
			return BusinessRules.TESTDATA_STATE_NUMERIC;
		case nVarChar:
			return BusinessRules.TESTDATA_STATE_NVARCHAR;
		case real:
			return BusinessRules.TESTDATA_STATE_REAL;
		case smallDateTime:
			return BusinessRules.TESTDATA_STATE_SMALLDATETIME;
		case smallInt:
			return BusinessRules.TESTDATA_STATE_SMALLINT;
		case smallMoney:
			return BusinessRules.TESTDATA_STATE_SMALLMONEY;
		case text:
			return BusinessRules.TESTDATA_STATE_TEXT;
		case timeStamp:
			return BusinessRules.TESTDATA_STATE_TIMESTAMP;
		case tinyInt:
			return BusinessRules.TESTDATA_STATE_TINYINT;
		case varBinary:
			return BusinessRules.TESTDATA_STATE_VARBINARY;
		case varChar:
			return BusinessRules.TESTDATA_STATE_VARCHAR;		
		default:
			return "";
		}
	
	}
	
	public static Type getTypeByName(String value)
	{
		for (Type type : Type.values())
			if (type.toString().equalsIgnoreCase(value))
				return type;
		return null;
	}	
	
	public int intValue()
	{
		return this.ordinal();
	}
	
	public static String getDefaultValue(String type){
		  if( type.equals(BusinessRules.TESTDATA_STATE_BINARY )) { return BusinessRules.TESTDATA_STATE_BINARY_DEFAULT_VALUE ; }
	      else if( type.equals(BusinessRules.TESTDATA_STATE_BIT)) { return BusinessRules.TESTDATA_STATE_BIT_DEFAULT_VALUE; }
	      else if( type.equals(BusinessRules.TESTDATA_STATE_BOOLEAN)) { return BusinessRules.TESTDATA_STATE_BOOLEAN_DEFAULT_VALUE; }
	      else if( type.equals(BusinessRules.TESTDATA_STATE_CHAR)) { return BusinessRules.TESTDATA_STATE_CHAR_DEFAULT_VALUE; }
	      else if( type.equals(BusinessRules.TESTDATA_STATE_DATETIME)) { return BusinessRules.TESTDATA_STATE_DATETIME_DEFAULT_VALUE; }
	      else if( type.equals(BusinessRules.TESTDATA_STATE_DECIMAL)) { return BusinessRules.TESTDATA_STATE_DECIMAL_DEFAULT_VALUE; }
	      else if( type.equals(BusinessRules.TESTDATA_STATE_FLOAT)) { return BusinessRules.TESTDATA_STATE_FLOAT_DEFAULT_VALUE; }
	      else if( type.equals(BusinessRules.TESTDATA_STATE_INT)) { return BusinessRules.TESTDATA_STATE_INT_DEFAULT_VALUE; }
	      else if( type.equals(BusinessRules.TESTDATA_STATE_MONEY)) { return BusinessRules.TESTDATA_STATE_MONEY_DEFAULT_VALUE; }
	      else if( type.equals(BusinessRules.TESTDATA_STATE_NCHAR)) { return BusinessRules.TESTDATA_STATE_NCHAR_DEFAULT_VALUE; }
	      else if( type.equals(BusinessRules.TESTDATA_STATE_NTEXT)) { return BusinessRules.TESTDATA_STATE_NTEXT_DEFAULT_VALUE; }
	      else if( type.equals(BusinessRules.TESTDATA_STATE_NUMERIC)) { return BusinessRules.TESTDATA_STATE_NUMERIC_DEFAULT_VALUE; }
	      else if( type.equals(BusinessRules.TESTDATA_STATE_NVARCHAR)) { return BusinessRules.TESTDATA_STATE_NVARCHAR_DEFAULT_VALUE; }
	      else if( type.equals(BusinessRules.TESTDATA_STATE_REAL)) { return BusinessRules.TESTDATA_STATE_REAL_DEFAULT_VALUE; }
	      else if( type.equals(BusinessRules.TESTDATA_STATE_SMALLDATETIME)) { return BusinessRules.TESTDATA_STATE_SMALLDATETIME_DEFAULT_VALUE; }
	      else if( type.equals(BusinessRules.TESTDATA_STATE_SMALLINT)) { return BusinessRules.TESTDATA_STATE_SMALLINT_DEFAULT_VALUE; }
	      else if( type.equals(BusinessRules.TESTDATA_STATE_SMALLMONEY)) { return BusinessRules.TESTDATA_STATE_SMALLMONEY_DEFAULT_VALUE; }
	      else if( type.equals(BusinessRules.TESTDATA_STATE_TEXT)) { return BusinessRules.TESTDATA_STATE_TEXT_DEFAULT_VALUE; }
	      else if( type.equals(BusinessRules.TESTDATA_STATE_TIMESTAMP)) { return BusinessRules.TESTDATA_STATE_TIMESTAMP_DEFAULT_VALUE; }
	      else if( type.equals(BusinessRules.TESTDATA_STATE_TINYINT)) { return BusinessRules.TESTDATA_STATE_TINYINT_DEFAULT_VALUE; }
		  else if( type.equals(BusinessRules.TESTDATA_STATE_VARBINARY)) { return BusinessRules.TESTDATA_STATE_VARBINARY_DEFAULT_VALUE; }
	      else if( type.equals(BusinessRules.TESTDATA_STATE_VARCHAR)) { return BusinessRules.TESTDATA_STATE_VARCHAR_DEFAULT_VALUE; }
	      else { return ""; }
	}
	
}
