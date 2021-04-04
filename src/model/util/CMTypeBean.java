/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved. 
 */
package model.util;

import model.Type;

/**
 * represents a model object that has type (with setter and getter)
 * @author ccastedo
 *
 */
public interface CMTypeBean {
	
   public static final int TYPE_BINARY = 0;
   public static final int TYPE_BIT = 1;
   public static final int TYPE_BOLEAN = 2;
   public static final int TYPE_CHARACTER = 3;
   public static final int TYPE_DATETIME = 4; 
   public static final int TYPE_DECIMAL = 5;
   public static final int TYPE_FLOATPOINT = 6; 
   public static final int TYPE_INTEGER = 7;
   public static final int TYPE_MONEY = 8;
   public static final int TYPE_NCHAR = 9;
   public static final int TYPE_NTEXT = 10;
   public static final int TYPE_NUMERIC = 11;
   public static final int TYPE_NVARCHAR = 12;
   public static final int TYPE_REAL = 13;
   public static final int TYPE_SMALLDATETIME = 14;
   public static final int TYPE_SMALLINT = 15;
   public static final int TYPE_SMALLMONEY = 16;
   public static final int TYPE_TEXT = 17;
   public static final int TYPE_TIMESTAMP = 18;
   public static final int TYPE_TINYINT = 19;
   public static final int TYPE_VARBINARY = 20;
   public static final int TYPE_VARCHAR = 21;

	
   public void setType(int p_type);
   public void setType(Type p_type);
   public String getTypeName();
   public CMTypeBean getType();
   public int getTypeIndex();
}
