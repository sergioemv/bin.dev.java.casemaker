
package bi.view.utils;

import java.util.Comparator;


/**
 * Title:        CaseMaker
 * Description:  Test tool for the automatic generation of test cases.
 * Copyright:    Copyright (c) 2003
 * Company:      BUSINESS SOFTWARE INNOVATIONS
 * @author hcanedo
 * @version 1.0
 * @since 14/06/2005 05:10:10 P.M.
 */

public class CMCauseEffectComparator implements Comparator {

	private boolean order = false; 
    /**
     * @param order
     */
	public CMCauseEffectComparator(boolean order) {
        this.order = order;
    }

	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(Object arg0, Object arg1) {
		String name0 = arg0.toString();
        String name1 = arg1.toString();
        
        if (order) {
            return name1.compareTo(name0);
        } else {
            return name0.compareTo(name1);
        }
	}

}
