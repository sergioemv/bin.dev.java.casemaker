/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved. 
 */
package bi.view.utils.event;

import java.util.EventObject;


/**
 * @author smoreno
 *
 */
public class CMProgressEvent extends EventObject{

	private String progress;

	
	public CMProgressEvent(Object p_source,String progress) {
		super(p_source);
		this.progress = progress;
	}
	
	
	public String getProgress() {
		return this.progress;
	}
}
