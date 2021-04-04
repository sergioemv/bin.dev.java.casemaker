package bi.view.businessrulesviews.brimport.filereader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * Reads a text File. 
 * @author portiz
 * @since 2007-10-23
 */
public class FileReaderCustom {

	//path of the text file
	private String _strDir;

	//Determine if the empty lines are included.
	private boolean _bNoLineSpaces=false;
	
	/**
	 * Gets the path of the text file.
	 * @return The path of the text file.
	 */
	public String getStrDir() {
		return _strDir;
	}
	
	/**
	 * Sets the path of the text file.
	 * @param __strDir
	 */	
	public void setStrDir(String __strDir) {
		this._strDir = __strDir;
	}

	/**
	 * Constructor.
	 */
	public FileReaderCustom() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor.
	 * @param __strDir The path of a text file.
	 */
	public FileReaderCustom(String __strDir) {
		this._strDir = __strDir;
	}
	
	/**
	 * Reads the text file and returns a ArrayList with the lines.
	 * @return a ArrayList with the lines.
	 * @throws Exception
	 */
	public ArrayList<String> readFile() throws Exception{
		try {
			ArrayList<String> list=new ArrayList<String>();
			if (_strDir==null)
				throw new Exception("File not specified");
			File myFile=new File(_strDir);
			if (!myFile.exists() )
				throw new Exception("File not exists");
			String text="";
			BufferedReader toRead=new BufferedReader(new FileReader(_strDir));
			while((text=toRead.readLine())!=null){
				text=text.trim();
				if (_bNoLineSpaces){
					if ( ! text.equals(""))
					list.add(text);
				}
				else
					list.add(text);
			}
				
			toRead.close();
			return list;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Get if line spaces are included
	 * @return True if no are included.
	 */
	public boolean is_bNoLineSpaces() {
		return _bNoLineSpaces;
	}

	/**
	 * Set if line spaces are included
	 * @param noLineSpaces True if no are included.
	 */
	public void set_bNoLineSpaces(boolean noLineSpaces) {
		_bNoLineSpaces = noLineSpaces;
	}
		
}
