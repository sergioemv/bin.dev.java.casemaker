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
public class CMUserOrderComparator implements Comparator {

	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(T, T)
	 */
	public int compare(Object p_o1, Object p_o2) {
		if ((p_o1 instanceof CMUserOrderBean)&&(p_o2 instanceof CMUserOrderBean))
	     {
			CMUserOrderBean tc1 = (CMUserOrderBean) p_o1;
			CMUserOrderBean tc2 = (CMUserOrderBean) p_o2;
	    	 if (tc1.getUserOrder()>tc2.getUserOrder())
	    		 return 1;
	    	 if (tc1.getUserOrder()==tc2.getUserOrder())
	    		 return 0;
	    	 if (tc1.getUserOrder()<tc2.getUserOrder())
	    		 return -1;
	     }
		return 0;
	}

}
