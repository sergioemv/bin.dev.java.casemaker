package bi.controller;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import javax.swing.JOptionPane;

import model.BusinessRules;
import model.License;

import org.apache.log4j.Logger;

import bi.controller.utils.CMBaseObjectReader;
import bi.view.lang.CMMessages;
import biz.bi.license.controller.RemoteLicenseManager;

public class LicenseManager {
/*    public License readLicense() {
  	  StringBuffer filePath = new StringBuffer();
        //deleteLastQuoteInInstalationDirectory();
  	  filePath.append(m_InstallationDirectory);
  	  filePath.append(BusinessRules.URL_SEPARATOR);
  	  filePath.append("lic.xml");
        //filePath.append('"');
        License license = this.readLicense(filePath.toString());
        return license;
      }
*/
	public boolean notifyBeforeSave() 
	{
  	  RemoteLicenseManager RLM = new RemoteLicenseManager();
  	  return RLM.notifyBeforeSave();
	}
	public String getClientType()
	{
		RemoteLicenseManager RLM = new RemoteLicenseManager();
	  	return RLM.getClientType();
	}
	public String getLicenseVersion()
	{
		RemoteLicenseManager RLM = new RemoteLicenseManager();
	  	return RLM.getLicenseVersion();
	}
    public License getExistingLicense(String m_InstallationDirectory) {
		StringBuffer filePath = new StringBuffer();
        //deleteLastQuoteInInstalationDirectory();
		filePath.append(m_InstallationDirectory);
		filePath.append(BusinessRules.URL_SEPARATOR);
		filePath.append("lic.xml");
        //filePath.append('"');
		License license = this.readLicense(filePath.toString());
		if( license != null) {
		  return license;
		}
		else {
		  return null;
		}
    }
	
      public License readLicense(String p_Filename){
         Object obj = null;
         try {
          File filename=new File(p_Filename);
          //Ccastedo begins 25-01-06
          CMBaseObjectReader in = new CMBaseObjectReader(new FileReader(filename),p_Filename);
         // if (in.isvalidFile())
           obj = in.readObject();
           if( obj == null) {
           	    in.close();
           	    return null;
             }
           //Ccastedo ends 25-01-06
           
           //CC  JSX.ObjectReader in = new JSX.ObjectReader(new FileReader(filename));
  		 //CC  obj = in.readObject();
           in.close();
         } catch(java.io.FileNotFoundException e1) {
        	 Logger.getLogger(this.getClass()).error(e1);
           JOptionPane.showMessageDialog(null,CMMessages.getString("LICENSE_FILE_NOT_FOUND"),CMMessages.getString("TESTDATA_TITLE_ERROR"),JOptionPane.ERROR_MESSAGE);
           return new License("000000000000000000000000000000000000", "000000000000000", "00");
         } catch(java.lang.ClassNotFoundException e2) {
        	 Logger.getLogger(this.getClass()).error(e2);
           JOptionPane.showMessageDialog(null,CMMessages.getString("LICENSE_CORRUPTED"),CMMessages.getString("TESTDATA_TITLE_ERROR"),JOptionPane.ERROR_MESSAGE);
           return new License("000000000000000000000000000000000000", "000000000000000", "00");
         } catch(java.io.InvalidClassException e3) {
        	 Logger.getLogger(this.getClass()).error(e3);
           JOptionPane.showMessageDialog(null,CMMessages.getString("LICENSE_CORRUPTED"),CMMessages.getString("TESTDATA_TITLE_ERROR"),JOptionPane.ERROR_MESSAGE);
           return new License("000000000000000000000000000000000000", "000000000000000", "00");
         } catch(java.io.StreamCorruptedException e4) {
        	 Logger.getLogger(this.getClass()).error(e4);
           JOptionPane.showMessageDialog(null,CMMessages.getString("LICENSE_CORRUPTED"),CMMessages.getString("TESTDATA_TITLE_ERROR"),JOptionPane.ERROR_MESSAGE);
           return new License("000000000000000000000000000000000000", "000000000000000", "00");
         } catch(java.io.OptionalDataException e5) {
        	 Logger.getLogger(this.getClass()).error(e5);
           JOptionPane.showMessageDialog(null,CMMessages.getString("LICENSE_CORRUPTED"),CMMessages.getString("TESTDATA_TITLE_ERROR"),JOptionPane.ERROR_MESSAGE);
           return new License("000000000000000000000000000000000000", "000000000000000", "00");
         } catch(java.io.IOException e6) {
        	 Logger.getLogger(this.getClass()).error(e6);
           JOptionPane.showMessageDialog(null,CMMessages.getString("LICENSE_CORRUPTED"),CMMessages.getString("TESTDATA_TITLE_ERROR"),JOptionPane.ERROR_MESSAGE);
           return new License("000000000000000000000000000000000000", "000000000000000", "00");
         }
         return (License)obj;
      }

      public boolean existLicenseFile(String m_InstallationDirectory ) {
  		StringBuffer filePath = new StringBuffer();
          //deleteLastQuoteInInstalationDirectory();
  		filePath.append(m_InstallationDirectory);
  		filePath.append(BusinessRules.URL_SEPARATOR);
  		filePath.append("lic.xml");
         // filePath.append('"');
          File file = new File(filePath.toString());
          if( file.exists()) {
            return true;
          }
          else {
            return false;
          }
      }
      public void writeLicense(License p_License,String m_InstallationDirectory) {
  	  StringBuffer filePath = new StringBuffer();
  	  filePath.append(m_InstallationDirectory);
  	  filePath.append(BusinessRules.URL_SEPARATOR);
  	  filePath.append("lic.xml");
//  	  writeLicense(p_License, filePath.toString());
      try {
           File fileName=new File(filePath.toString());
           JSX.ObjectWriter out = new JSX.ObjectWriter(new FileWriter(fileName));
           out.writeObject(p_License);
           out.close();
         } catch(java.io.IOException e) {
        	 Logger.getLogger(this.getClass()).error(e);
         }
      }
      public License writeNewLicenseFile(String m_InstallationDirectory) {
  	  StringBuffer filePath = new StringBuffer();
  	//  filePath.append(m_InstallationDirectory);
  	//  filePath.append(BusinessRules.URL_SEPARATOR);
  	//  filePath.append("lic.xml");
        License license = new License("000000000000000000000000000000000000", "000000000000000", "00");
  	  writeLicense(license, m_InstallationDirectory);
        return license;
      }
    public String getExistingClientName(License p_License) {
      int clientIdLength = p_License.getM_ClientId().length();
	  String licenseValue = p_License.getM_LicenseValue();

      StringBuffer clientName = new StringBuffer();
      for( int i = 0; i < clientIdLength; i++) {
        if( licenseValue != null &&  i < licenseValue.length() )
        {
          char charValue = licenseValue.charAt(i);
          clientName.append(charValue);
        }
      }
      return clientName.toString();
    }

    public String getExistingSerialNumber(License p_License) {
      int clientIdLength = p_License.getM_ClientId().length();
	  String licenseValue = p_License.getM_LicenseValue();

      StringBuffer serialNumber = new StringBuffer();
      for( int i = clientIdLength; i < licenseValue.length(); i++) {
        serialNumber.append(licenseValue.charAt(i));
      }

      return serialNumber.toString();
    }
}
