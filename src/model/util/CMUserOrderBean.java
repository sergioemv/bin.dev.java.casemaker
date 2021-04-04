/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package model.util;

import java.util.Comparator;

/**
 * @author smoreno
 *
 */
public interface CMUserOrderBean {

	public static final Comparator COMPARATOR = new CMUserOrderComparator();
	public int getUserOrder();
	public void setUserOrder(int order);
}
