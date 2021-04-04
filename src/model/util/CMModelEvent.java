/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved. 
 */
package model.util;

import model.CMField;

/**
 * @author smoreno
 *
 */
public class CMModelEvent {

	private CMModelSource source;
	private CMField changedField;
	public CMModelEvent(CMModelSource p_source, CMField p_changedField) {
		super();
		this.source = p_source;
		this.changedField = p_changedField;
	}
	public CMModelSource getSource() {
		return this.source;
	}
	public CMField getChangedField() {
		return this.changedField;
	}
	
	
}
