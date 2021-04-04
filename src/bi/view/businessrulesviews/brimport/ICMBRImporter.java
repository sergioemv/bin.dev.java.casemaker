package bi.view.businessrulesviews.brimport;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import model.brimport.BRLanguaje;

import org.apache.xmlbeans.XmlException;
/**
 * @author smoreno
 *  @purpose
 *   define a common contract for all classes that can transform from one external file into a Business Rule text
 *   Use this interface to define a new algorithm to transform an external file to a business rule file
 */
public interface ICMBRImporter {

	/**
	 * @param p_importFile
	 *  the file to be imported
	 * @return
	 *  a sintactical correct Business Rule String
	 * @throws IOException
	 *  all exceptions during the operation must be trhowed so the caller can show the error
	 *   do NOT show error messages direclty!
	 * @throws XmlException
	 *  exceptions of the xml reader
	 * @throws Exception
	 */
	public String doImport(File p_importFile,File p_importFileBom, Map<String, Object> aditionalOptions) throws IOException, XmlException, Exception;
	//portiz_26102007_begin
		//new parameter File p_importFileBom,
	//portiz_26102007_end
	//public String doImport(File p_importFile, Map<String, Object> aditionalOptions) throws IOException, XmlException, Exception;
	

	
	/**
	 * sets the languaje that the import will output
	 * @param languaje
	 */
	public void setLanguaje(BRLanguaje languaje);

	public String getWarningMessages();

}