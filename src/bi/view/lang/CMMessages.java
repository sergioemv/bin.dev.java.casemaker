package bi.view.lang;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;


//facade class
//initialize the available and default bundles
//save modified bundles also
//get an languaje specific string from the bundle (proxied)
//get all available languaje and custom bundles
//set the active languaje (proxied)
public class CMMessages {


		private final static String langDir = "/lang";
		private static String defaultLang;
    	public static String getString(String key) {
		try {
			return CommonResourceBundle.getBundle().getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}

    public static List<String> getLanguages(){
    	return CommonResourceBundle.getLanguages();
    }

	public static void initBundles() throws NullPointerException, FileNotFoundException, IOException{
		 
		Properties props = getLangIniProps();
		
		for (Object name : props.keySet()){
			if (!name.toString().equalsIgnoreCase("Default")){
				try{
				FileInputStream fis = new FileInputStream((new File("").getAbsolutePath()+langDir+"/"+props.getProperty(name.toString())));
				NamedPropertyResourceBundle rb = new NamedPropertyResourceBundle(fis,name.toString());
				fis.close();
				CommonResourceBundle.addResourceBundle(name.toString(),rb );
				}catch(Exception e){
					Logger.getLogger(CMMessages.class).error("Error loading language "+e.getLocalizedMessage());
					continue;
				}
			}
		}
		 defaultLang = (String) props.get("Default");
		if (defaultLang==null)
			throw new NullPointerException(CMMessages.getString("DEFAULT_LANGUAGE_MISSING")); //$NON-NLS-1$

		CommonResourceBundle.setDefaultBundle(defaultLang);
	}

	public static String getDefaultLanguage(){
		if(defaultLang!=""&&defaultLang!=null)
			return defaultLang;
		else
			return "Base";
	}

	public static void setDefaultLanguage(String name) throws IOException {
		defaultLang = name;
		//save the default language for the next reboot
		File file = new File(new File("").getAbsolutePath() + langDir +"/lang.ini");
		Properties props = getLangIniProps();
		props.setProperty("Default", name);
		FileOutputStream fop = new FileOutputStream(file);
		props.store(fop, "Changed default");
	}

	public static ResourceBundle getLangBaseBundle() {
		return CommonResourceBundle.getBundleProp("Base");
	}

	public static NamedPropertyResourceBundle saveLangBundle(Properties bundleProp, String name) throws IOException{
		Properties props = getLangIniProps();
		String bundleFile = props.getProperty(name);

		File file = new File(new File("").getAbsolutePath() + langDir +"/"+bundleFile);
		FileOutputStream fop = new FileOutputStream(file);
		bundleProp.store(fop, "Changed content");
		FileInputStream fis = new FileInputStream(file);
		NamedPropertyResourceBundle rb = new NamedPropertyResourceBundle(fis,name.toString());
		fis.close();
		CommonResourceBundle.addResourceBundle(name.toString(),rb );
		return rb;
	}
	private static Properties getLangIniProps() throws IOException {
		File file = new File(new File("").getAbsolutePath() + langDir +"/lang.ini");
		if (!file.exists()){
			file = createLangIni();
		}
		Properties props = new Properties();
		FileInputStream fis = new FileInputStream(file);
		props.load(fis);
		fis.close();
		return props;
	}

	private static File createLangIni() throws IOException {
		File file = new File(new File("").getAbsolutePath() + langDir +"/lang.ini");
		Properties props = new Properties();
		props.put("Base", "MessagesBundle.properties");
		props.put("Default","English");
		props.put("Spanish","MessagesBundle_es_LA.properties");
		props.put("German", "MessagesBundle_de_DE.properties");
		props.put("English", "MessagesBundle_en_US.properties");
		FileOutputStream fop = new FileOutputStream(file);
		props.store(fop, "Created");
		return file;
	}

	public static ResourceBundle getLangBundle(String bundle) {
		try{
		return CommonResourceBundle.getBundleProp(bundle);
		}
		catch(Exception e){
			return null;
		}

	}

	public static NamedPropertyResourceBundle registerNewBundle(String newLanguageName) throws Exception {
		if (newLanguageName.equalsIgnoreCase(""))
			throw new Exception(CMMessages.getString("LANGUAGE_NAME_EMPTY")); //$NON-NLS-1$
		Properties props = getLangIniProps();
		if (props.get(newLanguageName)!=null)
			throw new Exception(CMMessages.getString("LANGUAGE_NAME_ALREADY_EXISTS")); //$NON-NLS-1$
		File file = new File(new File("").getAbsolutePath() + langDir +"/"+newLanguageName+".lang");
		int i=0;
		while (file.exists()){
			i++;
			file =new File(new File("").getAbsolutePath() + langDir +"/"+newLanguageName+i+".lang");
		}
		FileOutputStream fop = new FileOutputStream(file);
		Properties langProp = new Properties();
		//save the new file
		langProp.store(fop,"created");
		//register in the lang.ini
		props.put(newLanguageName, file.getName());
		File fileprop = new File(new File("").getAbsolutePath() + langDir +"/lang.ini");
		FileOutputStream fopINI = new FileOutputStream(fileprop);
		props.store(fopINI, "Changed content");
		NamedPropertyResourceBundle rb = new NamedPropertyResourceBundle(new FileInputStream(file),newLanguageName);
		CommonResourceBundle.addResourceBundle(newLanguageName, rb);
		return null;
	}

	public static void unRegisterLanguage(String name) throws Exception {
		if (name.equalsIgnoreCase(""))
			throw new Exception(CMMessages.getString("LANGUAGE_NAME_EMPTY")); //$NON-NLS-1$
		Properties props = getLangIniProps();
		props.remove(name);
		File fileprop = new File(new File("").getAbsolutePath() + langDir +"/lang.ini");
		FileOutputStream fopINI = new FileOutputStream(fileprop);
		props.store(fopINI, "unregistered language "+name);
		CommonResourceBundle.removeResourceBundle(name);
	}
}
