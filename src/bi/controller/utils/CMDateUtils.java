package bi.controller.utils;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class CMDateUtils {

	
    public static String validateDate(String ac, int p) {
        int j = ac.indexOf("/");
        String day = ac.substring(0, j);
        if (Integer.parseInt(day) == 1) {
            String month = ac.substring(j + 1, ac.lastIndexOf("/"));
            if (Integer.parseInt(month) == 1) {
                String year = ac.substring(ac.lastIndexOf("/") + 1);
                int aux = Integer.parseInt(year) + p;
                return (ac.substring(0, ac.lastIndexOf("/") + 1) + Integer.toString(aux));
            }
            else {
                int aux = Integer.parseInt(month) + p;
                return (ac.substring(0, j + 1) + Integer.toString(aux) + ac.substring(ac.lastIndexOf("/")));
            }
        }
        else {
            int aux = Integer.parseInt(day) + p;
            return (Integer.toString(aux) + ac.substring(j));
        }
    }
    
	 public static void restrictedDateValues(String date)throws Exception{
	    	String year = date.substring(0, date.indexOf("/")); //$NON-NLS-1$
	    	String month = date.substring(date.indexOf("/") + 1, date.lastIndexOf("/")); //$NON-NLS-1$ //$NON-NLS-2$
	    	String day = date.substring(date.lastIndexOf("/") + 1); //$NON-NLS-1$*/
	    	restrictedDateValues(year,month,day);
	    }
	public static void restrictedDateValues(String p_year,String p_month,String p_day)throws Exception{
	    	int year= Integer.parseInt(p_year);
	    	int month=Integer.parseInt(p_month);
	    	int day=Integer.parseInt(p_day);
	    	if((year < 1000)||(year > 9999))
	    		throw new Exception();
	    	if((month <1)|| (month >12))
				throw new Exception();
	    	GregorianCalendar cal = new GregorianCalendar(year, month-1, 1);

	    	int maxDaysInMonth= cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	    	if((day < 1)||(day > maxDaysInMonth))
	    		throw new Exception();

	    }
	
	
}
