package bi.view.lang;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

 abstract class CommonResourceBundle extends ResourceBundle {


	protected static String defaultBundle;

/**
   *  @return a resource bundle

   */

   public static ResourceBundle getBundle(){

      if (instance == null)
    	  instance = new DefResourceBundle();
      return instance;
   }

   /**

   *  @return an array of all resource bundle base names

   */

   public String[] getBaseName(){ return baseName;}

   /**

   *  Adds a resource bundle to the collection of bundles

   *  @param bundle the ResourceBundle to add

   */

   public static void addResourceBundle(String name,ResourceBundle bundle){

      bundles.put(name,bundle);

   }

   /**

   *  Removes a resource bundle from the collection of bundles

   *  @param bundle the ResourceBundle to remove

   */

   public static void removeResourceBundle(String name){

      bundles.remove(name);

   }

   /**

   *  @return Enumeration of the keys

   */

   public abstract Enumeration getKeys();

   /**

   *  Gets an object for the given key from this resource bundle and null if

   *  this resource bundle does not contain an object for the given key

   */

   protected abstract Object handleGetObject(String key);

   /**

   *  Sets the resource bundle base names as an array

   */

   protected CommonResourceBundle(String[] baseName){

     this.baseName  = baseName;

   }

   /**

   *  Sets the resource bundle base names as an array from a string like:

   *  test1,test2 etc or test1 test2 etc

   */

   protected CommonResourceBundle(String baseName){

      buildBaseName(baseName," ,"); //$NON-NLS-1$

   }



   public CommonResourceBundle(){ this(new String[0]);}

   /**

   *  Builds the resource bundle base names as an array from a string like:

   *  test1,test2 etc or test1 test2 etc

   */

   protected void buildBaseName(String base,String delim){

      String s = null;

      try{

         s = System.getProperty(base);

         if (s == null) return;

         StringTokenizer st = new StringTokenizer(s,delim);

         baseName = new String[st.countTokens()];

         int i = 0;

         while(st.hasMoreTokens()) baseName[i++] = st.nextToken().trim();

      }catch (Exception e){

         throw new RuntimeException ("Can not resolve base name: "+s); //$NON-NLS-1$

      }

   }

   /** Resource bundle base names */

   protected      String[] baseName;

   /** Default implementation of this abstract class */

   private static DefResourceBundle instance;

   /** Collection of resource bundles */

   private static Map<String,ResourceBundle> bundles = new HashMap<String,ResourceBundle>();

   /**

   *  Default implementation

   */
   public static void setDefaultBundle(String rb) {
	   defaultBundle = rb;

	}

    static String getDefaultBundle(){
	   return defaultBundle;
   }

   static class DefResourceBundle extends CommonResourceBundle {



      public DefResourceBundle(String[] baseName){

         super(baseName);

      }



      public DefResourceBundle(String baseName){

         super(baseName);

      }



      public DefResourceBundle(){ this(new String[0]);}



      public Enumeration getKeys(){

         return new Enumeration(){

            Enumeration enumer = null;

            int i = 0;

            public boolean hasMoreElements(){

               boolean b = false;

               while (enumer == null || !(b = enumer.hasMoreElements())){

                  if (i >= bundles.size()){

                	  enumer = null;

                     return b;

                  }

                  enumer = ((ResourceBundle)bundles.get(i++)).getKeys();

              }

               return b;

            }

            public Object nextElement(){

               if (enumer == null) throw new NoSuchElementException();

               return enumer.nextElement();

            }

         };

      }


      // iterates over all the bundles searching for the value of a given key

      protected Object handleGetObject(String key) {

         ResourceBundle rb = null;

         String val        = null;

         //first with the default bundle
         try{
        	 val = bundles.get(defaultBundle).getString(key);
        	 if (val!="")
        		 return val;
         }catch (Exception e){}
         //next try with the Base
         try{
        	 return bundles.get("Base").getString(key); //$NON-NLS-1$
         }catch (Exception e){}
         //if not try to find it in the rest of the bundles
         for (int i=0;i<bundles.size();i++){

            rb = bundles.get(i);

            try{

               val = rb.getString(key);

           }catch (Exception e){}

            if (val != null) break;

         }

         return val;

      }

   }

public static List<String> getLanguages() {
	ArrayList<String> langs = new ArrayList<String>();
	langs.addAll(bundles.keySet());
	return langs;

}

public static ResourceBundle getBundleProp(String name){
	return bundles.get(name);
}


}

