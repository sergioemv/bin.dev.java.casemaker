package bi.view.businessrulesviews.brimport.cmbomparse;

/**
 * Enumerates that represents the visibility.
 * @author portiz
 * @since 2007-10-23
 */
public enum CMBomEVisibility {
	 ePUBLIC,
	 ePRIVATE,
	 ePROTECTED,
	 eNOT_SET;
	 
	/**
	 * Recives a visibility name and returns the correct CMBomEVisibility enumerate.
	 * @param __str String that represents visibility EG.- public
	 * @return The visibility represented by the CMBomEVisibility enumerate. If the 
	 * visibility doesn't exists, return eNOT_SET
	 */
	 public static CMBomEVisibility get_Visibility(String __str){
		String _strAux="e"+__str.toUpperCase();
		if (_strAux.equals(CMBomEVisibility.ePUBLIC.toString()) )
			return CMBomEVisibility.ePUBLIC;
		
		if (_strAux.equals(CMBomEVisibility.ePRIVATE.toString()) )
			return CMBomEVisibility.ePRIVATE;
		
		if (_strAux.equals(CMBomEVisibility.ePROTECTED.toString()) )
			return CMBomEVisibility.ePUBLIC;
		
		return CMBomEVisibility.ePROTECTED;
	 }
	
	/**
	 * Receives a CMBomEVisibility and returns a string tha represents visibility.
	 * @param __eState CMBomEVisibility enumerate.
	 * @return a String that represents CMBomEVisibility.
	 */
	public static String toString(CMBomEVisibility __eState){
		switch (__eState) {
		case ePUBLIC:
			return "Public";
			//break;
		case ePRIVATE:
			return "Private";
			//break;
		case ePROTECTED:
			return "Protected";
			//break;
		default:
			return "Undefined";
		}
}
	
}