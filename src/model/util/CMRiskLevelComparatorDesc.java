package model.util;

import java.util.Comparator;

public class CMRiskLevelComparatorDesc implements Comparator {

	public CMRiskLevelComparatorDesc() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int compare(Object o1, Object o2) {
	     if ((o1 instanceof CMRiskLevelBean)&&(o2 instanceof CMRiskLevelBean))
	     {
	    	 CMRiskLevelBean tc1 = (CMRiskLevelBean) o1;
	    	 CMRiskLevelBean tc2 = (CMRiskLevelBean) o2;
	    	 if (tc1.getRiskLevel()<tc2.getRiskLevel())
	    		 return 1;
	    	 if (tc1.getRiskLevel()==tc2.getRiskLevel())
	    		 return 0;
	    	 if (tc1.getRiskLevel()>tc2.getRiskLevel())
	    		 return -1;
	     }
		return 0;
	}

}
