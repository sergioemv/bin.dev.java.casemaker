package bi.controller.utils;

import bi.view.lang.CMMessages;

public enum CMXMLFileState {

	CORRUPTEDXML(false) ,
	VALID(true),
	NEWVERSION(false),
	//NOTFOUND(false),
	BIGGERFILE(false);
	private boolean state; 
	CMXMLFileState(boolean p_state)
	{
		this.state = p_state;
	}
	public String getMessage()
	{
		switch (this) {
		case CORRUPTEDXML:
			return CMMessages.getString("FILE_IS_CORRUPTED");
		case NEWVERSION:
			return CMMessages.getString("FILE_IS_ON_NEWER_VERSION");
		case BIGGERFILE:
			return CMMessages.getString("BIGGER_XML_FILES");
		default:
			return CMMessages.getString("FILE_IS_CORRUPTED");
		}
	}
	public boolean getState()
	{return state;}
}
