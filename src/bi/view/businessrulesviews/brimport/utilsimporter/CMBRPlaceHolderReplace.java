package bi.view.businessrulesviews.brimport.utilsimporter;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author portiz
 * 
 */
public class CMBRPlaceHolderReplace {

	private Map<String, String> __placeHolderValues = new HashMap<String, String>();;

	/**
	 * This method receives a string, that represents a placeholder. If the
	 * placeholder exists,returns the replacements of it. If the placeholder
	 * doesn' t exists, returns the placeholder itself.
	 * 
	 * @param __strPlaceHolder
	 *            String that represents a placehoder
	 * @return A string, that could be the placeholder itself or its
	 *         replacement.
	 */
	public String replacePlaceHolder(String __strPlaceHolder) {

		if (__placeHolderValues.containsKey(__strPlaceHolder))
			return __placeHolderValues.get(__strPlaceHolder);
		else
			return __strPlaceHolder;
	}

	/**
	 * Set a replacement for a placeholder.
	 * @param __strPlaceHolder
	 *            The placeholder.
	 * @param __strSpectedValue
	 *            Spected replacement for the placeholder.
	 */
	public void setPlaceHolder(String __strPlaceHolder, String __strSpectedValue) {
		if (!__placeHolderValues.containsKey(__strPlaceHolder))
			__placeHolderValues.put(__strPlaceHolder, __strSpectedValue);
	}

	/**
	 * Get the replacement value for the placeholder. 
	 * @param __strPlaceHolder The placeholder.
	 * @return If the placeholder exists, return its replacement, else null
	 */
	public String getValue(String __strPlaceHolder) {
		if (__placeHolderValues.containsKey(__strPlaceHolder))
			return __placeHolderValues.get(__strPlaceHolder);
		else
			return null;
	}

}
