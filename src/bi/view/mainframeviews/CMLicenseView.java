/**
 * Developed by BUSINESS SOFTWARE INNOVATIONS. .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
//05.04.04

package bi.view.mainframeviews;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import javax.swing.JOptionPane;

import model.BusinessRules;
import model.License;
import model.Session2;

import org.apache.log4j.Logger;

import bi.controller.LicenseManager;
import bi.view.lang.CMMessages;
import bi.view.utils.CMModalResult;
import bi.view.utils.GetEnvironmentVariables;
import bi.view.utils.NetworkInfo;
import biz.bi.license.controller.RemoteLicenseManager;

public class CMLicenseView {
    private CMApplication m_CMApplication;
    private StringBuffer m_Code;

    public CMLicenseView(CMApplication p_CMApplication) {
        m_CMApplication = p_CMApplication;
    }

    public int gmp_mod(int valor, int divisor) {
        return valor % divisor;
    }

    public String getHDSerialNumber() {
        String ds = new String("CDEFGHIJKLMNOPQRSTUVWXZ"); //$NON-NLS-1$
        String hdSerialNumber;
        for (int i = 0; i < ds.length(); i++) {

            if (directoryExists(ds.charAt(i))) {
                hdSerialNumber = getHardDiscSerialNumber(ds.charAt(i));
                int index = hdSerialNumber.indexOf('-');
                StringBuffer sBuffer = new StringBuffer(hdSerialNumber);
                if (index >= 0) {
                    sBuffer.deleteCharAt(index);
                }
                return sBuffer.toString();
            }
        }
        return null;
    }

    public String tomorrow() {
        StringBuffer filePath = new StringBuffer();
        filePath.append(m_CMApplication.getSessionManager().getM_InstallationDirectory()); //$NON-NLS-1$
        filePath.append(BusinessRules.URL_SEPARATOR); //$NON-NLS-1$
        filePath.append("lic.xml"); //$NON-NLS-1$
        LicenseManager LM = new LicenseManager();
        License license = LM.readLicense(filePath.toString());
        if (license != null) {
            return license.getM_LicenseValue();
        }
        else {
            return null;
        }
    }
    public String getM_Code() {
        return m_Code.toString();
    }
    public String requestInput(String p_HDSerialNumber, License p_License) {
        StringBuffer sBuffer = new StringBuffer();
        sBuffer.append(p_License.getM_ClientId());
        sBuffer.append("-"); //$NON-NLS-1$
        sBuffer.append(p_License.getM_Pd());
        sBuffer.append("-"); //$NON-NLS-1$
        sBuffer.append(p_HDSerialNumber);
        CMLicenseInputRequestView dlg = new CMLicenseInputRequestView();
        dlg.setCode(sBuffer.toString());

        dlg.show();
        if (dlg.getModalResult() == CMModalResult.OK) {
            return dlg.getLicenseTextField();
        }
        else {
            System.exit(0);
            return ""; //$NON-NLS-1$
        }
    }
    public boolean checkLicense(boolean checkDate) {
      LicenseManager LM = new LicenseManager();
      boolean licenseIsOk = false;
      if (LM.getClientType().equals("Stand-Alone"))
      {
    	License license;
        String licenseValue;
        Vector serialNumberposibilitys= new Vector(0);

        Vector hdSerialNumbers = NetworkInfo.getMacAddress();
        String hdSerialNumber="";
        if (hdSerialNumbers.size() <=0){//equalsIgnoreCase("empty"))
        	hdSerialNumber="empty";
        }
        else{
        	hdSerialNumber=hdSerialNumbers.firstElement().toString();
        }
        if(hdSerialNumber.equalsIgnoreCase("empty"))
            hdSerialNumber = this.getHDSerialNumber();
        if (hdSerialNumber.equalsIgnoreCase("empty"))
            hdSerialNumber = NetworkInfo.getLocalHostName();


        //String serialNumber = NetworkInfo.getMacAddress();
        //if (!serialNumber.equalsIgnoreCase("empty"))
       
        for(int i=0; i< hdSerialNumbers.size();i++)
        	serialNumberposibilitys.addElement(new String(hdSerialNumbers.elementAt(i).toString()));
        String serialNumber = this.getHDSerialNumber();
        if (!serialNumber.equalsIgnoreCase("empty"))
        	serialNumberposibilitys.addElement(new String(serialNumber));
        serialNumber = NetworkInfo.getLocalHostName();
        serialNumberposibilitys.addElement(new String(serialNumber));
        String m_InstallationDirectory = m_CMApplication.getSessionManager().getM_InstallationDirectory();
        if (!LM.existLicenseFile(m_InstallationDirectory)) {
            license = LM.writeNewLicenseFile(m_InstallationDirectory);
        }
        else {
            license = LM.getExistingLicense(m_InstallationDirectory);
        }
        //grueda_19072004_begin
        try{
        if (license.getM_LicenseValue().indexOf(BusinessRules.LICENSE_TYPE_DEMO) > 0) {
            hdSerialNumber = BusinessRules.LICENSE_TYPE_DEMO;
            serialNumberposibilitys.insertElementAt(new String(hdSerialNumber),0);
        }
        else if (license.getM_LicenseValue().indexOf(BusinessRules.LICENSE_TYPE_FULL) > 0) {
            hdSerialNumber = BusinessRules.LICENSE_TYPE_FULL;
            serialNumberposibilitys.insertElementAt(new String(hdSerialNumber),0);
        }
        } catch (Exception e)
        {
        	   hdSerialNumber = BusinessRules.LICENSE_TYPE_DEMO;
               serialNumberposibilitys.insertElementAt(new String(hdSerialNumber),0);
        }
        
        //grueda_19072004_end
        while (!licenseIsOk) {
        	for(int i=0; i<serialNumberposibilitys.size();i++){
        		String serialNumberAt=serialNumberposibilitys.elementAt(i).toString();
        		if (checkLicense(checkDate, license, serialNumberAt)) {
                    licenseIsOk = true;
                    LM.writeLicense(license,m_InstallationDirectory);
                    return licenseIsOk;
                }
        	}
        	/*if (checkLicense(checkDate, license, hdSerialNumber)) {
                licenseIsOk = true;
                m_CMApplication.getSessionManager().writeLicense(license);
            }
            else {*/
                licenseValue = requestInput(hdSerialNumber, license);
                license.setM_LicenseValue(licenseValue);
                licenseIsOk = false;
            //}
        }
      }
      else
      {
        //Here call the RemoteLicenseManager
   	    RemoteLicenseManager RLM = new RemoteLicenseManager();
   	    licenseIsOk = RLM.checkLicense();
      }
      return licenseIsOk;
    }

    public boolean checkLicense(boolean checkDate, License p_License, String p_HDSerialNumber) {
        // **************
        // Modul zur Überprüfung der Gültigkeit einer Lizenznummer ausgehend von den Code aus CaseMaker
        // ***********
        // Dieses Modul braucht vier Codes
        // code1 = seriennummer
        // code2 = id
        // code3 = prozessorID
        // code4 = Lizenz
        // benotite Funktionen:
        // modulo => gmp_mod(valor,divisor). gmp_mod liefert den Rest einer Division.
        // char => ord(char). Liefert den Wert des Ascii Zeichens
        // Die Lizenznummer verfügt über 36 Zeichen!!
        // Folgende Prüfungen müssen durchegführt werden:
        // 1. Die Seriennummer stimmt überein mit dem modulo der ersten Zeichen der Lizenz (Länge der Seriennummer) überein.
        //    Die Seriennummer stellt die Länge des Namens des Kunden.
        // 2. Die Positionen 16 udn 19 sind Prüfziffern und sind gleich code2[1] und code2[2]
        // 3. Der Mode von den Position 20 bsi 25 und 30 bsi 35 sind gleich und Liefern den Lizenzablauf
        // 4. Die letzten 4 Ziffern von code3 müssen mit den Ziffern in den Positionen 26 bis 29 übereinstiimen.
        // 5. Die Position 36 ist eine Prüfziffer
        // Von CaseMaker müssen zusätzlich folgende Prüfungen bzw. Ergänzungen durchgeführt werden.
        // Bei der Erstellung des Prozessor-ID muß geprüft werden, dass sie >= 4 Ziffer ist. Sollte es icht der fall sein,
        // muß sie mit zusätzlichen Ziffern ergänzt werden, die bei der Prüfung zu berücksichtigen wäre.
        // Das Datum muß <= Ablaufdatum sein. Sollte das Datum < Ablaufdatum sein, dann nach Dateien in c:/temp, c:\windows,
        // c:\windows\system, c:\windows\system32
        // die erstellt Datum > Ablaufdatum.
        //
        // Diese Prüfungen müssen bei jedem Start durchgeführt werden.
        // Die Ablaufprüfung müß aber abgeschaltet werden können.
        // Zum Beispiel durch den Aufruf casemaker.exe -variable
        // variable muss für jedes Programm extra im Quell-Code definiert werden.
        // Die anderen Prüfungen der Lizenz müssen aber erfolgen.
        //
        // **************
        // --------------------------
        // Importiere die Variablen
        // import_request_variables("gP", "rvar_");

/*$sn = $rvar_requiredseriennummer;
$id = $rvar_requiredID;
$pid = $rvar_requiredPID;
$lic = $rvar_requiredLizenz;
"HSH-NORDBANK12c43a1957dj7BFFhcb23k20"
 */

        //---------------------------
        int acum1 = 0; // erste Zwischensumme
        int acum2 = 0; // zweite Zwischensumme
        int acum3 = 0; // dritte Zwischensumme
        int acum4 = 0; // vierte Zwischensumme
        int pz1 = 0; // Prüfziffer 1
        int pz2 = 0; // Prüfziffer 2
        int pz3 = 0; // Prüfziffer 3
        String ablauf1 = new String("000000"); // Ablaufsdatum 1 //$NON-NLS-1$
        String ablauf2 = new String("000000"); // Ablaufsdatum 2 //$NON-NLS-1$
        String acum_pid = new String("0000"); //$NON-NLS-1$
        if (p_License==null)
        	return false;
        	
        String serialNumber = p_License.getM_ClientId(); //"012303620501790"; //= new String("011201625014");  //$sn = "0110002024391"; // Aus CaseMaker
        String id = p_License.getM_Pd(); //new String("01"); //$id = "89";            // Aus CaseMaker
        String pid = p_HDSerialNumber; //"0387FBFFh";   // Aus PC - Das heißt, dass Ihr den Processor-Id lesen mußtet.
        String lic = p_License.getM_LicenseValue(); //"BSI-Rueda-Sando0S214b2cc2B79F4cbJ334";//tomorrow(); //$lic =
        // "HANS-HANSHANS5482594c3c3cJu70K4JJaL7";  // Aus www.casemaker.de
        if (serialNumber == null || id == null || pid == null || lic == null) {
            return false;
        }
        if (lic.length() != 36) {
            return false;
        }
        //grueda22112004_begin
        if (id.length() < 2) {
            return false;
        }
        //grueda22112004_end
        // CaseMaker mußte ein Registrierungsfenster zeigen, in den die Daten
        // Seriennummer  ID1     ID2
        // 0110002024391  89   FghNJu70
        // gezeigt werden und aufgefordert wird, sich bei www.casemaker.de zu regsitrieren und
        // die Lizenzdaten, die aus www.CaseMaker.de kommen, als Lizenz eingeben.
        // Wenn derjenige dass tut, dann muß er noch die Lizenzvereinbarung als ok markieren - Ascii-Text ( Datei bekomst Du dennächst)
        // danach wird die Lizenzgeprüft udn wenn alles in Ordnung ist, ist alles ok.
        //$charwert = 0 ;    // Werte
        int[] mod = new int[36]; //$mod = array (1,24,36);
        int mod2 = 0;
        String[] erg = {"0","0"}; //$erg = array ("0","0"); //$NON-NLS-1$ //$NON-NLS-2$
        int[] charwert = new int[36];
        for (int i = 0; i < 36; i++) {
            charwert[i] = (int)lic.charAt(i); //OLD:$charwert[$i] = ord($lic[$i]);

            /**
             * //OLD // bis pos 15 if ( i < 15) { acum1 +=  charwert[ i];// Acumuliere die Werte der Positionen 1 bis 15 für
             * die Position 16 - Prüfziffer 1 mod[ i] = gmp_mod(( charwert[ i]+ mod2), i+1); mod2 =  mod[ i]; }  // if i < 15
             */
            //NEW *******************************************************************
            if (i < 15) {
                acum1 += charwert[i]; // Acumuliere die Werte der Positionen 1 bis 15 für die Position 16 - Prüfziffer 1
                if (i < 10) {
                    mod[i] = gmp_mod((charwert[i] + mod2), i + 1);
                }
                else {
                    mod[i] = gmp_mod((charwert[i] + mod2), 10);
                }
                mod2 = mod[i];
            } // if i < 15
            //***********************************************************************
            // bis Pos 19
            if (i >= 15 && i < 19) {
                // pos 16
                if (i == 15) {
                    mod[i] = gmp_mod(acum1 + mod2, 10);
                    pz1 = mod[i]; // 1. Prüfziffer;
                    mod2 = mod[i];
                }
                // pos 17 und 18
                if (i > 15 && i < 18) {
                    mod[i] = gmp_mod(charwert[i] + mod2, i - 5);
                    mod2 = mod[i];
                }
                // pos 19
                if (i == 18) {
                    acum2 = ((acum1 * 2) + charwert[16] + charwert[17]); // Acumuliere die Werte für die Position 19 - Prüfziffer 2
                    mod[i] = gmp_mod(acum2 + mod2, 10);
                    pz2 = mod[i]; // 2. Prüfziffer;
                    mod2 = mod[i];
                }
            } // if bis pos 19
            // bis pos 29
            if (i > 18 && i < 29) {
                acum4 += charwert[i]; // Acumuliere Werte für Prüfziffer 3
                if (i < 25) {
                    mod[i] = gmp_mod(charwert[i] + mod2, 10);
                    int tempVariable = mod[i];
                    StringBuffer sAblauf1Buffer = new StringBuffer(ablauf1);
                    StringBuffer sModeBuffer = new StringBuffer();
                    sModeBuffer.append(mod[i]);
                    char charValue = sModeBuffer.toString().charAt(0);
                    sAblauf1Buffer.setCharAt(i - 19, charValue); // ablauf1[ i-19] =  mod[ i];  // 1. Ablaufsdatum
                    ablauf1 = sAblauf1Buffer.toString();
                    mod2 = mod[i];
                }
                if (i > 24 && i < 29) {
                    acum3 += charwert[i]; // Acumuliere die Werte für Ablauf 2
                    mod[i] = gmp_mod(charwert[i] + mod2, i + 1);
                    StringBuffer sBuffer = new StringBuffer(acum_pid);
                    char charValue = lic.charAt(i); // lic[ i];
                    sBuffer.setCharAt(i - 25, charValue);
                    acum_pid = sBuffer.toString();
                    // acum_pid[ i-25] =  lic[ i]; // Aus der Lizenz
                    mod2 = mod[i];
                }
            } // bis pos 29
            if (i > 28) {
                if (i < 33) {
                    // OLD by Pepe mod[ i] = gmp_mod( charwert[ i]+  mod[ i-4]+ acum3 ,10);
                    //mod[ i] = gmp_mod( charwert[ i]+  mod2,10);
                    mod[i] = gmp_mod(charwert[i] + mod[i - 4] + acum3, 10);
                    mod2 = mod[i];
                    StringBuffer sAblauf2Buffer = new StringBuffer(ablauf2);
                    StringBuffer sModBuffer = new StringBuffer();
                    sModBuffer.append(mod[i]);
                    char charValue = sModBuffer.toString().charAt(0);
                    sAblauf2Buffer.setCharAt(i - 29, charValue);
                    ablauf2 = sAblauf2Buffer.toString();
                    //LATER:  ablauf2[ i-29] =  mod[ i]; // // 2. Ablaufsdatum  4 Ziffern
                }
                if (i > 32 && i < 35) {
                    //OLD: By Pepe: mod[ i] = gmp_mod( charwert[ i]+ acum3 ,10);
                    //mod[ i] = gmp_mod( charwert[ i] + mod2 ,10);
                    mod[i] = gmp_mod(charwert[i] + acum3, 10);
                    mod2 = mod[i];
                    StringBuffer sAblauf2Buffer = new StringBuffer(ablauf2);
                    StringBuffer sModBuffer = new StringBuffer();
                    sModBuffer.append(mod[i]);
                    char charValue = sModBuffer.toString().charAt(0);
                    sAblauf2Buffer.setCharAt(i - 29, charValue);
                    ablauf2 = sAblauf2Buffer.toString();
                    //LATER:  ablauf2[ i-29] =  mod[ i]; // 2. Ablaufsdatum 2 Ziffern
                }
                if (i == 35) {
                    mod[i] = gmp_mod(acum4 + mod[28], 10);
                    pz3 = mod[i];
                }
            } // if bis pos 36
        } // for
        /// Jetzt kommen die Prüfungen
        // Mod ist ein Array muß als String gewandelt werden
        StringBuffer modBuffer = new StringBuffer();
        int numOfMods = mod.length;
        for (int j = 0; j < numOfMods; j++) {
            modBuffer.append(mod[j]);
        }
        String serialNumber_2 = modBuffer.toString();
        StringBuffer pz1_Buffer = new StringBuffer();
        pz1_Buffer.append(pz1);
        char pz1Char = pz1_Buffer.toString().charAt(0);
        StringBuffer pz2_Buffer = new StringBuffer();
        pz2_Buffer.append(pz2);
        char pz2Char = pz2_Buffer.toString().charAt(0);
        StringBuffer pz3_Buffer = new StringBuffer();
        pz3_Buffer.append(pz3);
        char pz3Char = pz3_Buffer.toString().charAt(0);
        if (serialNumber.equals(serialNumber_2.substring(0, serialNumber.length()))) {
            if (ablauf1.equals(ablauf2)) {
                int position = pid.length() - 4;
                if (position >= 0) {
                    if (acum_pid.equals(pid.substring(position, pid.length()))) {
                        if (pz1Char == id.charAt(0)) { // Prüfziffer 1 gegen ID
                            if (pz2Char == id.charAt(1)) { // Prüfziffer 2 gegen ID
                                if (pz3Char == lic.charAt(35)) { // Prüfziffer 3 gegen Lizenz
                                    if (checkDate) {
                                        if (checkDate(ablauf2)) {
                                            return true;
                                        }
                                        else {
                                            JOptionPane.showMessageDialog(null, CMMessages.getString("INFO_LICENSE_HAS_EXPIRED")); //$NON-NLS-1$
                                            return false;
                                        }
                                    }
                                    else {
                                        return true;
                                    }
                                }
                                else {
                                    return false;
                                }
                            }
                            else {
                                return false;
                            }
                        }
                        else {
                            return false;
                        }
                    }
                    else {
                        return false;
                    }
                }
                else {
                    return false;
                }
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }

    public boolean checkDate(String p_EndDate) {
        Calendar calendarToday = Calendar.getInstance();
        StringBuffer buffer = new StringBuffer();
        buffer.append(p_EndDate.charAt(0));
        buffer.append(p_EndDate.charAt(1));
        Integer temp = new Integer(buffer.toString());
        int dayOfEndDate = temp.intValue();
        buffer = new StringBuffer();
        buffer.append(p_EndDate.charAt(2));
        buffer.append(p_EndDate.charAt(3));
        temp = new Integer(buffer.toString());
        int monthOfEndDate = temp.intValue();
        buffer = new StringBuffer();
        buffer.append(p_EndDate.charAt(4));
        buffer.append(p_EndDate.charAt(5));
        temp = new Integer(buffer.toString());
        int yearOfEndDate = 0;
        if (temp.intValue() >= 0 && temp.intValue() <= 80) {
            yearOfEndDate = temp.intValue() + 2000;
        }
        else {
            yearOfEndDate = temp.intValue() + 1900;
        }
        Calendar calendarEndDate = Calendar.getInstance();
        calendarEndDate.set(Calendar.YEAR, yearOfEndDate);
        calendarEndDate.set(Calendar.MONTH, monthOfEndDate - 1);
        calendarEndDate.set(Calendar.DAY_OF_MONTH, dayOfEndDate);
        if (!isFirstOlderThankSecondDate(calendarToday, calendarEndDate)) {
            if (isSystemDateValid(calendarEndDate)) {
                return true;
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }

    String buildWindowsDirectory(char p_Drive, String p_WindowsDirectory) {
        StringBuffer completeSearchPath = new StringBuffer();
        completeSearchPath.append(p_Drive);
        completeSearchPath.append(":"); //$NON-NLS-1$
        //completeSearchPath.append("\\"); //$NON-NLS-1$
        completeSearchPath.append(BusinessRules.URL_SEPARATOR); //$NON-NLS-1$
        completeSearchPath.append(p_WindowsDirectory);
        return completeSearchPath.toString();
    }

    boolean directoryPathExists(String p_DirectoryPath) {
        File file = new File(p_DirectoryPath);
        if (file == null) {
            return false;
        }
        if (file.exists()) {
            return true;
        }
        else {
            return false;
        }
    }

    boolean directoryExists(char p_Drive) {
        StringBuffer sBuffer = new StringBuffer();
        sBuffer.append(p_Drive);
        sBuffer.append(":\\"); //$NON-NLS-1$
        File file = new File(sBuffer.toString());
        //grueda30122004_begin
        if (file == null) {
            return false;
        }
        //grueda30122004_end
        if (file.exists()) {
            return true;
        }
        else {
            return false;
        }
    }

    boolean isFirstOlderThankSecondDate(Calendar p_First, Calendar p_Second) {
        int firstYear = p_First.get(Calendar.YEAR);
        int firstMonth = p_First.get(Calendar.MONTH);
        int firstDay = p_First.get(Calendar.DAY_OF_MONTH);
        int secondYear = p_Second.get(Calendar.YEAR);
        int secondMonth = p_Second.get(Calendar.MONTH);
        int secondDay = p_Second.get(Calendar.DAY_OF_MONTH);
        if (firstYear > secondYear) {
            return true;
        }
        else if (firstYear == secondYear) {
            if (firstMonth > secondMonth) {
                return true;
            }
            else if (firstMonth == secondMonth) {
                if (firstDay > secondDay) {
                    return true;
                }
            }
        }
        return false;
    }

    boolean foundOlderDate(String p_Path, Calendar p_EndDate) {
        Calendar calendarOfFile = Calendar.getInstance();
        File file = new File(p_Path);
        if (file != null) {
            if (file.exists()) {
                File[] files = file.listFiles();
                //grueda30122004_begin
                if (files != null) {
                    if (files.length < 3) {
                        return true;
                    }
                    for (int i = 0; i < files.length; i++) {
                        Date lastModified = new Date(files[i].lastModified());
                        calendarOfFile.setTime(lastModified);
                        if (isFirstOlderThankSecondDate(calendarOfFile, p_EndDate)) {
                            return true;
                        }
                    }
                }
                else {
                    return true;
                }
                //grueda30122004_end
            }
            else {
                //grueda30122004_begin
                return false;
                //grueda30122004_end
            }
            return false;
        }
        else {
            //grueda30122004_begin
            return false;
            //grueda30122004_end
        }
    }

    private String runConsoleCommand(String command) throws IOException {
        String commandPromt = "cmd /C ";
        Process p = Runtime.getRuntime().exec(commandPromt + command);
        InputStream stdoutStream = new BufferedInputStream(p.getInputStream());
        StringBuffer buffer = new StringBuffer();
        for ( ; ; ) {
            int c = stdoutStream.read();
            if (c == -1) break;
            buffer.append((char)c);
        }
        String outputText = buffer.toString();
        stdoutStream.close();
        return outputText;
    }

    static class StreamReader extends Thread {
        private InputStream is;
        private StringWriter sw;

        StreamReader(InputStream is) {
            this.is = is;
            sw = new StringWriter();
        }

        public void run() {
            try {
                int c;
                while ((c = is.read()) != -1)
                    sw.write(c);
            }
            catch (IOException e) { ; }
        }

        String getResult() {
            return sw.toString();
        }
    }


    public String getWindowsSystemRootPath() {
        try {
            Process process = Runtime.getRuntime().exec(SYSTEMROOT_FOLDER_CMD);
            StreamReader reader = new StreamReader(process.getInputStream());
            reader.start();
            process.waitFor();
            reader.join();
            String result = reader.getResult();
            int p = result.indexOf(REGSTR_TOKEN);
            if (p == -1)
                return null;
            String systemRoot = result.substring(p + REGSTR_TOKEN.length()).trim();
            File file = new File(systemRoot);
            if (file.exists()) {
                return systemRoot;
            }
            else {
                return null;
            }
        }
        catch (Exception e) {
            return null;
        }
    }

    private boolean isBackSlash(char x) {
        char j = '\\';
        return x == j;
    }

    private boolean isDobluePoint(char x) {
        char j = ':';
        return x == j;
    }

    private boolean isEOF(char x) {
        char j = '\u0003'; //'#';
        return x == j;
    }

    private String parseWindir(String windir) {
        windir.trim();
        StringBuffer ac = new StringBuffer();
        char cc;
        int i = 0;
        int estado = 0;
        while (true) { //loop for ever
            if (windir.length() > i) {
                cc = windir.charAt(i);
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
                        else if (isEOF(cc)) {
                            estado = 6;
                        }
                        break;
                    }
                case 1: {
                        if (isDobluePoint(cc)) {
                            i++;
                            ac.append(cc);
                            estado = 2;
                        }
                        else {
                            estado = 6;
                        }
                        break;
                    }
                case 2: {
                        if (isBackSlash(cc)) {
                            i++;
                            ac.append(cc);
                            estado = 3;
                        }
                        else {
                            estado = 6;
                        }
                        break;
                    }
                case 3: {
                        if (isBackSlash(cc) || Character.isSpaceChar(cc) || Character.isLetter(cc) || Character.isDigit(cc)) {
                            i++;
                            ac.append(cc);
                            estado = 3;
                        }
                        else {
                            estado = 6;
                        }
                        break;
                    }
                case 6: {
                        return ac.toString();
                    }
            }
        }
    }

    private String getWinDirectorPath() {
        String setResult = null;
        try {
            return GetEnvironmentVariables.getInstance().getSystemRootVariable();

            /*
            setResult = runConsoleCommand("set");
            setResult = setResult.toLowerCase();
            int indexWinDir = setResult.lastIndexOf("systemroot=");
            if (indexWinDir != -1) {
                String windir = setResult.substring(indexWinDir - 1);
                int indexEqual = windir.indexOf("=");
                if (indexEqual != -1) {
                    windir = windir.substring(indexEqual + 1);
                    windir = parseWindir(windir);
                    File file = new File(windir);
                    if (file.exists()) {
                        return windir;
                    }
                    else {
                        return null;
                    }
                }
                else {
                    int indexDots = windir.lastIndexOf(":");
                    if (indexDots != -1) {
                        windir = windir.substring(indexEqual - 1);
                        windir = parseWindir(windir);
                        File file = new File(windir);
                        if (file.exists()) {

                            return windir;
                        }
                        else {
                            return null;
                        }
                    }
                    else {
                        return null;
                    }
                }
            }
            else
                return null;*/
        }
        catch (Exception ex) {
            return null;
        }
    }

    private boolean validateDateWithSystemRootOrWindir(String windir, Calendar p_EndDate) {
        String windowsDirectory = null;
        String windowsSystemDirectory = null;
        if (windir != null) {
            windowsDirectory = windir.trim();
            windowsSystemDirectory = windowsDirectory.concat("\\system");
            Logger.getLogger(this.getClass()).info(windowsDirectory);
            Logger.getLogger(this.getClass()).info(windowsSystemDirectory);
            if (directoryPathExists(windowsDirectory) && directoryPathExists(windowsSystemDirectory)) {
                if (foundOlderDate(windowsDirectory, p_EndDate)) {
                    return false;
                }
                else if (foundOlderDate(windowsSystemDirectory, p_EndDate)) {
                    return false;
                }
                else {
                    return true;
                }
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }

    public boolean dateIsCorrectInWindowsXP(Calendar p_EndDate) {
        String windir = getWindowsSystemRootPath(); //getWinDirectorPath();
      //  String x = this.windir; // "windows"; //$NON-NLS-1$
      //  String y = windirsystem; //"windows\\system"; //$NON-NLS-1$
      //  String ds = "CDEFGHIJKLMNOPQRSTUVWXZ"; //$NON-NLS-1$
        String windowsDirectory = null;
        String windowsSystemDirectory = null;
        if (validateDateWithSystemRootOrWindir(windir, p_EndDate)) {
            return true;
        }
        else {
            windir = getWinDirectorPath();
            if (validateDateWithSystemRootOrWindir(windir, p_EndDate)) {
                return true;
            }
            else {
                Session2 session = m_CMApplication.getSessionManager().readSession2("cm.ini.xml");
                String existWinDir = null;
                if (session != null) {
                    existWinDir = session.getWindowsDirectory();
                }
                if (existWinDir == null) {
                    do {
                        windowsDirectory = JOptionPane.showInputDialog(null, CMMessages.getString("WINDOWS_SYSTEM_ROOT_MESSAGE"), "C:\\WINDOWS");
                        windowsSystemDirectory = windowsDirectory.concat("\\system");
                    } while (!directoryPathExists(windowsDirectory) || !directoryPathExists(windowsSystemDirectory));
                    if (session != null) {
                        session.setWindowsDirectory(windowsDirectory);
                        m_CMApplication.getSessionManager().writeSession2ToFile(session);
                    }
                    return validateDateWithSystemRootOrWindir(windowsDirectory, p_EndDate);
                }
                else {
                    return validateDateWithSystemRootOrWindir(existWinDir, p_EndDate);
                }

                /*
                boolean directoryFound = false;
                for (int i = 0; i < ds.length(); i++) {
                    windowsDirectory = buildWindowsDirectory(ds.charAt(i), x);
                    windowsSystemDirectory = buildWindowsDirectory(ds.charAt(i), y);
                    if (directoryPathExists(windowsDirectory) && directoryPathExists(windowsSystemDirectory)) {
                        directoryFound = true;
                        if (foundOlderDate(windowsDirectory, p_EndDate)) {
                            return false;
                        }
                        else if (foundOlderDate(windowsSystemDirectory, p_EndDate)) {
                            return false;
                        }
                    }
                }
                if (!directoryFound) {
                    return false;
                }
                return true;*/
            }
        }
    }

    public boolean dateIsCorrectInUNIX() {
        return false;
    }

    public boolean dateIsCorrectInMacintosh() {
        return false;
    }

    public boolean dateIsCorrectInLinux() {
        return false;
    }

    private void getRealPathForWindowsDir(String operatingSystem) {
        if (operatingSystem.equalsIgnoreCase("Windows 2000") || operatingSystem.equalsIgnoreCase("Windows NT")) {
            windir = "WINNT";
            windirsystem = "WINNT\\system";
        }
        else if (operatingSystem.equalsIgnoreCase("Windows 95") || operatingSystem.equalsIgnoreCase("Windows 98") ||
            operatingSystem.equalsIgnoreCase("Windows XP")) {
                windir = "windows";
                windirsystem = "windows\\system";
        }
    }

    public boolean isSystemDateValid(Calendar p_EndDate) {
        String operatingSystem = System.getProperty("os.name"); //$NON-NLS-1$
        Logger.getLogger(this.getClass()).info("OS NAME  " + operatingSystem);
        operatingSystem = operatingSystem.toLowerCase();
        int index = operatingSystem.indexOf("windows"); //$NON-NLS-1$
        if (index >= 0) {
            getRealPathForWindowsDir(operatingSystem);
            if (dateIsCorrectInWindowsXP(p_EndDate)) {
                return true;
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }

    public String getHardDiscSerialNumber(char p_Drive) {
        Vector vInfo = new Vector(0);
        try {
            Runtime rt = Runtime.getRuntime();
            StringBuffer commandBuffer = new StringBuffer();
            commandBuffer.append("cmd.exe "); //$NON-NLS-1$
            commandBuffer.append("/"); //$NON-NLS-1$
            commandBuffer.append(p_Drive);
            commandBuffer.append(" vol"); //$NON-NLS-1$
            Process proc = rt.exec(commandBuffer.toString());
            StreamGobbler errorGobbler = new StreamGobbler(proc.getErrorStream(), "ERROR"); //$NON-NLS-1$
            StreamGobbler outputGobbler = new StreamGobbler(proc.getInputStream(), "OUTPUT"); //$NON-NLS-1$
            errorGobbler.start();
            outputGobbler.start();
            int infoSize = vInfo.size();
            while (infoSize == 0) {
                int exitVal = proc.waitFor();
                vInfo = outputGobbler.getVolumeInfo();
                infoSize = vInfo.size();

            }
            if (vInfo.size() > 1) {
                String sSerialNumber = (String)vInfo.elementAt(1);
                int index = sSerialNumber.lastIndexOf(" "); //$NON-NLS-1$
                Logger.getLogger(this.getClass()).info(sSerialNumber);
                Logger.getLogger(this.getClass()).info(index);
                if (index >= 0) {
                    String output = sSerialNumber.substring(index + 1, sSerialNumber.length());
                    return output;
                }
                else {
                    return "empty"; //$NON-NLS-1$
                }
            }
            else {
                return "empty"; //$NON-NLS-1$
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return "empty"; //$NON-NLS-1$
        }
    }

    //////////////////////////////////////////////////////////////////////////////
    class StreamGobbler extends Thread {
        InputStream is;
        String type;
        StringBuffer serialNumber = new StringBuffer();
        Vector volumeInfo = new Vector(0);

        StreamGobbler(InputStream is, String type) {
            this.is = is;
            this.type = type;
        }

        public Vector getVolumeInfo() {
            return volumeInfo;
        }

        public void run() {
            try {
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String line = null;
                int lineNumber = 0;
                while ((line = br.readLine()) != null)
                    volumeInfo.addElement(line);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }


    ////////////////////////////////////////////////////////////////////////////////
    private String windir = "windows"; //$NON-NLS-1$
    private String windirsystem = "windows\\system"; //$NON-NLS-1$
    private static final String REGQUERY_UTIL = "reg query "; //$NON-NLS-1$
    private static final String REGSTR_TOKEN = "REG_SZ"; //$NON-NLS-1$
    private static final String SYSTEMROOT_FOLDER_CMD =
        REGQUERY_UTIL + "\"HKLM\\Software\\Microsoft\\Windows NT\\currentVersion\" /v SystemRoot"; //$NON-NLS-1$
}
