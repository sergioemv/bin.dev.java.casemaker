/*******************************************************************************
Developed by BUSINESS SOFTWARE INNOVATIONS. .
Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
*******************************************************************************/

package model;

/**
 * This class holds complimentary information that can be obtained during an
 * import procedure but is not a Part of the Test Object<br>
 * @author smoreno
 *
 */
public class TestObjectOrigin {

	private String visioFilePath;
	public TestObjectOrigin(String string) {
			visioFilePath = string;
	}
	public TestObjectOrigin() {
		// TODO Auto-generated constructor stub
	}
	public void setVisioFilePath(String p_VisioFilePath) {
		visioFilePath=p_VisioFilePath;

	}
	protected String getVisioFilePath() {
		return visioFilePath;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getVisioFilePath();
	}
}
