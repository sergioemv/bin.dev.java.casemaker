package bi.controller.utils;


import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Arrays;

import org.apache.commons.math.util.MathUtils;

import model.BusinessRules;
import bi.controller.StructureManager;
import bi.view.utils.CMFormatFactory;

public class CMNumberUtils {

	
	
	
    public static boolean isEOF(char x) {
        char j = '\u0003';
        return x == j;
    }
	
	
	public static boolean isValidNumber(String value) {
    	char sd='.';
		char sm=',';
    	if(CMFormatFactory.getTestObejctLocale().getCountry().equalsIgnoreCase("DE")){
    		sd=',';
    		sm='.';
    	}
    	value.trim();
        StringBuffer ac = new StringBuffer();
        char cc;
        int i = 0;
        int estado = 0;
        while (true) { //loop for ever
            if (value.length() > i) {
                cc = value.charAt(i);
            }
            else {
                cc = '\u0003';
            }
            switch (estado) {
                case 0: {
                	if (Character.isSpaceChar(cc)) {
                        i++;
                        estado = 0;
                    }
                	else if (Character.isDigit(cc)) {
                        i++;
                        ac.append(cc);
                        estado = 1;
                    }
                	else if (cc=='-') {
                        i++;
                        ac.append(cc);
                        estado = 0;
                    }
                	else
                		estado=11;
                	 break;
                }
                case 1:{
                	if (Character.isDigit(cc)) {
                        i++;
                        ac.append(cc);
                        estado = 2;
                    }
                	else if(isEOF(cc)){
                		estado=10;
                	}
                	else if(sd==cc){
                		if(sd==',')
                			cc='.';
                		i++;
                        ac.append(cc);
                        estado = 9;
                	}
                	else if(sm==cc){
                		i++;
                        estado = 5;
                	}
                	else
                		estado=11;
                	 break;
                }
                case 2:{
                	if (Character.isDigit(cc)) {
                        i++;
                        ac.append(cc);
                        estado = 3;
                    }
                	else if(isEOF(cc)){
                		estado=10;
                	}
                	else if(sd==cc){
                		if(sd==',')
                			cc='.';
                		i++;
                        ac.append(cc);
                        estado = 9;
                	}
                	else if(sm==cc){
                		i++;
                        estado = 5;
                	}
                	else
                		estado=11;
                	 break;
                }
                case 3:{
                	if (Character.isDigit(cc)) {
                        i++;
                        ac.append(cc);
                        estado = 4;
                    }
                	else if(isEOF(cc)){
                		estado=10;
                	}
                	else if(sd==cc){
                		if(sd==',')
                			cc='.';
                		i++;
                        ac.append(cc);
                        estado = 9;
                	}
                	else if(sm==cc){
                		i++;
                        estado = 5;
                	}
                	else
                		estado=11;
                	 break;
                }
                case 4:{
                	if (Character.isDigit(cc)) {
                        i++;
                        ac.append(cc);
                        estado = 4;
                    }
                	else if(isEOF(cc)){
                		estado=10;
                	}
                	else if(sd==cc){
                		if(sd==',')
                			cc='.';
                		i++;
                        ac.append(cc);
                        estado = 9;
                	}
                	else
                		estado=11;
                	 break;
                }
                case 5:{
                	if (Character.isDigit(cc)) {
                        i++;
                        ac.append(cc);
                        estado = 6;
                    }
                	else
                		estado=11;
                	 break;
                }
                case 6:{
                	if (Character.isDigit(cc)) {
                        i++;
                        ac.append(cc);
                        estado = 7;
                    }
                	else
                		estado=11;
                	 break;
                }
                case 7:{
                	if (Character.isDigit(cc)) {
                        i++;
                        ac.append(cc);
                        estado = 8;
                    }
                	else
                		estado=11;
                	 break;
                }
                case 8:{
                	if(isEOF(cc)){
                		estado=10;
                	}
                	else if(sd==cc){
                		if(sd==',')
                			cc='.';
                		i++;
                        ac.append(cc);
                        estado = 9;
                	}
                	else if(sm==cc){
                		i++;
                        estado = 5;
                	}
                	else
                		estado=11;
                	 break;
                }
                case 9:{
                	if (Character.isDigit(cc)) {
                        i++;
                        ac.append(cc);
                        estado = 12;
                    }

                	else
                		estado=11;
                	 break;
                }
                case 10:{
                	return true;
                }
                case 11:{
                	return false;
                }
                case 12:{
                	if (Character.isDigit(cc)) {
                        i++;
                        ac.append(cc);
                        estado = 12;
                    }
                	else if(isEOF(cc)){
                		estado=10;
                	}
                	else
                		estado=11;
                	 break;
                }
            }
        }
    }
	
	
    public static String analizer(String value) {
        value.trim();
        StringBuffer ac = new StringBuffer();
        char cc;
        int p = 0, i = 0;
        int estado = 0;
        while (true) { //loop for ever
            if (value.length() > i) {
                cc = value.charAt(i);
            }
            else {
                cc = '\u0003';
            }
            switch (estado) {
                case 0: {
                        if (Character.isSpaceChar(cc)) {
                            i++;
                            estado = 0;
                        }
                        else if (Character.isLetter(cc)) {
                            i++;
                            ac.append(cc);
                            estado = 1;
                        }
                        else if (Character.isDigit(cc)) {
                            i++;
                            ac.append(cc);
                            estado = 2;
                        }
                        else if (isMen(cc)) {
                            i++;
                            ac.append(cc);
                            estado = 3;
                        }
                        else if (isMay(cc)) {
                            i++;
                            ac.append(cc);
                            estado = 4;
                        }
                        else if (isEquals(cc)) {
                            i++;
                            ac.append(cc);
                            estado = 5;
                        }
                        else if (isNeg(cc)) {
                            estado = 2;
                        }
                        else if (isEOF(cc)) {
                            estado = 6;
                        }
                        else {
                            i++;
                            ac.append(cc);
                            estado = 7;
                        }
                        break;
                    }
                case 1: {
                        if (Character.isLetter(cc) || Character.isSpaceChar(cc)) {
                            i++;
                            ac.append(cc);
                            estado = 1;
                        }
                        else if (Character.isDigit(cc)) {
                            i++;
                            ac.append(cc);
                            estado = 14;
                        }
                        else {
                            estado = 7; // 15;
                        }
                        break;
                    }
                case 2: {
                        if (Character.isDigit(cc)) {
                            i++;
                            ac.append(cc);
                            estado = 2;
                        }
                        else if (CMCharUtils.isSlash(cc)) {
                            i++;
                            ac.append(cc);
                            estado = 16;
                        }
                        else if (isPoint(cc)) {
                            i++;
                            estado = 19;
                        }
                        else if (isComa(cc)) {
                            i++;
                            ac.append('.');
                            estado = 20;
                        }
                        else if (isEOF(cc)) {
                            estado = 13;
                        }
                        else {
                            estado = 7;
                        }
                        break;
                    }
                case 3: {
                        if (isMay(cc)) {
                            i++;
                            ac.append(cc);
                            estado = 11;
                        }
                        else if (isEquals(cc)) {
                            i++;
                            ac.append(cc);
                            estado = 10;
                        }
                        else {
                            estado = 12;
                        }
                        break;
                    }
                case 4: {
                        if (isEquals(cc)) {
                            i++;
                            ac.append(cc);
                            estado = 8;
                        }
                        else {
                            estado = 9;
                        }
                        break;
                    }
                case 5: {
                        if (Character.isDigit(cc) || isNeg(cc)) {
                            ac.deleteCharAt(0);
                            i++;
                            ac.append(cc);
                            p = 0;
                            estado = 23;
                        }
                        else if (Character.isSpaceChar(cc)) {
                            ac.append(cc);
                            i++;
                            estado = 25;
                        }
                        else {
                            estado = 7;
                        }
                        break;
                    }
                case 6: {
                        if (ac.length() == 0) {
                            return (BusinessRules.TESTDATA_EMPTY_AT_EQUIVALENCE_CLASS_IN_TEST_CASE + ",binary");
                        }
                        else {
                            return ac.toString();
                        }
                    }
                case 7: {
                        if (isEOF(cc)) {
                            ac.append(",varChar");
                            return (ac.toString());
                        }
                        else {
                            i++;
                            ac.append(cc);
                            estado = 7;
                            break;
                        }
                    }
                case 8: {
                        p = 0;
                        if (Character.isDigit(cc)) {
                            ac = new StringBuffer();
                            estado = 23;
                        }
                        else if (isNeg(cc)) {
                            ac = new StringBuffer();
                            ac.append(cc);
                            i++;
                            estado = 23;
                        }
                        else if (Character.isSpaceChar(cc)) {
                            ac.append(cc);
                            i++;
                            estado = 25;
                        }
                        else
                            estado = 7;
                        break;
                    }
                case 9: {
                        p = 1;
                        if (Character.isDigit(cc)) {
                            ac = new StringBuffer();
                            estado = 23;
                        }
                        else if (isNeg(cc)) {
                            ac = new StringBuffer();
                            ac.append(cc);
                            i++;
                            estado = 23;
                        }
                        else if (Character.isSpaceChar(cc)) {
                            ac.append(cc);
                            i++;
                            estado = 25;
                        }
                        else
                            estado = 7;
                        break;
                    }
                case 10: {
                        p = 0;
                        if (Character.isDigit(cc)) {
                            ac = new StringBuffer();
                            estado = 23;
                        }
                        else if (isNeg(cc)) {
                            ac = new StringBuffer();
                            ac.append(cc);
                            i++;
                            estado = 23;
                        }
                        else if (Character.isSpaceChar(cc)) {
                            ac.append(cc);
                            i++;
                            estado = 25;
                        }
                        else
                            estado = 7;
                        break;
                    }
                case 11: {
                        p = 1;
                        if (Character.isDigit(cc)) {
                            ac = new StringBuffer();
                            estado = 23;
                        }
                        else if (isNeg(cc)) {
                            ac = new StringBuffer();
                            ac.append(cc);
                            i++;
                            estado = 23;
                        }
                        else if (Character.isSpaceChar(cc)) {
                            ac.append(cc);
                            i++;
                            estado = 25;
                        }
                        else
                            estado = 7;
                        break;
                    }
                case 12: {
                        p = -1;
                        if (Character.isDigit(cc)) {
                            ac = new StringBuffer();
                            estado = 23;
                        }
                        else if (isNeg(cc)) {
                            ac = new StringBuffer();
                            ac.append(cc);
                            i++;
                            estado = 23;
                        }
                        else if (Character.isSpaceChar(cc)) {
                            ac.append(cc);
                            i++;
                            estado = 25;
                        }
                        else
                            estado = 7;
                        break;
                    }
                case 13: {
                        int aux = Integer.parseInt(ac.toString().trim());
                        aux = aux + p;
                        String s = Integer.toString(aux) + ",numeric";
                        return s;
                    }
                case 14: {
                        if (Character.isDigit(cc)) {
                            i++;
                            ac.append(cc);
                            estado = 14;
                        }
                        else if (Character.isLetter(cc)) {
                            i++;
                            ac.append(cc);
                            estado = 1;
                        }
                        else {
                            estado = 7; // 15;
                        }
                        break;
                    }
                case 15: {
                        ac.append(",varChar");
                        return (ac.toString());
                    }
                case 16: {
                        if (Character.isDigit(cc)) {
                            i++;
                            ac.append(cc);
                            estado = 17;
                        }
                        else {
                            estado = 7;
                        }
                        break;
                    }
                case 17: {
                        if (Character.isDigit(cc)) {
                            i++;
                            ac.append(cc);
                            estado = 17;
                        }
                        else if (CMCharUtils.isSlash(cc)) {
                            i++;
                            ac.append(cc);
                            estado = 22; // 16;
                        }
                        else {
                            estado = 7; //18;
                        }
                        break;
                    }
                case 18: {
                        String date = CMDateUtils.validateDate(ac.toString(), p);
                        return (date + ",dateTime");
                        //                    break;
                    }
                case 19: {
                        if (Character.isDigit(cc)) {
                            i++;
                            ac.append(cc);
                            estado = 2;
                        }
                        else {
                            estado = 13;
                        }
                        break;
                    }
                case 20: {
                        if (Character.isDigit(cc)) {
                            i++;
                            ac.append(cc);
                            estado = 20;
                        }
                        else if (isEOF(cc)) {
                            estado = 21;
                        }
                        else {
                            estado = 7;
                        }
                        break;
                    }
                case 21: {
                        String s = ac.toString().trim();
                        Float aux1 = Float.valueOf(ac.toString().trim());
                        float aux = aux1.floatValue();
                        if (p != 0) {
                            float decimal = calculateNumOfDecimal(ac.toString(), p);
                            aux = aux + decimal;
                            s = Float.toString(aux);
                        }
                        s.replace('.', ',');
                        String r = s + ",float";
                        return r;
                    }
                case 22: {
                        if (Character.isDigit(cc)) {
                            i++;
                            ac.append(cc);
                            estado = 22;
                        }
                        else if (isEOF(cc)) {
                            String date = CMDateUtils.validateDate(ac.toString(), p);
                            return (date + ",dateTime");
                        }
                        else {
                            estado = 7; //18;
                        }
                        break;
                    }
                case 23: {
                        if (Character.isDigit(cc)) {
                            i++;
                            ac.append(cc);
                            estado = 23;
                        }
                        else if (isPoint(cc)) {
                            i++;
                            estado = 23;
                        }
                        else if (isComa(cc)) {
                            i++;
                            ac.append('.');
                            estado = 24;
                        }
                        else {
                            estado = 13; //18;
                        }
                        break;
                    }
                case 24: {
                        if (Character.isDigit(cc)) {
                            i++;
                            ac.append(cc);
                            estado = 24;
                        }
                        else {
                            estado = 21;
                        }
                        break;
                    }
                case 25: {
                        if (Character.isDigit(cc)) {
                            ac = new StringBuffer();
                            estado = 23;
                        }
                        else if (isNeg(cc)) {
                            ac = new StringBuffer();
                            ac.append(cc);
                            i++;
                            estado = 23;
                        }
                        else if (Character.isSpaceChar(cc)) {
                            ac.append(cc);
                            i++;
                            estado = 25;
                        }
                        else
                            estado = 7;
                        break;
                    }
            }
        }
    }
    
    
    public static boolean isInterval(String s) {
        s = s.trim();
        if (s.startsWith("=") || s.startsWith(">") || s.startsWith("<")) {
            char cc;
            int i = 0;
            int estado = 0;
            StringBuffer ac = new StringBuffer();
            while (true) { //loop for ever
                if (s.length() > i) {
                    cc = s.charAt(i);
                }
                else {
                    cc = '\u0003';
                }
                switch (estado) {
                    case 0: {
                            if (isMen(cc)) {
                                i++;
                                estado = 1;
                            }
                            else if (isMay(cc)) {
                                i++;
                                estado = 2;
                            }
                            else if (isEquals(cc)) {
                                i++;
                                estado = 3;
                            }
                            break;
                        }
                    case 1: {
                            if (!isMen(cc) && !isEOF(cc)) {
                                i++;
                                estado = 4;
                            }
                            else {
                                return false;
                            }
                            break;
                        }
                    case 2: {
                            if (!isMen(cc) && !isMay(cc) && !isEOF(cc)) {
                                i++;
                                estado = 4;
                            }
                            else {
                                return false;
                            }
                            break;
                        }
                    case 3: {
                            if (!isMen(cc) && !isMay(cc) && !isEquals(cc) && !isEOF(cc)) {
                                i++;
                                estado = 4;
                            }
                            else {
                                return false;
                            }
                            break;
                        }
                    case 4: {
                            if (isMen(cc)) {
                                if (!ac.toString().trim().equals("")) {
                                    i++;
                                    estado = 5;
                                }
                                else
                                    return false;
                            }
                            else if (isMay(cc)) {
                                if (!ac.toString().trim().equals("")) {
                                    i++;
                                    estado = 6;
                                }
                                else {
                                    return false;
                                }
                            }
                            else if (isEquals(cc)) {
                                if (!ac.toString().trim().equals("")) {
                                    i++;
                                    estado = 7;
                                }
                                else {
                                    return false;
                                }
                            }
                            else if (isEOF(cc)) {
                                return false;
                            }
                            else {
                                ac.append(cc);
                                i++;
                                estado = 4;
                            }
                            break;
                        }
                    case 5: {
                            if (!isMen(cc) && !isEOF(cc)) {
                                i++;
                                estado = 8;
                            }
                            else {
                                return false;
                            }
                            break;
                        }
                    case 6: {
                            if (!isMen(cc) && !isMay(cc) && !isEOF(cc)) {
                                i++;
                                estado = 8;
                            }
                            else {
                                return false;
                            }
                            break;
                        }
                    case 7: {
                            if (!isMen(cc) && !isMay(cc) && !isEquals(cc) && !isEOF(cc)) {
                                i++;
                                estado = 8;
                            }
                            else {
                                return false;
                            }
                            break;
                        }
                    case 8: {
                            if (isMen(cc) && isMay(cc) && isEquals(cc)) {
                                return false;
                            }
                            else if (isEOF(cc)) {
                                return true;
                            }
                            else {
                                i++;
                                estado = 8;
                            }
                        }
                }
            }
        }
        else
            return false;
    }
    
    
    public static String getFirstValue(String value) {
        value.trim();
        StringBuffer ac = new StringBuffer();
        char cc;
        int i = 0;
        int estado = 0;
        while (true) { //loop for ever
            if (value.length() > i) {
                cc = value.charAt(i);
            }
            else {
                cc = '\u0003';
            }
            switch (estado) {
                case 0: {
                        if (isEquals(cc)) {
                            ac.append(cc);
                            i++;
                            estado = 1;
                            break;
                        }
                        else if (isMay(cc)) {
                            ac.append(cc);
                            i++;
                            estado = 2;
                            break;
                        }
                        else if (isMen(cc)) {
                            ac.append(cc);
                            i++;
                            estado = 3;
                            break;
                        }
                        else if (isEOF(cc)) {
                            return value;
                        }
                        else {
                            ac.append(cc);
                            i++;
                            estado = 4;
                            break;
                        }
                    }
                case 1: {
                        if (isEOF(cc)) {
                            return ac.toString();
                        }
                        else if (!isEquals(cc) && !isMay(cc) && !isMen(cc)) {
                            ac.append(cc);
                            i++;
                            estado = 1;
                            break;
                        }
                        else {
                            return ac.toString();
                        }
                    }
                case 2: {
                        if (isEOF(cc)) {
                            return ac.toString();
                        }
                        else if (isEquals(cc) || !isMay(cc) || !isMen(cc)) {
                            ac.append(cc);
                            i++;
                            estado = 1;
                            break;
                        }
                        else {
                            ac.append(cc);
                            i++;
                            estado = 4;
                            break;
                        }
                    }
                case 3: {
                        if (isEOF(cc)) {
                            return ac.toString();
                        }
                        else if (isEquals(cc) || isMay(cc) || !isMen(cc)) {
                            ac.append(cc);
                            i++;
                            estado = 1;
                            break;
                        }
                        else {
                            ac.append(cc);
                            i++;
                            estado = 4;
                            break;
                        }
                    }
                case 4: {
                        if (!isEOF(cc)) {
                            ac.append(cc);
                            i++;
                            estado = 4;
                            break;
                        }
                        else {
                            return ac.toString();
                        }
                    }
            }
        }
    }
    
    
    public static boolean isMen(char x) {
        char j = '<';
        return x == j;
    }
    
    public static boolean isEquals(char x) {
        char j = '=';
        return x == j;
    }
	
    
    public static boolean isMay(char x) {
        char j = '>';
        return x == j;
    }
    
    public static boolean isNeg(char x) {
        char j = '-';
        return x == j;
    }
    
    
    public static boolean isPoint(char x) {
        char j = '.';
        return x == j;
    }

    public static boolean isComa(char x) {
        char j = ',';
        return x == j;
    }
    
    
    public static float calculateNumOfDecimal(String numFloat, int dif) {
    	//Logger.getLogger(this.getClass()).debug(numFloat);
        String decimals = numFloat.substring(numFloat.indexOf(".") + 1, numFloat.length());
        int cant = decimals.length();
        StringBuffer result;
        if (cant > 0) {
            if (dif < 0)
                result = new StringBuffer("-0.");
            else
                result = new StringBuffer("0.");
            for (int i = 1; i < cant; i++) {
                result.append("0");
            }
            if (dif == 0)
                result.append("0");
            else
                result.append("1");
            //Logger.getLogger(this.getClass()).debug(result.toString());
            return Float.parseFloat(result.toString());
        }
        else {
            if (dif < 0)
                return -1f;
            else
                return 1f;
        }
    }
    
    /**
     * metodo que Formatea un String que contiene numero usando el siguiente patron ###.########################
     * Para el calulo de formulas Anidadas
     * @param String value que son los valores de los parametros de una formula.
     * @return String result que es el String formateado con el patron
     */
    public static String defaultFormatParam(String value) {

    	char sd='.';
		char sm=',';
    	if(CMFormatFactory.getTestObejctLocale().getCountry().equalsIgnoreCase("DE")){
    		sd=',';
    		sm='.';
    	}
    	value.trim();
        StringBuffer ac = new StringBuffer();
        char cc;
        int i = 0;
        int estado = 0;
        while (true) { //loop for ever
            if (value.length() > i) {
                cc = value.charAt(i);
            }
            else {
                cc = '\u0003';
            }
            switch (estado) {
                case 0: {
                	if (Character.isSpaceChar(cc)) {
                        i++;
                        estado = 0;
                    }
                	else if (Character.isDigit(cc)) {
                        i++;
                        ac.append(cc);
                        estado = 1;
                    }
                	else if (cc=='-') {
                        i++;
                        ac.append(cc);
                        estado = 0;
                    }
                	else
                		estado=11;
                	 break;
                }
                case 1:{
                	if (Character.isDigit(cc)) {
                        i++;
                        ac.append(cc);
                        estado = 2;
                    }
                	else if(isEOF(cc)){
                		estado=10;
                	}
                	else if(sd==cc){
                		if(sd==',')
                			cc='.';
                		i++;
                        ac.append(cc);
                        estado = 9;
                	}
                	else if(sm==cc){
                		i++;
                        estado = 5;
                	}
                	else
                		estado=11;
                	 break;
                }
                case 2:{
                	if (Character.isDigit(cc)) {
                        i++;
                        ac.append(cc);
                        estado = 3;
                    }
                	else if(isEOF(cc)){
                		estado=10;
                	}
                	else if(sd==cc){
                		if(sd==',')
                			cc='.';
                		i++;
                        ac.append(cc);
                        estado = 9;
                	}
                	else if(sm==cc){
                		i++;
                        estado = 5;
                	}
                	else
                		estado=11;
                	 break;
                }
                case 3:{
                	if (Character.isDigit(cc)) {
                        i++;
                        ac.append(cc);
                        estado = 4;
                    }
                	else if(isEOF(cc)){
                		estado=10;
                	}
                	else if(sd==cc){
                		if(sd==',')
                			cc='.';
                		i++;
                        ac.append(cc);
                        estado = 9;
                	}
                	else if(sm==cc){
                		i++;
                        estado = 5;
                	}
                	else
                		estado=11;
                	 break;
                }
                case 4:{
                	if (Character.isDigit(cc)) {
                        i++;
                        ac.append(cc);
                        estado = 4;
                    }
                	else if(isEOF(cc)){
                		estado=10;
                	}
                	else if(sd==cc){
                		if(sd==',')
                			cc='.';
                		i++;
                        ac.append(cc);
                        estado = 9;
                	}
                	else
                		estado=11;
                	 break;
                }
                case 5:{
                	if (Character.isDigit(cc)) {
                        i++;
                        ac.append(cc);
                        estado = 6;
                    }
                	else
                		estado=11;
                	 break;
                }
                case 6:{
                	if (Character.isDigit(cc)) {
                        i++;
                        ac.append(cc);
                        estado = 7;
                    }
                	else
                		estado=11;
                	 break;
                }
                case 7:{
                	if (Character.isDigit(cc)) {
                        i++;
                        ac.append(cc);
                        estado = 8;
                    }
                	else
                		estado=11;
                	 break;
                }
                case 8:{
                	if(isEOF(cc)){
                		estado=10;
                	}
                	else if(sd==cc){
                		if(sd==',')
                			cc='.';
                		i++;
                        ac.append(cc);
                        estado = 9;
                	}
                	else if(sm==cc){
                		i++;
                        estado = 5;
                	}
                	else
                		estado=11;
                	 break;
                }
                case 9:{
                	if (Character.isDigit(cc)) {
                        i++;
                        ac.append(cc);
                        estado = 12;
                    }

                	else
                		estado=11;
                	 break;
                }
                case 10:{
                	return ac.toString();
                }
                case 11:{
                	return null;
                }
                case 12:{
                	if (Character.isDigit(cc)) {
                        i++;
                        ac.append(cc);
                        estado = 12;
                    }
                	else if(isEOF(cc)){
                		estado=10;
                	}
                	else
                		estado=11;
                	 break;
                }
            }
        }
    }
    
    public static String getFormatRoundTo(int decimals, String p_Format){
    	String formatresult= new String();
    	if(decimals==0){
    		formatresult= "###,##0";
    	}
    	else{
    		formatresult= p_Format;
    		for(int i=1; i<decimals;i++){
    			formatresult=formatresult+"0";
    		}
    	}
    	return formatresult;
    }
    
    public static double ceilTo(int decimals, double value)
    {
        if (decimals == 0)
        {
            value = (int)(value + .9);
        }
        else
        {
            double shift = Math.pow(10.0, decimals);
            value = value * shift;
            if(decimals > 8 || value >= Integer.MAX_VALUE){
            	NumberFormat formatter = new DecimalFormat("#.##################");
                String s = formatter.format(value);
            	BigDecimal decimal=new BigDecimal(s);
            	decimal=decimal.add(new BigDecimal("0.9"));
            	BigInteger number2=decimal.toBigInteger();
            	value=number2.doubleValue();

            }
            else{
            		value = (int)(value + .9);
            }
            value = value / shift;
        }
        return value;
    }
    
    public static double division(double numX, double numY) {
        double x = numX / numY;
        // double y =numX%numY;
        return (x);
    }
    
    public static double factorial(int num) throws Exception {
    	return MathUtils.factorialDouble(num);
//    	if(num < 0)
//    		throw new Exception();
//        if (num == 0) {
//            return 1;
//        }
//        else if (num == 1) {
//            return 1;
//        }
//        else {
//            double x = factorial(num - 1);
//            return x * num;
//        }
    }
    
    public static double floorTo(int decimals, double value)
    {
        if (decimals == 0)
        {
            value = (int)(value + .4);
        }
        else
        {
            double shift = Math.pow(10, decimals);
            value = value * shift;
            if(decimals > 8 || value >= Integer.MAX_VALUE){
            	NumberFormat formatter = new DecimalFormat("#.##################");
                String s = formatter.format(value);
            	BigDecimal decimal=new BigDecimal(s);
            	//decimal=decimal.add(new BigDecimal("0.4"));
            	BigInteger number2=decimal.toBigInteger();
            	value=number2.doubleValue();

            }
            else{
            	value = (int)(value );//+ .4);
            }
            value = value / shift;
        }
        return value;
    }
    
    public static String getRomanString(int aValue) throws Exception
    {

    	String gRomanCharsA = "ixcm";
    	String gRomanCharsB = "vld?";
      StringBuffer  addOn  = new StringBuffer();
      StringBuffer  result2 = new StringBuffer();
      StringBuffer  decStr = new StringBuffer();

      decStr.append(aValue);

      int           len=decStr.length();
      int           romanPos=len;
      int           n,digitPos;
      boolean       negative=(aValue<0  || aValue>3999);
      if(negative){
    	  throw new Exception();
      }
      aValue=Math.abs(aValue);
      for(digitPos=0;digitPos<len;digitPos++)
      {
        romanPos--;
        addOn.setLength(0);
        switch(decStr.charAt(digitPos))
        {
          case  '3':  addOn.append(gRomanCharsA.charAt(romanPos));
          case  '2':  addOn.append(gRomanCharsA.charAt(romanPos));
          case  '1':  addOn.append(gRomanCharsA.charAt(romanPos));
            break;

          case  '4':
            addOn.append(gRomanCharsA.charAt(romanPos));

          case  '5':  case  '6':
          case  '7':  case  '8':
            addOn.append(gRomanCharsB.charAt(romanPos));
            for(n=0;n<(decStr.charAt(digitPos)-'5');n++)
              addOn.append(gRomanCharsA.charAt(romanPos));
            break;
          case  '9':
            addOn.append(gRomanCharsA.charAt(romanPos));
            addOn.append(gRomanCharsA.charAt(romanPos+1));
            break;
          default:
            break;
        }
        result2.append(addOn);
      }
      return result2.toString().toUpperCase();
    }
    
    /**  */
    /**
     * rounds a double to the specified number of decimal places
     */
    public static double roundTo(int decimals, double value)
    {
    	return MathUtils.round(value, decimals);
    	
//        if (decimals == 0)
//        {
//            value = (int)(value + .5);
//        }
//        else
//        {
//            double shift = Math.pow(10, decimals);
//            value = value * shift;
//            value = (int)(value + .5);
//            value = value / shift;
//        }
//        return value;
    }
    
    public static double aCosHyp(double X) {
        return Math.log(X + Math.sqrt(X * X - 1));
    }
    
    public static double aSenHyp(double X) {
        return Math.log(X + Math.sqrt(X * X + 1));
    }

    public static double aTanHyp(double X) {
        return Math.log((1 + X) / (1 - X)) / 2;
    }
    
    public static double cosHyp(double num) {
        return ((Math.exp(num)) + (Math.exp(-num))) / 2;
    }


    public static double senHyp(double num) {
        return ((Math.exp(num)) - (Math.exp(-num))) / 2;
    }

    public static double tanHyp(double num) {
        return senHyp(num) / cosHyp(num);
    }
    
    public static String createSpecialFormatPatternWithAllDecimals(String p_value){
				//eliminate all white spaces
				String l_value = p_value.trim().replaceAll("\\s", "");
				
				DecimalFormatSymbols testObjectSymbols = new DecimalFormatSymbols();
				if (StructureManager.getSelectedStructure().getTestObject().isDefaultSeparator())
				{
					testObjectSymbols.setDecimalSeparator('.');
					testObjectSymbols.setGroupingSeparator(',');
				}else
				{
					testObjectSymbols.setDecimalSeparator(StructureManager.getSelectedStructure().getTestObject().
							getDecimalSeparator().charAt(0));
					testObjectSymbols.setGroupingSeparator(StructureManager.getSelectedStructure().getTestObject().
							getMilesSeparator().charAt(0));
				}
				
				
				char thoSep = testObjectSymbols.getGroupingSeparator();
				StringBuilder newValue = new StringBuilder();
				char decSep = testObjectSymbols.getDecimalSeparator();
				int decPos = l_value.indexOf(decSep);
				int thoCount = 0;
				int i = 0;
//				count all thousand separators and commas from the original string after the comma
				for (Character ch : l_value.toCharArray())
				{
					if (((ch.charValue() == thoSep) 
					 || (ch.charValue() == decSep) || 
					 (ch.charValue() == '-' && decPos !=-1)) &&(i>decPos))
					 {
						thoCount++;
					}
					else
						//create a new string without those extra symbols
						newValue.append(ch);
					i++;
				}
				//change the decimal format to match the ammount of digits in the string after the comma
				if (l_value.indexOf(decSep)>0)
				{
					int positions = p_value.length() - l_value.indexOf(decSep)-1;
					char[] zeros = new char[positions-thoCount];
					Arrays.fill(zeros,'0');
					return ("#,##0."+String.copyValueOf(zeros));
				}
				else
					//use de default format
					return (null);
    } 
}
