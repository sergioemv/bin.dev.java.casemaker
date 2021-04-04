package bi.controller.utils;

import model.BusinessRules;

public class CMCharUtils {

	
    public static boolean isSlash(char x) {
        char j = '/';
        return x == j;
    }
	
    public static String killFirstElement(String s) {
        int i = s.indexOf(BusinessRules.TESTDATA_SEPARATOR);//svonborries_01092006
        if (i > 0)
            s = s.substring(i);
        else
            s = "";
        return s;
    }
    
    public static String firstElement(String s) {
        int i = s.indexOf(BusinessRules.TESTDATA_SEPARATOR);//svonborries_01092006
        if (i > 0)
            s = s.substring(0, i);
        return s;
    }
	
    public static String trimComa(String s) {
        s = s.trim();
        while (s.startsWith(BusinessRules.TESTDATA_SEPARATOR)) {//svonborries_01092006
            s = s.substring(1);
        }
        return s;
    }
    
    public static String trimComaNames(String s) {
        s = s.trim();
        if (s.startsWith(BusinessRules.TESTDATA_SEPARATOR)) {//svonborries_01092006
            s = s.substring(1);
        }
        return s;
    }
    
    public static String remplace(String s1, String s2, String s3) {
//    	if(s2.equalsIgnoreCase("null"))
//    		s2 = "";
//    	if(s3.equalsIgnoreCase("null"))
//    		s3 = "";
        StringBuffer resp = new StringBuffer();
        int i = 0;
        while (s1.length() != 0) {
            if (s1.startsWith(s2)) {
                resp.append(s3);
                i = s2.length();
                if (i >= s1.length()) {
                    return resp.toString();
                }
            }
            else {
                resp.append(s1.charAt(0));
                i = 1;
            }
            s1 = s1.substring(i);
        }
        return resp.toString();
    }
    
    public static String repeatString(String s1, String s2) {
        int cont = 0;
        int i;
        String s11 = s1;
        if (s2.length() > s1.length()) {
            return "0"; //$NON-NLS-1$
        }
        else {
            while (s11.length() != 0) {
                if (s11.startsWith(s2)) {
                    cont++;
                    i = s2.length();
                    if (i >= s1.length()) {
                        return Integer.toString(cont);
                    }
                }
                else {
                    i = 1;
                }
                s11 = s11.substring(i);
            }
        }
        return Integer.toString(cont);
    }
    
    public static String trimAll(String s) {
        int cont = s.length();
        StringBuffer aux = new StringBuffer();
        for (int i = 1; i <= cont; i++) {
            char ch = s.charAt(i - 1);
            if (!Character.isSpaceChar(ch)) {
                aux.append(ch);
            }
        }
        return aux.toString();
    }
    
}
