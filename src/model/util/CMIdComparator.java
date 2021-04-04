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
public class CMIdComparator implements Comparator {

	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(T, T)
	 */
	public int compare(Object p_o1, Object p_o2) {
		if ((p_o1 instanceof CMIdBean)&&(p_o2 instanceof CMIdBean))
	     {
	    	 CMIdBean tc1 = (CMIdBean) p_o1;
	    	 CMIdBean tc2 = (CMIdBean) p_o2;
	    	 if (tc1.getId()>tc2.getId())
	    		 return 1;
	    	 if (tc1.getId()==tc2.getId())
	    		 return 0;
	    	 if (tc1.getId()<tc2.getId())
	    		 return -1;
	     }
		return 0;
	}

}
