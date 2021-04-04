package bi.view.businessrulesviews.brimport.filereader;

import java.util.ArrayList;

/**
 * Parse a ArrayList.
 * @author portiz
 * @since 2007-10-23
 */
public class LineParser {

	//repository of lines.
	private ArrayList<String> _arrLines;
	
	//cursor of the parse.
	private Integer _i;
	
	/**
	 * Constructor.
	 * @param __arrLines An ArrayList to be parsed.
	 */
	public LineParser(ArrayList<String> __arrLines){
		this._arrLines=__arrLines;
		this.reset();
	}
	
	/**
	 * Indicates if the parser reaches the end.
	 * @return True if is at the end (the end has nothing to read.)
	 */
	public boolean isEndOfFile(){
		return ( (_i<0) || (_arrLines==null) || (_i >= _arrLines.size()) );
	}
		
	/**
	 * Read a line from the parsed ArrayList.Not advance the parser.
	 * @return A parsed String.
	 */
	public String readLine(){
		if (_i==-1)
			return null;
		else
			return _arrLines.get(_i);
	}
		
	/**
	 * Read a line from the parsed ArrayList.Advance the parses
	 * @return A parsed String.
	 */
	public String readLineAdvance(){
		if (this.isEndOfFile())
			return null;
		else
			return _arrLines.get(_i++);
	}
	
	/**
	 * Advance one position in the parser
	 */
	public void advance(){
		_i++;
	}
	
	/**
	 * Go one step back.
	 */
	public void backward(){
		_i++;
	}
	
	/**
	 * Reset the parser.
	 */
	public void reset(){
		if  ( (_arrLines== null ) || (_arrLines.size()==0) )
			_i=-1;
		else
			_i=0;
	}
	
}